package myorg.bouncycastle.asn1.cmp;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;

public class PKIStatus extends ASN1Encodable {

   public static final int GRANTED = 0;
   public static final int GRANTED_WITH_MODS = 1;
   public static final int KEY_UPDATE_WARNING = 6;
   public static final int REJECTION = 2;
   public static final int REVOCATION_NOTIFICATION = 5;
   public static final int REVOCATION_WARNING = 4;
   public static final int WAITING = 3;
   public static final PKIStatus granted = new PKIStatus(0);
   public static final PKIStatus grantedWithMods = new PKIStatus(1);
   public static final PKIStatus keyUpdateWaiting = new PKIStatus(6);
   public static final PKIStatus rejection = new PKIStatus(2);
   public static final PKIStatus revocationNotification = new PKIStatus(5);
   public static final PKIStatus revocationWarning = new PKIStatus(4);
   public static final PKIStatus waiting = new PKIStatus(3);
   private DERInteger value;


   private PKIStatus(int var1) {
      DERInteger var2 = new DERInteger(var1);
      this(var2);
   }

   private PKIStatus(DERInteger var1) {
      this.value = var1;
   }

   public static PKIStatus getInstance(Object var0) {
      PKIStatus var1;
      if(var0 instanceof PKIStatus) {
         var1 = (PKIStatus)var0;
      } else {
         if(!(var0 instanceof DERInteger)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         DERInteger var2 = (DERInteger)var0;
         var1 = new PKIStatus(var2);
      }

      return var1;
   }

   public DERObject toASN1Object() {
      return this.value;
   }
}
