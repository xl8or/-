package com.google.common.io;

import java.io.IOException;

public interface InputSupplier<T extends Object> {

   T getInput() throws IOException;
}
