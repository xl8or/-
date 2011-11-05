package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.UnmodifiableIterator;
import java.util.NoSuchElementException;

@GwtCompatible
public abstract class AbstractIterator<T extends Object> extends UnmodifiableIterator<T> {

   private T next;
   private AbstractIterator.State state;


   public AbstractIterator() {
      AbstractIterator.State var1 = AbstractIterator.State.NOT_READY;
      this.state = var1;
   }

   private boolean tryToComputeNext() {
      AbstractIterator.State var1 = AbstractIterator.State.FAILED;
      this.state = var1;
      Object var2 = this.computeNext();
      this.next = var2;
      AbstractIterator.State var3 = this.state;
      AbstractIterator.State var4 = AbstractIterator.State.DONE;
      boolean var6;
      if(var3 != var4) {
         AbstractIterator.State var5 = AbstractIterator.State.READY;
         this.state = var5;
         var6 = true;
      } else {
         var6 = false;
      }

      return var6;
   }

   protected abstract T computeNext();

   protected final T endOfData() {
      AbstractIterator.State var1 = AbstractIterator.State.DONE;
      this.state = var1;
      return null;
   }

   public final boolean hasNext() {
      boolean var1 = false;
      AbstractIterator.State var2 = this.state;
      AbstractIterator.State var3 = AbstractIterator.State.FAILED;
      byte var4;
      if(var2 != var3) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      Preconditions.checkState((boolean)var4);
      int[] var5 = AbstractIterator.1.$SwitchMap$com$google$common$collect$AbstractIterator$State;
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
         AbstractIterator.State var1 = AbstractIterator.State.NOT_READY;
         this.state = var1;
         return this.next;
      }
   }

   public final T peek() {
      if(!this.hasNext()) {
         throw new NoSuchElementException();
      } else {
         return this.next;
      }
   }

   // $FF: synthetic class
   static class 1 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$google$common$collect$AbstractIterator$State = new int[AbstractIterator.State.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$google$common$collect$AbstractIterator$State;
            int var1 = AbstractIterator.State.DONE.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var7) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$google$common$collect$AbstractIterator$State;
            int var3 = AbstractIterator.State.READY.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var6) {
            ;
         }
      }
   }

   private static enum State {

      // $FF: synthetic field
      private static final AbstractIterator.State[] $VALUES;
      DONE("DONE", 2),
      FAILED("FAILED", 3),
      NOT_READY("NOT_READY", 1),
      READY("READY", 0);


      static {
         AbstractIterator.State[] var0 = new AbstractIterator.State[4];
         AbstractIterator.State var1 = READY;
         var0[0] = var1;
         AbstractIterator.State var2 = NOT_READY;
         var0[1] = var2;
         AbstractIterator.State var3 = DONE;
         var0[2] = var3;
         AbstractIterator.State var4 = FAILED;
         var0[3] = var4;
         $VALUES = var0;
      }

      private State(String var1, int var2) {}
   }
}
