package com.htc.android.mail.pim.vcard;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.util.Log;
import com.htc.android.mail.pim.vcard.ContactStruct;
import com.htc.android.mail.pim.vcard.VCardException;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import org.apache.commons.codec.binary.Base64;

public class VCardComposer {

   public static final String KEY_CITY = "city";
   public static final String KEY_COUNTRY = "country";
   public static final String KEY_STATE = "state";
   public static final String KEY_STREET = "street";
   public static final String KEY_ZIP = "zip";
   private static final String TAG = "VCardComposer";
   public static final int VERSION_VCARD21_INT = 1;
   public static final int VERSION_VCARD30_INT = 2;
   private static final HashMap<Integer, String> emailTypeMap;
   private static final HashSet<String> emailTypes;
   private static final HashMap<Integer, String> phoneTypeMap;
   private static final HashSet<String> phoneTypes;
   private String mNewline;
   private StringBuilder mResult;


   static {
      String[] var0 = new String[]{"CELL", "AOL", "APPLELINK", "ATTMAIL", "CIS", "EWORLD", "INTERNET", "IBMMAIL", "MCIMAIL", "POWERSHARE", "PRODIGY", "TLX", "X400"};
      List var1 = Arrays.asList(var0);
      emailTypes = new HashSet(var1);
      String[] var2 = new String[]{"PREF", "WORK", "HOME", "VOICE", "FAX", "MSG", "CELL", "PAGER", "BBS", "MODEM", "CAR", "ISDN", "VIDEO"};
      List var3 = Arrays.asList(var2);
      phoneTypes = new HashSet(var3);
      phoneTypeMap = new HashMap();
      emailTypeMap = new HashMap();
      HashMap var4 = phoneTypeMap;
      Integer var5 = Integer.valueOf(1);
      var4.put(var5, "HOME");
      HashMap var7 = phoneTypeMap;
      Integer var8 = Integer.valueOf(2);
      var7.put(var8, "CELL");
      HashMap var10 = phoneTypeMap;
      Integer var11 = Integer.valueOf(3);
      var10.put(var11, "WORK");
      HashMap var13 = phoneTypeMap;
      Integer var14 = Integer.valueOf(4);
      var13.put(var14, "FAX;WORK");
      HashMap var16 = phoneTypeMap;
      Integer var17 = Integer.valueOf(5);
      var16.put(var17, "FAX;HOME");
      HashMap var19 = phoneTypeMap;
      Integer var20 = Integer.valueOf(6);
      var19.put(var20, "PAGER");
      HashMap var22 = phoneTypeMap;
      Integer var23 = Integer.valueOf(7);
      var22.put(var23, "X-OTHER");
      HashMap var25 = phoneTypeMap;
      Integer var26 = Integer.valueOf(0);
      var25.put(var26, "X-CUSTOM");
      HashMap var28 = emailTypeMap;
      Integer var29 = Integer.valueOf(0);
      var28.put(var29, "X-CUSTOM");
   }

   public VCardComposer() {}

   private void appendAnniversary(String var1) {
      StringBuilder var2 = this.mResult.append("X-ANNIVERSARY:").append(var1);
      String var3 = this.mNewline;
      var2.append(var3);
   }

   private void appendBirthday(String var1) {
      StringBuilder var2 = this.mResult.append("BDAY:").append(var1);
      String var3 = this.mNewline;
      var2.append(var3);
   }

   private void appendCategory(String var1) {
      StringBuilder var2 = this.mResult.append("X-CATEGORIES;CHARSET=UTF-8:").append(var1);
      String var3 = this.mNewline;
      var2.append(var3);
   }

