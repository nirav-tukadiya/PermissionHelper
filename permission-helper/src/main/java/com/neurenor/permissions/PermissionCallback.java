package com.neurenor.permissions;

import java.util.HashMap;

/**
 * <h1>Nirav Tukadiya</h1>
 * <p>
 * <p>
 *
 * @author Neil (Nirav Tukadiya) (niravt@meditab.com | neil.n@meditab.com) Meditab Software Inc.
 * @version 1.0
 * @since 27/02/17 10:16 PM
 */
public interface PermissionCallback {
    void onResponseReceived(HashMap<String, PermissionsHelper.PermissionGrant> mapPermissionGrants);

}
