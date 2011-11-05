package com.algorist;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static java.lang.System.out;

import java.math.BigInteger;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.NotImplementedException;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

// Heapsort competes with quicksort, another very efficient nearly-in-place sort algorithm
// Quicksort is typically somewhat faster, due to better cache behavior and other factors.
// The worst-case running time for quicksort is makes it unacceptable for large data sets.
// Heapsort relies strongly on random access, and its poor locality of reference requires
// efficient random access to be practical.
// Mergesort is a stable sort, unlike quicksort and heapsort, and can be easily adapted
// to operate on linked lists and very large set on slow media with long access time.
// Heapsort requires only @(1) auxiliary space instead of Mergesort @(n) auxiliary space,
// quicksort with in-place partitioning and tail recursion uses only O(log n) space.

public class Problems {
    public static class Feb21st2011AtWestlake {
        // There were 4 interviewers in a day loop; 40 minutes are assigned each interviewer.
        // 3 of interviewers challenged Erin with 6 technical problems to design, code, and test.
        // 1 of interviewers challenged Erin with 3+ behavioral interview questions, e.g. resolving conflicts.

        // [Mike] Can you write me a program that returns the number of words in a string?
        // [Erin] Can I use the white board to take notes about this problem? [Mike] go ahead.
        // [Erin] First of all, I like to clarify functional requirements (cases of input and output)
        // What happens if the input is 'null'? What about an empty "" string?
        // 15+ questions worked out a bunch of input and output cases as follows: 
        // null: error out, "": 0, " a ": 1, " aa ": 1, " a b ": 2, and the like.
        // [Erin] What are delimiters? [Mike] ' ' (a space) [Erin] so, we assume, tabs and crLF yield words.
        // 
        // [Erin] Moving on, I like to clarify non-functional requirements (irrelevant to I/O)
        // Performance (time & space), security, parallel executions, s/w & h/w platforms (OS, CPU w/ L2 cache, memory)
        // Will a linear-time, constant-space algorithm work for you? [Mike] Yes, it will.
        // How big the string is? Does the string fits all in RAM, or not? Should it be partially loaded from disk?
        // How many CPUs and computers do we use? Can we use String#split function? [Mike] Let's assume that we cannot.
        // 
        // [Erin] How to indicate an error given these options?
        // to return errors, throw exceptions, set out parameters, or side effects into fields. [Mike] Your choice.
        //
        // ** side notes on Erin's standard procedures:
        // 1. to specify functionalities and non-functionalities with 15 to 30 clarifying questions.
        //    classical input and output cases, and interesting cases,
        //    boundary conditions, legal and illegal input cases, and error indications,
        //    constraints: time & space, performance, security, configuration, concurrent executions, and the like.
        // 2. to agree on function signatures and then the algorithms before you begin writing any code.
        // 3. to validate input argument, and short-circuit cases for null, empty string or collections.
        // 4. to make notes on time and space complexities in-between iterations as follows:
        public static int getWordCount(String s) {
            if (null == s) throw new IllegalArgumentException("'s' must be non-null.");
            if ("".equals(s)) return 0;

            // [Mike] what is the time and space complexity?
            // [Erin] time complexity: O(n), space complexity: O(1)
            // [Mike] can you make it better?
            // [Erin] No as there is an essential complexity where we must scan the chars in the string.
            int count = 0;
            char previous = ' ';
            for (int i = 0; i < s.length(); i++) {
                if (' ' == previous && ' ' != s.charAt(i)) {
                    count++;
                }

                previous = s.charAt(i);
            }

            return count;
        }

        // [Mike] Design test cases for your algorithm
        // [Erin] I would do positive and negative cases.
        // positive cases (also called verification tests):
        // "", " ", "  ", "aa", "  aa", "aa  ", "  ab  cd", "ab  cd  ", and the like.
        // negative cases (also called falsification tests):
        // 'null' should return an exception.
        //
        // ** side notes on Erin's standard procedures: 
        // - do not forget to note why you design test cases this way, BVA, EQ-partitioning, and PICT.
        // - do not forget to note on condition coverage, path coverage, quality risks, and FMEA.
        @Test
        public void testGetWordCount() {
            try {
                assertEquals(0, getWordCount(null));
                Assert.fail("getWordCount(null) must throw IllegalArgumentException.");
            } catch (IllegalArgumentException e) {
            }

            // 0 alternation
            assertEquals(0, getWordCount(""));
            assertEquals(0, getWordCount("  "));
            assertEquals(1, getWordCount("ab"));

            // 1 alternation
            assertEquals(1, getWordCount("  ab"));
            assertEquals(1, getWordCount("ab  "));

            // 2 alternations
            assertEquals(1, getWordCount("  ab  cd"));
            assertEquals(2, getWordCount("ab  cd  "));

            // [Erin] I assume that further iterations belong to EQ classes of 0, 1, and 2 iterations.
        }

        // [Mike] Find a string is anagram of another? [Erin] What is it?
        // [Erin] Does this function signature make sense to you? [Mike] That totally makes sense to me.
        // [Erin] What are the time and space requirements? [Mike] I like your best algorithm.
        // [Erin] Let me give you alternatives with pros and cons.
        // One approach is to keep a map where the key is a distinct char,
        // and the value is the number of occurrences of the char.
        // Count up occurrences of each char of 's', and then down with 't'.
        // Then, in the end, we must see all zeros in the map of occurrences.
        // This algorithm runs with time: O(n), and space: O(n).
        // [Erin] Another way is to sort chars in s and t and compare them.
        // Time: n*log(n) for quick-sort, merge-sort, and heap-sort.
        // Space (stack depth): log(n) for quick-sort with randomization, and tail-recursion.
        // [Erin] The other way is to generate permutations of 's' and see if they contains t.
        // Time & Space: this factorial algorithm, O(n!) is worse than exponential algorithms such as O(2^^n).
        public static boolean isAnagram(String s, String t) {
            if (s == t) return true;
            if (null == s || null == t) return false;
            if (s.length() != t.length()) return false;

            // TODO: code the approach with a occurrence counting map.
            throw new NotImplementedException();
        }

        // ** Side-talks with Mike: map, hash functions, resolving collisions, designing hash functions.
        // [Mike] why your algorithm is linear? i.e. why your hash map has constant time operations?
        // [Erin] I assume that we use well designed hash function. blah, bash, bash, Java's String hash uses Multiply 31.
        // [Mike] How would you design a hash function for 26 letters (lower case 'a' - 'z').
        // [Erin] Multiply by 29, that is a prime number greater than 26; I don't have Ph. D on number theories, do you?

        // [Todd] Write a function that returns a number that is occurring odd number of times in an integer array.
        // ** like usual, specified functionalities & non-functionalities, explored alternatives w/ trade-offs.
        // the alternatives: 
        // - counting occurrences on map, and then scanning (time & space: linear)
        // - alternating bit-array, and then scanning (same as the counting map)
        // - sorting & scanning (time: n*log(n), space: log(n) stack depth)
        public static int findOddOccuringNumber(int[] ia) {
            throw new NotImplementedException();
        }

        // [Todd] Serialize and de-serialize a binary tree into a string; this problem is too big to solve in 20 minutes.
        // ** like usual, specified functionalities & non-functionalities; described approach w/ infix and postfix expressions.
        // [Todd] Serialization seems too straightforward w/ your approach; let us move on to implementing de-serialization.
        // ** talks on deriving recurrence relationship; spent 5+ minutes wrestling with base and recursive cases.
        // Erin turned down Sam's suggestion in the middle, as Sam's approach is to store a complete binary tree with nulls.
        // Todd agreed w/ Erin that a sparse binary tree uses up a memory that is 2 to the power of height at the worst case.
        public static void fromStrings(BNode<Character> root, String inorder, String preorder, int left, int right) {
            root.item = preorder.charAt(left);
            int pivot = inorder.indexOf(root.item);
            int r = left + 1;
            for (; r <= right && -1 == inorder.indexOf(preorder.charAt(r), pivot + 1); r++) {
            }

            if (left + 1 <= r - 1) {
                fromStrings(root.left = BNode.of(root), inorder, preorder, left + 1, r - 1);
            }

            if (r <= right) {
                fromStrings(root.right = BNode.of(root), inorder, preorder, r, right);
            }
        }

        //        a
        //     b      d
        //  c       e   g
        //        f       h
        //
        // preorder: a b c d e f g h
        // inorder : c b a f e d g h
        // 
        // ** side-notes: Erin verified the dev code w/ a test case when she came home after the interview as follows:
        @Test
        public void testFromStrings() {
            // the infix and prefix are given as a serialized form of a tree.
            BNode<Character> root = new BNode<Character>();
            fromStrings(root, "cbafedgh", "abcdefgh", 0, 7);

            assertEquals('a', root.item.charValue());
            assertEquals('b', root.left.item.charValue());
            assertEquals('d', root.right.item.charValue());
            assertEquals('c', root.left.left.item.charValue());
            assertEquals(null, root.left.right);
            assertEquals('e', root.right.left.item.charValue());
            assertEquals('g', root.right.right.item.charValue());
            assertEquals('f', root.right.left.left.item.charValue());
            assertEquals(null, root.right.left.right);
            assertEquals(null, root.right.right.left);
            assertEquals('h', root.right.right.right.item.charValue());
        }

        // [Naga] Find if a directed acyclic graph (DAG) has a cycle or not.
        // ** there were specification, class designs of Edge, Graph, and then DFS.
        public boolean hasCycle() {
            try {
                dfs();
                return false;
            } catch (IllegalStateException e) {
                return true;
            }
        }

        public void dfs() {
            // TODO:
            // - set and clear a flag on 'processed' boolean array when you enter and leave a node.
            // - throw an illegal state exception if 'processed' is true when crossing to child nodes.
            // ** side note: there was a logical bug on one line, and worked out the fix w/ Naga's help.

            throw new IllegalStateException("This is not a tree as there is a cyle");
        }

