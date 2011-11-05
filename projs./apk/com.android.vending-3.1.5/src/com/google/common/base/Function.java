package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import javax.annotation.Nullable;

@GwtCompatible
public interface Function<F extends Object, T extends Object> {

   T apply(@Nullable F var1);

   boolean equals(@Nullable Object var1);
}
