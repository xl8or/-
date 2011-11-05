package com.google.android.common;

import com.google.common.annotations.VisibleForTesting;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import org.xmlpull.v1.XmlSerializer;

public class SafeXmlSerializer implements XmlSerializer {

   private final XmlSerializer serializer;


   public SafeXmlSerializer(XmlSerializer var1) {
      this.serializer = var1;
   }

   @VisibleForTesting
   static String getSafeText(String var0) {
      int var1 = 0;

      while(true) {
         int var2 = var0.length();
         if(var1 >= var2) {
            return var0;
         }

         char var3 = var0.charAt(var1);
         if(var3 < 32 && var3 != 10 && var3 != 9 && var3 != 13) {
            int var4 = var0.length();
            StringBuilder var5 = new StringBuilder(var4);
            var5.append(var0, 0, var1);
            ++var1;

            while(true) {
               int var7 = var0.length();
               if(var1 >= var7) {
                  var0 = var5.toString();
                  return var0;
               }

               var3 = var0.charAt(var1);
               if(var3 >= 32 || var3 == 10 || var3 == 9 || var3 == 13) {
                  var5.append(var3);
               }

               int var9 = var1 + 1;
            }
         }

         ++var1;
      }
   }

   public XmlSerializer attribute(String var1, String var2, String var3) throws IOException, IllegalArgumentException, IllegalStateException {
      XmlSerializer var4 = this.serializer;
      String var5 = getSafeText(var3);
      return var4.attribute(var1, var2, var5);
   }

   public void cdsect(String var1) throws IOException, IllegalArgumentException, IllegalStateException {
      this.serializer.cdsect(var1);
   }

   public void comment(String var1) throws IOException, IllegalArgumentException, IllegalStateException {
      this.serializer.comment(var1);
   }

   public void docdecl(String var1) throws IOException, IllegalArgumentException, IllegalStateException {
      this.serializer.docdecl(var1);
   }

   public void endDocument() throws IOException, IllegalArgumentException, IllegalStateException {
      this.serializer.endDocument();
   }

   public XmlSerializer endTag(String var1, String var2) throws IOException, IllegalArgumentException, IllegalStateException {
      return this.serializer.endTag(var1, var2);
   }

   public void entityRef(String var1) throws IOException, IllegalArgumentException, IllegalStateException {
      this.serializer.entityRef(var1);
   }

   public void flush() throws IOException {
      this.serializer.flush();
   }

   public int getDepth() {
      return this.serializer.getDepth();
   }

   public boolean getFeature(String var1) {
      return this.serializer.getFeature(var1);
   }

   public String getName() {
      return this.serializer.getName();
   }

   public String getNamespace() {
      return this.serializer.getNamespace();
   }

   public String getPrefix(String var1, boolean var2) throws IllegalArgumentException {
      return this.serializer.getPrefix(var1, var2);
   }

   public Object getProperty(String var1) {
      return this.serializer.getProperty(var1);
   }

   public void ignorableWhitespace(String var1) throws IOException, IllegalArgumentException, IllegalStateException {
      this.serializer.ignorableWhitespace(var1);
   }

   public void processingInstruction(String var1) throws IOException, IllegalArgumentException, IllegalStateException {
      this.serializer.processingInstruction(var1);
   }

   public void setFeature(String var1, boolean var2) throws IllegalArgumentException, IllegalStateException {
      this.serializer.setFeature(var1, var2);
   }

   public void setOutput(OutputStream var1, String var2) throws IOException, IllegalArgumentException, IllegalStateException {
      this.serializer.setOutput(var1, var2);
   }

   public void setOutput(Writer var1) throws IOException, IllegalArgumentException, IllegalStateException {
      this.serializer.setOutput(var1);
   }

   public void setPrefix(String var1, String var2) throws IOException, IllegalArgumentException, IllegalStateException {
      this.serializer.setPrefix(var1, var2);
   }

   public void setProperty(String var1, Object var2) throws IllegalArgumentException, IllegalStateException {
      this.serializer.setProperty(var1, var2);
   }

   public void startDocument(String var1, Boolean var2) throws IOException, IllegalArgumentException, IllegalStateException {
      this.serializer.startDocument(var1, var2);
   }

   public XmlSerializer startTag(String var1, String var2) throws IOException, IllegalArgumentException, IllegalStateException {
      return this.serializer.startTag(var1, var2);
   }

   public XmlSerializer text(String var1) throws IOException, IllegalArgumentException, IllegalStateException {
      XmlSerializer var2 = this.serializer;
      String var3 = getSafeText(var1);
      return var2.text(var3);
   }

   public XmlSerializer text(char[] var1, int var2, int var3) throws IOException, IllegalArgumentException, IllegalStateException {
      String var4 = new String(var1, var2, var3);
      return this.text(var4);
   }
}
