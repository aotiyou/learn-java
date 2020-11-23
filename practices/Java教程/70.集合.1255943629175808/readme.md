集合（Collection）：由若干个确定的元素所构成的整体。

数组限制：
* 数组初始化后大小不可变
* 数组只能按索引顺序存取

java.util包主要提供了以下三种类型的集合：
* List：一种有序列表的集合。
* Set：一种保证没有重复元素的集合。
* Map：一种通过键值（key-value）查找的映射表集合。

Java集合的设计有几个特点：
* 实现了接口和实现类相分离。
* 支持泛型
* java访问集合，通过迭代器（Iterator）来实现

### 使用List

List<E>接口，主要的接口方法：
* 在末尾添加一个元素：boolean add(E e)
* 在指定索引添加一个元素：boolean add(int index, E e)
* 删除指定索引的元素：int remove(int index)
* 删除某个元素：int remove(Object e)
* 获取指定索引的元素：E get(int index)
* 获取链表大小（包含元素的个数）：int size()

比较ArrayList和LinkedList：

|      | ArrayList     | LinkedList  |
| ---- | ------------- | ----------- |
| 获取指定元素 | 速度很快 | 需要从开始查找元素 |
| 添加元素到末尾 | 速度很快 | 速度很快 |
| 在指定位置添加/删除 | 需要移动元素 | 不需要移动元素 |
| 内存占用 | 少 | 较大 |

List的特点：
* 允许添加重重复的元素
* 允许添加null

创建List

遍历List
* for循环根据索引配合get(int)方法遍历
* Iterator来访问List

List和Array转换

### 编写equals

* 判断List是否包含某个指定元素：boolean contains(Object o)
* 返回某个元素的索引：int indexOf(Object o)

List内部通过equals()方法判断两个元素是否相等，而不是==。

编写equals
满足一下条件：
* 自反性（Reflexive）：对于非null的x来说，x.equals(x)必须返回true；
* 对称性（Symmetric）：对于非null的x和y来说，如果x.equals(y)为true，则y.equals(x)也必须为true；
* 传递性（Transitive）：对于非null的x、y和z来说，如果x.equals(y)为true，y.equals(z)也为true，那么x.equals(z)也必须为true；
* 一致性（Consistent）：对于非null的x和y来说，只要x和y状态不变，则x.equals(y)总是一致地返回true或者false；
* 对null的比较：即x.equals(null)永远返回false。

equals()方法的正确编写方法：
1.先确定实例“相等”的逻辑，即哪些字段相等，就认为实例相等；
2.用instanceof判断传入的待比较Object是不是当前类型，如果是，继续比较，否则，返回false；
3.对引用类型用Object.equals()比较，对基本类型直接用==比较。

### 使用Map

Map（Key-value）映射表的数据结构，作用就是能高效通过key快速查找value（元素）。

**Map中不存在重复的key，因为放入相同的key，只会把原有的key-value对应的value给替换掉**

遍历Map
* 要遍历key，可以使用 for each循环遍历Map实例的keySet()方法返回Set集合，它包含不重复的key的集合
* 同时遍历key和value，可以使用for each循环遍历Map对象的entrySet()集合，它包含每一个key-value映射

**遍历Map时，不可假设输出的key是有序的**

### 编写equals和hashCode

key做比较是通过equals()实现的，作为key的对象必须正确覆写equals()方法。

我们经常使用String作为key，因为String已经正确覆写了equals()方法。

HashMap为什么能通过key直接计算出value存储的索引？
key计算索引的方式就是调用key对象的hashCode()方法，它返回一个int整数。
HashMap正是通过这个方法直接定位key对应的value索引，继而直接返回value。

正确使用Map必须保证：
1.作为key的对象必须正确覆写equals()方法，相等的两个key实例调用equals()必须返回true；
2.作为key的对象还必须正确覆写hashCode()方法，且hashCode()方法要严格遵循以下规范：
* 如果两个对象相等，则两个对象的hashCode()必须相等；
* 如果两个对象不相等，则两个对象的hashCode()尽量不要相等。

编写equals()和hashCode()遵循的原则：
equals()用到的用于比较的每一个字段，都必须在hashCode()中用于计算；
equals()中没有用到的字段，绝不可放在hashCode()中计算。

hashCode()返回的int范围高达±21亿,先不考虑负数，HashMap内部使用的数组有多大？
实际上，HashMap初始化时默认的数组大小只有16，任何key，无论它的hashCode有多大，都可以简单的通过：
`int index = key.hashCode() & 0xf // 0xf = 15`
把索引确定在0~15，即永远不会超出数组范围。

如果添加超过16个key-value到HashMap。数组不够用了怎么办？
添加超过一定数量的key-value时，HashMap会在内部自动扩容，每次扩容一倍，即长度为16的数组扩展为长度32，
相应地，需要重新确定hashCode()计算的索引位置。