        // [Naga] Design classes for a Tetris game
        // Originally, Erin was asked to design classes for expressions such as (1 * 2 + 3 ** 4).
        // There was no need to implement any functions, but the overall class design was expected.
        // ** some clarifications, and then a number of classes, enums, fields, and methods.
        public static interface TetrisEngine {
            // TODO:
            // fields such as currentPiece, queuedPieces, boardArray, gameState
            //   gamesPlayed, bestScores, currentLevel, currentScore, currentSpeed.
            // methods: setUp, takeDown, clear, movePiece, updateState, removeLines.
        }

        public static interface TetrisPiece {
            // fields such as location, direction, shape, and color.
        }

        public static enum TetrisShape {
            Line, Square, ZigZag, ZagZig, L, InverseL
        }
    }

    private static final Random random = new Random();

    public interface Action<V> {
        void apply(V v);
    }

    public interface Action2<V, W> {
        void apply(V v, W w);
    }

    public static class Edge {
        public int y;
        public int weight;
        public static Edge of(int y, int weight) { Edge e = new Edge(); e.y = y; e.weight = weight; return e; }
    }

    public static class Graph {
        public Stack<Integer> topologicalSort;
        public List<Edge>[] edges;
        int verticeCount; 
        int edgeCount;
        boolean directed;

        boolean[] discovered = new boolean[edges.length];
        boolean[] processed = new boolean[edges.length];
        int[] parents = new int[edges.length];
        int[] colors = new int[edges.length];

        public void addEdge(int x, int y, int weight) {
            edges[x].add(Edge.of(y, weight));
            if (!directed) {
                edges[y].add(Edge.of(x, weight));
            }
        }

        public void dfs(int v, Action<Integer> enter, Action2<Integer, Integer> cross, Action<Integer> leave) {
            discovered[v] = true;
            enter.apply(v);

            for (Edge e : edges[v]) {
                if (!discovered[e.y]) {
                    parents[e.y] = v;
                    cross.apply(v, e.y);
                    dfs(e.y, enter, cross, leave);
                } else if (!processed[e.y] || directed) {
                    cross.apply(v, e.y);
                }
            }

            leave.apply(v); // leave might be an action for topological sort.
            processed[v] = true;
        }

        private Action<Integer> sort = new Action<Integer>() {
            public void apply(Integer v) {
                topologicalSort.add(v);
            }
        };

        public void bfs(int start, Action<Integer> enter, Action2<Integer, Integer> cross, Action<Integer> leave) {
            Queue<Integer> queue = new LinkedList<Integer>();
            queue.add(start);
            discovered[start] = true;

            while (!queue.isEmpty()) {
                int v = queue.remove();
                enter.apply(v);
                processed[v] = true;

                for (Edge e : edges[v]) {
                    if (!processed[e.y] || directed) {
                        cross.apply(v, e.y);
                    }

                    if (!discovered[e.y]) {
                        queue.add(e.y);
                        discovered[e.y] = true;
                        parents[e.y] = v;
                    }
                }

                leave.apply(v);
            }
        }

        public int twoColorComponents() {
            int count = 0;
            for (int v = 0; v < edges.length; v++) {
                if (!discovered[v]) {
                    count++;
                    colors[v] = 0;
                    bfs(v, null, complementColor, null);
                }
            }

            return count;
        }

        private Action2<Integer, Integer> complementColor = new Action2<Integer, Integer>() {
            public void apply(Integer v, Integer y) {
                if (colors[v] == colors[y]) {
                    throw new IllegalStateException(String.format("This is not a bipartitle due to (%d, %d).", v, y));
                }

                colors[y] = ~colors[v];
            }
        };

        private Action2<Integer, Integer> throwIfCyclic = new Action2<Integer, Integer>() {
            public void apply(Integer v, Integer y) {
                if (v == y) {
                    throw new IllegalStateException();
                }
            }
        };

        public void prim(int start) {
            boolean[] isInTrees = new boolean [this.edges.length];
            int[] distances = new int[this.edges.length];
            for (int v = 0; v < this.edges.length; v++) {
                isInTrees[v] = false;
                distances[v] = Integer.MAX_VALUE;
                parents[v] = -1;
            }

            distances[start] = 0;
            for (int v = start; !isInTrees[v]; ) {
                isInTrees[v] = true;
                for (Edge e : this.edges[v]) {
                    if (!isInTrees[e.y] && distances[e.y] > e.weight) {
                        distances[e.y] = e.weight;
                        parents[e.y] = v;
                    }
                }

                v = 0;
                int distance = Integer.MAX_VALUE;
                for (int x = 0; x < this.edges.length; x++) {
                    if (!isInTrees[x] && distance > distances[x]) {
                        v = x;
                        distance = distances[x];
                    }
                }
            }
        }

        public void dijkstra(int start) {
            boolean[] isInTrees = new boolean[this.edges.length];
            int[] distances = new int[this.edges.length];

            for (int v = 0; v < this.edges.length; v++) {
                isInTrees[v] = false;
                distances[v] = Integer.MAX_VALUE;
                parents[v] = -1;
            }

            distances[start] = 0;
            for (int v = start; !isInTrees[v]; ) {
                isInTrees[v] = true;
                for (Edge e : this.edges[v]) {
                    if (distances[e.y] > distances[v] + e.weight) {
                        distances[e.y] = distances[v] + e.weight;
                        parents[e.y] = v;
                    }
                }

                v = 0;
                int distance = Integer.MAX_VALUE;
                for (int x = 0; x < this.edges.length; x++) {
                    if (!isInTrees[x] && distance > distances[x]) {
                        v = x;
                        distance = distances[x];
                    }
                }
            }
        }

        public void floyd(int[][] matrix) { // non-edges has Integer.MAX_VALUE
            for (int k = 0; k < matrix.length; k++) {
                for (int i = 0; i < matrix.length; i++) {
                    for (int j = 0; j < matrix.length; j++) {
                        int throughK = matrix[i][k] + matrix[k][j];
                        if (throughK < matrix[i][j]) {
                            matrix[i][j] = throughK;
                        }
                    }
                }
            }
        }

        public void printPath(int start, int end) {
            if (start == end || -1 == end) {
                System.out.print(String.format("%d ", start));
            } else {
                printPath(start, parents[end]);
                System.out.print(String.format("%d", end));
            }
        }
    }

    public static class LinkedDictionary<K, V> {
        private final LinkedList<SimpleEntry<K, V>> list = new LinkedList<SimpleEntry<K, V>>();
        private final Map<K, LinkedList.Node<SimpleEntry<K, V>>> map = new HashMap<K, LinkedList.Node<SimpleEntry<K, V>>>();
        private final int capacity = 1024;

        public void trimEldest() {
            SimpleEntry<K, V> eldest = this.list.getFirst();
            this.map.remove(eldest.getKey());
            this.list.removeFirst();
        }

        private void access(LinkedList.Node<SimpleEntry<K, V>> node) {
            this.list.remove(node);
            this.list.addLast(node);
        }

        public void put(K key, V value) {
            this.remove(key);
            this.list.addLast(new SimpleEntry<K, V>(key, value));
            this.map.put(key, this.list.getLastNode());

            while (this.map.size() > this.capacity) {
                this.trimEldest();
            }
        }

        public V get(K key) {
            if (!map.containsKey(key)) {
                return null;
            }

            access(map.get(key));
            return map.get(key).getValue().getValue();
        }

        public boolean remove(K key) {
            if (!map.containsKey(key)) {
                return false;
            }

            list.remove(map.get(key));
            map.remove(key);
            return true;
        }

        static class LinkedList<T> {
            public static class Node<T> {
                public T getValue() { throw new UnsupportedOperationException(); }
                public Node<T> getNext() { throw new UnsupportedOperationException(); }
                public Node<T> getPrevious() { throw new UnsupportedOperationException(); }
            }

            public boolean isEmpty() { throw new UnsupportedOperationException(); }
            public T getFirst() { throw new UnsupportedOperationException(); }
            public Node<T> getLastNode() { throw new UnsupportedOperationException(); }
            public void remove(Node<T> t) { throw new UnsupportedOperationException(); }
            public void removeFirst() { throw new UnsupportedOperationException(); }
            public void addLast(Node<T> t) { throw new UnsupportedOperationException(); }
            public void addLast(T t) { throw new UnsupportedOperationException(); }
        }
    }

    @Test
    public void testPrime() {
        assertEquals(919, prime(912));
        assertEquals(2, prime(1));
        assertEquals(2, prime(2));
        assertEquals(3, prime(3));
        assertEquals(5, prime(4));
        assertEquals(17, prime(15));
    }

    /*
     * This function returns a number when the probability that the number is prime exceeds (1 - 0.5^certainty).
     */
    public static long prime(long n) {
        return prime(n, 5); // the is 96.875% prime.
    }

    public static long prime(long n, int certainty) {

        if (n <= 2) return 2;
        if (n <= 3) return 3;

        long lnN = (int)Math.ceil(Math.log(n));
        for (long prime = (0 == n % 2 ? 1 + n : n); prime < n + lnN * 3/2; prime += 2) {
            boolean found = true;
            for (int i = 0; i < certainty; i++) { // an iteration of rabin miller tests.
                BigInteger a = BigInteger.valueOf(1 + random.nextInt((int)n - 1));
                BigInteger a2theNminus1 = a; // a uniform random on (1, n - 1)
                for (int e = 1; e < prime - 1; e++) {
                    a2theNminus1 = a2theNminus1.multiply(a);
                }

                if (!BigInteger.ONE.equals(a2theNminus1.mod(BigInteger.valueOf(prime)))) {
                    found = false;
                    break;
                }
            }

            if (found) {
                return prime;
            }
        }

        return -1;
    }

    public int lastIndexOfByRabinKarp(String t, String p) {
        char[] chars = t.toCharArray();
        int n = t.length();
        int m = p.length();

        int hashP = hash(p.toCharArray(), 0, m);
        int hashT = hash(t.toCharArray(), n - m, m);
        if (hashP == hashT) { return n - m; }

        int a_to_m_minus_1 = 1; // 31 ^ (m - 1)
        for (int i = 0; i < m - 1; i++) {
            a_to_m_minus_1 = (a_to_m_minus_1 << 5) - a_to_m_minus_1;
        }

        for (int offset = n - m - 1; offset >= 0; offset--) {
            hashT = hash(chars, offset, m, a_to_m_minus_1, hashT);
            if (hashP == hashT) { return offset; }
        }

        return -1;
    }

