package javax.activation;


public class MimeTypeParseException extends Exception {

   public MimeTypeParseException() {}

   public MimeTypeParseException(String var1) {
      super(var1);
   }

   MimeTypeParseException(String var1, String var2) {
      String var3 = var1 + ':' + ' ' + var2;
      this(var3);
   }
}
