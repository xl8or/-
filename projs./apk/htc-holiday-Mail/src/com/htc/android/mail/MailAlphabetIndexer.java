package com.htc.android.mail;

import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.SparseIntArray;
import android.widget.SectionIndexer;
import com.htc.android.mail.Mail;
import com.htc.android.mail.Util;
import com.htc.android.mail.ll;
import java.text.Collator;
import java.util.Calendar;

public class MailAlphabetIndexer extends DataSetObserver implements SectionIndexer {

   public static final int DATE_TYPE = 1;
   public static final int FROM_TYPE = 4;
   public static final int PRIORITY_TYPE = 2;
   public static final int SIZE_TYPE = 5;
   public static final int SUBJECT_TYPE = 3;
   public static final int UNKNOWN_TYPE;
   final String TAG = "MailAlphabetIndexer";
   long baseTime;
   final long dayUnitLong = 86400000L;
   private SparseIntArray mAlphaMap;
   protected CharSequence mAlphabet;
   private String[] mAlphabetArray;
   private int mAlphabetLength = 0;
   private Collator mCollator;
   protected int mColumnIndex;
   private Context mContext;
   protected Cursor mDataCursor;
   private Time mNowtime;
   private boolean mRevertOrder = 0;
   private int mType = 0;
   int tDur;


   public MailAlphabetIndexer(Context var1, Cursor var2, int var3, CharSequence var4) {
      Time var5 = new Time();
      this.mNowtime = var5;
      Context var6 = var1.getApplicationContext();
      this.mContext = var6;
      this.mDataCursor = var2;
      this.mColumnIndex = var3;
      this.mAlphabet = var4;
      int var7 = var4.length();
      this.mAlphabetLength = var7;
      String[] var8 = new String[this.mAlphabetLength];
      this.mAlphabetArray = var8;
      int var9 = 0;

      while(true) {
         int var10 = this.mAlphabetLength;
         if(var9 >= var10) {
            int var13 = this.mAlphabetLength;
            SparseIntArray var14 = new SparseIntArray(var13);
            this.mAlphaMap = var14;
            if(var2 != null) {
               var2.registerDataSetObserver(this);
            }

            Collator var15 = Collator.getInstance();
            this.mCollator = var15;
            this.mCollator.setStrength(0);
            return;
         }

         String[] var11 = this.mAlphabetArray;
         String var12 = Character.toString(this.mAlphabet.charAt(var9));
         var11[var9] = var12;
         if(this.mAlphabet.charAt(var9) == 32) {
            this.mAlphabetArray[var9] = "#";
         }

         ++var9;
      }
   }

   private void calDateArray() {
      long var1 = 0L;
      long var3 = 0L;
      byte var5 = 0;
      this.tDur = var5;
      Cursor var6 = this.mDataCursor;
      if(var6 == null) {
         if(Mail.MAIL_DETAIL_DEBUG) {
            ll.d("MailAlphabetIndexer", "mDataCursor null>");
         }
      } else {
         int var7 = Calendar.getInstance().getTimeZone().getRawOffset();
         if(Mail.MAIL_DETAIL_DEBUG) {
            StringBuilder var8 = (new StringBuilder()).append("calDateArray>");
            String var10 = var8.append(var7).toString();
            ll.d("MailAlphabetIndexer", var10);
         }

         if(var6.moveToFirst()) {
            int var11 = this.mColumnIndex;
            var1 = var6.getLong(var11) / 86400000L;
            if(Mail.MAIL_DETAIL_DEBUG) {
               long var14 = 86400000L * var1;
               long var16 = (long)var7;
               long var18 = var14 - var16;
               Context var20 = this.mContext;
               byte var25 = 1;
               String var26 = this.getRelativeTimeSpanString(var20, var18, (boolean)var25).toString();
               StringBuilder var27 = (new StringBuilder()).append("Start>");
               StringBuilder var30 = var27.append(var18).append(",");
               String var32 = var30.append(var26).toString();
               ll.d("MailAlphabetIndexer", var32);
            }
         }

         if(var6.moveToLast()) {
            int var33 = this.mColumnIndex;
            var3 = var6.getLong(var33) / 86400000L;
            if(Mail.MAIL_DETAIL_DEBUG) {
               long var36 = 86400000L * var3;
               long var38 = (long)var7;
               long var40 = var36 - var38;
               Context var42 = this.mContext;
               byte var47 = 1;
               String var48 = this.getRelativeTimeSpanString(var42, var40, (boolean)var47).toString();
               StringBuilder var49 = (new StringBuilder()).append("End>");
               StringBuilder var52 = var49.append(var40).append(",");
               String var54 = var52.append(var48).toString();
               ll.d("MailAlphabetIndexer", var54);
            }
         }

         int var55 = (int)(var1 - var3);
         this.tDur = var55;
         int var56 = Math.abs(this.tDur) + 1;
         this.tDur = var56;
         if(Mail.MAIL_DETAIL_DEBUG) {
            StringBuilder var57 = (new StringBuilder()).append("calDateArray<");
            int var58 = this.tDur;
            StringBuilder var59 = var57.append(var58).append(",");
            StringBuilder var62 = var59.append(var1).append(",");
            StringBuilder var65 = var62.append(var3).append(",");
            String var67 = var65.append(var7).toString();
            ll.d("MailAlphabetIndexer", var67);
         }

         long var68 = 86400000L * var1;
         long var70 = (long)var7;
         long var72 = var68 - var70;
         this.baseTime = var72;
         if(Mail.MAIL_DETAIL_DEBUG) {
            Context var74 = this.mContext;
            long var75 = this.baseTime;
            byte var81 = 1;
            String var82 = this.getRelativeTimeSpanString(var74, var75, (boolean)var81).toString();
            if(Mail.MAIL_DETAIL_DEBUG) {
               StringBuilder var83 = (new StringBuilder()).append("baseTime>>");
               boolean var84 = this.mRevertOrder;
               StringBuilder var85 = var83.append(var84).append(",");
               long var86 = this.baseTime;
               StringBuilder var88 = var85.append(var86).append(",");
               String var90 = var88.append(var82).toString();
               ll.d("MailAlphabetIndexer", var90);
            }
         }

         String[] var91 = new String[this.tDur];
         this.mAlphabetArray = var91;
         String var92 = Util.getBladeDateFormat(this.mContext);
         Calendar var93 = Calendar.getInstance();
         int var94 = 0;

         while(true) {
            int var95 = this.tDur;
            if(var94 >= var95) {
               int var124 = this.tDur;
               this.mAlphabetLength = var124;
               return;
            }

            long var102;
            if(this.mRevertOrder) {
               long var98 = this.baseTime;
               long var100 = (long)var94 * 86400000L;
               var102 = var98 - var100;
            } else {
               long var120 = this.baseTime;
               long var122 = (long)var94 * 86400000L;
               var102 = var120 + var122;
            }

            var93.setTimeInMillis(var102);
            String[] var104 = this.mAlphabetArray;
            String var105 = DateFormat.format(var92, var93).toString().trim();
            var104[var94] = var105;
            if(Mail.MAIL_DETAIL_DEBUG) {
               Context var106 = this.mContext;
               byte var111 = 1;
               String var112 = this.getRelativeTimeSpanString(var106, var102, (boolean)var111).toString();
               StringBuilder var113 = (new StringBuilder()).append("mAlphabetArray>");
               StringBuilder var115 = var113.append(var94).append(",");
               String var116 = this.mAlphabetArray[var94];
               StringBuilder var117 = var115.append(var116).append(",");
               String var119 = var117.append(var112).toString();
               ll.d("MailAlphabetIndexer", var119);
            }

            ++var94;
         }
      }
   }

