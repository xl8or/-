package com.facebook.katana;

import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnKeyListener;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.LinearLayout.LayoutParams;
import java.util.ArrayList;
import java.util.List;

public class MyTabHost extends TabHost {

   protected int mCurrentTab;
   private View mCurrentView;
   private boolean mHandleTouchMode;
   protected LocalActivityManager mLocalActivityManager;
   private MyTabHost.OnTabChangeListener mOnTabChangeListener;
   private FrameLayout mTabContent;
   private OnKeyListener mTabKeyListener;
   private List<MyTabHost.TabSpec> mTabSpecs;
   private ViewGroup mTabs = null;


   public MyTabHost(Context var1) {
      super(var1);
      ArrayList var2 = new ArrayList(2);
      this.mTabSpecs = var2;
      this.mCurrentTab = -1;
      this.mCurrentView = null;
      this.mLocalActivityManager = null;
      this.mHandleTouchMode = (boolean)1;
   }

   public MyTabHost(Context var1, AttributeSet var2) {
      super(var1, var2);
      ArrayList var3 = new ArrayList(2);
      this.mTabSpecs = var3;
      this.mCurrentTab = -1;
      this.mCurrentView = null;
      this.mLocalActivityManager = null;
      this.mHandleTouchMode = (boolean)1;
      this.mCurrentTab = -1;
      this.mCurrentView = null;
   }

   private void invokeOnTabChangeListener() {
      if(this.mOnTabChangeListener != null) {
         MyTabHost.OnTabChangeListener var1 = this.mOnTabChangeListener;
         String var2 = this.getCurrentTabTag();
         var1.onTabChanged(var2);
      }
   }

   public void addTab(MyTabHost.TabSpec var1) {
      if(var1.view == null) {
         throw new IllegalArgumentException("you must set the tab indicator view.");
      } else if(var1.mContentStrategy == null) {
         throw new IllegalArgumentException("you must specify a way to create the tab content");
      } else {
         View var2 = var1.view;
         OnKeyListener var3 = this.mTabKeyListener;
         var2.setOnKeyListener(var3);
         this.mTabSpecs.add(var1);
         if(this.mCurrentTab == -1) {
            this.setCurrentTab(0);
         }

         this.mTabs.addView(var2);
         LayoutParams var5 = new LayoutParams(0, -1, 1.0F);
         var2.setLayoutParams(var5);
      }
   }

   public void clearAllTabs() {
      this.mCurrentTab = -1;
      this.mCurrentView = null;
      this.mTabContent.removeAllViews();
      this.mTabSpecs.clear();
      this.requestLayout();
      this.invalidate();
   }

   public boolean dispatchKeyEvent(KeyEvent var1) {
      byte var2 = this.mTabContent.dispatchKeyEvent(var1);
      byte var6;
      if(var2 == 0 && var1.getAction() == 0 && var1.getKeyCode() == 19 && this.mCurrentView.hasFocus() && this.mCurrentView.findFocus().focusSearch(33) == null) {
         List var3 = this.mTabSpecs;
         int var4 = this.mCurrentTab;
         boolean var5 = ((MyTabHost.TabSpec)var3.get(var4)).view.requestFocus();
         this.playSoundEffect(2);
         var6 = 1;
      } else {
         var6 = var2;
      }

      return (boolean)var6;
   }

   public void dispatchWindowFocusChanged(boolean var1) {
      this.mCurrentView.dispatchWindowFocusChanged(var1);
   }

   public int getCurrentTab() {
      return this.mCurrentTab;
   }

   public String getCurrentTabTag() {
      String var5;
      if(this.mCurrentTab >= 0) {
         int var1 = this.mCurrentTab;
         int var2 = this.mTabSpecs.size();
         if(var1 < var2) {
            List var3 = this.mTabSpecs;
            int var4 = this.mCurrentTab;
            var5 = ((MyTabHost.TabSpec)var3.get(var4)).tag;
            return var5;
         }
      }

      var5 = null;
      return var5;
   }

   public View getCurrentTabView() {
      View var5;
      if(this.mCurrentTab >= 0) {
         int var1 = this.mCurrentTab;
         int var2 = this.mTabSpecs.size();
         if(var1 < var2) {
            List var3 = this.mTabSpecs;
            int var4 = this.mCurrentTab;
            var5 = ((MyTabHost.TabSpec)var3.get(var4)).view;
            return var5;
         }
      }

      var5 = null;
      return var5;
   }

   public View getCurrentView() {
      return this.mCurrentView;
   }

   public FrameLayout getTabContentView() {
      return this.mTabContent;
   }

   public List<MyTabHost.TabSpec> getTabSpecs() {
      return this.mTabSpecs;
   }

   public void handleTouchMode(boolean var1) {
      this.mHandleTouchMode = var1;
   }

   public MyTabHost.TabSpec myNewTabSpec(String var1, View var2) {
      return new MyTabHost.TabSpec(var1, var2, (MyTabHost.1)null);
   }

   public void onTouchModeChanged(boolean var1) {
      if(this.mHandleTouchMode) {
         if(!var1) {
            if(!this.mCurrentView.hasFocus() || this.mCurrentView.isFocused()) {
               List var2 = this.mTabSpecs;
               int var3 = this.mCurrentTab;
               boolean var4 = ((MyTabHost.TabSpec)var2.get(var3)).view.requestFocus();
            }
         }
      }
   }

