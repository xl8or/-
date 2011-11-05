package myorg.bouncycastle.crypto.digests;

import myorg.bouncycastle.crypto.ExtendedDigest;

public class MD2Digest implements ExtendedDigest {

   private static final int DIGEST_LENGTH = 16;
   private static final byte[] S = new byte[]{(byte)41, (byte)46, (byte)67, (byte)201, (byte)162, (byte)216, (byte)124, (byte)1, (byte)61, (byte)54, (byte)84, (byte)161, (byte)236, (byte)240, (byte)6, (byte)19, (byte)98, (byte)167, (byte)5, (byte)243, (byte)192, (byte)199, (byte)115, (byte)140, (byte)152, (byte)147, (byte)43, (byte)217, (byte)188, (byte)76, (byte)130, (byte)202, (byte)30, (byte)155, (byte)87, (byte)60, (byte)253, (byte)212, (byte)224, (byte)22, (byte)103, (byte)66, (byte)111, (byte)24, (byte)138, (byte)23, (byte)229, (byte)18, (byte)190, (byte)78, (byte)196, (byte)214, (byte)218, (byte)158, (byte)222, (byte)73, (byte)160, (byte)251, (byte)245, (byte)142, (byte)187, (byte)47, (byte)238, (byte)122, (byte)169, (byte)104, (byte)121, (byte)145, (byte)21, (byte)178, (byte)7, (byte)63, (byte)148, (byte)194, (byte)16, (byte)137, (byte)11, (byte)34, (byte)95, (byte)33, (byte)128, (byte)127, (byte)93, (byte)154, (byte)90, (byte)144, (byte)50, (byte)39, (byte)53, (byte)62, (byte)204, (byte)231, (byte)191, (byte)247, (byte)151, (byte)3, (byte)255, (byte)25, (byte)48, (byte)179, (byte)72, (byte)165, (byte)181, (byte)209, (byte)215, (byte)94, (byte)146, (byte)42, (byte)172, (byte)86, (byte)170, (byte)198, (byte)79, (byte)184, (byte)56, (byte)210, (byte)150, (byte)164, (byte)125, (byte)182, (byte)118, (byte)252, (byte)107, (byte)226, (byte)156, (byte)116, (byte)4, (byte)241, (byte)69, (byte)157, (byte)112, (byte)89, (byte)100, (byte)113, (byte)135, (byte)32, (byte)134, (byte)91, (byte)207, (byte)101, (byte)230, (byte)45, (byte)168, (byte)2, (byte)27, (byte)96, (byte)37, (byte)173, (byte)174, (byte)176, (byte)185, (byte)246, (byte)28, (byte)70, (byte)97, (byte)105, (byte)52, (byte)64, (byte)126, (byte)15, (byte)85, (byte)71, (byte)163, (byte)35, (byte)221, (byte)81, (byte)175, (byte)58, (byte)195, (byte)92, (byte)249, (byte)206, (byte)186, (byte)197, (byte)234, (byte)38, (byte)44, (byte)83, (byte)13, (byte)110, (byte)133, (byte)40, (byte)132, (byte)9, (byte)211, (byte)223, (byte)205, (byte)244, (byte)65, (byte)129, (byte)77, (byte)82, (byte)106, (byte)220, (byte)55, (byte)200, (byte)108, (byte)193, (byte)171, (byte)250, (byte)36, (byte)225, (byte)123, (byte)8, (byte)12, (byte)189, (byte)177, (byte)74, (byte)120, (byte)136, (byte)149, (byte)139, (byte)227, (byte)99, (byte)232, (byte)109, (byte)233, (byte)203, (byte)213, (byte)254, (byte)59, (byte)0, (byte)29, (byte)57, (byte)242, (byte)239, (byte)183, (byte)14, (byte)102, (byte)88, (byte)208, (byte)228, (byte)166, (byte)119, (byte)114, (byte)248, (byte)235, (byte)117, (byte)75, (byte)10, (byte)49, (byte)68, (byte)80, (byte)180, (byte)143, (byte)237, (byte)31, (byte)26, (byte)219, (byte)153, (byte)141, (byte)51, (byte)159, (byte)17, (byte)131, (byte)20};
   private byte[] C;
   private int COff;
   private byte[] M;
   private byte[] X;
   private int mOff;
   private int xOff;


   public MD2Digest() {
      byte[] var1 = new byte[48];
      this.X = var1;
      byte[] var2 = new byte[16];
      this.M = var2;
      byte[] var3 = new byte[16];
      this.C = var3;
      this.reset();
   }

