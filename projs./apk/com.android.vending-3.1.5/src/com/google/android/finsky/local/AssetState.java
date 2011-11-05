package com.google.android.finsky.local;

import java.util.HashMap;
import java.util.Map;

public enum AssetState {

   // $FF: synthetic field
   private static final AssetState[] $VALUES;
   DOWNLOADING("DOWNLOADING", 0, 1),
   DOWNLOAD_CANCELLED("DOWNLOAD_CANCELLED", 11, 12),
   DOWNLOAD_CANCEL_PENDING("DOWNLOAD_CANCEL_PENDING", 10, 11),
   DOWNLOAD_DECLINED("DOWNLOAD_DECLINED", 9, 10),
   DOWNLOAD_FAILED("DOWNLOAD_FAILED", 4, 5),
   DOWNLOAD_PENDING("DOWNLOAD_PENDING", 8, 9),
   INSTALLED("INSTALLED", 1, 2),
   INSTALLING("INSTALLING", 5, 6),
   INSTALL_FAILED("INSTALL_FAILED", 3, 4),
   UNINSTALLED("UNINSTALLED", 2, 3),
   UNINSTALLING("UNINSTALLING", 6, 7),
   UNINSTALL_FAILED("UNINSTALL_FAILED", 7, 8);
   private static final Map<AssetState, AssetState[]> sTransitions;
   private final int mValue;


   static {
      AssetState[] var0 = new AssetState[12];
      AssetState var1 = DOWNLOADING;
      var0[0] = var1;
      AssetState var2 = INSTALLED;
      var0[1] = var2;
      AssetState var3 = UNINSTALLED;
      var0[2] = var3;
      AssetState var4 = INSTALL_FAILED;
      var0[3] = var4;
      AssetState var5 = DOWNLOAD_FAILED;
      var0[4] = var5;
      AssetState var6 = INSTALLING;
      var0[5] = var6;
      AssetState var7 = UNINSTALLING;
      var0[6] = var7;
      AssetState var8 = UNINSTALL_FAILED;
      var0[7] = var8;
      AssetState var9 = DOWNLOAD_PENDING;
      var0[8] = var9;
      AssetState var10 = DOWNLOAD_DECLINED;
      var0[9] = var10;
      AssetState var11 = DOWNLOAD_CANCEL_PENDING;
      var0[10] = var11;
      AssetState var12 = DOWNLOAD_CANCELLED;
      var0[11] = var12;
      $VALUES = var0;
      sTransitions = new AssetState.1();
   }

   private AssetState(String var1, int var2, int var3) {
      this.mValue = var3;
   }

   public int getValue() {
      return this.mValue;
   }

   public boolean isDownloadingOrInstalling() {
      AssetState var1 = INSTALLING;
      boolean var4;
      if(this != var1) {
         AssetState var2 = DOWNLOADING;
         if(this != var2) {
            AssetState var3 = DOWNLOAD_PENDING;
            if(this != var3) {
               var4 = false;
               return var4;
            }
         }
      }

      var4 = true;
      return var4;
   }

   public boolean isNotInstallable() {
      AssetState var1 = INSTALLED;
      boolean var4;
      if(this != var1) {
         AssetState var2 = INSTALLING;
         if(this != var2) {
            AssetState var3 = DOWNLOADING;
            if(this != var3) {
               var4 = false;
               return var4;
            }
         }
      }

      var4 = true;
      return var4;
   }

   public boolean isTransient() {
      AssetState var1 = DOWNLOADING;
      boolean var6;
      if(this != var1) {
         AssetState var2 = INSTALLING;
         if(this != var2) {
            AssetState var3 = UNINSTALLING;
            if(this != var3) {
               AssetState var4 = DOWNLOAD_PENDING;
               if(this != var4) {
                  AssetState var5 = DOWNLOAD_CANCEL_PENDING;
                  if(this != var5) {
                     var6 = false;
                     return var6;
                  }
               }
            }
         }
      }

      var6 = true;
      return var6;
   }