   public void setCurrentTab(int var1) {
      if(var1 >= 0) {
         int var2 = this.mTabSpecs.size();
         if(var1 < var2) {
            int var3 = this.mCurrentTab;
            if(var1 != var3) {
               ((RadioButton)((RadioButton)((MyTabHost.TabSpec)this.mTabSpecs.get(var1)).view)).setChecked((boolean)1);
               if(this.mCurrentTab != -1) {
                  List var4 = this.mTabSpecs;
                  int var5 = this.mCurrentTab;
                  ((MyTabHost.TabSpec)var4.get(var5)).mContentStrategy.tabClosed();
               }

               this.mCurrentTab = var1;
               MyTabHost.TabSpec var6 = (MyTabHost.TabSpec)this.mTabSpecs.get(var1);
               List var7 = this.mTabSpecs;
               int var8 = this.mCurrentTab;
               boolean var9 = ((MyTabHost.TabSpec)var7.get(var8)).view.requestFocus();
               View var10 = var6.mContentStrategy.getContentView();
               this.mCurrentView = var10;
               if(this.mCurrentView.getParent() == null) {
                  FrameLayout var11 = this.mTabContent;
                  View var12 = this.mCurrentView;
                  android.view.ViewGroup.LayoutParams var13 = new android.view.ViewGroup.LayoutParams(-1, -1);
                  var11.addView(var12, var13);
               }

               if(!this.mTabs.hasFocus()) {
                  boolean var14 = this.mCurrentView.requestFocus();
               }

               this.invokeOnTabChangeListener();
            }
         }
      }
   }

   public void setCurrentTabByTag(String var1) {
      int var2 = 0;

      while(true) {
         int var3 = this.mTabSpecs.size();
         if(var2 >= var3) {
            return;
         }

         if(((MyTabHost.TabSpec)this.mTabSpecs.get(var2)).tag.equals(var1)) {
            this.setCurrentTab(var2);
            return;
         }

         ++var2;
      }
   }

   public void setOnTabChangedListener(MyTabHost.OnTabChangeListener var1) {
      this.mOnTabChangeListener = var1;
   }

   public void setup() {
      MyTabHost.1 var1 = new MyTabHost.1();
      this.mTabKeyListener = var1;
      FrameLayout var2 = (FrameLayout)this.findViewById(16908305);
      this.mTabContent = var2;
      if(this.mTabContent == null) {
         throw new RuntimeException("Your TabHost must have a FrameLayout whose id attribute is \'android.R.id.tabcontent\'");
      } else {
         ViewGroup var3 = (ViewGroup)this.findViewById(2131624203);
         this.mTabs = var3;
      }
   }

   public void setup(LocalActivityManager var1) {
      this.setup();
      this.mLocalActivityManager = var1;
   }

   private class IntentContentStrategy implements MyTabHost.ContentStrategy {

      private final Intent mIntent;
      private View mLaunchedView;
      private final String mTag;


      private IntentContentStrategy(String var2, Intent var3) {
         this.mTag = var2;
         this.mIntent = var3;
      }

      // $FF: synthetic method
      IntentContentStrategy(String var2, Intent var3, MyTabHost.1 var4) {
         this(var2, var3);
      }

      public View getContentView() {
         if(MyTabHost.this.mLocalActivityManager == null) {
            throw new IllegalStateException("Did you forget to call \'public void setup(LocalActivityManager activityGroup)\'?");
         } else {
            LocalActivityManager var1 = MyTabHost.this.mLocalActivityManager;
            String var2 = this.mTag;
            Intent var3 = this.mIntent;
            Window var4 = var1.startActivity(var2, var3);
            View var5;
            if(var4 != null) {
               var5 = var4.getDecorView();
            } else {
               var5 = null;
            }

            if(this.mLaunchedView != var5 && this.mLaunchedView != null && this.mLaunchedView.getParent() != null) {
               FrameLayout var6 = MyTabHost.this.mTabContent;
               View var7 = this.mLaunchedView;
               var6.removeView(var7);
            }

            this.mLaunchedView = var5;
            if(this.mLaunchedView != null) {
               this.mLaunchedView.setVisibility(0);
               this.mLaunchedView.setFocusableInTouchMode((boolean)1);
               ((ViewGroup)this.mLaunchedView).setDescendantFocusability(262144);
            }

            return this.mLaunchedView;
         }
      }

      public void tabClosed() {
         if(this.mLaunchedView != null) {
            this.mLaunchedView.setVisibility(8);
         }
      }
   }

   public class TabSpec {

      private MyTabHost.ContentStrategy mContentStrategy;
      public final String tag;
      public final View view;


      private TabSpec(String var2, View var3) {
         this.tag = var2;
         this.view = var3;
         var3.setTag(var2);
      }

      // $FF: synthetic method
      TabSpec(String var2, View var3, MyTabHost.1 var4) {
         this(var2, var3);
      }

      public MyTabHost.TabSpec setContent(Intent var1) {
         MyTabHost var2 = MyTabHost.this;
         String var3 = this.tag;
         MyTabHost.IntentContentStrategy var4 = var2.new IntentContentStrategy(var3, var1, (MyTabHost.1)null);
         this.mContentStrategy = var4;
         return this;
      }
   }

   public interface OnTabChangeListener {

      void onTabChanged(String var1);
   }

   private interface ContentStrategy {

      View getContentView();

      void tabClosed();
   }

   class 1 implements OnKeyListener {

      1() {}

      public boolean onKey(View var1, int var2, KeyEvent var3) {
         boolean var5;
         switch(var2) {
         case 19:
         case 20:
         case 21:
         case 22:
         case 23:
         case 66:
            var5 = false;
            break;
         default:
            boolean var4 = MyTabHost.this.mTabContent.requestFocus(2);
            var5 = MyTabHost.this.mTabContent.dispatchKeyEvent(var3);
         }

         return var5;
      }
   }
}
