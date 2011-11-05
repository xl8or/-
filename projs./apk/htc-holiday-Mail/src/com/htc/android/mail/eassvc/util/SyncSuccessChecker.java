package com.htc.android.mail.eassvc.util;

import android.content.Context;
import com.htc.android.mail.eassvc.core.SyncSource;
import com.htc.android.mail.eassvc.util.AccountUtil;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class SyncSuccessChecker {

   private static final String SYNC_FAIL_POSTFIX = "_fail";
   private static final String SYNC_FLAG_PREFIX = "in_sync_";
   private static final String SYNC_MORE_AVALIBLE = "more_avalible";
   private long mAccountId;
   private Context mContext;
   private String mSyncFlagFilePath;
   private ArrayList<String> moreAvalibleList = null;


   public SyncSuccessChecker(Context var1, long var2) {
      this.mContext = var1;
      this.mAccountId = var2;
      Context var4 = this.mContext;
      long var5 = this.mAccountId;
      String var7 = AccountUtil.getAccountConfigPath(var4, var5).getAbsolutePath();
      this.mSyncFlagFilePath = var7;
   }

   private File getFailFile(SyncSource var1) {
      StringBuilder var2 = new StringBuilder();
      String var3 = this.mSyncFlagFilePath;
      StringBuilder var4 = var2.append(var3);
      String var5 = File.separator;
      StringBuilder var6 = var4.append(var5).append("in_sync_");
      int var7 = var1.getType();
      String var8 = var6.append(var7).append("_fail").toString();
      return new File(var8);
   }

   private File getFile(SyncSource var1) {
      StringBuilder var2 = new StringBuilder();
      String var3 = this.mSyncFlagFilePath;
      StringBuilder var4 = var2.append(var3);
      String var5 = File.separator;
      StringBuilder var6 = var4.append(var5).append("in_sync_");
      int var7 = var1.getType();
      String var8 = var6.append(var7).toString();
      return new File(var8);
   }

   private File getMoreAvalibleFile() {
      StringBuilder var1 = new StringBuilder();
      String var2 = this.mSyncFlagFilePath;
      StringBuilder var3 = var1.append(var2);
      String var4 = File.separator;
      String var5 = var3.append(var4).append("more_avalible").toString();
      return new File(var5);
   }

   private ArrayList<String> getMoreAvalibleList() {
      if(this.moreAvalibleList == null) {
         ArrayList var1 = this.readMoreAvalibleFile();
         this.moreAvalibleList = var1;
      }

      return this.moreAvalibleList;
   }

   private ArrayList<String> readMoreAvalibleFile() {
      // $FF: Couldn't be decompiled
   }

   private void updateMoreAvalibleFile() {
      ArrayList var1 = this.getMoreAvalibleList();

      try {
         File var2 = this.getMoreAvalibleFile();
         if(!var2.exists()) {
            if(var1.size() == 0) {
               return;
            }

            boolean var3 = var2.createNewFile();
         }

         if(var1.size() == 0) {
            boolean var4 = var2.delete();
         } else {
            FileWriter var5 = new FileWriter(var2);
            BufferedWriter var6 = new BufferedWriter(var5);
            Iterator var7 = var1.iterator();

            while(var7.hasNext()) {
               String var8 = (String)var7.next();
               var6.write(var8);
               var6.newLine();
            }

            var6.flush();
            var6.close();
         }
      } catch (Exception var9) {
         var9.printStackTrace();
      }
   }

   public void cleanFile(SyncSource var1) {
      File var2 = this.getFile(var1);
      if(var2.exists()) {
         boolean var3 = var2.delete();
      }

      File var4 = this.getFailFile(var1);
      if(var4.exists()) {
         boolean var5 = var4.delete();
      }
   }

   public boolean isMoreAvalible(String var1) {
      synchronized(this){}
      boolean var6 = false;

      boolean var2;
      try {
         var6 = true;
         var2 = this.getMoreAvalibleList().contains(var1);
         var6 = false;
      } finally {
         if(var6) {
            ;
         }
      }

      return var2;
   }

   public boolean isSyncBreak(SyncSource var1) {
      return this.getFile(var1).exists();
   }

   public boolean isSyncFail(SyncSource var1) {
      File var2 = this.getFile(var1);
      File var3 = this.getFailFile(var1);
      boolean var4;
      if(!var2.exists() && !var3.exists()) {
         var4 = false;
      } else {
         var4 = true;
      }

      return var4;
   }

   public void makeFile(SyncSource var1) {
      File var2 = this.getFile(var1);

      try {
         if(!var2.exists()) {
            boolean var3 = var2.createNewFile();
         }
      } catch (IOException var4) {
         var4.printStackTrace();
      }
   }

   public void markAsFail(SyncSource var1) {
      File var2 = this.getFile(var1);
      File var3 = this.getFailFile(var1);

      try {
         if(var2.exists()) {
            if(var3.exists()) {
               boolean var4 = var2.delete();
            } else {
               var2.renameTo(var3);
            }
         } else {
            boolean var6 = var3.createNewFile();
         }
      } catch (IOException var7) {
         var7.printStackTrace();
      }
   }

   public void setMoreAvalible(String var1, boolean var2) {
      synchronized(this){}

      try {
         ArrayList var3 = this.getMoreAvalibleList();
         if(var2) {
            if(!var3.contains(var1)) {
               var3.add(var1);
               this.updateMoreAvalibleFile();
            }
         } else if(var3.contains(var1)) {
            var3.remove(var1);
            this.updateMoreAvalibleFile();
         }
      } finally {
         ;
      }

   }
}
