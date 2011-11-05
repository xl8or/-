package myorg.bouncycastle.jce.provider;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import javax.crypto.spec.RC5ParameterSpec;
import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.BufferedBlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.InvalidCipherTextException;
import myorg.bouncycastle.crypto.engines.AESFastEngine;
import myorg.bouncycastle.crypto.engines.BlowfishEngine;
import myorg.bouncycastle.crypto.engines.CAST5Engine;
import myorg.bouncycastle.crypto.engines.CAST6Engine;
import myorg.bouncycastle.crypto.engines.DESEngine;
import myorg.bouncycastle.crypto.engines.DESedeEngine;
import myorg.bouncycastle.crypto.engines.GOST28147Engine;
import myorg.bouncycastle.crypto.engines.RC2Engine;
import myorg.bouncycastle.crypto.engines.RC532Engine;
import myorg.bouncycastle.crypto.engines.RC564Engine;
import myorg.bouncycastle.crypto.engines.RC6Engine;
import myorg.bouncycastle.crypto.engines.RijndaelEngine;
import myorg.bouncycastle.crypto.engines.SEEDEngine;
import myorg.bouncycastle.crypto.engines.SerpentEngine;
import myorg.bouncycastle.crypto.engines.SkipjackEngine;
import myorg.bouncycastle.crypto.engines.TEAEngine;
import myorg.bouncycastle.crypto.engines.TwofishEngine;
import myorg.bouncycastle.crypto.engines.XTEAEngine;
import myorg.bouncycastle.crypto.modes.AEADBlockCipher;
import myorg.bouncycastle.crypto.modes.CBCBlockCipher;
import myorg.bouncycastle.crypto.modes.CCMBlockCipher;
import myorg.bouncycastle.crypto.modes.CFBBlockCipher;
import myorg.bouncycastle.crypto.modes.CTSBlockCipher;
import myorg.bouncycastle.crypto.modes.EAXBlockCipher;
import myorg.bouncycastle.crypto.modes.GCMBlockCipher;
import myorg.bouncycastle.crypto.modes.GOFBBlockCipher;
import myorg.bouncycastle.crypto.modes.OFBBlockCipher;
import myorg.bouncycastle.crypto.modes.OpenPGPCFBBlockCipher;
import myorg.bouncycastle.crypto.modes.PGPCFBBlockCipher;
import myorg.bouncycastle.crypto.modes.SICBlockCipher;
import myorg.bouncycastle.crypto.paddings.BlockCipherPadding;
import myorg.bouncycastle.crypto.paddings.ISO10126d2Padding;
import myorg.bouncycastle.crypto.paddings.ISO7816d4Padding;
import myorg.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import myorg.bouncycastle.crypto.paddings.TBCPadding;
import myorg.bouncycastle.crypto.paddings.X923Padding;
import myorg.bouncycastle.crypto.paddings.ZeroBytePadding;
import myorg.bouncycastle.crypto.params.ParametersWithIV;
import myorg.bouncycastle.jce.provider.PBE;
import myorg.bouncycastle.jce.provider.WrapCipherSpi;
import myorg.bouncycastle.jce.spec.GOST28147ParameterSpec;
import myorg.bouncycastle.util.Strings;

public class JCEBlockCipher extends WrapCipherSpi implements PBE {

   private Class[] availableSpecs;
   private BlockCipher baseEngine;
   private JCEBlockCipher.GenericBlockCipher cipher;
   private int ivLength;
   private ParametersWithIV ivParam;
   private String modeName;
   private boolean padded;
   private String pbeAlgorithm;
   private PBEParameterSpec pbeSpec;


   protected JCEBlockCipher(BlockCipher var1) {
      Class[] var2 = new Class[]{RC2ParameterSpec.class, RC5ParameterSpec.class, IvParameterSpec.class, PBEParameterSpec.class, GOST28147ParameterSpec.class};
      this.availableSpecs = var2;
      this.ivLength = 0;
      this.pbeSpec = null;
      this.pbeAlgorithm = null;
      this.modeName = null;
      this.baseEngine = var1;
      JCEBlockCipher.BufferedGenericBlockCipher var3 = new JCEBlockCipher.BufferedGenericBlockCipher(var1);
      this.cipher = var3;
   }

