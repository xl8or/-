package com.android.common.content;

import android.accounts.Account;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

public class SyncStateContentProviderHelper {

   private static final String[] ACCOUNT_PROJECTION;
   private static long DB_VERSION = 1L;
   public static final String PATH = "syncstate";
   private static final String QUERY_COUNT_SYNC_STATE_ROWS = "SELECT count(*) FROM _sync_state WHERE _id=?";
   private static final String SELECT_BY_ACCOUNT = "account_name=? AND account_type=?";
   private static final String SYNC_STATE_META_TABLE = "_sync_state_metadata";
   private static final String SYNC_STATE_META_VERSION_COLUMN = "version";
   private static final String SYNC_STATE_TABLE = "_sync_state";


   static {
      String[] var0 = new String[]{"account_name", "account_type"};
      ACCOUNT_PROJECTION = var0;
   }

   public SyncStateContentProviderHelper() {}

   private static <T extends Object> boolean contains(T[] var0, T var1) {
      boolean var2 = true;
      Object[] var3 = var0;
      int var4 = var0.length;
      int var5 = 0;

      while(true) {
         if(var5 >= var4) {
            var2 = false;
            break;
         }

         Object var6 = var3[var5];
         if(var6 == null) {
            if(var1 == null) {
               break;
            }
         } else if(var1 != null && var6.equals(var1)) {
            break;
         }

         ++var5;
      }

      return var2;
   }

   public void createDatabase(SQLiteDatabase var1) {
      var1.execSQL("DROP TABLE IF EXISTS _sync_state");
      var1.execSQL("CREATE TABLE _sync_state (_id INTEGER PRIMARY KEY,account_name TEXT NOT NULL,account_type TEXT NOT NULL,data TEXT,UNIQUE(account_name, account_type));");
      var1.execSQL("DROP TABLE IF EXISTS _sync_state_metadata");
      var1.execSQL("CREATE TABLE _sync_state_metadata (version INTEGER);");
      ContentValues var2 = new ContentValues();
      Long var3 = Long.valueOf(DB_VERSION);
      var2.put("version", var3);
      var1.insert("_sync_state_metadata", "version", var2);
   }

   public int delete(SQLiteDatabase var1, String var2, String[] var3) {
      return var1.delete("_sync_state", var2, var3);
   }

   public long insert(SQLiteDatabase var1, ContentValues var2) {
      return var1.replace("_sync_state", "account_name", var2);
   }

   public void onAccountsChanged(SQLiteDatabase var1, Account[] var2) {
      String[] var3 = ACCOUNT_PROJECTION;
      Object var5 = null;
      Object var6 = null;
      Object var7 = null;
      Object var8 = null;
      Cursor var9 = var1.query("_sync_state", var3, (String)null, (String[])var5, (String)var6, (String)var7, (String)var8);

      try {
         while(var9.moveToNext()) {
            String var10 = var9.getString(0);
            String var11 = var9.getString(1);
            Account var12 = new Account(var10, var11);
            if(!contains(var2, var12)) {
               String[] var13 = new String[]{var10, var11};
               var1.delete("_sync_state", "account_name=? AND account_type=?", var13);
            }
         }
      } finally {
         var9.close();
      }

   }

   public void onDatabaseOpened(SQLiteDatabase var1) {
      long var2 = DatabaseUtils.longForQuery(var1, "SELECT version FROM _sync_state_metadata", (String[])null);
      long var4 = DB_VERSION;
      if(var2 != var4) {
         this.createDatabase(var1);
      }
   }

   public Cursor query(SQLiteDatabase var1, String[] var2, String var3, String[] var4, String var5) {
      Object var10 = null;
      return var1.query("_sync_state", var2, var3, var4, (String)null, (String)var10, var5);
   }

   public int update(SQLiteDatabase var1, long var2, Object var4) {
      byte var5 = 0;
      String[] var6 = new String[1];
      String var7 = Long.toString(var2);
      var6[var5] = var7;
      if(DatabaseUtils.longForQuery(var1, "SELECT count(*) FROM _sync_state WHERE _id=?", var6) >= 1L) {
         String var8 = "UPDATE _sync_state SET data=? WHERE _id=" + var2;
         Object[] var9 = new Object[]{var4};
         var1.execSQL(var8, var9);
         var5 = 1;
      }

      return var5;
   }

   public int update(SQLiteDatabase var1, ContentValues var2, String var3, String[] var4) {
      return var1.update("_sync_state", var2, var3, var4);
   }
}
