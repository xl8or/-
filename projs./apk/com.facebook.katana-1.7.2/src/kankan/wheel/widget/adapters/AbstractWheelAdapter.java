package kankan.wheel.widget.adapters;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import kankan.wheel.widget.adapters.WheelViewAdapter;

public abstract class AbstractWheelAdapter implements WheelViewAdapter {

   private List<DataSetObserver> datasetObservers;


   public AbstractWheelAdapter() {}

   public View getEmptyItem(View var1, ViewGroup var2) {
      return null;
   }

   protected void notifyDataChangedEvent() {
      if(this.datasetObservers != null) {
         Iterator var1 = this.datasetObservers.iterator();

         while(var1.hasNext()) {
            ((DataSetObserver)var1.next()).onChanged();
         }

      }
   }

   protected void notifyDataInvalidatedEvent() {
      if(this.datasetObservers != null) {
         Iterator var1 = this.datasetObservers.iterator();

         while(var1.hasNext()) {
            ((DataSetObserver)var1.next()).onInvalidated();
         }

      }
   }

   public void registerDataSetObserver(DataSetObserver var1) {
      if(this.datasetObservers == null) {
         LinkedList var2 = new LinkedList();
         this.datasetObservers = var2;
      }

      this.datasetObservers.add(var1);
   }

   public void unregisterDataSetObserver(DataSetObserver var1) {
      if(this.datasetObservers != null) {
         this.datasetObservers.remove(var1);
      }
   }
}
