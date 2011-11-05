package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import javax.annotation.Nullable;

@GwtCompatible
interface MapConstraint<K extends Object, V extends Object> {

   void checkKeyValue(@Nullable K var1, @Nullable V var2);

   String toString();
}
