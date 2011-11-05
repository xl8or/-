package android.support.v4.os;

import android.os.Parcel;

public interface ParcelableCompatCreatorCallbacks<T extends Object> {

   T createFromParcel(Parcel var1, ClassLoader var2);

   T[] newArray(int var1);
}
