package com.android.email.bubblebutton;

import com.android.email.bubblebutton.BubbleButton;
import java.io.PrintStream;
import java.util.ArrayList;

public class BubbleButtonArrayList {

   private static final int BCC = 3;
   private static final int CC = 2;
   private static final int TO = 1;
   private ArrayList<BubbleButton> arrayList = null;


   public BubbleButtonArrayList() {
      ArrayList var1 = new ArrayList();
      this.arrayList = var1;
   }

   public boolean BubbleButtonDelete(String var1, int var2) {
      int var3 = 0;

      boolean var8;
      while(true) {
         int var4 = this.arrayList.size();
         if(var3 >= var4) {
            var8 = false;
            break;
         }

         String var5 = ((BubbleButton)this.arrayList.get(var3)).getAddr();
         if(var1.equals(var5)) {
            int var6 = ((BubbleButton)this.arrayList.get(var3)).getIndex();
            if(var2 == var6) {
               this.arrayList.remove(var3);
               var8 = true;
               break;
            }
         }

         ++var3;
      }

      return var8;
   }

   public ArrayList<String> getAddrString(int var1) {
      ArrayList var2 = new ArrayList();
      if(this.arrayList.size() > 0 && var1 >= 1 && var1 <= 3) {
         int var3 = 0;

         while(true) {
            int var4 = this.arrayList.size();
            if(var3 >= var4) {
               break;
            }

            if(((BubbleButton)this.arrayList.get(var3)).getIndex() == var1) {
               if(((BubbleButton)this.arrayList.get(var3)).getUserCheck() == 1) {
                  String var5 = ((BubbleButton)this.arrayList.get(var3)).getName();
                  StringBuffer var6 = new StringBuffer(var5);
                  StringBuffer var7 = var6.append('/');
                  String var8 = ((BubbleButton)this.arrayList.get(var3)).getAddr();
                  var7.append(var8);
                  String var10 = var6.toString();
                  var2.add(var10);
               } else {
                  String var12 = ((BubbleButton)this.arrayList.get(var3)).getAddr();
                  var2.add(var12);
               }
            }

            ++var3;
         }
      }

      return var2;
   }

   public ArrayList<BubbleButton> getButtonList(int var1) {
      ArrayList var2 = new ArrayList();
      if(this.arrayList.size() > 0 && var1 >= 1 && var1 <= 3) {
         int var3 = 0;

         while(true) {
            int var4 = this.arrayList.size();
            if(var3 >= var4) {
               break;
            }

            if(((BubbleButton)this.arrayList.get(var3)).getIndex() == var1) {
               Object var5 = this.arrayList.get(var3);
               var2.add(var5);
            }

            ++var3;
         }
      }

      return var2;
   }

   public boolean getCheckEmail(String var1) {
      StringBuffer var2 = new StringBuffer();
      int var3 = 0;

      boolean var9;
      while(true) {
         int var4 = this.arrayList.size();
         if(var3 >= var4) {
            var9 = false;
            break;
         }

         if(var1.indexOf(47) > -1) {
            String var5 = ((BubbleButton)this.arrayList.get(var3)).getName();
            StringBuffer var6 = var2.append(var5).append('/');
            String var7 = ((BubbleButton)this.arrayList.get(var3)).getAddr();
            var6.append(var7);
         } else {
            String var10 = ((BubbleButton)this.arrayList.get(var3)).getAddr();
            var2.append(var10);
         }

         if(var2.equals(var1)) {
            var9 = true;
            break;
         }

         ++var3;
      }

      return var9;
   }

   public String getFirstEmailAddr(int var1) {
      String var8;
      if(var1 >= 1 && var1 <= 3) {
         int var2 = 0;

         while(true) {
            int var3 = this.arrayList.size();
            if(var2 >= var3) {
               break;
            }

            if(((BubbleButton)this.arrayList.get(var2)).getIndex() == var1) {
               if(!((BubbleButton)this.arrayList.get(var2)).getName().equals("")) {
                  PrintStream var4 = System.out;
                  StringBuilder var5 = (new StringBuilder()).append("NAME >>>>>>>>>>>>>> ");
                  String var6 = ((BubbleButton)this.arrayList.get(var2)).getName();
                  String var7 = var5.append(var6).toString();
                  var4.println(var7);
                  var8 = ((BubbleButton)this.arrayList.get(var2)).getName();
               } else {
                  PrintStream var9 = System.out;
                  StringBuilder var10 = (new StringBuilder()).append("NAME >>>>>>>>>>>>>> ");
                  String var11 = ((BubbleButton)this.arrayList.get(var2)).getAddr();
                  String var12 = var10.append(var11).toString();
                  var9.println(var12);
                  var8 = ((BubbleButton)this.arrayList.get(var2)).getAddr();
               }

               return var8;
            }

            ++var2;
         }
      }

      var8 = null;
      return var8;
   }

   public int getIndexCount(int var1) {
      int var2 = 0;
      if(this.arrayList.size() >= 0 && var1 >= 1 && var1 <= 3) {
         int var3 = 0;

         while(true) {
            int var4 = this.arrayList.size();
            if(var3 >= var4) {
               break;
            }

            if(((BubbleButton)this.arrayList.get(var3)).getIndex() == var1) {
               ++var2;
            }

            ++var3;
         }
      }

      return var2;
   }

   public ArrayList<BubbleButton> getTotalArrayList() {
      return this.arrayList;
   }

   public void setButton(BubbleButton var1) {
      this.arrayList.add(var1);
   }
}
