package com.android.email.smime;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import com.android.email.smime.CertificateManagerException;
import com.android.email.smime.CertificateMgr;
import java.io.File;
import java.util.ArrayList;

public class ImportCertificate extends Activity implements OnCancelListener {

   private static final int CANCELLED = 4;
   private static final int DONE = 3;
   public static final String FILE_NAME = "fileName";
   private static final int PASSWORD_DIALOG = 1;
   private static final int PROGRESS_BAR_DIALOG = 3;
   private static final int RENAME_DIALOG = 2;
   private static final int REOPEN_PASSWORD = 1;
   private static final int REOPEN_RENAME = 2;
   private static final String TAG = "ImportCertificate";
   private File mCertToImport;
   private CertificateMgr mCertificateManager;
   private int mDialogResult = 1;
   private String mImportedCertificateName;
   private String mKeystorePassword;
   ImportCertificate.SdCardMonitor mSdCardMonitor;
   private View mView;


   public ImportCertificate() {}

   private void createFileList() {
      if(this.isFinishing()) {
         int var1 = Log.d("ImportCertificate", "finishing, exit createFileList()");
      } else if(!Environment.getExternalStorageState().equals("mounted")) {
         Toast.makeText(this.getApplicationContext(), 2131166750, 3000).show();
         this.finish();
      } else {
         File var2 = Environment.getExternalStorageDirectory();
         ArrayList var3 = new ArrayList();
         File[] var4;
         if(var2 != null) {
            var4 = var2.listFiles();
            if(var4 == null) {
               return;
            }

            byte var5 = 0;

            while(true) {
               int var6 = var4.length;
               if(var5 >= var6) {
                  break;
               }

               String var7 = var4[var5].getName();
               if(var7.endsWith(".p12") || var7.endsWith(".pfx")) {
                  var3.add(var7);
               }

               int var9 = var5 + 1;
            }
         } else {
            Toast var10 = Toast.makeText(this, 2131166751, 3000);
         }

         StringBuilder var11 = new StringBuilder();
         File var12 = Environment.getExternalStorageDirectory();
         String var13 = var11.append(var12).append("/external_sd").toString();
         File var14 = new File(var13);
         if(var14 != null) {
            var4 = var14.listFiles();
            StringBuilder var15 = (new StringBuilder()).append("name 2nd root ");
            String var16 = var14.toString();
            String var17 = var15.append(var16).toString();
            int var18 = Log.d("AJ", var17);
            if(var4 == null) {
               return;
            }

            int var32 = 0;

            while(true) {
               int var19 = var4.length;
               if(var32 >= var19) {
                  break;
               }

               String var20 = var4[var32].getName();
               if(var20.endsWith(".p12") || var20.endsWith(".pfx")) {
                  String var21 = "external_sd/" + var20;
                  var3.add(var21);
               }

               ++var32;
            }
         } else {
            int var23 = Log.d("ImportCertificate", "external_sd is not founded");
         }

         ListView var24 = (ListView)this.findViewById(2131362078);
         String[] var25 = new String[0];
         String[] var26 = (String[])var3.toArray(var25);
         StringBuilder var27 = (new StringBuilder()).append("files: ");
         int var28 = var26.length;
         String var29 = var27.append(var28).toString();
         int var30 = Log.d("ImportCertificate", var29);
         if(var26 != null && var26.length > 0) {
            ArrayAdapter var31 = new ArrayAdapter(this, 17367043, var26);
            var24.setAdapter(var31);
         } else {
            Toast.makeText(this, 2131166752, 3000).show();
            this.finish();
         }
      }
   }

   private OnClickListener createOnClickListener(int var1) {
      View var2 = View.inflate(this, 2130903193, (ViewGroup)null);
      this.mView = var2;
      return new ImportCertificate.2(var1);
   }

   private Dialog createSingleEditLineDialog(int var1) {
      OnClickListener var2 = this.createOnClickListener(var1);
      OnDismissListener var3 = this.createOnDismissListener(var1);
      String var4 = "";
      EditText var5 = (EditText)this.mView.findViewById(2131362517);
      switch(var1) {
      case 1:
         var4 = this.getString(2131166753);
         PasswordTransformationMethod var10 = new PasswordTransformationMethod();
         var5.setTransformationMethod(var10);
         break;
      case 2:
         var4 = this.getString(2131166755);
         String var9 = this.mImportedCertificateName;
         var5.setText(var9);
      }

      Builder var6 = new Builder(this);
      View var7 = this.mView;
      AlertDialog var8 = var6.setView(var7).setTitle(var4).setPositiveButton(17039370, var2).setNegativeButton(17039360, var2).setOnCancelListener(this).create();
      var8.setOnDismissListener(var3);
      return var8;
   }

   private String getViewText(int var1) {
      return ((TextView)this.mView.findViewById(var1)).getText().toString();
   }

