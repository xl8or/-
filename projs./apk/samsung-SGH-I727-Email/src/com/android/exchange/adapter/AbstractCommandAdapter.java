package com.android.exchange.adapter;

import com.android.email.provider.EmailContent;
import com.android.exchange.EasSyncService;
import com.android.exchange.adapter.AbstractSyncAdapter;

public abstract class AbstractCommandAdapter extends AbstractSyncAdapter {

   protected static final String[] MAILBOX_ID_COLUMNS_PROJECTION;
   protected final String ROOT_FOLDER_ID = "0";


   static {
      String[] var0 = new String[]{"_id", "serverId"};
      MAILBOX_ID_COLUMNS_PROJECTION = var0;
   }

   public AbstractCommandAdapter(EmailContent.Mailbox var1, EasSyncService var2) {
      super(var1, var2);
   }

   public abstract void callback(int var1);

   public abstract String getCommandName();

   public abstract boolean hasChangedItems();

   class FolderCommandResponse {

      public String mServerId;
      public int mStatus;
      public String mSyncKey;


      public FolderCommandResponse() {}
   }
}