   private void getPriorityArray() {
      String[] var1 = new String[3];
      this.mAlphabetArray = var1;
      if(this.mRevertOrder) {
         String[] var2 = this.mAlphabetArray;
         String var3 = this.mContext.getString(2131361817);
         var2[0] = var3;
         String[] var4 = this.mAlphabetArray;
         String var5 = this.mContext.getString(2131361818);
         var4[1] = var5;
         String[] var6 = this.mAlphabetArray;
         String var7 = this.mContext.getString(2131361819);
         var6[2] = var7;
      } else {
         String[] var8 = this.mAlphabetArray;
         String var9 = this.mContext.getString(2131361819);
         var8[0] = var9;
         String[] var10 = this.mAlphabetArray;
         String var11 = this.mContext.getString(2131361818);
         var10[1] = var11;
         String[] var12 = this.mAlphabetArray;
         String var13 = this.mContext.getString(2131361817);
         var12[2] = var13;
      }
   }

   private CharSequence getRelativeTimeSpanString(Context var1, long var2, boolean var4) {
      this.mNowtime.set(var2);
      long var5 = this.mNowtime.toMillis((boolean)0);
      return DateFormat.format("M/d k:m:s", var5);
   }

   private void getSizeArray() {
      String[] var1 = new String[7];
      this.mAlphabetArray = var1;
      if(this.mRevertOrder) {
         String[] var2 = this.mAlphabetArray;
         String var3 = this.mContext.getString(2131361816);
         var2[0] = var3;
         String[] var4 = this.mAlphabetArray;
         String var5 = this.mContext.getString(2131361815);
         var4[1] = var5;
         String[] var6 = this.mAlphabetArray;
         String var7 = this.mContext.getString(2131361814);
         var6[2] = var7;
         String[] var8 = this.mAlphabetArray;
         String var9 = this.mContext.getString(2131361813);
         var8[3] = var9;
         String[] var10 = this.mAlphabetArray;
         String var11 = this.mContext.getString(2131361812);
         var10[4] = var11;
         String[] var12 = this.mAlphabetArray;
         String var13 = this.mContext.getString(2131361811);
         var12[5] = var13;
         String[] var14 = this.mAlphabetArray;
         String var15 = this.mContext.getString(2131361810);
         var14[6] = var15;
      } else {
         String[] var16 = this.mAlphabetArray;
         String var17 = this.mContext.getString(2131361810);
         var16[0] = var17;
         String[] var18 = this.mAlphabetArray;
         String var19 = this.mContext.getString(2131361811);
         var18[1] = var19;
         String[] var20 = this.mAlphabetArray;
         String var21 = this.mContext.getString(2131361812);
         var20[2] = var21;
         String[] var22 = this.mAlphabetArray;
         String var23 = this.mContext.getString(2131361813);
         var22[3] = var23;
         String[] var24 = this.mAlphabetArray;
         String var25 = this.mContext.getString(2131361814);
         var24[4] = var25;
         String[] var26 = this.mAlphabetArray;
         String var27 = this.mContext.getString(2131361815);
         var26[5] = var27;
         String[] var28 = this.mAlphabetArray;
         String var29 = this.mContext.getString(2131361816);
         var28[6] = var29;
      }
   }

   protected int compare(String var1, String var2) {
      int var3;
      if(TextUtils.isEmpty(var1)) {
         var3 = -1;
      } else {
         Collator var4 = this.mCollator;
         String var5 = var1.substring(0, 1);
         var3 = var4.compare(var5, var2);
      }

      return var3;
   }

