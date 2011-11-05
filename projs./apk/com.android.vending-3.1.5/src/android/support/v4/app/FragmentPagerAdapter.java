package android.support.v4.app;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;

public abstract class FragmentPagerAdapter extends PagerAdapter {

   private static final boolean DEBUG = false;
   private static final String TAG = "FragmentPagerAdapter";
   private FragmentTransaction mCurTransaction = null;
   private final FragmentManager mFragmentManager;


   public FragmentPagerAdapter(FragmentManager var1) {
      this.mFragmentManager = var1;
   }

   private static String makeFragmentName(int var0, int var1) {
      return "android:switcher:" + var0 + ":" + var1;
   }

   public void destroyItem(View var1, int var2, Object var3) {
      if(this.mCurTransaction == null) {
         FragmentTransaction var4 = this.mFragmentManager.beginTransaction();
         this.mCurTransaction = var4;
      }

      FragmentTransaction var5 = this.mCurTransaction;
      Fragment var6 = (Fragment)var3;
      var5.detach(var6);
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
      if(this.mCurTransaction == null) {
         FragmentTransaction var3 = this.mFragmentManager.beginTransaction();
         this.mCurTransaction = var3;
      }

      String var4 = makeFragmentName(var1.getId(), var2);
      Fragment var5 = this.mFragmentManager.findFragmentByTag(var4);
      if(var5 != null) {
         this.mCurTransaction.attach(var5);
      } else {
         var5 = this.getItem(var2);
         FragmentTransaction var7 = this.mCurTransaction;
         int var8 = var1.getId();
         String var9 = makeFragmentName(var1.getId(), var2);
         var7.add(var8, var5, var9);
      }

      return var5;
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

   public void restoreState(Parcelable var1, ClassLoader var2) {}

   public Parcelable saveState() {
      return null;
   }

   public void startUpdate(View var1) {}
}
