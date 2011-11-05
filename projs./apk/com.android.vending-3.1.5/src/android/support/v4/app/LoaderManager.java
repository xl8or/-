package android.support.v4.app;

import android.os.Bundle;
import android.support.v4.app.LoaderManagerImpl;
import android.support.v4.content.Loader;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public abstract class LoaderManager {

   public LoaderManager() {}

   public static void enableDebugLogging(boolean var0) {
      LoaderManagerImpl.DEBUG = var0;
   }

   public abstract void destroyLoader(int var1);

   public abstract void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4);

   public abstract <D extends Object> Loader<D> getLoader(int var1);

   public abstract <D extends Object> Loader<D> initLoader(int var1, Bundle var2, LoaderManager.LoaderCallbacks<D> var3);

   public abstract <D extends Object> Loader<D> restartLoader(int var1, Bundle var2, LoaderManager.LoaderCallbacks<D> var3);

   public interface LoaderCallbacks<D extends Object> {

      Loader<D> onCreateLoader(int var1, Bundle var2);

      void onLoadFinished(Loader<D> var1, D var2);

      void onLoaderReset(Loader<D> var1);
   }
}
