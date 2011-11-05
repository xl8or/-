package android.support.v4.app;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout.LayoutParams;

public class ListFragment extends Fragment {

   static final int INTERNAL_EMPTY_ID = 16711681;
   static final int INTERNAL_LIST_CONTAINER_ID = 16711683;
   static final int INTERNAL_PROGRESS_CONTAINER_ID = 16711682;
   ListAdapter mAdapter;
   CharSequence mEmptyText;
   View mEmptyView;
   private final Handler mHandler;
   ListView mList;
   View mListContainer;
   boolean mListShown;
   private final OnItemClickListener mOnClickListener;
   View mProgressContainer;
   private final Runnable mRequestFocus;
   TextView mStandardEmptyView;


   public ListFragment() {
      Handler var1 = new Handler();
      this.mHandler = var1;
      ListFragment.1 var2 = new ListFragment.1();
      this.mRequestFocus = var2;
      ListFragment.2 var3 = new ListFragment.2();
      this.mOnClickListener = var3;
   }

   private void ensureList() {
      if(this.mList == null) {
         View var1 = this.getView();
         if(var1 == null) {
            throw new IllegalStateException("Content view not yet created");
         } else {
            if(var1 instanceof ListView) {
               ListView var2 = (ListView)var1;
               this.mList = var2;
            } else {
               TextView var9 = (TextView)var1.findViewById(16711681);
               this.mStandardEmptyView = var9;
               if(this.mStandardEmptyView == null) {
                  View var10 = var1.findViewById(16908292);
                  this.mEmptyView = var10;
               } else {
                  this.mStandardEmptyView.setVisibility(8);
               }

               View var11 = var1.findViewById(16711682);
               this.mProgressContainer = var11;
               View var12 = var1.findViewById(16711683);
               this.mListContainer = var12;
               View var13 = var1.findViewById(16908298);
               if(!(var13 instanceof ListView)) {
                  if(var13 == null) {
                     throw new RuntimeException("Your content must have a ListView whose id attribute is \'android.R.id.list\'");
                  }

                  throw new RuntimeException("Content has view with id attribute \'android.R.id.list\' that is not a ListView class");
               }

               ListView var14 = (ListView)var13;
               this.mList = var14;
               if(this.mEmptyView != null) {
                  ListView var15 = this.mList;
                  View var16 = this.mEmptyView;
                  var15.setEmptyView(var16);
               } else if(this.mEmptyText != null) {
                  TextView var17 = this.mStandardEmptyView;
                  CharSequence var18 = this.mEmptyText;
                  var17.setText(var18);
                  ListView var19 = this.mList;
                  TextView var20 = this.mStandardEmptyView;
                  var19.setEmptyView(var20);
               }
            }

            this.mListShown = (boolean)1;
            ListView var3 = this.mList;
            OnItemClickListener var4 = this.mOnClickListener;
            var3.setOnItemClickListener(var4);
            if(this.mAdapter != null) {
               ListAdapter var5 = this.mAdapter;
               this.mAdapter = null;
               this.setListAdapter(var5);
            } else if(this.mProgressContainer != null) {
               this.setListShown((boolean)0, (boolean)0);
            }

            Handler var6 = this.mHandler;
            Runnable var7 = this.mRequestFocus;
            var6.post(var7);
         }
      }
   }

   private void setListShown(boolean var1, boolean var2) {
      this.ensureList();
      if(this.mProgressContainer == null) {
         throw new IllegalStateException("Can\'t be used with a custom content view");
      } else if(this.mListShown != var1) {
         this.mListShown = var1;
         if(var1) {
            if(var2) {
               View var3 = this.mProgressContainer;
               Animation var4 = AnimationUtils.loadAnimation(this.getActivity(), 17432577);
               var3.startAnimation(var4);
               View var5 = this.mListContainer;
               Animation var6 = AnimationUtils.loadAnimation(this.getActivity(), 17432576);
               var5.startAnimation(var6);
            } else {
               this.mProgressContainer.clearAnimation();
               this.mListContainer.clearAnimation();
            }

            this.mProgressContainer.setVisibility(8);
            this.mListContainer.setVisibility(0);
         } else {
            if(var2) {
               View var7 = this.mProgressContainer;
               Animation var8 = AnimationUtils.loadAnimation(this.getActivity(), 17432576);
               var7.startAnimation(var8);
               View var9 = this.mListContainer;
               Animation var10 = AnimationUtils.loadAnimation(this.getActivity(), 17432577);
               var9.startAnimation(var10);
            } else {
               this.mProgressContainer.clearAnimation();
               this.mListContainer.clearAnimation();
            }

            this.mProgressContainer.setVisibility(0);
            this.mListContainer.setVisibility(8);
         }
      }
   }

