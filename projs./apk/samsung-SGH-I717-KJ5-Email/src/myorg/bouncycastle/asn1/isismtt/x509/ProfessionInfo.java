package myorg.bouncycastle.asn1.isismtt.x509;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERPrintableString;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.isismtt.x509.NamingAuthority;
import myorg.bouncycastle.asn1.x500.DirectoryString;

public class ProfessionInfo extends ASN1Encodable {

   public static final DERObjectIdentifier Notar;
   public static final DERObjectIdentifier Notariatsverwalter;
   public static final DERObjectIdentifier Notariatsverwalterin;
   public static final DERObjectIdentifier Notarin;
   public static final DERObjectIdentifier Notarvertreter;
   public static final DERObjectIdentifier Notarvertreterin;
   public static final DERObjectIdentifier Patentanwalt;
   public static final DERObjectIdentifier Patentanwltin;
   public static final DERObjectIdentifier Rechtsanwalt;
   public static final DERObjectIdentifier Rechtsanwltin;
   public static final DERObjectIdentifier Rechtsbeistand;
   public static final DERObjectIdentifier Steuerberater;
   public static final DERObjectIdentifier Steuerberaterin;
   public static final DERObjectIdentifier Steuerbevollmchtigte;
   public static final DERObjectIdentifier Steuerbevollmchtigter;
   public static final DERObjectIdentifier VereidigteBuchprferin;
   public static final DERObjectIdentifier VereidigterBuchprfer;
   public static final DERObjectIdentifier Wirtschaftsprfer;
   public static final DERObjectIdentifier Wirtschaftsprferin;
   private ASN1OctetString addProfessionInfo;
   private NamingAuthority namingAuthority;
   private ASN1Sequence professionItems;
   private ASN1Sequence professionOIDs;
   private String registrationNumber;


   static {
      StringBuilder var0 = new StringBuilder();
      DERObjectIdentifier var1 = NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern;
      String var2 = var0.append(var1).append(".1").toString();
      Rechtsanwltin = new DERObjectIdentifier(var2);
      StringBuilder var3 = new StringBuilder();
      DERObjectIdentifier var4 = NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern;
      String var5 = var3.append(var4).append(".2").toString();
      Rechtsanwalt = new DERObjectIdentifier(var5);
      StringBuilder var6 = new StringBuilder();
      DERObjectIdentifier var7 = NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern;
      String var8 = var6.append(var7).append(".3").toString();
      Rechtsbeistand = new DERObjectIdentifier(var8);
      StringBuilder var9 = new StringBuilder();
      DERObjectIdentifier var10 = NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern;
      String var11 = var9.append(var10).append(".4").toString();
      Steuerberaterin = new DERObjectIdentifier(var11);
      StringBuilder var12 = new StringBuilder();
      DERObjectIdentifier var13 = NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern;
      String var14 = var12.append(var13).append(".5").toString();
      Steuerberater = new DERObjectIdentifier(var14);
      StringBuilder var15 = new StringBuilder();
      DERObjectIdentifier var16 = NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern;
      String var17 = var15.append(var16).append(".6").toString();
      Steuerbevollmchtigte = new DERObjectIdentifier(var17);
      StringBuilder var18 = new StringBuilder();
      DERObjectIdentifier var19 = NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern;
      String var20 = var18.append(var19).append(".7").toString();
      Steuerbevollmchtigter = new DERObjectIdentifier(var20);
      StringBuilder var21 = new StringBuilder();
      DERObjectIdentifier var22 = NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern;
      String var23 = var21.append(var22).append(".8").toString();
      Notarin = new DERObjectIdentifier(var23);
      StringBuilder var24 = new StringBuilder();
      DERObjectIdentifier var25 = NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern;
      String var26 = var24.append(var25).append(".9").toString();
      Notar = new DERObjectIdentifier(var26);
      StringBuilder var27 = new StringBuilder();
      DERObjectIdentifier var28 = NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern;
      String var29 = var27.append(var28).append(".10").toString();
      Notarvertreterin = new DERObjectIdentifier(var29);
      StringBuilder var30 = new StringBuilder();
      DERObjectIdentifier var31 = NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern;
      String var32 = var30.append(var31).append(".11").toString();
      Notarvertreter = new DERObjectIdentifier(var32);
      StringBuilder var33 = new StringBuilder();
      DERObjectIdentifier var34 = NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern;
      String var35 = var33.append(var34).append(".12").toString();
      Notariatsverwalterin = new DERObjectIdentifier(var35);
      StringBuilder var36 = new StringBuilder();
      DERObjectIdentifier var37 = NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern;
      String var38 = var36.append(var37).append(".13").toString();
      Notariatsverwalter = new DERObjectIdentifier(var38);
      StringBuilder var39 = new StringBuilder();
      DERObjectIdentifier var40 = NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern;
      String var41 = var39.append(var40).append(".14").toString();
      Wirtschaftsprferin = new DERObjectIdentifier(var41);
      StringBuilder var42 = new StringBuilder();
      DERObjectIdentifier var43 = NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern;
      String var44 = var42.append(var43).append(".15").toString();
      Wirtschaftsprfer = new DERObjectIdentifier(var44);
      StringBuilder var45 = new StringBuilder();
      DERObjectIdentifier var46 = NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern;
      String var47 = var45.append(var46).append(".16").toString();
      VereidigteBuchprferin = new DERObjectIdentifier(var47);
      StringBuilder var48 = new StringBuilder();
      DERObjectIdentifier var49 = NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern;
      String var50 = var48.append(var49).append(".17").toString();
      VereidigterBuchprfer = new DERObjectIdentifier(var50);
      StringBuilder var51 = new StringBuilder();
      DERObjectIdentifier var52 = NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern;
      String var53 = var51.append(var52).append(".18").toString();
      Patentanwltin = new DERObjectIdentifier(var53);
      StringBuilder var54 = new StringBuilder();
      DERObjectIdentifier var55 = NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern;
      String var56 = var54.append(var55).append(".19").toString();
      Patentanwalt = new DERObjectIdentifier(var56);
   }

