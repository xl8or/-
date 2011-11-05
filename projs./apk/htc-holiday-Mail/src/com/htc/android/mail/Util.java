package com.htc.android.mail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.FileUtils;
import android.os.StatFs;
import android.os.FileUtils.FileStatus;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.text.format.Time;
import com.htc.android.mail.ByteString;
import com.htc.android.mail.Mail;
import com.htc.android.mail.MailCommon;
import com.htc.android.mail.MailProvider;
import com.htc.android.mail.ll;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Util {

   private static byte[] Big5_arr = new byte[]{(byte)198, (byte)161, (byte)198, (byte)162, (byte)198, (byte)163, (byte)198, (byte)164, (byte)198, (byte)165, (byte)198, (byte)166, (byte)198, (byte)167, (byte)198, (byte)168, (byte)198, (byte)169, (byte)198, (byte)170, (byte)198, (byte)171, (byte)198, (byte)172, (byte)198, (byte)173, (byte)198, (byte)174, (byte)198, (byte)175, (byte)198, (byte)176, (byte)198, (byte)177, (byte)198, (byte)178, (byte)198, (byte)179, (byte)198, (byte)180, (byte)198, (byte)181, (byte)198, (byte)182, (byte)198, (byte)183, (byte)198, (byte)184, (byte)198, (byte)185, (byte)198, (byte)186, (byte)198, (byte)187, (byte)198, (byte)188, (byte)198, (byte)189, (byte)198, (byte)190, (byte)198, (byte)191, (byte)198, (byte)192, (byte)198, (byte)193, (byte)198, (byte)194, (byte)198, (byte)195, (byte)198, (byte)196, (byte)198, (byte)197, (byte)198, (byte)198, (byte)198, (byte)199, (byte)198, (byte)200, (byte)198, (byte)201, (byte)198, (byte)202, (byte)198, (byte)203, (byte)198, (byte)204, (byte)198, (byte)205, (byte)198, (byte)206, (byte)198, (byte)207, (byte)198, (byte)208, (byte)198, (byte)209, (byte)198, (byte)210, (byte)198, (byte)211, (byte)198, (byte)212, (byte)198, (byte)213, (byte)198, (byte)214, (byte)198, (byte)215, (byte)198, (byte)216, (byte)198, (byte)217, (byte)198, (byte)218, (byte)198, (byte)219, (byte)198, (byte)220, (byte)198, (byte)221, (byte)198, (byte)224, (byte)198, (byte)225, (byte)198, (byte)226, (byte)198, (byte)227, (byte)198, (byte)228, (byte)198, (byte)229, (byte)198, (byte)230, (byte)198, (byte)231, (byte)198, (byte)232, (byte)198, (byte)233, (byte)198, (byte)234, (byte)198, (byte)235, (byte)198, (byte)236, (byte)198, (byte)237, (byte)198, (byte)238, (byte)198, (byte)239, (byte)198, (byte)240, (byte)198, (byte)241, (byte)198, (byte)242, (byte)198, (byte)243, (byte)198, (byte)244, (byte)198, (byte)245, (byte)198, (byte)246, (byte)198, (byte)247, (byte)198, (byte)248, (byte)198, (byte)249, (byte)198, (byte)250, (byte)198, (byte)251, (byte)198, (byte)252, (byte)198, (byte)253, (byte)198, (byte)254, (byte)199, (byte)64, (byte)199, (byte)65, (byte)199, (byte)66, (byte)199, (byte)67, (byte)199, (byte)68, (byte)199, (byte)69, (byte)199, (byte)70, (byte)199, (byte)71, (byte)199, (byte)72, (byte)199, (byte)73, (byte)199, (byte)74, (byte)199, (byte)75, (byte)199, (byte)76, (byte)199, (byte)77, (byte)199, (byte)78, (byte)199, (byte)79, (byte)199, (byte)80, (byte)199, (byte)81, (byte)199, (byte)82, (byte)199, (byte)83, (byte)199, (byte)84, (byte)199, (byte)85, (byte)199, (byte)86, (byte)199, (byte)87, (byte)199, (byte)88, (byte)199, (byte)89, (byte)199, (byte)90, (byte)199, (byte)91, (byte)199, (byte)92, (byte)199, (byte)93, (byte)199, (byte)94, (byte)199, (byte)95, (byte)199, (byte)96, (byte)199, (byte)97, (byte)199, (byte)98, (byte)199, (byte)99, (byte)199, (byte)100, (byte)199, (byte)101, (byte)199, (byte)102, (byte)199, (byte)103, (byte)199, (byte)104, (byte)199, (byte)105, (byte)199, (byte)106, (byte)199, (byte)107, (byte)199, (byte)108, (byte)199, (byte)109, (byte)199, (byte)110, (byte)199, (byte)111, (byte)199, (byte)112, (byte)199, (byte)113, (byte)199, (byte)114, (byte)199, (byte)115, (byte)199, (byte)116, (byte)199, (byte)117, (byte)199, (byte)118, (byte)199, (byte)119, (byte)199, (byte)120, (byte)199, (byte)121, (byte)199, (byte)122, (byte)199, (byte)123, (byte)199, (byte)124, (byte)199, (byte)125, (byte)199, (byte)126, (byte)199, (byte)161, (byte)199, (byte)162, (byte)199, (byte)163, (byte)199, (byte)164, (byte)199, (byte)165, (byte)199, (byte)166, (byte)199, (byte)167, (byte)199, (byte)168, (byte)199, (byte)169, (byte)199, (byte)170, (byte)199, (byte)171, (byte)199, (byte)172, (byte)199, (byte)173, (byte)199, (byte)174, (byte)199, (byte)175, (byte)199, (byte)176, (byte)199, (byte)177, (byte)199, (byte)178, (byte)199, (byte)179, (byte)199, (byte)180, (byte)199, (byte)181, (byte)199, (byte)182, (byte)199, (byte)183, (byte)199, (byte)184, (byte)199, (byte)185, (byte)199, (byte)186, (byte)199, (byte)187, (byte)199, (byte)188, (byte)199, (byte)189, (byte)199, (byte)190, (byte)199, (byte)191, (byte)199, (byte)192, (byte)199, (byte)193, (byte)199, (byte)194, (byte)199, (byte)195, (byte)199, (byte)196, (byte)199, (byte)197, (byte)199, (byte)198, (byte)199, (byte)199, (byte)199, (byte)200, (byte)199, (byte)201, (byte)199, (byte)202, (byte)199, (byte)203, (byte)199, (byte)204, (byte)199, (byte)205, (byte)199, (byte)206, (byte)199, (byte)207, (byte)199, (byte)208, (byte)199, (byte)209, (byte)199, (byte)210, (byte)199, (byte)211, (byte)199, (byte)212, (byte)199, (byte)213, (byte)199, (byte)214, (byte)199, (byte)215, (byte)199, (byte)216, (byte)199, (byte)217, (byte)199, (byte)218, (byte)199, (byte)219, (byte)199, (byte)220, (byte)199, (byte)221, (byte)199, (byte)222, (byte)199, (byte)223, (byte)199, (byte)224, (byte)199, (byte)225, (byte)199, (byte)226, (byte)199, (byte)227, (byte)199, (byte)228, (byte)199, (byte)229, (byte)199, (byte)230, (byte)199, (byte)231, (byte)199, (byte)232, (byte)199, (byte)233, (byte)199, (byte)234, (byte)199, (byte)235, (byte)199, (byte)236, (byte)199, (byte)237, (byte)199, (byte)238, (byte)199, (byte)239, (byte)199, (byte)240, (byte)199, (byte)241, (byte)199, (byte)242};
   private static String Big5_str = null;
   public static Locale CUR_LOCALE = null;
   private static final boolean DEBUG = Mail.MAIL_DEBUG;
   public static int EXTRA_SPACE_FOR_MAIL_ITEM = 0;
   public static final int FORWARD_MAIL = 2;
   private static byte[] JP_Unicode = new byte[]{(byte)36, (byte)96, (byte)36, (byte)97, (byte)36, (byte)98, (byte)36, (byte)99, (byte)36, (byte)100, (byte)36, (byte)101, (byte)36, (byte)102, (byte)36, (byte)103, (byte)36, (byte)104, (byte)36, (byte)105, (byte)36, (byte)116, (byte)36, (byte)117, (byte)36, (byte)118, (byte)36, (byte)119, (byte)36, (byte)120, (byte)36, (byte)121, (byte)36, (byte)122, (byte)36, (byte)123, (byte)36, (byte)124, (byte)36, (byte)125, (byte)33, (byte)112, (byte)33, (byte)113, (byte)33, (byte)114, (byte)33, (byte)115, (byte)33, (byte)116, (byte)33, (byte)117, (byte)33, (byte)118, (byte)33, (byte)119, (byte)33, (byte)120, (byte)33, (byte)121, (byte)47, (byte)2, (byte)47, (byte)3, (byte)47, (byte)5, (byte)47, (byte)7, (byte)47, (byte)12, (byte)47, (byte)13, (byte)47, (byte)14, (byte)47, (byte)19, (byte)47, (byte)22, (byte)47, (byte)25, (byte)47, (byte)27, (byte)47, (byte)34, (byte)47, (byte)39, (byte)47, (byte)46, (byte)47, (byte)51, (byte)47, (byte)52, (byte)47, (byte)53, (byte)47, (byte)57, (byte)47, (byte)58, (byte)47, (byte)65, (byte)47, (byte)70, (byte)47, (byte)103, (byte)47, (byte)104, (byte)47, (byte)161, (byte)47, (byte)170, (byte)0, (byte)168, (byte)255, (byte)62, (byte)48, (byte)253, (byte)48, (byte)254, (byte)48, (byte)157, (byte)48, (byte)158, (byte)48, (byte)5, (byte)48, (byte)6, (byte)48, (byte)7, (byte)48, (byte)252, (byte)255, (byte)59, (byte)255, (byte)61, (byte)39, (byte)61, (byte)48, (byte)65, (byte)48, (byte)66, (byte)48, (byte)67, (byte)48, (byte)68, (byte)48, (byte)69, (byte)48, (byte)70, (byte)48, (byte)71, (byte)48, (byte)72, (byte)48, (byte)73, (byte)48, (byte)74, (byte)48, (byte)75, (byte)48, (byte)76, (byte)48, (byte)77, (byte)48, (byte)78, (byte)48, (byte)79, (byte)48, (byte)80, (byte)48, (byte)81, (byte)48, (byte)82, (byte)48, (byte)83, (byte)48, (byte)84, (byte)48, (byte)85, (byte)48, (byte)86, (byte)48, (byte)87, (byte)48, (byte)88, (byte)48, (byte)89, (byte)48, (byte)90, (byte)48, (byte)91, (byte)48, (byte)92, (byte)48, (byte)93, (byte)48, (byte)94, (byte)48, (byte)95, (byte)48, (byte)96, (byte)48, (byte)97, (byte)48, (byte)98, (byte)48, (byte)99, (byte)48, (byte)100, (byte)48, (byte)101, (byte)48, (byte)102, (byte)48, (byte)103, (byte)48, (byte)104, (byte)48, (byte)105, (byte)48, (byte)106, (byte)48, (byte)107, (byte)48, (byte)108, (byte)48, (byte)109, (byte)48, (byte)110, (byte)48, (byte)111, (byte)48, (byte)112, (byte)48, (byte)113, (byte)48, (byte)114, (byte)48, (byte)115, (byte)48, (byte)116, (byte)48, (byte)117, (byte)48, (byte)118, (byte)48, (byte)119, (byte)48, (byte)120, (byte)48, (byte)121, (byte)48, (byte)122, (byte)48, (byte)123, (byte)48, (byte)124, (byte)48, (byte)125, (byte)48, (byte)126, (byte)48, (byte)127, (byte)48, (byte)128, (byte)48, (byte)129, (byte)48, (byte)130, (byte)48, (byte)131, (byte)48, (byte)132, (byte)48, (byte)133, (byte)48, (byte)134, (byte)48, (byte)135, (byte)48, (byte)136, (byte)48, (byte)137, (byte)48, (byte)138, (byte)48, (byte)139, (byte)48, (byte)140, (byte)48, (byte)141, (byte)48, (byte)142, (byte)48, (byte)143, (byte)48, (byte)144, (byte)48, (byte)145, (byte)48, (byte)146, (byte)48, (byte)147, (byte)48, (byte)161, (byte)48, (byte)162, (byte)48, (byte)163, (byte)48, (byte)164, (byte)48, (byte)165, (byte)48, (byte)166, (byte)48, (byte)167, (byte)48, (byte)168, (byte)48, (byte)169, (byte)48, (byte)170, (byte)48, (byte)171, (byte)48, (byte)172, (byte)48, (byte)173, (byte)48, (byte)174, (byte)48, (byte)175, (byte)48, (byte)176, (byte)48, (byte)177, (byte)48, (byte)178, (byte)48, (byte)179, (byte)48, (byte)180, (byte)48, (byte)181, (byte)48, (byte)182, (byte)48, (byte)183, (byte)48, (byte)184, (byte)48, (byte)185, (byte)48, (byte)186, (byte)48, (byte)187, (byte)48, (byte)188, (byte)48, (byte)189, (byte)48, (byte)190, (byte)48, (byte)191, (byte)48, (byte)192, (byte)48, (byte)193, (byte)48, (byte)194, (byte)48, (byte)195, (byte)48, (byte)196, (byte)48, (byte)197, (byte)48, (byte)198, (byte)48, (byte)199, (byte)48, (byte)200, (byte)48, (byte)201, (byte)48, (byte)202, (byte)48, (byte)203, (byte)48, (byte)204, (byte)48, (byte)205, (byte)48, (byte)206, (byte)48, (byte)207, (byte)48, (byte)208, (byte)48, (byte)209, (byte)48, (byte)210, (byte)48, (byte)211, (byte)48, (byte)212, (byte)48, (byte)213, (byte)48, (byte)214, (byte)48, (byte)215, (byte)48, (byte)216, (byte)48, (byte)217, (byte)48, (byte)218, (byte)48, (byte)219, (byte)48, (byte)220, (byte)48, (byte)221, (byte)48, (byte)222, (byte)48, (byte)223, (byte)48, (byte)224, (byte)48, (byte)225, (byte)48, (byte)226, (byte)48, (byte)227, (byte)48, (byte)228, (byte)48, (byte)229, (byte)48, (byte)230, (byte)48, (byte)231, (byte)48, (byte)232, (byte)48, (byte)233, (byte)48, (byte)234, (byte)48, (byte)235, (byte)48, (byte)236, (byte)48, (byte)237, (byte)48, (byte)238, (byte)48, (byte)239, (byte)48, (byte)240, (byte)48, (byte)241, (byte)48, (byte)242, (byte)48, (byte)243, (byte)48, (byte)244, (byte)48, (byte)245, (byte)48, (byte)246};
   private static String JP_str = null;
   public static final int REPLY_MAIL = 1;
   private static final String TAG = "Util";
   public static final int TRY_ACTION = 16;
   public static final int TRY_FORWARD_MAIL = 131072;
   public static final int TRY_FORWARD_SHIFT = 17;
   public static final int TRY_REPLY_MAIL = 65536;
   public static final int TRY_REPLY_SHIFT = 16;
   private static final String bufferFileNameForMailAP = "/data/data/com.htc.android.mail/bufferFileForMailAP";
   public static Calendar cal = Calendar.getInstance();
   private static boolean getMoreSpaceForMailAP = 0;
   private static boolean initBufferFileForMailAP = 0;
   private static String mCurSystemTimeFormat = null;
   public static Time mNowtime = new Time();
   private static CharSequence sDailyFormat;
   private static CharSequence sDailyFormat24;
   private static final long sDailyMillis = 86400000L;
   private static CharSequence sDateFormat;
   private static CharSequence sDateFormatMDY;
   private static CharSequence sDateTimeWeekFormat;
   private static CharSequence sDateTimeWeekFormatWith24;
   private static CharSequence sFullTimeFormat;
   private static CharSequence sMonthlyFormat;
   private static final long sMonthlyMillis = 2419200000L;
   private static CharSequence sWeeklyFormat;
   private static final long sWeeklyMillis = 604800000L;
   private static CharSequence sYearlyFormat;
   private static final long sYestodayMillis = 86400000L;
   public static long today;
   public static Calendar yearStart = Calendar.getInstance();
   public static long yesterday;


   public Util() {}

   public static final String FixBig5UnicodeForJP(String var0) {
      String var1 = var0;
      String var12;
      if(Big5_str == null) {
         try {
            byte[] var2 = Big5_arr;
            Big5_str = new String(var2, "Big5");
         } catch (UnsupportedEncodingException var15) {
            var12 = var0;
            return var12;
         }
      }

      if(JP_str == null) {
         try {
            byte[] var3 = JP_Unicode;
            JP_str = new String(var3, "unicode");
         } catch (UnsupportedEncodingException var14) {
            var12 = var0;
            return var12;
         }
      }

      int var4 = 0;

      while(true) {
         int var5 = var1.length();
         if(var4 >= var5) {
            var12 = var1;
            return var12;
         }

         String var6 = Big5_str;
         char var7 = var1.charAt(var4);
         int var8 = var6.indexOf(var7);
         if(var8 != -1) {
            char var9 = Big5_str.charAt(var8);
            char var10 = JP_str.charAt(var8);
            var1 = var1.replace(var9, var10);
         }

         ++var4;
      }
   }

   public static final boolean FixBig5UnicodeForJP(byte[] var0) {
      int var1 = Big5_arr.length;
      int var2 = 0;

      while(true) {
         int var3 = var0.length;
         if(var2 >= var3) {
            return true;
         }

         int var4 = 0;

         while(true) {
            int var5 = var1 - 1;
            if(var4 < var5) {
               label30: {
                  byte var6 = var0[var2];
                  byte var7 = Big5_arr[var4];
                  if(var6 == var7) {
                     int var8 = var2 + 1;
                     byte var9 = var0[var8];
                     byte[] var10 = Big5_arr;
                     int var11 = var4 + 1;
                     byte var12 = var10[var11];
                     if(var9 == var12) {
                        byte var13 = JP_Unicode[var4];
                        var0[var2] = var13;
                        int var14 = var2 + 1;
                        byte[] var15 = JP_Unicode;
                        int var16 = var4 + 1;
                        byte var17 = var15[var16];
                        var0[var14] = var17;
                        break label30;
                     }
                  }

                  var4 += 2;
                  continue;
               }
            }

            ++var2;
            break;
         }
      }
   }

   public static final String GenBoundary(int var0) {
      StringBuilder var1 = (new StringBuilder()).append("----=_Part_");
      String var2 = String.valueOf(var0);
      StringBuilder var3 = var1.append(var2).append("_");
      String var4 = String.valueOf(System.currentTimeMillis());
      return var3.append(var4).toString();
   }

   public static void checkAvailableSpace() {
      StatFs var0 = new StatFs("/data/data/com.htc.android.mail/");
      long var1 = (long)var0.getAvailableBlocks();
      long var3 = (long)var0.getBlockSize();
      long var5 = var1 * var3;
      FileStatus var7 = new FileStatus();
      boolean var8 = FileUtils.getFileStatus("/data/data/com.htc.android.mail/bufferFileForMailAP", var7);
      int var9 = (int)var7.size;
      if(!var8) {
         long var26;
         if(var5 < 1048576L) {
            var26 = var5;
         } else {
            var26 = 1048576L;
         }

         if(var26 > 0L) {
            if(DEBUG) {
               String var12 = "Available space is " + var5 + " bytes and the buffuer file does not exist, create one for size: " + var26 + " bytes";
               ll.d("Util", var12);
            }

            initBufferFileForMailAP(var26);
         }
      } else if(var5 < 65536L) {
         if(DEBUG) {
            String var15 = "Available space is less than 64K(" + var5 + " bytes) and the buffuer file exists, delete it to get more space...";
            ll.d("Util", var15);
         }

         getMoreSpaceForMailAP();
         long var16 = 1048576L;
      } else if((long)var9 < 1048576L) {
         long var17 = (long)var9 + var5 - 65536L;
         long var19;
         if(1048576L > var17) {
            var19 = (long)var9 + var5 - 65536L;
         } else {
            var19 = 1048576L;
         }

         if(DEBUG) {
            String var21 = "Available space is " + var5 + " bytes and the buffuer file size is " + var9 + " bytes, delete it and create a larger one(" + var19 + ")bytes";
            ll.d("Util", var21);
         }

         getMoreSpaceForMailAP();
         initBufferFileForMailAP(var19);
      } else {
         long var24 = 1048576L;
      }
   }

   public static boolean checkIsSupportType(Context var0, String var1, String var2, String var3) {
      boolean var4;
      if(var2 != null && var3 != null && var0 != null) {
         if(var1 == null) {
            var1 = "";
         }

         Intent var5 = new Intent(var1);
         PackageManager var6 = var0.getPackageManager();
         Uri var7 = Uri.fromParts(var2, "", (String)null);
         var5.setDataAndType(var7, var3);
         ResolveInfo var9 = var6.resolveActivity(var5, 65536);
         if(var9 != null) {
            if(DEBUG) {
               StringBuilder var10 = (new StringBuilder()).append(" is support.");
               String var11 = var9.toString();
               String var12 = var10.append(var11).toString();
               ll.d("Util", var12);
            }

            var4 = true;
         } else {
            if(DEBUG) {
               ll.d("Util", " not support.");
            }

            var4 = false;
         }
      } else {
         var4 = false;
      }

      return var4;
   }

   public static void clearSignaturePref(Context var0) {
      if(var0 != null) {
         boolean var1 = var0.getSharedPreferences("cusSignature", 0).edit().clear().commit();
      }
   }

   public static void createBufferFile() {
      if(DEBUG) {
         ll.d("Util", "> createBufferFile()");
      }

      try {
         String var0 = "/data/data/com.htc.android.mail/bufferFile";
         if(DEBUG) {
            String var1 = "bufferFile: " + var0;
            ll.d("Util", var1);
         }

         File var2 = new File(var0);
         if(!var2.exists()) {
            if(DEBUG) {
               ll.d("Util", "buffer file not found, create new one");
            }

            FileOutputStream var3 = new FileOutputStream(var2);
            byte[] var4 = new byte[819200];
            var3.write(var4);
            var3.flush();
            var3.close();
         } else if(DEBUG) {
            ll.d("Util", "buffer file exist, need not create");
         }
      } catch (Exception var6) {
         ll.e("Util", "createBufferFile() Exception");
         var6.printStackTrace();
      }

      if(DEBUG) {
         ll.d("Util", "< createBufferFile()");
      }
   }

   public static void deleteBufferFile() {
      if(DEBUG) {
         ll.d("Util", "> deleteBufferFile()");
      }

      try {
         File var0 = new File("/data/data/com.htc.android.mail/bufferFile");
         if(var0.exists()) {
            if(var0.delete() == 1) {
               if(DEBUG) {
                  ll.d("Util", "delete buffer file success");
               }
            } else if(DEBUG) {
               ll.d("Util", "delete buffer file failed");
            }
         } else if(DEBUG) {
            ll.d("Util", "buffer file doesn\'t exist");
         }
      } catch (Exception var2) {
         ll.e("Util", "deleteBufferFile() Exception");
         var2.printStackTrace();
      }

      if(DEBUG) {
         ll.d("Util", "< deleteBufferFile()");
      }
   }

   public static int getAccountCountPref(Context var0) {
      int var1;
      if(var0 == null) {
         var1 = 0;
      } else {
         int var2 = var0.getSharedPreferences("account", 0).getInt("totalCount", 0);
         if(DEBUG) {
            String var3 = "get account count:" + var2;
            ll.d("Util", var3);
         }

         var1 = var2;
      }

      return var1;
   }

   public static long getAccountLastAccessMailboxId(Context var0, long var1) {
      long var3;
      if(var0 == null) {
         var3 = 0L;
      } else {
         SharedPreferences var5 = var0.getSharedPreferences("account", 0);
         String var6 = var1 + "lastMailboxId";
         long var7 = var5.getLong(var6, 65535L);
         if(DEBUG) {
            String var9 = "get Last access mailbox id" + var7;
            ll.d("Util", var9);
         }

         var3 = var7;
      }

      return var3;
   }

   public static String getAccountLastMove2MailboxId(Context var0, long var1) {
      String var3;
      if(var0 == null) {
         var3 = null;
      } else {
         SharedPreferences var4 = var0.getSharedPreferences("account", 0);
         StringBuilder var5 = (new StringBuilder()).append("Move2MailboxId");
         String var6 = String.valueOf(var1);
         String var7 = var5.append(var6).toString();
         String var8 = var4.getString(var7, (String)null);
         if(DEBUG) {
            String var9 = "get Last access mailbox id" + var8;
            ll.d("Util", var9);
         }

         var3 = var8;
      }

      return var3;
   }

   public static int getAccountPowerSavingPref(Context var0, long var1) {
      int var3;
      if(var0 == null) {
         var3 = 1;
      } else {
         SharedPreferences var4 = var0.getSharedPreferences("account", 0);
         String var5 = "powerSaving" + var1;
         int var6 = var4.getInt(var5, 0);
         if(DEBUG) {
            String var7 = "account:" + var1 + ",power saving:" + var6;
            ll.d("Util", var7);
         }

         var3 = var6;
      }

      return var3;
   }

   public static String getBladeDateFormat(Context var0) {
      String var1;
      if(mCurSystemTimeFormat != null) {
         var1 = mCurSystemTimeFormat;
      } else {
         String var2 = "EE,";
         String var3 = ", EE";
         mCurSystemTimeFormat = android.provider.Settings.System.getString(var0.getContentResolver(), "date_format");
         if(mCurSystemTimeFormat.startsWith(var2)) {
            String var4 = mCurSystemTimeFormat;
            int var5 = var2.length();
            mCurSystemTimeFormat = var4.substring(var5);
         }

         if(mCurSystemTimeFormat.endsWith(var3)) {
            int var6 = mCurSystemTimeFormat.length();
            int var7 = var3.length();
            int var8 = var6 - var7;
            mCurSystemTimeFormat = mCurSystemTimeFormat.substring(0, var8);
         }

         var1 = mCurSystemTimeFormat;
      }

      return var1;
   }

   public static Locale getCurrentLocale(Context var0) {
      if(CUR_LOCALE == null && var0 != null) {
         CUR_LOCALE = var0.getResources().getConfiguration().locale;
      }

      return CUR_LOCALE;
   }

   public static final CharSequence getDateTimeWeekFormat(Context var0, long var1) {
      if(sDateTimeWeekFormat == null || sDateTimeWeekFormatWith24 == null) {
         init(var0);
      }

      CharSequence var3;
      if(DateFormat.is24HourFormat(var0)) {
         var3 = DateFormat.format(sDateTimeWeekFormatWith24, var1);
      } else {
         var3 = DateFormat.format(sDateTimeWeekFormat, var1);
      }

      return var3;
   }

   public static final CharSequence getFullTimeString(Context var0, long var1) {
      if(sFullTimeFormat == null) {
         init(var0);
      }

      return DateFormat.format(sFullTimeFormat, var1);
   }

   public static String getIMSIFromPref(Context var0) {
      String var1;
      if(var0 == null) {
         var1 = "";
      } else {
         String var2 = var0.getSharedPreferences("siminfo", 0).getString("lastIMSI", "");
         if(DEBUG) {
            String var3 = "get imsi:" + var2;
            ll.d("Util", var3);
         }

         var1 = var2;
      }

      return var1;
   }

   public static int getKeepTempMessage(Context var0, String var1) {
      int var2;
      if(var0 == null) {
         var2 = -1;
      } else {
         int var3 = var0.getSharedPreferences("TempMessage", 0).getInt(var1, 0);
         if(DEBUG) {
            String var4 = "get tmp message " + var1 + ", count:" + var3;
            ll.d("Util", var4);
         }

         var2 = var3;
      }

      return var2;
   }

   public static final CharSequence getMonthString(Context var0, long var1) {
      return DateFormat.format(var0.getText(2131362576), var1);
   }

   public static void getMoreSpaceForMailAP() {
      if(!getMoreSpaceForMailAP) {
         getMoreSpaceForMailAP = (boolean)1;
         if(DEBUG) {
            ll.d("Util", "> getMoreSpaceForMailAP()");
         }

         try {
            File var0 = new File("/data/data/com.htc.android.mail/bufferFileForMailAP");
            if(var0.exists()) {
               if(var0.delete() == 1) {
                  if(DEBUG) {
                     ll.d("Util", "delete buffer file success");
                  }
               } else if(DEBUG) {
                  ll.d("Util", "delete buffer file failed");
               }
            } else if(DEBUG) {
               ll.d("Util", "buffer file doesn\'t exist");
            }
         } catch (Exception var2) {
            ll.e("Util", "getMoreSpaceForMailAP() Exception");
            var2.printStackTrace();
         }

         getMoreSpaceForMailAP = (boolean)0;
         ll.d("Util", "< getMoreSpaceForMailAP()");
      }
   }

   public static final CharSequence getNewMonthString(long var0) {
      return DateFormat.format("MMM dd", var0);
   }

   public static final CharSequence getNewTimeWithYearString(long var0) {
      return DateFormat.format("MM/dd/yy", var0);
   }

   public static final CharSequence getRelativeTimeSpanString(Context var0, long var1) {
      if(sDailyFormat == null) {
         init(var0);
      }

      long var3 = System.currentTimeMillis() - var1;
      CharSequence var5;
      if(var3 < 86400000L) {
         var5 = sDailyFormat;
      } else if(var3 < 604800000L) {
         var5 = sWeeklyFormat;
      } else if(var3 < 2419200000L) {
         var5 = sMonthlyFormat;
      } else {
         var5 = sYearlyFormat;
      }

      return DateFormat.format(var5, var1);
   }

   public static final CharSequence getRelativeTimeSpanString2(Context var0, long var1, boolean var3) {
      if(sDailyFormat == null) {
         init(var0);
      }

      long var4 = System.currentTimeMillis();
      cal.setTimeInMillis(var1);
      mNowtime.set(var4);
      int var6 = mNowtime.year;
      int var7 = mNowtime.weekDay;
      Time var8 = mNowtime;
      int var9 = mNowtime.monthDay;
      int var10 = mNowtime.month;
      int var11 = mNowtime.year;
      var8.set(0, 0, 0, var9, var10, var11);
      today = mNowtime.toMillis((boolean)0);
      yesterday = today - 86400000L;
      Calendar var12 = yearStart;
      var12.set(var6, 0, 1, 0, 0, 0);
      long var14 = yearStart.getTimeInMillis();
      long var16 = var4 / 86400000L;
      boolean var18 = false;
      long var19 = today;
      CharSequence var47;
      Object var48;
      if(var1 >= var19) {
         if(var3 == null) {
            mNowtime.set(var1);
            int var22 = mNowtime.hour % 12;
            int var23 = mNowtime.minute;
            if(var22 == 0) {
               var22 = 12;
            }

            Locale var24 = var0.getResources().getConfiguration().locale;
            StringBuilder var49;
            if(!var24.toString().equalsIgnoreCase("zh_tw") && !var24.toString().equalsIgnoreCase("zh_cn")) {
               var49 = new StringBuilder();
               int var31;
               if(var22 < 10) {
                  var31 = var22;
               } else {
                  var31 = var22;
               }

               StringBuilder var32 = var49.append(var31).append(":");
               Object var33;
               if(var23 < 10) {
                  var33 = "0" + var23;
               } else {
                  var33 = Integer.valueOf(var23);
               }

               StringBuilder var34 = var32.append(var33).append(" ");
               String var35 = DateUtils.getAMPMString(cal.get(9)).toUpperCase();
               var48 = var34.append(var35).toString();
            } else {
               StringBuilder var25 = new StringBuilder();
               String var26 = DateUtils.getAMPMString(cal.get(9)).toUpperCase();
               var49 = var25.append(var26).append(" ");
               int var27;
               if(var22 < 10) {
                  var27 = var22;
               } else {
                  var27 = var22;
               }

               StringBuilder var28 = var49.append(var27).append(":");
               Object var29;
               if(var23 < 10) {
                  var29 = "0" + var23;
               } else {
                  var29 = Integer.valueOf(var23);
               }

               var48 = var28.append(var29).toString();
            }

            return (CharSequence)var48;
         }

         var47 = sDailyFormat24;
      } else {
         long var37 = today;
         if(var1 < var37) {
            long var39 = yesterday;
            if(var1 >= var39) {
               var48 = var0.getText(2131362280);
               return (CharSequence)var48;
            }
         }

         if(var7 != -1) {
            long var42 = (long)var7;
            if((var16 - var42 + 1L) * 86400000L < var1) {
               mNowtime.set(var1);
               var48 = DateUtils.getDayOfWeekString(mNowtime.weekDay + 1, 10);
               return (CharSequence)var48;
            }
         }

         if(var1 >= var14) {
            mNowtime.set(var1);
            long var45 = mNowtime.toMillis((boolean)0);
            var48 = DateFormat.format("MMM dd", var45);
            return (CharSequence)var48;
         }

         var47 = sYearlyFormat;
      }

      Calendar var21 = cal;
      var48 = DateFormat.format(var47, var21);
      return (CharSequence)var48;
   }

   public static final CharSequence getRelativeTimeSpanString3(Context var0, long var1) {
      if(sDailyFormat == null) {
         init(var0);
      }

      long var3 = System.currentTimeMillis() / 86400000L;
      long var5 = var1 / 86400000L;
      CharSequence var7 = null;
      long var8 = var5 + 1L;
      Object var13;
      if(var3 > var8) {
         var7 = sMonthlyFormat;
      } else {
         long var14 = var5 + 1L;
         if(var3 == var14) {
            if(Mail.debug && DEBUG) {
               String var16 = "day>yesterday" + var1 + "," + var3 + "," + var5;
               ll.d("DD", var16);
            }

            var13 = "Yesterday";
            return (CharSequence)var13;
         }

         if(var3 == var5) {
            var7 = sDailyFormat;
         }
      }

      if(Mail.debug && DEBUG) {
         StringBuilder var10 = (new StringBuilder()).append("day>").append(var1).append(",").append(var3).append(",").append(var5).append(",");
         CharSequence var11 = DateFormat.format(var7, var1);
         String var12 = var10.append(var11).toString();
         ll.d("DD", var12);
      }

      var13 = DateFormat.format(var7, var1);
      return (CharSequence)var13;
   }

   public static String getSignatureByLocale(Context var0, String var1) {
      String var2;
      if(var0 != null && var1 != null) {
         String var3 = var0.getSharedPreferences("cusSignature", 0).getString(var1, (String)null);
         if(DEBUG) {
            String var4 = "get signature from sharePreference:" + var3 + ", in locale:" + var1;
            ll.d("Util", var4);
         }

         var2 = var3;
      } else {
         var2 = null;
      }

      return var2;
   }

   public static final CharSequence getThisYearString() {
      long var0 = System.currentTimeMillis();
      return DateFormat.format("yy/MM/dd", var0).subSequence(6, 8);
   }

   public static final CharSequence getTimeFullString(Context var0, long var1) {
      return DateFormat.format("MM/dd/yy h:mmaa", var1);
   }

   public static final CharSequence getTimeString(Context var0, long var1) {
      return DateFormat.format(var0.getText(2131362578), var1);
   }

   public static final CharSequence getTimeWithTimeZone(long var0) {
      return DateFormat.format("E, d MMM yyyy kk:mm:ss z", var0);
   }

   public static final CharSequence getTitleTime(Context var0, long var1, boolean var3) {
      if(sDailyFormat == null) {
         init(var0);
      }

      Calendar.getInstance().setTimeInMillis(var1);
      boolean var4 = false;
      Time var5 = new Time();
      long var6 = System.currentTimeMillis();
      var5.set(var6);
      CharSequence var8 = sDateFormat;
      long var9 = System.currentTimeMillis();
      String var11 = (String)DateFormat.format(var8, var9);
      CharSequence var12 = sDateFormat;
      long var13 = System.currentTimeMillis() - 86400000L;
      String var15 = (String)DateFormat.format(var12, var13);
      String var16 = (String)DateFormat.format(sDateFormat, var1);
      int var17 = var5.year;
      int var18 = var5.weekDay;
      int var19 = 1;
      if(DateFormat.is24HourFormat(var0)) {
         var19 |= 128;
      }

      Calendar var20 = Calendar.getInstance();
      var20.set(var17, 0, 1, 0, 0, 0);
      long var21 = var20.getTimeInMillis();
      long var23 = System.currentTimeMillis() / 86400000L;
      if(DEBUG) {
         String var25 = "getTitleTime>" + var11 + "," + var15 + "," + var16;
         ll.d("Util", var25);
      }

      String var26 = MailCommon.formatDateTime(var0, var1, var19);
      Object var39;
      if(var16.equals(var11)) {
         var39 = (new StringBuilder(var26)).toString().toUpperCase();
      } else {
         Object var38;
         if(var16.equals(var15)) {
            var38 = var0.getString(2131362280);
         } else {
            label31: {
               if(var18 != -1) {
                  long var28 = (long)var18;
                  if((var23 - var28 + 1L) * 86400000L < var1) {
                     Time var30 = new Time();
                     var30.set(var1);
                     StringBuilder var31 = new StringBuilder();
                     String var32 = DateUtils.getDayOfWeekString(var30.weekDay + 1, 20);
                     var38 = var31.append(var32).append(".").toString();
                     break label31;
                  }
               }

               if(var1 >= var21) {
                  Time var33 = new Time();
                  var33.set(var1);
                  StringBuilder var34 = new StringBuilder();
                  String var35 = DateUtils.getMonthString(var33.month, 20);
                  StringBuilder var36 = var34.append(var35).append(". ");
                  int var37 = var33.monthDay;
                  var38 = var36.append(var37).toString();
               } else {
                  var38 = DateFormat.format(sYearlyFormat, var1);
               }
            }
         }

         if(var38 != null) {
            var39 = new StringBuilder((CharSequence)var38);
         } else {
            var39 = new StringBuilder(var26);
         }
      }

      return (CharSequence)var39;
   }

   public static long getUsingAccountPref(Context var0) {
      long var1;
      if(var0 == null) {
         var1 = 0L;
      } else {
         long var3 = var0.getSharedPreferences("account", 0).getLong("usingAccount", 0L);
         if(DEBUG) {
            String var5 = "get using account:" + var3;
            ll.d("Util", var5);
         }

         var1 = var3;
      }

      return var1;
   }

   public static final String getValueString(ArrayList<String> var0, String var1) {
      int var2 = 0;

      String var7;
      while(true) {
         int var3 = var0.size();
         if(var2 >= var3) {
            var7 = null;
            break;
         }

         if(((String)var0.get(var2)).equals(var1)) {
            int var4 = var2 + 1;
            int var5 = var0.size();
            if(var4 < var5) {
               int var6 = var2 + 1;
               var7 = (String)var0.get(var6);
            } else {
               var7 = null;
            }
            break;
         }

         ++var2;
      }

      return var7;
   }

   private static final void init(Context var0) {
      sDailyFormat = var0.getText(2131362573);
      sDailyFormat24 = var0.getText(2131362574);
      sWeeklyFormat = var0.getText(2131362575);
      sMonthlyFormat = var0.getText(2131362576);
      sYearlyFormat = var0.getText(2131362577);
      sDateFormat = var0.getText(2131362578);
      sFullTimeFormat = var0.getText(2131362580);
      sDateFormatMDY = var0.getText(2131362579);
      sDateTimeWeekFormat = var0.getText(2131362581);
      sDateTimeWeekFormatWith24 = var0.getText(2131362582);
   }

   public static void initBufferFileForMailAP(long var0) {
      if(!initBufferFileForMailAP) {
         initBufferFileForMailAP = (boolean)1;
         if(DEBUG) {
            ll.d("Util", "> initBufferFileForMailAP()");
         }

         try {
            File var2 = new File("/data/data/com.htc.android.mail/bufferFileForMailAP");
            if(!var2.exists()) {
               if(DEBUG) {
                  String var3 = "buffer file not found, create new one for " + var0 + " bytes, file name: " + "/data/data/com.htc.android.mail/bufferFileForMailAP";
                  ll.d("Util", var3);
               }

               FileOutputStream var4 = new FileOutputStream(var2);
               byte[] var5 = new byte[(int)var0];
               var4.write(var5);
               var4.flush();
               var4.close();
            } else if(DEBUG) {
               ll.d("Util", "buffer file exist, need not create");
            }
         } catch (Exception var7) {
            ll.e("Util", "initBufferFileForMailAP() Exception");
            var7.printStackTrace();
         }

         initBufferFileForMailAP = (boolean)0;
         if(DEBUG) {
            ll.d("Util", "< initBufferFileForMailAP()");
         }
      }
   }

   public static final ByteString linesToByteString(ArrayList<ByteString> var0, int var1, int var2, String var3) {
      ByteString var4 = new ByteString(var3);
      int var5 = var4.length();
      if(Mail.MAIL_DETAIL_DEBUG) {
         String var6 = "from is " + var1 + ",to is " + var2;
         ll.d("Util", var6);
      }

      for(int var7 = var1; var7 < var2; ++var7) {
         if(((ByteString)var0.get(var7)).regionMatches((boolean)1, 2, var4, 0, var5)) {
            var2 = var7;
         }
      }

      if(Mail.MAIL_DETAIL_DEBUG) {
         String var8 = "from is " + var1 + ",to is " + var2;
         ll.d("Util", var8);
      }

      return new ByteString(var0, var1, var2);
   }

   public static final String linesToString(ArrayList<ByteString> var0, int var1, int var2) {
      return linesToString(var0, var1, var2, "us-ascii");
   }

   public static final String linesToString(ArrayList<ByteString> var0, int var1, int var2, String var3) {
      String var4 = (new ByteString(var0, var1, var2)).toString(var3);
      if(var3.compareToIgnoreCase("BIG5") == 0) {
         var4 = FixBig5UnicodeForJP(var4);
      }

      return var4;
   }

   public static final String linesToString(ArrayList<ByteString> var0, int var1, int var2, String var3, String var4) {
      ByteString var5 = new ByteString(var4);
      int var6 = var5.length();
      if(Mail.MAIL_DETAIL_DEBUG) {
         String var7 = "from is " + var1 + ",to is " + var2;
         ll.d("Util", var7);
      }

      for(int var8 = var1; var8 < var2; ++var8) {
         if(((ByteString)var0.get(var8)).regionMatches((boolean)1, 2, var5, 0, var6)) {
            var2 = var8;
         }
      }

      if(Mail.MAIL_DETAIL_DEBUG) {
         String var9 = "from is " + var1 + ",to is " + var2;
         ll.d("Util", var9);
      }

      String var10 = (new ByteString(var0, var1, var2)).toString(var3);
      if(var3.compareToIgnoreCase("BIG5") == 0) {
         var10 = FixBig5UnicodeForJP(var10);
      }

      return var10;
   }

   public static final boolean needEncode(byte[] var0) {
      int var1 = 0;

      boolean var3;
      while(true) {
         int var2 = var0.length;
         if(var1 >= var2) {
            var3 = false;
            break;
         }

         if(var0[var1] < 48 || var0[var1] > 90 && var0[var1] < 97 || var0[var1] > 122) {
            var3 = true;
            break;
         }

         ++var1;
      }

      return var3;
   }

   public static final void normalizeLineEndings(ArrayList<ByteString> var0, int var1, int var2) {
      for(int var3 = var1; var3 < var2; ++var3) {
         ByteString var4 = (ByteString)var0.get(var3);
         ByteString var5 = ByteString.CRLF;
         ByteString var6 = ByteString.CRLF;
         if(var4.endsWith(var6)) {
            int var7 = var4.length();
            int var8 = var7 - 2;
            var4.set(var8, 10);
            int var9 = var7 - 1;
            var4.delete(var9);
         }
      }

   }

   public static final ArrayList parsePairString(String var0) {
      ArrayList var1 = new ArrayList();
      StringBuilder var2 = new StringBuilder();
      byte var3 = -1;
      int var4 = 0;

      while(true) {
         int var5 = var0.length();
         if(var4 >= var5) {
            return var1;
         }

         label70: {
            if(var0.charAt(var4) == 40) {
               if(var3 == -1) {
                  var3 = 1;
               } else if(var3 == 2) {
                  char var10 = var0.charAt(var4);
                  var2.append(var10);
               }
            } else if(var0.charAt(var4) == 41) {
               if(var3 == 1) {
                  String var12 = var2.toString();
                  var1.add(var12);
                  new StringBuilder();
                  var3 = -1;
                  break label70;
               }

               if(var3 == 2) {
                  char var15 = var0.charAt(var4);
                  var2.append(var15);
               }
            } else if(var0.charAt(var4) == 32) {
               if(var3 == 1) {
                  char var17 = var0.charAt(var4);
                  var2.append(var17);
               } else if(var3 == 2) {
                  char var19 = var0.charAt(var4);
                  var2.append(var19);
               } else if(var3 == 0) {
                  String var21 = var2.toString();
                  var1.add(var21);
                  new StringBuilder();
                  var3 = -1;
                  break label70;
               }
            } else if(var0.charAt(var4) == 34) {
               if(var3 == -1) {
                  var3 = 2;
               } else if(var3 == 2) {
                  String var24 = var2.toString();
                  var1.add(var24);
                  new StringBuilder();
                  var3 = -1;
                  break label70;
               }
            } else if(var3 == -1) {
               var3 = 0;
               char var27 = var0.charAt(var4);
               var2.append(var27);
            } else if(var3 == 1) {
               char var29 = var0.charAt(var4);
               var2.append(var29);
            } else if(var3 == 0) {
               char var31 = var0.charAt(var4);
               var2.append(var31);
            } else if(var3 == 2) {
               char var33 = var0.charAt(var4);
               var2.append(var33);
            }

            int var6 = var0.length() - 1;
            if(var4 == var6 && var3 == 0) {
               if(var2.toString() != null && var2.toString().length() > 0) {
                  String var7 = var2.toString();
                  var1.add(var7);
               }

               new StringBuilder();
            }
         }

         ++var4;
      }
   }

   public static void resetBladeDateFormat() {
      mCurSystemTimeFormat = null;
   }

   public static void resetCurrentLocale(Context var0) {
      if(var0 != null) {
         CUR_LOCALE = var0.getResources().getConfiguration().locale;
      }
   }

   public static void resetMailItemExtraHeight(Context var0) {
      if(var0 != null) {
         CUR_LOCALE = var0.getResources().getConfiguration().locale;
      }

      if(CUR_LOCALE.toString().startsWith("zh")) {
         ;
      }

      EXTRA_SPACE_FOR_MAIL_ITEM = 0;
   }

   public static String trimRight(String var0) {
      String var1;
      if(var0 == null) {
         var1 = "";
      } else {
         int var2 = var0.length();
         int var3 = var2;

         for(int var4 = var2 - 1; var4 >= 0 && var0.charAt(var4) <= 32; var4 += -1) {
            var3 += -1;
         }

         if(var2 == var3) {
            var1 = var0;
         } else {
            byte[] var5 = var0.getBytes();
            var1 = new String(var5, 0, var3);
         }
      }

      return var1;
   }

   public static final int unfoldLines(ArrayList<ByteString> var0, int var1, int var2, boolean var3) {
      int var4 = var2 - var1;
      int var5 = -1;
      int var6;
      if(var4 == 0) {
         var6 = -1;
      } else {
         if(1 == var4) {
            ByteString var7 = ByteString.CRLF;
            Object var8 = var0.get(var1);
            if(var7.equals(var8)) {
               var6 = var1;
               return var6;
            }
         }

         int var9 = 1;
         ByteString var10 = (ByteString)var0.get(var1);
         ByteString var11 = ByteString.CRLF;

         while(true) {
            if(var9 >= var4) {
               var6 = var5;
               break;
            }

            int var12 = var1 + var9;
            ByteString var13 = (ByteString)var0.get(var12);
            if(var13.equals(var11) && -1 == var5) {
               var5 = var1 + var9;
               if(var3) {
                  var6 = var5;
                  break;
               }
            } else if(var10.unfoldLine(var13)) {
               int var14 = var1 + var9;
               var0.remove(var14);
               var4 += -1;
            } else {
               var10 = var13;
               ++var9;
            }
         }
      }

      return var6;
   }

   public static final int unfoldLines(ArrayList<ByteString> var0, int var1, boolean var2) {
      int var3 = var0.size();
      return unfoldLines(var0, var1, var3, var2);
   }

   public static final int unfoldLines(ArrayList<ByteString> var0, boolean var1) {
      int var2 = var0.size();
      return unfoldLines(var0, 0, var2, var1);
   }

   public static void writeAccountCountToPref(Context var0, int var1) {
      if(var0 != null) {
         if(var1 != -1) {
            Editor var2 = var0.getSharedPreferences("account", 0).edit();
            var2.putInt("totalCount", var1);
            boolean var4 = var2.commit();
         } else {
            int var5 = MailProvider.getAccountCount((boolean)0);
            if(DEBUG) {
               String var6 = "write account count:" + var5;
               ll.d("Util", var6);
            }

            Editor var7 = var0.getSharedPreferences("account", 0).edit();
            var7.putInt("totalCount", var5);
            boolean var9 = var7.commit();
         }
      }
   }

   public static void writeAccountLastAccessMailboxIdToPref(Context var0, long var1, long var3) {
      if(var0 != null) {
         if(var1 > 0L) {
            if(var3 > 0L) {
               Editor var5 = var0.getSharedPreferences("account", 0).edit();
               String var6 = var1 + "lastMailboxId";
               var5.putLong(var6, var3);
               boolean var8 = var5.commit();
               if(DEBUG) {
                  String var9 = "account:" + var1 + ", last mailbox:" + var3;
                  ll.d("Util", var9);
               }
            } else {
               Editor var10 = var0.getSharedPreferences("account", 0).edit();
               String var11 = var1 + "lastMailboxId";
               var10.remove(var11);
               boolean var13 = var10.commit();
               if(DEBUG) {
                  String var14 = "account:" + var1 + ", remove last mailbox information";
                  ll.d("Util", var14);
               }
            }
         }
      }
   }

   public static void writeAccountLastMove2MailboxIdToPref(Context var0, long var1, String var3) {
      if(var0 != null) {
         if(var1 > 0L) {
            if(var3 != null) {
               Editor var4 = var0.getSharedPreferences("account", 0).edit();
               StringBuilder var5 = (new StringBuilder()).append("Move2MailboxId");
               String var6 = String.valueOf(var1);
               String var7 = var5.append(var6).toString();
               var4.putString(var7, var3);
               boolean var9 = var4.commit();
               if(DEBUG) {
                  String var10 = "account:" + var1 + ", last mailbox:" + var3;
                  ll.d("Util", var10);
               }
            } else {
               Editor var11 = var0.getSharedPreferences("account", 0).edit();
               String var12 = var1 + "Move2MailboxId";
               var11.remove(var12);
               boolean var14 = var11.commit();
               if(DEBUG) {
                  String var15 = "account:" + var1 + ", remove last mailbox information";
                  ll.d("Util", var15);
               }
            }
         }
      }
   }

   public static void writeAccountPowerSavingToPref(Context var0, long var1, int var3) {
      if(var0 != null) {
         if(DEBUG) {
            String var4 = "account:" + var1 + ",power saving:" + var3;
            ll.d("Util", var4);
         }

         Editor var5 = var0.getSharedPreferences("account", 0).edit();
         if(var3 != 1 && var3 != 0) {
            String var9 = "powerSaving" + var1;
            var5.remove(var9);
         } else {
            String var6 = "powerSaving" + var1;
            var5.putInt(var6, var3);
         }

         boolean var8 = var5.commit();
      }
   }

   public static void writeIMSIToPref(Context var0, String var1) {
      if(var0 != null) {
         if(var1 != null) {
            Editor var2 = var0.getSharedPreferences("siminfo", 0).edit();
            var2.putString("lastIMSI", var1);
            boolean var4 = var2.commit();
         }
      }
   }

   public static void writeKeepTempMessage(Context var0, String var1, int var2) {
      if(var0 != null) {
         Editor var3 = var0.getSharedPreferences("TempMessage", 0).edit();
         if(DEBUG) {
            String var4 = "key :" + var1 + ", count:" + var2;
            ll.d("Util", var4);
         }

         if(var2 == 0) {
            Editor var5 = var3.clear();
            boolean var6 = var3.commit();
         } else {
            var3.putInt(var1, var2);
            boolean var8 = var3.commit();
         }
      }
   }

   public static void writeSignatureToPref(Context var0, String var1, String var2) {
      if(var0 != null) {
         if(var1 != null) {
            if(var2 != null) {
               Editor var3 = var0.getSharedPreferences("cusSignature", 0).edit();
               var3.putString(var1, var2);
               boolean var5 = var3.commit();
            }
         }
      }
   }

   public static void writeUsingAccountToPref(Context var0, long var1) {
      if(var0 != null) {
         if(var1 > 0L) {
            if(DEBUG) {
               String var3 = "write using account:" + var1;
               ll.d("Util", var3);
            }

            Editor var4 = var0.getSharedPreferences("account", 0).edit();
            var4.putLong("usingAccount", var1);
            boolean var6 = var4.commit();
         }
      }
   }
}
