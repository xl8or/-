package com.overlakehome.common.places;

import static com.google.common.base.Predicates.and;
import static com.google.common.base.Predicates.equalTo;
import static com.google.common.base.Predicates.or;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.apache.commons.lang.StringUtils.defaultString;
import static org.apache.commons.lang.StringUtils.isAllLowerCase;
import static org.apache.commons.lang.StringUtils.isAllUpperCase;
import static org.apache.commons.lang.StringUtils.isAlpha;
import static org.apache.commons.lang.StringUtils.isAlphanumeric;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNumeric;
import static org.apache.commons.lang.StringUtils.join;
import static org.apache.commons.lang.StringUtils.left;
import static org.apache.commons.lang.StringUtils.right;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.math.RandomUtils;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.collect.BiMap;
import com.google.common.collect.Collections2;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.common.io.Closeables;
import com.google.common.io.Flushables;
import com.google.common.primitives.Ints;

public class CommonCodeTest {
    public static class ApacheCommons {
        // apache.commons.lang has tons of helper objects. // http://commons.apache.org/lang/
        // v2.6 stable is JDK 1.3 compatible; no generics (JDK 1.5); many functions obsoleted by JDK 5.
        // v3.0 beta is JDK 1.5 compatible; not backward compatible with v2.6.
        @Test
        public void testStringUtils() {
            assertTrue(isAlpha("abcde"));
            assertTrue(isNumeric("12345"));
            assertTrue(isAlphanumeric("12345abc"));

            assertTrue(isBlank(""));
            assertTrue(isBlank(null));

            assertTrue(isAllLowerCase("abcdef"));
            assertTrue(isAllUpperCase("ABCDEF"));

            assertEquals("abc", defaultString("abc"));
            assertEquals("", defaultString(""));
            assertEquals("", defaultString(null));

            assertEquals("abc", left("abcdef", 3));
            assertEquals("def", right("abcdef", 3));
            assertEquals(null, right(null, 3));

            assertEquals("1, 2, 3", join(new Object[] { 1, 2, 3 }, ", "));
            assertEquals("1, 2, 3", join(Arrays.asList(1, 2, 3), ", "));
        }

        @Test
        public void testStringEscapeUtils() {
            String xmlText = "\"Hello\" & \"GoodBye\"";
            String escapedXml = "&quot;Hello&quot; &amp; &quot;GoodBye&quot;";
            assertEquals(escapedXml, StringEscapeUtils.escapeXml(xmlText));
            assertEquals(xmlText, StringEscapeUtils.unescapeXml(escapedXml));

            String htmlText = "<body>text</body>";
            String escapedHtml = "&lt;body&gt;text&lt;/body&gt;";
            assertEquals(escapedHtml, StringEscapeUtils.escapeHtml(htmlText));
            assertEquals(htmlText, StringEscapeUtils.unescapeHtml(escapedHtml));

            String csvText = "red, beans, and rice";
            String escapedCsv = "\"red, beans, and rice\"";
            assertEquals(escapedCsv, StringEscapeUtils.escapeCsv(csvText));
            assertEquals(csvText, StringEscapeUtils.unescapeCsv(escapedCsv));
        }

        @Test
        public void testArrayUtils() {
            int[] evens = new int[] { 2, 4, 6 };
            int[] odds = new int[] { 1, 3, 5 };
            assertTrue(ArrayUtils.contains(evens, 2));
            assertTrue(ArrayUtils.contains(odds, 3));

            assertTrue(Arrays.equals(new int[] { 2, 4, 6, 8 }, ArrayUtils.add(evens, 8)));
            assertTrue(Arrays.equals(new int[] { 2, 4, 6, 1, 3, 5 }, ArrayUtils.addAll(evens, odds)));

            assertTrue(Arrays.equals(new int[] { 2, 6 }, ArrayUtils.remove(evens, 1)));
            assertTrue(Arrays.equals(new int[] { 1, 3 }, ArrayUtils.remove(odds, 2)));
        }

        @Test
        public void testBooleanUtils() {
            assertTrue(BooleanUtils.toBoolean("true"));
            assertTrue(BooleanUtils.toBoolean("TRUE"));
            assertTrue(BooleanUtils.toBoolean("tRUe"));
            assertTrue(BooleanUtils.toBoolean("on"));
            assertTrue(BooleanUtils.toBoolean("ON"));
            assertTrue(BooleanUtils.toBoolean("yes"));
            assertTrue(BooleanUtils.toBoolean("YES"));
        }

