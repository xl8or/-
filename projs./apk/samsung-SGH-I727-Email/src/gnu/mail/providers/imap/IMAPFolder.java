package gnu.mail.providers.imap;

import gnu.inet.imap.IMAPConnection;
import gnu.inet.imap.ListEntry;
import gnu.inet.imap.MailboxStatus;
import gnu.inet.imap.Quota;
import gnu.mail.providers.imap.ACL;
import gnu.mail.providers.imap.IMAPMessage;
import gnu.mail.providers.imap.IMAPStore;
import gnu.mail.providers.imap.Rights;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import javax.mail.Address;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.FolderNotFoundException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.MethodNotSupportedException;
import javax.mail.Store;
import javax.mail.UIDFolder;
import javax.mail.search.AddressTerm;
import javax.mail.search.AndTerm;
import javax.mail.search.BodyTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.DateTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.FromTerm;
import javax.mail.search.HeaderTerm;
import javax.mail.search.IntegerComparisonTerm;
import javax.mail.search.MessageIDTerm;
import javax.mail.search.NotTerm;
import javax.mail.search.OrTerm;
import javax.mail.search.RecipientTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SentDateTerm;
import javax.mail.search.SizeTerm;
import javax.mail.search.StringTerm;
import javax.mail.search.SubjectTerm;

public class IMAPFolder extends Folder implements UIDFolder {

   private static DateFormat searchdf = new SimpleDateFormat("d-MMM-yyyy");
   protected char delimiter;
   protected int messageCount;
   protected int newMessageCount;
   protected String path;
   protected Flags permanentFlags;
   protected boolean subscribed;
   protected int type;
   protected long uidValidity;


   protected IMAPFolder(Store var1, String var2) {
      this(var1, var2, -1, '\u0000');
   }

   protected IMAPFolder(Store var1, String var2, char var3) {
      this(var1, var2, -1, var3);
   }

   protected IMAPFolder(Store var1, String var2, int var3, char var4) {
      super(var1);
      Flags var5 = new Flags();
      this.permanentFlags = var5;
      this.messageCount = -1;
      this.newMessageCount = -1;
      this.uidValidity = 65535L;
      this.path = var2;
      this.type = var3;
      this.delimiter = var4;
   }

