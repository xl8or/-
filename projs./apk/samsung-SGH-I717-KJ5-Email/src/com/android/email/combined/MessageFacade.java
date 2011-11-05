package com.android.email.combined;

import android.content.Context;
import com.android.email.combined.EmailException;
import com.android.email.combined.MessageBehavior;
import com.android.email.provider.EmailContent;
import java.util.Hashtable;

public class MessageFacade {

   private Context mContext;


   public MessageFacade(Context var1) {
      this.mContext = var1;
   }

   public void addFolder(int var1, int var2, String var3) {
      this.getBehavior().addFolder(var1, var2, var3);
   }

   public void addListener(Context var1) {
      MessageBehavior var2 = this.getBehavior();
      MessageFacade.MessageListener var3 = (MessageFacade.MessageListener)var1;
      var2.addListener(var3);
   }

   public void addMessage(EmailContent.Message var1, EmailContent.Body var2, EmailContent.Attachment[] var3) {
      this.getBehavior().addMessage(var1, var2, var3);
   }

   public void createMailBox(long var1, Hashtable<String, Integer> var3) {
      this.getBehavior().listFolders(var1, var3, (boolean)0);
   }

   public MessageBehavior getBehavior() {
      return MessageBehavior.getInstance(this.mContext);
   }

   public void moveMessage(int var1, int var2, int var3) {
      this.getBehavior().moveMessage(var1, var2, var3);
   }

   public void removeFolder(int var1, int var2) {
      this.getBehavior().removeFolder(var1, var2);
   }

   public void removeListener(Context var1) {
      MessageBehavior var2 = this.getBehavior();
      MessageFacade.MessageListener var3 = (MessageFacade.MessageListener)var1;
      var2.removeListener(var3);
   }

   public void removeMessage(int var1, int var2) {
      this.getBehavior().removeMessage(var1, var2);
   }

   public void removeMessageForDate(int var1, long var2) {
      this.getBehavior().removeMessageForDate(var1, var2);
   }

   public void setMessageRead(int var1, boolean var2) {
      this.getBehavior().setMessageRead(var1, var2);
   }

   public void syncMessage(EmailContent.Message var1, EmailContent.Body var2, EmailContent.Attachment[] var3) {
      this.addMessage(var1, var2, var3);
   }

   public void updateFolder(int var1, int var2, String var3) {
      this.getBehavior().updateFolder(var1, var2, var3);
   }

   public interface MessageListener {

      void onFolderFailed(EmailException var1);

      void onFolderFinished(long var1);

      void onFolderStarted();

      void onMessageFailed(EmailException var1);

      void onMessageFinished(long var1);

      void onMessageStarted();
   }
}
