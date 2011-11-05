package org.apache.james.mime4j.field.address.parser;


public interface AddressListParserTreeConstants {

   int JJTADDRESS = 2;
   int JJTADDRESS_LIST = 1;
   int JJTADDR_SPEC = 9;
   int JJTANGLE_ADDR = 6;
   int JJTDOMAIN = 11;
   int JJTGROUP_BODY = 5;
   int JJTLOCAL_PART = 10;
   int JJTMAILBOX = 3;
   int JJTNAME_ADDR = 4;
   int JJTPHRASE = 8;
   int JJTROUTE = 7;
   int JJTVOID;
   String[] jjtNodeName;


   static {
      String[] var0 = new String[]{"void", "address_list", "address", "mailbox", "name_addr", "group_body", "angle_addr", "route", "phrase", "addr_spec", "local_part", "domain"};
      jjtNodeName = var0;
   }
}