   protected JCEBlockCipher(BlockCipher var1, int var2) {
      Class[] var3 = new Class[]{RC2ParameterSpec.class, RC5ParameterSpec.class, IvParameterSpec.class, PBEParameterSpec.class, GOST28147ParameterSpec.class};
      this.availableSpecs = var3;
      this.ivLength = 0;
      this.pbeSpec = null;
      this.pbeAlgorithm = null;
      this.modeName = null;
      this.baseEngine = var1;
      JCEBlockCipher.BufferedGenericBlockCipher var4 = new JCEBlockCipher.BufferedGenericBlockCipher(var1);
      this.cipher = var4;
      int var5 = var2 / 8;
      this.ivLength = var5;
   }

   protected JCEBlockCipher(BufferedBlockCipher var1, int var2) {
      Class[] var3 = new Class[]{RC2ParameterSpec.class, RC5ParameterSpec.class, IvParameterSpec.class, PBEParameterSpec.class, GOST28147ParameterSpec.class};
      this.availableSpecs = var3;
      this.ivLength = 0;
      this.pbeSpec = null;
      this.pbeAlgorithm = null;
      this.modeName = null;
      BlockCipher var4 = var1.getUnderlyingCipher();
      this.baseEngine = var4;
      JCEBlockCipher.BufferedGenericBlockCipher var5 = new JCEBlockCipher.BufferedGenericBlockCipher(var1);
      this.cipher = var5;
      int var6 = var2 / 8;
      this.ivLength = var6;
   }