   public int getPositionForSection(int var1) {
      int var4;
      switch(this.mType) {
      case 1:
         var4 = this.getPositionForSectionByDay(var1);
         break;
      case 2:
         var4 = this.getPositionForSectionByPriority(var1);
         break;
      case 3:
      case 4:
      default:
         SparseIntArray var2 = this.mAlphaMap;
         Cursor var3 = this.mDataCursor;
         if(var3 != null && this.mAlphabet != null) {
            if(var1 <= 0) {
               if(Mail.MAIL_DETAIL_DEBUG) {
                  ll.d("MailAlphabetIndexer", "return 0 a2>");
               }

               var4 = 0;
            } else {
               int var5 = this.mAlphabetLength;
               if(var1 >= var5) {
                  var1 = this.mAlphabetLength - 1;
               }

               int var8 = var3.getPosition();
               int var9 = var3.getCount();
               int var10 = 0;
               int var11 = var9;
               if(Mail.MAIL_DETAIL_DEBUG) {
                  StringBuilder var12 = (new StringBuilder()).append("getPositionForSection>");
                  StringBuilder var14 = var12.append(var1).append(",");
                  StringBuilder var16 = var14.append(var8).append(",");
                  StringBuilder var18 = var16.append(var9).append(",");
                  CharSequence var19 = this.mAlphabet;
                  String var20 = var18.append(var19).toString();
                  ll.d("MailAlphabetIndexer", var20);
               }

               CharSequence var21 = this.mAlphabet;
               char var23 = var21.charAt(var1);
               String var24 = Character.toString(var23);
               if(Mail.MAIL_DETAIL_DEBUG) {
                  StringBuilder var26 = (new StringBuilder()).append("letter>");
                  StringBuilder var28 = var26.append(var1).append(",");
                  StringBuilder var30 = var28.append(var23).append(",");
                  String var32 = var30.append(var23).toString();
                  ll.d("MailAlphabetIndexer", var32);
               }

               int var35 = Integer.MIN_VALUE;
               int var36 = var2.get(var23, var35);
               int var37 = Integer.MIN_VALUE;
               if(var37 != var36) {
                  if(Mail.MAIL_DETAIL_DEBUG) {
                     StringBuilder var39 = (new StringBuilder()).append("pos>");
                     String var41 = var39.append(var36).append(",").append(Integer.MIN_VALUE).toString();
                     ll.d("MailAlphabetIndexer", var41);
                  }

                  if(var36 >= 0) {
                     if(Mail.MAIL_DETAIL_DEBUG) {
                        StringBuilder var84 = (new StringBuilder()).append("pos b>");
                        String var86 = var84.append(var36).toString();
                        ll.d("MailAlphabetIndexer", var86);
                     }

                     var4 = var36;
                     break;
                  }

                  if(Mail.MAIL_DETAIL_DEBUG) {
                     StringBuilder var42 = (new StringBuilder()).append("pos a>");
                     String var44 = var42.append(var36).toString();
                     ll.d("MailAlphabetIndexer", var44);
                  }

                  var11 = -var36;
               }

               if(var1 > 0) {
                  CharSequence var45 = this.mAlphabet;
                  int var46 = var1 - 1;
                  char var47 = var45.charAt(var46);
                  if(Mail.MAIL_DETAIL_DEBUG) {
                     StringBuilder var48 = (new StringBuilder()).append("prevLetter>");
                     String var50 = var48.append(var47).toString();
                     ll.d("MailAlphabetIndexer", var50);
                  }

                  int var53 = Integer.MIN_VALUE;
                  int var54 = var2.get(var47, var53);
                  int var56 = Integer.MIN_VALUE;
                  if(var54 != var56) {
                     var10 = Math.abs(var54);
                  }
               }

               var36 = (var11 + var10) / 2;
               if(Mail.MAIL_DETAIL_DEBUG) {
                  StringBuilder var57 = (new StringBuilder()).append("pos z>");
                  StringBuilder var59 = var57.append(var36).append(",");
                  StringBuilder var61 = var59.append(var10).append(",");
                  String var63 = var61.append(var11).toString();
                  ll.d("MailAlphabetIndexer", var63);
               }

               while(var36 < var11) {
                  var3.moveToPosition(var36);
                  int var65 = this.mColumnIndex;
                  String var68 = var3.getString(var65);
                  if(Mail.MAIL_DETAIL_DEBUG) {
                     StringBuilder var69 = (new StringBuilder()).append("curName>");
                     StringBuilder var71 = var69.append(var68).append(",");
                     StringBuilder var73 = var71.append(var36).append(",");
                     StringBuilder var75 = var73.append(var10).append(",");
                     String var77 = var75.append(var11).toString();
                     ll.d("MailAlphabetIndexer", var77);
                  }

                  if(var68 == null) {
                     if(var36 == 0) {
                        if(Mail.MAIL_DETAIL_DEBUG) {
                           ll.d("MailAlphabetIndexer", "pos 0 >");
                        }
                        break;
                     }

                     var36 += -1;
                     if(Mail.MAIL_DETAIL_DEBUG) {
                        StringBuilder var87 = (new StringBuilder()).append("pos -->");
                        String var89 = var87.append(var36).toString();
                        ll.d("MailAlphabetIndexer", var89);
                     }
                  } else {
                     int var93 = this.compare(var68, var24);
                     if(Mail.MAIL_DETAIL_DEBUG) {
                        StringBuilder var94 = (new StringBuilder()).append("diff>");
                        StringBuilder var96 = var94.append(var68).append(",");
                        StringBuilder var98 = var96.append(var24).append(",");
                        StringBuilder var100 = var98.append(var93).append(",");
                        boolean var101 = this.mRevertOrder;
                        String var102 = var100.append(var101).toString();
                        ll.d("MailAlphabetIndexer", var102);
                     }

                     if(this.mRevertOrder) {
                        if(Mail.MAIL_DETAIL_DEBUG) {
                           ll.d("MailAlphabetIndexer", "set revert>");
                        }

                        if(var93 < 0) {
                           if(Mail.MAIL_DETAIL_DEBUG) {
                              ll.d("MailAlphabetIndexer", "set revert>a");
                           }

                           var93 = 1;
                        } else {
                           if(Mail.MAIL_DETAIL_DEBUG) {
                              ll.d("MailAlphabetIndexer", "set revert>a");
                           }

                           var93 = -1;
                        }
                     }

                     if(var93 != 0) {
                        if(var93 < 0) {
                           if(Mail.MAIL_DETAIL_DEBUG) {
                              ll.d("MailAlphabetIndexer", "diff < 0");
                           }

                           var10 = var36 + 1;
                           if(Mail.MAIL_DETAIL_DEBUG) {
                              StringBuilder var103 = (new StringBuilder()).append("diff c>");
                              StringBuilder var105 = var103.append(var93).append(",");
                              StringBuilder var107 = var105.append(var10).append(",");
                              StringBuilder var109 = var107.append(var36).append(",");
                              String var111 = var109.append(var11).toString();
                              ll.d("MailAlphabetIndexer", var111);
                           }

                           if(var10 >= var9) {
                              if(Mail.MAIL_DETAIL_DEBUG) {
                                 ll.d("MailAlphabetIndexer", "end 3>");
                              }

                              var36 = var9;
                              break;
                           }
                        } else {
                           if(Mail.MAIL_DETAIL_DEBUG) {
                              ll.d("MailAlphabetIndexer", "diff > 0");
                           }

                           var11 = var36;
                        }
                     } else {
                        if(Mail.MAIL_DETAIL_DEBUG) {
                           ll.d("MailAlphabetIndexer", "diff b>");
                        }

                        if(var10 == var36) {
                           if(Mail.MAIL_DETAIL_DEBUG) {
                              ll.d("MailAlphabetIndexer", "this is it>");
                           }
                           break;
                        }

                        if(Mail.MAIL_DETAIL_DEBUG) {
                           ll.d("MailAlphabetIndexer", "need to >");
                        }

                        var11 = var36;
                     }

                     var36 = (var10 + var11) / 2;
                     if(Mail.MAIL_DETAIL_DEBUG) {
                        StringBuilder var112 = (new StringBuilder()).append("pos zz>");
                        StringBuilder var114 = var112.append(var36).append(",");
                        StringBuilder var116 = var114.append(var10).append(",");
                        String var118 = var116.append(var11).toString();
                        ll.d("MailAlphabetIndexer", var118);
                     }
                  }
               }

               if(Mail.MAIL_DETAIL_DEBUG) {
                  StringBuilder var78 = (new StringBuilder()).append("pot>");
                  StringBuilder var80 = var78.append(var23).append(",");
                  String var82 = var80.append(var36).toString();
                  ll.d("MailAlphabetIndexer", var82);
               }

               var2.put(var23, var36);
               var3.moveToPosition(var8);
               var4 = var36;
            }
         } else {
            if(Mail.MAIL_DETAIL_DEBUG) {
               ll.d("MailAlphabetIndexer", "return 0 a1>");
            }

            var4 = 0;
         }
         break;
      case 5:
         var4 = this.getPositionForSectionByNumber(var1);
      }

      return var4;
   }

