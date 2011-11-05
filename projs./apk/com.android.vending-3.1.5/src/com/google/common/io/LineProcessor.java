package com.google.common.io;

import java.io.IOException;

public interface LineProcessor<T extends Object> {

   T getResult();

   boolean processLine(String var1) throws IOException;
}
