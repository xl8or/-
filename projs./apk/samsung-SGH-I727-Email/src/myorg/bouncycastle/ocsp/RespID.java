package myorg.bouncycastle.ocsp;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.PublicKey;
import javax.security.auth.x500.X500Principal;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.ocsp.ResponderID;
import myorg.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import myorg.bouncycastle.jce.X509Principal;
import myorg.bouncycastle.ocsp.OCSPException;
import myorg.bouncycastle.ocsp.OCSPUtil;

public class RespID {

   ResponderID id;


   public RespID(PublicKey var1) throws OCSPException {
      try {
         MessageDigest var2 = OCSPUtil.createDigestInstance("SHA1", (String)null);
         byte[] var3 = var1.getEncoded();
         byte[] var4 = SubjectPublicKeyInfo.getInstance((new ASN1InputStream(var3)).readObject()).getPublicKeyData().getBytes();
         var2.update(var4);
         byte[] var5 = var2.digest();
         DEROctetString var6 = new DEROctetString(var5);
         ResponderID var7 = new ResponderID(var6);
         this.id = var7;
      } catch (Exception var10) {
         String var9 = "problem creating ID: " + var10;
         throw new OCSPException(var9, var10);
      }
   }

   public RespID(X500Principal var1) {
      try {
         byte[] var2 = var1.getEncoded();
         X509Principal var3 = new X509Principal(var2);
         ResponderID var4 = new ResponderID(var3);
         this.id = var4;
      } catch (IOException var6) {
         throw new IllegalArgumentException("can\'t decode name.");
      }
   }

   public RespID(ResponderID var1) {
      this.id = var1;
   }

   public boolean equals(Object var1) {
      byte var2;
      if(!(var1 instanceof RespID)) {
         var2 = 0;
      } else {
         RespID var3 = (RespID)var1;
         ResponderID var4 = this.id;
         ResponderID var5 = var3.id;
         var2 = var4.equals(var5);
      }

      return (boolean)var2;
   }

   public int hashCode() {
      return this.id.hashCode();
   }

   public ResponderID toASN1Object() {
      return this.id;
   }
}
