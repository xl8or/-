package com.facebook.katana.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.facebook.katana.IntentUriHandler;
import com.facebook.katana.activity.FacebookActivity;
import com.facebook.katana.activity.FacebookActivityDelegate;
import com.facebook.katana.service.method.PerfLogging;
import com.facebook.katana.view.FacebookListView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BaseFacebookListActivity extends ListActivity implements FacebookActivity {

   private FacebookActivityDelegate mDelegate;
   protected List<Integer> mListHeaders;


   public BaseFacebookListActivity() {
      FacebookActivityDelegate var1 = new FacebookActivityDelegate(this);
      this.mDelegate = var1;
      ArrayList var2 = new ArrayList();
      this.mListHeaders = var2;
   }

   public boolean facebookOnBackPressed() {
      this.finish();
      return true;
   }

   public void finish() {
      this.mDelegate.finish();
      super.finish();
   }

   public long getActivityId() {
      return this.mDelegate.getActivityId();
   }

   protected int getCursorPosition(int var1) {
      int var2 = this.getListView().getHeaderViewsCount();
      return var1 - var2;
   }

   protected String getTag() {
      return this.mDelegate.getTag();
   }

   protected void hideSearchButton() {
      this.mDelegate.hideSearchButton();
   }

   public boolean isOnTop() {
      return this.mDelegate.isOnTop();
   }

   protected void launchComposer(Uri var1, Bundle var2, Integer var3, long var4) {
      FacebookActivityDelegate var6 = this.mDelegate;
      var6.launchComposer(var1, var2, var3, var4);
   }

   protected void logStepDataReceived() {
      this.mDelegate.logStepDataReceived();
      PerfLogging.Step var1 = PerfLogging.Step.UI_DRAWN_FRESH;
      this.setListViewNextDrawStep(var1);
   }

   protected void logStepDataRequested() {
      this.mDelegate.logStepDataRequested();
   }

   public void onContentChanged() {
      super.onContentChanged();
      this.mDelegate.onContentChanged();
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.mDelegate.onCreate(var1);
   }

   public boolean onKeyDown(int var1, KeyEvent var2) {
      Boolean var3 = this.mDelegate.onKeyDown(var1, var2);
      boolean var4;
      if(var3 != null) {
         var4 = var3.booleanValue();
      } else {
         var4 = super.onKeyDown(var1, var2);
      }

      return var4;
   }

   public boolean onKeyUp(int var1, KeyEvent var2) {
      Boolean var3 = this.mDelegate.onKeyUp(var1, var2);
      boolean var4;
      if(var3 != null) {
         var4 = var3.booleanValue();
      } else {
         var4 = super.onKeyUp(var1, var2);
      }

      return var4;
   }

   protected void onPause() {
      this.mDelegate.onPause();
      super.onPause();
   }

   protected void onResume() {
      super.onResume();
      this.mDelegate.onResume();
   }

   public boolean onSearchRequested() {
      return this.mDelegate.onSearchRequested();
   }

   protected void onStart() {
      super.onStart();
      PerfLogging.Step var1 = PerfLogging.Step.UI_DRAWN_STALE;
      this.setListViewNextDrawStep(var1);
      long var2 = this.mDelegate.mActivityId;
      String var4 = this.getTag();
      this.setupListView(var2, var4);
   }

   public void setListEmptyText(int var1) {
      ((TextView)this.findViewById(2131624022)).setText(var1);
   }

   protected void setListLoading(boolean var1) {
      byte var2;
      if(var1) {
         var2 = 0;
      } else {
         var2 = 8;
      }

      byte var3;
      if(var1) {
         var3 = 8;
      } else {
         var3 = 0;
      }

      this.findViewById(2131624023).setVisibility(var2);
      this.findViewById(2131624022).setVisibility(var3);
   }

   public void setListLoadingText(int var1) {
      ((TextView)this.findViewById(2131624024)).setText(var1);
   }

   public void setListViewNextDrawStep(PerfLogging.Step var1) {
      ListView var2 = this.getListView();
      if(var2 instanceof FacebookListView) {
         ((FacebookListView)var2).setNextDrawStep(var1);
      }
   }

   protected void setPrimaryActionFace(int var1, String var2) {
      this.mDelegate.setPrimaryActionFace(var1, var2);
   }

   protected void setPrimaryActionIcon(int var1) {
      this.mDelegate.setPrimaryActionIcon(var1);
   }

   public void setTransitioningToBackground(boolean var1) {
      this.mDelegate.setTransitioningToBackground(var1);
   }

   protected void setupListHeaders() {
      if(!this.mListHeaders.isEmpty()) {
         LayoutInflater var1 = (LayoutInflater)this.getSystemService("layout_inflater");
         Iterator var2 = this.mListHeaders.iterator();

         while(var2.hasNext()) {
            Integer var3 = (Integer)var2.next();
            ListView var4 = this.getListView();
            int var5 = var3.intValue();
            View var6 = var1.inflate(var5, (ViewGroup)null);
            var4.addHeaderView(var6, (Object)null, (boolean)0);
         }

      }
   }

   public void setupListView(long var1, String var3) {
      ListView var4 = this.getListView();
      if(var4 instanceof FacebookListView) {
         ((FacebookListView)var4).activityId = var1;
         ((FacebookListView)var4).TAG = var3;
      }
   }

   public void startActivity(Intent var1) {
      this.mDelegate.startActivity(var1);
      super.startActivity(var1);
   }

   public void startActivityForResult(Intent var1, int var2) {
      this.mDelegate.startActivityForResult(var1, var2);
      super.startActivityForResult(var1, var2);
   }

   public void titleBarClickHandler(View var1) {
      Intent var2 = IntentUriHandler.getIntentForUri(this, "fb://root");
      Intent var3 = var2.setFlags(67108864);
      this.startActivity(var2);
   }

   public void titleBarPrimaryActionClickHandler(View var1) {
      this.mDelegate.titleBarPrimaryActionClickHandler(var1);
   }

   public void titleBarSearchClickHandler(View var1) {
      this.mDelegate.titleBarSearchClickHandler(var1);
   }
}
