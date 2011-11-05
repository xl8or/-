package org.apache.harmony.javax.security.auth;

import org.apache.harmony.javax.security.auth.DestroyFailedException;

public interface Destroyable {

   void destroy() throws DestroyFailedException;

   boolean isDestroyed();
}
