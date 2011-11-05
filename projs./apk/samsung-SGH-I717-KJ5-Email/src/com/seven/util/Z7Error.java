package com.seven.util;

import com.seven.util.Z7ErrorCode;
import com.seven.util.Z7Result;

public class Z7Error extends Exception {

   private Throwable m_cause;
   private String m_description;
   private Z7ErrorCode m_errorCode;
   private Z7Error m_nestedError;
   private transient String[] m_parameters;
   private Z7Result m_resultCode;


   public Z7Error(Z7ErrorCode var1) {
      this.m_description = null;
      this.m_nestedError = null;
      this.m_parameters = null;
      Z7ErrorCode var2 = Z7ErrorCode.Z7_ERR_NOERROR;
      Z7Result var3;
      if(var1 == var2) {
         var3 = Z7Result.Z7_OK;
      } else {
         var3 = Z7Result.Z7_E_FAIL;
      }

      this.init(var1, var3, (String)null, (Z7Error)null);
   }

   public Z7Error(Z7ErrorCode var1, Z7Error var2) {
      this.m_description = null;
      this.m_nestedError = null;
      this.m_parameters = null;
      Z7ErrorCode var3 = Z7ErrorCode.Z7_ERR_NOERROR;
      Z7Result var4;
      if(var1 == var3) {
         var4 = Z7Result.Z7_OK;
      } else if(var2 != null) {
         var4 = var2.m_resultCode;
      } else {
         var4 = Z7Result.Z7_E_FAIL;
      }

      this.init(var1, var4, (String)null, var2);
   }

   public Z7Error(Z7ErrorCode var1, Z7Result var2) {
      this.m_description = null;
      this.m_nestedError = null;
      this.m_parameters = null;
      this.init(var1, var2, (String)null, (Z7Error)null);
   }

   public Z7Error(Z7ErrorCode var1, Z7Result var2, Z7Error var3) {
      this.m_description = null;
      this.m_nestedError = null;
      this.m_parameters = null;
      this.init(var1, var2, (String)null, var3);
   }

   public Z7Error(Z7ErrorCode var1, Z7Result var2, String var3) {
      this.m_description = null;
      this.m_nestedError = null;
      this.m_parameters = null;
      this.init(var1, var2, var3, (Z7Error)null);
   }

   public Z7Error(Z7ErrorCode var1, Z7Result var2, String var3, Z7Error var4) {
      this.m_description = null;
      this.m_nestedError = null;
      this.m_parameters = null;
      this.init(var1, var2, var3, var4);
   }

   public Z7Error(Z7ErrorCode var1, Z7Result var2, String var3, Throwable var4) {
      this.m_description = null;
      this.m_nestedError = null;
      this.m_parameters = null;
      this.init(var1, var2, var3, (Z7Error)null);
      this.m_cause = var4;
   }

   public Z7Error(Z7ErrorCode var1, Z7Result var2, Throwable var3) {
      String var8;
      if(var3.getMessage() != null) {
         StringBuilder var4 = new StringBuilder();
         String var5 = var3.getClass().getName();
         StringBuilder var6 = var4.append(var5).append(": ");
         String var7 = var3.getMessage();
         var8 = var6.append(var7).toString();
      } else {
         var8 = var3.getClass().getName();
      }

      this(var1, var2, var8, var3);
   }

   public Z7Error(Z7ErrorCode var1, String var2) {
      this.m_description = null;
      this.m_nestedError = null;
      this.m_parameters = null;
      Z7ErrorCode var3 = Z7ErrorCode.Z7_ERR_NOERROR;
      Z7Result var4;
      if(var1 == var3) {
         var4 = Z7Result.Z7_OK;
      } else {
         var4 = Z7Result.Z7_E_FAIL;
      }

      this.init(var1, var4, var2, (Z7Error)null);
   }

   public Z7Error(Z7ErrorCode var1, String var2, Z7Error var3) {
      this.m_description = null;
      this.m_nestedError = null;
      this.m_parameters = null;
      Z7ErrorCode var4 = Z7ErrorCode.Z7_ERR_NOERROR;
      Z7Result var5;
      if(var1 == var4) {
         var5 = Z7Result.Z7_OK;
      } else if(var3 != null) {
         var5 = var3.m_resultCode;
      } else {
         var5 = Z7Result.Z7_E_FAIL;
      }

      this.init(var1, var5, (String)null, var3);
   }

