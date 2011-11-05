package kankan.wheel.widget.adapters;

import android.content.Context;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;

public class ArrayWheelAdapter<T extends Object> extends AbstractWheelTextAdapter {

   private T[] items;


   public ArrayWheelAdapter(Context var1, T[] var2) {
      super(var1);
      this.items = var2;
   }

   public CharSequence getItemText(int var1) {
      Object var4;
      if(var1 >= 0) {
         int var2 = this.items.length;
         if(var1 < var2) {
            Object var3 = this.items[var1];
            if(var3 instanceof CharSequence) {
               var4 = (CharSequence)var3;
            } else {
               var4 = var3.toString();
            }

            return (CharSequence)var4;
         }
      }

      var4 = null;
      return (CharSequence)var4;
   }

   public int getItemsCount() {
      return this.items.length;
   }
}
