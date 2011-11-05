package org.apache.james.mime4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.BitSet;
import java.util.LinkedList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.james.mime4j.BodyDescriptor;
import org.apache.james.mime4j.CloseShieldInputStream;
import org.apache.james.mime4j.ContentHandler;
import org.apache.james.mime4j.EOLConvertingInputStream;
import org.apache.james.mime4j.MimeBoundaryInputStream;
import org.apache.james.mime4j.RootInputStream;
import org.apache.james.mime4j.decoder.Base64InputStream;
import org.apache.james.mime4j.decoder.QuotedPrintableInputStream;

public class MimeStreamParser {

   private static BitSet fieldChars = null;
   private static final Log log = LogFactory.getLog(MimeStreamParser.class);
   private LinkedList bodyDescriptors;
   private ContentHandler handler;
   private boolean raw;
   private RootInputStream rootStream = null;


   static {
      fieldChars = new BitSet();

      for(int var0 = 33; var0 <= 57; ++var0) {
         fieldChars.set(var0);
      }

      for(int var1 = 59; var1 <= 126; ++var1) {
         fieldChars.set(var1);
      }

   }

   public MimeStreamParser() {
      LinkedList var1 = new LinkedList();
      this.bodyDescriptors = var1;
      this.handler = null;
      this.raw = (boolean)0;
   }

   private void parseBodyPart(InputStream var1) throws IOException {
      if(this.raw) {
         ContentHandler var2 = this.handler;
         CloseShieldInputStream var3 = new CloseShieldInputStream(var1);
         var2.raw(var3);
      } else {
         this.handler.startBodyPart();
         this.parseEntity(var1);
         this.handler.endBodyPart();
      }
   }

   private void parseEntity(InputStream var1) throws IOException {
      BodyDescriptor var2 = this.parseHeader((InputStream)var1);
      if(var2.isMultipart()) {
         this.bodyDescriptors.addFirst(var2);
         this.handler.startMultipart(var2);
         String var3 = var2.getBoundary();
         MimeBoundaryInputStream var4 = new MimeBoundaryInputStream((InputStream)var1, var3);
         ContentHandler var5 = this.handler;
         CloseShieldInputStream var6 = new CloseShieldInputStream(var4);
         var5.preamble(var6);
         var4.consume();

         while(var4.hasMoreParts()) {
            String var7 = var2.getBoundary();
            var4 = new MimeBoundaryInputStream((InputStream)var1, var7);
            this.parseBodyPart(var4);
            var4.consume();
            if(var4.parentEOF()) {
               if(log.isWarnEnabled()) {
                  Log var8 = log;
                  StringBuilder var9 = (new StringBuilder()).append("Line ");
                  int var10 = this.rootStream.getLineNumber();
                  String var11 = var9.append(var10).append(": Body part ended prematurely. ").append("Higher level boundary detected or ").append("EOF reached.").toString();
                  var8.warn(var11);
               }
               break;
            }
         }

         ContentHandler var12 = this.handler;
         CloseShieldInputStream var13 = new CloseShieldInputStream((InputStream)var1);
         var12.epilogue(var13);
         this.handler.endMultipart();
         Object var14 = this.bodyDescriptors.removeFirst();
      } else if(var2.isMessage()) {
         if(var2.isBase64Encoded()) {
            log.warn("base64 encoded message/rfc822 detected");
            Base64InputStream var15 = new Base64InputStream((InputStream)var1);
            var1 = new EOLConvertingInputStream(var15);
         } else if(var2.isQuotedPrintableEncoded()) {
            log.warn("quoted-printable encoded message/rfc822 detected");
            QuotedPrintableInputStream var17 = new QuotedPrintableInputStream((InputStream)var1);
            var1 = new EOLConvertingInputStream(var17);
         }

         this.bodyDescriptors.addFirst(var2);
         this.parseMessage((InputStream)var1);
         Object var16 = this.bodyDescriptors.removeFirst();
      } else {
         ContentHandler var18 = this.handler;
         CloseShieldInputStream var19 = new CloseShieldInputStream((InputStream)var1);
         var18.body(var2, var19);
      }

      while(((InputStream)var1).read() != -1) {
         ;
      }

   }

