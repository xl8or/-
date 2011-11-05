package com.android.email.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import com.android.email.Account;
import com.android.email.Email;
import com.android.email.Preferences;
import java.net.URI;
import java.net.URISyntaxException;

public class DefaultAccountService extends Service {

   private static final String TAG = ">>> DefAccount";


   public DefaultAccountService() {}

   public static void actionGetDefaultAccount(Context var0, Intent var1) {
      StringBuilder var2 = (new StringBuilder()).append("action intent : ");
      Bundle var3 = var1.getExtras();
      String var4 = var2.append(var3).toString();
      Email.loge(">>> DefAccount", var4);
      Intent var5 = new Intent();
      var5.setClass(var0, DefaultAccountService.class);
      Intent var7 = var5.setAction("Get");
      var0.startService(var5);
   }

   public static void actionSetDefaultAccount(Context var0, Intent var1) {
      StringBuilder var2 = (new StringBuilder()).append("action intent : ");
      Bundle var3 = var1.getExtras();
      String var4 = var2.append(var3).toString();
      Email.loge(">>> DefAccount", var4);
      Intent var5 = new Intent();
      var5.setClass(var0, DefaultAccountService.class);
      Intent var7 = var5.setAction("Set");
      Bundle var8 = var1.getExtras();
      var5.putExtras(var8);
      var0.startService(var5);
   }

   private Account getCarrierAccount() {
      Email.loge(">>> DefAccount", "getCarrierAccount : ");
      return Preferences.getPreferences(this).getCarrierAccount();
   }

   private void onGetDefaultAccount() {
      // $FF: Couldn't be decompiled
   }

