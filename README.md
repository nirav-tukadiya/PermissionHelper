**Create PermissionHelper object**


        PermissionsHelper helper = new PermissionsHelper(context);


**Check If permission is granted or not**


        if(helper.isPermissionGranted(CAMERA))
        {
            //granted
        }else{
           // not granted
        }


**Request permission**


        helper.requestPermissions(new String[]{CAMERA}, new PermissionCallback() {
                   @Override
                   public void onResponseReceived(final HashMap<String, PermissionsHelper.PermissionGrant> mapPermissionGrants) {
                             PermissionsHelper.PermissionGrant permissionGrant = mapPermissionGrants
                                      .get(CAMERA);

                                switch (permissionGrant) {
                                    case GRANTED:
                                        //permission has been granted
                                        Toast.makeText(MainActivity.this,"Granted",Toast.LENGTH_SHORT).show();
                                        break;
                                    case DENIED:
                                        //permission has been denied
                                        Toast.makeText(MainActivity.this,"Denied",Toast.LENGTH_SHORT).show();
                                        break;
                                    case NEVERSHOW:
                                        //permission has been denied and never show has been selected. Open permission settings of the app.
                                        Toast.makeText(MainActivity.this,"Denied with Never show",Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                        });



**Handle permission result in activity**


    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        helper.onRequestPermissionsResult(permissions, grantResults);
    }


**Gradle**

        compile 'com.neurenor:permission-helper:1.0.1'