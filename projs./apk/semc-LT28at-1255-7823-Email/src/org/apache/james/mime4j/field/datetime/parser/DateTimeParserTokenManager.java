package org.apache.james.mime4j.field.datetime.parser;

import java.io.IOException;
import java.io.PrintStream;
import org.apache.james.mime4j.field.datetime.parser.DateTimeParserConstants;
import org.apache.james.mime4j.field.datetime.parser.SimpleCharStream;
import org.apache.james.mime4j.field.datetime.parser.Token;
import org.apache.james.mime4j.field.datetime.parser.TokenMgrError;

public class DateTimeParserTokenManager implements DateTimeParserConstants {

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
      String[] var0 = new String[]{"", "\r", "\n", ",", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec", ":", false, "UT", "GMT", "EST", "EDT", "CST", "CDT", "MST", "MDT", "PST", "PDT", false, false, false, false, false, false, false, false, false, false, false, false, false, false};
      jjstrLiteralImages = var0;
      String[] var1 = new String[]{"DEFAULT", "INCOMMENT", "NESTED_COMMENT"};
      lexStateNames = var1;
      jjnewLexState = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 0, -1, 2, -1, -1, -1, -1, -1, -1, -1, -1};
      Object var2 = null;
      ((Object[])var2)[0] = 70437463654399L;
      jjtoToken = (long[])var2;
      Object var3 = null;
      ((Object[])var3)[0] = 343597383680L;
      jjtoSkip = (long[])var3;
      Object var4 = null;
      ((Object[])var4)[0] = 68719476736L;
      jjtoSpecial = (long[])var4;
      Object var5 = null;
      ((Object[])var5)[0] = 69956427317248L;
      jjtoMore = (long[])var5;
   }

   public DateTimeParserTokenManager(SimpleCharStream var1) {
      PrintStream var2 = System.out;
      this.debugStream = var2;
      int[] var3 = new int[4];
      this.jjrounds = var3;
      int[] var4 = new int[8];
      this.jjstateSet = var4;
      this.curLexState = 0;
      this.defaultLexState = 0;
      this.input_stream = var1;
   }

   public DateTimeParserTokenManager(SimpleCharStream var1, int var2) {
      this(var1);
      this.SwitchTo(var2);
   }

   private final void ReInitRounds() {
      this.jjround = -2147483647;
      int var1 = 4;

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
      this.jjnewStateCnt = 4;
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
                  if((287948901175001088L & var8) != 0L) {
                     if(var5 > 46) {
                        var5 = 46;
                     }

                     this.jjCheckNAdd(3);
                  } else if((4294967808L & var8) != 0L) {
                     if(var5 > 36) {
                        var5 = 36;
                     }

                     this.jjCheckNAdd(2);
                  } else if((43980465111040L & var8) != 0L && var5 > 24) {
                     var5 = 24;
                  }
               case 1:
               default:
                  break;
               case 2:
                  if((4294967808L & var8) != 0L) {
                     var5 = 36;
                     this.jjCheckNAdd(2);
                  }
                  break;
               case 3:
                  if((287948901175001088L & var8) != 0L) {
                     var5 = 46;
                     this.jjCheckNAdd(3);
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
                  if((576456345801194494L & var8) != 0L) {
                     var5 = 35;
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
         var3 = 4 - var3;
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
                  if(var5 > 41) {
                     var5 = 41;
                  }
                  break;
               case 1:
                  if(var5 > 39) {
                     var5 = 39;
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
                  if(var5 > 41) {
                     var5 = 41;
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
                  if(var5 > 39) {
                     var5 = 39;
                  }
                  break;
               case 2:
                  if(var5 > 41) {
                     var5 = 41;
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
                  if((jjbitVec0[var18] & var20) != 0L && var5 > 41) {
                     var5 = 41;
                  }
                  break;
               case 1:
                  if((jjbitVec0[var18] & var20) != 0L && var5 > 39) {
                     var5 = 39;
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
                  if(var5 > 45) {
                     var5 = 45;
                  }
                  break;
               case 1:
                  if(var5 > 42) {
                     var5 = 42;
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
                  if(var5 > 45) {
                     var5 = 45;
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
                  if(var5 > 42) {
                     var5 = 42;
                  }
                  break;
               case 2:
                  if(var5 > 45) {
                     var5 = 45;
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
                  if((jjbitVec0[var18] & var20) != 0L && var5 > 45) {
                     var5 = 45;
                  }
                  break;
               case 1:
                  if((jjbitVec0[var18] & var20) != 0L && var5 > 42) {
                     var5 = 42;
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

   private final int jjMoveStringLiteralDfa0_0() {
      int var1;
      switch(this.curChar) {
      case 10:
         var1 = this.jjStopAtPos(0, 2);
         break;
      case 13:
         var1 = this.jjStopAtPos(0, 1);
         break;
      case 40:
         var1 = this.jjStopAtPos(0, 37);
         break;
      case 44:
         var1 = this.jjStopAtPos(0, 3);
         break;
      case 58:
         var1 = this.jjStopAtPos(0, 23);
         break;
      case 65:
         var1 = this.jjMoveStringLiteralDfa1_0(278528L);
         break;
      case 67:
         var1 = this.jjMoveStringLiteralDfa1_0(1610612736L);
         break;
      case 68:
         var1 = this.jjMoveStringLiteralDfa1_0(4194304L);
         break;
      case 69:
         var1 = this.jjMoveStringLiteralDfa1_0(402653184L);
         break;
      case 70:
         var1 = this.jjMoveStringLiteralDfa1_0(4352L);
         break;
      case 71:
         var1 = this.jjMoveStringLiteralDfa1_0(67108864L);
         break;
      case 74:
         var1 = this.jjMoveStringLiteralDfa1_0(198656L);
         break;
      case 77:
         var1 = this.jjMoveStringLiteralDfa1_0(6442491920L);
         break;
      case 78:
         var1 = this.jjMoveStringLiteralDfa1_0(2097152L);
         break;
      case 79:
         var1 = this.jjMoveStringLiteralDfa1_0(1048576L);
         break;
      case 80:
         var1 = this.jjMoveStringLiteralDfa1_0(25769803776L);
         break;
      case 83:
         var1 = this.jjMoveStringLiteralDfa1_0(525824L);
         break;
      case 84:
         var1 = this.jjMoveStringLiteralDfa1_0(160L);
         break;
      case 85:
         var1 = this.jjMoveStringLiteralDfa1_0(33554432L);
         break;
      case 87:
         var1 = this.jjMoveStringLiteralDfa1_0(64L);
         break;
      default:
         var1 = this.jjMoveNfa_0(0, 0);
      }

      return var1;
   }

   private final int jjMoveStringLiteralDfa0_1() {
      int var1;
      switch(this.curChar) {
      case 40:
         var1 = this.jjStopAtPos(0, 40);
         break;
      case 41:
         var1 = this.jjStopAtPos(0, 38);
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
         var1 = this.jjStopAtPos(0, 43);
         break;
      case 41:
         var1 = this.jjStopAtPos(0, 44);
         break;
      default:
         var1 = this.jjMoveNfa_2(0, 0);
      }

      return var1;
   }

   private final int jjMoveStringLiteralDfa1_0(long var1) {
      int var4;
      try {
         char var3 = this.input_stream.readChar();
         this.curChar = var3;
      } catch (IOException var7) {
         this.jjStopStringLiteralDfa_0(0, var1);
         var4 = 1;
         return var4;
      }

      switch(this.curChar) {
      case 68:
         var4 = this.jjMoveStringLiteralDfa2_0(var1, 22817013760L);
         break;
      case 77:
         var4 = this.jjMoveStringLiteralDfa2_0(var1, 67108864L);
         break;
      case 83:
         var4 = this.jjMoveStringLiteralDfa2_0(var1, 11408506880L);
         break;
      case 84:
         if((33554432L & var1) != 0L) {
            var4 = this.jjStopAtPos(1, 25);
            break;
         }
      case 97:
         var4 = this.jjMoveStringLiteralDfa2_0(var1, 43520L);
         break;
      case 99:
         var4 = this.jjMoveStringLiteralDfa2_0(var1, 1048576L);
         break;
      case 101:
         var4 = this.jjMoveStringLiteralDfa2_0(var1, 4722752L);
         break;
      case 104:
         var4 = this.jjMoveStringLiteralDfa2_0(var1, 128L);
         break;
      case 111:
         var4 = this.jjMoveStringLiteralDfa2_0(var1, 2097168L);
         break;
      case 112:
         var4 = this.jjMoveStringLiteralDfa2_0(var1, 16384L);
         break;
      case 114:
         var4 = this.jjMoveStringLiteralDfa2_0(var1, 256L);
         break;
      case 117:
         var4 = this.jjMoveStringLiteralDfa2_0(var1, 459808L);
         break;
      default:
         var4 = this.jjStartNfa_0(0, var1);
      }

      return var4;
   }

   private final int jjMoveStringLiteralDfa2_0(long var1, long var3) {
      long var5 = var3 & var1;
      int var7;
      if(var5 == 0L) {
         var7 = this.jjStartNfa_0(0, var1);
      } else {
         try {
            char var8 = this.input_stream.readChar();
            this.curChar = var8;
         } catch (IOException var11) {
            this.jjStopStringLiteralDfa_0(1, var5);
            var7 = 2;
            return var7;
         }

         switch(this.curChar) {
         case 84:
            if((67108864L & var5) != 0L) {
               var7 = this.jjStopAtPos(2, 26);
               return var7;
            }

            if((134217728L & var5) != 0L) {
               var7 = this.jjStopAtPos(2, 27);
               return var7;
            }

            if((268435456L & var5) != 0L) {
               var7 = this.jjStopAtPos(2, 28);
               return var7;
            }

            if((536870912L & var5) != 0L) {
               var7 = this.jjStopAtPos(2, 29);
               return var7;
            }

            if((1073741824L & var5) != 0L) {
               var7 = this.jjStopAtPos(2, 30);
               return var7;
            }

            if((2147483648L & var5) != 0L) {
               var7 = this.jjStopAtPos(2, 31);
               return var7;
            }

            if((4294967296L & var5) != 0L) {
               var7 = this.jjStopAtPos(2, 32);
               return var7;
            }

            if((8589934592L & var5) != 0L) {
               var7 = this.jjStopAtPos(2, 33);
               return var7;
            }

            if((17179869184L & var5) != 0L) {
               var7 = this.jjStopAtPos(2, 34);
               return var7;
            }
            break;
         case 98:
            if((4096L & var5) != 0L) {
               var7 = this.jjStopAtPos(2, 12);
               return var7;
            }
            break;
         case 99:
            if((4194304L & var5) != 0L) {
               var7 = this.jjStopAtPos(2, 22);
               return var7;
            }
            break;
         case 100:
            if((64L & var5) != 0L) {
               var7 = this.jjStopAtPos(2, 6);
               return var7;
            }
            break;
         case 101:
            if((32L & var5) != 0L) {
               var7 = this.jjStopAtPos(2, 5);
               return var7;
            }
            break;
         case 103:
            if((262144L & var5) != 0L) {
               var7 = this.jjStopAtPos(2, 18);
               return var7;
            }
            break;
         case 105:
            if((256L & var5) != 0L) {
               var7 = this.jjStopAtPos(2, 8);
               return var7;
            }
            break;
         case 108:
            if((131072L & var5) != 0L) {
               var7 = this.jjStopAtPos(2, 17);
               return var7;
            }
            break;
         case 110:
            if((16L & var5) != 0L) {
               var7 = this.jjStopAtPos(2, 4);
               return var7;
            }

            if((1024L & var5) != 0L) {
               var7 = this.jjStopAtPos(2, 10);
               return var7;
            }

            if((2048L & var5) != 0L) {
               var7 = this.jjStopAtPos(2, 11);
               return var7;
            }

            if((65536L & var5) != 0L) {
               var7 = this.jjStopAtPos(2, 16);
               return var7;
            }
            break;
         case 112:
            if((524288L & var5) != 0L) {
               var7 = this.jjStopAtPos(2, 19);
               return var7;
            }
            break;
         case 114:
            if((8192L & var5) != 0L) {
               var7 = this.jjStopAtPos(2, 13);
               return var7;
            }

            if((16384L & var5) != 0L) {
               var7 = this.jjStopAtPos(2, 14);
               return var7;
            }
            break;
         case 116:
            if((512L & var5) != 0L) {
               var7 = this.jjStopAtPos(2, 9);
               return var7;
            }

            if((1048576L & var5) != 0L) {
               var7 = this.jjStopAtPos(2, 20);
               return var7;
            }
            break;
         case 117:
            if((128L & var5) != 0L) {
               var7 = this.jjStopAtPos(2, 7);
               return var7;
            }
            break;
         case 118:
            if((2097152L & var5) != 0L) {
               var7 = this.jjStopAtPos(2, 21);
               return var7;
            }
            break;
         case 121:
            if((32768L & var5) != 0L) {
               var7 = this.jjStopAtPos(2, 15);
               return var7;
            }
         }

         var7 = this.jjStartNfa_0(1, var5);
      }

      return var7;
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

   private final int jjStopAtPos(int var1, int var2) {
      this.jjmatchedKind = var2;
      this.jjmatchedPos = var1;
      return var1 + 1;
   }

   private final int jjStopStringLiteralDfa_0(int var1, long var2) {
      byte var4;
      switch(var1) {
      case 0:
         if((34334373872L & var2) != 0L) {
            this.jjmatchedKind = 35;
            var4 = -1;
         } else {
            var4 = -1;
         }
         break;
      case 1:
         if((34334373872L & var2) != 0L) {
            if(this.jjmatchedPos == 0) {
               this.jjmatchedKind = 35;
               this.jjmatchedPos = 0;
            }

            var4 = -1;
         } else {
            var4 = -1;
         }
         break;
      default:
         var4 = -1;
      }

      return var4;
   }

   private final int jjStopStringLiteralDfa_1(int var1, long var2) {
      return -1;
   }

   private final int jjStopStringLiteralDfa_2(int var1, long var2) {
      return -1;
   }

   void MoreLexicalActions() {
      int var1 = this.jjimageLen;
      int var2 = this.jjmatchedPos + 1;
      this.lengthOfMatch = var2;
      int var3 = var1 + var2;
      this.jjimageLen = var3;
      switch(this.jjmatchedKind) {
      case 39:
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
      case 40:
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
         commentNest = 1;
         return;
      case 41:
      default:
         return;
      case 42:
         if(this.image == null) {
            StringBuffer var19 = new StringBuffer();
            this.image = var19;
         }

         StringBuffer var20 = this.image;
         SimpleCharStream var21 = this.input_stream;
         int var22 = this.jjimageLen;
         char[] var23 = var21.GetSuffix(var22);
         var20.append(var23);
         this.jjimageLen = 0;
         StringBuffer var25 = this.image;
         int var26 = this.image.length() - 2;
         var25.deleteCharAt(var26);
         return;
      case 43:
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
         ++commentNest;
         return;
      case 44:
         if(this.image == null) {
            StringBuffer var34 = new StringBuffer();
            this.image = var34;
         }

         StringBuffer var35 = this.image;
         SimpleCharStream var36 = this.input_stream;
         int var37 = this.jjimageLen;
         char[] var38 = var36.GetSuffix(var37);
         var35.append(var38);
         this.jjimageLen = 0;
         --commentNest;
         if(commentNest == 0) {
            this.SwitchTo(1);
         }
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
      if(var1 < 3 && var1 >= 0) {
         this.curLexState = var1;
      } else {
         String var2 = "Error: Ignoring invalid lexical state : " + var1 + ". State unchanged.";
         throw new TokenMgrError(var2, 2);
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
