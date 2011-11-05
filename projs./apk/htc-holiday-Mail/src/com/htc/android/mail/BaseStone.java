package com.htc.android.mail;

import android.content.IContentProvider;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;
import com.htc.android.mail.Mail;
import com.htc.android.mail.MailProvider;
import com.htc.android.mail.Mailbox;
import com.htc.android.mail.Measure;

public class BaseStone {

   private static final boolean DEBUG = Mail.MAIL_DEBUG;
   private static final String TAG = "BaseStone";
   private static final boolean VERBOSE;


   public BaseStone() {}

   public static boolean CheckEmailAddr(String var0) {
      boolean var1;
      if(var0 == null) {
         var1 = false;
      } else {
         char[] var2 = var0.toCharArray();
         int var3 = var0.length() - 1;
         int var4 = 0;
         boolean var5 = true;
         int var6 = 0;
         byte var7 = 0;

         boolean var8;
         for(var8 = false; var4 <= var3; ++var4) {
            if((var2[var4] < 97 || var2[var4] > 122) && (var2[var4] < 65 || var2[var4] > 90) && (var2[var4] < 48 || var2[var4] > 57) && var2[var4] != 45 && var2[var4] != 95) {
               if(var2[var4] == 46) {
                  if(!var5) {
                     ++var6;
                     if(!var8) {
                        var7 = 1;
                        break;
                     }

                     var8 = false;
                  }
               } else if(var2[var4] == 37) {
                  if(!var5) {
                     var7 = 2;
                     break;
                  }
               } else if(var2[var4] == 64) {
                  if(!var5) {
                     var7 = 3;
                     break;
                  }

                  var5 = false;
                  var8 = false;
               } else {
                  if(var2[var4] != 44 && var2[var4] != 59) {
                     var7 = 8;
                     break;
                  }

                  if(var5) {
                     var7 = 4;
                     break;
                  }

                  if(var6 == 0) {
                     var7 = 5;
                     break;
                  }

                  if(var5) {
                     var7 = 6;
                     break;
                  }

                  if(var4 - 1 >= 0) {
                     int var9 = var4 - 1;
                     if(var2[var9] == 46) {
                        var7 = 7;
                        break;
                     }
                  }

                  var5 = true;
                  var6 = 0;
                  var8 = false;
               }
            } else {
               var8 = true;
            }
         }

         if(var7 == 0) {
            if(var6 == 0) {
               var7 = 17;
            }

            if(var5) {
               var7 = 18;
            }

            if(!var8) {
               var7 = 19;
            }
         }

         if(var7 == 0) {
            var1 = true;
         } else {
            if(DEBUG) {
               String var10 = "Not match " + var0 + " error=" + var7;
               int var11 = Log.i("BaseStone", var10);
            }

            var1 = false;
         }
      }

      return var1;
   }

   public static long RecordTime(String var0, Exception var1) {
      return Measure.RecordTime(var0, var1);
   }

   public static void ShowTime() {
      ShowTime((String)null, (String)null);
   }

   public static void ShowTime(String var0, String var1) {
      Measure.ShowTime(var0, var1);
   }

