package org.jivesoftware.smack.packet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jivesoftware.smack.packet.PacketExtension;

public class XMPPError {

   private List<PacketExtension> applicationExtensions = null;
   private int code;
   private String condition;
   private String message;
   private XMPPError.Type type;


   public XMPPError(int var1) {
      this.code = var1;
      this.message = null;
   }

   public XMPPError(int var1, String var2) {
      this.code = var1;
      this.message = var2;
   }

   public XMPPError(int var1, XMPPError.Type var2, String var3, String var4, List<PacketExtension> var5) {
      this.code = var1;
      this.type = var2;
      this.condition = var3;
      this.message = var4;
      this.applicationExtensions = var5;
   }

   public XMPPError(XMPPError.Condition var1) {
      this.init(var1);
      this.message = null;
   }

   public XMPPError(XMPPError.Condition var1, String var2) {
      this.init(var1);
      this.message = var2;
   }

   private void init(XMPPError.Condition var1) {
      XMPPError.ErrorSpecification var2 = XMPPError.ErrorSpecification.specFor(var1);
      String var3 = var1.value;
      this.condition = var3;
      if(var2 != null) {
         XMPPError.Type var4 = var2.getType();
         this.type = var4;
         int var5 = var2.getCode();
         this.code = var5;
      }
   }

   public void addExtension(PacketExtension var1) {
      synchronized(this){}

      try {
         if(this.applicationExtensions == null) {
            ArrayList var2 = new ArrayList();
            this.applicationExtensions = var2;
         }

         this.applicationExtensions.add(var1);
      } finally {
         ;
      }

   }

   public int getCode() {
      return this.code;
   }

   public String getCondition() {
      return this.condition;
   }

