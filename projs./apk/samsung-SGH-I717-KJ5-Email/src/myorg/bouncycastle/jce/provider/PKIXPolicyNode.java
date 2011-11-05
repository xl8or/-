package myorg.bouncycastle.jce.provider;

import java.security.cert.PolicyNode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class PKIXPolicyNode implements PolicyNode {

   protected List children;
   protected boolean critical;
   protected int depth;
   protected Set expectedPolicies;
   protected PolicyNode parent;
   protected Set policyQualifiers;
   protected String validPolicy;


   public PKIXPolicyNode(List var1, int var2, Set var3, PolicyNode var4, Set var5, String var6, boolean var7) {
      this.children = var1;
      this.depth = var2;
      this.expectedPolicies = var3;
      this.parent = var4;
      this.policyQualifiers = var5;
      this.validPolicy = var6;
      this.critical = var7;
   }

   public void addChild(PKIXPolicyNode var1) {
      this.children.add(var1);
      var1.setParent(this);
   }

   public Object clone() {
      return this.copy();
   }

   public PKIXPolicyNode copy() {
      HashSet var1 = new HashSet();
      Iterator var2 = this.expectedPolicies.iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         String var4 = new String(var3);
         var1.add(var4);
      }

      HashSet var6 = new HashSet();
      Iterator var7 = this.policyQualifiers.iterator();

      while(var7.hasNext()) {
         String var8 = (String)var7.next();
         String var9 = new String(var8);
         var6.add(var9);
      }

      ArrayList var11 = new ArrayList();
      int var12 = this.depth;
      String var13 = this.validPolicy;
      String var14 = new String(var13);
      boolean var15 = this.critical;
      PKIXPolicyNode var16 = new PKIXPolicyNode(var11, var12, var1, (PolicyNode)null, var6, var14, var15);
      Iterator var17 = this.children.iterator();

      while(var17.hasNext()) {
         PKIXPolicyNode var18 = ((PKIXPolicyNode)var17.next()).copy();
         var18.setParent(var16);
         var16.addChild(var18);
      }

      return var16;
   }

   public Iterator getChildren() {
      return this.children.iterator();
   }

   public int getDepth() {
      return this.depth;
   }

   public Set getExpectedPolicies() {
      return this.expectedPolicies;
   }

   public PolicyNode getParent() {
      return this.parent;
   }

   public Set getPolicyQualifiers() {
      return this.policyQualifiers;
   }

   public String getValidPolicy() {
      return this.validPolicy;
   }

   public boolean hasChildren() {
      boolean var1;
      if(!this.children.isEmpty()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isCritical() {
      return this.critical;
   }

   public void removeChild(PKIXPolicyNode var1) {
      this.children.remove(var1);
   }

   public void setCritical(boolean var1) {
      this.critical = var1;
   }

   public void setParent(PKIXPolicyNode var1) {
      this.parent = var1;
   }

   public String toString() {
      return this.toString("");
   }

   public String toString(String var1) {
      StringBuffer var2 = new StringBuffer();
      var2.append(var1);
      String var4 = this.validPolicy;
      var2.append(var4);
      StringBuffer var6 = var2.append(" {\n");
      int var7 = 0;

      while(true) {
         int var8 = this.children.size();
         if(var7 >= var8) {
            var2.append(var1);
            StringBuffer var14 = var2.append("}\n");
            return var2.toString();
         }

         PKIXPolicyNode var9 = (PKIXPolicyNode)this.children.get(var7);
         String var10 = var1 + "    ";
         String var11 = var9.toString(var10);
         var2.append(var11);
         ++var7;
      }
   }
}
