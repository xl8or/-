package myorg.bouncycastle.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import myorg.bouncycastle.util.Selector;
import myorg.bouncycastle.util.Store;

public class CollectionStore implements Store {

   private Collection _local;


   public CollectionStore(Collection var1) {
      ArrayList var2 = new ArrayList(var1);
      this._local = var2;
   }

   public Collection getMatches(Selector var1) {
      ArrayList var3;
      if(var1 == null) {
         Collection var2 = this._local;
         var3 = new ArrayList(var2);
      } else {
         ArrayList var4 = new ArrayList();
         Iterator var5 = this._local.iterator();

         while(var5.hasNext()) {
            Object var6 = var5.next();
            if(var1.match(var6)) {
               var4.add(var6);
            }
         }

         var3 = var4;
      }

      return var3;
   }
}