   public static int TrimReFwd(String var0) {
      int var1;
      if(var0 == null) {
         var1 = -1;
      } else {
         char[] var2 = var0.toCharArray();
         int var3 = var0.length() - 1;
         int var4 = -1;
         int var5 = 0;

         while(var3 >= 0) {
            if(var2[var3] <= 32) {
               int var6 = var3 + -1;
               if(var4 == -1) {
                  ++var5;
               }
            } else {
               if(var2[var3] == 58) {
                  if(var3 > 2) {
                     label140: {
                        label141: {
                           int var7 = var3 - 1;
                           if(var2[var7] != 100) {
                              int var8 = var3 - 1;
                              if(var2[var8] != 68) {
                                 break label141;
                              }
                           }

                           int var9 = var3 - 2;
                           if(var2[var9] != 119) {
                              int var10 = var3 - 2;
                              if(var2[var10] != 87) {
                                 break label141;
                              }
                           }

                           int var11 = var3 - 3;
                           if(var2[var11] != 102) {
                              int var12 = var3 - 3;
                              if(var2[var12] != 70) {
                                 break label141;
                              }
                           }

                           if(var4 == -1) {
                              var4 = var3;
                           }

                           var3 += -3;
                           break label140;
                        }

                        label142: {
                           label143: {
                              int var14 = var3 - 1;
                              if(var2[var14] != 119) {
                                 int var15 = var3 - 1;
                                 if(var2[var15] != 87) {
                                    break label143;
                                 }
                              }

                              int var16 = var3 - 2;
                              if(var2[var16] == 102) {
                                 break label142;
                              }

                              int var17 = var3 - 2;
                              if(var2[var17] == 70) {
                                 break label142;
                              }
                           }

                           label144: {
                              int var18 = var3 - 1;
                              if(var2[var18] != 101) {
                                 int var19 = var3 - 1;
                                 if(var2[var19] != 69) {
                                    break label144;
                                 }
                              }

                              int var20 = var3 - 2;
                              if(var2[var20] == 114) {
                                 break label142;
                              }

                              int var21 = var3 - 2;
                              if(var2[var21] == 82) {
                                 break label142;
                              }
                           }

                           var4 = -1;
                           var5 = 0;
                           break label140;
                        }

                        if(var4 == -1) {
                           var4 = var3;
                        }

                        var3 += -2;
                     }
                  } else if(var3 == 2) {
                     label145: {
                        label146: {
                           label147: {
                              int var22 = var3 - 1;
                              if(var2[var22] != 119) {
                                 int var23 = var3 - 1;
                                 if(var2[var23] != 87) {
                                    break label147;
                                 }
                              }

                              int var24 = var3 - 2;
                              if(var2[var24] == 102) {
                                 break label146;
                              }

                              int var25 = var3 - 2;
                              if(var2[var25] == 70) {
                                 break label146;
                              }
                           }

                           label148: {
                              int var26 = var3 - 1;
                              if(var2[var26] != 101) {
                                 int var27 = var3 - 1;
                                 if(var2[var27] != 69) {
                                    break label148;
                                 }
                              }

                              int var28 = var3 - 2;
                              if(var2[var28] == 114) {
                                 break label146;
                              }

                              int var29 = var3 - 2;
                              if(var2[var29] == 82) {
                                 break label146;
                              }
                           }

                           var4 = -1;
                           var5 = 0;
                           break label145;
                        }

                        if(var4 == -1) {
                           var4 = var3;
                        }

                        var3 += -2;
                     }
                  } else {
                     var4 = -1;
                     var5 = 0;
                  }
               } else {
                  var4 = -1;
                  var5 = 0;
               }

               int var13 = var3 + -1;
            }
         }

         if(var4 != -1) {
            var4 = var4 + 1 + var5;
         }

         if(DEBUG) {
            if(var4 != -1) {
               StringBuilder var30 = (new StringBuilder()).append("Trim ").append(var0).append(" left=");
               String var31 = var0.substring(0, var4);
               StringBuilder var32 = var30.append(var31).append(" right=");
               int var33 = var0.length();
               String var34 = var0.substring(var4, var33);
               String var35 = var32.append(var34).toString();
               int var36 = Log.i("BaseStone", var35);
            } else {
               String var37 = "Trim " + var0 + " none";
               int var38 = Log.i("BaseStone", var37);
            }
         }

         var1 = var4;
      }

      return var1;
   }

   private static String createGroupID(String param0, String param1, String param2, String param3, long[] param4) {
      // $FF: Couldn't be decompiled
   }

   public static void dumpCursor(Cursor var0) {
      if(var0 == null) {
         if(DEBUG) {
            int var1 = Log.i("BaseStone", "Cursor is null");
         }
      } else {
         if(DEBUG) {
            StringBuilder var2 = (new StringBuilder()).append("Cursor ").append(var0).append(" Num= ");
            int var3 = var0.getCount();
            StringBuilder var4 = var2.append(var3).append(" Field=");
            int var5 = var0.getColumnCount();
            String var6 = var4.append(var5).toString();
            int var7 = Log.i("BaseStone", var6);
         }

         String[] var8 = var0.getColumnNames();
         int var9 = 0;

         while(true) {
            int var10 = var0.getCount();
            if(var9 >= var10) {
               return;
            }

            var0.moveToPosition(var9);
            int var12 = 0;

            while(true) {
               int var13 = var0.getColumnCount();
               if(var12 >= var13) {
                  ++var9;
                  break;
               }

               String var14 = var0.getString(var12);
               if(DEBUG) {
                  StringBuilder var15 = (new StringBuilder()).append("Item:(").append(var9).append(",").append(var12).append(") ");
                  String var16 = var8[var12];
                  String var17 = var15.append(var16).append("=").append(var14).toString();
                  int var18 = Log.i("BaseStone", var17);
               }

               ++var12;
            }
         }
      }
   }

