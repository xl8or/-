package org.jivesoftware.smack.packet;

import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.XMPPError;
import org.jivesoftware.smack.util.StringUtils;

public class Presence extends Packet {

   private String language;
   private Presence.Mode mode;
   private int priority;
   private String status;
   private Presence.Type type;


   public Presence(Presence.Type var1) {
      Presence.Type var2 = Presence.Type.available;
      this.type = var2;
      this.status = null;
      this.priority = Integer.MIN_VALUE;
      this.mode = null;
      this.setType(var1);
   }

   public Presence(Presence.Type var1, String var2, int var3, Presence.Mode var4) {
      Presence.Type var5 = Presence.Type.available;
      this.type = var5;
      this.status = null;
      this.priority = Integer.MIN_VALUE;
      this.mode = null;
      this.setType(var1);
      this.setStatus(var2);
      this.setPriority(var3);
      this.setMode(var4);
   }

   private String getLanguage() {
      return this.language;
   }

   public Presence.Mode getMode() {
      return this.mode;
   }

   public int getPriority() {
      return this.priority;
   }

   public String getStatus() {
      return this.status;
   }

   public Presence.Type getType() {
      return this.type;
   }

   public boolean isAvailable() {
      Presence.Type var1 = this.type;
      Presence.Type var2 = Presence.Type.available;
      boolean var3;
      if(var1 == var2) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public boolean isAway() {
      Presence.Type var1 = this.type;
      Presence.Type var2 = Presence.Type.available;
      boolean var9;
      if(var1 == var2) {
         label24: {
            Presence.Mode var3 = this.mode;
            Presence.Mode var4 = Presence.Mode.away;
            if(var3 != var4) {
               Presence.Mode var5 = this.mode;
               Presence.Mode var6 = Presence.Mode.xa;
               if(var5 != var6) {
                  Presence.Mode var7 = this.mode;
                  Presence.Mode var8 = Presence.Mode.dnd;
                  if(var7 != var8) {
                     break label24;
                  }
               }
            }

            var9 = true;
            return var9;
         }
      }

      var9 = false;
      return var9;
   }

   public void setLanguage(String var1) {
      this.language = var1;
   }

   public void setMode(Presence.Mode var1) {
      this.mode = var1;
   }

   public void setPriority(int var1) {
      if(var1 >= '\uff80' && var1 <= 128) {
         this.priority = var1;
      } else {
         String var2 = "Priority value " + var1 + " is not valid. Valid range is -128 through 128.";
         throw new IllegalArgumentException(var2);
      }
   }

   public void setStatus(String var1) {
      this.status = var1;
   }

   public void setType(Presence.Type var1) {
      if(var1 == null) {
         throw new NullPointerException("Type cannot be null");
      } else {
         this.type = var1;
      }
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      Presence.Type var2 = this.type;
      var1.append(var2);
      if(this.mode != null) {
         StringBuilder var4 = var1.append(": ");
         Presence.Mode var5 = this.mode;
         var4.append(var5);
      }

      if(this.getStatus() != null) {
         StringBuilder var7 = var1.append(" (");
         String var8 = this.getStatus();
         StringBuilder var9 = var7.append(var8).append(")");
      }

      return var1.toString();
   }

   public String toXML() {
      StringBuilder var1 = new StringBuilder();
      StringBuilder var2 = var1.append("<presence");
      if(this.getXmlns() != null) {
         StringBuilder var3 = var1.append(" xmlns=\"");
         String var4 = this.getXmlns();
         StringBuilder var5 = var3.append(var4).append("\"");
      }

      if(this.language != null) {
         StringBuilder var6 = var1.append(" xml:lang=\"");
         String var7 = this.getLanguage();
         StringBuilder var8 = var6.append(var7).append("\"");
      }

      if(this.getPacketID() != null) {
         StringBuilder var9 = var1.append(" id=\"");
         String var10 = this.getPacketID();
         StringBuilder var11 = var9.append(var10).append("\"");
      }

      if(this.getTo() != null) {
         StringBuilder var12 = var1.append(" to=\"");
         String var13 = StringUtils.escapeForXML(this.getTo());
         StringBuilder var14 = var12.append(var13).append("\"");
      }

      if(this.getFrom() != null) {
         StringBuilder var15 = var1.append(" from=\"");
         String var16 = StringUtils.escapeForXML(this.getFrom());
         StringBuilder var17 = var15.append(var16).append("\"");
      }

      Presence.Type var18 = this.type;
      Presence.Type var19 = Presence.Type.available;
      if(var18 != var19) {
         StringBuilder var20 = var1.append(" type=\"");
         Presence.Type var21 = this.type;
         StringBuilder var22 = var20.append(var21).append("\"");
      }

      StringBuilder var23 = var1.append(">");
      if(this.status != null) {
         StringBuilder var24 = var1.append("<status>");
         String var25 = StringUtils.escapeForXML(this.status);
         StringBuilder var26 = var24.append(var25).append("</status>");
      }

      if(this.priority != Integer.MIN_VALUE) {
         StringBuilder var27 = var1.append("<priority>");
         int var28 = this.priority;
         StringBuilder var29 = var27.append(var28).append("</priority>");
      }

      if(this.mode != null) {
         Presence.Mode var30 = this.mode;
         Presence.Mode var31 = Presence.Mode.available;
         if(var30 != var31) {
            StringBuilder var32 = var1.append("<show>");
            Presence.Mode var33 = this.mode;
            StringBuilder var34 = var32.append(var33).append("</show>");
         }
      }

      String var35 = this.getExtensionsXML();
      var1.append(var35);
      XMPPError var37 = this.getError();
      if(var37 != null) {
         String var38 = var37.toXML();
         var1.append(var38);
      }

      StringBuilder var40 = var1.append("</presence>");
      return var1.toString();
   }

   public static enum Type {

      // $FF: synthetic field
      private static final Presence.Type[] $VALUES;
      available("available", 0),
      error("error", 6),
      subscribe("subscribe", 2),
      subscribed("subscribed", 3),
      unavailable("unavailable", 1),
      unsubscribe("unsubscribe", 4),
      unsubscribed("unsubscribed", 5);


      static {
         Presence.Type[] var0 = new Presence.Type[7];
         Presence.Type var1 = available;
         var0[0] = var1;
         Presence.Type var2 = unavailable;
         var0[1] = var2;
         Presence.Type var3 = subscribe;
         var0[2] = var3;
         Presence.Type var4 = subscribed;
         var0[3] = var4;
         Presence.Type var5 = unsubscribe;
         var0[4] = var5;
         Presence.Type var6 = unsubscribed;
         var0[5] = var6;
         Presence.Type var7 = error;
         var0[6] = var7;
         $VALUES = var0;
      }

      private Type(String var1, int var2) {}
   }

   public static enum Mode {

      // $FF: synthetic field
      private static final Presence.Mode[] $VALUES;
      available("available", 1),
      away("away", 2),
      chat("chat", 0),
      dnd("dnd", 4),
      xa("xa", 3);


      static {
         Presence.Mode[] var0 = new Presence.Mode[5];
         Presence.Mode var1 = chat;
         var0[0] = var1;
         Presence.Mode var2 = available;
         var0[1] = var2;
         Presence.Mode var3 = away;
         var0[2] = var3;
         Presence.Mode var4 = xa;
         var0[3] = var4;
         Presence.Mode var5 = dnd;
         var0[4] = var5;
         $VALUES = var0;
      }

      private Mode(String var1, int var2) {}
   }
}
