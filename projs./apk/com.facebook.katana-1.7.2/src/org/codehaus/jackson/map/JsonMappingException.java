package org.codehaus.jackson.map;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.codehaus.jackson.JsonLocation;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;

public class JsonMappingException extends JsonProcessingException {

   protected LinkedList<JsonMappingException.Reference> _path;


   public JsonMappingException(String var1) {
      super(var1);
   }

   public JsonMappingException(String var1, Throwable var2) {
      super(var1, var2);
   }

   public JsonMappingException(String var1, JsonLocation var2) {
      super(var1, var2);
   }

   public JsonMappingException(String var1, JsonLocation var2, Throwable var3) {
      super(var1, var2, var3);
   }

   public static JsonMappingException from(JsonParser var0, String var1) {
      JsonLocation var2 = var0.getTokenLocation();
      return new JsonMappingException(var1, var2);
   }

   public static JsonMappingException from(JsonParser var0, String var1, Throwable var2) {
      JsonLocation var3 = var0.getTokenLocation();
      return new JsonMappingException(var1, var3, var2);
   }

   public static JsonMappingException wrapWithPath(Throwable var0, Object var1, int var2) {
      JsonMappingException.Reference var3 = new JsonMappingException.Reference(var1, var2);
      return wrapWithPath(var0, var3);
   }

   public static JsonMappingException wrapWithPath(Throwable var0, Object var1, String var2) {
      JsonMappingException.Reference var3 = new JsonMappingException.Reference(var1, var2);
      return wrapWithPath(var0, var3);
   }

   public static JsonMappingException wrapWithPath(Throwable var0, JsonMappingException.Reference var1) {
      JsonMappingException var2;
      if(var0 instanceof JsonMappingException) {
         var2 = (JsonMappingException)var0;
      } else {
         String var3 = var0.getMessage();
         if(var3 == null || var3.length() == 0) {
            StringBuilder var4 = (new StringBuilder()).append("(was ");
            String var5 = var0.getClass().getName();
            var3 = var4.append(var5).append(")").toString();
         }

         var2 = new JsonMappingException(var3, (JsonLocation)null, var0);
      }

      var2.prependPath(var1);
      return var2;
   }

   protected void _appendPathDesc(StringBuilder var1) {
      Iterator var2 = this._path.iterator();

      while(var2.hasNext()) {
         String var3 = ((JsonMappingException.Reference)var2.next()).toString();
         var1.append(var3);
         if(var2.hasNext()) {
            StringBuilder var5 = var1.append("->");
         }
      }

   }

   protected LinkedList<JsonMappingException.Reference> _nonNullPath() {
      if(this._path == null) {
         LinkedList var1 = new LinkedList();
         this._path = var1;
      }

      return this._path;
   }

   public String getMessage() {
      String var1 = super.getMessage();
      if(this._path != null) {
         StringBuilder var2;
         if(var1 == null) {
            var2 = new StringBuilder();
         } else {
            var2 = new StringBuilder(var1);
         }

         StringBuilder var3 = var2.append(" (through reference chain: ");
         this._appendPathDesc(var2);
         StringBuilder var4 = var2.append(')');
         var1 = var2.toString();
      }

      return var1;
   }

   public List<JsonMappingException.Reference> getPath() {
      List var1;
      if(this._path == null) {
         var1 = Collections.emptyList();
      } else {
         var1 = Collections.unmodifiableList(this._path);
      }

      return var1;
   }

   public void prependPath(Object var1, int var2) {
      JsonMappingException.Reference var3 = new JsonMappingException.Reference(var1, var2);
      this.prependPath(var3);
   }

   public void prependPath(Object var1, String var2) {
      JsonMappingException.Reference var3 = new JsonMappingException.Reference(var1, var2);
      this.prependPath(var3);
   }

   public void prependPath(JsonMappingException.Reference var1) {
      this._nonNullPath().addFirst(var1);
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      String var2 = this.getClass().getName();
      StringBuilder var3 = var1.append(var2).append(": ");
      String var4 = this.getMessage();
      return var3.append(var4).toString();
   }

   public static class Reference {

      protected String _fieldName;
      protected Object _from;
      protected int _index = -1;


      protected Reference() {}

      public Reference(Object var1) {
         this._from = var1;
      }

      public Reference(Object var1, int var2) {
         this._from = var1;
         this._index = var2;
      }

      public Reference(Object var1, String var2) {
         this._from = var1;
         if(var2 == null) {
            throw new NullPointerException("Can not pass null fieldName");
         } else {
            this._fieldName = var2;
         }
      }

      public String getFieldName() {
         return this._fieldName;
      }

      public Object getFrom() {
         return this._from;
      }

      public int getIndex() {
         return this._index;
      }

      public void setFieldName(String var1) {
         this._fieldName = var1;
      }

      public void setFrom(Object var1) {
         this._from = var1;
      }

      public void setIndex(int var1) {
         this._index = var1;
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         Class var2;
         if(this._from instanceof Class) {
            var2 = (Class)this._from;
         } else {
            var2 = this._from.getClass();
         }

         Package var3 = var2.getPackage();
         if(var3 != null) {
            String var4 = var3.getName();
            var1.append(var4);
            StringBuilder var6 = var1.append('.');
         }

         String var7 = var2.getSimpleName();
         var1.append(var7);
         StringBuilder var9 = var1.append('[');
         if(this._fieldName != null) {
            StringBuilder var10 = var1.append('\"');
            String var11 = this._fieldName;
            var1.append(var11);
            StringBuilder var13 = var1.append('\"');
         } else if(this._index >= 0) {
            int var15 = this._index;
            var1.append(var15);
         } else {
            StringBuilder var17 = var1.append('?');
         }

         StringBuilder var14 = var1.append(']');
         return var1.toString();
      }
   }
}
