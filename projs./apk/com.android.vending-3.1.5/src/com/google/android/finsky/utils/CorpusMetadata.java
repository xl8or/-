package com.google.android.finsky.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.remoting.protos.Toc;
import java.util.Iterator;
import java.util.List;

public class CorpusMetadata {

   public CorpusMetadata() {}

   public static boolean canRateReview(int var0) {
      boolean var1;
      switch(var0) {
      case 3:
      case 4:
         var1 = true;
         break;
      default:
         var1 = false;
      }

      return var1;
   }

   public static int getBackendForegroundColor(Context var0, int var1) {
      int var2;
      switch(var1) {
      case 1:
         var2 = 2131361830;
         break;
      case 2:
      default:
         var2 = 2131361829;
         break;
      case 3:
         var2 = 2131361829;
         break;
      case 4:
         var2 = 2131361831;
      }

      return var0.getResources().getColor(var2);
   }

   public static int getBackendHintColor(Context var0, int var1) {
      int var2;
      switch(var1) {
      case 1:
         var2 = 2131361826;
         break;
      case 2:
         var2 = 2131361828;
         break;
      case 3:
         var2 = 2131361825;
         break;
      case 4:
         var2 = 2131361827;
         break;
      default:
         var2 = 2131361825;
      }

      return var0.getResources().getColor(var2);
   }

   public static Drawable getBucketEntryBackground(Context var0, int var1) {
      return var0.getResources().getDrawable(2130837546);
   }

   public static int getCorpusCellContentDescriptionResource(int var0) {
      int var2;
      switch(var0) {
      case 1:
      case 2:
      case 3:
         var2 = 2131231083;
         break;
      case 4:
         var2 = 2131231084;
         break;
      default:
         String var1 = "Unsupported backend ID (" + var0 + ")";
         throw new IllegalArgumentException(var1);
      }

      return var2;
   }

   public static int getCorpusDetailsButtonLayoutResource(int var0) {
      int var2;
      switch(var0) {
      case 1:
         var2 = 2130968611;
         break;
      case 2:
      default:
         String var1 = "Unsupported backend ID (" + var0 + ")";
         throw new IllegalArgumentException(var1);
      case 3:
         var2 = 2130968610;
         break;
      case 4:
         var2 = 2130968623;
      }

      return var2;
   }

   public static int getCorpusDetailsLayoutResource(int var0) {
      int var2;
      switch(var0) {
      case 1:
         var2 = 2130968637;
         break;
      case 2:
         var2 = 2130968642;
         break;
      case 3:
         var2 = 2130968636;
         break;
      case 4:
         var2 = 2130968641;
         break;
      default:
         String var1 = "Unsupported backend ID (" + var0 + ")";
         throw new IllegalArgumentException(var1);
      }

      return var2;
   }

   public static String getCorpusMyCollectionDescription(int var0) {
      String var1 = null;
      if(var0 == 0) {
         var0 = 3;
      }

      DfeToc var2 = FinskyApp.get().getToc();
      if(var2 != null) {
         List var3 = var2.getCorpusList();
         if(var3 != null) {
            Iterator var4 = var3.iterator();

            while(var4.hasNext()) {
               Toc.CorpusMetadata var5 = (Toc.CorpusMetadata)var4.next();
               if(var5.hasBackend() && var5.getBackend() == var0 && var5.hasLibraryName()) {
                  var1 = var5.getLibraryName();
                  break;
               }
            }
         }
      }

      return var1;
   }

   public static int getCorpusMyCollectionIcon(int var0) {
      int var1;
      switch(var0) {
      case 1:
         var1 = 2130837573;
         break;
      case 2:
      case 3:
      default:
         var1 = 2130837572;
         break;
      case 4:
         var1 = 2130837574;
      }

      return var1;
   }

   public static int getCorpusSpinnerDrawable(int var0) {
      int var1;
      switch(var0) {
      case 1:
         var1 = 2130837656;
         break;
      case 2:
      case 3:
      default:
         var1 = 2130837655;
         break;
      case 4:
         var1 = 2130837658;
      }

      return var1;
   }

