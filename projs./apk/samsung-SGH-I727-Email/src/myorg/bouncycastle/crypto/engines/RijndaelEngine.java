package myorg.bouncycastle.crypto.engines;

import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.params.KeyParameter;

public class RijndaelEngine implements BlockCipher {

   private static final int MAXKC = 64;
   private static final int MAXROUNDS = 14;
   private static final byte[] S = new byte[]{(byte)99, (byte)124, (byte)119, (byte)123, (byte)242, (byte)107, (byte)111, (byte)197, (byte)48, (byte)1, (byte)103, (byte)43, (byte)254, (byte)215, (byte)171, (byte)118, (byte)202, (byte)130, (byte)201, (byte)125, (byte)250, (byte)89, (byte)71, (byte)240, (byte)173, (byte)212, (byte)162, (byte)175, (byte)156, (byte)164, (byte)114, (byte)192, (byte)183, (byte)253, (byte)147, (byte)38, (byte)54, (byte)63, (byte)247, (byte)204, (byte)52, (byte)165, (byte)229, (byte)241, (byte)113, (byte)216, (byte)49, (byte)21, (byte)4, (byte)199, (byte)35, (byte)195, (byte)24, (byte)150, (byte)5, (byte)154, (byte)7, (byte)18, (byte)128, (byte)226, (byte)235, (byte)39, (byte)178, (byte)117, (byte)9, (byte)131, (byte)44, (byte)26, (byte)27, (byte)110, (byte)90, (byte)160, (byte)82, (byte)59, (byte)214, (byte)179, (byte)41, (byte)227, (byte)47, (byte)132, (byte)83, (byte)209, (byte)0, (byte)237, (byte)32, (byte)252, (byte)177, (byte)91, (byte)106, (byte)203, (byte)190, (byte)57, (byte)74, (byte)76, (byte)88, (byte)207, (byte)208, (byte)239, (byte)170, (byte)251, (byte)67, (byte)77, (byte)51, (byte)133, (byte)69, (byte)249, (byte)2, (byte)127, (byte)80, (byte)60, (byte)159, (byte)168, (byte)81, (byte)163, (byte)64, (byte)143, (byte)146, (byte)157, (byte)56, (byte)245, (byte)188, (byte)182, (byte)218, (byte)33, (byte)16, (byte)255, (byte)243, (byte)210, (byte)205, (byte)12, (byte)19, (byte)236, (byte)95, (byte)151, (byte)68, (byte)23, (byte)196, (byte)167, (byte)126, (byte)61, (byte)100, (byte)93, (byte)25, (byte)115, (byte)96, (byte)129, (byte)79, (byte)220, (byte)34, (byte)42, (byte)144, (byte)136, (byte)70, (byte)238, (byte)184, (byte)20, (byte)222, (byte)94, (byte)11, (byte)219, (byte)224, (byte)50, (byte)58, (byte)10, (byte)73, (byte)6, (byte)36, (byte)92, (byte)194, (byte)211, (byte)172, (byte)98, (byte)145, (byte)149, (byte)228, (byte)121, (byte)231, (byte)200, (byte)55, (byte)109, (byte)141, (byte)213, (byte)78, (byte)169, (byte)108, (byte)86, (byte)244, (byte)234, (byte)101, (byte)122, (byte)174, (byte)8, (byte)186, (byte)120, (byte)37, (byte)46, (byte)28, (byte)166, (byte)180, (byte)198, (byte)232, (byte)221, (byte)116, (byte)31, (byte)75, (byte)189, (byte)139, (byte)138, (byte)112, (byte)62, (byte)181, (byte)102, (byte)72, (byte)3, (byte)246, (byte)14, (byte)97, (byte)53, (byte)87, (byte)185, (byte)134, (byte)193, (byte)29, (byte)158, (byte)225, (byte)248, (byte)152, (byte)17, (byte)105, (byte)217, (byte)142, (byte)148, (byte)155, (byte)30, (byte)135, (byte)233, (byte)206, (byte)85, (byte)40, (byte)223, (byte)140, (byte)161, (byte)137, (byte)13, (byte)191, (byte)230, (byte)66, (byte)104, (byte)65, (byte)153, (byte)45, (byte)15, (byte)176, (byte)84, (byte)187, (byte)22};
   private static final byte[] Si = new byte[]{(byte)82, (byte)9, (byte)106, (byte)213, (byte)48, (byte)54, (byte)165, (byte)56, (byte)191, (byte)64, (byte)163, (byte)158, (byte)129, (byte)243, (byte)215, (byte)251, (byte)124, (byte)227, (byte)57, (byte)130, (byte)155, (byte)47, (byte)255, (byte)135, (byte)52, (byte)142, (byte)67, (byte)68, (byte)196, (byte)222, (byte)233, (byte)203, (byte)84, (byte)123, (byte)148, (byte)50, (byte)166, (byte)194, (byte)35, (byte)61, (byte)238, (byte)76, (byte)149, (byte)11, (byte)66, (byte)250, (byte)195, (byte)78, (byte)8, (byte)46, (byte)161, (byte)102, (byte)40, (byte)217, (byte)36, (byte)178, (byte)118, (byte)91, (byte)162, (byte)73, (byte)109, (byte)139, (byte)209, (byte)37, (byte)114, (byte)248, (byte)246, (byte)100, (byte)134, (byte)104, (byte)152, (byte)22, (byte)212, (byte)164, (byte)92, (byte)204, (byte)93, (byte)101, (byte)182, (byte)146, (byte)108, (byte)112, (byte)72, (byte)80, (byte)253, (byte)237, (byte)185, (byte)218, (byte)94, (byte)21, (byte)70, (byte)87, (byte)167, (byte)141, (byte)157, (byte)132, (byte)144, (byte)216, (byte)171, (byte)0, (byte)140, (byte)188, (byte)211, (byte)10, (byte)247, (byte)228, (byte)88, (byte)5, (byte)184, (byte)179, (byte)69, (byte)6, (byte)208, (byte)44, (byte)30, (byte)143, (byte)202, (byte)63, (byte)15, (byte)2, (byte)193, (byte)175, (byte)189, (byte)3, (byte)1, (byte)19, (byte)138, (byte)107, (byte)58, (byte)145, (byte)17, (byte)65, (byte)79, (byte)103, (byte)220, (byte)234, (byte)151, (byte)242, (byte)207, (byte)206, (byte)240, (byte)180, (byte)230, (byte)115, (byte)150, (byte)172, (byte)116, (byte)34, (byte)231, (byte)173, (byte)53, (byte)133, (byte)226, (byte)249, (byte)55, (byte)232, (byte)28, (byte)117, (byte)223, (byte)110, (byte)71, (byte)241, (byte)26, (byte)113, (byte)29, (byte)41, (byte)197, (byte)137, (byte)111, (byte)183, (byte)98, (byte)14, (byte)170, (byte)24, (byte)190, (byte)27, (byte)252, (byte)86, (byte)62, (byte)75, (byte)198, (byte)210, (byte)121, (byte)32, (byte)154, (byte)219, (byte)192, (byte)254, (byte)120, (byte)205, (byte)90, (byte)244, (byte)31, (byte)221, (byte)168, (byte)51, (byte)136, (byte)7, (byte)199, (byte)49, (byte)177, (byte)18, (byte)16, (byte)89, (byte)39, (byte)128, (byte)236, (byte)95, (byte)96, (byte)81, (byte)127, (byte)169, (byte)25, (byte)181, (byte)74, (byte)13, (byte)45, (byte)229, (byte)122, (byte)159, (byte)147, (byte)201, (byte)156, (byte)239, (byte)160, (byte)224, (byte)59, (byte)77, (byte)174, (byte)42, (byte)245, (byte)176, (byte)200, (byte)235, (byte)187, (byte)60, (byte)131, (byte)83, (byte)153, (byte)97, (byte)23, (byte)43, (byte)4, (byte)126, (byte)186, (byte)119, (byte)214, (byte)38, (byte)225, (byte)105, (byte)20, (byte)99, (byte)85, (byte)33, (byte)12, (byte)125};
   private static final byte[] aLogtable = new byte[]{(byte)0, (byte)3, (byte)5, (byte)15, (byte)17, (byte)51, (byte)85, (byte)255, (byte)26, (byte)46, (byte)114, (byte)150, (byte)161, (byte)248, (byte)19, (byte)53, (byte)95, (byte)225, (byte)56, (byte)72, (byte)216, (byte)115, (byte)149, (byte)164, (byte)247, (byte)2, (byte)6, (byte)10, (byte)30, (byte)34, (byte)102, (byte)170, (byte)229, (byte)52, (byte)92, (byte)228, (byte)55, (byte)89, (byte)235, (byte)38, (byte)106, (byte)190, (byte)217, (byte)112, (byte)144, (byte)171, (byte)230, (byte)49, (byte)83, (byte)245, (byte)4, (byte)12, (byte)20, (byte)60, (byte)68, (byte)204, (byte)79, (byte)209, (byte)104, (byte)184, (byte)211, (byte)110, (byte)178, (byte)205, (byte)76, (byte)212, (byte)103, (byte)169, (byte)224, (byte)59, (byte)77, (byte)215, (byte)98, (byte)166, (byte)241, (byte)8, (byte)24, (byte)40, (byte)120, (byte)136, (byte)131, (byte)158, (byte)185, (byte)208, (byte)107, (byte)189, (byte)220, (byte)127, (byte)129, (byte)152, (byte)179, (byte)206, (byte)73, (byte)219, (byte)118, (byte)154, (byte)181, (byte)196, (byte)87, (byte)249, (byte)16, (byte)48, (byte)80, (byte)240, (byte)11, (byte)29, (byte)39, (byte)105, (byte)187, (byte)214, (byte)97, (byte)163, (byte)254, (byte)25, (byte)43, (byte)125, (byte)135, (byte)146, (byte)173, (byte)236, (byte)47, (byte)113, (byte)147, (byte)174, (byte)233, (byte)32, (byte)96, (byte)160, (byte)251, (byte)22, (byte)58, (byte)78, (byte)210, (byte)109, (byte)183, (byte)194, (byte)93, (byte)231, (byte)50, (byte)86, (byte)250, (byte)21, (byte)63, (byte)65, (byte)195, (byte)94, (byte)226, (byte)61, (byte)71, (byte)201, (byte)64, (byte)192, (byte)91, (byte)237, (byte)44, (byte)116, (byte)156, (byte)191, (byte)218, (byte)117, (byte)159, (byte)186, (byte)213, (byte)100, (byte)172, (byte)239, (byte)42, (byte)126, (byte)130, (byte)157, (byte)188, (byte)223, (byte)122, (byte)142, (byte)137, (byte)128, (byte)155, (byte)182, (byte)193, (byte)88, (byte)232, (byte)35, (byte)101, (byte)175, (byte)234, (byte)37, (byte)111, (byte)177, (byte)200, (byte)67, (byte)197, (byte)84, (byte)252, (byte)31, (byte)33, (byte)99, (byte)165, (byte)244, (byte)7, (byte)9, (byte)27, (byte)45, (byte)119, (byte)153, (byte)176, (byte)203, (byte)70, (byte)202, (byte)69, (byte)207, (byte)74, (byte)222, (byte)121, (byte)139, (byte)134, (byte)145, (byte)168, (byte)227, (byte)62, (byte)66, (byte)198, (byte)81, (byte)243, (byte)14, (byte)18, (byte)54, (byte)90, (byte)238, (byte)41, (byte)123, (byte)141, (byte)140, (byte)143, (byte)138, (byte)133, (byte)148, (byte)167, (byte)242, (byte)13, (byte)23, (byte)57, (byte)75, (byte)221, (byte)124, (byte)132, (byte)151, (byte)162, (byte)253, (byte)28, (byte)36, (byte)108, (byte)180, (byte)199, (byte)82, (byte)246, (byte)1, (byte)3, (byte)5, (byte)15, (byte)17, (byte)51, (byte)85, (byte)255, (byte)26, (byte)46, (byte)114, (byte)150, (byte)161, (byte)248, (byte)19, (byte)53, (byte)95, (byte)225, (byte)56, (byte)72, (byte)216, (byte)115, (byte)149, (byte)164, (byte)247, (byte)2, (byte)6, (byte)10, (byte)30, (byte)34, (byte)102, (byte)170, (byte)229, (byte)52, (byte)92, (byte)228, (byte)55, (byte)89, (byte)235, (byte)38, (byte)106, (byte)190, (byte)217, (byte)112, (byte)144, (byte)171, (byte)230, (byte)49, (byte)83, (byte)245, (byte)4, (byte)12, (byte)20, (byte)60, (byte)68, (byte)204, (byte)79, (byte)209, (byte)104, (byte)184, (byte)211, (byte)110, (byte)178, (byte)205, (byte)76, (byte)212, (byte)103, (byte)169, (byte)224, (byte)59, (byte)77, (byte)215, (byte)98, (byte)166, (byte)241, (byte)8, (byte)24, (byte)40, (byte)120, (byte)136, (byte)131, (byte)158, (byte)185, (byte)208, (byte)107, (byte)189, (byte)220, (byte)127, (byte)129, (byte)152, (byte)179, (byte)206, (byte)73, (byte)219, (byte)118, (byte)154, (byte)181, (byte)196, (byte)87, (byte)249, (byte)16, (byte)48, (byte)80, (byte)240, (byte)11, (byte)29, (byte)39, (byte)105, (byte)187, (byte)214, (byte)97, (byte)163, (byte)254, (byte)25, (byte)43, (byte)125, (byte)135, (byte)146, (byte)173, (byte)236, (byte)47, (byte)113, (byte)147, (byte)174, (byte)233, (byte)32, (byte)96, (byte)160, (byte)251, (byte)22, (byte)58, (byte)78, (byte)210, (byte)109, (byte)183, (byte)194, (byte)93, (byte)231, (byte)50, (byte)86, (byte)250, (byte)21, (byte)63, (byte)65, (byte)195, (byte)94, (byte)226, (byte)61, (byte)71, (byte)201, (byte)64, (byte)192, (byte)91, (byte)237, (byte)44, (byte)116, (byte)156, (byte)191, (byte)218, (byte)117, (byte)159, (byte)186, (byte)213, (byte)100, (byte)172, (byte)239, (byte)42, (byte)126, (byte)130, (byte)157, (byte)188, (byte)223, (byte)122, (byte)142, (byte)137, (byte)128, (byte)155, (byte)182, (byte)193, (byte)88, (byte)232, (byte)35, (byte)101, (byte)175, (byte)234, (byte)37, (byte)111, (byte)177, (byte)200, (byte)67, (byte)197, (byte)84, (byte)252, (byte)31, (byte)33, (byte)99, (byte)165, (byte)244, (byte)7, (byte)9, (byte)27, (byte)45, (byte)119, (byte)153, (byte)176, (byte)203, (byte)70, (byte)202, (byte)69, (byte)207, (byte)74, (byte)222, (byte)121, (byte)139, (byte)134, (byte)145, (byte)168, (byte)227, (byte)62, (byte)66, (byte)198, (byte)81, (byte)243, (byte)14, (byte)18, (byte)54, (byte)90, (byte)238, (byte)41, (byte)123, (byte)141, (byte)140, (byte)143, (byte)138, (byte)133, (byte)148, (byte)167, (byte)242, (byte)13, (byte)23, (byte)57, (byte)75, (byte)221, (byte)124, (byte)132, (byte)151, (byte)162, (byte)253, (byte)28, (byte)36, (byte)108, (byte)180, (byte)199, (byte)82, (byte)246, (byte)1};
   private static final byte[] logtable = new byte[]{(byte)0, (byte)0, (byte)25, (byte)1, (byte)50, (byte)2, (byte)26, (byte)198, (byte)75, (byte)199, (byte)27, (byte)104, (byte)51, (byte)238, (byte)223, (byte)3, (byte)100, (byte)4, (byte)224, (byte)14, (byte)52, (byte)141, (byte)129, (byte)239, (byte)76, (byte)113, (byte)8, (byte)200, (byte)248, (byte)105, (byte)28, (byte)193, (byte)125, (byte)194, (byte)29, (byte)181, (byte)249, (byte)185, (byte)39, (byte)106, (byte)77, (byte)228, (byte)166, (byte)114, (byte)154, (byte)201, (byte)9, (byte)120, (byte)101, (byte)47, (byte)138, (byte)5, (byte)33, (byte)15, (byte)225, (byte)36, (byte)18, (byte)240, (byte)130, (byte)69, (byte)53, (byte)147, (byte)218, (byte)142, (byte)150, (byte)143, (byte)219, (byte)189, (byte)54, (byte)208, (byte)206, (byte)148, (byte)19, (byte)92, (byte)210, (byte)241, (byte)64, (byte)70, (byte)131, (byte)56, (byte)102, (byte)221, (byte)253, (byte)48, (byte)191, (byte)6, (byte)139, (byte)98, (byte)179, (byte)37, (byte)226, (byte)152, (byte)34, (byte)136, (byte)145, (byte)16, (byte)126, (byte)110, (byte)72, (byte)195, (byte)163, (byte)182, (byte)30, (byte)66, (byte)58, (byte)107, (byte)40, (byte)84, (byte)250, (byte)133, (byte)61, (byte)186, (byte)43, (byte)121, (byte)10, (byte)21, (byte)155, (byte)159, (byte)94, (byte)202, (byte)78, (byte)212, (byte)172, (byte)229, (byte)243, (byte)115, (byte)167, (byte)87, (byte)175, (byte)88, (byte)168, (byte)80, (byte)244, (byte)234, (byte)214, (byte)116, (byte)79, (byte)174, (byte)233, (byte)213, (byte)231, (byte)230, (byte)173, (byte)232, (byte)44, (byte)215, (byte)117, (byte)122, (byte)235, (byte)22, (byte)11, (byte)245, (byte)89, (byte)203, (byte)95, (byte)176, (byte)156, (byte)169, (byte)81, (byte)160, (byte)127, (byte)12, (byte)246, (byte)111, (byte)23, (byte)196, (byte)73, (byte)236, (byte)216, (byte)67, (byte)31, (byte)45, (byte)164, (byte)118, (byte)123, (byte)183, (byte)204, (byte)187, (byte)62, (byte)90, (byte)251, (byte)96, (byte)177, (byte)134, (byte)59, (byte)82, (byte)161, (byte)108, (byte)170, (byte)85, (byte)41, (byte)157, (byte)151, (byte)178, (byte)135, (byte)144, (byte)97, (byte)190, (byte)220, (byte)252, (byte)188, (byte)149, (byte)207, (byte)205, (byte)55, (byte)63, (byte)91, (byte)209, (byte)83, (byte)57, (byte)132, (byte)60, (byte)65, (byte)162, (byte)109, (byte)71, (byte)20, (byte)42, (byte)158, (byte)93, (byte)86, (byte)242, (byte)211, (byte)171, (byte)68, (byte)17, (byte)146, (byte)217, (byte)35, (byte)32, (byte)46, (byte)137, (byte)180, (byte)124, (byte)184, (byte)38, (byte)119, (byte)153, (byte)227, (byte)165, (byte)103, (byte)74, (byte)237, (byte)222, (byte)197, (byte)49, (byte)254, (byte)24, (byte)13, (byte)99, (byte)140, (byte)128, (byte)192, (byte)247, (byte)112, (byte)7};
   private static final int[] rcon = new int[]{1, 2, 4, 8, 16, 32, 64, 128, 27, 54, 108, 216, 171, 77, 154, 47, 94, 188, 99, 198, 151, 53, 106, 212, 179, 125, 250, 239, 197, 145};
   static byte[][] shifts0;
   static byte[][] shifts1;
   private long A0;
   private long A1;
   private long A2;
   private long A3;
   private int BC;
   private long BC_MASK;
   private int ROUNDS;
   private int blockBits;
   private boolean forEncryption;
   private byte[] shifts0SC;
   private byte[] shifts1SC;
   private long[][] workingKey;


