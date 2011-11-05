package com.seven.Z7.provider;

import android.content.ContentUris;
import android.net.Uri;
import android.net.Uri.Builder;
import android.provider.BaseColumns;

public class Im {

   public static final String AUTHORITY = "com.seven.provider.im";


   private Im() {}

   public static final class ContactList implements BaseColumns, Im.ContactListColumns {

      public static final Uri ACCOUNT_CONTENT_URI = Uri.parse("content://com.seven.provider.im/contactLists/account");
      public static final String ACCOUNT_NAME = "account_name";
      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/im-contactLists";
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/im-contactLists";
      public static final Uri CONTENT_URI = Uri.parse("content://com.seven.provider.im/contactLists");
      public static final String DEFAULT_SORT_ORDER = "name COLLATE UNICODE ASC";


      private ContactList() {}
   }

   public interface PresenceColumns extends Im.CommonPresenceColumns {

      String ACCOUNTID = "account_id";
      String CLIENT_TYPE = "client_type";
      int CLIENT_TYPE_ANDROID = 2;
      int CLIENT_TYPE_DEFAULT = 0;
      int CLIENT_TYPE_MOBILE = 1;
      String CONTACT = "contact";
      String JID_RESOURCE = "jid_resource";
      String PRE_FROM_SUBSCRIPTION_STATUS = "prefromSubscriptionStatus";
      String RESOURCE_MAP = "resource_map";

   }

   public interface InvitationColumns {

      String ACCOUNT = "accountId";
      String GROUP_NAME = "groupName";
      String INVITE_ID = "inviteId";
      String NOTE = "note";
      String SENDER = "sender";
      String STATUS = "status";
      int STATUS_ACCEPTED = 1;
      int STATUS_PENDING = 0;
      int STATUS_REJECTED = 2;

   }

   public interface MessagesColumns extends Im.BaseMessageColumns {

      String ACCOUNT = "account";

   }

   public interface MessageType {

      int CONVERT_TO_GROUPCHAT = 6;
      int INCOMING = 1;
      int OTR_IS_TURNED_OFF = 9;
      int OTR_IS_TURNED_ON = 10;
      int OTR_TURNED_ON_BY_BUDDY = 12;
      int OTR_TURNED_ON_BY_USER = 11;
      int OUTGOING = 0;
      int POSTPONED = 8;
      int PRESENCE_AVAILABLE = 2;
      int PRESENCE_AWAY = 3;
      int PRESENCE_DND = 4;
      int PRESENCE_UNAVAILABLE = 5;
      int STATUS = 7;

   }

   public static final class Invitation implements Im.InvitationColumns, BaseColumns {

      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/im-invitations";
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/im-invitations";
      public static final Uri CONTENT_URI = Uri.parse("content://com.seven.provider.im/invitations");


      private Invitation() {}
   }

   public interface ContactListColumns {

      String ACCOUNT = "account";
      String NAME = "name";

   }

   public static final class Chats implements BaseColumns, Im.ChatsColumns {

      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/im-chats";
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/im-chats";
      public static final Uri CONTENT_URI = Uri.parse("content://com.seven.provider.im/chats");
      public static final Uri CONTENT_URI_BY_ACCOUNT = Uri.parse("content://com.seven.provider.im/chats/account");
      public static final Uri CONTENT_URI_BY_ID = Uri.parse("content://com.seven.provider.im/chats/id");
      public static final String DEFAULT_SORT_ORDER = "last_message_date ASC";


      private Chats() {}
   }

   public static enum ImProviders {

      // $FF: synthetic field
      private static final Im.ImProviders[] $VALUES;
      AIM("AIM", 3, 0, "AIM", "aim", "com.android.im.category.AIM"),
      GTALK("GTALK", 1, 5, "gmail", "gtalk", "com.android.im.category.GTALK"),
      ICQ("ICQ", 5, 6, "ICQ", "icq", "com.android.im.category.ICQ"),
      JABBER("JABBER", 4, 7, "JABBER", "jabber", "com.android.im.category.JABBER"),
      MSN("MSN", 2, 1, "MSN", "msn", "com.android.im.category.MSN"),
      QQ("QQ", 6, 4, "QQ", "qq", "com.android.im.category.QQ"),
      XMPP("XMPP", 7, -1, "XMPP", "xmpp", "com.android.im.category.XMPP"),
      YAHOO("YAHOO", 0, 2, "Yahoo", "yahoo", "com.android.im.category.YAHOO");
      public String category;
      public String host;
      public int id;
      public String name;


