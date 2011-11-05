package myorg.bouncycastle.asn1.isismtt.x509;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERIA5String;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERString;
import myorg.bouncycastle.asn1.isismtt.ISISMTTObjectIdentifiers;
import myorg.bouncycastle.asn1.x500.DirectoryString;

public class NamingAuthority extends ASN1Encodable {

   public static final DERObjectIdentifier id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern;
   private DERObjectIdentifier namingAuthorityId;
   private DirectoryString namingAuthorityText;
   private String namingAuthorityUrl;


   static {
      StringBuilder var0 = new StringBuilder();
      DERObjectIdentifier var1 = ISISMTTObjectIdentifiers.id_isismtt_at_namingAuthorities;
      String var2 = var0.append(var1).append(".1").toString();
      id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern = new DERObjectIdentifier(var2);
   }

   private NamingAuthority(ASN1Sequence var1) {
      if(var1.size() > 3) {
         StringBuilder var2 = (new StringBuilder()).append("Bad sequence size: ");
         int var3 = var1.size();
         String var4 = var2.append(var3).toString();
         throw new IllegalArgumentException(var4);
      } else {
         Enumeration var5 = var1.getObjects();
         DEREncodable var6;
         if(var5.hasMoreElements()) {
            var6 = (DEREncodable)var5.nextElement();
            if(var6 instanceof DERObjectIdentifier) {
               DERObjectIdentifier var7 = (DERObjectIdentifier)var6;
               this.namingAuthorityId = var7;
            } else if(var6 instanceof DERIA5String) {
               String var10 = DERIA5String.getInstance(var6).getString();
               this.namingAuthorityUrl = var10;
            } else {
               if(!(var6 instanceof DERString)) {
                  StringBuilder var12 = (new StringBuilder()).append("Bad object encountered: ");
                  Class var13 = var6.getClass();
                  String var14 = var12.append(var13).toString();
                  throw new IllegalArgumentException(var14);
               }

               DirectoryString var11 = DirectoryString.getInstance(var6);
               this.namingAuthorityText = var11;
            }
         }

         if(var5.hasMoreElements()) {
            var6 = (DEREncodable)var5.nextElement();
            if(var6 instanceof DERIA5String) {
               String var8 = DERIA5String.getInstance(var6).getString();
               this.namingAuthorityUrl = var8;
            } else {
               if(!(var6 instanceof DERString)) {
                  StringBuilder var16 = (new StringBuilder()).append("Bad object encountered: ");
                  Class var17 = var6.getClass();
                  String var18 = var16.append(var17).toString();
                  throw new IllegalArgumentException(var18);
               }

               DirectoryString var15 = DirectoryString.getInstance(var6);
               this.namingAuthorityText = var15;
            }
         }

         if(var5.hasMoreElements()) {
            var6 = (DEREncodable)var5.nextElement();
            if(var6 instanceof DERString) {
               DirectoryString var9 = DirectoryString.getInstance(var6);
               this.namingAuthorityText = var9;
            } else {
               StringBuilder var19 = (new StringBuilder()).append("Bad object encountered: ");
               Class var20 = var6.getClass();
               String var21 = var19.append(var20).toString();
               throw new IllegalArgumentException(var21);
            }
         }
      }
   }

   public NamingAuthority(DERObjectIdentifier var1, String var2, DirectoryString var3) {
      this.namingAuthorityId = var1;
      this.namingAuthorityUrl = var2;
      this.namingAuthorityText = var3;
   }

   public static NamingAuthority getInstance(Object var0) {
      NamingAuthority var1;
      if(var0 != null && !(var0 instanceof NamingAuthority)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("illegal object in getInstance: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new NamingAuthority(var2);
      } else {
         var1 = (NamingAuthority)var0;
      }

      return var1;
   }

   public static NamingAuthority getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public DERObjectIdentifier getNamingAuthorityId() {
      return this.namingAuthorityId;
   }

   public DirectoryString getNamingAuthorityText() {
      return this.namingAuthorityText;
   }

   public String getNamingAuthorityUrl() {
      return this.namingAuthorityUrl;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if(this.namingAuthorityId != null) {
         DERObjectIdentifier var2 = this.namingAuthorityId;
         var1.add(var2);
      }

      if(this.namingAuthorityUrl != null) {
         String var3 = this.namingAuthorityUrl;
         DERIA5String var4 = new DERIA5String(var3, (boolean)1);
         var1.add(var4);
      }

      if(this.namingAuthorityText != null) {
         DirectoryString var5 = this.namingAuthorityText;
         var1.add(var5);
      }

      return new DERSequence(var1);
   }
}
