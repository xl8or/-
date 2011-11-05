package com.google.common.io;

import java.io.IOException;

abstract class LineBuffer {

   private StringBuilder line;
   private boolean sawReturn;


   LineBuffer() {
      StringBuilder var1 = new StringBuilder();
      this.line = var1;
   }

   private boolean finishLine(boolean var1) throws IOException {
      String var2 = this.line.toString();
      String var3;
      if(this.sawReturn) {
         if(var1) {
            var3 = "\r\n";
         } else {
            var3 = "\r";
         }
      } else if(var1) {
         var3 = "\n";
      } else {
         var3 = "";
      }

      this.handleLine(var2, var3);
      StringBuilder var4 = new StringBuilder();
      this.line = var4;
      this.sawReturn = (boolean)0;
      return var1;
   }

   protected void add(char[] var1, int var2, int var3) throws IOException {
      int var4 = var2;
      if(this.sawReturn && var3 > 0) {
         byte var5;
         if(var1[var2] == 10) {
            var5 = 1;
         } else {
            var5 = 0;
         }

         if(this.finishLine((boolean)var5)) {
            var4 = var2 + 1;
         }
      }

      int var6 = var4;

      for(int var7 = var2 + var3; var4 < var7; ++var4) {
         switch(var1[var4]) {
         case 10:
            StringBuilder var13 = this.line;
            int var14 = var4 - var6;
            var13.append(var1, var6, var14);
            boolean var16 = this.finishLine((boolean)1);
            var6 = var4 + 1;
         case 11:
         case 12:
         default:
            break;
         case 13:
            StringBuilder var8 = this.line;
            int var9 = var4 - var6;
            var8.append(var1, var6, var9);
            this.sawReturn = (boolean)1;
            if(var4 + 1 < var7) {
               int var11 = var4 + 1;
               byte var12;
               if(var1[var11] == 10) {
                  var12 = 1;
               } else {
                  var12 = 0;
               }

               if(this.finishLine((boolean)var12)) {
                  ++var4;
               }
            }

            var6 = var4 + 1;
         }
      }

      StringBuilder var17 = this.line;
      int var18 = var2 + var3 - var6;
      var17.append(var1, var6, var18);
   }

   protected void finish() throws IOException {
      if(this.sawReturn || this.line.length() > 0) {
         boolean var1 = this.finishLine((boolean)0);
      }
   }

   protected abstract void handleLine(String var1, String var2) throws IOException;
}