      static {
         Im.ImProviders[] var0 = new Im.ImProviders[8];
         Im.ImProviders var1 = YAHOO;
         var0[0] = var1;
         Im.ImProviders var2 = GTALK;
         var0[1] = var2;
         Im.ImProviders var3 = MSN;
         var0[2] = var3;
         Im.ImProviders var4 = AIM;
         var0[3] = var4;
         Im.ImProviders var5 = JABBER;
         var0[4] = var5;
         Im.ImProviders var6 = ICQ;
         var0[5] = var6;
         Im.ImProviders var7 = QQ;
         var0[6] = var7;
         Im.ImProviders var8 = XMPP;
         var0[7] = var8;
         $VALUES = var0;
      }

      private ImProviders(String var1, int var2, int var3, String var4, String var5, String var6) {
         this.id = var3;
         this.name = var4;
         this.host = var5;
         this.category = var6;
      }

      public static Im.ImProviders get(Uri var0) {
         Im.ImProviders var1;
         if(var0.getScheme().equalsIgnoreCase("imto")) {
            var1 = getByProtocol(var0.getHost());
         } else {
            var1 = null;
         }

         return var1;
      }

      public static Im.ImProviders get(String var0) {
         Im.ImProviders[] var1 = values();
         int var2 = var1.length;
         int var3 = 0;

         Im.ImProviders var6;
         while(true) {
            if(var3 >= var2) {
               var6 = null;
               break;
            }

            Im.ImProviders var4 = var1[var3];
            String var5 = var4.name;
            if(var0.equalsIgnoreCase(var5)) {
               var6 = var4;
               break;
            }

            ++var3;
         }

         return var6;
      }

      public static Im.ImProviders getByProtocol(String var0) {
         Im.ImProviders[] var1 = values();
         int var2 = var1.length;
         int var3 = 0;

         Im.ImProviders var6;
         while(true) {
            if(var3 >= var2) {
               var6 = null;
               break;
            }

            Im.ImProviders var4 = var1[var3];
            String var5 = var4.host;
            if(var0.equalsIgnoreCase(var5)) {
               var6 = var4;
               break;
            }

            ++var3;
         }

         return var6;
      }

      public static Im.ImProviders getProviderNameForCategory(String var0) {
         Im.ImProviders[] var1 = values();
         int var2 = var1.length;
         int var3 = 0;

         Im.ImProviders var6;
         while(true) {
            if(var3 >= var2) {
               var6 = null;
               break;
            }

            Im.ImProviders var4 = var1[var3];
            String var5 = var4.category;
            if(var0.equalsIgnoreCase(var5)) {
               var6 = var4;
               break;
            }

            ++var3;
         }

         return var6;
      }
   }

   public interface CommonPresenceColumns {

      int AVAILABLE = 1;
      int AWAY = 2;
      int DO_NOT_DISTURB = 3;
      int IDLE = 1;
      int INVISIBLE = 6;
      int OFFLINE = 7;
      String PRESENCE_CUSTOM_STATUS = "status";
      String PRESENCE_STATUS = "mode";
      String PRIORITY = "priority";

   }

   public static final class Avatars implements BaseColumns, Im.AvatarsColumns {

      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/im-avatars";
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/im-avatars";
      public static final Uri CONTENT_URI = Uri.parse("content://com.seven.provider.im/avatars");
      public static final Uri CONTENT_URI_AVATARS_BY = Uri.parse("content://com.seven.provider.im/avatarsBy");
      public static final String DEFAULT_SORT_ORDER = "contact ASC";


      private Avatars() {}
   }

   public interface ContactsColumns {

      String ACCOUNT = "account";
      String CONTACTLIST = "contactList";
      String FROM_SUBSCRIPTION_STATUS = "fromSubscriptionStatus";
      String NICKNAME = "nickname";
      String REJECTED = "rejected";
      String TO_SUBSCRIPTION_STATUS = "toSubscriptionStatus";
      String USERNAME = "username";

   }

   public static final class Presence implements BaseColumns, Im.PresenceColumns {

      public static final Uri BULK_CONTENT_URI = Uri.parse("content://com.seven.provider.im/bulk_presence");
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/im-presence";
      public static final Uri CONTENT_URI = Uri.parse("content://com.seven.provider.im/presence");
      public static final Uri CONTENT_URI_BY_ACCOUNT = Uri.parse("content://com.seven.provider.im/presence/account");
      public static final String DEFAULT_SORT_ORDER = "mode DESC";
      public static final Uri SEED_PRESENCE_BY_ACCOUNT_CONTENT_URI = Uri.parse("content://com.seven.provider.im/seed_presence/account");


      public Presence() {}
   }

