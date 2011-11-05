package com.google.wireless.gdata2.calendar.client;

import com.google.wireless.gdata2.calendar.data.CalendarEntry;
import com.google.wireless.gdata2.client.GDataClient;
import com.google.wireless.gdata2.client.GDataParserFactory;
import com.google.wireless.gdata2.client.GDataServiceClient;
import com.google.wireless.gdata2.client.HttpException;
import com.google.wireless.gdata2.client.QueryParams;
import com.google.wireless.gdata2.parser.GDataParser;
import com.google.wireless.gdata2.parser.ParseException;
import java.io.IOException;
import java.io.InputStream;

public class CalendarClient extends GDataServiceClient {

   private static final String ALLCALENDARS_BASE_FEED_URL = "https://www.google.com/calendar/feeds/default/allcalendars/full/";
   private static final String CALENDAR_BASE_FEED_URL = "https://www.google.com/calendar/feeds/";
   public static final String PROJECTION_PRIVATE_FULL = "/private/full";
   public static final String SERVICE = "cl";


   public CalendarClient(GDataClient var1, GDataParserFactory var2) {
      super(var1, var2);
   }

   public String getDefaultCalendarUrl(String var1, String var2, QueryParams var3) {
      StringBuilder var4 = (new StringBuilder()).append("https://www.google.com/calendar/feeds/");
      String var5 = this.getGDataClient().encodeUri(var1);
      String var6 = var4.append(var5).toString();
      String var7 = var6 + var2;
      if(var3 != null) {
         var7 = var3.generateQueryUrl(var7);
      }

      return var7;
   }

   public GDataParser getParserForUserCalendars(String var1, String var2) throws ParseException, IOException, HttpException {
      GDataClient var3 = this.getGDataClient();
      String var4 = this.getProtocolVersion();
      InputStream var5 = var3.getFeedAsStream(var1, var2, (String)null, var4);
      return this.getGDataParserFactory().createParser(CalendarEntry.class, var5);
   }

   public String getProtocolVersion() {
      return DEFAULT_GDATA_VERSION;
   }

   public String getServiceName() {
      return "cl";
   }

   public String getUserCalendarsUrl() {
      return "https://www.google.com/calendar/feeds/default/allcalendars/full/";
   }
}
