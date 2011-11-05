package com.android.common;

import android.util.EventLog;

public class GoogleLogTags {

   public static final int C2DM = 204005;
   public static final int GLS_ACCOUNT_SAVED = 205009;
   public static final int GLS_ACCOUNT_TRIED = 205008;
   public static final int GLS_AUTHENTICATE = 205010;
   public static final int GOOGLE_HTTP_REQUEST = 203002;
   public static final int GOOGLE_MAIL_SWITCH = 205011;
   public static final int GTALKSERVICE = 204001;
   public static final int GTALK_CONNECTION = 204002;
   public static final int GTALK_CONN_CLOSE = 204003;
   public static final int GTALK_HEARTBEAT_RESET = 204004;
   public static final int SETUP_COMPLETED = 205007;
   public static final int SETUP_IO_ERROR = 205003;
   public static final int SETUP_NO_DATA_NETWORK = 205006;
   public static final int SETUP_REQUIRED_CAPTCHA = 205002;
   public static final int SETUP_RETRIES_EXHAUSTED = 205005;
   public static final int SETUP_SERVER_ERROR = 205004;
   public static final int SETUP_SERVER_TIMEOUT = 205001;
   public static final int SYNC_DETAILS = 203001;
   public static final int SYSTEM_UPDATE = 201001;
   public static final int SYSTEM_UPDATE_USER = 201002;
   public static final int TRANSACTION_EVENT = 202901;
   public static final int VENDING_RECONSTRUCT = 202001;


   private GoogleLogTags() {}

   public static void writeC2Dm(int var0, String var1, int var2, int var3) {
      Object[] var4 = new Object[4];
      Integer var5 = Integer.valueOf(var0);
      var4[0] = var5;
      var4[1] = var1;
      Integer var6 = Integer.valueOf(var2);
      var4[2] = var6;
      Integer var7 = Integer.valueOf(var3);
      var4[3] = var7;
      int var8 = EventLog.writeEvent(204005, var4);
   }

   public static void writeGlsAccountSaved(int var0) {
      int var1 = EventLog.writeEvent(205009, var0);
   }

   public static void writeGlsAccountTried(int var0) {
      int var1 = EventLog.writeEvent(205008, var0);
   }

   public static void writeGlsAuthenticate(int var0, String var1) {
      Object[] var2 = new Object[2];
      Integer var3 = Integer.valueOf(var0);
      var2[0] = var3;
      var2[1] = var1;
      int var4 = EventLog.writeEvent(205010, var2);
   }

   public static void writeGoogleHttpRequest(long var0, int var2, String var3, int var4) {
      Object[] var5 = new Object[4];
      Long var6 = Long.valueOf(var0);
      var5[0] = var6;
      Integer var7 = Integer.valueOf(var2);
      var5[1] = var7;
      var5[2] = var3;
      Integer var8 = Integer.valueOf(var4);
      var5[3] = var8;
      int var9 = EventLog.writeEvent(203002, var5);
   }

   public static void writeGoogleMailSwitch(int var0) {
      int var1 = EventLog.writeEvent(205011, var0);
   }

   public static void writeGtalkConnClose(int var0, int var1) {
      Object[] var2 = new Object[2];
      Integer var3 = Integer.valueOf(var0);
      var2[0] = var3;
      Integer var4 = Integer.valueOf(var1);
      var2[1] = var4;
      int var5 = EventLog.writeEvent(204003, var2);
   }

   public static void writeGtalkConnection(int var0) {
      int var1 = EventLog.writeEvent(204002, var0);
   }

   public static void writeGtalkHeartbeatReset(int var0, String var1) {
      Object[] var2 = new Object[2];
      Integer var3 = Integer.valueOf(var0);
      var2[0] = var3;
      var2[1] = var1;
      int var4 = EventLog.writeEvent(204004, var2);
   }

   public static void writeGtalkservice(int var0) {
      int var1 = EventLog.writeEvent(204001, var0);
   }

   public static void writeSetupCompleted() {
      Object[] var0 = new Object[0];
      int var1 = EventLog.writeEvent(205007, var0);
   }

   public static void writeSetupIoError(String var0) {
      int var1 = EventLog.writeEvent(205003, var0);
   }

   public static void writeSetupNoDataNetwork() {
      Object[] var0 = new Object[0];
      int var1 = EventLog.writeEvent(205006, var0);
   }

   public static void writeSetupRequiredCaptcha(String var0) {
      int var1 = EventLog.writeEvent(205002, var0);
   }

   public static void writeSetupRetriesExhausted() {
      Object[] var0 = new Object[0];
      int var1 = EventLog.writeEvent(205005, var0);
   }

   public static void writeSetupServerError() {
      Object[] var0 = new Object[0];
      int var1 = EventLog.writeEvent(205004, var0);
   }

   public static void writeSetupServerTimeout() {
      Object[] var0 = new Object[0];
      int var1 = EventLog.writeEvent(205001, var0);
   }

   public static void writeSyncDetails(String var0, int var1, int var2, String var3) {
      Object[] var4 = new Object[4];
      var4[0] = var0;
      Integer var5 = Integer.valueOf(var1);
      var4[1] = var5;
      Integer var6 = Integer.valueOf(var2);
      var4[2] = var6;
      var4[3] = var3;
      int var7 = EventLog.writeEvent(203001, var4);
   }

   public static void writeSystemUpdate(int var0, int var1, long var2, String var4) {
      Object[] var5 = new Object[4];
      Integer var6 = Integer.valueOf(var0);
      var5[0] = var6;
      Integer var7 = Integer.valueOf(var1);
      var5[1] = var7;
      Long var8 = Long.valueOf(var2);
      var5[2] = var8;
      var5[3] = var4;
      int var9 = EventLog.writeEvent(201001, var5);
   }

   public static void writeSystemUpdateUser(String var0) {
      int var1 = EventLog.writeEvent(201002, var0);
   }

   public static void writeTransactionEvent(String var0) {
      int var1 = EventLog.writeEvent(202901, var0);
   }

   public static void writeVendingReconstruct(int var0) {
      int var1 = EventLog.writeEvent(202001, var0);
   }
}
