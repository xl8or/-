package com.google.android.finsky.providers;

import android.content.SearchRecentSuggestionsProvider;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.finsky.FinskyApp;
import java.util.concurrent.Semaphore;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecentSuggestionsProvider extends SearchRecentSuggestionsProvider {

   public static final String AUTHORITY = "com.google.android.finsky.RecentSuggestionsProvider";
   public static final int MODE = 1;


   public RecentSuggestionsProvider() {
      this.setupSuggestions("com.google.android.finsky.RecentSuggestionsProvider", 1);
   }

   private int inferCurrentBackend() {
      return 3;
   }

   public Cursor query(Uri var1, String[] var2, String var3, String[] var4, String var5) {
      if(var4 != null && var4.length != 0) {
         String var7 = var4[0].toLowerCase();
         int var8 = this.inferCurrentBackend();
         Cursor var9 = super.query(var1, var2, var3, var4, var5);
         RecentSuggestionsProvider.HistorySearchSuggestor var10 = new RecentSuggestionsProvider.HistorySearchSuggestor(var7, var9);
         Object var11;
         switch(var8) {
         case 1:
            var11 = new RecentSuggestionsProvider.BooksSearchSuggestor(var7);
            break;
         case 2:
         default:
            var11 = new RecentSuggestionsProvider.EmptySearchSuggestor();
            break;
         case 3:
            var11 = new RecentSuggestionsProvider.AppsSearchSuggestor(var7);
         }

         Cursor[] var12 = new Cursor[2];
         Cursor var13 = var10.gatherSuggestions();
         var12[0] = var13;
         Cursor var14 = ((RecentSuggestionsProvider.AsyncSuggestionAuthority)var11).gatherSuggestions();
         var12[1] = var14;
         return new MergeCursor(var12);
      } else {
         String var6 = "SelectionArgs must be provided for the Uri: " + var1;
         throw new IllegalArgumentException(var6);
      }
   }

   private class AppsSearchSuggestor extends RecentSuggestionsProvider.AsyncSuggestionAuthority {

      final Uri BASE_URI;


      public AppsSearchSuggestor(String var2) {
         super(var2);
         Uri var3 = Uri.parse("https://market.android.com/suggest/SuggRequest");
         this.BASE_URI = var3;
      }

      protected void makeRequest(RecentSuggestionsProvider.OnCompleteListener var1) {
         Builder var2 = this.BASE_URI.buildUpon();
         String var3 = this.mQuery;
         Builder var4 = var2.appendQueryParameter("query", var3).appendQueryParameter("json", "1").appendQueryParameter("hl", "en").appendQueryParameter("gl", "US");
         if(TextUtils.isEmpty(this.mQuery)) {
            var1.onComplete();
         } else {
            String var5 = var4.build().toString();
            RecentSuggestionsProvider.AppsSearchSuggestor.1 var6 = new RecentSuggestionsProvider.AppsSearchSuggestor.1(var1);
            RecentSuggestionsProvider.AppsSearchSuggestor.2 var7 = new RecentSuggestionsProvider.AppsSearchSuggestor.2(var1);
            JsonArrayRequest var8 = new JsonArrayRequest(var5, var6, var7);
            Request var9 = FinskyApp.get().getRequestQueue().add(var8);
         }
      }

      class 2 implements Response.ErrorListener {

         // $FF: synthetic field
         final RecentSuggestionsProvider.OnCompleteListener val$listener;


         2(RecentSuggestionsProvider.OnCompleteListener var2) {
            this.val$listener = var2;
         }

         public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {
            this.val$listener.onComplete();
         }
      }

      class 1 implements Response.Listener<JSONArray> {

         // $FF: synthetic field
         final RecentSuggestionsProvider.OnCompleteListener val$listener;


         1(RecentSuggestionsProvider.OnCompleteListener var2) {
            this.val$listener = var2;
         }

         public void onResponse(JSONArray var1) {
            int var2 = 0;

            while(true) {
               boolean var11 = false;

               label51: {
                  try {
                     var11 = true;
                     int var3 = var1.length();
                     if(var2 >= var3) {
                        var11 = false;
                        break;
                     }

                     JSONObject var4 = var1.getJSONObject(var2);
                     RecentSuggestionsProvider.AppsSearchSuggestor var5 = AppsSearchSuggestor.this;
                     String var6 = var4.getString("s");
                     var5.addRow(var2, 17301583, var6);
                     var11 = false;
                     break label51;
                  } catch (JSONException var12) {
                     var11 = false;
                  } finally {
                     if(var11) {
                        this.val$listener.onComplete();
                     }
                  }

                  this.val$listener.onComplete();
                  return;
               }

               ++var2;
            }

            this.val$listener.onComplete();
         }
      }
   }

   private class HistorySearchSuggestor extends RecentSuggestionsProvider.AsyncSuggestionAuthority {

      private final Cursor mCursor;


      public HistorySearchSuggestor(String var2, Cursor var3) {
         super(var2);
         this.mCursor = var3;
      }

      protected void makeRequest(RecentSuggestionsProvider.OnCompleteListener var1) {
         int var2 = 0;
         int var3 = 0;
         int var4 = 0;
         String[] var5 = this.mCursor.getColumnNames();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            String var8 = var5[var7];
            if(var8.equalsIgnoreCase("_id")) {
               var3 = var2;
            } else if(var8.equalsIgnoreCase("suggest_text_1")) {
               var4 = var2;
            }

            ++var2;
         }

         boolean var12;
         for(boolean var9 = this.mCursor.moveToPosition(0); !this.mCursor.isAfterLast(); var12 = this.mCursor.moveToNext()) {
            int var10 = this.mCursor.getInt(var3);
            String var11 = this.mCursor.getString(var4);
            this.addRow(var10, 17301578, var11);
         }

         this.mCursor.close();
         var1.onComplete();
      }
   }

   private interface OnCompleteListener {

      void onComplete();
   }

   private class EmptySearchSuggestor extends RecentSuggestionsProvider.AsyncSuggestionAuthority {

      public EmptySearchSuggestor() {
         super("");
      }

      protected void makeRequest(RecentSuggestionsProvider.OnCompleteListener var1) {
         var1.onComplete();
      }
   }

   private class BooksSearchSuggestor extends RecentSuggestionsProvider.AsyncSuggestionAuthority {

      final Uri BASE_URI;


      public BooksSearchSuggestor(String var2) {
         super(var2);
         Uri var3 = Uri.parse("http://www.google.com/complete/search");
         this.BASE_URI = var3;
      }

      protected void makeRequest(RecentSuggestionsProvider.OnCompleteListener var1) {
         Builder var2 = this.BASE_URI.buildUpon();
         String var3 = this.mQuery;
         Builder var4 = var2.appendQueryParameter("q", var3).appendQueryParameter("json", "1").appendQueryParameter("hl", "en").appendQueryParameter("gl", "US").appendQueryParameter("ds", "bo");
         if(TextUtils.isEmpty(this.mQuery)) {
            var1.onComplete();
         } else {
            String var5 = var4.build().toString();
            RecentSuggestionsProvider.BooksSearchSuggestor.1 var6 = new RecentSuggestionsProvider.BooksSearchSuggestor.1(var1);
            RecentSuggestionsProvider.BooksSearchSuggestor.2 var7 = new RecentSuggestionsProvider.BooksSearchSuggestor.2(var1);
            JsonArrayRequest var8 = new JsonArrayRequest(var5, var6, var7);
            Request var9 = FinskyApp.get().getRequestQueue().add(var8);
         }
      }

      class 2 implements Response.ErrorListener {

         // $FF: synthetic field
         final RecentSuggestionsProvider.OnCompleteListener val$listener;


         2(RecentSuggestionsProvider.OnCompleteListener var2) {
            this.val$listener = var2;
         }

         public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {
            this.val$listener.onComplete();
         }
      }

      class 1 implements Response.Listener<JSONArray> {

         // $FF: synthetic field
         final RecentSuggestionsProvider.OnCompleteListener val$listener;


         1(RecentSuggestionsProvider.OnCompleteListener var2) {
            this.val$listener = var2;
         }

         public void onResponse(JSONArray param1) {
            // $FF: Couldn't be decompiled
         }
      }
   }

   private abstract class AsyncSuggestionAuthority {

      protected static final int COLUMN_ID = 0;
      protected static final int COLUMN_NAME = 2;
      protected static final int COLUMN_QUERY = 3;
      protected static final int COLUMN_THUMBNAIL = 1;
      protected final String[] COLUMNS;
      protected final String mQuery;
      private final MatrixCursor mResults;


      public AsyncSuggestionAuthority(String var2) {
         String[] var3 = new String[]{"_id", "suggest_icon_1", "suggest_text_1", "suggest_intent_query"};
         this.COLUMNS = var3;
         String[] var4 = this.COLUMNS;
         MatrixCursor var5 = new MatrixCursor(var4);
         this.mResults = var5;
         this.mQuery = var2;
      }

      protected void addRow(int var1, int var2, String var3) {
         Object[] var4 = new Object[this.COLUMNS.length];
         Integer var5 = Integer.valueOf(var1);
         var4[0] = var5;
         Integer var6 = Integer.valueOf(var2);
         var4[1] = var6;
         var4[2] = var3;
         var4[3] = var3;
         this.mResults.addRow(var4);
      }

      public Cursor gatherSuggestions() {
         Semaphore var1 = new Semaphore(0);
         RecentSuggestionsProvider.AsyncSuggestionAuthority.1 var2 = new RecentSuggestionsProvider.AsyncSuggestionAuthority.1(var1);
         this.makeRequest(var2);

         MatrixCursor var3;
         try {
            var1.acquire();
         } catch (InterruptedException var5) {
            var3 = this.mResults;
            return var3;
         }

         var3 = this.mResults;
         return var3;
      }

      protected abstract void makeRequest(RecentSuggestionsProvider.OnCompleteListener var1);

      class 1 implements RecentSuggestionsProvider.OnCompleteListener {

         // $FF: synthetic field
         final Semaphore val$sem;


         1(Semaphore var2) {
            this.val$sem = var2;
         }

         public void onComplete() {
            this.val$sem.release();
         }
      }
   }
}
