package org.apache.commons.httpclient;


public class HttpStatus {

   private static final String[][] REASON_PHRASES;
   public static final int SC_ACCEPTED = 202;
   public static final int SC_BAD_GATEWAY = 502;
   public static final int SC_BAD_REQUEST = 400;
   public static final int SC_CONFLICT = 409;
   public static final int SC_CONTINUE = 100;
   public static final int SC_CREATED = 201;
   public static final int SC_EXPECTATION_FAILED = 417;
   public static final int SC_FAILED_DEPENDENCY = 424;
   public static final int SC_FORBIDDEN = 403;
   public static final int SC_GATEWAY_TIMEOUT = 504;
   public static final int SC_GONE = 410;
   public static final int SC_HTTP_VERSION_NOT_SUPPORTED = 505;
   public static final int SC_INSUFFICIENT_SPACE_ON_RESOURCE = 419;
   public static final int SC_INSUFFICIENT_STORAGE = 507;
   public static final int SC_INTERNAL_SERVER_ERROR = 500;
   public static final int SC_LENGTH_REQUIRED = 411;
   public static final int SC_LOCKED = 423;
   public static final int SC_METHOD_FAILURE = 420;
   public static final int SC_METHOD_NOT_ALLOWED = 405;
   public static final int SC_MOVED_PERMANENTLY = 301;
   public static final int SC_MOVED_TEMPORARILY = 302;
   public static final int SC_MULTIPLE_CHOICES = 300;
   public static final int SC_MULTI_STATUS = 207;
   public static final int SC_NON_AUTHORITATIVE_INFORMATION = 203;
   public static final int SC_NOT_ACCEPTABLE = 406;
   public static final int SC_NOT_FOUND = 404;
   public static final int SC_NOT_IMPLEMENTED = 501;
   public static final int SC_NOT_MODIFIED = 304;
   public static final int SC_NO_CONTENT = 204;
   public static final int SC_OK = 200;
   public static final int SC_PARTIAL_CONTENT = 206;
   public static final int SC_PAYMENT_REQUIRED = 402;
   public static final int SC_PRECONDITION_FAILED = 412;
   public static final int SC_PROCESSING = 102;
   public static final int SC_PROXY_AUTHENTICATION_REQUIRED = 407;
   public static final int SC_REQUESTED_RANGE_NOT_SATISFIABLE = 416;
   public static final int SC_REQUEST_TIMEOUT = 408;
   public static final int SC_REQUEST_TOO_LONG = 413;
   public static final int SC_REQUEST_URI_TOO_LONG = 414;
   public static final int SC_RESET_CONTENT = 205;
   public static final int SC_RETRY_WITH = 449;
   public static final int SC_SEE_OTHER = 303;
   public static final int SC_SERVICE_UNAVAILABLE = 503;
   public static final int SC_SWITCHING_PROTOCOLS = 101;
   public static final int SC_TEMPORARY_REDIRECT = 307;
   public static final int SC_UNAUTHORIZED = 401;
   public static final int SC_UNPROCESSABLE_ENTITY = 422;
   public static final int SC_UNSUPPORTED_MEDIA_TYPE = 415;
   public static final int SC_USE_PROXY = 305;


   static {
      String[] var0 = new String[6];
      String[] var1 = new String[0];
      var0[0] = var1;
      String[] var2 = new String[3];
      var0[1] = var2;
      String[] var3 = new String[8];
      var0[2] = var3;
      String[] var4 = new String[8];
      var0[3] = var4;
      String[] var5 = new String[25];
      var0[4] = var5;
      String[] var6 = new String[8];
      var0[5] = var6;
      REASON_PHRASES = var0;
      addStatusCodeMap(200, "OK");
      addStatusCodeMap(201, "Created");
      addStatusCodeMap(202, "Accepted");
      addStatusCodeMap(204, "No Content");
      addStatusCodeMap(301, "Moved Permanently");
      addStatusCodeMap(302, "Moved Temporarily");
      addStatusCodeMap(304, "Not Modified");
      addStatusCodeMap(400, "Bad Request");
      addStatusCodeMap(401, "Unauthorized");
      addStatusCodeMap(403, "Forbidden");
      addStatusCodeMap(404, "Not Found");
      addStatusCodeMap(500, "Internal Server Error");
      addStatusCodeMap(501, "Not Implemented");
      addStatusCodeMap(502, "Bad Gateway");
      addStatusCodeMap(503, "Service Unavailable");
      addStatusCodeMap(100, "Continue");
      addStatusCodeMap(307, "Temporary Redirect");
      addStatusCodeMap(405, "Method Not Allowed");
      addStatusCodeMap(409, "Conflict");
      addStatusCodeMap(412, "Precondition Failed");
      addStatusCodeMap(413, "Request Too Long");
      addStatusCodeMap(414, "Request-URI Too Long");
      addStatusCodeMap(415, "Unsupported Media Type");
      addStatusCodeMap(300, "Multiple Choices");
      addStatusCodeMap(303, "See Other");
      addStatusCodeMap(305, "Use Proxy");
      addStatusCodeMap(402, "Payment Required");
      addStatusCodeMap(406, "Not Acceptable");
      addStatusCodeMap(407, "Proxy Authentication Required");
      addStatusCodeMap(408, "Request Timeout");
      addStatusCodeMap(101, "Switching Protocols");
      addStatusCodeMap(203, "Non Authoritative Information");
      addStatusCodeMap(205, "Reset Content");
      addStatusCodeMap(206, "Partial Content");
      addStatusCodeMap(504, "Gateway Timeout");
      addStatusCodeMap(505, "Http Version Not Supported");
      addStatusCodeMap(410, "Gone");
      addStatusCodeMap(411, "Length Required");
      addStatusCodeMap(416, "Requested Range Not Satisfiable");
      addStatusCodeMap(417, "Expectation Failed");
      addStatusCodeMap(102, "Processing");
      addStatusCodeMap(207, "Multi-Status");
      addStatusCodeMap(422, "Unprocessable Entity");
      addStatusCodeMap(419, "Insufficient Space On Resource");
      addStatusCodeMap(420, "Method Failure");
      addStatusCodeMap(423, "Locked");
      addStatusCodeMap(507, "Insufficient Storage");
      addStatusCodeMap(424, "Failed Dependency");
   }

   public HttpStatus() {}

   private static void addStatusCodeMap(int var0, String var1) {
      int var2 = var0 / 100;
      String[] var3 = REASON_PHRASES[var2];
      int var4 = var2 * 100;
      int var5 = var0 - var4;
      var3[var5] = var1;
   }

   public static String getStatusText(int var0) {
      if(var0 < 0) {
         throw new IllegalArgumentException("status code may not be negative");
      } else {
         int var1 = var0 / 100;
         int var2 = var1 * 100;
         int var3 = var0 - var2;
         String var6;
         if(var1 >= 1) {
            int var4 = REASON_PHRASES.length - 1;
            if(var1 <= var4 && var3 >= 0) {
               int var5 = REASON_PHRASES[var1].length - 1;
               if(var3 <= var5) {
                  var6 = REASON_PHRASES[var1][var3];
                  return var6;
               }
            }
         }

         var6 = null;
         return var6;
      }
   }
}
