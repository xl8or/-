package com.android.email.certificateManager;

import java.util.List;

public class CertificateInfo {

   List<String> mExtendedKeyUsage;
   boolean[] mKeyUsage;
   String mSubject;


   public CertificateInfo(boolean[] var1, List<String> var2, String var3) {
      this.mKeyUsage = var1;
      this.mExtendedKeyUsage = var2;
      this.mSubject = var3;
   }

   public List<String> getExtendedKeyUsage() {
      return this.mExtendedKeyUsage;
   }

   public boolean[] getKeyUsage() {
      return this.mKeyUsage;
   }

   public String getSubject() {
      return this.mSubject;
   }

   public void setExtendedKeyUsage(List<String> var1) {
      this.mExtendedKeyUsage = var1;
   }

   public void setKeyUsage(boolean[] var1) {
      this.mKeyUsage = var1;
   }

   public void setmSubject(String var1) {
      this.mSubject = var1;
   }
}
