package org.apache.james.mime4j.field.address.parser;

import java.util.Stack;
import org.apache.james.mime4j.field.address.parser.Node;

class JJTAddressListParserState {

   private Stack marks;
   private int mk;
   private boolean node_created;
   private Stack nodes;
   private int sp;


   JJTAddressListParserState() {
      Stack var1 = new Stack();
      this.nodes = var1;
      Stack var2 = new Stack();
      this.marks = var2;
      this.sp = 0;
      this.mk = 0;
   }

   void clearNodeScope(Node var1) {
      while(true) {
         int var2 = this.sp;
         int var3 = this.mk;
         if(var2 <= var3) {
            int var5 = ((Integer)this.marks.pop()).intValue();
            this.mk = var5;
            return;
         }

         Node var4 = this.popNode();
      }
   }

   void closeNodeScope(Node var1, int var2) {
      int var3 = ((Integer)this.marks.pop()).intValue();
      this.mk = var3;
      int var4 = var2;

      while(true) {
         var2 = var4 + -1;
         if(var4 <= 0) {
            var1.jjtClose();
            this.pushNode(var1);
            this.node_created = (boolean)1;
            return;
         }

         Node var5 = this.popNode();
         var5.jjtSetParent(var1);
         var1.jjtAddChild(var5, var2);
         var4 = var2;
      }
   }

   void closeNodeScope(Node var1, boolean var2) {
      if(!var2) {
         int var8 = ((Integer)this.marks.pop()).intValue();
         this.mk = var8;
         this.node_created = (boolean)0;
      } else {
         int var3 = this.nodeArity();
         int var4 = ((Integer)this.marks.pop()).intValue();
         this.mk = var4;
         int var5 = var3;

         while(true) {
            int var6 = var5 + -1;
            if(var5 <= 0) {
               var1.jjtClose();
               this.pushNode(var1);
               this.node_created = (boolean)1;
               return;
            }

            Node var7 = this.popNode();
            var7.jjtSetParent(var1);
            var1.jjtAddChild(var7, var6);
            var5 = var6;
         }
      }
   }

   int nodeArity() {
      int var1 = this.sp;
      int var2 = this.mk;
      return var1 - var2;
   }

   boolean nodeCreated() {
      return this.node_created;
   }

   void openNodeScope(Node var1) {
      Stack var2 = this.marks;
      Integer var3 = Integer.valueOf(this.mk);
      var2.push(var3);
      int var5 = this.sp;
      this.mk = var5;
      var1.jjtOpen();
   }

   Node peekNode() {
      return (Node)this.nodes.peek();
   }

   Node popNode() {
      int var1 = this.sp - 1;
      this.sp = var1;
      int var2 = this.mk;
      if(var1 < var2) {
         int var3 = ((Integer)this.marks.pop()).intValue();
         this.mk = var3;
      }

      return (Node)this.nodes.pop();
   }

   void pushNode(Node var1) {
      this.nodes.push(var1);
      int var3 = this.sp + 1;
      this.sp = var3;
   }

   void reset() {
      this.nodes.removeAllElements();
      this.marks.removeAllElements();
      this.sp = 0;
      this.mk = 0;
   }

   Node rootNode() {
      return (Node)this.nodes.elementAt(0);
   }
}
