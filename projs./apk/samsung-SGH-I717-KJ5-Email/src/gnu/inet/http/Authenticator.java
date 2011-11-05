package gnu.inet.http;

import gnu.inet.http.Credentials;

public interface Authenticator {

   Credentials getCredentials(String var1, int var2);
}