   static {
      byte[] var0 = new byte[5];
      byte[] var1 = new byte[]{(byte)0, (byte)8, (byte)16, (byte)24};
      var0[0] = (byte)var1;
      byte[] var2 = new byte[]{(byte)0, (byte)8, (byte)16, (byte)24};
      var0[1] = (byte)var2;
      byte[] var3 = new byte[]{(byte)0, (byte)8, (byte)16, (byte)24};
      var0[2] = (byte)var3;
      byte[] var4 = new byte[]{(byte)0, (byte)8, (byte)16, (byte)32};
      var0[3] = (byte)var4;
      byte[] var5 = new byte[]{(byte)0, (byte)8, (byte)24, (byte)32};
      var0[4] = (byte)var5;
      shifts0 = (byte[][])var0;
      byte[] var6 = new byte[5];
      byte[] var7 = new byte[]{(byte)0, (byte)24, (byte)16, (byte)8};
      var6[0] = (byte)var7;
      byte[] var8 = new byte[]{(byte)0, (byte)32, (byte)24, (byte)16};
      var6[1] = (byte)var8;
      byte[] var9 = new byte[]{(byte)0, (byte)40, (byte)32, (byte)24};
      var6[2] = (byte)var9;
      byte[] var10 = new byte[]{(byte)0, (byte)48, (byte)40, (byte)24};
      var6[3] = (byte)var10;
      byte[] var11 = new byte[]{(byte)0, (byte)56, (byte)40, (byte)32};
      var6[4] = (byte)var11;
      shifts1 = (byte[][])var6;
   }