   public int getPositionForSectionByDay(int var1) {
      SparseIntArray var2 = this.mAlphaMap;
      Cursor var3 = this.mDataCursor;
      int var4;
      if(var3 != null && this.mAlphabet != null) {
         if(var1 <= 0) {
            if(Mail.MAIL_DETAIL_DEBUG) {
               ll.d("MailAlphabetIndexer", "day return 0 a4>");
            }

            var4 = 0;
         } else {
            int var5 = this.mAlphabetLength;
            if(var1 >= var5) {
               var1 = this.mAlphabetLength - 1;
            }

            int var8 = var3.getPosition();
            int var9 = var3.getCount();
            int var10 = 0;
            int var11 = var9;
            if(Mail.MAIL_DETAIL_DEBUG) {
               StringBuilder var12 = (new StringBuilder()).append("getPositionForSectionByDay>");
               StringBuilder var14 = var12.append(var1).append(",");
               StringBuilder var16 = var14.append(var8).append(",");
               StringBuilder var18 = var16.append(var9).append(",");
               boolean var19 = this.mRevertOrder;
               String var20 = var18.append(var19).toString();
               ll.d("MailAlphabetIndexer", var20);
            }

            long var25;
            if(this.mRevertOrder) {
               long var21 = this.baseTime;
               long var23 = (long)(var1 - 1) * 86400000L;
               var25 = var21 - var23 - 1L;
            } else {
               long var110 = this.baseTime;
               long var112 = (long)var1 * 86400000L;
               var25 = var110 + var112;
            }

            long var27 = var25;
            int var29 = (int)var25;
            if(Mail.MAIL_DETAIL_DEBUG) {
               StringBuilder var30 = (new StringBuilder()).append("letter>");
               StringBuilder var32 = var30.append(var1).append(",");
               StringBuilder var35 = var32.append(var25).append(",");
               String var37 = var35.append(var29).toString();
               ll.i("MailAlphabetIndexer", var37);
            }

            int var40 = Integer.MIN_VALUE;
            int var41 = var2.get(var29, var40);
            int var42 = Integer.MIN_VALUE;
            if(var42 != var41) {
               if(Mail.MAIL_DETAIL_DEBUG) {
                  StringBuilder var44 = (new StringBuilder()).append("pos>");
                  String var46 = var44.append(var41).append(",").append(Integer.MIN_VALUE).toString();
                  ll.d("MailAlphabetIndexer", var46);
               }

               if(var41 >= 0) {
                  if(Mail.MAIL_DETAIL_DEBUG) {
                     StringBuilder var114 = (new StringBuilder()).append("pos b>");
                     String var116 = var114.append(var41).toString();
                     ll.d("MailAlphabetIndexer", var116);
                  }

                  var4 = var41;
                  return var4;
               }

               if(Mail.MAIL_DETAIL_DEBUG) {
                  StringBuilder var47 = (new StringBuilder()).append("pos a>");
                  String var49 = var47.append(var41).toString();
                  ll.d("MailAlphabetIndexer", var49);
               }

               var11 = -var41;
            }

            var41 = (var11 + var10) / 2;
            if(Mail.MAIL_DETAIL_DEBUG) {
               StringBuilder var50 = (new StringBuilder()).append("pos z>");
               StringBuilder var52 = var50.append(var41).append(",");
               StringBuilder var54 = var52.append(var10).append(",");
               String var56 = var54.append(var11).toString();
               ll.d("MailAlphabetIndexer", var56);
            }

            while(var41 < var11) {
               var3.moveToPosition(var41);
               int var58 = this.mColumnIndex;
               long var61 = var3.getLong(var58);
               String var64 = "_subject";
               int var65 = var3.getColumnIndexOrThrow(var64);
               String var68 = var3.getString(var65);
               if(Mail.MAIL_DETAIL_DEBUG) {
                  StringBuilder var69 = (new StringBuilder()).append("curName>");
                  StringBuilder var72 = var69.append(var61).append(",");
                  StringBuilder var74 = var72.append(var41).append(",");
                  StringBuilder var76 = var74.append(var10).append(",");
                  StringBuilder var78 = var76.append(var11).append(",");
                  String var80 = var78.append(var68).toString();
                  ll.d("MailAlphabetIndexer", var80);
               }

               byte var81 = 0;
               if(this.mRevertOrder) {
                  if(var61 == var27) {
                     var81 = 0;
                  }

                  if(var61 < var27) {
                     var81 = 1;
                  }

                  if(var61 > var27) {
                     var81 = -1;
                  }
               } else {
                  if(var61 == var27) {
                     var81 = 0;
                  }

                  if(var61 < var27) {
                     var81 = -1;
                  }

                  if(var61 > var27) {
                     var81 = 1;
                  }
               }

               if(Mail.MAIL_DETAIL_DEBUG) {
                  StringBuilder var82 = (new StringBuilder()).append("diffPirority>");
                  StringBuilder var85 = var82.append(var61).append(",");
                  StringBuilder var88 = var85.append(var27).append(",");
                  StringBuilder var90 = var88.append(var81).append(",");
                  boolean var91 = this.mRevertOrder;
                  String var92 = var90.append(var91).toString();
                  ll.d("MailAlphabetIndexer", var92);
               }

               if(var81 != 0) {
                  if(var81 < 0) {
                     if(Mail.MAIL_DETAIL_DEBUG) {
                        ll.d("MailAlphabetIndexer", "diff < 0");
                     }

                     var10 = var41 + 1;
                     if(Mail.MAIL_DETAIL_DEBUG) {
                        StringBuilder var93 = (new StringBuilder()).append("diff c>");
                        StringBuilder var95 = var93.append(var81).append(",");
                        StringBuilder var97 = var95.append(var10).append(",");
                        StringBuilder var99 = var97.append(var41).append(",");
                        String var101 = var99.append(var11).toString();
                        ll.d("MailAlphabetIndexer", var101);
                     }

                     if(var10 >= var9) {
                        if(Mail.MAIL_DETAIL_DEBUG) {
                           ll.d("MailAlphabetIndexer", "end 3>");
                        }

                        var41 = var9;
                        break;
                     }
                  } else {
                     if(Mail.MAIL_DETAIL_DEBUG) {
                        ll.d("MailAlphabetIndexer", "diff > 0");
                     }

                     var11 = var41;
                  }
               } else {
                  if(Mail.MAIL_DETAIL_DEBUG) {
                     ll.d("MailAlphabetIndexer", "diff b>");
                  }

                  if(var10 == var41) {
                     if(Mail.MAIL_DETAIL_DEBUG) {
                        ll.d("MailAlphabetIndexer", "this is it>");
                     }
                     break;
                  }

                  if(Mail.MAIL_DETAIL_DEBUG) {
                     ll.d("MailAlphabetIndexer", "need to >");
                  }

                  var11 = var41;
               }

               var41 = (var10 + var11) / 2;
               if(Mail.MAIL_DETAIL_DEBUG) {
                  StringBuilder var117 = (new StringBuilder()).append("pos zz>");
                  StringBuilder var119 = var117.append(var41).append(",");
                  StringBuilder var121 = var119.append(var10).append(",");
                  String var123 = var121.append(var11).toString();
                  ll.d("MailAlphabetIndexer", var123);
               }
            }

            if(Mail.MAIL_DETAIL_DEBUG) {
               StringBuilder var102 = (new StringBuilder()).append("pot>");
               StringBuilder var104 = var102.append(var1).append(",");
               StringBuilder var106 = var104.append(var29).append(",");
               String var108 = var106.append(var41).toString();
               ll.d("MailAlphabetIndexer", var108);
            }

            var2.put(var29, var41);
            var3.moveToPosition(var8);
            var4 = var41;
         }
      } else {
         if(Mail.MAIL_DETAIL_DEBUG) {
            ll.d("MailAlphabetIndexer", "day return 0 a3>");
         }

         var4 = 0;
      }

      return var4;
   }