        public class Person implements Comparable<Person> {
            String name;
            Date timestamp = new Date();

            public Person(String string) {
            }

            @Override
            public String toString() {
                return new ToStringBuilder(this).append("name", name)
                        .append("timestamp", timestamp).toString();
            }

            @Override
            public boolean equals(Object other) {
                if (other == null) { return false; }
                if (other == this) { return true; }
                if (other.getClass() != getClass()) { return false; }

                Person rhs = (Person)other;
                return new EqualsBuilder()
                        .append(name, rhs.name)
                        .append(timestamp, rhs.timestamp)
                        .isEquals();
            }

            @Override
            public int hashCode() {
                return new HashCodeBuilder(97, 31)
                        .append(name)
                        .append(timestamp).toHashCode();
            }

            @Override
            public int compareTo(Person other) {
                return new CompareToBuilder()
                        .append(this.name, other.name)
                        .toComparison();
            }

            public String getName() {
                return null;
            }
        }
    }

    public static class GoogleCommons {
        // Google Guava has tons of helper objects. // http://code.google.com/p/guava-libraries/
        // modern with generics, and fluent APIs; well-designed with consistency; active in development.
        @Test
        public void testCollections() {
            List<Integer> list = Lists.newArrayList();
            Map<String, Map<Long, List<String>>> map = Maps.newHashMap();
            ImmutableList<String> list2 = ImmutableList.of("a", "b", "c", "d");
            ImmutableMap<String, String> map2 = ImmutableMap.of("key1", "value", "key2", "value2");
            int[] ints = Ints.toArray(list);
            int[] array = { 1, 2, 3, 4, 5 };
            boolean contains = Ints.contains(array, 4);
            int indexOf = Ints.indexOf(array, 4);
            int max = Ints.max(array);
            int min = Ints.min(array);
            int[] concat = Ints.concat(array, new int[] { 6, 7 });

            ConcurrentMap<Person, Integer> lengths = new MapMaker()
                .weakKeys()
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .makeComputingMap(new Function<Person, Integer>() {
                    public Integer apply(Person p) {
                        return p.getPreferredName().length();
                    }
                });
        }

        @Test
        public void testMultimap() {
            Multimap<String, String> multiMap = HashMultimap.create();
            multiMap.put("Canoo", "Hamlet");
            multiMap.put("Canoo", "Dierk");
            multiMap.put("Canoo", "Andres");
            multiMap.put("EngineYard", "Charles");
            multiMap.put("EngineYard", "Thomas");
            multiMap.put("EngineYard", "Nick");

            assertTrue(multiMap.containsKey("Canoo"));
            assertTrue(multiMap.containsKey("EngineYard"));
            assertTrue(multiMap.containsValue("Hamlet"));
            assertTrue(multiMap.containsValue("Charles"));

            Collection<String> canooies = multiMap.get("Canoo");
            Collection<String> c = Lists.newArrayList("Dierk", "Andres",
                    "Hamlet");
            assertTrue(canooies.containsAll(c));
        }

        @Test
        public void testBiMap() {
            BiMap<String, String> biMap = HashBiMap.create();
            biMap.put("Switzerland", "die Schweiz");
            biMap.put("Poland", "Polska");
            biMap.put("Austria", "…sterreich");
            assertEquals("Polska", biMap.get("Poland"));
            assertEquals("Switzerland", biMap.inverse().get("die Schweiz"));
        }

        @Test
        public void testTable() {
            Table<Integer, Integer, String> table = HashBasedTable.create();
            table.put(1, 1, "Hamlet");
            table.put(1, 2, "Dierk");
            table.put(2, 1, "Andres");
            table.put(2, 2, "Matthius");

            assertTrue(table.contains(1, 1));
            assertTrue(table.containsRow(1));
            assertTrue(table.containsColumn(2));
            assertTrue(!table.contains(3, 1));
            assertTrue(!table.containsRow(3));
            assertTrue(!table.containsColumn(3));
            assertEquals("Hamlet", table.get(1, 1));
            assertTrue(table.containsValue("Hamlet"));
        }

