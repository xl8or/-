package org.codehaus.jackson.map.introspect;

import java.lang.reflect.Method;

public interface MethodFilter {

   boolean includeMethod(Method var1);
}
