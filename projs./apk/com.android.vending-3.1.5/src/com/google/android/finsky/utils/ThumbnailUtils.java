package com.google.android.finsky.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.widget.ImageView;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.remoting.protos.Doc;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ThumbnailUtils {

   private static final int FADE_IN_DURATION = 250;
   private static HashMap<Integer, Bitmap> sDefaultIcons;
   private static final Comparator<Doc.Image> sImageWidthComparator = new ThumbnailUtils.1();


   public ThumbnailUtils() {}

   private static String getBestImageUrl(List<Doc.Image> var0, int var1) {
      Comparator var2 = sImageWidthComparator;
      Collections.sort(var0, var2);
      Iterator var3 = var0.iterator();

      String var5;
      while(true) {
         if(var3.hasNext()) {
            Doc.Image var4 = (Doc.Image)var3.next();
            if(var4.getDimension() == null || var4.getDimension().getWidth() < var1) {
               continue;
            }

            var5 = var4.getImageUrl();
            break;
         }

         if(var0.size() > 0) {
            int var6 = var0.size() + -1;
            var5 = ((Doc.Image)var0.get(var6)).getImageUrl();
         } else {
            var5 = null;
         }
         break;
      }

      return var5;
   }

   public static Bitmap getDefaultIcon(int var0, Resources var1) {
      if(sDefaultIcons == null) {
         sDefaultIcons = new HashMap();
         Drawable var2 = var1.getDrawable(2130837608);
         HashMap var3 = sDefaultIcons;
         Integer var4 = Integer.valueOf(3);
         Bitmap var5 = BitmapFactory.decodeResource(var1, 2130837609);
         var3.put(var4, var5);
         HashMap var7 = sDefaultIcons;
         Integer var8 = Integer.valueOf(1);
         Bitmap var9 = BitmapFactory.decodeResource(var1, 2130837610);
         var7.put(var8, var9);
         HashMap var11 = sDefaultIcons;
         Integer var12 = Integer.valueOf(2);
         Bitmap var13 = BitmapFactory.decodeResource(var1, 2130837608);
         var11.put(var12, var13);
         HashMap var15 = sDefaultIcons;
         Integer var16 = Integer.valueOf(4);
         Bitmap var17 = BitmapFactory.decodeResource(var1, 2130837611);
         var15.put(var16, var17);
      }

      HashMap var19 = sDefaultIcons;
      Integer var20 = Integer.valueOf(var0);
      return (Bitmap)var19.get(var20);
   }

   public static String getIconUrlFromDocument(Document var0, int var1) {
      String var2 = getBestImageUrl(var0.getImages(4), var1);
      if(var2 == null) {
         var2 = getBestImageUrl(var0.getImages(0), var1);
      }

      return var2;
   }

   public static String getPromoBitmapUrlFromDocument(Document var0, int var1) {
      return getBestImageUrl(var0.getImages(2), var1);
   }

   public static boolean hasPromoBitmap(Document var0) {
      List var1 = var0.getImages(2);
      boolean var2;
      if(var1 != null && var1.size() > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public static void setImageBitmapWithFade(ImageView var0, Bitmap var1) {
      Resources var2 = var0.getResources();
      BitmapDrawable var3 = new BitmapDrawable(var2, var1);
      var3.setGravity(51);
      setImageDrawableWithFade(var0, var3);
   }

   public static void setImageDrawableWithFade(ImageView var0, Drawable var1) {
      Drawable var2 = var0.getDrawable();
      if(var2 != null) {
         Drawable[] var3 = new Drawable[]{var2, var1};
         TransitionDrawable var4 = new TransitionDrawable(var3);
         var4.setCrossFadeEnabled((boolean)1);
         var0.setImageDrawable(var4);
         var4.startTransition(250);
      } else {
         var0.setImageDrawable(var1);
      }
   }

   static class 1 implements Comparator<Doc.Image> {

      1() {}

      public int compare(Doc.Image var1, Doc.Image var2) {
         byte var3 = -1;
         if(var1.hasDimension()) {
            if(!var2.hasDimension()) {
               var3 = 1;
            } else {
               int var4 = var1.getDimension().getWidth();
               int var5 = var2.getDimension().getWidth();
               if(var4 >= var5) {
                  int var6 = var1.getDimension().getWidth();
                  int var7 = var2.getDimension().getWidth();
                  if(var6 > var7) {
                     var3 = 1;
                  } else {
                     var3 = 0;
                  }
               }
            }
         }

         return var3;
      }
   }
}
