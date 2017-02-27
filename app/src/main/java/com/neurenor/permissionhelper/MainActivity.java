package com.neurenor.permissionhelper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.neurenor.permissions.PermissionCallback;
import com.neurenor.permissions.PermissionsHelper;

import java.util.HashMap;

import static android.Manifest.permission.CAMERA;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    PermissionsHelper mHelper;
    Button btnCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCamera = (Button) findViewById(R.id.btnCamera);
        btnCamera.setOnClickListener(this);

        mHelper = new PermissionsHelper(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkCamera();
    }

    private void checkCamera() {
        if (mHelper.isPermissionGranted(CAMERA)) {
            btnCamera.setEnabled(false);
            btnCamera.setText("Camera Granted");
        } else {
            btnCamera.setEnabled(true);
            btnCamera.setText("Request Camera");
        }
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.btnCamera:
                mHelper.requestPermissions(new String[]{CAMERA}, new PermissionCallback() {
                    @Override
                    public void onResponseReceived(final HashMap<String, PermissionsHelper.PermissionGrant> mapPermissionGrants) {
                        PermissionsHelper.PermissionGrant permissionGrant = mapPermissionGrants
                                .get(CAMERA);

                        //updating button's status according to permission. you should check below switch case.
                        checkCamera();

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
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        /**
         * helper's onRequestPermissionsResult must be called from activity.
         */
        mHelper.onRequestPermissionsResult(permissions, grantResults);
    }
}