    public static int hash(char[] chars, int offset, int m) {
        int hash = 0;
        for (int i = m - 1; i >= 0; i--) {
            hash = (hash << 5) - hash;
            hash = hash + chars[offset + i];
        }

        return hash;
    }

    public static int hash(char[] chars, int offset, int m, int a_to_m_minus_1, int hash) {
        hash = hash - a_to_m_minus_1 * chars[offset + m];
        hash = hash * 31; // or, (hash << 5) - hash;
        hash = hash + chars[offset];
        return hash;
    }

    public static class Queueable<T> {
        Stack<T> stk1 = new Stack<T>();
        Stack<T> stk2 = new Stack<T>();

        void push(T e) {
            stk1.push(e);
        }

        T pop() {
            if (stk2.size() > 0) {
                while (stk1.size() == 0) {
                    stk2.push(stk1.pop());
                }
            }

            T e = stk2.pop();
            return e;
        }

    }

    public static class MinStack<T extends Comparable<T>> {
        private T mininum;
        private Stack<T> stack = new Stack<T>();

        public T getMin() {
            return mininum;
        }

        public MinStack<T> push(T element) {
            if (null == mininum || element.compareTo(mininum) <= 0) {
                stack.push(mininum);
                mininum = element;
            }

            stack.push(element);
            return this;
        }

        public T pop() {
            T element = stack.pop();
            if (element.compareTo(mininum) == 0) {
                mininum = stack.pop();
            }

            return element;
        }
    }

    public static int[] products(int... numbers) {
        int[] prefixProducts = new int[numbers.length];
        prefixProducts[0] = 1;
        for (int i = 1; i < numbers.length; i++) {
            prefixProducts[i] = prefixProducts[i-1] * numbers[i-1];
        }

        int[] postfixProducts = new int[numbers.length];
        postfixProducts[numbers.length - 1] = 1;
        for (int i = numbers.length - 2; i >= 0; i--) {
            postfixProducts[i] = postfixProducts[i+1] * numbers[i+1];
        }

        int[] products = new int[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            products[i] = prefixProducts[i] * postfixProducts[i];
        }

        return products;
    }

    // http://n1b-algo.blogspot.com/2009/01/string-permutations.html
    public static Iterable<String> permutate(char[] chars, int k) {
        if (chars.length == k) {
            return ImmutableList.of(String.valueOf(chars));
        }

        List<String> permutations = new ArrayList<String>();
        BitSet bits = new BitSet(128);
        for (int i = k; i < chars.length; i++) {
            if (bits.get(chars[i])) {
                continue;
            } else {
                bits.set(chars[i]);
                swap(chars, k, i);
                Iterables.addAll(permutations, permutate(chars, k + 1));
                swap(chars, k, i);
            }
        }

        return permutations;
    }

//    public static IEnumerable<string> Combine(string s)
//    {
//        return combine(s.ToCharArray(), new StringBuilder(), 0); // additional d.s.; recursion level
//    }
//
//    private static IEnumerable<string> combine(char[] chars, StringBuilder sb, int level)
//    {
//        // base cases
//        for (int i = level; i < chars.Length; i++)
//        {
//            sb.Append(chars[i]);
//            yield return sb.ToString();
//            foreach (string s in combine(chars, sb, i + 1)) yield return s; // recursive cases
//            sb.Length = sb.Length - 1;
//        }
//    }
//
//    public static IEnumerable<string> TelephoneWords(params int[] digits)
//    {
//        char[] chars = new char[digits.Length];
//        Dictionary<string, string> dict = new Dictionary<string, string>();
//        telephoneWords(digits, chars, dict, 0); // additional d.s. and recursion level
//        return dict.Keys;
//    }
//
//    private static void telephoneWords(int[] digits, char[] chars, Dictionary<string, string> dict, int level)
//    {
//        if (level == digits.Length)
//        {
//            string s = new string(chars);
//            dict[s] = s;
//            return;
//        }
//        for (int i = 0; i < 3; i++)
//        {
//            if (digits[level] == 0 || digits[level] == 1)
//                chars[level] = (char)((int)'0' + digits[level]);
//            else
//                chars[level] = (char)((int)'A' + 3 * (digits[level] - 2) + i);
//            telephoneWords(digits, chars, dict, level + 1);
//        }
//    }


    public static int divide(int dividend, int divisor) {
        int bit = 1;
        while (divisor <= dividend) {
            divisor <<= 1;
            bit <<= 1;
        }

        int quotient = 0;
        while (divisor > 0) {
            divisor >>= 1;
            bit >>= 1;
            if (dividend >= divisor) {
                dividend -= divisor;
                quotient |= bit;
            }
        }

        return quotient;
    }

    public static int[] kadane(int[][] m) {
        int[][] prefixSums = new int[m.length][m[0].length];
        for (int i = 0; i < m[0].length; i++) {
            prefixSums[0][i] = m[0][i];
            for (int j = 1; j < m.length; j++) {
                prefixSums[j][i] = prefixSums[j - 1][i] + m[j][i];
            }
        }

        int maxTop = 0, maxLeft = 0, maxBottom = 0, maxRight = 0, maxSum = m[0][0];
        for (int top = 0; top < m[0].length; top++) {
            for (int bottom = top; bottom < m[0].length; bottom++) {
                int minimum = 0, maximum = 0;
                for (int left = 0, right = 0; right < m.length; right++) {
                    if (top == 0) {
                        maximum += prefixSums[bottom][right];
                    } else {
                        maximum += prefixSums[bottom][right] - prefixSums[top - 1][right];
                    }

                    if (maximum <= minimum) {
                        minimum = maximum;
                        left = right;
                    }

                    if (maximum - minimum > maxSum) {
                        maxTop = top;
                        maxBottom = bottom;
                        maxLeft = left;
                        maxRight = right;
                        maxSum = maximum - minimum;
                    }
                }
            }
        }

        return new int[] { maxSum, maxTop, maxLeft, maxBottom, maxRight };
    }

    // Kadane's algorithm http://en.wikipedia.org/wiki/Maximum_subarray_problem
    public static int[] kadane(int... a) {
        int maxHead = 0, maxTail = 0, maxSum = a[0];
        for (int head = 0, tail = 1, sum = a[0]; tail < a.length; tail++) {
            sum = sum + a[tail];
            if (sum > maxSum) {
                maxHead = head; maxTail = tail; maxSum = sum;
            }

            if (0 > sum) {
                head = tail + 1; sum = 0;
            }
        }

        return new int[] { maxSum, maxHead, maxTail };
    }

    public static String toExcelColumn(int n) {
        //   26 cases :   A -   Z
        // 26^2 cases :  AA -  ZZ
        // 26^3 cases : AAA - ZZZ

        int cases = 26;
        int k = 0;
        for (; n > cases; k++) { // k is the level of recursion.
            n -= cases;
            cases *= 26;
        }

        n -= 1; // n falls between 0 and (26^k - 1).
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= k; i++) {
            sb.insert(0, (char)(n % 26 + 'A'));
            n /= 26;
        }

