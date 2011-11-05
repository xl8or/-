package com.htc.android.mail;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.htc.android.mail.Base64;
import com.htc.android.mail.ll;
import com.htc.android.mail.util.SparseLongBooleanArray;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

public class Mailbox implements Serializable, Parcelable {

   public static final Creator<Mailbox> CREATOR = new Mailbox.1();
   public static final long sCombinedAccountDefaultMailboxId = Long.MAX_VALUE;
   public static final long sCombinedAccountDraftMailboxId = 9223372036854775804L;
   public static final long sCombinedAccountOutMailboxId = 9223372036854775803L;
   public static final long sCombinedAccountSentMailboxId = 9223372036854775805L;
   public static final long sCombinedAccountTrashMailboxId = 9223372036854775806L;
   public static final long sCombinedDefaultSyncMailboxId = 9223372036854775802L;
   public static final long sCombinedInboxAndSentMailboxId = 9223372036854775801L;
   public static final int sKindCombined = 2147483642;
   public static final int sKindDefault = Integer.MAX_VALUE;
   public static final int sKindDraft = 2147483644;
   public static final int sKindNormal = 0;
   public static final int sKindOut = 2147483643;
   public static final int sKindSent = 2147483645;
   public static final int sKindTrash = 2147483646;
   public static final int sMimeViewerBox = 2147483447;
   private static final long serialVersionUID = 1L;
   public String decodeName;
   public long id;
   public boolean isServerFolder;
   public int kind;
   private long mAccountId;
   private SparseLongBooleanArray mCombinedMailboxArray;
   private long[] mCombinedMailboxIds;
   private boolean mDefaultSyncEnabled;
   public int mHasChild;
   public int mNoSelect;
   public long mScreenOffTime;
   public int moveGroup;
   public String parentId;
   public String serverId;
   public String shortName;
   public boolean showSender;
   public String type;
   public String unDecodeName;


   public Mailbox(long var1, long var3, String var5, String var6, String var7, int var8, String var9, String var10, String var11, int var12, int var13) {
      this.mHasChild = 0;
      this.mNoSelect = 0;
      this.mAccountId = 65535L;
      this.kind = 0;
      this.mScreenOffTime = 0L;
      this.mAccountId = var1;
      this.id = var3;
      this.unDecodeName = var5;
      this.decodeName = var6;
      this.shortName = var7;
      byte var14;
      if(var8 == null) {
         var14 = 1;
      } else {
         var14 = 0;
      }

      this.isServerFolder = (boolean)var14;
      this.moveGroup = var12;
      byte var15;
      if(var13 == null) {
         var15 = 1;
      } else {
         var15 = 0;
      }

      this.showSender = (boolean)var15;
      this.type = var9;
      this.serverId = var10;
      this.parentId = var11;
   }

   public Mailbox(long var1, String var3, boolean var4, int var5, int var6) {
      this.mHasChild = 0;
      this.mNoSelect = 0;
      this.mAccountId = 65535L;
      this.kind = 0;
      this.mScreenOffTime = 0L;
      this.mAccountId = var1;
      this.unDecodeName = var3;
      this.isServerFolder = var4;
      this.mHasChild = var5;
      this.mNoSelect = var6;
   }

   private Mailbox(Parcel var1) {
      this.mHasChild = 0;
      this.mNoSelect = 0;
      this.mAccountId = 65535L;
      this.kind = 0;
      this.mScreenOffTime = 0L;
      this.readFromParcel(var1);
   }

   // $FF: synthetic method
   Mailbox(Parcel var1, Mailbox.1 var2) {
      this(var1);
   }

   public void addMailboxId(long var1) {
      if(this.mCombinedMailboxArray == null) {
         SparseLongBooleanArray var3 = new SparseLongBooleanArray();
         this.mCombinedMailboxArray = var3;
      }

      this.mCombinedMailboxArray.put(var1, (boolean)1);
   }

   public boolean contains(long var1) {
      boolean var3;
      if(this.mCombinedMailboxArray != null) {
         var3 = this.mCombinedMailboxArray.get(var1);
      } else {
         long var4 = this.id;
         if(var1 == var4) {
            var3 = true;
         } else {
            var3 = false;
         }
      }

      return var3;
   }

