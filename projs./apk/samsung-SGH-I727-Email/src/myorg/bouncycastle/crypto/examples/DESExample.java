package myorg.bouncycastle.crypto.examples;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import myorg.bouncycastle.crypto.CryptoException;
import myorg.bouncycastle.crypto.engines.DESedeEngine;
import myorg.bouncycastle.crypto.modes.CBCBlockCipher;
import myorg.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import myorg.bouncycastle.crypto.params.KeyParameter;
import myorg.bouncycastle.util.encoders.Hex;

public class DESExample {

   private PaddedBufferedBlockCipher cipher = null;
   private boolean encrypt = 1;
   private BufferedInputStream in = null;
   private byte[] key = null;
   private BufferedOutputStream out = null;


   public DESExample() {}

   public DESExample(String param1, String param2, String param3, boolean param4) {
      // $FF: Couldn't be decompiled
   }

   public static void main(String[] var0) {
      byte var1 = 1;
      if(var0.length < 2) {
         DESExample var2 = new DESExample();
         PrintStream var3 = System.err;
         StringBuilder var4 = (new StringBuilder()).append("Usage: java ");
         String var5 = var2.getClass().getName();
         String var6 = var4.append(var5).append(" infile outfile [keyfile]").toString();
         var3.println(var6);
         System.exit(1);
      }

      String var7 = "deskey.dat";
      String var8 = var0[0];
      String var9 = var0[1];
      if(var0.length > 2) {
         var1 = 0;
         var7 = var0[2];
      }

      (new DESExample(var8, var9, var7, (boolean)var1)).process();
   }

   private void performDecrypt(byte[] var1) {
      PaddedBufferedBlockCipher var2 = this.cipher;
      KeyParameter var3 = new KeyParameter(var1);
      var2.init((boolean)0, var3);
      BufferedInputStream var4 = this.in;
      InputStreamReader var5 = new InputStreamReader(var4);
      BufferedReader var6 = new BufferedReader(var5);
      byte[] var7 = null;

      try {
         while(true) {
            String var8 = var6.readLine();
            int var14;
            if(var8 == null) {
               try {
                  var14 = this.cipher.doFinal(var7, 0);
                  if(var14 <= 0) {
                     return;
                  }

                  this.out.write(var7, 0, var14);
                  return;
               } catch (CryptoException var16) {
                  return;
               }
            }

            byte[] var9 = Hex.decode(var8);
            PaddedBufferedBlockCipher var10 = this.cipher;
            int var11 = var9.length;
            var7 = new byte[var10.getOutputSize(var11)];
            PaddedBufferedBlockCipher var12 = this.cipher;
            int var13 = var9.length;
            var14 = var12.processBytes(var9, 0, var13, var7, 0);
            if(var14 > 0) {
               this.out.write(var7, 0, var14);
            }
         }
      } catch (IOException var17) {
         var17.printStackTrace();
      }
   }

   private void performEncrypt(byte[] var1) {
      PaddedBufferedBlockCipher var2 = this.cipher;
      KeyParameter var3 = new KeyParameter(var1);
      var2.init((boolean)1, var3);
      int var4 = this.cipher.getOutputSize(47);
      byte[] var5 = new byte[47];
      byte[] var6 = new byte[var4];

      try {
         while(true) {
            int var7 = this.in.read(var5, 0, 47);
            int var8;
            if(var7 <= 0) {
               try {
                  var8 = this.cipher.doFinal(var6, 0);
                  if(var8 <= 0) {
                     return;
                  }

                  byte[] var12 = Hex.encode(var6, 0, var8);
                  BufferedOutputStream var13 = this.out;
                  int var14 = var12.length;
                  var13.write(var12, 0, var14);
                  this.out.write(10);
                  return;
               } catch (CryptoException var16) {
                  return;
               }
            }

            var8 = this.cipher.processBytes(var5, 0, var7, var6, 0);
            if(var8 > 0) {
               byte[] var9 = Hex.encode(var6, 0, var8);
               BufferedOutputStream var10 = this.out;
               int var11 = var9.length;
               var10.write(var9, 0, var11);
               this.out.write(10);
            }
         }
      } catch (IOException var17) {
         var17.printStackTrace();
      }
   }

   private void process() {
      DESedeEngine var1 = new DESedeEngine();
      CBCBlockCipher var2 = new CBCBlockCipher(var1);
      PaddedBufferedBlockCipher var3 = new PaddedBufferedBlockCipher(var2);
      this.cipher = var3;
      if(this.encrypt) {
         byte[] var4 = this.key;
         this.performEncrypt(var4);
      } else {
         byte[] var5 = this.key;
         this.performDecrypt(var5);
      }

      try {
         this.in.close();
         this.out.flush();
         this.out.close();
      } catch (IOException var7) {
         ;
      }
   }
}