   private boolean addTerm(SearchTerm var1, List var2) {
      Flags.Flag var3 = null;
      SearchTerm[] var4;
      int var5;
      boolean var8;
      if(var1 instanceof AndTerm) {
         var4 = ((AndTerm)var1).getTerms();
         var5 = 0;

         while(true) {
            int var6 = var4.length;
            if(var5 >= var6) {
               break;
            }

            SearchTerm var7 = var4[var5];
            if(!this.addTerm(var7, var2)) {
               var8 = false;
               return var8;
            }

            ++var5;
         }
      } else if(var1 instanceof OrTerm) {
         boolean var9 = var2.add("OR");
         var4 = ((OrTerm)var1).getTerms();
         var5 = 0;

         while(true) {
            int var10 = var4.length;
            if(var5 >= var10) {
               break;
            }

            SearchTerm var11 = var4[var5];
            if(!this.addTerm(var11, var2)) {
               var8 = false;
               return var8;
            }

            ++var5;
         }
      } else if(var1 instanceof NotTerm) {
         boolean var12 = var2.add("NOT");
         SearchTerm var13 = ((NotTerm)var1).getTerm();
         if(!this.addTerm(var13, var2)) {
            var8 = false;
            return var8;
         }
      } else if(var1 instanceof FlagTerm) {
         FlagTerm var14 = (FlagTerm)var1;
         Flags var15 = var14.getFlags();
         boolean var111 = var14.getTestSet();
         Flags.Flag[] var16 = var15.getSystemFlags();
         int var17 = 0;

         label161:
         while(true) {
            int var18 = var16.length;
            if(var17 >= var18) {
               String[] var109 = var15.getUserFlags();
               int var31 = 0;

               while(true) {
                  int var32 = var109.length;
                  if(var31 >= var32) {
                     break label161;
                  }

                  StringBuffer var33 = new StringBuffer();
                  String var34;
                  if(var111) {
                     var34 = "KEYWORD";
                  } else {
                     var34 = "UNKEYWORD";
                  }

                  var33.append(var34);
                  StringBuffer var36 = var33.append('\"');
                  String var37 = var109[var31];
                  var33.append(var37);
                  StringBuffer var39 = var33.append('\"');
                  String var40 = var33.toString();
                  var2.add(var40);
                  ++var31;
               }
            }

            var3 = var16[var17];
            Flags.Flag var19 = Flags.Flag.ANSWERED;
            String var108;
            if(var3 == var19) {
               if(var111) {
                  var108 = "ANSWERED";
               } else {
                  var108 = "UNANSWERED";
               }

               var2.add(var108);
            } else {
               Flags.Flag var21 = Flags.Flag.DELETED;
               if(var3 == var21) {
                  if(var111) {
                     var108 = "DELETED";
                  } else {
                     var108 = "UNDELETED";
                  }

                  var2.add(var108);
               } else {
                  Flags.Flag var23 = Flags.Flag.DRAFT;
                  if(var3 == var23) {
                     if(var111) {
                        var108 = "DRAFT";
                     } else {
                        var108 = "UNDRAFT";
                     }

                     var2.add(var108);
                  } else {
                     Flags.Flag var25 = Flags.Flag.FLAGGED;
                     if(var3 == var25) {
                        if(var111) {
                           var108 = "FLAGGED";
                        } else {
                           var108 = "UNFLAGGED";
                        }

                        var2.add(var108);
                     } else {
                        Flags.Flag var27 = Flags.Flag.RECENT;
                        if(var3 == var27) {
                           if(var111) {
                              var108 = "RECENT";
                           } else {
                              var108 = "OLD";
                           }

                           var2.add(var108);
                        } else {
                           Flags.Flag var29 = Flags.Flag.SEEN;
                           if(var3 == var29) {
                              if(var111) {
                                 var108 = "SEEN";
                              } else {
                                 var108 = "UNSEEN";
                              }

                              var2.add(var108);
                           }
                        }
                     }
                  }
               }
            }

            ++var17;
         }
      } else {
         StringBuffer var110;
         if(var1 instanceof AddressTerm) {
            Address var42 = ((AddressTerm)var1).getAddress();
            var110 = new StringBuffer();
            if(var1 instanceof FromTerm) {
               StringBuffer var43 = var110.append("FROM");
            } else if(var1 instanceof RecipientTerm) {
               Message.RecipientType var113 = ((RecipientTerm)var1).getRecipientType();
               Message.RecipientType var51 = Message.RecipientType.TO;
               if(var113 == var51) {
                  StringBuffer var52 = var110.append("TO");
               } else {
                  Message.RecipientType var53 = Message.RecipientType.CC;
                  if(var113 == var53) {
                     StringBuffer var54 = var110.append("CC");
                  } else {
                     Message.RecipientType var55 = Message.RecipientType.BCC;
                     if(var113 == var55) {
                        StringBuffer var56 = var110.append("BCC");
                     } else {
                        var110 = null;
                     }
                  }
               }
            } else {
               var110 = null;
            }

            if(var110 == null) {
               var8 = false;
               return var8;
            }

            StringBuffer var44 = var110.append(' ');
            StringBuffer var45 = var110.append('\"');
            String var46 = var42.toString();
            var110.append(var46);
            StringBuffer var48 = var110.append('\"');
            String var49 = var110.toString();
            var2.add(var49);
         } else if(var1 instanceof ComparisonTerm) {
            StringBuffer var59;
            if(var1 instanceof DateTerm) {
               DateTerm var57 = (DateTerm)var1;
               Date var112 = var57.getDate();
               int var58 = var57.getComparison();
               var59 = new StringBuffer();
               switch(var58) {
               case 1:
               case 4:
               case 6:
                  StringBuffer var66 = var59.append("NOT");
                  StringBuffer var67 = var59.append(' ');
               case 2:
               case 3:
               case 5:
               default:
                  if(var1 instanceof SentDateTerm) {
                     StringBuffer var60 = var59.append("SENT");
                  }

                  switch(var58) {
                  case 1:
                  case 5:
                     StringBuffer var70 = var59.append("SINCE");
                     break;
                  case 2:
                  case 6:
                     StringBuffer var69 = var59.append("BEFORE");
                     break;
                  case 3:
                  case 4:
                     StringBuffer var68 = var59.append("ON");
                  }

                  StringBuffer var61 = var59.append(' ');
                  String var62 = searchdf.format(var112);
                  var59.append(var62);
                  String var64 = var59.toString();
                  var2.add(var64);
               }
            } else if(var1 instanceof IntegerComparisonTerm) {
               IntegerComparisonTerm var71 = (IntegerComparisonTerm)var1;
               var5 = var71.getNumber();
               int var72 = var71.getComparison();
               if(!(var1 instanceof SizeTerm)) {
                  var8 = false;
                  return var8;
               }

               var59 = new StringBuffer();
               switch(var72) {
               case 1:
               case 3:
               case 6:
                  StringBuffer var75 = var59.append("NOT");
                  StringBuffer var76 = var59.append(' ');
               case 2:
               case 4:
               case 5:
               default:
                  switch(var72) {
                  case 1:
                  case 5:
                     StringBuffer var89 = var59.append("LARGER");
                     StringBuffer var90 = var59.append(' ');
                     var59.append(var5);
                     break;
                  case 2:
                  case 6:
                     StringBuffer var86 = var59.append("SMALLER");
                     StringBuffer var87 = var59.append(' ');
                     var59.append(var5);
                     break;
                  case 3:
                  case 4:
                     StringBuffer var77 = var59.append("OR");
                     StringBuffer var78 = var59.append(' ');
                     StringBuffer var79 = var59.append("SMALLER");
                     StringBuffer var80 = var59.append(' ');
                     var59.append(var5);
                     StringBuffer var82 = var59.append(' ');
                     StringBuffer var83 = var59.append("LARGER");
                     StringBuffer var84 = var59.append(' ');
                     var59.append(var5);
                  }

                  String var73 = var59.toString();
                  var2.add(var73);
               }
            }
         } else {
            if(!(var1 instanceof StringTerm)) {
               var8 = false;
               return var8;
            }

            String var92 = ((StringTerm)var1).getPattern();
            var110 = new StringBuffer();
            if(var1 instanceof BodyTerm) {
               StringBuffer var93 = var110.append("BODY");
            } else if(var1 instanceof HeaderTerm) {
               StringBuffer var100 = var110.append("HEADER");
               StringBuffer var101 = var110.append(' ');
               String var102 = ((HeaderTerm)var1).getHeaderName();
               var110.append(var102);
            } else if(var1 instanceof SubjectTerm) {
               StringBuffer var104 = var110.append("SUBJECT");
            } else if(var1 instanceof MessageIDTerm) {
               StringBuffer var105 = var110.append("HEADER");
               StringBuffer var106 = var110.append(' ');
               StringBuffer var107 = var110.append("Message-ID");
            } else {
               var110 = null;
            }

            if(var110 == null) {
               var8 = false;
               return var8;
            }

            StringBuffer var94 = var110.append(' ');
            StringBuffer var95 = var110.append('\"');
            var110.append(var92);
            StringBuffer var97 = var110.append('\"');
            String var98 = var110.toString();
            var2.add(var98);
         }
      }

      var8 = true;
      return var8;
   }

