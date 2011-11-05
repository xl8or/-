package com.android.vending;

import android.app.backup.BackupAgentHelper;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupDataOutput;
import android.app.backup.BackupManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.ParcelFileDescriptor;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile;
import com.google.android.finsky.services.RestoreService;
import com.google.android.finsky.utils.VendingPreferences;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class VendingBackupAgent extends BackupAgentHelper {

   private static final String VENDING_KEY = "vending";


   public VendingBackupAgent() {}

   public static void registerWithBackup(Context var0) {
      if(!((Boolean)VendingPreferences.BACKED_UP.get()).booleanValue()) {
         (new BackupManager(var0)).dataChanged();
      }
   }

   public void onBackup(ParcelFileDescriptor var1, BackupDataOutput var2, ParcelFileDescriptor var3) throws IOException {
      long var4 = ((Long)G.androidId.get()).longValue();
      ByteArrayOutputStream var6 = new ByteArrayOutputStream();
      (new DataOutputStream(var6)).writeLong(var4);
      byte[] var7 = var6.toByteArray();
      int var8 = var7.length;
      var2.writeEntityHeader("vending", var8);
      int var10 = var7.length;
      var2.writeEntityData(var7, var10);
      PreferenceFile.SharedPreference var12 = VendingPreferences.BACKED_UP;
      Boolean var13 = Boolean.valueOf((boolean)1);
      var12.put(var13);
   }

   public void onRestore(BackupDataInput var1, int var2, ParcelFileDescriptor var3) throws IOException {
      String var4;
      do {
         if(!var1.readNextHeader()) {
            return;
         }

         var4 = var1.getKey();
      } while(!"vending".equals(var4));

      int var5 = var1.getDataSize();
      byte[] var6 = new byte[var5];
      var1.readEntityData(var6, 0, var5);
      ByteArrayInputStream var8 = new ByteArrayInputStream(var6);
      long var9 = (new DataInputStream(var8)).readLong();
      Context var11 = this.getApplicationContext();
      Intent var12 = new Intent(var11, RestoreService.class);
      String var13 = Long.toHexString(var9);
      var12.putExtra("aid", var13);
      ComponentName var15 = this.getApplicationContext().startService(var12);
   }
}