   private void appendContactMethodStr(List<ContactStruct.ContactMethod> var1, int var2) {
      byte var4 = 1;
      if(var2 == var4) {
         String var5 = ";";
      } else {
         String var39 = ",";
      }

      int var6 = 0;
      Iterator var7 = var1.iterator();

      while(var7.hasNext()) {
         ContactStruct.ContactMethod var8 = (ContactStruct.ContactMethod)var7.next();
         switch(Integer.parseInt(var8.kind)) {
         case 1:
            String var9 = var8.data;
            if(!this.isNull(var9)) {
               String var12 = var8.type;
               int var13 = (new Integer(var12)).intValue();
               HashMap var14 = emailTypeMap;
               Integer var15 = Integer.valueOf(var13);
               String var18;
               if(var14.containsKey(var15)) {
                  HashMap var16 = emailTypeMap;
                  Integer var17 = Integer.valueOf(var13);
                  var18 = (String)var16.get(var17);
               } else {
                  var18 = "INTERNET";
               }

               byte var20 = 1;
               if(var2 == var20) {
                  StringBuilder var21 = this.mResult.append("EMAIL;");
               } else {
                  StringBuilder var40 = this.mResult.append("EMAIL;TYPE=");
               }

               String var23 = "X-CUSTOM";
               if(var18.equals(var23)) {
                  StringBuilder var24 = this.mResult.append("INTERNET;X-CUSTOM-");
                  ++var6;
                  StringBuilder var27 = var24.append(var6).append(":");
                  String var28 = var8.data;
                  StringBuilder var29 = var27.append(var28);
                  String var30 = this.mNewline;
                  var29.append(var30);
                  StringBuilder var32 = this.mResult.append("X-EMAIL-CUSTOM-TYPE;").append("X-CUSTOM-");
                  StringBuilder var34 = var32.append(var6).append(":");
                  String var35 = var8.label;
                  StringBuilder var36 = var34.append(var35);
                  String var37 = this.mNewline;
                  var36.append(var37);
               } else {
                  StringBuilder var41 = this.mResult;
                  StringBuilder var43 = var41.append(var18).append(";CHARSET=UTF-8:");
                  String var44 = var8.data;
                  StringBuilder var45 = var43.append(var44);
                  String var46 = this.mNewline;
                  var45.append(var46);
               }
            }
            break;
         case 2:
            String var48 = var8.data;
            if(!this.isNull(var48)) {
               if(var8.type.equals("CUSTOM")) {
                  StringBuilder var51 = this.mResult.append("ADR;").append("HOME").append(";").append("X-CUSTOM-");
                  ++var6;
                  StringBuilder var54 = var51.append(var6).append(":");
                  String var55 = var8.data;
                  String var59 = this.foldingString(var55, var2);
                  StringBuilder var60 = var54.append(var59);
                  String var61 = this.mNewline;
                  var60.append(var61);
                  StringBuilder var63 = this.mResult.append("X-ADR-CUSTOM-TYPE;").append("X-CUSTOM-");
                  StringBuilder var65 = var63.append(var6).append(":");
                  String var66 = var8.label;
                  String var70 = this.foldingString(var66, var2);
                  StringBuilder var71 = var65.append(var70);
                  String var72 = this.mNewline;
                  var71.append(var72);
               } else {
                  Uri var74 = Uri.parse(var8.data);

                  String var81;
                  String var80;
                  label77: {
                     try {
                        String var76 = "street";
                        String var77 = var74.getQueryParameter(var76);
                        String var78 = "\\\\";
                        String var79 = "\\\\\\\\";
                        var80 = var77.replaceAll(var78, var79).replaceAll(";", "\\\\;");
                     } catch (Exception var170) {
                        StringBuilder var127 = (new StringBuilder()).append("ERROR: Get street from: ");
                        String var128 = var8.data;
                        String var129 = var127.append(var128).toString();
                        String var130 = "VCardComposer";
                        Log.e(var130, var129, var170);
                        var81 = "";
                        break label77;
                     }

                     var81 = var80;
                  }

                  String var87;
                  label73: {
                     try {
                        String var83 = "city";
                        String var84 = var74.getQueryParameter(var83);
                        String var85 = "\\\\";
                        String var86 = "\\\\\\\\";
                        var80 = var84.replaceAll(var85, var86).replaceAll(";", "\\\\;");
                     } catch (Exception var169) {
                        StringBuilder var135 = (new StringBuilder()).append("ERROR: Get city from: ");
                        String var136 = var8.data;
                        String var137 = var135.append(var136).toString();
                        String var138 = "VCardComposer";
                        Log.e(var138, var137, var169);
                        var87 = "";
                        break label73;
                     }

                     var87 = var80;
                  }

                  String var93;
                  label69: {
                     try {
                        String var89 = "state";
                        String var90 = var74.getQueryParameter(var89);
                        String var91 = "\\\\";
                        String var92 = "\\\\\\\\";
                        var80 = var90.replaceAll(var91, var92).replaceAll(";", "\\\\;");
                     } catch (Exception var168) {
                        StringBuilder var143 = (new StringBuilder()).append("ERROR: Get state from: ");
                        String var144 = var8.data;
                        String var145 = var143.append(var144).toString();
                        String var146 = "VCardComposer";
                        Log.e(var146, var145, var168);
                        var93 = "";
                        break label69;
                     }

                     var93 = var80;
                  }

                  String var96;
                  label65: {
                     try {
                        String var95 = "zip";
                        var80 = var74.getQueryParameter(var95).replaceAll("\\\\", "\\\\\\\\").replaceAll(";", "\\\\;");
                     } catch (Exception var167) {
                        StringBuilder var151 = (new StringBuilder()).append("ERROR: Get zip from: ");
                        String var152 = var8.data;
                        String var153 = var151.append(var152).toString();
                        String var154 = "VCardComposer";
                        Log.e(var154, var153, var167);
                        var96 = "";
                        break label65;
                     }

                     var96 = var80;
                  }

                  String var102;
                  label61: {
                     try {
                        String var98 = "country";
                        String var99 = var74.getQueryParameter(var98);
                        String var100 = "\\\\";
                        String var101 = "\\\\\\\\";
                        var80 = var99.replaceAll(var100, var101).replaceAll(";", "\\\\;");
                     } catch (Exception var166) {
                        StringBuilder var159 = (new StringBuilder()).append("ERROR: Get country from: ");
                        String var160 = var8.data;
                        String var161 = var159.append(var160).toString();
                        String var162 = "VCardComposer";
                        Log.e(var162, var161, var166);
                        var102 = "";
                        break label61;
                     }

                     var102 = var80;
                  }

                  StringBuilder var103 = this.mResult.append("ADR;");
                  String var104 = var8.type;
                  StringBuilder var105 = var103.append(var104).append(";CHARSET=UTF-8:");
                  StringBuilder var106 = new StringBuilder();
                  String var108 = var106.append(var81).append(";").toString();
                  StringBuilder var109 = var105.append(var108);
                  StringBuilder var110 = new StringBuilder();
                  String var112 = var110.append(var87).append(";").toString();
                  StringBuilder var113 = var109.append(var112);
                  StringBuilder var114 = new StringBuilder();
                  String var116 = var114.append(var93).append(";").toString();
                  StringBuilder var117 = var113.append(var116);
                  StringBuilder var118 = new StringBuilder();
                  String var120 = var118.append(var96).append(";").toString();
                  StringBuilder var121 = var117.append(var120);
                  StringBuilder var123 = var121.append(var102);
                  String var124 = this.mNewline;
                  var123.append(var124);
               }
            }
         }
      }

   }

