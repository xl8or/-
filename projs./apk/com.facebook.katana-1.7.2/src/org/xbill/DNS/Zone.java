package org.xbill.DNS;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import java.util.Map.Entry;
import org.xbill.DNS.Master;
import org.xbill.DNS.Name;
import org.xbill.DNS.RRset;
import org.xbill.DNS.Record;
import org.xbill.DNS.SOARecord;
import org.xbill.DNS.SetResponse;
import org.xbill.DNS.TSIG;
import org.xbill.DNS.ZoneTransferException;
import org.xbill.DNS.ZoneTransferIn;

public class Zone implements Serializable {

   public static final int PRIMARY = 1;
   public static final int SECONDARY = 2;
   private static final long serialVersionUID = -9220510891189510942L;
   private RRset NS;
   private SOARecord SOA;
   private Map data;
   private int dclass = 1;
   private boolean hasWild;
   private Name origin;
   private Object originNode;


   public Zone(Name var1, int var2, String var3) throws IOException, ZoneTransferException {
      ZoneTransferIn var4 = ZoneTransferIn.newAXFR(var1, var3, (TSIG)null);
      var4.setDClass(var2);
      this.fromXFR(var4);
   }

   public Zone(Name var1, String var2) throws IOException {
      TreeMap var3 = new TreeMap();
      this.data = var3;
      if(var1 == null) {
         throw new IllegalArgumentException("no zone name specified");
      } else {
         Master var4 = new Master(var2, var1);
         this.origin = var1;

         while(true) {
            Record var5 = var4.nextRecord();
            if(var5 == null) {
               this.validate();
               return;
            }

            this.maybeAddRecord(var5);
         }
      }
   }

   public Zone(Name var1, Record[] var2) throws IOException {
      TreeMap var3 = new TreeMap();
      this.data = var3;
      if(var1 == null) {
         throw new IllegalArgumentException("no zone name specified");
      } else {
         this.origin = var1;
         int var4 = 0;

         while(true) {
            int var5 = var2.length;
            if(var4 >= var5) {
               this.validate();
               return;
            }

            Record var6 = var2[var4];
            this.maybeAddRecord(var6);
            ++var4;
         }
      }
   }

   public Zone(ZoneTransferIn var1) throws IOException, ZoneTransferException {
      this.fromXFR(var1);
   }

   // $FF: synthetic method
   static Map access$000(Zone var0) {
      return var0.data;
   }

   private void addRRset(Name param1, RRset param2) {
      // $FF: Couldn't be decompiled
   }

   private RRset[] allRRsets(Object var1) {
      synchronized(this){}

      RRset[] var4;
      try {
         if(var1 instanceof List) {
            List var2 = (List)var1;
            RRset[] var3 = new RRset[var2.size()];
            var4 = (RRset[])((RRset[])var2.toArray(var3));
         } else {
            RRset var5 = (RRset)var1;
            var4 = new RRset[]{var5};
         }
      } finally {
         ;
      }

      return var4;
   }

   private Object exactName(Name var1) {
      synchronized(this){}
      boolean var6 = false;

      Object var2;
      try {
         var6 = true;
         var2 = this.data.get(var1);
         var6 = false;
      } finally {
         if(var6) {
            ;
         }
      }

      return var2;
   }

