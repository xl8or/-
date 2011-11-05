package myorg.bouncycastle.sasn1;

import java.io.IOException;
import java.io.InputStream;
import myorg.bouncycastle.sasn1.Asn1InputStream;
import myorg.bouncycastle.sasn1.Asn1Object;
import myorg.bouncycastle.sasn1.BerOctetString;
import myorg.bouncycastle.sasn1.BerSequence;
import myorg.bouncycastle.sasn1.BerSet;
import myorg.bouncycastle.sasn1.DefiniteLengthInputStream;
import myorg.bouncycastle.sasn1.DerOctetString;
import myorg.bouncycastle.sasn1.DerSequence;
import myorg.bouncycastle.sasn1.DerSet;
import myorg.bouncycastle.sasn1.IndefiniteLengthInputStream;

public class Asn1TaggedObject extends Asn1Object {

   protected Asn1TaggedObject(int var1, int var2, InputStream var3) {
      super(var1, var2, var3);
   }

   public Asn1Object getObject(int var1, boolean var2) throws IOException {
      Object var4;
      if(var2) {
         InputStream var3 = this.getRawContentStream();
         var4 = (new Asn1InputStream(var3)).readObject();
      } else {
         switch(var1) {
         case 4:
            if(this.getRawContentStream() instanceof IndefiniteLengthInputStream) {
               InputStream var9 = this.getRawContentStream();
               var4 = new BerOctetString(32, var9);
            } else if(this.isConstructed()) {
               byte[] var10 = ((DefiniteLengthInputStream)this.getRawContentStream()).toByteArray();
               var4 = new DerOctetString(32, var10);
            } else {
               byte[] var11 = ((DefiniteLengthInputStream)this.getRawContentStream()).toByteArray();
               var4 = new DerOctetString(0, var11);
            }
            break;
         case 16:
            if(this.getRawContentStream() instanceof IndefiniteLengthInputStream) {
               InputStream var7 = this.getRawContentStream();
               var4 = new BerSequence(32, var7);
            } else {
               byte[] var8 = ((DefiniteLengthInputStream)this.getRawContentStream()).toByteArray();
               var4 = new DerSequence(32, var8);
            }
            break;
         case 17:
            if(this.getRawContentStream() instanceof IndefiniteLengthInputStream) {
               InputStream var5 = this.getRawContentStream();
               var4 = new BerSet(32, var5);
            } else {
               byte[] var6 = ((DefiniteLengthInputStream)this.getRawContentStream()).toByteArray();
               var4 = new DerSet(32, var6);
            }
            break;
         default:
            throw new RuntimeException("implicit tagging not implemented");
         }
      }

      return (Asn1Object)var4;
   }
}
