package gnu.inet.ftp;

import gnu.inet.ftp.FTPConnection;
import gnu.inet.util.GetLocalHostAction;
import gnu.inet.util.GetSystemPropertyAction;
import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.util.HashMap;
import java.util.Map;

public class FTPURLConnection extends URLConnection {

   protected FTPConnection connection;
   protected int fileStructure = -1;
   protected boolean passive = 1;
   protected int representationType = 3;
   protected int transferMode = -1;


   public FTPURLConnection(URL var1) {
      super(var1);
   }

   private void addRequestPropertyValue(Map var1, String var2) {
      String var3 = this.getRequestProperty(var2);
      var1.put(var2, var3);
   }

   public void addRequestProperty(String var1, String var2) {
      this.setRequestProperty(var1, var2);
   }

   public void connect() throws IOException {
      if(!this.connected) {
         String var1 = this.url.getHost();
         int var2 = this.url.getPort();
         String var3 = this.url.getUserInfo();
         Object var4 = null;
         String var10;
         String var11;
         if(var3 != null) {
            int var5 = var3.indexOf(58);
            if(var5 != -1) {
               int var6 = var5 + 1;
               String var7 = var3.substring(var6);
               String var8 = var3.substring(0, var5);
               var10 = var8;
               var11 = var7;
            } else {
               var10 = var3;
               var11 = (String)var4;
            }
         } else {
            String var26 = "anonymous";
            String var13 = (String)AccessController.doPrivileged(new GetSystemPropertyAction("user.name"));
            InetAddress var14 = (InetAddress)AccessController.doPrivileged(new GetLocalHostAction());
            StringBuilder var15 = (new StringBuilder()).append(var13).append("@");
            String var16;
            if(var14 == null) {
               var16 = "localhost";
            } else {
               var16 = var14.getHostName();
            }

            var11 = var15.append(var16).toString();
            var10 = var26;
         }

         FTPConnection var12 = new FTPConnection(var1, var2);
         this.connection = var12;
         if(!this.connection.authenticate(var10, var11)) {
            throw new SecurityException("Authentication failed");
         } else {
            FTPConnection var17 = this.connection;
            boolean var18 = this.passive;
            var17.setPassive(var18);
            if(this.representationType != -1) {
               FTPConnection var19 = this.connection;
               int var20 = this.representationType;
               var19.setRepresentationType(var20);
            }

            if(this.fileStructure != -1) {
               FTPConnection var21 = this.connection;
               int var22 = this.fileStructure;
               var21.setFileStructure(var22);
            }

            if(this.transferMode != -1) {
               FTPConnection var23 = this.connection;
               int var24 = this.transferMode;
               var23.setTransferMode(var24);
            }
         }
      }
   }

   public InputStream getInputStream() throws IOException {
      if(!this.connected) {
         this.connect();
      }

      String var1 = this.url.getPath();
      if(var1.startsWith("/")) {
         var1 = var1.substring(1);
      }

      int var2 = var1.lastIndexOf(47);
      String var6;
      if(var2 != -1) {
         int var3 = var2 + 1;
         String var4 = var1.substring(var3);
         String var5 = var1.substring(0, var2);
         if(!this.connection.changeWorkingDirectory(var5)) {
            throw new FileNotFoundException(var5);
         }

         var6 = var4;
      } else {
         var6 = null;
      }

      FTPURLConnection.ClosingInputStream var9;
      if(var6 != null && var6.length() > 0) {
         InputStream var7 = this.connection.retrieve(var6);
         var9 = new FTPURLConnection.ClosingInputStream(var7);
      } else {
         InputStream var8 = this.connection.list((String)null);
         var9 = new FTPURLConnection.ClosingInputStream(var8);
      }

      return var9;
   }

   public OutputStream getOutputStream() throws IOException {
      if(!this.connected) {
         this.connect();
      }

      String var1 = this.url.getPath();
      if(var1.startsWith("/")) {
         var1 = var1.substring(1);
      }

      String var2 = null;
      int var3 = var1.lastIndexOf(47);
      String var6;
      if(var3 != -1) {
         int var4 = var3 + 1;
         var2 = var1.substring(var4);
         String var5 = var1.substring(0, var3);
         if(!this.connection.changeWorkingDirectory(var5)) {
            throw new FileNotFoundException(var5);
         }

         var6 = var2;
      } else {
         var6 = var2;
      }

      if(var6 != null && var6.length() > 0) {
         OutputStream var7 = this.connection.store(var6);
         return new FTPURLConnection.ClosingOutputStream(var7);
      } else {
         throw new FileNotFoundException(var6);
      }
   }

