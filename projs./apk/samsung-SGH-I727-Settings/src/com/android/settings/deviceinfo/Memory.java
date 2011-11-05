package com.android.settings.deviceinfo;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.StatFs;
import android.os.storage.IMountService;
import android.os.storage.StorageEventListener;
import android.os.storage.StorageManager;
import android.os.storage.IMountService.Stub;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;
import android.preference.PreferenceScreen;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.Toast;
import com.android.settings.MediaFormat;
import com.android.settings.MediaFormatSd;
import java.io.File;
import java.util.List;

public class Memory extends PreferenceActivity implements OnCancelListener {

   private static final long CONV_TO_MB = 1048576L;
   private static final int DLG_CONFIRM_UNMOUNT = 1;
   private static final int DLG_CONFIRM_UNMOUNT_SD = 3;
   private static final int DLG_ERROR_UNMOUNT = 2;
   private static final int FORMAT_INTERNAL = 100;
   private static final String MEMORY_SD_AVAIL = "memory_sd_avail";
   private static final String MEMORY_SD_FORMAT = "memory_sd_format";
   private static final String MEMORY_SD_GROUP = "memory_sd";
   private static final String MEMORY_SD_INTERNAL_AVAIL = "memory_sd_internal_avail";
   private static final String MEMORY_SD_INTERNAL_FORMAT = "memory_sd_internal_format";
   private static final String MEMORY_SD_INTERNAL_GROUP = "memory_sd_internal";
   private static final String MEMORY_SD_INTERNAL_MOUNT_TOGGLE = "memory_sd_internal_mount_toggle";
   private static final String MEMORY_SD_INTERNAL_SIZE = "memory_sd_internal_size";
   private static final String MEMORY_SD_MOUNT_TOGGLE = "memory_sd_mount_toggle";
   private static final String MEMORY_SD_SIZE = "memory_sd_size";
   private static final String TAG = "Memory";
   private static final boolean localLOGV = true;
   private IMountService mMountService = null;
   private final BroadcastReceiver mReceiver;
   private Resources mRes;
   private Preference mSdAvail;
   private Preference mSdFormat;
   private Preference mSdInternalAvail;
   private Preference mSdInternalFormat;
   private PreferenceGroup mSdInternalMountPreferenceGroup;
   boolean mSdInternalMountToggleAdded = 1;
   private Preference mSdInternalSize;
   private PreferenceGroup mSdMountPreferenceGroup;
   private Preference mSdMountToggle;
   boolean mSdMountToggleAdded = 1;
   private Preference mSdSize;
   StorageEventListener mStorageListener;
   private StorageManager mStorageManager = null;


   public Memory() {
      Memory.1 var1 = new Memory.1();
      this.mStorageListener = var1;
      Memory.2 var2 = new Memory.2();
      this.mReceiver = var2;
   }

   private void doUnmount(String var1, boolean var2) {
      String var3 = "doUnmount :: extStoragePath = " + var1 + ", force = " + var2;
      int var4 = Log.d("Memory", var3);
      IMountService var5 = this.getMountService();
      if(var5 == null) {
         int var6 = Log.e("Memory", "doUnmount() mountService == null");
      } else {
         try {
            String var7 = Environment.getExternalStorageDirectorySd().getPath();
            if(var1.equals(var7)) {
               Toast.makeText(this, 2131231409, 0).show();
               this.mSdMountToggle.setEnabled((boolean)0);
               Preference var8 = this.mSdMountToggle;
               String var9 = this.mRes.getString(2131231410);
               var8.setTitle(var9);
               Preference var10 = this.mSdMountToggle;
               String var11 = this.mRes.getString(2131231411);
               var10.setSummary(var11);
               var5.unmountVolume(var1, var2);
            } else {
               String var13 = Environment.getExternalStorageDirectory().getPath();
               if(var1.equals(var13)) {
                  Toast.makeText(this, 2131231408, 0).show();
                  var5.unmountVolume(var1, var2);
               }
            }
         } catch (RemoteException var14) {
            this.showDialogInner(2);
         }
      }
   }

   private String formatSize(long var1) {
      StringBuilder var3 = (new StringBuilder()).append("formatSize :: size = ");
      long var4 = var1 / 1048576L;
      String var6 = var3.append(var4).toString();
      int var7 = Log.d("Memory", var6);
      return Formatter.formatFileSize(this, var1);
   }