   public static final class Contacts implements BaseColumns, Im.ContactsColumns, Im.PresenceColumns, Im.ChatsColumns {

      public static final String ALPHABETICAL_SORT_ORDER = "nickname COLLATE UNICODE ASC";
      public static final String AVATAR_DATA = "avatars_data";
      public static final String AVATAR_HASH = "avatars_hash";
      public static final Uri BULK_CONTENT_URI = Uri.parse("content://com.seven.provider.im/bulk_contacts");
      public static final String CHATS_CONTACT = "chats_contact";
      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/im-contacts";
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/im-contacts";
      public static final Uri CONTENT_URI = Uri.parse("content://com.seven.provider.im/contacts");
      public static final Uri CONTENT_URI_BLOCKED_CONTACTS = Uri.parse("content://com.seven.provider.im/contacts/blocked");
      public static final Uri CONTENT_URI_CHAT_CONTACTS = Uri.parse("content://com.seven.provider.im/contacts/chatting");
      public static final Uri CONTENT_URI_CHAT_CONTACTS_BY = Uri.parse("content://com.seven.provider.im/contacts/chatting");
      public static final Uri CONTENT_URI_CONTACTS_BAREBONE = Uri.parse("content://com.seven.provider.im/contactsBarebone");
      public static final Uri CONTENT_URI_CONTACTS_BY = Uri.parse("content://com.seven.provider.im/contacts/account");
      public static final Uri CONTENT_URI_OFFLINE_CONTACTS_BY = Uri.parse("content://com.seven.provider.im/contacts/offline");
      public static final Uri CONTENT_URI_ONLINE_CONTACTS_BY = Uri.parse("content://com.seven.provider.im/contacts/online");
      public static final Uri CONTENT_URI_ONLINE_COUNT = Uri.parse("content://com.seven.provider.im/contacts/onlineCount");
      public static final Uri CONTENT_URI_WITH_PRESENCE = Uri.parse("content://com.seven.provider.im/contactsWithPresence");
      public static final String DEFAULT_SORT_ORDER = "last_message_date DESC, mode ASC, nickname COLLATE UNICODE ASC";
      public static final String ISP_SORT_ORDER = "account ASC, nickname COLLATE UNICODE ASC";
      public static final String PRESENCE_SORT_ORDER = "mode=0,mode=7,mode asc, nickname COLLATE UNICODE ASC";


      private Contacts() {}
   }

   public static final class Messages implements BaseColumns, Im.MessagesColumns {

      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/im-messages";
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/im-messages";
      public static final Uri CONTENT_URI = Uri.parse("content://com.seven.provider.im/messages");
      public static final Uri CONTENT_URI_MESSAGES_BY = Uri.parse("content://com.seven.provider.im/messagesBy");
      public static final String DEFAULT_SORT_ORDER = "date ASC";


      private Messages() {}

      public static final Uri getContentUriByContact(int var0, String var1) {
         Builder var2 = CONTENT_URI_MESSAGES_BY.buildUpon();
         long var3 = (long)var0;
         ContentUris.appendId(var2, var3);
         var2.appendPath(var1);
         return var2.build();
      }
   }

   public interface BaseMessageColumns {

      String BODY = "body";
      String CONTACT = "contact";
      String DATE = "date";
      String ERROR_CODE = "err_code";
      String ERROR_MESSAGE = "err_msg";
      String PACKET_ID = "packet_id";
      String TYPE = "type";

   }

   public interface BlockedListColumns {

      String ACCOUNT = "account";
      String NICKNAME = "nickname";
      String USERNAME = "username";

   }

   public interface ChatsColumns {

      String CONTACT_ID = "contact_id";
      String GROUP_CHAT = "groupchat";
      String JID_RESOURCE = "jid_resource";
      String LAST_MESSAGE_DATE = "last_message_date";
      String LAST_UNREAD_MESSAGE = "last_unread_message";
      String SHORTCUT = "shortcut";
      String UNSENT_COMPOSED_MESSAGE = "unsent_composed_message";

   }

   public interface AvatarsColumns {

      String ACCOUNT = "account_id";
      String CONTACT = "contact";
      String DATA = "data";
      String HASH = "hash";

   }

   public static final class BlockedList implements BaseColumns, Im.BlockedListColumns {

      public static final String ACCOUNT_NAME = "account_name";
      public static final String AVATAR_DATA = "avatars_data";
      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/im-blockedList";
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/im-blockedList";
      public static final Uri CONTENT_URI = Uri.parse("content://com.seven.provider.im/blockedList");
      public static final String DEFAULT_SORT_ORDER = "nickname ASC";


      private BlockedList() {}
   }
}
