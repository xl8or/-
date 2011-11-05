package org.apache.james.mime4j.field.address.parser;

import java.io.IOException;
import java.io.PrintStream;
import org.apache.james.mime4j.field.address.parser.AddressListParserConstants;
import org.apache.james.mime4j.field.address.parser.SimpleCharStream;
import org.apache.james.mime4j.field.address.parser.Token;
import org.apache.james.mime4j.field.address.parser.TokenMgrError;

public class AddressListParserTokenManager implements AddressListParserConstants {

   static int commentNest;
   static final long[] jjbitVec0;
   public static final int[] jjnewLexState;
   static final int[] jjnextStates;
   public static final String[] jjstrLiteralImages;
   static final long[] jjtoMore;
   static final long[] jjtoSkip;
   static final long[] jjtoSpecial;
   static final long[] jjtoToken;
   public static final String[] lexStateNames;
   protected char curChar;
   int curLexState;
   public PrintStream debugStream;
   int defaultLexState;
   StringBuffer image;
   protected SimpleCharStream input_stream;
   int jjimageLen;
   int jjmatchedKind;
   int jjmatchedPos;
   int jjnewStateCnt;
   int jjround;
   private final int[] jjrounds;
   private final int[] jjstateSet;
   int lengthOfMatch;


   static {
      ((Object[])null)[0] = 0L;
      ((Object[])null)[1] = 0L;
      ((Object[])null)[2] = -1L;
      ((Object[])null)[3] = -1L;
      jjbitVec0 = null;
      jjnextStates = new int[0];
      String[] var0 = new String[]{"", "\r", "\n", ",", ":", ";", "<", ">", "@", ".", false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
      jjstrLiteralImages = var0;
      String[] var1 = new String[]{"DEFAULT", "INDOMAINLITERAL", "INCOMMENT", "NESTED_COMMENT", "INQUOTEDSTRING"};
      lexStateNames = var1;
      jjnewLexState = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, -1, -1, 0, 2, 0, -1, 3, -1, -1, -1, -1, -1, 4, -1, -1, 0, -1, -1};
      Object var2 = null;
      ((Object[])var2)[0] = 2147763199L;
      jjtoToken = (long[])var2;
      Object var3 = null;
      ((Object[])var3)[0] = 1049600L;
      jjtoSkip = (long[])var3;
      Object var4 = null;
      ((Object[])var4)[0] = 1024L;
      jjtoSpecial = (long[])var4;
      Object var5 = null;
      ((Object[])var5)[0] = 2146140160L;
      jjtoMore = (long[])var5;
   }

   public AddressListParserTokenManager(SimpleCharStream var1) {
      PrintStream var2 = System.out;
      this.debugStream = var2;
      int[] var3 = new int[3];
      this.jjrounds = var3;
      int[] var4 = new int[6];
      this.jjstateSet = var4;
      this.curLexState = 0;
      this.defaultLexState = 0;
      this.input_stream = var1;
   }

   public AddressListParserTokenManager(SimpleCharStream var1, int var2) {
      this(var1);
      this.SwitchTo(var2);
   }

   private final void ReInitRounds() {
      this.jjround = -2147483647;
      int var1 = 3;

      while(true) {
         int var2 = var1 + -1;
         if(var1 <= 0) {
            return;
         }

         this.jjrounds[var2] = Integer.MIN_VALUE;
         var1 = var2;
      }
   }

   private final void jjAddStates(int var1, int var2) {
      while(true) {
         int[] var3 = this.jjstateSet;
         int var4 = this.jjnewStateCnt;
         int var5 = var4 + 1;
         this.jjnewStateCnt = var5;
         int var6 = jjnextStates[var1];
         var3[var4] = var6;
         int var7 = var1 + 1;
         if(var1 == var2) {
            return;
         }

         var1 = var7;
      }
   }

   private final void jjCheckNAdd(int var1) {
      int var2 = this.jjrounds[var1];
      int var3 = this.jjround;
      if(var2 != var3) {
         int[] var4 = this.jjstateSet;
         int var5 = this.jjnewStateCnt;
         int var6 = var5 + 1;
         this.jjnewStateCnt = var6;
         var4[var5] = var1;
         int[] var7 = this.jjrounds;
         int var8 = this.jjround;
         var7[var1] = var8;
      }
   }

   private final void jjCheckNAddStates(int var1) {
      int var2 = jjnextStates[var1];
      this.jjCheckNAdd(var2);
      int[] var3 = jjnextStates;
      int var4 = var1 + 1;
      int var5 = var3[var4];
      this.jjCheckNAdd(var5);
   }

   private final void jjCheckNAddStates(int var1, int var2) {
      while(true) {
         int var3 = jjnextStates[var1];
         this.jjCheckNAdd(var3);
         int var4 = var1 + 1;
         if(var1 == var2) {
            return;
         }

         var1 = var4;
      }
   }

