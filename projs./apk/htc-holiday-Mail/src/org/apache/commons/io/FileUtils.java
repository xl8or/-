package org.apache.commons.io;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.Checksum;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.output.NullOutputStream;

public class FileUtils {

   public static final File[] EMPTY_FILE_ARRAY = new File[0];
   public static final long ONE_GB = 1073741824L;
   public static final long ONE_KB = 1024L;
   public static final long ONE_MB = 1048576L;


   public FileUtils() {}

   public static String byteCountToDisplaySize(long var0) {
      String var4;
      if(var0 / 1073741824L > 0L) {
         StringBuilder var2 = new StringBuilder();
         String var3 = String.valueOf(var0 / 1073741824L);
         var4 = var2.append(var3).append(" GB").toString();
      } else if(var0 / 1048576L > 0L) {
         StringBuilder var5 = new StringBuilder();
         String var6 = String.valueOf(var0 / 1048576L);
         var4 = var5.append(var6).append(" MB").toString();
      } else if(var0 / 1024L > 0L) {
         StringBuilder var7 = new StringBuilder();
         String var8 = String.valueOf(var0 / 1024L);
         var4 = var7.append(var8).append(" KB").toString();
      } else {
         StringBuilder var9 = new StringBuilder();
         String var10 = String.valueOf(var0);
         var4 = var9.append(var10).append(" bytes").toString();
      }

      return var4;
   }

   public static Checksum checksum(File var0, Checksum var1) throws IOException {
      if(var0.isDirectory()) {
         throw new IllegalArgumentException("Checksums can\'t be computed on directories");
      } else {
         Object var2 = null;
         boolean var12 = false;

         CheckedInputStream var4;
         try {
            var12 = true;
            FileInputStream var3 = new FileInputStream(var0);
            var4 = new CheckedInputStream(var3, var1);
            var12 = false;
         } finally {
            if(var12) {
               IOUtils.closeQuietly((InputStream)var2);
            }
         }

         try {
            NullOutputStream var5 = new NullOutputStream();
            IOUtils.copy((InputStream)var4, (OutputStream)var5);
         } finally {
            ;
         }

         IOUtils.closeQuietly((InputStream)var4);
         return var1;
      }
   }

   public static long checksumCRC32(File var0) throws IOException {
      CRC32 var1 = new CRC32();
      checksum(var0, var1);
      return var1.getValue();
   }

   public static void cleanDirectory(File var0) throws IOException {
      if(!var0.exists()) {
         String var1 = var0 + " does not exist";
         throw new IllegalArgumentException(var1);
      } else if(!var0.isDirectory()) {
         String var2 = var0 + " is not a directory";
         throw new IllegalArgumentException(var2);
      } else {
         File[] var3 = var0.listFiles();
         if(var3 == null) {
            String var4 = "Failed to list contents of " + var0;
            throw new IOException(var4);
         } else {
            IOException var5 = null;
            int var6 = 0;

            while(true) {
               int var7 = var3.length;
               if(var6 >= var7) {
                  if(var5 == null) {
                     return;
                  } else {
                     throw var5;
                  }
               }

               File var8 = var3[var6];

               try {
                  forceDelete(var8);
               } catch (IOException var9) {
                  var5 = var9;
               }

               ++var6;
            }
         }
      }
   }

   private static void cleanDirectoryOnExit(File var0) throws IOException {
      if(!var0.exists()) {
         String var1 = var0 + " does not exist";
         throw new IllegalArgumentException(var1);
      } else if(!var0.isDirectory()) {
         String var2 = var0 + " is not a directory";
         throw new IllegalArgumentException(var2);
      } else {
         File[] var3 = var0.listFiles();
         if(var3 == null) {
            String var4 = "Failed to list contents of " + var0;
            throw new IOException(var4);
         } else {
            IOException var5 = null;
            int var6 = 0;

            while(true) {
               int var7 = var3.length;
               if(var6 >= var7) {
                  if(var5 == null) {
                     return;
                  } else {
                     throw var5;
                  }
               }

               File var8 = var3[var6];

               try {
                  forceDeleteOnExit(var8);
               } catch (IOException var9) {
                  var5 = var9;
               }

               ++var6;
            }
         }
      }
   }

