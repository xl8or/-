package org.apache.james.mime4j.decoder;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.james.mime4j.decoder.ByteQueue;

public class QuotedPrintableInputStream extends InputStream {

   private static Log log = LogFactory.getLog(QuotedPrintableInputStream.class);
   ByteQueue byteq;
   ByteQueue pushbackq;
   private byte state;
   private InputStream stream;


   public QuotedPrintableInputStream(InputStream var1) {
      ByteQueue var2 = new ByteQueue();
      this.byteq = var2;
      ByteQueue var3 = new ByteQueue();
      this.pushbackq = var3;
      this.state = 0;
      this.stream = var1;
   }

   private byte asciiCharToNumericValue(byte var1) {
      byte var2;
      if(var1 >= 48 && var1 <= 57) {
         var2 = (byte)(var1 - 48);
      } else if(var1 >= 65 && var1 <= 90) {
         var2 = (byte)(var1 - 65 + 10);
      } else {
         if(var1 < 97 || var1 > 122) {
            StringBuilder var3 = new StringBuilder();
            char var4 = (char)var1;
            String var5 = var3.append(var4).append(" is not a hexadecimal digit").toString();
            throw new IllegalArgumentException(var5);
         }

         var2 = (byte)(var1 - 97 + 10);
      }

      return var2;
   }

   private void fillBuffer() throws IOException {
      byte var1 = 0;

      while(this.byteq.count() == 0) {
         if(this.pushbackq.count() == 0) {
            this.populatePushbackQueue();
            if(this.pushbackq.count() == 0) {
               return;
            }
         }

         byte var2 = this.pushbackq.dequeue();
         switch(this.state) {
         case 0:
            if(var2 != 61) {
               this.byteq.enqueue(var2);
            } else {
               this.state = 1;
            }
            break;
         case 1:
            if(var2 == 13) {
               this.state = 2;
            } else {
               if((var2 < 48 || var2 > 57) && (var2 < 65 || var2 > 70) && (var2 < 97 || var2 > 102)) {
                  if(var2 == 61) {
                     if(log.isWarnEnabled()) {
                        log.warn("Malformed MIME; got ==");
                     }

                     this.byteq.enqueue((byte)61);
                  } else {
                     if(log.isWarnEnabled()) {
                        Log var7 = log;
                        String var8 = "Malformed MIME; expected \\r or [0-9A-Z], got " + var2;
                        var7.warn(var8);
                     }

                     this.state = 0;
                     this.byteq.enqueue((byte)61);
                     this.byteq.enqueue(var2);
                  }
                  continue;
               }

               this.state = 3;
               var1 = var2;
            }
            break;
         case 2:
            if(var2 == 10) {
               this.state = 0;
            } else {
               if(log.isWarnEnabled()) {
                  Log var9 = log;
                  String var10 = "Malformed MIME; expected 10, got " + var2;
                  var9.warn(var10);
               }

               this.state = 0;
               this.byteq.enqueue((byte)61);
               this.byteq.enqueue((byte)13);
               this.byteq.enqueue(var2);
            }
            break;
         case 3:
            if((var2 < 48 || var2 > 57) && (var2 < 65 || var2 > 70) && (var2 < 97 || var2 > 102)) {
               if(log.isWarnEnabled()) {
                  Log var15 = log;
                  String var16 = "Malformed MIME; expected [0-9A-Z], got " + var2;
                  var15.warn(var16);
               }

               this.state = 0;
               this.byteq.enqueue((byte)61);
               this.byteq.enqueue(var1);
               this.byteq.enqueue(var2);
            } else {
               byte var11 = this.asciiCharToNumericValue(var1);
               byte var12 = this.asciiCharToNumericValue(var2);
               this.state = 0;
               ByteQueue var13 = this.byteq;
               byte var14 = (byte)(var11 << 4 | var12);
               var13.enqueue(var14);
            }
            break;
         default:
            Log var3 = log;
            StringBuilder var4 = (new StringBuilder()).append("Illegal state: ");
            byte var5 = this.state;
            String var6 = var4.append(var5).toString();
            var3.error(var6);
            this.state = 0;
            this.byteq.enqueue(var2);
         }
      }

   }

   private void populatePushbackQueue() throws IOException {
      // $FF: Couldn't be decompiled
   }

   public void close() throws IOException {
      this.stream.close();
   }

   public int read() throws IOException {
      this.fillBuffer();
      int var1;
      if(this.byteq.count() == 0) {
         var1 = -1;
      } else {
         byte var2 = this.byteq.dequeue();
         if(var2 >= 0) {
            var1 = var2;
         } else {
            var1 = var2 & 255;
         }
      }

      return var1;
   }
}
