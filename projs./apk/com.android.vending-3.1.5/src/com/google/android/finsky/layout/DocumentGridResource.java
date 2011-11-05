package com.google.android.finsky.layout;

import com.google.android.finsky.layout.CellBasedLayout;

public enum DocumentGridResource implements CellBasedLayout.Item {

   // $FF: synthetic field
   private static final DocumentGridResource[] $VALUES;
   PROMO,
   SMALL,
   SQUARE;
   private final int mCellHeight;
   private final int mCellWidth;
   private final boolean mIsPromoUsed;
   private final int mLayoutResourceId;


   static {
      byte var0 = 0;
      SMALL = new DocumentGridResource("SMALL", 0, 2, 1, 2130968715, (boolean)var0);
      byte var1 = 1;
      byte var2 = 2;
      byte var3 = 2;
      byte var4 = 1;
      SQUARE = new DocumentGridResource("SQUARE", var1, var2, var3, 2130968716, (boolean)var4);
      byte var5 = 2;
      byte var6 = 2;
      byte var7 = 1;
      PROMO = new DocumentGridResource("PROMO", var5, 4, var6, 2130968714, (boolean)var7);
      DocumentGridResource[] var8 = new DocumentGridResource[3];
      DocumentGridResource var9 = SMALL;
      var8[0] = var9;
      DocumentGridResource var10 = SQUARE;
      var8[1] = var10;
      DocumentGridResource var11 = PROMO;
      var8[2] = var11;
      $VALUES = var8;
   }

   private DocumentGridResource(String var1, int var2, int var3, int var4, int var5, boolean var6) {
      this.mCellWidth = var3;
      this.mCellHeight = var4;
      this.mLayoutResourceId = var5;
      this.mIsPromoUsed = var6;
   }

   public int getCellHeight() {
      return this.mCellHeight;
   }

   public int getCellWidth() {
      return this.mCellWidth;
   }

   public int getLayoutId() {
      return this.mLayoutResourceId;
   }

   public boolean isPromoUsed() {
      return this.mIsPromoUsed;
   }
}
