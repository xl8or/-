package org.apache.james.mime4j.message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Stack;
import org.apache.james.mime4j.BodyDescriptor;
import org.apache.james.mime4j.ContentHandler;
import org.apache.james.mime4j.MimeStreamParser;
import org.apache.james.mime4j.decoder.Base64InputStream;
import org.apache.james.mime4j.decoder.QuotedPrintableInputStream;
import org.apache.james.mime4j.field.Field;
import org.apache.james.mime4j.field.UnstructuredField;
import org.apache.james.mime4j.message.Body;
import org.apache.james.mime4j.message.BodyPart;
import org.apache.james.mime4j.message.Entity;
import org.apache.james.mime4j.message.Header;
import org.apache.james.mime4j.message.MemoryBinaryBody;
import org.apache.james.mime4j.message.MemoryTextBody;
import org.apache.james.mime4j.message.Multipart;

public class Message extends Entity implements Body {

   public Message() {}

   public Message(InputStream var1) throws IOException {
      MimeStreamParser var2 = new MimeStreamParser();
      Message.MessageBuilder var3 = new Message.MessageBuilder();
      var2.setContentHandler(var3);
      var2.parse(var1);
   }

   public UnstructuredField getSubject() {
      return (UnstructuredField)this.getHeader().getField("Subject");
   }

   public void writeTo(OutputStream var1) throws IOException {
      this.getHeader().writeTo(var1);
      Body var2 = this.getBody();
      if(var2 instanceof Multipart) {
         ((Multipart)var2).writeTo(var1);
      } else {
         var2.writeTo(var1);
      }
   }

   private class MessageBuilder implements ContentHandler {

      private Stack stack;


      public MessageBuilder() {
         Stack var2 = new Stack();
         this.stack = var2;
      }

      private void expect(Class var1) {
         Object var2 = this.stack.peek();
         if(!var1.isInstance(var2)) {
            StringBuilder var3 = (new StringBuilder()).append("Internal stack error: Expected \'");
            String var4 = var1.getName();
            StringBuilder var5 = var3.append(var4).append("\' found \'");
            String var6 = this.stack.peek().getClass().getName();
            String var7 = var5.append(var6).append("\'").toString();
            throw new IllegalStateException(var7);
         }
      }

      public void body(BodyDescriptor var1, InputStream var2) throws IOException {
         this.expect(Entity.class);
         String var3 = var1.getTransferEncoding();
         if("base64".equals(var3)) {
            var2 = new Base64InputStream((InputStream)var2);
         } else if("quoted-printable".equals(var3)) {
            var2 = new QuotedPrintableInputStream((InputStream)var2);
         }

         Object var5;
         if(var1.getMimeType().startsWith("text/")) {
            String var4 = var1.getCharset();
            var5 = new MemoryTextBody((InputStream)var2, var4);
         } else {
            var5 = new MemoryBinaryBody((InputStream)var2);
         }

         ((Entity)this.stack.peek()).setBody((Body)var5);
      }

      public void endBodyPart() {
         this.expect(BodyPart.class);
         Object var1 = this.stack.pop();
      }

      public void endHeader() {
         this.expect(Header.class);
         Header var1 = (Header)this.stack.pop();
         this.expect(Entity.class);
         ((Entity)this.stack.peek()).setHeader(var1);
      }

      public void endMessage() {
         this.expect(Message.class);
         Object var1 = this.stack.pop();
      }

      public void endMultipart() {
         Object var1 = this.stack.pop();
      }

      public void epilogue(InputStream var1) throws IOException {
         this.expect(Multipart.class);
         StringBuffer var2 = new StringBuffer();

         while(true) {
            int var3 = var1.read();
            if(var3 == -1) {
               Multipart var6 = (Multipart)this.stack.peek();
               String var7 = var2.toString();
               var6.setEpilogue(var7);
               return;
            }

            char var4 = (char)var3;
            var2.append(var4);
         }
      }

      public void field(String var1) {
         this.expect(Header.class);
         Header var2 = (Header)this.stack.peek();
         Field var3 = Field.parse(var1);
         var2.addField(var3);
      }

      public void preamble(InputStream var1) throws IOException {
         this.expect(Multipart.class);
         StringBuffer var2 = new StringBuffer();

         while(true) {
            int var3 = var1.read();
            if(var3 == -1) {
               Multipart var6 = (Multipart)this.stack.peek();
               String var7 = var2.toString();
               var6.setPreamble(var7);
               return;
            }

            char var4 = (char)var3;
            var2.append(var4);
         }
      }

      public void raw(InputStream var1) throws IOException {
         throw new UnsupportedOperationException("Not supported");
      }

      public void startBodyPart() {
         this.expect(Multipart.class);
         BodyPart var1 = new BodyPart();
         ((Multipart)this.stack.peek()).addBodyPart(var1);
         this.stack.push(var1);
      }

      public void startHeader() {
         Stack var1 = this.stack;
         Header var2 = new Header();
         var1.push(var2);
      }

      public void startMessage() {
         if(this.stack.isEmpty()) {
            Stack var1 = this.stack;
            Message var2 = Message.this;
            var1.push(var2);
         } else {
            this.expect(Entity.class);
            Message var4 = new Message();
            ((Entity)this.stack.peek()).setBody(var4);
            this.stack.push(var4);
         }
      }

      public void startMultipart(BodyDescriptor var1) {
         this.expect(Entity.class);
         Entity var2 = (Entity)this.stack.peek();
         Multipart var3 = new Multipart();
         var2.setBody(var3);
         this.stack.push(var3);
      }
   }
}