   public int getPositionForSectionByNumber(int var1) {
      SparseIntArray var2 = this.mAlphaMap;
      Cursor var3 = this.mDataCursor;
      if(Mail.MAIL_DETAIL_DEBUG) {
         StringBuilder var4 = (new StringBuilder()).append("getPositionForSectionByNumber>");
         String var6 = var4.append(var1).toString();
         ll.d("MailAlphabetIndexer", var6);
      }

      int var7;
      if(var3 != null && this.mAlphabet != null) {
         if(var1 <= 0) {
            if(Mail.MAIL_DETAIL_DEBUG) {
               ll.d("MailAlphabetIndexer", "return 0 a4>");
            }

            var7 = 0;
         } else {
            int var8 = this.mAlphabetLength;
            if(var1 >= var8) {
               var1 = this.mAlphabetLength - 1;
            }

            int var11 = var3.getPosition();
            int var12 = var3.getCount();
            int var13 = 0;
            int var14 = var12;
            if(Mail.MAIL_DETAIL_DEBUG) {
               StringBuilder var15 = (new StringBuilder()).append("getPositionForSectionByNumber>");
               StringBuilder var17 = var15.append(var1).append(",");
               StringBuilder var19 = var17.append(var11).append(",");
               StringBuilder var21 = var19.append(var12).append(",");
               CharSequence var22 = this.mAlphabet;
               String var23 = var21.append(var22).toString();
               ll.d("MailAlphabetIndexer", var23);
            }

            int var24 = 0;
            if(this.mRevertOrder) {
               switch(var1) {
               case 0:
                  var24 = 1073741824;
                  break;
               case 1:
                  var24 = 5242880;
                  break;
               case 2:
                  var24 = 1048576;
                  break;
               case 3:
                  var24 = 512000;
                  break;
               case 4:
                  var24 = 102400;
                  break;
               case 5:
                  var24 = 25600;
                  break;
               case 6:
                  var24 = 10240;
               }
            } else {
               switch(var1) {
               case 0:
                  var24 = 0;
                  break;
               case 1:
                  var24 = 10240;
                  break;
               case 2:
                  var24 = 25600;
                  break;
               case 3:
                  var24 = 102400;
                  break;
               case 4:
               case 5:
               case 6:
                  var24 = 5242880;
               }
            }

            int var25 = var24;
            if(Mail.MAIL_DETAIL_DEBUG) {
               StringBuilder var27 = (new StringBuilder()).append("letter>");
               StringBuilder var29 = var27.append(var1).append(",");
               StringBuilder var31 = var29.append(var24).append(",");
               String var33 = var31.append(var24).toString();
               ll.d("MailAlphabetIndexer", var33);
            }

            int var36 = Integer.MIN_VALUE;
            int var37 = var2.get(var24, var36);
            int var38 = Integer.MIN_VALUE;
            if(var38 != var37) {
               if(Mail.MAIL_DETAIL_DEBUG) {
                  StringBuilder var40 = (new StringBuilder()).append("pos>");
                  String var42 = var40.append(var37).append(",").append(Integer.MIN_VALUE).toString();
                  ll.d("MailAlphabetIndexer", var42);
               }

               if(var37 >= 0) {
                  if(Mail.MAIL_DETAIL_DEBUG) {
                     StringBuilder var81 = (new StringBuilder()).append("pos b>");
                     String var83 = var81.append(var37).toString();
                     ll.d("MailAlphabetIndexer", var83);
                  }

                  var7 = var37;
                  return var7;
               }

               if(Mail.MAIL_DETAIL_DEBUG) {
                  StringBuilder var43 = (new StringBuilder()).append("pos a>");
                  String var45 = var43.append(var37).toString();
                  ll.d("MailAlphabetIndexer", var45);
               }

               var14 = -var37;
            }

            var37 = (var14 + var13) / 2;
            if(Mail.MAIL_DETAIL_DEBUG) {
               StringBuilder var46 = (new StringBuilder()).append("pos z>");
               StringBuilder var48 = var46.append(var37).append(",");
               StringBuilder var50 = var48.append(var13).append(",");
               String var52 = var50.append(var14).toString();
               ll.d("MailAlphabetIndexer", var52);
            }

            while(var37 < var14) {
               var3.moveToPosition(var37);
               int var54 = this.mColumnIndex;
               int var57 = var3.getInt(var54);
               String var59 = "_subject";
               int var60 = var3.getColumnIndexOrThrow(var59);
               String var63 = var3.getString(var60);
               if(Mail.MAIL_DETAIL_DEBUG) {
                  StringBuilder var64 = (new StringBuilder()).append("curName>");
                  StringBuilder var66 = var64.append(var57).append(",");
                  StringBuilder var68 = var66.append(var37).append(",");
                  StringBuilder var70 = var68.append(var13).append(",");
                  StringBuilder var72 = var70.append(var14).append(",");
                  String var74 = var72.append(var63).toString();
                  ll.d("MailAlphabetIndexer", var74);
               }

               if(var57 == 0) {
                  if(var37 == 0) {
                     if(Mail.MAIL_DETAIL_DEBUG) {
                        ll.d("MailAlphabetIndexer", "pos 0 >");
                     }
                     break;
                  }

                  var37 += -1;
                  if(Mail.MAIL_DETAIL_DEBUG) {
                     StringBuilder var84 = (new StringBuilder()).append("pos -->");
                     String var86 = var84.append(var37).toString();
                     ll.d("MailAlphabetIndexer", var86);
                  }
               } else {
                  byte var87 = 0;
                  if(this.mRevertOrder) {
                     if(var57 == var25) {
                        var87 = 0;
                     }

                     if(var57 < var25) {
                        var87 = 1;
                     }

                     if(var57 > var25) {
                        var87 = -1;
                     }
                  } else {
                     if(var57 == var25) {
                        var87 = 0;
                     }

                     if(var57 < var25) {
                        var87 = -1;
                     }

                     if(var57 > var25) {
                        var87 = 1;
                     }
                  }

                  if(Mail.MAIL_DETAIL_DEBUG) {
                     StringBuilder var88 = (new StringBuilder()).append("diffNumber>");
                     StringBuilder var90 = var88.append(var57).append(",");
                     StringBuilder var92 = var90.append(var25).append(",");
                     StringBuilder var94 = var92.append(var87).append(",");
                     boolean var95 = this.mRevertOrder;
                     String var96 = var94.append(var95).toString();
                     ll.d("MailAlphabetIndexer", var96);
                  }

                  if(var87 != 0) {
                     if(var87 < 0) {
                        if(Mail.MAIL_DETAIL_DEBUG) {
                           ll.d("MailAlphabetIndexer", "diff < 0");
                        }

                        var13 = var37 + 1;
                        if(Mail.MAIL_DETAIL_DEBUG) {
                           StringBuilder var97 = (new StringBuilder()).append("diff c>");
                           StringBuilder var99 = var97.append(var87).append(",");
                           StringBuilder var101 = var99.append(var13).append(",");
                           StringBuilder var103 = var101.append(var37).append(",");
                           String var105 = var103.append(var14).toString();
                           ll.d("MailAlphabetIndexer", var105);
                        }

                        if(var13 >= var12) {
                           if(Mail.MAIL_DETAIL_DEBUG) {
                              ll.d("MailAlphabetIndexer", "end 3>");
                           }

                           var37 = var12;
                           break;
                        }
                     } else {
                        if(Mail.MAIL_DETAIL_DEBUG) {
                           ll.d("MailAlphabetIndexer", "diff > 0");
                        }

                        var14 = var37;
                     }
                  } else {
                     if(Mail.MAIL_DETAIL_DEBUG) {
                        ll.d("MailAlphabetIndexer", "diff b>");
                     }

                     if(var13 == var37) {
                        if(Mail.MAIL_DETAIL_DEBUG) {
                           ll.d("MailAlphabetIndexer", "this is it>");
                        }
                        break;
                     }

                     if(Mail.MAIL_DETAIL_DEBUG) {
                        ll.d("MailAlphabetIndexer", "need to >");
                     }

                     var14 = var37;
                  }

                  var37 = (var13 + var14) / 2;
                  if(Mail.MAIL_DETAIL_DEBUG) {
                     StringBuilder var106 = (new StringBuilder()).append("pos zz>");
                     StringBuilder var108 = var106.append(var37).append(",");
                     StringBuilder var110 = var108.append(var13).append(",");
                     String var112 = var110.append(var14).toString();
                     ll.d("MailAlphabetIndexer", var112);
                  }
               }
            }

            if(Mail.MAIL_DETAIL_DEBUG) {
               StringBuilder var75 = (new StringBuilder()).append("pot>");
               StringBuilder var77 = var75.append(var24).append(",");
               String var79 = var77.append(var37).toString();
               ll.d("MailAlphabetIndexer", var79);
            }

            var2.put(var24, var37);
            var3.moveToPosition(var11);
            var7 = var37;
         }
      } else {
         if(Mail.MAIL_DETAIL_DEBUG) {
            ll.d("MailAlphabetIndexer", "return 0 a3>");
         }

         var7 = 0;
      }

      return var7;
   }

