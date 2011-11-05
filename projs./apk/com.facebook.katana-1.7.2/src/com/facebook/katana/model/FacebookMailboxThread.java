package com.facebook.katana.model;

import android.content.ContentValues;
import com.facebook.katana.model.FacebookProfile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class FacebookMailboxThread {

   public static final long INVALID_OBJECT_ID;
   private final long mLastUpdate;
   private final int mMsgCount;
   private final long mObjectId;
   private final long mOtherPartyUserId;
   private final Set<Long> mParticipants;
   private final String mSnippet;
   private final String mSubject;
   private final long mThreadId;
   private int mUnreadCount;


   public FacebookMailboxThread(long param1, String param3, String param4, long param5, int param7, int param8, long param9, long param11, List<Long> param13) {
      // $FF: Couldn't be decompiled
   }

   public FacebookMailboxThread(JsonParser var1) throws JsonParseException, IOException {
      long var2 = 65535L;
      String var4 = null;
      String var5 = null;
      long var6 = 65535L;
      int var8 = 0;
      int var9 = 0;
      long var10 = 65535L;
      long var12 = 0L;
      LinkedHashSet var14 = new LinkedHashSet();
      this.mParticipants = var14;
      JsonToken var15 = var1.nextToken();

      while(true) {
         JsonToken var16 = JsonToken.END_OBJECT;
         if(var15 == var16) {
            if(0L != var12) {
               Set var75 = this.mParticipants;
               Long var76 = Long.valueOf(var12);
               var75.add(var76);
            }

            this.mThreadId = var2;
            this.mSubject = var4;
            this.mSnippet = var5;
            this.mOtherPartyUserId = var6;
            this.mMsgCount = var8;
            this.mUnreadCount = var9;
            this.mLastUpdate = var10;
            this.mObjectId = var12;
            return;
         }

         JsonToken var19 = JsonToken.VALUE_STRING;
         String var22;
         if(var15 == var19) {
            var22 = var1.getCurrentName();
            String var24 = "thread_id";
            if(var22.equals(var24)) {
               var2 = Long.parseLong(var1.getText());
            } else {
               String var26 = "subject";
               if(var22.equals(var26)) {
                  var4 = var1.getText();
               } else {
                  String var28 = "snippet";
                  if(var22.equals(var28)) {
                     var5 = var1.getText();
                  } else {
                     String var30 = "snippet_author";
                     if(var22.equals(var30)) {
                        var6 = Long.parseLong(var1.getText());
                     }
                  }
               }
            }
         } else {
            JsonToken var31 = JsonToken.VALUE_NUMBER_INT;
            if(var15 == var31) {
               var22 = var1.getCurrentName();
               String var35 = "thread_id";
               if(var22.equals(var35)) {
                  var2 = var1.getLongValue();
               } else {
                  String var37 = "message_count";
                  if(var22.equals(var37)) {
                     var8 = var1.getIntValue();
                  } else {
                     String var39 = "unread";
                     if(var22.equals(var39)) {
                        var9 = var1.getIntValue();
                     } else {
                        String var41 = "updated_time";
                        if(var22.equals(var41)) {
                           var10 = var1.getLongValue();
                        } else {
                           String var43 = "snippet_author";
                           if(var22.equals(var43)) {
                              var6 = var1.getLongValue();
                           } else {
                              String var45 = "object_id";
                              if(var22.equals(var45)) {
                                 var12 = var1.getLongValue();
                              }
                           }
                        }
                     }
                  }
               }
            } else {
               JsonToken var46 = JsonToken.START_OBJECT;
               if(var15 == var46) {
                  while(true) {
                     JsonToken var49 = var1.nextToken();
                     JsonToken var50 = JsonToken.END_OBJECT;
                     if(var49 != var50) {
                        continue;
                     }
                  }
               } else {
                  JsonToken var53 = JsonToken.START_ARRAY;
                  if(var15 == var53) {
                     if(false) {
                        Object var56 = null;
                        String var57 = "recipients";
                        if(((String)var56).equals(var57)) {
                           var15 = var1.nextToken();

                           while(true) {
                              JsonToken var58 = JsonToken.END_ARRAY;
                              if(var15 == var58) {
                                 break;
                              }

                              JsonToken var61 = JsonToken.VALUE_NUMBER_INT;
                              if(var15 == var61) {
                                 Set var64 = this.mParticipants;
                                 Long var65 = Long.valueOf(var1.getLongValue());
                                 var64.add(var65);
                              }

                              var15 = var1.nextToken();
                           }
                        } else {
                           while(true) {
                              JsonToken var67 = var1.nextToken();
                              JsonToken var68 = JsonToken.END_ARRAY;
                              if(var67 != var68) {
                                 continue;
                              }
                           }
                        }
                     }
                  } else {
                     JsonToken var71 = JsonToken.FIELD_NAME;
                     if(var15 == var71) {
                        String var74 = var1.getText();
                     }
                  }
               }
            }
         }

         var15 = var1.nextToken();
      }
   }

   public ContentValues getContentValues(int var1, long var2, Map<Long, FacebookProfile> var4, String var5) {
      ContentValues var6 = new ContentValues();
      Integer var7 = Integer.valueOf(var1);
      var6.put("folder", var7);
      Long var8 = Long.valueOf(this.getThreadId());
      var6.put("tid", var8);
      String var9 = this.getSubject();
      var6.put("subject", var9);
      String var10 = this.getSnippet();
      var6.put("snippet", var10);
      Long var11 = Long.valueOf(this.getOtherPartyUserId(var2));
      var6.put("other_party", var11);
      Integer var12 = Integer.valueOf(this.getMsgCount());
      var6.put("msg_count", var12);
      Integer var13 = Integer.valueOf(this.getUnreadCount());
      var6.put("unread_count", var13);
      Long var14 = Long.valueOf(this.getLastUpdate());
      var6.put("last_update", var14);
      Long var15 = Long.valueOf(this.getObjectId());
      var6.put("object_id", var15);
      Long var16 = Long.valueOf(var2);
      String var17 = this.getParticipantsString(var4, var5, var16);
      var6.put("participants", var17);
      return var6;
   }

   public long getLastUpdate() {
      return this.mLastUpdate;
   }

   public int getMsgCount() {
      return this.mMsgCount;
   }

   public long getObjectId() {
      return this.mObjectId;
   }

   public long getOtherPartyUserId() {
      return this.mOtherPartyUserId;
   }

   public long getOtherPartyUserId(long var1) {
      long var3 = 65535L;
      if(this.mObjectId != 0L) {
         var3 = this.mObjectId;
      } else if(this.mParticipants.size() > 0) {
         Iterator var5 = this.mParticipants.iterator();

         while(var5.hasNext()) {
            long var6 = ((Long)var5.next()).longValue();
            if(var6 != var1) {
               var3 = var6;
            }
         }
      }

      return var3;
   }

   public List<Long> getParticipants() {
      Set var1 = this.mParticipants;
      return new ArrayList(var1);
   }

   public String getParticipantsString(Map<Long, FacebookProfile> var1, String var2, Long var3) {
      String var6;
      if(this.mObjectId != 0L) {
         Long var4 = Long.valueOf(this.mObjectId);
         FacebookProfile var5 = (FacebookProfile)var1.get(var4);
         if(var5 != null) {
            var6 = var5.mDisplayName;
         } else {
            var6 = "";
         }
      } else if(this.mParticipants.size() > 0) {
         StringBuffer var7 = new StringBuffer();
         Iterator var8 = this.mParticipants.iterator();

         while(var8.hasNext()) {
            Long var9 = (Long)var8.next();
            if(!var9.equals(var3)) {
               if(var7.length() > 0) {
                  var7.append(var2);
               }

               FacebookProfile var11 = (FacebookProfile)var1.get(var9);
               if(var11 != null) {
                  String var12 = var11.mDisplayName;
                  if(var12 != null) {
                     var7.append(var12);
                  }
               }
            }
         }

         var6 = var7.toString();
      } else {
         var6 = "";
      }

      return var6;
   }

   public String getSnippet() {
      return this.mSnippet;
   }

   public String getSubject() {
      return this.mSubject;
   }

   public long getThreadId() {
      return this.mThreadId;
   }

   public int getUnreadCount() {
      return this.mUnreadCount;
   }

   public void setUnreadCount(int var1) {
      this.mUnreadCount = var1;
   }
}
