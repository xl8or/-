package com.htc.android.mail.eassvc.core;

import android.content.Context;

public class SyncException extends RuntimeException {

   public static final int BACKEND_ERROR = 506;
   public static final int CANCEL_SYNC_SOURCE = 601;
   public static final int CERTIFICATE_EXPIRED = 518;
   public static final int CERTIFICATE_MISMATCH = 517;
   public static final int CERTIFICATE_NOT_VALID_YET = 519;
   public static final int CERTIFICATE_UNTRUST = 516;
   public static final int CONNECTION_ABORT = 453;
   public static final int CONNECTION_FAIL = 450;
   public static final int CONNECTION_REFUSED = 455;
   public static final int DATABASE_ERROR = 812;
   public static final int DEST_ALREADY_EXISTS = 745;
   public static final int DEVICE_DISK_FULL = 811;
   public static final int DEVICE_EXTERNAL_DISK_FULL = 813;
   public static final int DM_ADMIN_NOT_READY = 1023;
   public static final int DM_DOWNLOAD_POLICY_FAIL = 1100;
   public static final int DM_ENFORCE_REMOTE_WIPE = 1000;
   public static final int DM_PASSWORD_EXPIRE = 1020;
   public static final int DM_PASSWORD_NOT_SUFFICIENT = 1021;
   public static final int DM_POLICY_NOT_FULLY_SUPPORT = 1022;
   public static final int DM_PROVISION_NEED_PROVISION = 1011;
   public static final int EXACCESS_ERROR = 712;
   public static final int EXSERVER_ERROR = 711;
   public static final int EXTIMEOUT = 713;
   public static final int EXUNKNOWN_ERROR = 700;
   public static final int FOLDER_SYNCKEY_ERROR = 716;
   public static final int HOST_DOWN_ERROR = 452;
   public static final int HTTP_BAD_REQUEST = 400;
   public static final int HTTP_FORBIDDEN = 403;
   public static final int HTTP_METHOD_NOT_ALLOWED = 405;
   public static final int HTTP_NOT_FOUND = 404;
   public static final int HTTP_PROXY_AUTH_REQUIRED = 407;
   public static final int HTTP_RETRY_WITH = 449;
   public static final int HTTP_UNAUTHORIZED = 401;
   public static final int INSUFFICIENT_SERVER_STORAGE = 747;
   public static final int INVALID_DEST_COLID = 742;
   public static final int INVALID_SRC_COLID = 741;
   public static final int IO_ERROR = 802;
   public static final int ITEM_LOCKED = 746;
   public static final int MOVEITEM_FAIL = 744;
   public static final int NETWORK_NOT_READY = 702;
   public static final int NETWORK_UNREACHABLE = 454;
   public static final int NOT_GET_SYNCKEY = 710;
   public static final int NUMBER_FORMAT_ERROR = 805;
   public static final int OUT_OF_MEMORY = 810;
   public static final int SERVER_BUSY = 503;
   public static final int SERVER_ERROR = 500;
   public static final int SOCKET_ERROR = 508;
   public static final int SOCKET_NOT_CONNECTED = 507;
   public static final int SOCKET_TIMEOUT = 509;
   public static final int SRC_DEST_THESAME = 743;
   public static final int SSL_FAIL = 515;
   public static final int SYNCFORMAT_ERROR = 715;
   public static final int SYNCKEY_ERROR = 714;
   public static final int SYNC_CONVERSION_ERROR = 722;
   public static final int SYNC_DATA_CONFLICT = 723;
   public static final int SYNC_DENIED_BY_POLICY = 750;
   public static final int SYNC_FOLDER_CHANGED = 724;
   public static final int SYNC_OTHER_STATUS_ERROR = 725;
   public static final int SYNC_PROTOCOL_ERROR = 721;
   public static final int SYNC_PROTOCOL_MISMATCH = 720;
   public static final int UNDEFINE_ERROR = 800;
   public static final int UNGOT_PROTOCOL_VER = 701;
   public static final int UNKNOW_HTTP_ERROR = 451;
   public static final int UNSUPPORT_ENCODING_ERROR = 804;
   public static final int URI_SYNTAX_ERROR = 801;
   public static final int USER_CANCEL = 600;
   public static final int XML_PARSER_ERROR = 803;
   private int code;
   private Object data;


   public SyncException(int var1, String var2) {
      super(var2);
      this.code = var1;
   }

   public SyncException(int var1, String var2, Object var3) {
      super(var2);
      this.code = var1;
      this.data = var3;
   }

