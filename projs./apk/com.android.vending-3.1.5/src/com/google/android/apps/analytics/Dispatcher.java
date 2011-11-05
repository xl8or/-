package com.google.android.apps.analytics;

import com.google.android.apps.analytics.Event;

interface Dispatcher {

   void dispatchEvents(Event[] var1);

   void init(Dispatcher.Callbacks var1, String var2);

   void stop();

   public interface Callbacks {

      void dispatchFinished();

      void eventDispatched(long var1);
   }
}