   public static void dumpMailBox(long var0, Mailbox var2) {
      Cursor var3 = null;
      if(DEBUG) {
         int var4 = Log.i("BaseStone", "dumpMailBox");
      }

      StringBuilder var5 = (new StringBuilder()).append("_account=").append(var0).append(" AND _mailboxId = \'");
      long var6 = var2.id;
      String var8 = var5.append(var6).append("\' AND _del <> \'1\'").toString();

      label23: {
         Cursor var11;
         try {
            IContentProvider var9 = MailProvider.instance();
            Uri var10 = MailProvider.sSummariesURI;
            var11 = var9.query(var10, (String[])null, var8, (String[])null, "_to collate nocase desc, _internaldate desc");
         } catch (RemoteException var15) {
            int var14 = Log.e("BaseStone", " dumpMailBox Fail", var15);
            break label23;
         }

         var3 = var11;
      }

      if(var3 == null) {
         if(DEBUG) {
            int var12 = Log.e("BaseStone", "Error");
         }
      } else {
         dumpCursor(var3);
         var3.close();
      }
   }

   public static String getGroupID(String var0, String var1, String var2, String var3, String var4, String var5, long[] var6) {
      if(false && null.length() != 0) {
         if(DEBUG) {
            String var11 = "Div referencer :" + null;
            int var12 = Log.i("BaseStone", var11);
         }

         int var13 = null.indexOf(">");
         if(var13 != -1) {
            var0 = null.substring(0, var13);
         } else {
            if(DEBUG) {
               String var14 = "References is wrong " + null;
               int var15 = Log.e("BaseStone", var14);
            }

            var0 = null.substring(0, var13);
         }

         if(var0 != null) {
            var0 = var0.trim().replaceAll("<", "").replaceAll(">", "");
         }
      } else {
         var0 = createGroupID(var0, var1, var2, var3, var6);
      }

      if(DEBUG) {
         if(var6 != null && var6.length >= 1) {
            Object[] var7 = new Object[]{var0, null};
            Long var8 = Long.valueOf(var6[0]);
            var7[1] = var8;
            String var9 = String.format("Group :%s key:%d", var7);
            int var10 = Log.i("BaseStone", var9);
         } else {
            Object[] var16 = new Object[]{var0};
            String var17 = String.format("Group :%s", var16);
            int var18 = Log.i("BaseStone", var17);
         }
      }

      return var0;
   }

   public static String getGroupID_Send(String var0, String var1, String var2, String var3, String var4, String var5, long var6, long[] var8) {
      int var9 = TrimReFwd(var2);
      if(var9 != -1) {
         int var10 = var2.length();
         var2 = var2.substring(var9, var10);
      }

      return getGroupID(var0, var1, var2, var3, var4, var5, var8);
   }