如果不同的两个key，hashCode()恰好是相同的，由于计算出的数组索引相同，value会覆盖吗？
不会。只要key不相同，它们映射的value就互不干扰。
但是，在HashMap内部，确实可能存在不同的key，映射到相同的hashCode()，即相同的数组索引上，怎么办？
HashMap内部实际上是List<Entry<String,Person>>

### 使用EnumMap

如果Map的key是enum类型，推荐使用EnumMap，即保证速度，也不浪费空间。

### 使用TreeMap

内部会对Key进行排序，这种Map就是SortedMap。注意到SortedMap是接口，它的实现类是TreeMap。

Comparator
a < b  return -1
a == b return 0
a > b  return 1

TreeMap不使用equals()和hashCode()

TreeMap在比较两个Key是否相等时，依赖Key的compareTo()方法或者Comparator.compare()方法。
在两个Key相等时，必须返回0.

作为SortedMap的Key必须实现Comparable接口，或者传入Comparator；
要严格按照compare()规范实现比较逻辑。

### 使用Properties

Properties内部本质上是一个Hashtable。

用Properties读取配置文件，一共有三步：
1.创建Properties实例；
2.调用load()读取文件；
3.调用getProperty()获取配置。

JDK9开始，Java的.properties文件可以使用UTF-8编码；之前，文件编码是ASCII编码(ISO8859-1)
需要注意的是，由于load(InputStream)默认总是以ASCII编码读取字节流，所以会导致读到乱码。
所以，需要重载load(Reader)
`
Properties props = new Properties()
props.load(new FileReader("setting.properties", StandardCharsets.UTF_8));
`
InputStream和Reader的区别是一个是字节流，一个是字符流。字符流在内存中已经以char类型表示了，不涉及编码问题。

### 使用Set

只需要存储不重复的key，不需要存储映射的value，就可以使用Set。

* 将元素添加进Set<E>：boolean add(E e)
* 将元素从Set<E>删除：boolean remove(Object e)
* 判断是否包含元素：boolean contains(Object e)

Set实际上相当于只存储key、不存储value的Map。

HashSet仅仅是对HashMap的一个简单封装。

Set接口并不保证有序，而SortedSet接口则保证元素是有序的：
* HashSet是无序的，因为它实现了Set接口，并没有实现SortedSet接口；
* TreeSet是有序的，因为它实现了SortedSet接口。

TreeSet和TreeMap要求一样，添加的元素必须正确实现Comparable接口，
如果没有实现Comparable接口，那么创建TreeSet时必须传入一个Comparator对象。

### 使用Queue

Queue：先进先出（FIFO：First In First Out）。
和List区别：
* List可以在任意位置添加和删除元素
* Queue把元素添加到队列末尾；
* Queue从队列头部取出元素

Queue方法：
* int size()：获取队列长度；
* boolean add(E)/boolean offer(E)：添加元素到队尾；
* E remove()/E poll()：获取队首元素并从队列中删除；
* E element()/E peek()：获取队首元素并不从队列中删除。

|     | throw Exception | 返回false或null |
| --- | ---- | ---- |
| 添加元素到队尾 | add(E e) | boolean offer(E e) |
| 取队首元素并删除 | E remove() | E poll() |
| 取队首元素但不删除 | E element() | E peek() |

**注意：不要把null添加到队列中，否则poll()方法返回null时，很难确定是取到了null元素还是队列为空。**

LinkedList即实现了List接口，又实现了Queue接口。
但是，在使用的时候，如果我们把它当做List，就获取List的引用，如果我们把它当做Queue，就获取Queue的引用。

### 使用PriorityQueue

优先队列：Priority

与Queue区别：
出队顺序与元素的优先级有关。

对Priority调用remove()或poll()方法，返回的总是优先级最高的元素。

必须实现Comparable接口，PriorityQueue会根据元素的排序顺序决定出队的优先级。
PriorityQueue允许我们提供一个Comparator对象来判断两个元素的顺序。

### 使用Deque

Queue：只能一头进，另一头出。
Deque：允许两头都进，两头都出。

Deque功能：
* 既可以添加到队尾，也可以添加到队首；
* 既可以从队首获取，又可以从队尾获取。

比较Queue和Deque出队和入队的方法：

| | Queue | Deque |
| --- | --- | --- |
| 添加元素到到队尾 | add(E e) / offer(E e) | addLast(E e) / offerLast(E e) |
| 取队首元素并删除 | E remove / E poll() | E removeFirst() / E pollFirst() |
| 取队首元素但不删除 | E element() / E peek() | E getFirst() / E peekFirst() |
| 添加元素到队首 | 无 | addFirst(E e) / offerFirst(E e) |
| 取队尾元素并删除 | 无 | E removeLast() / E pollLast() |
| 取队尾元素但不删除 | 无 | E getLast() / E peekLast() |
  
