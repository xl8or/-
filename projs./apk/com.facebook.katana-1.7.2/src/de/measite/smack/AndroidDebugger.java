package de.measite.smack;

import android.util.Log;
import java.io.Reader;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.debugger.SmackDebugger;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.util.ObservableReader;
import org.jivesoftware.smack.util.ObservableWriter;
import org.jivesoftware.smack.util.ReaderListener;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smack.util.WriterListener;

public class AndroidDebugger implements SmackDebugger {

   public static boolean printInterpreted = 0;
   private ConnectionListener connListener;
   private Connection connection;
   private SimpleDateFormat dateFormatter;
   private PacketListener listener;
   private Reader reader;
   private ReaderListener readerListener;
   private Writer writer;
   private WriterListener writerListener;


   public AndroidDebugger(Connection var1, Writer var2, Reader var3) {
      SimpleDateFormat var4 = new SimpleDateFormat("hh:mm:ss aaa");
      this.dateFormatter = var4;
      this.connection = null;
      this.listener = null;
      this.connListener = null;
      this.connection = var1;
      this.writer = var2;
      this.reader = var3;
      this.createDebug();
   }

   private void createDebug() {
      Reader var1 = this.reader;
      ObservableReader var2 = new ObservableReader(var1);
      AndroidDebugger.1 var3 = new AndroidDebugger.1();
      this.readerListener = var3;
      ReaderListener var4 = this.readerListener;
      var2.addReaderListener(var4);
      Writer var5 = this.writer;
      ObservableWriter var6 = new ObservableWriter(var5);
      AndroidDebugger.2 var7 = new AndroidDebugger.2();
      this.writerListener = var7;
      WriterListener var8 = this.writerListener;
      var6.addWriterListener(var8);
      this.reader = var2;
      this.writer = var6;
      AndroidDebugger.3 var9 = new AndroidDebugger.3();
      this.listener = var9;
      AndroidDebugger.4 var10 = new AndroidDebugger.4();
      this.connListener = var10;
   }

   public Reader getReader() {
      return this.reader;
   }

   public PacketListener getReaderListener() {
      return this.listener;
   }

   public Writer getWriter() {
      return this.writer;
   }

   public PacketListener getWriterListener() {
      return null;
   }

   public Reader newConnectionReader(Reader var1) {
      ObservableReader var2 = (ObservableReader)this.reader;
      ReaderListener var3 = this.readerListener;
      var2.removeReaderListener(var3);
      ObservableReader var4 = new ObservableReader(var1);
      ReaderListener var5 = this.readerListener;
      var4.addReaderListener(var5);
      this.reader = var4;
      return this.reader;
   }

   public Writer newConnectionWriter(Writer var1) {
      ObservableWriter var2 = (ObservableWriter)this.writer;
      WriterListener var3 = this.writerListener;
      var2.removeWriterListener(var3);
      ObservableWriter var4 = new ObservableWriter(var1);
      WriterListener var5 = this.writerListener;
      var4.addWriterListener(var5);
      this.writer = var4;
      return this.writer;
   }

   public void userHasLogged(String var1) {
      String var2 = StringUtils.parseName(var1);
      boolean var3 = "".equals(var2);
      StringBuilder var4 = (new StringBuilder()).append("User logged (");
      int var5 = this.connection.hashCode();
      StringBuilder var6 = var4.append(var5).append("): ");
      String var7;
      if(var3) {
         var7 = "";
      } else {
         var7 = StringUtils.parseBareAddress(var1);
      }

      StringBuilder var8 = var6.append(var7).append("@");
      String var9 = this.connection.getServiceName();
      StringBuilder var10 = var8.append(var9).append(":");
      int var11 = this.connection.getPort();
      String var12 = var10.append(var11).toString();
      StringBuilder var13 = (new StringBuilder()).append(var12).append("/");
      String var14 = StringUtils.parseResource(var1);
      String var15 = var13.append(var14).toString();
      int var16 = Log.d("SMACK", var15);
      Connection var17 = this.connection;
      ConnectionListener var18 = this.connListener;
      var17.addConnectionListener(var18);
   }

   class 4 implements ConnectionListener {

      4() {}

