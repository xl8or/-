package org.xbill.DNS;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DClass;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Header;
import org.xbill.DNS.Name;
import org.xbill.DNS.OPTRecord;
import org.xbill.DNS.RRset;
import org.xbill.DNS.Record;
import org.xbill.DNS.Section;
import org.xbill.DNS.TSIG;
import org.xbill.DNS.TSIGRecord;
import org.xbill.DNS.Type;
import org.xbill.DNS.Update;

public class Message implements Cloneable {

   public static final int MAXLENGTH = 65535;
   static final int TSIG_FAILED = 4;
   static final int TSIG_INTERMEDIATE = 2;
   static final int TSIG_SIGNED = 3;
   static final int TSIG_UNSIGNED = 0;
   static final int TSIG_VERIFIED = 1;
   private static RRset[] emptyRRsetArray = new RRset[0];
   private static Record[] emptyRecordArray = new Record[0];
   private Header header;
   private TSIGRecord querytsig;
   private List[] sections;
   int sig0start;
   private int size;
   int tsigState;
   private int tsigerror;
   private TSIG tsigkey;
   int tsigstart;


   public Message() {
      Header var1 = new Header();
      this(var1);
   }

   public Message(int var1) {
      Header var2 = new Header(var1);
      this(var2);
   }

   Message(DNSInput param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   private Message(Header var1) {
      List[] var2 = new List[4];
      this.sections = var2;
      this.header = var1;
   }

   public Message(byte[] var1) throws IOException {
      DNSInput var2 = new DNSInput(var1);
      this(var2);
   }

   public static Message newQuery(Record var0) {
      Message var1 = new Message();
      var1.header.setOpcode(0);
      var1.header.setFlag(7);
      var1.addRecord(var0, 0);
      return var1;
   }

   public static Message newUpdate(Name var0) {
      return new Update(var0);
   }

   private static boolean sameSet(Record var0, Record var1) {
      int var2 = var0.getRRsetType();
      int var3 = var1.getRRsetType();
      boolean var8;
      if(var2 == var3) {
         int var4 = var0.getDClass();
         int var5 = var1.getDClass();
         if(var4 == var5) {
            Name var6 = var0.getName();
            Name var7 = var1.getName();
            if(var6.equals(var7)) {
               var8 = true;
               return var8;
            }
         }
      }

      var8 = false;
      return var8;
   }

   private int sectionToWire(DNSOutput var1, int var2, Compression var3, int var4) {
      int var5 = this.sections[var2].size();
      Object var6 = var1.current();
      Object var7 = false;
      Object var8 = 0;
      Object var9 = var6;
      Object var10 = 0;

      int var12;
      while(true) {
         if(var10 >= var5) {
            var12 = 0;
            break;
         }

         Record var11 = (Record)this.sections[var2].get((int)var10);
         if(var7 != false && !sameSet(var11, (Record)var7)) {
            var8 = var1.current();
            var7 = var10;
         } else {
            var7 = var8;
            var8 = var9;
         }

         var11.toWire(var1, var2, var3);
         var9 = var1.current();
         if(var9 > var4) {
            var1.jump((int)var8);
            var12 = var5 - var7;
            break;
         }

         ++var10;
      }

      return var12;
   }

   private boolean toWire(DNSOutput var1, int var2) {
      boolean var3;
      if(var2 < 12) {
         var3 = false;
      } else {
         Header var4 = null;
         int var6;
         if(this.tsigkey != null) {
            int var5 = this.tsigkey.recordLength();
            var6 = var2 - var5;
         } else {
            var6 = var2;
         }

         int var7 = var1.current();
         this.header.toWire(var1);
         Compression var8 = new Compression();

         for(int var9 = 0; var9 < 4; ++var9) {
            if(this.sections[var9] != false) {
               int var10 = this.sectionToWire(var1, var9, var8, var6);
               if(var10 != 0) {
                  if(var9 != 3) {
                     if(var4 == null) {
                        var4 = (Header)this.header.clone();
                     }

                     var4.setFlag(6);
                     int var11 = var4.getCount(var9) - var10;
                     var4.setCount(var9, var11);

                     int var12;
                     for(var6 = var9 + 1; var6 < 4; var12 = var6 + 1) {
                        var4.setCount(var6, 0);
                     }

                     var1.save();
                     var1.jump(var7);
                     var4.toWire(var1);
                     var1.restore();
                  }
                  break;
               }
            }
         }

         if(this.tsigkey != null) {
            TSIG var13 = this.tsigkey;
            byte[] var14 = var1.toByteArray();
            int var15 = this.tsigerror;
            TSIGRecord var16 = this.querytsig;
            TSIGRecord var17 = var13.generate(this, var14, var15, var16);
            if(var4 == null) {
               var4 = (Header)this.header.clone();
            }

            var17.toWire(var1, 3, var8);
            var4.incCount(3);
            var1.save();
            var1.jump(var7);
            var4.toWire(var1);
            var1.restore();
         }

         var3 = true;
      }

      return var3;
   }

   public void addRecord(Record var1, int var2) {
      if(this.sections[var2] == false) {
         List[] var3 = this.sections;
         LinkedList var4 = new LinkedList();
         var3[var2] = var4;
      }

      this.header.incCount(var2);
      boolean var5 = this.sections[var2].add(var1);
   }

   public Object clone() {
      Message var1 = new Message();
      int var2 = 0;

      while(true) {
         int var3 = this.sections.length;
         if(var2 >= var3) {
            Header var7 = (Header)this.header.clone();
            var1.header = var7;
            int var8 = this.size;
            var1.size = var8;
            return var1;
         }

         if(this.sections[var2] != false) {
            List[] var4 = var1.sections;
            List var5 = this.sections[var2];
            LinkedList var6 = new LinkedList(var5);
            var4[var2] = var6;
         }

         ++var2;
      }
   }

   public boolean findRRset(Name var1, int var2) {
      boolean var3;
      if(!this.findRRset(var1, var2, 1) && !this.findRRset(var1, var2, 2) && !this.findRRset(var1, var2, 3)) {
         var3 = false;
      } else {
         var3 = true;
      }

      return var3;
   }

   public boolean findRRset(Name var1, int var2, int var3) {
      boolean var4;
      if(this.sections[var3] == false) {
         var4 = false;
      } else {
         int var5 = 0;

         while(true) {
            int var6 = this.sections[var3].size();
            if(var5 >= var6) {
               var4 = false;
               break;
            }

            Record var7 = (Record)this.sections[var3].get(var5);
            if(var7.getType() == var2) {
               Name var8 = var7.getName();
               if(var1.equals(var8)) {
                  var4 = true;
                  break;
               }
            }

            ++var5;
         }
      }

      return var4;
   }

   public boolean findRecord(Record var1) {
      int var2 = 1;

      boolean var3;
      while(true) {
         if(var2 > 3) {
            var3 = false;
            break;
         }

         if(this.sections[var2] != false && this.sections[var2].contains(var1)) {
            var3 = true;
            break;
         }

         ++var2;
      }

      return var3;
   }

   public boolean findRecord(Record var1, int var2) {
      boolean var3;
      if(this.sections[var2] != false && this.sections[var2].contains(var1)) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public Header getHeader() {
      return this.header;
   }

   public OPTRecord getOPT() {
      Record[] var1 = this.getSectionArray(3);
      int var2 = 0;

      OPTRecord var4;
      while(true) {
         int var3 = var1.length;
         if(var2 >= var3) {
            var4 = null;
            break;
         }

         if(var1[var2] instanceof OPTRecord) {
            var4 = (OPTRecord)var1[var2];
            break;
         }

         ++var2;
      }

      return var4;
   }

   public Record getQuestion() {
      List var1 = this.sections[0];
      Record var2;
      if(var1 != null && var1.size() != 0) {
         var2 = (Record)var1.get(0);
      } else {
         var2 = null;
      }

      return var2;
   }

   public int getRcode() {
      int var1 = this.header.getRcode();
      OPTRecord var2 = this.getOPT();
      if(var2 != null) {
         int var3 = var2.getExtendedRcode() << 4;
         var1 += var3;
      }

      return var1;
   }

   public Record[] getSectionArray(int var1) {
      Record[] var2;
      if(this.sections[var1] == false) {
         var2 = emptyRecordArray;
      } else {
         List var3 = this.sections[var1];
         Record[] var4 = new Record[var3.size()];
         var2 = (Record[])((Record[])var3.toArray(var4));
      }

      return var2;
   }

   public RRset[] getSectionRRsets(int var1) {
      RRset[] var2;
      if(this.sections[var1] == false) {
         var2 = emptyRRsetArray;
      } else {
         LinkedList var3 = new LinkedList();
         Record[] var4 = this.getSectionArray(var1);
         HashSet var5 = new HashSet();
         int var6 = 0;

         while(true) {
            int var7 = var4.length;
            if(var6 >= var7) {
               RRset[] var20 = new RRset[var3.size()];
               var2 = (RRset[])((RRset[])var3.toArray(var20));
               break;
            }

            Name var8;
            boolean var15;
            label37: {
               var8 = var4[var6].getName();
               if(var5.contains(var8)) {
                  for(int var9 = var3.size() - 1; var9 >= 0; var9 += -1) {
                     RRset var21 = (RRset)var3.get(var9);
                     int var10 = var21.getType();
                     int var11 = var4[var6].getRRsetType();
                     if(var10 == var11) {
                        int var12 = var21.getDClass();
                        int var13 = var4[var6].getDClass();
                        if(var12 == var13 && var21.getName().equals(var8)) {
                           Record var14 = var4[var6];
                           var21.addRR(var14);
                           var15 = false;
                           break label37;
                        }
                     }
                  }
               }

               var15 = true;
            }

            if(var15) {
               Record var16 = var4[var6];
               RRset var17 = new RRset(var16);
               var3.add(var17);
               var5.add(var8);
            }

            ++var6;
         }
      }

      return var2;
   }

   public TSIGRecord getTSIG() {
      int var1 = this.header.getCount(3);
      TSIGRecord var5;
      if(var1 == 0) {
         var5 = null;
      } else {
         List var2 = this.sections[3];
         int var3 = var1 - 1;
         Record var4 = (Record)var2.get(var3);
         if(var4.type != 250) {
            var5 = null;
         } else {
            var5 = (TSIGRecord)var4;
         }
      }

      return var5;
   }

   public boolean isSigned() {
      boolean var1;
      if(this.tsigState != 3 && this.tsigState != 1 && this.tsigState != 4) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public boolean isVerified() {
      boolean var1;
      if(this.tsigState == 1) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public int numBytes() {
      return this.size;
   }

   public void removeAllRecords(int var1) {
      this.sections[var1] = false;
      this.header.setCount(var1, 0);
   }

   public boolean removeRecord(Record var1, int var2) {
      boolean var3;
      if(this.sections[var2] != false && this.sections[var2].remove(var1)) {
         this.header.decCount(var2);
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public String sectionToString(int var1) {
      String var2;
      if(var1 > 3) {
         var2 = null;
      } else {
         StringBuffer var3 = new StringBuffer();
         Record[] var4 = this.getSectionArray(var1);
         int var5 = 0;

         while(true) {
            int var6 = var4.length;
            if(var5 >= var6) {
               var2 = var3.toString();
               break;
            }

            Record var7 = var4[var5];
            if(var1 == 0) {
               StringBuilder var8 = (new StringBuilder()).append(";;\t");
               Name var9 = var7.name;
               String var10 = var8.append(var9).toString();
               var3.append(var10);
               StringBuilder var12 = (new StringBuilder()).append(", type = ");
               String var13 = Type.string(var7.type);
               String var14 = var12.append(var13).toString();
               var3.append(var14);
               StringBuilder var16 = (new StringBuilder()).append(", class = ");
               String var17 = DClass.string(var7.dclass);
               String var18 = var16.append(var17).toString();
               var3.append(var18);
            } else {
               var3.append(var7);
            }

            StringBuffer var20 = var3.append("\n");
            ++var5;
         }
      }

      return var2;
   }

   public void setHeader(Header var1) {
      this.header = var1;
   }

   public void setTSIG(TSIG var1, int var2, TSIGRecord var3) {
      this.tsigkey = var1;
      this.tsigerror = var2;
      this.querytsig = var3;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      if(this.getOPT() != null) {
         StringBuilder var2 = new StringBuilder();
         Header var3 = this.header;
         int var4 = this.getRcode();
         String var5 = var3.toStringWithRcode(var4);
         String var6 = var2.append(var5).append("\n").toString();
         var1.append(var6);
      } else {
         StringBuilder var20 = new StringBuilder();
         Header var21 = this.header;
         String var22 = var20.append(var21).append("\n").toString();
         var1.append(var22);
      }

      if(this.isSigned()) {
         StringBuffer var8 = var1.append(";; TSIG ");
         if(this.isVerified()) {
            StringBuffer var9 = var1.append("ok");
         } else {
            StringBuffer var24 = var1.append("invalid");
         }

         StringBuffer var10 = var1.append('\n');
      }

      for(int var11 = 0; var11 < 4; ++var11) {
         if(this.header.getOpcode() != 5) {
            StringBuilder var12 = (new StringBuilder()).append(";; ");
            String var13 = Section.longString(var11);
            String var14 = var12.append(var13).append(":\n").toString();
            var1.append(var14);
         } else {
            StringBuilder var25 = (new StringBuilder()).append(";; ");
            String var26 = Section.updString(var11);
            String var27 = var25.append(var26).append(":\n").toString();
            var1.append(var27);
         }

         StringBuilder var16 = new StringBuilder();
         String var17 = this.sectionToString(var11);
         String var18 = var16.append(var17).append("\n").toString();
         var1.append(var18);
      }

      StringBuilder var29 = (new StringBuilder()).append(";; Message size: ");
      int var30 = this.numBytes();
      String var31 = var29.append(var30).append(" bytes").toString();
      var1.append(var31);
      return var1.toString();
   }

   void toWire(DNSOutput var1) {
      this.header.toWire(var1);
      Compression var2 = new Compression();

      for(int var3 = 0; var3 < 4; ++var3) {
         if(this.sections[var3] != false) {
            int var4 = 0;

            while(true) {
               int var5 = this.sections[var3].size();
               if(var4 >= var5) {
                  break;
               }

               ((Record)this.sections[var3].get(var4)).toWire(var1, var3, var2);
               ++var4;
            }
         }
      }

   }

   public byte[] toWire() {
      DNSOutput var1 = new DNSOutput();
      this.toWire(var1);
      int var2 = var1.current();
      this.size = var2;
      return var1.toByteArray();
   }

   public byte[] toWire(int var1) {
      DNSOutput var2 = new DNSOutput();
      this.toWire(var2, var1);
      int var4 = var2.current();
      this.size = var4;
      return var2.toByteArray();
   }
}
