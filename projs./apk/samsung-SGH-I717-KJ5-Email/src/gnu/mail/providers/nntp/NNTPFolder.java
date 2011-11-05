package gnu.mail.providers.nntp;

import gnu.inet.nntp.Newsrc;
import gnu.mail.providers.nntp.NNTPMessage;
import gnu.mail.providers.nntp.NNTPStore;
import java.io.IOException;
import java.util.Map;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.IllegalWriteException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.MethodNotSupportedException;

public final class NNTPFolder extends Folder {

   Map articleCache;
   int count = -1;
   int first = -1;
   int last = -1;
   String name;
   boolean open;


   NNTPFolder(NNTPStore var1, String var2) {
      super(var1);
      this.name = var2;
   }

   public void appendMessages(Message[] var1) throws MessagingException {
      throw new IllegalWriteException();
   }

   public void close(boolean var1) throws MessagingException {
      if(!this.open) {
         throw new IllegalStateException();
      } else {
         this.articleCache = null;
         this.open = (boolean)0;
         this.notifyConnectionListeners(3);
      }
   }

   public boolean create(int var1) throws MessagingException {
      throw new MethodNotSupportedException();
   }

   public boolean delete(boolean var1) throws MessagingException {
      throw new MethodNotSupportedException();
   }

   public boolean exists() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public Message[] expunge() throws MessagingException {
      throw new IllegalWriteException();
   }

   public void fetch(Message[] param1, FetchProfile param2) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public Folder getFolder(String var1) throws MessagingException {
      throw new MethodNotSupportedException();
   }

   public String getFullName() {
      return this.name;
   }

   public Message getMessage(int param1) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public int getMessageCount() throws MessagingException {
      return this.count;
   }

   NNTPMessage getMessageImpl(int var1) throws IOException {
      String var2 = ((NNTPStore)this.store).connection.stat(var1).messageId;
      return new NNTPMessage(this, var1, var2);
   }

   public Message[] getMessages() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public int getMode() {
      return 1;
   }

   public String getName() {
      return this.name;
   }

   public Folder getParent() throws MessagingException {
      return ((NNTPStore)this.store).root;
   }

   public Flags getPermanentFlags() {
      Flags var1 = ((NNTPStore)this.store).permanentFlags;
      return new Flags(var1);
   }

   public char getSeparator() throws MessagingException {
      return '.';
   }

   public int getType() throws MessagingException {
      return 1;
   }

   public boolean hasNewMessages() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public boolean isOpen() {
      return this.open;
   }

   boolean isSeen(int var1) {
      Newsrc var2 = ((NNTPStore)this.store).newsrc;
      String var3 = this.name;
      return var2.isSeen(var3, var1);
   }

   public boolean isSubscribed() {
      Newsrc var1 = ((NNTPStore)this.store).newsrc;
      String var2 = this.name;
      return var1.isSubscribed(var2);
   }

   public Folder[] list(String var1) throws MessagingException {
      throw new MethodNotSupportedException();
   }

   public Folder[] listSubscribed(String var1) throws MessagingException {
      return this.list(var1);
   }

   public void open(int param1) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public boolean renameTo(Folder var1) throws MessagingException {
      throw new MethodNotSupportedException();
   }

   void setSeen(int var1, boolean var2) {
      Newsrc var3 = ((NNTPStore)this.store).newsrc;
      String var4 = this.name;
      var3.setSeen(var4, var1, var2);
   }

   public void setSubscribed(boolean var1) throws MessagingException {
      Newsrc var2 = ((NNTPStore)this.store).newsrc;
      String var3 = this.name;
      var2.setSubscribed(var3, var1);
   }
}
