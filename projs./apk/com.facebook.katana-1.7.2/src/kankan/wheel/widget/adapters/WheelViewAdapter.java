package kankan.wheel.widget.adapters;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

public interface WheelViewAdapter {

   View getEmptyItem(View var1, ViewGroup var2);

   View getItem(int var1, View var2, ViewGroup var3);

   int getItemsCount();

   void registerDataSetObserver(DataSetObserver var1);

   void unregisterDataSetObserver(DataSetObserver var1);
}
