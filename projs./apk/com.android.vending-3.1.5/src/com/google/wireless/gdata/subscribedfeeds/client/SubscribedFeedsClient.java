package com.google.wireless.gdata.subscribedfeeds.client;

import com.google.wireless.gdata.client.GDataClient;
import com.google.wireless.gdata.client.GDataParserFactory;
import com.google.wireless.gdata.client.GDataServiceClient;

public class SubscribedFeedsClient extends GDataServiceClient {

   public static final String SERVICE = "mail";


   public SubscribedFeedsClient(GDataClient var1, GDataParserFactory var2) {
      super(var1, var2);
   }

   public String getServiceName() {
      return "mail";
   }
}
