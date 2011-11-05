package android.support.v4.content;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.support.v4.util.DebugUtils;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class Loader<D extends Object> {

   boolean mAbandoned = 0;
   boolean mContentChanged = 0;
   Context mContext;
   int mId;
   Loader.OnLoadCompleteListener<D> mListener;
   boolean mReset = 1;
   boolean mStarted = 0;


   public Loader(Context var1) {
      Context var2 = var1.getApplicationContext();
      this.mContext = var2;
   }

   public void abandon() {
      this.mAbandoned = (boolean)1;
      this.onAbandon();
   }

   public String dataToString(D var1) {
      StringBuilder var2 = new StringBuilder(64);
      DebugUtils.buildShortClassTag(var1, var2);
      StringBuilder var3 = var2.append("}");
      return var2.toString();
   }

   public void deliverResult(D var1) {
      if(this.mListener != null) {
         this.mListener.onLoadComplete(this, var1);
      }
   }

   public void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4) {
      var3.print(var1);
      var3.print("mId=");
      int var5 = this.mId;
      var3.print(var5);
      var3.print(" mListener=");
      Loader.OnLoadCompleteListener var6 = this.mListener;
      var3.println(var6);
      var3.print(var1);
      var3.print("mStarted=");
      boolean var7 = this.mStarted;
      var3.print(var7);
      var3.print(" mContentChanged=");
      boolean var8 = this.mContentChanged;
      var3.print(var8);
      var3.print(" mAbandoned=");
      boolean var9 = this.mAbandoned;
      var3.print(var9);
      var3.print(" mReset=");
      boolean var10 = this.mReset;
      var3.println(var10);
   }

   public void forceLoad() {
      this.onForceLoad();
   }

   public Context getContext() {
      return this.mContext;
   }

   public int getId() {
      return this.mId;
   }

   public boolean isAbandoned() {
      return this.mAbandoned;
   }

   public boolean isReset() {
      return this.mReset;
   }

   public boolean isStarted() {
      return this.mStarted;
   }

   protected void onAbandon() {}

   public void onContentChanged() {
      if(this.mStarted) {
         this.forceLoad();
      } else {
         this.mContentChanged = (boolean)1;
      }
   }

   protected void onForceLoad() {}

   protected void onReset() {}

   protected void onStartLoading() {}

   protected void onStopLoading() {}

   public void registerListener(int var1, Loader.OnLoadCompleteListener<D> var2) {
      if(this.mListener != null) {
         throw new IllegalStateException("There is already a listener registered");
      } else {
         this.mListener = var2;
         this.mId = var1;
      }
   }

   public void reset() {
      this.onReset();
      this.mReset = (boolean)1;
      this.mStarted = (boolean)0;
      this.mAbandoned = (boolean)0;
      this.mContentChanged = (boolean)0;
   }

   public final void startLoading() {
      this.mStarted = (boolean)1;
      this.mReset = (boolean)0;
      this.mAbandoned = (boolean)0;
      this.onStartLoading();
   }

   public void stopLoading() {
      this.mStarted = (boolean)0;
      this.onStopLoading();
   }

   public boolean takeContentChanged() {
      boolean var1 = this.mContentChanged;
      this.mContentChanged = (boolean)0;
      return var1;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder(64);
      DebugUtils.buildShortClassTag(this, var1);
      StringBuilder var2 = var1.append(" id=");
      int var3 = this.mId;
      var1.append(var3);
      StringBuilder var5 = var1.append("}");
      return var1.toString();
   }

   public void unregisterListener(Loader.OnLoadCompleteListener<D> var1) {
      if(this.mListener == null) {
         throw new IllegalStateException("No listener register");
      } else if(this.mListener != var1) {
         throw new IllegalArgumentException("Attempting to unregister the wrong listener");
      } else {
         this.mListener = null;
      }
   }

   public interface OnLoadCompleteListener<D extends Object> {

      void onLoadComplete(Loader<D> var1, D var2);
   }

   public final class ForceLoadContentObserver extends ContentObserver {

      public ForceLoadContentObserver() {
         Handler var2 = new Handler();
         super(var2);
      }

      public boolean deliverSelfNotifications() {
         return true;
      }

      public void onChange(boolean var1) {
         Loader.this.onContentChanged();
      }
   }
}
