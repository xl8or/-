package org.apache.commons.httpclient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

class Wire {

   public static Wire CONTENT_WIRE;
   public static Wire HEADER_WIRE;
   private Log log;


   static {
      Log var0 = LogFactory.getLog("httpclient.wire.header");
      HEADER_WIRE = new Wire(var0);
      Log var1 = LogFactory.getLog("httpclient.wire.content");
      CONTENT_WIRE = new Wire(var1);
   }

   private Wire(Log var1) {
      this.log = var1;
   }

   private void wire(String var1, InputStream var2) throws IOException {
      StringBuffer var3 = new StringBuffer();

      while(true) {
         int var4 = var2.read();
         if(var4 == -1) {
            if(var3.length() <= 0) {
               return;
            }

            StringBuffer var17 = var3.append("\"");
            StringBuffer var18 = var3.insert(0, "\"");
            var3.insert(0, var1);
            Log var20 = this.log;
            String var21 = var3.toString();
            var20.debug(var21);
            return;
         }

         if(var4 == 13) {
            StringBuffer var5 = var3.append("[\\r]");
         } else if(var4 == 10) {
            StringBuffer var6 = var3.append("[\\n]\"");
            StringBuffer var7 = var3.insert(0, "\"");
            var3.insert(0, var1);
            Log var9 = this.log;
            String var10 = var3.toString();
            var9.debug(var10);
            var3.setLength(0);
         } else if(var4 >= 32 && var4 <= 127) {
            char var15 = (char)var4;
            var3.append(var15);
         } else {
            StringBuffer var11 = var3.append("[0x");
            String var12 = Integer.toHexString(var4);
            var3.append(var12);
            StringBuffer var14 = var3.append("]");
         }
      }
   }

   public boolean enabled() {
      return this.log.isDebugEnabled();
   }

   public void input(int var1) throws IOException {
      byte[] var2 = new byte[1];
      byte var3 = (byte)var1;
      var2[0] = var3;
      this.input(var2);
   }

   public void input(InputStream var1) throws IOException {
      if(var1 == null) {
         throw new IllegalArgumentException("Input may not be null");
      } else {
         this.wire("<< ", var1);
      }
   }

   public void input(String var1) throws IOException {
      if(var1 == null) {
         throw new IllegalArgumentException("Input may not be null");
      } else {
         byte[] var2 = var1.getBytes();
         this.input(var2);
      }
   }

   public void input(byte[] var1) throws IOException {
      if(var1 == null) {
         throw new IllegalArgumentException("Input may not be null");
      } else {
         ByteArrayInputStream var2 = new ByteArrayInputStream(var1);
         this.wire("<< ", var2);
      }
   }

   public void input(byte[] var1, int var2, int var3) throws IOException {
      if(var1 == null) {
         throw new IllegalArgumentException("Input may not be null");
      } else {
         ByteArrayInputStream var4 = new ByteArrayInputStream(var1, var2, var3);
         this.wire("<< ", var4);
      }
   }

   public void output(int var1) throws IOException {
      byte[] var2 = new byte[1];
      byte var3 = (byte)var1;
      var2[0] = var3;
      this.output(var2);
   }

   public void output(InputStream var1) throws IOException {
      if(var1 == null) {
         throw new IllegalArgumentException("Output may not be null");
      } else {
         this.wire(">> ", var1);
      }
   }

   public void output(String var1) throws IOException {
      if(var1 == null) {
         throw new IllegalArgumentException("Output may not be null");
      } else {
         byte[] var2 = var1.getBytes();
         this.output(var2);
      }
   }

   public void output(byte[] var1) throws IOException {
      if(var1 == null) {
         throw new IllegalArgumentException("Output may not be null");
      } else {
         ByteArrayInputStream var2 = new ByteArrayInputStream(var1);
         this.wire(">> ", var2);
      }
   }

   public void output(byte[] var1, int var2, int var3) throws IOException {
      if(var1 == null) {
         throw new IllegalArgumentException("Output may not be null");
      } else {
         ByteArrayInputStream var4 = new ByteArrayInputStream(var1, var2, var3);
         this.wire(">> ", var4);
      }
   }
}
