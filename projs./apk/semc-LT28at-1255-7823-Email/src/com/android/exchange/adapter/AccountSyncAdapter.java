package com.android.exchange.adapter;

import com.android.email.provider.EmailContent;
import com.android.exchange.EasSyncService;
import com.android.exchange.adapter.AbstractSyncAdapter;
import com.android.exchange.adapter.Serializer;
import java.io.IOException;
import java.io.InputStream;

public class AccountSyncAdapter extends AbstractSyncAdapter {

   public AccountSyncAdapter(EmailContent.Mailbox var1, EasSyncService var2) {
      super(var1, var2);
   }

   public void cleanup() {}

   public String getCollectionName() {
      return null;
   }

   public boolean isSyncable() {
      return true;
   }

   public boolean parse(InputStream var1) throws IOException {
      return false;
   }

   public boolean sendLocalChanges(Serializer var1) throws IOException {
      return false;
   }
}
