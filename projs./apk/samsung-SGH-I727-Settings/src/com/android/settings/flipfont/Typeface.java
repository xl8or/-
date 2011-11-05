package com.android.settings.flipfont;

import com.android.settings.flipfont.TypefaceFile;
import java.util.ArrayList;
import java.util.List;

public class Typeface {

   private String mFontPackageName = null;
   public final List<TypefaceFile> mMonospaceFonts;
   private String mName = null;
   public final List<TypefaceFile> mSansFonts;
   public final List<TypefaceFile> mSerifFonts;
   private String mTypefaceFilename = null;


   public Typeface() {
      ArrayList var1 = new ArrayList();
      this.mSansFonts = var1;
      ArrayList var2 = new ArrayList();
      this.mSerifFonts = var2;
      ArrayList var3 = new ArrayList();
      this.mMonospaceFonts = var3;
   }

   public String getFontPackageName() {
      return this.mFontPackageName;
   }

   public String getMonospaceName() {
      String var1;
      if(this.mMonospaceFonts.isEmpty()) {
         var1 = null;
      } else {
         var1 = this.mName;
      }

      return var1;
   }

   public String getName() {
      return this.mName;
   }

   public String getSansName() {
      String var1;
      if(this.mSansFonts.isEmpty()) {
         var1 = null;
      } else {
         var1 = this.mName;
      }

      return var1;
   }

   public String getSerifName() {
      String var1;
      if(this.mSerifFonts.isEmpty()) {
         var1 = null;
      } else {
         var1 = this.mName;
      }

      return var1;
   }

   public String getTypefaceFilename() {
      return this.mTypefaceFilename;
   }

   public void setFontPackageName(String var1) {
      this.mFontPackageName = var1;
   }

   public void setName(String var1) {
      this.mName = var1;
   }

   public void setTypefaceFilename(String var1) {
      this.mTypefaceFilename = var1;
   }
}