   private RRset findRRset(Name param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   private void fromXFR(ZoneTransferIn var1) throws IOException, ZoneTransferException {
      TreeMap var2 = new TreeMap();
      this.data = var2;
      Name var3 = var1.getName();
      this.origin = var3;
      Iterator var4 = var1.run().iterator();

      while(var4.hasNext()) {
         Record var5 = (Record)var4.next();
         this.maybeAddRecord(var5);
      }

      if(!var1.isAXFR()) {
         throw new IllegalArgumentException("zones can only be created from AXFRs");
      } else {
         this.validate();
      }
   }

   private SetResponse lookup(Name param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   private final void maybeAddRecord(Record var1) throws IOException {
      int var2 = var1.getType();
      Name var3 = var1.getName();
      if(var2 == 6) {
         Name var4 = this.origin;
         if(!var3.equals(var4)) {
            StringBuilder var5 = (new StringBuilder()).append("SOA owner ").append(var3).append(" does not match zone origin ");
            Name var6 = this.origin;
            String var7 = var5.append(var6).toString();
            throw new IOException(var7);
         }
      }

      Name var8 = this.origin;
      if(var3.subdomain(var8)) {
         this.addRecord(var1);
      }
   }

   private void nodeToString(StringBuffer var1, Object var2) {
      RRset[] var3 = this.allRRsets(var2);
      int var4 = 0;

      while(true) {
         int var5 = var3.length;
         if(var4 >= var5) {
            return;
         }

         RRset var6 = var3[var4];
         Iterator var7 = var6.rrs();

         while(var7.hasNext()) {
            StringBuilder var8 = new StringBuilder();
            Object var9 = var7.next();
            String var10 = var8.append(var9).append("\n").toString();
            var1.append(var10);
         }

         Iterator var12 = var6.sigs();

         while(var12.hasNext()) {
            StringBuilder var13 = new StringBuilder();
            Object var14 = var12.next();
            String var15 = var13.append(var14).append("\n").toString();
            var1.append(var15);
         }

         ++var4;
      }
   }

   private RRset oneRRset(Object param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   private void removeRRset(Name param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   private void validate() throws IOException {
      Name var1 = this.origin;
      Object var2 = this.exactName(var1);
      this.originNode = var2;
      if(this.originNode == null) {
         StringBuilder var3 = new StringBuilder();
         Name var4 = this.origin;
         String var5 = var3.append(var4).append(": no data specified").toString();
         throw new IOException(var5);
      } else {
         Object var6 = this.originNode;
         RRset var7 = this.oneRRset(var6, 6);
         if(var7 != null && var7.size() == 1) {
            SOARecord var11 = (SOARecord)var7.rrs().next();
            this.SOA = var11;
            Object var12 = this.originNode;
            RRset var13 = this.oneRRset(var12, 2);
            this.NS = var13;
            if(this.NS == null) {
               StringBuilder var14 = new StringBuilder();
               Name var15 = this.origin;
               String var16 = var14.append(var15).append(": no NS set specified").toString();
               throw new IOException(var16);
            }
         } else {
            StringBuilder var8 = new StringBuilder();
            Name var9 = this.origin;
            String var10 = var8.append(var9).append(": exactly 1 SOA must be specified").toString();
            throw new IOException(var10);
         }
      }
   }

   public Iterator AXFR() {
      return new Zone.ZoneIterator(this, (boolean)1);
   }

   public void addRRset(RRset var1) {
      Name var2 = var1.getName();
      this.addRRset(var2, var1);
   }

   public void addRecord(Record var1) {
      Name var2 = var1.getName();
      int var3 = var1.getRRsetType();
      synchronized(this) {
         RRset var7 = this.findRRset(var2, var3);
         if(var7 == null) {
            RRset var4 = new RRset(var1);
            this.addRRset(var2, var4);
         } else {
            var7.addRR(var1);
         }

      }
   }

   public RRset findExactMatch(Name var1, int var2) {
      Object var3 = this.exactName(var1);
      RRset var4;
      if(var3 == null) {
         var4 = null;
      } else {
         var4 = this.oneRRset(var3, var2);
      }

      return var4;
   }

   public SetResponse findRecords(Name var1, int var2) {
      return this.lookup(var1, var2);
   }

   public int getDClass() {
      return this.dclass;
   }

   public RRset getNS() {
      return this.NS;
   }

   public Name getOrigin() {
      return this.origin;
   }

   public SOARecord getSOA() {
      return this.SOA;
   }

   public Iterator iterator() {
      return new Zone.ZoneIterator(this, (boolean)0);
   }

   public void removeRecord(Record var1) {
      Name var2 = var1.getName();
      int var3 = var1.getRRsetType();
      synchronized(this) {
         RRset var4 = this.findRRset(var2, var3);
         if(var4 != null) {
            if(var4.size() == 1 && var4.first().equals(var1)) {
               this.removeRRset(var2, var3);
            } else {
               var4.deleteRR(var1);
            }

         }
      }
   }

   public String toMasterFile() {
      synchronized(this){}
      boolean var12 = false;

      try {
         var12 = true;
         Iterator var1 = this.data.entrySet().iterator();
         StringBuffer var2 = new StringBuffer();
         Object var3 = this.originNode;
         this.nodeToString(var2, var3);

         while(var1.hasNext()) {
            Entry var4 = (Entry)var1.next();
            Name var5 = this.origin;
            Object var6 = var4.getKey();
            if(!var5.equals(var6)) {
               Object var7 = var4.getValue();
               this.nodeToString(var2, var7);
            }
         }

         String var9 = var2.toString();
         var12 = false;
         return var9;
      } finally {
         if(var12) {
            ;
         }
      }
   }

   public String toString() {
      return this.toMasterFile();
   }

   class ZoneIterator implements Iterator {

      private int count;
      private RRset[] current;
      // $FF: synthetic field
      final Zone this$0;
      private boolean wantLastSOA;
      private Iterator zentries;


      ZoneIterator(Zone param1, boolean param2) {
         // $FF: Couldn't be decompiled
      }

      public boolean hasNext() {
         boolean var1;
         if(this.current == null && !this.wantLastSOA) {
            var1 = false;
         } else {
            var1 = true;
         }

         return var1;
      }

      public Object next() {
         if(!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            RRset var3;
            if(this.current == null) {
               this.wantLastSOA = (boolean)0;
               Zone var1 = this.this$0;
               Object var2 = this.this$0.originNode;
               var3 = var1.oneRRset(var2, 6);
            } else {
               RRset[] var4 = this.current;
               int var5 = this.count;
               int var6 = var5 + 1;
               this.count = var6;
               RRset var7 = var4[var5];
               int var8 = this.count;
               int var9 = this.current.length;
               if(var8 == var9) {
                  this.current = null;

                  while(this.zentries.hasNext()) {
                     Entry var10 = (Entry)this.zentries.next();
                     Object var11 = var10.getKey();
                     Name var12 = this.this$0.origin;
                     if(!var11.equals(var12)) {
                        Zone var13 = this.this$0;
                        Object var14 = var10.getValue();
                        RRset[] var15 = var13.allRRsets(var14);
                        if(var15.length != 0) {
                           this.current = var15;
                           this.count = 0;
                           break;
                        }
                     }
                  }
               }

               var3 = var7;
            }

            return var3;
         }
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }
}