   public void addACL(ACL var1) throws MessagingException {
      if(this.isOpen()) {
         IMAPConnection var2 = ((IMAPStore)this.store).getConnection();

         try {
            Rights var3 = var1.getRights();
            if(var3 != null) {
               String var4 = this.path;
               String var5 = var1.name;
               int var6 = var2.listrights(var4, var5);
               int var7 = var3.rights | var6;
               String var8 = this.path;
               String var9 = var1.name;
               if(!var2.setacl(var8, var9, var7)) {
                  throw new MessagingException("can\'t set ACL");
               }
            }
         } catch (IOException var12) {
            String var11 = var12.getMessage();
            throw new MessagingException(var11, var12);
         }
      }
   }

   public void addRights(ACL var1) throws MessagingException {
      this.addACL(var1);
   }

   public void appendMessages(Message[] param1) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public void close(boolean param1) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public boolean create(int param1) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public boolean delete(boolean param1) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public boolean equals(Object var1) {
      boolean var4;
      if(var1 instanceof IMAPFolder) {
         String var2 = ((IMAPFolder)var1).path;
         String var3 = this.path;
         var4 = var2.equals(var3);
      } else {
         var4 = super.equals(var1);
      }

      return var4;
   }

   public boolean exists() throws MessagingException {
      boolean var2;
      try {
         int var1 = this.getType();
      } catch (FolderNotFoundException var4) {
         var2 = false;
         return var2;
      }

      var2 = true;
      return var2;
   }

