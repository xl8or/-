package com.google.android.finsky.local;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.os.AsyncTask;
import com.google.android.finsky.local.AssetStore;
import com.google.android.finsky.local.AutoUpdateState;
import com.google.android.finsky.local.LocalAsset;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Maps;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MigrationAsyncTask extends AsyncTask<Void, Void, Map<String, AutoUpdateState>> {

   private final String COLUMN_AUTO_UPDATE = "auto_update";
   private final String COLUMN_PACKAGE_NAME = "package_name";
   private final String DATABASE_NAME = "assets14.db";
   private final String TABLE_NAME = "assets10";
   private final AssetStore mAssetStore;
   private final Context mContext;


   public MigrationAsyncTask(Context var1, AssetStore var2) {
      this.mContext = var1;
      this.mAssetStore = var2;
   }

   protected Map<String, AutoUpdateState> doInBackground(Void ... var1) {
      HashMap var2 = Maps.newHashMap();
      File var3 = this.mContext.getDatabasePath("assets14.db");
      if(var3.exists()) {
         SQLiteDatabase var4;
         try {
            var4 = SQLiteDatabase.openDatabase(var3.getAbsolutePath(), (CursorFactory)null, 1);
         } catch (SQLiteException var27) {
            return var2;
         }

         Object[] var6 = new Object[0];
         FinskyLog.d("Legacy database found.", var6);
         String[] var7 = new String[]{"package_name", "auto_update"};
         Cursor var8 = var4.query("assets10", var7, (String)null, (String[])null, (String)null, (String)null, (String)null);
         byte var9 = 0;
         byte var10 = 1;

         try {
            while(var8.moveToNext()) {
               String var12 = var8.getString(var9);
               switch(var8.getInt(var10)) {
               case 0:
               default:
                  break;
               case 1:
                  AutoUpdateState var19 = AutoUpdateState.DISABLED;
                  var2.put(var12, var19);
                  break;
               case 2:
                  AutoUpdateState var21 = AutoUpdateState.ENABLED;
                  var2.put(var12, var21);
               }

               AutoUpdateState[] var13 = AutoUpdateState.values();
               int var14 = var8.getInt(var10);
               AutoUpdateState var15 = var13[var14];
               var2.put(var12, var15);
            }
         } finally {
            var8.close();
         }

         var4.close();
         boolean var23 = var3.delete();
      }

      return var2;
   }

   protected void onPostExecute(Map<String, AutoUpdateState> var1) {
      Iterator var2 = var1.keySet().iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         LocalAsset var4 = this.mAssetStore.getAsset(var3);
         if(var4 != null) {
            AutoUpdateState var5 = var4.getAutoUpdateState();
            AutoUpdateState var6 = AutoUpdateState.DEFAULT;
            if(var5.equals(var6)) {
               AutoUpdateState var7 = (AutoUpdateState)var1.get(var3);
               var4.setAutoUpdateState(var7);
            }
         }
      }

   }
}
