package com.android.settings.flipfont;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.util.Log;
import com.android.settings.flipfont.Typeface;
import com.android.settings.flipfont.TypefaceParser;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class TypefaceFinder {

   public static final String DEFAULT_FONT_VALUE = "default";
   static final String FONT_ASSET_DIR = "xml";
   static final String FONT_DIRECTORY = "fonts/";
   static final String FONT_EXTENSION = ".ttf";
   private static final String TAG = "TypefaceFinder";
   public Context context;
   public final List<Typeface> mTypefaces;


   public TypefaceFinder() {
      ArrayList var1 = new ArrayList();
      this.mTypefaces = var1;
   }

   public Typeface findMatchingTypeface(String var1) {
      Typeface var2 = null;
      int var3 = 0;

      while(true) {
         int var4 = this.mTypefaces.size();
         if(var3 >= var4) {
            break;
         }

         var2 = (Typeface)this.mTypefaces.get(var3);
         if(var2.getTypefaceFilename().equals(var1)) {
            break;
         }

         ++var3;
      }

      return var2;
   }

   public boolean findTypefaces(AssetManager var1, String var2) {
      String[] var3;
      boolean var13;
      try {
         var3 = var1.list("xml");
      } catch (Exception var18) {
         var13 = false;
         return var13;
      }

      String[] var4 = var3;
      int var5 = 0;

      while(true) {
         int var6 = var4.length;
         if(var5 >= var6) {
            var13 = true;
            return var13;
         }

         try {
            StringBuilder var7 = (new StringBuilder()).append("xml/");
            String var8 = var4[var5];
            String var9 = var7.append(var8).toString();
            InputStream var10 = var1.open(var9);
            String var11 = var4[var5];
            this.parseTypefaceXml(var11, var10, var2);
         } catch (Exception var17) {
            String var15 = "Not possible to open, continue to next file, " + var17;
            int var16 = Log.v("TypefaceFinder", var15);
         }

         ++var5;
      }
   }

   public void getMonospaceEntries(Vector var1, Vector var2) {
      String var3 = (String)this.context.getResources().getText(2131232057);
      var1.add(var3);
      boolean var5 = var2.add("default");
      int var6 = 0;

      while(true) {
         int var7 = this.mTypefaces.size();
         if(var6 >= var7) {
            return;
         }

         String var8 = ((Typeface)this.mTypefaces.get(var6)).getMonospaceName();
         if(var8 != null) {
            var1.add(var8);
            String var10 = ((Typeface)this.mTypefaces.get(var6)).getTypefaceFilename();
            var2.add(var10);
         }

         ++var6;
      }
   }

   public void getSansEntries(PackageManager var1, Vector var2, Vector var3, Vector var4) {
      String var5 = (String)this.context.getResources().getText(2131232057);
      boolean var8 = var2.add(var5);
      String var10 = "default";
      var3.add(var10);
      String var13 = "";
      var4.add(var13);
      List var15 = this.mTypefaces;
      TypefaceFinder.TypefaceSortByName var16 = new TypefaceFinder.TypefaceSortByName();
      Collections.sort(var15, var16);
      int var19 = 0;

      while(true) {
         int var20 = this.mTypefaces.size();
         if(var19 >= var20) {
            return;
         }

         String var21 = ((Typeface)this.mTypefaces.get(var19)).getSansName();
         if(var21 != null) {
            AssetManager var22 = this.context.getAssets();
            String var23 = ((Typeface)this.mTypefaces.get(var19)).getTypefaceFilename();
            int var24 = var23.lastIndexOf("/");
            int var25 = var23.lastIndexOf(".");
            if(var25 < 0) {
               var25 = var23.length();
            }

            int var26 = var24 + 1;
            String var27 = var23.substring(var26, var25);
            String var28 = var27.replaceAll(" ", "");
            String var29 = ((Typeface)this.mTypefaces.get(var19)).getFontPackageName();

            try {
               AssetManager var32 = var1.getResourcesForApplication(var29).getAssets();
               String var33 = "fonts/" + var27 + ".ttf";
               android.graphics.Typeface.createFromAsset(var32, var33);
               boolean var37 = var2.add(var21);
               String var38 = ((Typeface)this.mTypefaces.get(var19)).getTypefaceFilename();
               boolean var41 = var3.add(var38);
               String var42 = ((Typeface)this.mTypefaces.get(var19)).getFontPackageName();
               boolean var45 = var4.add(var42);
            } catch (Exception var49) {
               String var47 = "getSansEntries - Typeface.createFromAsset caused an exception for - fonts/" + var27 + ".ttf";
               int var48 = Log.v("FlipFont", var47);
               var49.printStackTrace();
            }
         }

         ++var19;
      }
   }

   public void getSerifEntries(Vector var1, Vector var2) {
      String var3 = (String)this.context.getResources().getText(2131232057);
      var1.add(var3);
      boolean var5 = var2.add("default");
      int var6 = 0;

      while(true) {
         int var7 = this.mTypefaces.size();
         if(var6 >= var7) {
            return;
         }

         String var8 = ((Typeface)this.mTypefaces.get(var6)).getSerifName();
         if(var8 != null) {
            var1.add(var8);
            String var10 = ((Typeface)this.mTypefaces.get(var6)).getTypefaceFilename();
            var2.add(var10);
         }

         ++var6;
      }
   }

   public void parseTypefaceXml(String var1, InputStream var2, String var3) {
      try {
         XMLReader var4 = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
         TypefaceParser var5 = new TypefaceParser();
         var4.setContentHandler(var5);
         InputSource var6 = new InputSource(var2);
         var4.parse(var6);
         Typeface var7 = var5.getParsedData();
         var7.setTypefaceFilename(var1);
         var7.setFontPackageName(var3);
         this.mTypefaces.add(var7);
      } catch (Exception var12) {
         String var10 = "File parsing is not possible, omit this typeface, " + var12;
         int var11 = Log.v("TypefaceFinder", var10);
      }
   }

   public class TypefaceSortByName implements Comparator<Typeface> {

      public TypefaceSortByName() {}

      public int compare(Typeface var1, Typeface var2) {
         String var3 = var1.getName();
         String var4 = var2.getName();
         return var3.compareTo(var4);
      }
   }
}