   public Message[] expunge() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public void fetch(Message[] param1, FetchProfile param2) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public ACL[] getACL() throws MessagingException {
      ACL[] var1;
      if(!this.isOpen()) {
         var1 = null;
      } else {
         IMAPConnection var2 = ((IMAPStore)this.store).getConnection();
         ArrayList var3 = new ArrayList();

         try {
            String var4 = this.path;

            ACL var8;
            for(Iterator var5 = var2.getacl(var4).entrySet().iterator(); var5.hasNext(); var3.add(var8)) {
               Entry var6 = (Entry)var5.next();
               String var7 = (String)var6.getKey();
               Integer var17 = (Integer)var6.getValue();
               var8 = new ACL(var7);
               if(var17 != null) {
                  Rights var9 = new Rights();
                  var8.rights = var9;
                  Rights var10 = var8.rights;
                  int var11 = var17.intValue();
                  var10.rights = var11;
               }
            }
         } catch (IOException var16) {
            String var14 = var16.getMessage();
            throw new MessagingException(var14, var16);
         }

         var1 = new ACL[var3.size()];
         var3.toArray(var1);
      }

      return var1;
   }

   public int getDeletedMessageCount() throws MessagingException {
      synchronized(this){}
      boolean var5 = false;

      int var1;
      try {
         var5 = true;
         var1 = this.getMessageCountByCriteria("DELETED");
         var5 = false;
      } finally {
         if(var5) {
            ;
         }
      }

      return var1;
   }

   public Folder getFolder(String var1) throws MessagingException {
      StringBuffer var2 = new StringBuffer();
      if(this.path != null && this.path.length() > 0) {
         String var3 = this.path;
         var2.append(var3);
         char var5 = this.delimiter;
         var2.append(var5);
      }

      var2.append(var1);
      Store var8 = this.store;
      String var9 = var2.toString();
      char var10 = this.getSeparator();
      return new IMAPFolder(var8, var9, -1, var10);
   }

   Folder[] getFolders(ListEntry[] var1, boolean var2) throws MessagingException {
      int var3 = var1.length;
      ArrayList var4 = new ArrayList(var3);
      int var5 = 0;

      while(true) {
         int var6 = var1.length;
         if(var5 >= var6) {
            Folder[] var14 = new Folder[var4.size()];
            var4.toArray(var14);
            return var14;
         }

         ListEntry var7 = var1[var5];
         byte var8;
         if(var7.isNoinferiors()) {
            var8 = 1;
         } else {
            var8 = 2;
         }

         if(!var7.isNoselect()) {
            Store var9 = this.store;
            String var10 = var7.getMailbox();
            char var11 = var7.getDelimiter();
            IMAPFolder var12 = new IMAPFolder(var9, var10, var8, var11);
            if(!var4.contains(var12)) {
               var4.add(var12);
               var12.subscribed = var2;
            }
         }

         ++var5;
      }
   }

   public String getFullName() {
      return this.path;
   }

   public Message getMessage(int var1) throws MessagingException {
      if(this.mode == -1) {
         throw new FolderClosedException(this);
      } else {
         return new IMAPMessage(this, var1);
      }
   }