   private final void jjCheckNAddTwoStates(int var1, int var2) {
      this.jjCheckNAdd(var1);
      this.jjCheckNAdd(var2);
   }

   private final int jjMoveNfa_0(int var1, int var2) {
      int var3 = 0;
      this.jjnewStateCnt = 3;
      int var4 = 1;
      this.jjstateSet[0] = var1;
      int var5 = Integer.MAX_VALUE;

      while(true) {
         int var6 = this.jjround + 1;
         this.jjround = var6;
         if(var6 == Integer.MAX_VALUE) {
            this.ReInitRounds();
         }

         long var8;
         if(this.curChar < 64) {
            char var7 = this.curChar;
            var8 = 1L << var7;

            do {
               int[] var10 = this.jjstateSet;
               var4 += -1;
               switch(var10[var4]) {
               case 0:
                  if((4294967808L & var8) != 0L) {
                     var5 = 10;
                     this.jjCheckNAdd(0);
                  }
                  break;
               case 1:
                  if((-6629389936724279296L & var8) != 0L) {
                     if(var5 > 14) {
                        var5 = 14;
                     }

                     this.jjCheckNAdd(2);
                  } else if((4294967808L & var8) != 0L) {
                     if(var5 > 10) {
                        var5 = 10;
                     }

                     this.jjCheckNAdd(0);
                  }
                  break;
               case 2:
                  if((-6629319567980101632L & var8) != 0L) {
                     if(var5 > 14) {
                        var5 = 14;
                     }

                     this.jjCheckNAdd(2);
                  }
               }
            } while(var4 != var3);
         } else if(this.curChar < 128) {
            int var11 = this.curChar & 63;
            var8 = 1L << var11;

            do {
               int[] var12 = this.jjstateSet;
               var4 += -1;
               switch(var12[var4]) {
               case 1:
               case 2:
                  if((9223372035915251710L & var8) != 0L) {
                     if(var5 > 14) {
                        var5 = 14;
                     }

                     this.jjCheckNAdd(2);
                  }
               }
            } while(var4 != var3);
         } else {
            int var13 = (this.curChar & 255) >> 6;
            int var14 = this.curChar & 63;
            long var15 = 1L << var14;

            do {
               int[] var17 = this.jjstateSet;
               var4 += -1;
               int var10000 = var17[var4];
            } while(var4 != var3);
         }

         if(var5 != Integer.MAX_VALUE) {
            this.jjmatchedKind = var5;
            this.jjmatchedPos = var2;
            var5 = Integer.MAX_VALUE;
         }

         ++var2;
         var4 = this.jjnewStateCnt;
         this.jjnewStateCnt = var3;
         var3 = 3 - var3;
         if(var4 == var3) {
            break;
         }

         try {
            char var19 = this.input_stream.readChar();
            this.curChar = var19;
         } catch (IOException var21) {
            break;
         }
      }

      return var2;
   }

   private final int jjMoveNfa_1(int var1, int var2) {
      int var3 = 0;
      this.jjnewStateCnt = 3;
      int var4 = 1;
      this.jjstateSet[0] = var1;
      int var5 = Integer.MAX_VALUE;

      while(true) {
         int var6 = this.jjround + 1;
         this.jjround = var6;
         if(var6 == Integer.MAX_VALUE) {
            this.ReInitRounds();
         }

         if(this.curChar < 64) {
            char var7 = this.curChar;
            long var8 = 1L << var7;

            do {
               int[] var10 = this.jjstateSet;
               var4 += -1;
               switch(var10[var4]) {
               case 0:
                  if(var5 > 17) {
                     var5 = 17;
                  }
                  break;
               case 1:
                  if(var5 > 16) {
                     var5 = 16;
                  }
               }
            } while(var4 != var3);
         } else if(this.curChar < 128) {
            int var11 = this.curChar & 63;
            long var12 = 1L << var11;

            do {
               int[] var14 = this.jjstateSet;
               var4 += -1;
               switch(var14[var4]) {
               case 0:
                  if((-939524097L & var12) != 0L) {
                     if(var5 > 17) {
                        var5 = 17;
                     }
                  } else if(this.curChar == 92) {
                     int[] var15 = this.jjstateSet;
                     int var16 = this.jjnewStateCnt;
                     int var17 = var16 + 1;
                     this.jjnewStateCnt = var17;
                     var15[var16] = 1;
                  }
                  break;
               case 1:
                  if(var5 > 16) {
                     var5 = 16;
                  }
                  break;
               case 2:
                  if((-939524097L & var12) != 0L && var5 > 17) {
                     var5 = 17;
                  }
               }
            } while(var4 != var3);
         } else {
            int var18 = (this.curChar & 255) >> 6;
            int var19 = this.curChar & 63;
            long var20 = 1L << var19;

            do {
               int[] var22 = this.jjstateSet;
               var4 += -1;
               switch(var22[var4]) {
               case 0:
                  if((jjbitVec0[var18] & var20) != 0L && var5 > 17) {
                     var5 = 17;
                  }
                  break;
               case 1:
                  if((jjbitVec0[var18] & var20) != 0L && var5 > 16) {
                     var5 = 16;
                  }
               }
            } while(var4 != var3);
         }

         if(var5 != Integer.MAX_VALUE) {
            this.jjmatchedKind = var5;
            this.jjmatchedPos = var2;
            var5 = Integer.MAX_VALUE;
         }

         ++var2;
         var4 = this.jjnewStateCnt;
         this.jjnewStateCnt = var3;
         var3 = 3 - var3;
         if(var4 == var3) {
            break;
         }

         try {
            char var23 = this.input_stream.readChar();
            this.curChar = var23;
         } catch (IOException var25) {
            break;
         }
      }

      return var2;
   }

