package com.facebook.katana.util.jsonmirror.types;

import com.facebook.katana.util.jsonmirror.JMFatalException;
import com.facebook.katana.util.jsonmirror.types.JMBase;
import com.facebook.katana.util.jsonmirror.types.JMDict;
import com.facebook.katana.util.jsonmirror.types.JMSimpleDict;
import com.facebook.katana.util.jsonmirror.types.JMString;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

public class JMList extends JMBase {

   public final int mMaxCount;
   public final int mMinCount;
   protected final Set<JMBase> mObjectTypes;


   public JMList(Set<JMBase> var1) {
      this(var1, 0, 0);
   }

   public JMList(Set<JMBase> var1, int var2, int var3) {
      int var4 = 0;
      int var5 = 0;
      int var6 = 0;
      Iterator var7 = var1.iterator();

      while(var7.hasNext()) {
         JMBase var8 = (JMBase)var7.next();
         if(var8 instanceof JMList) {
            ++var5;
         } else if(!(var8 instanceof JMDict) && !(var8 instanceof JMSimpleDict)) {
            if(var8 instanceof JMString) {
               ++var4;
            }
         } else {
            ++var6;
         }
      }

      if(var5 > 1) {
         throw new JMFatalException("We don\'t handle multiple types of child lists in the same list.");
      } else if(var6 > 1) {
         throw new JMFatalException("We don\'t handle multiple types of child dictionaries in the same list.");
      } else if(var4 > 1) {
         throw new JMFatalException("We don\'t handle multiple types of strings in the same list.");
      } else {
         this.mObjectTypes = var1;
         this.mMinCount = var2;
         this.mMaxCount = var3;
      }
   }

   public Set<JMBase> getObjectTypes() {
      return Collections.unmodifiableSet(this.mObjectTypes);
   }
}
