package gnu.inet.ldap;

import gnu.inet.ldap.AttributeValues;
import gnu.inet.ldap.BERDecoder;
import gnu.inet.ldap.BEREncoder;
import gnu.inet.ldap.BERException;
import gnu.inet.ldap.LDAPResult;
import gnu.inet.ldap.Modification;
import gnu.inet.ldap.ResultHandler;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ProtocolException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.naming.ldap.Control;

public class LDAPConnection {

   private static final int ADD_REQUEST = 104;
   private static final int ADD_RESPONSE = 105;
   private static final int BIND_REQUEST = 96;
   private static final int BIND_RESPONSE = 97;
   public static final int DEFAULT_PORT = 389;
   private static final int DELETE_REQUEST = 106;
   private static final int DELETE_RESPONSE = 107;
   public static final int DEREF_ALWAYS = 3;
   public static final int DEREF_FINDING_BASE_OBJ = 2;
   public static final int DEREF_IN_SEARCHING = 1;
   public static final int DEREF_NEVER = 0;
   private static final int MESSAGE = 48;
   private static final int MODIFY_DN_REQUEST = 108;
   private static final int MODIFY_DN_RESPONSE = 109;
   private static final int MODIFY_REQUEST = 102;
   private static final int MODIFY_RESPONSE = 103;
   private static final int SASL_BIND_IN_PROGRESS = 14;
   public static final int SCOPE_BASE_OBJECT = 0;
   public static final int SCOPE_SINGLE_LEVEL = 1;
   public static final int SCOPE_WHOLE_SUBTREE = 2;
   private static final int SEARCH_REFERENCE = 115;
   private static final int SEARCH_REQUEST = 99;
   private static final int SEARCH_RESULT = 100;
   private static final int SEARCH_RESULT_DONE = 101;
   private static final int SUCCESS = 0;
   private static final int UNBIND_REQUEST = 98;
   private Map asyncResponses;
   protected String host;
   private InputStream in;
   private int messageId;
   private OutputStream out;
   protected int port;
   private Socket socket;
   protected int version;


   public LDAPConnection(String var1) throws IOException {
      this(var1, 389, 0, 0);
   }

   public LDAPConnection(String var1, int var2) throws IOException {
      this(var1, var2, 0, 0);
   }

   public LDAPConnection(String var1, int var2, int var3, int var4) throws IOException {
      this.host = var1;
      int var5;
      if(var2 < 0) {
         var5 = 389;
      } else {
         var5 = var2;
      }

      this.port = var5;
      this.messageId = 0;
      HashMap var6 = new HashMap();
      this.asyncResponses = var6;
      this.version = 3;
      Socket var7 = new Socket();
      this.socket = var7;
      InetSocketAddress var8 = new InetSocketAddress(var1, var5);
      if(var3 > 0) {
         this.socket.connect(var8, var3);
      } else {
         this.socket.connect(var8);
      }

      InputStream var9 = this.socket.getInputStream();
      BufferedInputStream var10 = new BufferedInputStream(var9);
      this.in = var10;
      OutputStream var11 = this.socket.getOutputStream();
      BufferedOutputStream var12 = new BufferedOutputStream(var11);
      this.out = var12;
   }

   public LDAPResult add(String var1, AttributeValues[] var2) throws IOException {
      int var3 = 0;
      int var4 = this.messageId;
      int var5 = var4 + 1;
      this.messageId = var5;
      byte var6;
      if(this.version == 3) {
         var6 = 1;
      } else {
         var6 = 0;
      }

      BEREncoder var7 = new BEREncoder((boolean)var6);
      var7.append(var1);
      BEREncoder var8 = new BEREncoder((boolean)var6);

      while(true) {
         int var9 = var2.length;
         if(var3 >= var9) {
            byte[] var16 = var8.toByteArray();
            var7.append(var16, 16);
            byte[] var17 = var7.toByteArray();
            this.write(var4, 104, var17);
            BERDecoder var18 = this.read(var4).parseSequence(105);
            return this.parseResult(var18);
         }

         BEREncoder var10 = new BEREncoder((boolean)var6);
         String var11 = var2[var3].type;
         var10.append(var11);
         BEREncoder var12 = new BEREncoder((boolean)var6);
         Set var13 = var2[var3].values;
         this.appendValues(var12, var13);
         byte[] var14 = var12.toByteArray();
         var10.append(var14, 17);
         byte[] var15 = var10.toByteArray();
         var8.append(var15, 16);
         ++var3;
      }
   }

