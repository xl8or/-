package myorg.bouncycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.Hashtable;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.x509.KeyUsage;
import myorg.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import myorg.bouncycastle.asn1.x509.X509CertificateStructure;
import myorg.bouncycastle.asn1.x509.X509Extension;
import myorg.bouncycastle.asn1.x509.X509Extensions;
import myorg.bouncycastle.crypto.AsymmetricCipherKeyPair;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.CryptoException;
import myorg.bouncycastle.crypto.InvalidCipherTextException;
import myorg.bouncycastle.crypto.Signer;
import myorg.bouncycastle.crypto.agreement.DHBasicAgreement;
import myorg.bouncycastle.crypto.agreement.srp.SRP6Client;
import myorg.bouncycastle.crypto.digests.SHA1Digest;
import myorg.bouncycastle.crypto.encodings.PKCS1Encoding;
import myorg.bouncycastle.crypto.engines.RSABlindedEngine;
import myorg.bouncycastle.crypto.generators.DHBasicKeyPairGenerator;
import myorg.bouncycastle.crypto.io.SignerInputStream;
import myorg.bouncycastle.crypto.params.AsymmetricKeyParameter;
import myorg.bouncycastle.crypto.params.DHKeyGenerationParameters;
import myorg.bouncycastle.crypto.params.DHParameters;
import myorg.bouncycastle.crypto.params.DHPublicKeyParameters;
import myorg.bouncycastle.crypto.params.DSAPublicKeyParameters;
import myorg.bouncycastle.crypto.params.ParametersWithRandom;
import myorg.bouncycastle.crypto.params.RSAKeyParameters;
import myorg.bouncycastle.crypto.prng.ThreadedSeedGenerator;
import myorg.bouncycastle.crypto.tls.ByteQueue;
import myorg.bouncycastle.crypto.tls.Certificate;
import myorg.bouncycastle.crypto.tls.CertificateVerifyer;
import myorg.bouncycastle.crypto.tls.CombinedHash;
import myorg.bouncycastle.crypto.tls.RecordStream;
import myorg.bouncycastle.crypto.tls.TlsCipherSuite;
import myorg.bouncycastle.crypto.tls.TlsCipherSuiteManager;
import myorg.bouncycastle.crypto.tls.TlsDSSSigner;
import myorg.bouncycastle.crypto.tls.TlsInputStream;
import myorg.bouncycastle.crypto.tls.TlsOuputStream;
import myorg.bouncycastle.crypto.tls.TlsRSASigner;
import myorg.bouncycastle.crypto.tls.TlsUtils;
import myorg.bouncycastle.crypto.util.PublicKeyFactory;
import myorg.bouncycastle.util.BigIntegers;

public class TlsProtocolHandler {

   protected static final short AL_fatal = 2;
   protected static final short AL_warning = 1;
   protected static final short AP_access_denied = 49;
   protected static final short AP_bad_certificate = 42;
   protected static final short AP_bad_record_mac = 20;
   protected static final short AP_certificate_expired = 45;
   protected static final short AP_certificate_revoked = 44;
   protected static final short AP_certificate_unknown = 46;
   protected static final short AP_close_notify = 0;
   protected static final short AP_decode_error = 50;
   protected static final short AP_decompression_failure = 30;
   protected static final short AP_decrypt_error = 51;
   protected static final short AP_decryption_failed = 21;
   protected static final short AP_export_restriction = 60;
   protected static final short AP_handshake_failure = 40;
   protected static final short AP_illegal_parameter = 47;
   protected static final short AP_insufficient_security = 71;
   protected static final short AP_internal_error = 80;
   protected static final short AP_no_renegotiation = 100;
   protected static final short AP_protocol_version = 70;
   protected static final short AP_record_overflow = 22;
   protected static final short AP_unexpected_message = 10;
   protected static final short AP_unknown_ca = 48;
   protected static final short AP_unsupported_certificate = 43;
   protected static final short AP_user_canceled = 90;
   private static final short CS_CERTIFICATE_REQUEST_RECEIVED = 5;
   private static final short CS_CLIENT_CHANGE_CIPHER_SPEC_SEND = 9;
   private static final short CS_CLIENT_FINISHED_SEND = 10;
   private static final short CS_CLIENT_HELLO_SEND = 1;
   private static final short CS_CLIENT_KEY_EXCHANGE_SEND = 7;
   private static final short CS_CLIENT_VERIFICATION_SEND = 8;
   private static final short CS_DONE = 12;
   private static final short CS_SERVER_CERTIFICATE_RECEIVED = 3;
   private static final short CS_SERVER_CHANGE_CIPHER_SPEC_RECEIVED = 11;
   private static final short CS_SERVER_HELLO_DONE_RECEIVED = 6;
   private static final short CS_SERVER_HELLO_RECEIVED = 2;
   private static final short CS_SERVER_KEY_EXCHANGE_RECEIVED = 4;
   private static final short HP_CERTIFICATE = 11;
   private static final short HP_CERTIFICATE_REQUEST = 13;
   private static final short HP_CERTIFICATE_VERIFY = 15;
   private static final short HP_CLIENT_HELLO = 1;
   private static final short HP_CLIENT_KEY_EXCHANGE = 16;
   private static final short HP_FINISHED = 20;
   private static final short HP_HELLO_REQUEST = 0;
   private static final short HP_SERVER_HELLO = 2;
   private static final short HP_SERVER_HELLO_DONE = 14;
   private static final short HP_SERVER_KEY_EXCHANGE = 12;
   private static final BigInteger ONE = BigInteger.valueOf(1L);
   private static final short RL_ALERT = 21;
   private static final short RL_APPLICATION_DATA = 23;
   private static final short RL_CHANGE_CIPHER_SPEC = 20;
   private static final short RL_HANDSHAKE = 22;
   private static final String TLS_ERROR_MESSAGE = "Internal TLS error, this could be an attack";
   private static final BigInteger TWO = BigInteger.valueOf(2L);
   private static final byte[] emptybuf = new byte[0];
   private BigInteger SRP_A;
   private byte[] SRP_identity;
   private byte[] SRP_password;
   private BigInteger Yc;
   private ByteQueue alertQueue;
   private boolean appDataReady;
   private ByteQueue applicationDataQueue;
   private ByteQueue changeCipherSpecQueue;
   private TlsCipherSuite chosenCipherSuite;
   private byte[] clientRandom;
   private boolean closed;
   private short connection_state;
   private boolean extendedClientHello;
   private boolean failedWithError;
   private ByteQueue handshakeQueue;
   private byte[] ms;
   private byte[] pms;
   private SecureRandom random;
   private RecordStream rs;
   private AsymmetricKeyParameter serverPublicKey;
   private byte[] serverRandom;
   private TlsInputStream tlsInputStream;
   private TlsOuputStream tlsOutputStream;
   private CertificateVerifyer verifyer;


