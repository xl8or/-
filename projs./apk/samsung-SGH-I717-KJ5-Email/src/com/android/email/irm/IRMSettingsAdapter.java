package com.android.email.irm;

import android.util.Log;
import com.android.email.provider.EmailContent;
import com.android.exchange.EasSyncService;
import com.android.exchange.adapter.AbstractSyncAdapter;
import com.android.exchange.adapter.AbstractSyncParser;
import com.android.exchange.adapter.Serializer;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class IRMSettingsAdapter extends AbstractSyncAdapter {

   public static ArrayList<EmailContent.IRMTemplate> mTemplate;
   EmailContent.Account acc = null;
   public int mIrmStatus;


   public IRMSettingsAdapter(EmailContent.Mailbox var1, EasSyncService var2) {
      super(var1, var2);
      EmailContent.Account var3 = var2.mAccount;
      this.acc = var3;
   }

   public Serializer buildSettingsRequest() throws IOException {
      Serializer var1 = new Serializer();
      var1.start(1157).start(1195).start(1159).end().end().end().done();
      return var1;
   }

   public void cleanup() {}

   public String getCollectionName() {
      return "Email";
   }

   public boolean isSyncable() {
      return true;
   }

   public boolean parse(InputStream var1) throws IOException {
      return (new IRMSettingsAdapter.IRMSettingsParser(var1, this)).parse();
   }

   public boolean sendLocalChanges(Serializer var1) throws IOException {
      return false;
   }

   private class IRMSettingsParser extends AbstractSyncParser {

      public static final String Tag = "IRMSettingsParser";
      int mStatus;


      public IRMSettingsParser(InputStream var2, AbstractSyncAdapter var3) throws IOException {
         super(var2, var3);
      }

      private boolean checkIRMSettingStatus(int var1) throws IOException {
         boolean var3;
         if(var1 == 168) {
            int var2 = Log.e("IRMSettingsParser", "IRM feature is disabled");
            var3 = false;
         } else if(var1 == 169) {
            int var4 = Log.e("IRMSettingsParser", "IRM encountered an error");
            var3 = false;
         } else if(var1 == 170) {
            int var5 = Log.e("IRMSettingsParser", "IRM encountered permanent error");
            var3 = false;
         } else {
            var3 = true;
         }

         return var3;
      }

      private boolean parseIRMStatus() throws IOException {
         while(true) {
            boolean var4;
            if(this.nextTag(1195) != 3) {
               if(this.tag != 1158) {
                  if(this.tag == 1542) {
                     this.parseTemplates();
                  }
                  continue;
               }

               IRMSettingsAdapter var1 = IRMSettingsAdapter.this;
               int var2 = this.getValueInt();
               var1.mIrmStatus = var2;
               int var3 = IRMSettingsAdapter.this.mIrmStatus;
               if(this.checkIRMSettingStatus(var3)) {
                  continue;
               }

               var4 = false;
            } else {
               var4 = true;
            }

            return var4;
         }
      }

      private void parseTemplates() throws IOException {
         IRMSettingsAdapter.mTemplate = this.getArrayListOfTemplates();
         if(IRMSettingsAdapter.mTemplate == null) {
            this.setArrayListOfTemplates();
         }

         IRMSettingsAdapter.mTemplate.clear();

         while(this.nextTag(1542) != 3) {
            if(this.tag == 1543) {
               EmailContent.IRMTemplate var1 = new EmailContent.IRMTemplate();

               while(this.nextTag(1543) != 3) {
                  if(this.tag == 1556) {
                     String var2 = this.getValue();
                     var1.mIRMTemplateId = var2;
                  } else if(this.tag == 1557) {
                     String var3 = this.getValue();
                     var1.mIRMTemplateName = var3;
                  } else if(this.tag == 1558) {
                     String var4 = this.getValue();
                     var1.mIRMTemplateDescription = var4;
                  } else {
                     this.skipTag();
                  }
               }

               boolean var5 = IRMSettingsAdapter.mTemplate.add(var1);
            }
         }

      }

      private void setArrayListOfTemplates() {
         if(IRMSettingsAdapter.mTemplate == null) {
            IRMSettingsAdapter.mTemplate = new ArrayList();
         }
      }

      public void commandsParser() throws IOException {}

      public void commit() throws IOException {}

      public ArrayList<EmailContent.IRMTemplate> getArrayListOfTemplates() {
         return IRMSettingsAdapter.mTemplate;
      }

      public void moveResponseParser() throws IOException {}

      public boolean parse() throws IOException {
         boolean var2;
         if(this.nextTag(1157) != 3) {
            while(this.nextTag(0) != 3) {
               if(this.tag == 1158) {
                  int var1 = this.getValueInt();
                  this.mStatus = var1;
                  if(this.mStatus != 1) {
                     var2 = false;
                     return var2;
                  }
               } else if(this.tag == 1195) {
                  if(!this.parseIRMStatus()) {
                     var2 = false;
                     return var2;
                  }
               } else {
                  this.skipTag();
               }
            }
         }

         var2 = true;
         return var2;
      }

      public void responsesParser() throws IOException {}

      public void wipe() {}
   }
}