   public Z7Error(Z7ErrorCode var1, Throwable var2) {
      Z7ErrorCode var3 = Z7ErrorCode.Z7_ERR_NOERROR;
      Z7Result var4;
      if(var1 == var3) {
         var4 = Z7Result.Z7_OK;
      } else {
         var4 = Z7Result.Z7_E_FAIL;
      }

      String var9;
      if(var2.getMessage() != null) {
         StringBuilder var5 = new StringBuilder();
         String var6 = var2.getClass().getName();
         StringBuilder var7 = var5.append(var6).append(": ");
         String var8 = var2.getMessage();
         var9 = var7.append(var8).toString();
      } else {
         var9 = var2.getClass().getName();
      }

      this(var1, var4, var9, var2);
   }

   public Z7Error(Throwable var1) {
      Z7ErrorCode var2 = Z7ErrorCode.Z7_ERR_INTERNAL_ERROR;
      this(var2, var1);
   }

   public static Z7Error asZ7Error(Throwable var0) {
      Z7Error var1;
      if(var0 instanceof Z7Error) {
         var1 = (Z7Error)var0;
      } else {
         var1 = new Z7Error(var0);
      }

      return var1;
   }

   private void init(Z7ErrorCode var1, Z7Result var2, String var3, Z7Error var4) {
      this.m_errorCode = var1;
      this.m_resultCode = var2;
      this.m_description = var3;
      this.m_nestedError = var4;
   }

   public Throwable getCause() {
      return this.m_cause;
   }

   public String getDescription() {
      return this.m_description;
   }

   public String getDescriptionOrParameters() {
      String var1 = this.m_description;
      if(this.m_parameters != null && this.m_parameters.length > 0) {
         StringBuilder var2 = (new StringBuilder()).append("{");
         String var3 = this.m_parameters[0];
         var1 = var2.append(var3).append("}").toString();
         int var4 = 1;

         while(true) {
            int var5 = this.m_parameters.length;
            if(var4 >= var5) {
               break;
            }

            StringBuilder var6 = (new StringBuilder()).append(var1).append(",{");
            String var7 = this.m_parameters[var4];
            var1 = var6.append(var7).append("}").toString();
            ++var4;
         }
      }

      return var1;
   }

   public Z7ErrorCode getErrorCode() {
      return this.m_errorCode;
   }

   public Z7Error getNestedError() {
      return this.m_nestedError;
   }

   public Z7Result getResultCode() {
      return this.m_resultCode;
   }

   public void printStackTrace() {
      super.printStackTrace();
      if(this.m_cause != null) {
         String var1 = this.m_cause.getClass().getName();
         if(!"javax.mail.MessagingException".equals(var1)) {
            this.m_cause.printStackTrace();
         }
      } else if(this.m_nestedError != null) {
         this.m_nestedError.printStackTrace();
      }
   }

   public void setDescription(String var1) {
      this.m_description = var1;
   }

   public void setErrorCode(Z7ErrorCode var1) {
      this.m_errorCode = var1;
   }

   public void setNestedError(Z7Error var1) {
      this.m_nestedError = var1;
   }

   public void setParameters(String[] var1) {
      this.m_parameters = var1;
   }

   public void setResultCode(Z7Result var1) {
      this.m_resultCode = var1;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      StringBuffer var2 = var1.append("Error(code=");
      Z7ErrorCode var3 = this.m_errorCode;
      var1.append(var3);
      StringBuffer var5 = var1.append(", result=");
      Z7Result var6 = this.m_resultCode;
      var1.append(var6);
      if(this.m_description != null) {
         StringBuffer var8 = var1.append(", description=\'");
         String var9 = this.m_description;
         var1.append(var9);
         StringBuffer var11 = var1.append("\'");
      }

      if(this.m_nestedError != null) {
         StringBuffer var12 = var1.append(", nested=");
         Z7Error var13 = this.m_nestedError;
         var1.append(var13);
      }

      StringBuffer var15 = var1.append(")");
      return var1.toString();
   }
}
