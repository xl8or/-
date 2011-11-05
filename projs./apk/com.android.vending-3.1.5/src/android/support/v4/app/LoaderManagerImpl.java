package android.support.v4.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.HCSparseArray;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.util.DebugUtils;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;

class LoaderManagerImpl extends LoaderManager {

   static boolean DEBUG = 0;
   static final String TAG = "LoaderManager";
   FragmentActivity mActivity;
   boolean mCreatingLoader;
   final HCSparseArray<LoaderManagerImpl.LoaderInfo> mInactiveLoaders;
   final HCSparseArray<LoaderManagerImpl.LoaderInfo> mLoaders;
   boolean mRetaining;
   boolean mRetainingStarted;
   boolean mStarted;


   LoaderManagerImpl(FragmentActivity var1, boolean var2) {
      HCSparseArray var3 = new HCSparseArray();
      this.mLoaders = var3;
      HCSparseArray var4 = new HCSparseArray();
      this.mInactiveLoaders = var4;
      this.mActivity = var1;
      this.mStarted = var2;
   }

   private LoaderManagerImpl.LoaderInfo createAndInstallLoader(int var1, Bundle var2, LoaderManager.LoaderCallbacks<Object> var3) {
      LoaderManagerImpl.LoaderInfo var4;
      try {
         this.mCreatingLoader = (boolean)1;
         var4 = this.createLoader(var1, var2, var3);
         this.installLoader(var4);
      } finally {
         this.mCreatingLoader = (boolean)0;
      }

      return var4;
   }

   private LoaderManagerImpl.LoaderInfo createLoader(int var1, Bundle var2, LoaderManager.LoaderCallbacks<Object> var3) {
      LoaderManagerImpl.LoaderInfo var4 = new LoaderManagerImpl.LoaderInfo(var1, var2, var3);
      Loader var5 = var3.onCreateLoader(var1, var2);
      var4.mLoader = var5;
      return var4;
   }

   public void destroyLoader(int var1) {
      if(this.mCreatingLoader) {
         throw new IllegalStateException("Called while creating a loader");
      } else {
         if(DEBUG) {
            String var2 = "destroyLoader in " + this + " of " + var1;
            int var3 = Log.v("LoaderManager", var2);
         }

         int var4 = this.mLoaders.indexOfKey(var1);
         if(var4 >= 0) {
            LoaderManagerImpl.LoaderInfo var5 = (LoaderManagerImpl.LoaderInfo)this.mLoaders.valueAt(var4);
            this.mLoaders.removeAt(var4);
            var5.destroy();
         }

         int var6 = this.mInactiveLoaders.indexOfKey(var1);
         if(var6 >= 0) {
            LoaderManagerImpl.LoaderInfo var7 = (LoaderManagerImpl.LoaderInfo)this.mInactiveLoaders.valueAt(var6);
            this.mInactiveLoaders.removeAt(var6);
            var7.destroy();
         }
      }
   }

   void doDestroy() {
      if(!this.mRetaining) {
         if(DEBUG) {
            String var1 = "Destroying Active in " + this;
            int var2 = Log.v("LoaderManager", var1);
         }

         for(int var3 = this.mLoaders.size() + -1; var3 >= 0; var3 += -1) {
            ((LoaderManagerImpl.LoaderInfo)this.mLoaders.valueAt(var3)).destroy();
         }
      }

      if(DEBUG) {
         String var4 = "Destroying Inactive in " + this;
         int var5 = Log.v("LoaderManager", var4);
      }

      for(int var6 = this.mInactiveLoaders.size() + -1; var6 >= 0; var6 += -1) {
         ((LoaderManagerImpl.LoaderInfo)this.mInactiveLoaders.valueAt(var6)).destroy();
      }

      this.mInactiveLoaders.clear();
   }

   void doReportNextStart() {
      for(int var1 = this.mLoaders.size() + -1; var1 >= 0; var1 += -1) {
         ((LoaderManagerImpl.LoaderInfo)this.mLoaders.valueAt(var1)).mReportNextStart = (boolean)1;
      }

   }

   void doReportStart() {
      for(int var1 = this.mLoaders.size() + -1; var1 >= 0; var1 += -1) {
         ((LoaderManagerImpl.LoaderInfo)this.mLoaders.valueAt(var1)).reportStart();
      }

   }

