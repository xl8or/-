package com.htc.android.mail.pim;

import com.htc.android.mail.pim.PropertyNode;
import java.util.ArrayList;

public class VNode {

   public String VName;
   public int parseStatus;
   public ArrayList<PropertyNode> propList;


   public VNode() {
      ArrayList var1 = new ArrayList();
      this.propList = var1;
      this.parseStatus = 1;
   }
}
