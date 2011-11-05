package org.codehaus.jackson.util;

import java.util.Iterator;
import java.util.LinkedList;

public final class ByteArrayBuilder {

   static final int DEFAULT_BLOCK_ARRAY_SIZE = 40;
   private static final int INITIAL_BLOCK_SIZE = 500;
   private static final int MAX_BLOCK_SIZE = 262144;
   private static final byte[] NO_BYTES = new byte[0];
   private byte[] _currBlock;
   private int _currBlockPtr;
   private LinkedList<byte[]> _pastBlocks;
   private int _pastLen;


   public ByteArrayBuilder() {
      LinkedList var1 = new LinkedList();
      this._pastBlocks = var1;
      byte[] var2 = new byte[500];
      this._currBlock = var2;
   }

   private void _allocMoreAndAppend(byte var1) {
      int var2 = this._pastLen;
      int var3 = this._currBlock.length;
      int var4 = var2 + var3;
      this._pastLen = var4;
      int var5 = Math.max(this._pastLen >> 1, 1000);
      if(var5 > 262144) {
         var5 = 262144;
      }

      LinkedList var6 = this._pastBlocks;
      byte[] var7 = this._currBlock;
      var6.add(var7);
      byte[] var9 = new byte[var5];
      this._currBlock = var9;
      this._currBlockPtr = 0;
      byte[] var10 = this._currBlock;
      int var11 = this._currBlockPtr;
      int var12 = var11 + 1;
      this._currBlockPtr = var12;
      var10[var11] = var1;
   }

   public void append(int var1) {
      byte var2 = (byte)var1;
      int var3 = this._currBlockPtr;
      int var4 = this._currBlock.length;
      if(var3 < var4) {
         byte[] var5 = this._currBlock;
         int var6 = this._currBlockPtr;
         int var7 = var6 + 1;
         this._currBlockPtr = var7;
         var5[var6] = var2;
      } else {
         this._allocMoreAndAppend(var2);
      }
   }

   public void appendThreeBytes(int var1) {
      int var2 = this._currBlockPtr + 2;
      int var3 = this._currBlock.length;
      if(var2 < var3) {
         byte[] var4 = this._currBlock;
         int var5 = this._currBlockPtr;
         int var6 = var5 + 1;
         this._currBlockPtr = var6;
         byte var7 = (byte)(var1 >> 16);
         var4[var5] = var7;
         byte[] var8 = this._currBlock;
         int var9 = this._currBlockPtr;
         int var10 = var9 + 1;
         this._currBlockPtr = var10;
         byte var11 = (byte)(var1 >> 8);
         var8[var9] = var11;
         byte[] var12 = this._currBlock;
         int var13 = this._currBlockPtr;
         int var14 = var13 + 1;
         this._currBlockPtr = var14;
         byte var15 = (byte)var1;
         var12[var13] = var15;
      } else {
         int var16 = var1 >> 16;
         this.append(var16);
         int var17 = var1 >> 8;
         this.append(var17);
         this.append(var1);
      }
   }

   public void appendTwoBytes(int var1) {
      int var2 = this._currBlockPtr + 1;
      int var3 = this._currBlock.length;
      if(var2 < var3) {
         byte[] var4 = this._currBlock;
         int var5 = this._currBlockPtr;
         int var6 = var5 + 1;
         this._currBlockPtr = var6;
         byte var7 = (byte)(var1 >> 8);
         var4[var5] = var7;
         byte[] var8 = this._currBlock;
         int var9 = this._currBlockPtr;
         int var10 = var9 + 1;
         this._currBlockPtr = var10;
         byte var11 = (byte)var1;
         var8[var9] = var11;
      } else {
         int var12 = var1 >> 8;
         this.append(var12);
         this.append(var1);
      }
   }

   public void reset() {
      this._pastLen = 0;
      this._currBlockPtr = 0;
      if(!this._pastBlocks.isEmpty()) {
         byte[] var1 = (byte[])this._pastBlocks.getLast();
         this._currBlock = var1;
         this._pastBlocks.clear();
      }
   }

   public byte[] toByteArray() {
      int var1 = this._pastLen;
      int var2 = this._currBlockPtr + var1;
      byte[] var3;
      if(var2 == 0) {
         var3 = NO_BYTES;
      } else {
         byte[] var4 = new byte[var2];
         Iterator var5 = this._pastBlocks.iterator();

         int var6;
         int var8;
         for(var6 = 0; var5.hasNext(); var6 += var8) {
            byte[] var7 = (byte[])var5.next();
            var8 = var7.length;
            System.arraycopy(var7, 0, var4, var6, var8);
         }

         byte[] var9 = this._currBlock;
         int var10 = this._currBlockPtr;
         System.arraycopy(var9, 0, var4, var6, var10);
         int var11 = this._currBlockPtr + var6;
         if(var11 != var2) {
            String var12 = "Internal error: total len assumed to be " + var2 + ", copied " + var11 + " bytes";
            throw new RuntimeException(var12);
         }

         if(!this._pastBlocks.isEmpty()) {
            this.reset();
         }

         var3 = var4;
      }

      return var3;
   }
}
