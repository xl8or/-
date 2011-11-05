package com.android.exchange;

import com.android.email.provider.EmailContent;
import com.android.exchange.Request;

public class PartRequest extends Request {

   public EmailContent.Attachment mAttachment;
   public String mContentUriString;
   public String mDestination;
   public String mLocation;


   public PartRequest(EmailContent.Attachment var1) {
      long var2 = var1.mMessageKey;
      this.mMessageId = var2;
      this.mAttachment = var1;
      String var4 = this.mAttachment.mLocation;
      this.mLocation = var4;
   }

   public PartRequest(EmailContent.Attachment var1, String var2, String var3) {
      this(var1);
      this.mDestination = var2;
      this.mContentUriString = var3;
   }
}
