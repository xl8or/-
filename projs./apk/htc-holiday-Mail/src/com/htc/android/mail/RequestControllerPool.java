package com.htc.android.mail;

import com.htc.android.mail.AbsRequestController;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RequestControllerPool {

   private static RequestControllerPool mRequestControllerPool = null;
   private Map<Long, AbsRequestController> mRequestControllerMap;


   private RequestControllerPool() {
      Map var1 = Collections.synchronizedMap(new HashMap());
      this.mRequestControllerMap = var1;
   }
}
