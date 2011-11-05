package com.htc.android.mail;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import com.htc.widget.HtcAbsListView;
import com.htc.widget.HtcAdapterView;
import com.htc.widget.HtcListView;
import com.htc.widget.HtcAbsListView.OnScrollListener;
import com.htc.widget.HtcAdapterView.OnItemClickListener;

public abstract class BaseListActivity extends Activity {

   protected ListAdapter mAdapter;
   protected Context mAppContext;
   private boolean mFinishedStart;
   private Handler mHandler;
   protected InputMethodManager mInputManager;
   protected HtcListView mList;
   private Object mObjectLock;
   private OnItemClickListener mOnClickListener;
   private Runnable mRequestFocus;


   public BaseListActivity() {
      Object var1 = new Object();
      this.mObjectLock = var1;
      Handler var2 = new Handler();
      this.mHandler = var2;
      this.mFinishedStart = (boolean)0;
      BaseListActivity.2 var3 = new BaseListActivity.2();
      this.mRequestFocus = var3;
      BaseListActivity.3 var4 = new BaseListActivity.3();
      this.mOnClickListener = var4;
   }

   protected void ensureList() {
      if(this.mList == null) {
         this.setContentView(17367099);
      }
   }

   public ListAdapter getListAdapter() {
      return this.mAdapter;
   }

   public HtcListView getListView() {
      this.ensureList();
      return this.mList;
   }

   public long getSelectedItemId() {
      return this.mList.getSelectedItemId();
   }

   public int getSelectedItemPosition() {
      return this.mList.getSelectedItemPosition();
   }

   protected void initOnScrollListener() {
      HtcListView var1 = this.getListView();
      BaseListActivity.1 var2 = new BaseListActivity.1();
      var1.setOnScrollListener(var2);
   }

   public void onContentChanged() {
      super.onContentChanged();
      View var1 = this.findViewById(16908292);
      HtcListView var2 = (HtcListView)this.findViewById(16908298);
      this.mList = var2;
      if(this.mList == null) {
         throw new RuntimeException("Your content must have a HtcListView whose id attribute is \'android.R.id.list\'");
      } else {
         if(var1 != null) {
            this.mList.setEmptyView(var1);
         }

         HtcListView var3 = this.mList;
         OnItemClickListener var4 = this.mOnClickListener;
         var3.setOnItemClickListener(var4);
         if(this.mFinishedStart) {
            ListAdapter var5 = this.mAdapter;
            this.setListAdapter(var5);
         }

         Handler var6 = this.mHandler;
         Runnable var7 = this.mRequestFocus;
         var6.post(var7);
         this.mFinishedStart = (boolean)1;
      }
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      Context var2 = this.getApplicationContext();
      this.mAppContext = var2;
   }

   protected void onDestroy() {
      super.onDestroy();
      if(this.mList != null) {
         this.mList.setOnItemClickListener((OnItemClickListener)null);
         this.mList.setOnScrollListener((OnScrollListener)null);
      }
   }

   protected void onListItemClick(HtcListView var1, View var2, int var3, long var4) {}

   protected void onRestoreInstanceState(Bundle var1) {
      this.ensureList();
      super.onRestoreInstanceState(var1);
   }

   public void setListAdapter(ListAdapter var1) {
      Object var2 = this.mObjectLock;
      synchronized(var2) {
         this.ensureList();
         this.mAdapter = var1;
         this.mList.setAdapter(var1);
      }
   }

   public void setSelection(int var1) {
      this.mList.setSelection(var1);
   }

   class 1 implements OnScrollListener {

      1() {}

      public void onScroll(HtcAbsListView var1, int var2, int var3, int var4) {}

      public void onScrollStateChanged(HtcAbsListView var1, int var2) {
         if(var2 == 0) {
            System.gc();
         }
      }
   }

   class 2 implements Runnable {

      2() {}

      public void run() {
         HtcListView var1 = BaseListActivity.this.mList;
         HtcListView var2 = BaseListActivity.this.mList;
         var1.focusableViewAvailable(var2);
      }
   }

   class 3 implements OnItemClickListener {

      3() {}

      public void onItemClick(HtcAdapterView var1, View var2, int var3, long var4) {
         BaseListActivity var6 = BaseListActivity.this;
         HtcListView var7 = (HtcListView)var1;
         var6.onListItemClick(var7, var2, var3, var4);
      }
   }
}