   public RijndaelEngine() {
      this(128);
   }

   public RijndaelEngine(int var1) {
      switch(var1) {
      case 128:
         this.BC = 32;
         this.BC_MASK = 4294967295L;
         byte[] var2 = shifts0[0];
         this.shifts0SC = var2;
         byte[] var3 = shifts1[0];
         this.shifts1SC = var3;
         break;
      case 160:
         this.BC = 40;
         this.BC_MASK = 1099511627775L;
         byte[] var4 = shifts0[1];
         this.shifts0SC = var4;
         byte[] var5 = shifts1[1];
         this.shifts1SC = var5;
         break;
      case 192:
         this.BC = 48;
         this.BC_MASK = 281474976710655L;
         byte[] var6 = shifts0[2];
         this.shifts0SC = var6;
         byte[] var7 = shifts1[2];
         this.shifts1SC = var7;
         break;
      case 224:
         this.BC = 56;
         this.BC_MASK = 72057594037927935L;
         byte[] var8 = shifts0[3];
         this.shifts0SC = var8;
         byte[] var9 = shifts1[3];
         this.shifts1SC = var9;
         break;
      case 256:
         this.BC = 64;
         this.BC_MASK = 65535L;
         byte[] var10 = shifts0[4];
         this.shifts0SC = var10;
         byte[] var11 = shifts1[4];
         this.shifts1SC = var11;
         break;
      default:
         throw new IllegalArgumentException("unknown blocksize to Rijndael");
      }

      this.blockBits = var1;
   }

