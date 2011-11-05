package org.apache.commons.httpclient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpParser;
import org.apache.commons.httpclient.util.EncodingUtil;
import org.apache.commons.httpclient.util.ExceptionUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ChunkedInputStream extends InputStream {

   private static final Log LOG = LogFactory.getLog(ChunkedInputStream.class);
   private boolean bof;
   private int chunkSize;
   private boolean closed;
   private boolean eof;
   private InputStream in;
   private HttpMethod method;
   private int pos;


   public ChunkedInputStream(InputStream var1) throws IOException {
      this(var1, (HttpMethod)null);
   }

   public ChunkedInputStream(InputStream var1, HttpMethod var2) throws IOException {
      this.bof = (boolean)1;
      this.eof = (boolean)0;
      this.closed = (boolean)0;
      this.method = null;
      if(var1 == null) {
         throw new IllegalArgumentException("InputStream parameter may not be null");
      } else {
         this.in = var1;
         this.method = var2;
         this.pos = 0;
      }
   }

   static void exhaustInputStream(InputStream var0) throws IOException {
      byte[] var1 = new byte[1024];

      while(var0.read(var1) >= 0) {
         ;
      }

   }

   private static int getChunkSizeFromInputStream(InputStream var0) throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      byte var2 = 0;

      while(var2 != -1) {
         int var3 = var0.read();
         if(var3 == -1) {
            throw new IOException("chunked stream ended unexpectedly");
         }

         switch(var2) {
         case 0:
            switch(var3) {
            case 13:
               var2 = 1;
               continue;
            case 34:
               var2 = 2;
            default:
               var1.write(var3);
               continue;
            }
         case 1:
            if(var3 != 10) {
               StringBuffer var4 = new StringBuffer();
               StringBuffer var5 = var4.append("Protocol violation: Unexpected").append(" single newline character in chunk size");
               String var6 = var4.toString();
               throw new IOException(var6);
            }

            var2 = -1;
            break;
         case 2:
            switch(var3) {
            case 34:
               var2 = 0;
            case 92:
               int var7 = var0.read();
               var1.write(var7);
               continue;
            default:
               var1.write(var3);
               continue;
            }
         default:
            throw new RuntimeException("assertion failed");
         }
      }

      String var8 = EncodingUtil.getAsciiString(var1.toByteArray());
      int var9 = var8.indexOf(59);
      String var10;
      if(var9 > 0) {
         var10 = var8.substring(0, var9).trim();
      } else {
         var10 = var8.trim();
      }

      try {
         int var11 = Integer.parseInt(var10.trim(), 16);
         return var11;
      } catch (NumberFormatException var14) {
         String var13 = "Bad chunk size: " + var10;
         throw new IOException(var13);
      }
   }

   private void nextChunk() throws IOException {
      if(!this.bof) {
         this.readCRLF();
      }

      int var1 = getChunkSizeFromInputStream(this.in);
      this.chunkSize = var1;
      this.bof = (boolean)0;
      this.pos = 0;
      if(this.chunkSize == 0) {
         this.eof = (boolean)1;
         this.parseTrailerHeaders();
      }
   }

   private void parseTrailerHeaders() throws IOException {
      Header[] var2;
      try {
         String var1 = "US-ASCII";
         if(this.method != null) {
            var1 = this.method.getParams().getHttpElementCharset();
         }

         var2 = HttpParser.parseHeaders(this.in, var1);
      } catch (HttpException var11) {
         LOG.error("Error parsing trailer headers", var11);
         String var9 = var11.getMessage();
         IOException var10 = new IOException(var9);
         ExceptionUtil.initCause(var10, var11);
         throw var10;
      }

      Header[] var3 = var2;
      if(this.method != null) {
         int var4 = 0;

         while(true) {
            int var5 = var3.length;
            if(var4 >= var5) {
               return;
            }

            HttpMethod var6 = this.method;
            Header var7 = var3[var4];
            var6.addResponseFooter(var7);
            ++var4;
         }
      }
   }

   private void readCRLF() throws IOException {
      int var1 = this.in.read();
      int var2 = this.in.read();
      if(var1 != 13 || var2 != 10) {
         String var3 = "CRLF expected at end of chunk: " + var1 + "/" + var2;
         throw new IOException(var3);
      }
   }

   public void close() throws IOException {
      if(!this.closed) {
         try {
            if(!this.eof) {
               exhaustInputStream(this);
            }
         } finally {
            this.eof = (boolean)1;
            this.closed = (boolean)1;
         }

      }
   }

   public int read() throws IOException {
      if(this.closed) {
         throw new IOException("Attempted read from closed stream.");
      } else {
         int var1;
         if(this.eof) {
            var1 = -1;
         } else {
            int var2 = this.pos;
            int var3 = this.chunkSize;
            if(var2 >= var3) {
               this.nextChunk();
               if(this.eof) {
                  var1 = -1;
                  return var1;
               }
            }

            int var4 = this.pos + 1;
            this.pos = var4;
            var1 = this.in.read();
         }

         return var1;
      }
   }

   public int read(byte[] var1) throws IOException {
      int var2 = var1.length;
      return this.read(var1, 0, var2);
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      if(this.closed) {
         throw new IOException("Attempted read from closed stream.");
      } else {
         int var4;
         if(this.eof) {
            var4 = -1;
         } else {
            int var5 = this.pos;
            int var6 = this.chunkSize;
            if(var5 >= var6) {
               this.nextChunk();
               if(this.eof) {
                  var4 = -1;
                  return var4;
               }
            }

            int var7 = this.chunkSize;
            int var8 = this.pos;
            int var9 = var7 - var8;
            int var10 = Math.min(var3, var9);
            int var11 = this.in.read(var1, var2, var10);
            int var12 = this.pos + var11;
            this.pos = var12;
            var4 = var11;
         }

         return var4;
      }
   }
}