   public static Cursor getGroupSummaryCursor(long var0, long var2, String var4, String var5, int[] var6, boolean var7) {
      String var8 = new String();
      if(DEBUG) {
         int var9 = Log.i("BaseStone", "getGroupSummaryCursor");
      }

      Cursor var45;
      if(var4 == false && var5 == null) {
         var4 = "_internaldate";
         var5 = "DESC";
      } else if(var4 == false || var5 == null) {
         if(DEBUG) {
            int var21 = Log.e("BaseStone", "Invalid Arg");
         }

         boolean var22 = false;
         var45 = null;
         return var45;
      }

      String var10 = "ORDER BY " + var4 + " " + var5 + ", _group ASC,_internaldate DESC";
      String var13;
      if(var2 == 65535L) {
         Object[] var11 = new Object[1];
         Long var12 = Long.valueOf(var0);
         var11[0] = var12;
         var13 = String.format("WHERE _account = \'%d\' AND _del = \'-1\' ", var11);
      } else {
         Object[] var24 = new Object[1];
         Long var25 = Long.valueOf(var2);
         var24[0] = var25;
         String var26 = String.format("WHERE _mailboxId = \'%d\' AND _del = \'-1\' ", var24);
      }

      if(var6 != null) {
         int var14 = 0;
         String var44 = var8;

         while(true) {
            int var15 = var6.length;
            if(var14 >= var15) {
               var13 = var13 + var44;
               break;
            }

            StringBuilder var16 = (new StringBuilder()).append(var44);
            Object[] var17 = new Object[1];
            Integer var18 = Integer.valueOf(var6[var14]);
            var17[0] = var18;
            String var19 = String.format("AND _mailboxId <> \'%d\' ", var17);
            String var20 = var16.append(var19).toString();
            ++var14;
         }
      }

      String var27 = "_id,_subject,_group,_from,_internaldate,_subjtype,_incAttachment,_read,_flags";
      if(var4.compareToIgnoreCase("_id") != 0 && var4.compareToIgnoreCase("_subject") != 0 && var4.compareToIgnoreCase("_group") != 0 && var4.compareToIgnoreCase("_from") != 0 && var4.compareToIgnoreCase("_internaldate") != 0) {
         var27 + "," + var4;
      }

      String var29 = "messages ";
      String var30;
      if(var7 != null) {
         var30 = "SELECT _id,_subject,count(distinct _id) as count,group_concat(distinct _from) as namelist,_group,_internaldate,_subjtype,_incAttachment,_read,_flags,_downloadtotalsize,_messagesize,sum(_read) as readcount FROM " + var29 + var13 + "GROUP BY _group " + var10;
      } else {
         var30 = "SELECT _id,_subject,count(distinct _messageId) as count,group_concat(distinct _from) as namelist,_group,_internaldate,_subjtype,_incAttachment,_read,_flags,_downloadtotalsize,_messagesize,sum(_read) as readcount FROM " + var29 + var13 + "GROUP BY _group " + var10;
      }

      String var31 = var13.substring(6);
      String var32 = System.setProperty("SearchWhere", var31);
      String var33 = System.setProperty("sqlcmd", var30);
      if(DEBUG) {
         String var34 = "Sqlcmd= " + var30;
         int var35 = Log.i("BaseStone", var34);
      }

      Cursor var39;
      label52: {
         Cursor var38;
         try {
            IContentProvider var36 = MailProvider.instance();
            Uri var37 = MailProvider.SqliteCommandURI;
            var38 = var36.query(var37, (String[])null, var30, (String[])null, (String)null);
         } catch (RemoteException var43) {
            int var41 = Log.e("BaseStone", " getGroupSummaryCursor Fail ", var43);
            var39 = null;
            break label52;
         }

         var39 = var38;
      }

      var45 = var39;
      return var45;
   }

   public static String getReferencesFromID(String var0, long var1) {
      Object var4;
      if(var1 == 65535L) {
         if(DEBUG) {
            int var3 = Log.i("BaseStone", "getReferencesFromID null");
         }

         var4 = null;
      } else {
         var4 = null;
      }

      return (String)var4;
   }

   public static String getWhereClause(String[] var0, long var1, long var3, long var5, int[] var7, boolean var8, String var9) {
      String var10 = " ORDER BY _internaldate DESC, _mailboxId ASC , _messageId ASC ";
      String var11 = new String();
      if(var8) {
         var10 = " ORDER BY _internaldate DESC, _mailboxId ASC , _id ASC ";
      }

      String var12;
      if(var0 != null && var0.length != 0) {
         String var24 = var0[0];
         int var25 = 1;

         while(true) {
            int var26 = var0.length;
            if(var25 >= var26) {
               var12 = var24;
               break;
            }

            StringBuilder var27 = (new StringBuilder()).append(var24).append(",");
            String var28 = var0[var25];
            var24 = var27.append(var28).toString();
            ++var25;
         }
      } else {
         var12 = "* ";
      }

      String var13 = "messages";
      String var16;
      if(var3 == 65535L) {
         Object[] var14 = new Object[2];
         Long var15 = Long.valueOf(var1);
         var14[0] = var15;
         var14[1] = var9;
         var16 = String.format(" WHERE _account=\'%d\' AND _group=\'%s\' ", var14);
      } else if(var5 != 65535L) {
         Object[] var29 = new Object[4];
         Long var30 = Long.valueOf(var1);
         var29[0] = var30;
         Long var31 = Long.valueOf(var3);
         var29[1] = var31;
         Long var32 = Long.valueOf(var5);
         var29[2] = var32;
         var29[3] = var9;
         var16 = String.format(" WHERE _account=\'%d\' AND _mailboxId in (\'%d\',\'%d\') AND _group=\'%s\' ", var29);
      } else {
         Object[] var33 = new Object[3];
         Long var34 = Long.valueOf(var1);
         var33[0] = var34;
         Long var35 = Long.valueOf(var3);
         var33[1] = var35;
         var33[2] = var9;
         var16 = String.format(" WHERE _account=\'%d\' AND _mailboxId=\'%d\' AND _group=\'%s\' ", var33);
      }

      String var36;
      if(var7 != false) {
         int var17 = 0;
         String var18 = var11;

         while(true) {
            int var19 = var7.length;
            if(var17 >= var19) {
               var36 = var16 + var18;
               break;
            }

            StringBuilder var20 = (new StringBuilder()).append(var18);
            Object[] var21 = new Object[1];
            Integer var22 = Integer.valueOf(var7[var17]);
            var21[0] = var22;
            String var23 = String.format("AND _mailboxId <> \'%d\' ", var21);
            var18 = var20.append(var23).toString();
            ++var17;
         }
      } else {
         var36 = var16;
      }

      String var37 = "SELECT " + var12 + " FROM " + var13 + var36 + "" + var10;
      if(DEBUG) {
         String var38 = "getWhereClause sqlcmd=" + var37;
         int var39 = Log.i("BaseStone", var38);
      }

      return var37;
   }

