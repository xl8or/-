package org.codehaus.jackson.impl;

import java.io.IOException;
import java.util.Arrays;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.PrettyPrinter;
import org.codehaus.jackson.impl.Indenter;

public class DefaultPrettyPrinter implements PrettyPrinter {

   protected Indenter mArrayIndenter;
   protected int mNesting;
   protected Indenter mObjectIndenter;
   protected boolean mSpacesInObjectEntries;


   public DefaultPrettyPrinter() {
      DefaultPrettyPrinter.FixedSpaceIndenter var1 = new DefaultPrettyPrinter.FixedSpaceIndenter();
      this.mArrayIndenter = var1;
      DefaultPrettyPrinter.Lf2SpacesIndenter var2 = new DefaultPrettyPrinter.Lf2SpacesIndenter();
      this.mObjectIndenter = var2;
      this.mSpacesInObjectEntries = (boolean)1;
      this.mNesting = 0;
   }

   public void beforeArrayValues(JsonGenerator var1) throws IOException, JsonGenerationException {
      Indenter var2 = this.mArrayIndenter;
      int var3 = this.mNesting;
      var2.writeIndentation(var1, var3);
   }

   public void beforeObjectEntries(JsonGenerator var1) throws IOException, JsonGenerationException {
      Indenter var2 = this.mObjectIndenter;
      int var3 = this.mNesting;
      var2.writeIndentation(var1, var3);
   }

   public void indentArraysWith(Indenter var1) {
      Object var2;
      if(var1 == null) {
         var2 = new DefaultPrettyPrinter.NopIndenter();
      } else {
         var2 = var1;
      }

      this.mArrayIndenter = (Indenter)var2;
   }

   public void indentObjectsWith(Indenter var1) {
      Object var2;
      if(var1 == null) {
         var2 = new DefaultPrettyPrinter.NopIndenter();
      } else {
         var2 = var1;
      }

      this.mObjectIndenter = (Indenter)var2;
   }

   public void spacesInObjectEntries(boolean var1) {
      this.mSpacesInObjectEntries = var1;
   }

   public void writeArrayValueSeparator(JsonGenerator var1) throws IOException, JsonGenerationException {
      var1.writeRaw(',');
      Indenter var2 = this.mArrayIndenter;
      int var3 = this.mNesting;
      var2.writeIndentation(var1, var3);
   }

   public void writeEndArray(JsonGenerator var1, int var2) throws IOException, JsonGenerationException {
      if(!this.mArrayIndenter.isInline()) {
         int var3 = this.mNesting - 1;
         this.mNesting = var3;
      }

      if(var2 > 0) {
         Indenter var4 = this.mArrayIndenter;
         int var5 = this.mNesting;
         var4.writeIndentation(var1, var5);
      } else {
         var1.writeRaw(' ');
      }

      var1.writeRaw(']');
   }

   public void writeEndObject(JsonGenerator var1, int var2) throws IOException, JsonGenerationException {
      if(!this.mObjectIndenter.isInline()) {
         int var3 = this.mNesting - 1;
         this.mNesting = var3;
      }

      if(var2 > 0) {
         Indenter var4 = this.mObjectIndenter;
         int var5 = this.mNesting;
         var4.writeIndentation(var1, var5);
      } else {
         var1.writeRaw(' ');
      }

      var1.writeRaw('}');
   }

   public void writeObjectEntrySeparator(JsonGenerator var1) throws IOException, JsonGenerationException {
      var1.writeRaw(',');
      Indenter var2 = this.mObjectIndenter;
      int var3 = this.mNesting;
      var2.writeIndentation(var1, var3);
   }

   public void writeObjectFieldValueSeparator(JsonGenerator var1) throws IOException, JsonGenerationException {
      if(this.mSpacesInObjectEntries) {
         var1.writeRaw(" : ");
      } else {
         var1.writeRaw(':');
      }
   }

   public void writeRootValueSeparator(JsonGenerator var1) throws IOException, JsonGenerationException {
      var1.writeRaw(' ');
   }

   public void writeStartArray(JsonGenerator var1) throws IOException, JsonGenerationException {
      if(!this.mArrayIndenter.isInline()) {
         int var2 = this.mNesting + 1;
         this.mNesting = var2;
      }

      var1.writeRaw('[');
   }

   public void writeStartObject(JsonGenerator var1) throws IOException, JsonGenerationException {
      var1.writeRaw('{');
      if(!this.mObjectIndenter.isInline()) {
         int var2 = this.mNesting + 1;
         this.mNesting = var2;
      }
   }

   public static class NopIndenter implements Indenter {

      public NopIndenter() {}

      public boolean isInline() {
         return true;
      }

      public void writeIndentation(JsonGenerator var1, int var2) {}
   }

   public static class FixedSpaceIndenter implements Indenter {

      public FixedSpaceIndenter() {}

      public boolean isInline() {
         return true;
      }

      public void writeIndentation(JsonGenerator var1, int var2) throws IOException, JsonGenerationException {
         var1.writeRaw(' ');
      }
   }

   public static class Lf2SpacesIndenter implements Indenter {

      static final char[] SPACES;
      static final int SPACE_COUNT = 64;
      static final String SYSTEM_LINE_SEPARATOR;


      static {
         String var0 = null;

         label17: {
            String var1;
            try {
               var1 = System.getProperty("line.separator");
            } catch (Throwable var3) {
               break label17;
            }

            var0 = var1;
         }

         if(var0 == null) {
            var0 = "\n";
         }

         SYSTEM_LINE_SEPARATOR = var0;
         SPACES = new char[64];
         Arrays.fill(SPACES, ' ');
      }

      public Lf2SpacesIndenter() {}

      public boolean isInline() {
         return false;
      }

      public void writeIndentation(JsonGenerator var1, int var2) throws IOException, JsonGenerationException {
         String var3 = SYSTEM_LINE_SEPARATOR;
         var1.writeRaw(var3);

         int var4;
         int var6;
         for(var4 = var2 + var2; var4 > 64; var4 -= var6) {
            char[] var5 = SPACES;
            var1.writeRaw(var5, 0, 64);
            var6 = SPACES.length;
         }

         char[] var7 = SPACES;
         var1.writeRaw(var7, 0, var4);
      }
   }
}
