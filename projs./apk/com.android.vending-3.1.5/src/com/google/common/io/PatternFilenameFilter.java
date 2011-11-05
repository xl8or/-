package com.google.common.io;

import com.google.common.base.Preconditions;
import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

public final class PatternFilenameFilter implements FilenameFilter {

   private final Pattern pattern;


   public PatternFilenameFilter(String var1) {
      Pattern var2 = Pattern.compile(var1);
      this(var2);
   }

   public PatternFilenameFilter(Pattern var1) {
      Pattern var2 = (Pattern)Preconditions.checkNotNull(var1);
      this.pattern = var2;
   }

   public boolean accept(File var1, String var2) {
      return this.pattern.matcher(var2).matches();
   }
}
