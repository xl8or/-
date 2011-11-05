package org.acra;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import org.acra.ACRA;
import org.acra.ErrorReporter;

public class CrashReportDialog extends Activity {

   String mReportFileName = null;
   private SharedPreferences prefs = null;
   private EditText userComment = null;
   private EditText userEmail = null;


   public CrashReportDialog() {}

   protected void cancelNotification() {
      ((NotificationManager)this.getSystemService("notification")).cancel(666);
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      String var2 = this.getIntent().getStringExtra("REPORT_FILE_NAME");
      this.mReportFileName = var2;
      String var3 = ACRA.LOG_TAG;
      StringBuilder var4 = (new StringBuilder()).append("Opening CrashReportDialog for ");
      String var5 = this.mReportFileName;
      String var6 = var4.append(var5).toString();
      Log.d(var3, var6);
      if(this.mReportFileName == null) {
         this.finish();
      }

      boolean var8 = this.requestWindowFeature(3);
      LinearLayout var9 = new LinearLayout(this);
      var9.setOrientation(1);
      var9.setPadding(10, 10, 10, 10);
      LayoutParams var10 = new LayoutParams(-1, -1);
      var9.setLayoutParams(var10);
      ScrollView var11 = new ScrollView(this);
      android.widget.LinearLayout.LayoutParams var12 = new android.widget.LinearLayout.LayoutParams(-1, -1, 1.0F);
      var9.addView(var11, var12);
      TextView var13 = new TextView(this);
      int var14 = ACRA.getConfig().resDialogText();
      CharSequence var15 = this.getText(var14);
      var13.setText(var15);
      var11.addView(var13, -1, -1);
      int var16 = ACRA.getConfig().resDialogCommentPrompt();
      if(var16 != 0) {
         TextView var17 = new TextView(this);
         CharSequence var18 = this.getText(var16);
         var17.setText(var18);
         int var19 = var17.getPaddingLeft();
         int var20 = var17.getPaddingRight();
         int var21 = var17.getPaddingBottom();
         var17.setPadding(var19, 10, var20, var21);
         android.widget.LinearLayout.LayoutParams var22 = new android.widget.LinearLayout.LayoutParams(-1, -1);
         var9.addView(var17, var22);
         EditText var23 = new EditText(this);
         this.userComment = var23;
         this.userComment.setLines(2);
         EditText var24 = this.userComment;
         android.widget.LinearLayout.LayoutParams var25 = new android.widget.LinearLayout.LayoutParams(-1, -1);
         var9.addView(var24, var25);
      }

      int var26 = ACRA.getConfig().resDialogEmailPrompt();
      if(var26 != 0) {
         TextView var27 = new TextView(this);
         CharSequence var28 = this.getText(var26);
         var27.setText(var28);
         int var29 = var27.getPaddingLeft();
         int var30 = var27.getPaddingRight();
         int var31 = var27.getPaddingBottom();
         var27.setPadding(var29, 10, var30, var31);
         android.widget.LinearLayout.LayoutParams var32 = new android.widget.LinearLayout.LayoutParams(-1, -1);
         var9.addView(var27, var32);
         EditText var33 = new EditText(this);
         this.userEmail = var33;
         this.userEmail.setSingleLine();
         this.userEmail.setInputType(33);
         String var34 = ACRA.getConfig().sharedPreferencesName();
         int var35 = ACRA.getConfig().sharedPreferencesMode();
         SharedPreferences var36 = this.getSharedPreferences(var34, var35);
         this.prefs = var36;
         EditText var37 = this.userEmail;
         String var38 = this.prefs.getString("acra.user.email", "");
         var37.setText(var38);
         EditText var39 = this.userEmail;
         android.widget.LinearLayout.LayoutParams var40 = new android.widget.LinearLayout.LayoutParams(-1, -1);
         var9.addView(var39, var40);
      }

      LinearLayout var41 = new LinearLayout(this);
      android.widget.LinearLayout.LayoutParams var42 = new android.widget.LinearLayout.LayoutParams(-1, -1);
      var41.setLayoutParams(var42);
      int var43 = var41.getPaddingLeft();
      int var44 = var41.getPaddingRight();
      int var45 = var41.getPaddingBottom();
      var41.setPadding(var43, 10, var44, var45);
      Button var46 = new Button(this);
      var46.setText(17039379);
      CrashReportDialog.1 var47 = new CrashReportDialog.1();
      var46.setOnClickListener(var47);
      android.widget.LinearLayout.LayoutParams var48 = new android.widget.LinearLayout.LayoutParams(-1, -1, 1.0F);
      var41.addView(var46, var48);
      Button var49 = new Button(this);
      var49.setText(17039369);
      CrashReportDialog.2 var50 = new CrashReportDialog.2();
      var49.setOnClickListener(var50);
      android.widget.LinearLayout.LayoutParams var51 = new android.widget.LinearLayout.LayoutParams(-1, -1, 1.0F);
      var41.addView(var49, var51);
      android.widget.LinearLayout.LayoutParams var52 = new android.widget.LinearLayout.LayoutParams(-1, -1);
      var9.addView(var41, var52);
      this.setContentView(var9);
      int var53 = ACRA.getConfig().resDialogTitle();
      if(var53 != 0) {
         this.setTitle(var53);
      }

      Window var54 = this.getWindow();
      int var55 = ACRA.getConfig().resDialogIcon();
      var54.setFeatureDrawableResource(3, var55);
      this.cancelNotification();
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(View var1) {
         ErrorReporter var2 = ErrorReporter.getInstance();
         Class var3 = var2.getClass();
         ErrorReporter.ReportsSenderWorker var4 = var2.new ReportsSenderWorker();
         var4.setApprovePendingReports();
         if(CrashReportDialog.this.userComment != null) {
            String var5 = CrashReportDialog.this.mReportFileName;
            String var6 = CrashReportDialog.this.userComment.getText().toString();
            var4.setUserComment(var5, var6);
         }

         if(CrashReportDialog.this.prefs != null && CrashReportDialog.this.userEmail != null) {
            String var7 = CrashReportDialog.this.userEmail.getText().toString();
            Editor var8 = CrashReportDialog.this.prefs.edit();
            var8.putString("acra.user.email", var7);
            boolean var10 = var8.commit();
            String var11 = CrashReportDialog.this.mReportFileName;
            var4.setUserEmail(var11, var7);
         }

         int var12 = Log.v(ACRA.LOG_TAG, "About to start ReportSenderWorker from CrashReportDialog");
         var4.start();
         int var13 = ACRA.getConfig().resDialogOkToast();
         if(var13 != 0) {
            Toast.makeText(CrashReportDialog.this.getApplicationContext(), var13, 1).show();
         }

         CrashReportDialog.this.finish();
      }
   }

   class 2 implements OnClickListener {

      2() {}

      public void onClick(View var1) {
         ErrorReporter.getInstance().deletePendingReports();
         CrashReportDialog.this.finish();
      }
   }
}
