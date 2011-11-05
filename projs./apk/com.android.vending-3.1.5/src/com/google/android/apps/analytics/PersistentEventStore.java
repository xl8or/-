package com.google.android.apps.analytics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;
import com.google.android.apps.analytics.CustomVariable;
import com.google.android.apps.analytics.CustomVariableBuffer;
import com.google.android.apps.analytics.Event;
import com.google.android.apps.analytics.EventStore;

class PersistentEventStore implements EventStore {

   private static final String ACCOUNT_ID = "account_id";
   private static final String ACTION = "action";
   private static final String CATEGORY = "category";
   private static final String CUSTOMVAR_ID = "cv_id";
   private static final String CUSTOMVAR_INDEX = "cv_index";
   private static final String CUSTOMVAR_NAME = "cv_name";
   private static final String CUSTOMVAR_SCOPE = "cv_scope";
   private static final String CUSTOMVAR_VALUE = "cv_value";
   private static final String CUSTOM_VARIABLE_COLUMN_TYPE = "CHAR(64) NOT NULL";
   private static final String DATABASE_NAME = "google_analytics.db";
   private static final int DATABASE_VERSION = 2;
   private static final String EVENT_ID = "event_id";
   private static final String LABEL = "label";
   private static final int MAX_EVENTS = 1000;
   private static final String RANDOM_VAL = "random_val";
   private static final String REFERRER = "referrer";
   private static final String SCREEN_HEIGHT = "screen_height";
   private static final String SCREEN_WIDTH = "screen_width";
   private static final String STORE_ID = "store_id";
   private static final String TIMESTAMP_CURRENT = "timestamp_current";
   private static final String TIMESTAMP_FIRST = "timestamp_first";
   private static final String TIMESTAMP_PREVIOUS = "timestamp_previous";
   private static final String USER_ID = "user_id";
   private static final String VALUE = "value";
   private static final String VISITS = "visits";
   private SQLiteStatement compiledCountStatement = null;
   private PersistentEventStore.DataBaseHelper databaseHelper;
   private int numStoredEvents;
   private boolean sessionUpdated;
   private int storeId;
   private long timestampCurrent;
   private long timestampFirst;
   private long timestampPrevious;
   private boolean useStoredVisitorVars;
   private int visits;


   PersistentEventStore(PersistentEventStore.DataBaseHelper var1) {
      this.databaseHelper = var1;

      try {
         var1.getWritableDatabase().close();
      } catch (SQLiteException var4) {
         String var2 = var4.toString();
         int var3 = Log.e("googleanalytics", var2);
      }
   }

   public void deleteEvent(long var1) {
      String var3 = "event_id=" + var1;

      try {
         SQLiteDatabase var4 = this.databaseHelper.getWritableDatabase();
         if(var4.delete("events", var3, (String[])null) != 0) {
            int var5 = this.numStoredEvents + -1;
            this.numStoredEvents = var5;
            var4.delete("custom_variables", var3, (String[])null);
         }
      } catch (SQLiteException var9) {
         String var7 = var9.toString();
         int var8 = Log.e("googleanalytics", var7);
      }
   }

   CustomVariableBuffer getCustomVariables(long param1) {
      // $FF: Couldn't be decompiled
   }

   public int getNumStoredEvents() {
      long var2;
      int var4;
      try {
         if(this.compiledCountStatement == null) {
            SQLiteStatement var1 = this.databaseHelper.getReadableDatabase().compileStatement("SELECT COUNT(*) from events");
            this.compiledCountStatement = var1;
         }

         var2 = this.compiledCountStatement.simpleQueryForLong();
      } catch (SQLiteException var7) {
         String var5 = var7.toString();
         int var6 = Log.e("googleanalytics", var5);
         var4 = 0;
         return var4;
      }

      var4 = (int)var2;
      return var4;
   }

   public String getReferrer() {
      // $FF: Couldn't be decompiled
   }

   public int getStoreId() {
      return this.storeId;
   }

   public String getVisitorCustomVar(int param1) {
      // $FF: Couldn't be decompiled
   }

   CustomVariableBuffer getVisitorVarBuffer() {
      CustomVariableBuffer var1 = new CustomVariableBuffer();

      try {
         Cursor var2 = this.databaseHelper.getReadableDatabase().query("custom_var_cache", (String[])null, "cv_scope=1", (String[])null, (String)null, (String)null, (String)null);

         while(var2.moveToNext()) {
            int var3 = var2.getColumnIndex("cv_index");
            int var4 = var2.getInt(var3);
            int var5 = var2.getColumnIndex("cv_name");
            String var6 = var2.getString(var5);
            int var7 = var2.getColumnIndex("cv_value");
            String var8 = var2.getString(var7);
            int var9 = var2.getColumnIndex("cv_scope");
            int var10 = var2.getInt(var9);
            CustomVariable var11 = new CustomVariable(var4, var6, var8, var10);
            var1.setCustomVariable(var11);
         }

         var2.close();
      } catch (SQLiteException var14) {
         String var12 = var14.toString();
         int var13 = Log.e("googleanalytics", var12);
      }

      return var1;
   }

