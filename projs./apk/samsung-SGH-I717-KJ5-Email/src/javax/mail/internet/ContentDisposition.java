package javax.mail.internet;

import javax.mail.internet.HeaderTokenizer;
import javax.mail.internet.ParameterList;
import javax.mail.internet.ParseException;

public class ContentDisposition {

   private String disposition;
   private ParameterList list;


   public ContentDisposition() {}

   public ContentDisposition(String var1) throws ParseException {
      HeaderTokenizer var2 = new HeaderTokenizer(var1, "()<>@,;:\\\"\t []/?=");
      HeaderTokenizer.Token var3 = var2.next();
      if(var3.getType() != -1) {
         throw new ParseException();
      } else {
         String var4 = var3.getValue();
         this.disposition = var4;
         String var5 = var2.getRemainder();
         if(var5 != null) {
            ParameterList var6 = new ParameterList(var5);
            this.list = var6;
         }
      }
   }

   public ContentDisposition(String var1, ParameterList var2) {
      this.disposition = var1;
      this.list = var2;
   }

   public String getDisposition() {
      return this.disposition;
   }

   public String getParameter(String var1) {
      String var2;
      if(this.list != null) {
         var2 = this.list.get(var1);
      } else {
         var2 = null;
      }

      return var2;
   }

   public ParameterList getParameterList() {
      return this.list;
   }

   public void setDisposition(String var1) {
      this.disposition = var1;
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

   public String toString() {
      String var1;
      if(this.disposition == null) {
         var1 = null;
      } else if(this.list == null) {
         var1 = this.disposition;
      } else {
         StringBuffer var2 = new StringBuffer();
         String var3 = this.disposition;
         var2.append(var3);
         int var5 = var2.length() + 21;
         String var6 = this.list.toString(var5);
         var2.append(var6);
         var1 = var2.toString();
      }

      return var1;
   }
}
