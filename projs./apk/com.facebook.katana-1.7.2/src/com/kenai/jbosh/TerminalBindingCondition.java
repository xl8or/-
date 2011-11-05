package com.kenai.jbosh;

import java.util.HashMap;
import java.util.Map;

final class TerminalBindingCondition {

   static final TerminalBindingCondition BAD_REQUEST;
   private static final Map<Integer, TerminalBindingCondition> CODE_TO_INSTANCE = new HashMap();
   private static final Map<String, TerminalBindingCondition> COND_TO_INSTANCE = new HashMap();
   static final TerminalBindingCondition HOST_GONE;
   static final TerminalBindingCondition HOST_UNKNOWN;
   static final TerminalBindingCondition IMPROPER_ADDRESSING;
   static final TerminalBindingCondition INTERNAL_SERVER_ERROR;
   static final TerminalBindingCondition ITEM_NOT_FOUND;
   static final TerminalBindingCondition OTHER_REQUEST;
   static final TerminalBindingCondition POLICY_VIOLATION;
   static final TerminalBindingCondition REMOTE_CONNECTION_FAILED;
   static final TerminalBindingCondition REMOTE_STREAM_ERROR;
   static final TerminalBindingCondition SEE_OTHER_URI;
   static final TerminalBindingCondition SYSTEM_SHUTDOWN;
   static final TerminalBindingCondition UNDEFINED_CONDITION;
   private final String cond;
   private final String msg;


   static {
      Integer var0 = Integer.valueOf(400);
      BAD_REQUEST = createWithCode("bad-request", "The format of an HTTP header or binding element received from the client is unacceptable (e.g., syntax error).", var0);
      HOST_GONE = create("host-gone", "The target domain specified in the \'to\' attribute or the target host or port specified in the \'route\' attribute is no longer serviced by the connection manager.");
      HOST_UNKNOWN = create("host-unknown", "The target domain specified in the \'to\' attribute or the target host or port specified in the \'route\' attribute is unknown to the connection manager.");
      IMPROPER_ADDRESSING = create("improper-addressing", "The initialization element lacks a \'to\' or \'route\' attribute (or the attribute has no value) but the connection manager requires one.");
      INTERNAL_SERVER_ERROR = create("internal-server-error", "The connection manager has experienced an internal error that prevents it from servicing the request.");
      Integer var1 = Integer.valueOf(404);
      ITEM_NOT_FOUND = createWithCode("item-not-found", "(1) \'sid\' is not valid, (2) \'stream\' is not valid, (3) \'rid\' is larger than the upper limit of the expected window, (4) connection manager is unable to resend response, (5) \'key\' sequence is invalid.", var1);
      OTHER_REQUEST = create("other-request", "Another request being processed at the same time as this request caused the session to terminate.");
      Integer var2 = Integer.valueOf(403);
      POLICY_VIOLATION = createWithCode("policy-violation", "The client has broken the session rules (polling too frequently, requesting too frequently, sending too many simultaneous requests).", var2);
      REMOTE_CONNECTION_FAILED = create("remote-connection-failed", "The connection manager was unable to connect to, or unable to connect securely to, or has lost its connection to, the server.");
      REMOTE_STREAM_ERROR = create("remote-stream-error", "Encapsulated transport protocol error.");
      SEE_OTHER_URI = create("see-other-uri", "The connection manager does not operate at this URI (e.g., the connection manager accepts only SSL or TLS connections at some https: URI rather than the http: URI requested by the client).");
      SYSTEM_SHUTDOWN = create("system-shutdown", "The connection manager is being shut down. All active HTTP sessions are being terminated. No new sessions can be created.");
      UNDEFINED_CONDITION = create("undefined-condition", "Unknown or undefined error condition.");
   }

   private TerminalBindingCondition(String var1, String var2) {
      this.cond = var1;
      this.msg = var2;
   }

   private static TerminalBindingCondition create(String var0, String var1) {
      return createWithCode(var0, var1, (Integer)null);
   }

   private static TerminalBindingCondition createWithCode(String var0, String var1, Integer var2) {
      if(var0 == null) {
         throw new IllegalArgumentException("condition may not be null");
      } else if(var1 == null) {
         throw new IllegalArgumentException("message may not be null");
      } else if(COND_TO_INSTANCE.get(var0) != null) {
         String var3 = "Multiple definitions of condition: " + var0;
         throw new IllegalStateException(var3);
      } else {
         TerminalBindingCondition var4 = new TerminalBindingCondition(var0, var1);
         COND_TO_INSTANCE.put(var0, var4);
         if(var2 != null) {
            if(CODE_TO_INSTANCE.get(var2) != null) {
               String var6 = "Multiple definitions of code: " + var2;
               throw new IllegalStateException(var6);
            }

            CODE_TO_INSTANCE.put(var2, var4);
         }

         return var4;
      }
   }

   static TerminalBindingCondition forHTTPResponseCode(int var0) {
      Map var1 = CODE_TO_INSTANCE;
      Integer var2 = Integer.valueOf(var0);
      return (TerminalBindingCondition)var1.get(var2);
   }

   static TerminalBindingCondition forString(String var0) {
      return (TerminalBindingCondition)COND_TO_INSTANCE.get(var0);
   }

   String getCondition() {
      return this.cond;
   }

   String getMessage() {
      return this.msg;
   }
}