   public TlsProtocolHandler(InputStream var1, OutputStream var2) {
      ByteQueue var3 = new ByteQueue();
      this.applicationDataQueue = var3;
      ByteQueue var4 = new ByteQueue();
      this.changeCipherSpecQueue = var4;
      ByteQueue var5 = new ByteQueue();
      this.alertQueue = var5;
      ByteQueue var6 = new ByteQueue();
      this.handshakeQueue = var6;
      this.serverPublicKey = null;
      this.tlsInputStream = null;
      this.tlsOutputStream = null;
      this.closed = (boolean)0;
      this.failedWithError = (boolean)0;
      this.appDataReady = (boolean)0;
      this.chosenCipherSuite = null;
      this.verifyer = null;
      ThreadedSeedGenerator var7 = new ThreadedSeedGenerator();
      SecureRandom var8 = new SecureRandom();
      this.random = var8;
      SecureRandom var9 = this.random;
      byte[] var10 = var7.generateSeed(20, (boolean)1);
      var9.setSeed(var10);
      RecordStream var11 = new RecordStream(this, var1, var2);
      this.rs = var11;
   }

   public TlsProtocolHandler(InputStream var1, OutputStream var2, SecureRandom var3) {
      ByteQueue var4 = new ByteQueue();
      this.applicationDataQueue = var4;
      ByteQueue var5 = new ByteQueue();
      this.changeCipherSpecQueue = var5;
      ByteQueue var6 = new ByteQueue();
      this.alertQueue = var6;
      ByteQueue var7 = new ByteQueue();
      this.handshakeQueue = var7;
      this.serverPublicKey = null;
      this.tlsInputStream = null;
      this.tlsOutputStream = null;
      this.closed = (boolean)0;
      this.failedWithError = (boolean)0;
      this.appDataReady = (boolean)0;
      this.chosenCipherSuite = null;
      this.verifyer = null;
      this.random = var3;
      RecordStream var8 = new RecordStream(this, var1, var2);
      this.rs = var8;
   }

   private void processAlert() throws IOException {
      while(this.alertQueue.size() >= 2) {
         byte[] var1 = new byte[2];
         this.alertQueue.read(var1, 0, 2, 0);
         this.alertQueue.removeData(2);
         short var2 = (short)var1[0];
         short var3 = (short)var1[1];
         if(var2 == 2) {
            this.failedWithError = (boolean)1;
            this.closed = (boolean)1;

            try {
               this.rs.close();
            } catch (Exception var5) {
               ;
            }

            throw new IOException("Internal TLS error, this could be an attack");
         }

         if(var3 == 0) {
            this.failWithError((short)1, (short)0);
         }
      }

   }

   private void processApplicationData() {}

