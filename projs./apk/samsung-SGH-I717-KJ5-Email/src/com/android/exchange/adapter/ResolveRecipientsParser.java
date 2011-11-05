package com.android.exchange.adapter;

import com.android.exchange.EasSyncService;
import com.android.exchange.adapter.Parser;
import com.android.exchange.provider.RRResponse;
import com.android.exchange.provider.RecipientCertificates;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ResolveRecipientsParser extends Parser {

   ArrayList<RRResponse> mRRResult;
   private EasSyncService mService;
   private byte mStatus = 0;


   public ResolveRecipientsParser(InputStream var1, EasSyncService var2) throws IOException {
      super(var1);
      ArrayList var3 = new ArrayList();
      this.mRRResult = var3;
      this.mService = var2;
   }

   public ArrayList<RRResponse> getResult() {
      return this.mRRResult;
   }

   public byte getStatus() {
      return this.mStatus;
   }

   public boolean parse() throws IOException {
      if(this.nextTag(0) != 645) {
         throw new IOException();
      } else {
         while(this.nextTag(0) != 3) {
            if(this.tag == 646) {
               RRResponse var1 = new RRResponse();
               this.parseResponse(var1);
               this.mRRResult.add(var1);
            } else if(this.tag == 647) {
               byte var3 = Byte.parseByte(this.getValue());
               this.mStatus = var3;
            } else {
               this.skipTag();
            }
         }

         boolean var4;
         if(this.mRRResult != null && this.mRRResult.size() > 0 && this.mStatus == 1) {
            var4 = true;
         } else {
            var4 = false;
         }

         return var4;
      }
   }

   public RecipientCertificates parseCertificates() throws IOException {
      byte var1 = 0;
      int var2 = 0;
      int var3 = 0;
      String var4 = null;
      String var5 = null;

      while(this.nextTag(652) != 3) {
         if(this.tag == 647) {
            var1 = Byte.parseByte(this.getValue());
         } else if(this.tag == 661) {
            var2 = this.getValueInt();
         } else if(this.tag == 658) {
            var3 = this.getValueInt();
         } else if(this.tag == 653) {
            var4 = this.getValue();
         } else if(this.tag == 654) {
            var5 = this.getValue();
         } else {
            this.skipTag();
         }
      }

      return new RecipientCertificates(var1, var2, var3, var4, var5);
   }

   public void parseRecipient(RRResponse var1) throws IOException {
      byte var2 = 0;
      String var3 = null;
      String var4 = null;
      RecipientCertificates var5 = null;
      int var6 = 0;
      String var7 = null;

      while(this.nextTag(649) != 3) {
         if(this.tag == 648) {
            var2 = Byte.parseByte(this.getValue());
         } else if(this.tag == 650) {
            var3 = this.getValue();
         } else if(this.tag == 651) {
            var4 = this.getValue();
         } else if(this.tag == 652) {
            var5 = this.parseCertificates();
         } else if(this.tag == 662) {
            while(this.nextTag(662) != 3) {
               if(this.tag == 647) {
                  var6 = Integer.parseInt(this.getValue());
               } else if(this.tag == 665) {
                  var7 = this.getValue();
               } else {
                  this.skipTag();
               }
            }
         } else {
            this.skipTag();
         }
      }

      var1.addRecipient(var2, var3, var4, var5, var6, var7);
   }

   public void parseResponse(RRResponse var1) throws IOException {
      while(this.nextTag(646) != 3) {
         if(this.tag == 656) {
            String var2 = this.getValue();
            var1.to = var2;
         } else if(this.tag == 647) {
            byte var3 = Byte.parseByte(this.getValue());
            var1.status = var3;
         } else if(this.tag == 658) {
            int var4 = this.getValueInt();
            var1.recipientCount = var4;
         } else if(this.tag == 649) {
            this.parseRecipient(var1);
         } else {
            this.skipTag();
         }
      }

   }
}
