package com.google.wireless.gdata.client;

import com.google.wireless.gdata.client.HttpException;
import com.google.wireless.gdata.client.QueryParams;
import com.google.wireless.gdata.parser.ParseException;
import com.google.wireless.gdata.serializer.GDataSerializer;
import java.io.IOException;
import java.io.InputStream;

public interface GDataClient {

   void close();

   InputStream createEntry(String var1, String var2, GDataSerializer var3) throws HttpException, IOException, ParseException;

   QueryParams createQueryParams();

   void deleteEntry(String var1, String var2) throws HttpException, IOException;

   String encodeUri(String var1);

   InputStream getFeedAsStream(String var1, String var2) throws HttpException, IOException;

   InputStream getMediaEntryAsStream(String var1, String var2) throws HttpException, IOException;

   InputStream updateEntry(String var1, String var2, GDataSerializer var3) throws HttpException, IOException, ParseException;

   InputStream updateMediaEntry(String var1, String var2, InputStream var3, String var4) throws HttpException, IOException;
}
