package com.google.android.finsky.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import com.google.android.finsky.adapters.DocumentBasedAdapter;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.TrackList;
import com.google.android.finsky.model.Bucket;
import com.google.android.finsky.model.Track;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.remoting.protos.DeviceDoc;
import com.google.android.finsky.remoting.protos.DocList;
import com.google.android.finsky.utils.BitmapLoader;
import com.google.android.finsky.utils.CorpusMetadata;
import com.google.android.finsky.utils.Lists;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BucketAdapter extends DocumentBasedAdapter {

   private static final int SUGGESTION_HEADER_POSITION = 0;
   private static final int VIEW_TYPE_BUCKET = 0;
   private static final int VIEW_TYPE_COUNT = 2;
   private static final int VIEW_TYPE_SUGGESTION_HEADER = 1;
   private final int mBucketEntryIconHeight;
   private final List<Bucket> mBuckets;
   private final int mCellLayoutId;
   private final int mColumns;
   private final int mHeaderIconHeight;
   private final int mHeaderIconWidth;
   private final String mOriginalQuery;
   private final int mRows;
   private final int mSuggestionBarLayoutId;
   private String mSuggestionQueryTerm;


   public BucketAdapter(Context var1, NavigationManager var2, BitmapLoader var3, List<DocList.Bucket> var4, int var5, int var6, int var7, int var8, String var9, String var10, String var11) {
      super(var1, var2, var3, (boolean)0, (boolean)0, var11);
      this.mColumns = var5;
      this.mRows = var6;
      this.mCellLayoutId = var7;
      this.mSuggestionBarLayoutId = var8;
      Resources var19 = var1.getResources();
      int var20 = var19.getDimensionPixelSize(2131427344);
      this.mBucketEntryIconHeight = var20;
      int var21 = var19.getDimensionPixelSize(2131427340);
      this.mHeaderIconWidth = var21;
      int var22 = var19.getDimensionPixelSize(2131427339);
      this.mHeaderIconHeight = var22;
      List var23 = Bucket.fromProtos(var4);
      this.mBuckets = var23;
      this.mOriginalQuery = var9;
      this.mSuggestionQueryTerm = var10;
   }

   private void bindBucketEntries(Bucket var1, ViewGroup var2) {
      LinearLayout var3 = (LinearLayout)var2.findViewById(2131755071);
      int var4 = 0;

      while(true) {
         int var5 = this.mRows;
         if(var4 >= var5) {
            return;
         }

         LinearLayout var6 = (LinearLayout)var3.getChildAt(var4);
         int var7 = this.getDisplayedRows(var1);
         if(var4 >= var7) {
            var6.setVisibility(8);
         } else {
            var6.setVisibility(0);
            int var8 = this.getDisplayedColumns(var1, var4);
            int var9 = 0;

            while(true) {
               int var10 = this.mColumns;
               if(var9 >= var10) {
                  break;
               }

               View var11 = var6.getChildAt(var9);
               if(var9 < var8) {
                  int var12 = this.mColumns * var4 + var9;
                  ViewGroup var13 = (ViewGroup)var11;
                  this.bindBucketEntry(var1, var12, var13);
                  var11.setVisibility(0);
               } else {
                  var11.setVisibility(4);
               }

               ++var9;
            }
         }

         ++var4;
      }
   }

   private void bindBucketEntry(Bucket var1, int var2, ViewGroup var3) {
      DeviceDoc.DeviceDocument var4 = var1.getItem(var2);
      String var5 = var1.getCookie();
      Document var6 = new Document(var4, var5);
      Context var7 = this.mContext;
      int var8 = var6.getBackend();
      int var9 = CorpusMetadata.getIconWidth(var7, var8);
      int var10 = this.mBucketEntryIconHeight;
      boolean var11 = var1.isOrdered();
      this.bindDocument(var6, var3, var9, var10, var11, var2);
   }

   private void bindSongsListBucket(Bucket var1, ViewGroup var2) {
      ViewGroup var3 = (ViewGroup)var2.findViewById(2131755071);
      Context var4 = this.mContext;
      TrackList var5 = new TrackList(var4, (AttributeSet)null);
      List var6 = this.getMockTracks();
      var5.setTracks(var6);
      var3.removeAllViews();
      var3.addView(var5);
   }

   private void bindView(Bucket var1, ViewGroup var2) {
      if(var1.isSongsList()) {
         this.bindSongsListBucket(var1, var2);
      } else {
         this.bindBucketEntries(var1, var2);
      }

      String var3 = this.mOriginalQuery;
      int var4 = this.mHeaderIconWidth;
      int var5 = this.mHeaderIconHeight;
      this.bindBucketHeader(var1, var2, var3, var4, var5);
   }

   private View getBucketView(int var1, View var2, ViewGroup var3) {
      Bucket var4 = (Bucket)this.mBuckets.get(var1);
      if(var2 == null) {
         var2 = this.inflateViewForBucket(var4, var3);
      }

      ViewGroup var5 = (ViewGroup)var2;
      this.bindView(var4, var5);
      return var2;
   }

   private int getDisplayedColumns(Bucket var1, int var2) {
      int var3 = this.getDisplayedRows(var1);
      int var8;
      if(var2 < var3) {
         int var4 = var1.getItemCount();
         int var5 = this.mColumns * var2;
         int var6 = var4 - var5;
         int var7 = this.mColumns;
         var8 = Math.min(var6, var7);
      } else {
         var8 = 0;
      }

      return var8;
   }

   private int getDisplayedRows(Bucket var1) {
      double var2 = (double)var1.getItemCount();
      double var4 = (double)this.mColumns;
      int var6 = (int)Math.ceil(var2 / var4);
      int var7 = this.mRows;
      return Math.min(var6, var7);
   }

   private List<Track> getMockTracks() {
      ArrayList var1 = Lists.newArrayList();
      InputStream var2 = this.mContext.getResources().openRawResource(2131165186);
      InputStreamReader var3 = new InputStreamReader(var2);
      BufferedReader var4 = new BufferedReader(var3);
      int var5 = 1;

      IOException var18;
      while(true) {
         Track var7;
         try {
            String var6 = var4.readLine();
            if(var6 == null) {
               return var1;
            }

            var7 = new Track();
            var7.docId = "1234";
            var7.title = var6;
            var7.album = "This Is It";
            String var8 = var4.readLine();
            var7.artist = var8;
         } catch (IOException var21) {
            var18 = var21;
            break;
         }

         int var9 = var5 + 1;

         try {
            var7.trackNo = var5;
            int var10 = Integer.parseInt(var4.readLine());
            var7.year = var10;
            String var11 = var4.readLine();
            var7.length = var11;
            String var12 = var4.readLine();
            var7.price = var12;
            String var13 = var4.readLine();
            var7.url = var13;
            Track.TrackMode var14 = Track.TrackMode.READY;
            var7.mode = var14;
            var1.add(var7);
            String var16 = var4.readLine();
         } catch (IOException var20) {
            var18 = var20;
            break;
         }

         var5 = var9;
      }

      var18.printStackTrace();
      return var1;
   }

   private View getSuggestionHeaderView(View var1, ViewGroup var2, String var3) {
      View var5;
      if(var1 == null) {
         int var4 = this.mSuggestionBarLayoutId;
         var5 = this.inflate(var4, var2, (boolean)0);
      } else {
         var5 = var1;
      }

      this.bindSuggestionHeader(var3, var5);
      return var5;
   }

   private View inflateViewForBucket(Bucket var1, ViewGroup var2) {
      View var3 = this.inflate(2130968594, var2, (boolean)0);
      ViewGroup var4 = (ViewGroup)var3.findViewById(2131755071);
      if(var1.isSongsList()) {
         Context var5 = this.mContext;
         TrackList var6 = new TrackList(var5, (AttributeSet)null);
         List var7 = this.getMockTracks();
         var6.setTracks(var7);
         var4.addView(var6);
      } else {
         int var8 = 0;

         while(true) {
            int var9 = this.mRows;
            if(var8 >= var9) {
               break;
            }

            LinearLayout var10 = (LinearLayout)this.inflate(2130968593, (ViewGroup)null, (boolean)0);
            int var11 = 0;

            while(true) {
               int var12 = this.mColumns;
               if(var11 >= var12) {
                  var4.addView(var10);
                  ++var8;
                  break;
               }

               int var13 = this.mCellLayoutId;
               ViewGroup var14 = (ViewGroup)this.inflate(var13, var10, (boolean)0);
               var14.setVisibility(8);
               var10.addView(var14);
               ++var11;
            }
         }
      }

      return var3;
   }

   public int getCount() {
      int var1 = this.mBuckets.size();
      byte var2;
      if(this.mSuggestionQueryTerm == null) {
         var2 = 0;
      } else {
         var2 = 1;
      }

      return var2 + var1;
   }

   public Bucket getItem(int var1) {
      int var2 = this.mBuckets.size();
      Bucket var3;
      if(var1 < var2) {
         var3 = (Bucket)this.mBuckets.get(var1);
      } else {
         var3 = null;
      }

      return var3;
   }

   public int getItemViewType(int var1) {
      byte var2;
      if(this.mSuggestionQueryTerm != null && var1 == 0) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      return var2;
   }

   protected String getLastRequestError() {
      return null;
   }

   public View getView(int var1, View var2, ViewGroup var3) {
      View var8;
      switch(this.getItemViewType(var1)) {
      case 0:
         byte var9;
         if(this.mSuggestionQueryTerm == null) {
            var9 = 0;
         } else {
            var9 = 1;
         }

         int var10 = var1 - var9;
         var8 = this.getBucketView(var10, var2, var3);
         break;
      case 1:
         String var7 = this.mSuggestionQueryTerm;
         var8 = this.getSuggestionHeaderView(var2, var3, var7);
         break;
      default:
         StringBuilder var4 = (new StringBuilder()).append("Unknown type for getView ");
         int var5 = this.getItemViewType(var1);
         String var6 = var4.append(var5).toString();
         throw new IllegalStateException(var6);
      }

      return var8;
   }

   public int getViewTypeCount() {
      return 2;
   }

   protected boolean isMoreDataAvailable() {
      return false;
   }

   protected OnClickListener makeSuggestionClickListener(String var1) {
      return new BucketAdapter.1(var1);
   }

   protected void retryLoadingItems() {}

   class 1 implements OnClickListener {

      // $FF: synthetic field
      final String val$suggestionString;


      1(String var2) {
         this.val$suggestionString = var2;
      }

      public void onClick(View var1) {
         NavigationManager var2 = BucketAdapter.this.mNavigationManager;
         String var3 = this.val$suggestionString;
         String var4 = BucketAdapter.this.mCurrentPageUrl;
         var2.searchFromSuggestion(var3, 0, var4);
      }
   }
}
