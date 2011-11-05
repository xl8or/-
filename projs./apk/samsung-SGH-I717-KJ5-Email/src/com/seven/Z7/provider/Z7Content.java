package com.seven.Z7.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public final class Z7Content {

   public static final String ACCOUNT_ID = "account_id";
   public static final String AUTHORITY = "com.seven.provider.email";
   public static final String BLOB = "bb";
   public static final String CONTENT_ID = "content_id";
   public static final String CREATED_DATE = "created";
   public static final String MODIFIED_DATE = "modified";


   private Z7Content() {}

   public static final class PendingTransactions implements Z7Content.PendingTransactionColumns {

      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.seven.sync";
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.seven.sync";
      public static final Uri CONTENT_URI = Uri.parse("content://com.seven.provider.email/pending");
      public static final String DEFAULT_SORT_ORDER = "account_id ASC";


      private PendingTransactions() {}
   }

   public static final class Emails implements Z7Content.EmailColumns {

      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.seven.email";
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.seven.email";
      public static final Uri CONTENT_URI = Uri.parse("content://com.seven.provider.email/emails");
      public static final Uri CONTENT_URI_UNIFIED_INBOX = Uri.parse("content://com.seven.provider.email/emails/unified");
      public static final String DATE_SORT_ORDER = "delivery_time DESC";
      public static final String DEFAULT_SORT_ORDER = "delivery_time DESC";
      public static final String SENDER_SORT_ORDER = "UPPER(_from) ASC";
      public static final String SUBJECT_SORT_ORDER = "UPPER(subject) ASC";


      private Emails() {}
   }

   public static final class Accounts implements Z7Content.AccountColumns {

      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.seven.account";
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.seven.account";
      public static final Uri CONTENT_URI = Uri.parse("content://com.seven.provider.email/accounts");
      public static final String DEFAULT_SORT_ORDER = "account_id ASC";


      private Accounts() {}
   }

   public interface ServiceInfoColumns extends Z7Content.Z7BaseColumns {

      String CONTENT_ID = "content_id";
      String DATASTORE_CHANGE_COUNT = "datastore_count";

   }

   public interface ContactFieldMappingColumns extends Z7Content.Z7BaseColumns {

      String CONTACT_ID = "contact_id";
      String MAPPINGS = "mappings";

   }

   public interface AccountColumns extends Z7Content.Z7BaseColumns {

      String ALLOW_PWD_SAVE = "allow_pwd_save";
      String DO_CERTS = "do_certs";
      String EMAIL = "email";
      String ENDPOINT = "endpoint";
      String ENTERPRISE = "enterprise";
      String IM_PRESENCE = "im_mode";
      String IM_STATUS = "im_status";
      String ISP_SERVER_ID = "isp_server_id";
      String IS_EXCLUSIVE = "is_exclusive";
      String IS_EXPIRED = "is_expired";
      String LAST_SYNC = "last_sync";
      String MODE = "mode";
      String NAME = "name";
      String NAME_ID = "name_id";
      String NEEDS_PWD_SAVE = "needs_pwd_save";
      String PASSWORD = "password";
      String PROVISION_NAME = "provision_name";
      String SCOPE = "scope";
      String SERVICE_STATES = "service_states";
      String STATUS = "status";
      String STATUS2 = "status2";
      String URL = "url";
      String USER_NAME = "user_name";

   }

   public static final class ServiceInfo implements Z7Content.ServiceInfoColumns {

      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.seven.serviceinfo";
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.seven.serviceinfo";
      public static final Uri CONTENT_URI = Uri.parse("content://com.seven.provider.email/serviceinfo");
      public static final String DEFAULT_SORT_ORDER = "_id ASC";


      private ServiceInfo() {}
   }

   public static final class Settings implements Z7Content.SettingColumns {

      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.seven.sync";
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.seven.sync";
      public static final Uri CONTENT_URI = Uri.parse("content://com.seven.provider.email/settings");
      public static final String DEFAULT_SORT_ORDER = "_id ASC";


      private Settings() {}
   }

   public interface SearchColumns extends Z7Content.Z7BaseColumns {

      String BLOB = "blob";
      String EMAIL = "email";
      String NAME = "name";
      String PHONE = "phone";
      String SOURCE = "source";
      String SOURCE_ID = "source_id";

   }

   public interface FolderColumns extends Z7Content.Z7BaseColumns {

      String CONTENT_ID = "content_id";
      String COUNT = "count";
      String KEPT_IN_SYNC = "kept_in_sync";
      String NAME = "name";
      String PARENT_FOLDER_ID = "folder_id";
      String SPECIAL_FOLDER_ID = "special_id";

   }

   public static final class ContactFieldMapping implements Z7Content.ContactFieldMappingColumns {

      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.seven.fieldmap";
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.seven.fieldmap";
      public static final Uri CONTENT_URI = Uri.parse("content://com.seven.provider.email/fieldmap");
      public static final String DEFAULT_SORT_ORDER = "contact_id ASC";


      private ContactFieldMapping() {}
   }

   public static final class Notifications implements Z7Content.NotificationColumns {

      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.seven.sync";
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.seven.sync";
      public static final Uri CONTENT_URI = Uri.parse("content://com.seven.provider.email/notification");
      public static final String DEFAULT_SORT_ORDER = "account_id ASC";


      private Notifications() {}
   }

   public static final class Search implements Z7Content.SearchColumns {

      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.seven.contact";
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.seven.contact";
      public static final Uri CONTENT_URI = Uri.parse("content://com.seven.provider.email/search");
      public static final String DEFAULT_SORT_ORDER = "name ASC";


      private Search() {}
   }

   public static final class Attachment implements Z7Content.AttachmentColumns {

      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.seven.attachment";
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.seven.attachment";
      public static final Uri CONTENT_URI = Uri.parse("content://com.seven.provider.email/attachments");
      public static final String DEFAULT_SORT_ORDER = "pos ASC";


      private Attachment() {}
   }

   public interface SyncColumns extends Z7Content.Z7BaseColumns {

      String CHANGE_KEY = "change_key";
      String CONTENT_ID = "content_id";
      String DATA_TYPE = "date_type";
      String EXTRA_FOLDER_ID = "extra_folder_id";
      String FOLDER_ID = "folder_id";
      String LOCAL_CHANGE_COUNT = "local_count";
      String LOCAL_ITEM_ID = "local_id";
      String REMOTE_CHANGE_COUNT = "remote_count";
      String STATE_FLAGS = "state_flags";
      String SYNC_ID = "sync_id";

   }

   public interface PendingTransactionColumns extends Z7Content.Z7BaseColumns {

      String FOLDER_IDENTIFIER = "folder_id";
      String SI_CHANGE_KEY = "si_ck";
      String SI_IDENTIFIER = "si_id";
      String SI_ITEM = "si_item";
      String TYPE = "type";

   }

   public interface Z7DBPrefsColumns extends BaseColumns {

      String CATEGORY = "category";
      String FLAGS = "flags";
      String KEY = "key";
      String TYPE = "type";
      String VALUE = "value";

   }

   public interface SettingColumns extends Z7Content.Z7BaseColumns {

      String CONTENT_ID = "content_id";
      String CONTEXT_ID = "context_id";
      String DESC = "desc";
      String PROPERTY_ID = "property";
      String TYPE = "type";
      int TYPE_GLOBAL = 1;
      int TYPE_STORAGE_HANDLER = 4;
      int TYPE_TYPE_TX_LOG = 3;
      String VALUE = "value";
      String VALUE2 = "value2";

   }

   public interface Z7BaseColumns extends BaseColumns {

      String ACCOUNT_ID = "account_id";
      String CREATED_DATE = "created";
      String MODIFIED_DATE = "modified";

   }

   public interface EmailColumns extends Z7Content.Z7BaseColumns {

      String BCC = "bcc";
      String BODY = "body";
      String CC = "cc";
      String DELIVERY_TIME = "delivery_time";
      String FOLDER_ID = "folder_id";
      String FOLLOW_UP_STATUS = "followup_status";
      String FROM = "_from";
      String HAS_ATTACHMENTS = "has_attachments";
      String HTML_BODY = "html_body";
      String HTML_BODY_MISSING_SIZE = "missing_html_body";
      String IMPORTANCE = "importance";
      String IS_ENCODING_UNKNOWN = "unk_encoding";
      String IS_UNREAD = "is_unread";
      String MEET_STATE = "meet_state";
      String MESSAGE_CONTENT_TYPE = "message_content_type";
      String MESSAGE_TYPE = "mesage_type";
      String MISSING_BODY_SIZE = "missing_body";
      String OMIT_RECIPIENT = "is_omit_receipt";
      String ORIG_MESSAGE_ACTION = "org_action";
      String ORIG_MESSAGE_ID = "org_id";
      String READ_RECIEPT_PENDING = "is_receipt_pending";
      String REPLY_TO = "reply_to";
      String SEPARATOR = "separator";
      String SHOW_MESSAGE_ACTION = "show_action";
      String SUBJECT = "subject";
      String TO = "_to";

   }

   public static final class Z7DBPrefs implements Z7Content.Z7DBPrefsColumns {

      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.seven.dbprefs";
      public static final Uri CONTENT_URI = Uri.parse("content://com.seven.provider.email/dbprefs");
      public static final String PATH = "dbprefs";
      public static final int TYPE_BLOB = 6;
      public static final int TYPE_BOOLEAN = 4;
      public static final int TYPE_FLOAT = 3;
      public static final int TYPE_INT = 1;
      public static final int TYPE_LONG = 2;
      public static final int TYPE_STRING = 5;


      private Z7DBPrefs() {}
   }

   public interface AttachmentColumns extends Z7Content.Z7BaseColumns {

      String BLOB = "bb";
      String CAN_DOWNLOAD = "can_download";
      String CONTENT_ID = "content_id";
      String EMAIL_ID = "email_id";
      String EST_SIZE = "est_size";
      String FILE_NAME = "file_name";
      String MIME_TYPE = "mime_type";
      String OFFSET = "offset";
      String POS = "pos";
      String SIZE = "size";
      String STATUS = "status";
      String URI = "uri";

   }

   public interface FileSystem {

      String AUTHORITY = "com.seven.provider.file";

   }

   public static final class Folders implements Z7Content.FolderColumns {

      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.seven.email";
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.seven.email";
      public static final Uri CONTENT_URI = Uri.parse("content://com.seven.provider.email/folders");
      public static final String DEFAULT_SORT_ORDER = "_id ASC";


      private Folders() {}
   }

   public static final class Sync implements Z7Content.SyncColumns {

      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.seven.sync";
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.seven.sync";
      public static final Uri CONTENT_URI = Uri.parse("content://com.seven.provider.email/sync");
      public static final String DEFAULT_SORT_ORDER = "_id ASC";


      private Sync() {}
   }

   public interface NotificationColumns extends Z7Content.Z7BaseColumns {

      String DATE = "date";
      String DESC = "desc";
      String MESSAGE_ID = "m_id";
      String TITLE = "title";
      String TYPE = "type";

   }
}
