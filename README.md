# Scala 集合笔记

集合在编程语言中扮演着一个重要的角色,它用于数据存储、数据处理等等重要的操作。学会集合是必要的,其中的API更是要熟练掌握

在Scala中集合分为三大类分别是**序列Seq、集Set、映射Map**。所有集合都扩展于Iterable特质

> 特质:既对应Java的Interface接口

对于所有集合类型都分为两类分别是,**可变类型和不可变类型**,它们在不同的包下

> 不可变类型:scala.collection.immutable  
> 可变类型:scala.collection.mutable

不可变集合指的是该对象修改之后返回一个新对象,原对象不更改。可变集合直接在原对象上更改

## 数组

数组是一个很重要的数据结构,在计算机底层用一块连续的内存空间进行存储

### 定义一个数组

在Java中创建一个数组是这样的

```java
int[] array = new int[10]; 
```

而在Scala中定义数组是这样子的

```scala
val array = new Array[Int](10)
```

Scala中另一种创建数组的方式,通过调用伴生对线的apply方法进行创建

```scala
val array = Array(1, 2, 3)
```

### 访问一个元素

Scala在Array对象中有个apply方法,通过`array.apply(0)`进行访问,可以简写为`array(0)`

```scala
val array = new Array[Int](10)
//通过小括号加下标即可访问元素
array(0)
```

### 修改一个元素

Scala有一个update方法,通过`array.update(0,999)`进行更改,可以简写为`array(0) = 999`

```scala
val array = new Array[Int](10)
array(0) = 999
```

### 遍历一个数组

通过for循环来遍历

```scala
val array = new Array[Int](10)

for (i <- array.indices) {
  println(array(i))
}

//增强for循环
for (i <- array) println(i)
```

通过迭代器遍历

```scala
val array = new Array[Int](10)
val iterator = array.iterator

while (iterator.hasNext) {
  println(iterator.next())
}
```

通过foreach遍历

```scala
val array = new Array[Int](10)

//通过方法引用实现
array.foreach(println)
``` 

通过mkString返回字符串

```scala
val array = new Array[Int](10)

array.mkString(",")
//结果是0,0,0...
```

### 添加一个元素

由于不可变数组不会在本对象引用更新元素,调用添加之后返回一个新数组才是添加元素的数组

```scala
val array = new Array[Int](10)
//:+方法是编译器生成的
val newArray = array.:+(99)
//:+方法可以简化成
val newArray1 = array :+ 99
```

:+方法是往后添加,那有没有往前添加的方法呢?那肯定是有的,往前添加的方法是+:

```scala
val array = new Array[Int](10)
val newArray = 99 +: array

//基于简化写法，我们可以这样写
val newArray1 = 99 +: 99 +: 99 +: array :+ 88 :+ 88 :+ 88
```

### 创建一个可变数组

调用ArrayBuffer创建一个可变数组,通过new创建的数组底层默认创建一个长度为16的数组

```scala
val array = new ArrayBuffer[Int]()
//或者
val array1 = ArrayBuffer(1, 2, 3, 4, 5, 6)
```

### 添加一个可变数组的元素

你可以使用:+方法来进行添加,但返回的是一个新数组,所以不推荐。也可以使用+=方法在数组后面进行添加,在数组前面添加用+=:方法

```scala
val array = new ArrayBuffer[Int]()
array += 99
//往前后都分别添加
99 +=: 99 +=: 99 +=: array += 88 += 88 += 88
//也可以使用:+但是会返回一个新的数组，对于可变数组来说完全没有必要
val newArray = array :+ 99
```

通过append和prepend方法添加

```scala
val array = new ArrayBuffer[Int]()
array.append(11)
array.prepend(11)
```

通过insert插入元素

```scala
val array = new ArrayBuffer[Int]()
//0是下标,99是元素
array.insert(0, 99)
```

insertAll方法是将数组解构并添加到对象数组中

```scala
val array = ArrayBuffer(1, 2, 3)
val array2 = ArrayBuffer(4, 5, 6)

array.appendAll(array2)
array.prependAll(array2)
array.insertAll(0, array2)
```

### 删除一个元素

通过remove删除一个元素

```scala
val array = ArrayBuffer(1, 2, 3, 4)
array.remove(0)
//remove另一个方法,从零开始删除,删除三个数
array.remove(0, 3)
```

