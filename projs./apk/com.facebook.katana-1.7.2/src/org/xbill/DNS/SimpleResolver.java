package org.xbill.DNS;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;
import org.xbill.DNS.Message;
import org.xbill.DNS.Name;
import org.xbill.DNS.OPTRecord;
import org.xbill.DNS.Options;
import org.xbill.DNS.Rcode;
import org.xbill.DNS.Record;
import org.xbill.DNS.Resolver;
import org.xbill.DNS.ResolverConfig;
import org.xbill.DNS.ResolverListener;
import org.xbill.DNS.TCPClient;
import org.xbill.DNS.TSIG;
import org.xbill.DNS.TSIGRecord;
import org.xbill.DNS.UDPClient;
import org.xbill.DNS.WireParseException;
import org.xbill.DNS.ZoneTransferException;
import org.xbill.DNS.ZoneTransferIn;

public class SimpleResolver implements Resolver {

   public static final int DEFAULT_EDNS_PAYLOADSIZE = 1280;
   public static final int DEFAULT_PORT = 53;
   private static final short DEFAULT_UDPSIZE = 512;
   private static String defaultResolver = "localhost";
   private static int uniqueID = 0;
   private InetSocketAddress address;
   private boolean ignoreTruncation;
   private InetSocketAddress localAddress;
   private OPTRecord queryOPT;
   private long timeoutValue;
   private TSIG tsig;
   private boolean useTCP;


   public SimpleResolver() throws UnknownHostException {
      this((String)null);
   }

   public SimpleResolver(String var1) throws UnknownHostException {
      this.timeoutValue = 10000L;
      String var2;
      if(var1 == null) {
         var2 = ResolverConfig.getCurrentConfig().server();
         if(var2 == null) {
            var2 = defaultResolver;
         }
      } else {
         var2 = var1;
      }

      InetAddress var3;
      if(var2.equals("0")) {
         var3 = InetAddress.getLocalHost();
      } else {
         var3 = InetAddress.getByName(var2);
      }

      InetSocketAddress var4 = new InetSocketAddress(var3, 53);
      this.address = var4;
   }

   private void applyEDNS(Message var1) {
      if(this.queryOPT != null) {
         if(var1.getOPT() == null) {
            OPTRecord var2 = this.queryOPT;
            var1.addRecord(var2, 3);
         }
      }
   }

   private int maxUDPSize(Message var1) {
      OPTRecord var2 = var1.getOPT();
      int var3;
      if(var2 == null) {
         var3 = 512;
      } else {
         var3 = var2.getPayloadSize();
      }

      return var3;
   }

   private Message parseMessage(byte[] var1) throws WireParseException {
      try {
         Message var2 = new Message(var1);
         return var2;
      } catch (IOException var5) {
         if(Options.check("verbose")) {
            var5.printStackTrace();
         }

         Object var4;
         if(!(var5 instanceof WireParseException)) {
            var4 = new WireParseException("Error parsing message");
         } else {
            var4 = var5;
         }

         throw (WireParseException)var4;
      }
   }

   private Message sendAXFR(Message var1) throws IOException {
      Name var2 = var1.getQuestion().getName();
      InetSocketAddress var3 = this.address;
      TSIG var4 = this.tsig;
      ZoneTransferIn var5 = ZoneTransferIn.newAXFR(var2, (SocketAddress)var3, var4);
      int var6 = (int)(this.getTimeout() / 1000L);
      var5.setTimeout(var6);
      InetSocketAddress var7 = this.localAddress;
      var5.setLocalAddress(var7);

      try {
         List var8 = var5.run();
      } catch (ZoneTransferException var16) {
         String var15 = var16.getMessage();
         throw new WireParseException(var15);
      }

      List var9 = var5.getAXFR();
      int var10 = var1.getHeader().getID();
      Message var11 = new Message(var10);
      var11.getHeader().setFlag(5);
      var11.getHeader().setFlag(0);
      Record var12 = var1.getQuestion();
      var11.addRecord(var12, 0);
      Iterator var13 = var9.iterator();

      while(var13.hasNext()) {
         Record var14 = (Record)var13.next();
         var11.addRecord(var14, 1);
      }

      return var11;
   }

   public static void setDefaultResolver(String var0) {
      defaultResolver = var0;
   }

   private void verifyTSIG(Message var1, Message var2, byte[] var3, TSIG var4) {
      if(var4 != null) {
         TSIGRecord var5 = var1.getTSIG();
         int var6 = var4.verify(var2, var3, var5);
         if(Options.check("verbose")) {
            PrintStream var7 = System.err;
            StringBuilder var8 = (new StringBuilder()).append("TSIG verify: ");
            String var9 = Rcode.string(var6);
            String var10 = var8.append(var9).toString();
            var7.println(var10);
         }
      }
   }

