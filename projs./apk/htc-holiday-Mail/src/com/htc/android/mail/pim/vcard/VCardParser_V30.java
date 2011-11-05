package com.htc.android.mail.pim.vcard;

import com.htc.android.mail.pim.VBuilder;
import com.htc.android.mail.pim.util.StringUtil;
import com.htc.android.mail.pim.vcard.VCardParser_V21;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class VCardParser_V30 extends VCardParser_V21 {

   private static final String V21LINEBREAKER = "\r\n";
   private static final HashSet<String> acceptableParam;
   private static final HashSet<String> acceptablePropsWithParam;
   private static final HashSet<String> acceptablePropsWithoutParam;
   private static final HashMap<String, String> propV30ToV21Map;


   static {
      String[] var0 = new String[]{"PHOTO", "LOGO", "TEL", "EMAIL", "ADR"};
      List var1 = Arrays.asList(var0);
      acceptablePropsWithParam = new HashSet(var1);
      String[] var2 = new String[]{"ORG", "NOTE", "TITLE", "FN", "N"};
      List var3 = Arrays.asList(var2);
      acceptablePropsWithoutParam = new HashSet(var3);
      String[] var4 = new String[]{"CHARSET"};
      List var5 = Arrays.asList(var4);
      acceptableParam = new HashSet(var5);
      propV30ToV21Map = new HashMap();
      Object var6 = propV30ToV21Map.put("PHOTO", "PHOTO");
      Object var7 = propV30ToV21Map.put("LOGO", "PHOTO");
   }

   public VCardParser_V30() {}

   private String mapContentlineV30ToV21(String var1, String var2, String var3) {
      String var4;
      if(propV30ToV21Map.containsKey(var1)) {
         var4 = (String)propV30ToV21Map.get(var1);
      } else {
         var4 = var1;
      }

      if(acceptablePropsWithParam.contains(var1) && var2.length() > 0) {
         StringBuilder var5 = (new StringBuilder()).append(var4).append(";");
         String var6 = this.replaceAll(var2, ",", ";").replaceAll("ENCODING=B;", "ENCODING=BASE64;").replaceAll("ENCODING=b;", "ENCODING=BASE64;").replaceAll("ENCODING=B:", "ENCODING=BASE64:").replaceAll("ENCODING=b:", "ENCODING=BASE64:");
         var4 = var5.append(var6).toString();
      }

      if(acceptablePropsWithoutParam.contains(var1) && var2.length() > 0) {
         String[] var7 = var2.split(";");
         int var8 = var7.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            String var10 = var7[var9];
            String[] var11 = StringUtil.SplitN(var10, "=", 2);
            HashSet var12 = acceptableParam;
            String var13 = var11[0];
            if(var12.contains(var13)) {
               var4 = var4 + ";" + var10;
            }
         }
      }

      return var4 + ":" + var3 + "\r\n";
   }

   private String replaceAll(String var1, String var2, String var3) {
      StringBuffer var4 = new StringBuffer(var1);

      int var7;
      for(int var5 = var4.indexOf(var2); var5 > 0; var5 = var4.indexOf(var2, var7)) {
         int var6 = var2.length();
         var7 = var5 + var6;
         var4.replace(var5, var7, var3);
      }

      return var4.toString();
   }

   private String[] splitProperty(String var1) {
      String var2 = var1.replaceAll("\r\n", "\n");
      int var3 = var2.length();
      StringBuffer var4 = new StringBuffer(var3);
      var4.append(var2);
      int var6 = 0;
      int var7 = 0;

      while(var6 >= 0) {
         var6 = var4.indexOf("\n ", var6);
         if(var6 < 0) {
            var4.indexOf("\n\t", var6);
         }

         if(var6 >= 0) {
            while(true) {
               int var9 = var6 + 1 + var7;
               if(var4.charAt(var9) != 32) {
                  int var10 = var6 + 1 + var7;
                  if(var4.charAt(var10) != 9) {
                     int var11 = var6 + 1;
                     int var12 = var6 + 1 + var7;
                     var4.delete(var11, var12);
                     var7 = 0;
                     break;
                  }
               }

               ++var7;
            }
         }
      }

      return var4.toString().split("\n");
   }

   public boolean parse(String var1, VBuilder var2) throws IOException {
      StringBuilder var4 = new StringBuilder;
      String var6 = "";
      var4.<init>(var6);
      String[] var9 = this.splitProperty(var1);
      String var10 = var9[0];
      byte var26;
      if(!"BEGIN:vCard".equals(var10)) {
         String var11 = var9[0];
         if(!"BEGIN:VCARD".equals(var11)) {
            var26 = 0;
            return (boolean)var26;
         }
      }

      String var13 = "BEGIN:VCARD\r\n";
      var4.append(var13);
      boolean var15 = false;
      int var16 = 1;

      while(true) {
         int var17 = var9.length - 1;
         if(var16 >= var17) {
            int var59 = var9.length - 1;
            String var60 = var9[var59];
            if(!"END:vCard".equals(var60)) {
               int var61 = var9.length - 1;
               String var62 = var9[var61];
               if(!"END:VCARD".equals(var62)) {
                  var26 = 0;
                  break;
               }
            }

            String var64 = "END:VCARD\r\n";
            var4.append(var64);
            String var66 = var4.toString();
            var26 = super.parse(var66, var2);
            break;
         }

         String var20 = var9[var16];
         String var21 = "";
         if(var21.equals(var20)) {
            String var24 = "\r\n";
            var4.append(var24);
         } else {
            String var28 = ":";
            if(var20.indexOf(var28) < 0) {
               if(var15) {
                  var4.append(var20);
                  String var31 = "\r\n";
                  var4.append(var31);
               }
            } else {
               String var34 = ":";
               byte var35 = 2;
               String[] var36 = StringUtil.SplitN(var20, var34, var35);
               String var37 = var36[0];
               String var38 = var36[1];
               if(var37.length() > 0) {
                  label56: {
                     String var40 = ";";
                     byte var41 = 2;
                     String[] var42 = StringUtil.SplitN(var37, var40, var41);
                     String var43 = var42[0];
                     int var44 = var42.length;
                     byte var45 = 1;
                     String var46;
                     if(var44 > var45) {
                        var46 = var42[1];
                     } else {
                        var46 = "";
                     }

                     HashSet var47 = acceptablePropsWithParam;
                     if(!var47.contains(var43)) {
                        HashSet var49 = acceptablePropsWithoutParam;
                        if(!var49.contains(var43)) {
                           break label56;
                        }
                     }

                     String var55 = this.mapContentlineV30ToV21(var43, var46, var38);
                     StringBuilder var58 = var4.append(var55);
                  }
               }
            }
         }

         ++var16;
      }

      return (boolean)var26;
   }
}
