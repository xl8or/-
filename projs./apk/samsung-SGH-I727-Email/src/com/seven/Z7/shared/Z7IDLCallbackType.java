package com.seven.Z7.shared;


public interface Z7IDLCallbackType {

   int CALLBACK_EMAIL_BLOCK_START = 200;
   int CALLBACK_IM_BLOCK_START = 300;
   int CALLBACK_SYSTEM_BLOCK_START = 100;


   int getEventId();
}