   private final int jjMoveNfa_2(int var1, int var2) {
      int var3 = 0;
      this.jjnewStateCnt = 3;
      int var4 = 1;
      this.jjstateSet[0] = var1;
      int var5 = Integer.MAX_VALUE;

      while(true) {
         int var6 = this.jjround + 1;
         this.jjround = var6;
         if(var6 == Integer.MAX_VALUE) {
            this.ReInitRounds();
         }

         if(this.curChar < 64) {
            char var7 = this.curChar;
            long var8 = 1L << var7;

            do {
               int[] var10 = this.jjstateSet;
               var4 += -1;
               switch(var10[var4]) {
               case 0:
                  if(var5 > 23) {
                     var5 = 23;
                  }
                  break;
               case 1:
                  if(var5 > 21) {
                     var5 = 21;
                  }
               }
            } while(var4 != var3);
         } else if(this.curChar < 128) {
            int var11 = this.curChar & 63;
            long var12 = 1L << var11;

            do {
               int[] var14 = this.jjstateSet;
               var4 += -1;
               switch(var14[var4]) {
               case 0:
                  if(var5 > 23) {
                     var5 = 23;
                  }

                  if(this.curChar == 92) {
                     int[] var15 = this.jjstateSet;
                     int var16 = this.jjnewStateCnt;
                     int var17 = var16 + 1;
                     this.jjnewStateCnt = var17;
                     var15[var16] = 1;
                  }
                  break;
               case 1:
                  if(var5 > 21) {
                     var5 = 21;
                  }
                  break;
               case 2:
                  if(var5 > 23) {
                     var5 = 23;
                  }
               }
            } while(var4 != var3);
         } else {
            int var18 = (this.curChar & 255) >> 6;
            int var19 = this.curChar & 63;
            long var20 = 1L << var19;

            do {
               int[] var22 = this.jjstateSet;
               var4 += -1;
               switch(var22[var4]) {
               case 0:
                  if((jjbitVec0[var18] & var20) != 0L && var5 > 23) {
                     var5 = 23;
                  }
                  break;
               case 1:
                  if((jjbitVec0[var18] & var20) != 0L && var5 > 21) {
                     var5 = 21;
                  }
               }
            } while(var4 != var3);
         }

         if(var5 != Integer.MAX_VALUE) {
            this.jjmatchedKind = var5;
            this.jjmatchedPos = var2;
            var5 = Integer.MAX_VALUE;
         }

         ++var2;
         var4 = this.jjnewStateCnt;
         this.jjnewStateCnt = var3;
         var3 = 3 - var3;
         if(var4 == var3) {
            break;
         }

         try {
            char var23 = this.input_stream.readChar();
            this.curChar = var23;
         } catch (IOException var25) {
            break;
         }
      }

      return var2;
   }

