package gnu.inet.ldap;


public class LDAPResult {

   public static final int ADMIN_LIMIT_EXCEEDED = 11;
   public static final int AFFECTS_MULTIPLE_DSAS = 71;
   public static final int ALIAS_DEREFERENCING_PROBLEM = 36;
   public static final int ALIAS_PROBLEM = 33;
   public static final int ATTRIBUTE_OR_VALUE_EXISTS = 20;
   public static final int AUTH_METHOD_NOT_SUPPORTED = 7;
   public static final int BUSY = 51;
   public static final int COMPARE_FALSE = 5;
   public static final int COMPARE_TRUE = 6;
   public static final int CONFIDENTIALITY_REQUIRED = 13;
   public static final int CONSTRAINT_VIOLATION = 19;
   public static final int ENTRY_ALREADY_EXISTS = 68;
   public static final int INAPPROPRIATE_AUTHENTICATION = 48;
   public static final int INAPPROPRIATE_MATCHING = 18;
   public static final int INSUFFICIENT_ACCESS_RIGHTS = 50;
   public static final int INVALID_ATTRIBUTE_SYNTAX = 21;
   public static final int INVALID_CREDENTIALS = 49;
   public static final int INVALID_DN_SYNTAX = 34;
   public static final int LOOP_DETECT = 54;
   public static final int NAMING_VIOLATION = 64;
   public static final int NOT_ALLOWED_ON_NON_LEAF = 66;
   public static final int NOT_ALLOWED_ON_RDN = 67;
   public static final int NO_SUCH_ATTRIBUTE = 16;
   public static final int NO_SUCH_OBJECT = 32;
   public static final int OBJECT_CLASS_MODS_PROHIBITED = 69;
   public static final int OBJECT_CLASS_VIOLATION = 65;
   public static final int OPERATIONS_ERROR = 1;
   public static final int OTHER = 80;
   public static final int PROTOCOL_ERROR = 2;
   public static final int REFERRAL = 10;
   public static final int SASL_BIND_IN_PROGRESS = 14;
   public static final int SIZE_LIMIT_EXCEEDED = 4;
   public static final int STRONG_AUTH_REQUIRED = 8;
   public static final int SUCCESS = 0;
   public static final int TIME_LIMIT_EXCEEDED = 3;
   public static final int UNAVAILABLE = 52;
   public static final int UNAVAILABLE_CRITICAL_EXTENSION = 12;
   public static final int UNDEFINED_ATTRIBUTE_TYPE = 17;
   public static final int UNWILLING_TO_PERFORM = 53;
   public final String errorMessage;
   public final String matchedDN;
   public final String[] referrals;
   public final int status;


   protected LDAPResult(int var1, String var2, String var3, String[] var4) {
      this.status = var1;
      this.matchedDN = var2;
      this.errorMessage = var3;
      this.referrals = var4;
   }

   public String toString() {
      String var1 = this.getClass().getName();
      StringBuffer var2 = new StringBuffer(var1);
      StringBuffer var3 = var2.append('[');
      StringBuffer var4 = var2.append("status=");
      int var5 = this.status;
      var2.append(var5);
      StringBuffer var7 = var2.append(",matchedDN=");
      String var8 = this.matchedDN;
      var2.append(var8);
      if(this.errorMessage != null) {
         StringBuffer var10 = var2.append(",errorMessage=");
         String var11 = this.errorMessage;
         var2.append(var11);
      }

      if(this.referrals != null) {
         StringBuffer var13 = var2.append(",referrals=");
         String var14 = this.referrals.toString();
         var2.append(var14);
      }

      StringBuffer var16 = var2.append(']');
      return var2.toString();
   }
}
