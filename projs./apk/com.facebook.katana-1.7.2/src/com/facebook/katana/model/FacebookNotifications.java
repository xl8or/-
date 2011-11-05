package com.facebook.katana.model;

import android.content.Context;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.provider.UserValuesManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class FacebookNotifications {

   private static final String EVENT_INVITE_KEY = "event_invite";
   private static final String FRIEND_REQUEST_KEY = "friend_request";
   private static final String MESSAGE_KEY = "message";
   private static final String POKE_KEY = "poke";
   private static long mMostRecentEventInviteId;
   private static long mMostRecentFriendRequestId;
   private static long mMostRecentMessageId;
   private static long mMostRecentPokeId;
   private final List<Long> mEventInvites;
   private final List<Long> mFriendRequests;
   private final int mUnreadMessages;
   private final int mUnreadPokes;


   public FacebookNotifications(JsonParser var1) throws FacebookApiException, JsonParseException, IOException {
      int var2 = -1;
      String var3 = null;
      long var4 = 65535L;
      int var6 = 0;
      long var7 = 65535L;
      int var9 = 0;
      ArrayList var10 = new ArrayList();
      this.mFriendRequests = var10;
      ArrayList var11 = new ArrayList();
      this.mEventInvites = var11;
      JsonToken var12 = var1.nextToken();

      while(true) {
         JsonToken var13 = JsonToken.END_OBJECT;
         if(var12 == var13) {
            if(var2 > 0) {
               FacebookApiException var84 = new FacebookApiException(var2, var3);
               throw var84;
            }

            long var88 = mMostRecentMessageId;
            if(var4 != var88) {
               mMostRecentMessageId = var4;
               this.mUnreadMessages = var6;
            } else {
               byte var100 = 0;
               this.mUnreadMessages = var100;
            }

            long var91 = mMostRecentPokeId;
            if(var7 != var91) {
               mMostRecentPokeId = var7;
               this.mUnreadPokes = var9;
            } else {
               byte var101 = 0;
               this.mUnreadPokes = var101;
            }

            boolean var94 = false;
            Iterator var95 = this.mFriendRequests.iterator();

            while(var95.hasNext()) {
               long var96 = ((Long)var95.next()).longValue();
               if(!var94) {
                  long var98 = mMostRecentFriendRequestId;
                  if(var96 == var98) {
                     var94 = true;
                     var95.remove();
                  }
               } else {
                  var95.remove();
               }
            }

            if(this.mFriendRequests.size() > 0) {
               mMostRecentFriendRequestId = ((Long)this.mFriendRequests.get(0)).longValue();
            }

            boolean var102 = false;
            Iterator var103 = this.mEventInvites.iterator();

            while(var103.hasNext()) {
               long var104 = ((Long)var103.next()).longValue();
               if(!var102) {
                  long var106 = mMostRecentEventInviteId;
                  if(var104 == var106) {
                     var102 = true;
                     var103.remove();
                  }
               } else {
                  var103.remove();
               }
            }

            if(this.mEventInvites.size() <= 0) {
               return;
            }

            mMostRecentEventInviteId = ((Long)this.mEventInvites.get(0)).longValue();
            return;
         }

         JsonToken var16 = JsonToken.START_OBJECT;
         if(var12 == var16) {
            if(false) {
               Object var19 = null;
               String var20 = "messages";
               if(((String)var19).equals(var20)) {
                  while(true) {
                     JsonToken var21 = JsonToken.END_OBJECT;
                     if(var12 == var21) {
                        break;
                     }

                     JsonToken var24 = JsonToken.VALUE_NUMBER_INT;
                     if(var12 == var24) {
                        String var27 = var1.getCurrentName();
                        String var29 = "most_recent";
                        if(var27.equals(var29)) {
                           var4 = var1.getLongValue();
                        } else {
                           String var31 = "unread";
                           if(var27.equals(var31)) {
                              var6 = var1.getIntValue();
                           }
                        }
                     }

                     var12 = var1.nextToken();
                  }
               } else {
                  Object var32 = null;
                  String var33 = "pokes";
                  if(((String)var32).equals(var33)) {
                     while(true) {
                        JsonToken var34 = JsonToken.END_OBJECT;
                        if(var12 == var34) {
                           break;
                        }

                        JsonToken var37 = JsonToken.VALUE_NUMBER_INT;
                        if(var12 == var37) {
                           String var40 = var1.getCurrentName();
                           String var42 = "most_recent";
                           if(var40.equals(var42)) {
                              var7 = var1.getLongValue();
                           } else {
                              String var44 = "unread";
                              if(var40.equals(var44)) {
                                 var9 = var1.getIntValue();
                              }
                           }
                        }

                        var12 = var1.nextToken();
                     }
                  } else {
                     var1.skipChildren();
                  }
               }
            }
         } else {
            JsonToken var45 = JsonToken.START_ARRAY;
            if(var12 == var45) {
               if(false) {
                  Object var48 = null;
                  String var49 = "friend_requests";
                  if(((String)var48).equals(var49)) {
                     while(true) {
                        JsonToken var50 = JsonToken.END_ARRAY;
                        if(var12 == var50) {
                           break;
                        }

                        JsonToken var53 = JsonToken.VALUE_NUMBER_INT;
                        if(var12 == var53) {
                           List var56 = this.mFriendRequests;
                           Long var57 = Long.valueOf(var1.getLongValue());
                           var56.add(var57);
                        }

                        var12 = var1.nextToken();
                     }
                  } else {
                     Object var59 = null;
                     String var60 = "event_invites";
                     if(((String)var59).equals(var60)) {
                        while(true) {
                           JsonToken var61 = JsonToken.END_ARRAY;
                           if(var12 == var61) {
                              break;
                           }

                           JsonToken var64 = JsonToken.VALUE_NUMBER_INT;
                           if(var12 == var64) {
                              List var67 = this.mEventInvites;
                              Long var68 = Long.valueOf(var1.getLongValue());
                              var67.add(var68);
                           }

                           var12 = var1.nextToken();
                        }
                     } else {
                        var1.skipChildren();
                     }
                  }
               }
            } else {
               JsonToken var70 = JsonToken.VALUE_NUMBER_INT;
               if(var12 == var70) {
                  String var73 = var1.getCurrentName();
                  String var74 = "error_code";
                  if(var73.equals(var74)) {
                     var2 = var1.getIntValue();
                  }
               } else {
                  JsonToken var75 = JsonToken.VALUE_STRING;
                  if(var12 == var75) {
                     String var78 = var1.getCurrentName();
                     String var79 = "error_msg";
                     if(var78.equals(var79)) {
                        var3 = var1.getText();
                     }
                  } else {
                     JsonToken var80 = JsonToken.FIELD_NAME;
                     if(var12 == var80) {
                        String var83 = var1.getText();
                     }
                  }
               }
            }
         }

         var12 = var1.nextToken();
      }
   }

   public static void load(Context var0) {
      mMostRecentMessageId = UserValuesManager.getLastSeenId(var0, "message");
      mMostRecentPokeId = UserValuesManager.getLastSeenId(var0, "poke");
      mMostRecentFriendRequestId = UserValuesManager.getLastSeenId(var0, "friend_request");
      mMostRecentEventInviteId = UserValuesManager.getLastSeenId(var0, "event_invite");
   }

   public static void save(Context var0) {
      Long var1 = Long.valueOf(mMostRecentMessageId);
      UserValuesManager.setLastSeenId(var0, "message", var1);
      Long var2 = Long.valueOf(mMostRecentPokeId);
      UserValuesManager.setLastSeenId(var0, "poke", var2);
      Long var3 = Long.valueOf(mMostRecentFriendRequestId);
      UserValuesManager.setLastSeenId(var0, "friend_request", var3);
      Long var4 = Long.valueOf(mMostRecentEventInviteId);
      UserValuesManager.setLastSeenId(var0, "event_invite", var4);
   }

   public List<Long> getEventInvites() {
      return this.mEventInvites;
   }

   public List<Long> getFriendRequests() {
      return this.mFriendRequests;
   }

   public int getUnreadMessages() {
      return this.mUnreadMessages;
   }

   public int getUnreadPokes() {
      return this.mUnreadPokes;
   }

   public boolean hasNewNotifications() {
      boolean var1;
      if(this.mUnreadMessages == 0 && this.mUnreadPokes == 0 && this.mFriendRequests.size() == 0 && this.mEventInvites.size() == 0) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }
}
