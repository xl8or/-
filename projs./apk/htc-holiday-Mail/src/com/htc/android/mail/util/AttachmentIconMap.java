package com.htc.android.mail.util;

import com.htc.android.mail.Mail;
import com.htc.android.mail.MailCommon;
import java.util.HashMap;

public class AttachmentIconMap {

   private static final boolean DEBUG = Mail.MAIL_DEBUG;
   private static final int LOWERCASE = 0;
   private static final int ORIGINALCASE = 2;
   private static final String TAG = "AttachmentIconMap";
   private static final int UPPERCASE = 1;
   static HashMap<String, Integer> attachmentIconMap = new HashMap();
   public static final int defaultAttachmentIcon = 2130837527;
   public static final int defaultImageAttachmentIcon = 2130837529;
   public static final int defaultVideoAttachmentIcon = 2130837532;


   static {
      HashMap var0 = attachmentIconMap;
      Integer var1 = Integer.valueOf(2130837529);
      var0.put("jpg", var1);
      HashMap var3 = attachmentIconMap;
      Integer var4 = Integer.valueOf(2130837529);
      var3.put("jpe", var4);
      HashMap var6 = attachmentIconMap;
      Integer var7 = Integer.valueOf(2130837529);
      var6.put("jpeg", var7);
      HashMap var9 = attachmentIconMap;
      Integer var10 = Integer.valueOf(2130837529);
      var9.put("png", var10);
      HashMap var12 = attachmentIconMap;
      Integer var13 = Integer.valueOf(2130837529);
      var12.put("bmp", var13);
      HashMap var15 = attachmentIconMap;
      Integer var16 = Integer.valueOf(2130837529);
      var15.put("gif", var16);
      HashMap var18 = attachmentIconMap;
      Integer var19 = Integer.valueOf(2130837529);
      var18.put("tif", var19);
      HashMap var21 = attachmentIconMap;
      Integer var22 = Integer.valueOf(2130837529);
      var21.put("jps", var22);
      HashMap var24 = attachmentIconMap;
      Integer var25 = Integer.valueOf(2130837529);
      var24.put("mpo", var25);
      HashMap var27 = attachmentIconMap;
      Integer var28 = Integer.valueOf(2130837525);
      var27.put("mp3", var28);
      HashMap var30 = attachmentIconMap;
      Integer var31 = Integer.valueOf(2130837525);
      var30.put("wav", var31);
      HashMap var33 = attachmentIconMap;
      Integer var34 = Integer.valueOf(2130837525);
      var33.put("m4a", var34);
      HashMap var36 = attachmentIconMap;
      Integer var37 = Integer.valueOf(2130837525);
      var36.put("mp3", var37);
      HashMap var39 = attachmentIconMap;
      Integer var40 = Integer.valueOf(2130837525);
      var39.put("mid", var40);
      HashMap var42 = attachmentIconMap;
      Integer var43 = Integer.valueOf(2130837525);
      var42.put("midi", var43);
      HashMap var45 = attachmentIconMap;
      Integer var46 = Integer.valueOf(2130837525);
      var45.put("kar", var46);
      HashMap var48 = attachmentIconMap;
      Integer var49 = Integer.valueOf(2130837525);
      var48.put("amr", var49);
      HashMap var51 = attachmentIconMap;
      Integer var52 = Integer.valueOf(2130837525);
      var51.put("ogg", var52);
      HashMap var54 = attachmentIconMap;
      Integer var55 = Integer.valueOf(2130837525);
      var54.put("imy", var55);
      HashMap var57 = attachmentIconMap;
      Integer var58 = Integer.valueOf(2130837525);
      var57.put("wma", var58);
      HashMap var60 = attachmentIconMap;
      Integer var61 = Integer.valueOf(2130837532);
      var60.put("mp4", var61);
      HashMap var63 = attachmentIconMap;
      Integer var64 = Integer.valueOf(2130837532);
      var63.put("3gp", var64);
      HashMap var66 = attachmentIconMap;
      Integer var67 = Integer.valueOf(2130837532);
      var66.put("wmv", var67);
      HashMap var69 = attachmentIconMap;
      Integer var70 = Integer.valueOf(2130837532);
      var69.put("avi", var70);
      HashMap var72 = attachmentIconMap;
      Integer var73 = Integer.valueOf(2130837532);
      var72.put("3gpp", var73);
      HashMap var75 = attachmentIconMap;
      Integer var76 = Integer.valueOf(2130837532);
      var75.put("3g2", var76);
      HashMap var78 = attachmentIconMap;
      Integer var79 = Integer.valueOf(2130837532);
      var78.put("mpe", var79);
      HashMap var81 = attachmentIconMap;
      Integer var82 = Integer.valueOf(2130837532);
      var81.put("mpeg", var82);
      HashMap var84 = attachmentIconMap;
      Integer var85 = Integer.valueOf(2130837532);
      var84.put("mpg", var85);
      HashMap var87 = attachmentIconMap;
      Integer var88 = Integer.valueOf(2130837532);
      var87.put("mov", var88);
      HashMap var90 = attachmentIconMap;
      Integer var91 = Integer.valueOf(2130837532);
      var90.put("qt", var91);
      HashMap var93 = attachmentIconMap;
      Integer var94 = Integer.valueOf(2130837532);
      var93.put("asf", var94);
      HashMap var96 = attachmentIconMap;
      Integer var97 = Integer.valueOf(2130837532);
      var96.put("wmv", var97);
      HashMap var99 = attachmentIconMap;
      Integer var100 = Integer.valueOf(2130837528);
      var99.put("pdf", var100);
      HashMap var102 = attachmentIconMap;
      Integer var103 = Integer.valueOf(2130837526);
      var102.put("doc", var103);
      HashMap var105 = attachmentIconMap;
      Integer var106 = Integer.valueOf(2130837526);
      var105.put("xls", var106);
      HashMap var108 = attachmentIconMap;
      Integer var109 = Integer.valueOf(2130837526);
      var108.put("ppt", var109);
      HashMap var111 = attachmentIconMap;
      Integer var112 = Integer.valueOf(2130837526);
      var111.put("docx", var112);
      HashMap var114 = attachmentIconMap;
      Integer var115 = Integer.valueOf(2130837526);
      var114.put("xlsx", var115);
      HashMap var117 = attachmentIconMap;
      Integer var118 = Integer.valueOf(2130837526);
      var117.put("pptx", var118);
      HashMap var120 = attachmentIconMap;
      Integer var121 = Integer.valueOf(2130837526);
      var120.put("docm", var121);
      HashMap var123 = attachmentIconMap;
      Integer var124 = Integer.valueOf(2130837526);
      var123.put("xlsm", var124);
      HashMap var126 = attachmentIconMap;
      Integer var127 = Integer.valueOf(2130837526);
      var126.put("pptm", var127);
      HashMap var129 = attachmentIconMap;
      Integer var130 = Integer.valueOf(2130837526);
      var129.put("pps", var130);
      HashMap var132 = attachmentIconMap;
      Integer var133 = Integer.valueOf(2130837526);
      var132.put("txt", var133);
      HashMap var135 = attachmentIconMap;
      Integer var136 = Integer.valueOf(2130837533);
      var135.put("zip", var136);
      HashMap var138 = attachmentIconMap;
      Integer var139 = Integer.valueOf(2130837524);
      var138.put("eml", var139);
      HashMap var141 = attachmentIconMap;
      Integer var142 = Integer.valueOf(2130837531);
      var141.put("vcf", var142);
      HashMap var144 = attachmentIconMap;
      Integer var145 = Integer.valueOf(2130837530);
      var144.put("vcs", var145);
   }

   public AttachmentIconMap() {}

   public static int getFileIconResource(String var0) {
      int var1;
      if(var0 == null) {
         var1 = 2130837527;
      } else {
         String var2 = MailCommon.getSubFilename(var0);
         Integer var3 = (Integer)attachmentIconMap.get(var2);
         if(var3 == null) {
            var1 = 2130837527;
         } else {
            var1 = var3.intValue();
         }
      }

      return var1;
   }
}
