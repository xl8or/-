package org.apache.commons.httpclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.WeakHashMap;
import org.apache.commons.httpclient.ConnectionPoolTimeoutException;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.util.IdleConnectionHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MultiThreadedHttpConnectionManager implements HttpConnectionManager {

   private static WeakHashMap ALL_CONNECTION_MANAGERS = new WeakHashMap();
   public static final int DEFAULT_MAX_HOST_CONNECTIONS = 2;
   public static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 20;
   private static final Log LOG = LogFactory.getLog(MultiThreadedHttpConnectionManager.class);
   private static final ReferenceQueue REFERENCE_QUEUE = new ReferenceQueue();
   private static MultiThreadedHttpConnectionManager.ReferenceQueueThread REFERENCE_QUEUE_THREAD;
   private static final Map REFERENCE_TO_CONNECTION_SOURCE = new HashMap();
   private MultiThreadedHttpConnectionManager.ConnectionPool connectionPool;
   private HttpConnectionManagerParams params;
   private volatile boolean shutdown;


   public MultiThreadedHttpConnectionManager() {
      HttpConnectionManagerParams var1 = new HttpConnectionManagerParams();
      this.params = var1;
      this.shutdown = (boolean)0;
      MultiThreadedHttpConnectionManager.ConnectionPool var2 = new MultiThreadedHttpConnectionManager.ConnectionPool((MultiThreadedHttpConnectionManager.1)null);
      this.connectionPool = var2;
      WeakHashMap var3 = ALL_CONNECTION_MANAGERS;
      synchronized(var3) {
         Object var4 = ALL_CONNECTION_MANAGERS.put(this, (Object)null);
      }
   }

   // $FF: synthetic method
   static Map access$1400() {
      return REFERENCE_TO_CONNECTION_SOURCE;
   }

   private HostConfiguration configurationForConnection(HttpConnection var1) {
      HostConfiguration var2 = new HostConfiguration();
      String var3 = var1.getHost();
      int var4 = var1.getPort();
      Protocol var5 = var1.getProtocol();
      var2.setHost(var3, var4, var5);
      if(var1.getLocalAddress() != null) {
         InetAddress var6 = var1.getLocalAddress();
         var2.setLocalAddress(var6);
      }

      if(var1.getProxyHost() != null) {
         String var7 = var1.getProxyHost();
         int var8 = var1.getProxyPort();
         var2.setProxy(var7, var8);
      }

      return var2;
   }

   private HttpConnection doGetConnection(HostConfiguration param1, long param2) throws ConnectionPoolTimeoutException {
      // $FF: Couldn't be decompiled
   }

   private static void removeReferenceToConnection(MultiThreadedHttpConnectionManager.HttpConnectionWithReference var0) {
      Map var1 = REFERENCE_TO_CONNECTION_SOURCE;
      synchronized(var1) {
         Map var2 = REFERENCE_TO_CONNECTION_SOURCE;
         WeakReference var3 = var0.reference;
         var2.remove(var3);
      }
   }

   public static void shutdownAll() {
      // $FF: Couldn't be decompiled
   }

   private static void shutdownCheckedOutConnections(MultiThreadedHttpConnectionManager.ConnectionPool var0) {
      ArrayList var1 = new ArrayList();
      Map var2 = REFERENCE_TO_CONNECTION_SOURCE;
      synchronized(var2) {
         Iterator var3 = REFERENCE_TO_CONNECTION_SOURCE.keySet().iterator();

         while(true) {
            if(!var3.hasNext()) {
               break;
            }

            Reference var4 = (Reference)var3.next();
            if(((MultiThreadedHttpConnectionManager.ConnectionSource)REFERENCE_TO_CONNECTION_SOURCE.get(var4)).connectionPool == var0) {
               var3.remove();
               HttpConnection var5 = (HttpConnection)var4.get();
               if(var5 != null) {
                  var1.add(var5);
               }
            }
         }
      }

      Iterator var8 = var1.iterator();

      while(var8.hasNext()) {
         HttpConnection var9 = (HttpConnection)var8.next();
         var9.close();
         var9.setHttpConnectionManager((HttpConnectionManager)null);
         var9.releaseConnection();
      }

   }

   private static void storeReferenceToConnection(MultiThreadedHttpConnectionManager.HttpConnectionWithReference var0, HostConfiguration var1, MultiThreadedHttpConnectionManager.ConnectionPool var2) {
      MultiThreadedHttpConnectionManager.ConnectionSource var3 = new MultiThreadedHttpConnectionManager.ConnectionSource((MultiThreadedHttpConnectionManager.1)null);
      var3.connectionPool = var2;
      var3.hostConfiguration = var1;
      Map var4 = REFERENCE_TO_CONNECTION_SOURCE;
      synchronized(var4) {
         if(REFERENCE_QUEUE_THREAD == null) {
            REFERENCE_QUEUE_THREAD = new MultiThreadedHttpConnectionManager.ReferenceQueueThread();
            REFERENCE_QUEUE_THREAD.start();
         }

         Map var5 = REFERENCE_TO_CONNECTION_SOURCE;
         WeakReference var6 = var0.reference;
         var5.put(var6, var3);
      }
   }

   public void closeIdleConnections(long var1) {
      this.connectionPool.closeIdleConnections(var1);
      this.deleteClosedConnections();
   }

   public void deleteClosedConnections() {
      this.connectionPool.deleteClosedConnections();
   }

   public HttpConnection getConnection(HostConfiguration var1) {
      while(true) {
         try {
            HttpConnection var2 = this.getConnectionWithTimeout(var1, 0L);
            return var2;
         } catch (ConnectionPoolTimeoutException var4) {
            LOG.debug("Unexpected exception while waiting for connection", var4);
         }
      }
   }

   public HttpConnection getConnection(HostConfiguration var1, long var2) throws HttpException {
      LOG.trace("enter HttpConnectionManager.getConnection(HostConfiguration, long)");

      try {
         HttpConnection var4 = this.getConnectionWithTimeout(var1, var2);
         return var4;
      } catch (ConnectionPoolTimeoutException var6) {
         String var5 = var6.getMessage();
         throw new HttpException(var5);
      }
   }

   public HttpConnection getConnectionWithTimeout(HostConfiguration var1, long var2) throws ConnectionPoolTimeoutException {
      LOG.trace("enter HttpConnectionManager.getConnectionWithTimeout(HostConfiguration, long)");
      if(var1 == null) {
         throw new IllegalArgumentException("hostConfiguration is null");
      } else {
         if(LOG.isDebugEnabled()) {
            Log var4 = LOG;
            String var5 = "HttpConnectionManager.getConnection:  config = " + var1 + ", timeout = " + var2;
            var4.debug(var5);
         }

         HttpConnection var6 = this.doGetConnection(var1, var2);
         return new MultiThreadedHttpConnectionManager.HttpConnectionAdapter(var6);
      }
   }

   public int getConnectionsInPool() {
      MultiThreadedHttpConnectionManager.ConnectionPool var1 = this.connectionPool;
      synchronized(var1) {
         int var2 = this.connectionPool.numConnections;
         return var2;
      }
   }

   public int getConnectionsInPool(HostConfiguration var1) {
      MultiThreadedHttpConnectionManager.ConnectionPool var2 = this.connectionPool;
      synchronized(var2) {
         MultiThreadedHttpConnectionManager.HostConnectionPool var3 = this.connectionPool.getHostPool(var1, (boolean)0);
         int var4;
         if(var3 != null) {
            var4 = var3.numConnections;
         } else {
            var4 = 0;
         }

         return var4;
      }
   }

   public int getConnectionsInUse() {
      return this.getConnectionsInPool();
   }

   public int getConnectionsInUse(HostConfiguration var1) {
      return this.getConnectionsInPool(var1);
   }

   public int getMaxConnectionsPerHost() {
      return this.params.getDefaultMaxConnectionsPerHost();
   }

   public int getMaxTotalConnections() {
      return this.params.getMaxTotalConnections();
   }

   public HttpConnectionManagerParams getParams() {
      return this.params;
   }

   public boolean isConnectionStaleCheckingEnabled() {
      return this.params.isStaleCheckingEnabled();
   }

   public void releaseConnection(HttpConnection var1) {
      LOG.trace("enter HttpConnectionManager.releaseConnection(HttpConnection)");
      if(var1 instanceof MultiThreadedHttpConnectionManager.HttpConnectionAdapter) {
         var1 = ((MultiThreadedHttpConnectionManager.HttpConnectionAdapter)var1).getWrappedConnection();
      }

      SimpleHttpConnectionManager.finishLastResponse(var1);
      this.connectionPool.freeConnection(var1);
   }

   public void setConnectionStaleCheckingEnabled(boolean var1) {
      this.params.setStaleCheckingEnabled(var1);
   }

   public void setMaxConnectionsPerHost(int var1) {
      this.params.setDefaultMaxConnectionsPerHost(var1);
   }

   public void setMaxTotalConnections(int var1) {
      this.params.setMaxTotalConnections(var1);
   }

   public void setParams(HttpConnectionManagerParams var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("Parameters may not be null");
      } else {
         this.params = var1;
      }
   }

   public void shutdown() {
      // $FF: Couldn't be decompiled
   }

   // $FF: synthetic class
   static class 1 {
   }

   private static class ConnectionSource {

      public MultiThreadedHttpConnectionManager.ConnectionPool connectionPool;
      public HostConfiguration hostConfiguration;


      private ConnectionSource() {}

      // $FF: synthetic method
      ConnectionSource(MultiThreadedHttpConnectionManager.1 var1) {
         this();
      }
   }

   private static class HttpConnectionAdapter extends HttpConnection {

      private HttpConnection wrappedConnection;


      public HttpConnectionAdapter(HttpConnection var1) {
         String var2 = var1.getHost();
         int var3 = var1.getPort();
         Protocol var4 = var1.getProtocol();
         super(var2, var3, var4);
         this.wrappedConnection = var1;
      }

      public void close() {
         if(this.hasConnection()) {
            this.wrappedConnection.close();
         }
      }

      public boolean closeIfStale() throws IOException {
         boolean var1;
         if(this.hasConnection()) {
            var1 = this.wrappedConnection.closeIfStale();
         } else {
            var1 = false;
         }

         return var1;
      }

      public void flushRequestOutputStream() throws IOException {
         if(this.hasConnection()) {
            this.wrappedConnection.flushRequestOutputStream();
         } else {
            throw new IllegalStateException("Connection has been released");
         }
      }

      public String getHost() {
         String var1;
         if(this.hasConnection()) {
            var1 = this.wrappedConnection.getHost();
         } else {
            var1 = null;
         }

         return var1;
      }

      public HttpConnectionManager getHttpConnectionManager() {
         HttpConnectionManager var1;
         if(this.hasConnection()) {
            var1 = this.wrappedConnection.getHttpConnectionManager();
         } else {
            var1 = null;
         }

         return var1;
      }

      public InputStream getLastResponseInputStream() {
         InputStream var1;
         if(this.hasConnection()) {
            var1 = this.wrappedConnection.getLastResponseInputStream();
         } else {
            var1 = null;
         }

         return var1;
      }

      public InetAddress getLocalAddress() {
         InetAddress var1;
         if(this.hasConnection()) {
            var1 = this.wrappedConnection.getLocalAddress();
         } else {
            var1 = null;
         }

         return var1;
      }

      public HttpConnectionParams getParams() {
         if(this.hasConnection()) {
            return this.wrappedConnection.getParams();
         } else {
            throw new IllegalStateException("Connection has been released");
         }
      }

      public int getPort() {
         int var1;
         if(this.hasConnection()) {
            var1 = this.wrappedConnection.getPort();
         } else {
            var1 = -1;
         }

         return var1;
      }

      public Protocol getProtocol() {
         Protocol var1;
         if(this.hasConnection()) {
            var1 = this.wrappedConnection.getProtocol();
         } else {
            var1 = null;
         }

         return var1;
      }

      public String getProxyHost() {
         String var1;
         if(this.hasConnection()) {
            var1 = this.wrappedConnection.getProxyHost();
         } else {
            var1 = null;
         }

         return var1;
      }

      public int getProxyPort() {
         int var1;
         if(this.hasConnection()) {
            var1 = this.wrappedConnection.getProxyPort();
         } else {
            var1 = -1;
         }

         return var1;
      }

      public OutputStream getRequestOutputStream() throws IOException, IllegalStateException {
         OutputStream var1;
         if(this.hasConnection()) {
            var1 = this.wrappedConnection.getRequestOutputStream();
         } else {
            var1 = null;
         }

         return var1;
      }

      public InputStream getResponseInputStream() throws IOException, IllegalStateException {
         InputStream var1;
         if(this.hasConnection()) {
            var1 = this.wrappedConnection.getResponseInputStream();
         } else {
            var1 = null;
         }

         return var1;
      }

      public int getSendBufferSize() throws SocketException {
         if(this.hasConnection()) {
            return this.wrappedConnection.getSendBufferSize();
         } else {
            throw new IllegalStateException("Connection has been released");
         }
      }

      public int getSoTimeout() throws SocketException {
         if(this.hasConnection()) {
            return this.wrappedConnection.getSoTimeout();
         } else {
            throw new IllegalStateException("Connection has been released");
         }
      }

      public String getVirtualHost() {
         if(this.hasConnection()) {
            return this.wrappedConnection.getVirtualHost();
         } else {
            throw new IllegalStateException("Connection has been released");
         }
      }

      HttpConnection getWrappedConnection() {
         return this.wrappedConnection;
      }

      protected boolean hasConnection() {
         boolean var1;
         if(this.wrappedConnection != null) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public boolean isOpen() {
         boolean var1;
         if(this.hasConnection()) {
            var1 = this.wrappedConnection.isOpen();
         } else {
            var1 = false;
         }

         return var1;
      }

      public boolean isProxied() {
         boolean var1;
         if(this.hasConnection()) {
            var1 = this.wrappedConnection.isProxied();
         } else {
            var1 = false;
         }

         return var1;
      }

      public boolean isResponseAvailable() throws IOException {
         boolean var1;
         if(this.hasConnection()) {
            var1 = this.wrappedConnection.isResponseAvailable();
         } else {
            var1 = false;
         }

         return var1;
      }

      public boolean isResponseAvailable(int var1) throws IOException {
         boolean var2;
         if(this.hasConnection()) {
            var2 = this.wrappedConnection.isResponseAvailable(var1);
         } else {
            var2 = false;
         }

         return var2;
      }

      public boolean isSecure() {
         boolean var1;
         if(this.hasConnection()) {
            var1 = this.wrappedConnection.isSecure();
         } else {
            var1 = false;
         }

         return var1;
      }

      public boolean isStaleCheckingEnabled() {
         boolean var1;
         if(this.hasConnection()) {
            var1 = this.wrappedConnection.isStaleCheckingEnabled();
         } else {
            var1 = false;
         }

         return var1;
      }

      public boolean isTransparent() {
         boolean var1;
         if(this.hasConnection()) {
            var1 = this.wrappedConnection.isTransparent();
         } else {
            var1 = false;
         }

         return var1;
      }

      public void open() throws IOException {
         if(this.hasConnection()) {
            this.wrappedConnection.open();
         } else {
            throw new IllegalStateException("Connection has been released");
         }
      }

      public void print(String var1) throws IOException, IllegalStateException {
         if(this.hasConnection()) {
            this.wrappedConnection.print(var1);
         } else {
            throw new IllegalStateException("Connection has been released");
         }
      }

      public void print(String var1, String var2) throws IOException, IllegalStateException {
         if(this.hasConnection()) {
            this.wrappedConnection.print(var1, var2);
         } else {
            throw new IllegalStateException("Connection has been released");
         }
      }

      public void printLine() throws IOException, IllegalStateException {
         if(this.hasConnection()) {
            this.wrappedConnection.printLine();
         } else {
            throw new IllegalStateException("Connection has been released");
         }
      }

      public void printLine(String var1) throws IOException, IllegalStateException {
         if(this.hasConnection()) {
            this.wrappedConnection.printLine(var1);
         } else {
            throw new IllegalStateException("Connection has been released");
         }
      }

      public void printLine(String var1, String var2) throws IOException, IllegalStateException {
         if(this.hasConnection()) {
            this.wrappedConnection.printLine(var1, var2);
         } else {
            throw new IllegalStateException("Connection has been released");
         }
      }

      public String readLine() throws IOException, IllegalStateException {
         if(this.hasConnection()) {
            return this.wrappedConnection.readLine();
         } else {
            throw new IllegalStateException("Connection has been released");
         }
      }

      public String readLine(String var1) throws IOException, IllegalStateException {
         if(this.hasConnection()) {
            return this.wrappedConnection.readLine(var1);
         } else {
            throw new IllegalStateException("Connection has been released");
         }
      }

      public void releaseConnection() {
         if(!this.isLocked()) {
            if(this.hasConnection()) {
               HttpConnection var1 = this.wrappedConnection;
               this.wrappedConnection = null;
               var1.releaseConnection();
            }
         }
      }

      public void setConnectionTimeout(int var1) {
         if(this.hasConnection()) {
            this.wrappedConnection.setConnectionTimeout(var1);
         }
      }

      public void setHost(String var1) throws IllegalStateException {
         if(this.hasConnection()) {
            this.wrappedConnection.setHost(var1);
         }
      }

      public void setHttpConnectionManager(HttpConnectionManager var1) {
         if(this.hasConnection()) {
            this.wrappedConnection.setHttpConnectionManager(var1);
         }
      }

      public void setLastResponseInputStream(InputStream var1) {
         if(this.hasConnection()) {
            this.wrappedConnection.setLastResponseInputStream(var1);
         }
      }

      public void setLocalAddress(InetAddress var1) {
         if(this.hasConnection()) {
            this.wrappedConnection.setLocalAddress(var1);
         } else {
            throw new IllegalStateException("Connection has been released");
         }
      }

      public void setParams(HttpConnectionParams var1) {
         if(this.hasConnection()) {
            this.wrappedConnection.setParams(var1);
         } else {
            throw new IllegalStateException("Connection has been released");
         }
      }

      public void setPort(int var1) throws IllegalStateException {
         if(this.hasConnection()) {
            this.wrappedConnection.setPort(var1);
         }
      }

      public void setProtocol(Protocol var1) {
         if(this.hasConnection()) {
            this.wrappedConnection.setProtocol(var1);
         }
      }

      public void setProxyHost(String var1) throws IllegalStateException {
         if(this.hasConnection()) {
            this.wrappedConnection.setProxyHost(var1);
         }
      }

      public void setProxyPort(int var1) throws IllegalStateException {
         if(this.hasConnection()) {
            this.wrappedConnection.setProxyPort(var1);
         }
      }

      public void setSendBufferSize(int var1) throws SocketException {
         if(this.hasConnection()) {
            this.wrappedConnection.setSendBufferSize(var1);
         } else {
            throw new IllegalStateException("Connection has been released");
         }
      }

      public void setSoTimeout(int var1) throws SocketException, IllegalStateException {
         if(this.hasConnection()) {
            this.wrappedConnection.setSoTimeout(var1);
         }
      }

      public void setSocketTimeout(int var1) throws SocketException, IllegalStateException {
         if(this.hasConnection()) {
            this.wrappedConnection.setSocketTimeout(var1);
         } else {
            throw new IllegalStateException("Connection has been released");
         }
      }

      public void setStaleCheckingEnabled(boolean var1) {
         if(this.hasConnection()) {
            this.wrappedConnection.setStaleCheckingEnabled(var1);
         } else {
            throw new IllegalStateException("Connection has been released");
         }
      }

      public void setVirtualHost(String var1) throws IllegalStateException {
         if(this.hasConnection()) {
            this.wrappedConnection.setVirtualHost(var1);
         } else {
            throw new IllegalStateException("Connection has been released");
         }
      }

      public void shutdownOutput() {
         if(this.hasConnection()) {
            this.wrappedConnection.shutdownOutput();
         }
      }

      public void tunnelCreated() throws IllegalStateException, IOException {
         if(this.hasConnection()) {
            this.wrappedConnection.tunnelCreated();
         }
      }

      public void write(byte[] var1) throws IOException, IllegalStateException {
         if(this.hasConnection()) {
            this.wrappedConnection.write(var1);
         } else {
            throw new IllegalStateException("Connection has been released");
         }
      }

      public void write(byte[] var1, int var2, int var3) throws IOException, IllegalStateException {
         if(this.hasConnection()) {
            this.wrappedConnection.write(var1, var2, var3);
         } else {
            throw new IllegalStateException("Connection has been released");
         }
      }

      public void writeLine() throws IOException, IllegalStateException {
         if(this.hasConnection()) {
            this.wrappedConnection.writeLine();
         } else {
            throw new IllegalStateException("Connection has been released");
         }
      }

      public void writeLine(byte[] var1) throws IOException, IllegalStateException {
         if(this.hasConnection()) {
            this.wrappedConnection.writeLine(var1);
         } else {
            throw new IllegalStateException("Connection has been released");
         }
      }
   }

   private class ConnectionPool {

      private LinkedList freeConnections;
      private IdleConnectionHandler idleConnectionHandler;
      private final Map mapHosts;
      private int numConnections;
      private LinkedList waitingThreads;


      private ConnectionPool() {
         LinkedList var2 = new LinkedList();
         this.freeConnections = var2;
         LinkedList var3 = new LinkedList();
         this.waitingThreads = var3;
         HashMap var4 = new HashMap();
         this.mapHosts = var4;
         IdleConnectionHandler var5 = new IdleConnectionHandler();
         this.idleConnectionHandler = var5;
         this.numConnections = 0;
      }

      // $FF: synthetic method
      ConnectionPool(MultiThreadedHttpConnectionManager.1 var2) {
         this();
      }

      // $FF: synthetic method
      static LinkedList access$300(MultiThreadedHttpConnectionManager.ConnectionPool var0) {
         return var0.freeConnections;
      }

      // $FF: synthetic method
      static LinkedList access$500(MultiThreadedHttpConnectionManager.ConnectionPool var0) {
         return var0.waitingThreads;
      }

      private void deleteConnection(HttpConnection var1) {
         synchronized(this){}

         try {
            HostConfiguration var2 = MultiThreadedHttpConnectionManager.this.configurationForConnection(var1);
            if(MultiThreadedHttpConnectionManager.LOG.isDebugEnabled()) {
               Log var3 = MultiThreadedHttpConnectionManager.LOG;
               String var4 = "Reclaiming connection, hostConfig=" + var2;
               var3.debug(var4);
            }

            var1.close();
            MultiThreadedHttpConnectionManager.HostConnectionPool var5 = this.getHostPool(var2, (boolean)1);
            var5.freeConnections.remove(var1);
            int var7 = var5.numConnections - 1;
            var5.numConnections = var7;
            int var8 = this.numConnections - 1;
            this.numConnections = var8;
            if(var5.numConnections == 0 && var5.waitingThreads.isEmpty()) {
               this.mapHosts.remove(var2);
            }

            this.idleConnectionHandler.remove(var1);
         } finally {
            ;
         }

      }

      public void closeIdleConnections(long var1) {
         synchronized(this){}

         try {
            this.idleConnectionHandler.closeIdleConnections(var1);
         } finally {
            ;
         }

      }

      public HttpConnection createConnection(HostConfiguration var1) {
         synchronized(this){}

         MultiThreadedHttpConnectionManager.HttpConnectionWithReference var5;
         try {
            MultiThreadedHttpConnectionManager.HostConnectionPool var2 = this.getHostPool(var1, (boolean)1);
            if(MultiThreadedHttpConnectionManager.LOG.isDebugEnabled()) {
               Log var3 = MultiThreadedHttpConnectionManager.LOG;
               String var4 = "Allocating new connection, hostConfig=" + var1;
               var3.debug(var4);
            }

            var5 = new MultiThreadedHttpConnectionManager.HttpConnectionWithReference(var1);
            HttpConnectionParams var6 = var5.getParams();
            HttpConnectionManagerParams var7 = MultiThreadedHttpConnectionManager.this.params;
            var6.setDefaults(var7);
            MultiThreadedHttpConnectionManager var8 = MultiThreadedHttpConnectionManager.this;
            var5.setHttpConnectionManager(var8);
            int var9 = this.numConnections + 1;
            this.numConnections = var9;
            int var10 = var2.numConnections + 1;
            var2.numConnections = var10;
            MultiThreadedHttpConnectionManager.storeReferenceToConnection(var5, var1, this);
         } finally {
            ;
         }

         return var5;
      }

      public void deleteClosedConnections() {
         synchronized(this){}

         try {
            Iterator var1 = this.freeConnections.iterator();

            while(var1.hasNext()) {
               HttpConnection var2 = (HttpConnection)var1.next();
               if(!var2.isOpen()) {
                  var1.remove();
                  this.deleteConnection(var2);
               }
            }
         } finally {
            ;
         }

      }

      public void deleteLeastUsedConnection() {
         synchronized(this){}

         try {
            HttpConnection var1 = (HttpConnection)this.freeConnections.removeFirst();
            if(var1 != null) {
               this.deleteConnection(var1);
            } else if(MultiThreadedHttpConnectionManager.LOG.isDebugEnabled()) {
               MultiThreadedHttpConnectionManager.LOG.debug("Attempted to reclaim an unused connection but there were none.");
            }
         } finally {
            ;
         }

      }

      public void freeConnection(HttpConnection var1) {
         HostConfiguration var2 = MultiThreadedHttpConnectionManager.this.configurationForConnection(var1);
         if(MultiThreadedHttpConnectionManager.LOG.isDebugEnabled()) {
            Log var3 = MultiThreadedHttpConnectionManager.LOG;
            String var4 = "Freeing connection, hostConfig=" + var2;
            var3.debug(var4);
         }

         synchronized(this) {
            if(MultiThreadedHttpConnectionManager.this.shutdown) {
               var1.close();
            } else {
               MultiThreadedHttpConnectionManager.HostConnectionPool var5 = this.getHostPool(var2, (boolean)1);
               var5.freeConnections.add(var1);
               if(var5.numConnections == 0) {
                  Log var7 = MultiThreadedHttpConnectionManager.LOG;
                  String var8 = "Host connection pool not found, hostConfig=" + var2;
                  var7.error(var8);
                  var5.numConnections = 1;
               }

               this.freeConnections.add(var1);
               MultiThreadedHttpConnectionManager.removeReferenceToConnection((MultiThreadedHttpConnectionManager.HttpConnectionWithReference)var1);
               if(this.numConnections == 0) {
                  Log var10 = MultiThreadedHttpConnectionManager.LOG;
                  String var11 = "Host connection pool not found, hostConfig=" + var2;
                  var10.error(var11);
                  this.numConnections = 1;
               }

               this.idleConnectionHandler.add(var1);
               this.notifyWaitingThread(var5);
            }
         }
      }

      public HttpConnection getFreeConnection(HostConfiguration var1) {
         synchronized(this){}
         MultiThreadedHttpConnectionManager.HttpConnectionWithReference var2 = null;

         try {
            MultiThreadedHttpConnectionManager.HostConnectionPool var3 = this.getHostPool(var1, (boolean)0);
            if(var3 != null && var3.freeConnections.size() > 0) {
               var2 = (MultiThreadedHttpConnectionManager.HttpConnectionWithReference)var3.freeConnections.removeLast();
               this.freeConnections.remove(var2);
               MultiThreadedHttpConnectionManager.storeReferenceToConnection(var2, var1, this);
               if(MultiThreadedHttpConnectionManager.LOG.isDebugEnabled()) {
                  Log var5 = MultiThreadedHttpConnectionManager.LOG;
                  String var6 = "Getting free connection, hostConfig=" + var1;
                  var5.debug(var6);
               }

               this.idleConnectionHandler.remove(var2);
            } else if(MultiThreadedHttpConnectionManager.LOG.isDebugEnabled()) {
               Log var7 = MultiThreadedHttpConnectionManager.LOG;
               String var8 = "There were no free connections to get, hostConfig=" + var1;
               var7.debug(var8);
            }
         } finally {
            ;
         }

         return var2;
      }

      public MultiThreadedHttpConnectionManager.HostConnectionPool getHostPool(HostConfiguration var1, boolean var2) {
         synchronized(this){}

         MultiThreadedHttpConnectionManager.HostConnectionPool var3;
         try {
            MultiThreadedHttpConnectionManager.LOG.trace("enter HttpConnectionManager.ConnectionPool.getHostPool(HostConfiguration)");
            var3 = (MultiThreadedHttpConnectionManager.HostConnectionPool)this.mapHosts.get(var1);
            if(var3 == null && var2) {
               var3 = new MultiThreadedHttpConnectionManager.HostConnectionPool((MultiThreadedHttpConnectionManager.1)null);
               var3.hostConfiguration = var1;
               this.mapHosts.put(var1, var3);
            }
         } finally {
            ;
         }

         return var3;
      }

      public void handleLostConnection(HostConfiguration var1) {
         synchronized(this){}

         try {
            MultiThreadedHttpConnectionManager.HostConnectionPool var2 = this.getHostPool(var1, (boolean)1);
            int var3 = var2.numConnections - 1;
            var2.numConnections = var3;
            if(var2.numConnections == 0 && var2.waitingThreads.isEmpty()) {
               this.mapHosts.remove(var1);
            }

            int var5 = this.numConnections - 1;
            this.numConnections = var5;
            this.notifyWaitingThread(var1);
         } finally {
            ;
         }

      }

      public void notifyWaitingThread(HostConfiguration var1) {
         synchronized(this){}

         try {
            MultiThreadedHttpConnectionManager.HostConnectionPool var2 = this.getHostPool(var1, (boolean)1);
            this.notifyWaitingThread(var2);
         } finally {
            ;
         }

      }

      public void notifyWaitingThread(MultiThreadedHttpConnectionManager.HostConnectionPool var1) {
         synchronized(this){}
         MultiThreadedHttpConnectionManager.WaitingThread var2 = null;

         try {
            if(var1.waitingThreads.size() > 0) {
               if(MultiThreadedHttpConnectionManager.LOG.isDebugEnabled()) {
                  Log var3 = MultiThreadedHttpConnectionManager.LOG;
                  StringBuilder var4 = (new StringBuilder()).append("Notifying thread waiting on host pool, hostConfig=");
                  HostConfiguration var5 = var1.hostConfiguration;
                  String var6 = var4.append(var5).toString();
                  var3.debug(var6);
               }

               var2 = (MultiThreadedHttpConnectionManager.WaitingThread)var1.waitingThreads.removeFirst();
               this.waitingThreads.remove(var2);
            } else if(this.waitingThreads.size() > 0) {
               if(MultiThreadedHttpConnectionManager.LOG.isDebugEnabled()) {
                  MultiThreadedHttpConnectionManager.LOG.debug("No-one waiting on host pool, notifying next waiting thread.");
               }

               var2 = (MultiThreadedHttpConnectionManager.WaitingThread)this.waitingThreads.removeFirst();
               boolean var8 = var2.hostConnectionPool.waitingThreads.remove(var2);
            } else if(MultiThreadedHttpConnectionManager.LOG.isDebugEnabled()) {
               MultiThreadedHttpConnectionManager.LOG.debug("Notifying no-one, there are no waiting threads");
            }

            if(var2 != null) {
               var2.interruptedByConnectionPool = (boolean)1;
               var2.thread.interrupt();
            }
         } finally {
            ;
         }

      }

      public void shutdown() {
         synchronized(this){}

         try {
            Iterator var1 = this.freeConnections.iterator();

            while(var1.hasNext()) {
               HttpConnection var2 = (HttpConnection)var1.next();
               var1.remove();
               var2.close();
            }

            MultiThreadedHttpConnectionManager.shutdownCheckedOutConnections(this);
            var1 = this.waitingThreads.iterator();

            while(var1.hasNext()) {
               MultiThreadedHttpConnectionManager.WaitingThread var4 = (MultiThreadedHttpConnectionManager.WaitingThread)var1.next();
               var1.remove();
               var4.interruptedByConnectionPool = (boolean)1;
               var4.thread.interrupt();
            }

            this.mapHosts.clear();
            this.idleConnectionHandler.removeAll();
         } finally {
            ;
         }
      }
   }

   private static class HttpConnectionWithReference extends HttpConnection {

      public WeakReference reference;


      public HttpConnectionWithReference(HostConfiguration var1) {
         super(var1);
         ReferenceQueue var2 = MultiThreadedHttpConnectionManager.REFERENCE_QUEUE;
         WeakReference var3 = new WeakReference(this, var2);
         this.reference = var3;
      }
   }

   private static class HostConnectionPool {

      public LinkedList freeConnections;
      public HostConfiguration hostConfiguration;
      public int numConnections;
      public LinkedList waitingThreads;


      private HostConnectionPool() {
         LinkedList var1 = new LinkedList();
         this.freeConnections = var1;
         LinkedList var2 = new LinkedList();
         this.waitingThreads = var2;
         this.numConnections = 0;
      }

      // $FF: synthetic method
      HostConnectionPool(MultiThreadedHttpConnectionManager.1 var1) {
         this();
      }
   }

   private static class ReferenceQueueThread extends Thread {

      private volatile boolean shutdown = 0;


      public ReferenceQueueThread() {
         this.setDaemon((boolean)1);
         this.setName("MultiThreadedHttpConnectionManager cleanup");
      }

      private void handleReference(Reference param1) {
         // $FF: Couldn't be decompiled
      }

      public void run() {
         while(!this.shutdown) {
            try {
               Reference var1 = MultiThreadedHttpConnectionManager.REFERENCE_QUEUE.remove();
               if(var1 != null) {
                  this.handleReference(var1);
               }
            } catch (InterruptedException var3) {
               MultiThreadedHttpConnectionManager.LOG.debug("ReferenceQueueThread interrupted", var3);
            }
         }

      }

      public void shutdown() {
         this.shutdown = (boolean)1;
         this.interrupt();
      }
   }

   private static class WaitingThread {

      public MultiThreadedHttpConnectionManager.HostConnectionPool hostConnectionPool;
      public boolean interruptedByConnectionPool;
      public Thread thread;


      private WaitingThread() {
         this.interruptedByConnectionPool = (boolean)0;
      }

      // $FF: synthetic method
      WaitingThread(MultiThreadedHttpConnectionManager.1 var1) {
         this();
      }
   }
}
