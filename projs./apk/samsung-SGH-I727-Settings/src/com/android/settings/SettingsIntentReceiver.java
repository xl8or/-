package com.android.settings;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.os.SystemProperties;
import android.os.Vibrator;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.util.Log;
import com.android.internal.util.NVStore;
import com.android.internal.util.NVStore.datatype;
import com.sec.android.hardware.SecHardwareInterface;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.util.StringTokenizer;

public class SettingsIntentReceiver extends BroadcastReceiver {

   private static final boolean DBG = true;
   private static final String DBKEY_MT_PWD = "mt_pwd";
   private static final String DBKEY_MT_STATE = "mt_state";
   private static final String DM_FILE = "/system/samsungaccount.txt";
   private static final String MT_FILE = "/system/mobiletracker.txt";
   private static final String TAG = "SettingsIntentReceiver";
   public static final String USB_APCP_MODE = "usbapcpmode";
   private static final String USB_SETTING_MODE = "usb_setting_mode";
   private static final int VIBRATE_DURATION = 300;
   private String USB_PATH = "/sys/class/sec/switch/usb_sel";
   private AudioManager mAudioManager;
   private Vibrator mVibrator;
   private final byte[] modem;


   public SettingsIntentReceiver() {
      byte[] var1 = new byte[]{(byte)77, (byte)79, (byte)68, (byte)69, (byte)77, (byte)0};
      this.modem = var1;
   }

   private void changeUsb(String var1) throws IOException {
      String var2 = this.USB_PATH;
      FileOutputStream var3 = new FileOutputStream(var2);

      try {
         if("MODEM".equals(var1)) {
            int var4 = Log.i("SettingsIntentReceiver", "changeUsb : ");
            SystemProperties.set("persist.service.mdm_usb.enable", "1");
            byte[] var5 = this.modem;
            var3.write(var5);
         }

         return;
      } catch (Exception var10) {
         var10.printStackTrace();
      } finally {
         var3.close();
      }

   }

   private void makeDMfile() {
      boolean var38 = false;

      RandomAccessFile var6;
      label119: {
         label120: {
            try {
               var38 = true;
               StringBuilder var1 = new StringBuilder();
               String var2 = Environment.getDataDirectory().getAbsolutePath();
               String var3 = var1.append(var2).append("/system/samsungaccount.txt").toString();
               String var4 = "dmFileName=" + var3;
               int var5 = Log.i("SettingsIntentReceiver", var4);
               var6 = new RandomAccessFile(var3, "rw");
               var38 = false;
               break label119;
            } catch (FileNotFoundException var43) {
               int var14 = Log.i("SettingsIntentReceiver", "writedatatonv - Exception in steam write");
               var38 = false;
               break label120;
            } catch (IOException var44) {
               int var20 = Log.i("SettingsIntentReceiver", "writedatatonv - IOException in steam write");
               var38 = false;
            } finally {
               if(var38) {
                  int var26 = Log.i("SettingsIntentReceiver", "writedatatonv - finally");
                  if(false) {
                     try {
                        int var27 = Log.i("SettingsIntentReceiver", "writedatatonv - call raf.close()");
                        null.close();
                     } catch (IOException var41) {
                        int var29 = Log.w("SettingsIntentReceiver", var41);
                     }
                  }

               }
            }

            int var21 = Log.i("SettingsIntentReceiver", "writedatatonv - finally");
            if(true) {
               return;
            }

            try {
               int var22 = Log.i("SettingsIntentReceiver", "writedatatonv - call raf.close()");
               null.close();
               return;
            } catch (IOException var39) {
               int var24 = Log.w("SettingsIntentReceiver", var39);
               return;
            }
         }

         int var15 = Log.i("SettingsIntentReceiver", "writedatatonv - finally");
         if(true) {
            return;
         }

         try {
            int var16 = Log.i("SettingsIntentReceiver", "writedatatonv - call raf.close()");
            null.close();
            return;
         } catch (IOException var40) {
            int var18 = Log.w("SettingsIntentReceiver", var40);
            return;
         }
      }

      int var7 = Log.i("SettingsIntentReceiver", "writedatatonv - finally");
      if(var6 != null) {
         try {
            int var8 = Log.i("SettingsIntentReceiver", "writedatatonv - call raf.close()");
            var6.close();
         } catch (IOException var42) {
            int var11 = Log.w("SettingsIntentReceiver", var42);
            return;
         }

      }
   }

