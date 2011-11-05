package org.apache.commons.httpclient.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ProtocolException;
import java.net.URL;
import java.security.Permission;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpURLConnection extends java.net.HttpURLConnection {

   private static final Log LOG = LogFactory.getLog(HttpURLConnection.class);
   private HttpMethod method;
   private URL url;


   protected HttpURLConnection(URL var1) {
      super(var1);
      throw new RuntimeException("An HTTP URL connection can only be constructed from a HttpMethod class");
   }

   public HttpURLConnection(HttpMethod var1, URL var2) {
      super(var2);
      this.method = var1;
      this.url = var2;
   }

   public void connect() throws IOException {
      LOG.trace("enter HttpURLConnection.connect()");
      throw new RuntimeException("This class can only be used with alreadyretrieved data");
   }

   public void disconnect() {
      LOG.trace("enter HttpURLConnection.disconnect()");
      throw new RuntimeException("Not implemented yet");
   }

   public boolean getAllowUserInteraction() {
      LOG.trace("enter HttpURLConnection.getAllowUserInteraction()");
      throw new RuntimeException("Not implemented yet");
   }

   public Object getContent() throws IOException {
      LOG.trace("enter HttpURLConnection.getContent()");
      throw new RuntimeException("Not implemented yet");
   }

   public Object getContent(Class[] var1) throws IOException {
      LOG.trace("enter HttpURLConnection.getContent(Class[])");
      throw new RuntimeException("Not implemented yet");
   }

   public boolean getDefaultUseCaches() {
      LOG.trace("enter HttpURLConnection.getDefaultUseCaches()");
      throw new RuntimeException("Not implemented yet");
   }

   public boolean getDoInput() {
      LOG.trace("enter HttpURLConnection.getDoInput()");
      throw new RuntimeException("Not implemented yet");
   }

   public boolean getDoOutput() {
      LOG.trace("enter HttpURLConnection.getDoOutput()");
      throw new RuntimeException("Not implemented yet");
   }

   public InputStream getErrorStream() {
      LOG.trace("enter HttpURLConnection.getErrorStream()");
      throw new RuntimeException("Not implemented yet");
   }

   public String getHeaderField(int var1) {
      LOG.trace("enter HttpURLConnection.getHeaderField(int)");
      String var2;
      if(var1 == 0) {
         var2 = this.method.getStatusLine().toString();
      } else {
         Header[] var3 = this.method.getResponseHeaders();
         if(var1 >= 0) {
            int var4 = var3.length;
            if(var1 <= var4) {
               int var5 = var1 - 1;
               var2 = var3[var5].getValue();
               return var2;
            }
         }

         var2 = null;
      }

      return var2;
   }

   public String getHeaderField(String var1) {
      LOG.trace("enter HttpURLConnection.getHeaderField(String)");
      Header[] var2 = this.method.getResponseHeaders();
      int var3 = var2.length - 1;

      String var4;
      while(true) {
         if(var3 < 0) {
            var4 = null;
            break;
         }

         if(var2[var3].getName().equalsIgnoreCase(var1)) {
            var4 = var2[var3].getValue();
            break;
         }

         var3 += -1;
      }

      return var4;
   }

   public String getHeaderFieldKey(int var1) {
      LOG.trace("enter HttpURLConnection.getHeaderFieldKey(int)");
      String var2;
      if(var1 == 0) {
         var2 = null;
      } else {
         Header[] var3 = this.method.getResponseHeaders();
         if(var1 >= 0) {
            int var4 = var3.length;
            if(var1 <= var4) {
               int var5 = var1 - 1;
               var2 = var3[var5].getName();
               return var2;
            }
         }

         var2 = null;
      }

      return var2;
   }

   public long getIfModifiedSince() {
      LOG.trace("enter HttpURLConnection.getIfmodifiedSince()");
      throw new RuntimeException("Not implemented yet");
   }

   public InputStream getInputStream() throws IOException {
      LOG.trace("enter HttpURLConnection.getInputStream()");
      return this.method.getResponseBodyAsStream();
   }

   public boolean getInstanceFollowRedirects() {
      LOG.trace("enter HttpURLConnection.getInstanceFollowRedirects()");
      throw new RuntimeException("Not implemented yet");
   }

   public OutputStream getOutputStream() throws IOException {
      LOG.trace("enter HttpURLConnection.getOutputStream()");
      throw new RuntimeException("This class can only be used with alreadyretrieved data");
   }

   public Permission getPermission() throws IOException {
      LOG.trace("enter HttpURLConnection.getPermission()");
      throw new RuntimeException("Not implemented yet");
   }

   public String getRequestMethod() {
      LOG.trace("enter HttpURLConnection.getRequestMethod()");
      return this.method.getName();
   }

   public String getRequestProperty(String var1) {
      LOG.trace("enter HttpURLConnection.getRequestProperty()");
      throw new RuntimeException("Not implemented yet");
   }

   public int getResponseCode() throws IOException {
      LOG.trace("enter HttpURLConnection.getResponseCode()");
      return this.method.getStatusCode();
   }

   public String getResponseMessage() throws IOException {
      LOG.trace("enter HttpURLConnection.getResponseMessage()");
      return this.method.getStatusText();
   }

   public URL getURL() {
      LOG.trace("enter HttpURLConnection.getURL()");
      return this.url;
   }

   public boolean getUseCaches() {
      LOG.trace("enter HttpURLConnection.getUseCaches()");
      throw new RuntimeException("Not implemented yet");
   }

   public void setAllowUserInteraction(boolean var1) {
      LOG.trace("enter HttpURLConnection.setAllowUserInteraction(boolean)");
      throw new RuntimeException("This class can only be used with alreadyretrieved data");
   }

   public void setDefaultUseCaches(boolean var1) {
      LOG.trace("enter HttpURLConnection.setDefaultUseCaches(boolean)");
      throw new RuntimeException("This class can only be used with alreadyretrieved data");
   }

   public void setDoInput(boolean var1) {
      LOG.trace("enter HttpURLConnection.setDoInput()");
      throw new RuntimeException("This class can only be used with alreadyretrieved data");
   }

   public void setDoOutput(boolean var1) {
      LOG.trace("enter HttpURLConnection.setDoOutput()");
      throw new RuntimeException("This class can only be used with alreadyretrieved data");
   }

   public void setIfModifiedSince(long var1) {
      LOG.trace("enter HttpURLConnection.setIfModifiedSince(long)");
      throw new RuntimeException("This class can only be used with alreadyretrieved data");
   }

   public void setInstanceFollowRedirects(boolean var1) {
      LOG.trace("enter HttpURLConnection.setInstanceFollowRedirects(boolean)");
      throw new RuntimeException("This class can only be used with alreadyretrieved data");
   }

   public void setRequestMethod(String var1) throws ProtocolException {
      LOG.trace("enter HttpURLConnection.setRequestMethod(String)");
      throw new RuntimeException("This class can only be used with alreadyretrieved data");
   }

   public void setRequestProperty(String var1, String var2) {
      LOG.trace("enter HttpURLConnection.setRequestProperty()");
      throw new RuntimeException("This class can only be used with alreadyretrieved data");
   }

   public void setUseCaches(boolean var1) {
      LOG.trace("enter HttpURLConnection.setUseCaches(boolean)");
      throw new RuntimeException("This class can only be used with alreadyretrieved data");
   }

   public boolean usingProxy() {
      LOG.trace("enter HttpURLConnection.usingProxy()");
      throw new RuntimeException("Not implemented yet");
   }
}