        @Test
        public void testStrings() {
            assertEquals(null, Strings.emptyToNull(""));
            assertEquals("", Strings.nullToEmpty(null));
            assertTrue(Strings.isNullOrEmpty("") && Strings.isNullOrEmpty(null));
            assertEquals("abcabcabc", Strings.repeat("abc", 3));
        }

        @Test
        public void testTransformFilterAndSort() {
            Function<String, Integer> lengthOf = new Function<String, Integer>() {
                @Override
                public Integer apply(String input) {
                    return input.length();
                }
            };

            List<String> names = Lists.newArrayList("Aleksander", "Jaran", "Integrasco", "Guava", "Java");
            ImmutableMap<String, String> fromMap = ImmutableMap.of("key1", "value", "key2", "value2");
            Map<String, Integer> toMap = Maps.transformValues(fromMap, lengthOf);
            List<Integer> lengths = Lists.transform(names, lengthOf);

            Iterable<String> filtered = Iterables.filter(names,
                    and(or(equalTo("Aleksander"), equalTo("Jaran")), lengthLessThanOf(5)));

            List<Person> persons = Lists.newArrayList();
            Comparator<Person> byLastName = new Comparator<Person>() {
                public int compare(Person lhs, Person rhs) {
                    return lhs.getLastName().compareTo(rhs.getLastName());
                }
            };

            Comparator<Person> byFirstName = new Comparator<Person>() {
                public int compare(Person lhs, Person rhs) {
                    return lhs.getFirstName().compareTo(rhs.getFirstName());
                }
            };

            Collections.sort(persons, byLastName);
            List<Person> sorted = Ordering.from(byLastName)
                    .compound(byFirstName).reverse().sortedCopy(persons);
        }

        private static Predicate<String> lengthLessThanOf(int length) {
            return new LengthLessThan(length);
        }

        private static class LengthLessThan implements Predicate<String> {
            private final int length;

            private LengthLessThan(int length) {
                this.length = length;
            }

            public boolean apply(final String s) {
                return s.length() < length;
            }
        }

        @Test
        public void testStringsJoinerAndSplitter() {
            assertEquals(null, Strings.emptyToNull(""));
            assertEquals("", Strings.nullToEmpty(null));
            assertTrue(Strings.isNullOrEmpty("") && Strings.isNullOrEmpty(null));
            assertEquals("abcabcabc", Strings.repeat("abc", 3));

            Set<Integer> set = Sets.newHashSet(1, 2, 3);
            assertEquals("1, 2, 3", Joiner.on(", ").skipNulls().join(set));

            String string = "1, 2, , 3";
            Iterable<String> itr = Splitter.on(",").omitEmptyStrings()
                    .trimResults().split(string);

            Iterable<Integer> setAsInts = Iterables.transform(itr, 
                new Function<String, Integer>() {
                    @Override
                    public Integer apply(String input) {
                        return Integer.valueOf(input);
                    }
                }
            );

            assertTrue(Lists.newArrayList(1, 2, 3).containsAll(
                    Lists.newArrayList(setAsInts)));
        }

        @Test
        public void testCharMatchers() {
//          CharMatcher.WHITESPACE; CharMatcher.JAVA_DIGIT; CharMatcher.ASCII; CharMatcher.ANY;
//          CharMatcher#matchesAllOf, matchesAnyOf, matchesNoneOf, trimFrom, trimLeadingFrom, trimTrailingFrom, ...
            CharMatcher.is('a');
            CharMatcher.isNot('b');
            CharMatcher.anyOf("abcd").negate();
            CharMatcher.inRange('a', 'z').or(CharMatcher.inRange('A', 'Z'));
            assertEquals("89983", CharMatcher.DIGIT.retainFrom("some text 89983 and more"));
            assertEquals("some text  and more", CharMatcher.DIGIT.removeFrom("some text 89983 and more"));
        }

        @Test
        public void testPreconditions() {
            int userID = 1;
            String userName = "some name";
            Preconditions.checkArgument(userID > 0, "userid is negative: %s", userID);
            Preconditions.checkNotNull(userName, "user %s missing name", userID);
            Preconditions.checkState(userName.length() > 0, "user %s missing name", userID);
        }

        public class Person implements Comparable<Person> {
            String name;
            String nickname;
            Date timestamp = new Date();

            public Person(String name) {
                this.name = name;
            }

            @Override
            public String toString() {
                return Objects.toStringHelper(this)
                           .add("name", name)
                           .add("timestamp", timestamp)
                           .toString();
            }

