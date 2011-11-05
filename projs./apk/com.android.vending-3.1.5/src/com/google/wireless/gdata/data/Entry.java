package com.google.wireless.gdata.data;

import com.google.wireless.gdata.data.StringUtils;
import com.google.wireless.gdata.parser.ParseException;

public class Entry {

   private String author = null;
   private String category = null;
   private String categoryScheme = null;
   private String content = null;
   private boolean deleted = 0;
   private String editUri = null;
   private String email = null;
   private String htmlUri = null;
   private String id = null;
   private String publicationDate = null;
   private String summary = null;
   private String title = null;
   private String updateDate = null;


   public Entry() {}

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
      this.author = null;
      this.email = null;
      this.category = null;
      this.categoryScheme = null;
      this.publicationDate = null;
      this.updateDate = null;
      this.deleted = (boolean)0;
   }

   public String getAuthor() {
      return this.author;
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

   public String getEditUri() {
      return this.editUri;
   }

   public String getEmail() {
      return this.email;
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

   public void setCategory(String var1) {
      this.category = var1;
   }

   public void setCategoryScheme(String var1) {
      this.categoryScheme = var1;
   }

   public void setContent(String var1) {
      this.content = var1;
   }

   public void setDeleted(boolean var1) {
      this.deleted = var1;
   }

   public void setEditUri(String var1) {
      this.editUri = var1;
   }

   public void setEmail(String var1) {
      this.email = var1;
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
   }

   public void validate() throws ParseException {}
}
