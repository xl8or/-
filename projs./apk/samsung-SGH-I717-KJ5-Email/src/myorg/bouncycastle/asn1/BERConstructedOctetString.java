package myorg.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import myorg.bouncycastle.asn1.ASN1OutputStream;
import myorg.bouncycastle.asn1.BEROutputStream;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DEROutputStream;

public class BERConstructedOctetString extends DEROctetString {

   private static final int MAX_LENGTH = 1000;
   private Vector octs;


   public BERConstructedOctetString(Vector var1) {
      byte[] var2 = toBytes(var1);
      super(var2);
      this.octs = var1;
   }

   public BERConstructedOctetString(DEREncodable var1) {
      DERObject var2 = var1.getDERObject();
      super((DEREncodable)var2);
   }

   public BERConstructedOctetString(DERObject var1) {
      super((DEREncodable)var1);
   }

   public BERConstructedOctetString(byte[] var1) {
      super(var1);
   }

   private Vector generateOcts() {
      Vector var1 = new Vector();
      int var2 = 0;

      while(true) {
         int var3 = this.string.length;
         if(var2 >= var3) {
            return var1;
         }

         int var4 = var2 + 1000;
         int var5 = this.string.length;
         int var6;
         if(var4 > var5) {
            var6 = this.string.length;
         } else {
            var6 = var2 + 1000;
         }

         byte[] var7 = new byte[var6 - var2];
         byte[] var8 = this.string;
         int var9 = var7.length;
         System.arraycopy(var8, var2, var7, 0, var9);
         DEROctetString var10 = new DEROctetString(var7);
         var1.addElement(var10);
         var2 += 1000;
      }
   }

   private static byte[] toBytes(Vector var0) {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      int var2 = 0;

      while(true) {
         int var3 = var0.size();
         if(var2 == var3) {
            return var1.toByteArray();
         }

         try {
            byte[] var4 = ((DEROctetString)var0.elementAt(var2)).getOctets();
            var1.write(var4);
         } catch (ClassCastException var13) {
            StringBuilder var6 = new StringBuilder();
            String var7 = var0.elementAt(var2).getClass().getName();
            String var8 = var6.append(var7).append(" found in input should only contain DEROctetString").toString();
            throw new IllegalArgumentException(var8);
         } catch (IOException var14) {
            StringBuilder var10 = (new StringBuilder()).append("exception converting octets ");
            String var11 = var14.toString();
            String var12 = var10.append(var11).toString();
            throw new IllegalArgumentException(var12);
         }

         ++var2;
      }
   }

   public void encode(DEROutputStream var1) throws IOException {
      if(!(var1 instanceof ASN1OutputStream) && !(var1 instanceof BEROutputStream)) {
         super.encode(var1);
      } else {
         var1.write(36);
         var1.write(128);
         Enumeration var2 = this.getObjects();

         while(var2.hasMoreElements()) {
            Object var3 = var2.nextElement();
            var1.writeObject(var3);
         }

         var1.write(0);
         var1.write(0);
      }
   }

   public Enumeration getObjects() {
      Enumeration var1;
      if(this.octs == null) {
         var1 = this.generateOcts().elements();
      } else {
         var1 = this.octs.elements();
      }

      return var1;
   }

   public byte[] getOctets() {
      return this.string;
   }
}
