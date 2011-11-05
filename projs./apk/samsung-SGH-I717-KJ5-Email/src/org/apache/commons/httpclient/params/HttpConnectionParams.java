package org.apache.commons.httpclient.params;

import org.apache.commons.httpclient.params.DefaultHttpParams;

public class HttpConnectionParams extends DefaultHttpParams {

   public static final String CONNECTION_TIMEOUT = "http.connection.timeout";
   public static final String SO_LINGER = "http.socket.linger";
   public static final String SO_RCVBUF = "http.socket.receivebuffer";
   public static final String SO_SNDBUF = "http.socket.sendbuffer";
   public static final String SO_TIMEOUT = "http.socket.timeout";
   public static final String STALE_CONNECTION_CHECK = "http.connection.stalecheck";
   public static final String TCP_NODELAY = "http.tcp.nodelay";


   public HttpConnectionParams() {}

   public int getConnectionTimeout() {
      return this.getIntParameter("http.connection.timeout", 0);
   }

   public int getLinger() {
      return this.getIntParameter("http.socket.linger", -1);
   }

   public int getReceiveBufferSize() {
      return this.getIntParameter("http.socket.receivebuffer", -1);
   }

   public int getSendBufferSize() {
      return this.getIntParameter("http.socket.sendbuffer", -1);
   }

   public int getSoTimeout() {
      return this.getIntParameter("http.socket.timeout", 0);
   }

   public boolean getTcpNoDelay() {
      return this.getBooleanParameter("http.tcp.nodelay", (boolean)1);
   }

   public boolean isStaleCheckingEnabled() {
      return this.getBooleanParameter("http.connection.stalecheck", (boolean)1);
   }

   public void setConnectionTimeout(int var1) {
      this.setIntParameter("http.connection.timeout", var1);
   }

   public void setLinger(int var1) {
      this.setIntParameter("http.socket.linger", var1);
   }

   public void setReceiveBufferSize(int var1) {
      this.setIntParameter("http.socket.receivebuffer", var1);
   }

   public void setSendBufferSize(int var1) {
      this.setIntParameter("http.socket.sendbuffer", var1);
   }

   public void setSoTimeout(int var1) {
      this.setIntParameter("http.socket.timeout", var1);
   }

   public void setStaleCheckingEnabled(boolean var1) {
      this.setBooleanParameter("http.connection.stalecheck", var1);
   }

   public void setTcpNoDelay(boolean var1) {
      this.setBooleanParameter("http.tcp.nodelay", var1);
   }
}
