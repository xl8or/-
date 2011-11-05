package com.google.android.common.gdata;

import android.content.ContentResolver;
import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.common.gdata.QueryParamsImpl;
import com.google.android.common.http.GoogleHttpClient;
import com.google.wireless.gdata.client.GDataClient;
import com.google.wireless.gdata.client.HttpException;
import com.google.wireless.gdata.client.QueryParams;
import com.google.wireless.gdata.data.StringUtils;
import com.google.wireless.gdata.parser.ParseException;
import com.google.wireless.gdata.serializer.GDataSerializer;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.InputStreamEntity;

public class AndroidGDataClient implements GDataClient {

   private static final boolean DEBUG = false;
   private static final String DEFAULT_USER_AGENT_APP_VERSION = "Android-GData/1.1";
   private static final boolean LOCAL_LOGV = false;
   private static final int MAX_REDIRECTS = 10;
   private static final String TAG = "GDataClient";
   private static final String X_HTTP_METHOD_OVERRIDE = "X-HTTP-Method-Override";
   private final GoogleHttpClient mHttpClient;
   private ContentResolver mResolver;


   public AndroidGDataClient(Context var1) {
      this(var1, "Android-GData/1.1");
   }

   public AndroidGDataClient(Context var1, String var2) {
      GoogleHttpClient var3 = new GoogleHttpClient(var1, var2, (boolean)1);
      this.mHttpClient = var3;
      this.mHttpClient.enableCurlLogging("GDataClient", 2);
      ContentResolver var4 = var1.getContentResolver();
      this.mResolver = var4;
   }

   private InputStream createAndExecuteMethod(AndroidGDataClient.HttpRequestCreator var1, String var2, String var3) throws HttpException, IOException {
      HttpResponse var4 = null;
      int var5 = 500;
      int var6 = 10;

      URI var7;
      try {
         var7 = new URI(var2);
      } catch (URISyntaxException var121) {
         StringBuilder var29 = (new StringBuilder()).append("Unable to parse ");
         String var31 = var29.append(var2).append(" as URI.").toString();
         String var32 = "GDataClient";
         Log.w(var32, var31, var121);
         StringBuilder var36 = (new StringBuilder()).append("Unable to parse ");
         StringBuilder var38 = var36.append(var2).append(" as URI: ");
         String var39 = var121.getMessage();
         String var40 = var38.append(var39).toString();
         throw new IOException(var40);
      }

      InputStream var61;
      for(; var6 > 0; var6 += -1) {
         HttpUriRequest var12 = var1.createRequest(var7);
         AndroidHttpClient.modifyRequestToAcceptGzipResponse(var12);
         if(!TextUtils.isEmpty(var3)) {
            StringBuilder var13 = (new StringBuilder()).append("GoogleLogin auth=");
            String var15 = var13.append(var3).toString();
            String var17 = "Authorization";
            var12.addHeader(var17, var15);
         }

         if(Log.isLoggable("GDataClient", 3)) {
            StringBuilder var19 = (new StringBuilder()).append("Executing ");
            String var20 = var12.getRequestLine().toString();
            String var21 = var19.append(var20).toString();
            int var22 = Log.d("GDataClient", var21);
         }

         HttpResponse var25;
         try {
            GoogleHttpClient var23 = this.mHttpClient;
            var25 = var23.execute(var12);
         } catch (IOException var120) {
            String var42 = "Unable to execute HTTP request." + var120;
            int var43 = Log.w("GDataClient", var42);
            throw var120;
         }

         var4 = var25;
         StatusLine var26 = var25.getStatusLine();
         if(var26 == null) {
            int var27 = Log.w("GDataClient", "StatusLine is null.");
            throw new NullPointerException("StatusLine is null -- should not happen.");
         }

         if(Log.isLoggable("GDataClient", 3)) {
            String var44 = var25.getStatusLine().toString();
            int var45 = Log.d("GDataClient", var44);
            Header[] var46 = var25.getAllHeaders();
            int var47 = var46.length;

            for(int var48 = 0; var48 < var47; ++var48) {
               Header var49 = var46[var48];
               StringBuilder var50 = new StringBuilder();
               String var51 = var49.getName();
               StringBuilder var52 = var50.append(var51).append(": ");
               String var53 = var49.getValue();
               String var54 = var52.append(var53).toString();
               int var55 = Log.d("GDataClient", var54);
            }
         }

         var5 = var26.getStatusCode();
         HttpEntity var56 = var25.getEntity();
         short var58 = 200;
         if(var5 >= var58) {
            short var60 = 300;
            if(var5 < var60 && var56 != null) {
               var61 = AndroidHttpClient.getUngzippedContent(var56);
               if(Log.isLoggable("GDataClient", 3)) {
                  var61 = this.logInputStreamContents(var61);
               }

               return var61;
            }
         }

         short var63 = 302;
         if(var5 != var63) {
            break;
         }

         var56.consumeContent();
         String var65 = "Location";
         Header var66 = var25.getFirstHeader(var65);
         if(var66 == null) {
            if(Log.isLoggable("GDataClient", 3)) {
               int var67 = Log.d("GDataClient", "Redirect requested but no Location specified.");
            }
            break;
         }

         if(Log.isLoggable("GDataClient", 3)) {
            StringBuilder var80 = (new StringBuilder()).append("Following redirect to ");
            String var81 = var66.getValue();
            String var82 = var80.append(var81).toString();
            int var83 = Log.d("GDataClient", var82);
         }

         try {
            var7 = new URI;
            String var84 = var66.getValue();
            var7.<init>(var84);
         } catch (URISyntaxException var124) {
            if(!Log.isLoggable("GDataClient", 3)) {
               break;
            }

            StringBuilder var88 = (new StringBuilder()).append("Unable to parse ");
            String var89 = var66.getValue();
            String var90 = var88.append(var89).append(" as URI.").toString();
            String var91 = "GDataClient";
            Log.d(var91, var90, var124);
            StringBuilder var95 = (new StringBuilder()).append("Unable to parse ");
            String var96 = var66.getValue();
            String var97 = var95.append(var96).append(" as URI.").toString();
            throw new IOException(var97);
         }
      }

      if(Log.isLoggable("GDataClient", 2)) {
         StringBuilder var68 = (new StringBuilder()).append("Received ");
         String var70 = var68.append(var5).append(" status code.").toString();
         int var71 = Log.v("GDataClient", var70);
      }

      String var72 = null;
      HttpEntity var73 = var4.getEntity();
      if(var4 != null && var73 != null) {
         boolean var119 = false;

         String var99;
         try {
            var119 = true;
            var61 = AndroidHttpClient.getUngzippedContent(var73);
            ByteArrayOutputStream var74 = new ByteArrayOutputStream();
            byte[] var75 = new byte[8192];

            while(true) {
               int var76 = var61.read(var75);
               char var77 = '\uffff';
               if(var76 == var77) {
                  byte[] var98 = var74.toByteArray();
                  var99 = new String(var98);
                  var119 = false;
                  break;
               }

               byte var78 = 0;
               var74.write(var75, var78, var76);
            }
         } finally {
            if(var119) {
               if(var73 != null) {
                  var73.consumeContent();
               }

            }
         }

         try {
            if(Log.isLoggable("GDataClient", 2)) {
               int var100 = Log.v("GDataClient", var99);
            }
         } finally {
            ;
         }

         var72 = var99;
      }

      if(var73 != null) {
         var73.consumeContent();
      }

      StringBuilder var101 = (new StringBuilder()).append("Received ");
      String var103 = var101.append(var5).append(" status code").toString();
      if(var72 != null) {
         var103 = var103 + ": " + var72;
      }

      HttpException var104 = new HttpException;
      Object var107 = null;
      var104.<init>(var103, var5, (InputStream)var107);
      throw var104;
   }

