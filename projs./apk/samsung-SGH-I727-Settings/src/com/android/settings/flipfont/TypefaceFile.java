package com.android.settings.flipfont;


public class TypefaceFile {

   private String droidName = null;
   private String fileName = null;


   public TypefaceFile() {}

   public TypefaceFile(String var1, String var2) {
      this.fileName = var1;
      this.droidName = var2;
   }

   public String getDroidName() {
      return this.droidName;
   }

   public String getFileName() {
      return this.fileName;
   }

   public void setDroidName(String var1) {
      this.droidName = var1;
   }

   public void setFileName(String var1) {
      this.fileName = var1;
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder()).append("Filename = ");
      String var2 = this.fileName;
      StringBuilder var3 = var1.append(var2).append("\nDroidname = ");
      String var4 = this.droidName;
      return var3.append(var4).toString();
   }
}
