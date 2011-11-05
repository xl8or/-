package com.android.common;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.http.AndroidHttpClient;
import android.text.format.Time;
import com.android.common.SharedPreferencesCompat;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class OperationScheduler {

   private static final String PREFIX = "OperationScheduler_";
   private final SharedPreferences mStorage;


   public OperationScheduler(SharedPreferences var1) {
      this.mStorage = var1;
   }

   private long getTimeBefore(String var1, long var2) {
      long var4 = this.mStorage.getLong(var1, 0L);
      if(var4 > var2) {
         var4 = var2;
         SharedPreferencesCompat.apply(this.mStorage.edit().putLong(var1, var2));
      }

      return var4;
   }

   public static OperationScheduler.Options parseOptions(String var0, OperationScheduler.Options var1) throws IllegalArgumentException {
      String[] var2 = var0.split(" +");
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String var5 = var2[var4];
         if(var5.length() != 0) {
            if(var5.startsWith("backoff=")) {
               int var6 = var5.indexOf(43, 8);
               if(var6 < 0) {
                  long var7 = parseSeconds(var5.substring(8));
                  var1.backoffFixedMillis = var7;
               } else {
                  if(var6 > 8) {
                     long var9 = parseSeconds(var5.substring(8, var6));
                     var1.backoffFixedMillis = var9;
                  }

                  int var11 = var6 + 1;
                  long var12 = parseSeconds(var5.substring(var11));
                  var1.backoffIncrementalMillis = var12;
               }
            } else if(var5.startsWith("max=")) {
               long var14 = parseSeconds(var5.substring(4));
               var1.maxMoratoriumMillis = var14;
            } else if(var5.startsWith("min=")) {
               long var16 = parseSeconds(var5.substring(4));
               var1.minTriggerMillis = var16;
            } else if(var5.startsWith("period=")) {
               long var18 = parseSeconds(var5.substring(7));
               var1.periodicIntervalMillis = var18;
            } else {
               long var20 = parseSeconds(var5);
               var1.periodicIntervalMillis = var20;
            }
         }
      }

      return var1;
   }

   private static long parseSeconds(String var0) throws NumberFormatException {
      return (long)(Float.parseFloat(var0) * 1000.0F);
   }

   protected long currentTimeMillis() {
      return System.currentTimeMillis();
   }

   public long getLastAttemptTimeMillis() {
      long var1 = this.mStorage.getLong("OperationScheduler_lastSuccessTimeMillis", 0L);
      long var3 = this.mStorage.getLong("OperationScheduler_lastErrorTimeMillis", 0L);
      return Math.max(var1, var3);
   }

   public long getLastSuccessTimeMillis() {
      return this.mStorage.getLong("OperationScheduler_lastSuccessTimeMillis", 0L);
   }

   public long getNextTimeMillis(OperationScheduler.Options var1) {
      long var2;
      if(!this.mStorage.getBoolean("OperationScheduler_enabledState", (boolean)1)) {
         var2 = Long.MAX_VALUE;
      } else if(this.mStorage.getBoolean("OperationScheduler_permanentError", (boolean)0)) {
         var2 = Long.MAX_VALUE;
      } else {
         int var4 = this.mStorage.getInt("OperationScheduler_errorCount", 0);
         long var5 = this.currentTimeMillis();
         String var8 = "OperationScheduler_lastSuccessTimeMillis";
         long var9 = this.getTimeBefore(var8, var5);
         String var12 = "OperationScheduler_lastErrorTimeMillis";
         long var13 = this.getTimeBefore(var12, var5);
         long var15 = this.mStorage.getLong("OperationScheduler_triggerTimeMillis", Long.MAX_VALUE);
         String var18 = "OperationScheduler_moratoriumSetTimeMillis";
         long var19 = this.getTimeBefore(var18, var5);
         long var21 = var1.maxMoratoriumMillis + var19;
         String var24 = "OperationScheduler_moratoriumTimeMillis";
         long var27 = this.getTimeBefore(var24, var21);
         long var29 = var15;
         if(var1.periodicIntervalMillis > 0L) {
            long var31 = var1.periodicIntervalMillis + var9;
            var29 = Math.min(var15, var31);
         }

         long var37 = Math.max(var29, var27);
         long var39 = var1.minTriggerMillis + var9;
         var2 = Math.max(var37, var39);
         if(var4 > 0) {
            long var45 = var1.backoffFixedMillis + var13;
            long var47 = var1.backoffIncrementalMillis;
            long var49 = (long)var4;
            long var51 = var47 * var49;
            long var53 = var45 + var51;
            var2 = Math.max(var2, var53);
         }
      }

      return var2;
   }

   public void onPermanentError() {
      SharedPreferencesCompat.apply(this.mStorage.edit().putBoolean("OperationScheduler_permanentError", (boolean)1));
   }

   public void onSuccess() {
      this.resetTransientError();
      this.resetPermanentError();
      Editor var1 = this.mStorage.edit().remove("OperationScheduler_errorCount").remove("OperationScheduler_lastErrorTimeMillis").remove("OperationScheduler_permanentError").remove("OperationScheduler_triggerTimeMillis");
      long var2 = this.currentTimeMillis();
      SharedPreferencesCompat.apply(var1.putLong("OperationScheduler_lastSuccessTimeMillis", var2));
   }

   public void onTransientError() {
      Editor var1 = this.mStorage.edit();
      long var2 = this.currentTimeMillis();
      var1.putLong("OperationScheduler_lastErrorTimeMillis", var2);
      int var5 = this.mStorage.getInt("OperationScheduler_errorCount", 0) + 1;
      var1.putInt("OperationScheduler_errorCount", var5);
      SharedPreferencesCompat.apply(var1);
   }

   public void resetPermanentError() {
      SharedPreferencesCompat.apply(this.mStorage.edit().remove("OperationScheduler_permanentError"));
   }

   public void resetTransientError() {
      SharedPreferencesCompat.apply(this.mStorage.edit().remove("OperationScheduler_errorCount"));
   }

   public void setEnabledState(boolean var1) {
      SharedPreferencesCompat.apply(this.mStorage.edit().putBoolean("OperationScheduler_enabledState", var1));
   }

   public boolean setMoratoriumTimeHttp(String var1) {
      boolean var2 = true;

      try {
         long var3 = Long.valueOf(var1).longValue() * 1000L;
         long var5 = this.currentTimeMillis() + var3;
         this.setMoratoriumTimeMillis(var5);
      } catch (NumberFormatException var12) {
         try {
            long var8 = AndroidHttpClient.parseDate(var1);
            this.setMoratoriumTimeMillis(var8);
         } catch (IllegalArgumentException var11) {
            var2 = false;
         }
      }

      return var2;
   }

   public void setMoratoriumTimeMillis(long var1) {
      Editor var3 = this.mStorage.edit().putLong("OperationScheduler_moratoriumTimeMillis", var1);
      long var4 = this.currentTimeMillis();
      SharedPreferencesCompat.apply(var3.putLong("OperationScheduler_moratoriumSetTimeMillis", var4));
   }

   public void setTriggerTimeMillis(long var1) {
      SharedPreferencesCompat.apply(this.mStorage.edit().putLong("OperationScheduler_triggerTimeMillis", var1));
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder("[OperationScheduler:");
      Set var2 = this.mStorage.getAll().keySet();
      Iterator var3 = (new TreeSet(var2)).iterator();

      while(var3.hasNext()) {
         String var4 = (String)var3.next();
         if(var4.startsWith("OperationScheduler_")) {
            if(var4.endsWith("TimeMillis")) {
               Time var5 = new Time();
               long var6 = this.mStorage.getLong(var4, 0L);
               var5.set(var6);
               StringBuilder var8 = var1.append(" ");
               int var9 = "OperationScheduler_".length();
               int var10 = var4.length() + -10;
               String var11 = var4.substring(var9, var10);
               var8.append(var11);
               StringBuilder var13 = var1.append("=");
               String var14 = var5.format("%Y-%m-%d/%H:%M:%S");
               var13.append(var14);
            } else {
               StringBuilder var16 = var1.append(" ");
               int var17 = "OperationScheduler_".length();
               String var18 = var4.substring(var17);
               var16.append(var18);
               StringBuilder var20 = var1.append("=");
               String var21 = this.mStorage.getAll().get(var4).toString();
               var20.append(var21);
            }
         }
      }

      return var1.append("]").toString();
   }

   public static class Options {

      public long backoffFixedMillis = 0L;
      public long backoffIncrementalMillis = 5000L;
      public long maxMoratoriumMillis = 86400000L;
      public long minTriggerMillis = 0L;
      public long periodicIntervalMillis = 0L;


      public Options() {}

      public String toString() {
         Object[] var1 = new Object[5];
         Double var2 = Double.valueOf((double)this.backoffFixedMillis / 1000.0D);
         var1[0] = var2;
         Double var3 = Double.valueOf((double)this.backoffIncrementalMillis / 1000.0D);
         var1[1] = var3;
         Double var4 = Double.valueOf((double)this.maxMoratoriumMillis / 1000.0D);
         var1[2] = var4;
         Double var5 = Double.valueOf((double)this.minTriggerMillis / 1000.0D);
         var1[3] = var5;
         Double var6 = Double.valueOf((double)this.periodicIntervalMillis / 1000.0D);
         var1[4] = var6;
         return String.format("OperationScheduler.Options[backoff=%.1f+%.1f max=%.1f min=%.1f period=%.1f]", var1);
      }
   }
}
