package com.google.android.apps.analytics;

import com.google.android.apps.analytics.Event;

interface EventStore {

   void deleteEvent(long var1);

   int getNumStoredEvents();

   String getReferrer();

   int getStoreId();

   String getVisitorCustomVar(int var1);

   Event[] peekEvents();

   Event[] peekEvents(int var1);

   void putEvent(Event var1);

   void setReferrer(String var1);

   void startNewVisit();
}
