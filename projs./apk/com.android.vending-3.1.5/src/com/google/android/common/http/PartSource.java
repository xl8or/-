package com.google.android.common.http;

import java.io.IOException;
import java.io.InputStream;

public interface PartSource {

   InputStream createInputStream() throws IOException;

   String getFileName();

   long getLength();
}
