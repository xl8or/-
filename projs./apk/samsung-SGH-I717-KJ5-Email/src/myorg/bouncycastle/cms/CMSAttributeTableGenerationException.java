package myorg.bouncycastle.cms;

import myorg.bouncycastle.cms.CMSRuntimeException;

public class CMSAttributeTableGenerationException extends CMSRuntimeException {

   Exception e;


   public CMSAttributeTableGenerationException(String var1) {
      super(var1);
   }

   public CMSAttributeTableGenerationException(String var1, Exception var2) {
      super(var1);
      this.e = var2;
   }

   public Throwable getCause() {
      return this.e;
   }

   public Exception getUnderlyingException() {
      return this.e;
   }
}
