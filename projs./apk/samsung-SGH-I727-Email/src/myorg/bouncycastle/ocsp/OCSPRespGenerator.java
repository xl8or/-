package myorg.bouncycastle.ocsp;

import java.io.IOException;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.ocsp.OCSPObjectIdentifiers;
import myorg.bouncycastle.asn1.ocsp.OCSPResponse;
import myorg.bouncycastle.asn1.ocsp.OCSPResponseStatus;
import myorg.bouncycastle.asn1.ocsp.ResponseBytes;
import myorg.bouncycastle.ocsp.BasicOCSPResp;
import myorg.bouncycastle.ocsp.OCSPException;
import myorg.bouncycastle.ocsp.OCSPResp;

public class OCSPRespGenerator {

   public static final int INTERNAL_ERROR = 2;
   public static final int MALFORMED_REQUEST = 1;
   public static final int SIG_REQUIRED = 5;
   public static final int SUCCESSFUL = 0;
   public static final int TRY_LATER = 3;
   public static final int UNAUTHORIZED = 6;


   public OCSPRespGenerator() {}

   public OCSPResp generate(int var1, Object var2) throws OCSPException {
      OCSPResp var5;
      if(var2 == null) {
         OCSPResponseStatus var3 = new OCSPResponseStatus(var1);
         OCSPResponse var4 = new OCSPResponse(var3, (ResponseBytes)null);
         var5 = new OCSPResp(var4);
      } else {
         if(!(var2 instanceof BasicOCSPResp)) {
            throw new OCSPException("unknown response object");
         }

         BasicOCSPResp var6 = (BasicOCSPResp)var2;

         DEROctetString var8;
         try {
            byte[] var7 = var6.getEncoded();
            var8 = new DEROctetString(var7);
         } catch (IOException var14) {
            throw new OCSPException("can\'t encode object.", var14);
         }

         DERObjectIdentifier var9 = OCSPObjectIdentifiers.id_pkix_ocsp_basic;
         ResponseBytes var10 = new ResponseBytes(var9, var8);
         OCSPResponseStatus var11 = new OCSPResponseStatus(var1);
         OCSPResponse var12 = new OCSPResponse(var11, var10);
         var5 = new OCSPResp(var12);
      }

      return var5;
   }
}
