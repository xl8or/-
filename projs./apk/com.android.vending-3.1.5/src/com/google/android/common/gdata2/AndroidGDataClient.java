package com.google.android.common.gdata2;

import android.content.ContentResolver;
import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.util.TimeFormatException;
import com.google.android.common.gdata2.QueryParamsImpl;
import com.google.android.common.http.GoogleHttpClient;
import com.google.wireless.gdata2.client.GDataClient;
import com.google.wireless.gdata2.client.HttpException;
import com.google.wireless.gdata2.client.QueryParams;
import com.google.wireless.gdata2.data.StringUtils;
import com.google.wireless.gdata2.parser.ParseException;
import com.google.wireless.gdata2.serializer.GDataSerializer;
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

   private static String DEFAULT_GDATA_VERSION = "2.0";
   private static final String DEFAULT_USER_AGENT_APP_VERSION = "Android-GData/1.2";
   public static final String GDATA_VERSION_1_0 = "1.0";
   public static final String GDATA_VERSION_2_0 = "2.0";
   private static final boolean LOCAL_LOGV = false;
   private static final int MAX_REDIRECTS = 10;
   private static final String TAG = "GDataClient";
   private static final String X_HTTP_METHOD_OVERRIDE = "X-HTTP-Method-Override";
   private String mGDataVersion;
   private final GoogleHttpClient mHttpClient;
   private ContentResolver mResolver;


   public AndroidGDataClient(Context var1) {
      this(var1, "Android-GData/1.2");
   }

   public AndroidGDataClient(Context var1, String var2) {
      String var3 = DEFAULT_GDATA_VERSION;
      this(var1, var2, var3);
   }

   public AndroidGDataClient(Context var1, String var2, String var3) {
      GoogleHttpClient var4 = new GoogleHttpClient(var1, var2, (boolean)1);
      this.mHttpClient = var4;
      this.mHttpClient.enableCurlLogging("GDataClient", 2);
      ContentResolver var5 = var1.getContentResolver();
      this.mResolver = var5;
      this.mGDataVersion = var3;
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
            ;
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

   protected InputStream createAndExecuteMethod(AndroidGDataClient.HttpRequestCreator var1, String var2, String var3, String var4, String var5) throws HttpException, IOException {
      HttpResponse var6 = null;
      int var7 = 500;
      int var8 = 10;

      URI var9;
      try {
         var9 = new URI(var2);
      } catch (URISyntaxException var188) {
         StringBuilder var46 = (new StringBuilder()).append("Unable to parse ");
         String var48 = var46.append(var2).append(" as URI.").toString();
         String var49 = "GDataClient";
         Log.w(var49, var48, var188);
         StringBuilder var53 = (new StringBuilder()).append("Unable to parse ");
         StringBuilder var55 = var53.append(var2).append(" as URI: ");
         String var56 = var188.getMessage();
         String var57 = var55.append(var56).toString();
         throw new IOException(var57);
      }

      long var12;
      InputStream var92;
      for(var12 = 0L; var8 > 0; var8 += -1) {
         HttpUriRequest var16 = var1.createRequest(var9);
         AndroidHttpClient.modifyRequestToAcceptGzipResponse(var16);
         if(!TextUtils.isEmpty(var3)) {
            StringBuilder var17 = (new StringBuilder()).append("GoogleLogin auth=");
            String var19 = var17.append(var3).toString();
            String var21 = "Authorization";
            var16.addHeader(var21, var19);
         }

         if(!TextUtils.isEmpty(this.mGDataVersion)) {
            String var23 = this.mGDataVersion;
            String var25 = "GData-Version";
            var16.addHeader(var25, var23);
         }

         if(!TextUtils.isEmpty(var4)) {
            String var27 = var16.getMethod();
            String var29 = "X-HTTP-Method-Override";
            Header var30 = var16.getFirstHeader(var29);
            if(var30 != null) {
               var27 = var30.getValue();
            }

            String var31 = "GET";
            if(var31.equals(var27)) {
               String var34 = "If-None-Match";
               var16.addHeader(var34, var4);
            } else {
               label522: {
                  String var58 = "DELETE";
                  if(!var58.equals(var27)) {
                     String var60 = "PUT";
                     if(!var60.equals(var27)) {
                        String var62 = "PATCH";
                        if(!var62.equals(var27)) {
                           break label522;
                        }
                     }
                  }

                  String var65 = "W/";
                  if(!var4.startsWith(var65)) {
                     String var67 = "If-Match";
                     var16.addHeader(var67, var4);
                  }
               }
            }
         }

         if(Log.isLoggable("GDataClient", 3)) {
            StringBuilder var36 = (new StringBuilder()).append("Executing ");
            String var37 = var16.getRequestLine().toString();
            String var38 = var36.append(var37).toString();
            int var39 = Log.d("GDataClient", var38);
         }

         HttpResponse var42;
         try {
            GoogleHttpClient var40 = this.mHttpClient;
            var42 = var40.execute(var16);
         } catch (IOException var187) {
            StringBuilder var70 = (new StringBuilder()).append("Unable to execute HTTP request.");
            String var72 = var70.append(var187).toString();
            int var73 = Log.w("GDataClient", var72);
            throw var187;
         }

         var6 = var42;
         StatusLine var43 = var42.getStatusLine();
         if(var43 == null) {
            int var44 = Log.w("GDataClient", "StatusLine is null.");
            throw new NullPointerException("StatusLine is null -- should not happen.");
         }

         if(Log.isLoggable("GDataClient", 3)) {
            String var74 = var42.getStatusLine().toString();
            int var75 = Log.d("GDataClient", var74);
            Header[] var76 = var42.getAllHeaders();
            int var77 = var76.length;

            for(int var78 = 0; var78 < var77; ++var78) {
               Header var80 = var76[var78];
               StringBuilder var81 = new StringBuilder();
               String var82 = var80.getName();
               StringBuilder var83 = var81.append(var82).append(": ");
               String var84 = var80.getValue();
               String var85 = var83.append(var84).toString();
               int var86 = Log.d("GDataClient", var85);
            }
         }

         var7 = var43.getStatusCode();
         HttpEntity var87 = var42.getEntity();
         short var89 = 200;
         if(var7 >= var89) {
            short var91 = 300;
            if(var7 < var91 && var87 != null) {
               var92 = AndroidHttpClient.getUngzippedContent(var87);
               if(Log.isLoggable("GDataClient", 3)) {
                  var92 = this.logInputStreamContents(var92);
               }

               return var92;
            }
         }

         short var96 = 302;
         if(var7 != var96) {
            short var132 = 503;
            if(var7 == var132) {
               String var134 = "Retry-After";
               Header var135 = var42.getFirstHeader(var134);
               if(var135 != null) {
                  String var136 = var135.getValue();

                  long var137;
                  long var139;
                  try {
                     var137 = System.currentTimeMillis() / 1000L;
                     var139 = Long.parseLong(var136);
                  } catch (NumberFormatException var191) {
                     try {
                        Time var144 = new Time();
                        boolean var147 = var144.parse3339(var136);
                        byte var149 = 0;
                        var12 = var144.toMillis((boolean)var149) / 1000L;
                     } catch (TimeFormatException var186) {
                        StringBuilder var151 = (new StringBuilder()).append("Unable to parse ");
                        String var153 = var151.append(var136).toString();
                        String var154 = "GDataClient";
                        Log.d(var154, var153, var186);
                     }
                     break;
                  }

                  var12 = var137 + var139;
               }
            }
            break;
         }

         var87.consumeContent();
         String var98 = "Location";
         Header var99 = var42.getFirstHeader(var98);
         if(var99 == null) {
            if(Log.isLoggable("GDataClient", 3)) {
               int var100 = Log.d("GDataClient", "Redirect requested but no Location specified.");
            }
            break;
         }

         if(Log.isLoggable("GDataClient", 3)) {
            StringBuilder var113 = (new StringBuilder()).append("Following redirect to ");
            String var114 = var99.getValue();
            String var115 = var113.append(var114).toString();
            int var116 = Log.d("GDataClient", var115);
         }

         try {
            var9 = new URI;
            String var117 = var99.getValue();
            var9.<init>(var117);
         } catch (URISyntaxException var192) {
            if(!Log.isLoggable("GDataClient", 3)) {
               break;
            }

            StringBuilder var121 = (new StringBuilder()).append("Unable to parse ");
            String var122 = var99.getValue();
            String var123 = var121.append(var122).append(" as URI.").toString();
            String var124 = "GDataClient";
            Log.d(var124, var123, var192);
            StringBuilder var128 = (new StringBuilder()).append("Unable to parse ");
            String var129 = var99.getValue();
            String var130 = var128.append(var129).append(" as URI.").toString();
            throw new IOException(var130);
         }
      }

      if(Log.isLoggable("GDataClient", 2)) {
         StringBuilder var101 = (new StringBuilder()).append("Received ");
         String var103 = var101.append(var7).append(" status code.").toString();
         int var104 = Log.v("GDataClient", var103);
      }

      String var105 = null;
      HttpEntity var106 = var6.getEntity();
      if(var6 != null && var106 != null) {
         boolean var185 = false;

         String var158;
         try {
            var185 = true;
            var92 = AndroidHttpClient.getUngzippedContent(var106);
            ByteArrayOutputStream var107 = new ByteArrayOutputStream();
            byte[] var108 = new byte[8192];

            while(true) {
               int var109 = var92.read(var108);
               char var110 = '\uffff';
               if(var109 == var110) {
                  byte[] var157 = var107.toByteArray();
                  var158 = new String(var157);
                  var185 = false;
                  break;
               }

               byte var111 = 0;
               var107.write(var108, var111, var109);
            }
         } finally {
            if(var185) {
               if(var106 != null) {
                  var106.consumeContent();
               }

            }
         }

         try {
            if(Log.isLoggable("GDataClient", 2)) {
               int var159 = Log.v("GDataClient", var158);
            }
         } finally {
            ;
         }

         var105 = var158;
      }

      if(var106 != null) {
         var106.consumeContent();
      }

      StringBuilder var160 = (new StringBuilder()).append("Received ");
      String var162 = var160.append(var7).append(" status code").toString();
      if(var105 != null) {
         var162 = var162 + ": " + var105;
      }

      Object var164 = null;
      HttpException var165 = new HttpException(var162, var7, (InputStream)var164);
      short var167 = 503;
      if(var7 == var167) {
         var165.setRetryAfter(var12);
      }

      throw var165;
   }

   public InputStream createEntry(String var1, String var2, String var3, GDataSerializer var4) throws HttpException, IOException, ParseException {
      HttpEntity var5 = this.createEntityForEntry(var4, 1);
      AndroidGDataClient.PostRequestCreator var6 = new AndroidGDataClient.PostRequestCreator((String)null, var5);
      InputStream var11 = this.createAndExecuteMethod(var6, var1, var2, (String)null, var3);
      if(var11 != null) {
         return var11;
      } else {
         throw new IOException("Unable to create entry.");
      }
   }

   public QueryParams createQueryParams() {
      return new QueryParamsImpl();
   }

   public void deleteEntry(String var1, String var2, String var3) throws HttpException, IOException {
      if(StringUtils.isEmpty(var1)) {
         throw new IllegalArgumentException("you must specify an non-empty edit url");
      } else {
         AndroidGDataClient.PostRequestCreator var4 = new AndroidGDataClient.PostRequestCreator("DELETE", (HttpEntity)null);
         InputStream var9 = this.createAndExecuteMethod(var4, var1, var2, var3, (String)null);
         if(var9 == null) {
            throw new IOException("Unable to delete entry.");
         } else {
            try {
               var9.close();
            } catch (IOException var11) {
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

   public InputStream getFeedAsStream(String var1, String var2, String var3, String var4) throws HttpException, IOException {
      AndroidGDataClient.GetRequestCreator var5 = new AndroidGDataClient.GetRequestCreator();
      InputStream var11 = this.createAndExecuteMethod(var5, var1, var2, var3, var4);
      if(var11 != null) {
         return var11;
      } else {
         throw new IOException("Unable to access feed.");
      }
   }

   public InputStream getMediaEntryAsStream(String var1, String var2, String var3, String var4) throws HttpException, IOException {
      AndroidGDataClient.GetRequestCreator var5 = new AndroidGDataClient.GetRequestCreator();
      InputStream var11 = this.createAndExecuteMethod(var5, var1, var2, var3, var4);
      if(var11 != null) {
         return var11;
      } else {
         throw new IOException("Unable to access media entry.");
      }
   }

   public InputStream submitBatch(String var1, String var2, String var3, GDataSerializer var4) throws HttpException, IOException, ParseException {
      HttpEntity var5 = this.createEntityForEntry(var4, 3);
      AndroidGDataClient.PostRequestCreator var6 = new AndroidGDataClient.PostRequestCreator("POST", var5);
      InputStream var11 = this.createAndExecuteMethod(var6, var1, var2, (String)null, var3);
      if(var11 != null) {
         return var11;
      } else {
         throw new IOException("Unable to process batch request.");
      }
   }

   public InputStream updateEntry(String var1, String var2, String var3, String var4, GDataSerializer var5) throws HttpException, IOException, ParseException {
      HttpEntity var6 = this.createEntityForEntry(var5, 2);
      String var7;
      if(var5.isPartial()) {
         var7 = "PATCH";
      } else {
         var7 = "PUT";
      }

      AndroidGDataClient.PostRequestCreator var8 = new AndroidGDataClient.PostRequestCreator(var7, var6);
      InputStream var14 = this.createAndExecuteMethod(var8, var1, var2, var3, var4);
      if(var14 != null) {
         return var14;
      } else {
         throw new IOException("Unable to update entry.");
      }
   }

   public InputStream updateMediaEntry(String var1, String var2, String var3, String var4, InputStream var5, String var6) throws HttpException, IOException {
      AndroidGDataClient.MediaPutRequestCreator var7 = new AndroidGDataClient.MediaPutRequestCreator(var5, var6);
      InputStream var13 = this.createAndExecuteMethod(var7, var1, var2, var3, var4);
      if(var13 != null) {
         return var13;
      } else {
         throw new IOException("Unable to write media entry.");
      }
   }

   protected interface HttpRequestCreator {

      HttpUriRequest createRequest(URI var1);
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
}
