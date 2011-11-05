package myorg.bouncycastle.asn1.isismtt.x509;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.isismtt.x509.Admissions;
import myorg.bouncycastle.asn1.x509.GeneralName;

public class AdmissionSyntax extends ASN1Encodable {

   private GeneralName admissionAuthority;
   private ASN1Sequence contentsOfAdmissions;


   private AdmissionSyntax(ASN1Sequence var1) {
      switch(var1.size()) {
      case 1:
         ASN1Sequence var5 = DERSequence.getInstance(var1.getObjectAt(0));
         this.contentsOfAdmissions = var5;
         return;
      case 2:
         GeneralName var6 = GeneralName.getInstance(var1.getObjectAt(0));
         this.admissionAuthority = var6;
         ASN1Sequence var7 = DERSequence.getInstance(var1.getObjectAt(1));
         this.contentsOfAdmissions = var7;
         return;
      default:
         StringBuilder var2 = (new StringBuilder()).append("Bad sequence size: ");
         int var3 = var1.size();
         String var4 = var2.append(var3).toString();
         throw new IllegalArgumentException(var4);
      }
   }

   public AdmissionSyntax(GeneralName var1, ASN1Sequence var2) {
      this.admissionAuthority = var1;
      this.contentsOfAdmissions = var2;
   }

   public static AdmissionSyntax getInstance(Object var0) {
      AdmissionSyntax var1;
      if(var0 != null && !(var0 instanceof AdmissionSyntax)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("illegal object in getInstance: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new AdmissionSyntax(var2);
      } else {
         var1 = (AdmissionSyntax)var0;
      }

      return var1;
   }

   public GeneralName getAdmissionAuthority() {
      return this.admissionAuthority;
   }

   public Admissions[] getContentsOfAdmissions() {
      Admissions[] var1 = new Admissions[this.contentsOfAdmissions.size()];
      int var2 = 0;

      int var4;
      for(Enumeration var3 = this.contentsOfAdmissions.getObjects(); var3.hasMoreElements(); var2 = var4) {
         var4 = var2 + 1;
         Admissions var5 = Admissions.getInstance(var3.nextElement());
         var1[var2] = var5;
      }

      return var1;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if(this.admissionAuthority != null) {
         GeneralName var2 = this.admissionAuthority;
         var1.add(var2);
      }

      ASN1Sequence var3 = this.contentsOfAdmissions;
      var1.add(var3);
      return new DERSequence(var1);
   }
}
