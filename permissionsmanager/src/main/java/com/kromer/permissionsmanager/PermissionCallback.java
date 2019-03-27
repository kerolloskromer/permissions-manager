package com.kromer.permissionsmanager;

import android.content.Intent;

/**
 * Created by Kerollos Kromer on 20-Mar-19.
 */
public interface PermissionCallback {
  void onGranted();

  void onDenied();

  void onResult(Intent data);
}