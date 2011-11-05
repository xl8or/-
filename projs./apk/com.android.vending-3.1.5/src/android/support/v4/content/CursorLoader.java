package android.support.v4.content;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Arrays;

public class CursorLoader extends AsyncTaskLoader<Cursor> {

   Cursor mCursor;
   final Loader.ForceLoadContentObserver mObserver;
   String[] mProjection;
   String mSelection;
   String[] mSelectionArgs;
   String mSortOrder;
   Uri mUri;


   public CursorLoader(Context var1) {
      super(var1);
      Loader.ForceLoadContentObserver var2 = new Loader.ForceLoadContentObserver();
      this.mObserver = var2;
   }

   public CursorLoader(Context var1, Uri var2, String[] var3, String var4, String[] var5, String var6) {
      super(var1);
      Loader.ForceLoadContentObserver var7 = new Loader.ForceLoadContentObserver();
      this.mObserver = var7;
      this.mUri = var2;
      this.mProjection = var3;
      this.mSelection = var4;
      this.mSelectionArgs = var5;
      this.mSortOrder = var6;
   }

   public void deliverResult(Cursor var1) {
      if(this.isReset()) {
         if(var1 != null) {
            var1.close();
         }
      } else {
         Cursor var2 = this.mCursor;
         this.mCursor = var1;
         if(this.isStarted()) {
            super.deliverResult(var1);
         }

         if(var2 != null) {
            if(var2 != var1) {
               if(!var2.isClosed()) {
                  var2.close();
               }
            }
         }
      }
   }

   public void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4) {
      super.dump(var1, var2, var3, var4);
      var3.print(var1);
      var3.print("mUri=");
      Uri var5 = this.mUri;
      var3.println(var5);
      var3.print(var1);
      var3.print("mProjection=");
      String var6 = Arrays.toString(this.mProjection);
      var3.println(var6);
      var3.print(var1);
      var3.print("mSelection=");
      String var7 = this.mSelection;
      var3.println(var7);
      var3.print(var1);
      var3.print("mSelectionArgs=");
      String var8 = Arrays.toString(this.mSelectionArgs);
      var3.println(var8);
      var3.print(var1);
      var3.print("mSortOrder=");
      String var9 = this.mSortOrder;
      var3.println(var9);
      var3.print(var1);
      var3.print("mCursor=");
      Cursor var10 = this.mCursor;
      var3.println(var10);
      var3.print(var1);
      var3.print("mContentChanged=");
      boolean var11 = this.mContentChanged;
      var3.println(var11);
   }

   public String[] getProjection() {
      return this.mProjection;
   }

   public String getSelection() {
      return this.mSelection;
   }

   public String[] getSelectionArgs() {
      return this.mSelectionArgs;
   }

   public String getSortOrder() {
      return this.mSortOrder;
   }

   public Uri getUri() {
      return this.mUri;
   }

   public Cursor loadInBackground() {
      ContentResolver var1 = this.getContext().getContentResolver();
      Uri var2 = this.mUri;
      String[] var3 = this.mProjection;
      String var4 = this.mSelection;
      String[] var5 = this.mSelectionArgs;
      String var6 = this.mSortOrder;
      Cursor var7 = var1.query(var2, var3, var4, var5, var6);
      if(var7 != null) {
         int var8 = var7.getCount();
         Loader.ForceLoadContentObserver var9 = this.mObserver;
         this.registerContentObserver(var7, var9);
      }

      return var7;
   }

   public void onCanceled(Cursor var1) {
      if(var1 != null) {
         if(!var1.isClosed()) {
            var1.close();
         }
      }
   }

   protected void onReset() {
      super.onReset();
      this.onStopLoading();
      if(this.mCursor != null && !this.mCursor.isClosed()) {
         this.mCursor.close();
      }

      this.mCursor = null;
   }

   protected void onStartLoading() {
      if(this.mCursor != null) {
         Cursor var1 = this.mCursor;
         this.deliverResult(var1);
      }

      if(this.takeContentChanged() || this.mCursor == null) {
         this.forceLoad();
      }
   }

   protected void onStopLoading() {
      boolean var1 = this.cancelLoad();
   }

   void registerContentObserver(Cursor var1, ContentObserver var2) {
      Loader.ForceLoadContentObserver var3 = this.mObserver;
      var1.registerContentObserver(var3);
   }

   public void setProjection(String[] var1) {
      this.mProjection = var1;
   }

   public void setSelection(String var1) {
      this.mSelection = var1;
   }

   public void setSelectionArgs(String[] var1) {
      this.mSelectionArgs = var1;
   }

   public void setSortOrder(String var1) {
      this.mSortOrder = var1;
   }

   public void setUri(Uri var1) {
      this.mUri = var1;
   }
}