   public static boolean contentEquals(File param0, File param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public static File[] convertFileCollectionToFileArray(Collection var0) {
      File[] var1 = new File[var0.size()];
      return (File[])((File[])var0.toArray(var1));
   }

   public static void copyDirectory(File var0, File var1) throws IOException {
      copyDirectory(var0, var1, (boolean)1);
   }

   public static void copyDirectory(File var0, File var1, FileFilter var2) throws IOException {
      copyDirectory(var0, var1, var2, (boolean)1);
   }

   public static void copyDirectory(File var0, File var1, FileFilter var2, boolean var3) throws IOException {
      if(var0 == null) {
         throw new NullPointerException("Source must not be null");
      } else if(var1 == null) {
         throw new NullPointerException("Destination must not be null");
      } else if(!var0.exists()) {
         String var4 = "Source \'" + var0 + "\' does not exist";
         throw new FileNotFoundException(var4);
      } else if(!var0.isDirectory()) {
         String var5 = "Source \'" + var0 + "\' exists but is not a directory";
         throw new IOException(var5);
      } else {
         String var6 = var0.getCanonicalPath();
         String var7 = var1.getCanonicalPath();
         if(var6.equals(var7)) {
            String var8 = "Source \'" + var0 + "\' and destination \'" + var1 + "\' are the same";
            throw new IOException(var8);
         } else {
            ArrayList var9 = null;
            String var10 = var1.getCanonicalPath();
            String var11 = var0.getCanonicalPath();
            if(var10.startsWith(var11)) {
               File[] var12;
               if(var2 == null) {
                  var12 = var0.listFiles();
               } else {
                  var12 = var0.listFiles(var2);
               }

               if(var12 != null && var12.length > 0) {
                  int var13 = var12.length;
                  var9 = new ArrayList(var13);
                  int var14 = 0;

                  while(true) {
                     int var15 = var12.length;
                     if(var14 >= var15) {
                        break;
                     }

                     String var16 = var12[var14].getName();
                     String var17 = (new File(var1, var16)).getCanonicalPath();
                     var9.add(var17);
                     ++var14;
                  }
               }
            }

            doCopyDirectory(var0, var1, var2, var3, var9);
         }
      }
   }

   public static void copyDirectory(File var0, File var1, boolean var2) throws IOException {
      copyDirectory(var0, var1, (FileFilter)null, var2);
   }

   public static void copyDirectoryToDirectory(File var0, File var1) throws IOException {
      if(var0 == null) {
         throw new NullPointerException("Source must not be null");
      } else if(var0.exists() && !var0.isDirectory()) {
         String var2 = "Source \'" + var1 + "\' is not a directory";
         throw new IllegalArgumentException(var2);
      } else if(var1 == null) {
         throw new NullPointerException("Destination must not be null");
      } else if(var1.exists() && !var1.isDirectory()) {
         String var3 = "Destination \'" + var1 + "\' is not a directory";
         throw new IllegalArgumentException(var3);
      } else {
         String var4 = var0.getName();
         File var5 = new File(var1, var4);
         copyDirectory(var0, var5, (boolean)1);
      }
   }

   public static void copyFile(File var0, File var1) throws IOException {
      copyFile(var0, var1, (boolean)1);
   }

   public static void copyFile(File var0, File var1, boolean var2) throws IOException {
      if(var0 == null) {
         throw new NullPointerException("Source must not be null");
      } else if(var1 == null) {
         throw new NullPointerException("Destination must not be null");
      } else if(!var0.exists()) {
         String var3 = "Source \'" + var0 + "\' does not exist";
         throw new FileNotFoundException(var3);
      } else if(var0.isDirectory()) {
         String var4 = "Source \'" + var0 + "\' exists but is a directory";
         throw new IOException(var4);
      } else {
         String var5 = var0.getCanonicalPath();
         String var6 = var1.getCanonicalPath();
         if(var5.equals(var6)) {
            String var7 = "Source \'" + var0 + "\' and destination \'" + var1 + "\' are the same";
            throw new IOException(var7);
         } else if(var1.getParentFile() != null && !var1.getParentFile().exists() && !var1.getParentFile().mkdirs()) {
            String var8 = "Destination \'" + var1 + "\' directory cannot be created";
            throw new IOException(var8);
         } else if(var1.exists() && !var1.canWrite()) {
            String var9 = "Destination \'" + var1 + "\' exists but is read-only";
            throw new IOException(var9);
         } else {
            doCopyFile(var0, var1, var2);
         }
      }
   }

   public static void copyFileToDirectory(File var0, File var1) throws IOException {
      copyFileToDirectory(var0, var1, (boolean)1);
   }

   public static void copyFileToDirectory(File var0, File var1, boolean var2) throws IOException {
      if(var1 == null) {
         throw new NullPointerException("Destination must not be null");
      } else if(var1.exists() && !var1.isDirectory()) {
         String var3 = "Destination \'" + var1 + "\' is not a directory";
         throw new IllegalArgumentException(var3);
      } else {
         String var4 = var0.getName();
         File var5 = new File(var1, var4);
         copyFile(var0, var5, var2);
      }
   }

   public static void copyURLToFile(URL param0, File param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public static void deleteDirectory(File var0) throws IOException {
      if(var0.exists()) {
         cleanDirectory(var0);
         if(!var0.delete()) {
            String var1 = "Unable to delete directory " + var0 + ".";
            throw new IOException(var1);
         }
      }
   }

   private static void deleteDirectoryOnExit(File var0) throws IOException {
      if(var0.exists()) {
         cleanDirectoryOnExit(var0);
         var0.deleteOnExit();
      }
   }

   public static boolean deleteQuietly(File var0) {
      boolean var1;
      if(var0 == null) {
         var1 = false;
      } else {
         try {
            if(var0.isDirectory()) {
               cleanDirectory(var0);
            }
         } catch (Exception var5) {
            ;
         }

         boolean var2;
         try {
            var2 = var0.delete();
         } catch (Exception var6) {
            var1 = false;
            return var1;
         }

         var1 = var2;
      }

      return var1;
   }

   private static void doCopyDirectory(File var0, File var1, FileFilter var2, boolean var3, List var4) throws IOException {
      if(var1.exists()) {
         if(!var1.isDirectory()) {
            String var5 = "Destination \'" + var1 + "\' exists but is not a directory";
            throw new IOException(var5);
         }
      } else {
         if(!var1.mkdirs()) {
            String var6 = "Destination \'" + var1 + "\' directory cannot be created";
            throw new IOException(var6);
         }

         if(var3) {
            long var7 = var0.lastModified();
            var1.setLastModified(var7);
         }
      }

      if(!var1.canWrite()) {
         String var10 = "Destination \'" + var1 + "\' cannot be written to";
         throw new IOException(var10);
      } else {
         File[] var11;
         if(var2 == null) {
            var11 = var0.listFiles();
         } else {
            var11 = var0.listFiles(var2);
         }

         if(var11 == null) {
            String var12 = "Failed to list contents of " + var0;
            throw new IOException(var12);
         } else {
            int var13 = 0;

            while(true) {
               int var14 = var11.length;
               if(var13 >= var14) {
                  return;
               }

               label45: {
                  String var15 = var11[var13].getName();
                  File var16 = new File(var1, var15);
                  if(var4 != null) {
                     String var17 = var11[var13].getCanonicalPath();
                     if(var4.contains(var17)) {
                        break label45;
                     }
                  }

                  if(var11[var13].isDirectory()) {
                     doCopyDirectory(var11[var13], var16, var2, var3, var4);
                  } else {
                     doCopyFile(var11[var13], var16, var3);
                  }
               }

               ++var13;
            }
         }
      }
   }

   private static void doCopyFile(File param0, File param1, boolean param2) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public static void forceDelete(File var0) throws IOException {
      if(var0.isDirectory()) {
         deleteDirectory(var0);
      } else {
         boolean var1 = var0.exists();
         if(!var0.delete()) {
            if(!var1) {
               String var2 = "File does not exist: " + var0;
               throw new FileNotFoundException(var2);
            } else {
               String var3 = "Unable to delete file: " + var0;
               throw new IOException(var3);
            }
         }
      }
   }

   public static void forceDeleteOnExit(File var0) throws IOException {
      if(var0.isDirectory()) {
         deleteDirectoryOnExit(var0);
      } else {
         var0.deleteOnExit();
      }
   }

   public static void forceMkdir(File var0) throws IOException {
      if(var0.exists()) {
         if(var0.isFile()) {
            String var1 = "File " + var0 + " exists and is " + "not a directory. Unable to create directory.";
            throw new IOException(var1);
         }
      } else if(!var0.mkdirs()) {
         String var2 = "Unable to create directory " + var0;
         throw new IOException(var2);
      }
   }

   private static void innerListFiles(Collection var0, File var1, IOFileFilter var2) {
      File[] var3 = var1.listFiles(var2);
      if(var3 != null) {
         int var4 = 0;

         while(true) {
            int var5 = var3.length;
            if(var4 >= var5) {
               return;
            }

            if(var3[var4].isDirectory()) {
               File var6 = var3[var4];
               innerListFiles(var0, var6, var2);
            } else {
               File var7 = var3[var4];
               var0.add(var7);
            }

            ++var4;
         }
      }
   }

   public static boolean isFileNewer(File var0, long var1) {
      if(var0 == null) {
         throw new IllegalArgumentException("No specified file");
      } else {
         boolean var3;
         if(!var0.exists()) {
            var3 = false;
         } else if(var0.lastModified() > var1) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }
   }

   public static boolean isFileNewer(File var0, File var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("No specified reference file");
      } else if(!var1.exists()) {
         String var2 = "The reference file \'" + var0 + "\' doesn\'t exist";
         throw new IllegalArgumentException(var2);
      } else {
         long var3 = var1.lastModified();
         return isFileNewer(var0, var3);
      }
   }

   public static boolean isFileNewer(File var0, Date var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("No specified date");
      } else {
         long var2 = var1.getTime();
         return isFileNewer(var0, var2);
      }
   }

