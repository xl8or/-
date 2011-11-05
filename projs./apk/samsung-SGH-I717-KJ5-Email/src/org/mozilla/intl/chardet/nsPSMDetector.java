package org.mozilla.intl.chardet;

import org.mozilla.intl.chardet.Big5Statistics;
import org.mozilla.intl.chardet.EUCJPStatistics;
import org.mozilla.intl.chardet.EUCKRStatistics;
import org.mozilla.intl.chardet.EUCTWStatistics;
import org.mozilla.intl.chardet.GB2312Statistics;
import org.mozilla.intl.chardet.nsBIG5Verifier;
import org.mozilla.intl.chardet.nsCP1252Verifier;
import org.mozilla.intl.chardet.nsEUCJPVerifier;
import org.mozilla.intl.chardet.nsEUCKRVerifier;
import org.mozilla.intl.chardet.nsEUCSampler;
import org.mozilla.intl.chardet.nsEUCStatistics;
import org.mozilla.intl.chardet.nsEUCTWVerifier;
import org.mozilla.intl.chardet.nsGB18030Verifier;
import org.mozilla.intl.chardet.nsGB2312Verifier;
import org.mozilla.intl.chardet.nsHZVerifier;
import org.mozilla.intl.chardet.nsISO2022CNVerifier;
import org.mozilla.intl.chardet.nsISO2022JPVerifier;
import org.mozilla.intl.chardet.nsISO2022KRVerifier;
import org.mozilla.intl.chardet.nsSJISVerifier;
import org.mozilla.intl.chardet.nsUCS2BEVerifier;
import org.mozilla.intl.chardet.nsUCS2LEVerifier;
import org.mozilla.intl.chardet.nsUTF8Verifier;
import org.mozilla.intl.chardet.nsVerifier;

public abstract class nsPSMDetector {

   public static final int ALL = 0;
   public static final int CHINESE = 2;
   public static final int JAPANESE = 1;
   public static final int KOREAN = 5;
   public static final int MAX_VERIFIERS = 16;
   public static final int NO_OF_LANGUAGES = 6;
   public static final int SIMPLIFIED_CHINESE = 3;
   public static final int TRADITIONAL_CHINESE = 4;
   int mClassItems;
   boolean mClassRunSampler;
   boolean mDone;
   int[] mItemIdx;
   int mItems;
   boolean mRunSampler;
   nsEUCSampler mSampler;
   byte[] mState;
   nsEUCStatistics[] mStatisticsData;
   nsVerifier[] mVerifier;


   public nsPSMDetector() {
      nsEUCSampler var1 = new nsEUCSampler();
      this.mSampler = var1;
      byte[] var2 = new byte[16];
      this.mState = var2;
      int[] var3 = new int[16];
      this.mItemIdx = var3;
      this.initVerifiers(0);
      this.Reset();
   }

   public nsPSMDetector(int var1) {
      nsEUCSampler var2 = new nsEUCSampler();
      this.mSampler = var2;
      byte[] var3 = new byte[16];
      this.mState = var3;
      int[] var4 = new int[16];
      this.mItemIdx = var4;
      this.initVerifiers(var1);
      this.Reset();
   }

   public nsPSMDetector(int var1, nsVerifier[] var2, nsEUCStatistics[] var3) {
      nsEUCSampler var4 = new nsEUCSampler();
      this.mSampler = var4;
      byte[] var5 = new byte[16];
      this.mState = var5;
      int[] var6 = new int[16];
      this.mItemIdx = var6;
      byte var7;
      if(var3 != null) {
         var7 = 1;
      } else {
         var7 = 0;
      }

      this.mClassRunSampler = (boolean)var7;
      this.mStatisticsData = var3;
      this.mVerifier = var2;
      this.mClassItems = var1;
      this.Reset();
   }

