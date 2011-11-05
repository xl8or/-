package com.android.email.bubblebutton;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextPaint;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import com.android.email.ContactInfoCache;
import com.android.email.mail.Address;
import java.io.PrintStream;

public class BubbleButton extends Button {

   private static final int MAGIN = 10;
   private static final int MARGIN_BOTTOM_VIEWER = 6;
   private static final int MARGIN_RIGHT_VIEWER = 10;
   private static final int MAX_WIDTH = 480;
   private static final int PADDING_LEFT_RIGHT = 13;
   private static final int PADDING_LEFT_RIGHT_VIEWER = 7;
   private static final int PADDING_TOP_BOTTOM = 4;
   private static final int TOPMAGIN = 16;
   private String addr = "";
   private String address = "";
   private Button btn = null;
   private int index = 0;
   private Context mContext;
   private String name = "";
   private int userCheck = 0;
   private float width = 0.0F;


   public BubbleButton(Context var1) {
      super(var1);
      this.mContext = var1;
      System.out.println("BubuleButton Start");
   }

   public boolean blackCheck(String var1) {
      int var2 = 0;

      boolean var4;
      while(true) {
         int var3 = var1.length();
         if(var2 >= var3) {
            var4 = false;
            break;
         }

         if(var1.charAt(var2) == 32) {
            var4 = true;
            break;
         }

         ++var2;
      }

      return var4;
   }

   public void buttonCreate(String param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   public boolean buttonCreate(int var1, String var2, int var3) {
      Context var4 = this.mContext;
      Button var5 = new Button(var4);
      this.btn = var5;
      this.index = var3;
      this.userCheck = var1;
      switch(var1) {
      case 1:
         if(var2.indexOf(47) != -1) {
            int var27 = var2.indexOf(47);
            String var28 = var2.substring(0, var27);
            this.name = var28;
            int var29 = var2.indexOf(47) + 1;
            int var30 = var2.length();
            String var31 = var2.substring(var29, var30);
            this.addr = var31;
         } else if(var2.indexOf(91) != -1) {
            int var32 = var2.indexOf(91);
            String var33 = var2.substring(0, var32);
            this.name = var33;
            int var34 = var2.indexOf(91) + 1;
            int var35 = var2.length();
            String var36 = var2.substring(var34, var35);
            this.addr = var36;
         } else if(var2.indexOf(60) != -1) {
            int var37 = var2.indexOf(60);
            String var38 = var2.substring(0, var37);
            this.name = var38;
            int var39 = var2.indexOf(60) + 1;
            int var40 = var2.length();
            String var41 = var2.substring(var39, var40);
            this.addr = var41;
         }
         break;
      case 2:
         this.name = "";
         this.addr = var2;
      }

      TextPaint var6 = this.getPaint();
      float var10;
      if(this.name.equals("")) {
         Button var7 = this.btn;
         String var8 = this.addr;
         var7.setText(var8);
         var6.setTextSize(18.0F);
         String var9 = this.addr;
         var10 = var6.measureText(var9);
      } else {
         Button var42 = this.btn;
         String var43 = this.name;
         var42.setText(var43);
         String var44 = this.name;
         var10 = var6.measureText(var44);
      }

      float var11 = 26.0F + var10 + 10.0F;
      this.width = var11;
      StringBuilder var12 = (new StringBuilder()).append("create strwidth=").append(var10).append(",max=");
      float var13 = this.width;
      String var14 = var12.append(var13).toString();
      int var15 = Log.d("Email-##", var14);
      PrintStream var16 = System.out;
      StringBuilder var17 = (new StringBuilder()).append("Width :::");
      float var18 = this.width;
      String var19 = var17.append(var18).toString();
      var16.println(var19);
      this.btn.setBackgroundResource(2130838007);
      this.btn.setTextSize(0, 18.0F);
      this.btn.setPadding(13, 4, 13, 4);
      this.btn.setSingleLine((boolean)1);
      Button var20 = this.btn;
      TruncateAt var21 = this.getEllipsize();
      TruncateAt var22 = TruncateAt.END;
      var20.setEllipsize(var22);
      MarginLayoutParams var23 = new MarginLayoutParams(-1, -1);
      var23.setMargins(0, 16, 10, 0);
      Button var24 = this.btn;
      LayoutParams var25 = new LayoutParams(var23);
      var24.setLayoutParams(var25);
      Animation var26 = AnimationUtils.loadAnimation(this.mContext, 2130968578);
      this.btn.setAnimation(var26);
      return true;
   }

   public String getAddr() {
      return this.addr;
   }

   public Button getButton() {
      return this.btn;
   }

   public int getIndex() {
      return this.index;
   }

   public String getName() {
      return this.name;
   }

   public float getNewWidth() {
      return this.width;
   }

   public int getUserCheck() {
      return this.userCheck;
   }

   public void refreshBubbleButton() {
      if(Address.isAllValid(this.address)) {
         Resources var1 = this.mContext.getResources();
         ContactInfoCache var2 = ContactInfoCache.getInstance();

         ContactInfoCache.CacheEntry var5;
         try {
            String var3 = this.address;
            var2.invalidateContact(var3);
            String var4 = this.address;
            var5 = var2.getContactInfoForEmailAddress(var4, (boolean)1);
         } catch (Exception var23) {
            var23.printStackTrace();
            return;
         }

         if(var5 != null && var5.name != null) {
            String var7 = var5.name;
            this.name = var7;
         } else {
            String var22 = this.address;
            this.name = var22;
         }

         Button var8 = this.btn;
         String var9 = this.name;
         var8.setText(var9);
         TextPaint var10 = this.getPaint();
         String var11 = this.name;
         int var12 = (int)var10.measureText(var11);
         int var13 = var1.getInteger(2131230834);
         float var14 = (float)(var12 + 10 + 14);
         this.width = var14;
         float var15 = this.width;
         float var16 = (float)(var13 + 10);
         if(var15 >= var16) {
            float var17 = (float)(var13 + 10);
            this.width = var17;
         }

         StringBuilder var18 = (new StringBuilder()).append("refresh strwidth=").append(var12).append(",max=");
         float var19 = this.width;
         String var20 = var18.append(var19).toString();
         int var21 = Log.d("Email-##", var20);
      }
   }
}
