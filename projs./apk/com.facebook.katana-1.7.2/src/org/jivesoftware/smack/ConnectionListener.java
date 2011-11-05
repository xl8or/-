package org.jivesoftware.smack;


public interface ConnectionListener {

   void connectionClosed();

   void connectionClosedOnError(Exception var1);

   void reconnectingIn(int var1);

   void reconnectionFailed(Exception var1);

   void reconnectionSuccessful();
}
