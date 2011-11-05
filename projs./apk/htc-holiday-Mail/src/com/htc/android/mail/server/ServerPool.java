package com.htc.android.mail.server;

import android.content.Context;
import com.htc.android.mail.Account;
import com.htc.android.mail.Mail;
import com.htc.android.mail.server.Server;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerPool {

   private static final boolean DEBUG = Mail.MAIL_DEBUG;
   private static final String TAG = "ServerPool";
   public static ServerPool mServerPool = null;
   private Map<Long, List<Server>> mServerListMap;
   private Map<Long, List<Server>> mSmtpServerListMap;


   private ServerPool() {
      Map var1 = Collections.synchronizedMap(new HashMap());
      this.mServerListMap = var1;
      Map var2 = Collections.synchronizedMap(new HashMap());
      this.mSmtpServerListMap = var2;
   }

   public static ServerPool getInstance() {
      // $FF: Couldn't be decompiled
   }

   public Server getServer(Context param1, Account param2) {
      // $FF: Couldn't be decompiled
   }

   public Server getSmtpServer(Context param1, Account param2) {
      // $FF: Couldn't be decompiled
   }

   public void removeServer(long param1) {
      // $FF: Couldn't be decompiled
   }
}