        return sb.toString();
    }

    public static int fromExcelColumn(String s) {
        int columns = 0;
        for (int k = 1, cases = 26; k < s.length(); k++, cases *= 26) {
            columns += cases;
        }

        for (int k = s.length() - 1, cases = 1; k >= 0; k--, cases *= 26) {
            columns += cases * (int)(s.charAt(k) - 'A');
        }

        return columns + 1;
    }

    public static void subsets(int n) {
        Boolean a[] = new Boolean[n];
        backtrack(a, 0, n - 1);
    }

    public static boolean isSolution(Boolean a[], int k, int level) {
        return k == level;
    }

    public static void backtrack(Boolean a[], int k, int level) {
        if (isSolution(a, k, level)) {
            System.out.print(Joiner.on(" ,").join(a));
        } else {
            boolean candidates[] = {true, false}; // constructCandidates(a, k, n);
            for (int i = 0; i < candidates.length; i++) {
                a[k] = candidates[i];
                // move
                backtrack(a, k, level);
                // back
            }
        }
    }

    public static int median3(int[] elements, int left, int right) {
        int center = (left + right) / 2;
        if (elements[left] > elements[center]) swap(elements, left, center);
        if (elements[left] > elements[right]) swap(elements, left, right);
        if (elements[center] > elements[right]) swap(elements, center, right);
        swap(elements, center, right - 1); // place pivot at position right - 1;
        return elements[right - 1];
    }

    public static void quicksort(int[] elements, int left, int right) {
        if (left >= right) return;

        int pivot = elements[right];
        int i = left;
        int j = right - 1;

        while (true) {
            while (elements[i] < pivot) i++;
            while (j > 0 && elements[j] > pivot) j--;
            if (i >= j) break;
            swap(elements, i, j);
        }

        // to print the k-th smallest, if (i == k) System.out.print(elements[i]);
        swap(elements, i, right); // restore the pivot
        quicksort(elements, left, i - 1);
        quicksort(elements, i + 1, right);
    }

    public static void mergeSort(int[] elements) {
        int[] temp = new int[elements.length];
        mergeSort(elements, temp, 0, elements.length - 1);
    }

    private static void mergeSort(int[] input, int[] temp, int left, int right) {
        if (left < right) {
            int center = (left + right) / 2;
            mergeSort(input, temp, left, center);
            mergeSort(input, temp, center + 1, right);
            merge(input, temp, left, center + 1, right);
        }
    }

    private static void merge(int[] input, int[] temp, int left, int right, int rightEnd) {
        int leftEnd = right - 1;
        int leftBegin = left;
        int position = 0;
        while (left <= leftEnd && right <= rightEnd) {
            if (input[left] <= input[right]) {
                temp[position++] = input[left++];
            } else {
                temp[position++] = input[right++];
            }
        }

        while (left <= leftEnd) {
            temp[position++] = input[left++];
        }

        while (right <= rightEnd) {
            temp[position++] = input[right++];
        }

        System.arraycopy(temp, 0, input, leftBegin, rightEnd - leftBegin + 1);
    }

    public static class SNode<T> implements Comparable<SNode<T>> {
        public T item;
        public SNode<T> next;

        public static <T> SNode<T> reverse(SNode<T> current) {
            SNode<T> result = null;
            while (null != current) {
                SNode<T> save = current.next;
                current.next = result;
                result = current;
                current = save;
            }

            return result;
        }

        public static <T> SNode<T> insertIntoSerted(SNode<T> head, SNode<T> insert) {
            if (null == insert) return head;
            if (null == head) return insert;

            SNode<T> current = head;
            SNode<T> previous = null;

            for (; null != current; previous = current, current = current.next) {
                if (insert.compareTo(current) <= 0) {
                    break;
                }
            }

            if (null == previous) {
                head = insert;
            } else {
                previous.next = insert;
            }

            insert.next = current;
            return head;
        }

        // Delete (in a linear time) : Time: O(n), Space: O(1)
        // We return existing head when non-head is deleted or new head when head is deleted.
        public static <T> SNode<T> Delete(SNode<T> head, SNode<T> delete) {
            if (head == null) return head;
            if (head == delete) return head.next;

            for (SNode<T> prev = head; prev.next != null; prev = prev.next) {
                if (prev.next == delete) {
                    prev.next = prev.next.next;  break;
                }
            }

            return head;
        }

        public static <T> void deleteInConstantTime(SNode<T> current) {
            // We mark the current node for 'deleted' in cases
            // - it is the tail, or
            // - its next node is marked for 'deleted'.
            boolean nextNodeNotAvailable = null == current.next || null == current.next.item;
            if (nextNodeNotAvailable) {
                current.item = null;
                return;
            }

            // We copy the next node's data to the current node.
            // Then, delete the next node from the memory space.
            SNode<T> next = current.next;
            current.item = next.item;
            current.next = next.next;

            // We move on to the next node and collect garbage.
            while (null != current.next && null == current.next.item) {
                next = current.next;
                current.next = next.next;
            }
        }

        // This function swaps every two nodes in a single linked list.
        // Given 1 -> 2 -> 3 -> 4 -> 5, it returns 2 -> 1 -> 4 -> 3 -> 5.
        public static <T> SNode<T> swapEveryTwo(SNode<T> head) {
            if (null == head) throw new NullPointerException("head");
            if (null == head.next) return head;

            SNode<T> current = head;
            head = current.next;
            while (null != current && null != current.next) {
                SNode<T> next = current.next;
                SNode<T> next2 = current.next.next;

                next.next = current;
                current.next = null != next2 ? next2.next : next2;
                current = next2;
            }

            return head;
        }

        public static <T> SNode<T> findNthLastNode(SNode<T> current, int offset) {
            Queue<SNode<T>> q = new LinkedList<SNode<T>>();
            for (; current != null; current = current.next) {
                if (q.size() > offset) q.remove();
                q.add(current);
            }

            return q.size() < offset ? null : q.peek();
        }

        /// <summary>
        /// Let's assume the length of the cycle is M and the length of the rest of the linked list is L.
        /// Define the first node in the cycle (called joint) has position 0 and suppose two pointers
        /// p1 and p2 meet at position p where p1 has walked (L + k1 * M * p) and p2 (L + K2 * M * p).
        /// In other words, they meet at p = (k1 * m - L) mod M. p1 will be at position 0 after L steps.
        /// </summary>
        public static <T> SNode<T> fixCycle(SNode<T> current) {
            SNode<T> p1 = current; // walk at normal speed
            SNode<T> p2 = current; // walk at double speed
            SNode<T> joint = current; // first node of the cycle
            SNode<T> chain = null; // chain node to fix up

            while (p2 != null && p2.next != null) {
                p2 = p2.next.next;
                p1 = p1.next;
                if (p1 == p2) break;
            }

            if (p2 == null || p2.next == null) return null;

            while (p1 != joint) {
                joint = joint.next;
                chain = p1;
                p1 = p1.next;
            }

            if (chain != null) chain.next = null;
            return joint;
        }


        // This function sums two linked lists where each node holds a digit.
        // The operands and result are linked lists as we sum 9182 + 517 = 9699.
        // For example, given two linked lists, 9 -> 1 -> 8 -> 2 and 5 -> 1 ->
        // 7,
        // this function is expected to return a linked list, 9 -> 6 -> 9 -> 9.
        public static SNode<Integer> sumLinkedNumbers(SNode<Integer> lhs, SNode<Integer> rhs) {
            lhs = reverse(lhs);
            rhs = reverse(rhs);

            SNode<Integer> head = null;
            SNode<Integer> prev = null;
            int tens = 0; // carry over
            while (null != lhs || null != rhs) {
                int ones = 0;

                if (null == lhs) ones = rhs.item;
                else if (null == rhs) ones = lhs.item;
                else {
                    ones = (lhs.item + rhs.item) % 10;
                }

                if (null != head) { head = new SNode<Integer>(); head.item = ones + tens; prev = head; }
                else { prev.next = new SNode<Integer>(); prev.next.item = ones + tens; prev = prev.next; }
                tens = (null != lhs && null != rhs) ? (lhs.item + rhs.item) / 10 : 0; // carry over
                lhs = lhs.next;
                rhs = rhs.next;
            }

            if (tens > 0) {
                prev.next = new SNode<Integer>(); prev.next.item = tens;
            }

            return reverse(head);
        }

        public SNode<T> mergeSort(SNode<T> p) {
            if (null == p || null == p.next) return p;
            SNode<T> q = partition(p);
            p = mergeSort(p);
            q = mergeSort(q);
            p = merge(p, q);
            return p;
        }

        public SNode<T> middle(SNode<T> p) {
            SNode<T> p1 = p;
            SNode<T> p2 = p.next;
            while (null != p2 && null != p2.next) {
                p2 = p2.next.next;
                p2 = p1.next;
            }

            return p1;
        }

        public SNode<T> partition(SNode<T> p) {
            p = middle(p);
            SNode<T> q = p.next;
            p.next = null;
            return q;
        }

        public SNode<T> merge(SNode<T> p, SNode<T> q) {
            SNode<T> head = null, r = null;
            while (null != p && null != q) {
                SNode<T> next;
                if (p.compareTo(q) < 0) {
                    next = p; p = p.next;
                } else {
                    next = q; q = q.next;
                }

                if (null == head) {
                    head = r = next;
                } else {
                    r.next = next; r = r.next;
                }
            }

            if (null == p) r.next = q;
            if (null == q) r.next = p;
            return head;
        }

        @Override
        public int compareTo(SNode<T> o) {
            return 0;
        }
    }

    public static class DNode<T> {
        public T item;
        public DNode<T> next;
        public DNode<T> prev;

        public static <T> DNode<T> reverse(DNode<T> current) {
            if (null == current) return current;

            while (null != current.next) {
                DNode<T> save = current.next;
                current.next = current.prev;
                current.prev = save;
                current = save;
            }

            return current;
        }

        public static <T> void delete(DNode<T> delete) {
            if (delete.prev != null) delete.prev.next = delete.next;
            if (delete.next != null) delete.next.prev = delete.prev;
        }

        // This function clones a given linked list,
        // where 'next' link forms a linear linked list,
        // but, 'prev' link points to any node or nothing (which means 'null').
        //
        // e.g., 'next' link forms a linear linked list. 'prev' link forms a
        // graph.
        // 'next' : A -> B -> C -> D -> NULL (linear, ordered)
        // 'prev' : A -> C, B -> NULL, C -> D, D -> C (disordered)
        DNode<Integer> clone(DNode<Integer> head) {
            // We short-circuit known cases.
            if (null == head)
                return null;

            // We stores mappings from source nodes to target nodes.
            Map<DNode<Integer>, DNode<Integer>> map = new HashMap<DNode<Integer>, DNode<Integer>>();
            DNode<Integer> source = head; // It keeps a source node.
            DNode<Integer> target = null; // It keeps a target node.
            DNode<Integer> previous = null; // It keeps a previous node.
            DNode<Integer> result = null; // It keeps a cloned linked list.

            // Time to clone: O(n)
            while (null != source) {
                target = new DNode<Integer>(); // What if out-of-memory?
                target.item = source.item;
                target.prev = source.prev;
                target.next = null;

                if (null != previous) {
                    previous.next = target;
                } else {
                    result = target;
                }

                // We stores a mapping from a source node to a target node.
                map.put(source, target);

                // We move on to the next node.
                source = source.next;
                previous = target;
            }

            // Time to re-map: O(n)
            target = result;
            while (null != target) {
                target.prev = map.get(target.prev);
                target = target.next;
            }

            return result;
        }
    }

    public static class BNode<T extends Comparable<T>> implements Comparable<BNode<T>> {
        public T item;
        public BNode<T> left;
        public BNode<T> right;
        public BNode<T> parent;

        public static <T extends Comparable<T>> BNode<T> of(BNode<T> parent) {
            BNode<T> node = new BNode<T>();
            node.parent = parent;
            return node;
        }

        public static <T extends Comparable<T>> BNode<T> of(T item, BNode<T> left, BNode<T> right) {
            BNode<T> node = new BNode<T>();
            node.item = item;
            node.left = left;
            node.right = right;
            return node;
        }

        public static <T extends Comparable<T>> BNode<T> reverseInorder(BNode<T> current, int n, AtomicInteger k) {
            if (null == current) return current;

            reverseInorder(current.right, n, k);
            if (k.incrementAndGet() == n) {
                System.out.print(current.item);
                return current;
            }

            return reverseInorder(current.left, n, k);
        }

        // http://crackinterviewtoday.wordpress.com/2010/03/12/check-whether-given-binary-tree-is-a-bst-or-not/
        public static <T extends Comparable<T>> boolean isBST(BNode<T> current) {
            if (null == current) return true;
            if (null != current.left && maximum(current.left).compareTo(current) > 0) { return false; }
            if (null != current.right && minimum(current.right).compareTo(current) < 0) { return false; }
            if (!isBST(current.left) || !isBST(current.right)) return false;

            return true;
        }

        public static <T extends Comparable<T>> BNode<T> maximum(BNode<T> node) { while (null != node.right) { node = node.right; } return node; }
        public static <T extends Comparable<T>> BNode<T> minimum(BNode<T> node) { while (null != node.left) { node = node.left; } return node; }

        public static <T extends Comparable<T>> List<T> yieldInorder(BNode<T> current, BNode<T> left, BNode<T> right) {
            // BNode<T> ancestor = findLowestCommonAncestor(current, left, right);
            if (null ==  current) return ImmutableList.of();

            List<T> output = new ArrayList<T>();
            if (current.compareTo(left) >= 0 && current.compareTo(right) <= 0)
                output.add(current.item);
            if (current.compareTo(left) > 0)
                output.addAll(yieldInorder(current.left, left, right));
            if (current.compareTo(right) < 0)
                output.addAll(yieldInorder(current.right, left, right));
            return output;
        }

        public static <T extends Comparable<T>> BNode<T> findLowestCommonAncestor(BNode<T> current, BNode<T> left, BNode<T> right) {
            if (null == current) {
                return current;
            }

            if (current.compareTo(left) > 0 && current.compareTo(right) > 0) {
                return findLowestCommonAncestor(current.left, left, right);
            } else if (current.compareTo(left) < 0 && current.compareTo(right) < 0) {
                return findLowestCommonAncestor(current.right, left, right);
            }

            return current;
        }

        public static <T extends Comparable<T>> List<BNode<T>> yieldInorder(BNode<T> current) {
            if (null ==  current) return ImmutableList.of();

            List<BNode<T>> output = new ArrayList<BNode<T>>();
            if (null != current.left) output.addAll(yieldInorder(current.left));
            output.add(current);
            if (null != current.right) output.addAll(yieldInorder(current.right));
            return output;
        }

        public static <T extends Comparable<T>> List<BNode<T>> yieldInorderFast(BNode<T> current) {
            List<BNode<T>> output = new ArrayList<BNode<T>>();
            Stack<BNode<T>> stack = new Stack<BNode<T>>();

            while (null != current || !stack.isEmpty()) {
                if (null == current) {
                    current = stack.pop();
                    output.add(current);
                    current = current.right;
                } else {
                    stack.push(current);
                    current = current.left;
                }
            }

            return output;
        }

        public static <T extends Comparable<T>> List<BNode<T>> yieldPreorder(BNode<T> current) {
            List<BNode<T>> output = new ArrayList<BNode<T>>();
            Stack<BNode<T>> stack = new Stack<BNode<T>>();

            while (null != current || !stack.isEmpty()) {
                if (null == current) {
                    current = stack.pop();
                } else {
                    output.add(current);
                    stack.push(current.right);
                    current = current.left;
                }
            }

            return output;
        }

        public static <T extends Comparable<T>> List<BNode<T>> yieldPostorder(BNode<T> current) {
            List<BNode<T>> output = new ArrayList<BNode<T>>();
            Stack<BNode<T>> stack = new Stack<BNode<T>>();

            while (null != current || !stack.isEmpty()) {
                if (null == current) {
                    while (!stack.isEmpty() && null == stack.peek().right) {
                        current = stack.pop();
                        output.add(current);
                    }

                    current = stack.isEmpty() ? null : stack.peek().right;
                } else {
                    stack.push(current);
                    current = current.left;
                }
            }

            return output;
        }

        public static <T extends Comparable<T>> int findHeight(BNode<T> current) {
            return null == current ? -1 : 1 + Math.max(findHeight(current.left), findHeight(current.right));
        }

        public static <T extends Comparable<T>> int findDistance(BNode<T> lhs, BNode<T> rhs) {
            Map<BNode<T>, Integer> map = new HashMap<BNode<T>, Integer>();
            for (int i = 0; null != lhs; lhs = lhs.parent) {
                map.put(lhs, i++);
            }

            for (int i = 0; null != rhs; rhs = rhs.parent) {
                if (map.containsKey(rhs)) return i + map.get(rhs);
                i++;
            }

            return -1; // disjoint
        }

//        public static IEnumerable<BNode<T>> DepthFirstSearch(BNode<T> current)
//        {
//            Stack<BNode<T>> stack = new Stack<BNode<T>>();
//            stack.Push(current);
//            while (stack.Count > 0)
//            {
//                current = stack.Pop();
//                yield return current;
//                foreach (BNode<T> child in current.Children)
//                {
//                    stack.Push(child);
//                }
//            }
//        }
//
//        public static IEnumerable<BNode<T>> BreadthFirstSearch(BNode<T> current)
//        {   
//            Queue<BNode<T>> queue = new Queue<BNode<T>>();
//            queue.Enqueue(current);
//            while (queue.Count > 0)
//            {
//                current = queue.Dequeue();
//                yield return current;
//                foreach (BNode<T> child in current.Children)
//                {
//                    queue.Enqueue(child);
//                }
//            }
//        }

        @Override
        public int compareTo(BNode<T> o) {
            return this.item.compareTo(o.item);
        }

        @Override
        public String toString() {
            return item.toString();
        }
    }

    public static int weighedChoice(int... weights) {
        int sum = 0;
        for (int i = 0; i < weights.length; i++) {
            sum += weights[i];
        }

        int random = new Random().nextInt(sum);
        for (int i = 0; i < weights.length; i++, random -= weights[i]) {
            if (random < weights[i]) return i;
        }

        // http://download.oracle.com/javase/1.4.2/docs/guide/lang/assert.html
        throw new RuntimeException("This bug should go unhandled.");
    }

    public static List<Integer> reservoirSamples(Iterator<Integer> iterator, int k) {
        List<Integer> samples = new ArrayList<Integer>();
        int count = 0;
        Random random = new Random();
        while (iterator.hasNext()) {
            count++;
            if (samples.size() < k) {
                samples.add(iterator.next());
            } else {
                int s = random.nextInt(count);
                if (s < k) {
                    samples.set(s, iterator.next());
                }
            }
        }

        return samples;
    }

    public static void knuthShuffle(List<Integer> list) {
        Random random = new Random();
        for (int i = 0; i < list.size(); i++) {
            int j = i + random.nextInt(list.size() - i);
            Integer save = list.get(j);
            list.set(j, list.get(i));
            list.set(i, save);
        }
    }

    public static int rand7() {
        int rand21;
        do { 
            rand21 = 5 * (rand5() - 1); // 0, 5, ..., 20.
            rand21 += rand5(); // 1, 2, ..., 5.
        } while (rand21 > 21);

        assert rand21 >= 1 && rand21 <= 21;
        return rand21 % 7 + 1; // 1, 2, ..., 7
    }

    public static int rand5() {
        return new Random().nextInt() % 5 + 1;
    }

    @Test
    public void testMatchesWildcardByDP() {
        WildcardMatcher henry = new WildcardMatcher() {
            public boolean apply(String pattern, String text) {
                return matchesWildcardByRecursionByHenry(pattern, text);
            }
        };

        WildcardMatcher ajinkya = new WildcardMatcher() {
            public boolean apply(String pattern, String text) {
                return matchesWildcardByDPByAjinkya(pattern, text);
            }
        };

        testMatchesWildcardByDP(henry); // all passing ...
        // testMatchesWildcardByDP(ajinkya);
    }

    public interface WildcardMatcher {
        boolean apply(String pattern, String text);
    }

    public void testMatchesWildcardByDP(WildcardMatcher matcher) {
        Assert.assertFalse(matcher.apply("?", ""));
        Assert.assertTrue(matcher.apply("*", ""));
        Assert.assertTrue(matcher.apply("*c", "c"));
        Assert.assertTrue(matcher.apply("?", "a"));
        Assert.assertTrue(matcher.apply("?a?", "xab"));
        Assert.assertTrue(matcher.apply("?a*", "xabc"));
        Assert.assertTrue(matcher.apply("?a*d", "xad"));
        Assert.assertTrue(matcher.apply("*x?y", "abcxdy"));
        Assert.assertTrue(matcher.apply("*x*y", "abcxdefy"));
        Assert.assertTrue(matcher.apply("*a*b", "zab"));
        Assert.assertTrue(matcher.apply("*x*y", "axdefy"));
    }

    public boolean matchesWildcardByDPByAjinkya(String pattern, String text) {
        int n = text.length();
        int m = pattern.length();
        boolean f[][] = new boolean[n + 1][m + 1];

        f[0][0] = true;
        for (int i = 1; i <= n; i++)
            f[i][0] = false;

        for (int j = 1; j <= m; j++) {
            for (int i = 1; i <= n; i++) {
                f[i][j] = false;
                char c = pattern.charAt(j - 1);
                if ('*' == c) {
                    for (int k = 1; k <= i; k++) {
                        if (f[i - k][j - 1]) {
                            f[i][j] = true;
                            break;
                        }
                    }
                } else if ('?' == c || text.charAt(i - 1) == c) {
                    if (f[i - 1][j - 1]) {
                        f[i][j] = true;
                    }
                }
            }
        }

        return f[n][m];
    }

    public boolean matchesWildcardByRecursionByHenry(String pattern, String text) {
        if (pattern.equals(text))
            return true;

        int i = 0, j = 0;
        for (; i < pattern.length() && j < text.length(); i++, j++) {
            // recursive cases
            if ('*' == pattern.charAt(i)) {
                return matchesWildcardByRecursionByHenry(pattern.substring(i), text.substring(j + 1))
                        || matchesWildcardByRecursionByHenry(pattern.substring(i + 1), text.substring(j + 1))
                        || matchesWildcardByRecursionByHenry(pattern.substring(i + 1), text.substring(j));
            }

            if ('?' != pattern.charAt(i) && pattern.charAt(i) != text.charAt(j))
                return false;
        }

        if (i == pattern.length() && j == text.length())
            return true;
        if ('*' == pattern.charAt(i))
            return true;

        return false;
    }

    // This function matches brackets such as [], (), and {}.
    // For example, [ { ( a + b ) * -c } % d ] yields 'true'.
    public boolean bracketsMatching(String s) {
        Stack<Character> brackets = new Stack<Character>();
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            if ('(' == c) brackets.push(')');
            else if ('[' == c) brackets.push(']');
            else if ('{' == c) brackets.push('}');
            else {
                if (')' != c && ']' != c && '}' != c) continue;
                if (brackets.isEmpty() || brackets.peek() != c) return false;
                brackets.pop();
            }
        }

        return brackets.empty();
    }

    // Design a card game http://math.hws.edu/javanotes/c5/s4.html
    interface Deck {
        void suffle();
        int cardsLeft();
        Card dealCard() throws IllegalStateException; // if the deck is empty.
    }

    interface Card {
        int getSuit();
        int getValue();
    }

    interface Hand {
        void addCard(Card c);
        boolean removeCard(Card card) throws NoSuchElementException;
        Card removeCard(int position) throws IndexOutOfBoundsException;
        int getCardCount();
        void clear();
        Card getCard(int position);
        void sortBySuit();
        void sortByValue();
    }

    interface Game {
        int getGamesPlayed();
        int getScoresAchived();
        void setUp(); // new Deck() and shuffle();
        void tearDown(); // 
        Card getCurrentCard();
        Card getNextCard();
    }

    public static class TicTacToeEngine {
        public enum TicTacToePiece { None, X, O; }
        public enum TicTacToeState { Uninitialized, Initialized, InProgress, Over; }
        public enum TicTacToeEngineResponse { IllegalPiece, IllegalPosition, GameOver, GameInProgress }

        private TicTacToeState gameState = TicTacToeState.InProgress;
        private TicTacToePiece winningPiece = TicTacToePiece.None;
        private TicTacToePiece currentPiece = TicTacToePiece.None;
        private TicTacToePiece[] boardArray = new TicTacToePiece[9];
        private final static int[][] boardLines = new int[][] {
            {0, 1}, {3, 1}, {6, 1}, // horizontal lines
            {0, 3}, {1, 3}, {2, 3}, // vertical lines
            {0, 4}, {2, 2} }; // diagonal lines

        public TicTacToeEngine(TicTacToePiece firstPiece) { initialize(firstPiece); }
        public TicTacToeEngineResponse reset(TicTacToePiece firstPiece) { initialize(firstPiece); return null; }
        private void initialize(TicTacToePiece firstPiece) {
            this.boardArray = new TicTacToePiece[9];
            this.currentPiece = firstPiece;
            this.gameState = TicTacToeState.Initialized;
            this.winningPiece = TicTacToePiece.None;
        }

        public TicTacToeEngineResponse movePiece(TicTacToePiece piece, int offset) {
            if (gameState == TicTacToeState.Over) {
                return TicTacToeEngineResponse.GameOver;
            }

            if (piece != currentPiece) {
                return TicTacToeEngineResponse.IllegalPiece;
            }

            if (offset < 0 || offset > 9 || boardArray[offset] != TicTacToePiece.None) {
                return TicTacToeEngineResponse.IllegalPosition;
            }

            boardArray[offset] = currentPiece;
            updateGameState();

            if (gameState == TicTacToeState.Over) {
                return TicTacToeEngineResponse.GameOver;
            } else {
                return TicTacToeEngineResponse.GameInProgress;
            }
        }

        public void updateGameState() {
            for (int i = 0; i < boardLines.length; i++) {
                if (3 == countPiecesUsingSteps(boardLines[i][0], boardLines[i][1], this.currentPiece)) {
                    winningPiece = currentPiece;
                }
            }

            gameState = TicTacToeState.Over;
            if (winningPiece == TicTacToePiece.None) {
                for (int i = 0; i < 9; i++) {
                    if (boardArray[i] == TicTacToePiece.None) {
                        gameState = TicTacToeState.InProgress;
                        break;
                    }
                }
            }

            if (gameState == TicTacToeState.InProgress) {
                currentPiece = (currentPiece == TicTacToePiece.X) ? TicTacToePiece.O : TicTacToePiece.X;
            }
        }

        private int countPiecesUsingSteps(int offset, int step, TicTacToePiece current) {
            for (int count = 0; count < 3; count++, offset += step) {
                if (current != boardArray[offset]) {
                    return count;
                }
            }

            return 3;
        }
    }

    public static int indexOutOfCycle(int i, int... args) {
        if (null == args) throw new IllegalArgumentException("'args' must be non-null.");
        if (0 == args.length) throw new IllegalArgumentException("'args' must not be empty.");

        return indexOutOfCycle(0, args.length - 1, i, args);
    }

    private static int indexOutOfCycle(int left, int right, int i, int... args) {
        int pivot = (left + right) / 2;
        if (i == args[pivot]) {
            return pivot;
        } else if (left == right) {
            return -1;
        } else if (i == args[right]) {
            return right;
        }

        if (args[right] < args[pivot]) {
            if (args[right] < i && i < args[pivot]) {
                return indexOutOfCycle(left, pivot - 1, i, args);
            } else {
                return indexOutOfCycle(pivot + 1, right - 1, i, args);
            }
        } else {
            if (args[pivot] < i && i < args[right]) {
                return indexOutOfCycle(pivot + 1, right - 1, i, args);
            } else {
                return indexOutOfCycle(left, pivot - 1, i, args);
            }
        }
    }

    public static int smallestOutOfCycle(int... args) {
        if (null == args) throw new IllegalArgumentException("'args' must be non-null.");
        if (0 == args.length) throw new IllegalArgumentException("'args' must not be empty.");

        return smallestOutOfCycle(0, args.length - 1, args);
    }

    private static int smallestOutOfCycle(int left, int right, int... args) {
        if (right == left) {
            return args[left];
        } else {
            int pivot = (left + right) / 2;
            if (args[right] < args[pivot]) {
                return smallestOutOfCycle(pivot + 1, right, args);
            } else {
                return smallestOutOfCycle(left, pivot, args);
            }
        }
    }

    @Test
    public void testPartitionBookshelf() {
        partition(3, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    }

    public static void partition(int sections, int... books) {
        int[][] pivots = new int[books.length + 1][sections + 1];
        int[][] pages = new int[books.length + 1][sections + 1];
        int[] ps = new int[books.length+1]; // prefix sums

        ps[0] = 0;
        for (int i = 1; i <= books.length; i++) {
            ps[i] = ps[i-1] + books[i-1];
        }

        for (int i = 1; i <= books.length; i++) {
            pages[i][1] = ps[i];
        }

        for (int k = 1; k <= sections; k++) {
            pages[1][k] = books[0];
        }

        for (int n = 2; n <= books.length; n++) {
            for (int k = 2; k <= sections; k++) {
                pages[n][k] = Integer.MAX_VALUE;
                for (int book = 1; book <= n-1; book++) {
                    int value = Math.max(pages[book][k-1], ps[n] - ps[book]);
                    if (pages[n][k] > value) {
                        pages[n][k] = value;
                        pivots[n][k] = book;
                    }
                }
            }
        }

        pivots[0][0] = 0;
    }

    public static List<Integer> knapsackUnbounded(int[] values, int[] costs, int capacity) {
        int[] sums = new int[capacity + 1];
        int[] bests = new int[capacity + 1];
        for (int c = 0; c < costs.length; c++) {
            for (int k = 0; k <= capacity; k++) {
                if (costs[c] > k) continue;
                if (sums[k] > sums[k - costs[c]] + values[c]) continue;

                sums[k] = sums[k - costs[c]] + values[c];
                bests[k] = c;
            }
        }

        List<Integer> knapsack = new ArrayList<Integer>();
        while (capacity > 0) {
            knapsack.add(bests[capacity]);
            capacity -= costs[bests[capacity]];
        }

        return knapsack;
    }

    // http://www.animal.ahrgr.de/showAnimationDetails.php3?lang=en&anim=18
    public static List<Integer> knapsack01(int[] values, int[] costs, int capacity) {
        int[][] sums = new int[costs.length + 1][capacity + 1];
        for (int n = 0; n < capacity + 1; n++) { sums[0][n] = 0; }

        for (int c = 0, k = 1; k <= values.length; k++, c++) {
            sums[k][0] = 0;
            for (int n = 1; n <= capacity; n++) {
                if (costs[c] > n || sums[k - 1][n] > values[c] + sums[k - 1][n - costs[c]]) {
                    sums[k][n] = sums[k - 1][n];
                    continue;
                }

                sums[k][n] = values[c] + sums[k - 1][n - costs[c]];
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sums.length; i++) {
            for (int j = 0; j < sums[0].length; j++)
                sb.append(String.format("%d, ", sums[i][j]));
            sb.append("\r\n");
        }
        System.out.print(sb);

        List<Integer> knapsack = new ArrayList<Integer>();
        for (int k = values.length, c = k - 1; k > 0; k--, c--) {
            if (sums[k][capacity] != sums[k - 1][capacity]) {
                knapsack.add(c);
                capacity -= costs[c];
            }
        }

        return knapsack;
    }

    public static List<String> findLongestCommonSubstring(char[] lhs, char[] rhs) {
        int[][] spans = new int[lhs.length][rhs.length];
        int longest = 0;
        List<String> lcs = new ArrayList<String>();

        for (int i = 0; i < lhs.length; i++) {
            for (int j = 0; j < rhs.length; j++) {
                if (lhs[i] == rhs[j]) {
                    spans[i][j] = (i == 0 || j == 0) ? 1 : spans[i - 1][j - 1] + 1;
                    if (spans[i][j] > longest) {
                        longest = spans[i][j];
                        lcs.clear();
                    }

                    if (spans[i][j] == longest) {
                        lcs.add(new String(lhs, i - longest + 1, longest));
                    }
                }
            }
        }

        return lcs;
    }

    /*
     * Positive test cases:
     * - {1} yields {1}.
     * - {1, 2, 2} yields {2}.
     * - {1, 2, 2, 3, 3, 3} yields {3}.
     * - {2, 2} yields {2}.
     * - {1, 1, 2, 2} yields {1, 2}.

     * Negative test cases:
     * - null throws NPE. 
     * - empty yields empty.
     */
    @Test
    public void testFindModesUsingMap() {
        testFindModes(new Function<int[], List<Integer>>() {
            public List<Integer> apply(int[] input) { return findModesUsingMap(input); };
        });
    }

    @Test
    public void testFindModesUsingArray() {
        testFindModes(new Function<int[], List<Integer>>() {
            public List<Integer> apply(int[] input) { return findModesUsingArray(input); };
        });
    }

    public void testFindModes(Function<int[], List<Integer>> findModes) {
        try {
            findModes.apply(null);
            fail("findsModes should have thrown NPE.");
        } catch (AssertionError ae) {
        } catch (NullPointerException npe) {
        } catch (Exception e) {
            fail("findsModes should have thrown NPE.");
        }

        assertEquals(ImmutableList.of(), findModes.apply(new int[0]));

        assertEquals(ImmutableList.of(1), findModes.apply(new int[] {1}));
        assertEquals(ImmutableList.of(2), findModes.apply(new int[] {1, 2, 2}));
        assertEquals(ImmutableList.of(3), findModes.apply(new int[] {1, 2, 2, 3, 3, 3}));

        assertEquals(ImmutableList.of(2), findModes.apply(new int[] {2, 2}));
        assertEquals(ImmutableList.of(1, 2), findModes.apply(new int[] {1, 1, 2, 2}));
    }

    public static List<Integer> findModesUsingMap(int... numbers) {
        // We assert preconditions; arguments according to assumptions (for debug builds).
        assert null != numbers;

        // We validate arguments if this is a public interface (for debug and retail builds).
        if (null == numbers) {
            throw new NullPointerException("'numbers' must be non-null.");
        }

        // We short-circuit known cases such as an empty set of numbers.
        if (0 == numbers.length) {
            return ImmutableList.of();
        }

        int maximumHits = 1;
        List<Integer> modes = new ArrayList<Integer>();
        Map<Integer, Integer> hitsByNumber = new HashMap<Integer, Integer>();
        for (int number : numbers) {
            if (hitsByNumber.containsKey(number)) {
                hitsByNumber.put(number, 1 + hitsByNumber.get(number));
            } else {
                hitsByNumber.put(number, 1);
            }

            if (hitsByNumber.get(number) >= maximumHits) {
                if (hitsByNumber.get(number) > maximumHits) {
                    maximumHits = hitsByNumber.get(number);
                    modes.clear();
                }

                modes.add(number);
            } 
        }

        // We assert postconditions.
        assert !modes.isEmpty();
        return modes;
    }

    public static List<Integer> findModesUsingArray(int... numbers) {
        // We assert preconditions; arguments according to assumptions (for debug builds).
        assert null != numbers;

        // We validate arguments if this is a public interface (for debug and retail builds).
        if (null == numbers) {
            throw new NullPointerException("'numbers' must be non-null.");
        }

        // We short-circuit known cases such as an empty set of numbers.
        if (0 == numbers.length) {
            return ImmutableList.of();
        }

        int min = numbers[0];
        int max = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            min = Math.min(min, numbers[i]); // 1, 2, 2, 3, 3, 3
            max = Math.max(max, numbers[i]);
        }

        int maximumHits = 1;
        List<Integer> modes = new ArrayList<Integer>();

        int[] hitsByNumber = new int[max - min + 1]; // 11, 10..20 -> 10 rooms
        for (int number : numbers) {
            hitsByNumber[number - min]++; // {0, 0, ... } => {0, 1, ... }
            if (hitsByNumber[number - min] >= maximumHits) {
                if (hitsByNumber[number - min] > maximumHits) {
                    modes.clear();
                    maximumHits = hitsByNumber[number - min];
                }

                modes.add(number);
            }
        }

        return modes;
    }
    
//    /// <summary>Find the multiple missing elements from a list of unsorted elements.</summary>
//    public static IEnumerable<int>FindMissingElements(int[] set)
//    {
//        if (set == null || set.Length == 0) yield break;
//
//        int min = int.MaxValue, max = int.MinValue;
//        foreach (int i in set) // Time to find min/max : O(n)
//        {   
//            min = Math.Min(min, i); max = Math.Max(max, i);
//        }
//
//        if (max - min > set.Length * set.Length / 2)
//        {
//            Array.Sort(set); // Time to sort : O(nlogn)
//            for (int i = 1; i < set.Length; i++)
//            {
//                for (int j = set[i - 1]; j < set[i]; j++) // Time to search gap : O(n)
//                {
//                    yield return j;
//                }
//            }
//        }
//        else if (max - min > 80 * set.Length)
//        {   // Space to build hash map: O(10 * n)
//            Dictionary<int, object> map = new Dictionary<int, object>();
//            foreach (int i in set) map[i] = null; // Time to build hashmap: O(n)
//            for (int i = min + 1; i < max; i++)
//                if (map.ContainsKey(i) == false) yield return i; // Time to search gap : O(n)
//        }
//        else
//        {   // Space to build bitmap: O(k = max - min divided by 8)
//            System.Collections.BitArray map = new System.Collections.BitArray(max - min + 1);
//            foreach (int i in set) map[i - min] = true; // Time to build bitmap : O(n)
//            for (int i = min + 1; i < max; i++)
//                if (map[i - min] == false) yield return i; // Time to search gap : O(n)
//        }
//    }

    public static void swap(char[] chars, int i, int j) {
        if (chars[i] != chars[j]) {
            chars[i] ^= chars[j];
            chars[j] ^= chars[i];
            chars[i] ^= chars[j];
        }
    }

    public static void swap(int[] ints, int i, int j) {
        if (ints[i] != ints[j]) {
            ints[i] ^= ints[j];
            ints[j] ^= ints[i];
            ints[i] ^= ints[j];
        }
    }

    public static String ToString(int num) { // itoa in C/C++ runtime time library
        boolean negative = false;
        if (num < 0) {
            num = -num;
            negative = true;
        }

        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.insert(0, (char)('0' + num % 10));
            num /= 10;
        }

        if (negative) {
            sb.insert(0, '-');
        }

        return sb.toString();
    }

    public static int parse(String str) {
        int num = 0;
        boolean negative = false;
        int i = 0;
        if (str.charAt(i) == '-') {
            i++; negative = true;
        }

        for (; i < str.length(); i++) {
            if (str.charAt(i) < '0' && str.charAt(i) > '9') throw new IllegalArgumentException("Invalid format");
            num *= 10;
            num += (str.charAt(i) - '0');
        }

        return negative ? -num : num;
    }

    public static int LastIndexOfAny(String s, char... anyOf) {
        Map<Character, Object> map = new HashMap<Character, Object>();
        for (int i = 0; i < anyOf.length; i++) {
            map.put(anyOf[i], null);
        }

        for (int i = s.length() - 1; i > 0; i--) {
            if (map.containsKey(s.charAt(i))) return i;
        }

        return -1;
    }

    public static String FindAllNonRepeated(String s) {
        if (s == null) return null;

        Object seenOnce = new Object();
        Object seenTwice = new Object();
        Map<Character, Object> chars = new HashMap<Character, Object>();

        for (int i = 0; i < s.length(); i++) {
            if (chars.containsKey(s.charAt(i))) chars.put(s.charAt(i), seenTwice);
            else chars.put(s.charAt(i), seenOnce);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (chars.get(s.charAt(i)) == seenOnce) sb.append(s.charAt(i));
        }

        return sb.toString();
    }

