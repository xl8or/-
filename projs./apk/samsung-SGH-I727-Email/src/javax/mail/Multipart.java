package javax.mail;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.MultipartDataSource;
import javax.mail.Part;

public abstract class Multipart {

   protected String contentType = "multipart/mixed";
   protected Part parent = null;
   protected Vector parts;


   protected Multipart() {}

   public void addBodyPart(BodyPart param1) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public void addBodyPart(BodyPart param1, int param2) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public BodyPart getBodyPart(int var1) throws MessagingException {
      if(this.parts == null) {
         throw new IndexOutOfBoundsException();
      } else {
         return (BodyPart)this.parts.get(var1);
      }
   }

   public String getContentType() {
      return this.contentType;
   }

   public int getCount() throws MessagingException {
      int var1;
      if(this.parts == null) {
         var1 = 0;
      } else {
         var1 = this.parts.size();
      }

      return var1;
   }

   public Part getParent() {
      return this.parent;
   }

   public void removeBodyPart(int var1) throws MessagingException {
      if(this.parts == null) {
         throw new IndexOutOfBoundsException("No such BodyPart");
      } else {
         Vector var2 = this.parts;
         synchronized(var2) {
            BodyPart var3 = (BodyPart)this.parts.get(var1);
            this.parts.remove(var1);
            var3.setParent((Multipart)null);
         }
      }
   }

   public boolean removeBodyPart(BodyPart var1) throws MessagingException {
      if(this.parts == null) {
         throw new MessagingException("No such BodyPart");
      } else {
         Vector var2 = this.parts;
         synchronized(var2) {
            boolean var3 = this.parts.remove(var1);
            if(var3) {
               var1.setParent((Multipart)null);
            }

            return var3;
         }
      }
   }

   protected void setMultipartDataSource(MultipartDataSource var1) throws MessagingException {
      String var2 = var1.getContentType();
      this.contentType = var2;
      int var3 = var1.getCount();

      for(int var4 = 0; var4 < var3; ++var4) {
         BodyPart var5 = var1.getBodyPart(var4);
         this.addBodyPart(var5);
      }

   }

   public void setParent(Part var1) {
      this.parent = var1;
   }

   public abstract void writeTo(OutputStream var1) throws IOException, MessagingException;
}
