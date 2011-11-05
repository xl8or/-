package gnu.inet.imap;

import gnu.inet.imap.IMAPConstants;
import gnu.inet.imap.IMAPResponse;
import gnu.inet.imap.Pair;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class IMAPResponseTokenizer implements IMAPConstants {

   private static final int BUFFER_SIZE = 4096;
   private static final String DEFAULT_ENCODING = "US-ASCII";
   private static final int STATE_CODE = 4;
   private static final int STATE_COUNT = 1;
   private static final int STATE_ID = 2;
   private static final int STATE_LITERAL = 6;
   private static final int STATE_LITERAL_LENGTH = 5;
   private static final int STATE_MAYBE_CODE = 3;
   private static final int STATE_STATUS = 8;
   private static final int STATE_TAG = 0;
   private static final int STATE_TEXT = 7;
   private byte[] buffer = null;
   protected InputStream in;


   public IMAPResponseTokenizer(InputStream var1) {
      this.in = var1;
   }

   void mark(int var1) {
      int var2 = this.buffer.length;
      int var3 = var1 + 1;
      if(var3 < var2) {
         int var4 = var2 - var3;
         byte[] var5 = new byte[var4];
         System.arraycopy(this.buffer, var3, var5, 0, var4);
         this.buffer = var5;
      } else {
         this.buffer = null;
      }
   }

   public IMAPResponse next() throws IOException {
      byte var2 = 0;
      byte[] var3 = this.read((boolean)var2);
      IMAPResponse var4;
      if(var3 == null) {
         var4 = null;
      } else {
         int var5 = var3.length;
         IMAPResponse var6 = new IMAPResponse();
         ByteArrayOutputStream var7 = new ByteArrayOutputStream();
         Stack var8 = new Stack();
         byte var9 = 0;
         ByteArrayOutputStream var10 = null;
         boolean var11 = false;
         int var12 = -1;
         boolean var13 = var11;
         byte var14 = 0;
         int var15 = 0;
         int var16 = var14;

         while(true) {
            if(var15 >= var5) {
               byte var107 = 1;
               this.read((boolean)var107);
               var4 = this.next();
               break;
            }

            byte var19;
            boolean var18;
            int var21;
            int var20;
            ByteArrayOutputStream var22;
            label181: {
               byte var17 = var3[var15];
               switch(var9) {
               case 0:
                  if(var15 == 0 && var17 == 42) {
                     var6.tag = "*";
                     var18 = var13;
                     var19 = var9;
                     var20 = var12;
                     var21 = var16;
                     var22 = var10;
                  } else if(var15 == 0 && var17 == 43) {
                     var6.tag = "+";
                     var18 = var13;
                     var19 = var9;
                     var20 = var12;
                     var21 = var16;
                     var22 = var10;
                  } else if(var17 == 32) {
                     if(var6.tag == null) {
                        byte[] var23 = var7.toByteArray();
                        String var24 = new String(var23, "US-ASCII");
                        var6.tag = var24;
                     }

                     var7.reset();
                     if(var6.isContinuation()) {
                        var20 = var12;
                        var21 = var16;
                        var22 = var10;
                        byte var25 = 7;
                        var18 = var13;
                        var19 = var25;
                     } else {
                        var20 = var12;
                        var21 = var16;
                        var22 = var10;
                        byte var26 = 1;
                        var18 = var13;
                        var19 = var26;
                     }
                  } else {
                     var7.write(var17);
                     var18 = var13;
                     var19 = var9;
                     var20 = var12;
                     var21 = var16;
                     var22 = var10;
                  }
                  break label181;
               case 1:
                  if(var17 < 48 || var17 > 57) {
                     var9 = 2;
                  }

                  if(var17 == 32) {
                     byte[] var27 = var7.toByteArray();
                     var7.reset();
                     String var28 = new String(var27, "US-ASCII");

                     try {
                        int var29 = Integer.parseInt(var28);
                        var6.count = var29;
                     } catch (NumberFormatException var110) {
                        String var32 = "Expecting number: " + var28;
                        throw new ProtocolException(var32);
                     }

                     var20 = var12;
                     var21 = var16;
                     var22 = var10;
                     byte var30 = 2;
                     var18 = var13;
                     var19 = var30;
                  } else {
                     var7.write(var17);
                     var18 = var13;
                     var19 = var9;
                     var20 = var12;
                     var21 = var16;
                     var22 = var10;
                  }
                  break label181;
               case 2:
                  if(var17 == 32) {
                     byte[] var33 = var7.toByteArray();
                     var7.reset();
                     String var34 = (new String(var33, "US-ASCII")).intern();
                     var6.id = var34;
                     var20 = var12;
                     var21 = var16;
                     var22 = var10;
                     byte var35 = 3;
                     var18 = var13;
                     var19 = var35;
                     break label181;
                  }

                  if(var17 == 10) {
                     byte[] var36 = var7.toByteArray();
                     var7.reset();
                     String var37 = (new String(var36, "US-ASCII")).intern();
                     var6.id = var37;
                     this.mark(var15);
                     var4 = var6;
                     return var4;
                  }

                  if(var17 != 13) {
                     var7.write(var17);
                     var18 = var13;
                     var19 = var9;
                     var20 = var12;
                     var21 = var16;
                     var22 = var10;
                     break label181;
                  }
                  break;
               case 3:
                  if(var17 != 40 && var17 != 91) {
                     if(var6.id == "FETCH") {
                        var7.reset();
                        byte[] var43 = new byte[]{(byte)70, (byte)69, (byte)84, (byte)67, (byte)72, (byte)32};
                        var7.write(var43);
                        var7.write(var17);
                        var20 = var12;
                        var21 = var16;
                        var22 = var10;
                        byte var44 = 2;
                        var18 = var13;
                        var19 = var44;
                     } else if(var6.id == "STATUS") {
                        var7.write(var17);
                        var20 = var12;
                        var21 = var16;
                        var22 = var10;
                        byte var45 = 8;
                        var18 = var13;
                        var19 = var45;
                     } else {
                        var7.write(var17);
                        var20 = var12;
                        var21 = var16;
                        var22 = var10;
                        byte var46 = 7;
                        var18 = var13;
                        var19 = var46;
                     }
                  } else {
                     ArrayList var40 = new ArrayList();
                     var6.code = var40;
                     var8.push(var40);
                     var20 = var12;
                     var21 = var16;
                     var22 = var10;
                     byte var42 = 4;
                     var18 = var13;
                     var19 = var42;
                  }
                  break label181;
               case 4:
                  if(var17 == 34) {
                     if(!var13) {
                        var18 = true;
                     } else {
                        var18 = false;
                     }

                     var19 = var9;
                     var20 = var12;
                     var21 = var16;
                     var22 = var10;
                     break label181;
                  }

                  if(var13) {
                     var7.write(var17);
                     var18 = var13;
                     var19 = var9;
                     var20 = var12;
                     var21 = var16;
                     var22 = var10;
                     break label181;
                  }

                  if(var17 == 40 || var17 == 91) {
                     List var49 = (List)var8.peek();
                     ArrayList var50 = new ArrayList();
                     if(var7.size() > 0) {
                        byte[] var51 = var7.toByteArray();
                        String var52 = new String;
                        String var55 = "US-ASCII";
                        var52.<init>(var51, var55);
                        String var56 = var52.intern();
                        Pair var57 = new Pair(var56, var50);
                        boolean var63 = var49.add(var57);
                        var7.reset();
                     } else {
                        var49.add(var50);
                     }

                     var8.push(var50);
                     var18 = var13;
                     var19 = var9;
                     var20 = var12;
                     var21 = var16;
                     var22 = var10;
                     break label181;
                  }

                  if(var17 == 41 || var17 == 93) {
                     List var66 = (List)var8.pop();
                     if(var7.size() > 0) {
                        byte[] var67 = var7.toByteArray();
                        String var68 = new String;
                        String var71 = "US-ASCII";
                        var68.<init>(var67, var71);
                        String var72 = var68.intern();
                        var66.add(var72);
                        var7.reset();
                     }

                     var18 = var13;
                     var19 = var9;
                     var20 = var12;
                     var21 = var16;
                     var22 = var10;
                     break label181;
                  }

                  if(var17 == 123) {
                     var7.reset();
                     var20 = var12;
                     var21 = var16;
                     var22 = var10;
                     byte var74 = 5;
                     var18 = var13;
                     var19 = var74;
                     break label181;
                  }

                  if(var17 == 32) {
                     if(var8.size() == 0) {
                        var20 = var12;
                        var21 = var16;
                        var22 = var10;
                        byte var75 = 7;
                        var18 = var13;
                        var19 = var75;
                     } else {
                        List var76 = (List)var8.peek();
                        if(var7.size() > 0) {
                           byte[] var77 = var7.toByteArray();
                           String var78 = new String;
                           String var81 = "US-ASCII";
                           var78.<init>(var77, var81);
                           String var82 = var78.intern();
                           var76.add(var82);
                           var7.reset();
                        }

                        var18 = var13;
                        var19 = var9;
                        var20 = var12;
                        var21 = var16;
                        var22 = var10;
                     }
                     break label181;
                  }

                  if(var17 == 10) {
                     this.mark(var15);
                     var4 = var6;
                     return var4;
                  }

                  if(var17 != 13) {
                     var7.write(var17);
                     var18 = var13;
                     var19 = var9;
                     var20 = var12;
                     var21 = var16;
                     var22 = var10;
                     break label181;
                  }
                  break;
               case 5:
                  if(var17 == 125) {
                     byte[] var86 = var7.toByteArray();
                     var7.reset();
                     String var87 = new String(var86, "US-ASCII");

                     int var88;
                     try {
                        var88 = Integer.parseInt(var87);
                     } catch (NumberFormatException var109) {
                        String var92 = "Expecting number: " + var87;
                        throw new ProtocolException(var92);
                     }

                     var21 = var16;
                     var22 = var10;
                     var18 = var13;
                     var19 = var9;
                     var20 = var88;
                     break label181;
                  }

                  if(var17 == 10) {
                     ByteArrayOutputStream var93 = new ByteArrayOutputStream();
                     var19 = 6;
                     var18 = var13;
                     var21 = 0;
                     var22 = var93;
                     var20 = var12;
                     break label181;
                  }

                  if(var17 != 13) {
                     var7.write(var17);
                     var18 = var13;
                     var19 = var9;
                     var20 = var12;
                     var21 = var16;
                     var22 = var10;
                     break label181;
                  }
                  break;
               case 6:
                  if(var16 >= var12) {
                     List var96 = (List)var8.peek();
                     byte[] var97 = var10.toByteArray();
                     var96.add(var97);
                     var19 = 4;
                     var20 = var12;
                     var21 = var16;
                     var22 = null;
                     var18 = var13;
                  } else {
                     var10.write(var17);
                     int var100 = var16 + 1;
                     var22 = var10;
                     var20 = var12;
                     var21 = var100;
                     var18 = var13;
                     var19 = var9;
                  }
                  break label181;
               case 7:
                  if(var17 == 10) {
                     byte[] var102 = var7.toByteArray();
                     var7.reset();
                     String var103 = new String(var102, "US-ASCII");
                     var6.text = var103;
                     this.mark(var15);
                     var4 = var6;
                     return var4;
                  }

                  if(var17 != 13) {
                     var7.write(var17);
                  }
                  break;
               case 8:
                  if(var17 == 32) {
                     String var47 = var7.toString();
                     var6.mailbox = var47;
                     var7.reset();
                     var20 = var12;
                     var21 = var16;
                     var22 = var10;
                     byte var48 = 3;
                     var18 = var13;
                     var19 = var48;
                  } else {
                     var7.write(var17);
                     var18 = var13;
                     var19 = var9;
                     var20 = var12;
                     var21 = var16;
                     var22 = var10;
                  }
                  break label181;
               }

               var18 = var13;
               var19 = var9;
               var20 = var12;
               var21 = var16;
               var22 = var10;
            }

            ++var15;
            var10 = var22;
            var16 = var21;
            var12 = var20;
            var9 = var19;
            var13 = var18;
         }
      }

      return var4;
   }

   byte[] read(boolean var1) throws IOException {
      byte[] var2;
      if(this.buffer != null && !var1 && this.buffer.length > 0) {
         var2 = this.buffer;
      } else {
         int var3 = this.in.available();
         if(var3 < 1) {
            var3 = 4096;
         }

         byte[] var4 = new byte[var3];

         int var5;
         for(var5 = 0; var5 == 0; var5 = this.in.read(var4, 0, var3)) {
            ;
         }

         if(var5 == -1) {
            var2 = null;
         } else {
            int var6;
            if(this.buffer == null) {
               var6 = 0;
            } else {
               var6 = this.buffer.length;
            }

            byte[] var7 = new byte[var6 + var5];
            if(var6 != 0) {
               System.arraycopy(this.buffer, 0, var7, 0, var6);
            }

            System.arraycopy(var4, 0, var7, var6, var5);
            this.buffer = var7;
            var2 = this.buffer;
         }
      }

      return var2;
   }
}
