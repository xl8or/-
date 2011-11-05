package javax.mail.internet;

import javax.mail.internet.HeaderTokenizer;
import javax.mail.internet.ParameterList;
import javax.mail.internet.ParseException;

public class ContentType {

   private ParameterList list;
   private String primaryType;
   private String subType;


   public ContentType() {}

   public ContentType(String var1) throws ParseException {
      HeaderTokenizer var2 = new HeaderTokenizer(var1, "()<>@,;:\\\"\t []/?=");
      HeaderTokenizer.Token var3 = var2.next();
      if(var3.getType() != -1) {
         String var4 = "expected primary type: " + var1;
         throw new ParseException(var4);
      } else {
         String var5 = var3.getValue();
         this.primaryType = var5;
         if(var2.next().getType() != 47) {
            String var6 = "expected \'/\': " + var1;
            throw new ParseException(var6);
         } else {
            HeaderTokenizer.Token var7 = var2.next();
            if(var7.getType() != -1) {
               String var8 = "expected subtype: " + var1;
               throw new ParseException(var8);
            } else {
               String var9 = var7.getValue();
               this.subType = var9;
               String var10 = var2.getRemainder();
               if(var10 != null) {
                  ParameterList var11 = new ParameterList(var10);
                  this.list = var11;
               }
            }
         }
      }
   }

   public ContentType(String var1, String var2, ParameterList var3) {
      this.primaryType = var1;
      this.subType = var2;
      this.list = var3;
   }

   public String getBaseType() {
      String var1;
      if(this.primaryType != null && this.subType != null) {
         StringBuffer var2 = new StringBuffer();
         String var3 = this.primaryType;
         var2.append(var3);
         StringBuffer var5 = var2.append('/');
         String var6 = this.subType;
         var2.append(var6);
         var1 = var2.toString();
      } else {
         var1 = null;
      }

      return var1;
   }

   public String getParameter(String var1) {
      String var2;
      if(this.list == null) {
         var2 = null;
      } else {
         var2 = this.list.get(var1);
      }

      return var2;
   }

   public ParameterList getParameterList() {
      return this.list;
   }

   public String getPrimaryType() {
      return this.primaryType;
   }

   public String getSubType() {
      return this.subType;
   }

   public boolean match(String var1) {
      boolean var3;
      boolean var4;
      try {
         ContentType var2 = new ContentType(var1);
         var3 = this.match(var2);
      } catch (ParseException var6) {
         var4 = false;
         return var4;
      }

      var4 = var3;
      return var4;
   }

   public boolean match(ContentType var1) {
      String var2 = this.primaryType;
      String var3 = var1.getPrimaryType();
      boolean var4;
      if(!var2.equalsIgnoreCase(var3)) {
         var4 = false;
      } else {
         String var5 = var1.getSubType();
         if(this.subType.charAt(0) != 42 && var5.charAt(0) != 42) {
            var4 = this.subType.equalsIgnoreCase(var5);
         } else {
            var4 = true;
         }
      }

      return var4;
   }

   public void setParameter(String var1, String var2) {
      if(this.list == null) {
         ParameterList var3 = new ParameterList();
         this.list = var3;
      }

      this.list.set(var1, var2);
   }

   public void setParameterList(ParameterList var1) {
      this.list = var1;
   }

   public void setPrimaryType(String var1) {
      this.primaryType = var1;
   }

   public void setSubType(String var1) {
      this.subType = var1;
   }

   public String toString() {
      String var1;
      if(this.primaryType != null && this.subType != null) {
         StringBuffer var2 = new StringBuffer();
         String var3 = this.primaryType;
         var2.append(var3);
         StringBuffer var5 = var2.append('/');
         String var6 = this.subType;
         var2.append(var6);
         if(this.list != null) {
            int var8 = var2.length() + 14;
            String var9 = this.list.toString(var8);
            var2.append(var9);
         }

         var1 = var2.toString();
      } else {
         var1 = null;
      }

      return var1;
   }
}
