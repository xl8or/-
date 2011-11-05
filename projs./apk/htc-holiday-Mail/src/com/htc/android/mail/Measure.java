package com.htc.android.mail;

import android.util.Log;

public class Measure {

   private static final String TAG = "Time";
   static long[] m_Time = m_nMaxCount;
   static boolean[] m_bShow = new boolean[m_nMaxCount];
   static String[] m_labels = new String[m_nMaxCount];
   static int m_nLast = 0;
   static int[] m_nLine = new int[m_nMaxCount];
   static int m_nMaxCount = 60;


   public Measure() {}

   public static long RecordTime() {
      return RecordTime((String)null, (Exception)null);
   }

   public static long RecordTime(String var0, Exception var1) {
      int var2;
      if(var1 != null) {
         var2 = getLineNumber(var1);
      } else {
         var2 = 0;
      }

      int var3 = m_nMaxCount - 1;
      int var4 = m_nLast;
      if(var3 == var4) {
         int var5 = Log.i("Time", "Extent Table");
         byte var6 = 30;
         Object var7 = m_nMaxCount + var6;
         String[] var8 = new String[m_nMaxCount + var6];
         boolean[] var9 = new boolean[m_nMaxCount + var6];
         int[] var10 = new int[m_nMaxCount + var6];
         int var11 = 0;

         while(true) {
            int var12 = m_nMaxCount;
            if(var11 >= var12) {
               m_nMaxCount += var6;
               m_Time = (long[])var7;
               m_labels = var8;
               m_bShow = var9;
               m_nLine = var10;
               break;
            }

            long var13 = m_Time[var11];
            ((Object[])var7)[var11] = var13;
            String var15 = m_labels[var11];
            var8[var11] = var15;
            boolean var16 = m_bShow[var11];
            var9[var11] = var16;
            int var17 = m_nLine[var11];
            var10[var11] = var17;
            ++var11;
         }
      }

      long var18 = System.currentTimeMillis();
      if(var0 == null) {
         long[] var20 = m_Time;
         int var21 = m_nLast;
         var20[var21] = var18;
         String[] var22 = m_labels;
         int var23 = m_nLast;
         var22[var23] = var0;
         int[] var24 = m_nLine;
         int var25 = m_nLast;
         var24[var25] = var2;
         boolean[] var26 = m_bShow;
         int var27 = m_nLast;
         var26[var27] = false;
         ++m_nLast;
      } else {
         byte var37 = -1;
         int var38 = 0;

         int var41;
         while(true) {
            int var39 = m_nLast;
            if(var38 >= var39) {
               var41 = var37;
               break;
            }

            if(m_labels[var38] != false) {
               String var40 = m_labels[var38];
               if(var0.compareTo(var40) == 0) {
                  var41 = var38;
                  break;
               }
            }

            ++var38;
         }

         if(var41 != -1) {
            m_Time[var41] = var18;
            m_labels[var41] = var0;
            m_nLine[var41] = var2;
            m_bShow[var41] = false;
         } else {
            long[] var42 = m_Time;
            int var43 = m_nLast;
            var42[var43] = var18;
            String[] var44 = m_labels;
            int var45 = m_nLast;
            var44[var45] = var0;
            int[] var46 = m_nLine;
            int var47 = m_nLast;
            var46[var47] = var2;
            boolean[] var48 = m_bShow;
            int var49 = m_nLast;
            var48[var49] = false;
            ++m_nLast;
         }
      }

      if(var2 != 0) {
         if(var0 != null) {
            long var28 = m_Time[0];
            if(var18 != var28) {
               StringBuilder var30 = (new StringBuilder()).append(var0).append(" Line=").append(var2).append(" time=");
               long var31 = m_Time[0];
               long var33 = var18 - var31;
               String var35 = var30.append(var33).toString();
               int var36 = Log.i("Time", var35);
            } else {
               String var50 = var0 + " Line=" + var2 + " time=" + var18;
               int var51 = Log.i("Time", var50);
            }
         } else {
            long var52 = m_Time[0];
            if(var18 != var52) {
               StringBuilder var54 = (new StringBuilder()).append(" Line=").append(var2).append(" time=");
               long var55 = m_Time[0];
               long var57 = var18 - var55;
               String var59 = var54.append(var57).toString();
               int var60 = Log.i("Time", var59);
            } else {
               String var61 = " Line=" + var2 + " time=" + var18;
               int var62 = Log.i("Time", var61);
            }
         }
      }

      return var18;
   }

   public static void Reset() {
      int var0 = 0;

      while(true) {
         int var1 = m_nMaxCount;
         if(var0 >= var1) {
            m_nLast = 0;
            return;
         }

         m_Time[var0] = 0L;
         m_labels[var0] = false;
         m_bShow[var0] = false;
         m_nLine[var0] = 0;
         ++var0;
      }
   }

   public static void ShowTime(String param0, String param1) {
      // $FF: Couldn't be decompiled
   }

   public static int getLineNumber(Exception var0) {
      StackTraceElement[] var1 = var0.getStackTrace();
      int var2;
      if(var1 != null && var1.length != 0) {
         var2 = var1[0].getLineNumber();
      } else {
         var2 = -1;
      }

      return var2;
   }
}
