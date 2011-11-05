package com.google.wireless.gdata2.data;

import com.google.wireless.gdata2.data.StringUtils;
import com.google.wireless.gdata2.data.batch.BatchInfo;
import com.google.wireless.gdata2.parser.ParseException;

public class Entry {

   private String author = null;
   private BatchInfo batchInfo = null;
   private String category = null;
   private String categoryScheme = null;
   private String content = null;
   private String contentSource = null;
   private String contentType = null;
   private boolean deleted = 0;
   private String eTagValue = null;
   private String editUri = null;
   private String email = null;
   private String fields = null;
   private String htmlUri = null;
   private String id = null;
   private String publicationDate = null;
   private String summary = null;
   private String title = null;
   private String updateDate = null;


   public Entry() {}

   public Entry(Entry var1) {
      String var2 = var1.id;
      this.id = var2;
      String var3 = var1.title;
      this.title = var3;
      String var4 = var1.editUri;
      this.editUri = var4;
      String var5 = var1.htmlUri;
      this.htmlUri = var5;
      String var6 = var1.summary;
      this.summary = var6;
      String var7 = var1.content;
      this.content = var7;
      String var8 = var1.author;
      this.author = var8;
      String var9 = var1.email;
      this.email = var9;
      String var10 = var1.category;
      this.category = var10;
      String var11 = var1.categoryScheme;
      this.categoryScheme = var11;
      String var12 = var1.publicationDate;
      this.publicationDate = var12;
      String var13 = var1.updateDate;
      this.updateDate = var13;
      String var14 = var1.eTagValue;
      this.eTagValue = var14;
      boolean var15 = var1.deleted;
      this.deleted = var15;
      String var16 = var1.fields;
      this.fields = var16;
      String var17 = var1.contentSource;
      this.contentSource = var17;
      String var18 = var1.contentType;
      this.contentType = var18;
   }

   protected void appendIfNotNull(StringBuffer var1, String var2, String var3) {
      if(!StringUtils.isEmpty(var3)) {
         var1.append(var2);
         StringBuffer var5 = var1.append(": ");
         var1.append(var3);
         StringBuffer var7 = var1.append("\n");
      }
   }

   public void clear() {
      this.id = null;
      this.title = null;
      this.editUri = null;
      this.htmlUri = null;
      this.summary = null;
      this.content = null;
      this.contentType = null;
      this.contentSource = null;
      this.author = null;
      this.email = null;
      this.category = null;
      this.categoryScheme = null;
      this.publicationDate = null;
      this.updateDate = null;
      this.deleted = (boolean)0;
      this.batchInfo = null;
   }

   public String getAuthor() {
      return this.author;
   }

   public BatchInfo getBatchInfo() {
      return this.batchInfo;
   }

   public String getCategory() {
      return this.category;
   }

   public String getCategoryScheme() {
      return this.categoryScheme;
   }

   public String getContent() {
      return this.content;
   }

   public String getContentSource() {
      return this.contentSource;
   }

   public String getContentType() {
      return this.contentType;
   }

   public String getETag() {
      return this.eTagValue;
   }

   public String getEditUri() {
      return this.editUri;
   }

   public String getEmail() {
      return this.email;
   }

   public String getFields() {
      return this.fields;
   }

   public String getHtmlUri() {
      return this.htmlUri;
   }

   public String getId() {
      return this.id;
   }

   public String getPublicationDate() {
      return this.publicationDate;
   }

   public String getSummary() {
      return this.summary;
   }

   public String getTitle() {
      return this.title;
   }

   public String getUpdateDate() {
      return this.updateDate;
   }

   public boolean isDeleted() {
      return this.deleted;
   }

   public void setAuthor(String var1) {
      this.author = var1;
   }

   public void setBatchInfo(BatchInfo var1) {
      this.batchInfo = var1;
   }

   public void setCategory(String var1) {
      this.category = var1;
   }

   public void setCategoryScheme(String var1) {
      this.categoryScheme = var1;
   }

   public void setContent(String var1) {
      this.content = var1;
   }

   public void setContentSource(String var1) {
      this.contentSource = var1;
   }

   public void setContentType(String var1) {
      this.contentType = var1;
   }

   public void setDeleted(boolean var1) {
      this.deleted = var1;
   }

   public void setETag(String var1) {
      this.eTagValue = var1;
   }

   public void setEditUri(String var1) {
      this.editUri = var1;
   }

   public void setEmail(String var1) {
      this.email = var1;
   }

   public void setFields(String var1) {
      this.fields = var1;
   }

   public void setHtmlUri(String var1) {
      this.htmlUri = var1;
   }

   public void setId(String var1) {
      this.id = var1;
   }

   public void setPublicationDate(String var1) {
      this.publicationDate = var1;
   }

   public void setSummary(String var1) {
      this.summary = var1;
   }

   public void setTitle(String var1) {
      this.title = var1;
   }

   public void setUpdateDate(String var1) {
      this.updateDate = var1;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      this.toString(var1);
      return var1.toString();
   }

   protected void toString(StringBuffer var1) {
      String var2 = this.id;
      this.appendIfNotNull(var1, "ID", var2);
      String var3 = this.title;
      this.appendIfNotNull(var1, "TITLE", var3);
      String var4 = this.editUri;
      this.appendIfNotNull(var1, "EDIT URI", var4);
      String var5 = this.htmlUri;
      this.appendIfNotNull(var1, "HTML URI", var5);
      String var6 = this.summary;
      this.appendIfNotNull(var1, "SUMMARY", var6);
      String var7 = this.content;
      this.appendIfNotNull(var1, "CONTENT", var7);
      String var8 = this.author;
      this.appendIfNotNull(var1, "AUTHOR", var8);
      String var9 = this.category;
      this.appendIfNotNull(var1, "CATEGORY", var9);
      String var10 = this.categoryScheme;
      this.appendIfNotNull(var1, "CATEGORY SCHEME", var10);
      String var11 = this.publicationDate;
      this.appendIfNotNull(var1, "PUBLICATION DATE", var11);
      String var12 = this.updateDate;
      this.appendIfNotNull(var1, "UPDATE DATE", var12);
      String var13 = String.valueOf(this.deleted);
      this.appendIfNotNull(var1, "DELETED", var13);
      String var14 = String.valueOf(this.eTagValue);
      this.appendIfNotNull(var1, "ETAG", var14);
      if(this.batchInfo != null) {
         String var15 = this.batchInfo.toString();
         this.appendIfNotNull(var1, "BATCH", var15);
      }
   }

   public void validate() throws ParseException {}
}
