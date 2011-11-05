package myorg.bouncycastle.asn1;

import java.io.IOException;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROutputStream;
import myorg.bouncycastle.util.Arrays;

public class DERUnknownTag extends DERObject {

   private byte[] data;
   private boolean isConstructed;
   private int tag;


   public DERUnknownTag(int var1, byte[] var2) {
      this((boolean)0, var1, var2);
   }

   public DERUnknownTag(boolean var1, int var2, byte[] var3) {
      this.isConstructed = var1;
      this.tag = var2;
      this.data = var3;
   }

   void encode(DEROutputStream var1) throws IOException {
      byte var2;
      if(this.isConstructed) {
         var2 = 32;
      } else {
         var2 = 0;
      }

      int var3 = this.tag;
      byte[] var4 = this.data;
      var1.writeEncoded(var2, var3, var4);
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(!(var1 instanceof DERUnknownTag)) {
         var2 = false;
      } else {
         DERUnknownTag var3 = (DERUnknownTag)var1;
         boolean var4 = this.isConstructed;
         boolean var5 = var3.isConstructed;
         if(var4 == var5) {
            int var6 = this.tag;
            int var7 = var3.tag;
            if(var6 == var7) {
               byte[] var8 = this.data;
               byte[] var9 = var3.data;
               if(Arrays.areEqual(var8, var9)) {
                  var2 = true;
                  return var2;
               }
            }
         }

         var2 = false;
      }

      return var2;
   }

   public byte[] getData() {
      return this.data;
   }

   public int getTag() {
      return this.tag;
   }

   public int hashCode() {
      byte var1;
      if(this.isConstructed) {
         var1 = -1;
      } else {
         var1 = 0;
      }

      int var2 = this.tag;
      int var3 = var1 ^ var2;
      int var4 = Arrays.hashCode(this.data);
      return var3 ^ var4;
   }

   public boolean isConstructed() {
      return this.isConstructed;
   }
}
