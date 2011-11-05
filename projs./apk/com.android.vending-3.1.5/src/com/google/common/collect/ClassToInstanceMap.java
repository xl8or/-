package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Map;
import javax.annotation.Nullable;

@GwtCompatible
public interface ClassToInstanceMap<B extends Object> extends Map<Class<? extends B>, B> {

   <T extends B> T getInstance(Class<T> var1);

   <T extends B> T putInstance(Class<T> var1, @Nullable T var2);
}
