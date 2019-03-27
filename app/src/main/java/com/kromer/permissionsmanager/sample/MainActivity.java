package com.kromer.permissionsmanager.sample;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.kromer.permissionsmanager.PermissionCallback;
import com.kromer.permissionsmanager.PermissionsManager;
import com.kromer.permissionsmanager.R;

public class MainActivity extends AppCompatActivity {

  public static final int REQUEST_CODE = 123;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    PermissionsManager.getInstance()
        .checkPermission(this, Manifest.permission.CAMERA, REQUEST_CODE,
            "Camera permission is needed for core functionality",
            "Permission was denied, but is needed for core functionality", "Settings",
            new PermissionCallback() {
              @Override
              public void onGranted() {
                // permission granted
              }

              @Override
              public void onDenied() {
                // permission denied
              }

              @Override
              public void onResult(Intent data) {
                // data received from intent in onActivityResult
              }
            });
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    PermissionsManager.getInstance()
        .onRequestPermissionsResult(this, requestCode, permissions, grantResults);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    PermissionsManager.getInstance().onActivityResult(this, requestCode, resultCode, data);
  }
}