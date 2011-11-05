package com.android.common.content;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteTransactionListener;
import android.net.Uri;
import java.util.ArrayList;

public abstract class SQLiteContentProvider extends ContentProvider implements SQLiteTransactionListener {

   private static final int MAX_OPERATIONS_PER_YIELD_POINT = 500;
   private static final int SLEEP_AFTER_YIELD_DELAY = 4000;
   private static final String TAG = "SQLiteContentProvider";
   private final ThreadLocal<Boolean> mApplyingBatch;
   protected SQLiteDatabase mDb;
   private volatile boolean mNotifyChange;
   private SQLiteOpenHelper mOpenHelper;


   public SQLiteContentProvider() {
      ThreadLocal var1 = new ThreadLocal();
      this.mApplyingBatch = var1;
   }

   private boolean applyingBatch() {
      boolean var1;
      if(this.mApplyingBatch.get() != null && ((Boolean)this.mApplyingBatch.get()).booleanValue()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> var1) throws OperationApplicationException {
      int var2 = 0;
      SQLiteDatabase var3 = this.mOpenHelper.getWritableDatabase();
      this.mDb = var3;
      this.mDb.beginTransactionWithListener(this);
      boolean var20 = false;

      ContentProviderResult[] var7;
      try {
         var20 = true;
         ThreadLocal var4 = this.mApplyingBatch;
         Boolean var5 = Boolean.valueOf((boolean)1);
         var4.set(var5);
         int var6 = var1.size();
         var7 = new ContentProviderResult[var6];
         int var8 = 0;

         while(true) {
            if(var8 >= var6) {
               this.mDb.setTransactionSuccessful();
               var20 = false;
               break;
            }

            ++var2;
            if(var2 >= 500) {
               throw new OperationApplicationException("Too many content provider operations between yield points. The maximum number of operations per yield point is 500", 0);
            }

            ContentProviderOperation var12 = (ContentProviderOperation)var1.get(var8);
            if(var8 > 0 && var12.isYieldAllowed()) {
               var2 = 0;
               boolean var13 = this.mNotifyChange;
               if(this.mDb.yieldIfContendedSafely(4000L)) {
                  SQLiteDatabase var14 = this.mOpenHelper.getWritableDatabase();
                  this.mDb = var14;
                  this.mNotifyChange = var13;
                  int var15 = 0 + 1;
               }
            }

            ContentProviderResult var16 = var12.apply(this, var7, var8);
            var7[var8] = var16;
            ++var8;
         }
      } finally {
         if(var20) {
            ThreadLocal var10 = this.mApplyingBatch;
            Boolean var11 = Boolean.valueOf((boolean)0);
            var10.set(var11);
            this.mDb.endTransaction();
            this.onEndTransaction();
         }
      }

      ThreadLocal var17 = this.mApplyingBatch;
      Boolean var18 = Boolean.valueOf((boolean)0);
      var17.set(var18);
      this.mDb.endTransaction();
      this.onEndTransaction();
      return var7;
   }

   protected void beforeTransactionCommit() {}

   public int bulkInsert(Uri param1, ContentValues[] param2) {
      // $FF: Couldn't be decompiled
   }

   public int delete(Uri var1, String var2, String[] var3) {
      int var5;
      if(!this.applyingBatch()) {
         SQLiteDatabase var4 = this.mOpenHelper.getWritableDatabase();
         this.mDb = var4;
         this.mDb.beginTransactionWithListener(this);

         try {
            var5 = this.deleteInTransaction(var1, var2, var3);
            if(var5 > 0) {
               this.mNotifyChange = (boolean)1;
            }

            this.mDb.setTransactionSuccessful();
         } finally {
            this.mDb.endTransaction();
         }

         this.onEndTransaction();
      } else {
         var5 = this.deleteInTransaction(var1, var2, var3);
         if(var5 > 0) {
            this.mNotifyChange = (boolean)1;
         }
      }

      return var5;
   }

   protected abstract int deleteInTransaction(Uri var1, String var2, String[] var3);

   public SQLiteOpenHelper getDatabaseHelper() {
      return this.mOpenHelper;
   }

   protected abstract SQLiteOpenHelper getDatabaseHelper(Context var1);

   public Uri insert(Uri var1, ContentValues var2) {
      Uri var4;
      if(!this.applyingBatch()) {
         SQLiteDatabase var3 = this.mOpenHelper.getWritableDatabase();
         this.mDb = var3;
         this.mDb.beginTransactionWithListener(this);

         try {
            var4 = this.insertInTransaction(var1, var2);
            if(var4 != null) {
               this.mNotifyChange = (boolean)1;
            }

            this.mDb.setTransactionSuccessful();
         } finally {
            this.mDb.endTransaction();
         }

         this.onEndTransaction();
      } else {
         var4 = this.insertInTransaction(var1, var2);
         if(var4 != null) {
            this.mNotifyChange = (boolean)1;
         }
      }

      return var4;
   }

   protected abstract Uri insertInTransaction(Uri var1, ContentValues var2);

   protected abstract void notifyChange();

   public void onBegin() {
      this.onBeginTransaction();
   }

   protected void onBeginTransaction() {}

   public void onCommit() {
      this.beforeTransactionCommit();
   }

   public boolean onCreate() {
      Context var1 = this.getContext();
      SQLiteOpenHelper var2 = this.getDatabaseHelper(var1);
      this.mOpenHelper = var2;
      return true;
   }

   protected void onEndTransaction() {
      if(this.mNotifyChange) {
         this.mNotifyChange = (boolean)0;
         this.notifyChange();
      }
   }

   public void onRollback() {}

   public int update(Uri var1, ContentValues var2, String var3, String[] var4) {
      int var6;
      if(!this.applyingBatch()) {
         SQLiteDatabase var5 = this.mOpenHelper.getWritableDatabase();
         this.mDb = var5;
         this.mDb.beginTransactionWithListener(this);

         try {
            var6 = this.updateInTransaction(var1, var2, var3, var4);
            if(var6 > 0) {
               this.mNotifyChange = (boolean)1;
            }

            this.mDb.setTransactionSuccessful();
         } finally {
            this.mDb.endTransaction();
         }

         this.onEndTransaction();
      } else {
         var6 = this.updateInTransaction(var1, var2, var3, var4);
         if(var6 > 0) {
            this.mNotifyChange = (boolean)1;
         }
      }

      return var6;
   }

   protected abstract int updateInTransaction(Uri var1, ContentValues var2, String var3, String[] var4);
}