   public static int getCorpusStoreIconResource(int var0) {
      int var1;
      switch(var0) {
      case 1:
         var1 = 2130837566;
         break;
      case 2:
         var1 = 2130837569;
         break;
      case 3:
         var1 = 2130837565;
         break;
      case 4:
         var1 = 2130837568;
         break;
      default:
         var1 = 2130837567;
      }

      return var1;
   }

   public static int getCorpusWatermarkIconBig(int var0) {
      int var1 = 2130837560;
      switch(var0) {
      case 1:
         var1 = 2130837561;
         break;
      case 2:
         var1 = 2130837564;
      case 3:
      default:
         break;
      case 4:
         var1 = 2130837563;
      }

      return var1;
   }

   public static int getCorpusWatermarkIconSmall(int var0) {
      int var1 = 2130837554;
      switch(var0) {
      case 1:
         var1 = 2130837555;
         break;
      case 2:
         var1 = 2130837557;
      case 3:
      default:
         break;
      case 4:
         var1 = 2130837556;
      }

      return var1;
   }

   public static int getDescriptionHeaderStringId(int var0) {
      int var1;
      switch(var0) {
      case 4:
         var1 = 2131230972;
         break;
      default:
         var1 = 2131230974;
      }

      return var1;
   }

   public static int getDetailsIconWidth(Context var0, int var1) {
      int var3;
      switch(var1) {
      case 1:
         var3 = 2131427355;
         break;
      case 2:
      default:
         String var2 = "Unsupported backend ID (" + var1 + ")";
         throw new IllegalArgumentException(var2);
      case 3:
         var3 = 2131427353;
         break;
      case 4:
         var3 = 2131427357;
      }

      return var0.getResources().getDimensionPixelSize(var3);
   }

   public static int getIconResource(int var0) {
      int var1 = 2130837548;
      switch(var0) {
      case 1:
         var1 = 2130837549;
      case 2:
      case 3:
      default:
         break;
      case 4:
         var1 = 2130837586;
      }

      return var1;
   }

   public static int getIconWidth(Context var0, int var1) {
      Resources var2 = var0.getResources();
      int var3;
      switch(var1) {
      case 1:
         var3 = var2.getDimensionPixelSize(2131427341);
         break;
      case 2:
      case 3:
      default:
         var3 = var2.getDimensionPixelSize(2131427343);
         break;
      case 4:
         var3 = var2.getDimensionPixelSize(2131427342);
      }

      return var3;
   }

   public static int getLargeCorpusStoreIconResource(int var0) {
      int var1;
      switch(var0) {
      case 1:
         var1 = 2130903043;
         break;
      case 2:
         var1 = 2130903046;
         break;
      case 3:
         var1 = 2130903042;
         break;
      case 4:
         var1 = 2130903045;
         break;
      default:
         var1 = 2130903044;
      }

      return var1;
   }

   public static int getMoreArrowResource(int var0) {
      int var1 = 2130837633;
      switch(var0) {
      case 1:
         var1 = 2130837634;
      case 2:
      case 3:
      default:
         break;
      case 4:
         var1 = 2130837636;
      }

      return var1;
   }

   public static int getOwnedIconResource(int var0) {
      int var1;
      switch(var0) {
      case 1:
         var1 = 2130837590;
         break;
      case 2:
      case 3:
      default:
         var1 = 2130837589;
         break;
      case 4:
         var1 = 2130837591;
      }

      return var1;
   }

   public static int getOwnedNotLocalIconResource(int var0) {
      int var1;
      switch(var0) {
      case 1:
         var1 = 2130837593;
         break;
      default:
         var1 = 2130837592;
      }

      return var1;
   }

   public static int getRelatedIconWidth(Context var0, int var1) {
      Resources var2 = var0.getResources();
      int var3;
      switch(var1) {
      case 1:
         var3 = var2.getDimensionPixelSize(2131427371);
         break;
      default:
         var3 = var2.getDimensionPixelSize(2131427370);
      }

      return var3;
   }
}
