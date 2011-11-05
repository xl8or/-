package org.jivesoftware.smack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.RosterPacket;
import org.jivesoftware.smack.util.StringUtils;

public class RosterGroup {

   private Connection connection;
   private final List<RosterEntry> entries;
   private String name;


   RosterGroup(String var1, Connection var2) {
      this.name = var1;
      this.connection = var2;
      ArrayList var3 = new ArrayList();
      this.entries = var3;
   }

   public void addEntry(RosterEntry param1) throws XMPPException {
      // $FF: Couldn't be decompiled
   }

   public void addEntryLocal(RosterEntry var1) {
      List var2 = this.entries;
      synchronized(var2) {
         this.entries.remove(var1);
         this.entries.add(var1);
      }
   }

   public boolean contains(String var1) {
      boolean var2;
      if(this.getEntry(var1) != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean contains(RosterEntry var1) {
      List var2 = this.entries;
      synchronized(var2) {
         boolean var3 = this.entries.contains(var1);
         return var3;
      }
   }

   public Collection<RosterEntry> getEntries() {
      List var1 = this.entries;
      synchronized(var1) {
         List var2 = this.entries;
         List var3 = Collections.unmodifiableList(new ArrayList(var2));
         return var3;
      }
   }

   public RosterEntry getEntry(String var1) {
      RosterEntry var2;
      if(var1 == null) {
         var2 = null;
      } else {
         String var3 = StringUtils.parseBareAddress(var1).toLowerCase();
         List var4 = this.entries;
         synchronized(var4) {
            Iterator var5 = this.entries.iterator();

            while(true) {
               if(var5.hasNext()) {
                  RosterEntry var8 = (RosterEntry)var5.next();
                  if(!var8.getUser().equals(var3)) {
                     continue;
                  }

                  var2 = var8;
                  break;
               }

               var2 = null;
               break;
            }
         }
      }

      return var2;
   }

   public int getEntryCount() {
      List var1 = this.entries;
      synchronized(var1) {
         int var2 = this.entries.size();
         return var2;
      }
   }

   public String getName() {
      return this.name;
   }

   public void removeEntry(RosterEntry param1) throws XMPPException {
      // $FF: Couldn't be decompiled
   }

   void removeEntryLocal(RosterEntry var1) {
      List var2 = this.entries;
      synchronized(var2) {
         if(this.entries.contains(var1)) {
            this.entries.remove(var1);
         }

      }
   }

   public void setName(String var1) {
      List var2 = this.entries;
      synchronized(var2) {
         Iterator var3 = this.entries.iterator();

         while(var3.hasNext()) {
            RosterEntry var4 = (RosterEntry)var3.next();
            RosterPacket var5 = new RosterPacket();
            IQ.Type var6 = IQ.Type.SET;
            var5.setType(var6);
            RosterPacket.Item var7 = RosterEntry.toRosterItem(var4);
            String var8 = this.name;
            var7.removeGroupName(var8);
            var7.addGroupName(var1);
            var5.addRosterItem(var7);
            this.connection.sendPacket(var5);
         }

      }
   }
}