   private void InvMixColumn() {
      long var1 = 0L;
      long var3 = var1;
      long var5 = var1;
      long var7 = var1;
      int var9 = 0;

      while(true) {
         int var10 = this.BC;
         if(var9 >= var10) {
            this.A0 = var7;
            this.A1 = var5;
            this.A2 = var3;
            this.A3 = var1;
            return;
         }

         int var13 = (int)(this.A0 >> var9 & 255L);
         int var14 = (int)(this.A1 >> var9 & 255L);
         int var15 = (int)(this.A2 >> var9 & 255L);
         int var16 = (int)(this.A3 >> var9 & 255L);
         int var19;
         if(var13 != 0) {
            byte[] var17 = logtable;
            int var18 = var13 & 255;
            var19 = var17[var18] & 255;
         } else {
            var19 = '\uffff';
         }

         int var22;
         if(var14 != 0) {
            byte[] var20 = logtable;
            int var21 = var14 & 255;
            var22 = var20[var21] & 255;
         } else {
            var22 = '\uffff';
         }

         int var25;
         if(var15 != 0) {
            byte[] var23 = logtable;
            int var24 = var15 & 255;
            var25 = var23[var24] & 255;
         } else {
            var25 = '\uffff';
         }

         int var28;
         if(var16 != 0) {
            byte[] var26 = logtable;
            int var27 = var16 & 255;
            var28 = var26[var27] & 255;
         } else {
            var28 = '\uffff';
         }

         byte var31 = this.mul0xe(var19);
         byte var34 = this.mul0xb(var22);
         int var35 = var31 ^ var34;
         byte var38 = this.mul0xd(var25);
         int var39 = var35 ^ var38;
         byte var42 = this.mul0x9(var28);
         long var43 = (long)((var39 ^ var42) & 255) << var9;
         var7 |= var43;
         byte var47 = this.mul0xe(var22);
         byte var50 = this.mul0xb(var25);
         int var51 = var47 ^ var50;
         byte var54 = this.mul0xd(var28);
         int var55 = var51 ^ var54;
         byte var58 = this.mul0x9(var19);
         long var59 = (long)((var55 ^ var58) & 255) << var9;
         var5 |= var59;
         byte var63 = this.mul0xe(var25);
         byte var66 = this.mul0xb(var28);
         int var67 = var63 ^ var66;
         byte var70 = this.mul0xd(var19);
         int var71 = var67 ^ var70;
         byte var74 = this.mul0x9(var22);
         long var75 = (long)((var71 ^ var74) & 255) << var9;
         var3 |= var75;
         byte var79 = this.mul0xe(var28);
         byte var82 = this.mul0xb(var19);
         int var83 = var79 ^ var82;
         byte var86 = this.mul0xd(var22);
         int var87 = var83 ^ var86;
         byte var90 = this.mul0x9(var25);
         long var91 = (long)((var87 ^ var90) & 255) << var9;
         var1 |= var91;
         var9 += 8;
      }
   }

