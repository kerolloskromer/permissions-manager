/**
 * MIT License
 *
 * Copyright (c) 2019 Kerollos Kromer
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.kromer.permissionsmanager;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

/**
 * Created by Kerollos Kromer on 20-Mar-19.
 */
public class PermissionsManager {

  private static volatile PermissionsManager instance;

  public synchronized static PermissionsManager getInstance() {
    if (instance == null) instance = new PermissionsManager();

    return instance;
  }

  private PermissionRequest req = null;

  /**
   * Returns the current state of the permissions needed.
   */
  private boolean hasPermission(Activity activity, String permission) {
    return ActivityCompat.checkSelfPermission(activity, permission)
        == PackageManager.PERMISSION_GRANTED;
  }

  public void checkPermission(Activity activity,
      String permission,
      int reqCode,
      String permissionRationale,
      String permissionDeniedExplanation,
      String settingsLabel,
      PermissionCallback callback) {
    if (hasPermission(activity, permission)) {
      callback.onGranted();
    } else {
      // Provide an additional rationale to the user. This would happen if the user denied the
      // request previously, but didn't check the "Don't ask again" checkbox.
      boolean shouldProvideRationale = ActivityCompat
          .shouldShowRequestPermissionRationale(activity, permission);
      PermissionRequest req = new PermissionRequest(
          activity, permission, reqCode, permissionRationale, permissionDeniedExplanation,
          settingsLabel,
          callback);
      if (shouldProvideRationale) {
        showReqReason(req);
      } else {
        // Request permission. It's possible this can be auto answered if device policy
        // sets the permission in a given state or the user denied the permission
        // previously and checked "Never ask again".
        reqPermission(req);
      }
    }
  }

  private void showReqReason(PermissionRequest req) {
    Snackbar
        .make(req.getActivity().getWindow().getDecorView(), req.getPermissionRationale(),
            Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, view -> {
      // Request permission
      reqPermission(req);
    }).show();
  }

  private void reqPermission(PermissionRequest req) {
    this.req = req;
    ActivityCompat.requestPermissions(req.getActivity(), new String[] { req.getPermission() },
        req.getRequestCode());
  }

  private void showRejectedMsg(PermissionRequest req) {
    Snackbar.make(req.getActivity().getWindow().getDecorView(),
        req.getPermissionDeniedExplanation(), Snackbar.LENGTH_INDEFINITE)
        .setAction(req.getSettingsLabel(), view -> openAppSettings(req)).show();
  }

  private void openAppSettings(PermissionRequest req) {
    // Build intent that displays the App settings screen.
    Intent intent = new Intent();
    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
    Uri uri = Uri.fromParts("package", req.getActivity().getPackageName(), null);
    intent.setData(uri);
    req.getActivity().startActivityForResult(intent, req.getRequestCode());
  }

  /**
   * Callback received when a permissions request has been completed.
   */
  public void onRequestPermissionsResult(Activity activity,
      int requestCode,
      @NonNull String[] permissions,
      @NonNull int[] grantResults) {

    if (req != null) {
      if (req.getActivity().equals(activity)
          && req.getRequestCode() == requestCode
          && permissions.length > 0
          && req.getPermission().equals(permissions[0])
          && grantResults.length > 0) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          // Permission was granted.
          req.getCallback().onGranted();
          req = null;
        } else {
          if (TextUtils.isEmpty(req.getPermissionDeniedExplanation())) {
            req.getCallback().onDenied();
            req = null;
          } else {
            // Permission denied.
            showRejectedMsg(req);
          }
        }
      }
    }
  }

  public void onActivityResult(Activity activity, int requestCode, int resultCode,
      Intent data) {
    if (req != null) {
      if (req.getActivity().equals(activity)
          && req.getRequestCode() == requestCode) {
        // we don't check on resultCode because the user may be coming back from settings screen without granting permission,
        // so we need to check permission again
        if (hasPermission(req.getActivity(), req.getPermission())) {
          req.getCallback().onResult(data);
        } else {
          req.getCallback().onDenied();
        }
        req = null;
      }
    }
  }
}