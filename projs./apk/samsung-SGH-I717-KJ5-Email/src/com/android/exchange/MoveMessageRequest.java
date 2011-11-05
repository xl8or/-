package com.android.exchange;

import com.android.exchange.Request;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MoveMessageRequest extends Request {

   List<Long> mMessageIdList;
   public int mResponse;


   MoveMessageRequest(List<String> var1, long var2, long var4, long var6) {
      ArrayList var8 = new ArrayList();
      this.mMessageIdList = var8;
      Iterator var9 = var1.iterator();

      while(var9.hasNext()) {
         Long var10 = Long.valueOf(Long.parseLong((String)var9.next()));
         this.mMessageIdList.add(var10);
      }

      long var12 = ((Long)this.mMessageIdList.get(0)).longValue();
      this.mMessageId = var12;
      this.mSelBoxId = var4;
      this.mCurBoxId = var6;
   }
}