通过-=删除一个元素

```scala
val array = ArrayBuffer(1, 2, 3, 4)
//删除array数组中的“1”元素，只删除一个
array -= 1
```

### 可变数组与不可变数组的互相转换

使用`toArray`方法和`toBuffer`方法进行转换

```scala
val array = new Array[Int](10)
//不可变数组转化为可变数组
val buffer: mutable.Buffer[Int] = array.toBuffer
//可变数组转化为不可变数组
val array1: Array[Int] = buffer.toArray
```

### 多维数组

Scala语言是一个函数式编程和全部面向对象语言,所以并没有多维数组的语法,但是可以调用Array伴生对象的ofDim方法进行创建多维数组,最大支持五维数组

```scala
//创建一个两行三列的二维数组
val array: Array[Array[Int]] = Array.ofDim(2, 3)
//访问元素
array(0)(1)
//遍历元素
array.foreach(_.foreach(println))
```

## List列表

Scala中列表和数组都继承于Seq序列特质

### 创建一个不可变的列表

List不是一个对象类而是一个抽象类,可以调用它的伴生对线来创建一个List

```scala
var list = List(1, 2, 3)
```

### 添加一个元素

可以和数组一样使用:+和+:添加

```scala
var list: List[Int] = List(1, 2, 3)

var newList: List[Int] = 88 +: list :+ 99
```

使用::方法在列表前面添加元素,可以配合Nil类进行创建列表

> Nil是继承于List抽象类的一个子类

```scala
val list: List[Int] = 11 :: 22 :: 33 :: Nil

val list1 = 44 :: list 
```

如果我们拼接两个数组进行元素,他并不会拆开一个数组再来添加元素

```scala
val list1: List[Int] = 11 :: 22 :: 33 :: Nil
val list2: List[Int] = 44 :: 55 :: 66 :: Nil

val list3 = list1 :: list2
println(list3)
//控制台输出为：List(List(11, 22, 33), 44, 55, 66)
```

那我们如何拆开列表并赋值在另一个列表呢?我们可以使用:::和++方法进行解构赋值

```scala
val list1: List[Int] = 11 :: 22 :: 33 :: Nil
val list2: List[Int] = 44 :: 55 :: 66 :: Nil

val list3 = list1 ::: list2
val list4 = list1 ++ list2
println(list3)
println(list4)
//控制台输出为：List(11, 22, 33, 44, 55, 66)
```

### 创建一个可变列表

ArrayBuffer和ListBuffer都混入了Buffer的特质

> ListBuffer也有insert,insertAll,append,appendAll,prepend等等,因为他们都混入了Buffer特质

```scala
val list: ListBuffer[Int] = new ListBuffer[Int]()
```

### 添加一个元素到列表

ListBuffer追加元素和ArrayBuffer的用法是一样的,也是用+=:和+=

```scala
val list: ListBuffer[Int] = new ListBuffer[Int]()

22 +=: list += 11
```

### 合并列表

使用++方法会返回一个新列表,但是用++=会在调用者的列表更新

```scala
val list: ListBuffer[Int] = new ListBuffer[Int]()
val list1: ListBuffer[Int] = new ListBuffer[Int]()
list += 789 += 999
list1 += 456 += 123

//将list1的列表内容添加到list
list ++= list1
//list不变返回一个新列表
val list2 = list ++ list1 
```

### 修改一个元素

如果是不可变列表无法使用`list(0) = 99`,但如果是可变列表可以使用`list(0) = 99`这种方法

> list(0) = 99:底层调用的是update方法

### 删除一个元素

使用-=删除对应的元素,使用`remove()`方法删除指定位置的元素

```scala
val list: ListBuffer[Int] = new ListBuffer[Int]()
list += 11 += 22 += 33 += 44 += 55 += 66 += 77 += 88
//删除第0个元素
list.remove(0)
//删除一个元素为66的元素
list -= 66
println(list)
```

## Set集合

Set相对于列表有不同的特质,**Set是无序的且元素是唯一的**

### 创建一个不可变的Set

创建不可变Set和创建不可变List的方法一样,都是调用它们的伴生对象

```scala
val set = Set(1, 2, 3, 4) 
```

### 添加和删除元素

