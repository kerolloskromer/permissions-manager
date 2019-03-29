# Permissions Manager

A wrapper for android permissions with easy and convenient callback you can check for one permission or multiple permissions at once

## Getting Started

Add this dependency to your app build.gradle file
```
dependencies {
  implementation "com.kromer.permissions-manager:1.0.0"
}
```
Add this to your activity
```
  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    PermissionsManager.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    PermissionsManager.getInstance().onActivityResult(this, requestCode, resultCode, data);
  }
```
Use this to check for one permission
```
    PermissionsManager.getInstance().checkPermission(
        this,
        Manifest.permission.CAMERA, REQUEST_CODE,
        "Camera permission is needed for core functionality",
        "Permission was denied, but is needed for core functionality",
        "Settings",
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
```
## Report bug/issue/improvement

Please feel free to report bug , issue or improvement - see the [Issues](https://github.com/kerolloskromer/permissions-manager/issues) section fisrt to prevent duplicates.
Also, if you know how to fix this issue please feel free to fork this repo and make a pull request and i will gladly review and merge and add you as a contributer :)

## Author

* **Kerollos Kromer** - *Android Developer* - [Linkedin](https://www.linkedin.com/in/kerollos-kromer-39aba078/)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
