package com.htc.android.mail.ulog;

import android.content.Context;
import com.htc.android.mail.Mail;
import com.htc.android.mail.ll;
import com.htc.android.mail.ulog.Record;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class ULogFileMgr {

   private static final boolean DEBUG = Mail.MAIL_DEBUG;
   private static final String TAG = "ULogFileMgr";
   public static Object filelock = new Object();
   private final int NOTFOUND = -1;
   private BufferedReader breader;
   private BufferedWriter bwriter;
   private ArrayList<Record> cacheNotTodayRecord;
   private ArrayList<Record> content;
   private File file;
   private Context mContext;


   ULogFileMgr(Context var1) {
      try {
         File var2 = var1.getDir("ulog", 0);
         File var3 = new File(var2, "mailcount");
         this.file = var3;
         ArrayList var4 = new ArrayList();
         this.content = var4;
         ArrayList var5 = new ArrayList();
         this.cacheNotTodayRecord = var5;
         if(!this.file.exists()) {
            boolean var6 = this.file.createNewFile();
         }

         this.initData();
      } catch (IOException var7) {
         var7.printStackTrace();
      }
   }

   private void add(long var1, long var3, long var5, long var7) {
      ArrayList var9 = this.content;
      Record var18 = new Record(var1, var3, var5, var7);
      var9.add(var18);
   }

   private void initData() {
      Object var1 = filelock;
      synchronized(var1) {
         try {
            File var2 = this.file;
            FileReader var3 = new FileReader(var2);
            BufferedReader var4 = new BufferedReader(var3);
            this.breader = var4;

            while(true) {
               String var5 = this.breader.readLine();
               if(var5 == null) {
                  this.breader.close();
                  break;
               }

               String[] var6 = var5.split(",");
               long var7 = Long.parseLong(var6[0]);
               long var9 = Long.parseLong(var6[1]);
               long var11 = Long.parseLong(var6[2]);
               long var13 = Long.parseLong(var6[3]);
               Record var15 = new Record(var7, var9, var11, var13);
               this.content.add(var15);
            }
         } catch (NumberFormatException var19) {
            var19.printStackTrace();
         } catch (IOException var20) {
            var20.printStackTrace();
         }

      }
   }

   public static boolean isAddShowMeFile(Context var0) {
      File var1 = var0.getApplicationContext().getDir("ulog", 0);
      File var2 = new File(var1, "showMe");

      boolean var3;
      boolean var4;
      try {
         var3 = var2.createNewFile();
      } catch (IOException var5) {
         var5.printStackTrace();
         var4 = false;
         return var4;
      }

      if(var3) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   private int isIdExist(long var1) {
      int var3 = this.content.size() - 1;

      int var4;
      while(true) {
         if(var3 <= -1) {
            var4 = -1;
            break;
         }

         if(((Record)this.content.get(var3)).getId() == var1) {
            var4 = var3;
            break;
         }

         var3 += -1;
      }

      return var4;
   }

   private void output(boolean var1) {
      Object var2 = filelock;
      synchronized(var2) {
         try {
            File var3 = this.file;
            FileWriter var4 = new FileWriter(var3);
            BufferedWriter var5 = new BufferedWriter(var4);
            this.bwriter = var5;
            int var6 = this.content.size();

            for(int var7 = 0; var7 < var6; ++var7) {
               BufferedWriter var8 = this.bwriter;
               StringBuilder var9 = new StringBuilder();
               long var10 = ((Record)this.content.get(var7)).getId();
               StringBuilder var12 = var9.append(var10).append(",");
               String var13 = ((Record)this.content.get(var7)).toString();
               String var14 = var12.append(var13).append('\n').toString();
               var8.write(var14);
            }

            this.bwriter.flush();
            this.bwriter.close();
            if(var1) {
               this.refreshData();
            }
         } catch (IOException var16) {
            var16.printStackTrace();
         }

      }
   }

   private void setCacheForNotTodayRecords() {
      int var1 = 0;

      while(true) {
         int var2 = this.content.size();
         if(var1 >= var2) {
            return;
         }

         long var3 = ((Record)this.content.get(var1)).getRecordTime();
         if(!this.isToday(var3)) {
            ArrayList var5 = this.cacheNotTodayRecord;
            Object var6 = this.content.get(var1);
            var5.add(var6);
            this.content.remove(var1);
         } else {
            ++var1;
         }
      }
   }

   private void update(int var1, long var2, long var4, long var6, long var8) {
      Record var10 = (Record)this.content.get(var1);
      long var11 = var10.getMailReceivedNum() + var4;
      var10.setMailReceivedNum(var11);
      long var13 = var10.getMailSentNum() + var6;
      var10.setMailSentNum(var13);
      var10.setRecordTime(var8);
   }

   public void clean() {
      if(DEBUG) {
         ll.i("ULogFileMgr", "clean()");
      }

      Object var1 = filelock;
      synchronized(var1) {
         try {
            if(this.file.exists()) {
               boolean var2 = this.file.delete();
               boolean var3 = this.file.createNewFile();
            }
         } catch (IOException var5) {
            var5.printStackTrace();
         }

      }
   }

   public void cleanNotTodayRecordCache() {
      ArrayList var1 = new ArrayList();
      this.cacheNotTodayRecord = var1;
      this.output((boolean)0);
   }

   public void delete(boolean var1, long var2) {
      if(DEBUG) {
         String var4 = "delete() - id: " + var2;
         ll.i("ULogFileMgr", var4);
      }

      int var5 = this.isIdExist(var2);
      if(-1 != var5) {
         this.content.remove(var5);
      }

      this.output(var1);
   }

   public ArrayList<Record> getRecordList() {
      this.setCacheForNotTodayRecords();
      ArrayList var1;
      if(this.cacheNotTodayRecord.size() > 0) {
         var1 = this.cacheNotTodayRecord;
      } else {
         var1 = this.content;
      }

      return var1;
   }

   public boolean hasNotToday() {
      boolean var1 = false;
      int var2 = 0;

      while(true) {
         int var3 = this.content.size();
         if(var2 >= var3) {
            break;
         }

         long var4 = ((Record)this.content.get(var2)).getRecordTime();
         if(!this.isToday(var4)) {
            var1 = true;
            break;
         }

         ++var2;
      }

      return var1;
   }

   public boolean hasOldRecord() {
      boolean var1;
      if(this.cacheNotTodayRecord.size() > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isToday(long var1) {
      long var3 = System.currentTimeMillis();
      GregorianCalendar var5 = new GregorianCalendar();
      int var6 = var5.get(11);
      int var7 = var5.get(12);
      int var8 = var5.get(13);
      int var9 = var5.get(14);
      int var10 = var6 * 3600;
      int var11 = var7 * 60;
      long var12 = (long)((var10 + var11 + var8) * 1000);
      long var14 = 86400000L - var12;
      long var16 = (long)var9;
      long var18 = var14 + var16 - 541000L;
      boolean var20;
      if(var3 + var18 - var1 > 86400000L) {
         var20 = false;
      } else {
         var20 = true;
      }

      return var20;
   }

   public boolean isULogFileEmpty() {
      boolean var1;
      if(this.file.length() == 0L) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void manageMailCountULog(boolean var1, long var2, long var4, long var6) {
      int var11 = this.isIdExist(var2);
      if(DEBUG) {
         StringBuilder var12 = (new StringBuilder()).append("manageMailCountULog() - accountId: ");
         StringBuilder var15 = var12.append(var2).append("mailReceivedNum: ");
         StringBuilder var18 = var15.append(var4).append("mailSentNum: ");
         String var21 = var18.append(var6).append("result").append(var11).toString();
         ll.i("ULogFileMgr", var21);
      }

      label15: {
         if(-1 != var11) {
            long var22 = ((Record)this.content.get(var11)).getRecordTime();
            if(this.isToday(var22)) {
               long var33 = System.currentTimeMillis();
               this.update(var11, var2, var4, var6, var33);
               break label15;
            }
         }

         long var24 = System.currentTimeMillis();
         this.add(var2, var4, var6, var24);
      }

      this.output(var1);
   }

   public void refreshData() {
      if(!this.content.isEmpty()) {
         this.content.clear();
      }

      this.initData();
   }
}
