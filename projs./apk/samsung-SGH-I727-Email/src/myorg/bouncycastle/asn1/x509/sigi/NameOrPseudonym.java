package myorg.bouncycastle.asn1.x509.sigi;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Choice;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERString;
import myorg.bouncycastle.asn1.x500.DirectoryString;

public class NameOrPseudonym extends ASN1Encodable implements ASN1Choice {

   private ASN1Sequence givenName;
   private DirectoryString pseudonym;
   private DirectoryString surname;


   public NameOrPseudonym(String var1) {
      DirectoryString var2 = new DirectoryString(var1);
      this(var2);
   }

   private NameOrPseudonym(ASN1Sequence var1) {
      if(var1.size() != 2) {
         StringBuilder var2 = (new StringBuilder()).append("Bad sequence size: ");
         int var3 = var1.size();
         String var4 = var2.append(var3).toString();
         throw new IllegalArgumentException(var4);
      } else if(!(var1.getObjectAt(0) instanceof DERString)) {
         StringBuilder var5 = (new StringBuilder()).append("Bad object encountered: ");
         Class var6 = var1.getObjectAt(0).getClass();
         String var7 = var5.append(var6).toString();
         throw new IllegalArgumentException(var7);
      } else {
         DirectoryString var8 = DirectoryString.getInstance(var1.getObjectAt(0));
         this.surname = var8;
         ASN1Sequence var9 = ASN1Sequence.getInstance(var1.getObjectAt(1));
         this.givenName = var9;
      }
   }

   public NameOrPseudonym(DirectoryString var1) {
      this.pseudonym = var1;
   }

   public NameOrPseudonym(DirectoryString var1, ASN1Sequence var2) {
      this.surname = var1;
      this.givenName = var2;
   }

   public static NameOrPseudonym getInstance(Object var0) {
      NameOrPseudonym var1;
      if(var0 != null && !(var0 instanceof NameOrPseudonym)) {
         if(var0 instanceof DERString) {
            DirectoryString var2 = DirectoryString.getInstance(var0);
            var1 = new NameOrPseudonym(var2);
         } else {
            if(!(var0 instanceof ASN1Sequence)) {
               StringBuilder var4 = (new StringBuilder()).append("illegal object in getInstance: ");
               String var5 = var0.getClass().getName();
               String var6 = var4.append(var5).toString();
               throw new IllegalArgumentException(var6);
            }

            ASN1Sequence var3 = (ASN1Sequence)var0;
            var1 = new NameOrPseudonym(var3);
         }
      } else {
         var1 = (NameOrPseudonym)var0;
      }

      return var1;
   }

   public DirectoryString[] getGivenName() {
      DirectoryString[] var1 = new DirectoryString[this.givenName.size()];
      int var2 = 0;

      int var4;
      for(Enumeration var3 = this.givenName.getObjects(); var3.hasMoreElements(); var2 = var4) {
         var4 = var2 + 1;
         DirectoryString var5 = DirectoryString.getInstance(var3.nextElement());
         var1[var2] = var5;
      }

      return var1;
   }

   public DirectoryString getPseudonym() {
      return this.pseudonym;
   }

   public DirectoryString getSurname() {
      return this.surname;
   }

   public DERObject toASN1Object() {
      Object var1;
      if(this.pseudonym != null) {
         var1 = this.pseudonym.toASN1Object();
      } else {
         ASN1EncodableVector var2 = new ASN1EncodableVector();
         DirectoryString var3 = this.surname;
         var2.add(var3);
         ASN1Sequence var4 = this.givenName;
         var2.add(var4);
         var1 = new DERSequence(var2);
      }

      return (DERObject)var1;
   }
}
