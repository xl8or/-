package com.google.wireless.gdata.calendar.client;

import com.google.wireless.gdata.calendar.data.CalendarEntry;
import com.google.wireless.gdata.client.GDataClient;
import com.google.wireless.gdata.client.GDataParserFactory;
import com.google.wireless.gdata.client.GDataServiceClient;
import com.google.wireless.gdata.client.HttpException;
import com.google.wireless.gdata.client.QueryParams;
import com.google.wireless.gdata.parser.GDataParser;
import com.google.wireless.gdata.parser.ParseException;
import java.io.IOException;
import java.io.InputStream;

public class CalendarClient extends GDataServiceClient {

   private static final String CALENDAR_BASE_FEED_URL = "http://www.google.com/calendar/feeds/";
   public static final String PROJECTION_PRIVATE_FULL = "/private/full";
   public static final String PROJECTION_PRIVATE_SELF_ATTENDANCE = "/private/full-selfattendance";
   public static final String SERVICE = "cl";


   public CalendarClient(GDataClient var1, GDataParserFactory var2) {
      super(var1, var2);
   }

   public String getDefaultCalendarUrl(String var1, String var2, QueryParams var3) {
      StringBuilder var4 = (new StringBuilder()).append("http://www.google.com/calendar/feeds/");
      String var5 = this.getGDataClient().encodeUri(var1);
      String var6 = var4.append(var5).toString();
      String var7 = var6 + var2;
      if(var3 != null) {
         var7 = var3.generateQueryUrl(var7);
      }

      return var7;
   }

   public GDataParser getParserForUserCalendars(String var1, String var2) throws ParseException, IOException, HttpException {
      InputStream var3 = this.getGDataClient().getFeedAsStream(var1, var2);
      return this.getGDataParserFactory().createParser(CalendarEntry.class, var3);
   }

   public String getServiceName() {
      return "cl";
   }

   public String getUserCalendarsUrl(String var1) {
      StringBuilder var2 = (new StringBuilder()).append("http://www.google.com/calendar/feeds/");
      String var3 = this.getGDataClient().encodeUri(var1);
      return var2.append(var3).toString();
   }
}