   private void KeyAddition(long[] var1) {
      long var2 = this.A0;
      long var4 = var1[0];
      long var6 = var2 ^ var4;
      this.A0 = var6;
      long var8 = this.A1;
      long var10 = var1[1];
      long var12 = var8 ^ var10;
      this.A1 = var12;
      long var14 = this.A2;
      long var16 = var1[2];
      long var18 = var14 ^ var16;
      this.A2 = var18;
      long var20 = this.A3;
      long var22 = var1[3];
      long var24 = var20 ^ var22;
      this.A3 = var24;
   }

   private void MixColumn() {
      long var1 = 0L;
      long var3 = var1;
      long var5 = var1;
      long var7 = var1;
      int var9 = 0;

      while(true) {
         int var10 = this.BC;
         if(var9 >= var10) {
            this.A0 = var7;
            this.A1 = var5;
            this.A2 = var3;
            this.A3 = var1;
            return;
         }

         int var13 = (int)(this.A0 >> var9 & 255L);
         int var14 = (int)(this.A1 >> var9 & 255L);
         int var15 = (int)(this.A2 >> var9 & 255L);
         int var16 = (int)(this.A3 >> var9 & 255L);
         byte var19 = this.mul0x2(var13);
         byte var22 = this.mul0x3(var14);
         long var23 = (long)((var19 ^ var22 ^ var15 ^ var16) & 255) << var9;
         var7 |= var23;
         byte var27 = this.mul0x2(var14);
         byte var30 = this.mul0x3(var15);
         long var31 = (long)((var27 ^ var30 ^ var16 ^ var13) & 255) << var9;
         var5 |= var31;
         byte var35 = this.mul0x2(var15);
         byte var38 = this.mul0x3(var16);
         long var39 = (long)((var35 ^ var38 ^ var13 ^ var14) & 255) << var9;
         var3 |= var39;
         byte var43 = this.mul0x2(var16);
         byte var46 = this.mul0x3(var13);
         long var47 = (long)((var43 ^ var46 ^ var14 ^ var15) & 255) << var9;
         var1 |= var47;
         var9 += 8;
      }
   }

