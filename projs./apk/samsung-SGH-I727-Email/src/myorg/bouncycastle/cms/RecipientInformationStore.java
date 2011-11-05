package myorg.bouncycastle.cms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import myorg.bouncycastle.cms.RecipientId;
import myorg.bouncycastle.cms.RecipientInformation;

public class RecipientInformationStore {

   private final List all;
   private final Map table;


   public RecipientInformationStore(Collection var1) {
      HashMap var2 = new HashMap();
      this.table = var2;

      RecipientInformation var4;
      ArrayList var6;
      for(Iterator var3 = var1.iterator(); var3.hasNext(); var6.add(var4)) {
         var4 = (RecipientInformation)var3.next();
         RecipientId var5 = var4.getRID();
         var6 = (ArrayList)this.table.get(var5);
         if(var6 == null) {
            var6 = new ArrayList(1);
            this.table.put(var5, var6);
         }
      }

      ArrayList var9 = new ArrayList(var1);
      this.all = var9;
   }

   public RecipientInformation get(RecipientId var1) {
      ArrayList var2 = (ArrayList)this.table.get(var1);
      RecipientInformation var3;
      if(var2 == null) {
         var3 = null;
      } else {
         var3 = (RecipientInformation)var2.get(0);
      }

      return var3;
   }

   public Collection getRecipients() {
      List var1 = this.all;
      return new ArrayList(var1);
   }

   public Collection getRecipients(RecipientId var1) {
      ArrayList var2 = (ArrayList)this.table.get(var1);
      ArrayList var3;
      if(var2 == null) {
         var3 = new ArrayList();
      } else {
         var3 = new ArrayList(var2);
      }

      return var3;
   }

   public int size() {
      return this.all.size();
   }
}
