package com.google.android.finsky.api.model;

import android.database.Observable;
import com.google.android.finsky.api.model.OnDataChangedListener;

public class ItemListObservable extends Observable<OnDataChangedListener> {

   public ItemListObservable() {}

   public void notifyListChanged() {
      for(int var1 = this.mObservers.size() + -1; var1 >= 0; var1 += -1) {
         ((OnDataChangedListener)this.mObservers.get(var1)).onDataChanged();
      }

   }
}