   public Message getMessageByUID(long param1) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public int getMessageCount() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public int getMessageCountByCriteria(String var1) throws MessagingException {
      int var2;
      if(!this.isOpen()) {
         var2 = -1;
      } else {
         IMAPConnection var3 = ((IMAPStore)this.store).getConnection();
         String[] var4 = new String[]{var1};
         Object var5 = null;

         int[] var6;
         try {
            var6 = var3.search((String)var5, var4);
         } catch (IOException var9) {
            String var8 = var9.getMessage();
            throw new MessagingException(var8, var9);
         }

         var2 = var6.length;
      }

      return var2;
   }

   public Message[] getMessagesByUID(long param1, long param3) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public Message[] getMessagesByUID(long[] param1) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public String getName() {
      String var1 = this.path;
      char var2 = this.delimiter;
      int var3 = var1.lastIndexOf(var2);
      String var6;
      if(var3 == -1) {
         var6 = this.path;
      } else {
         String var4 = this.path;
         int var5 = var3 + 1;
         var6 = var4.substring(var5);
      }

      return var6;
   }

   public int getNewMessageCount() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public Folder getParent() throws MessagingException {
      IMAPStore var1 = (IMAPStore)this.store;
      IMAPConnection var2 = var1.getConnection();
      char var3 = this.getSeparator();
      String var4 = this.path;
      char var5 = this.delimiter;
      int var6 = var4.lastIndexOf(var5);
      Object var10;
      if(var6 == -1) {
         var10 = var1.getDefaultFolder();
      } else {
         Store var7 = this.store;
         String var8 = this.path.substring(0, var6);
         char var9 = this.delimiter;
         var10 = new IMAPFolder(var7, var8, var9);
      }

      return (Folder)var10;
   }

   public Flags getPermanentFlags() {
      return this.permanentFlags;
   }

   public Quota[] getQuota() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public char getSeparator() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public int getType() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public long getUID(Message var1) throws MessagingException {
      if(this.mode == -1) {
         throw new FolderClosedException(this);
      } else if(!(var1 instanceof IMAPMessage)) {
         throw new MethodNotSupportedException("not an IMAPMessage");
      } else {
         IMAPMessage var2 = (IMAPMessage)var1;
         if(var2.uid == 65535L) {
            var2.fetchUID();
         }

         return var2.uid;
      }
   }

   public long getUIDValidity() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public int getUnreadMessageCount() throws MessagingException {
      synchronized(this){}
      boolean var5 = false;

      int var1;
      try {
         var5 = true;
         var1 = this.getMessageCountByCriteria("NOT SEEN");
         var5 = false;
      } finally {
         if(var5) {
            ;
         }
      }

      return var1;
   }