   void appendValues(BEREncoder var1, Set var2) throws BERException {
      if(var2 != null) {
         Iterator var3 = var2.iterator();

         while(var3.hasNext()) {
            Object var9 = var3.next();
            if(var9 == null) {
               var1.appendNull();
            } else if(var9 instanceof String) {
               String var4 = (String)var9;
               var1.append(var4);
            } else if(var9 instanceof Integer) {
               int var5 = ((Integer)var9).intValue();
               var1.append(var5);
            } else if(var9 instanceof Boolean) {
               boolean var6 = ((Boolean)var9).booleanValue();
               var1.append(var6);
            } else {
               if(!(var9 instanceof byte[])) {
                  String var8 = var9.getClass().getName();
                  throw new ClassCastException(var8);
               }

               byte[] var7 = (byte[])((byte[])var9);
               var1.append(var7);
            }
         }

      }
   }

   public LDAPResult bind(String var1, String var2, byte[] var3, Control[] var4) throws IOException {
      int var5 = 0;
      int var6 = this.messageId;
      int var7 = var6 + 1;
      this.messageId = var7;
      byte var8;
      if(this.version == 3) {
         var8 = 1;
      } else {
         var8 = 0;
      }

      BEREncoder var9 = new BEREncoder((boolean)var8);
      if(var2 == null) {
         int var10 = this.version;
         var9.append(var10);
         var9.append(var1);
         if(var3 != null) {
            var9.append(var3);
         }
      } else {
         int var15 = this.version;
         var9.append(var15);
         var9.append(var1);
         BEREncoder var16 = new BEREncoder((boolean)var8);
         var16.append(var2);
         if(var3 != null) {
            var16.append(var3);
         }

         byte[] var17 = var16.toByteArray();
         var9.append(var17, 16);
      }

      BEREncoder var11 = new BEREncoder((boolean)var8);
      if(var4 != null) {
         while(true) {
            int var12 = var4.length;
            if(var5 >= var12) {
               break;
            }

            Control var13 = var4[var5];
            byte[] var14 = this.controlSequence(var13, (boolean)var8);
            var11.append(var14, 16);
            ++var5;
         }
      }

      byte[] var18 = var11.toByteArray();
      var9.append(var18, 128);
      byte[] var19 = var9.toByteArray();
      this.write(var6, 96, var19);
      BERDecoder var20 = this.read(var6).parseSequence(97);
      LDAPResult var21 = this.parseResult(var20);
      if(var20.available()) {
         byte[] var22 = var20.parseOctetString();
      }

      return var21;
   }

   byte[] controlSequence(Control var1, boolean var2) throws IOException {
      BEREncoder var3 = new BEREncoder(var2);
      String var4 = var1.getID();
      var3.append(var4);
      if(var1.isCritical()) {
         var3.append((boolean)1);
      }

      return var3.toByteArray();
   }

   public LDAPResult delete(String var1) throws IOException {
      int var2 = this.messageId;
      int var3 = var2 + 1;
      this.messageId = var3;
      byte var4;
      if(this.version == 3) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      BEREncoder var5 = new BEREncoder((boolean)var4);
      var5.append(var1);
      byte[] var6 = var5.toByteArray();
      this.write(var2, 106, var6);
      BERDecoder var7 = this.read(var2);
      int var8 = var7.parseType();
      if(var8 != 107) {
         String var9 = "Unexpected response type: " + var8;
         throw new ProtocolException(var9);
      } else {
         BERDecoder var10 = var7.parseSequence();
         return this.parseResult(var10);
      }
   }

   public LDAPResult modify(String var1, Modification[] var2) throws IOException {
      int var3 = 0;
      int var4 = this.messageId;
      int var5 = var4 + 1;
      this.messageId = var5;
      byte var6;
      if(this.version == 3) {
         var6 = 1;
      } else {
         var6 = 0;
      }

      BEREncoder var7 = new BEREncoder((boolean)var6);
      var7.append(var1);
      BEREncoder var8 = new BEREncoder((boolean)var6);

      while(true) {
         int var9 = var2.length;
         if(var3 >= var9) {
            byte[] var19 = var8.toByteArray();
            var7.append(var19, 16);
            byte[] var20 = var7.toByteArray();
            this.write(var4, 102, var20);
            BERDecoder var21 = this.read(var4).parseSequence(103);
            return this.parseResult(var21);
         }

         BEREncoder var10 = new BEREncoder((boolean)var6);
         int var11 = var2[var3].operation;
         var10.append(var11);
         BEREncoder var12 = new BEREncoder((boolean)var6);
         String var13 = var2[var3].type;
         var12.append(var13);
         BEREncoder var14 = new BEREncoder((boolean)var6);
         Set var15 = var2[var3].values;
         this.appendValues(var14, var15);
         byte[] var16 = var14.toByteArray();
         var12.append(var16, 17);
         byte[] var17 = var12.toByteArray();
         var10.append(var17, 16);
         byte[] var18 = var10.toByteArray();
         var8.append(var18, 16);
         ++var3;
      }
   }

