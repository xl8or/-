package myorg.bouncycastle.jce.provider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.ASN1OutputStream;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;

class PKCS12BagAttributeCarrierImpl implements PKCS12BagAttributeCarrier {

   private Hashtable pkcs12Attributes;
   private Vector pkcs12Ordering;


   public PKCS12BagAttributeCarrierImpl() {
      Hashtable var1 = new Hashtable();
      Vector var2 = new Vector();
      this(var1, var2);
   }

   PKCS12BagAttributeCarrierImpl(Hashtable var1, Vector var2) {
      this.pkcs12Attributes = var1;
      this.pkcs12Ordering = var2;
   }

   Hashtable getAttributes() {
      return this.pkcs12Attributes;
   }

   public DEREncodable getBagAttribute(DERObjectIdentifier var1) {
      return (DEREncodable)this.pkcs12Attributes.get(var1);
   }

   public Enumeration getBagAttributeKeys() {
      return this.pkcs12Ordering.elements();
   }

   Vector getOrdering() {
      return this.pkcs12Ordering;
   }

   public void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      Object var2 = var1.readObject();
      if(var2 instanceof Hashtable) {
         Hashtable var3 = (Hashtable)var2;
         this.pkcs12Attributes = var3;
         Vector var4 = (Vector)var1.readObject();
         this.pkcs12Ordering = var4;
      } else {
         byte[] var5 = (byte[])((byte[])var2);
         ASN1InputStream var6 = new ASN1InputStream(var5);

         while(true) {
            DERObjectIdentifier var7 = (DERObjectIdentifier)var6.readObject();
            if(var7 == null) {
               return;
            }

            DERObject var8 = var6.readObject();
            this.setBagAttribute(var7, var8);
         }
      }
   }

   public void setBagAttribute(DERObjectIdentifier var1, DEREncodable var2) {
      if(this.pkcs12Attributes.containsKey(var1)) {
         this.pkcs12Attributes.put(var1, var2);
      } else {
         this.pkcs12Attributes.put(var1, var2);
         this.pkcs12Ordering.addElement(var1);
      }
   }

   int size() {
      return this.pkcs12Ordering.size();
   }

   public void writeObject(ObjectOutputStream var1) throws IOException {
      if(this.pkcs12Ordering.size() == 0) {
         Hashtable var2 = new Hashtable();
         var1.writeObject(var2);
         Vector var3 = new Vector();
         var1.writeObject(var3);
      } else {
         ByteArrayOutputStream var4 = new ByteArrayOutputStream();
         ASN1OutputStream var5 = new ASN1OutputStream(var4);
         Enumeration var6 = this.getBagAttributeKeys();

         while(var6.hasMoreElements()) {
            DERObjectIdentifier var7 = (DERObjectIdentifier)var6.nextElement();
            var5.writeObject(var7);
            Object var8 = this.pkcs12Attributes.get(var7);
            var5.writeObject(var8);
         }

         byte[] var9 = var4.toByteArray();
         var1.writeObject(var9);
      }
   }
}
