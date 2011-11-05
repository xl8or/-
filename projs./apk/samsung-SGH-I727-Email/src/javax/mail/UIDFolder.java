package javax.mail;

import javax.mail.FetchProfile;
import javax.mail.Message;
import javax.mail.MessagingException;

public interface UIDFolder {

   long LASTUID = 255L;


   Message getMessageByUID(long var1) throws MessagingException;

   Message[] getMessagesByUID(long var1, long var3) throws MessagingException;

   Message[] getMessagesByUID(long[] var1) throws MessagingException;

   long getUID(Message var1) throws MessagingException;

   long getUIDValidity() throws MessagingException;

   public static class FetchProfileItem extends FetchProfile.Item {

      public static final UIDFolder.FetchProfileItem UID = new UIDFolder.FetchProfileItem("UID");


      protected FetchProfileItem(String var1) {
         super(var1);
      }
   }
}
