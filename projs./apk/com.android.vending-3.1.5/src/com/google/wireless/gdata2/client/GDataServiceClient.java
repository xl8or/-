package com.google.wireless.gdata2.client;

import com.google.wireless.gdata2.ConflictDetectedException;
import com.google.wireless.gdata2.client.AuthenticationException;
import com.google.wireless.gdata2.client.BadRequestException;
import com.google.wireless.gdata2.client.ForbiddenException;
import com.google.wireless.gdata2.client.GDataClient;
import com.google.wireless.gdata2.client.GDataParserFactory;
import com.google.wireless.gdata2.client.HttpException;
import com.google.wireless.gdata2.client.PreconditionFailedException;
import com.google.wireless.gdata2.client.QueryParams;
import com.google.wireless.gdata2.client.ResourceGoneException;
import com.google.wireless.gdata2.client.ResourceNotFoundException;
import com.google.wireless.gdata2.client.ResourceNotModifiedException;
import com.google.wireless.gdata2.data.Entry;
import com.google.wireless.gdata2.data.MediaEntry;
import com.google.wireless.gdata2.data.StringUtils;
import com.google.wireless.gdata2.parser.GDataParser;
import com.google.wireless.gdata2.parser.ParseException;
import com.google.wireless.gdata2.serializer.GDataSerializer;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

public abstract class GDataServiceClient {

   protected static String DEFAULT_GDATA_VERSION = "2.0";
   private final GDataClient gDataClient;
   private final GDataParserFactory gDataParserFactory;


   public GDataServiceClient(GDataClient var1, GDataParserFactory var2) {
      this.gDataClient = var1;
      this.gDataParserFactory = var2;
   }

   private Entry parseEntry(Class param1, InputStream param2) throws ParseException, IOException {
      // $FF: Couldn't be decompiled
   }

   protected void convertHttpExceptionForEntryReads(String var1, HttpException var2) throws AuthenticationException, HttpException, ResourceNotFoundException, ResourceNotModifiedException, ForbiddenException {
      switch(var2.getStatusCode()) {
      case 304:
         throw new ResourceNotModifiedException(var1, var2);
      case 401:
         throw new AuthenticationException(var1, var2);
      case 403:
         throw new ForbiddenException(var1, var2);
      case 404:
         throw new ResourceNotFoundException(var1, var2);
      default:
         StringBuilder var3 = (new StringBuilder()).append(var1).append(": ");
         String var4 = var2.getMessage();
         String var5 = var3.append(var4).toString();
         int var6 = var2.getStatusCode();
         InputStream var7 = var2.getResponseStream();
         throw new HttpException(var5, var6, var7);
      }
   }

   protected void convertHttpExceptionForFeedReads(String var1, HttpException var2) throws AuthenticationException, ResourceGoneException, ResourceNotModifiedException, HttpException, ForbiddenException {
      switch(var2.getStatusCode()) {
      case 304:
         throw new ResourceNotModifiedException(var1, var2);
      case 401:
         throw new AuthenticationException(var1, var2);
      case 403:
         throw new ForbiddenException(var1, var2);
      case 410:
         throw new ResourceGoneException(var1, var2);
      default:
         StringBuilder var3 = (new StringBuilder()).append(var1).append(": ");
         String var4 = var2.getMessage();
         String var5 = var3.append(var4).toString();
         int var6 = var2.getStatusCode();
         InputStream var7 = var2.getResponseStream();
         throw new HttpException(var5, var6, var7);
      }
   }

   protected void convertHttpExceptionForWrites(Class var1, String var2, HttpException var3) throws ConflictDetectedException, AuthenticationException, PreconditionFailedException, ParseException, HttpException, IOException, ForbiddenException, ResourceNotFoundException, BadRequestException {
      switch(var3.getStatusCode()) {
      case 400:
         throw new BadRequestException(var2, var3);
      case 401:
         throw new AuthenticationException(var2, var3);
      case 402:
      case 405:
      case 406:
      case 407:
      case 408:
      case 410:
      case 411:
      default:
         StringBuilder var4 = (new StringBuilder()).append(var2).append(": ");
         String var5 = var3.getMessage();
         String var6 = var4.append(var5).toString();
         int var7 = var3.getStatusCode();
         InputStream var8 = var3.getResponseStream();
         throw new HttpException(var6, var7, var8);
      case 403:
         throw new ForbiddenException(var2, var3);
      case 404:
         throw new ResourceNotFoundException(var2, var3);
      case 409:
         Entry var9 = null;
         if(var1 != null && var3.getResponseStream() != null) {
            InputStream var10 = var3.getResponseStream();
            var9 = this.parseEntry(var1, var10);
         }

         throw new ConflictDetectedException(var9);
      case 412:
         throw new PreconditionFailedException(var2, var3);
      }
   }