   public void DataEnd() {
      if(this.mDone != 1) {
         if(this.mItems == 2) {
            nsVerifier[] var1 = this.mVerifier;
            int var2 = this.mItemIdx[0];
            if(var1[var2].charset().equals("GB18030")) {
               nsVerifier[] var3 = this.mVerifier;
               int var4 = this.mItemIdx[1];
               String var5 = var3[var4].charset();
               this.Report(var5);
               this.mDone = (boolean)1;
            } else {
               nsVerifier[] var6 = this.mVerifier;
               int var7 = this.mItemIdx[1];
               if(var6[var7].charset().equals("GB18030")) {
                  nsVerifier[] var8 = this.mVerifier;
                  int var9 = this.mItemIdx[0];
                  String var10 = var8[var9].charset();
                  this.Report(var10);
                  this.mDone = (boolean)1;
               }
            }
         }

         if(this.mRunSampler) {
            this.Sample((byte[])null, 0, (boolean)1);
         }
      }
   }

   public boolean HandleData(byte[] var1, int var2) {
      int var3 = 0;

      boolean var15;
      label61:
      while(var3 < var2) {
         byte var4 = var1[var3];
         int var5 = 0;

         while(true) {
            int var6 = this.mItems;
            if(var5 >= var6) {
               if(this.mItems <= 1) {
                  int var28 = this.mItems;
                  if(1 == var28) {
                     nsVerifier[] var29 = this.mVerifier;
                     int var30 = this.mItemIdx[0];
                     String var31 = var29[var30].charset();
                     this.Report(var31);
                  }

                  this.mDone = (boolean)1;
                  var15 = this.mDone;
                  return var15;
               }

               int var32 = 0;
               byte var33 = 0;
               byte var43 = 0;

               while(true) {
                  int var34 = this.mItems;
                  if(var43 >= var34) {
                     if(1 == var32) {
                        nsVerifier[] var40 = this.mVerifier;
                        int var41 = this.mItemIdx[var33];
                        String var42 = var40[var41].charset();
                        this.Report(var42);
                        this.mDone = (boolean)1;
                        var15 = this.mDone;
                        return var15;
                     }

                     ++var3;
                     continue label61;
                  }

                  nsVerifier[] var35 = this.mVerifier;
                  int var36 = this.mItemIdx[var43];
                  if(!var35[var36].isUCS2()) {
                     nsVerifier[] var37 = this.mVerifier;
                     int var38 = this.mItemIdx[var43];
                     if(!var37[var38].isUCS2()) {
                        ++var32;
                        var33 = var43;
                     }
                  }

                  int var39 = var43 + 1;
               }
            }

            nsVerifier[] var7 = this.mVerifier;
            int var8 = this.mItemIdx[var5];
            nsVerifier var9 = var7[var8];
            byte var10 = this.mState[var5];
            byte var11 = nsVerifier.getNextState(var9, var4, var10);
            if(var11 == 2) {
               nsVerifier[] var12 = this.mVerifier;
               int var13 = this.mItemIdx[var5];
               String var14 = var12[var13].charset();
               this.Report(var14);
               this.mDone = (boolean)1;
               var15 = this.mDone;
               return var15;
            }

            if(var11 == 1) {
               int var16 = this.mItems - 1;
               this.mItems = var16;
               int var17 = this.mItems;
               if(var5 < var17) {
                  int[] var18 = this.mItemIdx;
                  int[] var19 = this.mItemIdx;
                  int var20 = this.mItems;
                  int var21 = var19[var20];
                  var18[var5] = var21;
                  byte[] var22 = this.mState;
                  byte[] var23 = this.mState;
                  int var24 = this.mItems;
                  byte var25 = var23[var24];
                  var22[var5] = var25;
               }
            } else {
               byte[] var26 = this.mState;
               int var27 = var5 + 1;
               var26[var5] = var11;
               var5 = var27;
            }
         }
      }

      if(this.mRunSampler) {
         this.Sample(var1, var2);
      }

      var15 = this.mDone;
      return var15;
   }

   public abstract void Report(String var1);

   public void Reset() {
      boolean var1 = this.mClassRunSampler;
      this.mRunSampler = var1;
      this.mDone = (boolean)0;
      int var2 = this.mClassItems;
      this.mItems = var2;
      int var3 = 0;

      while(true) {
         int var4 = this.mItems;
         if(var3 >= var4) {
            this.mSampler.Reset();
            return;
         }

         this.mState[var3] = 0;
         this.mItemIdx[var3] = var3++;
      }
   }

