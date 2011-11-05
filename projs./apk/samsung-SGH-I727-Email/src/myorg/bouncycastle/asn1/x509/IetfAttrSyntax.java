package myorg.bouncycastle.asn1.x509;

import java.util.Enumeration;
import java.util.Vector;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.DERUTF8String;
import myorg.bouncycastle.asn1.x509.GeneralNames;

public class IetfAttrSyntax extends ASN1Encodable {

   public static final int VALUE_OCTETS = 1;
   public static final int VALUE_OID = 2;
   public static final int VALUE_UTF8 = 3;
   GeneralNames policyAuthority = null;
   int valueChoice;
   Vector values;


   public IetfAttrSyntax(ASN1Sequence var1) {
      Vector var2 = new Vector();
      this.values = var2;
      this.valueChoice = -1;
      int var3 = 0;
      if(var1.getObjectAt(0) instanceof ASN1TaggedObject) {
         GeneralNames var4 = GeneralNames.getInstance((ASN1TaggedObject)var1.getObjectAt(0), (boolean)0);
         this.policyAuthority = var4;
         var3 = 0 + 1;
      } else if(var1.size() == 2) {
         GeneralNames var5 = GeneralNames.getInstance(var1.getObjectAt(0));
         this.policyAuthority = var5;
         var3 = 0 + 1;
      }

      if(!(var1.getObjectAt(var3) instanceof ASN1Sequence)) {
         throw new IllegalArgumentException("Non-IetfAttrSyntax encoding");
      } else {
         Enumeration var6 = ((ASN1Sequence)var1.getObjectAt(var3)).getObjects();

         while(var6.hasMoreElements()) {
            DERObject var7 = (DERObject)var6.nextElement();
            byte var8;
            if(var7 instanceof DERObjectIdentifier) {
               var8 = 2;
            } else if(var7 instanceof DERUTF8String) {
               var8 = 3;
            } else {
               if(!(var7 instanceof DEROctetString)) {
                  throw new IllegalArgumentException("Bad value type encoding IetfAttrSyntax");
               }

               var8 = 1;
            }

            if(this.valueChoice < 0) {
               this.valueChoice = var8;
            }

            int var9 = this.valueChoice;
            if(var8 != var9) {
               throw new IllegalArgumentException("Mix of value types in IetfAttrSyntax");
            }

            this.values.addElement(var7);
         }

      }
   }

   public GeneralNames getPolicyAuthority() {
      return this.policyAuthority;
   }

   public int getValueType() {
      return this.valueChoice;
   }

   public Object[] getValues() {
      Object var1;
      int var2;
      if(this.getValueType() == 1) {
         var1 = new ASN1OctetString[this.values.size()];
         var2 = 0;

         while(true) {
            int var3 = ((Object[])var1).length;
            if(var2 == var3) {
               break;
            }

            ASN1OctetString var4 = (ASN1OctetString)this.values.elementAt(var2);
            ((Object[])var1)[var2] = var4;
            ++var2;
         }
      } else if(this.getValueType() == 2) {
         var1 = new DERObjectIdentifier[this.values.size()];
         var2 = 0;

         while(true) {
            int var5 = ((Object[])var1).length;
            if(var2 == var5) {
               break;
            }

            DERObjectIdentifier var6 = (DERObjectIdentifier)this.values.elementAt(var2);
            ((Object[])var1)[var2] = var6;
            ++var2;
         }
      } else {
         var1 = new DERUTF8String[this.values.size()];
         var2 = 0;

         while(true) {
            int var7 = ((Object[])var1).length;
            if(var2 == var7) {
               break;
            }

            DERUTF8String var8 = (DERUTF8String)this.values.elementAt(var2);
            ((Object[])var1)[var2] = var8;
            ++var2;
         }
      }

      return (Object[])var1;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if(this.policyAuthority != null) {
         GeneralNames var2 = this.policyAuthority;
         DERTaggedObject var3 = new DERTaggedObject(0, var2);
         var1.add(var3);
      }

      ASN1EncodableVector var4 = new ASN1EncodableVector();
      Enumeration var5 = this.values.elements();

      while(var5.hasMoreElements()) {
         ASN1Encodable var6 = (ASN1Encodable)var5.nextElement();
         var4.add(var6);
      }

      DERSequence var7 = new DERSequence(var4);
      var1.add(var7);
      return new DERSequence(var1);
   }
}