Set和List添加元素不一样List用:+方法而Set用+方法,少了一个冒号。用-号删除一个元素

```scala
val set = Set(1, 2, 3, 4)
//添加一个元素
val set1 = set + 9
//删除一个元素
val set1 = set - 3
//合并元素
val set2 = Set(6, 7, 8, 4)
val set3 = set ++ set2
```

### 创建一个可变的Set

使用可变Set必须添加包名,要不然会是一个不可变的Set

```scala
val set = mutable.Set(1, 2, 3)
```

### 添加一个元素

可变Set也有+方法,但是会返回一个新数组,所以不推荐使用+方法,可以使用+=方法。可以使用add方法进行添加,方法内部使用的+=方法进行添加

```scala
val set = mutable.Set(1, 2, 3)
//返回一个新Set
val set2 = set + 55
//只更改set集合
set += 55
//等价于
set.add(55)
```

### 合并两个集合

使用++方法会返回一个新集合,但是使用++=会在调用者的集合上更新

```scala
val set = mutable.Set(1, 2, 3)
val set1 = mutable.Set(4, 5, 6)

set ++= set1
```

## Map集合

Map集合存储的键值对的关系,键不可以重复,但是值可以

### 创建一个Map

创建一个Map和Set创建的用法是一样的,Map默认和Set一样都是不可变的

```scala
val map: Map[Int, String] = Map((1, "1"))
//也可以使用这样的方法
val map1: Map[Int, String] = Map(1 -> "1")
```

### 访问一个元素

Map通过get方法返回的是一个Some对象,需要调用一个Some对象的get方法获取值,我们也可以通过`map(1)`这种方式直接调用

```scala
val map: Map[Int, String] = Map((1, "1"), (9, "9"), (2, "2"))
val i: Int = map.get(9).get
//效果等价
val i2: Int = map(9)
```

### 添加一个元素

添加一个方法和Set方法一致,都是用+号

```scala
val map: Map[Int, String] = Map((1, "1"), (9, "9"), (2, "2"))
//添加一个元素并返回一个新Map
val map2 = map + ((6, "12"))
//合并两个map
val map3 = map ++ map2
```

### 创建一个可变Map

默认的Map不是是mutable包下的,也就是默认的Map不是可变的,需要在Map指定mutable包,来创建一个可变Map

```scala
val map: mutable.Map[Int, String] = mutable.Map((1, "2"), (9, "8"), (2, "5"))
```

### 添加一个元素

使用+=添加元素

```scala
val map: mutable.Map[Int, String] = mutable.Map((1, "2"), (9, "8"), (2, "5"))
map += (3, "3")
//使用+=方法也可以修改Map值，或者使用update()方法进行修改
map += (3, "修改之后的3")
```

### 删除一个元素

使用-=方法或者remove方法进行删除

```scala
val map: mutable.Map[Int, String] = mutable.Map((1, "2"), (9, "8"), (2, "5"))
map -= 2
map.remove(1)
```

### 合并两个Map

通过++=合并两个Map

```scala
val map: mutable.Map[Int, String] = mutable.Map((1, "2"), (9, "8"), (2, "5"))
val map2: mutable.Map[Int, String] = mutable.Map((2, "2"), (6, "8"), (7, "5"))

map ++= map2
```

## 元组

元素是由不同的数据类型组合成一个集合

```scala
//创建一个元组
val tuple: (String, Int, Char, Boolean) = ("string", 1, 'c', true)
//使用下划线加元素的下标（不包含0）访问一个元素
tuple._1
//元组最多支持22位元素。元组也可以嵌套
```

## 常见方法

集合中的方法在开发中是至关重要的,需要熟练掌握集合的方法,才能更高效的开发

### 获取长度

Set、Map使用`size`方法获取长度,List和Array使用`length`方法获取长度

### 遍历元素

使用`foreach()`可以更加方便的遍历元素

```scala
val list: List[Int] = List(1, 2, 3)
list.foreach(print)
```

### 是否包含元素

使用`contains()`传一个元素或者Key返回一个Boolean类型,Array,List,Set,Map都适用

### 获取集合的头和尾

用`head`方法来获得头部的元素,底层调用iterator的next方法进行获取头部元素,使用`tail`方法来获取尾部,使用`last`
获取最后一个元素,使用`init`方法可以返回一个不包含最后一个的元素的集合

