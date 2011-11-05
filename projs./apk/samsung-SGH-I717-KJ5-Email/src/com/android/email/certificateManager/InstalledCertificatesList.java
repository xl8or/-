package com.android.email.certificateManager;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import com.android.email.certificateManager.CertificateManagerException;
import com.android.email.certificateManager.CertificateMgr;
import com.android.email.certificateManager.ImportCertificate;
import java.util.ArrayList;
import java.util.Enumeration;

public class InstalledCertificatesList extends Activity {

   public static final String CERTIFICATE_NOT_SELECTED = "None";
   public static final String EMAIL_ADDRESS = "EMAIL_ADDRESS";
   private static final int PROGRESS_BAR_DIALOG = 1;
   private Button importButton;
   private String mActiveCertificate;
   private CertificateMgr mCertificateManager = null;
   private ListView mCertificatesList;
   private String mEmailAddress;
   private String mPassword;
   private ImageView noCertificateImg;
   private LinearLayout noCertificateLayout;
   private TextView noCertificateText;


   public InstalledCertificatesList() {}

   private void fillCertificateList() {
      try {
         Enumeration var1 = this.mCertificateManager.getAliases();
         ArrayList var2 = new ArrayList();

         while(var1 != null && var1.hasMoreElements()) {
            String var3 = (String)var1.nextElement();
            if(this.mCertificateManager.getPrivateKey(var3) != null) {
               var2.add(var3);
            }
         }

         if(var2.size() == 0) {
            this.mCertificatesList.setVisibility(8);
            this.noCertificateLayout.setVisibility(0);
            this.noCertificateImg.setVisibility(0);
            this.noCertificateText.setVisibility(0);
         } else {
            this.mCertificatesList.setVisibility(0);
            this.noCertificateImg.setVisibility(8);
            this.noCertificateText.setVisibility(8);
            this.noCertificateLayout.setVisibility(8);
         }

         ListView var6 = this.mCertificatesList;
         String[] var7 = new String[0];
         Object[] var8 = var2.toArray(var7);
         ArrayAdapter var9 = new ArrayAdapter(this, 17367043, var8);
         var6.setAdapter(var9);
      } catch (CertificateManagerException var10) {
         Toast.makeText(this.getApplicationContext(), 2131166761, 3000).show();
      }
   }

   public void importCertificate() {
      Intent var1 = new Intent(this, ImportCertificate.class);
      String var2 = this.mPassword;
      var1.putExtra("KEYSTORE_PASSWORD", var2);
      this.startActivityForResult(var1, 0);
   }

   protected void onActivityResult(int var1, int var2, Intent var3) {
      super.onActivityResult(var1, var2, var3);
      if(var2 == -1) {
         this.fillCertificateList();
      }
   }

   public boolean onContextItemSelected(MenuItem param1) {
      // $FF: Couldn't be decompiled
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903075);
      ListView var2 = (ListView)this.findViewById(2131361988);
      this.mCertificatesList = var2;
      Button var3 = (Button)this.findViewById(2131361992);
      this.importButton = var3;
      LinearLayout var4 = (LinearLayout)this.findViewById(2131361989);
      this.noCertificateLayout = var4;
      ImageView var5 = (ImageView)this.findViewById(2131361990);
      this.noCertificateImg = var5;
      TextView var6 = (TextView)this.findViewById(2131361991);
      this.noCertificateText = var6;
      Bundle var7 = this.getIntent().getExtras();
      String var8 = var7.getString("KEYSTORE_PASSWORD");
      this.mPassword = var8;
      String var9 = var7.getString("CERTIFICATE_ALIAS");
      this.mActiveCertificate = var9;
      String var10 = var7.getString("EMAIL_ADDRESS");
      this.mEmailAddress = var10;

      try {
         String var11 = this.mPassword;
         Context var12 = this.getApplicationContext();
         CertificateMgr var13 = CertificateMgr.getInstance(var11, var12);
         this.mCertificateManager = var13;
      } catch (Exception var20) {
         throw new RuntimeException("Unable to instantiate Certificate Manager ");
      }

