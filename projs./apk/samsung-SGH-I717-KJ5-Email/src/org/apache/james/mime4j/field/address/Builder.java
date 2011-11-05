package org.apache.james.mime4j.field.address;

import java.util.ArrayList;
import java.util.Iterator;
import org.apache.james.mime4j.decoder.DecoderUtil;
import org.apache.james.mime4j.field.address.Address;
import org.apache.james.mime4j.field.address.AddressList;
import org.apache.james.mime4j.field.address.DomainList;
import org.apache.james.mime4j.field.address.Group;
import org.apache.james.mime4j.field.address.Mailbox;
import org.apache.james.mime4j.field.address.MailboxList;
import org.apache.james.mime4j.field.address.NamedMailbox;
import org.apache.james.mime4j.field.address.parser.ASTaddr_spec;
import org.apache.james.mime4j.field.address.parser.ASTaddress;
import org.apache.james.mime4j.field.address.parser.ASTaddress_list;
import org.apache.james.mime4j.field.address.parser.ASTangle_addr;
import org.apache.james.mime4j.field.address.parser.ASTdomain;
import org.apache.james.mime4j.field.address.parser.ASTgroup_body;
import org.apache.james.mime4j.field.address.parser.ASTlocal_part;
import org.apache.james.mime4j.field.address.parser.ASTmailbox;
import org.apache.james.mime4j.field.address.parser.ASTname_addr;
import org.apache.james.mime4j.field.address.parser.ASTphrase;
import org.apache.james.mime4j.field.address.parser.ASTroute;
import org.apache.james.mime4j.field.address.parser.Node;
import org.apache.james.mime4j.field.address.parser.SimpleNode;
import org.apache.james.mime4j.field.address.parser.Token;

class Builder {

   private static Builder singleton = new Builder();


   Builder() {}

   private void addSpecials(StringBuffer var1, Token var2) {
      if(var2 != null) {
         Token var3 = var2.specialToken;
         this.addSpecials(var1, var3);
         String var4 = var2.image;
         var1.append(var4);
      }
   }

   private Mailbox buildAddrSpec(DomainList var1, ASTaddr_spec var2) {
      Builder.ChildNodeIterator var3 = new Builder.ChildNodeIterator(var2);
      ASTlocal_part var4 = (ASTlocal_part)var3.nextNode();
      String var5 = this.buildString(var4, (boolean)1);
      ASTdomain var6 = (ASTdomain)var3.nextNode();
      String var7 = this.buildString(var6, (boolean)1);
      return new Mailbox(var1, var5, var7);
   }

   private Mailbox buildAddrSpec(ASTaddr_spec var1) {
      return this.buildAddrSpec((DomainList)null, var1);
   }

   private Address buildAddress(ASTaddress var1) {
      Builder.ChildNodeIterator var2 = new Builder.ChildNodeIterator(var1);
      Node var3 = var2.nextNode();
      Object var5;
      if(var3 instanceof ASTaddr_spec) {
         ASTaddr_spec var4 = (ASTaddr_spec)var3;
         var5 = this.buildAddrSpec(var4);
      } else if(var3 instanceof ASTangle_addr) {
         ASTangle_addr var6 = (ASTangle_addr)var3;
         var5 = this.buildAngleAddr(var6);
      } else {
         if(!(var3 instanceof ASTphrase)) {
            throw new IllegalStateException();
         }

         ASTphrase var7 = (ASTphrase)var3;
         String var8 = this.buildString(var7, (boolean)0);
         Node var9 = var2.nextNode();
         if(var9 instanceof ASTgroup_body) {
            ASTgroup_body var10 = (ASTgroup_body)var9;
            MailboxList var11 = this.buildGroupBody(var10);
            var5 = new Group(var8, var11);
         } else {
            if(!(var9 instanceof ASTangle_addr)) {
               throw new IllegalStateException();
            }

            String var12 = DecoderUtil.decodeEncodedWords(var8);
            ASTangle_addr var13 = (ASTangle_addr)var9;
            Mailbox var14 = this.buildAngleAddr(var13);
            var5 = new NamedMailbox(var12, var14);
         }
      }

      return (Address)var5;
   }

   private Mailbox buildAngleAddr(ASTangle_addr var1) {
      Builder.ChildNodeIterator var2 = new Builder.ChildNodeIterator(var1);
      DomainList var3 = null;
      Node var4 = var2.nextNode();
      if(var4 instanceof ASTroute) {
         ASTroute var5 = (ASTroute)var4;
         var3 = this.buildRoute(var5);
         var4 = var2.nextNode();
      } else if(!(var4 instanceof ASTaddr_spec)) {
         throw new IllegalStateException();
      }

      if(var4 instanceof ASTaddr_spec) {
         ASTaddr_spec var6 = (ASTaddr_spec)var4;
         return this.buildAddrSpec(var3, var6);
      } else {
         throw new IllegalStateException();
      }
   }

