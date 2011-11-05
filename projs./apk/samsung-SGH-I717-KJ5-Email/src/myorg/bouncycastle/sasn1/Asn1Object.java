package myorg.bouncycastle.sasn1;

import java.io.InputStream;

public abstract class Asn1Object {

   protected int _baseTag;
   protected InputStream _contentStream;
   protected int _tagNumber;


   protected Asn1Object(int var1, int var2, InputStream var3) {
      this._baseTag = var1;
      this._tagNumber = var2;
      this._contentStream = var3;
   }

   public InputStream getRawContentStream() {
      return this._contentStream;
   }

   public int getTagNumber() {
      return this._tagNumber;
   }

   public boolean isConstructed() {
      boolean var1;
      if((this._baseTag & 32) != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }
}
