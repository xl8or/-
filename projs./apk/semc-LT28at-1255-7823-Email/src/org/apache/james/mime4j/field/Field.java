package org.apache.james.mime4j.field;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.james.mime4j.field.DefaultFieldParser;

public abstract class Field {

   public static final String BCC = "Bcc";
   public static final String CC = "Cc";
   public static final String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";
   public static final String CONTENT_TYPE = "Content-Type";
   public static final String DATE = "Date";
   private static final String FIELD_NAME_PATTERN = "^([\\x21-\\x39\\x3b-\\x7e]+)[ \t]*:";
   public static final String FROM = "From";
   public static final String REPLY_TO = "Reply-To";
   public static final String RESENT_BCC = "Resent-Bcc";
   public static final String RESENT_CC = "Resent-Cc";
   public static final String RESENT_DATE = "Resent-Date";
   public static final String RESENT_FROM = "Resent-From";
   public static final String RESENT_SENDER = "Resent-Sender";
   public static final String RESENT_TO = "Resent-To";
   public static final String SENDER = "Sender";
   public static final String SUBJECT = "Subject";
   public static final String TO = "To";
   private static final Pattern fieldNamePattern = Pattern.compile("^([\\x21-\\x39\\x3b-\\x7e]+)[ \t]*:");
   private static final DefaultFieldParser parser = new DefaultFieldParser();
   private final String body;
   private final String name;
   private final String raw;


   protected Field(String var1, String var2, String var3) {
      this.name = var1;
      this.body = var2;
      this.raw = var3;
   }

   public static DefaultFieldParser getParser() {
      return parser;
   }

   public static Field parse(String var0) {
      String var1 = var0.replaceAll("\r|\n", "");
      Matcher var2 = fieldNamePattern.matcher(var1);
      if(!var2.find()) {
         throw new IllegalArgumentException("Invalid field in string");
      } else {
         String var3 = var2.group(1);
         int var4 = var2.end();
         String var5 = var1.substring(var4);
         if(var5.length() > 0 && var5.charAt(0) == 32) {
            var5 = var5.substring(1);
         }

         return parser.parse(var3, var5, var0);
      }
   }

   public String getBody() {
      return this.body;
   }

   public String getName() {
      return this.name;
   }

   public String getRaw() {
      return this.raw;
   }

   public boolean isContentType() {
      String var1 = this.name;
      return "Content-Type".equalsIgnoreCase(var1);
   }

   public boolean isFrom() {
      String var1 = this.name;
      return "From".equalsIgnoreCase(var1);
   }

   public boolean isSubject() {
      String var1 = this.name;
      return "Subject".equalsIgnoreCase(var1);
   }

   public boolean isTo() {
      String var1 = this.name;
      return "To".equalsIgnoreCase(var1);
   }

   public String toString() {
      return this.raw;
   }
}
