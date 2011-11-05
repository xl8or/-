package myorg.bouncycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1SequenceParser;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.BERSequence;
import myorg.bouncycastle.asn1.BERTaggedObject;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROutputStream;
import myorg.bouncycastle.asn1.DERSequence;

public abstract class ASN1Sequence extends ASN1Object {

   private Vector seq;


   public ASN1Sequence() {
      Vector var1 = new Vector();
      this.seq = var1;
   }

   public static ASN1Sequence getInstance(Object var0) {
      if(var0 != null && !(var0 instanceof ASN1Sequence)) {
         StringBuilder var1 = (new StringBuilder()).append("unknown object in getInstance: ");
         String var2 = var0.getClass().getName();
         String var3 = var1.append(var2).toString();
         throw new IllegalArgumentException(var3);
      } else {
         return (ASN1Sequence)var0;
      }
   }

   public static ASN1Sequence getInstance(ASN1TaggedObject var0, boolean var1) {
      Object var2;
      if(var1) {
         if(!var0.isExplicit()) {
            throw new IllegalArgumentException("object implicit - explicit expected.");
         }

         var2 = (ASN1Sequence)var0.getObject();
      } else if(var0.isExplicit()) {
         if(var0 instanceof BERTaggedObject) {
            DERObject var3 = var0.getObject();
            var2 = new BERSequence(var3);
         } else {
            DERObject var4 = var0.getObject();
            var2 = new DERSequence(var4);
         }
      } else {
         if(!(var0.getObject() instanceof ASN1Sequence)) {
            StringBuilder var5 = (new StringBuilder()).append("unknown object in getInstance: ");
            String var6 = var0.getClass().getName();
            String var7 = var5.append(var6).toString();
            throw new IllegalArgumentException(var7);
         }

         var2 = (ASN1Sequence)var0.getObject();
      }

      return (ASN1Sequence)var2;
   }

   protected void addObject(DEREncodable var1) {
      this.seq.addElement(var1);
   }

   boolean asn1Equals(DERObject var1) {
      boolean var2;
      if(!(var1 instanceof ASN1Sequence)) {
         var2 = false;
      } else {
         ASN1Sequence var3 = (ASN1Sequence)var1;
         int var4 = this.size();
         int var5 = var3.size();
         if(var4 != var5) {
            var2 = false;
         } else {
            Enumeration var6 = this.getObjects();
            Enumeration var7 = var3.getObjects();

            while(true) {
               if(var6.hasMoreElements()) {
                  DERObject var8 = ((DEREncodable)var6.nextElement()).getDERObject();
                  DERObject var9 = ((DEREncodable)var7.nextElement()).getDERObject();
                  if(var8 == var9 || var8 != null && var8.equals(var9)) {
                     continue;
                  }

                  var2 = false;
                  break;
               }

               var2 = true;
               break;
            }
         }
      }

      return var2;
   }

   abstract void encode(DEROutputStream var1) throws IOException;

   public DEREncodable getObjectAt(int var1) {
      return (DEREncodable)this.seq.elementAt(var1);
   }

   public Enumeration getObjects() {
      return this.seq.elements();
   }

   public int hashCode() {
      Enumeration var1 = this.getObjects();
      int var2 = this.size();

      while(var1.hasMoreElements()) {
         Object var3 = var1.nextElement();
         var2 *= 17;
         if(var3 != null) {
            int var4 = var3.hashCode();
            var2 ^= var4;
         }
      }

      return var2;
   }

   public ASN1SequenceParser parser() {
      return new ASN1Sequence.1(this);
   }

   public int size() {
      return this.seq.size();
   }

   public String toString() {
      return this.seq.toString();
   }

   class 1 implements ASN1SequenceParser {

      private int index;
      private final int max;
      // $FF: synthetic field
      final ASN1Sequence val$outer;


      1(ASN1Sequence var2) {
         this.val$outer = var2;
         int var3 = ASN1Sequence.this.size();
         this.max = var3;
      }

      public DERObject getDERObject() {
         return this.val$outer;
      }

      public DEREncodable readObject() throws IOException {
         int var1 = this.index;
         int var2 = this.max;
         Object var3;
         if(var1 == var2) {
            var3 = null;
         } else {
            ASN1Sequence var4 = ASN1Sequence.this;
            int var5 = this.index;
            int var6 = var5 + 1;
            this.index = var6;
            DEREncodable var7 = var4.getObjectAt(var5);
            if(var7 instanceof ASN1Sequence) {
               var3 = ((ASN1Sequence)var7).parser();
            } else if(var7 instanceof ASN1Set) {
               var3 = ((ASN1Set)var7).parser();
            } else {
               var3 = var7;
            }
         }

         return (DEREncodable)var3;
      }
   }
}
