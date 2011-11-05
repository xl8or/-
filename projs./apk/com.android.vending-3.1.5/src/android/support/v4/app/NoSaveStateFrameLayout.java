package android.support.v4.app;

import android.content.Context;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

public class NoSaveStateFrameLayout extends FrameLayout {

   public NoSaveStateFrameLayout(Context var1) {
      super(var1);
   }

   static ViewGroup wrap(View var0) {
      Context var1 = var0.getContext();
      NoSaveStateFrameLayout var2 = new NoSaveStateFrameLayout(var1);
      LayoutParams var3 = var0.getLayoutParams();
      if(var3 != null) {
         var2.setLayoutParams(var3);
      }

      android.widget.FrameLayout.LayoutParams var4 = new android.widget.FrameLayout.LayoutParams(-1, -1);
      var0.setLayoutParams(var4);
      var2.addView(var0);
      return var2;
   }

   protected void dispatchRestoreInstanceState(SparseArray<Parcelable> var1) {
      this.dispatchThawSelfOnly(var1);
   }

   protected void dispatchSaveInstanceState(SparseArray<Parcelable> var1) {
      this.dispatchFreezeSelfOnly(var1);
   }
}
