package com.google.wireless.gdata.client;

import com.google.wireless.gdata.client.GDataClient;
import com.google.wireless.gdata.client.GDataParserFactory;
import com.google.wireless.gdata.client.HttpException;
import com.google.wireless.gdata.client.QueryParams;
import com.google.wireless.gdata.data.Entry;
import com.google.wireless.gdata.data.MediaEntry;
import com.google.wireless.gdata.data.StringUtils;
import com.google.wireless.gdata.parser.GDataParser;
import com.google.wireless.gdata.parser.ParseException;
import com.google.wireless.gdata.serializer.GDataSerializer;
import java.io.IOException;
import java.io.InputStream;

public abstract class GDataServiceClient {

   private final GDataClient gDataClient;
   private final GDataParserFactory gDataParserFactory;


   public GDataServiceClient(GDataClient var1, GDataParserFactory var2) {
      this.gDataClient = var1;
      this.gDataParserFactory = var2;
   }

   private Entry parseEntry(Class param1, InputStream param2) throws ParseException, IOException {
      // $FF: Couldn't be decompiled
   }

   public Entry createEntry(String var1, String var2, Entry var3) throws ParseException, IOException, HttpException {
      GDataSerializer var4 = this.gDataParserFactory.createSerializer(var3);
      InputStream var5 = this.gDataClient.createEntry(var1, var2, var4);
      Class var6 = var3.getClass();
      return this.parseEntry(var6, var5);
   }

   public QueryParams createQueryParams() {
      return this.gDataClient.createQueryParams();
   }

   public void deleteEntry(String var1, String var2) throws IOException, HttpException {
      this.gDataClient.deleteEntry(var1, var2);
   }

   public Entry getEntry(Class var1, String var2, String var3) throws ParseException, IOException, HttpException {
      InputStream var4 = this.getGDataClient().getFeedAsStream(var2, var3);
      return this.parseEntry(var1, var4);
   }

   protected GDataClient getGDataClient() {
      return this.gDataClient;
   }

   protected GDataParserFactory getGDataParserFactory() {
      return this.gDataParserFactory;
   }

   public InputStream getMediaEntryAsStream(String var1, String var2) throws IOException, HttpException {
      return this.gDataClient.getMediaEntryAsStream(var1, var2);
   }

   public GDataParser getParserForFeed(Class var1, String var2, String var3) throws ParseException, IOException, HttpException {
      InputStream var4 = this.gDataClient.getFeedAsStream(var2, var3);
      return this.gDataParserFactory.createParser(var1, var4);
   }

   public abstract String getServiceName();

   public Entry updateEntry(Entry var1, String var2) throws ParseException, IOException, HttpException {
      String var3 = var1.getEditUri();
      if(StringUtils.isEmpty(var3)) {
         throw new ParseException("No edit URI -- cannot update.");
      } else {
         GDataSerializer var4 = this.gDataParserFactory.createSerializer(var1);
         InputStream var5 = this.gDataClient.updateEntry(var3, var2, var4);
         Class var6 = var1.getClass();
         return this.parseEntry(var6, var5);
      }
   }

   public MediaEntry updateMediaEntry(String var1, InputStream var2, String var3, String var4) throws IOException, HttpException, ParseException {
      if(StringUtils.isEmpty(var1)) {
         throw new IllegalArgumentException("No edit URI -- cannot update.");
      } else {
         InputStream var5 = this.gDataClient.updateMediaEntry(var1, var4, var2, var3);
         return (MediaEntry)this.parseEntry(MediaEntry.class, var5);
      }
   }
}
