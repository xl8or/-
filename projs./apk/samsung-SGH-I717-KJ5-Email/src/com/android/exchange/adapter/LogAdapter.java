package com.android.exchange.adapter;

import com.android.email.provider.EmailContent;
import com.android.exchange.EasSyncService;
import com.android.exchange.adapter.AbstractSyncAdapter;
import com.android.exchange.adapter.AbstractSyncParser;
import com.android.exchange.adapter.Serializer;
import java.io.IOException;
import java.io.InputStream;

public class LogAdapter extends AbstractSyncAdapter {

   public LogAdapter(EmailContent.Mailbox var1, EasSyncService var2) {
      super(var1, var2);
   }

   public void cleanup() {}

   public boolean commit() throws IOException {
      return false;
   }

   public String getCollectionName() {
      return null;
   }

   public boolean isSyncable() {
      return false;
   }

   public boolean parse(InputStream var1) throws IOException {
      return (new LogAdapter.LogParser(var1, this)).parse();
   }

   public boolean sendLocalChanges(Serializer var1) throws IOException {
      return false;
   }

   public class LogParser extends AbstractSyncParser {

      private static final String TAG = "LogParser";


      public LogParser(InputStream var2, AbstractSyncAdapter var3) throws IOException {
         super(var2, var3);
      }

      public void commandsParser() throws IOException {}

      public void commit() throws IOException {}

      public void moveResponseParser() throws IOException {}

      public boolean parse() throws IOException {
         this.setLoggingTag("LogParser");
         if(this.nextTag(0) != 3) {
            this.skipTag();
         }

         return true;
      }

      public void responsesParser() throws IOException {}

      public void wipe() {}
   }
}
