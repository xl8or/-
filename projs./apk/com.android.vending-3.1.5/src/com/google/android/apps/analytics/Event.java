package com.google.android.apps.analytics;

import com.google.android.apps.analytics.CustomVariableBuffer;

class Event {

   static final String INSTALL_EVENT_CATEGORY = "__##GOOGLEINSTALL##__";
   static final String PAGEVIEW_EVENT_CATEGORY = "__##GOOGLEPAGEVIEW##__";
   final String accountId;
   final String action;
   final String category;
   CustomVariableBuffer customVariableBuffer;
   final long eventId;
   final String label;
   final int randomVal;
   final int screenHeight;
   final int screenWidth;
   final int timestampCurrent;
   final int timestampFirst;
   final int timestampPrevious;
   final int userId;
   final int value;
   final int visits;


   Event(int var1, String var2, String var3, String var4, String var5, int var6, int var7, int var8) {
      this(65535L, var1, var2, -1, -1, -1, -1, -1, var3, var4, var5, var6, var7, var8);
   }

   Event(long var1, int var3, String var4, int var5, int var6, int var7, int var8, int var9, String var10, String var11, String var12, int var13, int var14, int var15) {
      this.eventId = var1;
      this.userId = var3;
      this.accountId = var4;
      this.randomVal = var5;
      this.timestampFirst = var6;
      this.timestampPrevious = var7;
      this.timestampCurrent = var8;
      this.visits = var9;
      this.category = var10;
      this.action = var11;
      this.label = var12;
      this.value = var13;
      this.screenHeight = var15;
      this.screenWidth = var14;
   }

   public CustomVariableBuffer getCustomVariableBuffer() {
      return this.customVariableBuffer;
   }

   public void setCustomVariableBuffer(CustomVariableBuffer var1) {
      this.customVariableBuffer = var1;
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder()).append("id:");
      long var2 = this.eventId;
      StringBuilder var4 = var1.append(var2).append(" ").append("random:");
      int var5 = this.randomVal;
      StringBuilder var6 = var4.append(var5).append(" ").append("timestampCurrent:");
      int var7 = this.timestampCurrent;
      StringBuilder var8 = var6.append(var7).append(" ").append("timestampPrevious:");
      int var9 = this.timestampPrevious;
      StringBuilder var10 = var8.append(var9).append(" ").append("timestampFirst:");
      int var11 = this.timestampFirst;
      StringBuilder var12 = var10.append(var11).append(" ").append("visits:");
      int var13 = this.visits;
      StringBuilder var14 = var12.append(var13).append(" ").append("value:");
      int var15 = this.value;
      StringBuilder var16 = var14.append(var15).append(" ").append("category:");
      String var17 = this.category;
      StringBuilder var18 = var16.append(var17).append(" ").append("action:");
      String var19 = this.action;
      StringBuilder var20 = var18.append(var19).append(" ").append("label:");
      String var21 = this.label;
      StringBuilder var22 = var20.append(var21).append(" ").append("width:");
      int var23 = this.screenWidth;
      StringBuilder var24 = var22.append(var23).append(" ").append("height:");
      int var25 = this.screenHeight;
      return var24.append(var25).toString();
   }
}