   private void processChangeCipherSpec() throws IOException {
      while(this.changeCipherSpecQueue.size() > 0) {
         byte[] var1 = new byte[1];
         this.changeCipherSpecQueue.read(var1, 0, 1, 0);
         this.changeCipherSpecQueue.removeData(1);
         if(var1[0] != 1) {
            this.failWithError((short)2, (short)10);
         } else if(this.connection_state == 10) {
            RecordStream var2 = this.rs;
            TlsCipherSuite var3 = this.rs.writeSuite;
            var2.readSuite = var3;
            this.connection_state = 11;
         } else {
            this.failWithError((short)2, (short)40);
         }
      }

   }

   private void processDHEKeyExchange(ByteArrayInputStream var1, Signer var2) throws IOException {
      Object var3 = var1;
      if(var2 != null) {
         AsymmetricKeyParameter var4 = this.serverPublicKey;
         byte var6 = 0;
         var2.init((boolean)var6, var4);
         byte[] var8 = this.clientRandom;
         int var9 = this.clientRandom.length;
         byte var12 = 0;
         var2.update(var8, var12, var9);
         byte[] var14 = this.serverRandom;
         int var15 = this.serverRandom.length;
         byte var18 = 0;
         var2.update(var14, var18, var15);
         var3 = new SignerInputStream(var1, var2);
      }

      byte[] var23 = TlsUtils.readOpaque16((InputStream)var3);
      byte[] var24 = TlsUtils.readOpaque16((InputStream)var3);
      byte[] var25 = TlsUtils.readOpaque16((InputStream)var3);
      if(var2 != null) {
         byte[] var26 = TlsUtils.readOpaque16(var1);
         if(!var2.verifySignature(var26)) {
            byte var30 = 2;
            byte var31 = 42;
            this.failWithError(var30, var31);
         }
      }

      this.assertEmpty(var1);
      BigInteger var32 = new BigInteger;
      byte var34 = 1;
      var32.<init>(var34, var23);
      BigInteger var36 = new BigInteger;
      byte var38 = 1;
      var36.<init>(var38, var24);
      BigInteger var40 = new BigInteger;
      byte var42 = 1;
      var40.<init>(var42, var25);
      byte var45 = 10;
      if(!var32.isProbablePrime(var45)) {
         byte var47 = 2;
         byte var48 = 47;
         this.failWithError(var47, var48);
      }

      label29: {
         BigInteger var49 = TWO;
         if(var36.compareTo(var49) >= 0) {
            BigInteger var52 = TWO;
            BigInteger var55 = var32.subtract(var52);
            if(var36.compareTo(var55) <= 0) {
               break label29;
            }
         }

         byte var59 = 2;
         byte var60 = 47;
         this.failWithError(var59, var60);
      }

      label24: {
         BigInteger var61 = TWO;
         if(var40.compareTo(var61) >= 0) {
            BigInteger var64 = ONE;
            BigInteger var67 = var32.subtract(var64);
            if(var40.compareTo(var67) <= 0) {
               break label24;
            }
         }

         byte var71 = 2;
         byte var72 = 47;
         this.failWithError(var71, var72);
      }

      DHParameters var73 = new DHParameters(var32, var36);
      DHBasicKeyPairGenerator var74 = new DHBasicKeyPairGenerator();
      DHKeyGenerationParameters var75 = new DHKeyGenerationParameters;
      SecureRandom var76 = this.random;
      var75.<init>(var76, var73);
      var74.init(var75);
      AsymmetricCipherKeyPair var82 = var74.generateKeyPair();
      BigInteger var83 = ((DHPublicKeyParameters)var82.getPublic()).getY();
      this.Yc = var83;
      DHBasicAgreement var84 = new DHBasicAgreement();
      CipherParameters var85 = var82.getPrivate();
      var84.init(var85);
      DHPublicKeyParameters var88 = new DHPublicKeyParameters(var40, var73);
      byte[] var94 = BigIntegers.asUnsignedByteArray(var84.calculateAgreement(var88));
      this.pms = var94;
   }