   private void appendIMStr(List<ContactStruct.IMData> var1, int var2) {
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         ContactStruct.IMData var4 = (ContactStruct.IMData)var3.next();
         String var5 = var4.data;
         if(!this.isNull(var5)) {
            StringBuilder var6 = this.mResult.append("X-IM;CHARSET=UTF-8:");
            String var7 = var4.data;
            String var8 = this.foldingString(var7, var2);
            StringBuilder var9 = var6.append(var8);
            String var10 = this.mNewline;
            var9.append(var10);
         }
      }

   }

   private void appendLastUpdateTime(String var1) {
      StringBuilder var2 = this.mResult.append("REV:").append(var1);
      String var3 = this.mNewline;
      var2.append(var3);
   }

   private void appendNameStr(String var1) {
      StringBuilder var2 = this.mResult.append("N;CHARSET=UTF-8:").append(var1);
      String var3 = this.mNewline;
      var2.append(var3);
   }

   private void appendOrganizationStr(List<String> var1, List<String> var2, List<String> var3, int var4) {
      new HashMap();
      if(var4 == 1) {
         String var6 = ";";
      } else {
         String var24 = ",";
      }

      if(var2 == null) {
         var2 = new ArrayList();
      }

      if(var3 == null) {
         var3 = new ArrayList();
      }

      int var7 = ((List)var2).size();
      int var8 = ((List)var3).size();
      int var9;
      if(var7 > var8) {
         var9 = ((List)var2).size();
      } else {
         var9 = ((List)var3).size();
      }

      for(int var10 = 0; var10 < var9; ++var10) {
         label44: {
            if(((List)var2).size() > var10) {
               String var11 = (String)((List)var2).get(var10);
               if(!this.isNull(var11)) {
                  StringBuilder var12 = this.mResult.append("ORG;CHARSET=UTF-8:");
                  String var13 = (String)((List)var2).get(var10);
                  StringBuilder var14 = var12.append(var13);
                  String var15 = this.mNewline;
                  var14.append(var15);
                  break label44;
               }
            }

            StringBuilder var25 = this.mResult.append("ORG:");
            String var26 = this.mNewline;
            var25.append(var26);
         }

         if(((List)var3).size() > var10) {
            String var17 = (String)((List)var3).get(var10);
            if(!this.isNull(var17)) {
               StringBuilder var18 = this.mResult.append("TITLE;CHARSET=UTF-8:");
               String var19 = (String)((List)var3).get(var10);
               String var20 = this.foldingString(var19, var4);
               StringBuilder var21 = var18.append(var20);
               String var22 = this.mNewline;
               var21.append(var22);
               continue;
            }
         }

         StringBuilder var28 = this.mResult.append("TITLE:");
         String var29 = this.mNewline;
         var28.append(var29);
      }

   }

   private void appendPhoneStr(List<ContactStruct.PhoneData> var1, int var2) {
      HashMap var3 = new HashMap();
      String var4;
      if(var2 == 1) {
         var4 = ";";
      } else {
         var4 = ",";
      }

      int var5 = 0;
      Iterator var6 = var1.iterator();

      while(var6.hasNext()) {
         ContactStruct.PhoneData var7 = (ContactStruct.PhoneData)var6.next();
         String var8 = var7.data;
         if(!this.isNull(var8)) {
            String var9 = this.getPhoneTypeStr(var7);
            if(var2 == 2 && var9.indexOf(";") != -1) {
               String var10 = var9.replace(";", ",");
            }

            String var11 = var7.data;
            if(var3.containsKey(var11)) {
               StringBuilder var12 = new StringBuilder();
               String var13 = var7.data;
               String var14 = (String)var3.get(var13);
               var9 = var12.append(var14).append(var4).append(var9).toString();
            }

            if(var2 == 1) {
               StringBuilder var15 = this.mResult.append("TEL;");
            } else {
               StringBuilder var27 = this.mResult.append("TEL;TYPE=");
            }

            if(var9.equals("X-CUSTOM")) {
               StringBuilder var16 = this.mResult.append("HOME;").append("X-CUSTOM-");
               ++var5;
               StringBuilder var17 = var16.append(var5).append(":");
               String var18 = var7.data;
               StringBuilder var19 = var17.append(var18);
               String var20 = this.mNewline;
               var19.append(var20);
               StringBuilder var22 = this.mResult.append("X-TEL-CUSTOM-TYPE;").append("X-CUSTOM-").append(var5).append(":");
               String var23 = var7.label;
               StringBuilder var24 = var22.append(var23);
               String var25 = this.mNewline;
               var24.append(var25);
            } else {
               StringBuilder var28 = this.mResult.append(var9).append(";CHARSET=UTF-8:");
               String var29 = var7.data;
               StringBuilder var30 = var28.append(var29);
               String var31 = this.mNewline;
               var30.append(var31);
            }
         }
      }

      Entry var34;
      for(Iterator var33 = var3.entrySet().iterator(); var33.hasNext(); var34 = (Entry)var33.next()) {
         ;
      }

   }

   private void appendPhotoStr(byte[] var1, String var2, int var3) throws VCardException {
      if(var1 != null) {
         if(var1.length != 0) {
            int var4 = var1.length;
            Bitmap var5 = BitmapFactory.decodeByteArray(var1, 0, var4);
            ByteArrayOutputStream var6 = new ByteArrayOutputStream();
            CompressFormat var7 = CompressFormat.JPEG;
            var5.compress(var7, 75, var6);
            byte[] var9 = Base64.encodeBase64(var6.toByteArray(), (boolean)1);
            String var10 = new String(var9);
            this.foldingString(var10, var3);
            byte[] var12 = Base64.encodeBase64(var1, (boolean)1);
            String var13 = new String(var12);
            String var14 = this.foldingString(var13, var3);
            if(this.isNull(var2)) {
               var2 = "image/jpeg";
            }

            String var15;
            if(var2.indexOf("jpeg") > 0) {
               var15 = "JPEG";
            } else if(var2.indexOf("gif") > 0) {
               var15 = "GIF";
            } else if(var2.indexOf("bmp") > 0) {
               var15 = "BMP";
            } else {
               int var25 = var2.indexOf("/");
               var15 = var2.substring(var25).toUpperCase();
            }

            StringBuilder var16 = this.mResult.append("PHOTO;TYPE=").append(var15);
            String var19;
            if(var3 == 1) {
               StringBuilder var17 = (new StringBuilder()).append(";ENCODING=BASE64:");
               String var18 = this.mNewline;
               var19 = var17.append(var18).toString();
               StringBuilder var20 = (new StringBuilder()).append(var14);
               String var21 = this.mNewline;
               var14 = var20.append(var21).toString();
            } else {
               if(var3 != 2) {
                  return;
               }

               StringBuilder var26 = (new StringBuilder()).append(";ENCODING=b:");
               String var27 = this.mNewline;
               var19 = var26.append(var27).toString();
            }

            StringBuilder var22 = this.mResult.append(var19).append(var14);
            String var23 = this.mNewline;
            var22.append(var23);
         }
      }
   }

   private String foldingString(String var1, int var2) {
      String var4;
      String var5;
      if(var1.endsWith("\r\n")) {
         int var3 = var1.length() - 2;
         var4 = var1.substring(0, var3);
      } else {
         if(!var1.endsWith("\n")) {
            var5 = var1;
            return var5;
         }

         int var6 = var1.length() - 1;
         var4 = var1.substring(0, var6);
      }

      var1 = var4.replaceAll(";", "\\\\;").replaceAll("\\\\", "\\\\\\\\").replaceAll("\r\n", "\n");
      if(var2 == 1) {
         var5 = var1.replaceAll("\n", "\r\n ");
      } else if(var2 == 2) {
         var5 = var1.replaceAll("\n", "\n ");
      } else {
         var5 = var1;
      }

      return var5;
   }

   private String getPhoneTypeStr(ContactStruct.PhoneData var1) {
      int var2 = Integer.parseInt(var1.type);
      HashMap var3 = phoneTypeMap;
      Integer var4 = Integer.valueOf(var2);
      String var7;
      if(var3.containsKey(var4)) {
         HashMap var5 = phoneTypeMap;
         Integer var6 = Integer.valueOf(var2);
         var7 = (String)var5.get(var6);
      } else {
         var7 = "VOICE";
      }

      return var7;
   }

   private boolean isNull(String var1) {
      boolean var2;
      if(var1 != null && !var1.trim().equals("")) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public String createVCard(ContactStruct var1, int var2) throws VCardException {
      StringBuilder var3 = new StringBuilder();
      this.mResult = var3;
      if(var2 == 1) {
         this.mNewline = "\r\n";
      } else {
         if(var2 != 2) {
            throw new VCardException(15, " version not match VERSION_VCARD21 or VERSION_VCARD30.");
         }

         this.mNewline = "\n";
      }

      StringBuilder var4 = this.mResult.append("BEGIN:VCARD");
      String var5 = this.mNewline;
      var4.append(var5);
      if(var2 == 1) {
         StringBuilder var7 = this.mResult.append("VERSION:2.1");
         String var8 = this.mNewline;
         var7.append(var8);
      } else {
         StringBuilder var37 = this.mResult.append("VERSION:3.0");
         String var38 = this.mNewline;
         var37.append(var38);
      }

      String var10 = var1.name;
      if(!this.isNull(var10)) {
         String var11 = var1.name;
         this.appendNameStr(var11);
      }

      if(var1.company != null && var1.company.size() > 0 || var1.title != null && var1.title.size() > 0) {
         List var12 = var1.orgType;
         List var13 = var1.company;
         List var14 = var1.title;
         this.appendOrganizationStr(var12, var13, var14, var2);
      }

      String var15 = var1.last_update_time;
      if(!this.isNull(var15)) {
         String var16 = var1.last_update_time;
         this.appendLastUpdateTime(var16);
      }

      String var17 = var1.birthday;
      if(!this.isNull(var17)) {
         String var18 = var1.birthday;
         this.appendBirthday(var18);
      }

      String var19 = var1.anniversary;
      if(!this.isNull(var19)) {
         String var20 = var1.anniversary;
         this.appendAnniversary(var20);
      }

      String var21 = var1.category;
      if(!this.isNull(var21)) {
         String var22 = var1.category;
         this.appendCategory(var22);
      }

      String var23 = var1.notes;
      if(!this.isNull(var23)) {
         StringBuilder var24 = this.mResult.append("NOTE;CHARSET=UTF-8:");
         String var25 = var1.notes;
         StringBuilder var26 = var24.append(var25);
         String var27 = this.mNewline;
         var26.append(var27);
      }

      if(var1.photoBytes != null) {
         byte[] var29 = var1.photoBytes;
         String var30 = var1.photoType;
         this.appendPhotoStr(var29, var30, var2);
      }

      if(var1.phoneList != null) {
         List var31 = var1.phoneList;
         this.appendPhoneStr(var31, var2);
      }

      if(var1.contactmethodList != null) {
         List var32 = var1.contactmethodList;
         this.appendContactMethodStr(var32, var2);
      }

      if(var1.IMList != null) {
         List var33 = var1.IMList;
         this.appendIMStr(var33, var2);
      }

      StringBuilder var34 = this.mResult.append("END:VCARD");
      String var35 = this.mNewline;
      var34.append(var35);
      return this.mResult.toString();
   }
}