   private final int jjMoveNfa_3(int var1, int var2) {
      int var3 = 0;
      this.jjnewStateCnt = 3;
      int var4 = 1;
      this.jjstateSet[0] = var1;
      int var5 = Integer.MAX_VALUE;

      while(true) {
         int var6 = this.jjround + 1;
         this.jjround = var6;
         if(var6 == Integer.MAX_VALUE) {
            this.ReInitRounds();
         }

         if(this.curChar < 64) {
            char var7 = this.curChar;
            long var8 = 1L << var7;

            do {
               int[] var10 = this.jjstateSet;
               var4 += -1;
               switch(var10[var4]) {
               case 0:
                  if(var5 > 27) {
                     var5 = 27;
                  }
                  break;
               case 1:
                  if(var5 > 24) {
                     var5 = 24;
                  }
               }
            } while(var4 != var3);
         } else if(this.curChar < 128) {
            int var11 = this.curChar & 63;
            long var12 = 1L << var11;

            do {
               int[] var14 = this.jjstateSet;
               var4 += -1;
               switch(var14[var4]) {
               case 0:
                  if(var5 > 27) {
                     var5 = 27;
                  }

                  if(this.curChar == 92) {
                     int[] var15 = this.jjstateSet;
                     int var16 = this.jjnewStateCnt;
                     int var17 = var16 + 1;
                     this.jjnewStateCnt = var17;
                     var15[var16] = 1;
                  }
                  break;
               case 1:
                  if(var5 > 24) {
                     var5 = 24;
                  }
                  break;
               case 2:
                  if(var5 > 27) {
                     var5 = 27;
                  }
               }
            } while(var4 != var3);
         } else {
            int var18 = (this.curChar & 255) >> 6;
            int var19 = this.curChar & 63;
            long var20 = 1L << var19;

            do {
               int[] var22 = this.jjstateSet;
               var4 += -1;
               switch(var22[var4]) {
               case 0:
                  if((jjbitVec0[var18] & var20) != 0L && var5 > 27) {
                     var5 = 27;
                  }
                  break;
               case 1:
                  if((jjbitVec0[var18] & var20) != 0L && var5 > 24) {
                     var5 = 24;
                  }
               }
            } while(var4 != var3);
         }

         if(var5 != Integer.MAX_VALUE) {
            this.jjmatchedKind = var5;
            this.jjmatchedPos = var2;
            var5 = Integer.MAX_VALUE;
         }

         ++var2;
         var4 = this.jjnewStateCnt;
         this.jjnewStateCnt = var3;
         var3 = 3 - var3;
         if(var4 == var3) {
            break;
         }

         try {
            char var23 = this.input_stream.readChar();
            this.curChar = var23;
         } catch (IOException var25) {
            break;
         }
      }

      return var2;
   }

   private final int jjMoveNfa_4(int var1, int var2) {
      int var3 = 0;
      this.jjnewStateCnt = 3;
      int var4 = 1;
      this.jjstateSet[0] = var1;
      int var5 = Integer.MAX_VALUE;

      while(true) {
         int var6 = this.jjround + 1;
         this.jjround = var6;
         if(var6 == Integer.MAX_VALUE) {
            this.ReInitRounds();
         }

         long var8;
         if(this.curChar < 64) {
            char var7 = this.curChar;
            var8 = 1L << var7;

            do {
               int[] var10 = this.jjstateSet;
               var4 += -1;
               switch(var10[var4]) {
               case 0:
               case 2:
                  if((-17179869185L & var8) != 0L) {
                     if(var5 > 30) {
                        var5 = 30;
                     }

                     this.jjCheckNAdd(2);
                  }
                  break;
               case 1:
                  if(var5 > 29) {
                     var5 = 29;
                  }
               }
            } while(var4 != var3);
         } else if(this.curChar < 128) {
            int var11 = this.curChar & 63;
            var8 = 1L << var11;

            do {
               int[] var12 = this.jjstateSet;
               var4 += -1;
               switch(var12[var4]) {
               case 0:
                  if((-268435457L & var8) != 0L) {
                     if(var5 > 30) {
                        var5 = 30;
                     }

                     this.jjCheckNAdd(2);
                  } else if(this.curChar == 92) {
                     int[] var13 = this.jjstateSet;
                     int var14 = this.jjnewStateCnt;
                     int var15 = var14 + 1;
                     this.jjnewStateCnt = var15;
                     var13[var14] = 1;
                  }
                  break;
               case 1:
                  if(var5 > 29) {
                     var5 = 29;
                  }
                  break;
               case 2:
                  if((-268435457L & var8) != 0L) {
                     if(var5 > 30) {
                        var5 = 30;
                     }

                     this.jjCheckNAdd(2);
                  }
               }
            } while(var4 != var3);
         } else {
            int var16 = (this.curChar & 255) >> 6;
            int var17 = this.curChar & 63;
            long var18 = 1L << var17;

            do {
               int[] var20 = this.jjstateSet;
               var4 += -1;
               switch(var20[var4]) {
               case 0:
               case 2:
                  if((jjbitVec0[var16] & var18) != 0L) {
                     if(var5 > 30) {
                        var5 = 30;
                     }

                     this.jjCheckNAdd(2);
                  }
                  break;
               case 1:
                  if((jjbitVec0[var16] & var18) != 0L && var5 > 29) {
                     var5 = 29;
                  }
               }
            } while(var4 != var3);
         }

         if(var5 != Integer.MAX_VALUE) {
            this.jjmatchedKind = var5;
            this.jjmatchedPos = var2;
            var5 = Integer.MAX_VALUE;
         }

         ++var2;
         var4 = this.jjnewStateCnt;
         this.jjnewStateCnt = var3;
         var3 = 3 - var3;
         if(var4 == var3) {
            break;
         }

         try {
            char var21 = this.input_stream.readChar();
            this.curChar = var21;
         } catch (IOException var23) {
            break;
         }
      }

      return var2;
   }