   private HttpEntity createEntityForEntry(GDataSerializer var1, int var2) throws IOException, ParseException {
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();

      try {
         var1.serialize(var3, var2);
      } catch (IOException var17) {
         int var13 = Log.e("GDataClient", "Unable to serialize entry.", var17);
         throw var17;
      } catch (IllegalArgumentException var18) {
         throw new ParseException("Unable to serialize entry: ", var18);
      }

      byte[] var4 = var3.toByteArray();
      if(var4 != null && Log.isLoggable("GDataClient", 3)) {
         try {
            StringBuilder var5 = (new StringBuilder()).append("Serialized entry: ");
            String var6 = new String(var4, "UTF-8");
            String var7 = var5.append(var6).toString();
            int var8 = Log.d("GDataClient", var7);
         } catch (UnsupportedEncodingException var16) {
            throw new IllegalStateException("UTF-8 should be supported!", var16);
         }
      }

      ContentResolver var9 = this.mResolver;
      AbstractHttpEntity var10 = AndroidHttpClient.getCompressedEntity(var4, var9);
      String var11 = var1.getContentType();
      var10.setContentType(var11);
      return var10;
   }

   private InputStream logInputStreamContents(InputStream var1) throws IOException {
      if(var1 != null) {
         BufferedInputStream var2 = new BufferedInputStream((InputStream)var1, 16384);
         var2.mark(16384);
         int var3 = 16384;
         int var4 = 0;

         byte[] var5;
         int var6;
         for(var5 = new byte[var3]; var3 > 0; var4 += var6) {
            var6 = var2.read(var5, var4, var3);
            if(var6 <= 0) {
               break;
            }

            var3 -= var6;
         }

         String var7 = new String(var5, 0, var4, "UTF-8");
         int var8 = Log.d("GDataClient", var7);
         var2.reset();
         var1 = var2;
      }

      return (InputStream)var1;
   }

   public void close() {
      this.mHttpClient.close();
   }

