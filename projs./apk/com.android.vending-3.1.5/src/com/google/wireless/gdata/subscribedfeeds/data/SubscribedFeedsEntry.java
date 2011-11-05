package com.google.wireless.gdata.subscribedfeeds.data;

import com.google.wireless.gdata.data.Entry;
import com.google.wireless.gdata.subscribedfeeds.data.FeedUrl;

public class SubscribedFeedsEntry extends Entry {

   private String clientToken;
   private FeedUrl feedUrl;
   private String routingInfo;


   public SubscribedFeedsEntry() {}

   public void clear() {
      super.clear();
   }

   public String getClientToken() {
      return this.clientToken;
   }

   public String getRoutingInfo() {
      return this.routingInfo;
   }

   public FeedUrl getSubscribedFeed() {
      return this.feedUrl;
   }

   public void setClientToken(String var1) {
      this.clientToken = var1;
   }

   public void setRoutingInfo(String var1) {
      this.routingInfo = var1;
   }

   public void setSubscribedFeed(FeedUrl var1) {
      this.feedUrl = var1;
   }

   public void toString(StringBuffer var1) {
      super.toString(var1);
   }
}
