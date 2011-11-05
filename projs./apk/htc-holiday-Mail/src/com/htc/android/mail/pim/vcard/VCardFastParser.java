package com.htc.android.mail.pim.vcard;

import com.htc.android.mail.pim.vcard.VCardException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class VCardFastParser {

   private final int BUFFER_SIZE = 4096;
   private boolean mStopFlag = 0;


   public VCardFastParser() {}

   private VCardFastParser.SimpleContact getContact(byte[] param1) {
      // $FF: Couldn't be decompiled
   }

   private byte[] getRawData(RandomAccessFile var1, long var2, long var4) {
      byte[] var6;
      if(var4 - var2 == 0L) {
         var6 = null;
      } else {
         byte[] var7 = new byte[(int)(var4 - var2)];

         try {
            var1.seek(var2);
            var1.read(var7);
         } catch (IOException var9) {
            var9.printStackTrace();
         }

         var6 = var7;
      }

      return var6;
   }

   private static String parseName(String var0) {
      String var1 = "";
      String[] var2 = var0.split("(?<=[^\\\\]);");
      if(var2.length >= 2) {
         if(var2[1].length() > 0) {
            StringBuilder var3 = new StringBuilder();
            String var4 = var2[1];
            StringBuilder var5 = var3.append(var4).append(" ");
            String var6 = var2[0];
            var1 = var5.append(var6).toString();
         } else {
            var1 = var2[0];
         }
      } else if(var2.length == 1) {
         var1 = var2[0];
      } else if(var2.length == 0) {
         var1 = var0;
      }

      return var1;
   }

   private void setStopPoint() throws VCardException {
      if(this.mStopFlag) {
         this.mStopFlag = (boolean)0;
         throw new VCardException(20, "ContactGen Stop manual");
      }
   }

   public int getvCardCount(File var1) throws IOException, VCardException {
      this.mStopFlag = (boolean)0;
      int var2 = 0;
      byte[] var3 = new byte[4096];
      RandomAccessFile var4 = new RandomAccessFile(var1, "r");
      int var5 = 0;

      try {
         while(true) {
            int var6 = var4.read(var3);
            if(var6 == -1) {
               break;
            }

            this.setStopPoint();
            if(var6 < 4096) {
               int var7 = var6 + 1;
               Arrays.fill(var3, var7, 4095, (byte)0);
            }

            int var8;
            for(var8 = 0; var8 < 4085; ++var8) {
               if(var3[var8] == 66) {
                  int var9 = var8 + 1;
                  if(var3[var9] == 69) {
                     int var10 = var8 + 2;
                     if(var3[var10] == 71) {
                        int var11 = var8 + 3;
                        if(var3[var11] == 73) {
                           int var12 = var8 + 4;
                           if(var3[var12] == 78) {
                              int var13 = var8 + 5;
                              if(var3[var13] == 58) {
                                 int var14 = var8 + 6;
                                 if(var3[var14] == 86) {
                                    int var15 = var8 + 7;
                                    if(var3[var15] == 67) {
                                       int var16 = var8 + 8;
                                       if(var3[var16] == 65) {
                                          int var17 = var8 + 9;
                                          if(var3[var17] == 82) {
                                             int var18 = var8 + 10;
                                             if(var3[var18] == 68) {
                                                ++var2;
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }

            var5 = var5 + var8 + 1;
            long var19 = (long)var5;
            var4.seek(var19);
         }
      } finally {
         var4.close();
      }

      if(var2 > 1) {
         var2 += -1;
      }

      return var2;
   }

   public long[] getvCardStartpoint(File var1) throws IOException, VCardException {
      this.mStopFlag = (boolean)0;
      ArrayList var2 = new ArrayList();
      byte[] var3 = new byte[4096];
      RandomAccessFile var4 = new RandomAccessFile(var1, "r");
      long var5 = 0L;

      while(true) {
         int var7 = var4.read(var3);
         if(var7 == -1) {
            var4.close();
            Object var23;
            if(var2.size() == 0) {
               var23 = false;
            } else if(var2.size() == 1) {
               var23 = true;
               long var24 = ((Long)var2.get(0)).longValue();
               ((Object[])var23)[0] = var24;
            } else {
               Object var26 = var2.size() - 1;
               int var27 = 1;

               while(true) {
                  int var28 = var2.size();
                  if(var27 >= var28) {
                     var23 = var26;
                     break;
                  }

                  int var29 = var27 - 1;
                  long var30 = ((Long)var2.get(var27)).longValue();
                  ((Object[])var26)[var29] = var30;
                  ++var27;
               }
            }

            return (long[])var23;
         }

         this.setStopPoint();
         if(var7 < 4096) {
            int var8 = var7 + 1;
            Arrays.fill(var3, var8, 4095, (byte)0);
         }

         int var9;
         for(var9 = 0; var9 < 4085; ++var9) {
            if(var3[var9] == 66) {
               int var10 = var9 + 1;
               if(var3[var10] == 69) {
                  int var11 = var9 + 2;
                  if(var3[var11] == 71) {
                     int var12 = var9 + 3;
                     if(var3[var12] == 73) {
                        int var13 = var9 + 4;
                        if(var3[var13] == 78) {
                           int var14 = var9 + 5;
                           if(var3[var14] == 58) {
                              int var15 = var9 + 6;
                              if(var3[var15] == 86) {
                                 int var16 = var9 + 7;
                                 if(var3[var16] == 67) {
                                    int var17 = var9 + 8;
                                    if(var3[var17] == 65) {
                                       int var18 = var9 + 9;
                                       if(var3[var18] == 82) {
                                          int var19 = var9 + 10;
                                          if(var3[var19] == 68) {
                                             int var20 = 0 + 1;
                                             Long var21 = Long.valueOf((long)var9 + var5);
                                             var2.add(var21);
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         var5 = (long)var9 + var5 + 1L;
         var4.seek(var5);
      }
   }

   public VCardFastParser.SimpleContact[] parseContactsData(File var1) throws IOException, VCardException {
      this.mStopFlag = (boolean)0;
      ArrayList var2 = new ArrayList();
      RandomAccessFile var3 = new RandomAccessFile(var1, "r");

      try {
         long[] var4 = this.getvCardStartpoint(var1);
         new Date();
         int var6 = 0;

         while(true) {
            int var7 = var4.length;
            if(var6 >= var7) {
               new Date();
               break;
            }

            this.setStopPoint();
            int var8 = var6 + 1;
            int var9 = var4.length;
            byte[] var14;
            if(var8 == var9) {
               long var10 = var4[var6];
               long var12 = var1.length();
               var14 = this.getRawData(var3, var10, var12);
            } else {
               long var17 = var4[var6];
               int var19 = var6 + 1;
               long var20 = var4[var19];
               var14 = this.getRawData(var3, var17, var20);
            }

            VCardFastParser.SimpleContact var15 = this.getContact(var14);
            var2.add(var15);
            ++var6;
         }
      } finally {
         var3.close();
      }

      VCardFastParser.SimpleContact[] var23 = new VCardFastParser.SimpleContact[0];
      return (VCardFastParser.SimpleContact[])var2.toArray(var23);
   }

   public void stop() {
      this.mStopFlag = (boolean)1;
   }

   public static class SimpleContact {

      public String Name;
      public String[] Phone;


      public SimpleContact() {}
   }
}