   private MailboxList buildGroupBody(ASTgroup_body var1) {
      ArrayList var2 = new ArrayList();
      Builder.ChildNodeIterator var3 = new Builder.ChildNodeIterator(var1);

      while(var3.hasNext()) {
         Node var4 = var3.nextNode();
         if(!(var4 instanceof ASTmailbox)) {
            throw new IllegalStateException();
         }

         ASTmailbox var5 = (ASTmailbox)var4;
         Mailbox var6 = this.buildMailbox(var5);
         var2.add(var6);
      }

      return new MailboxList(var2, (boolean)1);
   }

   private Mailbox buildMailbox(ASTmailbox var1) {
      Node var2 = (new Builder.ChildNodeIterator(var1)).nextNode();
      Object var4;
      if(var2 instanceof ASTaddr_spec) {
         ASTaddr_spec var3 = (ASTaddr_spec)var2;
         var4 = this.buildAddrSpec(var3);
      } else if(var2 instanceof ASTangle_addr) {
         ASTangle_addr var5 = (ASTangle_addr)var2;
         var4 = this.buildAngleAddr(var5);
      } else {
         if(!(var2 instanceof ASTname_addr)) {
            throw new IllegalStateException();
         }

         ASTname_addr var6 = (ASTname_addr)var2;
         var4 = this.buildNameAddr(var6);
      }

      return (Mailbox)var4;
   }

   private NamedMailbox buildNameAddr(ASTname_addr var1) {
      Builder.ChildNodeIterator var2 = new Builder.ChildNodeIterator(var1);
      Node var3 = var2.nextNode();
      if(var3 instanceof ASTphrase) {
         ASTphrase var4 = (ASTphrase)var3;
         String var5 = this.buildString(var4, (boolean)0);
         Node var6 = var2.nextNode();
         if(var6 instanceof ASTangle_addr) {
            String var7 = DecoderUtil.decodeEncodedWords(var5);
            ASTangle_addr var8 = (ASTangle_addr)var6;
            Mailbox var9 = this.buildAngleAddr(var8);
            return new NamedMailbox(var7, var9);
         } else {
            throw new IllegalStateException();
         }
      } else {
         throw new IllegalStateException();
      }
   }

   private DomainList buildRoute(ASTroute var1) {
      int var2 = var1.jjtGetNumChildren();
      ArrayList var3 = new ArrayList(var2);
      Builder.ChildNodeIterator var4 = new Builder.ChildNodeIterator(var1);

      while(var4.hasNext()) {
         Node var5 = var4.nextNode();
         if(!(var5 instanceof ASTdomain)) {
            throw new IllegalStateException();
         }

         ASTdomain var6 = (ASTdomain)var5;
         String var7 = this.buildString(var6, (boolean)1);
         var3.add(var7);
      }

      return new DomainList(var3, (boolean)1);
   }

   private String buildString(SimpleNode var1, boolean var2) {
      Token var3 = var1.firstToken;
      Token var4 = var1.lastToken;
      StringBuffer var5 = new StringBuffer();

      while(var3 != var4) {
         String var6 = var3.image;
         var5.append(var6);
         var3 = var3.next;
         if(!var2) {
            Token var8 = var3.specialToken;
            this.addSpecials(var5, var8);
         }
      }

      String var9 = var4.image;
      var5.append(var9);
      return var5.toString();
   }

   public static Builder getInstance() {
      return singleton;
   }

   public AddressList buildAddressList(ASTaddress_list var1) {
      ArrayList var2 = new ArrayList();
      int var3 = 0;

      while(true) {
         int var4 = var1.jjtGetNumChildren();
         if(var3 >= var4) {
            return new AddressList(var2, (boolean)1);
         }

         ASTaddress var5 = (ASTaddress)var1.jjtGetChild(var3);
         Address var6 = this.buildAddress(var5);
         var2.add(var6);
         ++var3;
      }
   }

   private static class ChildNodeIterator implements Iterator {

      private int index;
      private int len;
      private SimpleNode simpleNode;


      public ChildNodeIterator(SimpleNode var1) {
         this.simpleNode = var1;
         int var2 = var1.jjtGetNumChildren();
         this.len = var2;
         this.index = 0;
      }

      public boolean hasNext() {
         int var1 = this.index;
         int var2 = this.len;
         boolean var3;
         if(var1 < var2) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }

      public Object next() {
         return this.nextNode();
      }

      public Node nextNode() {
         SimpleNode var1 = this.simpleNode;
         int var2 = this.index;
         int var3 = var2 + 1;
         this.index = var3;
         return var1.jjtGetChild(var2);
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }
}
