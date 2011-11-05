package org.apache.commons.httpclient.cookie;

import org.apache.commons.httpclient.Header;

public interface CookieVersionSupport {

   int getVersion();

   Header getVersionHeader();
}
