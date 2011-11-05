package myorg.bouncycastle.crypto.prng;

import myorg.bouncycastle.crypto.prng.RandomGenerator;

public class VMPCRandomGenerator implements RandomGenerator {

   private byte[] P;
   private byte n = 0;
   private byte s;


   public VMPCRandomGenerator() {
      byte[] var1 = new byte[]{(byte)187, (byte)44, (byte)98, (byte)127, (byte)181, (byte)170, (byte)212, (byte)13, (byte)129, (byte)254, (byte)178, (byte)130, (byte)203, (byte)160, (byte)161, (byte)8, (byte)24, (byte)113, (byte)86, (byte)232, (byte)73, (byte)2, (byte)16, (byte)196, (byte)222, (byte)53, (byte)165, (byte)236, (byte)128, (byte)18, (byte)184, (byte)105, (byte)218, (byte)47, (byte)117, (byte)204, (byte)162, (byte)9, (byte)54, (byte)3, (byte)97, (byte)45, (byte)253, (byte)224, (byte)221, (byte)5, (byte)67, (byte)144, (byte)173, (byte)200, (byte)225, (byte)175, (byte)87, (byte)155, (byte)76, (byte)216, (byte)81, (byte)174, (byte)80, (byte)133, (byte)60, (byte)10, (byte)228, (byte)243, (byte)156, (byte)38, (byte)35, (byte)83, (byte)201, (byte)131, (byte)151, (byte)70, (byte)177, (byte)153, (byte)100, (byte)49, (byte)119, (byte)213, (byte)29, (byte)214, (byte)120, (byte)189, (byte)94, (byte)176, (byte)138, (byte)34, (byte)56, (byte)248, (byte)104, (byte)43, (byte)42, (byte)197, (byte)211, (byte)247, (byte)188, (byte)111, (byte)223, (byte)4, (byte)229, (byte)149, (byte)62, (byte)37, (byte)134, (byte)166, (byte)11, (byte)143, (byte)241, (byte)36, (byte)14, (byte)215, (byte)64, (byte)179, (byte)207, (byte)126, (byte)6, (byte)21, (byte)154, (byte)77, (byte)28, (byte)163, (byte)219, (byte)50, (byte)146, (byte)88, (byte)17, (byte)39, (byte)244, (byte)89, (byte)208, (byte)78, (byte)106, (byte)23, (byte)91, (byte)172, (byte)255, (byte)7, (byte)192, (byte)101, (byte)121, (byte)252, (byte)199, (byte)205, (byte)118, (byte)66, (byte)93, (byte)231, (byte)58, (byte)52, (byte)122, (byte)48, (byte)40, (byte)15, (byte)115, (byte)1, (byte)249, (byte)209, (byte)210, (byte)25, (byte)233, (byte)145, (byte)185, (byte)90, (byte)237, (byte)65, (byte)109, (byte)180, (byte)195, (byte)158, (byte)191, (byte)99, (byte)250, (byte)31, (byte)51, (byte)96, (byte)71, (byte)137, (byte)240, (byte)150, (byte)26, (byte)95, (byte)147, (byte)61, (byte)55, (byte)75, (byte)217, (byte)168, (byte)193, (byte)27, (byte)246, (byte)57, (byte)139, (byte)183, (byte)12, (byte)32, (byte)206, (byte)136, (byte)110, (byte)182, (byte)116, (byte)142, (byte)141, (byte)22, (byte)41, (byte)242, (byte)135, (byte)245, (byte)235, (byte)112, (byte)227, (byte)251, (byte)85, (byte)159, (byte)198, (byte)68, (byte)74, (byte)69, (byte)125, (byte)226, (byte)107, (byte)92, (byte)108, (byte)102, (byte)169, (byte)140, (byte)238, (byte)132, (byte)19, (byte)167, (byte)30, (byte)157, (byte)220, (byte)103, (byte)72, (byte)186, (byte)46, (byte)230, (byte)164, (byte)171, (byte)124, (byte)148, (byte)0, (byte)33, (byte)239, (byte)234, (byte)190, (byte)202, (byte)114, (byte)79, (byte)82, (byte)152, (byte)63, (byte)194, (byte)20, (byte)123, (byte)59, (byte)84};
      this.P = var1;
      this.s = -66;
   }

   public void addSeedMaterial(long var1) {
      byte[] var3 = new byte[4];
      byte var4 = (byte)((int)(255L & var1));
      var3[3] = var4;
      byte var5 = (byte)((int)((65280L & var1) >> 8));
      var3[2] = var5;
      byte var6 = (byte)((int)((16711680L & var1) >> 16));
      var3[1] = var6;
      byte var7 = (byte)((int)((-16777216L & var1) >> 24));
      var3[0] = var7;
      this.addSeedMaterial(var3);
   }

   public void addSeedMaterial(byte[] var1) {
      int var2 = 0;

      while(true) {
         int var3 = var1.length;
         if(var2 >= var3) {
            return;
         }

         byte[] var4 = this.P;
         byte var5 = this.s;
         byte[] var6 = this.P;
         int var7 = this.n & 255;
         byte var8 = var6[var7];
         int var9 = var5 + var8;
         byte var10 = var1[var2];
         int var11 = var9 + var10 & 255;
         byte var12 = var4[var11];
         this.s = var12;
         byte[] var13 = this.P;
         int var14 = this.n & 255;
         byte var15 = var13[var14];
         byte[] var16 = this.P;
         int var17 = this.n & 255;
         byte[] var18 = this.P;
         int var19 = this.s & 255;
         byte var20 = var18[var19];
         var16[var17] = var20;
         byte[] var21 = this.P;
         int var22 = this.s & 255;
         var21[var22] = var15;
         byte var23 = (byte)(this.n + 1 & 255);
         this.n = var23;
         ++var2;
      }
   }

   public void nextBytes(byte[] var1) {
      int var2 = var1.length;
      this.nextBytes(var1, 0, var2);
   }

   public void nextBytes(byte[] param1, int param2, int param3) {
      // $FF: Couldn't be decompiled
   }
}
