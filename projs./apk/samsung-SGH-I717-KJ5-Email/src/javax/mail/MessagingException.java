package javax.mail;

import java.io.PrintStream;
import java.io.PrintWriter;

public class MessagingException extends Exception {

   private Exception nextException;


   public MessagingException() {
      this((String)null, (Exception)null);
   }

   public MessagingException(String var1) {
      this(var1, (Exception)null);
   }

   public MessagingException(String var1, Exception var2) {
      super(var1);
      this.nextException = var2;
   }

   public String getMessage() {
      String var1 = super.getMessage();
      if(this.nextException != null) {
         StringBuffer var2 = new StringBuffer();
         var2.append(var1);
         StringBuffer var4 = var2.append(";\n  nested exception is: \n\t");
         String var5 = this.nextException.toString();
         var2.append(var5);
         var1 = var2.toString();
      }

      return var1;
   }

   public Exception getNextException() {
      return this.nextException;
   }

   public void printStackTrace(PrintStream var1) {
      super.printStackTrace(var1);
      if(this.nextException != null) {
         var1.println("nested exception is:");
         this.nextException.printStackTrace(var1);
      }
   }

   public void printStackTrace(PrintWriter var1) {
      super.printStackTrace(var1);
      if(this.nextException != null) {
         var1.println("nested exception is:");
         this.nextException.printStackTrace(var1);
      }
   }

   public boolean setNextException(Exception var1) {
      synchronized(this){}
      Object var2 = this;

      boolean var3;
      while(true) {
         boolean var6 = false;

         label61: {
            try {
               var6 = true;
               if(var2 instanceof MessagingException && ((MessagingException)var2).nextException != null) {
                  var2 = ((MessagingException)var2).nextException;
                  continue;
               }

               if(var2 instanceof MessagingException) {
                  ((MessagingException)var2).nextException = var1;
                  var6 = false;
                  break label61;
               }

               var6 = false;
            } finally {
               if(var6) {
                  ;
               }
            }

            var3 = false;
            break;
         }

         var3 = true;
         break;
      }

      return var3;
   }
}
