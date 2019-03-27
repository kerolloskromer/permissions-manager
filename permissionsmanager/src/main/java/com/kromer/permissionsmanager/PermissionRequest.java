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
import java.lang.ref.WeakReference;

/**
 * Created by Kerollos Kromer on 20-Mar-19.
 */
class PermissionRequest {
  private WeakReference<Activity> activityWeakReference;
  private String permission;
  private int requestCode;
  private String permissionRationale;
  private String permissionDeniedExplanation;
  private String settingsLabel;
  private PermissionCallback callback;

  PermissionRequest(Activity activity,
      String permission,
      int requestCode,
      String permissionRationale,
      String permissionDeniedExplanation,
      String settingsLabel,
      PermissionCallback callback) {
    this.activityWeakReference = new WeakReference<>(activity);
    this.permission = permission;
    this.requestCode = requestCode;
    this.permissionRationale = permissionRationale;
    this.permissionDeniedExplanation = permissionDeniedExplanation;
    this.settingsLabel = settingsLabel;
    this.callback = callback;
  }

  Activity getActivity() {
    return activityWeakReference.get();
  }

  String getPermission() {
    return permission;
  }

  int getRequestCode() {
    return requestCode;
  }

  String getPermissionRationale() {
    return permissionRationale;
  }

  String getPermissionDeniedExplanation() {
    return permissionDeniedExplanation;
  }

  String getSettingsLabel() {
    return settingsLabel;
  }

  PermissionCallback getCallback() {
    return callback;
  }
}