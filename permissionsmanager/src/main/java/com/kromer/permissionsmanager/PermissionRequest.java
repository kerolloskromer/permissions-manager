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