   public void decode() {
      if(this.unDecodeName != null) {
         if(this.unDecodeName.length() > 0) {
            String var1 = this.unDecodeName;
            if(this.unDecodeName.lastIndexOf("/") != -1) {
               String var2 = this.unDecodeName;
               int var3 = this.unDecodeName.lastIndexOf("/") + 1;
               int var4 = this.unDecodeName.length();
               var2.substring(var3, var4);
            }

            try {
               byte[] var6 = this.unDecodeName.getBytes("ISO8859-1");
               String var7 = new String(var6, "IMAP-mailbox-name");
               this.decodeName = var7;
               byte[] var8 = var1.getBytes("ISO8859-1");
               String var9 = new String(var8, "IMAP-mailbox-name");
               this.shortName = var9;
            } catch (UnsupportedEncodingException var13) {
               String var11 = Base64.ModifiedUtf7decode(this.unDecodeName);
               this.shortName = var11;
               String var12 = Base64.ModifiedUtf7decode(var1);
               this.decodeName = var12;
               ll.d("Base64", "UnsupportedEncodingException");
            }
         }
      }
   }

   public int describeContents() {
      return 0;
   }

   public long getAccountId() {
      return this.mAccountId;
   }

   public String getDecodeName(Context var1, boolean var2) {
      String var3;
      if(var2) {
         var3 = this.decodeName;
      } else {
         if(this.mAccountId == Long.MAX_VALUE) {
            if(this.kind == Integer.MAX_VALUE) {
               var3 = var1.getString(2131362253);
               return var3;
            }

            if(this.kind == 2147483646) {
               var3 = var1.getString(2131362257);
               return var3;
            }

            if(this.kind == 2147483645) {
               var3 = var1.getString(2131362256);
               return var3;
            }

            if(this.kind == 2147483644) {
               var3 = var1.getString(2131362254);
               return var3;
            }

            if(this.kind == 2147483643) {
               var3 = var1.getString(2131362255);
               return var3;
            }
         } else {
            if(this.kind == Integer.MAX_VALUE) {
               var3 = var1.getString(2131362246);
               return var3;
            }

            if(this.kind == 2147483646) {
               var3 = var1.getString(2131362250);
               return var3;
            }

            if(this.kind == 2147483645) {
               var3 = var1.getString(2131362251);
               return var3;
            }

            if(this.kind == 2147483644) {
               var3 = var1.getString(2131362249);
               return var3;
            }

            if(this.kind == 2147483643) {
               var3 = var1.getString(2131362252);
               return var3;
            }
         }

         if(this.id == 9223372036854775802L) {
            var3 = var1.getString(2131362259);
         } else if(this.id == 9223372036854775801L) {
            var3 = var1.getString(2131362260);
         } else {
            var3 = this.decodeName;
         }
      }

      return var3;
   }

   public boolean getDefaultSyncEnabled() {
      return this.mDefaultSyncEnabled;
   }

   public long[] getMailboxIds() {
      if(this.mCombinedMailboxIds == null) {
         if(this.kind == 2147483642) {
            Object var1 = this.mCombinedMailboxArray.size();
            this.mCombinedMailboxIds = (long[])var1;
            int var2 = 0;

            while(true) {
               int var3 = this.mCombinedMailboxArray.size();
               if(var2 >= var3) {
                  break;
               }

               long[] var4 = this.mCombinedMailboxIds;
               long var5 = this.mCombinedMailboxArray.keyAt(var2);
               var4[var2] = var5;
               ++var2;
            }
         } else {
            Object var7 = null;
            long var8 = this.id;
            ((Object[])var7)[0] = var8;
            this.mCombinedMailboxIds = (long[])var7;
         }
      }

      return this.mCombinedMailboxIds;
   }

   public String getShortFolderName(Context var1, boolean var2) {
      String var3;
      if(var2) {
         var3 = this.shortName;
      } else {
         if(this.mAccountId == Long.MAX_VALUE) {
            if(this.kind == Integer.MAX_VALUE) {
               var3 = var1.getString(2131362253);
               return var3;
            }

            if(this.kind == 2147483646) {
               var3 = var1.getString(2131362257);
               return var3;
            }

            if(this.kind == 2147483645) {
               var3 = var1.getString(2131362256);
               return var3;
            }

            if(this.kind == 2147483644) {
               var3 = var1.getString(2131362254);
               return var3;
            }

            if(this.kind == 2147483643) {
               var3 = var1.getString(2131362255);
               return var3;
            }
         } else {
            if(this.kind == Integer.MAX_VALUE) {
               var3 = var1.getString(2131362246);
               return var3;
            }

            if(this.kind == 2147483646) {
               var3 = var1.getString(2131362250);
               return var3;
            }

            if(this.kind == 2147483645) {
               var3 = var1.getString(2131362251);
               return var3;
            }

            if(this.kind == 2147483644) {
               var3 = var1.getString(2131362249);
               return var3;
            }

            if(this.kind == 2147483643) {
               var3 = var1.getString(2131362252);
               return var3;
            }
         }

         if(this.id == 9223372036854775802L) {
            var3 = var1.getString(2131362259);
         } else if(this.id == 9223372036854775801L) {
            var3 = var1.getString(2131362260);
         } else {
            var3 = this.shortName;
         }
      }

      return var3;
   }

