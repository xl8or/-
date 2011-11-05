package com.android.exchange;

import com.android.exchange.Request;

public class MeetingResponseRequest extends Request {

   public int mResponse;


   MeetingResponseRequest(long var1, int var3) {
      this.mMessageId = var1;
      this.mResponse = var3;
   }
}
