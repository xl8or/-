package myorg.bouncycastle.x509;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.util.Collection;
import myorg.bouncycastle.x509.NoSuchParserException;
import myorg.bouncycastle.x509.X509StreamParserSpi;
import myorg.bouncycastle.x509.X509Util;
import myorg.bouncycastle.x509.util.StreamParser;
import myorg.bouncycastle.x509.util.StreamParsingException;

public class X509StreamParser implements StreamParser {

   private Provider _provider;
   private X509StreamParserSpi _spi;


   private X509StreamParser(Provider var1, X509StreamParserSpi var2) {
      this._provider = var1;
      this._spi = var2;
   }

   private static X509StreamParser createParser(X509Util.Implementation var0) {
      X509StreamParserSpi var1 = (X509StreamParserSpi)var0.getEngine();
      Provider var2 = var0.getProvider();
      return new X509StreamParser(var2, var1);
   }

   public static X509StreamParser getInstance(String var0) throws NoSuchParserException {
      try {
         X509StreamParser var1 = createParser(X509Util.getImplementation("X509StreamParser", var0));
         return var1;
      } catch (NoSuchAlgorithmException var3) {
         String var2 = var3.getMessage();
         throw new NoSuchParserException(var2);
      }
   }

   public static X509StreamParser getInstance(String var0, String var1) throws NoSuchParserException, NoSuchProviderException {
      Provider var2 = X509Util.getProvider(var1);
      return getInstance(var0, var2);
   }

   public static X509StreamParser getInstance(String var0, Provider var1) throws NoSuchParserException {
      try {
         X509StreamParser var2 = createParser(X509Util.getImplementation("X509StreamParser", var0, var1));
         return var2;
      } catch (NoSuchAlgorithmException var4) {
         String var3 = var4.getMessage();
         throw new NoSuchParserException(var3);
      }
   }

   public Provider getProvider() {
      return this._provider;
   }

   public void init(InputStream var1) {
      this._spi.engineInit(var1);
   }

   public void init(byte[] var1) {
      X509StreamParserSpi var2 = this._spi;
      ByteArrayInputStream var3 = new ByteArrayInputStream(var1);
      var2.engineInit(var3);
   }

   public Object read() throws StreamParsingException {
      return this._spi.engineRead();
   }

   public Collection readAll() throws StreamParsingException {
      return this._spi.engineReadAll();
   }
}
