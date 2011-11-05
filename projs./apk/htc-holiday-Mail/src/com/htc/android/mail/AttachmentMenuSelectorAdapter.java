package com.htc.android.mail;

import android.content.Context;
import android.os.Environment;
import com.htc.android.mail.IconListAdapter;
import com.htc.android.mail.MailCommon;
import java.util.ArrayList;
import java.util.List;

public class AttachmentMenuSelectorAdapter extends IconListAdapter {

   public static final int FILE_DRM_DCF = 4;
   public static final int FILE_DRM_FL = 3;
   public static final int FILE_NOSUPPORT = 1;
   public static final int FILE_SUPPORT = 0;
   public static final int FILE_VCALENDAR = 5;
   public static final int FILE_VCARD = 2;
   public static final int FILE_ZIP = 6;
   public static final int MODE_WITHOUT_SLIDESHOW = 1;
   public static final int MODE_WITH_SLIDESHOW;


   public AttachmentMenuSelectorAdapter(Context var1, int var2) {
      List var3 = getData(var2, var1);
      super(var1, var3);
   }

   protected static void addItem(List<IconListAdapter.IconListItem> var0, String var1, int var2) {
      IconListAdapter.IconListItem var3 = new IconListAdapter.IconListItem(var1, var2);
      var0.add(var3);
   }

   protected static void addItem(List<IconListAdapter.IconListItem> var0, String var1, int var2, MailCommon.Command var3) {
      IconListAdapter.IconListItem var4 = new IconListAdapter.IconListItem(var1, var2, var3);
      var0.add(var4);
   }

   protected static List<IconListAdapter.IconListItem> getData(int var0, Context var1) {
      ArrayList var2 = null;
      switch(var0) {
      case 0:
         var2 = new ArrayList(2);
         String var3 = var1.getString(2131362045);
         MailCommon.Command var4 = MailCommon.Command.ATTACH_OPEN;
         addItem(var2, var3, 2130837647, var4);
         if(MailCommon.m_bSupportPhoneStorage && Environment.getPhoneStorageState().equals("mounted")) {
            String var5 = var1.getString(2131362135);
            MailCommon.Command var6 = MailCommon.Command.ATTACH_SAVE_TO_PHONE_STORAGE;
            addItem(var2, var5, 2130837647, var6);
         }

         String var7 = var1.getString(2131362134);
         MailCommon.Command var8 = MailCommon.Command.ATTACH_SAVE_TO_EXTERNAL_STORAGE;
         addItem(var2, var7, 2130837647, var8);
         break;
      case 1:
         var2 = new ArrayList(1);
         if(MailCommon.m_bSupportPhoneStorage && Environment.getPhoneStorageState().equals("mounted")) {
            String var9 = var1.getString(2131362135);
            MailCommon.Command var10 = MailCommon.Command.ATTACH_SAVE_TO_PHONE_STORAGE;
            addItem(var2, var9, 2130837647, var10);
         }

         String var11 = var1.getString(2131362134);
         MailCommon.Command var12 = MailCommon.Command.ATTACH_SAVE_TO_EXTERNAL_STORAGE;
         addItem(var2, var11, 2130837647, var12);
         break;
      case 2:
         var2 = new ArrayList(2);
         String var13 = var1.getString(2131362132);
         MailCommon.Command var14 = MailCommon.Command.ATTACH_IMPORT_VCARD;
         addItem(var2, var13, 2130837647, var14);
         if(MailCommon.m_bSupportPhoneStorage && Environment.getPhoneStorageState().equals("mounted")) {
            String var15 = var1.getString(2131362135);
            MailCommon.Command var16 = MailCommon.Command.ATTACH_SAVE_TO_PHONE_STORAGE;
            addItem(var2, var15, 2130837647, var16);
         }

         String var17 = var1.getString(2131362134);
         MailCommon.Command var18 = MailCommon.Command.ATTACH_SAVE_TO_EXTERNAL_STORAGE;
         addItem(var2, var17, 2130837647, var18);
         break;
      case 3:
         var2 = new ArrayList(1);
         String var25 = var1.getString(2131362045);
         MailCommon.Command var26 = MailCommon.Command.ATTACH_DRM_FL_OPEN;
         addItem(var2, var25, 2130837647, var26);
         break;
      case 4:
         var2 = new ArrayList(2);
         String var27 = var1.getString(2131362045);
         MailCommon.Command var28 = MailCommon.Command.ATTACH_DRM_DCF_OPEN;
         addItem(var2, var27, 2130837647, var28);
         if(MailCommon.m_bSupportPhoneStorage && Environment.getPhoneStorageState().equals("mounted")) {
            String var29 = var1.getString(2131362135);
            MailCommon.Command var30 = MailCommon.Command.ATTACH_SAVE_TO_PHONE_STORAGE;
            addItem(var2, var29, 2130837647, var30);
         }

         String var31 = var1.getString(2131362134);
         MailCommon.Command var32 = MailCommon.Command.ATTACH_SAVE_TO_EXTERNAL_STORAGE;
         addItem(var2, var31, 2130837647, var32);
         break;
      case 5:
         var2 = new ArrayList(2);
         String var19 = var1.getString(2131362133);
         MailCommon.Command var20 = MailCommon.Command.ATTACH_IMPORT_VCAL;
         addItem(var2, var19, 2130837647, var20);
         if(MailCommon.m_bSupportPhoneStorage && Environment.getPhoneStorageState().equals("mounted")) {
            String var21 = var1.getString(2131362135);
            MailCommon.Command var22 = MailCommon.Command.ATTACH_SAVE_TO_PHONE_STORAGE;
            addItem(var2, var21, 2130837647, var22);
         }

         String var23 = var1.getString(2131362134);
         MailCommon.Command var24 = MailCommon.Command.ATTACH_SAVE_TO_EXTERNAL_STORAGE;
         addItem(var2, var23, 2130837647, var24);
      }

      return var2;
   }

   public MailCommon.Command getItemCommand(int var1) {
      return ((IconListAdapter.IconListItem)this.getItem(var1)).getCommand();
   }
}