   public int getPositionForSectionByPriority(int var1) {
      SparseIntArray var2 = this.mAlphaMap;
      Cursor var3 = this.mDataCursor;
      int var4;
      if(var3 != null && this.mAlphabet != null) {
         if(var1 <= 0) {
            if(Mail.MAIL_DETAIL_DEBUG) {
               ll.d("MailAlphabetIndexer", "priority return 0 a4>");
            }

            var4 = 0;
         } else {
            int var5 = this.mAlphabetLength;
            if(var1 >= var5) {
               var1 = this.mAlphabetLength - 1;
            }

            int var8 = var3.getPosition();
            int var9 = var3.getCount();
            int var10 = 0;
            int var11 = var9;
            if(Mail.MAIL_DETAIL_DEBUG) {
               StringBuilder var12 = (new StringBuilder()).append("getPositionForSectionByPriority>");
               StringBuilder var14 = var12.append(var1).append(",");
               StringBuilder var16 = var14.append(var8).append(",");
               StringBuilder var18 = var16.append(var9).append(",");
               CharSequence var19 = this.mAlphabet;
               String var20 = var18.append(var19).toString();
               ll.d("MailAlphabetIndexer", var20);
            }

            byte var21 = 0;
            if(this.mRevertOrder) {
               switch(var1) {
               case 0:
                  var21 = 2;
                  break;
               case 1:
                  var21 = 1;
                  break;
               case 2:
                  var21 = 0;
               }
            } else {
               switch(var1) {
               case 0:
                  var21 = 0;
                  break;
               case 1:
                  var21 = 1;
                  break;
               case 2:
                  var21 = 2;
               }
            }

            byte var22 = var21;
            if(Mail.MAIL_DETAIL_DEBUG) {
               StringBuilder var24 = (new StringBuilder()).append("letter>");
               StringBuilder var26 = var24.append(var1).append(",");
               StringBuilder var28 = var26.append(var21).append(",");
               String var30 = var28.append(var21).toString();
               ll.d("MailAlphabetIndexer", var30);
            }

            int var33 = Integer.MIN_VALUE;
            int var34 = var2.get(var21, var33);
            int var35 = Integer.MIN_VALUE;
            if(var35 != var34) {
               if(Mail.MAIL_DETAIL_DEBUG) {
                  StringBuilder var37 = (new StringBuilder()).append("pos>");
                  String var39 = var37.append(var34).append(",").append(Integer.MIN_VALUE).toString();
                  ll.d("MailAlphabetIndexer", var39);
               }

               if(var34 >= 0) {
                  if(Mail.MAIL_DETAIL_DEBUG) {
                     StringBuilder var97 = (new StringBuilder()).append("pos b>");
                     String var99 = var97.append(var34).toString();
                     ll.d("MailAlphabetIndexer", var99);
                  }

                  var4 = var34;
                  return var4;
               }

               if(Mail.MAIL_DETAIL_DEBUG) {
                  StringBuilder var40 = (new StringBuilder()).append("pos a>");
                  String var42 = var40.append(var34).toString();
                  ll.d("MailAlphabetIndexer", var42);
               }

               var11 = -var34;
            }

            var34 = (var11 + var10) / 2;
            if(Mail.MAIL_DETAIL_DEBUG) {
               StringBuilder var43 = (new StringBuilder()).append("pos z>");
               StringBuilder var45 = var43.append(var34).append(",");
               StringBuilder var47 = var45.append(var10).append(",");
               String var49 = var47.append(var11).toString();
               ll.d("MailAlphabetIndexer", var49);
            }

            while(var34 < var11) {
               var3.moveToPosition(var34);
               int var51 = this.mColumnIndex;
               int var54 = var3.getInt(var51);
               String var56 = "_subject";
               int var57 = var3.getColumnIndexOrThrow(var56);
               String var60 = var3.getString(var57);
               if(Mail.MAIL_DETAIL_DEBUG) {
                  StringBuilder var61 = (new StringBuilder()).append("curName>");
                  StringBuilder var63 = var61.append(var54).append(",");
                  StringBuilder var65 = var63.append(var34).append(",");
                  StringBuilder var67 = var65.append(var10).append(",");
                  StringBuilder var69 = var67.append(var11).append(",");
                  String var71 = var69.append(var60).toString();
                  ll.d("MailAlphabetIndexer", var71);
               }

               byte var72 = 0;
               if(this.mRevertOrder) {
                  if(var54 == var22) {
                     var72 = 0;
                  }

                  if(var54 < var22) {
                     var72 = 1;
                  }

                  if(var54 > var22) {
                     var72 = -1;
                  }
               } else {
                  if(var54 == var22) {
                     var72 = 0;
                  }

                  if(var54 < var22) {
                     var72 = -1;
                  }

                  if(var54 > var22) {
                     var72 = 1;
                  }
               }

               if(Mail.MAIL_DETAIL_DEBUG) {
                  StringBuilder var73 = (new StringBuilder()).append("diffPirority>");
                  StringBuilder var75 = var73.append(var54).append(",");
                  StringBuilder var77 = var75.append(var22).append(",");
                  StringBuilder var79 = var77.append(var72).append(",");
                  boolean var80 = this.mRevertOrder;
                  String var81 = var79.append(var80).toString();
                  ll.d("MailAlphabetIndexer", var81);
               }

               if(var72 != 0) {
                  if(var72 < 0) {
                     if(Mail.MAIL_DETAIL_DEBUG) {
                        ll.d("MailAlphabetIndexer", "diff < 0");
                     }

                     var10 = var34 + 1;
                     if(Mail.MAIL_DETAIL_DEBUG) {
                        StringBuilder var82 = (new StringBuilder()).append("diff c>");
                        StringBuilder var84 = var82.append(var72).append(",");
                        StringBuilder var86 = var84.append(var10).append(",");
                        StringBuilder var88 = var86.append(var34).append(",");
                        String var90 = var88.append(var11).toString();
                        ll.d("MailAlphabetIndexer", var90);
                     }

                     if(var10 >= var9) {
                        if(Mail.MAIL_DETAIL_DEBUG) {
                           ll.d("MailAlphabetIndexer", "end 3>");
                        }

                        var34 = var9;
                        break;
                     }
                  } else {
                     if(Mail.MAIL_DETAIL_DEBUG) {
                        ll.d("MailAlphabetIndexer", "diff > 0");
                     }

                     var11 = var34;
                  }
               } else {
                  if(Mail.MAIL_DETAIL_DEBUG) {
                     ll.d("MailAlphabetIndexer", "diff b>");
                  }

                  if(var10 == var34) {
                     if(Mail.MAIL_DETAIL_DEBUG) {
                        ll.d("MailAlphabetIndexer", "this is it>");
                     }
                     break;
                  }

                  if(Mail.MAIL_DETAIL_DEBUG) {
                     ll.d("MailAlphabetIndexer", "need to >");
                  }

                  var11 = var34;
               }

               var34 = (var10 + var11) / 2;
               if(Mail.MAIL_DETAIL_DEBUG) {
                  StringBuilder var100 = (new StringBuilder()).append("pos zz>");
                  StringBuilder var102 = var100.append(var34).append(",");
                  StringBuilder var104 = var102.append(var10).append(",");
                  String var106 = var104.append(var11).toString();
                  ll.d("MailAlphabetIndexer", var106);
               }
            }

            if(Mail.MAIL_DETAIL_DEBUG) {
               StringBuilder var91 = (new StringBuilder()).append("pot>");
               StringBuilder var93 = var91.append(var21).append(",");
               String var95 = var93.append(var34).toString();
               ll.d("MailAlphabetIndexer", var95);
            }

            var2.put(var21, var34);
            var3.moveToPosition(var8);
            var4 = var34;
         }
      } else {
         if(Mail.MAIL_DETAIL_DEBUG) {
            ll.d("MailAlphabetIndexer", "priority return 0 a3>");
         }

         var4 = 0;
      }

      return var4;
   }