   private final int jjMoveStringLiteralDfa0_0() {
      int var1;
      switch(this.curChar) {
      case 10:
         var1 = this.jjStopAtPos(0, 2);
         break;
      case 13:
         var1 = this.jjStopAtPos(0, 1);
         break;
      case 34:
         var1 = this.jjStopAtPos(0, 28);
         break;
      case 40:
         var1 = this.jjStopAtPos(0, 19);
         break;
      case 44:
         var1 = this.jjStopAtPos(0, 3);
         break;
      case 46:
         var1 = this.jjStopAtPos(0, 9);
         break;
      case 58:
         var1 = this.jjStopAtPos(0, 4);
         break;
      case 59:
         var1 = this.jjStopAtPos(0, 5);
         break;
      case 60:
         var1 = this.jjStopAtPos(0, 6);
         break;
      case 62:
         var1 = this.jjStopAtPos(0, 7);
         break;
      case 64:
         var1 = this.jjStopAtPos(0, 8);
         break;
      case 91:
         var1 = this.jjStopAtPos(0, 15);
         break;
      default:
         var1 = this.jjMoveNfa_0(1, 0);
      }

      return var1;
   }

   private final int jjMoveStringLiteralDfa0_1() {
      int var1;
      switch(this.curChar) {
      case 93:
         var1 = this.jjStopAtPos(0, 18);
         break;
      default:
         var1 = this.jjMoveNfa_1(0, 0);
      }

      return var1;
   }

   private final int jjMoveStringLiteralDfa0_2() {
      int var1;
      switch(this.curChar) {
      case 40:
         var1 = this.jjStopAtPos(0, 22);
         break;
      case 41:
         var1 = this.jjStopAtPos(0, 20);
         break;
      default:
         var1 = this.jjMoveNfa_2(0, 0);
      }

      return var1;
   }

   private final int jjMoveStringLiteralDfa0_3() {
      int var1;
      switch(this.curChar) {
      case 40:
         var1 = this.jjStopAtPos(0, 25);
         break;
      case 41:
         var1 = this.jjStopAtPos(0, 26);
         break;
      default:
         var1 = this.jjMoveNfa_3(0, 0);
      }

      return var1;
   }

   private final int jjMoveStringLiteralDfa0_4() {
      int var1;
      switch(this.curChar) {
      case 34:
         var1 = this.jjStopAtPos(0, 31);
         break;
      default:
         var1 = this.jjMoveNfa_4(0, 0);
      }

      return var1;
   }

   private final int jjStartNfaWithStates_0(int var1, int var2, int var3) {
      this.jjmatchedKind = var2;
      this.jjmatchedPos = var1;

      int var6;
      try {
         char var4 = this.input_stream.readChar();
         this.curChar = var4;
      } catch (IOException var8) {
         var6 = var1 + 1;
         return var6;
      }

      int var5 = var1 + 1;
      var6 = this.jjMoveNfa_0(var3, var5);
      return var6;
   }

   private final int jjStartNfaWithStates_1(int var1, int var2, int var3) {
      this.jjmatchedKind = var2;
      this.jjmatchedPos = var1;

      int var6;
      try {
         char var4 = this.input_stream.readChar();
         this.curChar = var4;
      } catch (IOException var8) {
         var6 = var1 + 1;
         return var6;
      }

      int var5 = var1 + 1;
      var6 = this.jjMoveNfa_1(var3, var5);
      return var6;
   }

   private final int jjStartNfaWithStates_2(int var1, int var2, int var3) {
      this.jjmatchedKind = var2;
      this.jjmatchedPos = var1;

      int var6;
      try {
         char var4 = this.input_stream.readChar();
         this.curChar = var4;
      } catch (IOException var8) {
         var6 = var1 + 1;
         return var6;
      }

      int var5 = var1 + 1;
      var6 = this.jjMoveNfa_2(var3, var5);
      return var6;
   }

   private final int jjStartNfaWithStates_3(int var1, int var2, int var3) {
      this.jjmatchedKind = var2;
      this.jjmatchedPos = var1;

      int var6;
      try {
         char var4 = this.input_stream.readChar();
         this.curChar = var4;
      } catch (IOException var8) {
         var6 = var1 + 1;
         return var6;
      }

      int var5 = var1 + 1;
      var6 = this.jjMoveNfa_3(var3, var5);
      return var6;
   }