   private IMountService getMountService() {
      synchronized(this){}

      IMountService var4;
      try {
         int var1 = Log.d("Memory", "getMountService ::");
         if(this.mMountService == null) {
            IBinder var2 = ServiceManager.getService("mount");
            if(var2 != null) {
               IMountService var3 = Stub.asInterface(var2);
               this.mMountService = var3;
            } else {
               int var5 = Log.e("Memory", "Can\'t get mount service");
            }
         }

         var4 = this.mMountService;
      } finally {
         ;
      }

      return var4;
   }

   private boolean hasAppsAccessingStorage(String var1) throws RemoteException {
      String var2 = "hasAppsAccessingStorage :: extStoragePath = " + var1;
      int var3 = Log.d("Memory", var2);
      IMountService var4 = this.getMountService();
      boolean var5;
      if(var4 == null) {
         var5 = false;
      } else {
         int[] var6 = var4.getStorageUsers(var1);
         if(var6 != null && var6.length > 0) {
            var5 = true;
         } else {
            List var7 = ((ActivityManager)this.getSystemService("activity")).getRunningExternalApplications();
            if(var7 != null && var7.size() > 0) {
               var5 = true;
            } else {
               var5 = false;
            }
         }
      }

      return var5;
   }

   private void mount(String param1) {
      // $FF: Couldn't be decompiled
   }

   private void showDialogInner(int var1) {
      String var2 = "showDialogInner :: id = " + var1;
      int var3 = Log.d("Memory", var2);
      this.removeDialog(var1);
      this.showDialog(var1);
   }

   private void unmount(String var1) {
      String var2 = "unmount :: extStoragePath = " + var1;
      int var3 = Log.d("Memory", var2);

      try {
         if(this.hasAppsAccessingStorage(var1)) {
            int var4 = Log.i("Memory", "Do have storage users accessing media");
            String var5 = Environment.getExternalStorageDirectorySd().getPath();
            if(var1.equals(var5)) {
               this.showDialogInner(3);
            }
         } else {
            int var6 = Log.d("Memory", "unmount :: doUnmount");
            this.doUnmount(var1, (boolean)1);
         }
      } catch (RemoteException var9) {
         int var8 = Log.e("Memory", "Is MountService running?");
         this.showDialogInner(2);
      }
   }

