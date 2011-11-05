package com.android.exchange.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MatrixCursor.RowBuilder;
import android.net.Uri;
import android.os.Bundle;
import com.android.exchange.EasSyncService;
import com.android.exchange.provider.GalResult;
import java.util.Iterator;

public class ExchangeProvider extends ContentProvider {

   public static final String EXCHANGE_AUTHORITY = "com.android.exchange.provider";
   public static final String EXTRAS_TOTAL_RESULTS = "com.android.exchange.provider.TOTAL_RESULTS";
   private static final int GAL_BASE = 0;
   public static final int GAL_COLUMN_DATA = 2;
   public static final int GAL_COLUMN_DISPLAYNAME = 1;
   public static final int GAL_COLUMN_ID;
   private static final int GAL_FILTER;
   public static final String[] GAL_PROJECTION;
   public static final Uri GAL_URI = Uri.parse("content://com.android.exchange.provider/gal/");
   private static final UriMatcher sURIMatcher;


   static {
      String[] var0 = new String[]{"_id", "displayName", "data"};
      GAL_PROJECTION = var0;
      sURIMatcher = new UriMatcher(-1);
      sURIMatcher.addURI("com.android.exchange.provider", "gal/*/*", 0);
   }

   public ExchangeProvider() {}

   private static void addGalDataRow(MatrixCursor var0, long var1, String var3, String var4) {
      RowBuilder var5 = var0.newRow();
      Long var6 = Long.valueOf(var1);
      RowBuilder var7 = var5.add(var6).add(var3).add(var4);
   }

   public int delete(Uri var1, String var2, String[] var3) {
      return -1;
   }

   public String getType(Uri var1) {
      return "vnd.android.cursor.dir/gal-entry";
   }

   public Uri insert(Uri var1, ContentValues var2) {
      return null;
   }

   public boolean onCreate() {
      return false;
   }

   public Cursor query(Uri var1, String[] var2, String var3, String[] var4, String var5) {
      UriMatcher var6 = sURIMatcher;
      switch(var6.match(var1)) {
      case 0:
         ExchangeProvider.MatrixCursorExtras var11 = new ExchangeProvider.MatrixCursorExtras;
         String[] var12 = GAL_PROJECTION;
         var11.<init>(var12);
         String var15 = (String)var1.getPathSegments().get(1);
         String var16 = (String)var1.getPathSegments().get(2);

         long var17;
         try {
            var17 = Long.parseLong(var15);
         } catch (NumberFormatException var43) {
            throw new IllegalArgumentException("Illegal value in URI");
         }

         Context var21 = this.getContext();
         GalResult var25 = EasSyncService.searchGal(var21, var17, var16);
         if(var25 != null) {
            Iterator var26 = var25.galData.iterator();

            while(var26.hasNext()) {
               GalResult.GalData var27 = (GalResult.GalData)var26.next();
               long var28 = var27._id;
               String var30 = var27.displayName;
               String var31 = var27.emailAddress;
               addGalDataRow(var11, var28, var30, var31);
            }

            Bundle var38 = new Bundle();
            int var39 = var25.total;
            String var41 = "com.android.exchange.provider.TOTAL_RESULTS";
            var38.putInt(var41, var39);
            var11.setExtras(var38);
         }

         return var11;
      default:
         StringBuilder var8 = (new StringBuilder()).append("Unknown URI ");
         String var10 = var8.append(var1).toString();
         throw new IllegalArgumentException(var10);
      }
   }

   public int update(Uri var1, ContentValues var2, String var3, String[] var4) {
      return -1;
   }

   private static class MatrixCursorExtras extends MatrixCursor {

      private Bundle mExtras = null;


      public MatrixCursorExtras(String[] var1) {
         super(var1);
      }

      public Bundle getExtras() {
         return this.mExtras;
      }

      public void setExtras(Bundle var1) {
         this.mExtras = var1;
      }
   }
}