   public static final String getMessage(Context var0, int var1) {
      String var2;
      if(var1 == 400) {
         var2 = var0.getString(2131362322);
      } else if(var1 == 401) {
         var2 = var0.getString(2131362323);
      } else if(var1 == 403) {
         var2 = var0.getString(2131362324);
      } else if(var1 == 404) {
         var2 = var0.getString(2131362325);
      } else if(var1 == 405) {
         var2 = var0.getString(2131362326);
      } else if(var1 == 407) {
         var2 = var0.getString(2131362327);
      } else if(var1 == 500) {
         var2 = var0.getString(2131362328);
      } else if(var1 == 503) {
         var2 = var0.getString(2131362329);
      } else if(var1 == 507) {
         var2 = var0.getString(2131362330);
      } else if(var1 == 600) {
         var2 = var0.getString(2131362331);
      } else if(var1 == 450) {
         var2 = var0.getString(2131362332);
      } else if(var1 == 452) {
         var2 = var0.getString(2131362333);
      } else if(var1 == 800) {
         var2 = var0.getString(2131362334);
      } else if(var1 == 801) {
         var2 = var0.getString(2131362335);
      } else if(var1 == 802) {
         var2 = var0.getString(2131362336);
      } else if(var1 == 803) {
         var2 = var0.getString(2131362337);
      } else if(var1 == 804) {
         var2 = var0.getString(2131362338);
      } else if(var1 == 805) {
         var2 = var0.getString(2131362339);
      } else if(var1 == 710) {
         var2 = var0.getString(2131362340);
      } else if(var1 == 711) {
         var2 = var0.getString(2131362341);
      } else if(var1 == 712) {
         var2 = var0.getString(2131362342);
      } else if(var1 == 713) {
         var2 = var0.getString(2131362343);
      } else if(var1 == 714) {
         var2 = var0.getString(2131362344);
      } else if(var1 == 715) {
         var2 = var0.getString(2131362345);
      } else if(var1 == 700) {
         var2 = var0.getString(2131362346);
      } else if(var1 == 720) {
         var2 = var0.getString(2131362347);
      } else if(var1 == 721) {
         var2 = var0.getString(2131362348);
      } else if(var1 == 722) {
         var2 = var0.getString(2131362349);
      } else if(var1 == 723) {
         var2 = var0.getString(2131362350);
      } else if(var1 == 725) {
         var2 = var0.getString(2131362351);
      } else if(var1 == 741) {
         var2 = var0.getString(2131362352);
      } else if(var1 == 742) {
         var2 = var0.getString(2131362353);
      } else if(var1 == 743) {
         var2 = var0.getString(2131362354);
      } else if(var1 == 744) {
         var2 = var0.getString(2131362355);
      } else if(var1 == 745) {
         var2 = var0.getString(2131362356);
      } else if(var1 == 746) {
         var2 = var0.getString(2131362357);
      } else if(var1 == 701) {
         var2 = var0.getString(2131362358);
      } else if(var1 == 702) {
         var2 = var0.getString(2131362359);
      } else if(var1 == 508) {
         var2 = var0.getString(2131362360);
      } else if(var1 == 509) {
         var2 = var0.getString(2131362361);
      } else if(var1 == 515) {
         var2 = var0.getString(2131362362);
      } else if(var1 == 810) {
         var2 = var0.getString(2131362363);
      } else if(var1 == 811) {
         var2 = var0.getString(2131362364);
      } else if(var1 == 812) {
         var2 = var0.getString(2131362336);
      } else if(var1 == 747) {
         var2 = var0.getString(2131362365);
      } else if(var1 == 516) {
         var2 = var0.getString(2131362214);
      } else if(var1 == 517) {
         var2 = var0.getString(2131362215);
      } else if(var1 == 518) {
         var2 = var0.getString(2131362216);
      } else if(var1 == 519) {
         var2 = var0.getString(2131362217);
      } else if(var1 == 1011) {
         var2 = var0.getString(2131362366);
      } else if(var1 == 1021) {
         var2 = var0.getString(2131362368);
      } else if(var1 == 1020) {
         var2 = var0.getString(2131362368);
      } else if(var1 == 750) {
         var2 = var0.getString(2131362367);
      } else if(var1 == 1022) {
         var2 = var0.getString(2131362369);
      } else {
         var2 = var0.getString(2131362334);
      }

      return var2;
   }

   public int getCode() {
      return this.code;
   }

   public Object getData() {
      return this.data;
   }

   public void setData(Object var1) {
      this.data = var1;
   }
}
