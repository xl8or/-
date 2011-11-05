package org.jivesoftware.smack.packet;

import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.XMPPError;
import org.jivesoftware.smack.util.StringUtils;

public abstract class IQ extends Packet {

   private IQ.Type type;


   public IQ() {
      IQ.Type var1 = IQ.Type.GET;
      this.type = var1;
   }

   public static IQ createErrorResponse(IQ var0, XMPPError var1) {
      IQ.Type var2 = var0.getType();
      IQ.Type var3 = IQ.Type.GET;
      if(var2 != var3) {
         IQ.Type var4 = var0.getType();
         IQ.Type var5 = IQ.Type.SET;
         if(var4 != var5) {
            StringBuilder var6 = (new StringBuilder()).append("IQ must be of type \'set\' or \'get\'. Original IQ: ");
            String var7 = var0.toXML();
            String var8 = var6.append(var7).toString();
            throw new IllegalArgumentException(var8);
         }
      }

      IQ.2 var9 = new IQ.2(var0);
      IQ.Type var10 = IQ.Type.ERROR;
      var9.setType(var10);
      String var11 = var0.getPacketID();
      var9.setPacketID(var11);
      String var12 = var0.getTo();
      var9.setFrom(var12);
      String var13 = var0.getFrom();
      var9.setTo(var13);
      var9.setError(var1);
      return var9;
   }

   public static IQ createResultIQ(IQ var0) {
      IQ.Type var1 = var0.getType();
      IQ.Type var2 = IQ.Type.GET;
      if(var1 != var2) {
         IQ.Type var3 = var0.getType();
         IQ.Type var4 = IQ.Type.SET;
         if(var3 != var4) {
            StringBuilder var5 = (new StringBuilder()).append("IQ must be of type \'set\' or \'get\'. Original IQ: ");
            String var6 = var0.toXML();
            String var7 = var5.append(var6).toString();
            throw new IllegalArgumentException(var7);
         }
      }

      IQ.1 var8 = new IQ.1();
      IQ.Type var9 = IQ.Type.RESULT;
      var8.setType(var9);
      String var10 = var0.getPacketID();
      var8.setPacketID(var10);
      String var11 = var0.getTo();
      var8.setFrom(var11);
      String var12 = var0.getFrom();
      var8.setTo(var12);
      return var8;
   }

   public abstract String getChildElementXML();

   public IQ.Type getType() {
      return this.type;
   }

   public void setType(IQ.Type var1) {
      if(var1 == null) {
         IQ.Type var2 = IQ.Type.GET;
         this.type = var2;
      } else {
         this.type = var1;
      }
   }

   public String toXML() {
      StringBuilder var1 = new StringBuilder();
      StringBuilder var2 = var1.append("<iq ");
      if(this.getPacketID() != null) {
         StringBuilder var3 = (new StringBuilder()).append("id=\"");
         String var4 = this.getPacketID();
         String var5 = var3.append(var4).append("\" ").toString();
         var1.append(var5);
      }

      if(this.getTo() != null) {
         StringBuilder var7 = var1.append("to=\"");
         String var8 = StringUtils.escapeForXML(this.getTo());
         StringBuilder var9 = var7.append(var8).append("\" ");
      }

      if(this.getFrom() != null) {
         StringBuilder var10 = var1.append("from=\"");
         String var11 = StringUtils.escapeForXML(this.getFrom());
         StringBuilder var12 = var10.append(var11).append("\" ");
      }

      if(this.type == null) {
         StringBuilder var13 = var1.append("type=\"get\">");
      } else {
         StringBuilder var20 = var1.append("type=\"");
         IQ.Type var21 = this.getType();
         StringBuilder var22 = var20.append(var21).append("\">");
      }

      String var14 = this.getChildElementXML();
      if(var14 != null) {
         var1.append(var14);
      }

      XMPPError var16 = this.getError();
      if(var16 != null) {
         String var17 = var16.toXML();
         var1.append(var17);
      }

      StringBuilder var19 = var1.append("</iq>");
      return var1.toString();
   }

   static class 1 extends IQ {

      1() {}

      public String getChildElementXML() {
         return null;
      }
   }

   static class 2 extends IQ {

      // $FF: synthetic field
      final IQ val$request;


      2(IQ var1) {
         this.val$request = var1;
      }

      public String getChildElementXML() {
         return this.val$request.getChildElementXML();
      }
   }

   public static class Type {

      public static final IQ.Type ERROR = new IQ.Type("error");
      public static final IQ.Type GET = new IQ.Type("get");
      public static final IQ.Type RESULT = new IQ.Type("result");
      public static final IQ.Type SET = new IQ.Type("set");
      private String value;


      private Type(String var1) {
         this.value = var1;
      }

      public static IQ.Type fromString(String var0) {
         IQ.Type var1;
         if(var0 == null) {
            var1 = null;
         } else {
            String var2 = var0.toLowerCase();
            if(GET.toString().equals(var2)) {
               var1 = GET;
            } else if(SET.toString().equals(var2)) {
               var1 = SET;
            } else if(ERROR.toString().equals(var2)) {
               var1 = ERROR;
            } else if(RESULT.toString().equals(var2)) {
               var1 = RESULT;
            } else {
               var1 = null;
            }
         }

         return var1;
      }

      public String toString() {
         return this.value;
      }
   }
}