   public InputStream createEntry(String var1, String var2, GDataSerializer var3) throws HttpException, IOException, ParseException {
      HttpEntity var4 = this.createEntityForEntry(var3, 1);
      AndroidGDataClient.PostRequestCreator var5 = new AndroidGDataClient.PostRequestCreator((String)null, var4);
      InputStream var6 = this.createAndExecuteMethod(var5, var1, var2);
      if(var6 != null) {
         return var6;
      } else {
         throw new IOException("Unable to create entry.");
      }
   }

   public QueryParams createQueryParams() {
      return new QueryParamsImpl();
   }

   public void deleteEntry(String var1, String var2) throws HttpException, IOException {
      if(StringUtils.isEmpty(var1)) {
         throw new IllegalArgumentException("you must specify an non-empty edit url");
      } else {
         AndroidGDataClient.PostRequestCreator var3 = new AndroidGDataClient.PostRequestCreator("DELETE", (HttpEntity)null);
         InputStream var4 = this.createAndExecuteMethod(var3, var1, var2);
         if(var4 == null) {
            throw new IOException("Unable to delete entry.");
         } else {
            try {
               var4.close();
            } catch (IOException var6) {
               ;
            }
         }
      }
   }

   public String encodeUri(String var1) {
      String var2;
      String var3;
      try {
         var2 = URLEncoder.encode(var1, "UTF-8");
      } catch (UnsupportedEncodingException var6) {
         int var5 = Log.e("JakartaGDataClient", "UTF-8 not supported -- should not happen.  Using default encoding.", var6);
         var3 = URLEncoder.encode(var1);
         return var3;
      }

      var3 = var2;
      return var3;
   }

   public InputStream getFeedAsStream(String var1, String var2) throws HttpException, IOException {
      AndroidGDataClient.GetRequestCreator var3 = new AndroidGDataClient.GetRequestCreator();
      InputStream var4 = this.createAndExecuteMethod(var3, var1, var2);
      if(var4 != null) {
         return var4;
      } else {
         throw new IOException("Unable to access feed.");
      }
   }

   public InputStream getMediaEntryAsStream(String var1, String var2) throws HttpException, IOException {
      AndroidGDataClient.GetRequestCreator var3 = new AndroidGDataClient.GetRequestCreator();
      InputStream var4 = this.createAndExecuteMethod(var3, var1, var2);
      if(var4 != null) {
         return var4;
      } else {
         throw new IOException("Unable to access media entry.");
      }
   }

   public InputStream updateEntry(String var1, String var2, GDataSerializer var3) throws HttpException, IOException, ParseException {
      HttpEntity var4 = this.createEntityForEntry(var3, 2);
      AndroidGDataClient.PostRequestCreator var5 = new AndroidGDataClient.PostRequestCreator("PUT", var4);
      InputStream var6 = this.createAndExecuteMethod(var5, var1, var2);
      if(var6 != null) {
         return var6;
      } else {
         throw new IOException("Unable to update entry.");
      }
   }

   public InputStream updateMediaEntry(String var1, String var2, InputStream var3, String var4) throws HttpException, IOException {
      AndroidGDataClient.MediaPutRequestCreator var5 = new AndroidGDataClient.MediaPutRequestCreator(var3, var4);
      InputStream var6 = this.createAndExecuteMethod(var5, var1, var2);
      if(var6 != null) {
         return var6;
      } else {
         throw new IOException("Unable to write media entry.");
      }
   }

   private static class MediaPutRequestCreator implements AndroidGDataClient.HttpRequestCreator {

      private final String mContentType;
      private final InputStream mMediaInputStream;


      public MediaPutRequestCreator(InputStream var1, String var2) {
         this.mMediaInputStream = var1;
         this.mContentType = var2;
      }

      public HttpUriRequest createRequest(URI var1) {
         HttpPost var2 = new HttpPost(var1);
         var2.addHeader("X-HTTP-Method-Override", "PUT");
         InputStream var3 = this.mMediaInputStream;
         InputStreamEntity var4 = new InputStreamEntity(var3, 65535L);
         String var5 = this.mContentType;
         var4.setContentType(var5);
         var2.setEntity(var4);
         return var2;
      }
   }

   private static class GetRequestCreator implements AndroidGDataClient.HttpRequestCreator {

      public GetRequestCreator() {}

      public HttpUriRequest createRequest(URI var1) {
         return new HttpGet(var1);
      }
   }

   private static class PostRequestCreator implements AndroidGDataClient.HttpRequestCreator {

      private final HttpEntity mEntity;
      private final String mMethodOverride;


      public PostRequestCreator(String var1, HttpEntity var2) {
         this.mMethodOverride = var1;
         this.mEntity = var2;
      }

      public HttpUriRequest createRequest(URI var1) {
         HttpPost var2 = new HttpPost(var1);
         if(this.mMethodOverride != null) {
            String var3 = this.mMethodOverride;
            var2.addHeader("X-HTTP-Method-Override", var3);
         }

         HttpEntity var4 = this.mEntity;
         var2.setEntity(var4);
         return var2;
      }
   }

   private interface HttpRequestCreator {

      HttpUriRequest createRequest(URI var1);
   }
}