   public MD2Digest(MD2Digest var1) {
      byte[] var2 = new byte[48];
      this.X = var2;
      byte[] var3 = new byte[16];
      this.M = var3;
      byte[] var4 = new byte[16];
      this.C = var4;
      byte[] var5 = var1.X;
      byte[] var6 = this.X;
      int var7 = var1.X.length;
      System.arraycopy(var5, 0, var6, 0, var7);
      int var8 = var1.xOff;
      this.xOff = var8;
      byte[] var9 = var1.M;
      byte[] var10 = this.M;
      int var11 = var1.M.length;
      System.arraycopy(var9, 0, var10, 0, var11);
      int var12 = var1.mOff;
      this.mOff = var12;
      byte[] var13 = var1.C;
      byte[] var14 = this.C;
      int var15 = var1.C.length;
      System.arraycopy(var13, 0, var14, 0, var15);
      int var16 = var1.COff;
      this.COff = var16;
   }

   public int doFinal(byte[] var1, int var2) {
      int var3 = this.M.length;
      int var4 = this.mOff;
      byte var5 = (byte)(var3 - var4);
      int var6 = this.mOff;

      while(true) {
         int var7 = this.M.length;
         if(var6 >= var7) {
            byte[] var8 = this.M;
            this.processCheckSum(var8);
            byte[] var9 = this.M;
            this.processBlock(var9);
            byte[] var10 = this.C;
            this.processBlock(var10);
            byte[] var11 = this.X;
            int var12 = this.xOff;
            System.arraycopy(var11, var12, var1, var2, 16);
            this.reset();
            return 16;
         }

         this.M[var6] = var5;
         ++var6;
      }
   }

   public String getAlgorithmName() {
      return "MD2";
   }

   public int getByteLength() {
      return 16;
   }

   public int getDigestSize() {
      return 16;
   }

   protected void processBlock(byte[] var1) {
      for(int var2 = 0; var2 < 16; ++var2) {
         byte[] var3 = this.X;
         int var4 = var2 + 16;
         byte var5 = var1[var2];
         var3[var4] = var5;
         byte[] var6 = this.X;
         int var7 = var2 + 32;
         byte var8 = var1[var2];
         byte var9 = this.X[var2];
         byte var10 = (byte)(var8 ^ var9);
         var6[var7] = var10;
      }

      int var11 = 0;

      for(int var12 = 0; var12 < 18; ++var12) {
         for(int var13 = 0; var13 < 48; ++var13) {
            byte[] var14 = this.X;
            byte var15 = var14[var13];
            byte var16 = S[var11];
            byte var17 = (byte)(var15 ^ var16);
            var14[var13] = var17;
            var11 = var17 & 255;
         }

         var11 = (var11 + var12) % 256;
      }

   }

   protected void processCheckSum(byte[] var1) {
      byte var2 = this.C[15];

      for(int var3 = 0; var3 < 16; ++var3) {
         byte[] var4 = this.C;
         byte var5 = var4[var3];
         byte[] var6 = S;
         int var7 = (var1[var3] ^ var2) & 255;
         byte var8 = var6[var7];
         byte var9 = (byte)(var5 ^ var8);
         var4[var3] = var9;
         var2 = this.C[var3];
      }

   }

   public void reset() {
      this.xOff = 0;
      int var1 = 0;

      while(true) {
         int var2 = this.X.length;
         if(var1 == var2) {
            this.mOff = 0;
            int var3 = 0;

            while(true) {
               int var4 = this.M.length;
               if(var3 == var4) {
                  this.COff = 0;
                  int var5 = 0;

                  while(true) {
                     int var6 = this.C.length;
                     if(var5 == var6) {
                        return;
                     }

                     this.C[var5] = 0;
                     ++var5;
                  }
               }

               this.M[var3] = 0;
               ++var3;
            }
         }

         this.X[var1] = 0;
         ++var1;
      }
   }

   public void update(byte var1) {
      byte[] var2 = this.M;
      int var3 = this.mOff;
      int var4 = var3 + 1;
      this.mOff = var4;
      var2[var3] = var1;
      if(this.mOff == 16) {
         byte[] var5 = this.M;
         this.processCheckSum(var5);
         byte[] var6 = this.M;
         this.processBlock(var6);
         this.mOff = 0;
      }
   }

   public void update(byte[] var1, int var2, int var3) {
      while(this.mOff != 0 && var3 > 0) {
         byte var4 = var1[var2];
         this.update(var4);
         ++var2;
         var3 += -1;
      }

      while(var3 > 16) {
         byte[] var5 = this.M;
         System.arraycopy(var1, var2, var5, 0, 16);
         byte[] var6 = this.M;
         this.processCheckSum(var6);
         byte[] var7 = this.M;
         this.processBlock(var7);
         var3 += -16;
         var2 += 16;
      }

      while(var3 > 0) {
         byte var8 = var1[var2];
         this.update(var8);
         ++var2;
         var3 += -1;
      }

   }
}
