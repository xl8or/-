package myorg.bouncycastle.asn1.isismtt.x509;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.isismtt.x509.NamingAuthority;
import myorg.bouncycastle.asn1.isismtt.x509.ProfessionInfo;
import myorg.bouncycastle.asn1.x509.GeneralName;

public class Admissions extends ASN1Encodable {

   private GeneralName admissionAuthority;
   private NamingAuthority namingAuthority;
   private ASN1Sequence professionInfos;


   private Admissions(ASN1Sequence var1) {
      if(var1.size() > 3) {
         StringBuilder var2 = (new StringBuilder()).append("Bad sequence size: ");
         int var3 = var1.size();
         String var4 = var2.append(var3).toString();
         throw new IllegalArgumentException(var4);
      } else {
         Enumeration var5 = var1.getObjects();
         DEREncodable var6 = (DEREncodable)var5.nextElement();
         if(var6 instanceof ASN1TaggedObject) {
            switch(((ASN1TaggedObject)var6).getTagNo()) {
            case 0:
               GeneralName var10 = GeneralName.getInstance((ASN1TaggedObject)var6, (boolean)1);
               this.admissionAuthority = var10;
               break;
            case 1:
               NamingAuthority var14 = NamingAuthority.getInstance((ASN1TaggedObject)var6, (boolean)1);
               this.namingAuthority = var14;
               break;
            default:
               StringBuilder var7 = (new StringBuilder()).append("Bad tag number: ");
               int var8 = ((ASN1TaggedObject)var6).getTagNo();
               String var9 = var7.append(var8).toString();
               throw new IllegalArgumentException(var9);
            }

            var6 = (DEREncodable)var5.nextElement();
         }

         if(var6 instanceof ASN1TaggedObject) {
            switch(((ASN1TaggedObject)var6).getTagNo()) {
            case 1:
               NamingAuthority var15 = NamingAuthority.getInstance((ASN1TaggedObject)var6, (boolean)1);
               this.namingAuthority = var15;
               var6 = (DEREncodable)var5.nextElement();
               break;
            default:
               StringBuilder var11 = (new StringBuilder()).append("Bad tag number: ");
               int var12 = ((ASN1TaggedObject)var6).getTagNo();
               String var13 = var11.append(var12).toString();
               throw new IllegalArgumentException(var13);
            }
         }

         ASN1Sequence var16 = ASN1Sequence.getInstance(var6);
         this.professionInfos = var16;
         if(var5.hasMoreElements()) {
            StringBuilder var17 = (new StringBuilder()).append("Bad object encountered: ");
            Class var18 = var5.nextElement().getClass();
            String var19 = var17.append(var18).toString();
            throw new IllegalArgumentException(var19);
         }
      }
   }

   public Admissions(GeneralName var1, NamingAuthority var2, ProfessionInfo[] var3) {
      this.admissionAuthority = var1;
      this.namingAuthority = var2;
      DERSequence var4 = new DERSequence(var3);
      this.professionInfos = var4;
   }

   public static Admissions getInstance(Object var0) {
      Admissions var1;
      if(var0 != null && !(var0 instanceof Admissions)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("illegal object in getInstance: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new Admissions(var2);
      } else {
         var1 = (Admissions)var0;
      }

      return var1;
   }

   public GeneralName getAdmissionAuthority() {
      return this.admissionAuthority;
   }

   public NamingAuthority getNamingAuthority() {
      return this.namingAuthority;
   }

   public ProfessionInfo[] getProfessionInfos() {
      ProfessionInfo[] var1 = new ProfessionInfo[this.professionInfos.size()];
      int var2 = 0;

      int var4;
      for(Enumeration var3 = this.professionInfos.getObjects(); var3.hasMoreElements(); var2 = var4) {
         var4 = var2 + 1;
         ProfessionInfo var5 = ProfessionInfo.getInstance(var3.nextElement());
         var1[var2] = var5;
      }

      return var1;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if(this.admissionAuthority != null) {
         GeneralName var2 = this.admissionAuthority;
         DERTaggedObject var3 = new DERTaggedObject((boolean)1, 0, var2);
         var1.add(var3);
      }

      if(this.namingAuthority != null) {
         NamingAuthority var4 = this.namingAuthority;
         DERTaggedObject var5 = new DERTaggedObject((boolean)1, 1, var4);
         var1.add(var5);
      }

      ASN1Sequence var6 = this.professionInfos;
      var1.add(var6);
      return new DERSequence(var1);
   }
}