   private void ShiftRow(byte[] var1) {
      long var2 = this.A1;
      byte var4 = var1[1];
      long var5 = this.shift(var2, var4);
      this.A1 = var5;
      long var7 = this.A2;
      byte var9 = var1[2];
      long var10 = this.shift(var7, var9);
      this.A2 = var10;
      long var12 = this.A3;
      byte var14 = var1[3];
      long var15 = this.shift(var12, var14);
      this.A3 = var15;
   }

   private void Substitution(byte[] var1) {
      long var2 = this.A0;
      long var4 = this.applyS(var2, var1);
      this.A0 = var4;
      long var6 = this.A1;
      long var8 = this.applyS(var6, var1);
      this.A1 = var8;
      long var10 = this.A2;
      long var12 = this.applyS(var10, var1);
      this.A2 = var12;
      long var14 = this.A3;
      long var16 = this.applyS(var14, var1);
      this.A3 = var16;
   }

   private long applyS(long var1, byte[] var3) {
      long var4 = 0L;
      int var6 = 0;

      while(true) {
         int var7 = this.BC;
         if(var6 >= var7) {
            return var4;
         }

         int var8 = (int)(var1 >> var6 & 255L);
         long var9 = (long)(var3[var8] & 255) << var6;
         var4 |= var9;
         var6 += 8;
      }
   }