   public boolean hasNewMessages() throws MessagingException {
      boolean var1;
      if(this.getNewMessageCount() > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isOpen() {
      boolean var1;
      if(this.mode != -1) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isSubscribed() {
      return this.subscribed;
   }

   public Folder[] list(String param1) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public Rights listRights(String var1) throws MessagingException {
      Rights var2;
      if(!this.isOpen()) {
         var2 = null;
      } else {
         IMAPConnection var3 = ((IMAPStore)this.store).getConnection();

         Rights var4;
         try {
            var4 = new Rights();
            String var5 = this.path;
            int var6 = var3.listrights(var5, var1);
            var4.rights = var6;
         } catch (IOException var9) {
            String var8 = var9.getMessage();
            throw new MessagingException(var8, var9);
         }

         var2 = var4;
      }

      return var2;
   }

   public Folder[] listSubscribed(String param1) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public Rights myRights() throws MessagingException {
      Rights var1;
      if(!this.isOpen()) {
         var1 = null;
      } else {
         IMAPConnection var2 = ((IMAPStore)this.store).getConnection();

         Rights var3;
         try {
            var3 = new Rights();
            String var4 = this.path;
            int var5 = var2.myrights(var4);
            var3.rights = var5;
         } catch (IOException var8) {
            String var7 = var8.getMessage();
            throw new MessagingException(var7, var8);
         }

         var1 = var3;
      }

      return var1;
   }

   public void open(int param1) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   Flags readFlags(List var1) {
      Flags var2 = new Flags();
      int var3 = var1.size();

      for(int var4 = 0; var4 < var3; ++var4) {
         String var11 = (String)var1.get(var4);
         if(var11 == "\\Answered") {
            Flags.Flag var5 = Flags.Flag.ANSWERED;
            var2.add(var5);
         } else if(var11 == "\\Deleted") {
            Flags.Flag var6 = Flags.Flag.DELETED;
            var2.add(var6);
         } else if(var11 == "\\Draft") {
            Flags.Flag var7 = Flags.Flag.DRAFT;
            var2.add(var7);
         } else if(var11 == "\\Flagged") {
            Flags.Flag var8 = Flags.Flag.FLAGGED;
            var2.add(var8);
         } else if(var11 == "\\Recent") {
            Flags.Flag var9 = Flags.Flag.RECENT;
            var2.add(var9);
         } else if(var11 == "\\Seen") {
            Flags.Flag var10 = Flags.Flag.SEEN;
            var2.add(var10);
         }
      }

      return var2;
   }

   public void removeACL(String var1) throws MessagingException {
      if(this.isOpen()) {
         IMAPConnection var2 = ((IMAPStore)this.store).getConnection();

         try {
            String var3 = this.path;
            if(!var2.deleteacl(var3, var1)) {
               throw new MessagingException("can\'t delete ACL");
            }
         } catch (IOException var6) {
            String var5 = var6.getMessage();
            throw new MessagingException(var5, var6);
         }
      }
   }

   public void removeRights(ACL var1) throws MessagingException {
      if(this.isOpen()) {
         IMAPConnection var2 = ((IMAPStore)this.store).getConnection();

         try {
            Rights var3 = var1.getRights();
            if(var3 != null) {
               String var4 = this.path;
               String var5 = var1.name;
               int var6 = var2.listrights(var4, var5);
               int var7 = var3.rights;
               int var8 = var6 - var7;
               String var9 = this.path;
               String var10 = var1.name;
               if(!var2.setacl(var9, var10, var8)) {
                  throw new MessagingException("can\'t set ACL");
               }
            }
         } catch (IOException var13) {
            String var12 = var13.getMessage();
            throw new MessagingException(var12, var13);
         }
      }
   }

   public boolean renameTo(Folder param1) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public Message[] search(SearchTerm var1) throws MessagingException {
      return this.search(var1, (Message[])null);
   }

   public Message[] search(SearchTerm param1, Message[] param2) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public void setSubscribed(boolean param1) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   void update(MailboxStatus var1, boolean var2) throws MessagingException {
      if(var1 == null) {
         throw new FolderNotFoundException(this);
      } else {
         byte var3;
         if(var1.readWrite) {
            var3 = 2;
         } else {
            var3 = 1;
         }

         this.mode = var3;
         if(var1.permanentFlags != null) {
            List var4 = var1.permanentFlags;
            Flags var5 = this.readFlags(var4);
            this.permanentFlags = var5;
         }

         int var6 = this.messageCount;
         int var7 = var1.messageCount;
         this.messageCount = var7;
         int var8 = var1.newMessageCount;
         this.newMessageCount = var8;
         long var9 = (long)var1.uidValidity;
         this.uidValidity = var9;
         if(var2) {
            Message[] var11;
            int var12;
            if(this.messageCount > var6) {
               var11 = new Message[this.messageCount - var6];
               var12 = var6;

               while(true) {
                  int var13 = this.messageCount;
                  if(var12 >= var13) {
                     this.notifyMessageAddedListeners(var11);
                     return;
                  }

                  int var14 = var12 - var6;
                  Message var15 = this.getMessage(var12);
                  var11[var14] = var15;
                  ++var12;
               }
            } else if(this.messageCount < var6) {
               int var16 = this.messageCount;
               var11 = new Message[var6 - var16];

               int var20;
               for(var12 = this.messageCount; var12 < var6; var20 = var12 + 1) {
                  int var17 = this.messageCount;
                  int var18 = var12 - var17;
                  Message var19 = this.getMessage(var12);
                  var11[var18] = var19;
               }

               this.notifyMessageRemovedListeners((boolean)0, var11);
            }
         }
      }
   }
}
