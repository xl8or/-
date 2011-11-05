package com.android.exchange.adapter;

import android.util.Log;
import com.android.email.mail.DeviceAccessException;
import com.android.exchange.EasSyncService;
import com.android.exchange.IllegalHeartbeatException;
import com.android.exchange.StaleFolderListException;
import com.android.exchange.adapter.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class PingParser extends Parser {

   private EasSyncService mService;
   private int mSyncStatus;
   private ArrayList<String> syncList;


   public PingParser(InputStream var1, EasSyncService var2) throws IOException {
      super(var1);
      ArrayList var3 = new ArrayList();
      this.syncList = var3;
      this.mSyncStatus = 0;
      this.mService = var2;
   }

   public ArrayList<String> getSyncList() {
      return this.syncList;
   }

   public int getSyncStatus() {
      return this.mSyncStatus;
   }

   public boolean parse() throws IOException, StaleFolderListException, IllegalHeartbeatException, DeviceAccessException {
      boolean var1 = false;
      if(this.nextTag(0) != 837) {
         throw new IOException();
      } else {
         while(true) {
            while(true) {
               while(this.nextTag(0) != 3) {
                  if(this.tag == 839) {
                     int var2 = this.getValueInt();
                     this.mSyncStatus = var2;
                     this.mService.userLog("Ping completed, status = ", var2);
                     if(var2 != 2) {
                        if(var2 == 7 || var2 == 4) {
                           throw new StaleFolderListException();
                        }

                        if(var2 != 5 && var2 == 129) {
                           int var3 = Log.i("PingParser", "PingParser::parse() - Received status 129 for ping, to Block device ");
                           throw new DeviceAccessException(262145, 2131166819);
                        }
                     } else {
                        var1 = true;
                     }
                  } else if(this.tag == 841) {
                     ArrayList var4 = this.syncList;
                     this.parsePingFolders(var4);
                  } else {
                     if(this.tag == 840) {
                        int var5 = this.getValueInt();
                        throw new IllegalHeartbeatException(var5);
                     }

                     this.skipTag();
                  }
               }

               return var1;
            }
         }
      }
   }

   public void parsePingFolders(ArrayList<String> var1) throws IOException {
      while(this.nextTag(841) != 3) {
         if(this.tag == 842) {
            String var2 = this.getValue();
            var1.add(var2);
            EasSyncService var4 = this.mService;
            String[] var5 = new String[]{"Changes found in: ", var2};
            var4.userLog(var5);
         } else {
            this.skipTag();
         }
      }

   }
}
