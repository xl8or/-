package kankan.wheel.widget.adapters;

import android.content.Context;
import kankan.wheel.widget.WheelAdapter;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;

public class AdapterWheel extends AbstractWheelTextAdapter {

   private WheelAdapter adapter;


   public AdapterWheel(Context var1, WheelAdapter var2) {
      super(var1);
      this.adapter = var2;
   }

   public WheelAdapter getAdapter() {
      return this.adapter;
   }

   protected CharSequence getItemText(int var1) {
      return this.adapter.getItem(var1);
   }

   public int getItemsCount() {
      return this.adapter.getItemsCount();
   }
}