   public void Sample(byte[] var1, int var2) {
      this.Sample(var1, var2, (boolean)0);
   }

   public void Sample(byte[] var1, int var2, boolean var3) {
      int var4 = 0;
      int var5 = 0;
      int var6 = 0;

      while(true) {
         int var7 = this.mItems;
         if(var6 >= var7) {
            byte var14;
            if(var5 > 1) {
               var14 = 1;
            } else {
               var14 = 0;
            }

            this.mRunSampler = (boolean)var14;
            if(!this.mRunSampler) {
               return;
            } else {
               nsEUCSampler var16 = this.mSampler;
               boolean var19 = var16.Sample(var1, var2);
               this.mRunSampler = var19;
               if((!var3 || !this.mSampler.GetSomeData()) && !this.mSampler.EnoughData()) {
                  return;
               } else if(var5 != var4) {
                  return;
               } else {
                  this.mSampler.CalFreq();
                  byte var20 = -1;
                  byte var21 = 0;
                  float var22 = 0.0F;
                  byte var53 = 0;

                  while(true) {
                     int var23 = this.mItems;
                     if(var53 >= var23) {
                        if(var20 < 0) {
                           return;
                        }

                        nsVerifier[] var46 = this.mVerifier;
                        int var47 = this.mItemIdx[var20];
                        String var48 = var46[var47].charset();
                        this.Report(var48);
                        byte var51 = 1;
                        this.mDone = (boolean)var51;
                        return;
                     }

                     nsEUCStatistics[] var24 = this.mStatisticsData;
                     int var25 = this.mItemIdx[var53];
                     if(var24[var25] != false) {
                        nsVerifier[] var26 = this.mVerifier;
                        int var27 = this.mItemIdx[var53];
                        if(!var26[var27].charset().equals("Big5")) {
                           nsEUCSampler var28 = this.mSampler;
                           nsEUCStatistics[] var29 = this.mStatisticsData;
                           int var30 = this.mItemIdx[var53];
                           float[] var31 = var29[var30].mFirstByteFreq();
                           nsEUCStatistics[] var32 = this.mStatisticsData;
                           int var33 = this.mItemIdx[var53];
                           float var34 = var32[var33].mFirstByteWeight();
                           nsEUCStatistics[] var35 = this.mStatisticsData;
                           int var36 = this.mItemIdx[var53];
                           float[] var37 = var35[var36].mSecondByteFreq();
                           nsEUCStatistics[] var38 = this.mStatisticsData;
                           int var39 = this.mItemIdx[var53];
                           float var40 = var38[var39].mSecondByteWeight();
                           float var41 = var28.GetScore(var31, var34, var37, var40);
                           int var42 = var21 + 1;
                           if(var21 == 0 || var22 > var41) {
                              var20 = var53;
                           }
                        }
                     }

                     int var45 = var53 + 1;
                  }
               }
            }
         }

         nsEUCStatistics[] var8 = this.mStatisticsData;
         int var9 = this.mItemIdx[var6];
         if(var8[var9] != false) {
            ++var5;
         }

         nsVerifier[] var10 = this.mVerifier;
         int var11 = this.mItemIdx[var6];
         if(!var10[var11].isUCS2()) {
            nsVerifier[] var12 = this.mVerifier;
            int var13 = this.mItemIdx[var6];
            if(!var12[var13].charset().equals("GB18030")) {
               ++var4;
            }
         }

         ++var6;
      }
   }

   public String[] getProbableCharsets() {
      String[] var2;
      if(this.mItems <= 0) {
         String[] var1 = new String[]{"nomatch"};
         var2 = var1;
      } else {
         String[] var3 = new String[this.mItems];
         int var4 = 0;

         while(true) {
            int var5 = this.mItems;
            if(var4 >= var5) {
               var2 = var3;
               break;
            }

            nsVerifier[] var6 = this.mVerifier;
            int var7 = this.mItemIdx[var4];
            String var8 = var6[var7].charset();
            var3[var4] = var8;
            ++var4;
         }
      }

      return var2;
   }

