package myorg.bouncycastle.asn1;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Vector;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1OctetStringParser;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.BERConstructedOctetString;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROutputStream;
import myorg.bouncycastle.util.Arrays;
import myorg.bouncycastle.util.encoders.Hex;

public abstract class ASN1OctetString extends ASN1Object implements ASN1OctetStringParser {

   byte[] string;


   public ASN1OctetString(DEREncodable var1) {
      try {
         byte[] var2 = var1.getDERObject().getEncoded("DER");
         this.string = var2;
      } catch (IOException var7) {
         StringBuilder var4 = (new StringBuilder()).append("Error processing object : ");
         String var5 = var7.toString();
         String var6 = var4.append(var5).toString();
         throw new IllegalArgumentException(var6);
      }
   }

   public ASN1OctetString(byte[] var1) {
      if(var1 == null) {
         throw new NullPointerException("string cannot be null");
      } else {
         this.string = var1;
      }
   }

   public static ASN1OctetString getInstance(Object var0) {
      Object var1;
      if(var0 != null && !(var0 instanceof ASN1OctetString)) {
         if(var0 instanceof ASN1TaggedObject) {
            var1 = getInstance(((ASN1TaggedObject)var0).getObject());
         } else {
            if(!(var0 instanceof ASN1Sequence)) {
               StringBuilder var5 = (new StringBuilder()).append("illegal object in getInstance: ");
               String var6 = var0.getClass().getName();
               String var7 = var5.append(var6).toString();
               throw new IllegalArgumentException(var7);
            }

            Vector var2 = new Vector();
            Enumeration var3 = ((ASN1Sequence)var0).getObjects();

            while(var3.hasMoreElements()) {
               Object var4 = var3.nextElement();
               var2.addElement(var4);
            }

            var1 = new BERConstructedOctetString(var2);
         }
      } else {
         var1 = (ASN1OctetString)var0;
      }

      return (ASN1OctetString)var1;
   }

   public static ASN1OctetString getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(var0.getObject());
   }

   boolean asn1Equals(DERObject var1) {
      byte var2;
      if(!(var1 instanceof ASN1OctetString)) {
         var2 = 0;
      } else {
         ASN1OctetString var3 = (ASN1OctetString)var1;
         byte[] var4 = this.string;
         byte[] var5 = var3.string;
         var2 = Arrays.areEqual(var4, var5);
      }

      return (boolean)var2;
   }

   abstract void encode(DEROutputStream var1) throws IOException;

   public InputStream getOctetStream() {
      byte[] var1 = this.string;
      return new ByteArrayInputStream(var1);
   }

   public byte[] getOctets() {
      return this.string;
   }

   public int hashCode() {
      return Arrays.hashCode(this.getOctets());
   }

   public ASN1OctetStringParser parser() {
      return this;
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder()).append("#");
      byte[] var2 = Hex.encode(this.string);
      String var3 = new String(var2);
      return var1.append(var3).toString();
   }
}
