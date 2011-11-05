package com.kenai.jbosh;

import com.kenai.jbosh.AbstractBody;
import com.kenai.jbosh.BOSHException;
import com.kenai.jbosh.BodyQName;
import com.kenai.jbosh.StaticBody;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ComposableBody extends AbstractBody {

   private static final Pattern BOSH_START = Pattern.compile("<body(?:[\t\n\r ][^>]*?)?(/>|>)", 64);
   private final Map<BodyQName, String> attrs;
   private final AtomicReference<String> computed;
   private final String payload;


   private ComposableBody(Map<BodyQName, String> var1, String var2) {
      AtomicReference var3 = new AtomicReference();
      this.computed = var3;
      this.attrs = var1;
      this.payload = var2;
   }

   // $FF: synthetic method
   ComposableBody(Map var1, String var2, ComposableBody.1 var3) {
      this(var1, var2);
   }

   public static ComposableBody.Builder builder() {
      return new ComposableBody.Builder((ComposableBody.1)null);
   }

   private String computeXML() {
      BodyQName var1 = getBodyQName();
      StringBuilder var2 = new StringBuilder();
      StringBuilder var3 = var2.append("<");
      String var4 = var1.getLocalPart();
      var2.append(var4);

      StringBuilder var19;
      for(Iterator var6 = this.attrs.entrySet().iterator(); var6.hasNext(); var19 = var2.append("\'")) {
         Entry var7 = (Entry)var6.next();
         StringBuilder var8 = var2.append(" ");
         BodyQName var9 = (BodyQName)var7.getKey();
         String var10 = var9.getPrefix();
         if(var10 != null && var10.length() > 0) {
            var2.append(var10);
            StringBuilder var12 = var2.append(":");
         }

         String var13 = var9.getLocalPart();
         var2.append(var13);
         StringBuilder var15 = var2.append("=\'");
         String var16 = (String)var7.getValue();
         String var17 = this.escape(var16);
         var2.append(var17);
      }

      StringBuilder var20 = var2.append(" ");
      StringBuilder var21 = var2.append("xmlns");
      StringBuilder var22 = var2.append("=\'");
      String var23 = var1.getNamespaceURI();
      var2.append(var23);
      StringBuilder var25 = var2.append("\'>");
      if(this.payload != null) {
         String var26 = this.payload;
         var2.append(var26);
      }

      StringBuilder var28 = var2.append("</body>");
      return var2.toString();
   }

   private String escape(String var1) {
      return var1.replace("\'", "&apos;");
   }

   static ComposableBody fromStaticBody(StaticBody var0) throws BOSHException {
      String var1 = var0.toXML();
      Matcher var2 = BOSH_START.matcher(var1);
      if(!var2.find()) {
         StringBuilder var3 = (new StringBuilder()).append("Could not locate \'body\' element in XML.  The raw XML did not match the pattern: ");
         Pattern var4 = BOSH_START;
         String var5 = var3.append(var4).toString();
         throw new BOSHException(var5);
      } else {
         String var6 = var2.group(1);
         if(">".equals(var6)) {
            int var9 = var2.end();
            int var7 = var1.lastIndexOf("</");
            if(var7 < var9) {
               var7 = var9;
            }

            var1 = var1.substring(var9, var7);
         } else {
            var1 = "";
         }

         Map var8 = var0.getAttributes();
         return new ComposableBody(var8, var1);
      }
   }

   public Map<BodyQName, String> getAttributes() {
      return Collections.unmodifiableMap(this.attrs);
   }

   public String getPayloadXML() {
      return this.payload;
   }

   public ComposableBody.Builder rebuild() {
      return ComposableBody.Builder.fromBody(this);
   }

   public String toXML() {
      String var1 = (String)this.computed.get();
      if(var1 == null) {
         var1 = this.computeXML();
         this.computed.set(var1);
      }

      return var1;
   }

   public static final class Builder {

      private boolean doMapCopy;
      private Map<BodyQName, String> map;
      private String payloadXML;


      private Builder() {}

      // $FF: synthetic method
      Builder(ComposableBody.1 var1) {
         this();
      }

      private static ComposableBody.Builder fromBody(ComposableBody var0) {
         ComposableBody.Builder var1 = new ComposableBody.Builder();
         Map var2 = var0.getAttributes();
         var1.map = var2;
         var1.doMapCopy = (boolean)1;
         String var3 = var0.payload;
         var1.payloadXML = var3;
         return var1;
      }

      public ComposableBody build() {
         if(this.map == null) {
            HashMap var1 = new HashMap();
            this.map = var1;
         }

         if(this.payloadXML == null) {
            this.payloadXML = "";
         }

         Map var2 = this.map;
         String var3 = this.payloadXML;
         return new ComposableBody(var2, var3, (ComposableBody.1)null);
      }

      public ComposableBody.Builder setAttribute(BodyQName var1, String var2) {
         if(this.map == null) {
            HashMap var3 = new HashMap();
            this.map = var3;
         } else if(this.doMapCopy) {
            Map var5 = this.map;
            HashMap var6 = new HashMap(var5);
            this.map = var6;
            this.doMapCopy = (boolean)0;
         }

         if(var2 == null) {
            this.map.remove(var1);
         } else {
            this.map.put(var1, var2);
         }

         return this;
      }

      public ComposableBody.Builder setNamespaceDefinition(String var1, String var2) {
         BodyQName var3 = BodyQName.createWithPrefix("http://www.w3.org/XML/1998/namespace", var1, "xmlns");
         return this.setAttribute(var3, var2);
      }

      public ComposableBody.Builder setPayloadXML(String var1) {
         if(var1 == null) {
            throw new IllegalArgumentException("payload XML argument cannot be null");
         } else {
            this.payloadXML = var1;
            return this;
         }
      }
   }

   // $FF: synthetic class
   static class 1 {
   }
}