//    public static String ReverseWords(String s) {
//        char[] chars = s.toCharArray();
//        Reverse(chars, 0, chars.length - 1);
//        for (int head = 0, tail = 0; tail < s.length(); )
//        {
//            for (head = tail; head < s.length() && chars[head] == ' '; ) head++;
//            for (tail = head; tail < s.length() && chars[tail] != ' '; ) tail++;
//            Reverse(chars, head, tail - 1);
//        }
//        return new String(chars);
//    }

//    public static IEnumerable<string> Tokenize(string str, string delimiters) {
//        System.Collections.BitArray map = new System.Collections.BitArray(256); // ASCII
//        for (int i = 0; i < delimiters.Length; i++) map[delimiters[i]] = true;
//        for (int head = 0; head < str.Length; head++)
//        {
//            if (map[str[head]]) continue;
//            int tail = head + 1;
//            while (tail < str.Length && map[str[tail]] == false) tail++;
//            yield return str.Substring(head, tail - head);
//        }
//    }

    public static int reverseInteger(int number) {
        int reverse = 0;
        while (number != 0) {
            reverse = reverse * 10 + number % 10;
            number /= 10;
        }

        return reverse;
    }

    public static int fibonacciBottomUp(int n) {
        if (n == 0 || n == 1) return 1;

        int current = 2;
        int one_back = 1;
        int two_back = 1;
        for (int i = 3; i <= n; i++) {
            current = one_back + two_back;
            two_back = one_back;
            one_back = current;
        }

        return current;
    }

    private static Map<Integer, Integer> memoization = new HashMap<Integer, Integer>();

    static {
        memoization.put(0, 1);
        memoization.put(1, 1);
    }

    public static int FibonacciTopDown(int n) {
        if (!memoization.containsKey(n)) {
            memoization.put(n, FibonacciTopDown(n - 1) + FibonacciTopDown(n - 2));
        }

        return memoization.get(n);
    }

