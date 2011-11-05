package myorg.bouncycastle.asn1.x9;

import java.math.BigInteger;
import myorg.bouncycastle.math.ec.ECCurve;
import myorg.bouncycastle.math.ec.ECFieldElement;

public class X9IntegerConverter {

   public X9IntegerConverter() {}

   public int getByteLength(ECCurve var1) {
      return (var1.getFieldSize() + 7) / 8;
   }

   public int getByteLength(ECFieldElement var1) {
      return (var1.getFieldSize() + 7) / 8;
   }

   public byte[] integerToBytes(BigInteger var1, int var2) {
      byte[] var3 = var1.toByteArray();
      int var4 = var3.length;
      byte[] var10;
      if(var2 < var4) {
         byte[] var5 = new byte[var2];
         int var6 = var3.length;
         int var7 = var5.length;
         int var8 = var6 - var7;
         int var9 = var5.length;
         System.arraycopy(var3, var8, var5, 0, var9);
         var10 = var5;
      } else {
         int var11 = var3.length;
         if(var2 > var11) {
            byte[] var12 = new byte[var2];
            int var13 = var12.length;
            int var14 = var3.length;
            int var15 = var13 - var14;
            int var16 = var3.length;
            System.arraycopy(var3, 0, var12, var15, var16);
            var10 = var12;
         } else {
            var10 = var3;
         }
      }

      return var10;
   }
}