   private void hide(int var1) {
      View var2 = this.mView.findViewById(var1);
      if(var2 != null) {
         var2.setVisibility(8);
      }
   }

   private void hideError() {
      this.hide(2131362515);
   }

   private void importCertificate(String var1) {
      try {
         CertificateMgr var2 = this.mCertificateManager;
         File var3 = this.mCertToImport;
         String var4 = var2.importCertificate(var3, var1);
         this.mImportedCertificateName = var4;
      } catch (CertificateManagerException var7) {
         ImportCertificate.4 var6 = new ImportCertificate.4();
         this.runOnUiThread(var6);
      }
   }

   private void renameCertificate(String var1) {
      String var2 = this.mImportedCertificateName;
      if(!var1.equals(var2)) {
         try {
            CertificateMgr var3 = this.mCertificateManager;
            String var4 = this.mImportedCertificateName;
            var3.renameCertificate(var1, var4);
         } catch (CertificateManagerException var7) {
            ImportCertificate.5 var6 = new ImportCertificate.5();
            this.runOnUiThread(var6);
         }
      }
   }

   private void reopenDialog() {
      if(this.mDialogResult == 2) {
         this.showDialog(2);
      } else if(this.mDialogResult == 1) {
         this.showDialog(1);
      }
   }

   private TextView showError(int var1) {
      TextView var2 = (TextView)this.mView.findViewById(2131362515);
      if(var2 != null) {
         var2.setText(var1);
         var2.setVisibility(0);
      }

      return var2;
   }

   private void stopSdCardMonitor() {
      if(this.mSdCardMonitor != null) {
         this.mSdCardMonitor.stopWatching();
      }
   }

   public OnDismissListener createOnDismissListener(int var1) {
      return new ImportCertificate.3(var1);
   }

   public void onCancel(DialogInterface var1) {
      this.mDialogResult = 4;
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903093);
      Bundle var2 = this.getIntent().getExtras();
      if(var2 != null) {
         String var3 = var2.getString("KEYSTORE_PASSWORD");
         this.mKeystorePassword = var3;
      }

