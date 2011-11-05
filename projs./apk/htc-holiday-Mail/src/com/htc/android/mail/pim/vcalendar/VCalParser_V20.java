package com.htc.android.mail.pim.vcalendar;

import com.htc.android.mail.pim.VBuilder;
import com.htc.android.mail.pim.vcalendar.VCalParser_V10;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class VCalParser_V20 extends VCalParser_V10 {

   private static final String V10LINEBREAKER = "\r\n";
   private static final HashSet<String> acceptableComponents;
   private static final HashSet<String> acceptableV20Props;
   private boolean hasTZ = 0;
   private int index;
   private String[] lines;


   static {
      String[] var0 = new String[]{"VEVENT", "VTODO", "VALARM", "VTIMEZONE"};
      List var1 = Arrays.asList(var0);
      acceptableComponents = new HashSet(var1);
      String[] var2 = new String[]{"DESCRIPTION", "DTEND", "DTSTART", "DUE", "COMPLETED", "RRULE", "STATUS", "SUMMARY", "LOCATION"};
      List var3 = Arrays.asList(var2);
      acceptableV20Props = new HashSet(var3);
   }

   public VCalParser_V20() {}

   private boolean parseV20Calbody(String[] var1, StringBuilder var2) {
      boolean var7;
      try {
         while(true) {
            int var3 = this.index;
            String var4 = var1[var3];
            if("VERSION:2.0".equals(var4)) {
               break;
            }

            int var5 = this.index + 1;
            this.index = var5;
         }
      } catch (ArrayIndexOutOfBoundsException var20) {
         var7 = false;
         return var7;
      }

      StringBuilder var8 = var2.append("VERSION:1.0\r\n");
      int var9 = this.index + 1;
      this.index = var9;

      while(true) {
         int var10 = this.index;
         int var11 = var1.length - 1;
         if(var10 >= var11) {
            var7 = true;
            break;
         }

         int var12 = this.index;
         String[] var13 = var1[var12].split(":", 2);
         String var14 = var13[0];
         String var15 = var13[1];
         String var16 = var14.trim();
         if("BEGIN".equals(var16)) {
            String var17 = var14.trim();
            if(!var14.equals(var17)) {
               var7 = false;
               break;
            }

            int var18 = this.index + 1;
            this.index = var18;
            if(!this.parseV20Component(var15, var2)) {
               var7 = false;
               break;
            }
         }

         int var19 = this.index + 1;
         this.index = var19;
      }

      return var7;
   }

   private boolean parseV20Component(String var1, StringBuilder var2) throws ArrayIndexOutOfBoundsException {
      String var3 = "END:" + var1;
      boolean var28;
      if(acceptableComponents.contains(var1)) {
         if(!"VEVENT".equals(var1) && !"VTODO".equals(var1)) {
            if("VALARM".equals(var1)) {
               var28 = false;
               return var28;
            }

            if(!"VTIMEZONE".equals(var1)) {
               var28 = false;
               return var28;
            }

            String var41;
            do {
               if(!this.hasTZ) {
                  String[] var31 = this.lines;
                  int var32 = this.index;
                  String[] var33 = var31[var32].split(":", 2);
                  String var34 = var33[0].split(";", 2)[0];
                  if("TZOFFSETFROM".equals(var34)) {
                     String var35 = var33[1];
                     String var36 = "TZ:" + var35 + "\r\n";
                     var2.append(var36);
                     this.hasTZ = (boolean)1;
                  }
               }

               int var38 = this.index + 1;
               this.index = var38;
               String[] var39 = this.lines;
               int var40 = this.index;
               var41 = var39[var40];
            } while(!var3.equals(var41));
         } else {
            String var4 = "BEGIN:" + var1 + "\r\n";
            var2.append(var4);

            while(true) {
               String[] var6 = this.lines;
               int var7 = this.index;
               String var8 = var6[var7];
               if(var3.equals(var8)) {
                  String var29 = var3 + "\r\n";
                  var2.append(var29);
                  break;
               }

               String[] var9 = this.lines;
               int var10 = this.index;
               String[] var11 = var9[var10].split(":", 2);
               String var12 = var11[0].split(";", 2)[0];
               String var13 = var11[1];
               String[] var14 = this.lines;
               int var15 = this.index;
               String var16 = var14[var15];
               if("".equals(var16)) {
                  StringBuilder var17 = var2.append("\r\n");
               } else if(acceptableV20Props.contains(var12)) {
                  String var19 = var12 + ":" + var13 + "\r\n";
                  var2.append(var19);
               } else {
                  String var21 = var12.trim();
                  if("BEGIN".equals(var21)) {
                     String var22 = var12.trim();
                     if(!var12.equals(var22) || !"VALARM".equals(var13)) {
                        var28 = false;
                        return var28;
                     }

                     StringBuilder var23 = var2.append("AALARM:default\r\n");

                     while(true) {
                        String[] var24 = this.lines;
                        int var25 = this.index;
                        String var26 = var24[var25];
                        if("END:VALARM".equals(var26)) {
                           break;
                        }

                        int var27 = this.index + 1;
                        this.index = var27;
                     }
                  }
               }

               int var18 = this.index + 1;
               this.index = var18;
            }
         }
      } else {
         while(true) {
            String[] var42 = this.lines;
            int var43 = this.index;
            String var44 = var42[var43];
            if(var3.equals(var44)) {
               break;
            }

            int var45 = this.index + 1;
            this.index = var45;
         }
      }

      var28 = true;
      return var28;
   }

   private String[] splitProperty(String var1) {
      return var1.replaceAll("\r\n", "\n").replaceAll("\n ", "").replaceAll("\n\t", "").split("\n");
   }

   public boolean parse(InputStream var1, String var2, VBuilder var3) throws IOException {
      byte[] var4 = new byte[var1.available()];
      var1.read(var4);
      String var6 = new String(var4);
      StringBuilder var7 = new StringBuilder("");
      String[] var8 = this.splitProperty(var6);
      this.lines = var8;
      this.index = 0;
      String[] var9 = this.lines;
      int var10 = this.index;
      String var11 = var9[var10];
      boolean var17;
      if("BEGIN:VCALENDAR".equals(var11)) {
         StringBuilder var12 = var7.append("BEGIN:VCALENDAR\r\n");
         int var13 = this.index + 1;
         this.index = var13;
         String[] var14 = this.lines;
         if(this.parseV20Calbody(var14, var7)) {
            int var15 = this.index;
            int var16 = this.lines.length - 1;
            if(var15 <= var16) {
               int var18 = this.lines.length - 1;
               int var19 = this.index;
               if(var18 == var19) {
                  String[] var20 = this.lines;
                  int var21 = this.index;
                  String var22 = var20[var21];
                  if("END:VCALENDAR".equals(var22)) {
                     StringBuilder var23 = var7.append("END:VCALENDAR\r\n");
                     byte[] var24 = var7.toString().getBytes();
                     ByteArrayInputStream var25 = new ByteArrayInputStream(var24);
                     var17 = super.parse(var25, var2, var3);
                     return var17;
                  }
               }

               var17 = false;
               return var17;
            }
         }

         var17 = false;
      } else {
         var17 = false;
      }

      return var17;
   }
}