   private void writeMTData(String param1) {
      // $FF: Couldn't be decompiled
   }

   public boolean GetMTStatus(String var1) {
      StringTokenizer var2 = new StringTokenizer(var1, ";");
      String[] var3 = new String[var2.countTokens()];

      int var4;
      for(var4 = 0; var2.hasMoreTokens(); ++var4) {
         String var5 = var2.nextToken();
         var3[var4] = var5;
      }

      boolean var6;
      if(var4 < 2) {
         var6 = false;
      } else if(var3[1].compareTo("1") == 0) {
         var6 = true;
      } else {
         var6 = false;
      }

      return var6;
   }

   public String GetPhPWD(String var1) {
      StringTokenizer var2 = new StringTokenizer(var1, ";");
      String[] var3 = new String[var2.countTokens()];

      int var4;
      for(var4 = 0; var2.hasMoreTokens(); ++var4) {
         String var5 = var2.nextToken();
         var3[var4] = var5;
      }

      String var6;
      if(var4 < 1) {
         var6 = null;
      } else if(var3[0].length() <= 8 && var3[0].length() >= 8) {
         var6 = var3[0];
      } else {
         var6 = null;
      }

      return var6;
   }

   public void onReceive(Context var1, Intent var2) {
      String var4 = "audio";
      AudioManager var5 = (AudioManager)var1.getSystemService(var4);
      this.mAudioManager = var5;
      Vibrator var6 = new Vibrator();
      this.mVibrator = var6;
      if(var2.getAction().equals("com.samsung.wipe.MTDATA")) {
         int var7 = Log.i("SettingsIntentReceiver", "onReceive() : com.samsung.wipe.MTDATA");
         Bundle var8 = var2.getExtras();
         String var9 = "MTDATA";
         String var10 = var8.getString(var9);
         StringBuilder var11 = (new StringBuilder()).append("extras.getString(MTDATA) : ");
         String var13 = var11.append(var10).toString();
         int var14 = Log.i("SettingsIntentReceiver", var13);
         this.writeMTData(var10);
         this.makeDMfile();
         ContentResolver var17 = var1.getContentResolver();
         String var20 = this.GetPhPWD(var10);
         System.putString(var17, "mt_pwd", var20);
         if(this.GetMTStatus(var10)) {
            boolean var24 = System.putInt(var1.getContentResolver(), "mt_state", 1);
         } else {
            boolean var27 = System.putInt(var1.getContentResolver(), "mt_state", 0);
         }
      } else if(var2.getAction().equals("android.media.RINGER_MODE_CHANGED")) {
         int var28 = Log.i("SettingsIntentReceiver", "onReceive() : RINGER_MODE_CHANGED_ACTION");
         Bundle var29 = var2.getExtras();
         int var32;
         if(var29 != null) {
            String var31 = "android.media.EXTRA_RINGER_MODE";
            var32 = var29.getInt(var31);
            StringBuilder var33 = (new StringBuilder()).append("Extras (ringer mode) : ");
            String var35 = var33.append(var32).toString();
            int var36 = Log.i("SettingsIntentReceiver", var35);
         } else {
            var32 = this.mAudioManager.getRingerMode();
            StringBuilder var39 = (new StringBuilder()).append("No extras (ringer mode) : ");
            String var41 = var39.append(var32).toString();
            int var42 = Log.i("SettingsIntentReceiver", var41);
         }

         switch(var32) {
         case 0:
            int var37 = Log.i("SettingsIntentReceiver", "Ringer mode : silent & set driving mode off");
            boolean var38 = System.putInt(var1.getContentResolver(), "driving_mode_on", 0);
            break;
         case 1:
            int var43 = Log.i("SettingsIntentReceiver", "Ringer mode : vibrate");
            break;
         case 2:
            int var44 = Log.i("SettingsIntentReceiver", "Ringer mode : normal");
         }
      } else if(var2.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
         int var45 = Log.v("SettingsIntentReceiver", "Received intent : android.intent.action.BOOT_COMPLETED");
         int var46 = System.getInt(var1.getContentResolver(), "vibrate_in_silent", 1);
         byte var47 = 1;
         boolean var48;
         if(var46 == var47) {
            var48 = true;
         } else {
            var48 = false;
         }

         int var49 = this.mAudioManager.getVibrateSetting(0);
         if(var48) {
            if(var49 == 0) {
               this.mAudioManager.setVibrateSetting(0, 2);
            }
         } else {
            byte var95 = 2;
            if(var49 == var95) {
               this.mAudioManager.setVibrateSetting(0, 0);
            }
         }

         ContentResolver var50 = var1.getContentResolver();
         String var51 = "power_saving_mode";
         byte var52 = 0;
         int var53 = System.getInt(var50, var51, var52);
         byte var54;
         if(var53 != 0) {
            var54 = 1;
         } else {
            var54 = 0;
         }

         SecHardwareInterface.setAmoledACL(Boolean.valueOf((boolean)var54).booleanValue());
         StringBuilder var55 = (new StringBuilder()).append("com.sec.android.app.SecFeature.POWER_SAVING_MODE : true, SecHardwareInterface.setAmoledACL(");
         String var57 = var55.append(var53).append(")").toString();
         int var58 = Log.i("SettingsIntentReceiver", var57);
         if("GT-I9103".equalsIgnoreCase("SGH-I727")) {
            boolean var59 = System.putInt(var1.getContentResolver(), "screen_mode_setting", 0);
         }

         if(!"GT-I9100G".equalsIgnoreCase("SGH-I727")) {
            SecHardwareInterface.setmDNIeUserMode(System.getInt(var1.getContentResolver(), "screen_mode_setting", 1));
         }

         int var60 = Log.i("SettingsIntentReceiver", "<Boot Complete> in Settings");
         int var61 = System.getInt(var1.getContentResolver(), "screen_off_timeout", 30000);
         StringBuilder var62 = (new StringBuilder()).append("oldScreenTimeout: ");
         String var64 = var62.append(var61).toString();
         int var65 = Log.i("SettingsIntentReceiver", var64);
         int var67 = 600000;
         if(var61 > var67 || var61 < 0) {
            int var68 = Log.i("SettingsIntentReceiver", "Set timeout to 10mins forcely");
            boolean var69 = System.putInt(var1.getContentResolver(), "screen_off_timeout", 600000);
         }

         int var70 = System.getInt(var1.getContentResolver(), "stay_on_while_plugged_in", 0);
         StringBuilder var71 = (new StringBuilder()).append("oldStayAway: ");
         String var73 = var71.append(var70).toString();
         int var74 = Log.i("SettingsIntentReceiver", var73);
         if(var70 != 0) {
            int var75 = Log.i("SettingsIntentReceiver", "Set StayAway off");
            boolean var76 = System.putInt(var1.getContentResolver(), "stay_on_while_plugged_in", 0);
         }

         int var77 = Secure.getInt(var1.getContentResolver(), "usb_setting_mode", 0);
         byte var78 = 2;
         if(var77 == var78) {
            SystemProperties.set("persist.service.usb.setting", "0");
            boolean var79 = Secure.putInt(var1.getContentResolver(), "usb_setting_mode", 0);
         }

         int var80 = System.getInt(var1.getContentResolver(), "usbapcpmode", 1);
         StringBuilder var81 = (new StringBuilder()).append("currentUsb : ");
         String var83 = var81.append(var80).toString();
         int var84 = Log.i("SettingsIntentReceiver", var83);
         if(var80 == 0) {
            try {
               String var86 = "MODEM";
               this.changeUsb(var86);
               StringBuilder var87 = (new StringBuilder()).append("USB_STATUS1 : ");
               String var89 = var87.append(var80).toString();
               int var90 = Log.i("PUT_USB", var89);
            } catch (Exception var214) {
               PrintStream var92 = java.lang.System.err;
               var92.println(var214);
            }
         }
      } else if(var2.getAction().equals("android.intent.action.REGISTRATION_COMPLETED")) {
         String var97 = "MT_Setting_Pref";
         byte var98 = 0;
         Editor var99 = var1.getSharedPreferences(var97, var98).edit();
         String var101 = "MT_OPS_Signed_in";
         byte var102 = 1;
         var99.putBoolean(var101, (boolean)var102);
         boolean var104 = var99.commit();
         NVStore var105 = new NVStore(var1);
         String var108;
         if(var105.IsPhLockeEnabled()) {
            var108 = "1";
         } else {
            var108 = "0";
         }

         if(var105.GetPhPWD() != null) {
            StringBuilder var109 = new StringBuilder();
            String var110 = var105.GetPhPWD();
            StringBuilder var111 = var109.append(var110).append(";").append("1").append(";");
            String var112 = var105.GetSenderName();
            StringBuilder var113 = var111.append(var112).append(";");
            String var114 = var105.GetRec1();
            StringBuilder var115 = var113.append(var114).append(";");
            String var116 = var105.GetRec2();
            StringBuilder var117 = var115.append(var116).append(";");
            String var118 = var105.GetRec3();
            StringBuilder var119 = var117.append(var118).append(";");
            String var120 = var105.GetRec4();
            StringBuilder var121 = var119.append(var120).append(";");
            String var122 = var105.GetRec5();
            StringBuilder var123 = var121.append(var122).append(";");
            String var124 = var105.GetSmsMsg();
            StringBuilder var125 = var123.append(var124).append(";");
            String var126 = var105.GetStoredIMSI();
            StringBuilder var127 = var125.append(var126).append(";");
            String var129 = var127.append(var108).append(";").toString();
            datatype var130 = datatype.All;
            var105.writedata(var129, var130);
         }
      } else if(var2.getAction().equals("android.intent.action.REGISTRATION_CANCELED")) {
         String var135 = "MT_Setting_Pref";
         byte var136 = 0;
         Editor var137 = var1.getSharedPreferences(var135, var136).edit();
         String var139 = "MT_OPS_Signed_in";
         byte var140 = 0;
         var137.putBoolean(var139, (boolean)var140);
         boolean var142 = var137.commit();
      } else if(var2.getAction().equals("android.intent.action.DOCK_EVENT")) {
         String var144 = "android.intent.extra.DOCK_STATE";
         byte var145 = 0;
         int var146 = var2.getIntExtra(var144, var145);
         StringBuilder var147 = (new StringBuilder()).append("Received intent : android.intent.action.DOCK_EVENT with State:");
         String var149 = var147.append(var146).toString();
         int var150 = Log.v("SettingsIntentReceiver", var149);
         int var151 = System.getInt(var1.getContentResolver(), "cradle_enable", 0);
         if(var146 == 0) {
            boolean var152 = System.putInt(var1.getContentResolver(), "cradle_connect", 0);
            if(var151 != 0) {
               StringBuilder var153 = (new StringBuilder()).append("Cradle is enabled:");
               String var155 = var153.append(var151).toString();
               int var156 = Log.v("SettingsIntentReceiver", var155);
               Intent var157 = new Intent();
               String var159 = "com.sec.android.intent.action.INTERNAL_SPEAKER";
               var157.setAction(var159);
               String var162 = "state";
               byte var163 = 0;
               var157.putExtra(var162, var163);
               var1.sendBroadcast(var157);
               StringBuilder var167 = (new StringBuilder()).append("Sound state changed to Phone:");
               byte var168 = 0;
               String var169 = var167.append(var168).toString();
               int var170 = Log.v("SettingsIntentReceiver", var169);
            } else {
               StringBuilder var171 = (new StringBuilder()).append("Cradle is disabled:");
               String var173 = var171.append(var151).toString();
               int var174 = Log.v("SettingsIntentReceiver", var173);
               int var175 = Log.v("SettingsIntentReceiver", "Sound state is Phone:");
            }
         } else {
            boolean var176 = System.putInt(var1.getContentResolver(), "cradle_connect", 1);
            if(var151 != 0) {
               StringBuilder var177 = (new StringBuilder()).append("Cradle is enabled:");
               String var179 = var177.append(var151).toString();
               int var180 = Log.v("SettingsIntentReceiver", var179);
               Intent var181 = new Intent();
               String var183 = "com.sec.android.intent.action.INTERNAL_SPEAKER";
               var181.setAction(var183);
               String var186 = "state";
               byte var187 = 1;
               var181.putExtra(var186, var187);
               var1.sendBroadcast(var181);
               StringBuilder var191 = (new StringBuilder()).append("Sound state changed to Line out:");
               byte var192 = 1;
               String var193 = var191.append(var192).toString();
               int var194 = Log.v("SettingsIntentReceiver", var193);
            } else {
               StringBuilder var195 = (new StringBuilder()).append("Cradle is disabled:");
               String var197 = var195.append(var151).toString();
               int var198 = Log.v("SettingsIntentReceiver", var197);
               int var199 = Log.v("SettingsIntentReceiver", "Sound state is Phone:");
            }
         }
      } else if(var2.getAction().equals("shopdemo_on")) {
         int var200 = Log.i("SettingsIntentReceiver", " +++++ displayForshop_onReceive  shopdemo_on++++++++++++++");
         boolean var201 = System.putInt(var1.getContentResolver(), "shopdemo", 1);
      } else if(var2.getAction().equals("shopdemo_off")) {
         int var202 = Log.i("SettingsIntentReceiver", " +++++ displayForshop_onReceive  shopdemo_off++++++++++++++");
         boolean var203 = System.putInt(var1.getContentResolver(), "shopdemo", 0);
      } else if(var2.getAction().equals("android.intent.action.ACTION_POWER_DISCONNECTED")) {
         String var205 = "power";
         int var206 = ((PowerManager)var1.getSystemService(var205)).getPlugType();
         byte var207 = 2;
         if(var206 != var207) {
            int var208 = Secure.getInt(var1.getContentResolver(), "usb_setting_mode", 0);
            byte var209 = 2;
            if(var208 == var209) {
               SystemProperties.set("persist.service.usb.setting", "0");
               boolean var210 = Secure.putInt(var1.getContentResolver(), "usb_setting_mode", 0);
            }
         }
      } else if(var2.getAction().equals("osp.signin.SAMSUNG_ACCOUNT_SIGNOUT")) {
         boolean var211 = System.putInt(var1.getContentResolver(), "change_alert", 0);
         boolean var212 = System.putInt(var1.getContentResolver(), "remote_control", 0);
         boolean var213 = System.putInt(var1.getContentResolver(), "samsung_signin", 0);
      }

      if(var2.getAction().equals("android.intent.action.REGISTRATION_COMPLETED")) {
         boolean var25 = System.putInt(var1.getContentResolver(), "remote_control", 1);
         boolean var26 = System.putInt(var1.getContentResolver(), "samsung_signin", 1);
      }
   }
}
