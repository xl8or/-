package com.google.android.finsky.utils;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.finsky.adapters.PromotedListGridItem;
import com.google.android.finsky.api.model.DfeList;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.config.G;
import com.google.android.finsky.remoting.protos.DocList;
import com.google.android.finsky.remoting.protos.Toc;
import com.google.android.finsky.utils.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ElegantFeaturedGridHackIsolator {

   public ElegantFeaturedGridHackIsolator() {}

   private static void addToOutput(PromotedListGridItem.PromotedListGridItemConfig var0, List<PromotedListGridItem.PromotedListGridItemConfig> var1, int var2) {
      if(var0 != null) {
         if(var1.size() < var2) {
            var1.add(var0);
         }
      }
   }

   private static int getBackendFromCorpusUrl(String var0, DfeToc var1) {
      int var2 = -1;
      Iterator var3 = var1.getCorpusList().iterator();

      while(var3.hasNext()) {
         Toc.CorpusMetadata var4 = (Toc.CorpusMetadata)var3.next();
         if(var4.getLandingUrl().equals(var0)) {
            var2 = var4.getBackend();
            break;
         }
      }

      return var2;
   }

   public static List<PromotedListGridItem.PromotedListGridItemConfig> getPromotedItemsForPageFromList(Context var0, String var1, DfeList var2, int var3, DfeToc var4) {
      ArrayList var5 = Lists.newArrayList();
      Iterator var15;
      switch(getBackendFromCorpusUrl(var1, var4)) {
      case 1:
         var15 = var2.getBucketList().iterator();

         while(var15.hasNext()) {
            DocList.Bucket var27 = (DocList.Bucket)var15.next();
            if(var27.getFullContentsUrl().contains("ctr=books_feat_1052")) {
               PromotedListGridItem.PromotedItemType var28 = PromotedListGridItem.PromotedItemType.BOOKS_NYT;
               String var29 = var27.getFullContentsUrl();
               String var30 = var27.getTitle();
               PromotedListGridItem.PromotedListGridItemConfig var31 = new PromotedListGridItem.PromotedListGridItemConfig(var28, var29, var30);
               var5.add(var31);
            }
         }
      case 2:
      default:
         break;
      case 3:
         PromotedListGridItem.PromotedListGridItemConfig var8 = null;
         PromotedListGridItem.PromotedListGridItemConfig var9 = null;
         PromotedListGridItem.PromotedListGridItemConfig var10 = null;
         PromotedListGridItem.PromotedListGridItemConfig var11 = null;
         if(!TextUtils.isEmpty((CharSequence)G.gamesBrowseUrl.get())) {
            PromotedListGridItem.PromotedItemType var12 = PromotedListGridItem.PromotedItemType.GAMES;
            String var13 = (String)G.gamesBrowseUrl.get();
            String var14 = var0.getResources().getString(2131231097);
            var8 = new PromotedListGridItem.PromotedListGridItemConfig(var12, var13, var14);
         }

         var15 = var2.getBucketList().iterator();

         while(var15.hasNext()) {
            DocList.Bucket var16 = (DocList.Bucket)var15.next();
            String var17 = var16.getFullContentsUrl();
            String var18 = var16.getTitle();
            if(var17.contains("ctr=apps_partnerchannel")) {
               PromotedListGridItem.PromotedItemType var19 = PromotedListGridItem.PromotedItemType.APPS_PARTNER_CHANNEL;
               var9 = new PromotedListGridItem.PromotedListGridItemConfig(var19, var17, var18);
            } else if(var17.contains("ctr=apps_editors_choice")) {
               PromotedListGridItem.PromotedItemType var20 = PromotedListGridItem.PromotedItemType.APPS_EDITORS_CHOICE;
               var10 = new PromotedListGridItem.PromotedListGridItemConfig(var20, var17, var18);
            } else if(var17.contains("ctr=apps_badge_editorschoice")) {
               PromotedListGridItem.PromotedItemType var21 = PromotedListGridItem.PromotedItemType.APPS_EDITORS_CHOICE;
               var10 = new PromotedListGridItem.PromotedListGridItemConfig(var21, var17, var18);
            } else if(var17.contains("ctr=apps_featured")) {
               PromotedListGridItem.PromotedItemType var22 = PromotedListGridItem.PromotedItemType.APPS_STAFF_PICKS;
               var11 = new PromotedListGridItem.PromotedListGridItemConfig(var22, var17, var18);
            }
         }

         addToOutput(var8, var5, var3);
         addToOutput(var11, var5, var3);
         addToOutput(var10, var5, var3);
         addToOutput(var9, var5, var3);
         break;
      case 4:
         var15 = var2.getBucketList().iterator();

         while(var15.hasNext()) {
            DocList.Bucket var33 = (DocList.Bucket)var15.next();
            if(var33.getFullContentsUrl().contains("ctr=movies_featured")) {
               PromotedListGridItem.PromotedItemType var34 = PromotedListGridItem.PromotedItemType.MOVIES_STAFF_PICKS;
               String var35 = var33.getFullContentsUrl();
               String var36 = var33.getTitle();
               PromotedListGridItem.PromotedListGridItemConfig var37 = new PromotedListGridItem.PromotedListGridItemConfig(var34, var35, var36);
               var5.add(var37);
               break;
            }
         }
      }

      return var5;
   }
}