   private void updateMemoryStatus() {
      int var1 = Log.d("Memory", "updateMemoryStatus :: ");
      String var2 = Environment.getExternalStorageStateSd();
      String var3 = "";
      String var5 = "mounted_ro";
      if(var2.equals(var5)) {
         var2 = "mounted";
         var3 = this.mRes.getString(2131231399);
         StringBuilder var6 = (new StringBuilder()).append("updateMemoryStatus :: status = ");
         StringBuilder var8 = var6.append(var2).append(", readOnly");
         String var10 = var8.append(var3).toString();
         int var11 = Log.d("Memory", var10);
      }

      this.mSdFormat.setEnabled((boolean)0);
      StringBuilder var12 = (new StringBuilder()).append("EXTERNAL updateMemoryStatus :: status = ");
      String var14 = var12.append(var2).toString();
      int var15 = Log.d("Memory", var14);
      String var17 = "mounted";
      if(var2.equals(var17)) {
         if(!Environment.isExternalStorageRemovableSd() && this.mSdMountToggleAdded) {
            PreferenceGroup var18 = this.mSdMountPreferenceGroup;
            Preference var19 = this.mSdMountToggle;
            var18.removePreference(var19);
            byte var21 = 0;
            this.mSdMountToggleAdded = (boolean)var21;
         }

         try {
            File var22 = Environment.getExternalStorageDirectorySd();
            StatFs var23 = new StatFs;
            String var24 = var22.getPath();
            var23.<init>(var24);
            long var27 = (long)var23.getBlockSize();
            long var29 = (long)var23.getBlockCount();
            long var31 = (long)var23.getAvailableBlocks();
            long var33 = var29 * var27;
            long var35 = var31 * var27;
            Preference var37 = this.mSdSize;
            String var41 = this.formatSize(var33);
            var37.setSummary(var41);
            Preference var42 = this.mSdAvail;
            StringBuilder var43 = new StringBuilder();
            String var47 = this.formatSize(var35);
            StringBuilder var48 = var43.append(var47);
            String var50 = var48.append(var3).toString();
            var42.setSummary(var50);
            StringBuilder var51 = (new StringBuilder()).append("Storage Path :: ");
            String var52 = var22.getPath();
            StringBuilder var53 = var51.append(var52).append(", Total space :: ");
            long var54 = var33 / 1048576L;
            StringBuilder var56 = var53.append(var54).append(", and Available space :: ");
            long var57 = var35 / 1048576L;
            String var59 = var56.append(var57).toString();
            int var60 = Log.d("Memory", var59);
            this.mSdMountToggle.setEnabled((boolean)1);
            Preference var61 = this.mSdMountToggle;
            String var62 = this.mRes.getString(2131231385);
            var61.setTitle(var62);
            Preference var63 = this.mSdMountToggle;
            String var64 = this.mRes.getString(2131231387);
            var63.setSummary(var64);
         } catch (IllegalArgumentException var174) {
            ;
         }
      } else {
         label61: {
            Preference var144 = this.mSdSize;
            String var145 = this.mRes.getString(2131231398);
            var144.setSummary(var145);
            Preference var146 = this.mSdAvail;
            String var147 = this.mRes.getString(2131231398);
            var146.setSummary(var147);
            if(!Environment.isExternalStorageRemovableSd()) {
               String var149 = "unmounted";
               if(var2.equals(var149) && !this.mSdMountToggleAdded) {
                  PreferenceGroup var150 = this.mSdMountPreferenceGroup;
                  Preference var151 = this.mSdMountToggle;
                  var150.addPreference(var151);
                  byte var153 = 1;
                  this.mSdMountToggleAdded = (boolean)var153;
               }
            }

            String var155 = "unmounted";
            if(!var2.equals(var155)) {
               String var157 = "nofs";
               if(!var2.equals(var157)) {
                  String var159 = "unmountable";
                  if(!var2.equals(var159)) {
                     this.mSdMountToggle.setEnabled((boolean)0);
                     Preference var164 = this.mSdMountToggle;
                     String var165 = this.mRes.getString(2131231391);
                     var164.setTitle(var165);
                     Preference var166 = this.mSdMountToggle;
                     String var167 = this.mRes.getString(2131231389);
                     var166.setSummary(var167);
                     break label61;
                  }
               }
            }

            this.mSdFormat.setEnabled((boolean)1);
            this.mSdMountToggle.setEnabled((boolean)1);
            Preference var160 = this.mSdMountToggle;
            String var161 = this.mRes.getString(2131231391);
            var160.setTitle(var161);
            Preference var162 = this.mSdMountToggle;
            String var163 = this.mRes.getString(2131231393);
            var162.setSummary(var163);
         }
      }

      String var65 = Environment.getExternalStorageState();
      String var66 = "";
      String var68 = "mounted_ro";
      if(var65.equals(var68)) {
         var65 = "mounted";
         var66 = this.mRes.getString(2131231399);
         StringBuilder var69 = (new StringBuilder()).append("updateMemoryStatus :: status = ");
         StringBuilder var71 = var69.append(var65).append(", readOnly");
         String var73 = var71.append(var66).toString();
         int var74 = Log.d("Memory", var73);
      }

      this.mSdInternalFormat.setEnabled((boolean)1);
      StringBuilder var75 = (new StringBuilder()).append("INTERNAL updateMemoryStatus :: status = ");
      String var77 = var75.append(var65).toString();
      int var78 = Log.d("Memory", var77);
      String var80 = "mounted";
      if(var65.equals(var80)) {
         int var81 = Log.d("Memory", "PATH = Remove Dialog");
         byte var83 = 100;
         this.removeDialog(var83);
      }

      String var85 = "mounted";
      if(var65.equals(var85)) {
         if(!Environment.isExternalStorageRemovable()) {
            ;
         }

         try {
            File var86 = Environment.getExternalStorageDirectory();
            StatFs var87 = new StatFs;
            String var88 = var86.getPath();
            var87.<init>(var88);
            long var91 = (long)var87.getBlockSize();
            long var93 = (long)var87.getBlockCount();
            long var95 = (long)var87.getAvailableBlocks();
            long var97 = var93 * var91;
            long var99 = var95 * var91;
            Preference var101 = this.mSdInternalSize;
            String var105 = this.formatSize(var97);
            var101.setSummary(var105);
            Preference var106 = this.mSdInternalAvail;
            StringBuilder var107 = new StringBuilder();
            String var111 = this.formatSize(var99);
            StringBuilder var112 = var107.append(var111);
            String var114 = var112.append(var66).toString();
            var106.setSummary(var114);
            StringBuilder var115 = (new StringBuilder()).append("Storage Path :: ");
            String var116 = var86.getPath();
            StringBuilder var117 = var115.append(var116).append(", Total space :: ");
            long var118 = var97 / 1048576L;
            StringBuilder var120 = var117.append(var118).append(", and Available space :: ");
            long var121 = var99 / 1048576L;
            String var123 = var120.append(var121).toString();
            int var124 = Log.d("Memory", var123);
         } catch (IllegalArgumentException var173) {
            ;
         }
      } else {
         Preference var169 = this.mSdInternalSize;
         String var170 = this.mRes.getString(2131231398);
         var169.setSummary(var170);
         Preference var171 = this.mSdInternalAvail;
         String var172 = this.mRes.getString(2131231398);
         var171.setSummary(var172);
      }

      File var125 = Environment.getDataDirectory();
      StatFs var126 = new StatFs;
      String var127 = var125.getPath();
      var126.<init>(var127);
      long var130 = (long)var126.getBlockSize();
      long var132 = (long)var126.getAvailableBlocks();
      String var135 = "memory_internal_avail";
      Preference var136 = this.findPreference(var135);
      long var137 = var132 * var130;
      String var142 = this.formatSize(var137);
      var136.setSummary(var142);
   }

