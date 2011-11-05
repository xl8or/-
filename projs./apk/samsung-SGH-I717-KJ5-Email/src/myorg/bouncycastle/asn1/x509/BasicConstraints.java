package myorg.bouncycastle.asn1.x509;

import java.math.BigInteger;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERBoolean;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.X509Extension;

public class BasicConstraints extends ASN1Encodable {

   DERBoolean cA;
   DERInteger pathLenConstraint;


   public BasicConstraints(int var1) {
      DERBoolean var2 = new DERBoolean((boolean)0);
      this.cA = var2;
      this.pathLenConstraint = null;
      DERBoolean var3 = new DERBoolean((boolean)1);
      this.cA = var3;
      DERInteger var4 = new DERInteger(var1);
      this.pathLenConstraint = var4;
   }

   public BasicConstraints(ASN1Sequence var1) {
      DERBoolean var2 = new DERBoolean((boolean)0);
      this.cA = var2;
      this.pathLenConstraint = null;
      if(var1.size() == 0) {
         this.cA = null;
         this.pathLenConstraint = null;
      } else {
         if(var1.getObjectAt(0) instanceof DERBoolean) {
            DERBoolean var3 = DERBoolean.getInstance(var1.getObjectAt(0));
            this.cA = var3;
         } else {
            this.cA = null;
            DERInteger var5 = DERInteger.getInstance(var1.getObjectAt(0));
            this.pathLenConstraint = var5;
         }

         if(var1.size() > 1) {
            if(this.cA != null) {
               DERInteger var4 = DERInteger.getInstance(var1.getObjectAt(1));
               this.pathLenConstraint = var4;
            } else {
               throw new IllegalArgumentException("wrong sequence in constructor");
            }
         }
      }
   }

   public BasicConstraints(boolean var1) {
      DERBoolean var2 = new DERBoolean((boolean)0);
      this.cA = var2;
      this.pathLenConstraint = null;
      if(var1) {
         DERBoolean var3 = new DERBoolean((boolean)1);
         this.cA = var3;
      } else {
         this.cA = null;
      }

      this.pathLenConstraint = null;
   }

   public BasicConstraints(boolean var1, int var2) {
      DERBoolean var3 = new DERBoolean((boolean)0);
      this.cA = var3;
      this.pathLenConstraint = null;
      if(var1) {
         DERBoolean var4 = new DERBoolean(var1);
         this.cA = var4;
         DERInteger var5 = new DERInteger(var2);
         this.pathLenConstraint = var5;
      } else {
         this.cA = null;
         this.pathLenConstraint = null;
      }
   }

   public static BasicConstraints getInstance(Object var0) {
      BasicConstraints var1;
      if(var0 != null && !(var0 instanceof BasicConstraints)) {
         if(var0 instanceof ASN1Sequence) {
            ASN1Sequence var2 = (ASN1Sequence)var0;
            var1 = new BasicConstraints(var2);
         } else {
            if(!(var0 instanceof X509Extension)) {
               StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
               String var4 = var0.getClass().getName();
               String var5 = var3.append(var4).toString();
               throw new IllegalArgumentException(var5);
            }

            var1 = getInstance(X509Extension.convertValueToObject((X509Extension)var0));
         }
      } else {
         var1 = (BasicConstraints)var0;
      }

      return var1;
   }

   public static BasicConstraints getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public BigInteger getPathLenConstraint() {
      BigInteger var1;
      if(this.pathLenConstraint != null) {
         var1 = this.pathLenConstraint.getValue();
      } else {
         var1 = null;
      }

      return var1;
   }

   public boolean isCA() {
      boolean var1;
      if(this.cA != null && this.cA.isTrue()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if(this.cA != null) {
         DERBoolean var2 = this.cA;
         var1.add(var2);
      }

      if(this.pathLenConstraint != null) {
         DERInteger var3 = this.pathLenConstraint;
         var1.add(var3);
      }

      return new DERSequence(var1);
   }

   public String toString() {
      String var1;
      if(this.pathLenConstraint == null) {
         if(this.cA == null) {
            var1 = "BasicConstraints: isCa(false)";
         } else {
            StringBuilder var2 = (new StringBuilder()).append("BasicConstraints: isCa(");
            boolean var3 = this.isCA();
            var1 = var2.append(var3).append(")").toString();
         }
      } else {
         StringBuilder var4 = (new StringBuilder()).append("BasicConstraints: isCa(");
         boolean var5 = this.isCA();
         StringBuilder var6 = var4.append(var5).append("), pathLenConstraint = ");
         BigInteger var7 = this.pathLenConstraint.getValue();
         var1 = var6.append(var7).toString();
      }

      return var1;
   }
}
