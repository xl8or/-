package com.htc.android.mail.pim;

import android.content.ContentValues;
import android.util.Log;
import com.htc.android.mail.pim.PropertyNode;
import com.htc.android.mail.pim.VBuilder;
import com.htc.android.mail.pim.VNode;
import java.io.RandomAccessFile;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.net.QuotedPrintableCodec;

public class VDataBuilder implements VBuilder {

   private static final String TAG = "VDataBuilder";
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

   private static String byte2String(byte[] var0) {
      char[] var1 = new char[var0.length];
      int var2 = 0;

      while(true) {
         int var3 = var1.length;
         if(var2 >= var3) {
            CharBuffer var5 = CharBuffer.wrap(var1);
            int var6 = var5.length();
            StringBuffer var7 = new StringBuffer(var6);
            var7.append(var5);
            return var7.toString();
         }

         char var4 = (char)var0[var2];
         var1[var2] = var4;
         ++var2;
      }
   }

   public static byte[] getBytes(String var0) {
      char[] var1 = var0.toCharArray();
      byte[] var2 = new byte[var1.length];
      int var3 = 0;

      while(true) {
         int var4 = var1.length;
         if(var3 >= var4) {
            return var2;
         }

         byte var5 = (byte)var1[var3];
         var2[var3] = var5;
         ++var3;
      }
   }

   public static String getRawString(RandomAccessFile var0, long var1, long var3) {
      String var5;
      if(var3 - var1 == 0L) {
         var5 = null;
      } else {
         int var6 = (int)(var3 - var1);

         String var12;
         try {
            byte[] var7 = new byte[var6];
            var0.seek(var1);
            var0.read(var7);
            boolean var9 = false;
            int var10 = (new String(var7, "UTF-8")).getBytes("UTF-8").length;
            int var11 = var7.length;
            if(var10 == var11) {
               var9 = true;
            }

            if(var9) {
               var5 = new String(var7, "UTF-8");
               return var5;
            }

            var12 = byte2String(var7);
         } catch (Exception var13) {
            var13.printStackTrace();
            var5 = "";
            return var5;
         }

         var5 = var12;
      }

      return var5;
   }

   public static String getRawString(String param0) {
      // $FF: Couldn't be decompiled
   }

   private static boolean isUTF8(byte[] var0) {
      int var1 = 0;
      int var2 = 0;
      int var3 = 1;

      while(true) {
         int var4 = var0.length;
         if(var3 >= var4) {
            boolean var8;
            if(var1 > var2) {
               var8 = true;
            } else {
               var8 = false;
            }

            return var8;
         }

         byte var5 = var0[var3];
         int var6 = var3 - 1;
         byte var7 = var0[var6];
         if((var5 & 192) == 128) {
            if((var7 & 192) == 192) {
               ++var1;
            } else if((var7 & 128) == 0) {
               ++var2;
            }
         } else if((var7 & 192) == 192) {
            ++var2;
         }

         ++var3;
      }
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
            byte[] var5 = Base64.decodeBase64(this.curPropNode.propValue.replaceAll(" ", "").replaceAll("\t", "").replaceAll("\r\n", "").getBytes());
            var4.propValue_byts = var5;
         }

         if(this.curPropNode.paraMap.getAsString("ENCODING").equalsIgnoreCase("QUOTED-PRINTABLE")) {
            try {
               PropertyNode var6 = this.curPropNode;
               byte[] var7 = QuotedPrintableCodec.decodeQuotedPrintable(this.curPropNode.propValue.replaceAll("= ", " ").replaceAll("=\t", "\t").replaceAll("=\r\n", "").getBytes());
               var6.propValue_byts = var7;
               if(this.curPropNode.paraMap.containsKey("CHARSET")) {
                  String var8 = this.curPropNode.paraMap.getAsString("CHARSET");
                  PropertyNode var9 = this.curPropNode;
                  byte[] var10 = this.curPropNode.propValue_byts;
                  String var11 = new String(var10, var8);
                  var9.propValue = var11;
               } else {
                  PropertyNode var15 = this.curPropNode;
                  byte[] var16 = this.curPropNode.propValue_byts;
                  String var17 = new String(var16);
                  var15.propValue = var17;
               }
            } catch (Exception var38) {
               System.out.println("=Decode quoted-printable exception.");
               StringBuilder var19 = (new StringBuilder()).append("Error in get QUOTED-PRINTABLE: ");
               String var20 = var38.getMessage();
               String var21 = var19.append(var20).toString();
               int var22 = Log.d("VDataBuilder", var21);
            }
         }
      } else if(this.curPropNode.paraMap.containsKey("CHARSET")) {
         String var23 = this.curPropNode.paraMap.getAsString("CHARSET");

         try {
            PropertyNode var24 = this.curPropNode;
            byte[] var25 = getBytes(this.curPropNode.propValue);
            String var26 = new String(var25, var23);
            var24.propValue = var26;
         } catch (Exception var37) {
            ;
         }

         ArrayList var27 = new ArrayList();
         Iterator var28 = this.curPropNode.propValue_vector.iterator();

         while(var28.hasNext()) {
            String var29 = (String)var28.next();

            try {
               byte[] var30 = getBytes(var29);
               String var31 = new String(var30, var23);
               var27.add(var31);
            } catch (Exception var36) {
               var27.add(var29);
            }
         }

         this.curPropNode.propValue_vector = var27;
      }

      ArrayList var12 = this.curVNode.propList;
      PropertyNode var13 = this.curPropNode;
      var12.add(var13);
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