### 反转集合

用`reverse`方法反转整个集合

```scala
val list: List[Int] = List(1, 2, 3)
val list2 = list.reverse
```

### 取前后n个元素

使用take()方法取元素的n个元素,takeRight()从后面取n个元素

```scala
val list: List[Int] = List(1, 2, 3)
val list2 = list.take(2)
val list2 = list.takeRight(2)
```

### 舍弃前后n个元素

使用drop()方法舍弃元素的n个元素,dropRight()从后面舍弃n个元素

```scala
val list: List[Int] = List(1, 2, 3)
val list2 = list.drop(2)
val list2 = list.dropRight(2)
```

### 并集

合并两个集合类似++方法和++=方法,用`union()`方法合并两个集合

```scala
val list1: List[Int] = List(1, 2, 3)
val list2: List[Int] = List(5, 4, 9)

val list3 = list1.union(list2)
```

### 交集

合并里面相同的元素,使用`intersect()`方法进行调用

```scala
val list1: List[Int] = List(1, 2, 3)
val list2: List[Int] = List(5, 2, 9)

//list3中只有一个2
val list3 = list1.intersect(list2)
```

### 差集

相同的元素不更新于新集合,使用`diff()`方法

```scala
val list1: List[Int] = List(1, 2, 3)
val list2: List[Int] = List(5, 2, 9)

//list3中只有有1和3,不包含2
val list3 = list1.diff(list2)
```

### 拉链

两个集合拆分并合并为一个二元组的集合

```scala
val list1: List[Int] = List(1, 2, 3)
val list2: List[Int] = List(5, 2, 9, 2)

//控制台输出为：List((1,5), (2,2), (3,9))
println(list1.zip(list2))
```

### 滑窗

将一个集合用滑窗位数,分成多个集合,使用`sliding()`方法

```scala
val list: List[Int] = List(1, 2, 3, 5, 2, 9, 2)

//控制台输出为List(1, 2, 3) List(2, 3, 5) List(3, 5, 2)...
list.sliding(3).foreach(println)
//也可以设置步长
list.sliding(3, 2).foreach(println)
```

## 集合简单计算函数

有些简单的方法Scala已经给我们写好了,我们可以直接调用省去了一部分开发时间

### 求和

使用sum方法获取集合里元素所有的和

```scala
val list: List[Int] = List(1, 2, 3, 5, 2, 9, 2)

list.sum
```

### 求乘积

使用`product`方法来求乘积

### 求最大值和最小值

使用`max`方法来求最大值,使用`min`方法来求最小值

### 使用maxBy获取最大值

对象可能包含许多属性,我们可以用`maxBy`方法来指定属性进行比较

```scala
val list: List[(String, Int)] = List(("a", 10), ("b", 2), ("c", 3), ("d", 4), ("e", 5), ("f", 6), ("g", 7), ("h", 8))

//控制台输出为：(a,10)
println(list.maxBy(_._2))
```

### 排序

使用`sorted`方法进行排序

```scala
val list: List[Int] = List(1, 2, 3, 5, 2, 9, 2)

//控制台输出为：List(1, 2, 2, 2, 3, 5, 9)
println(list.sorted)
//排序并反转,但是这样有个问题,为什么排序的时候不直接反转呢？显然下方的代码并不是一个完美的答案
println(list.sorted.reverse)
//通过传入参数进行排序,如果不传参数默认传一个Ordering参数从小到大进行排序,通过隐式传参实现
println(list.sorted(Ordering[Int]).reverse)
```

### 使用sortBy进行排序

如果集合里的对象属性有很多,动态实现属性排序,可以使用sortBy进行排序

```scala
val list: List[(String, Int)] = List(("a", 10), ("b", 2), ("c", 3), ("d", 4), ("e", 5), ("f", 6), ("g", 7), ("h", 8))

//控制台输出为：List((b,2), (c,3), (d,4), (e,5), (f,6), (g,7), (h,8), (a,10))
println(list.sortBy(_._2))
```

### 使用sortWith进行排序

类似Java中的比较器,判断元素的值是否为真