   public PacketExtension getExtension(String param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   public List<PacketExtension> getExtensions() {
      synchronized(this){}
      boolean var5 = false;

      List var1;
      List var2;
      label55: {
         try {
            var5 = true;
            if(this.applicationExtensions == null) {
               var1 = Collections.emptyList();
               var5 = false;
               break label55;
            }

            var1 = Collections.unmodifiableList(this.applicationExtensions);
            var5 = false;
         } finally {
            if(var5) {
               ;
            }
         }

         var2 = var1;
         return var2;
      }

      var2 = var1;
      return var2;
   }

   public String getMessage() {
      return this.message;
   }

   public XMPPError.Type getType() {
      return this.type;
   }

   public void setExtension(List<PacketExtension> var1) {
      synchronized(this){}

      try {
         this.applicationExtensions = var1;
      } finally {
         ;
      }

   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      if(this.condition != null) {
         String var2 = this.condition;
         var1.append(var2);
      }

      StringBuilder var4 = var1.append("(");
      int var5 = this.code;
      StringBuilder var6 = var4.append(var5).append(")");
      if(this.message != null) {
         StringBuilder var7 = var1.append(" ");
         String var8 = this.message;
         var7.append(var8);
      }

      return var1.toString();
   }

   public String toXML() {
      StringBuilder var1 = new StringBuilder();
      StringBuilder var2 = var1.append("<error code=\"");
      int var3 = this.code;
      StringBuilder var4 = var2.append(var3).append("\"");
      if(this.type != null) {
         StringBuilder var5 = var1.append(" type=\"");
         String var6 = this.type.name();
         var1.append(var6);
         StringBuilder var8 = var1.append("\"");
      }

      StringBuilder var9 = var1.append(">");
      if(this.condition != null) {
         StringBuilder var10 = var1.append("<");
         String var11 = this.condition;
         var10.append(var11);
         StringBuilder var13 = var1.append(" xmlns=\"urn:ietf:params:xml:ns:xmpp-stanzas\"/>");
      }

      if(this.message != null) {
         StringBuilder var14 = var1.append("<text xml:lang=\"en\" xmlns=\"urn:ietf:params:xml:ns:xmpp-stanzas\">");
         String var15 = this.message;
         var1.append(var15);
         StringBuilder var17 = var1.append("</text>");
      }

      Iterator var18 = this.getExtensions().iterator();

      while(var18.hasNext()) {
         String var19 = ((PacketExtension)var18.next()).toXML();
         var1.append(var19);
      }

      StringBuilder var21 = var1.append("</error>");
      return var1.toString();
   }

   public static enum Type {

      // $FF: synthetic field
      private static final XMPPError.Type[] $VALUES;
      AUTH("AUTH", 3),
      CANCEL("CANCEL", 1),
      CONTINUE("CONTINUE", 4),
      MODIFY("MODIFY", 2),
      WAIT("WAIT", 0);


      static {
         XMPPError.Type[] var0 = new XMPPError.Type[5];
         XMPPError.Type var1 = WAIT;
         var0[0] = var1;
         XMPPError.Type var2 = CANCEL;
         var0[1] = var2;
         XMPPError.Type var3 = MODIFY;
         var0[2] = var3;
         XMPPError.Type var4 = AUTH;
         var0[3] = var4;
         XMPPError.Type var5 = CONTINUE;
         var0[4] = var5;
         $VALUES = var0;
      }

      private Type(String var1, int var2) {}
   }

   public static class Condition {

      public static final XMPPError.Condition bad_request = new XMPPError.Condition("bad-request");
      public static final XMPPError.Condition conflict = new XMPPError.Condition("conflict");
      public static final XMPPError.Condition feature_not_implemented = new XMPPError.Condition("feature-not-implemented");
      public static final XMPPError.Condition forbidden = new XMPPError.Condition("forbidden");
      public static final XMPPError.Condition gone = new XMPPError.Condition("gone");
      public static final XMPPError.Condition interna_server_error = new XMPPError.Condition("internal-server-error");
      public static final XMPPError.Condition item_not_found = new XMPPError.Condition("item-not-found");
      public static final XMPPError.Condition jid_malformed = new XMPPError.Condition("jid-malformed");
      public static final XMPPError.Condition no_acceptable = new XMPPError.Condition("not-acceptable");
      public static final XMPPError.Condition not_allowed = new XMPPError.Condition("not-allowed");
      public static final XMPPError.Condition not_authorized = new XMPPError.Condition("not-authorized");
      public static final XMPPError.Condition payment_required = new XMPPError.Condition("payment-required");
      public static final XMPPError.Condition recipient_unavailable = new XMPPError.Condition("recipient-unavailable");
      public static final XMPPError.Condition redirect = new XMPPError.Condition("redirect");
      public static final XMPPError.Condition registration_required = new XMPPError.Condition("registration-required");
      public static final XMPPError.Condition remote_server_error = new XMPPError.Condition("remote-server-error");
      public static final XMPPError.Condition remote_server_not_found = new XMPPError.Condition("remote-server-not-found");
      public static final XMPPError.Condition remote_server_timeout = new XMPPError.Condition("remote-server-timeout");
      public static final XMPPError.Condition request_timeout = new XMPPError.Condition("request-timeout");
      public static final XMPPError.Condition resource_constraint = new XMPPError.Condition("resource-constraint");
      public static final XMPPError.Condition service_unavailable = new XMPPError.Condition("service-unavailable");
      public static final XMPPError.Condition subscription_required = new XMPPError.Condition("subscription-required");
      public static final XMPPError.Condition undefined_condition = new XMPPError.Condition("undefined-condition");
      public static final XMPPError.Condition unexpected_request = new XMPPError.Condition("unexpected-request");
      private String value;


      public Condition(String var1) {
         this.value = var1;
      }

      public String toString() {
         return this.value;
      }
   }

   private static class ErrorSpecification {

      private static Map<XMPPError.Condition, XMPPError.ErrorSpecification> instances = errorSpecifications();
      private int code;
      private XMPPError.Condition condition;
      private XMPPError.Type type;


      private ErrorSpecification(XMPPError.Condition var1, XMPPError.Type var2, int var3) {
         this.code = var3;
         this.type = var2;
         this.condition = var1;
      }

      private static Map<XMPPError.Condition, XMPPError.ErrorSpecification> errorSpecifications() {
         HashMap var0 = new HashMap(22);
         XMPPError.Condition var1 = XMPPError.Condition.interna_server_error;
         XMPPError.Condition var2 = XMPPError.Condition.interna_server_error;
         XMPPError.Type var3 = XMPPError.Type.WAIT;
         XMPPError.ErrorSpecification var4 = new XMPPError.ErrorSpecification(var2, var3, 500);
         var0.put(var1, var4);
         XMPPError.Condition var6 = XMPPError.Condition.forbidden;
         XMPPError.Condition var7 = XMPPError.Condition.forbidden;
         XMPPError.Type var8 = XMPPError.Type.AUTH;
         XMPPError.ErrorSpecification var9 = new XMPPError.ErrorSpecification(var7, var8, 403);
         var0.put(var6, var9);
         XMPPError.Condition var11 = XMPPError.Condition.bad_request;
         XMPPError.Condition var12 = XMPPError.Condition.bad_request;
         XMPPError.Type var13 = XMPPError.Type.MODIFY;
         XMPPError.ErrorSpecification var14 = new XMPPError.ErrorSpecification(var12, var13, 400);
         var0.put(var11, var14);
         XMPPError.Condition var16 = XMPPError.Condition.item_not_found;
         XMPPError.Condition var17 = XMPPError.Condition.item_not_found;
         XMPPError.Type var18 = XMPPError.Type.CANCEL;
         XMPPError.ErrorSpecification var19 = new XMPPError.ErrorSpecification(var17, var18, 404);
         var0.put(var16, var19);
         XMPPError.Condition var21 = XMPPError.Condition.conflict;
         XMPPError.Condition var22 = XMPPError.Condition.conflict;
         XMPPError.Type var23 = XMPPError.Type.CANCEL;
         XMPPError.ErrorSpecification var24 = new XMPPError.ErrorSpecification(var22, var23, 409);
         var0.put(var21, var24);
         XMPPError.Condition var26 = XMPPError.Condition.feature_not_implemented;
         XMPPError.Condition var27 = XMPPError.Condition.feature_not_implemented;
         XMPPError.Type var28 = XMPPError.Type.CANCEL;
         XMPPError.ErrorSpecification var29 = new XMPPError.ErrorSpecification(var27, var28, 501);
         var0.put(var26, var29);
         XMPPError.Condition var31 = XMPPError.Condition.gone;
         XMPPError.Condition var32 = XMPPError.Condition.gone;
         XMPPError.Type var33 = XMPPError.Type.MODIFY;
         XMPPError.ErrorSpecification var34 = new XMPPError.ErrorSpecification(var32, var33, 302);
         var0.put(var31, var34);
         XMPPError.Condition var36 = XMPPError.Condition.jid_malformed;
         XMPPError.Condition var37 = XMPPError.Condition.jid_malformed;
         XMPPError.Type var38 = XMPPError.Type.MODIFY;
         XMPPError.ErrorSpecification var39 = new XMPPError.ErrorSpecification(var37, var38, 400);
         var0.put(var36, var39);
         XMPPError.Condition var41 = XMPPError.Condition.no_acceptable;
         XMPPError.Condition var42 = XMPPError.Condition.no_acceptable;
         XMPPError.Type var43 = XMPPError.Type.MODIFY;
         XMPPError.ErrorSpecification var44 = new XMPPError.ErrorSpecification(var42, var43, 406);
         var0.put(var41, var44);
         XMPPError.Condition var46 = XMPPError.Condition.not_allowed;
         XMPPError.Condition var47 = XMPPError.Condition.not_allowed;
         XMPPError.Type var48 = XMPPError.Type.CANCEL;
         XMPPError.ErrorSpecification var49 = new XMPPError.ErrorSpecification(var47, var48, 405);
         var0.put(var46, var49);
         XMPPError.Condition var51 = XMPPError.Condition.not_authorized;
         XMPPError.Condition var52 = XMPPError.Condition.not_authorized;
         XMPPError.Type var53 = XMPPError.Type.AUTH;
         XMPPError.ErrorSpecification var54 = new XMPPError.ErrorSpecification(var52, var53, 401);
         var0.put(var51, var54);
         XMPPError.Condition var56 = XMPPError.Condition.payment_required;
         XMPPError.Condition var57 = XMPPError.Condition.payment_required;
         XMPPError.Type var58 = XMPPError.Type.AUTH;
         XMPPError.ErrorSpecification var59 = new XMPPError.ErrorSpecification(var57, var58, 402);
         var0.put(var56, var59);
         XMPPError.Condition var61 = XMPPError.Condition.recipient_unavailable;
         XMPPError.Condition var62 = XMPPError.Condition.recipient_unavailable;
         XMPPError.Type var63 = XMPPError.Type.WAIT;
         XMPPError.ErrorSpecification var64 = new XMPPError.ErrorSpecification(var62, var63, 404);
         var0.put(var61, var64);
         XMPPError.Condition var66 = XMPPError.Condition.redirect;
         XMPPError.Condition var67 = XMPPError.Condition.redirect;
         XMPPError.Type var68 = XMPPError.Type.MODIFY;
         XMPPError.ErrorSpecification var69 = new XMPPError.ErrorSpecification(var67, var68, 302);
         var0.put(var66, var69);
         XMPPError.Condition var71 = XMPPError.Condition.registration_required;
         XMPPError.Condition var72 = XMPPError.Condition.registration_required;
         XMPPError.Type var73 = XMPPError.Type.AUTH;
         XMPPError.ErrorSpecification var74 = new XMPPError.ErrorSpecification(var72, var73, 407);
         var0.put(var71, var74);
         XMPPError.Condition var76 = XMPPError.Condition.remote_server_not_found;
         XMPPError.Condition var77 = XMPPError.Condition.remote_server_not_found;
         XMPPError.Type var78 = XMPPError.Type.CANCEL;
         XMPPError.ErrorSpecification var79 = new XMPPError.ErrorSpecification(var77, var78, 404);
         var0.put(var76, var79);
         XMPPError.Condition var81 = XMPPError.Condition.remote_server_timeout;
         XMPPError.Condition var82 = XMPPError.Condition.remote_server_timeout;
         XMPPError.Type var83 = XMPPError.Type.WAIT;
         XMPPError.ErrorSpecification var84 = new XMPPError.ErrorSpecification(var82, var83, 504);
         var0.put(var81, var84);
         XMPPError.Condition var86 = XMPPError.Condition.remote_server_error;
         XMPPError.Condition var87 = XMPPError.Condition.remote_server_error;
         XMPPError.Type var88 = XMPPError.Type.CANCEL;
         XMPPError.ErrorSpecification var89 = new XMPPError.ErrorSpecification(var87, var88, 502);
         var0.put(var86, var89);
         XMPPError.Condition var91 = XMPPError.Condition.resource_constraint;
         XMPPError.Condition var92 = XMPPError.Condition.resource_constraint;
         XMPPError.Type var93 = XMPPError.Type.WAIT;
         XMPPError.ErrorSpecification var94 = new XMPPError.ErrorSpecification(var92, var93, 500);
         var0.put(var91, var94);
         XMPPError.Condition var96 = XMPPError.Condition.service_unavailable;
         XMPPError.Condition var97 = XMPPError.Condition.service_unavailable;
         XMPPError.Type var98 = XMPPError.Type.CANCEL;
         XMPPError.ErrorSpecification var99 = new XMPPError.ErrorSpecification(var97, var98, 503);
         var0.put(var96, var99);
         XMPPError.Condition var101 = XMPPError.Condition.subscription_required;
         XMPPError.Condition var102 = XMPPError.Condition.subscription_required;
         XMPPError.Type var103 = XMPPError.Type.AUTH;
         XMPPError.ErrorSpecification var104 = new XMPPError.ErrorSpecification(var102, var103, 407);
         var0.put(var101, var104);
         XMPPError.Condition var106 = XMPPError.Condition.undefined_condition;
         XMPPError.Condition var107 = XMPPError.Condition.undefined_condition;
         XMPPError.Type var108 = XMPPError.Type.WAIT;
         XMPPError.ErrorSpecification var109 = new XMPPError.ErrorSpecification(var107, var108, 500);
         var0.put(var106, var109);
         XMPPError.Condition var111 = XMPPError.Condition.unexpected_request;
         XMPPError.Condition var112 = XMPPError.Condition.unexpected_request;
         XMPPError.Type var113 = XMPPError.Type.WAIT;
         XMPPError.ErrorSpecification var114 = new XMPPError.ErrorSpecification(var112, var113, 400);
         var0.put(var111, var114);
         XMPPError.Condition var116 = XMPPError.Condition.request_timeout;
         XMPPError.Condition var117 = XMPPError.Condition.request_timeout;
         XMPPError.Type var118 = XMPPError.Type.CANCEL;
         XMPPError.ErrorSpecification var119 = new XMPPError.ErrorSpecification(var117, var118, 408);
         var0.put(var116, var119);
         return var0;
      }

      protected static XMPPError.ErrorSpecification specFor(XMPPError.Condition var0) {
         return (XMPPError.ErrorSpecification)instances.get(var0);
      }

      protected int getCode() {
         return this.code;
      }

      protected XMPPError.Condition getCondition() {
         return this.condition;
      }

      protected XMPPError.Type getType() {
         return this.type;
      }
   }
}