   public static boolean isFileOlder(File var0, long var1) {
      if(var0 == null) {
         throw new IllegalArgumentException("No specified file");
      } else {
         boolean var3;
         if(!var0.exists()) {
            var3 = false;
         } else if(var0.lastModified() < var1) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }
   }

   public static boolean isFileOlder(File var0, File var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("No specified reference file");
      } else if(!var1.exists()) {
         String var2 = "The reference file \'" + var0 + "\' doesn\'t exist";
         throw new IllegalArgumentException(var2);
      } else {
         long var3 = var1.lastModified();
         return isFileOlder(var0, var3);
      }
   }

   public static boolean isFileOlder(File var0, Date var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("No specified date");
      } else {
         long var2 = var1.getTime();
         return isFileOlder(var0, var2);
      }
   }

   public static Iterator iterateFiles(File var0, IOFileFilter var1, IOFileFilter var2) {
      return listFiles(var0, var1, var2).iterator();
   }

   public static Iterator iterateFiles(File var0, String[] var1, boolean var2) {
      return listFiles(var0, var1, var2).iterator();
   }

   public static LineIterator lineIterator(File var0) throws IOException {
      return lineIterator(var0, (String)null);
   }

   public static LineIterator lineIterator(File param0, String param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public static Collection listFiles(File var0, IOFileFilter var1, IOFileFilter var2) {
      if(!var0.isDirectory()) {
         throw new IllegalArgumentException("Parameter \'directory\' is not a directory");
      } else if(var1 == null) {
         throw new NullPointerException("Parameter \'fileFilter\' is null");
      } else {
         IOFileFilter var3 = FileFilterUtils.notFileFilter(DirectoryFileFilter.INSTANCE);
         IOFileFilter var4 = FileFilterUtils.andFileFilter(var1, var3);
         IOFileFilter var5;
         if(var2 == null) {
            var5 = FalseFileFilter.INSTANCE;
         } else {
            IOFileFilter var8 = DirectoryFileFilter.INSTANCE;
            var5 = FileFilterUtils.andFileFilter(var2, var8);
         }

         LinkedList var6 = new LinkedList();
         IOFileFilter var7 = FileFilterUtils.orFileFilter(var4, var5);
         innerListFiles(var6, var0, var7);
         return var6;
      }
   }

   public static Collection listFiles(File var0, String[] var1, boolean var2) {
      Object var3;
      if(var1 == null) {
         var3 = TrueFileFilter.INSTANCE;
      } else {
         String[] var5 = toSuffixes(var1);
         var3 = new SuffixFileFilter(var5);
      }

      IOFileFilter var4;
      if(var2) {
         var4 = TrueFileFilter.INSTANCE;
      } else {
         var4 = FalseFileFilter.INSTANCE;
      }

      return listFiles(var0, (IOFileFilter)var3, var4);
   }

   public static void moveDirectory(File var0, File var1) throws IOException {
      if(var0 == null) {
         throw new NullPointerException("Source must not be null");
      } else if(var1 == null) {
         throw new NullPointerException("Destination must not be null");
      } else if(!var0.exists()) {
         String var2 = "Source \'" + var0 + "\' does not exist";
         throw new FileNotFoundException(var2);
      } else if(!var0.isDirectory()) {
         String var3 = "Source \'" + var0 + "\' is not a directory";
         throw new IOException(var3);
      } else if(var1.exists()) {
         String var4 = "Destination \'" + var1 + "\' already exists";
         throw new IOException(var4);
      } else if(!var0.renameTo(var1)) {
         copyDirectory(var0, var1);
         deleteDirectory(var0);
         if(var0.exists()) {
            String var5 = "Failed to delete original directory \'" + var0 + "\' after copy to \'" + var1 + "\'";
            throw new IOException(var5);
         }
      }
   }

   public static void moveDirectoryToDirectory(File var0, File var1, boolean var2) throws IOException {
      if(var0 == null) {
         throw new NullPointerException("Source must not be null");
      } else if(var1 == null) {
         throw new NullPointerException("Destination directory must not be null");
      } else {
         if(!var1.exists() && var2) {
            boolean var3 = var1.mkdirs();
         }

         if(!var1.exists()) {
            String var4 = "Destination directory \'" + var1 + "\' does not exist [createDestDir=" + var2 + "]";
            throw new FileNotFoundException(var4);
         } else if(!var1.isDirectory()) {
            String var5 = "Destination \'" + var1 + "\' is not a directory";
            throw new IOException(var5);
         } else {
            String var6 = var0.getName();
            File var7 = new File(var1, var6);
            moveDirectory(var0, var7);
         }
      }
   }

   public static void moveFile(File var0, File var1) throws IOException {
      if(var0 == null) {
         throw new NullPointerException("Source must not be null");
      } else if(var1 == null) {
         throw new NullPointerException("Destination must not be null");
      } else if(!var0.exists()) {
         String var2 = "Source \'" + var0 + "\' does not exist";
         throw new FileNotFoundException(var2);
      } else if(var0.isDirectory()) {
         String var3 = "Source \'" + var0 + "\' is a directory";
         throw new IOException(var3);
      } else if(var1.exists()) {
         String var4 = "Destination \'" + var1 + "\' already exists";
         throw new IOException(var4);
      } else if(var1.isDirectory()) {
         String var5 = "Destination \'" + var1 + "\' is a directory";
         throw new IOException(var5);
      } else if(!var0.renameTo(var1)) {
         copyFile(var0, var1);
         if(!var0.delete()) {
            boolean var6 = deleteQuietly(var1);
            String var7 = "Failed to delete original file \'" + var0 + "\' after copy to \'" + var1 + "\'";
            throw new IOException(var7);
         }
      }
   }

   public static void moveFileToDirectory(File var0, File var1, boolean var2) throws IOException {
      if(var0 == null) {
         throw new NullPointerException("Source must not be null");
      } else if(var1 == null) {
         throw new NullPointerException("Destination directory must not be null");
      } else {
         if(!var1.exists() && var2) {
            boolean var3 = var1.mkdirs();
         }

         if(!var1.exists()) {
            String var4 = "Destination directory \'" + var1 + "\' does not exist [createDestDir=" + var2 + "]";
            throw new FileNotFoundException(var4);
         } else if(!var1.isDirectory()) {
            String var5 = "Destination \'" + var1 + "\' is not a directory";
            throw new IOException(var5);
         } else {
            String var6 = var0.getName();
            File var7 = new File(var1, var6);
            moveFile(var0, var7);
         }
      }
   }

   public static void moveToDirectory(File var0, File var1, boolean var2) throws IOException {
      if(var0 == null) {
         throw new NullPointerException("Source must not be null");
      } else if(var1 == null) {
         throw new NullPointerException("Destination must not be null");
      } else if(!var0.exists()) {
         String var3 = "Source \'" + var0 + "\' does not exist";
         throw new FileNotFoundException(var3);
      } else if(var0.isDirectory()) {
         moveDirectoryToDirectory(var0, var1, var2);
      } else {
         moveFileToDirectory(var0, var1, var2);
      }
   }

   public static FileInputStream openInputStream(File var0) throws IOException {
      if(var0.exists()) {
         if(var0.isDirectory()) {
            String var1 = "File \'" + var0 + "\' exists but is a directory";
            throw new IOException(var1);
         } else if(!var0.canRead()) {
            String var2 = "File \'" + var0 + "\' cannot be read";
            throw new IOException(var2);
         } else {
            return new FileInputStream(var0);
         }
      } else {
         String var3 = "File \'" + var0 + "\' does not exist";
         throw new FileNotFoundException(var3);
      }
   }

   public static FileOutputStream openOutputStream(File var0) throws IOException {
      if(var0.exists()) {
         if(var0.isDirectory()) {
            String var1 = "File \'" + var0 + "\' exists but is a directory";
            throw new IOException(var1);
         }

         if(!var0.canWrite()) {
            String var2 = "File \'" + var0 + "\' cannot be written to";
            throw new IOException(var2);
         }
      } else {
         File var3 = var0.getParentFile();
         if(var3 != null && !var3.exists() && !var3.mkdirs()) {
            String var4 = "File \'" + var0 + "\' could not be created";
            throw new IOException(var4);
         }
      }

      return new FileOutputStream(var0);
   }

   public static byte[] readFileToByteArray(File param0) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public static String readFileToString(File var0) throws IOException {
      return readFileToString(var0, (String)null);
   }

   public static String readFileToString(File param0, String param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public static List readLines(File var0) throws IOException {
      return readLines(var0, (String)null);
   }

   public static List readLines(File param0, String param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public static long sizeOfDirectory(File var0) {
      if(!var0.exists()) {
         String var1 = var0 + " does not exist";
         throw new IllegalArgumentException(var1);
      } else if(!var0.isDirectory()) {
         String var2 = var0 + " is not a directory";
         throw new IllegalArgumentException(var2);
      } else {
         long var3 = 0L;
         File[] var5 = var0.listFiles();
         long var6;
         if(var5 == null) {
            var6 = 0L;
         } else {
            int var8 = 0;

            while(true) {
               int var9 = var5.length;
               if(var8 >= var9) {
                  var6 = var3;
                  break;
               }

               File var10 = var5[var8];
               if(var10.isDirectory()) {
                  long var11 = sizeOfDirectory(var10);
                  var3 += var11;
               } else {
                  long var13 = var10.length();
                  var3 += var13;
               }

               ++var8;
            }
         }

         return var6;
      }
   }

   public static File toFile(URL var0) {
      File var1;
      if(var0 != null && var0.getProtocol().equals("file")) {
         String var2 = var0.getFile();
         char var3 = File.separatorChar;
         String var4 = var2.replace('/', var3);
         int var5 = 0;

         while(true) {
            var5 = var4.indexOf(37, var5);
            if(var5 < 0) {
               var1 = new File(var4);
               break;
            }

            int var6 = var5 + 2;
            int var7 = var4.length();
            if(var6 < var7) {
               int var8 = var5 + 1;
               int var9 = var5 + 3;
               char var10 = (char)Integer.parseInt(var4.substring(var8, var9), 16);
               StringBuilder var11 = new StringBuilder();
               String var12 = var4.substring(0, var5);
               StringBuilder var13 = var11.append(var12).append(var10);
               int var14 = var5 + 3;
               String var15 = var4.substring(var14);
               var4 = var13.append(var15).toString();
            }
         }
      } else {
         var1 = null;
      }

      return var1;
   }

   public static File[] toFiles(URL[] var0) {
      File[] var1;
      if(var0 != null && var0.length != 0) {
         File[] var2 = new File[var0.length];
         int var3 = 0;

         while(true) {
            int var4 = var0.length;
            if(var3 >= var4) {
               var1 = var2;
               break;
            }

            URL var5 = var0[var3];
            if(var5 != null) {
               if(!var5.getProtocol().equals("file")) {
                  String var6 = "URL could not be converted to a File: " + var5;
                  throw new IllegalArgumentException(var6);
               }

               File var7 = toFile(var5);
               var2[var3] = var7;
            }

            ++var3;
         }
      } else {
         var1 = EMPTY_FILE_ARRAY;
      }

      return var1;
   }

   private static String[] toSuffixes(String[] var0) {
      String[] var1 = new String[var0.length];
      int var2 = 0;

      while(true) {
         int var3 = var0.length;
         if(var2 >= var3) {
            return var1;
         }

         StringBuilder var4 = (new StringBuilder()).append(".");
         String var5 = var0[var2];
         String var6 = var4.append(var5).toString();
         var1[var2] = var6;
         ++var2;
      }
   }

   public static URL[] toURLs(File[] var0) throws IOException {
      URL[] var1 = new URL[var0.length];
      int var2 = 0;

      while(true) {
         int var3 = var1.length;
         if(var2 >= var3) {
            return var1;
         }

         URL var4 = var0[var2].toURL();
         var1[var2] = var4;
         ++var2;
      }
   }

   public static void touch(File var0) throws IOException {
      if(!var0.exists()) {
         IOUtils.closeQuietly((OutputStream)openOutputStream(var0));
      }

      long var1 = System.currentTimeMillis();
      if(!var0.setLastModified(var1)) {
         String var3 = "Unable to set the last modification time for " + var0;
         throw new IOException(var3);
      }
   }

   public static boolean waitFor(File var0, int var1) {
      int var2 = 0;

      boolean var5;
      while(true) {
         if(!var0.exists()) {
            int var3 = var2 + 1;
            if(var2 >= 10) {
               var2 = 0;
               int var4 = 0 + 1;
               if(0 > var1) {
                  var5 = false;
                  break;
               }
            } else {
               var2 = var3;
            }

            long var8 = 100L;

            try {
               Thread.sleep(var8);
               continue;
            } catch (InterruptedException var12) {
               continue;
            } catch (Exception var13) {
               ;
            }
         }

         var5 = true;
         break;
      }

      return var5;
   }

   public static void writeByteArrayToFile(File param0, byte[] param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public static void writeLines(File var0, String var1, Collection var2) throws IOException {
      writeLines(var0, var1, var2, (String)null);
   }

   public static void writeLines(File param0, String param1, Collection param2, String param3) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public static void writeLines(File var0, Collection var1) throws IOException {
      writeLines(var0, (String)null, var1, (String)null);
   }

   public static void writeLines(File var0, Collection var1, String var2) throws IOException {
      writeLines(var0, (String)null, var1, var2);
   }

   public static void writeStringToFile(File var0, String var1) throws IOException {
      writeStringToFile(var0, var1, (String)null);
   }

   public static void writeStringToFile(File param0, String param1, String param2) throws IOException {
      // $FF: Couldn't be decompiled
   }
}