   private ProfessionInfo(ASN1Sequence var1) {
      if(var1.size() > 5) {
         StringBuilder var2 = (new StringBuilder()).append("Bad sequence size: ");
         int var3 = var1.size();
         String var4 = var2.append(var3).toString();
         throw new IllegalArgumentException(var4);
      } else {
         Enumeration var5 = var1.getObjects();
         DEREncodable var6 = (DEREncodable)var5.nextElement();
         if(var6 instanceof ASN1TaggedObject) {
            if(((ASN1TaggedObject)var6).getTagNo() != 0) {
               StringBuilder var7 = (new StringBuilder()).append("Bad tag number: ");
               int var8 = ((ASN1TaggedObject)var6).getTagNo();
               String var9 = var7.append(var8).toString();
               throw new IllegalArgumentException(var9);
            }

            NamingAuthority var10 = NamingAuthority.getInstance((ASN1TaggedObject)var6, (boolean)1);
            this.namingAuthority = var10;
            var6 = (DEREncodable)var5.nextElement();
         }

         ASN1Sequence var11 = ASN1Sequence.getInstance(var6);
         this.professionItems = var11;
         if(var5.hasMoreElements()) {
            var6 = (DEREncodable)var5.nextElement();
            if(var6 instanceof ASN1Sequence) {
               ASN1Sequence var12 = ASN1Sequence.getInstance(var6);
               this.professionOIDs = var12;
            } else if(var6 instanceof DERPrintableString) {
               String var15 = DERPrintableString.getInstance(var6).getString();
               this.registrationNumber = var15;
            } else {
               if(!(var6 instanceof ASN1OctetString)) {
                  StringBuilder var17 = (new StringBuilder()).append("Bad object encountered: ");
                  Class var18 = var6.getClass();
                  String var19 = var17.append(var18).toString();
                  throw new IllegalArgumentException(var19);
               }

               ASN1OctetString var16 = ASN1OctetString.getInstance(var6);
               this.addProfessionInfo = var16;
            }
         }

         if(var5.hasMoreElements()) {
            var6 = (DEREncodable)var5.nextElement();
            if(var6 instanceof DERPrintableString) {
               String var13 = DERPrintableString.getInstance(var6).getString();
               this.registrationNumber = var13;
            } else {
               if(!(var6 instanceof DEROctetString)) {
                  StringBuilder var21 = (new StringBuilder()).append("Bad object encountered: ");
                  Class var22 = var6.getClass();
                  String var23 = var21.append(var22).toString();
                  throw new IllegalArgumentException(var23);
               }

               DEROctetString var20 = (DEROctetString)var6;
               this.addProfessionInfo = var20;
            }
         }

         if(var5.hasMoreElements()) {
            var6 = (DEREncodable)var5.nextElement();
            if(var6 instanceof DEROctetString) {
               DEROctetString var14 = (DEROctetString)var6;
               this.addProfessionInfo = var14;
            } else {
               StringBuilder var24 = (new StringBuilder()).append("Bad object encountered: ");
               Class var25 = var6.getClass();
               String var26 = var24.append(var25).toString();
               throw new IllegalArgumentException(var26);
            }
         }
      }
   }

