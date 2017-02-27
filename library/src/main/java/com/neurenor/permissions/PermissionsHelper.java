package com.neurenor.permissions;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * <h1>Nirav Tukadiya</h1>
 * <p>
 * <p>
 *
 * @author Neil (Nirav Tukadiya) (niravt@meditab.com | neil.n@meditab.com) Meditab Software Inc.
 * @version 1.0
 * @since 24/9/15 10:28 PM
 */
public class PermissionsHelper {

    private static final int PERMISSION = 100;
    private PermissionCallback callback;
    private Context mContext;
    private HashMap<String, PermissionGrant> mapPermissionsGrants;

    public PermissionsHelper(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * Checks whether the permission is granted or not.
     *
     * @param permission permission string
     * @return true or false
     */
    public boolean isPermissionGranted(String permission) {
        return mContext != null && permission != null && ContextCompat.checkSelfPermission(mContext, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Request permissions
     *
     * @param permissions        array of permission strings.
     * @param permissionCallback callback
     * @throws NullPointerException
     */
    public void requestPermissions(String[] permissions, PermissionCallback permissionCallback) throws NullPointerException {
        this.callback = permissionCallback;
        mapPermissionsGrants = new HashMap<>();
        ArrayList<String> lstToBeRequestedPermissions = new ArrayList<>();
        for (String requestedPermission : permissions) {

            if (!isPermissionGranted(requestedPermission)) {
                lstToBeRequestedPermissions.add(requestedPermission);
                mapPermissionsGrants.put(requestedPermission, PermissionGrant
                        .DENIED);
            } else if (isPermissionGranted(requestedPermission)) {
                mapPermissionsGrants.put(requestedPermission, PermissionGrant.GRANTED);
            } else if (!ActivityCompat.shouldShowRequestPermissionRationale(
                    (Activity) mContext, requestedPermission)) {
                mapPermissionsGrants.put(requestedPermission, PermissionGrant
                        .NEVERSHOW);
            }
        }

        if (!lstToBeRequestedPermissions.isEmpty()) {
            if (mContext == null) {
                throw new NullPointerException("Activity instance cannot be null.");
            } else {
                ActivityCompat.requestPermissions((Activity) mContext,
                        lstToBeRequestedPermissions.toArray(new
                                String[lstToBeRequestedPermissions.size()]),
                        PERMISSION);
            }
        } else {
            if (permissionCallback != null) {
                permissionCallback.onResponseReceived(mapPermissionsGrants);
            }
        }

    }

    /**
     * checks whether permission is granted or not and pass the result to {@link PermissionCallback}
     *
     * @param permissions  array of permission strings.
     * @param grantResults results of requested permissions
     */
    public void onRequestPermissionsResult(String[] permissions, int[] grantResults) {
        int index = 0;
        for (String s : permissions) {
            if (grantResults[index] == PackageManager.PERMISSION_GRANTED) {
                mapPermissionsGrants.put(s, PermissionGrant.GRANTED);
            } else if (!ActivityCompat.shouldShowRequestPermissionRationale(
                    (Activity) mContext, s)) {
                mapPermissionsGrants.put(s, PermissionGrant.NEVERSHOW);
            } else if (grantResults[index] == PackageManager.PERMISSION_DENIED) {
                mapPermissionsGrants.put(s, PermissionGrant.DENIED);
            }

            index++;
        }

        if (callback != null) {
            callback.onResponseReceived(mapPermissionsGrants);
        }

    }

    /**
     * possible values for permissions.
     * <p>
     * {@link #GRANTED - permission has been granted}
     * {@link #DENIED - permission has been denied}
     * {@link #NEVERSHOW - permission has been denied and never show has been selected.}
     */
    public enum PermissionGrant {
        GRANTED, DENIED, NEVERSHOW
    }
}