   public LDAPResult modifyDN(String var1, String var2, boolean var3, String var4) throws IOException {
      int var5 = this.messageId;
      int var6 = var5 + 1;
      this.messageId = var6;
      byte var7;
      if(this.version == 3) {
         var7 = 1;
      } else {
         var7 = 0;
      }

      BEREncoder var8 = new BEREncoder((boolean)var7);
      var8.append(var1);
      var8.append(var2);
      var8.append(var3);
      if(var4 != null) {
         var8.append(var4);
      }

      byte[] var9 = var8.toByteArray();
      this.write(var5, 108, var9);
      BERDecoder var10 = this.read(var5).parseSequence(109);
      return this.parseResult(var10);
   }

   LDAPResult parseResult(BERDecoder var1) throws IOException {
      int var2 = var1.parseInt();
      String var3 = var1.parseString();
      String var4 = var1.parseString();
      String[] var5 = null;
      if(var1.available() && var1.parseType() == 16) {
         ArrayList var6 = new ArrayList();
         BERDecoder var7 = var1.parseSequence();
         int var8 = var7.parseType();

         while(var8 != -1) {
            String var9 = var7.parseString();
            var6.add(var9);
         }

         String[] var11 = new String[var6.size()];
         var6.toArray(var11);
         var5 = var11;
      }

      return new LDAPResult(var2, var3, var4, var5);
   }

   BERDecoder read(int var1) throws IOException {
      Integer var2 = new Integer(var1);
      List var3 = (List)this.asyncResponses.get(var2);
      BERDecoder var4;
      BERDecoder var6;
      if(var3 != null) {
         var4 = (BERDecoder)var3.remove(0);
         if(var3.size() == 0) {
            this.asyncResponses.remove(var2);
         }

         var6 = var4;
      } else {
         while(true) {
            byte[] var10 = this.readMessage();
            byte var11;
            if(this.version == 3) {
               var11 = 1;
            } else {
               var11 = 0;
            }

            var4 = (new BERDecoder(var10, (boolean)var11)).parseSequence(48);
            int var12 = var4.parseInt();
            if(var12 == var1) {
               var6 = var4;
               break;
            }

            var2 = new Integer(var12);
            var3 = (List)this.asyncResponses.get(var2);
            if(var3 == null) {
               ArrayList var7 = new ArrayList();
               this.asyncResponses.put(var2, var7);
            }

            var3.add(var4);
         }
      }

      return var6;
   }

   int readByte() throws IOException {
      int var1 = this.in.read();
      if(var1 == -1) {
         throw new IOException("EOF");
      } else {
         return var1 & 255;
      }
   }

   byte[] readMessage() throws IOException {
      byte[] var1 = new byte[6];
      int var2 = 0 + 1;
      byte var3 = (byte)this.readByte();
      var1[0] = var3;
      int var4 = this.readByte();
      int var5 = var2 + 1;
      byte var6 = (byte)var4;
      var1[var2] = var6;
      int var14;
      int var15;
      if((var4 & 128) != 0) {
         int var7 = var4 - 128;
         if(var7 > 4) {
            String var8 = "Data too long: " + var7;
            throw new BERException(var8);
         }

         int var9 = 0;
         int var10 = var5;

         int var12;
         for(var5 = 0; var9 < var7; var10 = var12) {
            int var11 = this.readByte();
            var12 = var10 + 1;
            byte var13 = (byte)var11;
            var1[var10] = var13;
            var5 = (var5 << 8) + var11;
            ++var9;
         }

         var14 = var5;
         var15 = var10;
      } else {
         var14 = var4;
         var15 = var5;
      }

      byte[] var16 = new byte[var15 + var14];
      System.arraycopy(var1, 0, var16, 0, var15);
      if(var14 == 0) {
         var1 = var16;
      } else {
         int var17 = var14;
         int var18 = var15;

         do {
            var15 = this.in.read(var16, var18, var17);
            if(var15 == -1) {
               throw new IOException("EOF");
            }

            int var10000 = var18 + var15;
            var17 -= var15;
         } while(var17 > 0);

         var1 = var16;
      }

      return var1;
   }