   public void onCancel(DialogInterface var1) {
      int var2 = Log.d("Memory", "onCancel :: ");
      this.finish();
   }

   protected void onCreate(Bundle var1) {
      int var2 = Log.d("Memory", "onCreate ::");
      super.onCreate(var1);
      if(this.mStorageManager == null) {
         StorageManager var3 = (StorageManager)this.getSystemService("storage");
         this.mStorageManager = var3;
         StorageManager var4 = this.mStorageManager;
         StorageEventListener var5 = this.mStorageListener;
         var4.registerListener(var5);
      }

      this.addPreferencesFromResource(2130968589);
      Resources var6 = this.getResources();
      this.mRes = var6;
      Preference var7 = this.findPreference("memory_sd_size");
      this.mSdSize = var7;
      Preference var8 = this.findPreference("memory_sd_avail");
      this.mSdAvail = var8;
      Preference var9 = this.findPreference("memory_sd_mount_toggle");
      this.mSdMountToggle = var9;
      Preference var10 = this.findPreference("memory_sd_format");
      this.mSdFormat = var10;
      PreferenceGroup var11 = (PreferenceGroup)this.findPreference("memory_sd");
      this.mSdMountPreferenceGroup = var11;
      Preference var12 = this.findPreference("memory_sd_internal_size");
      this.mSdInternalSize = var12;
      Preference var13 = this.findPreference("memory_sd_internal_avail");
      this.mSdInternalAvail = var13;
      Preference var14 = this.findPreference("memory_sd_internal_format");
      this.mSdInternalFormat = var14;
      this.mSdInternalFormat.setEnabled((boolean)1);
      PreferenceGroup var15 = (PreferenceGroup)this.findPreference("memory_sd_internal");
      this.mSdInternalMountPreferenceGroup = var15;
   }

   public Dialog onCreateDialog(int var1, Bundle var2) {
      String var3 = "onCreateDialog :: id = " + var1;
      int var4 = Log.d("Memory", var3);
      AlertDialog var5;
      switch(var1) {
      case 1:
         Builder var6 = (new Builder(this)).setTitle(2131231400);
         Memory.3 var7 = new Memory.3();
         var5 = var6.setPositiveButton(2131231632, var7).setNegativeButton(2131230922, (OnClickListener)null).setMessage(2131231402).setOnCancelListener(this).create();
         break;
      case 2:
         var5 = (new Builder(this)).setTitle(2131231404).setNeutralButton(2131231632, (OnClickListener)null).setMessage(2131231406).setOnCancelListener(this).create();
         break;
      case 3:
         Builder var8 = (new Builder(this)).setTitle(2131231401);
         Memory.4 var9 = new Memory.4();
         var5 = var8.setPositiveButton(2131231632, var9).setNegativeButton(2131230922, (OnClickListener)null).setMessage(2131231403).setOnCancelListener(this).create();
         break;
      default:
         var5 = null;
      }

      return var5;
   }