            @Override
            public int hashCode() {
                return Objects.hashCode(name, timestamp);
            }

            @Override
            public boolean equals(Object other) {
                if (other == null) { return false; }
                if (other == this) { return true; }
                if (other.getClass() != getClass()) { return false; }

                Person rhs = (Person)other;
                return Objects.equal(name, rhs.name)
                       && Objects.equal(timestamp, rhs.timestamp);
            }

            @Override
            public int compareTo(Person other) {
                return ComparisonChain.start()
                           .compare(name, other.name)
                           .compare(timestamp, other.timestamp)
                           .result();
            }

            public String getPreferredName() {
                return Objects.firstNonNull(nickname, name);
            }

            public String getName() {
                return name;
            }

            public String getFirstName() {
                return null;
            }

            public String getLastName() {
                return null;
            }
        }

        @Test
        @Ignore
        public void testThrowables() throws Exception {
            Exception e = new Exception();
            Throwables.propagateIfPossible(e);
            Throwables.propagateIfInstanceOf(e, IOException.class);
            Throwables.propagate(e);
            Throwables.throwCause(e, false);
        }

        @Test
        public void testFunction() {
            Person alice = new Person("Alice");
            Person bob = new Person("Bob");
            Person carol = new Person("Carol");
            List<Person> people = Lists.newArrayList(alice, bob, carol);

            Function<Person, String> getName = new Function<Person, String>() {
                public String apply(Person p) {
                    return p.getName();
                }
            };

            assertEquals("Alice", getName.apply(alice));
            Collection<String> names = Collections2.transform(people, getName);
            assertTrue(names.containsAll(Lists.newArrayList("Alice", "Bob", "Carol")));
        }

        @Test
        public void testIOs() throws IOException, NoSuchAlgorithmException {
            // Java 7 will make most of google.common.io obsolete.
            ByteArrayDataOutput byteOut = ByteStreams.newDataOutput();
            byteOut.writeDouble(Math.PI);
            byteOut.writeInt(RandomUtils.nextInt());
            byteOut.writeChar('\n');

            ByteArrayDataInput byteIn = ByteStreams.newDataInput(byteOut.toByteArray());
            assertEquals(Math.PI, byteIn.readDouble());
            byteIn.readInt();
            assertEquals('\n', byteIn.readChar());

//          InputStream is = ByteStreams.class.getResourceAsStream("test.data");
//          OutputStream os = System.out;
//          ByteStreams.copy(is, os);
//
//          is = ByteStreams.class.getResourceAsStream("test.data");
//          byte[] bytes = ByteStreams.toByteArray(is);
//
//          CRC32 crc32 = new CRC32();
//          long checksum = ByteStreams.getChecksum(
//                  ByteStreams.newInputStreamSupplier(bytes), crc32);
//
//          MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
//          byte[] digest = ByteStreams.getDigest(
//                  ByteStreams.newInputStreamSupplier(bytes), sha256);
//
//          InputSupplier<FileInputStream> fileIn = Files
//                  .newInputStreamSupplier(new File("data", "large.txt"));
//                  InputSupplier<InputStream> slicedStream =
//                  ByteStreams.slice( fileIn, 10, 1000 );
//                  byte[] data = ByteStreams.toByteArray( slicedStream );
//
            OutputStream os = null;
            try {
                os = new FileOutputStream(new File("data", "output.txt"));
                // Do something fantastic with this file!!!
                // etc.
                byte magnificentByte = 1;
                os.write(magnificentByte);
            } catch (FileNotFoundException fnfe) {
                // Do something about this file not being found.
            } catch (IOException ioe) {
                // Egad, there's been an exception! Do something!!!
            } finally {
                if (null != os) {
                    Flushables.flushQuietly(os);
                    Closeables.closeQuietly(os);
                }
            }
        }
    }

    public static class Fluents {
        // Builders and Fluent APIs
        // http://en.wikipedia.org/wiki/Fluent_interface
        // +More expressive, type-safe, and easier to use.
        // -Harder to write, and some code formatters choke.
        public void testFluent() {
            Order order = new Order();
            order.setItemName("lattes");
            order.setQuantity(2);
            order.pay(Currency.getInstance("USD"));

            new FluentOrder()
                    .withItem("lattes")
                    .withQuantity(2)
                    .pay(Currency.getInstance("USD"));

            new OrderBuilder()
                    .withItem("lattes")
                    .withQuantity(2)
                    .pay(Currency.getInstance("USD"));
        }

