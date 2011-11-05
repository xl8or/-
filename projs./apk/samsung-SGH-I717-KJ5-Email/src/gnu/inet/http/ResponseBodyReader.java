package gnu.inet.http;

import gnu.inet.http.Request;
import gnu.inet.http.Response;

public interface ResponseBodyReader {

   boolean accept(Request var1, Response var2);

   void close();

   void read(byte[] var1, int var2, int var3);
}