   void doRetain() {
      if(DEBUG) {
         String var1 = "Retaining in " + this;
         int var2 = Log.v("LoaderManager", var1);
      }

      if(!this.mStarted) {
         RuntimeException var3 = new RuntimeException("here");
         Throwable var4 = var3.fillInStackTrace();
         String var5 = "Called doRetain when not started: " + this;
         Log.w("LoaderManager", var5, var3);
      } else {
         this.mRetaining = (boolean)1;
         this.mStarted = (boolean)0;

         for(int var7 = this.mLoaders.size() + -1; var7 >= 0; var7 += -1) {
            ((LoaderManagerImpl.LoaderInfo)this.mLoaders.valueAt(var7)).retain();
         }

      }
   }

   void doStart() {
      if(DEBUG) {
         String var1 = "Starting in " + this;
         int var2 = Log.v("LoaderManager", var1);
      }

      if(this.mStarted) {
         RuntimeException var3 = new RuntimeException("here");
         Throwable var4 = var3.fillInStackTrace();
         String var5 = "Called doStart when already started: " + this;
         Log.w("LoaderManager", var5, var3);
      } else {
         this.mStarted = (boolean)1;

         for(int var7 = this.mLoaders.size() + -1; var7 >= 0; var7 += -1) {
            ((LoaderManagerImpl.LoaderInfo)this.mLoaders.valueAt(var7)).start();
         }

      }
   }

   void doStop() {
      if(DEBUG) {
         String var1 = "Stopping in " + this;
         int var2 = Log.v("LoaderManager", var1);
      }

      if(!this.mStarted) {
         RuntimeException var3 = new RuntimeException("here");
         Throwable var4 = var3.fillInStackTrace();
         String var5 = "Called doStop when not started: " + this;
         Log.w("LoaderManager", var5, var3);
      } else {
         for(int var7 = this.mLoaders.size() + -1; var7 >= 0; var7 += -1) {
            ((LoaderManagerImpl.LoaderInfo)this.mLoaders.valueAt(var7)).stop();
         }

         this.mStarted = (boolean)0;
      }
   }

