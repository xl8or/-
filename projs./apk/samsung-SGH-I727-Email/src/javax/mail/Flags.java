package javax.mail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Flags implements Cloneable, Serializable {

   private int systemFlags;
   private HashMap userFlags;


   public Flags() {
      this.systemFlags = 0;
      this.userFlags = null;
   }

   public Flags(String var1) {
      this.systemFlags = 0;
      HashMap var2 = new HashMap(1);
      this.userFlags = var2;
      HashMap var3 = this.userFlags;
      String var4 = var1.toLowerCase();
      var3.put(var4, var1);
   }

   public Flags(Flags.Flag var1) {
      int var2 = this.systemFlags;
      int var3 = var1.flag;
      int var4 = var2 | var3;
      this.systemFlags = var4;
      this.userFlags = null;
   }

   public Flags(Flags var1) {
      int var2 = var1.systemFlags;
      this.systemFlags = var2;
      if(var1.userFlags != null) {
         HashMap var3 = (HashMap)var1.userFlags.clone();
         this.userFlags = var3;
      } else {
         this.userFlags = null;
      }
   }

   public void add(String var1) {
      if(this.userFlags == null) {
         HashMap var2 = new HashMap(1);
         this.userFlags = var2;
      }

      HashMap var3 = this.userFlags;
      synchronized(var3) {
         HashMap var4 = this.userFlags;
         String var5 = var1.toLowerCase();
         var4.put(var5, var1);
      }
   }

   public void add(Flags.Flag var1) {
      int var2 = this.systemFlags;
      int var3 = var1.flag;
      int var4 = var2 | var3;
      this.systemFlags = var4;
   }

   public void add(Flags param1) {
      // $FF: Couldn't be decompiled
   }

   public Object clone() {
      return new Flags(this);
   }

   public boolean contains(String var1) {
      byte var2;
      if(this.userFlags == null) {
         var2 = 0;
      } else {
         HashMap var3 = this.userFlags;
         String var4 = var1.toLowerCase();
         var2 = var3.containsKey(var4);
      }

      return (boolean)var2;
   }

   public boolean contains(Flags.Flag var1) {
      int var2 = this.systemFlags;
      int var3 = var1.flag;
      boolean var4;
      if((var2 & var3) != 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   public boolean contains(Flags param1) {
      // $FF: Couldn't be decompiled
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 == this) {
         var2 = true;
      } else if(!(var1 instanceof Flags)) {
         var2 = false;
      } else {
         Flags var7 = (Flags)var1;
         int var3 = var7.systemFlags;
         int var4 = this.systemFlags;
         if(var3 != var4) {
            var2 = false;
         } else if(var7.userFlags == null && this.userFlags == null) {
            var2 = true;
         } else {
            if(var7.userFlags != null && this.userFlags != null) {
               HashMap var5 = var7.userFlags;
               HashMap var6 = this.userFlags;
               if(var5.equals(var6)) {
                  var2 = true;
                  return var2;
               }
            }

            var2 = false;
         }
      }

      return var2;
   }

   public Flags.Flag[] getSystemFlags() {
      ArrayList var1 = new ArrayList(7);
      Iterator var2 = Flags.Flag.flag2flag.keySet().iterator();

      while(var2.hasNext()) {
         Integer var3 = (Integer)var2.next();
         int var4 = this.systemFlags;
         int var5 = var3.intValue();
         if((var4 & var5) != 0) {
            Object var6 = Flags.Flag.flag2flag.get(var3);
            var1.add(var6);
         }
      }

      Flags.Flag[] var8 = new Flags.Flag[var1.size()];
      var1.toArray(var8);
      return var8;
   }

   public String[] getUserFlags() {
      int var1 = 0;
      String[] var2;
      if(this.userFlags == null) {
         var2 = new String[0];
      } else {
         HashMap var3 = this.userFlags;
         synchronized(var3) {
            String[] var4 = new String[this.userFlags.size()];

            int var6;
            for(Iterator var5 = this.userFlags.keySet().iterator(); var5.hasNext(); var1 = var6) {
               var6 = var1 + 1;
               HashMap var7 = this.userFlags;
               Object var8 = var5.next();
               String var9 = (String)var7.get(var8);
               var4[var1] = var9;
            }

            var2 = var4;
         }
      }

      return var2;
   }

   public int hashCode() {
      int var1 = this.systemFlags;
      if(this.userFlags != null) {
         int var2 = this.userFlags.hashCode();
         var1 += var2;
      }

      return var1;
   }

   public void remove(String var1) {
      if(this.userFlags != null) {
         HashMap var2 = this.userFlags;
         synchronized(var2) {
            HashMap var3 = this.userFlags;
            String var4 = var1.toLowerCase();
            var3.remove(var4);
         }
      }
   }

   public void remove(Flags.Flag var1) {
      int var2 = this.systemFlags;
      int var3 = ~var1.flag;
      int var4 = var2 & var3;
      this.systemFlags = var4;
   }

   public void remove(Flags param1) {
      // $FF: Couldn't be decompiled
   }

   public static final class Flag {

      public static final Flags.Flag ANSWERED = new Flags.Flag(1);
      public static final Flags.Flag DELETED = new Flags.Flag(2);
      public static final Flags.Flag DRAFT = new Flags.Flag(4);
      public static final Flags.Flag FLAGGED = new Flags.Flag(8);
      public static final Flags.Flag RECENT = new Flags.Flag(16);
      public static final Flags.Flag SEEN = new Flags.Flag(32);
      public static final Flags.Flag USER = new Flags.Flag(Integer.MIN_VALUE);
      private static final HashMap flag2flag = new HashMap(7);
      int flag;


      private Flag(int var1) {
         this.flag = var1;
         HashMap var2 = flag2flag;
         Integer var3 = new Integer(var1);
         var2.put(var3, this);
      }
   }
}