   public static String getWhereClauseAllGroup(String[] var0, long var1, long var3, long var5, int[] var7, boolean var8, String var9) {
      String var10 = " ORDER BY _internaldate DESC, _mailboxId ASC , _messageId ASC ";
      String var11 = new String();
      if(var8) {
         var10 = " ORDER BY _internaldate DESC, _mailboxId ASC , _id ASC ";
      }

      String var12;
      if(var0 != null && var0.length != 0) {
         String var25 = var0[0];
         int var26 = 1;

         while(true) {
            int var27 = var0.length;
            if(var26 >= var27) {
               var12 = var25;
               break;
            }

            StringBuilder var28 = (new StringBuilder()).append(var25).append(",");
            String var29 = var0[var26];
            var25 = var28.append(var29).toString();
            ++var26;
         }
      } else {
         var12 = "* ";
      }

      String var13 = "messages";
      String var17;
      if(var3 == 65535L) {
         String var14 = " WHERE _account=\'%d\' AND " + var9;
         Object[] var15 = new Object[1];
         Long var16 = Long.valueOf(var1);
         var15[0] = var16;
         var17 = String.format(var14, var15);
      } else if(var5 != 65535L) {
         String var30 = " WHERE _account=\'%d\' AND _mailboxId in (\'%d\',\'%d\') AND " + var9;
         Object[] var31 = new Object[3];
         Long var32 = Long.valueOf(var1);
         var31[0] = var32;
         Long var33 = Long.valueOf(var3);
         var31[1] = var33;
         Long var34 = Long.valueOf(var5);
         var31[2] = var34;
         var17 = String.format(var30, var31);
      } else {
         String var35 = " WHERE _account=\'%d\' AND _mailboxId=\'%d\' AND " + var9;
         Object[] var36 = new Object[2];
         Long var37 = Long.valueOf(var1);
         var36[0] = var37;
         Long var38 = Long.valueOf(var3);
         var36[1] = var38;
         var17 = String.format(var35, var36);
      }

      String var39;
      if(var7 != false) {
         int var18 = 0;
         String var19 = var11;

         while(true) {
            int var20 = var7.length;
            if(var18 >= var20) {
               var39 = var17 + var19;
               break;
            }

            StringBuilder var21 = (new StringBuilder()).append(var19);
            Object[] var22 = new Object[1];
            Integer var23 = Integer.valueOf(var7[var18]);
            var22[0] = var23;
            String var24 = String.format("AND _mailboxId <> \'%d\' ", var22);
            var19 = var21.append(var24).toString();
            ++var18;
         }
      } else {
         var39 = var17;
      }

      String var40 = "SELECT " + var12 + " FROM " + var13 + var39 + "" + var10;
      if(DEBUG) {
         String var41 = "getWhereClause sqlcmd=" + var40;
         int var42 = Log.i("BaseStone", var41);
      }

      return var40;
   }
}
