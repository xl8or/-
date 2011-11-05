package com.android.exchange.provider;


public class EmailResult {

   public static final int BAD_CONNECTION_ID = 11;
   public static final int CONTENT_INDEX = 9;
   public static final int DEVICE_BLOCK_ERROR = 13;
   public static final int DEVICE_QURANTINED_ERROR = 14;
   public static final int INVALID = 255;
   public static final int PROTOCOL_VIOLATION = 2;
   public static final int QUERY_TOO_COMPLEX = 8;
   public static final int SERVER_ERR = 3;
   public static final int SUCCESS = 1;
   public static final int TIMED_OUT = 10;
   public static final int TOO_MANY_RESULTS = 12;
   public int endRange = 0;
   public int result;
   public int startRange = 0;
   public int total;


   public EmailResult() {}
}
