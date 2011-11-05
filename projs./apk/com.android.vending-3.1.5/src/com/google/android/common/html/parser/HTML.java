package com.google.android.common.html.parser;

import com.google.android.common.base.Preconditions;
import java.util.Set;

public final class HTML {

   public HTML() {}

   public static final class Element {

      public static final int NO_TYPE = 0;
      public static final int TABLE_TYPE = 1;
      private final boolean breaksFlow;
      private final boolean empty;
      private final HTML.Element.Flow flow;
      private final String name;
      private final boolean optionalEndTag;
      private final int type;


      public Element(String var1, int var2, boolean var3, boolean var4, boolean var5) {
         HTML.Element.Flow var6 = HTML.Element.Flow.NONE;
         this(var1, var2, var3, var4, var5, var6);
      }

      public Element(String var1, int var2, boolean var3, boolean var4, boolean var5, HTML.Element.Flow var6) {
         Object var7 = Preconditions.checkNotNull(var1, "Element name can not be null");
         Object var8 = Preconditions.checkNotNull(var6, "Element flow can not be null");
         this.name = var1;
         this.type = var2;
         this.empty = var3;
         this.optionalEndTag = var4;
         this.breaksFlow = var5;
         this.flow = var6;
      }

      public boolean breaksFlow() {
         return this.breaksFlow;
      }

      public boolean equals(Object var1) {
         boolean var2;
         if(var1 == this) {
            var2 = true;
         } else if(var1 instanceof HTML.Element) {
            HTML.Element var3 = (HTML.Element)var1;
            String var4 = this.name;
            String var5 = var3.name;
            var2 = var4.equals(var5);
         } else {
            var2 = false;
         }

         return var2;
      }

      public HTML.Element.Flow getFlow() {
         return this.flow;
      }

      public String getName() {
         return this.name;
      }

      public int getType() {
         return this.type;
      }

      public int hashCode() {
         return this.name.hashCode();
      }

      public boolean isEmpty() {
         return this.empty;
      }

      public boolean isEndTagOptional() {
         return this.optionalEndTag;
      }

      public String toString() {
         return this.name;
      }

      public static enum Flow {

         // $FF: synthetic field
         private static final HTML.Element.Flow[] $VALUES;
         BLOCK("BLOCK", 1),
         INLINE("INLINE", 0),
         NONE("NONE", 2);


         static {
            HTML.Element.Flow[] var0 = new HTML.Element.Flow[3];
            HTML.Element.Flow var1 = INLINE;
            var0[0] = var1;
            HTML.Element.Flow var2 = BLOCK;
            var0[1] = var2;
            HTML.Element.Flow var3 = NONE;
            var0[2] = var3;
            $VALUES = var0;
         }

         private Flow(String var1, int var2) {}
      }
   }

   public static final class Attribute {

      public static final int BOOLEAN_TYPE = 4;
      public static final int ENUM_TYPE = 3;
      public static final int NO_TYPE = 0;
      public static final int SCRIPT_TYPE = 2;
      public static final int URI_TYPE = 1;
      private final String name;
      private final int type;
      private final Set<String> values;


      public Attribute(String var1, int var2) {
         this(var1, var2, (Set)null);
      }

      public Attribute(String var1, int var2, Set<String> var3) {
         boolean var4 = true;
         super();
         Object var5 = Preconditions.checkNotNull(var1, "Attribute name can not be null");
         boolean var6;
         if(var3 == null) {
            var6 = true;
         } else {
            var6 = false;
         }

         if(var2 != 3) {
            var4 = false;
         }

         Preconditions.checkArgument(var4 ^ var6, "Only ENUM_TYPE can have values != null");
         this.name = var1;
         this.type = var2;
         this.values = var3;
      }

      public boolean equals(Object var1) {
         boolean var2;
         if(var1 == this) {
            var2 = true;
         } else if(var1 instanceof HTML.Attribute) {
            HTML.Attribute var3 = (HTML.Attribute)var1;
            String var4 = this.name;
            String var5 = var3.name;
            var2 = var4.equals(var5);
         } else {
            var2 = false;
         }

         return var2;
      }

      public Set<String> getEnumValues() {
         byte var1;
         if(this.type == 3) {
            var1 = 1;
         } else {
            var1 = 0;
         }

         Preconditions.checkState((boolean)var1);
         return this.values;
      }

      public String getName() {
         return this.name;
      }

      public int getType() {
         return this.type;
      }

      public int hashCode() {
         return this.name.hashCode();
      }

      public String toString() {
         return this.name;
      }
   }
}