   private void decryptBlock(long[][] var1) {
      int var2 = this.ROUNDS;
      long[] var3 = var1[var2];
      this.KeyAddition(var3);
      byte[] var4 = Si;
      this.Substitution(var4);
      byte[] var5 = this.shifts1SC;
      this.ShiftRow(var5);

      for(int var6 = this.ROUNDS - 1; var6 > 0; var6 += -1) {
         long[] var7 = var1[var6];
         this.KeyAddition(var7);
         this.InvMixColumn();
         byte[] var8 = Si;
         this.Substitution(var8);
         byte[] var9 = this.shifts1SC;
         this.ShiftRow(var9);
      }

      long[] var10 = var1[0];
      this.KeyAddition(var10);
   }

   private void encryptBlock(long[][] var1) {
      long[] var2 = var1[0];
      this.KeyAddition(var2);
      int var3 = 1;

      while(true) {
         int var4 = this.ROUNDS;
         if(var3 >= var4) {
            byte[] var8 = S;
            this.Substitution(var8);
            byte[] var9 = this.shifts0SC;
            this.ShiftRow(var9);
            int var10 = this.ROUNDS;
            long[] var11 = var1[var10];
            this.KeyAddition(var11);
            return;
         }

         byte[] var5 = S;
         this.Substitution(var5);
         byte[] var6 = this.shifts0SC;
         this.ShiftRow(var6);
         this.MixColumn();
         long[] var7 = var1[var3];
         this.KeyAddition(var7);
         ++var3;
      }
   }

   private long[][] generateWorkingKey(byte[] param1) {
      // $FF: Couldn't be decompiled
   }

   private byte mul0x2(int var1) {
      byte var4;
      if(var1 != 0) {
         byte[] var2 = aLogtable;
         int var3 = (logtable[var1] & 255) + 25;
         var4 = var2[var3];
      } else {
         var4 = 0;
      }

      return var4;
   }

   private byte mul0x3(int var1) {
      byte var4;
      if(var1 != 0) {
         byte[] var2 = aLogtable;
         int var3 = (logtable[var1] & 255) + 1;
         var4 = var2[var3];
      } else {
         var4 = 0;
      }

      return var4;
   }

   private byte mul0x9(int var1) {
      byte var4;
      if(var1 >= 0) {
         byte[] var2 = aLogtable;
         int var3 = var1 + 199;
         var4 = var2[var3];
      } else {
         var4 = 0;
      }

      return var4;
   }

   private byte mul0xb(int var1) {
      byte var4;
      if(var1 >= 0) {
         byte[] var2 = aLogtable;
         int var3 = var1 + 104;
         var4 = var2[var3];
      } else {
         var4 = 0;
      }

      return var4;
   }

