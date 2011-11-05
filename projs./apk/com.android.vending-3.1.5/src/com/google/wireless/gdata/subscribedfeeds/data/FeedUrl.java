package com.google.wireless.gdata.subscribedfeeds.data;


public class FeedUrl {

   private String authToken;
   private String feed;
   private String service;


   public FeedUrl() {}

   public FeedUrl(String var1, String var2, String var3) {
      this.setFeed(var1);
      this.setService(var2);
      this.setAuthToken(var3);
   }

   public String getAuthToken() {
      return this.authToken;
   }

   public String getFeed() {
      return this.feed;
   }

   public String getService() {
      return this.service;
   }

   public void setAuthToken(String var1) {
      this.authToken = var1;
   }

   public void setFeed(String var1) {
      this.feed = var1;
   }

   public void setService(String var1) {
      this.service = var1;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      this.toString(var1);
      return var1.toString();
   }

   public void toString(StringBuffer var1) {
      StringBuffer var2 = var1.append("FeedUrl");
      StringBuffer var3 = var1.append(" url:");
      String var4 = this.getFeed();
      var3.append(var4);
      StringBuffer var6 = var1.append(" service:");
      String var7 = this.getService();
      var6.append(var7);
      StringBuffer var9 = var1.append(" authToken:");
      String var10 = this.getAuthToken();
      var9.append(var10);
   }
}