   public Event[] peekEvents() {
      return this.peekEvents(1000);
   }

   public Event[] peekEvents(int param1) {
      // $FF: Couldn't be decompiled
   }

   void putCustomVariables(Event param1, long param2) {
      // $FF: Couldn't be decompiled
   }

   public void putEvent(Event param1) {
      // $FF: Couldn't be decompiled
   }

   public void setReferrer(String var1) {
      try {
         SQLiteDatabase var2 = this.databaseHelper.getWritableDatabase();
         ContentValues var3 = new ContentValues();
         var3.put("referrer", var1);
         var2.insert("install_referrer", (String)null, var3);
      } catch (SQLiteException var8) {
         String var6 = var8.toString();
         int var7 = Log.e("googleanalytics", var6);
      }
   }

   public void startNewVisit() {
      // $FF: Couldn't be decompiled
   }

   void storeUpdatedSession() {
      try {
         SQLiteDatabase var1 = this.databaseHelper.getWritableDatabase();
         ContentValues var2 = new ContentValues();
         Long var3 = Long.valueOf(this.timestampPrevious);
         var2.put("timestamp_previous", var3);
         Long var4 = Long.valueOf(this.timestampCurrent);
         var2.put("timestamp_current", var4);
         Integer var5 = Integer.valueOf(this.visits);
         var2.put("visits", var5);
         String[] var6 = new String[1];
         String var7 = Long.toString(this.timestampFirst);
         var6[0] = var7;
         var1.update("session", var2, "timestamp_first=?", var6);
         this.sessionUpdated = (boolean)1;
      } catch (SQLiteException var11) {
         String var9 = var11.toString();
         int var10 = Log.e("googleanalytics", var9);
      }
   }

   static class DataBaseHelper extends SQLiteOpenHelper {

      public DataBaseHelper(Context var1) {
         super(var1, "google_analytics.db", (CursorFactory)null, 2);
      }

      public DataBaseHelper(Context var1, String var2) {
         super(var1, var2, (CursorFactory)null, 2);
      }

      private void createCustomVariableTables(SQLiteDatabase var1) {
         int var2 = 1;
         var1.execSQL("DROP TABLE IF EXISTS custom_variables");
         StringBuilder var3 = (new StringBuilder()).append("CREATE TABLE custom_variables (");
         Object[] var4 = new Object[var2];
         var4[0] = "cv_id";
         String var5 = String.format(" \'%s\' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,", var4);
         StringBuilder var6 = var3.append(var5);
         Object[] var7 = new Object[var2];
         var7[0] = "event_id";
         String var8 = String.format(" \'%s\' INTEGER NOT NULL,", var7);
         StringBuilder var9 = var6.append(var8);
         Object[] var10 = new Object[var2];
         var10[0] = "cv_index";
         String var11 = String.format(" \'%s\' INTEGER NOT NULL,", var10);
         StringBuilder var12 = var9.append(var11);
         Object[] var13 = new Object[var2];
         var13[0] = "cv_name";
         String var14 = String.format(" \'%s\' CHAR(64) NOT NULL,", var13);
         StringBuilder var15 = var12.append(var14);
         Object[] var16 = new Object[var2];
         var16[0] = "cv_value";
         String var17 = String.format(" \'%s\' CHAR(64) NOT NULL,", var16);
         StringBuilder var18 = var15.append(var17);
         Object[] var19 = new Object[var2];
         var19[0] = "cv_scope";
         String var20 = String.format(" \'%s\' INTEGER NOT NULL);", var19);
         String var21 = var18.append(var20).toString();
         var1.execSQL(var21);
         var1.execSQL("DROP TABLE IF EXISTS custom_var_cache");
         StringBuilder var22 = (new StringBuilder()).append("CREATE TABLE custom_var_cache (");
         Object[] var23 = new Object[var2];
         var23[0] = "cv_id";
         String var24 = String.format(" \'%s\' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,", var23);
         StringBuilder var25 = var22.append(var24);
         Object[] var26 = new Object[var2];
         var26[0] = "event_id";
         String var27 = String.format(" \'%s\' INTEGER NOT NULL,", var26);
         StringBuilder var28 = var25.append(var27);
         Object[] var29 = new Object[var2];
         var29[0] = "cv_index";
         String var30 = String.format(" \'%s\' INTEGER NOT NULL,", var29);
         StringBuilder var31 = var28.append(var30);
         Object[] var32 = new Object[var2];
         var32[0] = "cv_name";
         String var33 = String.format(" \'%s\' CHAR(64) NOT NULL,", var32);
         StringBuilder var34 = var31.append(var33);
         Object[] var35 = new Object[var2];
         var35[0] = "cv_value";
         String var36 = String.format(" \'%s\' CHAR(64) NOT NULL,", var35);
         StringBuilder var37 = var34.append(var36);
         Object[] var38 = new Object[var2];
         var38[0] = "cv_scope";
         String var39 = String.format(" \'%s\' INTEGER NOT NULL);", var38);
         String var40 = var37.append(var39).toString();
         var1.execSQL(var40);

         while(var2 <= 5) {
            ContentValues var41 = new ContentValues();
            Integer var42 = Integer.valueOf(0);
            var41.put("event_id", var42);
            Integer var43 = Integer.valueOf(var2);
            var41.put("cv_index", var43);
            var41.put("cv_name", "");
            Integer var44 = Integer.valueOf(3);
            var41.put("cv_scope", var44);
            var41.put("cv_value", "");
            var1.insert("custom_var_cache", "event_id", var41);
            ++var2;
         }

      }

