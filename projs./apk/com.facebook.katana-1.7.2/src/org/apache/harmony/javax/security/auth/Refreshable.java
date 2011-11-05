package org.apache.harmony.javax.security.auth;

import org.apache.harmony.javax.security.auth.RefreshFailedException;

public interface Refreshable {

   boolean isCurrent();

   void refresh() throws RefreshFailedException;
}