   public int getSectionForPosition(int var1) {
      int var2 = this.mDataCursor.getPosition();
      this.mDataCursor.moveToPosition(var1);
      this.mDataCursor.moveToPosition(var2);
      Cursor var5 = this.mDataCursor;
      int var6 = this.mColumnIndex;
      String var7 = var5.getString(var6);
      int var8 = 0;

      int var11;
      while(true) {
         int var9 = this.mAlphabetLength;
         if(var8 >= var9) {
            var11 = 0;
            break;
         }

         String var10 = Character.toString(this.mAlphabet.charAt(var8));
         if(this.compare(var7, var10) == 0) {
            var11 = var8;
            break;
         }

         ++var8;
      }

      return var11;
   }

   public Object[] getSections() {
      if(Mail.MAIL_DETAIL_DEBUG) {
         StringBuilder var1 = (new StringBuilder()).append("getSections>");
         int var2 = this.mType;
         String var3 = var1.append(var2).toString();
         ll.d("MailAlphabetIndexer", var3);
      }

      switch(this.mType) {
      case 1:
      case 2:
      default:
         return this.mAlphabetArray;
      }
   }

   public void onChanged() {
      super.onChanged();
      this.mAlphaMap.clear();
   }

   public void onInvalidated() {
      super.onInvalidated();
      this.mAlphaMap.clear();
   }

   public void setCompareType(int var1) {
      this.mType = var1;
      switch(this.mType) {
      case 1:
         try {
            this.calDateArray();
            return;
         } catch (Exception var4) {
            if(Mail.MAIL_DETAIL_DEBUG) {
               String var3 = "e>" + var4;
               ll.e("MailAlphabetIndexer", var3);
            }

            var4.printStackTrace();
            return;
         }
      case 2:
         this.getPriorityArray();
         return;
      case 3:
      case 4:
      default:
         return;
      case 5:
         this.getSizeArray();
      }
   }

   public void setCursor(Cursor var1) {
      if(this.mDataCursor != null) {
         this.mDataCursor.unregisterDataSetObserver(this);
      }

      this.mDataCursor = var1;
      if(var1 != null) {
         this.mDataCursor.registerDataSetObserver(this);
      }

      this.mAlphaMap.clear();
   }

   public void setRevertOrder() {
      this.mRevertOrder = (boolean)1;
   }
}