      public void onCreate(SQLiteDatabase var1) {
         StringBuilder var2 = (new StringBuilder()).append("CREATE TABLE events (");
         Object[] var3 = new Object[]{"event_id"};
         String var4 = String.format(" \'%s\' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,", var3);
         StringBuilder var5 = var2.append(var4);
         Object[] var6 = new Object[]{"user_id"};
         String var7 = String.format(" \'%s\' INTEGER NOT NULL,", var6);
         StringBuilder var8 = var5.append(var7);
         Object[] var9 = new Object[]{"account_id"};
         String var10 = String.format(" \'%s\' CHAR(256) NOT NULL,", var9);
         StringBuilder var11 = var8.append(var10);
         Object[] var12 = new Object[]{"random_val"};
         String var13 = String.format(" \'%s\' INTEGER NOT NULL,", var12);
         StringBuilder var14 = var11.append(var13);
         Object[] var15 = new Object[]{"timestamp_first"};
         String var16 = String.format(" \'%s\' INTEGER NOT NULL,", var15);
         StringBuilder var17 = var14.append(var16);
         Object[] var18 = new Object[]{"timestamp_previous"};
         String var19 = String.format(" \'%s\' INTEGER NOT NULL,", var18);
         StringBuilder var20 = var17.append(var19);
         Object[] var21 = new Object[]{"timestamp_current"};
         String var22 = String.format(" \'%s\' INTEGER NOT NULL,", var21);
         StringBuilder var23 = var20.append(var22);
         Object[] var24 = new Object[]{"visits"};
         String var25 = String.format(" \'%s\' INTEGER NOT NULL,", var24);
         StringBuilder var26 = var23.append(var25);
         Object[] var27 = new Object[]{"category"};
         String var28 = String.format(" \'%s\' CHAR(256) NOT NULL,", var27);
         StringBuilder var29 = var26.append(var28);
         Object[] var30 = new Object[]{"action"};
         String var31 = String.format(" \'%s\' CHAR(256) NOT NULL,", var30);
         StringBuilder var32 = var29.append(var31);
         Object[] var33 = new Object[]{"label"};
         String var34 = String.format(" \'%s\' CHAR(256), ", var33);
         StringBuilder var35 = var32.append(var34);
         Object[] var36 = new Object[]{"value"};
         String var37 = String.format(" \'%s\' INTEGER,", var36);
         StringBuilder var38 = var35.append(var37);
         Object[] var39 = new Object[]{"screen_width"};
         String var40 = String.format(" \'%s\' INTEGER,", var39);
         StringBuilder var41 = var38.append(var40);
         Object[] var42 = new Object[]{"screen_height"};
         String var43 = String.format(" \'%s\' INTEGER);", var42);
         String var44 = var41.append(var43).toString();
         var1.execSQL(var44);
         StringBuilder var45 = (new StringBuilder()).append("CREATE TABLE session (");
         Object[] var46 = new Object[]{"timestamp_first"};
         String var47 = String.format(" \'%s\' INTEGER PRIMARY KEY,", var46);
         StringBuilder var48 = var45.append(var47);
         Object[] var49 = new Object[]{"timestamp_previous"};
         String var50 = String.format(" \'%s\' INTEGER NOT NULL,", var49);
         StringBuilder var51 = var48.append(var50);
         Object[] var52 = new Object[]{"timestamp_current"};
         String var53 = String.format(" \'%s\' INTEGER NOT NULL,", var52);
         StringBuilder var54 = var51.append(var53);
         Object[] var55 = new Object[]{"visits"};
         String var56 = String.format(" \'%s\' INTEGER NOT NULL,", var55);
         StringBuilder var57 = var54.append(var56);
         Object[] var58 = new Object[]{"store_id"};
         String var59 = String.format(" \'%s\' INTEGER NOT NULL);", var58);
         String var60 = var57.append(var59).toString();
         var1.execSQL(var60);
         var1.execSQL("CREATE TABLE install_referrer (referrer TEXT PRIMARY KEY NOT NULL);");
         this.createCustomVariableTables(var1);
      }

      public void onUpgrade(SQLiteDatabase var1, int var2, int var3) {
         if(var3 == 2) {
            this.createCustomVariableTables(var1);
         }
      }
   }
}