   private byte mul0xd(int var1) {
      byte var4;
      if(var1 >= 0) {
         byte[] var2 = aLogtable;
         int var3 = var1 + 238;
         var4 = var2[var3];
      } else {
         var4 = 0;
      }

      return var4;
   }

   private byte mul0xe(int var1) {
      byte var4;
      if(var1 >= 0) {
         byte[] var2 = aLogtable;
         int var3 = var1 + 223;
         var4 = var2[var3];
      } else {
         var4 = 0;
      }

      return var4;
   }

   private void packBlock(byte[] var1, int var2) {
      int var3 = var2;
      int var4 = 0;

      while(true) {
         int var5 = this.BC;
         if(var4 == var5) {
            return;
         }

         int var6 = var3 + 1;
         byte var7 = (byte)((int)(this.A0 >> var4));
         var1[var3] = var7;
         int var8 = var6 + 1;
         byte var9 = (byte)((int)(this.A1 >> var4));
         var1[var6] = var9;
         int var10 = var8 + 1;
         byte var11 = (byte)((int)(this.A2 >> var4));
         var1[var8] = var11;
         var3 = var10 + 1;
         byte var12 = (byte)((int)(this.A3 >> var4));
         var1[var10] = var12;
         var4 += 8;
      }
   }

   private long shift(long var1, int var3) {
      long var4 = var1 >>> var3;
      int var6 = this.BC - var3;
      long var7 = var1 << var6;
      long var9 = var4 | var7;
      long var11 = this.BC_MASK;
      return var9 & var11;
   }

   private void unpackBlock(byte[] var1, int var2) {
      int var4 = var2 + 1;
      long var5 = (long)(var1[var2] & 255);
      this.A0 = var5;
      int var7 = var4 + 1;
      long var8 = (long)(var1[var4] & 255);
      this.A1 = var8;
      int var10 = var7 + 1;
      long var11 = (long)(var1[var7] & 255);
      this.A2 = var11;
      int var13 = var10 + 1;
      long var14 = (long)(var1[var10] & 255);
      this.A3 = var14;
      int var16 = 8;

      while(true) {
         int var17 = this.BC;
         if(var16 == var17) {
            return;
         }

         long var18 = this.A0;
         int var20 = var13 + 1;
         long var21 = (long)(var1[var13] & 255) << var16;
         long var23 = var18 | var21;
         this.A0 = var23;
         long var25 = this.A1;
         int var27 = var20 + 1;
         long var28 = (long)(var1[var20] & 255) << var16;
         long var30 = var25 | var28;
         this.A1 = var30;
         long var32 = this.A2;
         int var34 = var27 + 1;
         long var35 = (long)(var1[var27] & 255) << var16;
         long var37 = var32 | var35;
         this.A2 = var37;
         long var39 = this.A3;
         var13 = var34 + 1;
         long var41 = (long)(var1[var34] & 255) << var16;
         long var43 = var39 | var41;
         this.A3 = var43;
         var16 += 8;
      }
   }

   public String getAlgorithmName() {
      return "Rijndael";
   }

   public int getBlockSize() {
      return this.BC / 2;
   }

   public void init(boolean var1, CipherParameters var2) {
      if(var2 instanceof KeyParameter) {
         byte[] var3 = ((KeyParameter)var2).getKey();
         long[][] var4 = this.generateWorkingKey(var3);
         this.workingKey = var4;
         this.forEncryption = var1;
      } else {
         StringBuilder var5 = (new StringBuilder()).append("invalid parameter passed to Rijndael init - ");
         String var6 = var2.getClass().getName();
         String var7 = var5.append(var6).toString();
         throw new IllegalArgumentException(var7);
      }
   }

   public int processBlock(byte[] var1, int var2, byte[] var3, int var4) {
      if(this.workingKey == null) {
         throw new IllegalStateException("Rijndael engine not initialised");
      } else {
         int var5 = this.BC / 2 + var2;
         int var6 = var1.length;
         if(var5 > var6) {
            throw new DataLengthException("input buffer too short");
         } else {
            int var7 = this.BC / 2 + var4;
            int var8 = var3.length;
            if(var7 > var8) {
               throw new DataLengthException("output buffer too short");
            } else {
               if(this.forEncryption) {
                  this.unpackBlock(var1, var2);
                  long[][] var9 = this.workingKey;
                  this.encryptBlock(var9);
                  this.packBlock(var3, var4);
               } else {
                  this.unpackBlock(var1, var2);
                  long[][] var10 = this.workingKey;
                  this.decryptBlock(var10);
                  this.packBlock(var3, var4);
               }

               return this.BC / 2;
            }
         }
      }
   }

   public void reset() {}
}
