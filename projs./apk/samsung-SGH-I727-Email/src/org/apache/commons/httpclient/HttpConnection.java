package org.apache.commons.httpclient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpParser;
import org.apache.commons.httpclient.Wire;
import org.apache.commons.httpclient.WireLogOutputStream;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.SecureProtocolSocketFactory;
import org.apache.commons.httpclient.util.EncodingUtil;
import org.apache.commons.httpclient.util.ExceptionUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpConnection {

   private static final byte[] CRLF = new byte[]{(byte)13, (byte)10};
   private static final Log LOG = LogFactory.getLog(HttpConnection.class);
   private String hostName;
   private HttpConnectionManager httpConnectionManager;
   private InputStream inputStream;
   protected boolean isOpen;
   private InputStream lastResponseInputStream;
   private InetAddress localAddress;
   private boolean locked;
   private OutputStream outputStream;
   private HttpConnectionParams params;
   private int portNumber;
   private Protocol protocolInUse;
   private String proxyHostName;
   private int proxyPortNumber;
   private Socket socket;
   private boolean tunnelEstablished;
   private boolean usingSecureSocket;


   public HttpConnection(String var1, int var2) {
      Protocol var3 = Protocol.getProtocol("http");
      Object var6 = null;
      this((String)null, -1, var1, (String)var6, var2, var3);
   }

   public HttpConnection(String var1, int var2, String var3, int var4) {
      Protocol var5 = Protocol.getProtocol("http");
      this(var1, var2, var3, (String)null, var4, var5);
   }

   public HttpConnection(String var1, int var2, String var3, int var4, Protocol var5) {
      this.hostName = null;
      this.portNumber = -1;
      this.proxyHostName = null;
      this.proxyPortNumber = -1;
      this.socket = null;
      this.inputStream = null;
      this.outputStream = null;
      this.lastResponseInputStream = null;
      this.isOpen = (boolean)0;
      HttpConnectionParams var6 = new HttpConnectionParams();
      this.params = var6;
      this.locked = (boolean)0;
      this.usingSecureSocket = (boolean)0;
      this.tunnelEstablished = (boolean)0;
      if(var3 == null) {
         throw new IllegalArgumentException("host parameter is null");
      } else if(var5 == null) {
         throw new IllegalArgumentException("protocol is null");
      } else {
         this.proxyHostName = var1;
         this.proxyPortNumber = var2;
         this.hostName = var3;
         int var7 = var5.resolvePort(var4);
         this.portNumber = var7;
         this.protocolInUse = var5;
      }
   }

   public HttpConnection(String var1, int var2, String var3, String var4, int var5, Protocol var6) {
      this(var1, var2, var3, var5, var6);
   }

   public HttpConnection(String var1, int var2, Protocol var3) {
      Object var6 = null;
      this((String)null, -1, var1, (String)var6, var2, var3);
   }

   public HttpConnection(String var1, String var2, int var3, Protocol var4) {
      this((String)null, -1, var1, var2, var3, var4);
   }

   public HttpConnection(HostConfiguration var1) {
      String var2 = var1.getProxyHost();
      int var3 = var1.getProxyPort();
      String var4 = var1.getHost();
      int var5 = var1.getPort();
      Protocol var6 = var1.getProtocol();
      this(var2, var3, var4, var5, var6);
      InetAddress var7 = var1.getLocalAddress();
      this.localAddress = var7;
   }

   protected void assertNotOpen() throws IllegalStateException {
      if(this.isOpen) {
         throw new IllegalStateException("Connection is open");
      }
   }

   protected void assertOpen() throws IllegalStateException {
      if(!this.isOpen) {
         throw new IllegalStateException("Connection is not open");
      }
   }

   public void close() {
      LOG.trace("enter HttpConnection.close()");
      this.closeSocketAndStreams();
   }

   public boolean closeIfStale() throws IOException {
      boolean var1;
      if(this.isOpen && this.isStale()) {
         LOG.debug("Connection is stale, closing...");
         this.close();
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   protected void closeSocketAndStreams() {
      LOG.trace("enter HttpConnection.closeSockedAndStreams()");
      this.isOpen = (boolean)0;
      this.lastResponseInputStream = null;
      if(this.outputStream != null) {
         OutputStream var1 = this.outputStream;
         this.outputStream = null;

         try {
            var1.close();
         } catch (Exception var7) {
            LOG.debug("Exception caught when closing output", var7);
         }
      }

      if(this.inputStream != null) {
         InputStream var8 = this.inputStream;
         this.inputStream = null;

         try {
            var8.close();
         } catch (Exception var6) {
            LOG.debug("Exception caught when closing input", var6);
         }
      }

      if(this.socket != null) {
         Socket var9 = this.socket;
         this.socket = null;

         try {
            var9.close();
         } catch (Exception var5) {
            LOG.debug("Exception caught when closing socket", var5);
         }
      }

      this.tunnelEstablished = (boolean)0;
      this.usingSecureSocket = (boolean)0;
   }

   public void flushRequestOutputStream() throws IOException {
      LOG.trace("enter HttpConnection.flushRequestOutputStream()");
      this.assertOpen();
      this.outputStream.flush();
   }

   public String getHost() {
      return this.hostName;
   }

   public HttpConnectionManager getHttpConnectionManager() {
      return this.httpConnectionManager;
   }

   public InputStream getLastResponseInputStream() {
      return this.lastResponseInputStream;
   }

   public InetAddress getLocalAddress() {
      return this.localAddress;
   }

   public HttpConnectionParams getParams() {
      return this.params;
   }

   public int getPort() {
      int var1;
      if(this.portNumber < 0) {
         if(this.isSecure()) {
            var1 = 443;
         } else {
            var1 = 80;
         }
      } else {
         var1 = this.portNumber;
      }

      return var1;
   }

   public Protocol getProtocol() {
      return this.protocolInUse;
   }

   public String getProxyHost() {
      return this.proxyHostName;
   }

   public int getProxyPort() {
      return this.proxyPortNumber;
   }

   public OutputStream getRequestOutputStream() throws IOException, IllegalStateException {
      LOG.trace("enter HttpConnection.getRequestOutputStream()");
      this.assertOpen();
      Object var1 = this.outputStream;
      if(Wire.CONTENT_WIRE.enabled()) {
         Wire var2 = Wire.CONTENT_WIRE;
         var1 = new WireLogOutputStream((OutputStream)var1, var2);
      }

      return (OutputStream)var1;
   }

   public InputStream getResponseInputStream() throws IOException, IllegalStateException {
      LOG.trace("enter HttpConnection.getResponseInputStream()");
      this.assertOpen();
      return this.inputStream;
   }

   public int getSendBufferSize() throws SocketException {
      int var1;
      if(this.socket == null) {
         var1 = -1;
      } else {
         var1 = this.socket.getSendBufferSize();
      }

      return var1;
   }

   public int getSoTimeout() throws SocketException {
      return this.params.getSoTimeout();
   }

   protected Socket getSocket() {
      return this.socket;
   }

   public String getVirtualHost() {
      return this.hostName;
   }

   protected boolean isLocked() {
      return this.locked;
   }

   public boolean isOpen() {
      return this.isOpen;
   }

   public boolean isProxied() {
      boolean var1;
      if(this.proxyHostName != null && this.proxyPortNumber > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isResponseAvailable() throws IOException {
      LOG.trace("enter HttpConnection.isResponseAvailable()");
      boolean var1;
      if(this.isOpen) {
         if(this.inputStream.available() > 0) {
            var1 = true;
         } else {
            var1 = false;
         }
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isResponseAvailable(int var1) throws IOException {
      LOG.trace("enter HttpConnection.isResponseAvailable(int)");
      this.assertOpen();
      boolean var2 = false;
      if(this.inputStream.available() > 0) {
         var2 = true;
      } else {
         boolean var21 = false;

         label119: {
            label118: {
               label117: {
                  try {
                     var21 = true;
                     this.socket.setSoTimeout(var1);
                     this.inputStream.mark(1);
                     if(this.inputStream.read() == -1) {
                        LOG.debug("Input data not available");
                        var21 = false;
                        break label118;
                     }

                     this.inputStream.reset();
                     LOG.debug("Input data available");
                     var21 = false;
                  } catch (InterruptedIOException var25) {
                     if(!ExceptionUtil.isSocketTimeoutException(var25)) {
                        throw var25;
                     }

                     if(LOG.isDebugEnabled()) {
                        Log var10 = LOG;
                        String var11 = "Input data not available after " + var1 + " ms";
                        var10.debug(var11);
                        var21 = false;
                     } else {
                        var21 = false;
                     }
                     break label117;
                  } finally {
                     if(var21) {
                        try {
                           Socket var8 = this.socket;
                           int var9 = this.params.getSoTimeout();
                           var8.setSoTimeout(var9);
                        } catch (IOException var22) {
                           LOG.debug("An error ocurred while resetting soTimeout, we will assume that no response is available.", var22);
                        }

                     }
                  }

                  var2 = true;
                  break label118;
               }

               try {
                  Socket var12 = this.socket;
                  int var13 = this.params.getSoTimeout();
                  var12.setSoTimeout(var13);
                  return var2;
               } catch (IOException var23) {
                  LOG.debug("An error ocurred while resetting soTimeout, we will assume that no response is available.", var23);
                  break label119;
               }
            }

            try {
               Socket var3 = this.socket;
               int var4 = this.params.getSoTimeout();
               var3.setSoTimeout(var4);
               return var2;
            } catch (IOException var24) {
               LOG.debug("An error ocurred while resetting soTimeout, we will assume that no response is available.", var24);
            }
         }

         var2 = false;
      }

      return var2;
   }

   public boolean isSecure() {
      return this.protocolInUse.isSecure();
   }

   protected boolean isStale() throws IOException {
      // $FF: Couldn't be decompiled
   }

   public boolean isStaleCheckingEnabled() {
      return this.params.isStaleCheckingEnabled();
   }

   public boolean isTransparent() {
      boolean var1;
      if(this.isProxied() && !this.tunnelEstablished) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public void open() throws IOException {
      // $FF: Couldn't be decompiled
   }

   public void print(String var1) throws IOException, IllegalStateException {
      LOG.trace("enter HttpConnection.print(String)");
      byte[] var2 = EncodingUtil.getBytes(var1, "ISO-8859-1");
      this.write(var2);
   }

   public void print(String var1, String var2) throws IOException, IllegalStateException {
      LOG.trace("enter HttpConnection.print(String)");
      byte[] var3 = EncodingUtil.getBytes(var1, var2);
      this.write(var3);
   }

   public void printLine() throws IOException, IllegalStateException {
      LOG.trace("enter HttpConnection.printLine()");
      this.writeLine();
   }

   public void printLine(String var1) throws IOException, IllegalStateException {
      LOG.trace("enter HttpConnection.printLine(String)");
      byte[] var2 = EncodingUtil.getBytes(var1, "ISO-8859-1");
      this.writeLine(var2);
   }

   public void printLine(String var1, String var2) throws IOException, IllegalStateException {
      LOG.trace("enter HttpConnection.printLine(String)");
      byte[] var3 = EncodingUtil.getBytes(var1, var2);
      this.writeLine(var3);
   }

   public String readLine() throws IOException, IllegalStateException {
      LOG.trace("enter HttpConnection.readLine()");
      this.assertOpen();
      return HttpParser.readLine(this.inputStream);
   }

   public String readLine(String var1) throws IOException, IllegalStateException {
      LOG.trace("enter HttpConnection.readLine()");
      this.assertOpen();
      return HttpParser.readLine(this.inputStream, var1);
   }

   public void releaseConnection() {
      LOG.trace("enter HttpConnection.releaseConnection()");
      if(this.locked) {
         LOG.debug("Connection is locked.  Call to releaseConnection() ignored.");
      } else if(this.httpConnectionManager != null) {
         LOG.debug("Releasing connection back to connection manager.");
         this.httpConnectionManager.releaseConnection(this);
      } else {
         LOG.warn("HttpConnectionManager is null.  Connection cannot be released.");
      }
   }

   public void setConnectionTimeout(int var1) {
      this.params.setConnectionTimeout(var1);
   }

   public void setHost(String var1) throws IllegalStateException {
      if(var1 == null) {
         throw new IllegalArgumentException("host parameter is null");
      } else {
         this.assertNotOpen();
         this.hostName = var1;
      }
   }

   public void setHttpConnectionManager(HttpConnectionManager var1) {
      this.httpConnectionManager = var1;
   }

   public void setLastResponseInputStream(InputStream var1) {
      this.lastResponseInputStream = var1;
   }

   public void setLocalAddress(InetAddress var1) {
      this.assertNotOpen();
      this.localAddress = var1;
   }

   protected void setLocked(boolean var1) {
      this.locked = var1;
   }

   public void setParams(HttpConnectionParams var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("Parameters may not be null");
      } else {
         this.params = var1;
      }
   }

   public void setPort(int var1) throws IllegalStateException {
      this.assertNotOpen();
      this.portNumber = var1;
   }

   public void setProtocol(Protocol var1) {
      this.assertNotOpen();
      if(var1 == null) {
         throw new IllegalArgumentException("protocol is null");
      } else {
         this.protocolInUse = var1;
      }
   }

   public void setProxyHost(String var1) throws IllegalStateException {
      this.assertNotOpen();
      this.proxyHostName = var1;
   }

   public void setProxyPort(int var1) throws IllegalStateException {
      this.assertNotOpen();
      this.proxyPortNumber = var1;
   }

   public void setSendBufferSize(int var1) throws SocketException {
      this.params.setSendBufferSize(var1);
   }

   public void setSoTimeout(int var1) throws SocketException, IllegalStateException {
      this.params.setSoTimeout(var1);
      if(this.socket != null) {
         this.socket.setSoTimeout(var1);
      }
   }

   public void setSocketTimeout(int var1) throws SocketException, IllegalStateException {
      this.assertOpen();
      if(this.socket != null) {
         this.socket.setSoTimeout(var1);
      }
   }

   public void setStaleCheckingEnabled(boolean var1) {
      this.params.setStaleCheckingEnabled(var1);
   }

   public void setVirtualHost(String var1) throws IllegalStateException {
      this.assertNotOpen();
   }

   public void shutdownOutput() {
      LOG.trace("enter HttpConnection.shutdownOutput()");

      try {
         Class[] var1 = new Class[0];
         Method var2 = this.socket.getClass().getMethod("shutdownOutput", var1);
         Object[] var3 = new Object[0];
         Socket var4 = this.socket;
         var2.invoke(var4, var3);
      } catch (Exception var7) {
         LOG.debug("Unexpected Exception caught", var7);
      }
   }

   public void tunnelCreated() throws IllegalStateException, IOException {
      LOG.trace("enter HttpConnection.tunnelCreated()");
      if(this.isSecure() && this.isProxied()) {
         if(this.usingSecureSocket) {
            throw new IllegalStateException("Already using a secure socket");
         } else {
            if(LOG.isDebugEnabled()) {
               Log var1 = LOG;
               StringBuilder var2 = (new StringBuilder()).append("Secure tunnel to ");
               String var3 = this.hostName;
               StringBuilder var4 = var2.append(var3).append(":");
               int var5 = this.portNumber;
               String var6 = var4.append(var5).toString();
               var1.debug(var6);
            }

            SecureProtocolSocketFactory var7 = (SecureProtocolSocketFactory)this.protocolInUse.getSocketFactory();
            Socket var8 = this.socket;
            String var9 = this.hostName;
            int var10 = this.portNumber;
            Socket var11 = var7.createSocket(var8, var9, var10, (boolean)1);
            this.socket = var11;
            int var12 = this.params.getSendBufferSize();
            if(var12 >= 0) {
               this.socket.setSendBufferSize(var12);
            }

            int var13 = this.params.getReceiveBufferSize();
            if(var13 >= 0) {
               this.socket.setReceiveBufferSize(var13);
            }

            int var14 = this.socket.getSendBufferSize();
            if(var14 > 2048) {
               var14 = 2048;
            }

            int var15 = this.socket.getReceiveBufferSize();
            if(var15 > 2048) {
               var15 = 2048;
            }

            InputStream var16 = this.socket.getInputStream();
            BufferedInputStream var17 = new BufferedInputStream(var16, var15);
            this.inputStream = var17;
            OutputStream var18 = this.socket.getOutputStream();
            BufferedOutputStream var19 = new BufferedOutputStream(var18, var14);
            this.outputStream = var19;
            this.usingSecureSocket = (boolean)1;
            this.tunnelEstablished = (boolean)1;
         }
      } else {
         throw new IllegalStateException("Connection must be secure and proxied to use this feature");
      }
   }

   public void write(byte[] var1) throws IOException, IllegalStateException {
      LOG.trace("enter HttpConnection.write(byte[])");
      int var2 = var1.length;
      this.write(var1, 0, var2);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException, IllegalStateException {
      LOG.trace("enter HttpConnection.write(byte[], int, int)");
      if(var2 < 0) {
         throw new IllegalArgumentException("Array offset may not be negative");
      } else if(var3 < 0) {
         throw new IllegalArgumentException("Array length may not be negative");
      } else {
         int var4 = var2 + var3;
         int var5 = var1.length;
         if(var4 > var5) {
            throw new IllegalArgumentException("Given offset and length exceed the array length");
         } else {
            this.assertOpen();
            this.outputStream.write(var1, var2, var3);
         }
      }
   }

   public void writeLine() throws IOException, IllegalStateException {
      LOG.trace("enter HttpConnection.writeLine()");
      byte[] var1 = CRLF;
      this.write(var1);
   }

   public void writeLine(byte[] var1) throws IOException, IllegalStateException {
      LOG.trace("enter HttpConnection.writeLine(byte[])");
      this.write(var1);
      this.writeLine();
   }
}
