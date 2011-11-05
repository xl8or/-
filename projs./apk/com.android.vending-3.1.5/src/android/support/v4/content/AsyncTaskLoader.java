package android.support.v4.content;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.content.Loader;
import android.support.v4.util.TimeUtils;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.CountDownLatch;

public abstract class AsyncTaskLoader<D extends Object> extends Loader<D> {

   static final boolean DEBUG = false;
   static final String TAG = "AsyncTaskLoader";
   volatile AsyncTaskLoader.LoadTask mCancellingTask;
   Handler mHandler;
   long mLastLoadCompleteTime = 55536L;
   volatile AsyncTaskLoader.LoadTask mTask;
   long mUpdateThrottle;


   public AsyncTaskLoader(Context var1) {
      super(var1);
   }

   public boolean cancelLoad() {
      boolean var1 = false;
      if(this.mTask != null) {
         if(this.mCancellingTask != null) {
            if(this.mTask.waiting) {
               this.mTask.waiting = (boolean)0;
               Handler var2 = this.mHandler;
               AsyncTaskLoader.LoadTask var3 = this.mTask;
               var2.removeCallbacks(var3);
            }

            this.mTask = null;
         } else if(this.mTask.waiting) {
            this.mTask.waiting = (boolean)0;
            Handler var4 = this.mHandler;
            AsyncTaskLoader.LoadTask var5 = this.mTask;
            var4.removeCallbacks(var5);
            this.mTask = null;
         } else {
            var1 = this.mTask.cancel((boolean)0);
            if(var1) {
               AsyncTaskLoader.LoadTask var6 = this.mTask;
               this.mCancellingTask = var6;
            }

            this.mTask = null;
         }
      }

      return var1;
   }

   void dispatchOnCancelled(AsyncTaskLoader.LoadTask var1, D var2) {
      this.onCanceled(var2);
      if(this.mCancellingTask == var1) {
         long var3 = SystemClock.uptimeMillis();
         this.mLastLoadCompleteTime = var3;
         this.mCancellingTask = null;
         this.executePendingTask();
      }
   }

   void dispatchOnLoadComplete(AsyncTaskLoader.LoadTask var1, D var2) {
      if(this.mTask != var1) {
         this.dispatchOnCancelled(var1, var2);
      } else if(this.isAbandoned()) {
         this.onCanceled(var2);
      } else {
         long var3 = SystemClock.uptimeMillis();
         this.mLastLoadCompleteTime = var3;
         this.mTask = null;
         this.deliverResult(var2);
      }
   }

   public void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4) {
      super.dump(var1, var2, var3, var4);
      if(this.mTask != null) {
         var3.print(var1);
         var3.print("mTask=");
         AsyncTaskLoader.LoadTask var5 = this.mTask;
         var3.print(var5);
         var3.print(" waiting=");
         boolean var6 = this.mTask.waiting;
         var3.println(var6);
      }

      if(this.mCancellingTask != null) {
         var3.print(var1);
         var3.print("mCancellingTask=");
         AsyncTaskLoader.LoadTask var7 = this.mCancellingTask;
         var3.print(var7);
         var3.print(" waiting=");
         boolean var8 = this.mCancellingTask.waiting;
         var3.println(var8);
      }

      if(this.mUpdateThrottle != 0L) {
         var3.print(var1);
         var3.print("mUpdateThrottle=");
         TimeUtils.formatDuration(this.mUpdateThrottle, var3);
         var3.print(" mLastLoadCompleteTime=");
         long var9 = this.mLastLoadCompleteTime;
         long var11 = SystemClock.uptimeMillis();
         TimeUtils.formatDuration(var9, var11, var3);
         var3.println();
      }
   }

   void executePendingTask() {
      if(this.mCancellingTask == null) {
         if(this.mTask != null) {
            if(this.mTask.waiting) {
               this.mTask.waiting = (boolean)0;
               Handler var1 = this.mHandler;
               AsyncTaskLoader.LoadTask var2 = this.mTask;
               var1.removeCallbacks(var2);
            }

            if(this.mUpdateThrottle > 0L) {
               long var3 = SystemClock.uptimeMillis();
               long var5 = this.mLastLoadCompleteTime;
               long var7 = this.mUpdateThrottle;
               long var9 = var5 + var7;
               if(var3 < var9) {
                  this.mTask.waiting = (boolean)1;
                  Handler var11 = this.mHandler;
                  AsyncTaskLoader.LoadTask var12 = this.mTask;
                  long var13 = this.mLastLoadCompleteTime;
                  long var15 = this.mUpdateThrottle;
                  long var17 = var13 + var15;
                  var11.postAtTime(var12, var17);
                  return;
               }
            }

            AsyncTaskLoader.LoadTask var20 = this.mTask;
            Void[] var21 = (Void[])false;
            var20.execute(var21);
         }
      }
   }

   public abstract D loadInBackground();

   public void onCanceled(D var1) {}

   protected void onForceLoad() {
      super.onForceLoad();
      boolean var1 = this.cancelLoad();
      AsyncTaskLoader.LoadTask var2 = new AsyncTaskLoader.LoadTask();
      this.mTask = var2;
      this.executePendingTask();
   }

   protected D onLoadInBackground() {
      return this.loadInBackground();
   }

   public void setUpdateThrottle(long var1) {
      this.mUpdateThrottle = var1;
      if(var1 != 0L) {
         Handler var3 = new Handler();
         this.mHandler = var3;
      }
   }

   public void waitForLoader() {
      AsyncTaskLoader.LoadTask var1 = this.mTask;
      if(var1 != null) {
         try {
            var1.done.await();
         } catch (InterruptedException var3) {
            ;
         }
      }
   }

   final class LoadTask extends AsyncTask<Void, Void, D> implements Runnable {

      private CountDownLatch done;
      D result;
      boolean waiting;


      LoadTask() {
         CountDownLatch var2 = new CountDownLatch(1);
         this.done = var2;
      }

      protected D doInBackground(Void ... var1) {
         Object var2 = AsyncTaskLoader.this.onLoadInBackground();
         this.result = var2;
         return this.result;
      }

      protected void onCancelled() {
         try {
            AsyncTaskLoader var1 = AsyncTaskLoader.this;
            Object var2 = this.result;
            var1.dispatchOnCancelled(this, var2);
         } finally {
            this.done.countDown();
         }

      }

      protected void onPostExecute(D var1) {
         try {
            AsyncTaskLoader.this.dispatchOnLoadComplete(this, var1);
         } finally {
            this.done.countDown();
         }

      }

      public void run() {
         this.waiting = (boolean)0;
         AsyncTaskLoader.this.executePendingTask();
      }
   }
}
