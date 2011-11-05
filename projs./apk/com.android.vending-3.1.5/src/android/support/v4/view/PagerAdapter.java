package android.support.v4.view;

import android.os.Parcelable;
import android.view.View;

public abstract class PagerAdapter {

   public static final int POSITION_NONE = 254;
   public static final int POSITION_UNCHANGED = 255;
   private PagerAdapter.DataSetObserver mObserver;


   public PagerAdapter() {}

   public abstract void destroyItem(View var1, int var2, Object var3);

   public abstract void finishUpdate(View var1);

   public abstract int getCount();

   public int getItemPosition(Object var1) {
      return -1;
   }

   public abstract Object instantiateItem(View var1, int var2);

   public abstract boolean isViewFromObject(View var1, Object var2);

   public void notifyDataSetChanged() {
      if(this.mObserver != null) {
         this.mObserver.onDataSetChanged();
      }
   }

   public abstract void restoreState(Parcelable var1, ClassLoader var2);

   public abstract Parcelable saveState();

   void setDataSetObserver(PagerAdapter.DataSetObserver var1) {
      this.mObserver = var1;
   }

   public abstract void startUpdate(View var1);

   interface DataSetObserver {

      void onDataSetChanged();
   }
}
