package org.xbill.DNS;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.PrintStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.xbill.DNS.Cache;
import org.xbill.DNS.DClass;
import org.xbill.DNS.DNAMERecord;
import org.xbill.DNS.ExtendedResolver;
import org.xbill.DNS.Message;
import org.xbill.DNS.Mnemonic;
import org.xbill.DNS.Name;
import org.xbill.DNS.NameTooLongException;
import org.xbill.DNS.RRset;
import org.xbill.DNS.Rcode;
import org.xbill.DNS.Record;
import org.xbill.DNS.Resolver;
import org.xbill.DNS.ResolverConfig;
import org.xbill.DNS.SetResponse;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

public final class Lookup {

   public static final int HOST_NOT_FOUND = 3;
   public static final int SUCCESSFUL = 0;
   public static final int TRY_AGAIN = 2;
   public static final int TYPE_NOT_FOUND = 4;
   public static final int UNRECOVERABLE = 1;
   private static Map defaultCaches;
   private static Resolver defaultResolver;
   private static Name[] defaultSearchPath;
   private static final Name[] noAliases = new Name[0];
   private List aliases;
   private Record[] answers;
   private boolean badresponse;
   private String badresponse_error;
   private Cache cache;
   private int credibility;
   private int dclass;
   private boolean done;
   private boolean doneCurrent;
   private String error;
   private boolean foundAlias;
   private int iterations;
   private Name name;
   private boolean nametoolong;
   private boolean networkerror;
   private boolean nxdomain;
   private boolean referral;
   private Resolver resolver;
   private int result;
   private Name[] searchPath;
   private boolean temporary_cache;
   private boolean timedout;
   private int type;
   private boolean verbose;


   static {
      refreshDefault();
   }

   public Lookup(String var1) throws TextParseException {
      Name var2 = Name.fromString(var1);
      this(var2, 1, 1);
   }

   public Lookup(String var1, int var2) throws TextParseException {
      Name var3 = Name.fromString(var1);
      this(var3, var2, 1);
   }

   public Lookup(String var1, int var2, int var3) throws TextParseException {
      Name var4 = Name.fromString(var1);
      this(var4, var2, var3);
   }

   public Lookup(Name var1) {
      this(var1, 1, 1);
   }

   public Lookup(Name var1, int var2) {
      this(var1, var2, 1);
   }

   public Lookup(Name param1, int param2, int param3) {
      // $FF: Couldn't be decompiled
   }

   private void checkDone() {
      if(!this.done || this.result == -1) {
         StringBuilder var1 = (new StringBuilder()).append("Lookup of ");
         Name var2 = this.name;
         String var3 = var1.append(var2).append(" ").toString();
         StringBuffer var4 = new StringBuffer(var3);
         if(this.dclass != 1) {
            StringBuilder var5 = new StringBuilder();
            String var6 = DClass.string(this.dclass);
            String var7 = var5.append(var6).append(" ").toString();
            var4.append(var7);
         }

         StringBuilder var9 = new StringBuilder();
         String var10 = Type.string(this.type);
         String var11 = var9.append(var10).append(" isn\'t done").toString();
         var4.append(var11);
         String var13 = var4.toString();
         throw new IllegalStateException(var13);
      }
   }

   private void follow(Name var1, Name var2) {
      this.foundAlias = (boolean)1;
      this.badresponse = (boolean)0;
      this.networkerror = (boolean)0;
      this.timedout = (boolean)0;
      this.nxdomain = (boolean)0;
      this.referral = (boolean)0;
      int var3 = this.iterations + 1;
      this.iterations = var3;
      if(this.iterations < 6 && !var1.equals(var2)) {
         if(this.aliases == null) {
            ArrayList var4 = new ArrayList();
            this.aliases = var4;
         }

         this.aliases.add(var2);
         this.lookup(var1);
      } else {
         this.result = 1;
         this.error = "CNAME loop";
         this.done = (boolean)1;
      }
   }

   public static Cache getDefaultCache(int var0) {
      synchronized(Lookup.class){}

      Cache var3;
      try {
         DClass.check(var0);
         Map var1 = defaultCaches;
         Integer var2 = Mnemonic.toInteger(var0);
         var3 = (Cache)var1.get(var2);
         if(var3 == null) {
            var3 = new Cache(var0);
            Map var4 = defaultCaches;
            Integer var5 = Mnemonic.toInteger(var0);
            var4.put(var5, var3);
         }
      } finally {
         ;
      }

      return var3;
   }

   public static Resolver getDefaultResolver() {
      synchronized(Lookup.class){}

      Resolver var0;
      try {
         var0 = defaultResolver;
      } finally {
         ;
      }

      return var0;
   }

   public static Name[] getDefaultSearchPath() {
      synchronized(Lookup.class){}

      Name[] var0;
      try {
         var0 = defaultSearchPath;
      } finally {
         ;
      }

      return var0;
   }

