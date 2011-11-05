package com.google.wireless.gdata2.client;

import com.google.wireless.gdata2.client.HttpException;
import com.google.wireless.gdata2.client.QueryParams;
import com.google.wireless.gdata2.parser.ParseException;
import com.google.wireless.gdata2.serializer.GDataSerializer;
import java.io.IOException;
import java.io.InputStream;

public interface GDataClient {

   void close();

   InputStream createEntry(String var1, String var2, String var3, GDataSerializer var4) throws HttpException, IOException, ParseException;

   QueryParams createQueryParams();

   void deleteEntry(String var1, String var2, String var3) throws HttpException, IOException;

   String encodeUri(String var1);

   InputStream getFeedAsStream(String var1, String var2, String var3, String var4) throws HttpException, IOException;

   InputStream getMediaEntryAsStream(String var1, String var2, String var3, String var4) throws HttpException, IOException;

   InputStream submitBatch(String var1, String var2, String var3, GDataSerializer var4) throws HttpException, IOException, ParseException;

   InputStream updateEntry(String var1, String var2, String var3, String var4, GDataSerializer var5) throws HttpException, IOException, ParseException;

   InputStream updateMediaEntry(String var1, String var2, String var3, String var4, InputStream var5, String var6) throws HttpException, IOException;
}
