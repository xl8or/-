package com.facebook.katana.model;

import com.facebook.katana.model.FacebookChatUser;
import org.jivesoftware.smack.packet.Message;

public class FacebookChatMessage {

   public final String mBody;
   public final FacebookChatMessage.Type mMessageType;
   public final long mRecipientUid;
   public final long mSenderUid;
   public final Long mTimestamp;


   public FacebookChatMessage(long var1, long var3, String var5, Long var6) {
      FacebookChatMessage.Type var7 = FacebookChatMessage.Type.NORMAL;
      this(var1, var3, var5, var6, var7);
   }

   public FacebookChatMessage(long var1, long var3, String var5, Long var6, FacebookChatMessage.Type var7) {
      this.mSenderUid = var1;
      this.mRecipientUid = var3;
      this.mBody = var5;
      this.mTimestamp = var6;
      this.mMessageType = var7;
   }

   public FacebookChatMessage(Message var1) {
      long var2 = FacebookChatUser.getUid(var1.getFrom());
      this.mSenderUid = var2;
      long var4 = FacebookChatUser.getUid(var1.getTo());
      this.mRecipientUid = var4;
      String var6 = var1.getBody();
      this.mBody = var6;
      Long var7 = Long.valueOf(System.currentTimeMillis());
      this.mTimestamp = var7;
      if(var1.getBody() == null) {
         if(var1.toXML().contains("composing")) {
            FacebookChatMessage.Type var8 = FacebookChatMessage.Type.COMPOSING;
            this.mMessageType = var8;
         } else {
            FacebookChatMessage.Type var9 = FacebookChatMessage.Type.ACTIVE;
            this.mMessageType = var9;
         }
      } else {
         FacebookChatMessage.Type var10 = FacebookChatMessage.Type.NORMAL;
         this.mMessageType = var10;
      }
   }

   public static enum Type {

      // $FF: synthetic field
      private static final FacebookChatMessage.Type[] $VALUES;
      ACTIVE("ACTIVE", 2),
      COMPOSING("COMPOSING", 1),
      NORMAL("NORMAL", 0);


      static {
         FacebookChatMessage.Type[] var0 = new FacebookChatMessage.Type[3];
         FacebookChatMessage.Type var1 = NORMAL;
         var0[0] = var1;
         FacebookChatMessage.Type var2 = COMPOSING;
         var0[1] = var2;
         FacebookChatMessage.Type var3 = ACTIVE;
         var0[2] = var3;
         $VALUES = var0;
      }

      private Type(String var1, int var2) {}
   }
}