   private final int jjStartNfaWithStates_4(int var1, int var2, int var3) {
      this.jjmatchedKind = var2;
      this.jjmatchedPos = var1;

      int var6;
      try {
         char var4 = this.input_stream.readChar();
         this.curChar = var4;
      } catch (IOException var8) {
         var6 = var1 + 1;
         return var6;
      }

      int var5 = var1 + 1;
      var6 = this.jjMoveNfa_4(var3, var5);
      return var6;
   }

   private final int jjStartNfa_0(int var1, long var2) {
      int var4 = this.jjStopStringLiteralDfa_0(var1, var2);
      int var5 = var1 + 1;
      return this.jjMoveNfa_0(var4, var5);
   }

   private final int jjStartNfa_1(int var1, long var2) {
      int var4 = this.jjStopStringLiteralDfa_1(var1, var2);
      int var5 = var1 + 1;
      return this.jjMoveNfa_1(var4, var5);
   }

   private final int jjStartNfa_2(int var1, long var2) {
      int var4 = this.jjStopStringLiteralDfa_2(var1, var2);
      int var5 = var1 + 1;
      return this.jjMoveNfa_2(var4, var5);
   }

   private final int jjStartNfa_3(int var1, long var2) {
      int var4 = this.jjStopStringLiteralDfa_3(var1, var2);
      int var5 = var1 + 1;
      return this.jjMoveNfa_3(var4, var5);
   }

   private final int jjStartNfa_4(int var1, long var2) {
      int var4 = this.jjStopStringLiteralDfa_4(var1, var2);
      int var5 = var1 + 1;
      return this.jjMoveNfa_4(var4, var5);
   }

   private final int jjStopAtPos(int var1, int var2) {
      this.jjmatchedKind = var2;
      this.jjmatchedPos = var1;
      return var1 + 1;
   }

   private final int jjStopStringLiteralDfa_0(int var1, long var2) {
      return -1;
   }

   private final int jjStopStringLiteralDfa_1(int var1, long var2) {
      return -1;
   }

   private final int jjStopStringLiteralDfa_2(int var1, long var2) {
      return -1;
   }

   private final int jjStopStringLiteralDfa_3(int var1, long var2) {
      return -1;
   }

   private final int jjStopStringLiteralDfa_4(int var1, long var2) {
      return -1;
   }

   void MoreLexicalActions() {
      int var1 = this.jjimageLen;
      int var2 = this.jjmatchedPos + 1;
      this.lengthOfMatch = var2;
      int var3 = var1 + var2;
      this.jjimageLen = var3;
      switch(this.jjmatchedKind) {
      case 16:
         if(this.image == null) {
            StringBuffer var4 = new StringBuffer();
            this.image = var4;
         }

         StringBuffer var5 = this.image;
         SimpleCharStream var6 = this.input_stream;
         int var7 = this.jjimageLen;
         char[] var8 = var6.GetSuffix(var7);
         var5.append(var8);
         this.jjimageLen = 0;
         StringBuffer var10 = this.image;
         int var11 = this.image.length() - 2;
         var10.deleteCharAt(var11);
         return;
      case 17:
      case 18:
      case 19:
      case 20:
      case 23:
      case 27:
      default:
         return;
      case 21:
         if(this.image == null) {
            StringBuffer var13 = new StringBuffer();
            this.image = var13;
         }

         StringBuffer var14 = this.image;
         SimpleCharStream var15 = this.input_stream;
         int var16 = this.jjimageLen;
         char[] var17 = var15.GetSuffix(var16);
         var14.append(var17);
         this.jjimageLen = 0;
         StringBuffer var19 = this.image;
         int var20 = this.image.length() - 2;
         var19.deleteCharAt(var20);
         return;
      case 22:
         if(this.image == null) {
            StringBuffer var22 = new StringBuffer();
            this.image = var22;
         }

         StringBuffer var23 = this.image;
         SimpleCharStream var24 = this.input_stream;
         int var25 = this.jjimageLen;
         char[] var26 = var24.GetSuffix(var25);
         var23.append(var26);
         this.jjimageLen = 0;
         commentNest = 1;
         return;
      case 24:
         if(this.image == null) {
            StringBuffer var28 = new StringBuffer();
            this.image = var28;
         }

         StringBuffer var29 = this.image;
         SimpleCharStream var30 = this.input_stream;
         int var31 = this.jjimageLen;
         char[] var32 = var30.GetSuffix(var31);
         var29.append(var32);
         this.jjimageLen = 0;
         StringBuffer var34 = this.image;
         int var35 = this.image.length() - 2;
         var34.deleteCharAt(var35);
         return;
      case 25:
         if(this.image == null) {
            StringBuffer var37 = new StringBuffer();
            this.image = var37;
         }

         StringBuffer var38 = this.image;
         SimpleCharStream var39 = this.input_stream;
         int var40 = this.jjimageLen;
         char[] var41 = var39.GetSuffix(var40);
         var38.append(var41);
         this.jjimageLen = 0;
         ++commentNest;
         return;
      case 26:
         if(this.image == null) {
            StringBuffer var43 = new StringBuffer();
            this.image = var43;
         }

         StringBuffer var44 = this.image;
         SimpleCharStream var45 = this.input_stream;
         int var46 = this.jjimageLen;
         char[] var47 = var45.GetSuffix(var46);
         var44.append(var47);
         this.jjimageLen = 0;
         --commentNest;
         if(commentNest != 0) {
            return;
         }

         this.SwitchTo(2);
         return;
      case 28:
         if(this.image == null) {
            StringBuffer var49 = new StringBuffer();
            this.image = var49;
         }

         StringBuffer var50 = this.image;
         SimpleCharStream var51 = this.input_stream;
         int var52 = this.jjimageLen;
         char[] var53 = var51.GetSuffix(var52);
         var50.append(var53);
         this.jjimageLen = 0;
         StringBuffer var55 = this.image;
         int var56 = this.image.length() - 1;
         var55.deleteCharAt(var56);
         return;
      case 29:
         if(this.image == null) {
            StringBuffer var58 = new StringBuffer();
            this.image = var58;
         }

         StringBuffer var59 = this.image;
         SimpleCharStream var60 = this.input_stream;
         int var61 = this.jjimageLen;
         char[] var62 = var60.GetSuffix(var61);
         var59.append(var62);
         this.jjimageLen = 0;
         StringBuffer var64 = this.image;
         int var65 = this.image.length() - 2;
         var64.deleteCharAt(var65);
      }
   }