      this.fillCertificateList();
      ListView var14 = this.mCertificatesList;
      this.registerForContextMenu(var14);
      ListView var15 = this.mCertificatesList;
      InstalledCertificatesList.1 var16 = new InstalledCertificatesList.1();
      var15.setOnItemClickListener(var16);
      Button var17 = this.importButton;
      InstalledCertificatesList.2 var18 = new InstalledCertificatesList.2();
      var17.setOnClickListener(var18);
   }

   public void onCreateContextMenu(ContextMenu var1, View var2, ContextMenuInfo var3) {
      this.getMenuInflater().inflate(2131689475, var1);
      super.onCreateContextMenu(var1, var2, var3);
   }

   protected Dialog onCreateDialog(int var1) {
      ProgressDialog var2;
      switch(var1) {
      case 1:
         ProgressDialog var3 = new ProgressDialog(this);
         String var4 = this.getString(2131166765);
         var3.setMessage(var4);
         var3.setIndeterminate((boolean)1);
         var3.setCancelable((boolean)0);
         var2 = var3;
         break;
      default:
         var2 = null;
      }

      return var2;
   }

   class 1 implements OnItemClickListener {

      1() {}

      public void onItemClick(AdapterView<?> var1, View var2, int var3, long var4) {
         TextView var6 = (TextView)var2;
         if(!TextUtils.isEmpty(var6.getText())) {
            String var7 = var6.getText().toString();

            try {
               String var8 = InstalledCertificatesList.this.mCertificateManager.getSubject(var7);
               String var9 = InstalledCertificatesList.this.mEmailAddress;
               if(!var8.contains(var9)) {
                  Toast.makeText(InstalledCertificatesList.this.getApplicationContext(), 2131166768, 0).show();
                  InstalledCertificatesList var10 = InstalledCertificatesList.this;
                  Intent var11 = new Intent();
                  CharSequence var12 = var6.getText();
                  Intent var13 = var11.putExtra("CERTIFICATE_ALIAS", var12);
                  var10.setResult(-1, var13);
                  InstalledCertificatesList.this.finish();
               } else {
                  InstalledCertificatesList var14 = InstalledCertificatesList.this;
                  Intent var15 = new Intent();
                  CharSequence var16 = var6.getText();
                  Intent var17 = var15.putExtra("CERTIFICATE_ALIAS", var16);
                  var14.setResult(-1, var17);
                  InstalledCertificatesList.this.finish();
               }
            } catch (CertificateManagerException var18) {
               var18.printStackTrace();
               Toast.makeText(InstalledCertificatesList.this.getApplicationContext(), 2131166761, 0).show();
            }
         }
      }
   }

   class 2 implements OnClickListener {

      2() {}

      public void onClick(View var1) {
         InstalledCertificatesList.this.importCertificate();
      }
   }

   class 3 implements Runnable {

      // $FF: synthetic field
      final String val$alias;


      3(String var2) {
         this.val$alias = var2;
      }

      public void run() {
         try {
            CertificateMgr var1 = InstalledCertificatesList.this.mCertificateManager;
            String var2 = this.val$alias;
            var1.removeCertificate(var2);
         } catch (Exception var9) {
            InstalledCertificatesList var7 = InstalledCertificatesList.this;
            InstalledCertificatesList.3.1 var8 = new InstalledCertificatesList.3.1();
            var7.runOnUiThread(var8);
         }

         InstalledCertificatesList var4 = InstalledCertificatesList.this;
         InstalledCertificatesList.3.2 var5 = new InstalledCertificatesList.3.2();
         var4.runOnUiThread(var5);
         InstalledCertificatesList.this.removeDialog(1);
      }

      class 1 implements Runnable {

         1() {}

         public void run() {
            Toast var1 = Toast.makeText(InstalledCertificatesList.this.getApplicationContext(), 2131166764, 3000);
         }
      }

      class 2 implements Runnable {

         2() {}

         public void run() {
            InstalledCertificatesList.this.fillCertificateList();
         }
      }
   }
}
