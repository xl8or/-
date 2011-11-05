package myorg.bouncycastle.asn1.ocsp;

import myorg.bouncycastle.asn1.DEREnumerated;

public class OCSPResponseStatus extends DEREnumerated {

   public static final int INTERNAL_ERROR = 2;
   public static final int MALFORMED_REQUEST = 1;
   public static final int SIG_REQUIRED = 5;
   public static final int SUCCESSFUL = 0;
   public static final int TRY_LATER = 3;
   public static final int UNAUTHORIZED = 6;


   public OCSPResponseStatus(int var1) {
      super(var1);
   }

   public OCSPResponseStatus(DEREnumerated var1) {
      int var2 = var1.getValue().intValue();
      super(var2);
   }
}
