package javax.mail;


public class Provider {

   private String className;
   private String protocol;
   private Provider.Type type;
   private String vendor;
   private String version;


   public Provider(Provider.Type var1, String var2, String var3, String var4, String var5) {
      this.type = var1;
      this.protocol = var2;
      this.className = var3;
      this.vendor = var4;
      this.version = var5;
   }

   public String getClassName() {
      return this.className;
   }

   public String getProtocol() {
      return this.protocol;
   }

   public Provider.Type getType() {
      return this.type;
   }

   public String getVendor() {
      return this.vendor;
   }

   public String getVersion() {
      return this.version;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      StringBuffer var2 = var1.append("javax.mail.Provider[");
      Provider.Type var3 = this.type;
      Provider.Type var4 = Provider.Type.STORE;
      if(var3 == var4) {
         StringBuffer var5 = var1.append("STORE,");
      } else {
         Provider.Type var18 = this.type;
         Provider.Type var19 = Provider.Type.TRANSPORT;
         if(var18 == var19) {
            StringBuffer var20 = var1.append("TRANSPORT,");
         }
      }

      String var6 = this.protocol;
      var1.append(var6);
      StringBuffer var8 = var1.append(',');
      String var9 = this.className;
      var1.append(var9);
      if(this.vendor != null) {
         StringBuffer var11 = var1.append(',');
         String var12 = this.vendor;
         var1.append(var12);
      }

      if(this.version != null) {
         StringBuffer var14 = var1.append(',');
         String var15 = this.version;
         var1.append(var15);
      }

      StringBuffer var17 = var1.append("]");
      return var1.toString();
   }

   public static class Type {

      public static final Provider.Type STORE = new Provider.Type("Store");
      public static final Provider.Type TRANSPORT = new Provider.Type("Transport");
      private String type;


      private Type(String var1) {
         this.type = var1;
      }
   }
}