   public LDAPResult search(String var1, int var2, int var3, int var4, int var5, boolean var6, String var7, String[] var8, Control[] var9, ResultHandler var10) throws IOException {
      String var11;
      if(var7 != null && var7.length() != 0) {
         var11 = var7;
      } else {
         var11 = "(objectClass=*)";
      }

      int var12 = this.messageId;
      int var13 = var12 + 1;
      this.messageId = var13;
      byte var14;
      if(this.version == 3) {
         var14 = 1;
      } else {
         var14 = 0;
      }

      BEREncoder var15 = new BEREncoder((boolean)var14);
      var15.append(var1);
      var15.append(var2, 10);
      var15.append(var3, 10);
      var15.append(var4);
      var15.append(var5);
      var15.append(var6);
      var15.appendFilter(var11);
      BEREncoder var22 = new BEREncoder((boolean)var14);
      if(var8 != null) {
         int var23 = 0;

         while(true) {
            int var24 = var8.length;
            if(var23 >= var24) {
               break;
            }

            String var25 = var8[var23];
            var22.append(var25);
            ++var23;
         }
      }

      byte[] var26 = var22.toByteArray();
      var15.append(var26, 16);
      BEREncoder var27 = new BEREncoder((boolean)var14);
      if(var9 != null) {
         int var28 = 0;

         while(true) {
            int var29 = var9.length;
            if(var28 >= var29) {
               break;
            }

            Control var30 = var9[var28];
            byte[] var31 = this.controlSequence(var30, (boolean)var14);
            var27.append(var31, 16);
            ++var28;
         }
      }

      byte[] var32 = var27.toByteArray();
      var15.append(var32, 16);
      byte[] var33 = var15.toByteArray();
      this.write(var12, 99, var33);

      while(true) {
         BERDecoder var34 = this.read(var12);
         int var35 = var34.parseType();
         BERDecoder var39;
         switch(var35) {
         case 100:
            BERDecoder var37 = var34.parseSequence(var35);
            String var38 = var37.parseString();
            var39 = var37.parseSequence(48);
            TreeMap var62 = new TreeMap();

            while(var39.available()) {
               BERDecoder var40 = var39.parseSequence(48);
               String var41 = var40.parseString();
               BERDecoder var63 = var40.parseSet(49);
               ArrayList var42 = new ArrayList();

               while(var63.available()) {
                  switch(var63.parseType()) {
                  case 1:
                     Boolean var43 = Boolean.valueOf(var63.parseBoolean());
                     var42.add(var43);
                     break;
                  case 2:
                  case 10:
                     int var45 = var63.parseInt();
                     Integer var46 = new Integer(var45);
                     var42.add(var46);
                     break;
                  case 4:
                     byte[] var50 = var63.parseOctetString();
                     var42.add(var50);
                     break;
                  case 12:
                     String var48 = var63.parseString();
                     var42.add(var48);
                  }
               }

               var62.put(var41, var42);
            }

            var10.searchResultEntry(var38, var62);
            break;
         case 101:
            BERDecoder var60 = var34.parseSequence(var35);
            return this.parseResult(var60);
         case 115:
            ArrayList var61 = new ArrayList();
            var39 = var34.parseSequence(var35);

            while(var39.available()) {
               String var56 = var39.parseString();
               var61.add(var56);
            }

            var10.searchResultReference(var61);
            break;
         default:
            String var36 = "Unexpected response: " + var35;
            throw new ProtocolException(var36);
         }
      }
   }

   public void setVersion(int var1) {
      if(var1 >= 2 && var1 <= 3) {
         this.version = var1;
      } else {
         String var2 = Integer.toString(var1);
         throw new IllegalArgumentException(var2);
      }
   }

   public void unbind() throws IOException {
      int var1 = this.messageId;
      int var2 = var1 + 1;
      this.messageId = var2;
      byte var3;
      if(this.version == 3) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      BEREncoder var4 = new BEREncoder((boolean)var3);
      var4.appendNull();
      byte[] var5 = var4.toByteArray();
      this.write(var1, 98, var5);
      this.socket.close();
   }

   void write(int var1, int var2, byte[] var3) throws IOException {
      byte var4;
      if(this.version == 3) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      BEREncoder var5 = new BEREncoder((boolean)var4);
      var5.append(var1);
      var5.append(var3, var2);
      BEREncoder var6 = new BEREncoder((boolean)var4);
      byte[] var7 = var5.toByteArray();
      var6.append(var7, 48);
      byte[] var8 = var6.toByteArray();
      this.out.write(var8);
      this.out.flush();
   }
}
