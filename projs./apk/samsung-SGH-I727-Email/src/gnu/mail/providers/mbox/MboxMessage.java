package gnu.mail.providers.mbox;

import gnu.mail.providers.ReadOnlyMessage;
import gnu.mail.providers.mbox.MboxFolder;
import java.io.InputStream;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class MboxMessage extends ReadOnlyMessage {

   protected static final String STATUS = "Status";
   protected String fromLine;


   protected MboxMessage(MboxFolder var1, String var2, InputStream var3, int var4) throws MessagingException {
      super(var1, var3, var4);
      this.fromLine = var2;
      this.readStatusHeader();
   }

   protected MboxMessage(MboxFolder var1, MimeMessage var2, int var3) throws MessagingException {
      super(var2);
      this.folder = var1;
      this.msgnum = var3;
      this.readStatusHeader();
   }

   private void readStatusHeader() throws MessagingException {
      String[] var1 = this.getHeader("Status");
      if(var1 != null) {
         if(var1.length > 0) {
            Flags var2 = new Flags();
            this.flags = var2;
            if(var1[0].indexOf(82) >= 0) {
               Flags var3 = this.flags;
               Flags.Flag var4 = Flags.Flag.SEEN;
               var3.add(var4);
            }

            if(var1[0].indexOf(79) < 0) {
               Flags var5 = this.flags;
               Flags.Flag var6 = Flags.Flag.RECENT;
               var5.add(var6);
            }

            if(var1[0].indexOf(65) >= 0) {
               Flags var7 = this.flags;
               Flags.Flag var8 = Flags.Flag.ANSWERED;
               var7.add(var8);
            }

            if(var1[0].indexOf(68) >= 0) {
               Flags var9 = this.flags;
               Flags.Flag var10 = Flags.Flag.DELETED;
               var9.add(var10);
            }
         }
      }
   }

   public boolean equals(Object var1) {
      boolean var6;
      if(var1 instanceof MimeMessage) {
         MimeMessage var7 = (MimeMessage)var1;
         Folder var2 = var7.getFolder();
         Folder var3 = this.getFolder();
         if(var2 == var3) {
            int var4 = var7.getMessageNumber();
            int var5 = this.getMessageNumber();
            if(var4 == var5) {
               var6 = true;
               return var6;
            }
         }

         var6 = false;
      } else {
         var6 = false;
      }

      return var6;
   }

   protected void setExpunged(boolean var1) {
      super.setExpunged(var1);
   }

   public void setFlags(Flags param1, boolean param2) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   protected void updateHeaders() throws MessagingException {
      super.updateHeaders();
      String var1 = this.getHeader("Status", "\n");
      StringBuffer var2 = new StringBuffer();
      Flags var3 = this.flags;
      Flags.Flag var4 = Flags.Flag.SEEN;
      boolean var5 = var3.contains(var4);
      Flags var6 = this.flags;
      Flags.Flag var7 = Flags.Flag.RECENT;
      boolean var8 = var6.contains(var7);
      Flags var9 = this.flags;
      Flags.Flag var10 = Flags.Flag.ANSWERED;
      boolean var11 = var9.contains(var10);
      Flags var12 = this.flags;
      Flags.Flag var13 = Flags.Flag.DELETED;
      boolean var14 = var12.contains(var13);
      if(var5) {
         StringBuffer var15 = var2.append('R');
      }

      if(!var8) {
         StringBuffer var16 = var2.append('O');
      }

      if(var11) {
         StringBuffer var17 = var2.append('A');
      }

      if(var14) {
         StringBuffer var18 = var2.append('D');
      }

      String var19 = var2.toString();
      if(!var19.equals(var1)) {
         this.setHeader("Status", var19);
      }
   }
}
