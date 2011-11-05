package com.android.email.mail.store.imap;

import com.android.email.mail.store.imap.ImapElement;
import com.android.email.mail.store.imap.ImapList;
import com.android.email.mail.store.imap.ImapString;

public class ImapResponse extends ImapList {

   private final boolean mIsContinuationRequest;
   private final String mTag;


   ImapResponse(String var1, boolean var2) {
      this.mTag = var1;
      this.mIsContinuationRequest = var2;
   }

   static boolean isStatusResponse(String var0) {
      boolean var1;
      if(!"OK".equalsIgnoreCase(var0) && !"NO".equalsIgnoreCase(var0) && !"BAD".equalsIgnoreCase(var0) && !"PREAUTH".equalsIgnoreCase(var0) && !"BYE".equalsIgnoreCase(var0)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public boolean equalsForTest(ImapElement var1) {
      boolean var2;
      if(!super.equalsForTest(var1)) {
         var2 = false;
      } else {
         ImapResponse var3 = (ImapResponse)var1;
         if(this.mTag == null) {
            if(var3.mTag != null) {
               var2 = false;
               return var2;
            }
         } else {
            String var4 = this.mTag;
            String var5 = var3.mTag;
            if(!var4.equals(var5)) {
               var2 = false;
               return var2;
            }
         }

         boolean var6 = this.mIsContinuationRequest;
         boolean var7 = var3.mIsContinuationRequest;
         if(var6 != var7) {
            var2 = false;
         } else {
            var2 = true;
         }
      }

      return var2;
   }

   public ImapString getAlertTextOrEmpty() {
      ImapString var1;
      if(!this.getResponseCodeOrEmpty().is("ALERT")) {
         var1 = ImapString.EMPTY;
      } else {
         var1 = this.getStringOrEmpty(2);
      }

      return var1;
   }

   public ImapString getResponseCodeOrEmpty() {
      ImapString var1;
      if(!this.isStatusResponse()) {
         var1 = ImapString.EMPTY;
      } else {
         var1 = this.getListOrEmpty(1).getStringOrEmpty(0);
      }

      return var1;
   }

   public ImapString getStatusResponseTextOrEmpty() {
      ImapString var1;
      if(!this.isStatusResponse()) {
         var1 = ImapString.EMPTY;
      } else {
         byte var2;
         if(this.getElementOrNone(1).isList()) {
            var2 = 2;
         } else {
            var2 = 1;
         }

         var1 = this.getStringOrEmpty(var2);
      }

      return var1;
   }

   public boolean isContinuationRequest() {
      return this.mIsContinuationRequest;
   }

   public final boolean isDataResponse(int var1, String var2) {
      boolean var3;
      if(!this.isTagged() && this.getStringOrEmpty(var1).is(var2)) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public boolean isOk() {
      return this.is(0, "OK");
   }

   public boolean isStatusResponse() {
      return isStatusResponse(this.getStringOrEmpty(0).getString());
   }

   public boolean isTagged() {
      boolean var1;
      if(this.mTag != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public String toString() {
      String var1 = this.mTag;
      if(this.isContinuationRequest()) {
         var1 = "+";
      }

      StringBuilder var2 = (new StringBuilder()).append("#").append(var1).append("# ");
      String var3 = super.toString();
      return var2.append(var3).toString();
   }
}
