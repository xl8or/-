package myorg.bouncycastle.jce.provider;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.PBEParametersGenerator;
import myorg.bouncycastle.crypto.digests.SHA1Digest;
import myorg.bouncycastle.crypto.generators.PKCS12ParametersGenerator;
import myorg.bouncycastle.crypto.io.DigestInputStream;
import myorg.bouncycastle.crypto.io.DigestOutputStream;
import myorg.bouncycastle.crypto.io.MacInputStream;
import myorg.bouncycastle.crypto.io.MacOutputStream;
import myorg.bouncycastle.crypto.macs.HMac;
import myorg.bouncycastle.jce.interfaces.BCKeyStore;
import myorg.bouncycastle.util.Arrays;
import myorg.bouncycastle.util.io.Streams;

public class JDKKeyStore extends KeyStoreSpi implements BCKeyStore {

   static final int CERTIFICATE = 1;
   static final int KEY = 2;
   private static final String KEY_CIPHER = "PBEWithSHAAnd3-KeyTripleDES-CBC";
   static final int KEY_PRIVATE = 0;
   static final int KEY_PUBLIC = 1;
   private static final int KEY_SALT_SIZE = 20;
   static final int KEY_SECRET = 2;
   private static final int MIN_ITERATIONS = 1024;
   static final int NULL = 0;
   static final int SEALED = 4;
   static final int SECRET = 3;
   private static final String STORE_CIPHER = "PBEWithSHAAndTwofish-CBC";
   private static final int STORE_SALT_SIZE = 20;
   private static final int STORE_VERSION = 1;
   protected SecureRandom random;
   protected Hashtable table;


   public JDKKeyStore() {
      Hashtable var1 = new Hashtable();
      this.table = var1;
      SecureRandom var2 = new SecureRandom();
      this.random = var2;
   }

   // $FF: synthetic method
   static Key access$100(JDKKeyStore var0, DataInputStream var1) throws IOException {
      return var0.decodeKey(var1);
   }

   private Certificate decodeCertificate(DataInputStream var1) throws IOException {
      String var2 = var1.readUTF();
      byte[] var3 = new byte[var1.readInt()];
      var1.readFully(var3);

      try {
         CertificateFactory var4 = CertificateFactory.getInstance(var2, "myBC");
         ByteArrayInputStream var5 = new ByteArrayInputStream(var3);
         Certificate var6 = var4.generateCertificate(var5);
         return var6;
      } catch (NoSuchProviderException var9) {
         String var7 = var9.toString();
         throw new IOException(var7);
      } catch (CertificateException var10) {
         String var8 = var10.toString();
         throw new IOException(var8);
      }
   }

