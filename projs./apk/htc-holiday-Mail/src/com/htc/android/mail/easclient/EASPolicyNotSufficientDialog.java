package com.htc.android.mail.easclient;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.htc.android.mail.Mail;
import com.htc.android.mail.eassvc.pim.ExchangeAccount;
import com.htc.android.mail.eassvc.provision.EASProvision;
import com.htc.android.mail.eassvc.provision.EASProvisionDoc;
import com.htc.android.mail.eassvc.provision.ProvisionUtil;
import com.htc.android.mail.eassvc.util.EASLog;
import java.util.Iterator;

public class EASPolicyNotSufficientDialog extends ListActivity {

   private static final boolean DEBUG = Mail.EAS_DEBUG;
   private static final int DIALOG_POLICY_DETAIL = 2;
   private static final int DIALOG_WARNING_DESCRIPT = 1;
   public static final int MODE_SHOW_DETAIL = 1;
   public static final int MODE_SHOW_WARNING = 0;
   private static final String TAG = "EASPolicyNotSufficientDialog";
   private Context mContext = null;
   ExchangeAccount mExAccount = null;
   int mMode = 0;
   EASProvisionDoc mProvisionDoc = null;


   public EASPolicyNotSufficientDialog() {}

   String getPolicyDescript(String var1) {
      String var2 = EASProvision.DeviceEncryptionEnabled;
      String var3;
      if(var1.equals(var2)) {
         var3 = this.getString(2131362525);
      } else {
         String var4 = EASProvision.RequireDeviceEncryption;
         if(var1.equals(var4)) {
            var3 = this.getString(2131362526);
         } else {
            String var5 = EASProvision.RequireSignedSMIMEMessages;
            if(var1.equals(var5)) {
               var3 = this.getString(2131362527);
            } else {
               String var6 = EASProvision.RequireEncryptedSMIMEMessages;
               if(var1.equals(var6)) {
                  var3 = this.getString(2131362528);
               } else {
                  String var7 = EASProvision.RequireSignedSMIMEAlgorithm;
                  if(var1.equals(var7)) {
                     var3 = this.getString(2131362529);
                  } else {
                     String var8 = EASProvision.RequireEncryptionSMIMEAlgorithm;
                     if(var1.equals(var8)) {
                        var3 = this.getString(2131362530);
                     } else {
                        String var9 = EASProvision.AllowSMIMEEncryptionAlgorithmNegotiation;
                        if(var1.equals(var9)) {
                           var3 = this.getString(2131362531);
                        } else {
                           String var10 = EASProvision.AllowStorageCard;
                           if(var1.equals(var10)) {
                              var3 = this.getString(2131362532);
                           } else {
                              String var11 = EASProvision.AllowCamera;
                              if(var1.equals(var11)) {
                                 var3 = this.getString(2131362533);
                              } else {
                                 String var12 = EASProvision.AllowUnsignedApplications;
                                 if(var1.equals(var12)) {
                                    var3 = this.getString(2131362534);
                                 } else {
                                    String var13 = EASProvision.AllowUnsignedInstallationPackages;
                                    if(var1.equals(var13)) {
                                       var3 = this.getString(2131362535);
                                    } else {
                                       String var14 = EASProvision.AllowWiFi;
                                       if(var1.equals(var14)) {
                                          var3 = this.getString(2131362536);
                                       } else {
                                          String var15 = EASProvision.AllowTextMessaging;
                                          if(var1.equals(var15)) {
                                             var3 = this.getString(2131362537);
                                          } else {
                                             String var16 = EASProvision.AllowPOPIMAPEmail;
                                             if(var1.equals(var16)) {
                                                var3 = this.getString(2131362538);
                                             } else {
                                                String var17 = EASProvision.AllowBluetooth;
                                                if(var1.equals(var17)) {
                                                   var3 = this.getString(2131362539);
                                                } else {
                                                   String var18 = EASProvision.AllowIrDA;
                                                   if(var1.equals(var18)) {
                                                      var3 = this.getString(2131362540);
                                                   } else {
                                                      String var19 = EASProvision.AllowDesktopSync;
                                                      if(var1.equals(var19)) {
                                                         var3 = this.getString(2131362541);
                                                      } else {
                                                         String var20 = EASProvision.AllowSMIMESoftCerts;
                                                         if(var1.equals(var20)) {
                                                            var3 = this.getString(2131362542);
                                                         } else {
                                                            String var21 = EASProvision.AllowBrowser;
                                                            if(var1.equals(var21)) {
                                                               var3 = this.getString(2131362543);
                                                            } else {
                                                               String var22 = EASProvision.AllowConsumerEmail;
                                                               if(var1.equals(var22)) {
                                                                  var3 = this.getString(2131362544);
                                                               } else {
                                                                  String var23 = EASProvision.AllowRemoteDesktop;
                                                                  if(var1.equals(var23)) {
                                                                     var3 = this.getString(2131362545);
                                                                  } else {
                                                                     String var24 = EASProvision.AllowInternetSharing;
                                                                     if(var1.equals(var24)) {
                                                                        var3 = this.getString(2131362546);
                                                                     } else {
                                                                        String var25 = EASProvision.ApprovedApplicationList;
                                                                        if(var1.equals(var25)) {
                                                                           var3 = this.getString(2131362547);
                                                                        } else {
                                                                           String var26 = EASProvision.UnapprovedInROMApplicationList;
                                                                           if(var1.equals(var26)) {
                                                                              var3 = this.getString(2131362548);
                                                                           } else {
                                                                              var3 = null;
                                                                           }
                                                                        }
                                                                     }
                                                                  }
                                                               }
                                                            }
                                                         }
                                                      }
                                                   }
                                                }
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return var3;
   }

   Dialog getPolicyIncompatibleDetailDialog() {
      View var1 = LayoutInflater.from(this.mContext).inflate(2130903079, (ViewGroup)null);
      LinearLayout var2 = (LinearLayout)var1.findViewById(2131296528);

      TextView var6;
      for(Iterator var3 = ProvisionUtil.getUnsupportList(this.mProvisionDoc).iterator(); var3.hasNext(); var2.addView(var6)) {
         String var4 = (String)var3.next();
         Context var5 = this.mContext;
         var6 = new TextView(var5);
         String var7 = this.getPolicyDescript(var4);
         if(var7 != null) {
            var6.setText(var7);
         }
      }

      Context var8 = this.mContext;
      Builder var9 = (new Builder(var8)).setTitle(2131362460).setIcon(17301543).setView(var1);
      EASPolicyNotSufficientDialog.5 var10 = new EASPolicyNotSufficientDialog.5();
      Builder var11 = var9.setPositiveButton(2131362432, var10).setCancelable((boolean)1);
      EASPolicyNotSufficientDialog.4 var12 = new EASPolicyNotSufficientDialog.4();
      return var11.setOnCancelListener(var12).create();
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      if(DEBUG) {
         EASLog.d("EASPolicyNotSufficientDialog", "- onCreate()");
      }

      this.mContext = this;
      EASProvisionDoc var2 = (EASProvisionDoc)this.getIntent().getParcelableExtra("provisionDoc");
      this.mProvisionDoc = var2;
      if(this.mProvisionDoc == null) {
         EASLog.e("EASPolicyNotSufficientDialog", "onCreate(): Error, mProvisionDoc is null");
         this.finish();
      } else {
         int var3 = this.getIntent().getIntExtra("mode", 0);
         this.mMode = var3;
         if(this.mMode == 1) {
            this.showDialog(2);
         } else {
            this.showDialog(1);
         }
      }
   }

   protected Dialog onCreateDialog(int var1) {
      if(DEBUG) {
         ExchangeAccount var2 = this.mExAccount;
         String var3 = "onCreateDialog: " + var1;
         EASLog.d("EASPolicyNotSufficientDialog", var2, var3);
      }

      Object var4;
      switch(var1) {
      case 1:
         String var5 = this.getString(2131362521);
         StringBuilder var6 = (new StringBuilder()).append(var5).append("\r\n\r\n");
         String var7 = this.getString(2131362524);
         String var8 = var6.append(var7).toString();
         Context var9 = this.mContext;
         com.htc.widget.HtcAlertDialog.Builder var10 = (new com.htc.widget.HtcAlertDialog.Builder(var9)).setTitle(2131362520).setMessage(var8);
         EASPolicyNotSufficientDialog.3 var11 = new EASPolicyNotSufficientDialog.3();
         com.htc.widget.HtcAlertDialog.Builder var12 = var10.setPositiveButton(2131362432, var11);
         EASPolicyNotSufficientDialog.2 var13 = new EASPolicyNotSufficientDialog.2();
         com.htc.widget.HtcAlertDialog.Builder var14 = var12.setNegativeButton(2131362522, var13).setCancelable((boolean)1);
         EASPolicyNotSufficientDialog.1 var15 = new EASPolicyNotSufficientDialog.1();
         var4 = var14.setOnCancelListener(var15).create();
         break;
      case 2:
         var4 = this.getPolicyIncompatibleDetailDialog();
         break;
      default:
         var4 = super.onCreateDialog(var1, (Bundle)null);
      }

      return (Dialog)var4;
   }

   class 5 implements OnClickListener {

      5() {}

      public void onClick(DialogInterface var1, int var2) {
         if(EASPolicyNotSufficientDialog.this.mMode == 0) {
            EASPolicyNotSufficientDialog.this.showDialog(1);
         } else {
            EASPolicyNotSufficientDialog.this.finish();
         }
      }
   }

   class 3 implements OnClickListener {

      3() {}

      public void onClick(DialogInterface var1, int var2) {
         EASPolicyNotSufficientDialog.this.finish();
      }
   }

   class 4 implements OnCancelListener {

      4() {}

      public void onCancel(DialogInterface var1) {
         if(EASPolicyNotSufficientDialog.this.mMode == 0) {
            EASPolicyNotSufficientDialog.this.showDialog(1);
         } else {
            EASPolicyNotSufficientDialog.this.finish();
         }
      }
   }

   class 1 implements OnCancelListener {

      1() {}

      public void onCancel(DialogInterface var1) {
         EASPolicyNotSufficientDialog.this.finish();
      }
   }

   class 2 implements OnClickListener {

      2() {}

      public void onClick(DialogInterface var1, int var2) {
         EASPolicyNotSufficientDialog.this.showDialog(2);
      }
   }
}
