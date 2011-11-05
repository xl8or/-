package com.google.android.common.base;

import com.google.android.common.base.CharMatcher;
import com.google.android.common.base.Preconditions;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Splitter {

   private final boolean omitEmptyStrings;
   private final Splitter.Strategy strategy;
   private final CharMatcher trimmer;


   private Splitter(Splitter.Strategy var1) {
      CharMatcher var2 = CharMatcher.NONE;
      this(var1, (boolean)0, var2);
   }

   private Splitter(Splitter.Strategy var1, boolean var2, CharMatcher var3) {
      this.strategy = var1;
      this.omitEmptyStrings = var2;
      this.trimmer = var3;
   }

   public static Splitter fixedLength(int var0) {
      byte var1;
      if(var0 > 0) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      Preconditions.checkArgument((boolean)var1, "The length may not be less than 1");
      Splitter.4 var2 = new Splitter.4(var0);
      return new Splitter(var2);
   }

   public static Splitter on(char var0) {
      return on(CharMatcher.is(var0));
   }

   public static Splitter on(CharMatcher var0) {
      Object var1 = Preconditions.checkNotNull(var0);
      Splitter.1 var2 = new Splitter.1(var0);
      return new Splitter(var2);
   }

   public static Splitter on(String var0) {
      byte var1;
      if(var0.length() != 0) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      Preconditions.checkArgument((boolean)var1, "The separator may not be the empty string.");
      Splitter.2 var2 = new Splitter.2(var0);
      return new Splitter(var2);
   }

   public static Splitter on(Pattern var0) {
      Object var1 = Preconditions.checkNotNull(var0);
      byte var2;
      if(!var0.matcher("").matches()) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      Object[] var3 = new Object[]{var0};
      Preconditions.checkArgument((boolean)var2, "The pattern may not match the empty string: %s", var3);
      Splitter.3 var4 = new Splitter.3(var0);
      return new Splitter(var4);
   }

   public static Splitter onPattern(String var0) {
      return on(Pattern.compile(var0));
   }

   public Splitter omitEmptyStrings() {
      Splitter.Strategy var1 = this.strategy;
      CharMatcher var2 = this.trimmer;
      return new Splitter(var1, (boolean)1, var2);
   }

   public Iterable<String> split(CharSequence var1) {
      Object var2 = Preconditions.checkNotNull(var1);
      return new Splitter.5(var1);
   }

   public Splitter trimResults() {
      CharMatcher var1 = CharMatcher.WHITESPACE;
      return this.trimResults(var1);
   }

   public Splitter trimResults(CharMatcher var1) {
      Object var2 = Preconditions.checkNotNull(var1);
      Splitter.Strategy var3 = this.strategy;
      boolean var4 = this.omitEmptyStrings;
      return new Splitter(var3, var4, var1);
   }

   static class 2 implements Splitter.Strategy {

      // $FF: synthetic field
      final String val$separator;


      2(String var1) {
         this.val$separator = var1;
      }

      public Splitter.SplittingIterator iterator(Splitter var1, CharSequence var2) {
         return new Splitter.2.1(var1, var2);
      }

      class 1 extends Splitter.SplittingIterator {

         1(Splitter var2, CharSequence var3) {
            super(var2, var3);
         }

         public int separatorEnd(int var1) {
            return 2.this.val$separator.length() + var1;
         }

         public int separatorStart(int var1) {
            int var2 = 2.this.val$separator.length();
            int var3 = var1;
            int var4 = this.toSplit.length() - var2;

            label22:
            while(true) {
               if(var3 <= var4) {
                  for(int var5 = 0; var5 < var2; ++var5) {
                     CharSequence var6 = this.toSplit;
                     int var7 = var5 + var3;
                     char var8 = var6.charAt(var7);
                     char var9 = 2.this.val$separator.charAt(var5);
                     if(var8 != var9) {
                        ++var3;
                        continue label22;
                     }
                  }

                  return var3;
               }

               var3 = -1;
               return var3;
            }
         }
      }
   }

   private interface Strategy {

      Iterator<String> iterator(Splitter var1, CharSequence var2);
   }

   private abstract static class AbstractIterator<T extends Object> implements Iterator<T> {

      T next;
      Splitter.AbstractIterator.State state;


      private AbstractIterator() {
         Splitter.AbstractIterator.State var1 = Splitter.AbstractIterator.State.NOT_READY;
         this.state = var1;
      }

      // $FF: synthetic method
      AbstractIterator(Splitter.1 var1) {
         this();
      }

      protected abstract T computeNext();

      protected final T endOfData() {
         Splitter.AbstractIterator.State var1 = Splitter.AbstractIterator.State.DONE;
         this.state = var1;
         return null;
      }

      public final boolean hasNext() {
         boolean var1 = false;
         Splitter.AbstractIterator.State var2 = this.state;
         Splitter.AbstractIterator.State var3 = Splitter.AbstractIterator.State.FAILED;
         byte var4;
         if(var2 != var3) {
            var4 = 1;
         } else {
            var4 = 0;
         }

         Preconditions.checkState((boolean)var4);
         int[] var5 = Splitter.6.$SwitchMap$com$google$android$common$base$Splitter$AbstractIterator$State;
         int var6 = this.state.ordinal();
         switch(var5[var6]) {
         case 1:
            break;
         case 2:
            var1 = true;
            break;
         default:
            var1 = this.tryToComputeNext();
         }

         return var1;
      }

      public final T next() {
         if(!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            Splitter.AbstractIterator.State var1 = Splitter.AbstractIterator.State.NOT_READY;
            this.state = var1;
            return this.next;
         }
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

      boolean tryToComputeNext() {
         Splitter.AbstractIterator.State var1 = Splitter.AbstractIterator.State.FAILED;
         this.state = var1;
         Object var2 = this.computeNext();
         this.next = var2;
         Splitter.AbstractIterator.State var3 = this.state;
         Splitter.AbstractIterator.State var4 = Splitter.AbstractIterator.State.DONE;
         boolean var6;
         if(var3 != var4) {
            Splitter.AbstractIterator.State var5 = Splitter.AbstractIterator.State.READY;
            this.state = var5;
            var6 = true;
         } else {
            var6 = false;
         }

         return var6;
      }

      static enum State {

         // $FF: synthetic field
         private static final Splitter.AbstractIterator.State[] $VALUES;
         DONE("DONE", 2),
         FAILED("FAILED", 3),
         NOT_READY("NOT_READY", 1),
         READY("READY", 0);


         static {
            Splitter.AbstractIterator.State[] var0 = new Splitter.AbstractIterator.State[4];
            Splitter.AbstractIterator.State var1 = READY;
            var0[0] = var1;
            Splitter.AbstractIterator.State var2 = NOT_READY;
            var0[1] = var2;
            Splitter.AbstractIterator.State var3 = DONE;
            var0[2] = var3;
            Splitter.AbstractIterator.State var4 = FAILED;
            var0[3] = var4;
            $VALUES = var0;
         }

         private State(String var1, int var2) {}
      }
   }

   static class 1 implements Splitter.Strategy {

      // $FF: synthetic field
      final CharMatcher val$separatorMatcher;


      1(CharMatcher var1) {
         this.val$separatorMatcher = var1;
      }

      public Splitter.SplittingIterator iterator(Splitter var1, CharSequence var2) {
         return new Splitter.1.1(var1, var2);
      }

      class 1 extends Splitter.SplittingIterator {

         1(Splitter var2, CharSequence var3) {
            super(var2, var3);
         }

         int separatorEnd(int var1) {
            return var1 + 1;
         }

         int separatorStart(int var1) {
            CharMatcher var2 = 1.this.val$separatorMatcher;
            CharSequence var3 = this.toSplit;
            return var2.indexIn(var3, var1);
         }
      }
   }

   static class 4 implements Splitter.Strategy {

      // $FF: synthetic field
      final int val$length;


      4(int var1) {
         this.val$length = var1;
      }

      public Splitter.SplittingIterator iterator(Splitter var1, CharSequence var2) {
         return new Splitter.4.1(var1, var2);
      }

      class 1 extends Splitter.SplittingIterator {

         1(Splitter var2, CharSequence var3) {
            super(var2, var3);
         }

         public int separatorEnd(int var1) {
            return var1;
         }

         public int separatorStart(int var1) {
            int var2 = 4.this.val$length;
            int var3 = var1 + var2;
            int var4 = this.toSplit.length();
            if(var3 >= var4) {
               var3 = -1;
            }

            return var3;
         }
      }
   }

   private abstract static class SplittingIterator extends Splitter.AbstractIterator<String> {

      int offset = 0;
      final boolean omitEmptyStrings;
      final CharSequence toSplit;
      final CharMatcher trimmer;


      protected SplittingIterator(Splitter var1, CharSequence var2) {
         super((Splitter.1)null);
         CharMatcher var3 = var1.trimmer;
         this.trimmer = var3;
         boolean var4 = var1.omitEmptyStrings;
         this.omitEmptyStrings = var4;
         this.toSplit = var2;
      }

      protected String computeNext() {
         while(true) {
            String var12;
            if(this.offset != -1) {
               int var1 = this.offset;
               int var2 = this.offset;
               int var3 = this.separatorStart(var2);
               int var4;
               if(var3 == -1) {
                  var4 = this.toSplit.length();
                  this.offset = -1;
               } else {
                  var4 = var3;
                  int var7 = this.separatorEnd(var3);
                  this.offset = var7;
               }

               while(var1 < var4) {
                  CharMatcher var5 = this.trimmer;
                  char var6 = this.toSplit.charAt(var1);
                  if(!var5.matches(var6)) {
                     break;
                  }

                  ++var1;
               }

               while(var4 > var1) {
                  CharMatcher var8 = this.trimmer;
                  CharSequence var9 = this.toSplit;
                  int var10 = var4 + -1;
                  char var11 = var9.charAt(var10);
                  if(!var8.matches(var11)) {
                     break;
                  }

                  var4 += -1;
               }

               if(this.omitEmptyStrings && var1 == var4) {
                  continue;
               }

               var12 = this.toSplit.subSequence(var1, var4).toString();
            } else {
               var12 = (String)this.endOfData();
            }

            return var12;
         }
      }

      abstract int separatorEnd(int var1);

      abstract int separatorStart(int var1);
   }

   static class 3 implements Splitter.Strategy {

      // $FF: synthetic field
      final Pattern val$separatorPattern;


      3(Pattern var1) {
         this.val$separatorPattern = var1;
      }

      public Splitter.SplittingIterator iterator(Splitter var1, CharSequence var2) {
         Matcher var3 = this.val$separatorPattern.matcher(var2);
         return new Splitter.3.1(var1, var2, var3);
      }

      class 1 extends Splitter.SplittingIterator {

         // $FF: synthetic field
         final Matcher val$matcher;


         1(Splitter var2, CharSequence var3, Matcher var4) {
            super(var2, var3);
            this.val$matcher = var4;
         }

         public int separatorEnd(int var1) {
            return this.val$matcher.end();
         }

         public int separatorStart(int var1) {
            int var2;
            if(this.val$matcher.find(var1)) {
               var2 = this.val$matcher.start();
            } else {
               var2 = -1;
            }

            return var2;
         }
      }
   }

   // $FF: synthetic class
   static class 6 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$google$android$common$base$Splitter$AbstractIterator$State = new int[Splitter.AbstractIterator.State.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$google$android$common$base$Splitter$AbstractIterator$State;
            int var1 = Splitter.AbstractIterator.State.DONE.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var7) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$google$android$common$base$Splitter$AbstractIterator$State;
            int var3 = Splitter.AbstractIterator.State.READY.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var6) {
            ;
         }
      }
   }

   class 5 implements Iterable<String> {

      // $FF: synthetic field
      final CharSequence val$sequence;


      5(CharSequence var2) {
         this.val$sequence = var2;
      }

      public Iterator<String> iterator() {
         Splitter.Strategy var1 = Splitter.this.strategy;
         Splitter var2 = Splitter.this;
         CharSequence var3 = this.val$sequence;
         return var1.iterator(var2, var3);
      }
   }
}
