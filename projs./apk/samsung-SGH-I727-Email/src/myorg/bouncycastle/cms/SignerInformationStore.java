package myorg.bouncycastle.cms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import myorg.bouncycastle.cms.SignerId;
import myorg.bouncycastle.cms.SignerInformation;

public class SignerInformationStore {

   private Map table;


   public SignerInformationStore(Collection var1) {
      HashMap var2 = new HashMap();
      this.table = var2;
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         SignerInformation var4 = (SignerInformation)var3.next();
         SignerId var5 = var4.getSID();
         if(this.table.get(var5) == null) {
            this.table.put(var5, var4);
         } else {
            Object var7 = this.table.get(var5);
            if(var7 instanceof List) {
               boolean var8 = ((List)var7).add(var4);
            } else {
               ArrayList var9 = new ArrayList();
               var9.add(var7);
               var9.add(var4);
               this.table.put(var5, var9);
            }
         }
      }

   }

   public SignerInformation get(SignerId var1) {
      Object var2 = this.table.get(var1);
      SignerInformation var3;
      if(var2 instanceof List) {
         var3 = (SignerInformation)((List)var2).get(0);
      } else {
         var3 = (SignerInformation)var2;
      }

      return var3;
   }

   public Collection getSigners() {
      int var1 = this.table.size();
      ArrayList var2 = new ArrayList(var1);
      Iterator var3 = this.table.values().iterator();

      while(var3.hasNext()) {
         Object var4 = var3.next();
         if(var4 instanceof List) {
            List var5 = (List)var4;
            var2.addAll(var5);
         } else {
            var2.add(var4);
         }
      }

      return var2;
   }

   public Collection getSigners(SignerId var1) {
      Object var2 = this.table.get(var1);
      Object var4;
      if(var2 instanceof List) {
         List var3 = (List)var2;
         var4 = new ArrayList(var3);
      } else if(var2 != null) {
         var4 = Collections.singletonList(var2);
      } else {
         var4 = new ArrayList();
      }

      return (Collection)var4;
   }

   public int size() {
      Iterator var1 = this.table.values().iterator();
      int var2 = 0;

      while(var1.hasNext()) {
         Object var3 = var1.next();
         if(var3 instanceof List) {
            int var4 = ((List)var3).size();
            var2 += var4;
         } else {
            ++var2;
         }
      }

      return var2;
   }
}