   public void ReInit(SimpleCharStream var1) {
      this.jjnewStateCnt = 0;
      this.jjmatchedPos = 0;
      int var2 = this.defaultLexState;
      this.curLexState = var2;
      this.input_stream = var1;
      this.ReInitRounds();
   }

   public void ReInit(SimpleCharStream var1, int var2) {
      this.ReInit(var1);
      this.SwitchTo(var2);
   }

   public void SwitchTo(int var1) {
      if(var1 < 5 && var1 >= 0) {
         this.curLexState = var1;
      } else {
         String var2 = "Error: Ignoring invalid lexical state : " + var1 + ". State unchanged.";
         throw new TokenMgrError(var2, 2);
      }
   }

   void TokenLexicalActions(Token var1) {
      switch(this.jjmatchedKind) {
      case 18:
         if(this.image == null) {
            StringBuffer var2 = new StringBuffer();
            this.image = var2;
         }

         StringBuffer var3 = this.image;
         SimpleCharStream var4 = this.input_stream;
         int var5 = this.jjimageLen;
         int var6 = this.jjmatchedPos + 1;
         this.lengthOfMatch = var6;
         int var7 = var5 + var6;
         char[] var8 = var4.GetSuffix(var7);
         var3.append(var8);
         String var10 = this.image.toString();
         var1.image = var10;
         return;
      case 31:
         if(this.image == null) {
            StringBuffer var11 = new StringBuffer();
            this.image = var11;
         }

         StringBuffer var12 = this.image;
         SimpleCharStream var13 = this.input_stream;
         int var14 = this.jjimageLen;
         int var15 = this.jjmatchedPos + 1;
         this.lengthOfMatch = var15;
         int var16 = var14 + var15;
         char[] var17 = var13.GetSuffix(var16);
         var12.append(var17);
         StringBuffer var19 = this.image;
         int var20 = this.image.length() - 1;
         String var21 = var19.substring(0, var20);
         var1.image = var21;
         return;
      default:
      }
   }

