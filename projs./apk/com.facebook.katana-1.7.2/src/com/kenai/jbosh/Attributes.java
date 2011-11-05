package com.kenai.jbosh;

import com.kenai.jbosh.BodyQName;

final class Attributes {

   static final BodyQName ACCEPT = BodyQName.createBOSH("accept");
   static final BodyQName ACK = BodyQName.createBOSH("ack");
   static final BodyQName AUTHID = BodyQName.createBOSH("authid");
   static final BodyQName CHARSETS = BodyQName.createBOSH("charsets");
   static final BodyQName CONDITION = BodyQName.createBOSH("condition");
   static final BodyQName CONTENT = BodyQName.createBOSH("content");
   static final BodyQName FROM = BodyQName.createBOSH("from");
   static final BodyQName HOLD = BodyQName.createBOSH("hold");
   static final BodyQName INACTIVITY = BodyQName.createBOSH("inactivity");
   static final BodyQName KEY = BodyQName.createBOSH("key");
   static final BodyQName MAXPAUSE = BodyQName.createBOSH("maxpause");
   static final BodyQName NEWKEY = BodyQName.createBOSH("newkey");
   static final BodyQName PAUSE = BodyQName.createBOSH("pause");
   static final BodyQName POLLING = BodyQName.createBOSH("polling");
   static final BodyQName REPORT = BodyQName.createBOSH("report");
   static final BodyQName REQUESTS = BodyQName.createBOSH("requests");
   static final BodyQName RID = BodyQName.createBOSH("rid");
   static final BodyQName ROUTE = BodyQName.createBOSH("route");
   static final BodyQName SECURE = BodyQName.createBOSH("secure");
   static final BodyQName SID = BodyQName.createBOSH("sid");
   static final BodyQName STREAM = BodyQName.createBOSH("stream");
   static final BodyQName TIME = BodyQName.createBOSH("time");
   static final BodyQName TO = BodyQName.createBOSH("to");
   static final BodyQName TYPE = BodyQName.createBOSH("type");
   static final BodyQName VER = BodyQName.createBOSH("ver");
   static final BodyQName WAIT = BodyQName.createBOSH("wait");
   static final BodyQName XML_LANG = BodyQName.createWithPrefix("http://www.w3.org/XML/1998/namespace", "lang", "xml");


   private Attributes() {}
}
