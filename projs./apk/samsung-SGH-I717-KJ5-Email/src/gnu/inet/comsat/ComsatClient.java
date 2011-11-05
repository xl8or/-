package gnu.inet.comsat;

import gnu.inet.comsat.ComsatInfo;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.StringTokenizer;

public class ComsatClient {

   public static final int DEFAULT_PORT = 512;
   protected DatagramSocket socket;


   public ComsatClient() throws IOException {
      this(512, 0);
   }

   public ComsatClient(int var1, int var2) throws IOException {
      int var3;
      if(var1 < 0) {
         var3 = 512;
      } else {
         var3 = var1;
      }

      DatagramSocket var4 = new DatagramSocket(var3);
      this.socket = var4;
      if(var2 > 0) {
         this.socket.setSoTimeout(var2);
      }

      this.socket.setReceiveBufferSize(1024);
   }

   public void close() throws IOException {
      this.socket.close();
   }

   public ComsatInfo read() throws IOException {
      byte[] var1 = new byte[this.socket.getReceiveBufferSize()];
      int var2 = var1.length;
      DatagramPacket var3 = new DatagramPacket(var1, var2);
      this.socket.receive(var3);
      byte[] var4 = var3.getData();
      int var5 = var3.getLength();
      String var6 = new String(var4, 0, var5, "ISO-8859-1");
      ComsatInfo var7 = new ComsatInfo();
      StringTokenizer var8 = new StringTokenizer(var6, "\n");
      String var9 = var8.nextToken();
      var7.setMailbox(var9);
      String var10 = null;
      boolean var11 = false;

      while(var8.hasMoreTokens()) {
         String var12 = var8.nextToken();
         if(var11) {
            String var13 = var7.getBody();
            if(var13 != null) {
               var12 = var13 + "\n" + var12;
            }

            var7.setBody(var12);
         } else if(var12.length() == 0) {
            var11 = true;
         } else {
            int var14 = var12.indexOf(58);
            if(var14 != -1) {
               var10 = var12.substring(0, var14);
               int var15 = var14 + 1;
               String var16 = var12.substring(var15).trim();
               var7.setHeader(var10, var16);
            } else {
               String var17 = var7.getHeader(var10);
               String var18 = var17 + "\n" + var12;
               var7.setHeader(var10, var18);
            }
         }
      }

      return var7;
   }
}
