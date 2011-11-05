package myorg.bouncycastle.asn1;

import java.util.Vector;
import myorg.bouncycastle.asn1.DEREncodable;

public class DEREncodableVector {

   Vector v;


   public DEREncodableVector() {
      Vector var1 = new Vector();
      this.v = var1;
   }

   public void add(DEREncodable var1) {
      this.v.addElement(var1);
   }

   public DEREncodable get(int var1) {
      return (DEREncodable)this.v.elementAt(var1);
   }

   public int size() {
      return this.v.size();
   }
}
