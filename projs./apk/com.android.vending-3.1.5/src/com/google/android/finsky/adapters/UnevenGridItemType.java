package com.google.android.finsky.adapters;


public enum UnevenGridItemType {

   // $FF: synthetic field
   private static final UnevenGridItemType[] $VALUES;
   CORPORA_LIST_2XN("CORPORA_LIST_2XN", 3),
   DOCUMENT_PROMO_4x2("DOCUMENT_PROMO_4x2", 0),
   DOCUMENT_SMALL_2X1("DOCUMENT_SMALL_2X1", 2),
   DOCUMENT_SQUARE_2X2("DOCUMENT_SQUARE_2X2", 1),
   PLACEHOLDER("PLACEHOLDER", 5),
   PROMOTED_LIST_LINK_2X1("PROMOTED_LIST_LINK_2X1", 4);


   static {
      UnevenGridItemType[] var0 = new UnevenGridItemType[6];
      UnevenGridItemType var1 = DOCUMENT_PROMO_4x2;
      var0[0] = var1;
      UnevenGridItemType var2 = DOCUMENT_SQUARE_2X2;
      var0[1] = var2;
      UnevenGridItemType var3 = DOCUMENT_SMALL_2X1;
      var0[2] = var3;
      UnevenGridItemType var4 = CORPORA_LIST_2XN;
      var0[3] = var4;
      UnevenGridItemType var5 = PROMOTED_LIST_LINK_2X1;
      var0[4] = var5;
      UnevenGridItemType var6 = PLACEHOLDER;
      var0[5] = var6;
      $VALUES = var0;
   }

   private UnevenGridItemType(String var1, int var2) {}
}
