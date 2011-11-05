package com.google.wireless.gdata2.contacts.client;

import com.google.wireless.gdata2.client.GDataClient;
import com.google.wireless.gdata2.client.GDataParserFactory;
import com.google.wireless.gdata2.client.GDataServiceClient;

public class ContactsClient extends GDataServiceClient {

   public static final String SERVICE = "cp";


   public ContactsClient(GDataClient var1, GDataParserFactory var2) {
      super(var1, var2);
   }

   public String getProtocolVersion() {
      return "3.0";
   }

   public String getServiceName() {
      return "cp";
   }
}
