package com.google.wireless.gdata.calendar.data;

import com.google.wireless.gdata.data.Feed;

public class EventsFeed extends Feed {

   private String timezone = null;


   public EventsFeed() {}

   public String getTimezone() {
      return this.timezone;
   }

   public void setTimezone(String var1) {
      this.timezone = var1;
   }
}
