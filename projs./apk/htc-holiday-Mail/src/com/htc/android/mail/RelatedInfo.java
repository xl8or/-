package com.htc.android.mail;


public class RelatedInfo {

   String mCid;
   String mFileName;
   String mFilePath;
   String mMimetype;
   long mPartId;
   int mSize;


   RelatedInfo(String var1, String var2, String var3, long var4) {
      this.mCid = var1;
      this.mFilePath = var2;
      this.mFileName = var3;
      this.mPartId = var4;
   }

   RelatedInfo(String var1, String var2, String var3, long var4, String var6) {
      this.mCid = var1;
      this.mFilePath = var2;
      this.mFileName = var3;
      this.mPartId = var4;
      this.mMimetype = var6;
   }

   public String getCid() {
      return this.mCid;
   }

   public String getFileName() {
      return this.mFileName;
   }

   public String getFilePath() {
      return this.mFilePath;
   }

   public String getMimetype() {
      return this.mMimetype;
   }

   public long getPartId() {
      return this.mPartId;
   }

   public int getSize() {
      return this.mSize;
   }

   public void setCid(String var1) {
      this.mCid = var1;
   }

   public void setFileName(String var1) {
      this.mFileName = var1;
   }

   public void setFilePath(String var1) {
      this.mFilePath = var1;
   }

   public void setMimetype(String var1) {
      this.mMimetype = var1;
   }

   public void setSize(int var1) {
      this.mSize = var1;
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder()).append("");
      String var2 = this.mCid;
      StringBuilder var3 = var1.append(var2).append(",");
      String var4 = this.mFilePath;
      StringBuilder var5 = var3.append(var4).append(",");
      String var6 = this.mFileName;
      StringBuilder var7 = var5.append(var6).append(",");
      long var8 = this.mPartId;
      StringBuilder var10 = var7.append(var8).append(",");
      int var11 = this.mSize;
      StringBuilder var12 = var10.append(var11).append(",");
      String var13 = this.mMimetype;
      return var12.append(var13).toString();
   }
}