   public ListAdapter getListAdapter() {
      return this.mAdapter;
   }

   public ListView getListView() {
      this.ensureList();
      return this.mList;
   }

   public long getSelectedItemId() {
      this.ensureList();
      return this.mList.getSelectedItemId();
   }

   public int getSelectedItemPosition() {
      this.ensureList();
      return this.mList.getSelectedItemPosition();
   }

   public View onCreateView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
      FragmentActivity var4 = this.getActivity();
      FrameLayout var5 = new FrameLayout(var4);
      LinearLayout var6 = new LinearLayout(var4);
      var6.setId(16711682);
      var6.setOrientation(1);
      var6.setVisibility(8);
      var6.setGravity(17);
      ProgressBar var7 = new ProgressBar(var4, (AttributeSet)null, 16842874);
      LayoutParams var8 = new LayoutParams(-1, -1);
      var6.addView(var7, var8);
      LayoutParams var9 = new LayoutParams(-1, -1);
      var5.addView(var6, var9);
      FrameLayout var10 = new FrameLayout(var4);
      var10.setId(16711683);
      FragmentActivity var11 = this.getActivity();
      TextView var12 = new TextView(var11);
      var12.setId(16711681);
      var12.setGravity(17);
      LayoutParams var13 = new LayoutParams(-1, -1);
      var10.addView(var12, var13);
      FragmentActivity var14 = this.getActivity();
      ListView var15 = new ListView(var14);
      var15.setId(16908298);
      var15.setDrawSelectorOnTop((boolean)0);
      LayoutParams var16 = new LayoutParams(-1, -1);
      var10.addView(var15, var16);
      LayoutParams var17 = new LayoutParams(-1, -1);
      var5.addView(var10, var17);
      LayoutParams var18 = new LayoutParams(-1, -1);
      var5.setLayoutParams(var18);
      return var5;
   }

   public void onDestroyView() {
      Handler var1 = this.mHandler;
      Runnable var2 = this.mRequestFocus;
      var1.removeCallbacks(var2);
      this.mList = null;
      this.mListShown = (boolean)0;
      this.mListContainer = null;
      this.mProgressContainer = null;
      this.mEmptyView = null;
      this.mStandardEmptyView = null;
      super.onDestroyView();
   }

   public void onListItemClick(ListView var1, View var2, int var3, long var4) {}

   public void onViewCreated(View var1, Bundle var2) {
      super.onViewCreated(var1, var2);
      this.ensureList();
   }

   public void setEmptyText(CharSequence var1) {
      this.ensureList();
      if(this.mStandardEmptyView == null) {
         throw new IllegalStateException("Can\'t be used with a custom content view");
      } else {
         this.mStandardEmptyView.setText(var1);
         if(this.mEmptyText == null) {
            ListView var2 = this.mList;
            TextView var3 = this.mStandardEmptyView;
            var2.setEmptyView(var3);
         }

         this.mEmptyText = var1;
      }
   }

   public void setListAdapter(ListAdapter var1) {
      byte var2 = 0;
      boolean var3;
      if(this.mAdapter != null) {
         var3 = true;
      } else {
         var3 = false;
      }

      this.mAdapter = var1;
      if(this.mList != null) {
         this.mList.setAdapter(var1);
         if(!this.mListShown) {
            if(!var3) {
               if(this.getView().getWindowToken() != null) {
                  var2 = 1;
               }

               this.setListShown((boolean)1, (boolean)var2);
            }
         }
      }
   }

   public void setListShown(boolean var1) {
      this.setListShown(var1, (boolean)1);
   }

   public void setListShownNoAnimation(boolean var1) {
      this.setListShown(var1, (boolean)0);
   }

   public void setSelection(int var1) {
      this.ensureList();
      this.mList.setSelection(var1);
   }

   class 1 implements Runnable {

      1() {}

      public void run() {
         ListView var1 = ListFragment.this.mList;
         ListView var2 = ListFragment.this.mList;
         var1.focusableViewAvailable(var2);
      }
   }

   class 2 implements OnItemClickListener {

      2() {}

      public void onItemClick(AdapterView<?> var1, View var2, int var3, long var4) {
         ListFragment var6 = ListFragment.this;
         ListView var7 = (ListView)var1;
         var6.onListItemClick(var7, var2, var3, var4);
      }
   }
}
