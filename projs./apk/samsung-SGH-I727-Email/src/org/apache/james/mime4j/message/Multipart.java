package org.apache.james.mime4j.message;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.james.mime4j.field.ContentTypeField;
import org.apache.james.mime4j.message.Body;
import org.apache.james.mime4j.message.BodyPart;
import org.apache.james.mime4j.message.Entity;
import org.apache.james.mime4j.util.CharsetUtil;

public class Multipart implements Body {

   private List bodyParts;
   private String epilogue = "";
   private Entity parent;
   private String preamble = "";
   private String subType;


   public Multipart() {
      LinkedList var1 = new LinkedList();
      this.bodyParts = var1;
      this.parent = null;
      this.subType = "alternative";
   }

   private String getBoundary() {
      return ((ContentTypeField)this.getParent().getHeader().getField("Content-Type")).getBoundary();
   }

   private String getCharset() {
      return ((ContentTypeField)this.getParent().getHeader().getField("Content-Type")).getCharset();
   }

   public void addBodyPart(BodyPart var1) {
      this.bodyParts.add(var1);
      Entity var3 = this.parent;
      var1.setParent(var3);
   }

   public List getBodyParts() {
      return Collections.unmodifiableList(this.bodyParts);
   }

   public String getEpilogue() {
      return this.epilogue;
   }

   public Entity getParent() {
      return this.parent;
   }

   public String getPreamble() {
      return this.preamble;
   }

   public String getSubType() {
      return this.subType;
   }

   public void setBodyParts(List var1) {
      this.bodyParts = var1;
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         BodyPart var3 = (BodyPart)var2.next();
         Entity var4 = this.parent;
         var3.setParent(var4);
      }

   }

   public void setEpilogue(String var1) {
      this.epilogue = var1;
   }

   public void setParent(Entity var1) {
      this.parent = var1;
      Iterator var2 = this.bodyParts.iterator();

      while(var2.hasNext()) {
         ((BodyPart)var2.next()).setParent(var1);
      }

   }

   public void setPreamble(String var1) {
      this.preamble = var1;
   }

   public void setSubType(String var1) {
      this.subType = var1;
   }

   public void writeTo(OutputStream var1) throws IOException {
      String var2 = this.getBoundary();
      List var3 = this.getBodyParts();
      StringBuffer var4 = new StringBuffer();
      Charset var5 = CharsetUtil.getCharset(this.getCharset());
      OutputStreamWriter var6 = new OutputStreamWriter(var1, var5);
      BufferedWriter var7 = new BufferedWriter(var6, 8192);
      StringBuilder var8 = new StringBuilder();
      String var9 = this.getPreamble();
      String var10 = var8.append(var9).append("\r\n").toString();
      var7.write(var10);
      int var11 = 0;

      while(true) {
         int var12 = var3.size();
         if(var11 >= var12) {
            StringBuilder var17 = new StringBuilder();
            String var18 = this.getEpilogue();
            String var19 = var17.append(var18).append("\r\n").toString();
            var7.write(var19);
            String var20 = var2 + "--" + "\r\n";
            var7.write(var20);
            return;
         }

         StringBuffer var13 = var4.append(var2).append("\r\n");
         String var14 = var4.toString();
         var7.write(var14);
         int var15 = var4.length();
         var4.delete(0, var15);
         ((BodyPart)var3.get(var11)).writeTo(var1);
         ++var11;
      }
   }
}