   private void processHandshake() throws IOException {
      boolean var1;
      label153:
      do {
         var1 = false;
         int var2 = this.handshakeQueue.size();
         byte var3 = 4;
         if(var2 >= var3) {
            byte[] var4 = new byte[4];
            ByteQueue var5 = this.handshakeQueue;
            byte var7 = 0;
            byte var8 = 4;
            byte var9 = 0;
            var5.read(var4, var7, var8, var9);
            ByteArrayInputStream var10 = new ByteArrayInputStream(var4);
            short var11 = TlsUtils.readUint8(var10);
            int var12 = TlsUtils.readUint24(var10);
            int var13 = this.handshakeQueue.size();
            int var14 = var12 + 4;
            if(var13 >= var14) {
               byte[] var17 = new byte[var12];
               ByteQueue var18 = this.handshakeQueue;
               byte var20 = 0;
               byte var22 = 4;
               var18.read(var17, var20, var12, var22);
               ByteQueue var23 = this.handshakeQueue;
               int var24 = var12 + 4;
               var23.removeData(var24);
               byte var26 = 20;
               if(var11 != var26) {
                  CombinedHash var27 = this.rs.hash1;
                  byte var29 = 0;
                  byte var30 = 4;
                  var27.update(var4, var29, var30);
                  CombinedHash var31 = this.rs.hash2;
                  byte var33 = 0;
                  byte var34 = 4;
                  var31.update(var4, var33, var34);
                  CombinedHash var35 = this.rs.hash1;
                  byte var37 = 0;
                  var35.update(var17, var37, var12);
                  CombinedHash var39 = this.rs.hash2;
                  byte var41 = 0;
                  var39.update(var17, var41, var12);
               }

               ByteArrayInputStream var43 = new ByteArrayInputStream(var17);
               switch(var11) {
               case 2:
                  switch(this.connection_state) {
                  case 1:
                     TlsUtils.checkVersion((InputStream)var43, this);
                     byte[] var127 = new byte[32];
                     this.serverRandom = var127;
                     byte[] var128 = this.serverRandom;
                     TlsUtils.readFully(var128, var43);
                     byte[] var130 = TlsUtils.readOpaque8(var43);
                     int var131 = TlsUtils.readUint16(var43);
                     TlsCipherSuite var133 = TlsCipherSuiteManager.getCipherSuite(var131, this);
                     this.chosenCipherSuite = var133;
                     if(TlsUtils.readUint8(var43) != 0) {
                        byte var135 = 2;
                        byte var136 = 47;
                        this.failWithError(var135, var136);
                     }

                     if(this.extendedClientHello && var43.available() > 0) {
                        byte[] var137 = TlsUtils.readOpaque16(var43);
                        Hashtable var138 = new Hashtable();

                        byte[] var141;
                        Integer var142;
                        Object var148;
                        for(ByteArrayInputStream var139 = new ByteArrayInputStream(var137); var139.available() > 0; var148 = var138.put(var142, var141)) {
                           int var140 = TlsUtils.readUint16(var139);
                           var141 = TlsUtils.readOpaque16(var139);
                           var142 = new Integer(var140);
                        }
                     }

                     this.assertEmpty(var43);
                     byte var151 = 2;
                     this.connection_state = var151;
                     var1 = true;
                     continue;
                  default:
                     byte var123 = 2;
                     byte var124 = 10;
                     this.failWithError(var123, var124);
                     continue;
                  }
               case 11:
                  switch(this.connection_state) {
                  case 2:
                     Certificate var53 = Certificate.parse(var43);
                     this.assertEmpty(var43);
                     X509CertificateStructure var56 = var53.certs[0];
                     SubjectPublicKeyInfo var57 = var56.getSubjectPublicKeyInfo();

                     try {
                        AsymmetricKeyParameter var58 = PublicKeyFactory.createKey(var57);
                        this.serverPublicKey = var58;
                     } catch (RuntimeException var314) {
                        byte var72 = 2;
                        byte var73 = 43;
                        this.failWithError(var72, var73);
                     }

                     if(this.serverPublicKey.isPrivate()) {
                        byte var60 = 2;
                        byte var61 = 80;
                        this.failWithError(var60, var61);
                     }

                     switch(this.chosenCipherSuite.getKeyExchangeAlgorithm()) {
                     case 1:
                        if(!(this.serverPublicKey instanceof RSAKeyParameters)) {
                           byte var75 = 2;
                           byte var76 = 46;
                           this.failWithError(var75, var76);
                        }

                        byte var79 = 32;
                        this.validateKeyUsage(var56, var79);
                        break;
                     case 3:
                     case 12:
                        if(!(this.serverPublicKey instanceof DSAPublicKeyParameters)) {
                           byte var87 = 2;
                           byte var88 = 46;
                           this.failWithError(var87, var88);
                        }
                        break;
                     case 5:
                     case 11:
                        if(!(this.serverPublicKey instanceof RSAKeyParameters)) {
                           byte var81 = 2;
                           byte var82 = 46;
                           this.failWithError(var81, var82);
                        }

                        short var85 = 128;
                        this.validateKeyUsage(var56, var85);
                        break;
                     default:
                        byte var63 = 2;
                        byte var64 = 43;
                        this.failWithError(var63, var64);
                     }

                     CertificateVerifyer var65 = this.verifyer;
                     X509CertificateStructure[] var66 = var53.getCerts();
                     if(!var65.isValid(var66)) {
                        byte var68 = 2;
                        byte var69 = 90;
                        this.failWithError(var68, var69);
                     }
                     break;
                  default:
                     byte var50 = 2;
                     byte var51 = 10;
                     this.failWithError(var50, var51);
                  }

                  byte var52 = 3;
                  this.connection_state = var52;
                  var1 = true;
                  break;
               case 12:
                  label97:
                  switch(this.connection_state) {
                  case 2:
                     short var274 = this.chosenCipherSuite.getKeyExchangeAlgorithm();
                     byte var275 = 10;
                     if(var274 != var275) {
                        byte var277 = 2;
                        byte var278 = 10;
                        this.failWithError(var277, var278);
                     }
                  case 3:
                     switch(this.chosenCipherSuite.getKeyExchangeAlgorithm()) {
                     case 3:
                        TlsDSSSigner var286 = new TlsDSSSigner();
                        this.processDHEKeyExchange(var43, var286);
                        break label97;
                     case 4:
                     case 6:
                     case 7:
                     case 8:
                     case 9:
                     default:
                        byte var280 = 2;
                        byte var281 = 10;
                        this.failWithError(var280, var281);
                        break label97;
                     case 5:
                        TlsRSASigner var282 = new TlsRSASigner();
                        this.processDHEKeyExchange(var43, var282);
                        break label97;
                     case 10:
                        Object var292 = null;
                        this.processSRPKeyExchange(var43, (Signer)var292);
                        break label97;
                     case 11:
                        TlsRSASigner var293 = new TlsRSASigner();
                        this.processSRPKeyExchange(var43, var293);
                        break label97;
                     case 12:
                        TlsDSSSigner var297 = new TlsDSSSigner();
                        this.processSRPKeyExchange(var43, var297);
                        break label97;
                     }
                  default:
                     byte var271 = 2;
                     byte var272 = 10;
                     this.failWithError(var271, var272);
                  }

                  byte var273 = 4;
                  this.connection_state = var273;
                  var1 = true;
                  break;
               case 13:
                  switch(this.connection_state) {
                  case 3:
                     short var305 = this.chosenCipherSuite.getKeyExchangeAlgorithm();
                     byte var306 = 1;
                     if(var305 != var306) {
                        byte var308 = 2;
                        byte var309 = 10;
                        this.failWithError(var308, var309);
                     }
                  case 4:
                     byte[] var310 = TlsUtils.readOpaque8(var43);
                     byte[] var311 = TlsUtils.readOpaque8(var43);
                     this.assertEmpty(var43);
                     break;
                  default:
                     byte var302 = 2;
                     byte var303 = 10;
                     this.failWithError(var302, var303);
                  }

                  byte var304 = 5;
                  this.connection_state = var304;
                  var1 = true;
                  break;
               case 14:
                  switch(this.connection_state) {
                  case 3:
                     short var155 = this.chosenCipherSuite.getKeyExchangeAlgorithm();
                     byte var156 = 1;
                     if(var155 != var156) {
                        byte var158 = 2;
                        byte var159 = 10;
                        this.failWithError(var158, var159);
                     }
                  case 4:
                  case 5:
                     this.assertEmpty(var43);
                     short var162 = this.connection_state;
                     byte var163 = 5;
                     boolean var164;
                     if(var162 == var163) {
                        var164 = true;
                     } else {
                        var164 = false;
                     }

                     byte var165 = 6;
                     this.connection_state = var165;
                     if(var164) {
                        this.sendClientCertificate();
                     }

                     switch(this.chosenCipherSuite.getKeyExchangeAlgorithm()) {
                     case 1:
                        byte[] var237 = new byte[48];
                        this.pms = var237;
                        SecureRandom var238 = this.random;
                        byte[] var239 = this.pms;
                        var238.nextBytes(var239);
                        this.pms[0] = 3;
                        this.pms[1] = 1;
                        RSABlindedEngine var240 = new RSABlindedEngine();
                        PKCS1Encoding var241 = new PKCS1Encoding(var240);
                        AsymmetricKeyParameter var244 = this.serverPublicKey;
                        SecureRandom var245 = this.random;
                        ParametersWithRandom var246 = new ParametersWithRandom(var244, var245);
                        byte var248 = 1;
                        var241.init((boolean)var248, var246);
                        byte[] var250 = null;

                        label124: {
                           byte[] var257;
                           try {
                              byte[] var251 = this.pms;
                              int var252 = this.pms.length;
                              byte var255 = 0;
                              var257 = var241.processBlock(var251, var255, var252);
                           } catch (InvalidCipherTextException var315) {
                              byte var262 = 2;
                              byte var263 = 80;
                              this.failWithError(var262, var263);
                              break label124;
                           }

                           var250 = var257;
                        }

                        this.sendClientKeyExchange(var250);
                        break;
                     case 2:
                     case 4:
                     case 6:
                     case 7:
                     case 8:
                     case 9:
                     default:
                        byte var167 = 2;
                        byte var168 = 10;
                        this.failWithError(var167, var168);
                        break;
                     case 3:
                     case 5:
                        byte[] var264 = BigIntegers.asUnsignedByteArray(this.Yc);
                        this.sendClientKeyExchange(var264);
                        break;
                     case 10:
                     case 11:
                     case 12:
                        byte[] var267 = BigIntegers.asUnsignedByteArray(this.SRP_A);
                        this.sendClientKeyExchange(var267);
                     }

                     byte var169 = 7;
                     this.connection_state = var169;
                     byte[] var170 = new byte[]{(byte)1};
                     RecordStream var171 = this.rs;
                     int var172 = var170.length;
                     byte var174 = 20;
                     byte var176 = 0;
                     var171.writeMessage(var174, var170, var176, var172);
                     byte var178 = 9;
                     this.connection_state = var178;
                     byte[] var179 = new byte[48];
                     this.ms = var179;
                     int var180 = this.clientRandom.length;
                     int var181 = this.serverRandom.length;
                     byte[] var182 = new byte[var180 + var181];
                     byte[] var183 = this.clientRandom;
                     int var184 = this.clientRandom.length;
                     byte var186 = 0;
                     byte var188 = 0;
                     System.arraycopy(var183, var186, var182, var188, var184);
                     byte[] var190 = this.serverRandom;
                     int var191 = this.clientRandom.length;
                     int var192 = this.serverRandom.length;
                     byte var194 = 0;
                     System.arraycopy(var190, var194, var182, var191, var192);
                     byte[] var198 = this.pms;
                     byte[] var199 = TlsUtils.toByteArray("master secret");
                     byte[] var200 = this.ms;
                     TlsUtils.PRF(var198, var199, var182, var200);
                     RecordStream var205 = this.rs;
                     TlsCipherSuite var206 = this.chosenCipherSuite;
                     var205.writeSuite = var206;
                     TlsCipherSuite var207 = this.rs.writeSuite;
                     byte[] var208 = this.ms;
                     byte[] var209 = this.clientRandom;
                     byte[] var210 = this.serverRandom;
                     var207.init(var208, var209, var210);
                     byte[] var211 = new byte[12];
                     byte[] var212 = new byte[36];
                     CombinedHash var213 = this.rs.hash1;
                     byte var215 = 0;
                     var213.doFinal(var212, var215);
                     byte[] var217 = this.ms;
                     byte[] var218 = TlsUtils.toByteArray("client finished");
                     TlsUtils.PRF(var217, var218, var212, var211);
                     ByteArrayOutputStream var223 = new ByteArrayOutputStream();
                     byte var224 = 20;
                     TlsUtils.writeUint8(var224, var223);
                     byte var226 = 12;
                     TlsUtils.writeUint24(var226, var223);
                     var223.write(var211);
                     byte[] var228 = var223.toByteArray();
                     RecordStream var229 = this.rs;
                     int var230 = var228.length;
                     byte var232 = 22;
                     byte var234 = 0;
                     var229.writeMessage(var232, var228, var234, var230);
                     byte var236 = 10;
                     this.connection_state = var236;
                     var1 = true;
                     continue;
                  default:
                     byte var153 = 2;
                     byte var154 = 40;
                     this.failWithError(var153, var154);
                     continue;
                  }
               case 20:
                  switch(this.connection_state) {
                  case 11:
                     byte[] var92 = new byte[12];
                     TlsUtils.readFully(var92, var43);
                     this.assertEmpty(var43);
                     byte[] var97 = new byte[12];
                     byte[] var98 = new byte[36];
                     CombinedHash var99 = this.rs.hash2;
                     byte var101 = 0;
                     var99.doFinal(var98, var101);
                     byte[] var103 = this.ms;
                     byte[] var104 = TlsUtils.toByteArray("server finished");
                     TlsUtils.PRF(var103, var104, var98, var97);
                     int var109 = 0;

                     while(true) {
                        int var110 = var92.length;
                        if(var109 >= var110) {
                           byte var120 = 12;
                           this.connection_state = var120;
                           byte var121 = 1;
                           this.appDataReady = (boolean)var121;
                           var1 = true;
                           continue label153;
                        }

                        byte var113 = var92[var109];
                        byte var114 = var97[var109];
                        if(var113 != var114) {
                           byte var118 = 2;
                           byte var119 = 40;
                           this.failWithError(var118, var119);
                        }

                        ++var109;
                     }
                  default:
                     byte var90 = 2;
                     byte var91 = 10;
                     this.failWithError(var90, var91);
                     continue;
                  }
               default:
                  byte var47 = 2;
                  byte var48 = 10;
                  this.failWithError(var47, var48);
               }
            }
         }
      } while(var1);

   }

