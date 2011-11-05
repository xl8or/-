package android.support.v4.app;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class FragmentStatePagerAdapter extends PagerAdapter {

   private static final boolean DEBUG = false;
   private static final String TAG = "FragmentStatePagerAdapter";
   private FragmentTransaction mCurTransaction = null;
   private final FragmentManager mFragmentManager;
   private ArrayList<Fragment> mFragments;
   private ArrayList<Fragment.SavedState> mSavedState;


   public FragmentStatePagerAdapter(FragmentManager var1) {
      ArrayList var2 = new ArrayList();
      this.mSavedState = var2;
      ArrayList var3 = new ArrayList();
      this.mFragments = var3;
      this.mFragmentManager = var1;
   }

   public void destroyItem(View var1, int var2, Object var3) {
      Fragment var4 = (Fragment)var3;
      if(this.mCurTransaction == null) {
         FragmentTransaction var5 = this.mFragmentManager.beginTransaction();
         this.mCurTransaction = var5;
      }

      while(this.mSavedState.size() <= var2) {
         boolean var6 = this.mSavedState.add((Object)null);
      }

      ArrayList var7 = this.mSavedState;
      Fragment.SavedState var8 = this.mFragmentManager.saveFragmentInstanceState(var4);
      var7.set(var2, var8);
      this.mFragments.set(var2, (Object)null);
      this.mCurTransaction.remove(var4);
   }

   public void finishUpdate(View var1) {
      if(this.mCurTransaction != null) {
         int var2 = this.mCurTransaction.commit();
         this.mCurTransaction = null;
         boolean var3 = this.mFragmentManager.executePendingTransactions();
      }
   }

   public abstract Fragment getItem(int var1);

   public Object instantiateItem(View var1, int var2) {
      Fragment var3;
      if(this.mFragments.size() > var2) {
         var3 = (Fragment)this.mFragments.get(var2);
         if(var3 != null) {
            return var3;
         }
      }

      if(this.mCurTransaction == null) {
         FragmentTransaction var4 = this.mFragmentManager.beginTransaction();
         this.mCurTransaction = var4;
      }

      Fragment var5 = this.getItem(var2);
      if(this.mSavedState.size() > var2) {
         Fragment.SavedState var6 = (Fragment.SavedState)this.mSavedState.get(var2);
         if(var6 != null) {
            var5.setInitialSavedState(var6);
         }
      }

      while(this.mFragments.size() <= var2) {
         boolean var7 = this.mFragments.add((Object)null);
      }

      this.mFragments.set(var2, var5);
      FragmentTransaction var9 = this.mCurTransaction;
      int var10 = var1.getId();
      var9.add(var10, var5);
      var3 = var5;
      return var3;
   }

   public boolean isViewFromObject(View var1, Object var2) {
      boolean var3;
      if(((Fragment)var2).getView() == var1) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public void restoreState(Parcelable var1, ClassLoader var2) {
      if(var1 != null) {
         Bundle var3 = (Bundle)var1;
         var3.setClassLoader(var2);
         Parcelable[] var4 = var3.getParcelableArray("states");
         this.mSavedState.clear();
         this.mFragments.clear();
         if(var4 != null) {
            int var5 = 0;

            while(true) {
               int var6 = var4.length;
               if(var5 >= var6) {
                  break;
               }

               ArrayList var7 = this.mSavedState;
               Fragment.SavedState var8 = (Fragment.SavedState)var4[var5];
               var7.add(var8);
               ++var5;
            }
         }

         Iterator var10 = var3.keySet().iterator();

         while(var10.hasNext()) {
            String var11 = (String)var10.next();
            if(var11.startsWith("f")) {
               int var12 = Integer.parseInt(var11.substring(1));
               Fragment var13 = this.mFragmentManager.getFragment(var3, var11);
               if(var13 != null) {
                  while(this.mFragments.size() <= var12) {
                     boolean var14 = this.mFragments.add((Object)null);
                  }

                  this.mFragments.set(var12, var13);
               } else {
                  String var16 = "Bad fragment at key " + var11;
                  int var17 = Log.w("FragmentStatePagerAdapter", var16);
               }
            }
         }

      }
   }

   public Parcelable saveState() {
      Bundle var1 = null;
      if(this.mSavedState.size() > 0) {
         var1 = new Bundle();
         Fragment.SavedState[] var2 = new Fragment.SavedState[this.mSavedState.size()];
         this.mSavedState.toArray(var2);
         var1.putParcelableArray("states", var2);
      }

      int var4 = 0;

      while(true) {
         int var5 = this.mFragments.size();
         if(var4 >= var5) {
            return var1;
         }

         Fragment var6 = (Fragment)this.mFragments.get(var4);
         if(var6 != null) {
            if(var1 == null) {
               var1 = new Bundle();
            }

            String var7 = "f" + var4;
            this.mFragmentManager.putFragment(var1, var7, var6);
         }

         ++var4;
      }
   }

   public void startUpdate(View var1) {}
}
