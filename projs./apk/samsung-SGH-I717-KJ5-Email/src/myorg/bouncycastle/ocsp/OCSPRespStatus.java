package myorg.bouncycastle.ocsp;


public interface OCSPRespStatus {

   int INTERNAL_ERROR = 2;
   int MALFORMED_REQUEST = 1;
   int SIGREQUIRED = 5;
   int SUCCESSFUL = 0;
   int TRY_LATER = 3;
   int UNAUTHORIZED = 6;

}
