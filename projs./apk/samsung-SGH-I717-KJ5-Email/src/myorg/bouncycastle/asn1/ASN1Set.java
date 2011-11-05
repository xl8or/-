package myorg.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1OutputStream;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1SetParser;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROutputStream;
import myorg.bouncycastle.asn1.DERSet;

public abstract class ASN1Set extends ASN1Object {

   protected Vector set;


   public ASN1Set() {
      Vector var1 = new Vector();
      this.set = var1;
   }

   private byte[] getEncoded(DEREncodable var1) {
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      ASN1OutputStream var3 = new ASN1OutputStream(var2);

      try {
         var3.writeObject(var1);
      } catch (IOException var5) {
         throw new IllegalArgumentException("cannot encode object added to SET");
      }

      return var2.toByteArray();
   }

   public static ASN1Set getInstance(Object var0) {
      if(var0 != null && !(var0 instanceof ASN1Set)) {
         StringBuilder var1 = (new StringBuilder()).append("unknown object in getInstance: ");
         String var2 = var0.getClass().getName();
         String var3 = var1.append(var2).toString();
         throw new IllegalArgumentException(var3);
      } else {
         return (ASN1Set)var0;
      }
   }

   public static ASN1Set getInstance(ASN1TaggedObject var0, boolean var1) {
      Object var2;
      if(var1) {
         if(!var0.isExplicit()) {
            throw new IllegalArgumentException("object implicit - explicit expected.");
         }

         var2 = (ASN1Set)var0.getObject();
      } else if(var0.isExplicit()) {
         DERObject var3 = var0.getObject();
         var2 = new DERSet(var3);
      } else if(var0.getObject() instanceof ASN1Set) {
         var2 = (ASN1Set)var0.getObject();
      } else {
         ASN1EncodableVector var4 = new ASN1EncodableVector();
         if(!(var0.getObject() instanceof ASN1Sequence)) {
            StringBuilder var8 = (new StringBuilder()).append("unknown object in getInstance: ");
            String var9 = var0.getClass().getName();
            String var10 = var8.append(var9).toString();
            throw new IllegalArgumentException(var10);
         }

         ASN1Sequence var5 = (ASN1Sequence)var0.getObject();
         if(var5 != null) {
            Enumeration var6 = var5.getObjects();

            while(var6.hasMoreElements()) {
               DEREncodable var7 = (DEREncodable)var6.nextElement();
               var4.add(var7);
            }
         }

         var2 = new DERSet(var4, (boolean)0);
      }

      return (ASN1Set)var2;
   }

   private boolean lessThanOrEqual(byte[] var1, byte[] var2) {
      int var3 = var1.length;
      int var4 = var2.length;
      int var5;
      int var7;
      int var8;
      boolean var9;
      if(var3 <= var4) {
         var5 = 0;

         while(true) {
            int var6 = var1.length;
            if(var5 == var6) {
               var9 = true;
               break;
            }

            var7 = var1[var5] & 255;
            var8 = var2[var5] & 255;
            if(var8 > var7) {
               var9 = true;
               break;
            }

            if(var7 > var8) {
               var9 = false;
               break;
            }

            ++var5;
         }
      } else {
         var5 = 0;

         while(true) {
            int var10 = var2.length;
            if(var5 == var10) {
               var9 = false;
               break;
            }

            var7 = var1[var5] & 255;
            var8 = var2[var5] & 255;
            if(var8 > var7) {
               var9 = true;
               break;
            }

            if(var7 > var8) {
               var9 = false;
               break;
            }

            ++var5;
         }
      }

      return var9;
   }

   protected void addObject(DEREncodable var1) {
      this.set.addElement(var1);
   }

   boolean asn1Equals(DERObject var1) {
      boolean var2;
      if(!(var1 instanceof ASN1Set)) {
         var2 = false;
      } else {
         ASN1Set var3 = (ASN1Set)var1;
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
      return (DEREncodable)this.set.elementAt(var1);
   }

   public Enumeration getObjects() {
      return this.set.elements();
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

   public ASN1SetParser parser() {
      return new ASN1Set.1(this);
   }

   public int size() {
      return this.set.size();
   }

   protected void sort() {
      if(this.set.size() > 1) {
         boolean var1 = true;

         int var4;
         for(int var2 = this.set.size() - 1; var1; var2 = var4) {
            int var3 = 0;
            var4 = 0;
            DEREncodable var5 = (DEREncodable)this.set.elementAt(0);
            byte[] var6 = this.getEncoded(var5);

            for(var1 = false; var3 != var2; ++var3) {
               Vector var7 = this.set;
               int var8 = var3 + 1;
               DEREncodable var9 = (DEREncodable)var7.elementAt(var8);
               byte[] var10 = this.getEncoded(var9);
               if(this.lessThanOrEqual(var6, var10)) {
                  var6 = var10;
               } else {
                  Object var11 = this.set.elementAt(var3);
                  Vector var12 = this.set;
                  Vector var13 = this.set;
                  int var14 = var3 + 1;
                  Object var15 = var13.elementAt(var14);
                  var12.setElementAt(var15, var3);
                  Vector var16 = this.set;
                  int var17 = var3 + 1;
                  var16.setElementAt(var11, var17);
                  var4 = var3;
               }
            }
         }

      }
   }

   public String toString() {
      return this.set.toString();
   }

   class 1 implements ASN1SetParser {

      private int index;
      private final int max;
      // $FF: synthetic field
      final ASN1Set val$outer;


      1(ASN1Set var2) {
         this.val$outer = var2;
         int var3 = ASN1Set.this.size();
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
            ASN1Set var4 = ASN1Set.this;
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