   public Map getRequestProperties() {
      HashMap var1 = new HashMap();
      this.addRequestPropertyValue(var1, "passive");
      this.addRequestPropertyValue(var1, "representationType");
      this.addRequestPropertyValue(var1, "fileStructure");
      this.addRequestPropertyValue(var1, "transferMode");
      return var1;
   }

   public String getRequestProperty(String var1) {
      String var2;
      if("passive".equals(var1)) {
         var2 = Boolean.toString(this.passive);
      } else {
         if("representationType".equals(var1)) {
            switch(this.representationType) {
            case 1:
               var2 = "ASCII";
               return var2;
            case 2:
               var2 = "EBCDIC";
               return var2;
            case 3:
               var2 = "BINARY";
               return var2;
            }
         } else if("fileStructure".equals(var1)) {
            switch(this.fileStructure) {
            case 1:
               var2 = "FILE";
               return var2;
            case 2:
               var2 = "RECORD";
               return var2;
            case 3:
               var2 = "PAGE";
               return var2;
            }
         } else if("transferMode".equals(var1)) {
            switch(this.transferMode) {
            case 1:
               var2 = "STREAM";
               return var2;
            case 2:
               var2 = "BLOCK";
               return var2;
            case 3:
               var2 = "COMPRESSED";
               return var2;
            }
         }

         var2 = null;
      }

      return var2;
   }

   public void setDoInput(boolean var1) {
      this.doInput = var1;
   }

   public void setDoOutput(boolean var1) {
      this.doOutput = var1;
   }

   public void setRequestProperty(String var1, String var2) {
      if(this.connected) {
         throw new IllegalStateException();
      } else if("passive".equals(var1)) {
         boolean var3 = Boolean.valueOf(var2).booleanValue();
         this.passive = var3;
      } else if("representationType".equals(var1)) {
         if(!"A".equalsIgnoreCase(var2) && !"ASCII".equalsIgnoreCase(var2)) {
            if(!"E".equalsIgnoreCase(var2) && !"EBCDIC".equalsIgnoreCase(var2)) {
               if(!"I".equalsIgnoreCase(var2) && !"BINARY".equalsIgnoreCase(var2)) {
                  throw new IllegalArgumentException(var2);
               } else {
                  this.representationType = 3;
               }
            } else {
               this.representationType = 2;
            }
         } else {
            this.representationType = 1;
         }
      } else if("fileStructure".equals(var1)) {
         if(!"F".equalsIgnoreCase(var2) && !"FILE".equalsIgnoreCase(var2)) {
            if(!"R".equalsIgnoreCase(var2) && !"RECORD".equalsIgnoreCase(var2)) {
               if(!"P".equalsIgnoreCase(var2) && !"PAGE".equalsIgnoreCase(var2)) {
                  throw new IllegalArgumentException(var2);
               } else {
                  this.fileStructure = 3;
               }
            } else {
               this.fileStructure = 2;
            }
         } else {
            this.fileStructure = 1;
         }
      } else if("transferMode".equals(var1)) {
         if(!"S".equalsIgnoreCase(var2) && !"STREAM".equalsIgnoreCase(var2)) {
            if(!"B".equalsIgnoreCase(var2) && !"BLOCK".equalsIgnoreCase(var2)) {
               if(!"C".equalsIgnoreCase(var2) && !"COMPRESSED".equalsIgnoreCase(var2)) {
                  throw new IllegalArgumentException(var2);
               } else {
                  this.transferMode = 3;
               }
            } else {
               this.transferMode = 2;
            }
         } else {
            this.transferMode = 1;
         }
      }
   }

   class ClosingInputStream extends FilterInputStream {

      ClosingInputStream(InputStream var2) {
         super(var2);
      }

      public void close() throws IOException {
         super.close();
         FTPURLConnection.this.connection.logout();
      }
   }

   class ClosingOutputStream extends FilterOutputStream {

      ClosingOutputStream(OutputStream var2) {
         super(var2);
      }

      public void close() throws IOException {
         super.close();
         FTPURLConnection.this.connection.logout();
      }
   }
}
