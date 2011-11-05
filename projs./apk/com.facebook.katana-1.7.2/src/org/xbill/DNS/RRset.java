package org.xbill.DNS;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.xbill.DNS.DClass;
import org.xbill.DNS.Name;
import org.xbill.DNS.RRSIGRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.Type;

public class RRset implements Serializable {

   private static final long serialVersionUID = -3270249290171239695L;
   private short nsigs;
   private short position;
   private List rrs;


   public RRset() {
      ArrayList var1 = new ArrayList(1);
      this.rrs = var1;
      this.nsigs = 0;
      this.position = 0;
   }

   public RRset(RRset var1) {
      synchronized(var1) {
         List var2 = (List)((ArrayList)var1.rrs).clone();
         this.rrs = var2;
         short var3 = var1.nsigs;
         this.nsigs = var3;
         short var4 = var1.position;
         this.position = var4;
      }
   }

   public RRset(Record var1) {
      this();
      this.safeAddRR(var1);
   }

   private Iterator iterator(boolean var1, boolean var2) {
      int var3 = 0;
      synchronized(this){}
      boolean var19 = false;

      Iterator var7;
      Iterator var21;
      try {
         var19 = true;
         int var4 = this.rrs.size();
         int var6;
         if(var1) {
            short var5 = this.nsigs;
            var6 = var4 - var5;
         } else {
            var6 = this.nsigs;
         }

         if(var6 != 0) {
            if(var1) {
               if(var2) {
                  if(this.position >= var6) {
                     this.position = 0;
                  }

                  var3 = this.position;
                  short var13 = (short)(var3 + 1);
                  this.position = var13;
               }
            } else {
               short var15 = this.nsigs;
               var3 = var4 - var15;
            }

            ArrayList var8 = new ArrayList(var6);
            if(var1) {
               List var9 = this.rrs.subList(var3, var6);
               var8.addAll(var9);
               if(var3 != 0) {
                  List var11 = this.rrs.subList(0, var3);
                  var8.addAll(var11);
               }
            } else {
               List var16 = this.rrs.subList(var3, var4);
               var8.addAll(var16);
            }

            var21 = var8.iterator();
            var19 = false;
            return var21;
         }

         var7 = Collections.EMPTY_LIST.iterator();
         var19 = false;
      } finally {
         if(var19) {
            ;
         }
      }

      var21 = var7;
      return var21;
   }

   private String iteratorToString(Iterator var1) {
      StringBuffer var2 = new StringBuffer();

      while(var1.hasNext()) {
         Record var3 = (Record)var1.next();
         StringBuffer var4 = var2.append("[");
         String var5 = var3.rdataToString();
         var2.append(var5);
         StringBuffer var7 = var2.append("]");
         if(var1.hasNext()) {
            StringBuffer var8 = var2.append(" ");
         }
      }

      return var2.toString();
   }

   private void safeAddRR(Record var1) {
      if(!(var1 instanceof RRSIGRecord)) {
         if(this.nsigs == 0) {
            this.rrs.add(var1);
         } else {
            List var3 = this.rrs;
            int var4 = this.rrs.size();
            short var5 = this.nsigs;
            int var6 = var4 - var5;
            var3.add(var6, var1);
         }
      } else {
         this.rrs.add(var1);
         short var8 = (short)(this.nsigs + 1);
         this.nsigs = var8;
      }
   }

   public void addRR(Record param1) {
      // $FF: Couldn't be decompiled
   }

   public void clear() {
      synchronized(this){}

      try {
         this.rrs.clear();
         this.position = 0;
         this.nsigs = 0;
      } finally {
         ;
      }

   }

   public void deleteRR(Record var1) {
      synchronized(this){}

      try {
         if(this.rrs.remove(var1) && var1 instanceof RRSIGRecord) {
            short var2 = (short)(this.nsigs - 1);
            this.nsigs = var2;
         }
      } finally {
         ;
      }

   }

   public Record first() {
      synchronized(this){}

      Record var2;
      try {
         if(this.rrs.size() == 0) {
            throw new IllegalStateException("rrset is empty");
         }

         var2 = (Record)this.rrs.get(0);
      } finally {
         ;
      }

      return var2;
   }

   public int getDClass() {
      return this.first().getDClass();
   }

   public Name getName() {
      return this.first().getName();
   }

   public long getTTL() {
      synchronized(this){}
      boolean var7 = false;

      long var1;
      try {
         var7 = true;
         var1 = this.first().getTTL();
         var7 = false;
      } finally {
         if(var7) {
            ;
         }
      }

      return var1;
   }

   public int getType() {
      return this.first().getRRsetType();
   }

   public Iterator rrs() {
      synchronized(this){}
      boolean var5 = false;

      Iterator var1;
      try {
         var5 = true;
         var1 = this.iterator((boolean)1, (boolean)1);
         var5 = false;
      } finally {
         if(var5) {
            ;
         }
      }

      return var1;
   }

   public Iterator rrs(boolean var1) {
      synchronized(this){}
      boolean var6 = false;

      Iterator var2;
      try {
         var6 = true;
         var2 = this.iterator((boolean)1, var1);
         var6 = false;
      } finally {
         if(var6) {
            ;
         }
      }

      return var2;
   }

   public Iterator sigs() {
      synchronized(this){}
      boolean var5 = false;

      Iterator var1;
      try {
         var5 = true;
         var1 = this.iterator((boolean)0, (boolean)0);
         var5 = false;
      } finally {
         if(var5) {
            ;
         }
      }

      return var1;
   }

   public int size() {
      synchronized(this){}
      boolean var6 = false;

      int var1;
      short var2;
      try {
         var6 = true;
         var1 = this.rrs.size();
         var2 = this.nsigs;
         var6 = false;
      } finally {
         if(var6) {
            ;
         }
      }

      int var3 = var1 - var2;
      return var3;
   }

   public String toString() {
      String var1;
      if(this.rrs == null) {
         var1 = "{empty}";
      } else {
         StringBuffer var2 = new StringBuffer();
         StringBuffer var3 = var2.append("{ ");
         StringBuilder var4 = new StringBuilder();
         Name var5 = this.getName();
         String var6 = var4.append(var5).append(" ").toString();
         var2.append(var6);
         StringBuilder var8 = new StringBuilder();
         long var9 = this.getTTL();
         String var11 = var8.append(var9).append(" ").toString();
         var2.append(var11);
         StringBuilder var13 = new StringBuilder();
         String var14 = DClass.string(this.getDClass());
         String var15 = var13.append(var14).append(" ").toString();
         var2.append(var15);
         StringBuilder var17 = new StringBuilder();
         String var18 = Type.string(this.getType());
         String var19 = var17.append(var18).append(" ").toString();
         var2.append(var19);
         Iterator var21 = this.iterator((boolean)1, (boolean)0);
         String var22 = this.iteratorToString(var21);
         var2.append(var22);
         if(this.nsigs > 0) {
            StringBuffer var24 = var2.append(" sigs: ");
            Iterator var25 = this.iterator((boolean)0, (boolean)0);
            String var26 = this.iteratorToString(var25);
            var2.append(var26);
         }

         StringBuffer var28 = var2.append(" }");
         var1 = var2.toString();
      }

      return var1;
   }
}
