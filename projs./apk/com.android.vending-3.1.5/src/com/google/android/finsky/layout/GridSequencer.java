package com.google.android.finsky.layout;

import android.content.Context;
import android.view.ViewGroup;
import com.google.android.finsky.adapters.CorpusGridItem;
import com.google.android.finsky.adapters.DocumentGridItem;
import com.google.android.finsky.adapters.PromotedListGridItem;
import com.google.android.finsky.adapters.UnevenGridAdapter;
import com.google.android.finsky.adapters.UnevenGridItemType;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.utils.BitmapLoader;
import com.google.android.finsky.utils.Lists;
import com.google.android.finsky.utils.Maps;
import com.google.android.finsky.utils.ThumbnailUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GridSequencer {

   private final UnevenGridAdapter mAdapter;
   private BitmapLoader mBitmapLoader;
   private List<PromotedListGridItem.PromotedListGridItemConfig> mCannedListData;
   private final Context mContext;
   private String mCurrentPageUrl;
   private List<Document> mDocumentsWithPromo;
   private List<Document> mDocumentsWithoutPromo;
   private List<UnevenGridAdapter.UnevenGridItem> mFillerItems;
   private final UnevenGridItemType[] mLayoutSequence;
   private final NavigationManager mNavigationManager;
   boolean mReadyToSequence;
   private List<UnevenGridAdapter.UnevenGridItem> mSequencedItems;
   private final DfeToc mToc;
   private List<Document> mUsedDocuments;


   public GridSequencer(Context var1, NavigationManager var2, BitmapLoader var3, UnevenGridItemType[] var4, DfeToc var5, String var6) {
      ArrayList var7 = Lists.newArrayList();
      this.mDocumentsWithPromo = var7;
      ArrayList var8 = Lists.newArrayList();
      this.mDocumentsWithoutPromo = var8;
      ArrayList var9 = Lists.newArrayList();
      this.mUsedDocuments = var9;
      ArrayList var10 = Lists.newArrayList();
      this.mCannedListData = var10;
      ArrayList var11 = Lists.newArrayList();
      this.mSequencedItems = var11;
      ArrayList var12 = Lists.newArrayList();
      this.mFillerItems = var12;
      this.mContext = var1;
      this.mNavigationManager = var2;
      this.mBitmapLoader = var3;
      this.mLayoutSequence = var4;
      this.mCurrentPageUrl = var6;
      Context var13 = this.mContext;
      UnevenGridAdapter var14 = new UnevenGridAdapter(var13);
      this.mAdapter = var14;
      this.mToc = var5;
   }

   private void dedupeDocuments(List<Document> var1, List<Document> var2) {
      if(var1 != null) {
         if(var2 != null) {
            if(var1.size() != 0) {
               if(var2.size() != 0) {
                  Iterator var3 = var1.iterator();

                  while(var3.hasNext()) {
                     Document var4 = (Document)var3.next();
                     int var5 = 0;

                     while(true) {
                        int var6 = var2.size();
                        if(var5 >= var6) {
                           break;
                        }

                        String var7 = ((Document)var2.get(var5)).getDocId();
                        String var8 = var4.getDocId();
                        if(var7.equals(var8)) {
                           var2.remove(var5);
                           break;
                        }

                        ++var5;
                     }
                  }

               }
            }
         }
      }
   }

   private void fillPromoAndSquareItems(Map<UnevenGridItemType, Integer> var1, List<Document> var2, List<DocumentGridItem> var3, List<DocumentGridItem> var4) {
      UnevenGridItemType var5 = UnevenGridItemType.DOCUMENT_PROMO_4x2;
      int var7;
      if(var1.containsKey(var5)) {
         UnevenGridItemType var6 = UnevenGridItemType.DOCUMENT_PROMO_4x2;
         var7 = ((Integer)var1.get(var6)).intValue();
      } else {
         var7 = 0;
      }

      UnevenGridItemType var8 = UnevenGridItemType.DOCUMENT_SQUARE_2X2;
      int var10;
      if(var1.containsKey(var8)) {
         UnevenGridItemType var9 = UnevenGridItemType.DOCUMENT_SQUARE_2X2;
         var10 = ((Integer)var1.get(var9)).intValue();
      } else {
         var10 = 0;
      }

      UnevenGridItemType[] var11 = this.mLayoutSequence;
      int var12 = var11.length;

      for(int var13 = 0; var13 < var12; ++var13) {
         UnevenGridItemType var14 = var11[var13];
         UnevenGridItemType var15 = UnevenGridItemType.DOCUMENT_PROMO_4x2;
         if(var14 != var15 || var7 <= 0) {
            UnevenGridItemType var16 = UnevenGridItemType.DOCUMENT_SQUARE_2X2;
            if(var14 != var16 || var10 <= 0) {
               continue;
            }
         }

         if(var2.isEmpty()) {
            return;
         }

         Document var17 = (Document)var2.remove(0);
         UnevenGridItemType var18 = UnevenGridItemType.DOCUMENT_PROMO_4x2;
         if(var14 == var18) {
            Context var19 = this.mContext;
            NavigationManager var20 = this.mNavigationManager;
            BitmapLoader var21 = this.mBitmapLoader;
            String var22 = this.mCurrentPageUrl;
            DocumentGridItem var23 = DocumentGridItem.createLargePromo(var19, var20, var21, var17, var22);
            var3.add(var23);
            this.mUsedDocuments.add(var17);
            int var26 = var7 + -1;
         } else {
            UnevenGridItemType var27 = UnevenGridItemType.DOCUMENT_SQUARE_2X2;
            if(var14 == var27) {
               Context var28 = this.mContext;
               NavigationManager var29 = this.mNavigationManager;
               BitmapLoader var30 = this.mBitmapLoader;
               String var31 = this.mCurrentPageUrl;
               DocumentGridItem var32 = DocumentGridItem.createSquarePromo(var28, var29, var30, var17, var31);
               var4.add(var32);
               this.mUsedDocuments.add(var17);
               int var35 = var10 + -1;
            }
         }
      }

   }

   private void fillSmallItems(Map<UnevenGridItemType, Integer> var1, List<Document> var2, List<Document> var3, List<DocumentGridItem> var4) {
      UnevenGridItemType var5 = UnevenGridItemType.DOCUMENT_SMALL_2X1;
      int var7;
      if(var1.containsKey(var5)) {
         UnevenGridItemType var6 = UnevenGridItemType.DOCUMENT_SMALL_2X1;
         var7 = ((Integer)var1.get(var6)).intValue();
      } else {
         var7 = 0;
      }

      UnevenGridItemType[] var8 = this.mLayoutSequence;
      int var9 = var8.length;

      for(int var10 = 0; var10 < var9; ++var10) {
         UnevenGridItemType var11 = var8[var10];
         UnevenGridItemType var12 = UnevenGridItemType.DOCUMENT_SMALL_2X1;
         if(var11 == var12 && var7 > 0) {
            Document var13 = null;
            if(!var2.isEmpty()) {
               var13 = (Document)var2.remove(0);
            } else if(!var3.isEmpty()) {
               var13 = (Document)var3.remove(0);
            }

            if(var13 == null) {
               return;
            }

            Context var14 = this.mContext;
            NavigationManager var15 = this.mNavigationManager;
            BitmapLoader var16 = this.mBitmapLoader;
            String var17 = this.mCurrentPageUrl;
            DocumentGridItem var18 = DocumentGridItem.createSmallPromo(var14, var15, var16, var13, var17);
            var4.add(var18);
            this.mUsedDocuments.add(var13);
         }
      }

   }

   private static UnevenGridAdapter.UnevenGridItem getCannedListItem(Context var0, NavigationManager var1, List<PromotedListGridItem.PromotedListGridItemConfig> var2) {
      Object var4;
      if(var2 != null && var2.size() > 0) {
         PromotedListGridItem.PromotedListGridItemConfig var3 = (PromotedListGridItem.PromotedListGridItemConfig)var2.remove(0);
         var4 = new PromotedListGridItem(var0, var1, var3);
      } else {
         var4 = new GridSequencer.PlaceholderCell(2, 1);
      }

      return (UnevenGridAdapter.UnevenGridItem)var4;
   }

   private Map<UnevenGridItemType, Integer> getNeededCellCounts() {
      HashMap var1 = Maps.newHashMap();
      UnevenGridItemType[] var2 = this.mLayoutSequence;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         UnevenGridItemType var5 = var2[var4];
         if(var1.containsKey(var5)) {
            Integer var6 = Integer.valueOf(((Integer)var1.get(var5)).intValue() + 1);
            var1.put(var5, var6);
         } else {
            Integer var8 = Integer.valueOf(1);
            var1.put(var5, var8);
         }
      }

      Iterator var10 = this.mSequencedItems.iterator();

      while(var10.hasNext()) {
         UnevenGridItemType var11 = ((UnevenGridAdapter.UnevenGridItem)var10.next()).getGridItemType();
         if(var1.containsKey(var11)) {
            Integer var12 = Integer.valueOf(((Integer)var1.get(var11)).intValue() + -1);
            var1.put(var11, var12);
         }
      }

      return var1;
   }

   private boolean isSequenceCompleted() {
      boolean var1 = false;
      int var2 = this.mSequencedItems.size();
      int var3 = this.mLayoutSequence.length;
      if(var2 >= var3) {
         Iterator var4 = this.mSequencedItems.iterator();

         UnevenGridItemType var5;
         UnevenGridItemType var6;
         do {
            if(!var4.hasNext()) {
               var1 = true;
               break;
            }

            var5 = ((UnevenGridAdapter.UnevenGridItem)var4.next()).getGridItemType();
            var6 = UnevenGridItemType.PLACEHOLDER;
         } while(var5 != var6);
      }

      return var1;
   }

   private void notifyDataSetChanged() {
      if(this.updateGridItems()) {
         UnevenGridAdapter var1 = this.mAdapter;
         List var2 = this.mSequencedItems;
         List var3 = this.mFillerItems;
         var1.setData(var2, var3);
      }
   }

   private static void separateDocumentsByPromos(List<Document> var0, List<Document> var1, List<Document> var2) {
      Iterator var3 = var0.iterator();

      while(var3.hasNext()) {
         Document var4 = (Document)var3.next();
         if(ThumbnailUtils.hasPromoBitmap(var4)) {
            var1.add(var4);
         } else {
            var2.add(var4);
         }
      }

   }

   private boolean updateGridItems() {
      boolean var1;
      if(this.mReadyToSequence && !this.isSequenceCompleted()) {
         ArrayList var2 = Lists.newArrayList();
         ArrayList var3 = Lists.newArrayList();
         ArrayList var4 = Lists.newArrayList();
         Map var5 = this.getNeededCellCounts();
         List var6 = this.mDocumentsWithPromo;
         this.fillPromoAndSquareItems(var5, var6, var2, var3);
         List var7 = this.mDocumentsWithPromo;
         List var8 = this.mDocumentsWithoutPromo;
         this.fillSmallItems(var5, var7, var8, var4);
         int var9 = 0;

         while(true) {
            int var10 = this.mLayoutSequence.length;
            if(var9 >= var10) {
               var1 = true;
               break;
            }

            UnevenGridItemType var11 = this.mLayoutSequence[var9];
            if(this.mSequencedItems.size() <= var9 || ((UnevenGridAdapter.UnevenGridItem)this.mSequencedItems.get(var9)).getGridItemType() != var11) {
               Object var12 = null;
               int[] var13 = GridSequencer.1.$SwitchMap$com$google$android$finsky$adapters$UnevenGridItemType;
               int var14 = var11.ordinal();
               switch(var13[var14]) {
               case 1:
                  if(!var2.isEmpty()) {
                     var12 = (UnevenGridAdapter.UnevenGridItem)var2.remove(0);
                  } else {
                     var12 = new GridSequencer.PlaceholderCell(4, 2);
                  }
                  break;
               case 2:
                  if(!var4.isEmpty()) {
                     var12 = (UnevenGridAdapter.UnevenGridItem)var4.remove(0);
                  } else {
                     var12 = new GridSequencer.PlaceholderCell(2, 1);
                  }
                  break;
               case 3:
                  if(!var3.isEmpty()) {
                     var12 = (UnevenGridAdapter.UnevenGridItem)var3.remove(0);
                  } else {
                     var12 = new GridSequencer.PlaceholderCell(2, 2);
                  }
                  break;
               case 4:
                  Context var16 = this.mContext;
                  NavigationManager var17 = this.mNavigationManager;
                  List var18 = this.mCannedListData;
                  var12 = getCannedListItem(var16, var17, var18);
                  break;
               case 5:
                  Context var19 = this.mContext;
                  NavigationManager var20 = this.mNavigationManager;
                  DfeToc var21 = this.mToc;
                  var12 = new CorpusGridItem(var19, var20, var21);
               }

               if(this.mSequencedItems.size() > var9) {
                  this.mSequencedItems.set(var9, var12);
               } else {
                  this.mSequencedItems.add(var12);
               }
            }

            ++var9;
         }
      } else {
         var1 = false;
      }

      return var1;
   }

   public void addCannedListData(List<PromotedListGridItem.PromotedListGridItemConfig> var1) {
      this.mCannedListData.clear();
      this.mCannedListData.addAll(var1);
      this.notifyDataSetChanged();
   }

   public void addFillerData(List<Document> var1) {
      List var2 = this.mUsedDocuments;
      this.dedupeDocuments(var2, var1);
      ArrayList var3 = Lists.newArrayList();
      ArrayList var4 = Lists.newArrayList();
      separateDocumentsByPromos(var1, var3, var4);
      List var5 = this.mDocumentsWithPromo;
      this.dedupeDocuments(var5, var3);
      List var6 = this.mDocumentsWithoutPromo;
      this.dedupeDocuments(var6, var4);
      this.mDocumentsWithPromo.addAll(var3);
      this.mDocumentsWithoutPromo.addAll(var4);
      this.notifyDataSetChanged();
   }

   public void addPromoData(List<Document> var1) {
      List var2 = this.mDocumentsWithPromo;
      this.dedupeDocuments(var1, var2);
      this.mDocumentsWithPromo.addAll(0, var1);
      this.mReadyToSequence = (boolean)1;
      this.notifyDataSetChanged();
   }

   public void dataFullyLoaded() {
      this.mFillerItems.clear();
      ArrayList var1 = Lists.newArrayList();
      List var2 = this.mDocumentsWithPromo;
      var1.addAll(var2);
      List var4 = this.mDocumentsWithoutPromo;
      var1.addAll(var4);

      for(int var6 = this.mSequencedItems.size() + -1; var6 >= 0; var6 += -1) {
         UnevenGridAdapter.UnevenGridItem var7 = (UnevenGridAdapter.UnevenGridItem)this.mSequencedItems.get(var6);
         UnevenGridItemType var8 = var7.getGridItemType();
         UnevenGridItemType var9 = UnevenGridItemType.PLACEHOLDER;
         if(var8 == var9 && var7.getCellWidth() >= 2) {
            int var10 = var7.getCellHeight();
            int var11 = var7.getCellWidth();
            int var12 = var10 * var11 / 2;
            ArrayList var13 = Lists.newArrayList();

            for(int var14 = 0; var14 < var12 && var1.size() != 0; ++var14) {
               Context var18 = this.mContext;
               NavigationManager var19 = this.mNavigationManager;
               BitmapLoader var20 = this.mBitmapLoader;
               Document var21 = (Document)var1.remove(0);
               String var22 = this.mCurrentPageUrl;
               DocumentGridItem var23 = DocumentGridItem.createSmallPromo(var18, var19, var20, var21, var22);
               var13.add(var23);
            }

            this.mSequencedItems.remove(var6);
            int var16 = this.mSequencedItems.size();
            if(var6 >= var16) {
               this.mSequencedItems.addAll(var13);
            } else {
               this.mSequencedItems.addAll(var6, var13);
            }
         }
      }

      Iterator var26 = var1.iterator();

      while(var26.hasNext()) {
         Document var27 = (Document)var26.next();
         List var28 = this.mFillerItems;
         Context var29 = this.mContext;
         NavigationManager var30 = this.mNavigationManager;
         BitmapLoader var31 = this.mBitmapLoader;
         String var32 = this.mCurrentPageUrl;
         DocumentGridItem var33 = DocumentGridItem.createSmallPromo(var29, var30, var31, var27, var32);
         var28.add(var33);
      }

      UnevenGridAdapter var35 = this.mAdapter;
      List var36 = this.mSequencedItems;
      List var37 = this.mFillerItems;
      var35.setData(var36, var37);
   }

   public void forceDataDisplay() {
      List var1 = this.mSequencedItems;
      Context var2 = this.mContext;
      NavigationManager var3 = this.mNavigationManager;
      DfeToc var4 = this.mToc;
      CorpusGridItem var5 = new CorpusGridItem(var2, var3, var4);
      var1.add(var5);
      UnevenGridAdapter var7 = this.mAdapter;
      List var8 = this.mSequencedItems;
      List var9 = this.mFillerItems;
      var7.setData(var8, var9);
   }

   public UnevenGridAdapter getAdapter() {
      return this.mAdapter;
   }

   public List<PromotedListGridItem.PromotedListGridItemConfig> getCannedListData() {
      return this.mCannedListData;
   }

   public void onDestroy() {
      this.mDocumentsWithoutPromo.clear();
      this.mDocumentsWithPromo.clear();
      this.mUsedDocuments.clear();
      this.mCannedListData.clear();
      this.mSequencedItems.clear();
      this.mAdapter.onDestroy();
   }

   public void setImagesEnabled(boolean var1) {
      this.mAdapter.setImagesEnabled(var1);
   }

   private static class PlaceholderCell implements UnevenGridAdapter.UnevenGridItem {

      private final int mCellHeight;
      private final int mCellWidth;


      public PlaceholderCell(int var1, int var2) {
         this.mCellWidth = var1;
         this.mCellHeight = var2;
      }

      public void bind(ViewGroup var1, boolean var2, boolean var3) {}

      public int getCellHeight() {
         return this.mCellHeight;
      }

      public int getCellWidth() {
         return this.mCellWidth;
      }

      public UnevenGridItemType getGridItemType() {
         return UnevenGridItemType.PLACEHOLDER;
      }

      public int getLayoutId() {
         return 2130968681;
      }
   }

   // $FF: synthetic class
   static class 1 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$google$android$finsky$adapters$UnevenGridItemType = new int[UnevenGridItemType.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$google$android$finsky$adapters$UnevenGridItemType;
            int var1 = UnevenGridItemType.DOCUMENT_PROMO_4x2.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var19) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$google$android$finsky$adapters$UnevenGridItemType;
            int var3 = UnevenGridItemType.DOCUMENT_SMALL_2X1.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var18) {
            ;
         }

         try {
            int[] var4 = $SwitchMap$com$google$android$finsky$adapters$UnevenGridItemType;
            int var5 = UnevenGridItemType.DOCUMENT_SQUARE_2X2.ordinal();
            var4[var5] = 3;
         } catch (NoSuchFieldError var17) {
            ;
         }

         try {
            int[] var6 = $SwitchMap$com$google$android$finsky$adapters$UnevenGridItemType;
            int var7 = UnevenGridItemType.PROMOTED_LIST_LINK_2X1.ordinal();
            var6[var7] = 4;
         } catch (NoSuchFieldError var16) {
            ;
         }

         try {
            int[] var8 = $SwitchMap$com$google$android$finsky$adapters$UnevenGridItemType;
            int var9 = UnevenGridItemType.CORPORA_LIST_2XN.ordinal();
            var8[var9] = 5;
         } catch (NoSuchFieldError var15) {
            ;
         }
      }
   }
}
