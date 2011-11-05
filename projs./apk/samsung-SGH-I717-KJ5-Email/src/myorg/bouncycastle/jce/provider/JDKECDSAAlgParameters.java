package myorg.bouncycastle.jce.provider;

import java.io.IOException;
import java.security.AlgorithmParametersSpi;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.jce.provider.JDKAlgorithmParameters;

public abstract class JDKECDSAAlgParameters extends AlgorithmParametersSpi {

   public JDKECDSAAlgParameters() {}

   public static class SigAlgParameters extends JDKAlgorithmParameters {

      public SigAlgParameters() {}

      protected byte[] engineGetEncoded() throws IOException {
         return this.engineGetEncoded("ASN.1");
      }

      protected byte[] engineGetEncoded(String var1) throws IOException {
         byte[] var2;
         if(var1 == null) {
            var2 = this.engineGetEncoded("ASN.1");
         } else if(var1.equals("ASN.1")) {
            byte[] var3 = this.engineGetEncoded("RAW");
            var2 = (new DEROctetString(var3)).getEncoded();
         } else {
            var2 = null;
         }

         return var2;
      }

      protected void engineInit(AlgorithmParameterSpec var1) throws InvalidParameterSpecException {
         throw new InvalidParameterSpecException("unknown parameter spec passed to ECDSA parameters object.");
      }

      protected void engineInit(byte[] var1) throws IOException {}

      protected void engineInit(byte[] var1, String var2) throws IOException {
         throw new IOException("Unknown parameters format in IV parameters object");
      }

      protected String engineToString() {
         return "ECDSA Parameters";
      }

      protected AlgorithmParameterSpec localEngineGetParameterSpec(Class var1) throws InvalidParameterSpecException {
         throw new InvalidParameterSpecException("unknown parameter spec passed to ECDSA parameters object.");
      }
   }
}
