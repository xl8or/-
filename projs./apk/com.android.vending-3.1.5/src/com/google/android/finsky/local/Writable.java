package com.google.android.finsky.local;

import com.google.android.finsky.local.PersistentAssetStore;
import com.google.android.finsky.local.Writer;

public interface Writable {

   void write(PersistentAssetStore var1, Writer.Op var2);
}
