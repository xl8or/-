package com.kenai.jbosh;

import com.kenai.jbosh.AbstractBody;
import com.kenai.jbosh.AttrAccept;
import com.kenai.jbosh.AttrAck;
import com.kenai.jbosh.AttrCharsets;
import com.kenai.jbosh.AttrHold;
import com.kenai.jbosh.AttrInactivity;
import com.kenai.jbosh.AttrMaxPause;
import com.kenai.jbosh.AttrPolling;
import com.kenai.jbosh.AttrRequests;
import com.kenai.jbosh.AttrSessionID;
import com.kenai.jbosh.AttrVersion;
import com.kenai.jbosh.AttrWait;
import com.kenai.jbosh.Attributes;
import com.kenai.jbosh.BOSHException;
import com.kenai.jbosh.BodyQName;

final class CMSessionParams {

   private final AttrAccept accept;
   private final AttrAck ack;
   private final boolean ackingRequests;
   private final AttrCharsets charsets;
   private final AttrHold hold;
   private final AttrInactivity inactivity;
   private final AttrMaxPause maxPause;
   private final AttrPolling polling;
   private final AttrRequests requests;
   private final AttrSessionID sid;
   private final AttrVersion ver;
   private final AttrWait wait;


   private CMSessionParams(AttrSessionID var1, AttrWait var2, AttrVersion var3, AttrPolling var4, AttrInactivity var5, AttrRequests var6, AttrHold var7, AttrAccept var8, AttrMaxPause var9, AttrAck var10, AttrCharsets var11, boolean var12) {
      this.sid = var1;
      this.wait = var2;
      this.ver = var3;
      this.polling = var4;
      this.inactivity = var5;
      this.requests = var6;
      this.hold = var7;
      this.accept = var8;
      this.maxPause = var9;
      this.ack = var10;
      this.charsets = var11;
      this.ackingRequests = var12;
   }

   static CMSessionParams fromSessionInit(AbstractBody var0, AbstractBody var1) throws BOSHException {
      BodyQName var2 = Attributes.ACK;
      AttrAck var3 = AttrAck.createFromString(var1.getAttribute(var2));
      BodyQName var4 = Attributes.RID;
      String var5 = var0.getAttribute(var4);
      byte var6;
      if(var3 != null && ((String)var3.getValue()).equals(var5)) {
         var6 = 1;
      } else {
         var6 = 0;
      }

      BodyQName var7 = Attributes.SID;
      AttrSessionID var8 = AttrSessionID.createFromString(getRequiredAttribute(var1, var7));
      BodyQName var9 = Attributes.WAIT;
      AttrWait var10 = AttrWait.createFromString(getRequiredAttribute(var1, var9));
      BodyQName var11 = Attributes.VER;
      AttrVersion var12 = AttrVersion.createFromString(var1.getAttribute(var11));
      BodyQName var13 = Attributes.POLLING;
      AttrPolling var14 = AttrPolling.createFromString(var1.getAttribute(var13));
      BodyQName var15 = Attributes.INACTIVITY;
      AttrInactivity var16 = AttrInactivity.createFromString(var1.getAttribute(var15));
      BodyQName var17 = Attributes.REQUESTS;
      AttrRequests var18 = AttrRequests.createFromString(var1.getAttribute(var17));
      BodyQName var19 = Attributes.HOLD;
      AttrHold var20 = AttrHold.createFromString(var1.getAttribute(var19));
      BodyQName var21 = Attributes.ACCEPT;
      AttrAccept var22 = AttrAccept.createFromString(var1.getAttribute(var21));
      BodyQName var23 = Attributes.MAXPAUSE;
      AttrMaxPause var24 = AttrMaxPause.createFromString(var1.getAttribute(var23));
      BodyQName var25 = Attributes.CHARSETS;
      AttrCharsets var26 = AttrCharsets.createFromString(var1.getAttribute(var25));
      return new CMSessionParams(var8, var10, var12, var14, var16, var18, var20, var22, var24, var3, var26, (boolean)var6);
   }

   private static String getRequiredAttribute(AbstractBody var0, BodyQName var1) throws BOSHException {
      String var2 = var0.getAttribute(var1);
      if(var2 == null) {
         StringBuilder var3 = (new StringBuilder()).append("Connection Manager session creation response did not include required \'");
         String var4 = var1.getLocalPart();
         String var5 = var3.append(var4).append("\' attribute").toString();
         throw new BOSHException(var5);
      } else {
         return var2;
      }
   }

   AttrAccept getAccept() {
      return this.accept;
   }

   AttrAck getAck() {
      return this.ack;
   }

   AttrCharsets getCharsets() {
      return this.charsets;
   }

   AttrHold getHold() {
      return this.hold;
   }

   AttrInactivity getInactivityPeriod() {
      return this.inactivity;
   }

   AttrMaxPause getMaxPause() {
      return this.maxPause;
   }

   AttrPolling getPollingInterval() {
      return this.polling;
   }

   AttrRequests getRequests() {
      return this.requests;
   }

   AttrSessionID getSessionID() {
      return this.sid;
   }

   AttrVersion getVersion() {
      return this.ver;
   }

   AttrWait getWait() {
      return this.wait;
   }

   boolean isAckingRequests() {
      return this.ackingRequests;
   }
}