   protected void convertHttpExceptionsForBatches(String var1, HttpException var2) throws AuthenticationException, ParseException, HttpException, ForbiddenException, BadRequestException {
      switch(var2.getStatusCode()) {
      case 400:
         throw new BadRequestException(var1, var2);
      case 401:
         throw new AuthenticationException(var1, var2);
      case 402:
      default:
         StringBuilder var3 = (new StringBuilder()).append(var1).append(": ");
         String var4 = var2.getMessage();
         String var5 = var3.append(var4).toString();
         int var6 = var2.getStatusCode();
         InputStream var7 = var2.getResponseStream();
         throw new HttpException(var5, var6, var7);
      case 403:
         throw new ForbiddenException(var1, var2);
      }
   }

   public Entry createEntry(String var1, String var2, Entry var3) throws ConflictDetectedException, AuthenticationException, PreconditionFailedException, HttpException, ParseException, IOException, ForbiddenException, BadRequestException {
      GDataSerializer var4 = this.gDataParserFactory.createSerializer(var3);

      Entry var9;
      Entry var10;
      try {
         GDataClient var5 = this.gDataClient;
         String var6 = this.getProtocolVersion();
         InputStream var7 = var5.createEntry(var1, var2, var6, var4);
         Class var8 = var3.getClass();
         var9 = this.parseEntry(var8, var7);
      } catch (HttpException var16) {
         HttpException var11 = var16;

         try {
            Class var12 = var3.getClass();
            String var13 = "Could not create entry " + var1;
            this.convertHttpExceptionForWrites(var12, var13, var11);
         } catch (ResourceNotFoundException var15) {
            throw var16;
         }

         var10 = null;
         return var10;
      }

      var10 = var9;
      return var10;
   }

   public QueryParams createQueryParams() {
      return this.gDataClient.createQueryParams();
   }

   public void deleteEntry(String var1, String var2, String var3) throws AuthenticationException, ConflictDetectedException, PreconditionFailedException, HttpException, ParseException, IOException, ForbiddenException, ResourceNotFoundException, BadRequestException {
      try {
         this.gDataClient.deleteEntry(var1, var2, var3);
      } catch (HttpException var6) {
         if(var6.getStatusCode() != 404) {
            String var5 = "Unable to delete " + var1;
            this.convertHttpExceptionForWrites((Class)null, var5, var6);
         }
      }
   }

   public Entry getEntry(Class var1, String var2, String var3, String var4) throws AuthenticationException, ResourceNotFoundException, ResourceNotModifiedException, HttpException, ParseException, IOException, ForbiddenException {
      Entry var8;
      Entry var9;
      try {
         GDataClient var5 = this.getGDataClient();
         String var6 = this.getProtocolVersion();
         InputStream var7 = var5.getFeedAsStream(var2, var3, var4, var6);
         var8 = this.parseEntry(var1, var7);
      } catch (HttpException var12) {
         String var11 = "Could not fetch entry " + var2;
         this.convertHttpExceptionForEntryReads(var11, var12);
         var9 = null;
         return var9;
      }

      var9 = var8;
      return var9;
   }

   protected GDataClient getGDataClient() {
      return this.gDataClient;
   }

   protected GDataParserFactory getGDataParserFactory() {
      return this.gDataParserFactory;
   }

   public InputStream getMediaEntryAsStream(String var1, String var2, String var3) throws AuthenticationException, ResourceGoneException, ResourceNotModifiedException, ResourceNotFoundException, HttpException, IOException, ForbiddenException {
      InputStream var6;
      InputStream var7;
      try {
         GDataClient var4 = this.gDataClient;
         String var5 = this.getProtocolVersion();
         var6 = var4.getMediaEntryAsStream(var1, var2, var3, var5);
      } catch (HttpException var10) {
         String var9 = "Could not fetch media entry " + var1;
         this.convertHttpExceptionForEntryReads(var9, var10);
         var7 = null;
         return var7;
      }

      var7 = var6;
      return var7;
   }