      public void connectionClosed() {
         StringBuilder var1 = new StringBuilder();
         SimpleDateFormat var2 = AndroidDebugger.this.dateFormatter;
         Date var3 = new Date();
         String var4 = var2.format(var3);
         StringBuilder var5 = var1.append(var4).append(" Connection closed (");
         int var6 = AndroidDebugger.this.connection.hashCode();
         String var7 = var5.append(var6).append(")").toString();
         int var8 = Log.d("SMACK", var7);
      }

      public void connectionClosedOnError(Exception var1) {
         StringBuilder var2 = new StringBuilder();
         SimpleDateFormat var3 = AndroidDebugger.this.dateFormatter;
         Date var4 = new Date();
         String var5 = var3.format(var4);
         StringBuilder var6 = var2.append(var5).append(" Connection closed due to an exception (");
         int var7 = AndroidDebugger.this.connection.hashCode();
         String var8 = var6.append(var7).append(")").toString();
         int var9 = Log.d("SMACK", var8);
         var1.printStackTrace();
      }

      public void reconnectingIn(int var1) {
         StringBuilder var2 = new StringBuilder();
         SimpleDateFormat var3 = AndroidDebugger.this.dateFormatter;
         Date var4 = new Date();
         String var5 = var3.format(var4);
         StringBuilder var6 = var2.append(var5).append(" Connection (");
         int var7 = AndroidDebugger.this.connection.hashCode();
         String var8 = var6.append(var7).append(") will reconnect in ").append(var1).toString();
         int var9 = Log.d("SMACK", var8);
      }

      public void reconnectionFailed(Exception var1) {
         StringBuilder var2 = new StringBuilder();
         SimpleDateFormat var3 = AndroidDebugger.this.dateFormatter;
         Date var4 = new Date();
         String var5 = var3.format(var4);
         StringBuilder var6 = var2.append(var5).append(" Reconnection failed due to an exception (");
         int var7 = AndroidDebugger.this.connection.hashCode();
         String var8 = var6.append(var7).append(")").toString();
         int var9 = Log.d("SMACK", var8);
         var1.printStackTrace();
      }

      public void reconnectionSuccessful() {
         StringBuilder var1 = new StringBuilder();
         SimpleDateFormat var2 = AndroidDebugger.this.dateFormatter;
         Date var3 = new Date();
         String var4 = var2.format(var3);
         StringBuilder var5 = var1.append(var4).append(" Connection reconnected (");
         int var6 = AndroidDebugger.this.connection.hashCode();
         String var7 = var5.append(var6).append(")").toString();
         int var8 = Log.d("SMACK", var7);
      }
   }

   class 3 implements PacketListener {

      3() {}

      public void processPacket(Packet var1) {
         if(AndroidDebugger.printInterpreted) {
            StringBuilder var2 = new StringBuilder();
            SimpleDateFormat var3 = AndroidDebugger.this.dateFormatter;
            Date var4 = new Date();
            String var5 = var3.format(var4);
            StringBuilder var6 = var2.append(var5).append(" RCV PKT (");
            int var7 = AndroidDebugger.this.connection.hashCode();
            StringBuilder var8 = var6.append(var7).append("): ");
            String var9 = var1.toXML();
            String var10 = var8.append(var9).toString();
            int var11 = Log.d("SMACK", var10);
         }
      }
   }

   class 2 implements WriterListener {

      2() {}

      public void write(String var1) {
         StringBuilder var2 = new StringBuilder();
         SimpleDateFormat var3 = AndroidDebugger.this.dateFormatter;
         Date var4 = new Date();
         String var5 = var3.format(var4);
         StringBuilder var6 = var2.append(var5).append(" SENT (");
         int var7 = AndroidDebugger.this.connection.hashCode();
         String var8 = var6.append(var7).append("): ").append(var1).toString();
         int var9 = Log.d("SMACK", var8);
      }
   }

   class 1 implements ReaderListener {

      1() {}

      public void read(String var1) {
         StringBuilder var2 = new StringBuilder();
         SimpleDateFormat var3 = AndroidDebugger.this.dateFormatter;
         Date var4 = new Date();
         String var5 = var3.format(var4);
         StringBuilder var6 = var2.append(var5).append(" RCV  (");
         int var7 = AndroidDebugger.this.connection.hashCode();
         String var8 = var6.append(var7).append("): ").append(var1).toString();
         int var9 = Log.d("SMACK", var8);
      }
   }
}
