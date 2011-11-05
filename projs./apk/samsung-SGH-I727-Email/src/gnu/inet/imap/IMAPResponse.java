package gnu.inet.imap;

import java.util.List;

public class IMAPResponse {

   public static final String CONTINUATION = "+";
   public static final String UNTAGGED = "*";
   protected List code = null;
   protected int count = -1;
   protected String id = null;
   protected String mailbox = null;
   protected String tag = null;
   protected String text;


   public IMAPResponse() {}

   public int getCount() {
      return this.count;
   }

   public String getID() {
      return this.id;
   }

   public List getResponseCode() {
      return this.code;
   }

   public String getTag() {
      return this.tag;
   }

   public String getText() {
      return this.text;
   }

   public boolean isContinuation() {
      boolean var1;
      if(this.tag == "+") {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isTagged() {
      boolean var1;
      if(this.tag != "*" && this.tag != "+") {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isUntagged() {
      boolean var1;
      if(this.tag == "*") {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public String toANSIString() {
      StringBuffer var1 = new StringBuffer();
      String var2 = this.tag;
      var1.append(var2);
      if(this.count != -1) {
         StringBuffer var4 = var1.append(" [00;31m");
         int var5 = this.count;
         var1.append(var5);
         StringBuffer var7 = var1.append("[00m");
      }

      if(!this.isContinuation()) {
         StringBuffer var8 = var1.append(" [01m");
         String var9 = this.id;
         var1.append(var9);
         StringBuffer var11 = var1.append("[00m");
      }

      if(this.mailbox != null) {
         StringBuffer var12 = var1.append(" [00;35m");
         String var13 = this.mailbox;
         var1.append(var13);
         StringBuffer var15 = var1.append("[00m");
      }

      if(this.code != null) {
         StringBuffer var16 = var1.append(" [00;36m");
         List var17 = this.code;
         var1.append(var17);
         StringBuffer var19 = var1.append("[00m");
      }

      if(this.text != null) {
         StringBuffer var20 = var1.append(" [00;33m");
         String var21 = this.text;
         var1.append(var21);
         StringBuffer var23 = var1.append("[00m");
      }

      return var1.toString();
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      String var2 = this.tag;
      var1.append(var2);
      if(this.count != -1) {
         StringBuffer var4 = var1.append(' ');
         int var5 = this.count;
         var1.append(var5);
      }

      if(!this.isContinuation()) {
         StringBuffer var7 = var1.append(' ');
         String var8 = this.id;
         var1.append(var8);
      }

      if(this.mailbox != null) {
         StringBuffer var10 = var1.append(' ');
         String var11 = this.mailbox;
         var1.append(var11);
      }

      if(this.code != null) {
         StringBuffer var13 = var1.append(' ');
         List var14 = this.code;
         var1.append(var14);
      }

      if(this.text != null) {
         StringBuffer var16 = var1.append(' ');
         String var17 = this.text;
         var1.append(var17);
      }

      return var1.toString();
   }
}
