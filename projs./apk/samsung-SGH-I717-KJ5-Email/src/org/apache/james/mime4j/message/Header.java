package org.apache.james.mime4j.message;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.james.mime4j.AbstractContentHandler;
import org.apache.james.mime4j.MimeStreamParser;
import org.apache.james.mime4j.field.ContentTypeField;
import org.apache.james.mime4j.field.Field;
import org.apache.james.mime4j.util.CharsetUtil;

public class Header {

   private HashMap fieldMap;
   private List fields;


   public Header() {
      LinkedList var1 = new LinkedList();
      this.fields = var1;
      HashMap var2 = new HashMap();
      this.fieldMap = var2;
   }

   public Header(InputStream var1) throws IOException {
      LinkedList var2 = new LinkedList();
      this.fields = var2;
      HashMap var3 = new HashMap();
      this.fieldMap = var3;
      MimeStreamParser var4 = new MimeStreamParser();
      Header.1 var5 = new Header.1(var4);
      var4.setContentHandler(var5);
      var4.parse(var1);
   }

   public void addField(Field var1) {
      HashMap var2 = this.fieldMap;
      String var3 = var1.getName().toLowerCase();
      Object var4 = (List)var2.get(var3);
      if(var4 == null) {
         var4 = new LinkedList();
         HashMap var5 = this.fieldMap;
         String var6 = var1.getName().toLowerCase();
         var5.put(var6, var4);
      }

      ((List)var4).add(var1);
      this.fields.add(var1);
   }

   public Field getField(String var1) {
      HashMap var2 = this.fieldMap;
      String var3 = var1.toLowerCase();
      List var4 = (List)var2.get(var3);
      Field var5;
      if(var4 != null && !var4.isEmpty()) {
         var5 = (Field)var4.get(0);
      } else {
         var5 = null;
      }

      return var5;
   }

   public List getFields() {
      return Collections.unmodifiableList(this.fields);
   }

   public List getFields(String var1) {
      HashMap var2 = this.fieldMap;
      String var3 = var1.toLowerCase();
      return Collections.unmodifiableList((List)var2.get(var3));
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();

      StringBuffer var5;
      for(Iterator var2 = this.fields.iterator(); var2.hasNext(); var5 = var1.append("\r\n")) {
         String var3 = var2.next().toString();
         var1.append(var3);
      }

      return var1.toString();
   }

   public void writeTo(OutputStream var1) throws IOException {
      Charset var2 = CharsetUtil.getCharset(((ContentTypeField)this.getField("Content-Type")).getCharset());
      OutputStreamWriter var3 = new OutputStreamWriter(var1, var2);
      BufferedWriter var4 = new BufferedWriter(var3, 8192);
      StringBuilder var5 = new StringBuilder();
      String var6 = this.toString();
      String var7 = var5.append(var6).append("\r\n").toString();
      var4.write(var7);
      var4.flush();
   }

   class 1 extends AbstractContentHandler {

      // $FF: synthetic field
      final MimeStreamParser val$parser;


      1(MimeStreamParser var2) {
         this.val$parser = var2;
      }

      public void endHeader() {
         this.val$parser.stop();
      }

      public void field(String var1) {
         Header var2 = Header.this;
         Field var3 = Field.parse(var1);
         var2.addField(var3);
      }
   }
}
