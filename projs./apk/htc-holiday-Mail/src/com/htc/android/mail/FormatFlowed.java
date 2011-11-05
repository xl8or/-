package com.htc.android.mail;

import com.htc.android.mail.ByteString;
import java.util.ArrayList;

public class FormatFlowed {

   private static final ByteString SLB = new ByteString(" \r\n");
   private static final ByteString USENET_SIG = new ByteString("-- \r\n");


   public FormatFlowed() {}

   public static final void decode(ArrayList<ByteString> var0, int var1, int var2, boolean var3) {
      for(int var4 = var1; var4 < var2; ++var4) {
         ByteString var5 = (ByteString)var0.get(var4);
         stripSpaceStuffing(var5);
         stripSoftLineBreak(var5, var3);
      }

   }

   public static final String encode(String var0) {
      return var0;
   }

   private static final boolean isAllSpaces(ByteString var0, int var1) {
      int var2 = 0;

      boolean var4;
      while(true) {
         int var3 = var1 - 2;
         if(var2 >= var3) {
            var4 = true;
            break;
         }

         if(var0.byteAt(var2) != 32) {
            var4 = false;
            break;
         }

         ++var2;
      }

      return var4;
   }

   public static final boolean needsEncoding(String var0) {
      return false;
   }

   private static final void stripSoftLineBreak(ByteString var0, boolean var1) {
      int var2 = var0.length();
      if(var2 > 2) {
         ByteString var3 = USENET_SIG;
         if(!var0.equals(var3)) {
            if(!isAllSpaces(var0, var2)) {
               ByteString var4 = SLB;
               if(!var0.endsWith(var4)) {
                  return;
               }
            }

            if(var1) {
               int var5 = var0.length() - 3;
               var0.delete(var5, 3);
            } else {
               int var6 = var0.length() - 2;
               var0.delete(var6, 2);
            }
         }
      }
   }

   private static final void stripSpaceStuffing(ByteString var0) {
      if(var0.length() > 2) {
         if(var0.byteAt(0) == 32) {
            var0.delete(0);
         }
      }
   }
}