使用Deque，推荐总是明确调用offerLast() / offerFirst() 获取pollFirst() / pollLast()方法。

Deque是一个接口，它的实现类有ArrayDeque和LinkedList。

LinkedList，既是List，又是Queue，还是Deque。
在使用的时候，用特定的接口来引用它，这是因为持有接口说明代码的抽象层次更高，而且接口本身定义的方法代表了特定的用途。

面向抽象变成的一个原则就是：**尽量持有接口，而不是具体的实现类**

Deque实现了一个双端队列（Double Ended Queue），它可以：
* 将元素添加到队尾或队首：addLast()/offerLast()/addFirst()/offerFirst()；
* 从队首／队尾获取元素并删除：removeFirst()/pollFirst()/removeLast()/pollLast()；
* 从队首／队尾获取元素但不删除：getFirst()/peekFirst()/getLast()/peekLast()；
* 总是调用xxxFirst()/xxxLast()以便与Queue的方法区分开；
* 避免把null添加到队列。

### 使用Stack

栈（Stack）：后进先出（LIFO：Last In Last First）的数据结构。

Stack是这样的一种数据机构：只能不断往Stack中压入(push)元素，最后进去的必须最早弹出(pop)来。

Stack只有入栈和出栈的操作：
* 把元素压栈：push(E);
* 把栈顶的元素“弹出”：pop(E);
* 取栈顶元素但不弹出：peek(E)。

在Java中，我们用Deque可以实现Stack的功能：
* 把元素压栈：push(E) / addFirst(E);
* 把栈顶的元素“弹出”：pop(E) / removeFirst();
* 取栈顶元素但不弹出：peek(E) / peekFirst()；

我们把Deque作为Stack使用时：
**注意只调用push()/pop()/peek()方法，不要调用addFirst()/removeFirst()/peekFirst()方法，这样代码更加清晰。**

Stack的作用：
JVM会创建方法调用栈，每调用一个方法时，先将参数压栈，然后执行对应的方法；当方法返回时，返回值压栈，调用方法通过出栈操作获得方法返回值。

因为方法调用栈有容量限制，嵌套调用过多会造成栈溢出，即引发StackOverflowError。（测试无限递归调用）

对整数进行进制的转换就可以利用栈。

### 使用Iterator

通过Iterator对象遍历集合的模式成为迭代器。

自定义集合类，用for each循环，需要满足以下条件：
* 集合类实现Iterable接口，该接口要求返回一个Iterator对象；
* 用Iterator对象迭代集合内部数据。

Iterator是一种抽象的数据访问模型。使用Iterator模式进行迭代的好处有：
* 对任何集合都采用同一种访问模型；
* 调用者对集合内部结构一无所知；
* 集合类返回的Iterator对象知道如何迭代。

### 使用Collections

Collections是JDK提供的工具类，同样位于java.util包中。它提供了一系列静态方法，能方便地操作各种集合。

创建空集合

* 创建空List：List<T> emptyList()
* 创建空Map：Map<K, V> emptyMap()
* 创建Set：Set<T> emptySet()

**注意：返回的空集合是不可变集合，无法向其中添加或删除元素**

创建单元素集合

* 创建一个元素的List：List<T> singletonList(T o)
* 创建一个元素的Map：Map<K, V> singletonMap(K key, V value)
* 创建一个元素的Set：Set<T> singleton(T o)

**注意：返回的单元素集合也是不可变集合，无法向其中添加或删除元素**

排序

排序会直接修改List元素的位置，因此必须传入可变List

洗牌

传入一个有序的List，可以随机打乱List内部元素的顺序，效果相当于让计算机洗牌

不可变集合

* 封装成不可变List：List<T> unmodifiableList(List<? extends T> list)
* 封装成不可变Set：Set<T> unmodifiableSet(Set<? extends T> set)
* 封装成不可变Map：Map<K, V> unmodifiableMap(Map<? extends K, ? extends V> m)

**注意：继续对原始的可变List进行增删是可以的，并且，会直接影响到封装后的“不可变”List。
因此，可变List封装成不可变List后，最好立刻扔掉可变List的引用，这样可以保证后续操作不会意外改变原始对象，从而造就“不可变”List变化了**

线程安全集合

可以把线程不安全的集合变为线程安全的集合;
* 变为线程安全的List：List<T> synchronizedList(List<T> list)
* 变为线程安全的Set：Set<T> synchronizedSet(Set<T> s)
* 变为线程安全的Map：Map<K, V> synchronizedMap(Map<K, V> m)

从Java 5开始，引入了更搞笑的并发集合类，所以上述这几个同步方法已经没有什么用了。







