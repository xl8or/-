package gnu.inet.imap;


public interface IMAPConstants {

   String ACL = "ACL";
   String ALERT = "ALERT";
   String APPEND = "APPEND";
   String AUTHENTICATE = "AUTHENTICATE";
   String BAD = "BAD";
   String BODY = "BODY";
   String BODYSTRUCTURE = "BODYSTRUCTURE";
   String BODY_PEEK = "BODY.PEEK";
   String BYE = "BYE";
   String CAPABILITY = "CAPABILITY";
   String CHECK = "CHECK";
   String CLOSE = "CLOSE";
   String COPY = "COPY";
   String CRAM_MD5 = "CRAM-MD5";
   String CREATE = "CREATE";
   String DELETE = "DELETE";
   String DELETEACL = "DELETEACL";
   String ENVELOPE = "ENVELOPE";
   String EXAMINE = "EXAMINE";
   String EXISTS = "EXISTS";
   String EXPUNGE = "EXPUNGE";
   String FETCH = "FETCH";
   String FETCH_FLAGS = "FETCH FLAGS";
   String FLAGS = "FLAGS";
   String FLAG_ANSWERED = "\\Answered";
   String FLAG_DELETED = "\\Deleted";
   String FLAG_DRAFT = "\\Draft";
   String FLAG_FLAGGED = "\\Flagged";
   String FLAG_RECENT = "\\Recent";
   String FLAG_SEEN = "\\Seen";
   String GETACL = "GETACL";
   String GETQUOTA = "GETQUOTA";
   String GETQUOTAROOT = "GETQUOTAROOT";
   String GSSAPI = "GSSAPI";
   String HEADER = "HEADER";
   String HEADER_FIELDS = "HEADER.FIELDS";
   String HEADER_FIELDS_NOT = "HEADER.FIELDS.NOT";
   String INTERNALDATE = "INTERNALDATE";
   String KERBEROS_V4 = "KERBEROS_V4";
   String LIST = "LIST";
   String LISTRIGHTS = "LISTRIGHTS";
   String LIST_MARKED = "\\Marked";
   String LIST_NOINFERIORS = "\\Noinferiors";
   String LIST_NOSELECT = "\\Noselect";
   String LIST_UNMARKED = "\\Unmarked";
   String LOGIN = "LOGIN";
   String LOGINDISABLED = "LOGINDISABLED";
   String LOGOUT = "LOGOUT";
   String LSUB = "LSUB";
   String MESSAGES = "MESSAGES";
   String MYRIGHTS = "MYRIGHTS";
   String NAMESPACE = "NAMESPACE";
   String NEWNAME = "NEWNAME";
   String NIL = "NIL";
   String NO = "NO";
   String NOOP = "NOOP";
   String OK = "OK";
   String PARSE = "PARSE";
   String PERMANENTFLAGS = "PERMANENTFLAGS";
   String PREAUTH = "PREAUTH";
   String QUOTA = "QUOTA";
   String QUOTAROOT = "QUOTAROOT";
   String READ_ONLY = "READ-ONLY";
   String READ_WRITE = "READ-WRITE";
   String RECENT = "RECENT";
   String RENAME = "RENAME";
   String RFC822 = "RFC822";
   String RFC822_HEADER = "RFC822.HEADER";
   String RFC822_SIZE = "RFC822.SIZE";
   String RFC822_TEXT = "RFC822.TEXT";
   int RIGHTS_ADMIN = 256;
   int RIGHTS_CREATE = 64;
   int RIGHTS_DELETE = 128;
   int RIGHTS_INSERT = 16;
   int RIGHTS_LOOKUP = 1;
   int RIGHTS_POST = 32;
   int RIGHTS_READ = 2;
   int RIGHTS_SEEN = 4;
   int RIGHTS_WRITE = 8;
   String SEARCH = "SEARCH";
   String SEARCH_ALL = "ALL";
   String SEARCH_ANSWERED = "ANSWERED";
   String SEARCH_BCC = "BCC";
   String SEARCH_BEFORE = "BEFORE";
   String SEARCH_BODY = "BODY";
   String SEARCH_CC = "CC";
   String SEARCH_DELETED = "DELETED";
   String SEARCH_DRAFT = "DRAFT";
   String SEARCH_FLAGGED = "FLAGGED";
   String SEARCH_FROM = "FROM";
   String SEARCH_HEADER = "HEADER";
   String SEARCH_KEYWORD = "KEYWORD";
   String SEARCH_LARGER = "LARGER";
   String SEARCH_NEW = "NEW";
   String SEARCH_NOT = "NOT";
   String SEARCH_OLD = "OLD";
   String SEARCH_ON = "ON";
   String SEARCH_OR = "OR";
   String SEARCH_RECENT = "RECENT";
   String SEARCH_SEEN = "SEEN";
   String SEARCH_SENTBEFORE = "SENTBEFORE";
   String SEARCH_SENTON = "SENTON";
   String SEARCH_SENTSINCE = "SENTSINCE";
   String SEARCH_SINCE = "SINCE";
   String SEARCH_SMALLER = "SMALLER";
   String SEARCH_SUBJECT = "SUBJECT";
   String SEARCH_TEXT = "TEXT";
   String SEARCH_TO = "TO";
   String SEARCH_UID = "UID";
   String SEARCH_UNANSWERED = "UNANSWERED";
   String SEARCH_UNDELETED = "UNDELETED";
   String SEARCH_UNDRAFT = "UNDRAFT";
   String SEARCH_UNFLAGGED = "UNFLAGGED";
   String SEARCH_UNKEYWORD = "UNKEYWORD";
   String SEARCH_UNSEEN = "UNSEEN";
   String SELECT = "SELECT";
   String SETACL = "SETACL";
   String SETQUOTA = "SETQUOTA";
   String SKEY = "SKEY";
   String STARTTLS = "STARTTLS";
   String STATUS = "STATUS";
   String STORAGE = "STORAGE";
   String STORE = "STORE";
   String SUBSCRIBE = "SUBSCRIBE";
   String TRYCREATE = "TRYCREATE";
   String UID = "UID";
   String UIDNEXT = "UIDNEXT";
   String UIDVALIDITY = "UIDVALIDITY";
   String UNSEEN = "UNSEEN";
   String UNSUBSCRIBE = "UNSUBSCRIBE";

}
