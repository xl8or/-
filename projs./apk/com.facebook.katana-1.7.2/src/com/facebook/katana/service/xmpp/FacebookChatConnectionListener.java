package com.facebook.katana.service.xmpp;


public interface FacebookChatConnectionListener {

   void onConnectionEstablished();

   void onConnectionPaused();

   void onConnectionStopped();
}