   protected void initVerifiers(int var1) {
      int var2;
      if(var1 >= 0 && var1 < 6) {
         var2 = var1;
      } else {
         var2 = 0;
      }

      this.mVerifier = null;
      this.mStatisticsData = null;
      if(var2 == 4) {
         nsVerifier[] var3 = new nsVerifier[7];
         nsUTF8Verifier var4 = new nsUTF8Verifier();
         var3[0] = var4;
         nsBIG5Verifier var5 = new nsBIG5Verifier();
         var3[1] = var5;
         nsISO2022CNVerifier var6 = new nsISO2022CNVerifier();
         var3[2] = var6;
         nsEUCTWVerifier var7 = new nsEUCTWVerifier();
         var3[3] = var7;
         nsCP1252Verifier var8 = new nsCP1252Verifier();
         var3[4] = var8;
         nsUCS2BEVerifier var9 = new nsUCS2BEVerifier();
         var3[5] = var9;
         nsUCS2LEVerifier var10 = new nsUCS2LEVerifier();
         var3[6] = var10;
         this.mVerifier = var3;
         nsEUCStatistics[] var11 = new nsEUCStatistics[7];
         var11[0] = false;
         Big5Statistics var12 = new Big5Statistics();
         var11[1] = var12;
         var11[2] = false;
         EUCTWStatistics var13 = new EUCTWStatistics();
         var11[3] = var13;
         var11[4] = false;
         var11[5] = false;
         var11[6] = false;
         this.mStatisticsData = var11;
      } else if(var2 == 5) {
         nsVerifier[] var16 = new nsVerifier[6];
         nsUTF8Verifier var17 = new nsUTF8Verifier();
         var16[0] = var17;
         nsEUCKRVerifier var18 = new nsEUCKRVerifier();
         var16[1] = var18;
         nsISO2022KRVerifier var19 = new nsISO2022KRVerifier();
         var16[2] = var19;
         nsCP1252Verifier var20 = new nsCP1252Verifier();
         var16[3] = var20;
         nsUCS2BEVerifier var21 = new nsUCS2BEVerifier();
         var16[4] = var21;
         nsUCS2LEVerifier var22 = new nsUCS2LEVerifier();
         var16[5] = var22;
         this.mVerifier = var16;
      } else if(var2 == 3) {
         nsVerifier[] var23 = new nsVerifier[8];
         nsUTF8Verifier var24 = new nsUTF8Verifier();
         var23[0] = var24;
         nsGB2312Verifier var25 = new nsGB2312Verifier();
         var23[1] = var25;
         nsGB18030Verifier var26 = new nsGB18030Verifier();
         var23[2] = var26;
         nsISO2022CNVerifier var27 = new nsISO2022CNVerifier();
         var23[3] = var27;
         nsHZVerifier var28 = new nsHZVerifier();
         var23[4] = var28;
         nsCP1252Verifier var29 = new nsCP1252Verifier();
         var23[5] = var29;
         nsUCS2BEVerifier var30 = new nsUCS2BEVerifier();
         var23[6] = var30;
         nsUCS2LEVerifier var31 = new nsUCS2LEVerifier();
         var23[7] = var31;
         this.mVerifier = var23;
      } else if(var2 == 1) {
         nsVerifier[] var32 = new nsVerifier[7];
         nsUTF8Verifier var33 = new nsUTF8Verifier();
         var32[0] = var33;
         nsSJISVerifier var34 = new nsSJISVerifier();
         var32[1] = var34;
         nsEUCJPVerifier var35 = new nsEUCJPVerifier();
         var32[2] = var35;
         nsISO2022JPVerifier var36 = new nsISO2022JPVerifier();
         var32[3] = var36;
         nsCP1252Verifier var37 = new nsCP1252Verifier();
         var32[4] = var37;
         nsUCS2BEVerifier var38 = new nsUCS2BEVerifier();
         var32[5] = var38;
         nsUCS2LEVerifier var39 = new nsUCS2LEVerifier();
         var32[6] = var39;
         this.mVerifier = var32;
      } else if(var2 == 2) {
         nsVerifier[] var40 = new nsVerifier[10];
         nsUTF8Verifier var41 = new nsUTF8Verifier();
         var40[0] = var41;
         nsGB2312Verifier var42 = new nsGB2312Verifier();
         var40[1] = var42;
         nsGB18030Verifier var43 = new nsGB18030Verifier();
         var40[2] = var43;
         nsBIG5Verifier var44 = new nsBIG5Verifier();
         var40[3] = var44;
         nsISO2022CNVerifier var45 = new nsISO2022CNVerifier();
         var40[4] = var45;
         nsHZVerifier var46 = new nsHZVerifier();
         var40[5] = var46;
         nsEUCTWVerifier var47 = new nsEUCTWVerifier();
         var40[6] = var47;
         nsCP1252Verifier var48 = new nsCP1252Verifier();
         var40[7] = var48;
         nsUCS2BEVerifier var49 = new nsUCS2BEVerifier();
         var40[8] = var49;
         nsUCS2LEVerifier var50 = new nsUCS2LEVerifier();
         var40[9] = var50;
         this.mVerifier = var40;
         nsEUCStatistics[] var51 = new nsEUCStatistics[10];
         var51[0] = false;
         GB2312Statistics var52 = new GB2312Statistics();
         var51[1] = var52;
         var51[2] = false;
         Big5Statistics var53 = new Big5Statistics();
         var51[3] = var53;
         var51[4] = false;
         var51[5] = false;
         EUCTWStatistics var54 = new EUCTWStatistics();
         var51[6] = var54;
         var51[7] = false;
         var51[8] = false;
         var51[9] = false;
         this.mStatisticsData = var51;
      } else if(var2 == 0) {
         nsVerifier[] var55 = new nsVerifier[15];
         nsUTF8Verifier var56 = new nsUTF8Verifier();
         var55[0] = var56;
         nsSJISVerifier var57 = new nsSJISVerifier();
         var55[1] = var57;
         nsEUCJPVerifier var58 = new nsEUCJPVerifier();
         var55[2] = var58;
         nsISO2022JPVerifier var59 = new nsISO2022JPVerifier();
         var55[3] = var59;
         nsEUCKRVerifier var60 = new nsEUCKRVerifier();
         var55[4] = var60;
         nsISO2022KRVerifier var61 = new nsISO2022KRVerifier();
         var55[5] = var61;
         nsBIG5Verifier var62 = new nsBIG5Verifier();
         var55[6] = var62;
         nsEUCTWVerifier var63 = new nsEUCTWVerifier();
         var55[7] = var63;
         nsGB2312Verifier var64 = new nsGB2312Verifier();
         var55[8] = var64;
         nsGB18030Verifier var65 = new nsGB18030Verifier();
         var55[9] = var65;
         nsISO2022CNVerifier var66 = new nsISO2022CNVerifier();
         var55[10] = var66;
         nsHZVerifier var67 = new nsHZVerifier();
         var55[11] = var67;
         nsCP1252Verifier var68 = new nsCP1252Verifier();
         var55[12] = var68;
         nsUCS2BEVerifier var69 = new nsUCS2BEVerifier();
         var55[13] = var69;
         nsUCS2LEVerifier var70 = new nsUCS2LEVerifier();
         var55[14] = var70;
         this.mVerifier = var55;
         nsEUCStatistics[] var71 = new nsEUCStatistics[15];
         var71[0] = false;
         var71[1] = false;
         EUCJPStatistics var72 = new EUCJPStatistics();
         var71[2] = var72;
         var71[3] = false;
         EUCKRStatistics var73 = new EUCKRStatistics();
         var71[4] = var73;
         var71[5] = false;
         Big5Statistics var74 = new Big5Statistics();
         var71[6] = var74;
         EUCTWStatistics var75 = new EUCTWStatistics();
         var71[7] = var75;
         GB2312Statistics var76 = new GB2312Statistics();
         var71[8] = var76;
         var71[9] = false;
         var71[10] = false;
         var71[11] = false;
         var71[12] = false;
         var71[13] = false;
         var71[14] = false;
         this.mStatisticsData = var71;
      }

      byte var14;
      if(this.mStatisticsData != null) {
         var14 = 1;
      } else {
         var14 = 0;
      }

      this.mClassRunSampler = (boolean)var14;
      int var15 = this.mVerifier.length;
      this.mClassItems = var15;
   }
}
