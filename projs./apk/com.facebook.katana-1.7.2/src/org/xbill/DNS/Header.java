package org.xbill.DNS;

import java.io.IOException;
import java.util.Random;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Flags;
import org.xbill.DNS.Opcode;
import org.xbill.DNS.Rcode;
import org.xbill.DNS.Section;

public class Header implements Cloneable {

   public static final int LENGTH = 12;
   private static Random random = new Random();
   private int[] counts;
   private int flags;
   private int id;


   public Header() {
      this.init();
   }

   public Header(int var1) {
      this.init();
      this.setID(var1);
   }

   Header(DNSInput var1) throws IOException {
      int var2 = var1.readU16();
      this(var2);
      int var3 = var1.readU16();
      this.flags = var3;
      int var4 = 0;

      while(true) {
         int var5 = this.counts.length;
         if(var4 >= var5) {
            return;
         }

         int[] var6 = this.counts;
         int var7 = var1.readU16();
         var6[var4] = var7;
         ++var4;
      }
   }

   public Header(byte[] var1) throws IOException {
      DNSInput var2 = new DNSInput(var1);
      this(var2);
   }

   private static void checkFlag(int var0) {
      if(!validFlag(var0)) {
         String var1 = "invalid flag bit " + var0;
         throw new IllegalArgumentException(var1);
      }
   }

   private void init() {
      int[] var1 = new int[4];
      this.counts = var1;
      this.flags = 0;
      this.id = -1;
   }

