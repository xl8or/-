package com.android.settings.applications;

import android.app.ActivityManager;
import android.app.Dialog;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.text.format.DateUtils;
import android.text.format.Formatter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.RecyclerListener;
import android.widget.AdapterView.OnItemClickListener;
import com.android.settings.applications.LinearColorBar;
import com.android.settings.applications.RunningServiceDetails;
import com.android.settings.applications.RunningState;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class RunningProcessesView extends FrameLayout implements OnItemClickListener, RecyclerListener, RunningState.OnRefreshUiListener {

   static final long PAGE_SIZE = 4096L;
   private static final String TAG = "RunningProcessesView";
   long SECONDARY_SERVER_MEM;
   final HashMap<View, RunningProcessesView.ActiveItem> mActiveItems;
   RunningProcessesView.ServiceListAdapter mAdapter;
   ActivityManager mAm;
   TextView mBackgroundProcessText;
   byte[] mBuffer;
   StringBuilder mBuilder;
   LinearColorBar mColorBar;
   Dialog mCurDialog;
   RunningState.BaseItem mCurSelected;
   Runnable mDataAvail;
   TextView mForegroundProcessText;
   long mLastAvailMemory;
   long mLastBackgroundProcessMemory;
   long mLastForegroundProcessMemory;
   int mLastNumBackgroundProcesses;
   int mLastNumForegroundProcesses;
   int mLastNumServiceProcesses;
   long mLastServiceProcessMemory;
   ListView mListView;
   RunningState mState;


   public RunningProcessesView(Context var1, AttributeSet var2) {
      super(var1, var2);
      HashMap var3 = new HashMap();
      this.mActiveItems = var3;
      StringBuilder var4 = new StringBuilder(128);
      this.mBuilder = var4;
      this.mLastNumBackgroundProcesses = -1;
      this.mLastNumForegroundProcesses = -1;
      this.mLastNumServiceProcesses = -1;
      this.mLastBackgroundProcessMemory = 65535L;
      this.mLastForegroundProcessMemory = 65535L;
      this.mLastServiceProcessMemory = 65535L;
      this.mLastAvailMemory = 65535L;
      byte[] var5 = new byte[1024];
      this.mBuffer = var5;
   }

   private long extractMemValue(byte[] var1, int var2) {
      while(true) {
         int var3 = var1.length;
         long var8;
         if(var2 < var3 && var1[var2] != 10) {
            if(var1[var2] < 48 || var1[var2] > 57) {
               ++var2;
               continue;
            }

            int var4 = var2++;

            while(true) {
               int var5 = var1.length;
               if(var2 >= var5 || var1[var2] < 48 || var1[var2] > 57) {
                  int var7 = var2 - var4;
                  var8 = (long)Integer.parseInt(new String(var1, 0, var4, var7)) * 1024L;
                  return var8;
               }

               int var6 = var2 + 1;
            }
         }

         var8 = 0L;
         return var8;
      }
   }

   private boolean matchText(byte[] var1, int var2, String var3) {
      int var4 = var3.length();
      int var5 = var2 + var4;
      int var6 = var1.length;
      boolean var7;
      if(var5 >= var6) {
         var7 = false;
      } else {
         int var8 = 0;

         while(true) {
            if(var8 >= var4) {
               var7 = true;
               break;
            }

            int var9 = var2 + var8;
            byte var10 = var1[var9];
            char var11 = var3.charAt(var8);
            if(var10 != var11) {
               var7 = false;
               break;
            }

            ++var8;
         }
      }

      return var7;
   }

   private long readAvailMem() {
      // $FF: Couldn't be decompiled
   }

   public void doCreate(Bundle var1, Object var2) {
      ActivityManager var3 = (ActivityManager)this.getContext().getSystemService("activity");
      this.mAm = var3;
      RunningState var4 = RunningState.getInstance(this.getContext());
      this.mState = var4;
      View var5 = ((LayoutInflater)this.getContext().getSystemService("layout_inflater")).inflate(2130903124, this);
      ListView var6 = (ListView)this.findViewById(16908298);
      this.mListView = var6;
      View var7 = this.findViewById(16908292);
      if(var7 != null) {
         this.mListView.setEmptyView(var7);
      }

      this.mListView.setOnItemClickListener(this);
      this.mListView.setRecyclerListener(this);
      RunningState var8 = this.mState;
      RunningProcessesView.ServiceListAdapter var9 = new RunningProcessesView.ServiceListAdapter(var8);
      this.mAdapter = var9;
      ListView var10 = this.mListView;
      RunningProcessesView.ServiceListAdapter var11 = this.mAdapter;
      var10.setAdapter(var11);
      LinearColorBar var12 = (LinearColorBar)this.findViewById(2131427619);
      this.mColorBar = var12;
      TextView var13 = (TextView)this.findViewById(2131427621);
      this.mBackgroundProcessText = var13;
      TextView var14 = this.mBackgroundProcessText;
      RunningProcessesView.1 var15 = new RunningProcessesView.1();
      var14.setOnClickListener(var15);
      TextView var16 = (TextView)this.findViewById(2131427620);
      this.mForegroundProcessText = var16;
      TextView var17 = this.mForegroundProcessText;
      RunningProcessesView.2 var18 = new RunningProcessesView.2();
      var17.setOnClickListener(var18);
      long var19 = (long)Integer.valueOf(SystemProperties.get("ro.SECONDARY_SERVER_MEM")).intValue() * 4096L;
      this.SECONDARY_SERVER_MEM = var19;
   }

   public void doPause() {
      this.mState.pause();
      this.mDataAvail = null;
   }

   public boolean doResume(Runnable var1) {
      this.mState.resume(this);
      boolean var2;
      if(this.mState.hasData()) {
         this.refreshUi((boolean)1);
         var2 = true;
      } else {
         this.mDataAvail = var1;
         var2 = false;
      }

      return var2;
   }

   public Object doRetainNonConfigurationInstance() {
      return null;
   }

   public void onItemClick(AdapterView<?> var1, View var2, int var3, long var4) {
      RunningState.MergedItem var6 = (RunningState.MergedItem)((ListView)var1).getAdapter().getItem(var3);
      this.mCurSelected = var6;
      Intent var7 = new Intent();
      int var8 = var6.mProcess.mUid;
      var7.putExtra("uid", var8);
      String var10 = var6.mProcess.mProcessName;
      var7.putExtra("process", var10);
      boolean var12 = this.mAdapter.mShowBackground;
      var7.putExtra("background", var12);
      Context var14 = this.getContext();
      var7.setClass(var14, RunningServiceDetails.class);
      this.getContext().startActivity(var7);
   }

   public void onMovedToScrapHeap(View var1) {
      this.mActiveItems.remove(var1);
   }

   public void onRefreshUi(int var1) {
      switch(var1) {
      case 0:
         this.updateTimes();
         return;
      case 1:
         this.refreshUi((boolean)0);
         this.updateTimes();
         return;
      case 2:
         this.refreshUi((boolean)1);
         this.updateTimes();
         return;
      default:
      }
   }

   void refreshUi(boolean var1) {
      if(var1) {
         RunningProcessesView.ServiceListAdapter var2 = (RunningProcessesView.ServiceListAdapter)((RunningProcessesView.ServiceListAdapter)this.mListView.getAdapter());
         var2.refreshItems();
         var2.notifyDataSetChanged();
      }

      if(this.mDataAvail != null) {
         this.mDataAvail.run();
         this.mDataAvail = null;
      }

      long var3 = this.readAvailMem();
      long var5 = this.SECONDARY_SERVER_MEM;
      long var7 = var3 - var5;
      if(var7 < 0L) {
         var7 = 0L;
      }

      Object var9 = this.mState.mLock;
      synchronized(var9) {
         label36: {
            int var10 = this.mLastNumBackgroundProcesses;
            int var11 = this.mState.mNumBackgroundProcesses;
            if(var10 == var11) {
               long var12 = this.mLastBackgroundProcessMemory;
               long var14 = this.mState.mBackgroundProcessMemory;
               if(var12 == var14 && this.mLastAvailMemory == var7) {
                  break label36;
               }
            }

            int var16 = this.mState.mNumBackgroundProcesses;
            this.mLastNumBackgroundProcesses = var16;
            long var17 = this.mState.mBackgroundProcessMemory;
            this.mLastBackgroundProcessMemory = var17;
            this.mLastAvailMemory = var7;
            Context var19 = this.getContext();
            long var20 = this.mLastAvailMemory;
            long var22 = this.mLastBackgroundProcessMemory;
            long var24 = var20 + var22;
            String var26 = Formatter.formatShortFileSize(var19, var24);
            TextView var27 = this.mBackgroundProcessText;
            Resources var28 = this.getResources();
            Object[] var29 = new Object[]{var26};
            String var30 = var28.getString(2131231671, var29);
            var27.setText(var30);
         }

         label30: {
            int var31 = this.mLastNumForegroundProcesses;
            int var32 = this.mState.mNumForegroundProcesses;
            if(var31 == var32) {
               long var33 = this.mLastForegroundProcessMemory;
               long var35 = this.mState.mForegroundProcessMemory;
               if(var33 == var35) {
                  int var37 = this.mLastNumServiceProcesses;
                  int var38 = this.mState.mNumServiceProcesses;
                  if(var37 == var38) {
                     long var39 = this.mLastServiceProcessMemory;
                     long var41 = this.mState.mServiceProcessMemory;
                     if(var39 == var41) {
                        break label30;
                     }
                  }
               }
            }

            int var43 = this.mState.mNumForegroundProcesses;
            this.mLastNumForegroundProcesses = var43;
            long var44 = this.mState.mForegroundProcessMemory;
            this.mLastForegroundProcessMemory = var44;
            int var46 = this.mState.mNumServiceProcesses;
            this.mLastNumServiceProcesses = var46;
            long var47 = this.mState.mServiceProcessMemory;
            this.mLastServiceProcessMemory = var47;
            Context var49 = this.getContext();
            long var50 = this.mLastForegroundProcessMemory;
            long var52 = this.mLastServiceProcessMemory;
            long var54 = var50 + var52;
            String var56 = Formatter.formatShortFileSize(var49, var54);
            TextView var57 = this.mForegroundProcessText;
            Resources var58 = this.getResources();
            Object[] var59 = new Object[]{var56};
            String var60 = var58.getString(2131231672, var59);
            var57.setText(var60);
         }

         long var61 = this.mLastBackgroundProcessMemory + var7;
         long var63 = this.mLastForegroundProcessMemory;
         long var65 = var61 + var63;
         long var67 = this.mLastServiceProcessMemory;
         float var69 = (float)(var65 + var67);
         LinearColorBar var70 = this.mColorBar;
         float var71 = (float)this.mLastForegroundProcessMemory / var69;
         float var72 = (float)this.mLastServiceProcessMemory / var69;
         float var73 = (float)this.mLastBackgroundProcessMemory / var69;
         var70.setRatios(var71, var72, var73);
      }
   }

   void updateTimes() {
      Iterator var1 = this.mActiveItems.values().iterator();

      while(var1.hasNext()) {
         RunningProcessesView.ActiveItem var2 = (RunningProcessesView.ActiveItem)var1.next();
         if(var2.mRootView.getWindowToken() == null) {
            var1.remove();
         } else {
            Context var3 = this.getContext();
            StringBuilder var4 = this.mBuilder;
            var2.updateTime(var3, var4);
         }
      }

   }

   public static class ActiveItem {

      long mFirstRunTime;
      RunningProcessesView.ViewHolder mHolder;
      RunningState.BaseItem mItem;
      View mRootView;
      RunningServiceInfo mService;
      boolean mSetBackground;


      public ActiveItem() {}

      void updateTime(Context var1, StringBuilder var2) {
         TextView var3 = null;
         if(this.mItem instanceof RunningState.ServiceItem) {
            var3 = this.mHolder.size;
         } else {
            String var11;
            if(this.mItem.mSizeStr != null) {
               var11 = this.mItem.mSizeStr;
            } else {
               var11 = "";
            }

            String var12 = this.mItem.mCurSizeStr;
            if(!var11.equals(var12)) {
               this.mItem.mCurSizeStr = var11;
               this.mHolder.size.setText(var11);
            }

            if(this.mItem.mBackground) {
               if(!this.mSetBackground) {
                  this.mSetBackground = (boolean)1;
                  this.mHolder.uptime.setText("");
               }
            } else if(this.mItem instanceof RunningState.MergedItem) {
               var3 = this.mHolder.uptime;
            }
         }

         if(var3 != null) {
            this.mSetBackground = (boolean)0;
            if(this.mFirstRunTime >= 0L) {
               long var4 = SystemClock.elapsedRealtime();
               long var6 = this.mFirstRunTime;
               long var8 = (var4 - var6) / 1000L;
               String var10 = DateUtils.formatElapsedTime(var2, var8);
               var3.setText(var10);
            } else {
               boolean var13 = false;
               if(this.mItem instanceof RunningState.MergedItem) {
                  if(((RunningState.MergedItem)this.mItem).mServices.size() > 0) {
                     var13 = true;
                  } else {
                     var13 = false;
                  }
               }

               if(var13) {
                  CharSequence var14 = var1.getResources().getText(2131231666);
                  var3.setText(var14);
               } else {
                  var3.setText("");
               }
            }
         }
      }
   }

   public static class ViewHolder {

      public TextView description;
      public ImageView icon;
      public TextView name;
      public View rootView;
      public TextView size;
      public TextView uptime;


      public ViewHolder(View var1) {
         this.rootView = var1;
         ImageView var2 = (ImageView)var1.findViewById(2131427364);
         this.icon = var2;
         TextView var3 = (TextView)var1.findViewById(2131427398);
         this.name = var3;
         TextView var4 = (TextView)var1.findViewById(2131427400);
         this.description = var4;
         TextView var5 = (TextView)var1.findViewById(2131427618);
         this.size = var5;
         TextView var6 = (TextView)var1.findViewById(2131427349);
         this.uptime = var6;
         var1.setTag(this);
      }

      public RunningProcessesView.ActiveItem bind(RunningState var1, RunningState.BaseItem var2, StringBuilder var3) {
         Object var4 = var1.mLock;
         synchronized(var4) {
            PackageManager var5 = this.rootView.getContext().getPackageManager();
            if(var2.mPackageInfo == null && var2 instanceof RunningState.MergedItem) {
               ((RunningState.MergedItem)var2).mProcess.ensureLabel(var5);
               PackageItemInfo var6 = ((RunningState.MergedItem)var2).mProcess.mPackageInfo;
               var2.mPackageInfo = var6;
               CharSequence var7 = ((RunningState.MergedItem)var2).mProcess.mDisplayLabel;
               var2.mDisplayLabel = var7;
            }

            TextView var8 = this.name;
            CharSequence var9 = var2.mDisplayLabel;
            var8.setText(var9);
            RunningProcessesView.ActiveItem var10 = new RunningProcessesView.ActiveItem();
            View var11 = this.rootView;
            var10.mRootView = var11;
            var10.mItem = var2;
            var10.mHolder = this;
            long var12 = var2.mActiveSince;
            var10.mFirstRunTime = var12;
            if(var2.mBackground) {
               TextView var14 = this.description;
               CharSequence var15 = this.rootView.getContext().getText(2131231667);
               var14.setText(var15);
            } else {
               TextView var19 = this.description;
               String var20 = var2.mDescription;
               var19.setText(var20);
            }

            var2.mCurSizeStr = null;
            if(var2.mPackageInfo != null) {
               ImageView var16 = this.icon;
               Drawable var17 = var2.mPackageInfo.loadIcon(var5);
               var16.setImageDrawable(var17);
            }

            this.icon.setVisibility(0);
            Context var18 = this.rootView.getContext();
            var10.updateTime(var18, var3);
            return var10;
         }
      }
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(View var1) {
         RunningProcessesView.this.mAdapter.setShowBackground((boolean)1);
      }
   }

   class ServiceListAdapter extends BaseAdapter {

      final LayoutInflater mInflater;
      ArrayList<RunningState.MergedItem> mItems;
      boolean mShowBackground;
      final RunningState mState;


      ServiceListAdapter(RunningState var2) {
         this.mState = var2;
         LayoutInflater var3 = (LayoutInflater)RunningProcessesView.this.getContext().getSystemService("layout_inflater");
         this.mInflater = var3;
         this.refreshItems();
      }

      public boolean areAllItemsEnabled() {
         return false;
      }

      public void bindView(View var1, int var2) {
         Object var3 = this.mState.mLock;
         synchronized(var3) {
            int var4 = this.mItems.size();
            if(var2 < var4) {
               RunningProcessesView.ViewHolder var5 = (RunningProcessesView.ViewHolder)var1.getTag();
               RunningState.MergedItem var6 = (RunningState.MergedItem)this.mItems.get(var2);
               RunningState var7 = this.mState;
               StringBuilder var8 = RunningProcessesView.this.mBuilder;
               RunningProcessesView.ActiveItem var9 = var5.bind(var7, var6, var8);
               RunningProcessesView.this.mActiveItems.put(var1, var9);
            }
         }
      }

      public int getCount() {
         return this.mItems.size();
      }

      public Object getItem(int var1) {
         return this.mItems.get(var1);
      }

      public long getItemId(int var1) {
         return (long)((RunningState.MergedItem)this.mItems.get(var1)).hashCode();
      }

      boolean getShowBackground() {
         return this.mShowBackground;
      }

      public View getView(int var1, View var2, ViewGroup var3) {
         View var4;
         if(var2 == null) {
            var4 = this.newView(var3);
         } else {
            var4 = var2;
         }

         this.bindView(var4, var1);
         return var4;
      }

      public boolean hasStableIds() {
         return true;
      }

      public boolean isEmpty() {
         boolean var1;
         if(this.mState.hasData() && this.mItems.size() == 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public boolean isEnabled(int var1) {
         boolean var2;
         if(!((RunningState.MergedItem)this.mItems.get(var1)).mIsProcess) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public View newView(ViewGroup var1) {
         View var2 = this.mInflater.inflate(2130903123, var1, (boolean)0);
         new RunningProcessesView.ViewHolder(var2);
         return var2;
      }

      void refreshItems() {
         ArrayList var1;
         if(this.mShowBackground) {
            var1 = this.mState.getCurrentBackgroundItems();
         } else {
            var1 = this.mState.getCurrentMergedItems();
         }

         if(this.mItems != var1) {
            this.mItems = var1;
         }

         if(this.mItems == null) {
            ArrayList var2 = new ArrayList();
            this.mItems = var2;
         }
      }

      void setShowBackground(boolean var1) {
         if(this.mShowBackground != var1) {
            this.mShowBackground = var1;
            this.mState.setWatchingBackgroundItems(var1);
            this.refreshItems();
            this.notifyDataSetChanged();
            LinearColorBar var2 = RunningProcessesView.this.mColorBar;
            boolean var3 = this.mShowBackground;
            var2.setShowingGreen(var3);
         }
      }
   }

   class 2 implements OnClickListener {

      2() {}

      public void onClick(View var1) {
         RunningProcessesView.this.mAdapter.setShowBackground((boolean)0);
      }
   }

   static class TimeTicker extends TextView {

      public TimeTicker(Context var1, AttributeSet var2) {
         super(var1, var2);
      }
   }
}