   private void onSetDefaultAccount(Intent var1) {
      Email.loge(">>> DefAccount", "onSetDefaultAccount : ");
      Account var2 = this.getCarrierAccount();
      String var4 = "provider_id";
      String var5 = var1.getStringExtra(var4);
      String var7 = "user_id";
      String var8 = var1.getStringExtra(var7);
      String var10 = "user_passwd";
      String var11 = var1.getStringExtra(var10);
      String var13 = "service";
      String var14 = var1.getStringExtra(var13);
      String var16 = "receive_host";
      String var17 = var1.getStringExtra(var16);
      String var19 = "receive_port";
      byte var20 = -1;
      int var21 = var1.getIntExtra(var19, var20);
      String var23 = "receive_security";
      String var24 = var1.getStringExtra(var23);
      String var26 = "user_id";
      String var27 = var1.getStringExtra(var26);
      String var29 = "user_passwd";
      String var30 = var1.getStringExtra(var29);
      String var32 = "send_host";
      String var33 = var1.getStringExtra(var32);
      String var35 = "send_port";
      byte var36 = -1;
      int var37 = var1.getIntExtra(var35, var36);
      String var39 = "send_from";
      String var40 = var1.getStringExtra(var39);
      String var42 = "send_security";
      String var43 = var1.getStringExtra(var42);
      String var45 = "send_auth";
      String var46 = var1.getStringExtra(var45);
      if(var5 != null) {
         StringBuilder var47 = (new StringBuilder()).append("provider_id : ");
         String var49 = var47.append(var5).toString();
         Email.loge(">>> DefAccount", var49);
      }

      if(var8 != null) {
         StringBuilder var50 = (new StringBuilder()).append("user_id : ");
         String var52 = var50.append(var8).toString();
         Email.loge(">>> DefAccount", var52);
      }

      if(var11 != null) {
         StringBuilder var53 = (new StringBuilder()).append("user_passwd : ");
         String var55 = var53.append(var11).toString();
         Email.loge(">>> DefAccount", var55);
      }

      if(var14 != null) {
         String var56 = "service : " + var14;
         Email.loge(">>> DefAccount", var56);
      }

      if(var17 != null) {
         StringBuilder var57 = (new StringBuilder()).append("receive_host : ");
         String var59 = var57.append(var17).toString();
         Email.loge(">>> DefAccount", var59);
      }

      String var60 = "receive_port : " + var21;
      Email.loge(">>> DefAccount", var60);
      if(var24 != null) {
         StringBuilder var61 = (new StringBuilder()).append("recv_security : ");
         String var63 = var61.append(var24).toString();
         Email.loge(">>> DefAccount", var63);
      }

      if(var33 != null) {
         StringBuilder var64 = (new StringBuilder()).append("send_host : ");
         String var66 = var64.append(var33).toString();
         Email.loge(">>> DefAccount", var66);
      }

      String var67 = "send_port : " + var37;
      Email.loge(">>> DefAccount", var67);
      if(var40 != null) {
         StringBuilder var68 = (new StringBuilder()).append("send_from : ");
         String var70 = var68.append(var40).toString();
         Email.loge(">>> DefAccount", var70);
      }

      if(var43 != null) {
         StringBuilder var71 = (new StringBuilder()).append("send_security : ");
         String var73 = var71.append(var43).toString();
         Email.loge(">>> DefAccount", var73);
      }

      if(var46 != null) {
         StringBuilder var74 = (new StringBuilder()).append("send_auth : ");
         String var76 = var74.append(var46).toString();
         Email.loge(">>> DefAccount", var76);
      }

      if(var2 == null) {
         var2 = new Account(this);
         Preferences var79 = Preferences.getPreferences(this);
         String var80 = var2.getUuid();
         var79.setCarrierAccountId(var80);
         StringBuilder var81 = (new StringBuilder()).append("onSetDefaultAccount : mAccount.getUuid()");
         String var82 = var2.getUuid();
         String var83 = var81.append(var82).toString();
         Email.loge(">>> DefAccount", var83);
      }

      if(Preferences.getPreferences(this).getCPUpdateMessage() == 0 && var2 != null) {
         Preferences.getPreferences(this).setCPUpdateMessage(1);
      }

      URI var141;
      label157: {
         if(var17 != null) {
            String var85 = "";
            if(!var17.equals(var85)) {
               String var86 = "";
               if(!var86.equals(var8)) {
                  String var88 = "";
                  String var89 = "";
                  if(var8 != null) {
                     String var91 = "@";
                     String[] var92 = var8.split(var91);
                     var88 = var8;
                     String var93 = "";
                     if(var93.equals(var27)) {
                        var27 = var8;
                     }

                     if(var92.length > 1) {
                        String var95 = "";
                        if(!var95.equals(var8)) {
                           var89 = var92[1].trim();
                           var2.setEmail(var8);
                        }
                     }
                  }

                  String var99 = "";
                  if(var99.equals(var30)) {
                     var30 = var11;
                  }

                  StringBuilder var101 = (new StringBuilder()).append("usr : ");
                  String var103 = var101.append(var88).toString();
                  Email.loge(">>> DefAccount", var103);
                  StringBuilder var104 = (new StringBuilder()).append("domain : ");
                  String var106 = var104.append(var89).toString();
                  Email.loge(">>> DefAccount", var106);
                  int var108 = 2131166183;
                  String var109 = this.getString(var108);
                  var2.setDraftsFolderName(var109);
                  int var113 = 2131166184;
                  String var114 = this.getString(var113);
                  var2.setTrashFolderName(var114);
                  int var118 = 2131166182;
                  String var119 = this.getString(var118);
                  var2.setOutboxFolderName(var119);
                  int var123 = 2131166185;
                  String var124 = this.getString(var123);
                  var2.setSentFolderName(var124);
                  byte var128 = 15;
                  var2.setAutomaticCheckIntervalMinutes(var128);
                  if(var24 != null) {
                     String var130 = "ssl";
                     byte var131;
                     if(var24.contains(var130)) {
                        var131 = 1;
                     } else {
                        var131 = 0;
                     }

                     var2.setSecurityFlags(var131);
                  } else {
                     byte var173 = 0;
                     var2.setSecurityFlags(var173);
                  }

                  if(var21 == -1) {
                     if("pop3".equals(var14)) {
                        if(var2.getSecurityFlags() == 1) {
                           var21 = 995;
                        } else {
                           var21 = 110;
                        }
                     } else if(var2.getSecurityFlags() == 1) {
                        var21 = 993;
                     } else {
                        var21 = 143;
                     }
                  }

                  if(var2.getSecurityFlags() == 1) {
                     String var134 = var14 + "+ssl+";
                  }

                  try {
                     StringBuilder var135 = new StringBuilder();
                     StringBuilder var137 = var135.append(var27).append(":");
                     String var139 = var137.append(var11).toString();
                     String var140 = var17.trim();
                     var141 = new URI(var14, var139, var140, var21, (String)null, (String)null, (String)null);
                  } catch (URISyntaxException var200) {
                     var200.printStackTrace();
                     return;
                  }

                  String var142 = "1. uri recv : " + var141;
                  Email.loge(">>> DefAccount", var142);
                  String var143 = var141.toString();
                  var2.setStoreUri(var143);
                  var2.setDescription(var89);
                  var2.setName(var27);
                  var2.setPasswd(var30);
                  byte var153 = 1;
                  var2.setNotifyNewMail((boolean)var153);
                  var33 = var2.getSendAddr();
                  var37 = var2.getSendPort();
                  String var154 = var2.getSecurityAuth();
               }
               break label157;
            }
         }

         var27 = var2.getName();
         StringBuilder var174 = (new StringBuilder()).append("send : user_name ");
         String var176 = var174.append(var27).toString();
         Email.loge(">>> DefAccount", var176);
         var30 = var2.getPasswd();
         StringBuilder var177 = (new StringBuilder()).append("send : user_passwd ");
         String var179 = var177.append(var30).toString();
         Email.loge(">>> DefAccount", var179);
         byte var181 = 1;
         var2.setNotifyNewMail((boolean)var181);
         var2.setSendAddr(var33);
         var2.setSecurityAuth(var46);
         if(var2.getEmail() == null && var40 != null) {
            String var187 = "";
            if(!var40.equals(var187)) {
               var2.setEmail(var40);
            }
         }

         if(var43 != null) {
            String var191 = "ssl";
            byte var192;
            if(var43.contains(var191)) {
               var192 = 1;
            } else {
               var192 = 0;
            }

            var2.setSendSecurityFlags(var192);
         } else {
            byte var198 = 0;
            var2.setSendSecurityFlags(var198);
         }

         if(var37 == -1) {
            if(var2.getSendSecurityFlags() == 1) {
               var37 = 465;
            } else {
               var37 = 25;
            }
         }

         var2.setSendPort(var37);
      }

      if(var2.getSendSecurityFlags() == 1) {
         var14 = "smtp+ssl+";
      } else {
         var14 = "smtp";
      }

      if(var33 != null) {
         String var156 = "";
         if(!var33.equals(var156)) {
            try {
               var141 = new URI;
               StringBuilder var157 = new StringBuilder();
               StringBuilder var159 = var157.append(var27).append(":");
               String var161 = var159.append(var30).toString();
               String var162 = var33.trim();
               var141.<init>(var14, var161, var162, var37, (String)null, (String)null, (String)null);
            } catch (URISyntaxException var199) {
               var199.printStackTrace();
               return;
            }

            String var165 = "2. uri send : " + var141;
            Email.loge(">>> DefAccount", var165);
            String var166 = var141.toString();
            var2.setSenderUri(var166);
         }
      }

      Preferences var169 = Preferences.getPreferences(this);
      var2.save(var169);
      Email.setNotifyUiAccountsChanged((boolean)1);
   }

   public IBinder onBind(Intent var1) {
      return null;
   }

   public void onStart(Intent var1, int var2) {
      super.onStart(var1, var2);
      if(var1 != null) {
         StringBuilder var3 = (new StringBuilder()).append("intent : ");
         Bundle var4 = var1.getExtras();
         String var5 = var3.append(var4).toString();
         Email.loge(">>> DefAccount", var5);
         if(var1.getAction().equals("Get")) {
            this.onGetDefaultAccount();
         } else if(var1.getAction().equals("Set")) {
            this.onSetDefaultAccount(var1);
         }
      }
   }
}