   public Token getNextToken() {
      Token var1 = null;
      int var2 = 0;

      while(true) {
         Token var20;
         try {
            char var3 = this.input_stream.BeginToken();
            this.curChar = var3;
         } catch (IOException var60) {
            this.jjmatchedKind = 0;
            Token var22 = this.jjFillToken();
            var22.specialToken = var1;
            var20 = var22;
            return var20;
         }

         this.image = null;
         this.jjimageLen = 0;

         while(true) {
            switch(this.curLexState) {
            case 0:
               this.jjmatchedKind = Integer.MAX_VALUE;
               this.jjmatchedPos = 0;
               var2 = this.jjMoveStringLiteralDfa0_0();
               break;
            case 1:
               this.jjmatchedKind = Integer.MAX_VALUE;
               this.jjmatchedPos = 0;
               var2 = this.jjMoveStringLiteralDfa0_1();
               break;
            case 2:
               this.jjmatchedKind = Integer.MAX_VALUE;
               this.jjmatchedPos = 0;
               var2 = this.jjMoveStringLiteralDfa0_2();
               break;
            case 3:
               this.jjmatchedKind = Integer.MAX_VALUE;
               this.jjmatchedPos = 0;
               var2 = this.jjMoveStringLiteralDfa0_3();
               break;
            case 4:
               this.jjmatchedKind = Integer.MAX_VALUE;
               this.jjmatchedPos = 0;
               var2 = this.jjMoveStringLiteralDfa0_4();
            }

            if(this.jjmatchedKind != Integer.MAX_VALUE) {
               if(this.jjmatchedPos + 1 < var2) {
                  SimpleCharStream var4 = this.input_stream;
                  int var5 = this.jjmatchedPos;
                  int var6 = var2 - var5 - 1;
                  var4.backup(var6);
               }

               long[] var7 = jjtoToken;
               int var8 = this.jjmatchedKind >> 6;
               long var9 = var7[var8];
               int var11 = this.jjmatchedKind & 63;
               long var12 = 1L << var11;
               if((var9 & var12) != 0L) {
                  Token var14 = this.jjFillToken();
                  var14.specialToken = var1;
                  this.TokenLexicalActions(var14);
                  int[] var15 = jjnewLexState;
                  int var16 = this.jjmatchedKind;
                  if(var15[var16] != -1) {
                     int[] var17 = jjnewLexState;
                     int var18 = this.jjmatchedKind;
                     int var19 = var17[var18];
                     this.curLexState = var19;
                  }

                  var20 = var14;
                  return var20;
               }

               long[] var23 = jjtoSkip;
               int var24 = this.jjmatchedKind >> 6;
               long var25 = var23[var24];
               int var27 = this.jjmatchedKind & 63;
               long var28 = 1L << var27;
               if((var25 & var28) != 0L) {
                  long[] var30 = jjtoSpecial;
                  int var31 = this.jjmatchedKind >> 6;
                  long var32 = var30[var31];
                  int var34 = this.jjmatchedKind & 63;
                  long var35 = 1L << var34;
                  if((var32 & var35) != 0L) {
                     Token var37 = this.jjFillToken();
                     if(var1 == null) {
                        var1 = var37;
                     } else {
                        var37.specialToken = var1;
                        var1.next = var37;
                        var1 = var37;
                     }
                  }

                  int[] var38 = jjnewLexState;
                  int var39 = this.jjmatchedKind;
                  if(var38[var39] != -1) {
                     int[] var40 = jjnewLexState;
                     int var41 = this.jjmatchedKind;
                     int var42 = var40[var41];
                     this.curLexState = var42;
                  }
                  break;
               }

               this.MoreLexicalActions();
               int[] var43 = jjnewLexState;
               int var44 = this.jjmatchedKind;
               if(var43[var44] != -1) {
                  int[] var45 = jjnewLexState;
                  int var46 = this.jjmatchedKind;
                  int var47 = var45[var46];
                  this.curLexState = var47;
               }

               var2 = 0;
               this.jjmatchedKind = Integer.MAX_VALUE;

               try {
                  char var48 = this.input_stream.readChar();
                  this.curChar = var48;
                  continue;
               } catch (IOException var59) {
                  ;
               }
            }

            int var50 = this.input_stream.getEndLine();
            int var51 = this.input_stream.getEndColumn();
            String var52 = null;
            byte var53 = 0;

            try {
               char var54 = this.input_stream.readChar();
               this.input_stream.backup(1);
            } catch (IOException var58) {
               var53 = 1;
               if(var2 <= 1) {
                  var52 = "";
               } else {
                  var52 = this.input_stream.GetImage();
               }

               if(this.curChar != 10 && this.curChar != 13) {
                  ++var51;
               } else {
                  ++var50;
                  var51 = 0;
               }
            }

            if(var53 == 0) {
               this.input_stream.backup(1);
               if(var2 <= 1) {
                  var52 = "";
               } else {
                  var52 = this.input_stream.GetImage();
               }
            }

            int var55 = this.curLexState;
            char var56 = this.curChar;
            throw new TokenMgrError((boolean)var53, var55, var50, var51, var52, var56, 0);
         }
      }
   }

   protected Token jjFillToken() {
      Token var1 = Token.newToken(this.jjmatchedKind);
      int var2 = this.jjmatchedKind;
      var1.kind = var2;
      String[] var3 = jjstrLiteralImages;
      int var4 = this.jjmatchedKind;
      String var5 = var3[var4];
      String var6;
      if(var5 == null) {
         var6 = this.input_stream.GetImage();
      } else {
         var6 = var5;
      }

      var1.image = var6;
      int var7 = this.input_stream.getBeginLine();
      var1.beginLine = var7;
      int var8 = this.input_stream.getBeginColumn();
      var1.beginColumn = var8;
      int var9 = this.input_stream.getEndLine();
      var1.endLine = var9;
      int var10 = this.input_stream.getEndColumn();
      var1.endColumn = var10;
      return var1;
   }

   public void setDebugStream(PrintStream var1) {
      this.debugStream = var1;
   }
}
