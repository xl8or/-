package com.seven.Z7.util;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import com.seven.util.Z7ErrorCode;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.channels.Channel;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.TimeZone;

public class Utils {

   private static final String EMAIL_ADDRESS_DELIMITERS = ";,";
   private static final int FEATURE_PER_INT = 30;
   public static final int INTDATE_BITS_DAY = 6;
   public static final int INTDATE_BITS_MONTH = 4;
   public static final int INTDATE_BITS_YEAR = 21;
   private static final int INTDATE_MASK_DAY = getMask(6);
   private static final int INTDATE_MASK_MONTH = getMask(4);
   private static final int INTDATE_MASK_YEAR = getMask(21);
   public static final int INTDATE_MAX_YEAR = 2097151;
   private static final int[] MONTH_DAYS_NON_LEAP = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
   public static final String TAG = "Utils";


   public Utils() {}

   public static boolean canSaveToDevice(long var0) {
      long var2 = getAvailableSpaceDevice();
      boolean var4;
      if(var0 < var2) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   public static boolean canSaveToSDCard(long var0) {
      long var2 = getAvailableSpaceSDCard();
      boolean var4;
      if(var0 < var2) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   public static void close(InputStream var0) {
      if(var0 != null) {
         try {
            var0.close();
         } catch (IOException var4) {
            String var2 = "Failed to close " + var0;
            int var3 = Log.v("Utils", var2);
         }
      }
   }

   public static void close(OutputStream var0) {
      if(var0 != null) {
         try {
            var0.close();
         } catch (IOException var4) {
            String var2 = "Failed to close " + var0;
            int var3 = Log.v("Utils", var2);
         }
      }
   }

   public static void closeCloseable(Closeable var0) {
      if(var0 != null) {
         try {
            var0.close();
         } catch (IOException var4) {
            String var2 = "failed to close " + var0;
            int var3 = Log.w("Utils", var2);
         }
      }
   }

   public static void compressFiles(File[] param0, File param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public static void copyFile(File param0, File param1, boolean param2) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public static int dateToInt(int var0, int var1, int var2) {
      int var3 = (INTDATE_MASK_YEAR & var2) << 10;
      int var4 = var1 << 6;
      return var3 + var4 + var0;
   }

   public static int dateToInt(Calendar var0) {
      int var1 = var0.get(5);
      int var2 = var0.get(2);
      int var3 = var0.get(1);
      return dateToInt(var1, var2, var3);
   }

   public static void deleteFiles(File[] var0) {
      File[] var1 = var0;
      int var2 = var0.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         File var4 = var1[var3];
         String var5 = "Removing file " + var4;
         int var6 = Log.v("Utils", var5);
         if(!var4.delete()) {
            String var7 = "Can\'t remove " + var4;
            int var8 = Log.v("Utils", var7);
         }
      }

   }

   public static boolean equals(byte[] var0, byte[] var1) {
      boolean var4;
      if(var0 != null && var1 != null) {
         int var2 = var0.length;
         int var3 = var1.length;
         if(var2 == var3) {
            if(var0 != var1) {
               for(int var5 = var0.length - 1; var5 >= 0; var5 += -1) {
                  byte var6 = var0[var5];
                  byte var7 = var1[var5];
                  if(var6 != var7) {
                     var4 = false;
                     return var4;
                  }
               }
            }

            var4 = true;
            return var4;
         }
      }

      var4 = false;
      return var4;
   }

   public static boolean equals(boolean[] var0, boolean[] var1) {
      boolean var4;
      if(var0 != null && var1 != null) {
         int var2 = var0.length;
         int var3 = var1.length;
         if(var2 == var3) {
            if(var0 != var1) {
               for(int var5 = var0.length - 1; var5 >= 0; var5 += -1) {
                  boolean var6 = var0[var5];
                  boolean var7 = var1[var5];
                  if(var6 != var7) {
                     var4 = false;
                     return var4;
                  }
               }
            }

            var4 = true;
            return var4;
         }
      }

      var4 = false;
      return var4;
   }

   public static Date fillAndGetDate(int var0, int var1, int var2, int var3, int var4, int var5, int var6, TimeZone var7) {
      Calendar var8 = Calendar.getInstance(var7);
      var8.set(1, var0);
      var8.set(2, var1);
      var8.set(5, var2);
      var8.set(11, var3);
      var8.set(12, var4);
      var8.set(13, var5);
      var8.set(14, var6);
      return var8.getTime();
   }

   public static int findNextNonNumber(String var0, int var1) {
      int var5;
      if(var0 != null) {
         int var2 = var0.length();
         if(var1 < var2) {
            int var3;
            if(var1 < 0) {
               var3 = 0;
            } else {
               var3 = var1;
            }

            while(true) {
               int var4 = var0.length();
               if(var3 >= var4) {
                  break;
               }

               if(!Character.isDigit(var0.charAt(var3))) {
                  var5 = var3;
                  return var5;
               }

               ++var3;
            }
         }
      }

      var5 = -1;
      return var5;
   }

   public static int findNextNumber(String var0, int var1) {
      int var5;
      if(var0 != null) {
         int var2 = var0.length();
         if(var1 < var2) {
            int var3;
            if(var1 < 0) {
               var3 = 0;
            } else {
               var3 = var1;
            }

            while(true) {
               int var4 = var0.length();
               if(var3 >= var4) {
                  break;
               }

               if(Character.isDigit(var0.charAt(var3))) {
                  var5 = var3;
                  return var5;
               }

               ++var3;
            }
         }
      }

      var5 = -1;
      return var5;
   }

   public static long getAvailableSpace(String var0) {
      StatFs var1 = new StatFs(var0);
      long var2 = (long)var1.getAvailableBlocks();
      long var4 = (long)var1.getBlockSize();
      return var2 * var4;
   }

   public static long getAvailableSpaceDevice() {
      return getAvailableSpace(Environment.getDataDirectory().getAbsolutePath());
   }

   public static long getAvailableSpaceSDCard() {
      return getAvailableSpace(Environment.getExternalStorageDirectory().getAbsolutePath());
   }

   public static final Boolean getBoolean(boolean var0) {
      byte var1;
      if(var0) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      return Boolean.valueOf((boolean)var1);
   }

   public static int getDaysBetween(int var0, int var1, int var2, int var3, int var4, int var5) {
      int var6;
      if(var2 == var5 && var0 == var3) {
         var6 = var4 - var1;
      } else {
         int var7 = var2;
         int var8 = 0;

         while(var7 <= var5) {
            int var9 = 0;
            if(var7 == var2) {
               var9 = var0 + 1;
               var8 = getNumDays(var0, var7) - var1;
               if(var0 == 11) {
                  ++var7;
                  continue;
               }
            }

            int var10 = 12;
            if(var7 == var5) {
               var10 = var3;
            }

            for(int var11 = var9; var11 < var10; ++var11) {
               int var12 = getNumDays(var11, var7);
               var8 += var12;
            }

            ++var7;
         }

         var6 = var8 + var4;
      }

      return var6;
   }

   public static TimeZone getDeviceTimeZone() {
      return TimeZone.getDefault();
   }

   public static String getLocalIpAddress() {
      String var3;
      String var4;
      label38: {
         try {
            Enumeration var0 = NetworkInterface.getNetworkInterfaces();

            while(var0.hasMoreElements()) {
               Enumeration var1 = ((NetworkInterface)var0.nextElement()).getInetAddresses();

               while(var1.hasMoreElements()) {
                  InetAddress var2 = (InetAddress)var1.nextElement();
                  if(!var2.isLoopbackAddress()) {
                     var3 = var2.getHostAddress().toString();
                     break label38;
                  }
               }
            }
         } catch (SocketException var6) {
            ;
         }

         var4 = "";
         return var4;
      }

      var4 = var3;
      return var4;
   }

   private static int getMask(int var0) {
      int var1 = 0;

      for(int var2 = var0; var2 > 0; var2 += -1) {
         var1 = var1 << 1 | 1;
      }

      return var1;
   }

   public static int getMonthsBetween(int var0, int var1, int var2, int var3) {
      int var4 = var1;

      int var5;
      for(var5 = 0; var4 <= var3; ++var4) {
         if(var1 == var3) {
            int var6 = var2 - var0;
            var5 += var6;
         } else if(var4 == var1) {
            int var7 = 12 - var0;
            var5 += var7;
         } else if(var4 == var3) {
            var5 += var2;
         } else {
            var5 += 12;
         }
      }

      return var5;
   }

   public static Date getNextDay(long var0, TimeZone var2) {
      Calendar var3 = Calendar.getInstance(var2);
      Date var4 = new Date(var0);
      var3.setTime(var4);
      int var5 = var3.get(1);
      int var6 = var3.get(2);
      int var7 = var3.get(5) + 1;
      int var8 = getNumDays(var6, var5);
      if(var7 > var8) {
         var7 = 1;
         ++var6;
         if(var6 > 11) {
            int var9 = var5 + 1;
            var3.set(1, var9);
         }

         var3.set(2, var6);
      }

      var3.set(5, var7);
      return var3.getTime();
   }

   public static final int getNumDays(int var0, int var1) {
      int var2;
      if(var0 >= 0 && var0 <= 11) {
         if(var0 == 1 && isLeapYear(var1)) {
            var2 = 29;
         } else {
            var2 = MONTH_DAYS_NON_LEAP[var0];
         }
      } else {
         var2 = -1;
      }

      return var2;
   }

   public static String getSimpleClassName(String var0) {
      int var1 = var0.lastIndexOf(46) + 1;
      return var0.substring(var1);
   }

   public static long getSizeFromDescriptor(ContentResolver param0, Uri param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public static Date getTodaysDate(TimeZone var0) {
      Calendar var1 = Calendar.getInstance(var0);
      var1.set(11, 0);
      var1.set(12, 0);
      var1.set(13, 0);
      var1.set(14, 0);
      return var1.getTime();
   }

   public static int getYearsBetween(Date var0, Date var1, TimeZone var2) {
      Calendar var3 = Calendar.getInstance(var2);
      var3.setTime(var0);
      int var4 = var3.get(1);
      var3.setTime(var1);
      return var3.get(1) - var4;
   }

   public static void insertObjectIntoArray(Object[] var0, Object[] var1, Object var2, int var3) {
      System.arraycopy(var0, 0, var1, 0, var3);
      int var4 = var3 + 1;
      int var5 = var0.length - var3;
      System.arraycopy(var0, var3, var1, var4, var5);
      var1[var3] = var2;
   }

   public static void intToDate(int var0, Calendar var1) {
      int var2 = INTDATE_MASK_DAY & var0;
      var1.set(5, var2);
      int var3 = var0 >> 6;
      int var4 = INTDATE_MASK_MONTH & var3;
      var1.set(2, var4);
      int var5 = var3 >> 4;
      var1.set(1, var5);
   }

   public static boolean isCharOrDigit(char var0) {
      boolean var1;
      if(!Character.isDigit(var0) && !Character.isLowerCase(var0) && !Character.isUpperCase(var0)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public static final boolean isEmpty(String var0) {
      boolean var1;
      if(var0 != null && var0.length() != 0) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public static final boolean isKeywordFound(String var0, String var1) {
      boolean var4;
      if(var1 != null) {
         int var2 = var1.length();
         int var3 = var0.length();
         if(var2 >= var3) {
            String var5 = var1.toLowerCase();
            String var6 = var0.toLowerCase();
            var4 = var5.startsWith(var6);
            return var4;
         }
      }

      var4 = false;
      return var4;
   }

   public static final boolean isKeywordFound(String var0, String[] var1) {
      boolean var7;
      if(var1 != null) {
         int var2 = var0.length();
         int var3 = 0;

         while(true) {
            int var4 = var1.length;
            if(var3 >= var4) {
               break;
            }

            if(var1[var3] != false && var1[var3].length() >= var2) {
               String var5 = var1[var3].toLowerCase();
               String var6 = var0.toLowerCase();
               if(var5.startsWith(var6)) {
                  var7 = true;
                  return var7;
               }
            }

            ++var3;
         }
      }

      var7 = false;
      return var7;
   }

   public static final boolean isLeapYear(int var0) {
      boolean var1;
      if(var0 % 100 == 0) {
         if(var0 % 400 == 0) {
            var1 = true;
         } else {
            var1 = false;
         }
      } else if(var0 % 4 == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static boolean isSDCardAvailable() {
      return Environment.getExternalStorageDirectory().canRead();
   }

   public static boolean isSameDay(Date var0, Date var1, TimeZone var2) {
      Calendar var3 = Calendar.getInstance(var2);
      var3.setTime(var0);
      Calendar var4 = Calendar.getInstance(var2);
      var4.setTime(var1);
      int var5 = var3.get(5);
      int var6 = var4.get(5);
      boolean var11;
      if(var5 == var6) {
         int var7 = var3.get(2);
         int var8 = var4.get(2);
         if(var7 == var8) {
            int var9 = var3.get(1);
            int var10 = var4.get(1);
            if(var9 == var10) {
               var11 = true;
               return var11;
            }
         }
      }

      var11 = false;
      return var11;
   }

   public static boolean isTransportLevelError(Z7ErrorCode var0) {
      Z7ErrorCode var1 = Z7ErrorCode.Z7_ERR_INTERNAL_ERROR;
      boolean var10;
      if(var0 != var1) {
         Z7ErrorCode var2 = Z7ErrorCode.Z7_ERR_BAD_REQUEST;
         if(var0 != var2) {
            Z7ErrorCode var3 = Z7ErrorCode.Z7_ERR_BAD_RELAY_NEGOTIATION;
            if(var0 != var3) {
               Z7ErrorCode var4 = Z7ErrorCode.Z7_ERR_ENDPOINT_NOT_FOUND;
               if(var0 != var4) {
                  Z7ErrorCode var5 = Z7ErrorCode.Z7_ERR_SEND_FAILED;
                  if(var0 != var5) {
                     Z7ErrorCode var6 = Z7ErrorCode.Z7_ERR_SEND_TIMEDOUT;
                     if(var0 != var6) {
                        Z7ErrorCode var7 = Z7ErrorCode.Z7_ERR_ENDPOINT_DOWN;
                        if(var0 != var7) {
                           Z7ErrorCode var8 = Z7ErrorCode.Z7_ERR_CONNECT_FAILED;
                           if(var0 != var8) {
                              Z7ErrorCode var9 = Z7ErrorCode.Z7_ERR_SERVER_BUSY;
                              if(var0 != var9) {
                                 var10 = false;
                                 return var10;
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      var10 = true;
      return var10;
   }

   public static boolean isValidAddresses(String var0) {
      boolean var8;
      if(var0.length() != 0) {
         boolean var1 = false;
         StringTokenizer var2 = new StringTokenizer(var0, ";,");

         while(true) {
            if(var2.hasMoreTokens()) {
               String var3 = var2.nextToken().trim();
               int var4 = var3.indexOf(60);
               if(var4 >= 0) {
                  int var5 = var3.indexOf(62);
                  int var6 = var4 + 5;
                  if(var5 > var6) {
                     int var7 = var4 + 1;
                     var3 = var3.substring(var7, var5);
                  }
               }

               if(var3.length() == 0) {
                  continue;
               }

               var1 = true;
               if(isValidEmailAddress(var3)) {
                  continue;
               }

               var8 = false;
               break;
            }

            var8 = var1;
            break;
         }
      } else {
         var8 = true;
      }

      return var8;
   }

   public static boolean isValidDomainName(String var0) {
      boolean var1;
      if(var0 != null && !"".equals(var0)) {
         int var2 = var0.length() - 1;
         char var3 = var0.charAt(0);
         char var4 = var0.charAt(var2);
         if(var3 != 46 && var4 != 46) {
            if(var3 != 45 && var4 != 45) {
               int var5 = 0;

               while(true) {
                  if(var5 > var2) {
                     var1 = true;
                     break;
                  }

                  if(!legalCharForDomain(var0.charAt(var5))) {
                     var1 = false;
                     break;
                  }

                  ++var5;
               }
            } else {
               var1 = false;
            }
         } else {
            var1 = false;
         }
      } else {
         var1 = false;
      }

      return var1;
   }

   public static boolean isValidEmailAddress(String var0) {
      boolean var1;
      if(var0.length() > 255) {
         var1 = false;
      } else {
         byte var2 = 0;
         byte var3 = 64;

         boolean var14;
         try {
            label64: {
               int var4 = var0.indexOf(var3);
               String var5 = var0.substring(var2, var4);
               int var6 = var5.length() + 1;
               String var7 = var0.substring(var6);
               String var8 = var5.trim();
               String var9 = var7.trim();
               if(var8.length() == 0) {
                  var1 = false;
                  return var1;
               }

               if(var8.charAt(0) != 46) {
                  int var10 = var8.length() - 1;
                  if(var8.charAt(var10) != 46) {
                     int var11 = 0;

                     while(true) {
                        int var12 = var8.length();
                        if(var11 >= var12) {
                           var14 = isValidDomainName(var9);
                           break label64;
                        }

                        if(!legalCharForLocal(var8.charAt(var11))) {
                           int var13 = var11 - 1;
                           if(var8.charAt(var13) != 92) {
                              var1 = false;
                              return var1;
                           }
                        }

                        ++var11;
                     }
                  }
               }

               var1 = false;
               return var1;
            }
         } catch (StringIndexOutOfBoundsException var16) {
            var1 = false;
            return var1;
         }

         if(!var14) {
            var1 = false;
         } else {
            var1 = true;
         }
      }

      return var1;
   }

   public static boolean legalCharForDomain(char var0) {
      boolean var1;
      if(!isCharOrDigit(var0) && var0 != 45 && var0 != 46) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public static boolean legalCharForLocal(char var0) {
      boolean var1;
      if(!legalCharForDomain(var0) && "!#$%&\\\'*+/=?\'^_`{|}~\\".indexOf(var0) == -1) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public static void moveFile(File var0, File var1, boolean var2) throws IOException {
      copyFile(var0, var1, var2);
      boolean var3 = var0.delete();
   }

   public static Date parseDate(String var0) {
      Date var1;
      if(var0.length() != 13) {
         var1 = null;
      } else {
         Calendar var2 = Calendar.getInstance(getDeviceTimeZone());
         int var3 = Integer.parseInt(var0.substring(0, 4));
         var2.set(1, var3);
         int var4 = Integer.parseInt(var0.substring(4, 6)) - 1;
         var2.set(2, var4);
         int var5 = Integer.parseInt(var0.substring(6, 8));
         var2.set(5, var5);
         int var6 = Integer.parseInt(var0.substring(9, 11));
         var2.set(11, var6);
         int var7 = Integer.parseInt(var0.substring(11, 13));
         var2.set(12, var7);
         var1 = var2.getTime();
      }

      return var1;
   }

   public static String removeInvalidCharForSql(String var0) {
      return var0.replace("?", "").replace("\'", "").replace("\"", "").replace("%", "").trim();
   }

   public static void resetCalTime(Calendar var0) {
      setCalTime(var0, 0, 0, 0, 0);
   }

   public static final Thread runThread(Runnable var0, String var1) {
      Thread var2 = new Thread(var0, var1);
      var2.start();
      return var2;
   }

   public static void safeCloseChannel(Channel var0) {
      if(var0 != null) {
         try {
            var0.close();
         } catch (IOException var3) {
            int var2 = Log.v("Utils", "Failed to close channel");
         }
      }
   }

   public static void setCalTime(Calendar var0, int var1, int var2, int var3, int var4) {
      var0.set(11, var1);
      var0.set(12, var2);
      var0.set(13, var3);
      var0.set(14, var4);
   }

   public static void signalTask(Object var0) {
      synchronized(var0) {
         var0.notifyAll();
      }
   }
}