   public boolean needShowNotification() {
      byte var1;
      if(this.id == 9223372036854775802L) {
         var1 = 1;
      } else if(this.id == 9223372036854775801L) {
         var1 = 1;
      } else {
         var1 = this.getDefaultSyncEnabled();
      }

      return (boolean)var1;
   }

   public void readFromParcel(Parcel var1) {
      long var2 = var1.readLong();
      this.id = var2;
      String var4 = var1.readString();
      this.unDecodeName = var4;
      String var5 = var1.readString();
      this.decodeName = var5;
      String var6 = var1.readString();
      this.shortName = var6;
      byte var7;
      if(var1.readByte() == 1) {
         var7 = 1;
      } else {
         var7 = 0;
      }

      this.isServerFolder = (boolean)var7;
      int var8 = var1.readInt();
      this.moveGroup = var8;
      byte var9;
      if(var1.readByte() == 1) {
         var9 = 1;
      } else {
         var9 = 0;
      }

      this.showSender = (boolean)var9;
      int var10 = var1.readInt();
      this.mHasChild = var10;
      int var11 = var1.readInt();
      this.mNoSelect = var11;
      long var12 = var1.readLong();
      this.mAccountId = var12;
      int var14 = var1.readInt();
      this.kind = var14;
      String var15 = var1.readString();
      this.type = var15;
      String var16 = var1.readString();
      this.serverId = var16;
      String var17 = var1.readString();
      this.parentId = var17;
      Object var18 = var1.readInt();
      if(var18 >= 0) {
         this.mCombinedMailboxIds = (long[])var18;
         long[] var20 = this.mCombinedMailboxIds;
         var1.readLongArray(var20);
      }
   }

   public void removeMailboxId(long var1) {
      this.mCombinedMailboxArray.delete(var1);
      this.mCombinedMailboxIds = null;
   }

   public void setDefaultSyncEnabled(boolean var1) {
      this.mDefaultSyncEnabled = var1;
   }

   public void writeToParcel(Parcel var1, int var2) {
      long var3 = this.id;
      var1.writeLong(var3);
      String var5 = this.unDecodeName;
      var1.writeString(var5);
      String var6 = this.decodeName;
      var1.writeString(var6);
      String var7 = this.shortName;
      var1.writeString(var7);
      byte var8;
      if(this.isServerFolder) {
         var8 = 1;
      } else {
         var8 = 0;
      }

      var1.writeByte(var8);
      int var9 = this.moveGroup;
      var1.writeInt(var9);
      byte var10;
      if(this.showSender) {
         var10 = 1;
      } else {
         var10 = 0;
      }

      var1.writeByte(var10);
      int var11 = this.mHasChild;
      var1.writeInt(var11);
      int var12 = this.mNoSelect;
      var1.writeInt(var12);
      long var13 = this.mAccountId;
      var1.writeLong(var13);
      int var15 = this.kind;
      var1.writeInt(var15);
      String var16 = this.type;
      var1.writeString(var16);
      String var17 = this.serverId;
      var1.writeString(var17);
      String var18 = this.parentId;
      var1.writeString(var18);
      if(this.mCombinedMailboxIds == null) {
         var1.writeInt(-1);
      } else {
         int var19 = this.mCombinedMailboxIds.length;
         var1.writeInt(var19);
         long[] var20 = this.mCombinedMailboxIds;
         var1.writeLongArray(var20);
      }
   }

   static class 1 implements Creator<Mailbox> {

      1() {}

      public Mailbox createFromParcel(Parcel var1) {
         return new Mailbox(var1, (Mailbox.1)null);
      }

      public Mailbox[] newArray(int var1) {
         return new Mailbox[var1];
      }
   }
}