        class Order {
            public void setItemName(String item) {
            }

            public void setQuantity(int quantity) {
            }

            public boolean pay(Currency currenty) {
                return true;
            }
        }

        class FluentOrder {
            private String item;
            private int quantity;

            public FluentOrder withItem(String item) {
                this.item = item;
                return this;
            }

            public FluentOrder withQuantity(int quantity) {
                this.quantity = quantity;
                return this;
            }

            boolean pay(Currency currency) {
                // pay order
                return true;
            }
        }

        class OrderBuilder {
            QuantityHolder withItem(String item) {
                return new QuantityHolder(item);
            }
        }

        class QuantityHolder {
            private final String item;

            public QuantityHolder(String item) {
                this.item = item;
            }

            OrderFiller withQuantity(int quantity) {
                return new OrderFiller(item, quantity);
            }
        }

        class OrderFiller {
            private final String item;
            private final int quantity;

            OrderFiller(String item, int quantity) {
                this.item = item;
                this.quantity = quantity;
            }

            boolean pay(Currency currency) {
                /* pay order... */return true;
            }
        }
    }

    public class Disposal {
        // Function objects http://en.wikipedia.org/wiki/Function_object
        // +Safe, Easier in Java 8 and Groovy
        // -Verbose
        public class OldResourceProvider {
            private final ReadWriteLock lock = new ReentrantReadWriteLock();
            private final Map<String, String> data = new HashMap<String, String>();

            public String getResource(String key) throws Exception {
                lock.readLock().lock();
                try {
                    return data.get(key);
                } finally {
                    lock.readLock().unlock();
                }
            }

            public void refresh() throws Exception {
                lock.writeLock().lock();
                try {
                    // reload settings...
                } finally {
                    lock.writeLock().unlock();
                }
            }
        }

        public class NewResourceProvider {
            private final ResourceController controller = new ResourceController();
            private final Map<String, String> data = new HashMap<String, String>();

            public String getResource(final String key) throws Exception {
                return controller.invokeReader(new Callable<String>() {
                    public String call() throws Exception {
                        return data.get(key);
                    }
                });
            }

            public void refresh() throws Exception {
                controller.invokeWriter(new Callable<Void>() {
                    public Void call() throws Exception {
                        // reload settings
                        return null;
                    }
                });
            }
        }

        public class ResourceController {
            private final ReadWriteLock lock = new ReentrantReadWriteLock();

            public <T> T invokeReader(Callable<T> reader) throws Exception {
                lock.readLock().lock();
                try {
                    return reader.call();
                } finally {
                    lock.readLock().unlock();
                }
            }

            public <T> T invokeWriter(Callable<T> writer) throws Exception {
                lock.writeLock().lock();
                try {
                    return writer.call();
                } finally {
                    lock.writeLock().unlock();
                }
            }
        }
    }

    // Java 7 has general availability 2011/07/28
    // http://openjdk.java.net/projects/jdk7/
    // +Soon to be widely used; Succinct code
    // -No Lambdas, :(
    public void testJava6() throws IOException {
        // Traditional error handling
        try {
            doSomething();
        } catch (UnsupportedOperationException e) {
            handleError(e);
        } catch (IllegalStateException e) {
            handleError(e);
        } catch (IllegalArgumentException e) {
            handleError(e);
        }

        // Modern error handling in Java 7
        // try {
        // doSomething();
        // } catch (UnsupportedOperationException
        // | IllegalStateException
        // | IllegalArgumentException e) {
        // handleError(e);
        // }

        String path = "~/.gitconfig";
        int read = -1;
        FileReader reader = new FileReader(path);
        try {
            read = reader.read();
            assert read > 0;
        } finally {
            reader.close();
        }

        // Modern resource management in Java 7
        // try (FileReader reader = new FileReader(path)) {
        // read = reader.read();
        // }

        // class MyResource implements AutoCloseable {
        // String getResource() {
        // return "some resource";
        // }
        //
        // @Override
        // public void close() throws Exception {
        // // closing
        // }
        // }
        //
        // try (MyResource resource = new MyResource()) {
        // return resource.getResource();
        // }
    }

    private static void doSomething() {
    }

    public static void handleError(Exception e) {
    }
}
