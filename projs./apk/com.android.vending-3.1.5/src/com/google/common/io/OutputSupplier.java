package com.google.common.io;

import java.io.IOException;

public interface OutputSupplier<T extends Object> {

   T getOutput() throws IOException;
}
