package myorg.bouncycastle.cms;

import java.io.IOException;
import java.io.OutputStream;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.cms.CMSEnvelopedGenerator;
import myorg.bouncycastle.cms.CMSEnvelopedHelper;
import myorg.bouncycastle.cms.CMSException;

public class CMSAuthenticatedGenerator extends CMSEnvelopedGenerator {

   public CMSAuthenticatedGenerator() {}

   public CMSAuthenticatedGenerator(SecureRandom var1) {
      super(var1);
   }

   protected AlgorithmParameterSpec generateParameterSpec(String param1, SecretKey param2, Provider param3) throws CMSException {
      // $FF: Couldn't be decompiled
   }

   protected AlgorithmIdentifier getAlgorithmIdentifier(String var1, AlgorithmParameterSpec var2, Provider var3) throws IOException, NoSuchAlgorithmException, InvalidParameterSpecException {
      AlgorithmParameters var4 = CMSEnvelopedHelper.INSTANCE.createAlgorithmParameters(var1, var3);
      var4.init(var2);
      return this.getAlgorithmIdentifier(var1, var4);
   }

   protected static class MacOutputStream extends OutputStream {

      private Mac mac;
      private final OutputStream out;


      MacOutputStream(OutputStream var1, Mac var2) {
         this.out = var1;
         this.mac = var2;
      }

      public void close() throws IOException {
         this.out.close();
      }

      public byte[] getMac() {
         return this.mac.doFinal();
      }

      public void write(int var1) throws IOException {
         Mac var2 = this.mac;
         byte var3 = (byte)var1;
         var2.update(var3);
         this.out.write(var1);
      }

      public void write(byte[] var1) throws IOException {
         Mac var2 = this.mac;
         int var3 = var1.length;
         var2.update(var1, 0, var3);
         OutputStream var4 = this.out;
         int var5 = var1.length;
         var4.write(var1, 0, var5);
      }

      public void write(byte[] var1, int var2, int var3) throws IOException {
         this.mac.update(var1, var2, var3);
         this.out.write(var1, var2, var3);
      }
   }
}