   protected void onDestroy() {
      int var1 = Log.d("Memory", "onDestroy ::");
      if(this.mStorageManager != null && this.mStorageListener != null) {
         StorageManager var2 = this.mStorageManager;
         StorageEventListener var3 = this.mStorageListener;
         var2.unregisterListener(var3);
      }

      super.onDestroy();
   }

   protected void onPause() {
      super.onPause();
      int var1 = Log.d("Memory", "onPause ::");
      BroadcastReceiver var2 = this.mReceiver;
      this.unregisterReceiver(var2);
   }

   public boolean onPreferenceTreeClick(PreferenceScreen var1, Preference var2) {
      int var3 = Log.d("Memory", "onPreferenceTreeClick ::");
      Preference var4 = this.mSdMountToggle;
      boolean var7;
      if(var2.equals(var4)) {
         if(Environment.getExternalStorageStateSd().equals("mounted")) {
            int var5 = Log.d("Memory", "onPreferenceTreeClick :: mSdMountToggle for unmount");
            String var6 = Environment.getExternalStorageDirectorySd().toString();
            this.unmount(var6);
         } else {
            int var8 = Log.d("Memory", "onPreferenceTreeClick :: mSdMountToggle for mount");
            String var9 = Environment.getExternalStorageDirectorySd().toString();
            this.mount(var9);
         }

         var7 = true;
      } else {
         Preference var10 = this.mSdFormat;
         if(var2.equals(var10)) {
            int var11 = Log.d("Memory", "onPreferenceTreeClick :: mSdFormat");
            Intent var12 = new Intent("android.intent.action.VIEW");
            var12.setClass(this, MediaFormatSd.class);
            this.startActivity(var12);
            var7 = true;
         } else {
            Preference var14 = this.mSdInternalFormat;
            if(var2.equals(var14)) {
               int var15 = Log.d("Memory", "onPreferenceTreeClick :: mSdInternalFormat");
               Intent var16 = new Intent("android.intent.action.VIEW");
               var16.setClass(this, MediaFormat.class);
               this.startActivity(var16);
               var7 = true;
            } else {
               var7 = false;
            }
         }
      }

      return var7;
   }

   protected void onResume() {
      super.onResume();
      int var1 = Log.d("Memory", "onResume ::");
      IntentFilter var2 = new IntentFilter("android.intent.action.MEDIA_SCANNER_STARTED");
      var2.addAction("android.intent.action.MEDIA_SCANNER_FINISHED");
      var2.addDataScheme("file");
      BroadcastReceiver var3 = this.mReceiver;
      this.registerReceiver(var3, var2);
      this.updateMemoryStatus();
   }

   class 1 extends StorageEventListener {

      int DIALOG_FLAG = 0;


      1() {}

      public void onStorageStateChanged(String var1, String var2, String var3) {
         String var4 = "Received storage state changed notification that " + var1 + " changed state from " + var2 + " to " + var3;
         int var5 = Log.i("Memory", var4);
         if(Environment.getExternalStorageState().equals("unmounted")) {
            int var6 = Log.d("Memory", "PATH = Show Dialog");
            Memory.this.showDialog(100);
         }

         Memory.this.updateMemoryStatus();
      }
   }

   class 4 implements OnClickListener {

      4() {}

      public void onClick(DialogInterface var1, int var2) {
         String var3 = "onCreateDialog :: onClick... which = " + var2 + ", ##############>> CHECK POINT";
         int var4 = Log.d("Memory", var3);
         Memory var5 = Memory.this;
         String var6 = Environment.getExternalStorageDirectorySd().toString();
         var5.doUnmount(var6, (boolean)1);
      }
   }

   class 3 implements OnClickListener {

      3() {}

      public void onClick(DialogInterface var1, int var2) {
         String var3 = "onCreateDialog :: onClick... which = " + var2 + ", ##############>> CHECK POINT";
         int var4 = Log.d("Memory", var3);
         Memory var5 = Memory.this;
         String var6 = Environment.getExternalStorageDirectory().toString();
         var5.doUnmount(var6, (boolean)1);
      }
   }

   class 2 extends BroadcastReceiver {

      2() {}

      public void onReceive(Context var1, Intent var2) {
         Memory.this.updateMemoryStatus();
      }
   }
}
