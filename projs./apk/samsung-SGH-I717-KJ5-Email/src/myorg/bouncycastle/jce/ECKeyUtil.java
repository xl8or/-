package myorg.bouncycastle.jce;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;

public class ECKeyUtil {

   public ECKeyUtil() {}

   public static PrivateKey privateToExplicitParameters(PrivateKey var0, String var1) throws IllegalArgumentException, NoSuchAlgorithmException, NoSuchProviderException {
      Provider var2 = Security.getProvider(var1);
      if(var2 == null) {
         String var3 = "cannot find provider: " + var1;
         throw new NoSuchProviderException(var3);
      } else {
         return privateToExplicitParameters(var0, var2);
      }
   }

   public static PrivateKey privateToExplicitParameters(PrivateKey param0, Provider param1) throws IllegalArgumentException, NoSuchAlgorithmException {
      // $FF: Couldn't be decompiled
   }

   public static PublicKey publicToExplicitParameters(PublicKey var0, String var1) throws IllegalArgumentException, NoSuchAlgorithmException, NoSuchProviderException {
      Provider var2 = Security.getProvider(var1);
      if(var2 == null) {
         String var3 = "cannot find provider: " + var1;
         throw new NoSuchProviderException(var3);
      } else {
         return publicToExplicitParameters(var0, var2);
      }
   }

   public static PublicKey publicToExplicitParameters(PublicKey param0, Provider param1) throws IllegalArgumentException, NoSuchAlgorithmException {
      // $FF: Couldn't be decompiled
   }

   private static class UnexpectedException extends RuntimeException {

      private Throwable cause;


      UnexpectedException(Throwable var1) {
         String var2 = var1.toString();
         super(var2);
         this.cause = var1;
      }

      public Throwable getCause() {
         return this.cause;
      }
   }
}
