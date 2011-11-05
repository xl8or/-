package com.htc.android.mail;

import com.htc.android.mail.VBuilder;
import com.htc.android.mail.VCardParser_V21;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class VCardParser_V30 extends VCardParser_V21 {

   private static final String V21LINEBREAKER = "\r\n";
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
      propV30ToV21Map = new HashMap();
      Object var4 = propV30ToV21Map.put("PHOTO", "PHOTO");
      Object var5 = propV30ToV21Map.put("LOGO", "PHOTO");
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
         String var6 = var2.replaceAll(",", ";").replaceAll("ENCODING=B", "ENCODING=BASE64").replaceAll("ENCODING=b", "ENCODING=BASE64");
         var4 = var5.append(var6).toString();
      }

      return var4 + ":" + var3 + "\r\n";
   }

   private String[] splitProperty(String var1) {
      return var1.replaceAll("\r\n", "\n").replaceAll("\n ", "").replaceAll("\n\t", "").split("\n");
   }

   public boolean parse(InputStream var1, String var2, VBuilder var3) throws IOException {
      byte[] var4 = new byte[var1.available()];
      int var7 = var1.read(var4);
      String var8 = new String(var4);
      StringBuilder var9 = new StringBuilder;
      String var11 = "";
      var9.<init>(var11);
      String[] var14 = this.splitProperty(var8);
      String var15 = var14[0];
      byte var30;
      if(!"BEGIN:vCard".equals(var15)) {
         String var16 = var14[0];
         if(!"BEGIN:VCARD".equals(var16)) {
            var30 = 0;
            return (boolean)var30;
         }
      }

      String var18 = "BEGIN:VCARD\r\n";
      var9.append(var18);
      int var20 = 1;

      while(true) {
         int var21 = var14.length - 1;
         if(var20 >= var21) {
            int var59 = var14.length - 1;
            String var60 = var14[var59];
            if(!"END:vCard".equals(var60)) {
               int var61 = var14.length - 1;
               String var62 = var14[var61];
               if(!"END:VCARD".equals(var62)) {
                  var30 = 0;
                  break;
               }
            }

            String var64 = "END:VCARD\r\n";
            var9.append(var64);
            byte[] var66 = var9.toString().getBytes();
            ByteArrayInputStream var67 = new ByteArrayInputStream(var66);
            var30 = super.parse(var67, var2, var3);
            break;
         }

         String var24 = var14[var20];
         String var25 = "";
         if(var25.equals(var24)) {
            String var28 = "\r\n";
            var9.append(var28);
         } else {
            String var32 = ":";
            byte var33 = 2;
            String[] var34 = var24.split(var32, var33);
            String var35 = var34[0];
            int var36 = var34.length;
            byte var37 = 1;
            String var38;
            if(var36 > var37) {
               var38 = var34[1];
            } else {
               var38 = "";
            }

            if(var35.length() > 0) {
               label55: {
                  String var40 = ";";
                  byte var41 = 2;
                  String[] var42 = var35.split(var40, var41);
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
                        break label55;
                     }
                  }

                  String var55 = this.mapContentlineV30ToV21(var43, var46, var38);
                  StringBuilder var58 = var9.append(var55);
               }
            }
         }

         ++var20;
      }

      return (boolean)var30;
   }
}
