package com.android.email.smime;


public class CertificateManagerException extends Exception {

   private int mErrorCode;


   private CertificateManagerException() {}

   public CertificateManagerException(String var1) {
      super(var1);
   }

   public CertificateManagerException(String var1, int var2) {}
}