   private boolean isAEADModeName(String var1) {
      boolean var2;
      if(!"CCM".equals(var1) && !"EAX".equals(var1) && !"GCM".equals(var1)) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   protected int engineDoFinal(byte[] var1, int var2, int var3, byte[] var4, int var5) throws IllegalBlockSizeException, BadPaddingException {
      int var6 = 0;
      if(var3 != 0) {
         JCEBlockCipher.GenericBlockCipher var7 = this.cipher;
         var6 = var7.processBytes(var1, var2, var3, var4, var5);
      }

      int var15;
      try {
         JCEBlockCipher.GenericBlockCipher var13 = this.cipher;
         int var14 = var5 + var6;
         var15 = var13.doFinal(var4, var14);
      } catch (DataLengthException var18) {
         String var16 = var18.getMessage();
         throw new IllegalBlockSizeException(var16);
      } catch (InvalidCipherTextException var19) {
         String var17 = var19.getMessage();
         throw new BadPaddingException(var17);
      }

      return var15 + var6;
   }

   protected byte[] engineDoFinal(byte[] var1, int var2, int var3) throws IllegalBlockSizeException, BadPaddingException {
      int var4 = 0;
      byte[] var5 = new byte[this.engineGetOutputSize(var3)];
      if(var3 != 0) {
         JCEBlockCipher.GenericBlockCipher var6 = this.cipher;
         var4 = var6.processBytes(var1, var2, var3, var5, 0);
      }

      int var10;
      try {
         var10 = this.cipher.doFinal(var5, var4);
      } catch (DataLengthException var17) {
         String var14 = var17.getMessage();
         throw new IllegalBlockSizeException(var14);
      } catch (InvalidCipherTextException var18) {
         String var15 = var18.getMessage();
         throw new BadPaddingException(var15);
      }

      var4 += var10;
      int var12 = var5.length;
      byte[] var13;
      if(var4 == var12) {
         var13 = var5;
      } else {
         byte[] var16 = new byte[var4];
         System.arraycopy(var5, 0, var16, 0, var4);
         var13 = var16;
      }

      return var13;
   }

   protected int engineGetBlockSize() {
      return this.baseEngine.getBlockSize();
   }

   protected byte[] engineGetIV() {
      byte[] var1;
      if(this.ivParam != null) {
         var1 = this.ivParam.getIV();
      } else {
         var1 = null;
      }

      return var1;
   }

   protected int engineGetKeySize(Key var1) {
      return var1.getEncoded().length * 8;
   }

   protected int engineGetOutputSize(int var1) {
      return this.cipher.getOutputSize(var1);
   }

   protected AlgorithmParameters engineGetParameters() {
      AlgorithmParameters var4;
      if(this.engineParams == null) {
         if(this.pbeSpec != null) {
            try {
               AlgorithmParameters var1 = AlgorithmParameters.getInstance(this.pbeAlgorithm, "myBC");
               this.engineParams = var1;
               AlgorithmParameters var2 = this.engineParams;
               PBEParameterSpec var3 = this.pbeSpec;
               var2.init(var3);
            } catch (Exception var13) {
               var4 = null;
               return var4;
            }
         } else if(this.ivParam != null) {
            String var6 = this.cipher.getUnderlyingCipher().getAlgorithmName();
            if(var6.indexOf(47) >= 0) {
               int var7 = var6.indexOf(47);
               var6 = var6.substring(0, var7);
            }

            try {
               AlgorithmParameters var8 = AlgorithmParameters.getInstance(var6, "myBC");
               this.engineParams = var8;
               AlgorithmParameters var9 = this.engineParams;
               byte[] var10 = this.ivParam.getIV();
               var9.init(var10);
            } catch (Exception var12) {
               String var11 = var12.toString();
               throw new RuntimeException(var11);
            }
         }
      }

      var4 = this.engineParams;
      return var4;
   }

   protected void engineInit(int var1, Key var2, AlgorithmParameters var3, SecureRandom var4) throws InvalidKeyException, InvalidAlgorithmParameterException {
      AlgorithmParameterSpec var5 = null;
      if(var3 != null) {
         int var6 = 0;

         while(true) {
            int var7 = this.availableSpecs.length;
            if(var6 == var7) {
               break;
            }

            AlgorithmParameterSpec var9;
            try {
               Class var8 = this.availableSpecs[var6];
               var9 = var3.getParameterSpec(var8);
            } catch (Exception var14) {
               ++var6;
               continue;
            }

            var5 = var9;
            break;
         }

         if(var5 == null) {
            StringBuilder var10 = (new StringBuilder()).append("can\'t handle parameter ");
            String var11 = var3.toString();
            String var12 = var10.append(var11).toString();
            throw new InvalidAlgorithmParameterException(var12);
         }
      }

      this.engineInit(var1, var2, var5, var4);
      this.engineParams = var3;
   }

   protected void engineInit(int var1, Key var2, SecureRandom var3) throws InvalidKeyException {
      try {
         AlgorithmParameterSpec var4 = (AlgorithmParameterSpec)false;
         this.engineInit(var1, var2, var4, var3);
      } catch (InvalidAlgorithmParameterException var6) {
         String var5 = var6.getMessage();
         throw new InvalidKeyException(var5);
      }
   }

   protected void engineInit(int param1, Key param2, AlgorithmParameterSpec param3, SecureRandom param4) throws InvalidKeyException, InvalidAlgorithmParameterException {
      // $FF: Couldn't be decompiled
   }

   protected void engineSetMode(String var1) throws NoSuchAlgorithmException {
      String var2 = Strings.toUpperCase(var1);
      this.modeName = var2;
      if(this.modeName != null) {
         String var3 = this.modeName;
         if("ECB".equals(var3)) {
            this.ivLength = 0;
            BlockCipher var4 = this.baseEngine;
            JCEBlockCipher.BufferedGenericBlockCipher var5 = new JCEBlockCipher.BufferedGenericBlockCipher(var4);
            this.cipher = var5;
         } else {
            String var6 = this.modeName;
            if("CBC".equals(var6)) {
               int var7 = this.baseEngine.getBlockSize();
               this.ivLength = var7;
               BlockCipher var8 = this.baseEngine;
               CBCBlockCipher var9 = new CBCBlockCipher(var8);
               JCEBlockCipher.BufferedGenericBlockCipher var10 = new JCEBlockCipher.BufferedGenericBlockCipher(var9);
               this.cipher = var10;
            } else if(this.modeName.startsWith("OFB")) {
               int var11 = this.baseEngine.getBlockSize();
               this.ivLength = var11;
               if(this.modeName.length() != 3) {
                  int var12 = Integer.parseInt(this.modeName.substring(3));
                  BlockCipher var13 = this.baseEngine;
                  OFBBlockCipher var14 = new OFBBlockCipher(var13, var12);
                  JCEBlockCipher.BufferedGenericBlockCipher var15 = new JCEBlockCipher.BufferedGenericBlockCipher(var14);
                  this.cipher = var15;
               } else {
                  BlockCipher var16 = this.baseEngine;
                  int var17 = this.baseEngine.getBlockSize() * 8;
                  OFBBlockCipher var18 = new OFBBlockCipher(var16, var17);
                  JCEBlockCipher.BufferedGenericBlockCipher var19 = new JCEBlockCipher.BufferedGenericBlockCipher(var18);
                  this.cipher = var19;
               }
            } else if(this.modeName.startsWith("CFB")) {
               int var20 = this.baseEngine.getBlockSize();
               this.ivLength = var20;
               if(this.modeName.length() != 3) {
                  int var21 = Integer.parseInt(this.modeName.substring(3));
                  BlockCipher var22 = this.baseEngine;
                  CFBBlockCipher var23 = new CFBBlockCipher(var22, var21);
                  JCEBlockCipher.BufferedGenericBlockCipher var24 = new JCEBlockCipher.BufferedGenericBlockCipher(var23);
                  this.cipher = var24;
               } else {
                  BlockCipher var25 = this.baseEngine;
                  int var26 = this.baseEngine.getBlockSize() * 8;
                  CFBBlockCipher var27 = new CFBBlockCipher(var25, var26);
                  JCEBlockCipher.BufferedGenericBlockCipher var28 = new JCEBlockCipher.BufferedGenericBlockCipher(var27);
                  this.cipher = var28;
               }
            } else if(this.modeName.startsWith("PGP")) {
               boolean var29 = this.modeName.equalsIgnoreCase("PGPCFBwithIV");
               int var30 = this.baseEngine.getBlockSize();
               this.ivLength = var30;
               BlockCipher var31 = this.baseEngine;
               PGPCFBBlockCipher var32 = new PGPCFBBlockCipher(var31, var29);
               JCEBlockCipher.BufferedGenericBlockCipher var33 = new JCEBlockCipher.BufferedGenericBlockCipher(var32);
               this.cipher = var33;
            } else if(this.modeName.equalsIgnoreCase("OpenPGPCFB")) {
               this.ivLength = 0;
               BlockCipher var34 = this.baseEngine;
               OpenPGPCFBBlockCipher var35 = new OpenPGPCFBBlockCipher(var34);
               JCEBlockCipher.BufferedGenericBlockCipher var36 = new JCEBlockCipher.BufferedGenericBlockCipher(var35);
               this.cipher = var36;
            } else if(this.modeName.startsWith("SIC")) {
               int var37 = this.baseEngine.getBlockSize();
               this.ivLength = var37;
               if(this.ivLength < 16) {
                  throw new IllegalArgumentException("Warning: SIC-Mode can become a twotime-pad if the blocksize of the cipher is too small. Use a cipher with a block size of at least 128 bits (e.g. AES)");
               } else {
                  BlockCipher var38 = this.baseEngine;
                  SICBlockCipher var39 = new SICBlockCipher(var38);
                  BufferedBlockCipher var40 = new BufferedBlockCipher(var39);
                  JCEBlockCipher.BufferedGenericBlockCipher var41 = new JCEBlockCipher.BufferedGenericBlockCipher(var40);
                  this.cipher = var41;
               }
            } else if(this.modeName.startsWith("CTR")) {
               int var42 = this.baseEngine.getBlockSize();
               this.ivLength = var42;
               BlockCipher var43 = this.baseEngine;
               SICBlockCipher var44 = new SICBlockCipher(var43);
               BufferedBlockCipher var45 = new BufferedBlockCipher(var44);
               JCEBlockCipher.BufferedGenericBlockCipher var46 = new JCEBlockCipher.BufferedGenericBlockCipher(var45);
               this.cipher = var46;
            } else if(this.modeName.startsWith("GOFB")) {
               int var47 = this.baseEngine.getBlockSize();
               this.ivLength = var47;
               BlockCipher var48 = this.baseEngine;
               GOFBBlockCipher var49 = new GOFBBlockCipher(var48);
               BufferedBlockCipher var50 = new BufferedBlockCipher(var49);
               JCEBlockCipher.BufferedGenericBlockCipher var51 = new JCEBlockCipher.BufferedGenericBlockCipher(var50);
               this.cipher = var51;
            } else if(this.modeName.startsWith("CTS")) {
               int var52 = this.baseEngine.getBlockSize();
               this.ivLength = var52;
               BlockCipher var53 = this.baseEngine;
               CBCBlockCipher var54 = new CBCBlockCipher(var53);
               CTSBlockCipher var55 = new CTSBlockCipher(var54);
               JCEBlockCipher.BufferedGenericBlockCipher var56 = new JCEBlockCipher.BufferedGenericBlockCipher(var55);
               this.cipher = var56;
            } else if(this.modeName.startsWith("CCM")) {
               int var57 = this.baseEngine.getBlockSize();
               this.ivLength = var57;
               BlockCipher var58 = this.baseEngine;
               CCMBlockCipher var59 = new CCMBlockCipher(var58);
               JCEBlockCipher.AEADGenericBlockCipher var60 = new JCEBlockCipher.AEADGenericBlockCipher(var59);
               this.cipher = var60;
            } else if(this.modeName.startsWith("EAX")) {
               int var61 = this.baseEngine.getBlockSize();
               this.ivLength = var61;
               BlockCipher var62 = this.baseEngine;
               EAXBlockCipher var63 = new EAXBlockCipher(var62);
               JCEBlockCipher.AEADGenericBlockCipher var64 = new JCEBlockCipher.AEADGenericBlockCipher(var63);
               this.cipher = var64;
            } else if(this.modeName.startsWith("GCM")) {
               int var65 = this.baseEngine.getBlockSize();
               this.ivLength = var65;
               BlockCipher var66 = this.baseEngine;
               GCMBlockCipher var67 = new GCMBlockCipher(var66);
               JCEBlockCipher.AEADGenericBlockCipher var68 = new JCEBlockCipher.AEADGenericBlockCipher(var67);
               this.cipher = var68;
            } else {
               String var69 = "can\'t support mode " + var1;
               throw new NoSuchAlgorithmException(var69);
            }
         }
      } else {
         String var70 = "can\'t support mode " + var1;
         throw new NoSuchAlgorithmException(var70);
      }
   }

   protected void engineSetPadding(String var1) throws NoSuchPaddingException {
      String var2 = Strings.toUpperCase(var1);
      if(var2 != null) {
         if("NOPADDING".equals(var2)) {
            if(this.cipher.wrapOnNoPadding()) {
               BlockCipher var3 = this.cipher.getUnderlyingCipher();
               BufferedBlockCipher var4 = new BufferedBlockCipher(var3);
               JCEBlockCipher.BufferedGenericBlockCipher var5 = new JCEBlockCipher.BufferedGenericBlockCipher(var4);
               this.cipher = var5;
            }
         } else if("WITHCTS".equals(var2)) {
            BlockCipher var6 = this.cipher.getUnderlyingCipher();
            CTSBlockCipher var7 = new CTSBlockCipher(var6);
            JCEBlockCipher.BufferedGenericBlockCipher var8 = new JCEBlockCipher.BufferedGenericBlockCipher(var7);
            this.cipher = var8;
         } else {
            this.padded = (boolean)1;
            String var9 = this.modeName;
            if(this.isAEADModeName(var9)) {
               throw new NoSuchPaddingException("Only NoPadding can be used with AEAD modes.");
            } else if(!"PKCS5PADDING".equals(var2) && !"PKCS7PADDING".equals(var2)) {
               if("ZEROBYTEPADDING".equals(var2)) {
                  BlockCipher var12 = this.cipher.getUnderlyingCipher();
                  ZeroBytePadding var13 = new ZeroBytePadding();
                  JCEBlockCipher.BufferedGenericBlockCipher var14 = new JCEBlockCipher.BufferedGenericBlockCipher(var12, var13);
                  this.cipher = var14;
               } else if(!"ISO10126PADDING".equals(var2) && !"ISO10126-2PADDING".equals(var2)) {
                  if(!"X9.23PADDING".equals(var2) && !"X923PADDING".equals(var2)) {
                     if(!"ISO7816-4PADDING".equals(var2) && !"ISO9797-1PADDING".equals(var2)) {
                        if("TBCPADDING".equals(var2)) {
                           BlockCipher var24 = this.cipher.getUnderlyingCipher();
                           TBCPadding var25 = new TBCPadding();
                           JCEBlockCipher.BufferedGenericBlockCipher var26 = new JCEBlockCipher.BufferedGenericBlockCipher(var24, var25);
                           this.cipher = var26;
                        } else {
                           String var27 = "Padding " + var1 + " unknown.";
                           throw new NoSuchPaddingException(var27);
                        }
                     } else {
                        BlockCipher var21 = this.cipher.getUnderlyingCipher();
                        ISO7816d4Padding var22 = new ISO7816d4Padding();
                        JCEBlockCipher.BufferedGenericBlockCipher var23 = new JCEBlockCipher.BufferedGenericBlockCipher(var21, var22);
                        this.cipher = var23;
                     }
                  } else {
                     BlockCipher var18 = this.cipher.getUnderlyingCipher();
                     X923Padding var19 = new X923Padding();
                     JCEBlockCipher.BufferedGenericBlockCipher var20 = new JCEBlockCipher.BufferedGenericBlockCipher(var18, var19);
                     this.cipher = var20;
                  }
               } else {
                  BlockCipher var15 = this.cipher.getUnderlyingCipher();
                  ISO10126d2Padding var16 = new ISO10126d2Padding();
                  JCEBlockCipher.BufferedGenericBlockCipher var17 = new JCEBlockCipher.BufferedGenericBlockCipher(var15, var16);
                  this.cipher = var17;
               }
            } else {
               BlockCipher var10 = this.cipher.getUnderlyingCipher();
               JCEBlockCipher.BufferedGenericBlockCipher var11 = new JCEBlockCipher.BufferedGenericBlockCipher(var10);
               this.cipher = var11;
            }
         }
      } else {
         String var28 = "Padding " + var1 + " unknown.";
         throw new NoSuchPaddingException(var28);
      }
   }

   protected int engineUpdate(byte[] var1, int var2, int var3, byte[] var4, int var5) throws ShortBufferException {
      try {
         JCEBlockCipher.GenericBlockCipher var6 = this.cipher;
         int var12 = var6.processBytes(var1, var2, var3, var4, var5);
         return var12;
      } catch (DataLengthException var14) {
         String var13 = var14.getMessage();
         throw new ShortBufferException(var13);
      }
   }

   protected byte[] engineUpdate(byte[] var1, int var2, int var3) {
      JCEBlockCipher.GenericBlockCipher var4 = this.cipher;
      int var6 = var4.getUpdateOutputSize(var3);
      byte[] var13;
      if(var6 > 0) {
         byte[] var7 = new byte[var6];
         JCEBlockCipher.GenericBlockCipher var8 = this.cipher;
         int var12 = var8.processBytes(var1, var2, var3, var7, 0);
         if(var12 == 0) {
            var13 = null;
         } else {
            int var14 = var7.length;
            if(var12 != var14) {
               byte[] var15 = new byte[var12];
               System.arraycopy(var7, 0, var15, 0, var12);
               var13 = var15;
            } else {
               var13 = var7;
            }
         }
      } else {
         JCEBlockCipher.GenericBlockCipher var16 = this.cipher;
         var16.processBytes(var1, var2, var3, (byte[])null, 0);
         var13 = null;
      }

      return var13;
   }

   public static class AESCBC extends JCEBlockCipher {

      public AESCBC() {
         AESFastEngine var1 = new AESFastEngine();
         CBCBlockCipher var2 = new CBCBlockCipher(var1);
         super((BlockCipher)var2, 128);
      }
   }

   public static class PBEWithSHAAnd128BitRC2 extends JCEBlockCipher {

      public PBEWithSHAAnd128BitRC2() {
         RC2Engine var1 = new RC2Engine();
         CBCBlockCipher var2 = new CBCBlockCipher(var1);
         super(var2);
      }
   }

   public static class PBEWithSHAAndDES3Key extends JCEBlockCipher {

      public PBEWithSHAAndDES3Key() {
         DESedeEngine var1 = new DESedeEngine();
         CBCBlockCipher var2 = new CBCBlockCipher(var1);
         super(var2);
      }
   }

   public static class PBEWithSHAAndTwofish extends JCEBlockCipher {

      public PBEWithSHAAndTwofish() {
         TwofishEngine var1 = new TwofishEngine();
         CBCBlockCipher var2 = new CBCBlockCipher(var1);
         super(var2);
      }
   }

   public static class DES extends JCEBlockCipher {

      public DES() {
         DESEngine var1 = new DESEngine();
         super(var1);
      }
   }

   public static class Blowfish extends JCEBlockCipher {

      public Blowfish() {
         BlowfishEngine var1 = new BlowfishEngine();
         super(var1);
      }
   }

   private interface GenericBlockCipher {

      int doFinal(byte[] var1, int var2) throws IllegalStateException, InvalidCipherTextException;

      String getAlgorithmName();

      int getOutputSize(int var1);

      BlockCipher getUnderlyingCipher();

      int getUpdateOutputSize(int var1);

      void init(boolean var1, CipherParameters var2) throws IllegalArgumentException;

      int processByte(byte var1, byte[] var2, int var3) throws DataLengthException;

      int processBytes(byte[] var1, int var2, int var3, byte[] var4, int var5) throws DataLengthException;

      boolean wrapOnNoPadding();
   }

   public static class AESOFB extends JCEBlockCipher {

      public AESOFB() {
         AESFastEngine var1 = new AESFastEngine();
         OFBBlockCipher var2 = new OFBBlockCipher(var1, 128);
         super((BlockCipher)var2, 128);
      }
   }

   public static class DESede extends JCEBlockCipher {

      public DESede() {
         DESedeEngine var1 = new DESedeEngine();
         super(var1);
      }
   }

   public static class TEA extends JCEBlockCipher {

      public TEA() {
         TEAEngine var1 = new TEAEngine();
         super(var1);
      }
   }

   public static class SEED extends JCEBlockCipher {

      public SEED() {
         SEEDEngine var1 = new SEEDEngine();
         super(var1);
      }
   }

   public static class Rijndael extends JCEBlockCipher {

      public Rijndael() {
         RijndaelEngine var1 = new RijndaelEngine();
         super(var1);
      }
   }

   public static class PBEWithSHAAnd40BitRC2 extends JCEBlockCipher {

      public PBEWithSHAAnd40BitRC2() {
         RC2Engine var1 = new RC2Engine();
         CBCBlockCipher var2 = new CBCBlockCipher(var1);
         super(var2);
      }
   }

   public static class RC2CBC extends JCEBlockCipher {

      public RC2CBC() {
         RC2Engine var1 = new RC2Engine();
         CBCBlockCipher var2 = new CBCBlockCipher(var1);
         super((BlockCipher)var2, 64);
      }
   }

   public static class DESCBC extends JCEBlockCipher {

      public DESCBC() {
         DESEngine var1 = new DESEngine();
         CBCBlockCipher var2 = new CBCBlockCipher(var1);
         super((BlockCipher)var2, 64);
      }
   }

   public static class Skipjack extends JCEBlockCipher {

      public Skipjack() {
         SkipjackEngine var1 = new SkipjackEngine();
         super(var1);
      }
   }

   public static class Twofish extends JCEBlockCipher {

      public Twofish() {
         TwofishEngine var1 = new TwofishEngine();
         super(var1);
      }
   }

   public static class PBEWithAESCBC extends JCEBlockCipher {

      public PBEWithAESCBC() {
         AESFastEngine var1 = new AESFastEngine();
         CBCBlockCipher var2 = new CBCBlockCipher(var1);
         super(var2);
      }
   }

   public static class PBEWithMD5AndRC2 extends JCEBlockCipher {

      public PBEWithMD5AndRC2() {
         RC2Engine var1 = new RC2Engine();
         CBCBlockCipher var2 = new CBCBlockCipher(var1);
         super(var2);
      }
   }

   public static class AES extends JCEBlockCipher {

      public AES() {
         AESFastEngine var1 = new AESFastEngine();
         super(var1);
      }
   }

   private static class BufferedGenericBlockCipher implements JCEBlockCipher.GenericBlockCipher {

      private BufferedBlockCipher cipher;


      BufferedGenericBlockCipher(BlockCipher var1) {
         PaddedBufferedBlockCipher var2 = new PaddedBufferedBlockCipher(var1);
         this.cipher = var2;
      }

      BufferedGenericBlockCipher(BlockCipher var1, BlockCipherPadding var2) {
         PaddedBufferedBlockCipher var3 = new PaddedBufferedBlockCipher(var1, var2);
         this.cipher = var3;
      }

      BufferedGenericBlockCipher(BufferedBlockCipher var1) {
         this.cipher = var1;
      }

      public int doFinal(byte[] var1, int var2) throws IllegalStateException, InvalidCipherTextException {
         return this.cipher.doFinal(var1, var2);
      }

      public String getAlgorithmName() {
         return this.cipher.getUnderlyingCipher().getAlgorithmName();
      }

      public int getOutputSize(int var1) {
         return this.cipher.getOutputSize(var1);
      }

      public BlockCipher getUnderlyingCipher() {
         return this.cipher.getUnderlyingCipher();
      }

      public int getUpdateOutputSize(int var1) {
         return this.cipher.getUpdateOutputSize(var1);
      }

      public void init(boolean var1, CipherParameters var2) throws IllegalArgumentException {
         this.cipher.init(var1, var2);
      }

      public int processByte(byte var1, byte[] var2, int var3) throws DataLengthException {
         return this.cipher.processByte(var1, var2, var3);
      }

      public int processBytes(byte[] var1, int var2, int var3, byte[] var4, int var5) throws DataLengthException {
         BufferedBlockCipher var6 = this.cipher;
         return var6.processBytes(var1, var2, var3, var4, var5);
      }

      public boolean wrapOnNoPadding() {
         boolean var1;
         if(!(this.cipher instanceof CTSBlockCipher)) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }
   }

   public static class PBEWithMD5AndDES extends JCEBlockCipher {

      public PBEWithMD5AndDES() {
         DESEngine var1 = new DESEngine();
         CBCBlockCipher var2 = new CBCBlockCipher(var1);
         super(var2);
      }
   }

   public static class PBEWithSHA1AndDES extends JCEBlockCipher {

      public PBEWithSHA1AndDES() {
         DESEngine var1 = new DESEngine();
         CBCBlockCipher var2 = new CBCBlockCipher(var1);
         super(var2);
      }
   }

   public static class AESCFB extends JCEBlockCipher {

      public AESCFB() {
         AESFastEngine var1 = new AESFastEngine();
         CFBBlockCipher var2 = new CFBBlockCipher(var1, 128);
         super((BlockCipher)var2, 128);
      }
   }

   public static class BlowfishCBC extends JCEBlockCipher {

      public BlowfishCBC() {
         BlowfishEngine var1 = new BlowfishEngine();
         CBCBlockCipher var2 = new CBCBlockCipher(var1);
         super((BlockCipher)var2, 64);
      }
   }

   public static class PBEWithSHA1AndRC2 extends JCEBlockCipher {

      public PBEWithSHA1AndRC2() {
         RC2Engine var1 = new RC2Engine();
         CBCBlockCipher var2 = new CBCBlockCipher(var1);
         super(var2);
      }
   }

   public static class RC564 extends JCEBlockCipher {

      public RC564() {
         RC564Engine var1 = new RC564Engine();
         super(var1);
      }
   }

   public static class RC6 extends JCEBlockCipher {

      public RC6() {
         RC6Engine var1 = new RC6Engine();
         super(var1);
      }
   }

   public static class GOST28147cbc extends JCEBlockCipher {

      public GOST28147cbc() {
         GOST28147Engine var1 = new GOST28147Engine();
         CBCBlockCipher var2 = new CBCBlockCipher(var1);
         super((BlockCipher)var2, 64);
      }
   }

   public static class XTEA extends JCEBlockCipher {

      public XTEA() {
         XTEAEngine var1 = new XTEAEngine();
         super(var1);
      }
   }

   public static class CAST6 extends JCEBlockCipher {

      public CAST6() {
         CAST6Engine var1 = new CAST6Engine();
         super(var1);
      }
   }

   public static class Serpent extends JCEBlockCipher {

      public Serpent() {
         SerpentEngine var1 = new SerpentEngine();
         super(var1);
      }
   }

   public static class CAST5 extends JCEBlockCipher {

      public CAST5() {
         CAST5Engine var1 = new CAST5Engine();
         super(var1);
      }
   }

   public static class RC2 extends JCEBlockCipher {

      public RC2() {
         RC2Engine var1 = new RC2Engine();
         super(var1);
      }
   }

   public static class PBEWithSHAAndDES2Key extends JCEBlockCipher {

      public PBEWithSHAAndDES2Key() {
         DESedeEngine var1 = new DESedeEngine();
         CBCBlockCipher var2 = new CBCBlockCipher(var1);
         super(var2);
      }
   }

   public static class GOST28147 extends JCEBlockCipher {

      public GOST28147() {
         GOST28147Engine var1 = new GOST28147Engine();
         super(var1);
      }
   }

   public static class RC5 extends JCEBlockCipher {

      public RC5() {
         RC532Engine var1 = new RC532Engine();
         super(var1);
      }
   }

   private static class AEADGenericBlockCipher implements JCEBlockCipher.GenericBlockCipher {

      private AEADBlockCipher cipher;


      AEADGenericBlockCipher(AEADBlockCipher var1) {
         this.cipher = var1;
      }

      public int doFinal(byte[] var1, int var2) throws IllegalStateException, InvalidCipherTextException {
         return this.cipher.doFinal(var1, var2);
      }

      public String getAlgorithmName() {
         return this.cipher.getUnderlyingCipher().getAlgorithmName();
      }

      public int getOutputSize(int var1) {
         return this.cipher.getOutputSize(var1);
      }

      public BlockCipher getUnderlyingCipher() {
         return this.cipher.getUnderlyingCipher();
      }

      public int getUpdateOutputSize(int var1) {
         return this.cipher.getUpdateOutputSize(var1);
      }

      public void init(boolean var1, CipherParameters var2) throws IllegalArgumentException {
         this.cipher.init(var1, var2);
      }

      public int processByte(byte var1, byte[] var2, int var3) throws DataLengthException {
         return this.cipher.processByte(var1, var2, var3);
      }

      public int processBytes(byte[] var1, int var2, int var3, byte[] var4, int var5) throws DataLengthException {
         AEADBlockCipher var6 = this.cipher;
         return var6.processBytes(var1, var2, var3, var4, var5);
      }

      public boolean wrapOnNoPadding() {
         return false;
      }
   }

   public static class DESedeCBC extends JCEBlockCipher {

      public DESedeCBC() {
         DESedeEngine var1 = new DESedeEngine();
         CBCBlockCipher var2 = new CBCBlockCipher(var1);
         super((BlockCipher)var2, 64);
      }
   }

   public static class CAST5CBC extends JCEBlockCipher {

      public CAST5CBC() {
         CAST5Engine var1 = new CAST5Engine();
         CBCBlockCipher var2 = new CBCBlockCipher(var1);
         super((BlockCipher)var2, 64);
      }
   }
}
