package com.google.android.finsky.adapters;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.finsky.adapters.UnevenGridAdapter;
import com.google.android.finsky.adapters.UnevenGridItemType;
import com.google.android.finsky.navigationmanager.NavigationManager;

public class PromotedListGridItem implements UnevenGridAdapter.UnevenGridItem {

   public static final int HEIGHT_IN_GRID_CELLS = 1;
   public static final int WIDTH_IN_GRID_CELLS = 2;
   private final PromotedListGridItem.PromotedListGridItemConfig mConfig;
   private final Context mContext;
   private final NavigationManager mNavigationManager;


   public PromotedListGridItem(Context var1, NavigationManager var2, PromotedListGridItem.PromotedListGridItemConfig var3) {
      this.mContext = var1;
      this.mNavigationManager = var2;
      this.mConfig = var3;
   }

   private void setOnClickListener(View var1) {
      PromotedListGridItem.1 var2 = new PromotedListGridItem.1();
      var1.setOnClickListener(var2);
   }

   public void bind(ViewGroup var1, boolean var2, boolean var3) {
      if(var1.getTag() == null) {
         PromotedListGridItem.ViewHolder var4 = new PromotedListGridItem.ViewHolder((PromotedListGridItem.1)null);
         ImageView var5 = (ImageView)var1.findViewById(2131755268);
         var4.imageView = var5;
         TextView var6 = (TextView)var1.findViewById(2131755020);
         var4.title = var6;
         View var7 = var1.findViewById(2131755120);
         var4.accessibilityOverlay = var7;
         var1.setTag(var4);
      }

      PromotedListGridItem.ViewHolder var8 = (PromotedListGridItem.ViewHolder)var1.getTag();
      int[] var9 = PromotedListGridItem.2.$SwitchMap$com$google$android$finsky$adapters$PromotedListGridItem$PromotedItemType;
      int var10 = this.mConfig.itemType.ordinal();
      switch(var9[var10]) {
      case 1:
         var8.imageView.setImageResource(2130837619);
         break;
      case 2:
         var8.imageView.setImageResource(2130837622);
         break;
      case 3:
         var8.imageView.setImageResource(2130837626);
         break;
      case 4:
         var8.imageView.setImageResource(2130837624);
         break;
      case 5:
         var8.imageView.setImageResource(2130837618);
         break;
      case 6:
         var8.imageView.setImageResource(2130837625);
      }

      PromotedListGridItem.PromotedItemType var11 = this.mConfig.itemType;
      PromotedListGridItem.PromotedItemType var12 = PromotedListGridItem.PromotedItemType.BOOKS_NYT;
      if(var11 != var12) {
         TextView var13 = var8.title;
         String var14 = this.mConfig.title;
         var13.setText(var14);
      }

      View var15 = var8.accessibilityOverlay;
      String var16 = this.mConfig.title;
      var15.setContentDescription(var16);
      View var17 = var8.accessibilityOverlay;
      this.setOnClickListener(var17);
   }

   public int getCellHeight() {
      return 1;
   }

   public int getCellWidth() {
      return 2;
   }

   public UnevenGridItemType getGridItemType() {
      return UnevenGridItemType.PROMOTED_LIST_LINK_2X1;
   }

   public int getLayoutId() {
      return 2130968685;
   }

   public static class PromotedListGridItemConfig implements Parcelable {

      public static Creator<PromotedListGridItem.PromotedListGridItemConfig> CREATOR = new PromotedListGridItem.PromotedListGridItemConfig.1();
      public final PromotedListGridItem.PromotedItemType itemType;
      public final String title;
      public final String url;


      public PromotedListGridItemConfig(PromotedListGridItem.PromotedItemType var1, String var2, String var3) {
         this.itemType = var1;
         this.url = var2;
         this.title = var3;
      }

      public int describeContents() {
         return 0;
      }

      public void writeToParcel(Parcel var1, int var2) {
         String var3 = this.itemType.toString();
         var1.writeString(var3);
         String var4 = this.url;
         var1.writeString(var4);
         String var5 = this.title;
         var1.writeString(var5);
      }

      static class 1 implements Creator<PromotedListGridItem.PromotedListGridItemConfig> {

         1() {}

         public PromotedListGridItem.PromotedListGridItemConfig createFromParcel(Parcel var1) {
            PromotedListGridItem.PromotedItemType var2 = PromotedListGridItem.PromotedItemType.valueOf(var1.readString());
            String var3 = var1.readString();
            String var4 = var1.readString();
            return new PromotedListGridItem.PromotedListGridItemConfig(var2, var3, var4);
         }

