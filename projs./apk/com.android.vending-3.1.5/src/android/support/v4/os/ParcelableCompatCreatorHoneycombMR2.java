package android.support.v4.os;

import android.os.Parcel;
import android.os.Parcelable.ClassLoaderCreator;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;

class ParcelableCompatCreatorHoneycombMR2<T extends Object> implements ClassLoaderCreator<T> {

   private final ParcelableCompatCreatorCallbacks<T> mCallbacks;


   public ParcelableCompatCreatorHoneycombMR2(ParcelableCompatCreatorCallbacks<T> var1) {
      this.mCallbacks = var1;
   }

   public T createFromParcel(Parcel var1) {
      return this.mCallbacks.createFromParcel(var1, (ClassLoader)null);
   }

   public T createFromParcel(Parcel var1, ClassLoader var2) {
      return this.mCallbacks.createFromParcel(var1, var2);
   }

   public T[] newArray(int var1) {
      return this.mCallbacks.newArray(var1);
   }
}
