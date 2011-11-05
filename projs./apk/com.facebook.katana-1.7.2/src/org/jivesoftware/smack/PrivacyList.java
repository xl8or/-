package org.jivesoftware.smack;

import java.util.List;
import org.jivesoftware.smack.packet.PrivacyItem;

public class PrivacyList {

   private boolean isActiveList;
   private boolean isDefaultList;
   private List<PrivacyItem> items;
   private String listName;


   protected PrivacyList(boolean var1, boolean var2, String var3, List<PrivacyItem> var4) {
      this.isActiveList = var1;
      this.isDefaultList = var2;
      this.listName = var3;
      this.items = var4;
   }

   public List<PrivacyItem> getItems() {
      return this.items;
   }

   public boolean isActiveList() {
      return this.isActiveList;
   }

   public boolean isDefaultList() {
      return this.isDefaultList;
   }

   public String toString() {
      return this.listName;
   }
}