   private void lookup(Name var1) {
      Cache var2 = this.cache;
      int var3 = this.type;
      int var4 = this.credibility;
      SetResponse var5 = var2.lookupRecords(var1, var3, var4);
      if(this.verbose) {
         PrintStream var6 = System.err;
         StringBuilder var7 = (new StringBuilder()).append("lookup ").append(var1).append(" ");
         String var8 = Type.string(this.type);
         String var9 = var7.append(var8).toString();
         var6.println(var9);
         System.err.println(var5);
      }

      this.processResponse(var1, var5);
      if(!this.done) {
         if(!this.doneCurrent) {
            int var10 = this.type;
            int var11 = this.dclass;
            Message var12 = Message.newQuery(Record.newRecord(var1, var10, var11));

            Message var13;
            try {
               var13 = this.resolver.send(var12);
            } catch (IOException var28) {
               if(var28 instanceof InterruptedIOException) {
                  this.timedout = (boolean)1;
                  return;
               }

               this.networkerror = (boolean)1;
               return;
            }

            int var15 = var13.getHeader().getRcode();
            if(var15 != 0 && var15 != 3) {
               this.badresponse = (boolean)1;
               String var16 = Rcode.string(var15);
               this.badresponse_error = var16;
            } else {
               Record var17 = var12.getQuestion();
               Record var18 = var13.getQuestion();
               if(!var17.equals(var18)) {
                  this.badresponse = (boolean)1;
                  this.badresponse_error = "response does not match query";
               } else {
                  SetResponse var19 = this.cache.addMessage(var13);
                  if(var19 == null) {
                     Cache var20 = this.cache;
                     int var21 = this.type;
                     int var22 = this.credibility;
                     var20.lookupRecords(var1, var21, var22);
                  }

                  if(this.verbose) {
                     PrintStream var24 = System.err;
                     StringBuilder var25 = (new StringBuilder()).append("queried ").append(var1).append(" ");
                     String var26 = Type.string(this.type);
                     String var27 = var25.append(var26).toString();
                     var24.println(var27);
                     System.err.println(var19);
                  }

                  this.processResponse(var1, var19);
               }
            }
         }
      }
   }

   private void processResponse(Name var1, SetResponse var2) {
      if(!var2.isSuccessful()) {
         if(var2.isNXDOMAIN()) {
            this.nxdomain = (boolean)1;
            this.doneCurrent = (boolean)1;
            if(this.iterations > 0) {
               this.result = 3;
               this.done = (boolean)1;
            }
         } else if(var2.isNXRRSET()) {
            this.result = 4;
            this.answers = null;
            this.done = (boolean)1;
         } else if(var2.isCNAME()) {
            Name var12 = var2.getCNAME().getTarget();
            this.follow(var12, var1);
         } else if(var2.isDNAME()) {
            DNAMERecord var13 = var2.getDNAME();

            try {
               Name var14 = var1.fromDNAME(var13);
               this.follow(var14, var1);
            } catch (NameTooLongException var16) {
               this.result = 1;
               this.error = "Invalid DNAME target";
               this.done = (boolean)1;
            }
         } else if(var2.isDelegation()) {
            this.referral = (boolean)1;
         }
      } else {
         RRset[] var3 = var2.answers();
         ArrayList var4 = new ArrayList();
         int var5 = 0;

         while(true) {
            int var6 = var3.length;
            if(var5 >= var6) {
               this.result = 0;
               Record[] var10 = new Record[var4.size()];
               Record[] var11 = (Record[])((Record[])var4.toArray(var10));
               this.answers = var11;
               this.done = (boolean)1;
               return;
            }

            Iterator var7 = var3[var5].rrs();

            while(var7.hasNext()) {
               Object var8 = var7.next();
               var4.add(var8);
            }

            ++var5;
         }
      }
   }

   public static void refreshDefault() {
      synchronized(Lookup.class){}

      try {
         try {
            defaultResolver = new ExtendedResolver();
         } catch (UnknownHostException var4) {
            throw new RuntimeException("Failed to initialize resolver");
         }

         defaultSearchPath = ResolverConfig.getCurrentConfig().searchPath();
         defaultCaches = new HashMap();
      } finally {
         ;
      }

   }

   private final void reset() {
      this.iterations = 0;
      this.foundAlias = (boolean)0;
      this.done = (boolean)0;
      this.doneCurrent = (boolean)0;
      this.aliases = null;
      this.answers = null;
      this.result = -1;
      this.error = null;
      this.nxdomain = (boolean)0;
      this.badresponse = (boolean)0;
      this.badresponse_error = null;
      this.networkerror = (boolean)0;
      this.timedout = (boolean)0;
      this.nametoolong = (boolean)0;
      this.referral = (boolean)0;
      if(this.temporary_cache) {
         this.cache.clearCache();
      }
   }

