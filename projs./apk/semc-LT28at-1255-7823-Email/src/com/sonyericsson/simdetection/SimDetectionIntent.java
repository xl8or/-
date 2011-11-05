package com.sonyericsson.simdetection;


public final class SimDetectionIntent {

   public static final String EXTRA_DEVICE_BOOT_COMPLETED = "com.sonyericsson.intent.extra.DEVICE_BOOT_COMPLETED";
   public static final String EXTRA_SIMCARD_PRESENT = "com.sonyericsson.intent.extra.SIMCARD_PRESENT";
   public static final String SIMCARD_STATE_CHANGED = "com.sonyericsson.intent.action.SIMCARD_STATE_CHANGED";
   public static final String SIMCARD_STATE_CHANGED_PERMISSION_BROADCAST = "com.sonyericsson.simdetection.permission.BROADCAST_SIMCARD_STATE_CHANGED";
   public static final String SIMCARD_STATE_CHANGED_PERMISSION_RECEIVE = "com.sonyericsson.simdetection.permission.RECEIVE_SIMCARD_STATE_CHANGED";


   public SimDetectionIntent() {}
}
