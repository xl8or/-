package org.codehaus.jackson.node;

import java.io.IOException;
import java.util.Arrays;
import org.codehaus.jackson.Base64Variant;
import org.codehaus.jackson.Base64Variants;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.node.ValueNode;

public final class BinaryNode extends ValueNode {

   static final BinaryNode EMPTY_BINARY_NODE;
   final byte[] _data;


   static {
      byte[] var0 = new byte[0];
      EMPTY_BINARY_NODE = new BinaryNode(var0);
   }

   public BinaryNode(byte[] var1) {
      this._data = var1;
   }

   public BinaryNode(byte[] var1, int var2, int var3) {
      if(var2 == 0) {
         int var4 = var1.length;
         if(var3 == var4) {
            this._data = var1;
            return;
         }
      }

      byte[] var5 = new byte[var3];
      this._data = var5;
      byte[] var6 = this._data;
      System.arraycopy(var1, var2, var6, 0, var3);
   }

   protected static String _asBase64(boolean var0, byte[] var1) {
      int var2 = var1.length;
      int var3 = _outputLength(var2);
      StringBuilder var4 = new StringBuilder(var3);
      if(var0) {
         StringBuilder var5 = var4.append('\"');
      }

      Base64Variant var6 = Base64Variants.getDefaultVariant();
      int var7 = var6.getMaxLineLength() >> 2;
      int var8 = var2 - 3;
      byte var9 = 0;
      int var10 = var7;

      int var11;
      int var17;
      for(var11 = var9; var11 <= var8; var11 = var17) {
         int var12 = var11 + 1;
         int var13 = var1[var11] << 8;
         int var14 = var12 + 1;
         int var15 = var1[var12] & 255;
         int var16 = (var13 | var15) << 8;
         var17 = var14 + 1;
         int var18 = var1[var14] & 255;
         int var19 = var16 | var18;
         var6.encodeBase64Chunk(var4, var19);
         int var20 = var10 + -1;
         if(var20 <= 0) {
            StringBuilder var21 = var4.append('\\');
            StringBuilder var22 = var4.append('n');
            var20 = var6.getMaxLineLength() >> 2;
         }

         var10 = var20;
      }

      int var23 = var2 - var11;
      if(var23 > 0) {
         var10 = var11 + 1;
         var11 = var1[var11] << 16;
         if(var23 == 2) {
            int var24 = var10 + 1;
            int var25 = (var1[var10] & 255) << 8;
            int var10000 = var11 | var25;
         }

         var6.encodeBase64Partial(var4, var11, var23);
      }

      if(var0) {
         StringBuilder var27 = var4.append('\"');
      }

      return var4.toString();
   }

   private static int _outputLength(int var0) {
      int var1 = (var0 >> 2) + var0;
      int var2 = var0 >> 3;
      return var1 + var2;
   }

   public static BinaryNode valueOf(byte[] var0) {
      BinaryNode var1;
      if(var0 == null) {
         var1 = null;
      } else if(var0.length == 0) {
         var1 = EMPTY_BINARY_NODE;
      } else {
         var1 = new BinaryNode(var0);
      }

      return var1;
   }

   public static BinaryNode valueOf(byte[] var0, int var1, int var2) {
      BinaryNode var3;
      if(var0 == null) {
         var3 = null;
      } else if(var2 == 0) {
         var3 = EMPTY_BINARY_NODE;
      } else {
         var3 = new BinaryNode(var0, var1, var2);
      }

      return var3;
   }

   public boolean equals(Object var1) {
      byte var2;
      if(var1 == this) {
         var2 = 1;
      } else if(var1 == null) {
         var2 = 0;
      } else {
         Class var3 = var1.getClass();
         Class var4 = this.getClass();
         if(var3 != var4) {
            var2 = 0;
         } else {
            byte[] var5 = ((BinaryNode)var1)._data;
            byte[] var6 = this._data;
            var2 = Arrays.equals(var5, var6);
         }
      }

      return (boolean)var2;
   }

   public byte[] getBinaryValue() {
      return this._data;
   }

   public String getValueAsText() {
      byte[] var1 = this._data;
      return _asBase64((boolean)0, var1);
   }

   public int hashCode() {
      int var1;
      if(this._data == null) {
         var1 = -1;
      } else {
         var1 = this._data.length;
      }

      return var1;
   }

   public boolean isBinary() {
      return true;
   }

   public final void serialize(JsonGenerator var1, SerializerProvider var2) throws IOException, JsonProcessingException {
      byte[] var3 = this._data;
      var1.writeBinary(var3);
   }

   public String toString() {
      byte[] var1 = this._data;
      return _asBase64((boolean)1, var1);
   }
}
