package org.acra;


public enum ReportingInteractionMode {

   // $FF: synthetic field
   private static final ReportingInteractionMode[] $VALUES;
   NOTIFICATION("NOTIFICATION", 1),
   SILENT("SILENT", 0),
   TOAST("TOAST", 2);


   static {
      ReportingInteractionMode[] var0 = new ReportingInteractionMode[3];
      ReportingInteractionMode var1 = SILENT;
      var0[0] = var1;
      ReportingInteractionMode var2 = NOTIFICATION;
      var0[1] = var2;
      ReportingInteractionMode var3 = TOAST;
      var0[2] = var3;
      $VALUES = var0;
   }

   private ReportingInteractionMode(String var1, int var2) {}
}