   private void processSRPKeyExchange(ByteArrayInputStream var1, Signer var2) throws IOException {
      Object var3 = var1;
      if(var2 != null) {
         AsymmetricKeyParameter var4 = this.serverPublicKey;
         byte var6 = 0;
         var2.init((boolean)var6, var4);
         byte[] var8 = this.clientRandom;
         int var9 = this.clientRandom.length;
         byte var12 = 0;
         var2.update(var8, var12, var9);
         byte[] var14 = this.serverRandom;
         int var15 = this.serverRandom.length;
         byte var18 = 0;
         var2.update(var14, var18, var15);
         var3 = new SignerInputStream(var1, var2);
      }

      byte[] var23 = TlsUtils.readOpaque16((InputStream)var3);
      byte[] var24 = TlsUtils.readOpaque16((InputStream)var3);
      byte[] var25 = TlsUtils.readOpaque8((InputStream)var3);
      byte[] var26 = TlsUtils.readOpaque16((InputStream)var3);
      if(var2 != null) {
         byte[] var27 = TlsUtils.readOpaque16(var1);
         if(!var2.verifySignature(var27)) {
            byte var31 = 2;
            byte var32 = 42;
            this.failWithError(var31, var32);
         }
      }

      this.assertEmpty(var1);
      BigInteger var33 = new BigInteger;
      byte var35 = 1;
      var33.<init>(var35, var23);
      BigInteger var37 = new BigInteger;
      byte var39 = 1;
      var37.<init>(var39, var24);
      BigInteger var42 = new BigInteger;
      byte var44 = 1;
      var42.<init>(var44, var26);
      SRP6Client var46 = new SRP6Client();
      SHA1Digest var47 = new SHA1Digest();
      SecureRandom var48 = this.random;
      var46.init(var33, var37, var47, var48);
      byte[] var54 = this.SRP_identity;
      byte[] var55 = this.SRP_password;
      BigInteger var60 = var46.generateClientCredentials(var25, var54, var55);
      this.SRP_A = var60;

      try {
         byte[] var63 = BigIntegers.asUnsignedByteArray(var46.calculateSecret(var42));
         this.pms = var63;
      } catch (CryptoException var68) {
         byte var66 = 2;
         byte var67 = 47;
         this.failWithError(var66, var67);
      }
   }

