package myorg.bouncycastle.x509;

import java.util.ArrayList;
import java.util.Collection;
import myorg.bouncycastle.x509.X509StoreParameters;

public class X509CollectionStoreParameters implements X509StoreParameters {

   private Collection collection;


   public X509CollectionStoreParameters(Collection var1) {
      if(var1 == null) {
         throw new NullPointerException("collection cannot be null");
      } else {
         this.collection = var1;
      }
   }

   public Object clone() {
      Collection var1 = this.collection;
      return new X509CollectionStoreParameters(var1);
   }

   public Collection getCollection() {
      Collection var1 = this.collection;
      return new ArrayList(var1);
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      StringBuffer var2 = var1.append("X509CollectionStoreParameters: [\n");
      StringBuilder var3 = (new StringBuilder()).append("  collection: ");
      Collection var4 = this.collection;
      String var5 = var3.append(var4).append("\n").toString();
      var1.append(var5);
      StringBuffer var7 = var1.append("]");
      return var1.toString();
   }
}
