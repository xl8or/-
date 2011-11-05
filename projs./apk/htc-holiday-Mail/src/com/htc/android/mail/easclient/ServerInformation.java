package com.htc.android.mail.easclient;

import android.text.TextUtils;

public class ServerInformation {

   public static final int ACCOUNT_TYPE_FULL_EMAIL = 2;
   public static final int ACCOUNT_TYPE_USERNAME = 1;
   public static ServerInformation[] autocompleteServer;
   int accountType = 2;
   String domain;
   String maildomain;
   String provider;
   String serverAddress;
   boolean useSSL;


   static {
      ServerInformation[] var0 = new ServerInformation[49];
      ServerInformation var1 = new ServerInformation("Hotmail", "@hotmail.com", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[0] = var1;
      ServerInformation var2 = new ServerInformation("Hotmail", "@hotmail.com.au", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[1] = var2;
      ServerInformation var3 = new ServerInformation("Hotmail", "@live.com.au", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[2] = var3;
      ServerInformation var4 = new ServerInformation("Hotmail", "@live.cn", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[3] = var4;
      ServerInformation var5 = new ServerInformation("Hotmail", "@live.com", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[4] = var5;
      ServerInformation var6 = new ServerInformation("Hotmail", "@hotmail.co.in", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[5] = var6;
      ServerInformation var7 = new ServerInformation("Hotmail", "@hotmail.co.jp", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[6] = var7;
      ServerInformation var8 = new ServerInformation("Hotmail", "@live.jp", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[7] = var8;
      ServerInformation var9 = new ServerInformation("Hotmail", "@hotmail.co.kr", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[8] = var9;
      ServerInformation var10 = new ServerInformation("Hotmail", "@hotmail.com.hk", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[9] = var10;
      ServerInformation var11 = new ServerInformation("Hotmail", "@hotmail.com.tw", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[10] = var11;
      ServerInformation var12 = new ServerInformation("Hotmail", "@livemail.tw", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[11] = var12;
      ServerInformation var13 = new ServerInformation("Hotmail", "@hotmail.sg", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[12] = var13;
      ServerInformation var14 = new ServerInformation("Hotmail", "@hotmail.my", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[13] = var14;
      ServerInformation var15 = new ServerInformation("Hotmail", "@hotmail.co.th", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[14] = var15;
      ServerInformation var16 = new ServerInformation("Hotmail", "@hotmail.co.id", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[15] = var16;
      ServerInformation var17 = new ServerInformation("Hotmail", "@hotmail.ph", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[16] = var17;
      ServerInformation var18 = new ServerInformation("Hotmail", "@hotmail.com.vn", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[17] = var18;
      ServerInformation var19 = new ServerInformation("Hotmail", "@hotmail.be", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[18] = var19;
      ServerInformation var20 = new ServerInformation("Hotmail", "@live.be", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[19] = var20;
      ServerInformation var21 = new ServerInformation("Hotmail", "@live.dk", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[20] = var21;
      ServerInformation var22 = new ServerInformation("Hotmail", "@hotmail.dk", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[21] = var22;
      ServerInformation var23 = new ServerInformation("Hotmail", "@hotmail.fi", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[22] = var23;
      ServerInformation var24 = new ServerInformation("Hotmail", "@hotmail.fr", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[23] = var24;
      ServerInformation var25 = new ServerInformation("Hotmail", "@live.fr", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[24] = var25;
      ServerInformation var26 = new ServerInformation("Hotmail", "@hotmail.de", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[25] = var26;
      ServerInformation var27 = new ServerInformation("Hotmail", "@live.de", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[26] = var27;
      ServerInformation var28 = new ServerInformation("Hotmail", "@hotmail.it", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[27] = var28;
      ServerInformation var29 = new ServerInformation("Hotmail", "@live.it", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[28] = var29;
      ServerInformation var30 = new ServerInformation("Hotmail", "@live.nl", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[29] = var30;
      ServerInformation var31 = new ServerInformation("Hotmail", "@hotmail.no", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[30] = var31;
      ServerInformation var32 = new ServerInformation("Hotmail", "@live.no", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[31] = var32;
      ServerInformation var33 = new ServerInformation("Hotmail", "@hotmail.es", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[32] = var33;
      ServerInformation var34 = new ServerInformation("Hotmail", "@hotmail.se", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[33] = var34;
      ServerInformation var35 = new ServerInformation("Hotmail", "@live.se", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[34] = var35;
      ServerInformation var36 = new ServerInformation("Hotmail", "@hotmail.ch", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[35] = var36;
      ServerInformation var37 = new ServerInformation("Hotmail", "@hotmail.com.tr", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[36] = var37;
      ServerInformation var38 = new ServerInformation("Hotmail", "@hotmail.co.uk", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[37] = var38;
      ServerInformation var39 = new ServerInformation("Hotmail", "@live.co.uk", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[38] = var39;
      ServerInformation var40 = new ServerInformation("Hotmail", "@hotmail.sk", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[39] = var40;
      ServerInformation var41 = new ServerInformation("Hotmail", "@hotmail.cz", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[40] = var41;
      ServerInformation var42 = new ServerInformation("Hotmail", "@hotmail.gr", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[41] = var42;
      ServerInformation var43 = new ServerInformation("Hotmail", "@hotmail.lv", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[42] = var43;
      ServerInformation var44 = new ServerInformation("Hotmail", "@hotmail.lt", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[43] = var44;
      ServerInformation var45 = new ServerInformation("Hotmail", "@hotmail.rs", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[44] = var45;
      ServerInformation var46 = new ServerInformation("Hotmail", "@hotmail.co.za", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[45] = var46;
      ServerInformation var47 = new ServerInformation("Hotmail", "@hotmail.co.nz", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[46] = var47;
      ServerInformation var48 = new ServerInformation("Hotmail", "@hotmail.com.br", "m.hotmail.com", (String)null, (boolean)1, 2);
      var0[47] = var48;
      ServerInformation var49 = new ServerInformation("GMail", "@gmail.com", "m.google.com", (String)null, (boolean)1, 2);
      var0[48] = var49;
      autocompleteServer = var0;
   }

   public ServerInformation(String var1, String var2, String var3, String var4, boolean var5, int var6) {
      this.provider = var1;
      this.maildomain = var2;
      this.serverAddress = var3;
      this.domain = var4;
      this.useSSL = var5;
      this.accountType = var6;
   }

   public static String getAccountName(String var0, ServerInformation var1) {
      String var2;
      if(TextUtils.isEmpty(var0)) {
         var2 = null;
      } else if(var1.accountType == 2) {
         var2 = var0;
      } else if(var1.accountType == 1) {
         int var3 = var0.indexOf(64);
         var2 = var0.substring(0, var3);
      } else {
         var2 = null;
      }

      return var2;
   }

   public static ServerInformation getServerInfoByMail(String var0) {
      ServerInformation var1;
      if(TextUtils.isEmpty(var0)) {
         var1 = null;
      } else {
         int var2 = 0;

         while(true) {
            int var3 = autocompleteServer.length;
            if(var2 >= var3) {
               var1 = null;
               break;
            }

            String var4 = var0.toLowerCase();
            String var5 = autocompleteServer[var2].maildomain.toLowerCase();
            if(var4.contains(var5)) {
               var1 = autocompleteServer[var2];
               break;
            }

            ++var2;
         }
      }

      return var1;
   }
}
