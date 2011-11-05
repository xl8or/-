package myorg.bouncycastle.sasn1;

import java.io.OutputStream;

public abstract class Asn1Generator {

   protected OutputStream _out;


   public Asn1Generator(OutputStream var1) {
      this._out = var1;
   }

   public abstract OutputStream getRawOutputStream();
}
