package com.google.wireless.gdata.contacts.client;

import com.google.wireless.gdata.client.GDataClient;
import com.google.wireless.gdata.client.GDataParserFactory;
import com.google.wireless.gdata.client.GDataServiceClient;

public class ContactsClient extends GDataServiceClient {

   public static final String SERVICE = "cp";


   public ContactsClient(GDataClient var1, GDataParserFactory var2) {
      super(var1, var2);
   }

   public String getServiceName() {
      return "cp";
   }
}
