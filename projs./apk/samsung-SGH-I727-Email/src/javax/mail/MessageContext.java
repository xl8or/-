package javax.mail;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Part;
import javax.mail.Session;

public class MessageContext {

   private Part part;


   public MessageContext(Part var1) {
      this.part = var1;
   }

   public Message getMessage() {
      Part var1 = this.part;

      Message var2;
      while(true) {
         if(var1 == null) {
            var2 = null;
            break;
         }

         if(var1 instanceof Message) {
            var2 = (Message)var1;
            break;
         }

         if(var1 instanceof BodyPart) {
            var1 = ((BodyPart)var1).getParent().getParent();
         } else {
            var1 = null;
         }
      }

      return var2;
   }

   public Part getPart() {
      return this.part;
   }

   public Session getSession() {
      Message var1 = this.getMessage();
      Session var2;
      if(var1 != null) {
         var2 = var1.session;
      } else {
         var2 = null;
      }

      return var2;
   }
}
