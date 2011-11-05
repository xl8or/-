package com.google.android.finsky.download;

import android.content.BroadcastReceiver;

public abstract class DownloadReceiver extends BroadcastReceiver {

   protected static final String ASSET_BLOB_KEY = "asset_blob_url";
   protected static final String ASSET_FORWARD_LOCKED_KEY = "asset_is_forward_locked";
   protected static final String ASSET_ID_KEY = "assetid";
   protected static final String ASSET_NAME_KEY = "asset_name";
   protected static final String ASSET_PACKAGE_KEY = "asset_package";
   protected static final String ASSET_REFUND_PERIOD_END_TIME_KEY = "asset_refundtimeout";
   protected static final String ASSET_SHOULD_BE_SECURED_KEY = "asset_secure";
   protected static final String ASSET_SIGNATURE_KEY = "asset_signature";
   protected static final String ASSET_SIZE_KEY = "asset_size";
   protected static final String ASSET_VERSION_CODE = "asset_version_code";
   protected static final String DECLINE_ASSET_REASON_KEY = "decline_reason";
   protected static final String DIRECT_DOWNLOAD_KEY = "direct_download_key";
   protected static final String DOWNLOAD_ACCOUNT_KEY = "user_email";
   protected static final String DOWNLOAD_AUTH_COOKIE_NAME_KEY = "download_auth_cookie_name";
   protected static final String DOWNLOAD_AUTH_COOKIE_VALUE_KEY = "download_auth_cookie_value";
   protected static final String OBB_DOWNLOAD_URL_KEY = "additional_file_url";
   protected static final String OBB_MAIN_TYPE = "OBB";
   protected static final String OBB_PATCH_TYPE = "OBB_PATCH";
   protected static final String OBB_SIZE_KEY = "additional_file_size";
   protected static final String OBB_TYPE_KEY = "additional_file_type";
   protected static final String OBB_VERSION_CODE_KEY = "additional_file_version_code";
   protected static final String SERVER_INITIATED_KEY = "server_initiated";
   protected static final String SHOW_NOTIFICATION_KEY = "show_notification";


   public DownloadReceiver() {}
}
