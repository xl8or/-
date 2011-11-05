package com.htc.android.mail.easclient;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.net.http.SslCertificate;
import android.net.http.SslCertificate.DName;
import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.htc.android.mail.Mail;
import com.htc.android.mail.eassvc.common.EASCommon;
import com.htc.android.mail.eassvc.pim.IEASService;
import com.htc.android.mail.eassvc.util.EASLog;
import com.htc.app.HtcProgressDialog;
import com.htc.widget.HtcAlertDialog.Builder;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class CertificateUtil {

   private static final boolean DEBUG = Mail.EAS_DEBUG;
   public static final int DIALOG_GET_CERTIFICATE_ERROR = 315;
   public static final int DIALOG_PROGRESS_SAVE_KEYSTORE = 300;
   public static final int DIALOG_TEST_CERTIFICATE_NOT_TRUST = 310;
   public static final int DIALOG_TEST_VIEW_CERTIFICATE = 311;
   public static final String EXTRA_CANCEL_ACTION_MESSAGE = "extra.message.cancel";
   public static final String EXTRA_CONTINUE_ACTION_MESSAGE = "extra.message.continue";
   public static final String TAG = "CertificateUtil";
   private static IEASService mService;


   public CertificateUtil() {}

   private static EASCommon.CertificateError getCertificateError() {
      EASCommon.CertificateError var1;
      try {
         FileInputStream var0 = new FileInputStream("/data/data/com.htc.android.mail/CerError");
         var1 = (EASCommon.CertificateError)(new ObjectInputStream(var0)).readObject();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

      return var1;
   }

   public static Dialog handleCreateDialog(Activity var0, int var1, long var2, String var4, Bundle var5) {
      Object var6;
      switch(var1) {
      case 300:
         HtcProgressDialog var7 = new HtcProgressDialog(var0);
         var7.setIndeterminate((boolean)1);
         CharSequence var8 = var0.getText(2131362428);
         var7.setMessage(var8);
         var7.setCancelable((boolean)0);
         var6 = var7;
         break;
      case 310:
         CertificateUtil.ButtonEvent var9 = new CertificateUtil.ButtonEvent();
         CertificateUtil.1 var15 = new CertificateUtil.1(var5, var2, var4, var0);
         var9.positiveClick = var15;
         CertificateUtil.2 var16 = new CertificateUtil.2(var0);
         var9.neutralClick = var16;
         CertificateUtil.3 var17 = new CertificateUtil.3(var5, var0);
         var9.negativeClick = var17;
         var6 = showSSLCertificateError(var0, var9, var2, var4, var5);
         break;
      case 311:
         CertificateUtil.ButtonEvent var24 = new CertificateUtil.ButtonEvent();
         CertificateUtil.4 var25 = new CertificateUtil.4(var0);
         var24.positiveClick = var25;
         var6 = showSSLCertificateDetail(var0, var24, var2, var4, var5);
         break;
      case 315:
         Builder var31 = (new Builder(var0)).setTitle(2131361925);
         CharSequence var32 = var0.getText(2131362209);
         Builder var33 = var31.setMessage(var32);
         CertificateUtil.5 var34 = new CertificateUtil.5(var0);
         var6 = var33.setPositiveButton(2131362432, var34).create();
         break;
      default:
         var6 = null;
      }

      return (Dialog)var6;
   }

   private static View inflateCertificateView(Context var0, SslCertificate var1) {
      View var2;
      if(var1 == null) {
         var2 = null;
      } else {
         View var3 = LayoutInflater.from(var0).inflate(2130903095, (ViewGroup)null);
         DName var4 = var1.getIssuedTo();
         if(var4 != null) {
            TextView var5 = (TextView)var3.findViewById(2131296584);
            String var6 = var4.getCName();
            var5.setText(var6);
            TextView var7 = (TextView)var3.findViewById(2131296586);
            String var8 = var4.getOName();
            var7.setText(var8);
            TextView var9 = (TextView)var3.findViewById(2131296588);
            String var10 = var4.getUName();
            var9.setText(var10);
         }

         DName var11 = var1.getIssuedBy();
         if(var11 != null) {
            TextView var12 = (TextView)var3.findViewById(2131296590);
            String var13 = var11.getCName();
            var12.setText(var13);
            TextView var14 = (TextView)var3.findViewById(2131296592);
            String var15 = var11.getOName();
            var14.setText(var15);
            TextView var16 = (TextView)var3.findViewById(2131296594);
            String var17 = var11.getUName();
            var16.setText(var17);
         }

         String var18 = var1.getValidNotBefore();
         String var19 = reformatCertificateDate(var0, var18);
         ((TextView)var3.findViewById(2131296598)).setText(var19);
         String var20 = var1.getValidNotAfter();
         String var21 = reformatCertificateDate(var0, var20);
         ((TextView)var3.findViewById(2131296601)).setText(var21);
         var2 = var3;
      }

      return var2;
   }

   private static String reformatCertificateDate(Context var0, String var1) {
      String var2 = null;
      if(var1 != null) {
         Date var4;
         label24: {
            Date var3;
            try {
               var3 = DateFormat.getInstance().parse(var1);
            } catch (ParseException var7) {
               var4 = null;
               break label24;
            }

            var4 = var3;
         }

         if(var4 != null) {
            var2 = android.text.format.DateFormat.getDateFormat(var0).format(var4);
         }
      }

      String var5;
      if(var2 != null) {
         var5 = var2;
      } else if(var1 != null) {
         var5 = var1;
      } else {
         var5 = "";
      }

      return var5;
   }

   public static void showCertificateInvalid(Activity var0, IEASService var1, Message var2, Message var3) {
      mService = var1;
      Bundle var4 = new Bundle();
      var4.putParcelable("extra.message.continue", var2);
      var4.putParcelable("extra.message.cancel", var3);
      var0.showDialog(310, var4);
   }

   private static Dialog showSSLCertificateDetail(Activity var0, CertificateUtil.ButtonEvent var1, long var2, String var4, Bundle var5) {
      EASCommon.CertificateError var6 = getCertificateError();
      Object var17;
      if(var6 != null && var6.certificate != null) {
         X509Certificate var12 = var6.certificate;
         SslCertificate var13 = new SslCertificate(var12);
         View var18 = inflateCertificateView(var0, var13);
         if(var18 == null) {
            var17 = null;
         } else {
            LayoutInflater var14 = LayoutInflater.from(var0);
            LinearLayout var20 = (LinearLayout)var18.findViewById(2131296580);
            if(var6.errorCode == '\ufff3') {
               ((TextView)((LinearLayout)var14.inflate(2130903096, var20)).findViewById(2131296602)).setText(2131362214);
            } else if(var6.errorCode == '\ufff5') {
               ((TextView)((LinearLayout)var14.inflate(2130903096, var20)).findViewById(2131296602)).setText(2131362215);
            } else if(var6.errorCode == '\ufff6') {
               ((TextView)((LinearLayout)var14.inflate(2130903096, var20)).findViewById(2131296602)).setText(2131362216);
            } else if(var6.errorCode == '\ufff4') {
               ((TextView)((LinearLayout)var14.inflate(2130903096, var20)).findViewById(2131296602)).setText(2131362217);
            }

            Builder var15 = (new Builder(var0)).setTitle(2131362218).setIcon(17301659).setView(var18);
            int var19 = 2131362432;
            OnClickListener var16;
            if(var1 != null) {
               var16 = var1.positiveClick;
            } else {
               var16 = null;
            }

            var17 = var15.setPositiveButton(var19, var16).setOnCancelListener((OnCancelListener)null).create();
         }
      } else {
         var17 = handleCreateDialog(var0, 315, var2, var4, var5);
      }

      return (Dialog)var17;
   }

   private static Dialog showSSLCertificateError(Activity var0, CertificateUtil.ButtonEvent var1, long var2, String var4, Bundle var5) {
      EASCommon.CertificateError var6 = getCertificateError();
      char var7;
      if(var6 == null) {
         var7 = '\ufff3';
      } else {
         int var18 = var6.errorCode;
      }

      LayoutInflater var8 = LayoutInflater.from(var0);
      View var23 = var8.inflate(2130903097, (ViewGroup)null);
      LinearLayout var22 = (LinearLayout)var23.findViewById(2131296580);
      if(var7 == '\ufff3') {
         LinearLayout var9 = (LinearLayout)var8.inflate(2130903096, (ViewGroup)null);
         ((TextView)var9.findViewById(2131296602)).setText(2131362214);
         var22.addView(var9);
      } else if(var7 == '\ufff5') {
         LinearLayout var19 = (LinearLayout)var8.inflate(2130903096, (ViewGroup)null);
         ((TextView)var19.findViewById(2131296602)).setText(2131362215);
         var22.addView(var19);
      } else if(var7 == '\ufff6') {
         LinearLayout var20 = (LinearLayout)var8.inflate(2130903096, (ViewGroup)null);
         ((TextView)var20.findViewById(2131296602)).setText(2131362216);
         var22.addView(var20);
      } else if(var7 == '\ufff4') {
         LinearLayout var21 = (LinearLayout)var8.inflate(2130903096, (ViewGroup)null);
         ((TextView)var21.findViewById(2131296602)).setText(2131362217);
         var22.addView(var21);
      }

      int var10 = 2131362210;
      Builder var11 = (new Builder(var0)).setTitle(2131362212).setIcon(17301543).setView(var23);
      OnClickListener var12;
      if(var1 != null) {
         var12 = var1.positiveClick;
      } else {
         var12 = null;
      }

      Builder var13 = var11.setPositiveButton(var10, var12);
      int var14 = 2131362213;
      OnClickListener var15;
      if(var1 != null) {
         var15 = var1.neutralClick;
      } else {
         var15 = null;
      }

      Builder var16 = var13.setNeutralButton(var14, var15);
      int var24 = 2131361931;
      OnClickListener var17;
      if(var1 != null) {
         var17 = var1.negativeClick;
      } else {
         var17 = null;
      }

      return var16.setNegativeButton(var24, var17).setOnCancelListener((OnCancelListener)null).create();
   }

   static class 1 implements OnClickListener {

      // $FF: synthetic field
      final long val$accountId;
      // $FF: synthetic field
      final String val$accountName;
      // $FF: synthetic field
      final Activity val$activity;
      // $FF: synthetic field
      final Bundle val$args;


      1(Bundle var1, long var2, String var4, Activity var5) {
         this.val$args = var1;
         this.val$accountId = var2;
         this.val$accountName = var4;
         this.val$activity = var5;
      }

      public void onClick(DialogInterface var1, int var2) {
         Message var3 = (Message)this.val$args.getParcelable("extra.message.continue");
         if(var3 != null && var3.getTarget() != null) {
            CertificateUtil.SaveKeystoreThread var4 = new CertificateUtil.SaveKeystoreThread((CertificateUtil.1)null);
            long var5 = this.val$accountId;
            var4.setAccountId(var5);
            String var7 = this.val$accountName;
            var4.setAccountName(var7);
            var4.setEndMessage(var3);
            var4.start();
            this.val$activity.showDialog(300);
         } else if(Mail.MAIL_DEBUG) {
            EASLog.d("CertificateUtil", "continue message invalid");
         }

         this.val$activity.removeDialog(310);
         this.val$activity.removeDialog(311);
      }
   }

   private static class SaveKeystoreThread extends Thread {

      long accountId;
      String accountName;
      Message endMessage;
      IEASService svc;


      private SaveKeystoreThread() {
         IEASService var1 = CertificateUtil.mService;
         this.svc = var1;
         this.accountId = 65535L;
         this.accountName = null;
      }

      // $FF: synthetic method
      SaveKeystoreThread(CertificateUtil.1 var1) {
         this();
      }

      public void run() {
         if(CertificateUtil.DEBUG) {
            long var1 = this.accountId;
            EASLog.d("CertificateUtil", var1, " - SaveKeystoreThread");
         }

         try {
            if(this.svc != null) {
               IEASService var3 = this.svc;
               long var4 = this.accountId;
               String var6 = this.accountName;
               var3.applySSLCertificate(var4, var6);
               if(this.endMessage != null) {
                  this.endMessage.sendToTarget();
               }
            } else {
               long var7 = this.accountId;
               EASLog.e("CertificateUtil", var7, "save keystore failed: service is null");
               if(this.endMessage != null) {
                  this.endMessage.sendToTarget();
               }
            }
         } catch (RemoteException var9) {
            var9.printStackTrace();
         }

         this.svc = null;
      }

      void setAccountId(long var1) {
         this.accountId = var1;
      }

      void setAccountName(String var1) {
         this.accountName = var1;
      }

      public void setEndMessage(Message var1) {
         this.endMessage = var1;
      }
   }

   static class 4 implements OnClickListener {

      // $FF: synthetic field
      final Activity val$activity;


      4(Activity var1) {
         this.val$activity = var1;
      }

      public void onClick(DialogInterface var1, int var2) {
         this.val$activity.showDialog(310);
      }
   }

   static class 5 implements OnClickListener {

      // $FF: synthetic field
      final Activity val$activity;


      5(Activity var1) {
         this.val$activity = var1;
      }

      public void onClick(DialogInterface var1, int var2) {
         this.val$activity.showDialog(310);
      }
   }

   static class 2 implements OnClickListener {

      // $FF: synthetic field
      final Activity val$activity;


      2(Activity var1) {
         this.val$activity = var1;
      }

      public void onClick(DialogInterface var1, int var2) {
         this.val$activity.showDialog(311);
      }
   }

   static class 3 implements OnClickListener {

      // $FF: synthetic field
      final Activity val$activity;
      // $FF: synthetic field
      final Bundle val$args;


      3(Bundle var1, Activity var2) {
         this.val$args = var1;
         this.val$activity = var2;
      }

      public void onClick(DialogInterface var1, int var2) {
         Message var3 = (Message)this.val$args.getParcelable("extra.message.cancel");
         if(var3 != null) {
            var3.sendToTarget();
         }

         this.val$activity.removeDialog(310);
         this.val$activity.removeDialog(311);
      }
   }

   static class ButtonEvent {

      OnClickListener negativeClick;
      OnClickListener neutralClick;
      OnClickListener positiveClick;


      ButtonEvent() {}
   }
}