   private Key decodeKey(DataInputStream param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   private void encodeCertificate(Certificate var1, DataOutputStream var2) throws IOException {
      try {
         byte[] var3 = var1.getEncoded();
         String var4 = var1.getType();
         var2.writeUTF(var4);
         int var5 = var3.length;
         var2.writeInt(var5);
         var2.write(var3);
      } catch (CertificateEncodingException var7) {
         String var6 = var7.toString();
         throw new IOException(var6);
      }
   }

   private void encodeKey(Key var1, DataOutputStream var2) throws IOException {
      byte[] var3 = var1.getEncoded();
      if(var1 instanceof PrivateKey) {
         var2.write(0);
      } else if(var1 instanceof PublicKey) {
         var2.write(1);
      } else {
         var2.write(2);
      }

      String var4 = var1.getFormat();
      var2.writeUTF(var4);
      String var5 = var1.getAlgorithm();
      var2.writeUTF(var5);
      int var6 = var3.length;
      var2.writeInt(var6);
      var2.write(var3);
   }

   public Enumeration engineAliases() {
      return this.table.keys();
   }

   public boolean engineContainsAlias(String var1) {
      boolean var2;
      if(this.table.get(var1) != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public void engineDeleteEntry(String var1) throws KeyStoreException {
      if(this.table.get(var1) == null) {
         String var2 = "no such entry as " + var1;
         throw new KeyStoreException(var2);
      } else {
         this.table.remove(var1);
      }
   }

   public Certificate engineGetCertificate(String var1) {
      JDKKeyStore.StoreEntry var2 = (JDKKeyStore.StoreEntry)this.table.get(var1);
      Certificate var3;
      if(var2 != null) {
         if(var2.getType() == 1) {
            var3 = (Certificate)var2.getObject();
            return var3;
         }

         Certificate[] var4 = var2.getCertificateChain();
         if(var4 != null) {
            var3 = var4[0];
            return var3;
         }
      }

      var3 = null;
      return var3;
   }

   public String engineGetCertificateAlias(Certificate var1) {
      Enumeration var2 = this.table.elements();

      String var4;
      while(true) {
         if(var2.hasMoreElements()) {
            JDKKeyStore.StoreEntry var3 = (JDKKeyStore.StoreEntry)var2.nextElement();
            if(var3.getObject() instanceof Certificate) {
               if(!((Certificate)var3.getObject()).equals(var1)) {
                  continue;
               }

               var4 = var3.getAlias();
               break;
            }

            Certificate[] var5 = var3.getCertificateChain();
            if(var5 == null || !var5[0].equals(var1)) {
               continue;
            }

            var4 = var3.getAlias();
            break;
         }

         var4 = null;
         break;
      }

      return var4;
   }

   public Certificate[] engineGetCertificateChain(String var1) {
      JDKKeyStore.StoreEntry var2 = (JDKKeyStore.StoreEntry)this.table.get(var1);
      Certificate[] var3;
      if(var2 != null) {
         var3 = var2.getCertificateChain();
      } else {
         var3 = null;
      }

      return var3;
   }

   public Date engineGetCreationDate(String var1) {
      JDKKeyStore.StoreEntry var2 = (JDKKeyStore.StoreEntry)this.table.get(var1);
      Date var3;
      if(var2 != null) {
         var3 = var2.getDate();
      } else {
         var3 = null;
      }

      return var3;
   }

   public Key engineGetKey(String var1, char[] var2) throws NoSuchAlgorithmException, UnrecoverableKeyException {
      JDKKeyStore.StoreEntry var3 = (JDKKeyStore.StoreEntry)this.table.get(var1);
      Key var4;
      if(var3 != null && var3.getType() != 1) {
         var4 = (Key)var3.getObject(var2);
      } else {
         var4 = null;
      }

      return var4;
   }

   public boolean engineIsCertificateEntry(String var1) {
      JDKKeyStore.StoreEntry var2 = (JDKKeyStore.StoreEntry)this.table.get(var1);
      boolean var3;
      if(var2 != null && var2.getType() == 1) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public boolean engineIsKeyEntry(String var1) {
      JDKKeyStore.StoreEntry var2 = (JDKKeyStore.StoreEntry)this.table.get(var1);
      boolean var3;
      if(var2 != null && var2.getType() != 1) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public void engineLoad(InputStream var1, char[] var2) throws IOException {
      this.table.clear();
      if(var1 != null) {
         DataInputStream var3 = new DataInputStream(var1);
         int var4 = var3.readInt();
         if(var4 != 1 && var4 != 0) {
            throw new IOException("Wrong version of key store.");
         } else {
            byte[] var5 = new byte[var3.readInt()];
            var3.readFully(var5);
            int var6 = var3.readInt();
            SHA1Digest var7 = new SHA1Digest();
            HMac var8 = new HMac(var7);
            if(var2 != null && var2.length != 0) {
               byte[] var9 = PBEParametersGenerator.PKCS12PasswordToBytes(var2);
               SHA1Digest var10 = new SHA1Digest();
               PKCS12ParametersGenerator var11 = new PKCS12ParametersGenerator(var10);
               var11.init(var9, var5, var6);
               int var12 = var8.getMacSize();
               CipherParameters var13 = var11.generateDerivedMacParameters(var12);
               Arrays.fill(var9, (byte)0);
               var8.init(var13);
               MacInputStream var14 = new MacInputStream(var3, var8);
               this.loadStore(var14);
               byte[] var15 = new byte[var8.getMacSize()];
               var8.doFinal(var15, 0);
               byte[] var17 = new byte[var8.getMacSize()];
               var3.readFully(var17);
               if(!Arrays.constantTimeAreEqual(var15, var17)) {
                  this.table.clear();
                  throw new IOException("KeyStore integrity check failed.");
               }
            } else {
               this.loadStore(var3);
               byte[] var18 = new byte[var8.getMacSize()];
               var3.readFully(var18);
            }
         }
      }
   }

   public void engineSetCertificateEntry(String var1, Certificate var2) throws KeyStoreException {
      JDKKeyStore.StoreEntry var3 = (JDKKeyStore.StoreEntry)this.table.get(var1);
      if(var3 != null && var3.getType() != 1) {
         String var4 = "key store already has a key entry with alias " + var1;
         throw new KeyStoreException(var4);
      } else {
         Hashtable var5 = this.table;
         JDKKeyStore.StoreEntry var6 = new JDKKeyStore.StoreEntry(var1, var2);
         var5.put(var1, var6);
      }
   }

   public void engineSetKeyEntry(String var1, Key var2, char[] var3, Certificate[] var4) throws KeyStoreException {
      if(var2 instanceof PrivateKey && var4 == null) {
         throw new KeyStoreException("no certificate chain for private key");
      } else {
         try {
            Hashtable var5 = this.table;
            JDKKeyStore.StoreEntry var11 = new JDKKeyStore.StoreEntry(var1, var2, var3, var4);
            var5.put(var1, var11);
         } catch (Exception var14) {
            String var13 = var14.toString();
            throw new KeyStoreException(var13);
         }
      }
   }

   public void engineSetKeyEntry(String var1, byte[] var2, Certificate[] var3) throws KeyStoreException {
      Hashtable var4 = this.table;
      JDKKeyStore.StoreEntry var5 = new JDKKeyStore.StoreEntry(var1, var2, var3);
      var4.put(var1, var5);
   }

   public int engineSize() {
      return this.table.size();
   }

   public void engineStore(OutputStream var1, char[] var2) throws IOException {
      DataOutputStream var3 = new DataOutputStream(var1);
      byte[] var4 = new byte[20];
      int var5 = (this.random.nextInt() & 1023) + 1024;
      this.random.nextBytes(var4);
      var3.writeInt(1);
      int var6 = var4.length;
      var3.writeInt(var6);
      var3.write(var4);
      var3.writeInt(var5);
      SHA1Digest var7 = new SHA1Digest();
      HMac var8 = new HMac(var7);
      MacOutputStream var9 = new MacOutputStream(var3, var8);
      SHA1Digest var10 = new SHA1Digest();
      PKCS12ParametersGenerator var11 = new PKCS12ParametersGenerator(var10);
      byte[] var12 = PBEParametersGenerator.PKCS12PasswordToBytes(var2);
      var11.init(var12, var4, var5);
      int var13 = var8.getMacSize();
      CipherParameters var14 = var11.generateDerivedMacParameters(var13);
      var8.init(var14);
      int var15 = 0;

      while(true) {
         int var16 = var12.length;
         if(var15 == var16) {
            this.saveStore(var9);
            byte[] var17 = new byte[var8.getMacSize()];
            var8.doFinal(var17, 0);
            var3.write(var17);
            var3.close();
            return;
         }

         var12[var15] = 0;
         ++var15;
      }
   }

   protected void loadStore(InputStream var1) throws IOException {
      DataInputStream var2 = new DataInputStream(var1);

      for(int var5 = var2.read(); var5 > 0; var5 = var2.read()) {
         String var6 = var2.readUTF();
         long var7 = var2.readLong();
         Date var9 = new Date(var7);
         int var10 = var2.readInt();
         Certificate[] var11 = null;
         if(var10 != 0) {
            var11 = new Certificate[var10];

            for(int var12 = 0; var12 != var10; ++var12) {
               Certificate var17 = this.decodeCertificate(var2);
               var11[var12] = var17;
            }
         }

         switch(var5) {
         case 1:
            Certificate var20 = this.decodeCertificate(var2);
            Hashtable var21 = this.table;
            JDKKeyStore.StoreEntry var23 = new JDKKeyStore.StoreEntry(var6, var9, 1, var20);
            var21.put(var6, var23);
            break;
         case 2:
            Key var27 = this.decodeKey(var2);
            Hashtable var28 = this.table;
            JDKKeyStore.StoreEntry var32 = new JDKKeyStore.StoreEntry(var6, var9, 2, var27, var11);
            var28.put(var6, var32);
            break;
         case 3:
         case 4:
            byte[] var34 = new byte[var2.readInt()];
            var2.readFully(var34);
            Hashtable var37 = this.table;
            JDKKeyStore.StoreEntry var42 = new JDKKeyStore.StoreEntry(var6, var9, var5, var34, var11);
            var37.put(var6, var42);
            break;
         default:
            throw new RuntimeException("Unknown object type in store.");
         }
      }

   }

   protected Cipher makePBECipher(String var1, int var2, char[] var3, byte[] var4, int var5) throws IOException {
      try {
         PBEKeySpec var6 = new PBEKeySpec(var3);
         SecretKeyFactory var7 = SecretKeyFactory.getInstance(var1, "myBC");
         PBEParameterSpec var8 = new PBEParameterSpec(var4, var5);
         Cipher var9 = Cipher.getInstance(var1, "myBC");
         SecretKey var10 = var7.generateSecret(var6);
         var9.init(var2, var10, var8);
         return var9;
      } catch (Exception var13) {
         String var12 = "Error initialising store of key store: " + var13;
         throw new IOException(var12);
      }
   }

   protected void saveStore(OutputStream var1) throws IOException {
      Enumeration var2 = this.table.elements();
      DataOutputStream var3 = new DataOutputStream(var1);

      while(var2.hasMoreElements()) {
         JDKKeyStore.StoreEntry var4 = (JDKKeyStore.StoreEntry)var2.nextElement();
         int var5 = var4.getType();
         var3.write(var5);
         String var6 = var4.getAlias();
         var3.writeUTF(var6);
         long var7 = var4.getDate().getTime();
         var3.writeLong(var7);
         Certificate[] var9 = var4.getCertificateChain();
         if(var9 == null) {
            var3.writeInt(0);
         } else {
            int var10 = var9.length;
            var3.writeInt(var10);
            int var11 = 0;

            while(true) {
               int var12 = var9.length;
               if(var11 == var12) {
                  break;
               }

               Certificate var13 = var9[var11];
               this.encodeCertificate(var13, var3);
               ++var11;
            }
         }

         switch(var4.getType()) {
         case 1:
            Certificate var14 = (Certificate)var4.getObject();
            this.encodeCertificate(var14, var3);
            break;
         case 2:
            Key var15 = (Key)var4.getObject();
            this.encodeKey(var15, var3);
            break;
         case 3:
         case 4:
            byte[] var16 = (byte[])((byte[])var4.getObject());
            int var17 = var16.length;
            var3.writeInt(var17);
            var3.write(var16);
            break;
         default:
            throw new RuntimeException("Unknown object type in store.");
         }
      }

      var3.write(0);
   }

   public void setRandom(SecureRandom var1) {
      this.random = var1;
   }

   public static class BouncyCastleStore extends JDKKeyStore {

      public BouncyCastleStore() {}

      public void engineLoad(InputStream var1, char[] var2) throws IOException {
         this.table.clear();
         if(var1 != null) {
            DataInputStream var3 = new DataInputStream(var1);
            int var4 = var3.readInt();
            if(var4 != 1 && var4 != 0) {
               throw new IOException("Wrong version of key store.");
            } else {
               byte[] var5 = new byte[var3.readInt()];
               if(var5.length != 20) {
                  throw new IOException("Key store corrupted.");
               } else {
                  var3.readFully(var5);
                  int var6 = var3.readInt();
                  if(var6 >= 0 && var6 <= 4096) {
                     String var7;
                     if(var4 == 0) {
                        var7 = "OldPBEWithSHAAndTwofish-CBC";
                     } else {
                        var7 = "PBEWithSHAAndTwofish-CBC";
                     }

                     Cipher var10 = this.makePBECipher(var7, 2, var2, var5, var6);
                     CipherInputStream var11 = new CipherInputStream(var3, var10);
                     SHA1Digest var12 = new SHA1Digest();
                     DigestInputStream var13 = new DigestInputStream(var11, var12);
                     this.loadStore(var13);
                     byte[] var14 = new byte[var12.getDigestSize()];
                     var12.doFinal(var14, 0);
                     byte[] var16 = new byte[var12.getDigestSize()];
                     Streams.readFully(var11, var16);
                     if(!Arrays.constantTimeAreEqual(var14, var16)) {
                        this.table.clear();
                        throw new IOException("KeyStore integrity check failed.");
                     }
                  } else {
                     throw new IOException("Key store corrupted.");
                  }
               }
            }
         }
      }

      public void engineStore(OutputStream var1, char[] var2) throws IOException {
         DataOutputStream var3 = new DataOutputStream(var1);
         byte[] var4 = new byte[20];
         int var5 = (this.random.nextInt() & 1023) + 1024;
         this.random.nextBytes(var4);
         var3.writeInt(1);
         int var6 = var4.length;
         var3.writeInt(var6);
         var3.write(var4);
         var3.writeInt(var5);
         Cipher var9 = this.makePBECipher("PBEWithSHAAndTwofish-CBC", 1, var2, var4, var5);
         CipherOutputStream var10 = new CipherOutputStream(var3, var9);
         SHA1Digest var11 = new SHA1Digest();
         DigestOutputStream var12 = new DigestOutputStream(var10, var11);
         this.saveStore(var12);
         Digest var13 = var12.getDigest();
         byte[] var14 = new byte[var13.getDigestSize()];
         var13.doFinal(var14, 0);
         var10.write(var14);
         var10.close();
      }
   }

   private class StoreEntry {

      String alias;
      Certificate[] certChain;
      Date date;
      Object obj;
      int type;


      StoreEntry(String var2, Key var3, char[] var4, Certificate[] var5) throws Exception {
         Date var6 = new Date();
         this.date = var6;
         this.type = 4;
         this.alias = var2;
         this.certChain = var5;
         byte[] var7 = new byte[20];
         SecureRandom var8 = JDKKeyStore.this.random;
         long var9 = System.currentTimeMillis();
         var8.setSeed(var9);
         JDKKeyStore.this.random.nextBytes(var7);
         int var11 = (JDKKeyStore.this.random.nextInt() & 1023) + 1024;
         ByteArrayOutputStream var12 = new ByteArrayOutputStream();
         DataOutputStream var13 = new DataOutputStream(var12);
         int var14 = var7.length;
         var13.writeInt(var14);
         var13.write(var7);
         var13.writeInt(var11);
         Cipher var17 = JDKKeyStore.this.makePBECipher("PBEWithSHAAnd3-KeyTripleDES-CBC", 1, var4, var7, var11);
         CipherOutputStream var18 = new CipherOutputStream(var13, var17);
         DataOutputStream var19 = new DataOutputStream(var18);
         JDKKeyStore.this.encodeKey(var3, var19);
         var19.close();
         byte[] var20 = var12.toByteArray();
         this.obj = var20;
      }

      StoreEntry(String var2, Certificate var3) {
         Date var4 = new Date();
         this.date = var4;
         this.type = 1;
         this.alias = var2;
         this.obj = var3;
         this.certChain = null;
      }

      StoreEntry(String var2, Date var3, int var4, Object var5) {
         Date var6 = new Date();
         this.date = var6;
         this.alias = var2;
         this.date = var3;
         this.type = var4;
         this.obj = var5;
      }

      StoreEntry(String var2, Date var3, int var4, Object var5, Certificate[] var6) {
         Date var7 = new Date();
         this.date = var7;
         this.alias = var2;
         this.date = var3;
         this.type = var4;
         this.obj = var5;
         this.certChain = var6;
      }

      StoreEntry(String var2, byte[] var3, Certificate[] var4) {
         Date var5 = new Date();
         this.date = var5;
         this.type = 3;
         this.alias = var2;
         this.obj = var3;
         this.certChain = var4;
      }

      String getAlias() {
         return this.alias;
      }

      Certificate[] getCertificateChain() {
         return this.certChain;
      }

      Date getDate() {
         return this.date;
      }

      Object getObject() {
         return this.obj;
      }

      Object getObject(char[] param1) throws NoSuchAlgorithmException, UnrecoverableKeyException {
         // $FF: Couldn't be decompiled
      }

      int getType() {
         return this.type;
      }
   }
}
