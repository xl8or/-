package gnu.inet.imap;

import gnu.inet.imap.IMAPConstants;

public class ListEntry implements IMAPConstants {

   private char delimiter;
   private String mailbox;
   private boolean marked;
   private boolean noinferiors;
   private boolean noselect;
   private boolean unmarked;


   ListEntry(String var1, char var2, boolean var3, boolean var4, boolean var5, boolean var6) {
      this.mailbox = var1;
      this.delimiter = var2;
      this.noinferiors = var3;
      this.noselect = var4;
      this.marked = var5;
      this.unmarked = var6;
   }

   private static boolean conditionalAppend(StringBuffer var0, boolean var1, boolean var2, String var3) {
      boolean var6;
      if(var2) {
         if(var1) {
            StringBuffer var4 = var0.append(' ');
         }

         var0.append(var3);
         var6 = true;
      } else {
         var6 = var1;
      }

      return var6;
   }

   public char getDelimiter() {
      return this.delimiter;
   }

   public String getMailbox() {
      return this.mailbox;
   }

   public boolean isMarked() {
      return this.marked;
   }

   public boolean isNoinferiors() {
      return this.noinferiors;
   }

   public boolean isNoselect() {
      return this.noselect;
   }

   public boolean isUnmarked() {
      return this.unmarked;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      if(this.noinferiors || this.noselect || this.marked || this.unmarked) {
         StringBuffer var2 = var1.append("([00;35m");
         boolean var3 = this.noinferiors;
         boolean var4 = conditionalAppend(var1, (boolean)0, var3, "\\Noinferiors");
         boolean var5 = this.noselect;
         boolean var6 = conditionalAppend(var1, var4, var5, "\\Noselect");
         boolean var7 = this.marked;
         boolean var8 = conditionalAppend(var1, var6, var7, "\\Marked");
         boolean var9 = this.unmarked;
         conditionalAppend(var1, var8, var9, "\\Unmarked");
         StringBuffer var11 = var1.append("[00m) ");
      }

      StringBuffer var12 = var1.append("\"[00;31m");
      char var13 = this.delimiter;
      var1.append(var13);
      StringBuffer var15 = var1.append("[00m\" ");
      String var16 = this.mailbox;
      var1.append(var16);
      return var1.toString();
   }
}