   private void resolve(Name var1, Name var2) {
      this.doneCurrent = (boolean)0;
      Name var3;
      if(var2 == null) {
         var3 = var1;
      } else {
         Name var4;
         try {
            var4 = Name.concatenate(var1, var2);
         } catch (NameTooLongException var6) {
            this.nametoolong = (boolean)1;
            return;
         }

         var3 = var4;
      }

      this.lookup(var3);
   }

   public static void setDefaultCache(Cache var0, int var1) {
      synchronized(Lookup.class){}

      try {
         DClass.check(var1);
         Map var2 = defaultCaches;
         Integer var3 = Mnemonic.toInteger(var1);
         var2.put(var3, var0);
      } finally {
         ;
      }

   }

   public static void setDefaultResolver(Resolver var0) {
      synchronized(Lookup.class){}

      try {
         defaultResolver = var0;
      } finally {
         ;
      }

   }

   public static void setDefaultSearchPath(String[] param0) throws TextParseException {
      // $FF: Couldn't be decompiled
   }

   public static void setDefaultSearchPath(Name[] var0) {
      synchronized(Lookup.class){}

      try {
         defaultSearchPath = var0;
      } finally {
         ;
      }

   }

   public Name[] getAliases() {
      this.checkDone();
      Name[] var1;
      if(this.aliases == null) {
         var1 = noAliases;
      } else {
         List var2 = this.aliases;
         Name[] var3 = new Name[this.aliases.size()];
         var1 = (Name[])((Name[])var2.toArray(var3));
      }

      return var1;
   }

   public Record[] getAnswers() {
      this.checkDone();
      return this.answers;
   }

   public String getErrorString() {
      this.checkDone();
      String var1;
      if(this.error != null) {
         var1 = this.error;
      } else {
         switch(this.result) {
         case 0:
            var1 = "successful";
            break;
         case 1:
            var1 = "unrecoverable error";
            break;
         case 2:
            var1 = "try again";
            break;
         case 3:
            var1 = "host not found";
            break;
         case 4:
            var1 = "type not found";
            break;
         default:
            throw new IllegalStateException("unknown result");
         }
      }

      return var1;
   }

   public int getResult() {
      this.checkDone();
      return this.result;
   }

   public Record[] run() {
      if(this.done) {
         this.reset();
      }

      Record[] var3;
      if(this.name.isAbsolute()) {
         Name var1 = this.name;
         this.resolve(var1, (Name)null);
      } else if(this.searchPath == null) {
         Name var4 = this.name;
         Name var5 = Name.root;
         this.resolve(var4, var5);
      } else {
         if(this.name.labels() > 1) {
            Name var6 = this.name;
            Name var7 = Name.root;
            this.resolve(var6, var7);
         }

         if(this.done) {
            var3 = this.answers;
            return var3;
         }

         int var8 = 0;

         while(true) {
            int var9 = this.searchPath.length;
            if(var8 >= var9) {
               break;
            }

            Name var10 = this.name;
            Name var11 = this.searchPath[var8];
            this.resolve(var10, var11);
            if(this.done) {
               var3 = this.answers;
               return var3;
            }

            if(this.foundAlias) {
               break;
            }

            ++var8;
         }
      }

      if(!this.done) {
         if(this.badresponse) {
            this.result = 2;
            String var2 = this.badresponse_error;
            this.error = var2;
            this.done = (boolean)1;
         } else if(this.timedout) {
            this.result = 2;
            this.error = "timed out";
            this.done = (boolean)1;
         } else if(this.networkerror) {
            this.result = 2;
            this.error = "network error";
            this.done = (boolean)1;
         } else if(this.nxdomain) {
            this.result = 3;
            this.done = (boolean)1;
         } else if(this.referral) {
            this.result = 1;
            this.error = "referral";
            this.done = (boolean)1;
         } else if(this.nametoolong) {
            this.result = 1;
            this.error = "name too long";
            this.done = (boolean)1;
         }
      }

      var3 = this.answers;
      return var3;
   }

   public void setCache(Cache var1) {
      if(var1 == null) {
         int var2 = this.dclass;
         Cache var3 = new Cache(var2);
         this.cache = var3;
         this.temporary_cache = (boolean)1;
      } else {
         this.cache = var1;
         this.temporary_cache = (boolean)0;
      }
   }

   public void setCredibility(int var1) {
      this.credibility = var1;
   }

   public void setResolver(Resolver var1) {
      this.resolver = var1;
   }

   public void setSearchPath(String[] var1) throws TextParseException {
      if(var1 == null) {
         this.searchPath = null;
      } else {
         Name[] var2 = new Name[var1.length];
         int var3 = 0;

         while(true) {
            int var4 = var1.length;
            if(var3 >= var4) {
               this.searchPath = var2;
               return;
            }

            String var5 = var1[var3];
            Name var6 = Name.root;
            Name var7 = Name.fromString(var5, var6);
            var2[var3] = var7;
            ++var3;
         }
      }
   }

   public void setSearchPath(Name[] var1) {
      this.searchPath = var1;
   }
}