      if(this.mKeystorePassword != null && this.mKeystorePassword.length() > 0) {
         try {
            String var4 = this.mKeystorePassword;
            Context var5 = this.getApplicationContext();
            CertificateMgr var6 = CertificateMgr.getInstance(var4, var5);
            this.mCertificateManager = var6;
         } catch (CertificateManagerException var11) {
            Toast.makeText(this.getApplicationContext(), 2131166759, 3000).show();
         }

         this.createFileList();
         ListView var7 = (ListView)this.findViewById(2131362078);
         ImportCertificate.1 var8 = new ImportCertificate.1();
         var7.setOnItemClickListener(var8);
         ImportCertificate.SdCardMonitor var9 = new ImportCertificate.SdCardMonitor();
         this.mSdCardMonitor = var9;
         this.mSdCardMonitor.startWatching();
      } else {
         throw new RuntimeException("keystore password can not be null");
      }
   }

   protected Dialog onCreateDialog(int var1) {
      Object var2;
      switch(var1) {
      case 1:
         this.mDialogResult = 1;
         var2 = this.createSingleEditLineDialog(var1);
         break;
      case 2:
         this.mDialogResult = 2;
         var2 = this.createSingleEditLineDialog(var1);
         break;
      case 3:
         ProgressDialog var3 = new ProgressDialog(this);
         String var4 = this.getString(2131166757);
         var3.setMessage(var4);
         var3.setIndeterminate((boolean)1);
         var3.setCancelable((boolean)0);
         var2 = var3;
         break;
      default:
         var2 = null;
      }

      return (Dialog)var2;
   }

   protected void onDestroy() {
      super.onDestroy();
      this.stopSdCardMonitor();
   }

   class 3 implements OnDismissListener {

      // $FF: synthetic field
      final int val$id;


      3(int var2) {
         this.val$id = var2;
      }

      public void onDismiss(DialogInterface var1) {
         if(ImportCertificate.this.isFinishing()) {
            int var2 = ImportCertificate.this.mDialogResult = 4;
         }

         if(ImportCertificate.this.mDialogResult == 3) {
            ImportCertificate.this.showDialog(3);
            String var3 = ImportCertificate.this.getViewText(2131362517);
            ImportCertificate.3.1 var4 = new ImportCertificate.3.1(var3);
            (new Thread(var4)).start();
         } else if(ImportCertificate.this.mDialogResult == 4) {
            ImportCertificate.3.2 var5 = new ImportCertificate.3.2();
            (new Thread(var5)).start();
         } else {
            ImportCertificate.this.reopenDialog();
         }
      }

      class 2 implements Runnable {

         2() {}

         public void run() {
            try {
               if(!TextUtils.isEmpty(ImportCertificate.this.mImportedCertificateName)) {
                  CertificateMgr var1 = ImportCertificate.this.mCertificateManager;
                  String var2 = ImportCertificate.this.mImportedCertificateName;
                  var1.removeCertificate(var2);
               }
            } catch (Exception var5) {
               ;
            }
         }
      }

      class 1 implements Runnable {

         // $FF: synthetic field
         final String val$data;


         1(String var2) {
            this.val$data = var2;
         }

         public void run() {
            switch(3.this.val$id) {
            case 1:
               ImportCertificate var5 = ImportCertificate.this;
               String var6 = this.val$data;
               var5.importCertificate(var6);
               ImportCertificate.this.removeDialog(3);
               ImportCertificate var7 = ImportCertificate.this;
               ImportCertificate.3.1.2 var8 = new ImportCertificate.3.1.2();
               var7.runOnUiThread(var8);
               return;
            case 2:
               ImportCertificate var1 = ImportCertificate.this;
               String var2 = this.val$data;
               var1.renameCertificate(var2);
               ImportCertificate.this.removeDialog(3);
               ImportCertificate var3 = ImportCertificate.this;
               ImportCertificate.3.1.1 var4 = new ImportCertificate.3.1.1();
               var3.runOnUiThread(var4);
               return;
            default:
            }
         }

         class 1 implements Runnable {

            1() {}

            public void run() {
               ImportCertificate.this.setResult(-1);
               ImportCertificate.this.finish();
            }
         }

         class 2 implements Runnable {

            2() {}

            public void run() {
               ImportCertificate.this.showDialog(2);
            }
         }
      }
   }

   private class SdCardMonitor {

      FileObserver mRootMonitor;


      SdCardMonitor() {
         String var2 = Environment.getExternalStorageDirectory().getPath();
         ImportCertificate.SdCardMonitor.1 var3 = new ImportCertificate.SdCardMonitor.1(var2, ImportCertificate.this);
         this.mRootMonitor = var3;
      }

      private void commonHandler(int var1, String var2) {
         switch(var1) {
         case 256:
         case 512:
            if(!var2.endsWith(".p12")) {
               return;
            }

            ImportCertificate var3 = ImportCertificate.this;
            ImportCertificate.SdCardMonitor.2 var4 = new ImportCertificate.SdCardMonitor.2();
            var3.runOnUiThread(var4);
            return;
         default:
         }
      }

      void startWatching() {
         this.mRootMonitor.startWatching();
      }

      void stopWatching() {
         this.mRootMonitor.stopWatching();
      }

      class 1 extends FileObserver {

         // $FF: synthetic field
         final ImportCertificate val$this$0;


         1(String var2, ImportCertificate var3) {
            super(var2);
            this.val$this$0 = var3;
         }

         public void onEvent(int var1, String var2) {
            SdCardMonitor.this.commonHandler(var1, var2);
         }
      }

      class 2 implements Runnable {

         2() {}

         public void run() {
            ImportCertificate.this.createFileList();
         }
      }
   }

   class 4 implements Runnable {

      4() {}

      public void run() {
         ImportCertificate.this.finish();
         Toast.makeText(ImportCertificate.this.getApplicationContext(), 2131166758, 3000).show();
      }
   }

   class 5 implements Runnable {

      5() {}

      public void run() {
         ImportCertificate.this.finish();
         Toast.makeText(ImportCertificate.this.getApplicationContext(), 2131166760, 3000).show();
      }
   }

   class 1 implements OnItemClickListener {

      1() {}

      public void onItemClick(AdapterView<?> var1, View var2, int var3, long var4) {
         TextView var6 = (TextView)var2;
         ImportCertificate var7 = ImportCertificate.this;
         File var8 = Environment.getExternalStorageDirectory();
         String var9 = var6.getText().toString();
         File var10 = new File(var8, var9);
         var7.mCertToImport = var10;
         ImportCertificate.this.showDialog(1);
      }
   }

   class 2 implements OnClickListener {

      // $FF: synthetic field
      final int val$id;


      2(int var2) {
         this.val$id = var2;
      }

      public void onClick(DialogInterface var1, int var2) {
         if(var2 == -1) {
            ImportCertificate.this.onCancel(var1);
         } else {
            String var3 = ImportCertificate.this.getViewText(2131362517);
            ImportCertificate.this.hideError();
            if(TextUtils.isEmpty(var3)) {
               switch(this.val$id) {
               case 1:
                  TextView var5 = ImportCertificate.this.showError(2131166754);
                  return;
               case 2:
                  TextView var4 = ImportCertificate.this.showError(2131166756);
                  return;
               default:
                  throw new RuntimeException("Invalid Dialog Id");
               }
            } else {
               int var6 = ImportCertificate.this.mDialogResult = 3;
            }
         }
      }
   }
}