   private static boolean validFlag(int var0) {
      boolean var1;
      if(var0 >= 0 && var0 <= 15 && Flags.isFlag(var0)) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public Object clone() {
      Header var1 = new Header();
      int var2 = this.id;
      var1.id = var2;
      int var3 = this.flags;
      var1.flags = var3;
      int[] var4 = this.counts;
      int[] var5 = var1.counts;
      int var6 = this.counts.length;
      System.arraycopy(var4, 0, var5, 0, var6);
      return var1;
   }

   void decCount(int var1) {
      if(this.counts[var1] == 0) {
         throw new IllegalStateException("DNS section count cannot be decremented");
      } else {
         int[] var2 = this.counts;
         int var3 = var2[var1] - 1;
         var2[var1] = var3;
      }
   }

   public int getCount(int var1) {
      return this.counts[var1];
   }

   public boolean getFlag(int var1) {
      checkFlag(var1);
      int var2 = this.flags;
      int var3 = 15 - var1;
      int var4 = 1 << var3;
      boolean var5;
      if((var2 & var4) != 0) {
         var5 = true;
      } else {
         var5 = false;
      }

      return var5;
   }

   boolean[] getFlags() {
      boolean[] var1 = new boolean[16];
      int var2 = 0;

      while(true) {
         int var3 = var1.length;
         if(var2 >= var3) {
            return var1;
         }

         if(validFlag(var2)) {
            boolean var4 = this.getFlag(var2);
            var1[var2] = var4;
         }

         ++var2;
      }
   }

   public int getID() {
      int var1;
      if(this.id >= 0) {
         var1 = this.id;
      } else {
         synchronized(this) {
            if(this.id < 0) {
               int var2 = random.nextInt('\uffff');
               this.id = var2;
            }

            var1 = this.id;
         }
      }

      return var1;
   }

   public int getOpcode() {
      return this.flags >> 11 & 15;
   }

   public int getRcode() {
      return this.flags & 15;
   }

   void incCount(int var1) {
      if(this.counts[var1] == '\uffff') {
         throw new IllegalStateException("DNS section count cannot be incremented");
      } else {
         int[] var2 = this.counts;
         int var3 = var2[var1] + 1;
         var2[var1] = var3;
      }
   }

   public String printFlags() {
      StringBuffer var1 = new StringBuffer();

      for(int var2 = 0; var2 < 16; ++var2) {
         if(validFlag(var2) && this.getFlag(var2)) {
            String var3 = Flags.string(var2);
            var1.append(var3);
            StringBuffer var5 = var1.append(" ");
         }
      }

      return var1.toString();
   }

   void setCount(int var1, int var2) {
      if(var2 >= 0 && var2 <= '\uffff') {
         this.counts[var1] = var2;
      } else {
         String var3 = "DNS section count " + var2 + " is out of range";
         throw new IllegalArgumentException(var3);
      }
   }

   public void setFlag(int var1) {
      checkFlag(var1);
      int var2 = this.flags;
      int var3 = 15 - var1;
      int var4 = 1 << var3;
      int var5 = var2 | var4;
      this.flags = var5;
   }

   public void setID(int var1) {
      if(var1 >= 0 && var1 <= '\uffff') {
         this.id = var1;
      } else {
         String var2 = "DNS message ID " + var1 + " is out of range";
         throw new IllegalArgumentException(var2);
      }
   }

   public void setOpcode(int var1) {
      if(var1 >= 0 && var1 <= 15) {
         int var3 = this.flags & '\u87ff';
         this.flags = var3;
         int var4 = this.flags;
         int var5 = var1 << 11;
         int var6 = var4 | var5;
         this.flags = var6;
      } else {
         String var2 = "DNS Opcode " + var1 + "is out of range";
         throw new IllegalArgumentException(var2);
      }
   }

   public void setRcode(int var1) {
      if(var1 >= 0 && var1 <= 15) {
         int var3 = this.flags & -16;
         this.flags = var3;
         int var4 = this.flags | var1;
         this.flags = var4;
      } else {
         String var2 = "DNS Rcode " + var1 + " is out of range";
         throw new IllegalArgumentException(var2);
      }
   }

   public String toString() {
      int var1 = this.getRcode();
      return this.toStringWithRcode(var1);
   }

   String toStringWithRcode(int var1) {
      StringBuffer var2 = new StringBuffer();
      StringBuffer var3 = var2.append(";; ->>HEADER<<- ");
      StringBuilder var4 = (new StringBuilder()).append("opcode: ");
      String var5 = Opcode.string(this.getOpcode());
      String var6 = var4.append(var5).toString();
      var2.append(var6);
      StringBuilder var8 = (new StringBuilder()).append(", status: ");
      String var9 = Rcode.string(var1);
      String var10 = var8.append(var9).toString();
      var2.append(var10);
      StringBuilder var12 = (new StringBuilder()).append(", id: ");
      int var13 = this.getID();
      String var14 = var12.append(var13).toString();
      var2.append(var14);
      StringBuffer var16 = var2.append("\n");
      StringBuilder var17 = (new StringBuilder()).append(";; flags: ");
      String var18 = this.printFlags();
      String var19 = var17.append(var18).toString();
      var2.append(var19);
      StringBuffer var21 = var2.append("; ");

      for(int var22 = 0; var22 < 4; ++var22) {
         StringBuilder var23 = new StringBuilder();
         String var24 = Section.string(var22);
         StringBuilder var25 = var23.append(var24).append(": ");
         int var26 = this.getCount(var22);
         String var27 = var25.append(var26).append(" ").toString();
         var2.append(var27);
      }

      return var2.toString();
   }

   void toWire(DNSOutput var1) {
      int var2 = this.getID();
      var1.writeU16(var2);
      int var3 = this.flags;
      var1.writeU16(var3);
      int var4 = 0;

      while(true) {
         int var5 = this.counts.length;
         if(var4 >= var5) {
            return;
         }

         int var6 = this.counts[var4];
         var1.writeU16(var6);
         ++var4;
      }
   }

   public byte[] toWire() {
      DNSOutput var1 = new DNSOutput();
      this.toWire(var1);
      return var1.toByteArray();
   }

   public void unsetFlag(int var1) {
      checkFlag(var1);
      int var2 = this.flags;
      int var3 = 15 - var1;
      int var4 = ~(1 << var3);
      int var5 = var2 & var4;
      this.flags = var5;
   }
}