   public ProfessionInfo(NamingAuthority var1, DirectoryString[] var2, DERObjectIdentifier[] var3, String var4, ASN1OctetString var5) {
      this.namingAuthority = var1;
      ASN1EncodableVector var6 = new ASN1EncodableVector();
      int var7 = 0;

      while(true) {
         int var8 = var2.length;
         if(var7 == var8) {
            DERSequence var10 = new DERSequence(var6);
            this.professionItems = var10;
            if(var3 != null) {
               var6 = new ASN1EncodableVector();
               byte var15 = 0;

               while(true) {
                  int var11 = var3.length;
                  if(var15 == var11) {
                     DERSequence var14 = new DERSequence(var6);
                     this.professionOIDs = var14;
                     break;
                  }

                  DERObjectIdentifier var12 = var3[var15];
                  var6.add(var12);
                  int var13 = var15 + 1;
               }
            }

            this.registrationNumber = var4;
            this.addProfessionInfo = var5;
            return;
         }

         DirectoryString var9 = var2[var7];
         var6.add(var9);
         ++var7;
      }
   }

   public static ProfessionInfo getInstance(Object var0) {
      ProfessionInfo var1;
      if(var0 != null && !(var0 instanceof ProfessionInfo)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("illegal object in getInstance: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new ProfessionInfo(var2);
      } else {
         var1 = (ProfessionInfo)var0;
      }

      return var1;
   }

   public ASN1OctetString getAddProfessionInfo() {
      return this.addProfessionInfo;
   }

   public NamingAuthority getNamingAuthority() {
      return this.namingAuthority;
   }

   public DirectoryString[] getProfessionItems() {
      DirectoryString[] var1 = new DirectoryString[this.professionItems.size()];
      int var2 = 0;

      int var4;
      for(Enumeration var3 = this.professionItems.getObjects(); var3.hasMoreElements(); var2 = var4) {
         var4 = var2 + 1;
         DirectoryString var5 = DirectoryString.getInstance(var3.nextElement());
         var1[var2] = var5;
      }

      return var1;
   }

   public DERObjectIdentifier[] getProfessionOIDs() {
      DERObjectIdentifier[] var1;
      if(this.professionOIDs == null) {
         var1 = new DERObjectIdentifier[0];
      } else {
         DERObjectIdentifier[] var2 = new DERObjectIdentifier[this.professionOIDs.size()];
         int var3 = 0;

         int var5;
         for(Enumeration var4 = this.professionOIDs.getObjects(); var4.hasMoreElements(); var3 = var5) {
            var5 = var3 + 1;
            DERObjectIdentifier var6 = DERObjectIdentifier.getInstance(var4.nextElement());
            var2[var3] = var6;
         }

         var1 = var2;
      }

      return var1;
   }

   public String getRegistrationNumber() {
      return this.registrationNumber;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if(this.namingAuthority != null) {
         NamingAuthority var2 = this.namingAuthority;
         DERTaggedObject var3 = new DERTaggedObject((boolean)1, 0, var2);
         var1.add(var3);
      }

      ASN1Sequence var4 = this.professionItems;
      var1.add(var4);
      if(this.professionOIDs != null) {
         ASN1Sequence var5 = this.professionOIDs;
         var1.add(var5);
      }

      if(this.registrationNumber != null) {
         String var6 = this.registrationNumber;
         DERPrintableString var7 = new DERPrintableString(var6, (boolean)1);
         var1.add(var7);
      }

      if(this.addProfessionInfo != null) {
         ASN1OctetString var8 = this.addProfessionInfo;
         var1.add(var8);
      }

      return new DERSequence(var1);
   }
}