   public boolean isUninstallable() {
      AssetState var1 = INSTALLED;
      boolean var2;
      if(this == var1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean isValidTransition(AssetState var1) {
      AssetState[] var2 = (AssetState[])sTransitions.get(this);
      int var3 = var2.length;
      int var4 = 0;

      boolean var5;
      while(true) {
         if(var4 >= var3) {
            var5 = false;
            break;
         }

         if(var2[var4] == var1) {
            var5 = true;
            break;
         }

         ++var4;
      }

      return var5;
   }

   static class 1 extends HashMap<AssetState, AssetState[]> {

      1() {
         AssetState var1 = AssetState.DOWNLOADING;
         AssetState[] var2 = new AssetState[6];
         AssetState var3 = AssetState.DOWNLOAD_FAILED;
         var2[0] = var3;
         AssetState var4 = AssetState.DOWNLOAD_DECLINED;
         var2[1] = var4;
         AssetState var5 = AssetState.DOWNLOAD_CANCEL_PENDING;
         var2[2] = var5;
         AssetState var6 = AssetState.DOWNLOADING;
         var2[3] = var6;
         AssetState var7 = AssetState.DOWNLOAD_CANCELLED;
         var2[4] = var7;
         AssetState var8 = AssetState.INSTALLING;
         var2[5] = var8;
         this.put(var1, var2);
         AssetState var10 = AssetState.INSTALLED;
         AssetState[] var11 = new AssetState[1];
         AssetState var12 = AssetState.UNINSTALLING;
         var11[0] = var12;
         this.put(var10, var11);
         AssetState var14 = AssetState.UNINSTALLED;
         AssetState[] var15 = new AssetState[2];
         AssetState var16 = AssetState.DOWNLOAD_PENDING;
         var15[0] = var16;
         AssetState var17 = AssetState.INSTALLED;
         var15[1] = var17;
         this.put(var14, var15);
         AssetState var19 = AssetState.INSTALL_FAILED;
         AssetState[] var20 = new AssetState[1];
         AssetState var21 = AssetState.DOWNLOAD_PENDING;
         var20[0] = var21;
         this.put(var19, var20);
         AssetState var23 = AssetState.DOWNLOAD_FAILED;
         AssetState[] var24 = new AssetState[1];
         AssetState var25 = AssetState.DOWNLOAD_PENDING;
         var24[0] = var25;
         this.put(var23, var24);
         AssetState var27 = AssetState.INSTALLING;
         AssetState[] var28 = new AssetState[2];
         AssetState var29 = AssetState.INSTALLED;
         var28[0] = var29;
         AssetState var30 = AssetState.INSTALL_FAILED;
         var28[1] = var30;
         this.put(var27, var28);
         AssetState var32 = AssetState.UNINSTALLING;
         AssetState[] var33 = new AssetState[2];
         AssetState var34 = AssetState.UNINSTALLED;
         var33[0] = var34;
         AssetState var35 = AssetState.UNINSTALL_FAILED;
         var33[1] = var35;
         this.put(var32, var33);
         AssetState var37 = AssetState.UNINSTALL_FAILED;
         AssetState[] var38 = new AssetState[1];
         AssetState var39 = AssetState.DOWNLOAD_PENDING;
         var38[0] = var39;
         this.put(var37, var38);
         AssetState var41 = AssetState.DOWNLOAD_PENDING;
         AssetState[] var42 = new AssetState[6];
         AssetState var43 = AssetState.DOWNLOADING;
         var42[0] = var43;
         AssetState var44 = AssetState.DOWNLOAD_PENDING;
         var42[1] = var44;
         AssetState var45 = AssetState.DOWNLOAD_DECLINED;
         var42[2] = var45;
         AssetState var46 = AssetState.DOWNLOAD_CANCEL_PENDING;
         var42[3] = var46;
         AssetState var47 = AssetState.DOWNLOAD_CANCELLED;
         var42[4] = var47;
         AssetState var48 = AssetState.DOWNLOAD_FAILED;
         var42[5] = var48;
         this.put(var41, var42);
         AssetState var50 = AssetState.DOWNLOAD_DECLINED;
         AssetState[] var51 = new AssetState[1];
         AssetState var52 = AssetState.DOWNLOAD_PENDING;
         var51[0] = var52;
         this.put(var50, var51);
         AssetState var54 = AssetState.DOWNLOAD_CANCEL_PENDING;
         AssetState[] var55 = new AssetState[1];
         AssetState var56 = AssetState.DOWNLOAD_CANCELLED;
         var55[0] = var56;
         this.put(var54, var55);
         AssetState var58 = AssetState.DOWNLOAD_CANCELLED;
         AssetState[] var59 = new AssetState[2];
         AssetState var60 = AssetState.DOWNLOAD_PENDING;
         var59[0] = var60;
         AssetState var61 = AssetState.DOWNLOAD_CANCELLED;
         var59[1] = var61;
         this.put(var58, var59);
      }
   }
}