   public void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4) {
      String var5;
      int var6;
      if(this.mLoaders.size() > 0) {
         var3.print(var1);
         var3.println("Active Loaders:");
         var5 = var1 + "    ";
         var6 = 0;

         while(true) {
            int var7 = this.mLoaders.size();
            if(var6 >= var7) {
               break;
            }

            LoaderManagerImpl.LoaderInfo var8 = (LoaderManagerImpl.LoaderInfo)this.mLoaders.valueAt(var6);
            var3.print(var1);
            var3.print("  #");
            int var9 = this.mLoaders.keyAt(var6);
            var3.print(var9);
            var3.print(": ");
            String var10 = var8.toString();
            var3.println(var10);
            var8.dump(var5, var2, var3, var4);
            ++var6;
         }
      }

      if(this.mInactiveLoaders.size() > 0) {
         var3.print(var1);
         var3.println("Inactive Loaders:");
         var5 = var1 + "    ";
         var6 = 0;

         while(true) {
            int var11 = this.mInactiveLoaders.size();
            if(var6 >= var11) {
               return;
            }

            LoaderManagerImpl.LoaderInfo var12 = (LoaderManagerImpl.LoaderInfo)this.mInactiveLoaders.valueAt(var6);
            var3.print(var1);
            var3.print("  #");
            int var13 = this.mInactiveLoaders.keyAt(var6);
            var3.print(var13);
            var3.print(": ");
            String var14 = var12.toString();
            var3.println(var14);
            var12.dump(var5, var2, var3, var4);
            ++var6;
         }
      }
   }

   void finishRetain() {
      if(this.mRetaining) {
         if(DEBUG) {
            String var1 = "Finished Retaining in " + this;
            int var2 = Log.v("LoaderManager", var1);
         }

         this.mRetaining = (boolean)0;

         for(int var3 = this.mLoaders.size() + -1; var3 >= 0; var3 += -1) {
            ((LoaderManagerImpl.LoaderInfo)this.mLoaders.valueAt(var3)).finishRetain();
         }

      }
   }

   public <D extends Object> Loader<D> getLoader(int var1) {
      if(this.mCreatingLoader) {
         throw new IllegalStateException("Called while creating a loader");
      } else {
         LoaderManagerImpl.LoaderInfo var2 = (LoaderManagerImpl.LoaderInfo)this.mLoaders.get(var1);
         Loader var3;
         if(var2 != null) {
            if(var2.mPendingLoader != null) {
               var3 = var2.mPendingLoader.mLoader;
            } else {
               var3 = var2.mLoader;
            }
         } else {
            var3 = null;
         }

         return var3;
      }
   }

   public <D extends Object> Loader<D> initLoader(int var1, Bundle var2, LoaderManager.LoaderCallbacks<D> var3) {
      if(this.mCreatingLoader) {
         throw new IllegalStateException("Called while creating a loader");
      } else {
         LoaderManagerImpl.LoaderInfo var4 = (LoaderManagerImpl.LoaderInfo)this.mLoaders.get(var1);
         if(DEBUG) {
            String var5 = "initLoader in " + this + ": args=" + var2;
            int var6 = Log.v("LoaderManager", var5);
         }

         if(var4 == null) {
            var4 = this.createAndInstallLoader(var1, var2, var3);
            if(DEBUG) {
               String var7 = "  Created new loader " + var4;
               int var8 = Log.v("LoaderManager", var7);
            }
         } else {
            if(DEBUG) {
               String var11 = "  Re-using existing loader " + var4;
               int var12 = Log.v("LoaderManager", var11);
            }

            var4.mCallbacks = var3;
         }

         if(var4.mHaveData && this.mStarted) {
            Loader var9 = var4.mLoader;
            Object var10 = var4.mData;
            var4.callOnLoadFinished(var9, var10);
         }

         return var4.mLoader;
      }
   }

   void installLoader(LoaderManagerImpl.LoaderInfo var1) {
      HCSparseArray var2 = this.mLoaders;
      int var3 = var1.mId;
      var2.put(var3, var1);
      if(this.mStarted) {
         var1.start();
      }
   }

   public <D extends Object> Loader<D> restartLoader(int var1, Bundle var2, LoaderManager.LoaderCallbacks<D> var3) {
      if(this.mCreatingLoader) {
         throw new IllegalStateException("Called while creating a loader");
      } else {
         LoaderManagerImpl.LoaderInfo var4 = (LoaderManagerImpl.LoaderInfo)this.mLoaders.get(var1);
         if(DEBUG) {
            String var5 = "restartLoader in " + this + ": args=" + var2;
            int var6 = Log.v("LoaderManager", var5);
         }

         Loader var10;
         if(var4 != null) {
            LoaderManagerImpl.LoaderInfo var7 = (LoaderManagerImpl.LoaderInfo)this.mInactiveLoaders.get(var1);
            if(var7 != null) {
               if(var4.mHaveData) {
                  if(DEBUG) {
                     String var8 = "  Removing last inactive loader: " + var4;
                     int var9 = Log.v("LoaderManager", var8);
                  }

                  var7.mDeliveredData = (boolean)0;
                  var7.destroy();
                  var4.mLoader.abandon();
                  this.mInactiveLoaders.put(var1, var4);
               } else {
                  if(var4.mStarted) {
                     if(var4.mPendingLoader != null) {
                        if(DEBUG) {
                           StringBuilder var12 = (new StringBuilder()).append("  Removing pending loader: ");
                           LoaderManagerImpl.LoaderInfo var13 = var4.mPendingLoader;
                           String var14 = var12.append(var13).toString();
                           int var15 = Log.v("LoaderManager", var14);
                        }

                        var4.mPendingLoader.destroy();
                        var4.mPendingLoader = null;
                     }

                     if(DEBUG) {
                        int var16 = Log.v("LoaderManager", "  Enqueuing as new pending loader");
                     }

                     LoaderManagerImpl.LoaderInfo var17 = this.createLoader(var1, var2, var3);
                     var4.mPendingLoader = var17;
                     var10 = var4.mPendingLoader.mLoader;
                     return var10;
                  }

                  if(DEBUG) {
                     int var11 = Log.v("LoaderManager", "  Current loader is stopped; replacing");
                  }

                  this.mLoaders.put(var1, (Object)null);
                  var4.destroy();
               }
            } else {
               if(DEBUG) {
                  String var18 = "  Making last loader inactive: " + var4;
                  int var19 = Log.v("LoaderManager", var18);
               }

               var4.mLoader.abandon();
               this.mInactiveLoaders.put(var1, var4);
            }
         }

         var10 = this.createAndInstallLoader(var1, var2, var3).mLoader;
         return var10;
      }
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder(128);
      StringBuilder var2 = var1.append("LoaderManager{");
      String var3 = Integer.toHexString(System.identityHashCode(this));
      var1.append(var3);
      StringBuilder var5 = var1.append(" in ");
      DebugUtils.buildShortClassTag(this.mActivity, var1);
      StringBuilder var6 = var1.append("}}");
      return var1.toString();
   }

   void updateActivity(FragmentActivity var1) {
      this.mActivity = var1;
   }

   final class LoaderInfo implements Loader.OnLoadCompleteListener<Object> {

      final Bundle mArgs;
      LoaderManager.LoaderCallbacks<Object> mCallbacks;
      Object mData;
      boolean mDeliveredData;
      boolean mDestroyed;
      boolean mHaveData;
      final int mId;
      boolean mListenerRegistered;
      Loader<Object> mLoader;
      LoaderManagerImpl.LoaderInfo mPendingLoader;
      boolean mReportNextStart;
      boolean mRetaining;
      boolean mRetainingStarted;
      boolean mStarted;


      public LoaderInfo(int var2, Bundle var3, LoaderManager.LoaderCallbacks var4) {
         this.mId = var2;
         this.mArgs = var3;
         this.mCallbacks = var4;
      }

      void callOnLoadFinished(Loader<Object> var1, Object var2) {
         if(this.mCallbacks != null) {
            String var3 = null;
            if(LoaderManagerImpl.this.mActivity != null) {
               var3 = LoaderManagerImpl.this.mActivity.mFragments.mNoTransactionsBecause;
               LoaderManagerImpl.this.mActivity.mFragments.mNoTransactionsBecause = "onLoadFinished";
            }

            try {
               if(LoaderManagerImpl.DEBUG) {
                  StringBuilder var4 = (new StringBuilder()).append("  onLoadFinished in ").append(var1).append(": ");
                  String var5 = var1.dataToString(var2);
                  String var6 = var4.append(var5).toString();
                  int var7 = Log.v("LoaderManager", var6);
               }

               this.mCallbacks.onLoadFinished(var1, var2);
            } finally {
               if(LoaderManagerImpl.this.mActivity != null) {
                  LoaderManagerImpl.this.mActivity.mFragments.mNoTransactionsBecause = var3;
               }

            }

            this.mDeliveredData = (boolean)1;
         }
      }

      void destroy() {
         if(LoaderManagerImpl.DEBUG) {
            String var1 = "  Destroying: " + this;
            int var2 = Log.v("LoaderManager", var1);
         }

         this.mDestroyed = (boolean)1;
         boolean var3 = this.mDeliveredData;
         this.mDeliveredData = (boolean)0;
         if(this.mCallbacks != null && this.mLoader != null && this.mHaveData && var3) {
            if(LoaderManagerImpl.DEBUG) {
               String var4 = "  Reseting: " + this;
               int var5 = Log.v("LoaderManager", var4);
            }

            String var6 = null;
            if(LoaderManagerImpl.this.mActivity != null) {
               var6 = LoaderManagerImpl.this.mActivity.mFragments.mNoTransactionsBecause;
               LoaderManagerImpl.this.mActivity.mFragments.mNoTransactionsBecause = "onLoaderReset";
            }

            try {
               LoaderManager.LoaderCallbacks var7 = this.mCallbacks;
               Loader var8 = this.mLoader;
               var7.onLoaderReset(var8);
            } finally {
               if(LoaderManagerImpl.this.mActivity != null) {
                  LoaderManagerImpl.this.mActivity.mFragments.mNoTransactionsBecause = var6;
               }

            }
         }

         this.mCallbacks = null;
         this.mData = null;
         this.mHaveData = (boolean)0;
         if(this.mLoader != null) {
            if(this.mListenerRegistered) {
               this.mListenerRegistered = (boolean)0;
               this.mLoader.unregisterListener(this);
            }

            this.mLoader.reset();
         }

         if(this.mPendingLoader != null) {
            this.mPendingLoader.destroy();
         }
      }

      public void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4) {
         var3.print(var1);
         var3.print("mId=");
         int var5 = this.mId;
         var3.print(var5);
         var3.print(" mArgs=");
         Bundle var6 = this.mArgs;
         var3.println(var6);
         var3.print(var1);
         var3.print("mCallbacks=");
         LoaderManager.LoaderCallbacks var7 = this.mCallbacks;
         var3.println(var7);
         var3.print(var1);
         var3.print("mLoader=");
         Loader var8 = this.mLoader;
         var3.println(var8);
         if(this.mLoader != null) {
            Loader var9 = this.mLoader;
            String var10 = var1 + "  ";
            var9.dump(var10, var2, var3, var4);
         }

         if(this.mHaveData || this.mDeliveredData) {
            var3.print(var1);
            var3.print("mHaveData=");
            boolean var11 = this.mHaveData;
            var3.print(var11);
            var3.print("  mDeliveredData=");
            boolean var12 = this.mDeliveredData;
            var3.println(var12);
            var3.print(var1);
            var3.print("mData=");
            Object var13 = this.mData;
            var3.println(var13);
         }

         var3.print(var1);
         var3.print("mStarted=");
         boolean var14 = this.mStarted;
         var3.print(var14);
         var3.print(" mReportNextStart=");
         boolean var15 = this.mReportNextStart;
         var3.print(var15);
         var3.print(" mDestroyed=");
         boolean var16 = this.mDestroyed;
         var3.println(var16);
         var3.print(var1);
         var3.print("mRetaining=");
         boolean var17 = this.mRetaining;
         var3.print(var17);
         var3.print(" mRetainingStarted=");
         boolean var18 = this.mRetainingStarted;
         var3.print(var18);
         var3.print(" mListenerRegistered=");
         boolean var19 = this.mListenerRegistered;
         var3.println(var19);
         if(this.mPendingLoader != null) {
            var3.print(var1);
            var3.println("Pending Loader ");
            LoaderManagerImpl.LoaderInfo var20 = this.mPendingLoader;
            var3.print(var20);
            var3.println(":");
            LoaderManagerImpl.LoaderInfo var21 = this.mPendingLoader;
            String var22 = var1 + "  ";
            var21.dump(var22, var2, var3, var4);
         }
      }

      void finishRetain() {
         if(this.mRetaining) {
            if(LoaderManagerImpl.DEBUG) {
               String var1 = "  Finished Retaining: " + this;
               int var2 = Log.v("LoaderManager", var1);
            }

            this.mRetaining = (boolean)0;
            boolean var3 = this.mStarted;
            boolean var4 = this.mRetainingStarted;
            if(var3 != var4 && !this.mStarted) {
               this.stop();
            }
         }

         if(this.mStarted) {
            if(this.mHaveData) {
               if(!this.mReportNextStart) {
                  Loader var5 = this.mLoader;
                  Object var6 = this.mData;
                  this.callOnLoadFinished(var5, var6);
               }
            }
         }
      }

      public void onLoadComplete(Loader<Object> var1, Object var2) {
         if(LoaderManagerImpl.DEBUG) {
            String var3 = "onLoadComplete: " + this;
            int var4 = Log.v("LoaderManager", var3);
         }

         if(this.mDestroyed) {
            if(LoaderManagerImpl.DEBUG) {
               int var5 = Log.v("LoaderManager", "  Ignoring load complete -- destroyed");
            }
         } else {
            HCSparseArray var6 = LoaderManagerImpl.this.mLoaders;
            int var7 = this.mId;
            if(var6.get(var7) != this) {
               if(LoaderManagerImpl.DEBUG) {
                  int var8 = Log.v("LoaderManager", "  Ignoring load complete -- not active");
               }
            } else {
               LoaderManagerImpl.LoaderInfo var9 = this.mPendingLoader;
               if(var9 != null) {
                  if(LoaderManagerImpl.DEBUG) {
                     String var10 = "  Switching to pending loader: " + var9;
                     int var11 = Log.v("LoaderManager", var10);
                  }

                  this.mPendingLoader = null;
                  HCSparseArray var12 = LoaderManagerImpl.this.mLoaders;
                  int var13 = this.mId;
                  var12.put(var13, (Object)null);
                  this.destroy();
                  LoaderManagerImpl.this.installLoader(var9);
               } else {
                  if(this.mData != var2 || !this.mHaveData) {
                     this.mData = var2;
                     this.mHaveData = (boolean)1;
                     if(this.mStarted) {
                        this.callOnLoadFinished(var1, var2);
                     }
                  }

                  HCSparseArray var14 = LoaderManagerImpl.this.mInactiveLoaders;
                  int var15 = this.mId;
                  LoaderManagerImpl.LoaderInfo var16 = (LoaderManagerImpl.LoaderInfo)var14.get(var15);
                  if(var16 != null) {
                     if(var16 != this) {
                        var16.mDeliveredData = (boolean)0;
                        var16.destroy();
                        HCSparseArray var17 = LoaderManagerImpl.this.mInactiveLoaders;
                        int var18 = this.mId;
                        var17.remove(var18);
                     }
                  }
               }
            }
         }
      }

      void reportStart() {
         if(this.mStarted) {
            if(this.mReportNextStart) {
               this.mReportNextStart = (boolean)0;
               if(this.mHaveData) {
                  Loader var1 = this.mLoader;
                  Object var2 = this.mData;
                  this.callOnLoadFinished(var1, var2);
               }
            }
         }
      }

      void retain() {
         if(LoaderManagerImpl.DEBUG) {
            String var1 = "  Retaining: " + this;
            int var2 = Log.v("LoaderManager", var1);
         }

         this.mRetaining = (boolean)1;
         boolean var3 = this.mStarted;
         this.mRetainingStarted = var3;
         this.mStarted = (boolean)0;
         this.mCallbacks = null;
      }

      void start() {
         if(this.mRetaining && this.mRetainingStarted) {
            this.mStarted = (boolean)1;
         } else if(!this.mStarted) {
            this.mStarted = (boolean)1;
            if(LoaderManagerImpl.DEBUG) {
               String var1 = "  Starting: " + this;
               int var2 = Log.v("LoaderManager", var1);
            }

            if(this.mLoader == null && this.mCallbacks != null) {
               LoaderManager.LoaderCallbacks var3 = this.mCallbacks;
               int var4 = this.mId;
               Bundle var5 = this.mArgs;
               Loader var6 = var3.onCreateLoader(var4, var5);
               this.mLoader = var6;
            }

            if(this.mLoader != null) {
               if(this.mLoader.getClass().isMemberClass() && !Modifier.isStatic(this.mLoader.getClass().getModifiers())) {
                  StringBuilder var7 = (new StringBuilder()).append("Object returned from onCreateLoader must not be a non-static inner member class: ");
                  Loader var8 = this.mLoader;
                  String var9 = var7.append(var8).toString();
                  throw new IllegalArgumentException(var9);
               } else {
                  if(!this.mListenerRegistered) {
                     Loader var10 = this.mLoader;
                     int var11 = this.mId;
                     var10.registerListener(var11, this);
                     this.mListenerRegistered = (boolean)1;
                  }

                  this.mLoader.startLoading();
               }
            }
         }
      }

      void stop() {
         if(LoaderManagerImpl.DEBUG) {
            String var1 = "  Stopping: " + this;
            int var2 = Log.v("LoaderManager", var1);
         }

         this.mStarted = (boolean)0;
         if(!this.mRetaining) {
            if(this.mLoader != null) {
               if(this.mListenerRegistered) {
                  this.mListenerRegistered = (boolean)0;
                  this.mLoader.unregisterListener(this);
                  this.mLoader.stopLoading();
               }
            }
         }
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder(64);
         StringBuilder var2 = var1.append("LoaderInfo{");
         String var3 = Integer.toHexString(System.identityHashCode(this));
         var1.append(var3);
         StringBuilder var5 = var1.append(" #");
         int var6 = this.mId;
         var1.append(var6);
         StringBuilder var8 = var1.append(" : ");
         DebugUtils.buildShortClassTag(this.mLoader, var1);
         StringBuilder var9 = var1.append("}}");
         return var1.toString();
      }
   }
}
