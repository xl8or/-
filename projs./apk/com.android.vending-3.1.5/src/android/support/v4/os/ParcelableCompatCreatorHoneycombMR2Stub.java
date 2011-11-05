package android.support.v4.os;

import android.os.Parcelable.Creator;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;
import android.support.v4.os.ParcelableCompatCreatorHoneycombMR2;

class ParcelableCompatCreatorHoneycombMR2Stub {

   ParcelableCompatCreatorHoneycombMR2Stub() {}

   static <T extends Object> Creator<T> instantiate(ParcelableCompatCreatorCallbacks<T> var0) {
      return new ParcelableCompatCreatorHoneycombMR2(var0);
   }
}
