package gnu.inet.imap;

import java.util.List;

public class MailboxStatus {

   public int firstUnreadMessage = -1;
   public List flags;
   public int messageCount = -1;
   public int newMessageCount = -1;
   public List permanentFlags;
   public boolean readWrite;
   public int uidNext = -1;
   public int uidValidity = -1;


   public MailboxStatus() {}
}
