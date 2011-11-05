package gnu.inet.http;


public interface RequestBodyWriter {

   int getContentLength();

   void reset();

   int write(byte[] var1);
}