   InetSocketAddress getAddress() {
      return this.address;
   }

   TSIG getTSIGKey() {
      return this.tsig;
   }

   long getTimeout() {
      return this.timeoutValue;
   }

   public Message send(Message var1) throws IOException {
      if(Options.check("verbose")) {
         PrintStream var2 = System.err;
         StringBuilder var3 = (new StringBuilder()).append("Sending to ");
         String var4 = this.address.getAddress().getHostAddress();
         StringBuilder var5 = var3.append(var4).append(":");
         int var6 = this.address.getPort();
         String var7 = var5.append(var6).toString();
         var2.println(var7);
      }

      Message var30;
      if(var1.getHeader().getOpcode() == 0) {
         Record var8 = var1.getQuestion();
         if(var8 != null && var8.getType() == 252) {
            var30 = this.sendAXFR(var1);
            return var30;
         }
      }

      Message var9 = (Message)var1.clone();
      this.applyEDNS(var9);
      if(this.tsig != null) {
         this.tsig.apply(var9, (TSIGRecord)null);
      }

      byte[] var10 = var9.toWire('\uffff');
      int var11 = this.maxUDPSize(var9);
      long var12 = System.currentTimeMillis();
      long var14 = this.timeoutValue + var12;
      boolean var16 = false;

      while(true) {
         while(true) {
            boolean var17;
            if(!this.useTCP && var10.length <= var11) {
               var17 = var16;
            } else {
               var17 = true;
            }

            byte[] var20;
            if(var17) {
               InetSocketAddress var18 = this.localAddress;
               InetSocketAddress var19 = this.address;
               var20 = TCPClient.sendrecv(var18, var19, var10, var14);
            } else {
               InetSocketAddress var21 = this.localAddress;
               InetSocketAddress var22 = this.address;
               var20 = UDPClient.sendrecv(var21, var22, var10, var11, var14);
            }

            if(var20.length < 12) {
               throw new WireParseException("invalid DNS header - too short");
            }

            int var23 = (var20[0] & 255) << 8;
            int var24 = var20[1] & 255;
            int var25 = var23 + var24;
            int var26 = var9.getHeader().getID();
            if(var25 == var26) {
               Message var28 = this.parseMessage(var20);
               TSIG var29 = this.tsig;
               this.verifyTSIG(var9, var28, var20, var29);
               if(var17 || this.ignoreTruncation || !var28.getHeader().getFlag(6)) {
                  var30 = var28;
                  return var30;
               }

               var16 = true;
            } else {
               String var27 = "invalid message id: expected " + var26 + "; got id " + var25;
               if(var17) {
                  throw new WireParseException(var27);
               }

               if(Options.check("verbose")) {
                  System.err.println(var27);
                  var16 = var17;
               } else {
                  var16 = var17;
               }
            }
         }
      }
   }

   public Object sendAsync(Message param1, ResolverListener param2) {
      // $FF: Couldn't be decompiled
   }

   public void setAddress(InetAddress var1) {
      int var2 = this.address.getPort();
      InetSocketAddress var3 = new InetSocketAddress(var1, var2);
      this.address = var3;
   }

   public void setAddress(InetSocketAddress var1) {
      this.address = var1;
   }

   public void setEDNS(int var1) {
      this.setEDNS(var1, 0, 0, (List)null);
   }

   public void setEDNS(int var1, int var2, int var3, List var4) {
      if(var1 != 0 && var1 != -1) {
         throw new IllegalArgumentException("invalid EDNS level - must be 0 or -1");
      } else {
         int var5;
         if(var2 == 0) {
            var5 = 1280;
         } else {
            var5 = var2;
         }

         OPTRecord var9 = new OPTRecord(var5, 0, var1, var3, var4);
         this.queryOPT = var9;
      }
   }

   public void setIgnoreTruncation(boolean var1) {
      this.ignoreTruncation = var1;
   }

   public void setLocalAddress(InetAddress var1) {
      InetSocketAddress var2 = new InetSocketAddress(var1, 0);
      this.localAddress = var2;
   }

   public void setLocalAddress(InetSocketAddress var1) {
      this.localAddress = var1;
   }

   public void setPort(int var1) {
      InetAddress var2 = this.address.getAddress();
      InetSocketAddress var3 = new InetSocketAddress(var2, var1);
      this.address = var3;
   }

   public void setTCP(boolean var1) {
      this.useTCP = var1;
   }

   public void setTSIGKey(TSIG var1) {
      this.tsig = var1;
   }

   public void setTimeout(int var1) {
      this.setTimeout(var1, 0);
   }

   public void setTimeout(int var1, int var2) {
      long var3 = (long)var1 * 1000L;
      long var5 = (long)var2;
      long var7 = var3 + var5;
      this.timeoutValue = var7;
   }
}