         public PromotedListGridItem.PromotedListGridItemConfig[] newArray(int var1) {
            return new PromotedListGridItem.PromotedListGridItemConfig[var1];
         }
      }
   }

   public static enum PromotedItemType {

      // $FF: synthetic field
      private static final PromotedListGridItem.PromotedItemType[] $VALUES;
      APPS_EDITORS_CHOICE("APPS_EDITORS_CHOICE", 4),
      APPS_PARTNER_CHANNEL("APPS_PARTNER_CHANNEL", 3),
      APPS_STAFF_PICKS("APPS_STAFF_PICKS", 5),
      BOOKS_NYT("BOOKS_NYT", 1),
      GAMES("GAMES", 0),
      MOVIES_STAFF_PICKS("MOVIES_STAFF_PICKS", 2);


      static {
         PromotedListGridItem.PromotedItemType[] var0 = new PromotedListGridItem.PromotedItemType[6];
         PromotedListGridItem.PromotedItemType var1 = GAMES;
         var0[0] = var1;
         PromotedListGridItem.PromotedItemType var2 = BOOKS_NYT;
         var0[1] = var2;
         PromotedListGridItem.PromotedItemType var3 = MOVIES_STAFF_PICKS;
         var0[2] = var3;
         PromotedListGridItem.PromotedItemType var4 = APPS_PARTNER_CHANNEL;
         var0[3] = var4;
         PromotedListGridItem.PromotedItemType var5 = APPS_EDITORS_CHOICE;
         var0[4] = var5;
         PromotedListGridItem.PromotedItemType var6 = APPS_STAFF_PICKS;
         var0[5] = var6;
         $VALUES = var0;
      }

      private PromotedItemType(String var1, int var2) {}
   }

   // $FF: synthetic class
   static class 2 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$google$android$finsky$adapters$PromotedListGridItem$PromotedItemType = new int[PromotedListGridItem.PromotedItemType.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$google$android$finsky$adapters$PromotedListGridItem$PromotedItemType;
            int var1 = PromotedListGridItem.PromotedItemType.GAMES.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var23) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$google$android$finsky$adapters$PromotedListGridItem$PromotedItemType;
            int var3 = PromotedListGridItem.PromotedItemType.BOOKS_NYT.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var22) {
            ;
         }

         try {
            int[] var4 = $SwitchMap$com$google$android$finsky$adapters$PromotedListGridItem$PromotedItemType;
            int var5 = PromotedListGridItem.PromotedItemType.MOVIES_STAFF_PICKS.ordinal();
            var4[var5] = 3;
         } catch (NoSuchFieldError var21) {
            ;
         }

         try {
            int[] var6 = $SwitchMap$com$google$android$finsky$adapters$PromotedListGridItem$PromotedItemType;
            int var7 = PromotedListGridItem.PromotedItemType.APPS_PARTNER_CHANNEL.ordinal();
            var6[var7] = 4;
         } catch (NoSuchFieldError var20) {
            ;
         }

         try {
            int[] var8 = $SwitchMap$com$google$android$finsky$adapters$PromotedListGridItem$PromotedItemType;
            int var9 = PromotedListGridItem.PromotedItemType.APPS_EDITORS_CHOICE.ordinal();
            var8[var9] = 5;
         } catch (NoSuchFieldError var19) {
            ;
         }

         try {
            int[] var10 = $SwitchMap$com$google$android$finsky$adapters$PromotedListGridItem$PromotedItemType;
            int var11 = PromotedListGridItem.PromotedItemType.APPS_STAFF_PICKS.ordinal();
            var10[var11] = 6;
         } catch (NoSuchFieldError var18) {
            ;
         }
      }
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(View var1) {
         byte var2 = -1;
         int[] var3 = PromotedListGridItem.2.$SwitchMap$com$google$android$finsky$adapters$PromotedListGridItem$PromotedItemType;
         int var4 = PromotedListGridItem.this.mConfig.itemType.ordinal();
         switch(var3[var4]) {
         case 1:
         case 4:
            var2 = 3;
            break;
         case 2:
            var2 = 1;
            break;
         case 3:
            var2 = 4;
         }

         NavigationManager var5 = PromotedListGridItem.this.mNavigationManager;
         String var6 = PromotedListGridItem.this.mConfig.url;
         String var7 = PromotedListGridItem.this.mConfig.title;
         var5.goBrowse(var6, var7, var2, (String)null);
      }
   }

   private static class ViewHolder {

      View accessibilityOverlay;
      ImageView imageView;
      TextView title;


      private ViewHolder() {}

      // $FF: synthetic method
      ViewHolder(PromotedListGridItem.1 var1) {
         this();
      }
   }
}
