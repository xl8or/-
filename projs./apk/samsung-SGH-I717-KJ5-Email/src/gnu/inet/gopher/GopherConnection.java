package gnu.inet.gopher;

import gnu.inet.gopher.DirectoryListing;
import gnu.inet.util.CRLFInputStream;
import gnu.inet.util.MessageInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class GopherConnection {

   public static final int DEFAULT_PORT = 80;
   protected InputStream in;
   protected OutputStream out;
   protected Socket socket;


   public GopherConnection(String var1) throws IOException {
      this(var1, 80);
   }

   public GopherConnection(String var1, int var2) throws IOException {
      int var3;
      if(var2 <= 0) {
         var3 = 80;
      } else {
         var3 = var2;
      }

      Socket var4 = new Socket(var1, var3);
      this.socket = var4;
      InputStream var5 = this.socket.getInputStream();
      this.in = var5;
      OutputStream var6 = this.socket.getOutputStream();
      this.out = var6;
   }

   public InputStream get(String var1) throws IOException {
      byte[] var2 = var1.getBytes("US-ASCII");
      byte[] var3 = new byte[var2.length + 2];
      int var4 = var2.length;
      System.arraycopy(var2, 0, var3, 0, var4);
      int var5 = var2.length;
      var3[var5] = 13;
      int var6 = var2.length + 1;
      var3[var6] = 10;
      this.out.write(var3);
      this.out.flush();
      return this.in;
   }

   public DirectoryListing list() throws IOException {
      byte[] var1 = new byte[]{(byte)13, (byte)10};
      this.out.write(var1);
      this.out.flush();
      InputStream var2 = this.in;
      CRLFInputStream var3 = new CRLFInputStream(var2);
      MessageInputStream var4 = new MessageInputStream(var3);
      return new DirectoryListing(var4);
   }
}
