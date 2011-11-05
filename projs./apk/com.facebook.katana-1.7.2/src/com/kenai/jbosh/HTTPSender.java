package com.kenai.jbosh;

import com.kenai.jbosh.AbstractBody;
import com.kenai.jbosh.BOSHClientConfig;
import com.kenai.jbosh.CMSessionParams;
import com.kenai.jbosh.HTTPResponse;

interface HTTPSender {

   void destroy();

   void init(BOSHClientConfig var1);

   HTTPResponse send(CMSessionParams var1, AbstractBody var2);
}
