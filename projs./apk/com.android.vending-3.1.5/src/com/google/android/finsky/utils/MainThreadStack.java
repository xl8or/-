package com.google.android.finsky.utils;

import com.google.android.finsky.utils.Utils;
import java.util.Stack;

public class MainThreadStack<T extends Object> extends Stack<T> {

   public MainThreadStack() {}

   public boolean isEmpty() {
      Utils.ensureOnMainThread();
      return super.isEmpty();
   }

   public T peek() {
      Utils.ensureOnMainThread();
      return super.peek();
   }

   public T pop() {
      Utils.ensureOnMainThread();
      return super.pop();
   }

   public T push(T var1) {
      Utils.ensureOnMainThread();
      return super.push(var1);
   }
}
