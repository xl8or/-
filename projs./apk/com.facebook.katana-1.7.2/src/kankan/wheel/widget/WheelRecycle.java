package kankan.wheel.widget;

import android.view.View;
import android.widget.LinearLayout;
import java.util.LinkedList;
import java.util.List;
import kankan.wheel.widget.ItemsRange;
import kankan.wheel.widget.WheelView;

public class WheelRecycle {

   private List<View> emptyItems;
   private List<View> items;
   private WheelView wheel;


   public WheelRecycle(WheelView var1) {
      this.wheel = var1;
   }

   private List<View> addView(View var1, List<View> var2) {
      if(var2 == null) {
         var2 = new LinkedList();
      }

      ((List)var2).add(var1);
      return (List)var2;
   }

   private View getCachedView(List<View> var1) {
      View var4;
      if(var1 != null && var1.size() > 0) {
         View var2 = (View)var1.get(0);
         Object var3 = var1.remove(0);
         var4 = var2;
      } else {
         var4 = null;
      }

      return var4;
   }

   private void recycleView(View var1, int var2) {
      int var3 = this.wheel.getViewAdapter().getItemsCount();
      if((var2 < 0 || var2 >= var3) && !this.wheel.isCyclic()) {
         List var4 = this.emptyItems;
         List var5 = this.addView(var1, var4);
         this.emptyItems = var5;
      } else {
         while(var2 < 0) {
            var2 += var3;
         }

         int var10000 = var2 % var3;
         List var7 = this.items;
         List var8 = this.addView(var1, var7);
         this.items = var8;
      }
   }

   public void clearAll() {
      if(this.items != null) {
         this.items.clear();
      }

      if(this.emptyItems != null) {
         this.emptyItems.clear();
      }
   }

   public View getEmptyItem() {
      List var1 = this.emptyItems;
      return this.getCachedView(var1);
   }

   public View getItem() {
      List var1 = this.items;
      return this.getCachedView(var1);
   }

   public int recycleItems(LinearLayout var1, int var2, ItemsRange var3) {
      int var4 = var2;
      int var5 = 0;

      while(true) {
         int var6 = var1.getChildCount();
         if(var5 >= var6) {
            return var2;
         }

         if(!var3.contains(var4)) {
            View var7 = var1.getChildAt(var5);
            this.recycleView(var7, var4);
            var1.removeViewAt(var5);
            if(var5 == 0) {
               ++var2;
            }
         } else {
            ++var5;
         }

         ++var4;
      }
   }
}