//    public static List<String> LongestCommonSubstring(String lhs, String rhs) {
//        int[,] spans = new int[lhs.Length, rhs.Length];
//        int longest = 0;
//        List<string> lcs = new List<string>();
//
//        for (int i = 0; i < lhs.Length; i++) {
//            for (int j = 0; j < rhs.Length; j++) {
//                if (lhs[i] == rhs[j]) {
//                    spans[i, j] = (i == 0 || j == 0) ? 1 : spans[i - 1, j - 1] + 1;
//                    if (spans[i, j] > longest) {
//                        longest = spans[i, j];
//                        lcs.Clear();
//                    }
//
//                    if (spans[i, j] == longest) {
//                        lcs.Add(lhs.Substring(i - longest + 1, longest));
//                    }
//                }
//            }
//        }
//
//        return lcs;
//    }

    public static void printHex(int i) {
        if (i < 0) {
           out.print("-");
           i = -i;
        }

        Stack<Integer> stack = new Stack<Integer>();
        stack.push(i);
        while (!stack.isEmpty()) {
            i = stack.pop();
            if (i < 16) {
                out.print((char)((i < 10) ? ((int)'0' + i) : ((int)'A' + i - 10)));
            } else {
                stack.push(i % 16);
                stack.push(i / 16);
            }
        }
    }

    @Test
    public void testIndexOutOfCycle() {
        assertEquals(4, indexOutOfCycle(30, new int[] {90, 100, 10, 20, 30, 40, 50, 60, 70, 80}));
        assertEquals(3, indexOutOfCycle(20, new int[] {90, 100, 10, 20, 30, 40, 50, 60, 70, 80}));
        assertEquals(0, indexOutOfCycle(90, new int[] {90, 100, 10, 20, 30, 40, 50, 60, 70, 80}));
        assertEquals(-1, indexOutOfCycle(95, new int[] {90, 100, 10, 20, 30, 40, 50, 60, 70, 80}));
        assertEquals(5, indexOutOfCycle(40, new int[] {90, 100, 10, 20, 30, 40, 50, 60, 70, 80}));
        assertEquals(9, indexOutOfCycle(80, new int[] {90, 100, 10, 20, 30, 40, 50, 60, 70, 80}));
        assertEquals(8, indexOutOfCycle(70, new int[] {90, 100, 10, 20, 30, 40, 50, 60, 70, 80}));
        assertEquals(-1, indexOutOfCycle(75, new int[] {90, 100, 10, 20, 30, 40, 50, 60, 70, 80}));

        assertEquals(4, indexOutOfCycle(70, new int[] {30, 40, 50, 60, 70, 80, 90, 100, 10, 20}));
        assertEquals(0, indexOutOfCycle(30, new int[] {30, 40, 50, 60, 70, 80, 90, 100, 10, 20}));
        assertEquals(3, indexOutOfCycle(60, new int[] {30, 40, 50, 60, 70, 80, 90, 100, 10, 20}));
        assertEquals(-1, indexOutOfCycle(55, new int[] {30, 40, 50, 60, 70, 80, 90, 100, 10, 20}));
        assertEquals(5, indexOutOfCycle(80, new int[] {30, 40, 50, 60, 70, 80, 90, 100, 10, 20}));
        assertEquals(9, indexOutOfCycle(20, new int[] {30, 40, 50, 60, 70, 80, 90, 100, 10, 20}));
        assertEquals(8, indexOutOfCycle(10, new int[] {30, 40, 50, 60, 70, 80, 90, 100, 10, 20}));
        assertEquals(-1, indexOutOfCycle(15, new int[] {30, 40, 50, 60, 70, 80, 90, 100, 10, 20}));
    }

    @Test
    public void testMinStack() {
        MinStack<Integer> stack = new MinStack<Integer>();
        stack.push(2).push(6).push(4).push(1).push(5).push(1);
        assertTrue(1 == stack.getMin());
        assertTrue(1 == stack.pop());
        assertTrue(1 == stack.getMin());
        assertTrue(5 == stack.pop());
        assertTrue(1 == stack.getMin());
        assertTrue(1 == stack.pop());
        assertTrue(2 == stack.getMin());
        assertTrue(4 == stack.pop());
        assertTrue(2 == stack.getMin());
        assertTrue(6 == stack.pop());
        assertTrue(2 == stack.getMin());
        assertTrue(2 == stack.pop());
        assertTrue(null == stack.getMin());
    }

    @Test
    public void testMatchPattern() {
        assertEquals(3, lastIndexOfByRabinKarp("aabab", "ab"));
        assertEquals(2, lastIndexOfByRabinKarp("aaabcc", "abc"));
        assertEquals(2, lastIndexOfByRabinKarp("aaabc", "abc"));
        assertEquals(0, lastIndexOfByRabinKarp("abc", "abc"));
        assertEquals(0, lastIndexOfByRabinKarp("abcc", "abc"));
        assertEquals(-1, lastIndexOfByRabinKarp("abc", "xyz"));
        assertEquals(-1, lastIndexOfByRabinKarp("abcc", "xyz"));
    }

    @Test
    public void testQuicksort() {
        int[] input = new int[] {44, 75, 23, 43, 55, 12, 64, 77, 33};
        quicksort(input, 0, 8);
        assertTrue(Arrays.equals(new int[]{12, 23, 33, 43, 44, 55, 64, 75, 77}, input));
    }

    @Test
    public void testKadane() {
        int[] a = { -2, 1, -3, 4, -1, 2, 1, -5, 4};
        assertTrue(Arrays.equals(new int[]{6, 3, 6}, kadane(a)));
        assertTrue(Arrays.equals(new int[]{3, 0, 0, 2, 2}, kadane(new int[][] {{1, 0, 1}, {0, -1, 0}, {1, 0, 1}})));
        assertTrue(Arrays.equals(new int[]{3, 0, 0, 0, 1}, kadane(new int[][] {{1, 2, -1}, {-3, -1, -4}, {1, -5, 2}})));
    }

    @Test
    public void testProducts() {
        Iterables.elementsEqual(ImmutableList.of(120, 60, 40, 30, 24), ImmutableList.of(products(1, 2, 3, 4, 5)));
    }

    @Test
    public void testDivide() {
        assertEquals(1024768/7, divide(1024768, 7));
    }

    @Test
    public void testPermutate() {
        assertTrue(Iterables.elementsEqual(
            ImmutableList.of("abc", "acb", "bac", "bca", "cba", "cab"),
            permutate("abc".toCharArray(), 0)));
        assertTrue(Iterables.elementsEqual(
            ImmutableList.of("abab", "abba", "aabb", "baab", "baba", "bbaa"),
            permutate("abab".toCharArray(), 0)));
    }

    @Test
    public void testFindSmallestOutOfCycle() {
        try {
            smallestOutOfCycle(null);
            fail("'numbers' must be non-null.");
        } catch (IllegalArgumentException e) {
        }

        try {
            smallestOutOfCycle(new int[0]);
            fail("'numbers' must not be empty.");
        } catch (IllegalArgumentException e) {
        }

        assertEquals(6, smallestOutOfCycle(new int[] {6}));
        assertEquals(6, smallestOutOfCycle(new int[] {6, 7}));
        assertEquals(6, smallestOutOfCycle(new int[] {7, 6}));
        assertEquals(6, smallestOutOfCycle(new int[] {38, 40, 55, 89, 6, 13, 20, 23, 36}));
        assertEquals(6, smallestOutOfCycle(new int[] {6, 13, 20, 23, 36, 38, 40, 55, 89}));
        assertEquals(6, smallestOutOfCycle(new int[] {13, 20, 23, 36, 38, 40, 55, 89, 6}));
    }

    @Test
    public void testToAndFromExcelColumn() {
        assertEquals("AB", toExcelColumn(28));
        assertEquals("ABC", toExcelColumn(731));
        assertEquals(28, fromExcelColumn(toExcelColumn(28)));
        assertEquals(731, fromExcelColumn(toExcelColumn(731)));
    }

    @Test
    public void testYieldInorderLeftToRight() {
        BNode<Integer> i12 = BNode.of(12, null, null);
        BNode<Integer> i17 = BNode.of(17, null, null);
        BNode<Integer> i15 = BNode.of(15, i12, i17);
        BNode<Integer> i8 = BNode.of(8, null, null);
        BNode<Integer> i10 = BNode.of(10, i8, i15);
        BNode<Integer> i2 = BNode.of(2, null, null);
        BNode<Integer> i5 = BNode.of(5, i2, i10);
        BNode<Integer> i20 = BNode.of(20, null, null);
        BNode<Integer> i24 = BNode.of(24, null, null);
        BNode<Integer> i22 = BNode.of(22, i20, i24);
        BNode<Integer> i27 = BNode.of(27, null, null);
        BNode<Integer> i25 = BNode.of(25, i22, i27);
        BNode<Integer> i19 = BNode.of(19, i5, i25);
        List<Integer> i13_21 = BNode.yieldInorder(i19, BNode.of(13, null, null), BNode.of(21, null, null));
        List<Integer> i12_22 = BNode.yieldInorder(i19, BNode.of(12, null, null), BNode.of(22, null, null));
        assertTrue(Iterables.elementsEqual(ImmutableList.of(19, 15, 17, 20), i13_21));
        assertTrue(Iterables.elementsEqual(ImmutableList.of(19, 15, 12, 17, 22, 20), i12_22));
    }

    public static class BitOperations {
        public static boolean isPowerOf2(int x) {
            return (0 == (x & x - 1)) && (x > 0);
        }

        public static void swap(int[] a) {
            a[0] ^= a[1] ^= a[0] ^= a[1];
        }

//      public static int CountOnes(int value) {
//          unchecked {
//              uint x = (uint)value;
//              x = ((0xaaaaaaaa & x) >> 1) + (0x55555555 & x);
//              x = ((0xcccccccc & x) >> 2) + (0x33333333 & x);
//              x = ((0xf0f0f0f0 & x) >> 4) + (0x0f0f0f0f & x);
//              x = ((0xff00ff00 & x) >> 8) + (0x00ff00ff & x);
//              x = (x >> 16) + (0x0000ffff & x);
//              return (int)x;
//          }
//      }

//      public static int Reverse(int value) {
//          unchecked {
//              uint x = (uint)value;
//              x = x >> 16 | (0x0000ffff & x) << 16;
//              x = (0xff00ff00 & x) >> 8 | (0x00ff00ff & x) << 8;
//              x = (0xf0f0f0f0 & x) >> 4 | (0x0f0f0f0f & x) << 4;
//              x = (0xcccccccc & x) >> 2 | (0x33333333 & x) << 2;
//              x = (0xaaaaaaaa & x) >> 1 | (0x55555555 & x) << 1;
//              return (int)x;
//          };
//      }

//      public static int CountTrailingZeros(int value) {
//          return CountOnes((value & -value) - 1);
//      }

//      public static int CountLeadingZeros(int value) {
//          unchecked {
//              uint x = (uint)value;
//              x |= x >>= 1;
//              x |= x >>= 2;
//              x |= x >>= 4;
//              x |= x >>= 8;
//              x |= x >>= 16;
//              return CountOnes((int)~x);
//          }
//      }
    }
}
