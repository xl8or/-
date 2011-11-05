package com.android.exchange.adapter;

import android.content.ContentResolver;
import android.content.Context;
import com.android.email.mail.DeviceAccessException;
import com.android.email.provider.EmailContent;
import com.android.exchange.EasSyncService;
import com.android.exchange.adapter.AbstractSyncAdapter;
import com.android.exchange.adapter.AbstractUtiltyParser;
import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractSyncParser extends AbstractUtiltyParser {

   static final String logTAG = "AbstractSyncParser";
   private final String DELETE_MAILBOX_OF_TYPE = "accountKey=? and type=?";
   protected EmailContent.Account mAccount;
   protected AbstractSyncAdapter mAdapter;
   protected ContentResolver mContentResolver = null;
   protected Context mContext;
   private boolean mLooping;
   protected EmailContent.Mailbox mMailbox;
   protected EasSyncService mService;


   public AbstractSyncParser(InputStream var1, AbstractSyncAdapter var2) throws IOException {
      super(var1);
      this.mAdapter = var2;
      EasSyncService var3 = var2.mService;
      this.mService = var3;
      Context var4 = this.mService.mContext;
      this.mContext = var4;
      if(this.mContext != null) {
         ContentResolver var5 = this.mContext.getContentResolver();
         this.mContentResolver = var5;
      }

      EmailContent.Mailbox var6 = this.mService.mMailbox;
      this.mMailbox = var6;
      EmailContent.Account var7 = this.mService.mAccount;
      this.mAccount = var7;
   }

   public abstract void commandsParser() throws IOException;

   public abstract void commit() throws IOException;

   public boolean isLooping() {
      return this.mLooping;
   }

   public abstract void moveResponseParser() throws IOException;

   public boolean parse() throws IOException, DeviceAccessException {
      // $FF: Couldn't be decompiled
   }

   public abstract void responsesParser() throws IOException;

   void userLog(String var1, int var2, String var3) {
      this.mService.userLog(var1, var2, var3);
   }

   void userLog(String ... var1) {
      this.mService.userLog(var1);
   }

   public abstract void wipe();
}
