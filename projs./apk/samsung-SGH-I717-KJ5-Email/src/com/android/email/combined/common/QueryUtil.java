package com.android.email.combined.common;

import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteQueryBuilder;
import android.text.TextUtils;
import java.util.regex.Pattern;

public class QueryUtil {

   private static final String TAG = "DBUtil";
   private static final Pattern sLimitPattern = Pattern.compile("\\s*\\d+\\s*(,\\s*\\d+\\s*)?");


   public QueryUtil() {}

   private static void appendClause(StringBuilder var0, String var1, String var2) {
      if(!TextUtils.isEmpty(var2)) {
         var0.append(var1);
         var0.append(var2);
      }
   }

   private static void appendClauseEscapeClause(StringBuilder var0, String var1, String var2) {
      if(!TextUtils.isEmpty(var2)) {
         var0.append(var1);
         DatabaseUtils.appendEscapedSQLString(var0, var2);
      }
   }

   public static void appendColumns(StringBuilder var0, String[] var1, String var2) {
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String var5 = var1[var4];
         if(var5 != null) {
            if(var4 > 0) {
               StringBuilder var6 = var0.append(", ");
            }

            if("_id".equals(var5)) {
               String var7 = var2 + "._id";
               var0.append(var7);
            } else {
               var0.append(var5);
            }
         }
      }

      StringBuilder var10 = var0.append(' ');
   }

   public static String buildQueryString(boolean var0, String var1, String var2, String var3) {
      if(TextUtils.isEmpty(var1)) {
         throw new IllegalArgumentException("no tables");
      } else {
         StringBuilder var4 = new StringBuilder(120);
         StringBuilder var5 = var4.append("SELECT ");
         if(var0) {
            StringBuilder var6 = var4.append("DISTINCT ");
         }

         if(!TextUtils.isEmpty(var2)) {
            String var7 = var2 + " ";
            var4.append(var7);
         } else {
            StringBuilder var11 = var4.append("* ");
         }

         StringBuilder var9 = var4.append("FROM ");
         var4.append(var1);
         appendClause(var4, " WHERE ", var3);
         return var4.toString();
      }
   }

   public static String getAccountQueryString(String[] var0, String var1, String var2) {
      StringBuffer var3 = new StringBuffer();
      StringBuffer var4 = var3.append('(');
      String var5 = buildQueryString((boolean)0, "Account t1, Account_CB t2", "t1.*, t2.accountKey, t2.sevenAccountKey, t2.typeMsg, t2.timeLimit, t2.sizeLimit, t2.peakTime, t2.offPeakTime, t2.days, t2.peakStartTime, t2.peakEndTime, t2.whileRoaming, t2.attachmentEnabled ", "t1._id = t2.accountKey");
      var3.append(var5);
      StringBuffer var7 = var3.append(')');
      String var8 = var3.toString();
      Object var11 = null;
      Object var13 = null;
      return SQLiteQueryBuilder.buildQueryString((boolean)0, var8, var0, var1, (String)null, (String)var11, var2, (String)var13);
   }

   public static String getMailboxQueryString(String[] var0, String var1, String var2) {
      StringBuffer var3 = new StringBuffer();
      StringBuffer var4 = var3.append('(');
      String var5 = buildQueryString((boolean)0, "Mailbox t1, Mailbox_CB t2", "t1.*, t2.mailboxKey, t2.typeMsg, t2.sevenMailboxKey, t2.syncFlag ", "t1._id = t2.mailboxKey");
      var3.append(var5);
      StringBuffer var7 = var3.append(')');
      String var8 = var3.toString();
      Object var11 = null;
      Object var13 = null;
      return SQLiteQueryBuilder.buildQueryString((boolean)0, var8, var0, var1, (String)null, (String)var11, var2, (String)var13);
   }

   public static String getMessageQueryString(String[] var0, String var1, String var2) {
      StringBuffer var3 = new StringBuffer();
      StringBuffer var4 = var3.append('(');
      String var5 = buildQueryString((boolean)0, "Message t1, Message_CB t2", "t1.*, t2.typeMsg, t2.sevenMessageKey, t2.missingBody, t2.missingHtmlBody, t2.unkEncoding, sevenAccountKey", "t1._id = t2.messageKey");
      var3.append(var5);
      StringBuffer var7 = var3.append(')');
      String var8 = var3.toString();
      Object var11 = null;
      Object var13 = null;
      return SQLiteQueryBuilder.buildQueryString((boolean)0, var8, var0, var1, (String)null, (String)var11, var2, (String)var13);
   }

   public static String getProtocolQueryString(String[] var0, String var1, String var2) {
      StringBuffer var3 = new StringBuffer();
      StringBuffer var4 = var3.append("( select c.* ");
      StringBuffer var5 = var3.append("     , (select protocol from hostAuth x where x._id = a.hostAuthKeyRecv) recvProtocol ");
      StringBuffer var6 = var3.append("     , (select protocol from hostAuth x where x._id = a.hostAuthKeySend) sendProtocol ");
      StringBuffer var7 = var3.append(" from account a, account_cb c ");
      String var8 = " where a._id = " + var1;
      var3.append(var8);
      StringBuffer var10 = var3.append(" and a._id = c.accountKey ) ");
      String var11 = var3.toString();
      Object var13 = null;
      Object var14 = null;
      Object var16 = null;
      return SQLiteQueryBuilder.buildQueryString((boolean)0, var11, var0, (String)null, (String)var13, (String)var14, var2, (String)var16);
   }
}
