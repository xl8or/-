package org.jivesoftware.smack.packet;


public class PrivacyItem {

   private boolean allow;
   private boolean filterIQ = 0;
   private boolean filterMessage = 0;
   private boolean filterPresence_in = 0;
   private boolean filterPresence_out = 0;
   private int order;
   private PrivacyItem.PrivacyRule rule;


   public PrivacyItem(String var1, boolean var2, int var3) {
      PrivacyItem.PrivacyRule var4 = PrivacyItem.PrivacyRule.fromString(var1);
      this.setRule(var4);
      this.setAllow(var2);
      this.setOrder(var3);
   }

   private PrivacyItem.PrivacyRule getRule() {
      return this.rule;
   }

   private void setAllow(boolean var1) {
      this.allow = var1;
   }

   private void setOrder(int var1) {
      this.order = var1;
   }

   private void setRule(PrivacyItem.PrivacyRule var1) {
      this.rule = var1;
   }

   public int getOrder() {
      return this.order;
   }

   public PrivacyItem.Type getType() {
      PrivacyItem.Type var1;
      if(this.getRule() == null) {
         var1 = null;
      } else {
         var1 = this.getRule().getType();
      }

      return var1;
   }

   public String getValue() {
      String var1;
      if(this.getRule() == null) {
         var1 = null;
      } else {
         var1 = this.getRule().getValue();
      }

      return var1;
   }

   public boolean isAllow() {
      return this.allow;
   }

   public boolean isFilterEverything() {
      boolean var1;
      if(!this.isFilterIQ() && !this.isFilterMessage() && !this.isFilterPresence_in() && !this.isFilterPresence_out()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isFilterIQ() {
      return this.filterIQ;
   }

   public boolean isFilterMessage() {
      return this.filterMessage;
   }

   public boolean isFilterPresence_in() {
      return this.filterPresence_in;
   }

   public boolean isFilterPresence_out() {
      return this.filterPresence_out;
   }

   public void setFilterIQ(boolean var1) {
      this.filterIQ = var1;
   }

   public void setFilterMessage(boolean var1) {
      this.filterMessage = var1;
   }

   public void setFilterPresence_in(boolean var1) {
      this.filterPresence_in = var1;
   }

   public void setFilterPresence_out(boolean var1) {
      this.filterPresence_out = var1;
   }

   public void setValue(String var1) {
      if(this.getRule() != null || var1 != null) {
         this.getRule().setValue(var1);
      }
   }

   public String toXML() {
      StringBuilder var1 = new StringBuilder();
      StringBuilder var2 = var1.append("<item");
      if(this.isAllow()) {
         StringBuilder var3 = var1.append(" action=\"allow\"");
      } else {
         StringBuilder var14 = var1.append(" action=\"deny\"");
      }

      StringBuilder var4 = var1.append(" order=\"");
      int var5 = this.getOrder();
      StringBuilder var6 = var4.append(var5).append("\"");
      if(this.getType() != null) {
         StringBuilder var7 = var1.append(" type=\"");
         PrivacyItem.Type var8 = this.getType();
         StringBuilder var9 = var7.append(var8).append("\"");
      }

      if(this.getValue() != null) {
         StringBuilder var10 = var1.append(" value=\"");
         String var11 = this.getValue();
         StringBuilder var12 = var10.append(var11).append("\"");
      }

      if(this.isFilterEverything()) {
         StringBuilder var13 = var1.append("/>");
      } else {
         StringBuilder var15 = var1.append(">");
         if(this.isFilterIQ()) {
            StringBuilder var16 = var1.append("<iq/>");
         }

         if(this.isFilterMessage()) {
            StringBuilder var17 = var1.append("<message/>");
         }

         if(this.isFilterPresence_in()) {
            StringBuilder var18 = var1.append("<presence-in/>");
         }

         if(this.isFilterPresence_out()) {
            StringBuilder var19 = var1.append("<presence-out/>");
         }

         StringBuilder var20 = var1.append("</item>");
      }

      return var1.toString();
   }

   public static enum Type {

      // $FF: synthetic field
      private static final PrivacyItem.Type[] $VALUES;
      group("group", 0),
      jid("jid", 1),
      subscription("subscription", 2);


      static {
         PrivacyItem.Type[] var0 = new PrivacyItem.Type[3];
         PrivacyItem.Type var1 = group;
         var0[0] = var1;
         PrivacyItem.Type var2 = jid;
         var0[1] = var2;
         PrivacyItem.Type var3 = subscription;
         var0[2] = var3;
         $VALUES = var0;
      }

      private Type(String var1, int var2) {}
   }

   public static class PrivacyRule {

      public static final String SUBSCRIPTION_BOTH = "both";
      public static final String SUBSCRIPTION_FROM = "from";
      public static final String SUBSCRIPTION_NONE = "none";
      public static final String SUBSCRIPTION_TO = "to";
      private PrivacyItem.Type type;
      private String value;


      public PrivacyRule() {}

      protected static PrivacyItem.PrivacyRule fromString(String var0) {
         PrivacyItem.PrivacyRule var1;
         if(var0 == null) {
            var1 = null;
         } else {
            var1 = new PrivacyItem.PrivacyRule();
            PrivacyItem.Type var2 = PrivacyItem.Type.valueOf(var0.toLowerCase());
            var1.setType(var2);
         }

         return var1;
      }

      private void setSuscriptionValue(String var1) {
         if(var1 == null) {
            ;
         }

         String var2;
         if("both".equalsIgnoreCase(var1)) {
            var2 = "both";
         } else if("to".equalsIgnoreCase(var1)) {
            var2 = "to";
         } else if("from".equalsIgnoreCase(var1)) {
            var2 = "from";
         } else if("none".equalsIgnoreCase(var1)) {
            var2 = "none";
         } else {
            var2 = null;
         }

         this.value = var2;
      }

      private void setType(PrivacyItem.Type var1) {
         this.type = var1;
      }

      public PrivacyItem.Type getType() {
         return this.type;
      }

      public String getValue() {
         return this.value;
      }

      public boolean isSuscription() {
         PrivacyItem.Type var1 = this.getType();
         PrivacyItem.Type var2 = PrivacyItem.Type.subscription;
         boolean var3;
         if(var1 == var2) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }

      protected void setValue(String var1) {
         if(this.isSuscription()) {
            this.setSuscriptionValue(var1);
         } else {
            this.value = var1;
         }
      }
   }
}
