# 149. 荷马史诗

追逐影子的人，自己就是影子。 ——荷马

达达最近迷上了文学。

她喜欢在一个慵懒的午后，细细地品上一杯卡布奇诺，静静地阅读她爱不释手的《荷马史诗》。

但是由《奥德赛》和《伊利亚特》组成的鸿篇巨制《荷马史诗》实在是太长了，达达想通过一种编码方式使得它变得短一些。

一部《荷马史诗》中有 n 种不同的单词，从 1 到 n 进行编号。其中第 i 种单词出现的总次数为 wi。

达达想要用 k 进制串 si 来替换第 i 种单词，使得其满足如下要求:

对于任意的 1≤i,j≤n，i≠j，都有：si 不是 sj 的前缀。

现在达达想要知道，如何选择 si，才能使替换以后得到的新的《荷马史诗》长度最小。

在确保总长度最小的情况下，达达还想知道最长的 si 的最短长度是多少？

一个字符串被称为 kk 进制字符串，当且仅当它的每个字符是 0 到 k−1 之间（包括 0 和 k−1）的整数。

字符串 Str1 被称为字符串 Str2 的前缀，当且仅当：存在 1≤t≤m，使得 Str1=Str2[1..t]。

其中，m 是字符串 Str2 的长度，Str2[1..t] 表示 Str2 的前 t 个字符组成的字符串。

**注意**:请使用 64 位整数进行输入输出、储存和计算。

#### 输入格式

输入文件的第 1 行包含 2 个正整数 n,k，中间用单个空格隔开，表示共有 n 种单词，需要使用 k 进制字符串进行替换。

第 2∼n+1 行：第 i+1 行包含 1 个非负整数 wi，表示第 i 种单词的出现次数。

#### 输出格式

输出文件包括 2 行。

第 1 行输出 1 个整数，为《荷马史诗》经过重新编码以后的最短长度。

第 2 行输出 1 个整数，为保证最短总长度的情况下，最长字符串 si 的最短长度。

#### 数据范围

2≤n≤100000, 2≤k≤9, 1≤wi≤10^12^

#### 输入样例：

```
4 2
1
1
2
2
```

#### 输出样例：

```
12
2
```



## heap

```java
import java.util.*;

public class Main {
    Queue<Node> heap = new PriorityQueue<>(100002, (a,b) -> (a.x == b.x ? a.y-b.y : (a.x > b.x ? 1 : -1)));
    Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        int N = sc.nextInt(), K = sc.nextInt();
        for (int i = 0 ; i < N ; i ++) heap.offer(new Node(sc.nextLong(), 0));
        solve(K);
    }

    void solve(int k) {  // 防止最后节点不够k，而叶子节点多加了
        while ((heap.size()-1)%(k-1) != 0) heap.offer(new Node(0, 0));

        long res = 0;
        while (heap.size() > 1) {
            long sum = 0;
            int depth = 0;
            for (int i = 0 ; i < k ; i++) {
                Node tmp = heap.poll();
                sum += tmp.x;
                depth = Math.max(depth, tmp.y);
            }
            heap.add(new Node(sum, depth + 1));
            res += sum;
        }

        System.out.println(res);
        System.out.println(heap.peek().y);
    }

    static class Node {
        long x;
        int y;
        Node (long x, int y) {
            this.x = x; this.y = y;
        }
    }
}
```