```scala
val list: List[(String, Int)] = List(("a", 10), ("b", 2), ("c", 3), ("d", 4), ("e", 5), ("f", 6), ("g", 7), ("h", 8))

//控制台输出：List((b,2), (c,3), (d,4), (e,5), (f,6), (g,7), (h,8), (a,10))
println(list.sortWith((a, b) => a._2 < b._2))
//可简写为
println(list.sortWith(_._2 < _._2))
```

## 集合高级计算函数

集合高级计算函数,可以更加高度自定义结果,实现更高级的内容

### 过滤

使用`filter`方法进行集合过滤

```scala
val list: List[Int] = List(1, 2, 3, 5, 2, 9, 2)

//过滤所有奇数
println(list.filter(_ % 2 == 0))
``` 

### 映射Map

将集合加工后映射到新集合里,使用`map`方法

```scala
val list: List[Int] = List(1, 2, 3, 5, 2, 9, 2)

//将每个数值都乘二
println(list.map(_ * 2))
```

### 扁平化

使用`flatten`方法将集合里的集合打散映射到一个集合

```scala
val list = List(List(1, 2, 3), List(4, 5, 6), List(7, 8, 9))

//控制台输出为：List(1, 2, 3, 4, 5, 6, 7, 8, 9)
println(list.flatten)
```

### 扁平映射

使用`flatMap()`方法进行映射,再扁平化。需要传入一个函数

```scala
val list = List("hello world", "hello Ema", "what do you do", "i want watch")
//将单词打散映射再扁平化
println(list.flatMap(_.split(" ")))
```

### 分组

可以将元素通过需求分组并返回一个map映射,使用`groupBy`方法

```scala
val list = List("hello world", "hello Ema", "what do you do", "i want watch")
//单词分割并打散再分组，控制台输出为：HashMap(E -> List(Ema), y -> List(you), i -> List(i), h -> List(hello, hello), w -> List(world, what, want, watch), d -> List(do, do))
println(list.flatMap(_.split(" ")).groupBy(_.charAt(0)))
```

### 归约

将前两个数进行操作,操作完之后拿着结果和下一个数进行操作,使用`reduce`方法

```scala
val list = List("hello world", "hello Ema", "what do you do", "i want watch")
//将单词拼成一起，控制台输出为：helloworldhelloEmawhatdoyoudoiwantwatch
println(list.flatMap(_.split(" ")).reduce(_ + _))
```

### 折叠

类似归约,折叠在计算之前包含一个初始值,使用`fold()()`方法

> 括号没打多,这是函数柯里化

```scala
val list = List("hello world", "hello Ema", "what do you do", "i want watch")
拼在一起之前加上初始值
println(list.flatMap(_.split(" ")).fold("我是初始值")(_ + _))
```

### 合并map

如果用++或者++=合并两个map会更改掉第一个map的值,直接丢弃并不会组合在一起,有时候需要合并map并相加里面的值,那么可以使用foldLeft方法进行合并

```scala
val map = Map(("a", 1), ("b", 1), ("c", 1))
val map1 = mutable.Map(("a", 1), ("b", 2), ("c", 3), ("d", 4))

//传入map和map1 
println(map.foldLeft(map1)((mergedMap, kv) => {
  //由于map1是可变map，在map1中进行更新
  mergedMap(kv._1) = mergedMap(kv._1) + kv._2
  //返回更改后的map
  mergedMap
  //控制台输出为：HashMap(a -> 2, b -> 3, c -> 4, d -> 4)
}))
```

### 单词计数(WordCount)

将hello scala hbase kafka,hello scala hbase,hello scala,hello进行单词计数,计算单词出现的次数

```scala
val list = List("hello scala hbase kafka", "hello scala hbase", "hello scala", "hello")

//控制台输出为：List((kafka,1), (hbase,2), (scala,3), (hello,4))
println(list.flatMap(_.split(" ")).groupBy(s => s).map(kv => (kv._1, kv._2.length)).toList.sortBy(kv => kv._2))
```

更加高级的单词计数

```scala
val list = List(("hello scala hbase kafka", 2), ("hello scala hbase", 3), ("hello scala", "hello", 5))

//控制台输出为：List((kafka,2), (hbase,5), (scala,7), (hello,12))
println(list.flatMap(kv => kv._1.split(" ").map((_, kv._2))).groupBy(_._1).map(tuple => (tuple._1, tuple._2.map(_._2).sum)).toList.sortBy(_._2))
```