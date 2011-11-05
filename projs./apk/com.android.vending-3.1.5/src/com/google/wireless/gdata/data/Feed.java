package com.google.wireless.gdata.data;


public class Feed {

   private String category;
   private String categoryScheme;
   private String id;
   private int itemsPerPage;
   private String lastUpdated;
   private int startIndex;
   private String title;
   private int totalResults;


   public Feed() {}

   public String getCategory() {
      return this.category;
   }

   public String getCategoryScheme() {
      return this.categoryScheme;
   }

   public String getId() {
      return this.id;
   }

   public int getItemsPerPage() {
      return this.itemsPerPage;
   }

   public String getLastUpdated() {
      return this.lastUpdated;
   }

   public int getStartIndex() {
      return this.startIndex;
   }

   public String getTitle() {
      return this.title;
   }

   public int getTotalResults() {
      return this.totalResults;
   }

   public void setCategory(String var1) {
      this.category = var1;
   }

   public void setCategoryScheme(String var1) {
      this.categoryScheme = var1;
   }

   public void setId(String var1) {
      this.id = var1;
   }

   public void setItemsPerPage(int var1) {
      this.itemsPerPage = var1;
   }

   public void setLastUpdated(String var1) {
      this.lastUpdated = var1;
   }

   public void setStartIndex(int var1) {
      this.startIndex = var1;
   }

   public void setTitle(String var1) {
      this.title = var1;
   }

   public void setTotalResults(int var1) {
      this.totalResults = var1;
   }
}
