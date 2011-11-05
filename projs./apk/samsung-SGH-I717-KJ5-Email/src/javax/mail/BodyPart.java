package javax.mail;

import javax.mail.Multipart;
import javax.mail.Part;

public abstract class BodyPart implements Part {

   protected Multipart parent = null;


   public BodyPart() {}

   public Multipart getParent() {
      return this.parent;
   }

   void setParent(Multipart var1) {
      this.parent = var1;
   }
}
