package com.google.android.finsky.activities;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.Analytics;
import com.google.android.finsky.config.ContentLevel;
import com.google.android.finsky.config.G;
import com.google.android.finsky.utils.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ContentFilterActivity extends Activity implements OnClickListener {

   private static final String INSTANCE_KEY_LEVEL = "level";
   private static final String MORE_ABOUT_CONTENT_FILTER_TEMPLATE = "%s <a href=\'%s\'>%s</a>";
   private final List<CheckBox> mCheckboxes;
   private LinearLayout mCheckboxesView;
   private ContentLevel mLevel;
   private TextView mMoreInfoView;
   private Button mOkButton;


   public ContentFilterActivity() {
      ArrayList var1 = Lists.newArrayList();
      this.mCheckboxes = var1;
   }

   public static String getLabel(Context var0, int var1) {
      Resources var2 = var0.getResources();
      String var3;
      switch(var1) {
      case -1:
         var3 = var2.getString(2131231046);
         break;
      case 0:
         var3 = var2.getString(2131231047);
         break;
      case 1:
         var3 = var2.getString(2131231048);
         break;
      case 2:
         var3 = var2.getString(2131231049);
         break;
      case 3:
         var3 = var2.getString(2131231050);
         break;
      case 4:
         var3 = var2.getString(2131231051);
         break;
      default:
         var3 = null;
      }

      return var3;
   }

   private void setupCheckbox(ContentLevel var1, int var2, int var3) {
      CheckBox var4 = (CheckBox)this.mCheckboxesView.findViewById(var2);
      var4.setText(var3);
      var4.setTag(var1);
      var4.setOnClickListener(this);
      boolean var5 = this.mLevel.encompasses(var1);
      var4.setChecked(var5);
      this.mCheckboxes.add(var4);
   }

   private void setupViews() {
      this.mCheckboxes.clear();
      ContentLevel var1 = ContentLevel.EVERYONE;
      this.setupCheckbox(var1, 2131755104, 2131231047);
      ContentLevel var2 = ContentLevel.LOW_MATURITY;
      this.setupCheckbox(var2, 2131755105, 2131231048);
      ContentLevel var3 = ContentLevel.MEDIUM_MATURITY;
      this.setupCheckbox(var3, 2131755106, 2131231049);
      ContentLevel var4 = ContentLevel.HIGH_MATURITY;
      this.setupCheckbox(var4, 2131755107, 2131231050);
      ContentLevel var5 = ContentLevel.SHOW_ALL;
      this.setupCheckbox(var5, 2131755108, 2131231051);
      Object[] var6 = new Object[3];
      String var7 = this.getString(2131231052);
      var6[0] = var7;
      Object var8 = G.contentFilterInfoUrl.get();
      var6[1] = var8;
      String var9 = this.getString(2131231053);
      var6[2] = var9;
      String var10 = String.format("%s <a href=\'%s\'>%s</a>", var6);
      TextView var11 = this.mMoreInfoView;
      Spanned var12 = Html.fromHtml(var10);
      var11.setText(var12);
      TextView var13 = this.mMoreInfoView;
      MovementMethod var14 = LinkMovementMethod.getInstance();
      var13.setMovementMethod(var14);
      Button var15 = this.mOkButton;
      ContentFilterActivity.1 var16 = new ContentFilterActivity.1();
      var15.setOnClickListener(var16);
      View var17 = this.findViewById(2131755054);
      ContentFilterActivity.2 var18 = new ContentFilterActivity.2();
      var17.setOnClickListener(var18);
   }

   public void onClick(View var1) {
      ContentLevel var2 = (ContentLevel)var1.getTag();
      this.mLevel = var2;
      Iterator var3 = this.mCheckboxes.iterator();

      while(var3.hasNext()) {
         CheckBox var4 = (CheckBox)var3.next();
         ContentLevel var5 = this.mLevel;
         ContentLevel var6 = (ContentLevel)var4.getTag();
         boolean var7 = var5.encompasses(var6);
         var4.setChecked(var7);
      }

      Analytics var8 = FinskyApp.get().getAnalytics();
      StringBuilder var9 = (new StringBuilder()).append("contentFilter?");
      String var10 = this.mLevel.name();
      String var11 = var9.append(var10).toString();
      var8.logPageView("settings", (String)null, var11);
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130968601);
      if(var1 == null) {
         ContentLevel var2 = ContentLevel.importFromSettings(this);
         this.mLevel = var2;
      } else {
         ContentLevel var6 = ContentLevel.convertFromLegacyValue(var1.getInt("level"));
         this.mLevel = var6;
      }

      LinearLayout var3 = (LinearLayout)this.findViewById(2131755103);
      this.mCheckboxesView = var3;
      TextView var4 = (TextView)this.findViewById(2131755109);
      this.mMoreInfoView = var4;
      Button var5 = (Button)this.findViewById(2131755110);
      this.mOkButton = var5;
      this.setupViews();
      this.setResult(0);
   }

   public void onSaveInstanceState(Bundle var1) {
      super.onSaveInstanceState(var1);
      int var2 = this.mLevel.getValue();
      var1.putInt("level", var2);
   }

   class 2 implements OnClickListener {

      2() {}

      public void onClick(View var1) {
         ContentFilterActivity.this.finish();
      }
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(View var1) {
         ContentLevel var2 = ContentFilterActivity.this.mLevel;
         ContentFilterActivity var3 = ContentFilterActivity.this;
         var2.exportToSettings(var3);
         ContentFilterActivity.this.setResult(-1);
         ContentFilterActivity.this.finish();
      }
   }
}