   public GDataParser getParserForFeed(Class var1, String var2, String var3, String var4) throws AuthenticationException, ResourceGoneException, ResourceNotModifiedException, HttpException, ParseException, IOException, ForbiddenException {
      GDataParser var8;
      GDataParser var9;
      try {
         GDataClient var5 = this.gDataClient;
         String var6 = this.getProtocolVersion();
         InputStream var7 = var5.getFeedAsStream(var2, var3, var4, var6);
         var8 = this.gDataParserFactory.createParser(var1, var7);
      } catch (HttpException var12) {
         String var11 = "Could not fetch feed " + var2;
         this.convertHttpExceptionForFeedReads(var11, var12);
         var9 = null;
         return var9;
      }

      var9 = var8;
      return var9;
   }

   public abstract String getProtocolVersion();

   public abstract String getServiceName();

   public GDataParser submitBatch(Class var1, String var2, String var3, Enumeration var4) throws AuthenticationException, HttpException, ParseException, IOException, ForbiddenException, BadRequestException {
      GDataSerializer var5 = this.gDataParserFactory.createSerializer(var4);

      GDataParser var9;
      GDataParser var10;
      try {
         GDataClient var6 = this.gDataClient;
         String var7 = this.getProtocolVersion();
         InputStream var8 = var6.submitBatch(var2, var3, var7, var5);
         var9 = this.gDataParserFactory.createParser(var1, var8);
      } catch (HttpException var13) {
         String var12 = "Could not submit batch " + var2;
         this.convertHttpExceptionsForBatches(var12, var13);
         var10 = null;
         return var10;
      }

      var10 = var9;
      return var10;
   }

   public Entry updateEntry(Entry var1, Entry var2, String var3) throws AuthenticationException, ConflictDetectedException, PreconditionFailedException, HttpException, ParseException, IOException, ForbiddenException, ResourceNotFoundException, BadRequestException {
      String var4 = var1.getEditUri();
      if(StringUtils.isEmpty(var4)) {
         throw new ParseException("No edit URI -- cannot update.");
      } else {
         GDataSerializer var5 = this.gDataParserFactory.createSerializer(var1, var2);

         Entry var12;
         Entry var13;
         try {
            GDataClient var6 = this.gDataClient;
            String var7 = var1.getETag();
            String var8 = this.getProtocolVersion();
            InputStream var10 = var6.updateEntry(var4, var3, var7, var8, var5);
            Class var11 = var1.getClass();
            var12 = this.parseEntry(var11, var10);
         } catch (HttpException var17) {
            Class var15 = var1.getClass();
            String var16 = "Could not update entry " + var4;
            this.convertHttpExceptionForWrites(var15, var16, var17);
            var13 = null;
            return var13;
         }

         var13 = var12;
         return var13;
      }
   }

   public Entry updateEntry(Entry var1, String var2) throws AuthenticationException, ConflictDetectedException, PreconditionFailedException, HttpException, ParseException, IOException, ForbiddenException, ResourceNotFoundException, BadRequestException {
      return this.updateEntry(var1, (Entry)null, var2);
   }

   public MediaEntry updateMediaEntry(String var1, InputStream var2, String var3, String var4, String var5) throws AuthenticationException, ConflictDetectedException, PreconditionFailedException, HttpException, ParseException, IOException, ForbiddenException, ResourceNotFoundException, BadRequestException {
      if(StringUtils.isEmpty(var1)) {
         throw new IllegalArgumentException("No edit URI -- cannot update.");
      } else {
         MediaEntry var14;
         try {
            GDataClient var6 = this.gDataClient;
            String var7 = this.getProtocolVersion();
            InputStream var13 = var6.updateMediaEntry(var1, var4, var5, var7, var2, var3);
            var14 = (MediaEntry)this.parseEntry(MediaEntry.class, var13);
         } catch (HttpException var17) {
            String var16 = "Could not update entry " + var1;
            this.convertHttpExceptionForWrites(MediaEntry.class, var16, var17);
            var14 = null;
         }

         return var14;
      }
   }
}
