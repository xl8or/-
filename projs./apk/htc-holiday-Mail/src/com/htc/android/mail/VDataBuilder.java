package com.htc.android.mail;

import android.content.ContentValues;
import com.htc.android.mail.PropertyNode;
import com.htc.android.mail.VBuilder;
import com.htc.android.mail.VNode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.apache.commons.codec.net.QuotedPrintableCodec;

public class VDataBuilder implements VBuilder {

   String curParamType;
   PropertyNode curPropNode;
   VNode curVNode;
   int nodeListPos;
   public ArrayList<VNode> vNodeList;


   public VDataBuilder() {
      ArrayList var1 = new ArrayList();
      this.vNodeList = var1;
      this.nodeListPos = 0;
   }

   private String listToString(Collection<String> var1) {
      StringBuilder var2 = new StringBuilder();

      String var4;
      StringBuilder var5;
      for(Iterator var3 = var1.iterator(); var3.hasNext(); var5 = var2.append(var4).append(";")) {
         var4 = (String)var3.next();
      }

      int var6 = var2.length();
      String var9;
      if(var6 > 0) {
         int var7 = var6 - 1;
         if(var2.charAt(var7) == 59) {
            int var8 = var6 - 1;
            var9 = var2.substring(0, var8);
            return var9;
         }
      }

      var9 = var2.toString();
      return var9;
   }

   public void end() {}

   public void endProperty() {}

   public void endRecord() {
      ArrayList var1 = this.vNodeList;
      int var2 = this.nodeListPos;
      ((VNode)var1.get(var2)).parseStatus = 0;

      while(this.nodeListPos > 0) {
         int var3 = this.nodeListPos - 1;
         this.nodeListPos = var3;
         ArrayList var4 = this.vNodeList;
         int var5 = this.nodeListPos;
         if(((VNode)var4.get(var5)).parseStatus == 1) {
            break;
         }
      }

      ArrayList var6 = this.vNodeList;
      int var7 = this.nodeListPos;
      VNode var8 = (VNode)var6.get(var7);
      this.curVNode = var8;
   }

   public String getResult() {
      return null;
   }

   public void propertyName(String var1) {
      PropertyNode var2 = new PropertyNode();
      this.curPropNode = var2;
      this.curPropNode.propName = var1;
   }

   public void propertyParamType(String var1) {
      this.curParamType = var1;
   }

   public void propertyParamValue(String var1) {
      if(this.curParamType == null) {
         this.curPropNode.paraMap_TYPE.add(var1);
      } else if(this.curParamType.equalsIgnoreCase("TYPE")) {
         this.curPropNode.paraMap_TYPE.add(var1);
      } else {
         ContentValues var4 = this.curPropNode.paraMap;
         String var5 = this.curParamType;
         var4.put(var5, var1);
      }

      this.curParamType = null;
   }

   public void propertyValues(Collection<String> var1) {
      this.curPropNode.propValue_vector = var1;
      PropertyNode var2 = this.curPropNode;
      String var3 = this.listToString(var1);
      var2.propValue = var3;
      if(this.curPropNode.paraMap.containsKey("ENCODING")) {
         if(this.curPropNode.paraMap.getAsString("ENCODING").equalsIgnoreCase("BASE64")) {
            PropertyNode var4 = this.curPropNode;
            byte[] var5 = org.apache.commons.codec.binary.Base64.decodeBase64(this.curPropNode.propValue.replaceAll(" ", "").replaceAll("\t", "").replaceAll("\r\n", "").getBytes());
            var4.propValue_byts = var5;
         }

         if(this.curPropNode.paraMap.getAsString("ENCODING").equalsIgnoreCase("QUOTED-PRINTABLE")) {
            try {
               PropertyNode var6 = this.curPropNode;
               byte[] var7 = QuotedPrintableCodec.decodeQuotedPrintable(this.curPropNode.propValue.replaceAll("= ", " ").replaceAll("=\t", "\t").getBytes());
               var6.propValue_byts = var7;
               PropertyNode var8 = this.curPropNode;
               byte[] var9 = this.curPropNode.propValue_byts;
               String var10 = new String(var9);
               var8.propValue = var10;
            } catch (Exception var15) {
               System.out.println("=Decode quoted-printable exception.");
               var15.printStackTrace();
            }
         }
      }

      ArrayList var11 = this.curVNode.propList;
      PropertyNode var12 = this.curPropNode;
      var11.add(var12);
   }

   public void start() {}

   public void startProperty() {}

   public void startRecord(String var1) {
      VNode var2 = new VNode();
      var2.parseStatus = 1;
      var2.VName = var1;
      this.vNodeList.add(var2);
      int var4 = this.vNodeList.size() - 1;
      this.nodeListPos = var4;
      ArrayList var5 = this.vNodeList;
      int var6 = this.nodeListPos;
      VNode var7 = (VNode)var5.get(var6);
      this.curVNode = var7;
   }
}