   private BodyDescriptor parseHeader(InputStream var1) throws IOException {
      BodyDescriptor var2 = new BodyDescriptor;
      BodyDescriptor var3;
      if(this.bodyDescriptors.isEmpty()) {
         var3 = null;
      } else {
         var3 = (BodyDescriptor)this.bodyDescriptors.getFirst();
      }

      var2.<init>(var3);
      this.handler.startHeader();
      int var4 = this.rootStream.getLineNumber();
      StringBuffer var5 = new StringBuffer();
      byte var6 = 0;

      int var7;
      while(true) {
         var7 = var1.read();
         if(var7 == -1) {
            break;
         }

         if(var7 == 10 && (var6 == 10 || var6 == 0)) {
            int var8 = var5.length() - 1;
            var5.deleteCharAt(var8);
            break;
         }

         char var19 = (char)var7;
         var5.append(var19);
         if(var7 != 13) {
            ;
         }
      }

      if(var7 == -1 && log.isWarnEnabled()) {
         Log var10 = log;
         StringBuilder var11 = (new StringBuilder()).append("Line ");
         int var12 = this.rootStream.getLineNumber();
         String var13 = var11.append(var12).append(": Unexpected end of headers detected. ").append("Boundary detected in header or EOF reached.").toString();
         var10.warn(var13);
      }

      int var14 = 0;
      int var15 = 0;
      int var16 = var4;

      while(true) {
         int var17 = var5.length();
         if(var15 >= var17) {
            this.handler.endHeader();
            return var2;
         }

         while(true) {
            int var18 = var5.length();
            if(var15 >= var18 || var5.charAt(var15) == 13) {
               int var22 = var5.length() - 1;
               if(var15 < var22) {
                  int var23 = var15 + 1;
                  if(var5.charAt(var23) != 10) {
                     ++var15;
                     break;
                  }
               }

               label97: {
                  int var24 = var5.length() - 2;
                  if(var15 < var24) {
                     BitSet var25 = fieldChars;
                     int var26 = var15 + 2;
                     char var27 = var5.charAt(var26);
                     if(!var25.get(var27)) {
                        break label97;
                     }
                  }

                  String var28 = var5.substring(var14, var15);
                  var14 = var15 + 2;
                  int var29 = var28.indexOf(58);
                  boolean var30 = false;
                  if(var29 != -1) {
                     BitSet var31 = fieldChars;
                     char var32 = var28.charAt(0);
                     if(var31.get(var32)) {
                        var30 = true;
                        String var33 = var28.substring(0, var29).trim();
                        int var34 = 0;

                        while(true) {
                           int var35 = var33.length();
                           if(var34 >= var35) {
                              break;
                           }

                           BitSet var36 = fieldChars;
                           char var37 = var33.charAt(var34);
                           if(!var36.get(var37)) {
                              break;
                           }

                           ++var34;
                        }

                        if(var30) {
                           this.handler.field(var28);
                           int var38 = var29 + 1;
                           String var39 = var28.substring(var38);
                           var2.addField(var33, var39);
                        }
                     }
                  }

                  if(!var30 && log.isWarnEnabled()) {
                     Log var40 = log;
                     StringBuilder var41 = (new StringBuilder()).append("Line ").append(var16).append(": Ignoring invalid field: \'");
                     String var42 = var28.trim();
                     String var43 = var41.append(var42).append("\'").toString();
                     var40.warn(var43);
                  }

                  var16 = var4;
               }

               var15 += 2;
               ++var4;
               break;
            }

            ++var15;
         }
      }
   }

   private void parseMessage(InputStream var1) throws IOException {
      if(this.raw) {
         ContentHandler var2 = this.handler;
         CloseShieldInputStream var3 = new CloseShieldInputStream(var1);
         var2.raw(var3);
      } else {
         this.handler.startMessage();
         this.parseEntity(var1);
         this.handler.endMessage();
      }
   }

   public boolean isRaw() {
      return this.raw;
   }

   public void parse(InputStream var1) throws IOException {
      RootInputStream var2 = new RootInputStream(var1);
      this.rootStream = var2;
      RootInputStream var3 = this.rootStream;
      this.parseMessage(var3);
   }

   public void setContentHandler(ContentHandler var1) {
      this.handler = var1;
   }

   public void setRaw(boolean var1) {
      this.raw = var1;
   }

   public void stop() {
      this.rootStream.truncate();
   }
}
