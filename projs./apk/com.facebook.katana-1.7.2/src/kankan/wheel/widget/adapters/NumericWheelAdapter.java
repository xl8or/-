package kankan.wheel.widget.adapters;

import android.content.Context;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;

public class NumericWheelAdapter extends AbstractWheelTextAdapter {

   public static final int DEFAULT_MAX_VALUE = 9;
   private static final int DEFAULT_MIN_VALUE;
   private String format;
   private int maxValue;
   private int minValue;


   public NumericWheelAdapter(Context var1) {
      this(var1, 0, 9);
   }

   public NumericWheelAdapter(Context var1, int var2, int var3) {
      this(var1, var2, var3, (String)null);
   }

   public NumericWheelAdapter(Context var1, int var2, int var3, String var4) {
      super(var1);
      this.minValue = var2;
      this.maxValue = var3;
      this.format = var4;
   }

   public CharSequence getItemText(int var1) {
      String var7;
      if(var1 >= 0) {
         int var2 = this.getItemsCount();
         if(var1 < var2) {
            int var3 = this.minValue + var1;
            if(this.format != null) {
               String var4 = this.format;
               Object[] var5 = new Object[1];
               Integer var6 = Integer.valueOf(var3);
               var5[0] = var6;
               var7 = String.format(var4, var5);
            } else {
               var7 = Integer.toString(var3);
            }

            return var7;
         }
      }

      var7 = null;
      return var7;
   }

   public int getItemsCount() {
      int var1 = this.maxValue;
      int var2 = this.minValue;
      return var1 - var2 + 1;
   }
}