   private void sendClientCertificate() throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      TlsUtils.writeUint8((short)11, var1);
      TlsUtils.writeUint24(3, var1);
      TlsUtils.writeUint24(0, var1);
      byte[] var2 = var1.toByteArray();
      RecordStream var3 = this.rs;
      int var4 = var2.length;
      var3.writeMessage((short)22, var2, 0, var4);
   }

   private void sendClientKeyExchange(byte[] var1) throws IOException {
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      TlsUtils.writeUint8((short)16, var2);
      TlsUtils.writeUint24(var1.length + 2, var2);
      TlsUtils.writeOpaque16(var1, var2);
      byte[] var3 = var2.toByteArray();
      RecordStream var4 = this.rs;
      int var5 = var3.length;
      var4.writeMessage((short)22, var3, 0, var5);
   }

   private void validateKeyUsage(X509CertificateStructure var1, int var2) throws IOException {
      X509Extensions var3 = var1.getTBSCertificate().getExtensions();
      if(var3 != null) {
         DERObjectIdentifier var4 = X509Extensions.KeyUsage;
         X509Extension var5 = var3.getExtension(var4);
         if(var5 != null) {
            if((KeyUsage.getInstance(var5).getBytes()[0] & 255 & var2) != var2) {
               this.failWithError((short)2, (short)46);
            }
         }
      }
   }

   protected void assertEmpty(ByteArrayInputStream var1) throws IOException {
      if(var1.available() > 0) {
         this.failWithError((short)2, (short)50);
      }
   }

   public void close() throws IOException {
      if(!this.closed) {
         this.failWithError((short)1, (short)0);
      }
   }

   public void connect(CertificateVerifyer var1) throws IOException {
      this.verifyer = var1;
      byte[] var3 = new byte[32];
      this.clientRandom = var3;
      SecureRandom var4 = this.random;
      byte[] var5 = this.clientRandom;
      var4.nextBytes(var5);
      int var6 = (int)(System.currentTimeMillis() / 1000L);
      byte[] var7 = this.clientRandom;
      byte var8 = (byte)(var6 >> 24);
      var7[0] = var8;
      byte[] var9 = this.clientRandom;
      byte var10 = (byte)(var6 >> 16);
      var9[1] = var10;
      byte[] var11 = this.clientRandom;
      byte var12 = (byte)(var6 >> 8);
      var11[2] = var12;
      byte[] var13 = this.clientRandom;
      byte var14 = (byte)var6;
      var13[3] = var14;
      ByteArrayOutputStream var15 = new ByteArrayOutputStream();
      TlsUtils.writeVersion(var15);
      byte[] var16 = this.clientRandom;
      var15.write(var16);
      TlsUtils.writeUint8((short)0, var15);
      TlsCipherSuiteManager.writeCipherSuites(var15);
      byte[] var17 = new byte[]{(byte)0};
      TlsUtils.writeOpaque8(var17, var15);
      Hashtable var18 = new Hashtable();
      byte var19;
      if(!var18.isEmpty()) {
         var19 = 1;
      } else {
         var19 = 0;
      }

      this.extendedClientHello = (boolean)var19;
      if(this.extendedClientHello) {
         ByteArrayOutputStream var21 = new ByteArrayOutputStream();
         Enumeration var22 = var18.keys();

         while(var22.hasMoreElements()) {
            Integer var23 = (Integer)var22.nextElement();
            byte[] var24 = (byte[])((byte[])var18.get(var23));
            TlsUtils.writeUint16(var23.intValue(), var21);
            TlsUtils.writeOpaque16(var24, var21);
         }

         TlsUtils.writeOpaque16(var21.toByteArray(), var15);
      }

      ByteArrayOutputStream var25 = new ByteArrayOutputStream();
      TlsUtils.writeUint8((short)1, var25);
      TlsUtils.writeUint24(var15.size(), var25);
      byte[] var26 = var15.toByteArray();
      var25.write(var26);
      byte[] var27 = var25.toByteArray();
      RecordStream var28 = this.rs;
      int var29 = var27.length;
      var28.writeMessage((short)22, var27, 0, var29);
      byte var30 = 1;
      this.connection_state = var30;

      while(this.connection_state != 12) {
         this.rs.readData();
      }

      TlsInputStream var31 = new TlsInputStream(this);
      this.tlsInputStream = var31;
      TlsOuputStream var35 = new TlsOuputStream(this);
      this.tlsOutputStream = var35;
   }

   protected void failWithError(short var1, short var2) throws IOException {
      if(!this.closed) {
         byte[] var3 = new byte[2];
         byte var4 = (byte)var1;
         var3[0] = var4;
         byte var5 = (byte)var2;
         var3[1] = var5;
         this.closed = (boolean)1;
         if(var1 == 2) {
            this.failedWithError = (boolean)1;
         }

         this.rs.writeMessage((short)21, var3, 0, 2);
         this.rs.close();
         if(var1 == 2) {
            throw new IOException("Internal TLS error, this could be an attack");
         }
      } else {
         throw new IOException("Internal TLS error, this could be an attack");
      }
   }

   protected void flush() throws IOException {
      this.rs.flush();
   }

   public InputStream getInputStream() {
      return this.tlsInputStream;
   }

   public OutputStream getOutputStream() {
      return this.tlsOutputStream;
   }

   public TlsInputStream getTlsInputStream() {
      return this.tlsInputStream;
   }

   public TlsOuputStream getTlsOuputStream() {
      return this.tlsOutputStream;
   }

   protected void processData(short var1, byte[] var2, int var3, int var4) throws IOException {
      switch(var1) {
      case 20:
         this.changeCipherSpecQueue.addData(var2, var3, var4);
         this.processChangeCipherSpec();
         return;
      case 21:
         this.alertQueue.addData(var2, var3, var4);
         this.processAlert();
         return;
      case 22:
         this.handshakeQueue.addData(var2, var3, var4);
         this.processHandshake();
         return;
      case 23:
         if(!this.appDataReady) {
            this.failWithError((short)2, (short)10);
         }

         this.applicationDataQueue.addData(var2, var3, var4);
         this.processApplicationData();
         return;
      default:
      }
   }

   protected int readApplicationData(byte[] var1, int var2, int var3) throws IOException {
      while(true) {
         int var4;
         if(this.applicationDataQueue.size() == 0) {
            if(this.failedWithError) {
               throw new IOException("Internal TLS error, this could be an attack");
            }

            if(!this.closed) {
               try {
                  this.rs.readData();
                  continue;
               } catch (IOException var9) {
                  if(!this.closed) {
                     this.failWithError((short)2, (short)80);
                  }

                  throw var9;
               } catch (RuntimeException var10) {
                  if(!this.closed) {
                     this.failWithError((short)2, (short)80);
                  }

                  throw var10;
               }
            }

            var4 = -1;
         } else {
            int var7 = this.applicationDataQueue.size();
            int var8 = Math.min(var3, var7);
            this.applicationDataQueue.read(var1, var2, var8, 0);
            this.applicationDataQueue.removeData(var8);
            var4 = var8;
         }

         return var4;
      }
   }

   protected void writeData(byte[] var1, int var2, int var3) throws IOException {
      if(this.failedWithError) {
         throw new IOException("Internal TLS error, this could be an attack");
      } else if(this.closed) {
         throw new IOException("Sorry, connection has been closed, you cannot write more data");
      } else {
         RecordStream var4 = this.rs;
         byte[] var5 = emptybuf;
         var4.writeMessage((short)23, var5, 0, 0);

         do {
            int var6 = Math.min(var3, 16384);

            try {
               this.rs.writeMessage((short)23, var1, var2, var6);
            } catch (IOException var9) {
               if(!this.closed) {
                  this.failWithError((short)2, (short)80);
               }

               throw var9;
            } catch (RuntimeException var10) {
               if(!this.closed) {
                  this.failWithError((short)2, (short)80);
               }

               throw var10;
            }

            var2 += var6;
            var3 -= var6;
         } while(var3 > 0);

      }
   }
}
