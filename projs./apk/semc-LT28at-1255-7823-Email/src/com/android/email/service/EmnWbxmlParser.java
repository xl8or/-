package com.android.email.service;

import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.kxml2.wap.WbxmlParser;
import org.xmlpull.v1.XmlPullParserException;

public final class EmnWbxmlParser {

   private static final String[] ATTR_START_TABLE_0;
   private static final String[] ATTR_VALUE_TABLE_0;
   private static final String ELEM_CHART_EMN = "Emn";
   private static final String PARM_NAME_MAILBOX = "Mailbox";
   private static final String PARM_NAME_TIMESTAMP = "Timestamp";
   private static final String TAG = "EmnWbxmlParser";
   private static final String[] TAG_TABLE_0;
   private String mMailbox = null;
   private byte[] mPdu = null;
   private String mTimestamp = null;


   static {
      String[] var0 = new String[]{"Emn"};
      TAG_TABLE_0 = var0;
      String[] var1 = new String[]{"Timestamp", "Mailbox", "Mailbox=mailat:", "Mailbox=pop://", "Mailbox=imap://", "Mailbox=http://", "Mailbox=http://www.", "Mailbox=https://", "Mailbox=https://www."};
      ATTR_START_TABLE_0 = var1;
      String[] var2 = new String[]{".com", ".edu", ".net", ".org"};
      ATTR_VALUE_TABLE_0 = var2;
   }

   EmnWbxmlParser(byte[] var1) {
      this.mPdu = var1;
   }

   private void createTypeEmn(WbxmlParser var1) throws XmlPullParserException, IOException {
      String var2 = var1.getAttributeValue("", "Mailbox");
      if(var2 != null) {
         this.mMailbox = var2;
      }

      StringBuilder var3 = (new StringBuilder()).append("createTypeEmn mailbox = ");
      String var4 = this.mMailbox;
      String var5 = var3.append(var4).toString();
      int var6 = Log.i("EmnWbxmlParser", var5);
      String var7 = var1.getAttributeValue("", "Timestamp");
      if(var7 != null) {
         this.mTimestamp = var7;
      }

      StringBuilder var8 = (new StringBuilder()).append("createTypeEmn timestamp = ");
      String var9 = this.mTimestamp;
      String var10 = var8.append(var9).toString();
      int var11 = Log.i("EmnWbxmlParser", var10);
   }

   public String getMailbox() {
      return this.mMailbox;
   }

   public String getTimestamp() {
      return this.mTimestamp;
   }

   public void parse() throws XmlPullParserException, IOException {
      WbxmlParser var1 = new WbxmlParser();
      String[] var2 = TAG_TABLE_0;
      var1.setTagTable(0, var2);
      String[] var3 = ATTR_START_TABLE_0;
      var1.setAttrStartTable(0, var3);
      String[] var4 = ATTR_VALUE_TABLE_0;
      var1.setAttrValueTable(0, var4);
      byte[] var5 = this.mPdu;
      ByteArrayInputStream var6 = new ByteArrayInputStream(var5);
      var1.setInput(var6, (String)null);

      int var8;
      for(; var1.getEventType() != 1; var8 = var1.next()) {
         String var7 = var1.getName();
         if(var7 != null && var7.equals("Emn") && var1.getEventType() == 2) {
            this.createTypeEmn(var1);
         }
      }

   }
}
