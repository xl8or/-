package org.apache.james.mime4j.field.address.parser;

import java.io.PrintStream;
import org.apache.james.mime4j.field.address.parser.AddressListParser;
import org.apache.james.mime4j.field.address.parser.AddressListParserTreeConstants;
import org.apache.james.mime4j.field.address.parser.AddressListParserVisitor;
import org.apache.james.mime4j.field.address.parser.BaseNode;
import org.apache.james.mime4j.field.address.parser.Node;

public class SimpleNode extends BaseNode implements Node {

   protected Node[] children;
   protected int id;
   protected Node parent;
   protected AddressListParser parser;


   public SimpleNode(int var1) {
      this.id = var1;
   }

   public SimpleNode(AddressListParser var1, int var2) {
      this(var2);
      this.parser = var1;
   }

   public Object childrenAccept(AddressListParserVisitor var1, Object var2) {
      if(this.children != null) {
         int var3 = 0;

         while(true) {
            int var4 = this.children.length;
            if(var3 >= var4) {
               break;
            }

            this.children[var3].jjtAccept(var1, var2);
            ++var3;
         }
      }

      return var2;
   }

   public void dump(String var1) {
      PrintStream var2 = System.out;
      String var3 = this.toString(var1);
      var2.println(var3);
      StringBuffer var4 = new StringBuffer();
      if(this.children != null) {
         int var5 = 0;

         while(true) {
            int var6 = this.children.length;
            if(var5 >= var6) {
               return;
            }

            SimpleNode var7 = (SimpleNode)this.children[var5];
            if(var7 != null) {
               StringBuffer var8 = var4.append(var1).append(" ");
               String var9 = var4.toString();
               var7.dump(var9);
               int var10 = var4.length();
               var4.delete(0, var10);
            }

            ++var5;
         }
      }
   }

   public Object jjtAccept(AddressListParserVisitor var1, Object var2) {
      return var1.visit(this, var2);
   }

   public void jjtAddChild(Node var1, int var2) {
      if(this.children == null) {
         Node[] var3 = new Node[var2 + 1];
         this.children = var3;
      } else {
         int var4 = this.children.length;
         if(var2 >= var4) {
            Node[] var5 = new Node[var2 + 1];
            Node[] var6 = this.children;
            int var7 = this.children.length;
            System.arraycopy(var6, 0, var5, 0, var7);
            this.children = var5;
         }
      }

      this.children[var2] = var1;
   }

   public void jjtClose() {}

   public Node jjtGetChild(int var1) {
      return this.children[var1];
   }

   public int jjtGetNumChildren() {
      int var1;
      if(this.children == null) {
         var1 = 0;
      } else {
         var1 = this.children.length;
      }

      return var1;
   }

   public Node jjtGetParent() {
      return this.parent;
   }

   public void jjtOpen() {}

   public void jjtSetParent(Node var1) {
      this.parent = var1;
   }

   public String toString() {
      String[] var1 = AddressListParserTreeConstants.jjtNodeName;
      int var2 = this.id;
      return var1[var2];
   }

   public String toString(String var1) {
      StringBuilder var2 = (new StringBuilder()).append(var1);
      String var3 = this.toString();
      return var2.append(var3).toString();
   }
}
