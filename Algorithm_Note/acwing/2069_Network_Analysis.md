# 2069. 网络分析

小明正在做一个网络实验。

他设置了 n 台电脑，称为节点，用于收发和存储数据。

初始时，所有节点都是独立的，不存在任何连接。

小明可以通过网线将两个节点连接起来，连接后两个节点就可以互相通信了。

两个节点如果存在网线连接，称为相邻。

小明有时会测试当时的网络，他会在某个节点发送一条信息，信息会发送到每个相邻的节点，之后这些节点又会转发到自己相邻的节点，直到所有直接或间接相邻的节点都收到了信息。

所有发送和接收的节点都会将信息存储下来。

一条信息只存储一次。

给出小明连接和测试的过程，请计算出每个节点存储信息的大小。

#### 输入格式

输入的第一行包含两个整数 n,m，分别表示节点数量和操作数量。

节点从 1 至 n 编号。

接下来 m 行，每行三个整数，表示一个操作。

- 如果操作为 `1 a b`，表示将节点 a 和节点 b 通过网线连接起来。当 a = b 时，表示连接了一个自环，对网络没有实质影响。
- 如果操作为 `2 p t`，表示在节点 p 上发送一条大小为 t 的信息。

#### 输出格式

输出一行，包含 n 个整数，相邻整数之间用一个空格分割，依次表示进行完上述操作后节点 1 至节点 n 上存储信息的大小。

#### 数据范围

1≤n≤10000, 1≤m≤105, 1≤t≤100

#### 输入样例：

```
4 8
1 1 2
2 1 10
2 3 5
1 4 1
2 2 2
1 1 2
1 2 4
2 2 1
```

#### 输出样例：

```
13 13 5 3
```



## 并查集

公共父节点

```java
import java.io.*;

public class Main {
    static int N, M, maxN = 20010;
    static int[] p = new int[maxN], res = new int[maxN];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String[] str = br.readLine().split(" ");
        N = Integer.parseInt(str[0]); M = Integer.parseInt(str[1]);

        for (int i = 0; i < maxN; i++) p[i] = i;
        int root = N;
        while (M-- > 0) {
            str = br.readLine().split(" ");
            int op= Integer.parseInt(str[0]);
            int a = Integer.parseInt(str[1]), b = Integer.parseInt(str[2]);

            if (op == 1) {  // 连接
                a = find(a); b = find(b);
                if (a != b) {
                    root++;
                    p[a] = root; p[b] = root;
                }
            } else {  // 发送
                res[find(a)] += b;  // 加到新增公共父节点，下次查询会向下传递
            }
        }

        for(int i = 1; i <= N; i++) {
            if (i == find(i)) bw.write(res[i]+" ");
            else bw.write(res[i]+res[find(i)]+" ");
        }
        bw.flush(); bw.close();
    }

    private static int find(int n) {
        if (p[n] == n || p[n] == p[p[n]]) return p[n];
        int t = find(p[n]);
        res[n] += res[p[n]];
        return p[n] = t;
    }
}
```



自连操作

```java
import java.io.*;

public class Main {
    static int N, M, maxN = 10010;
    static int[] p = new int[maxN], res = new int[maxN];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String[] str = br.readLine().split(" ");
        N = Integer.parseInt(str[0]); M = Integer.parseInt(str[1]);

        for (int i = 1; i <= N; i++) p[i] = i;
        while (M-- > 0) {
            str = br.readLine().split(" ");
            int op= Integer.parseInt(str[0]);
            int a = Integer.parseInt(str[1]), b = Integer.parseInt(str[2]);


            if (op == 1) {  // 连接
                a = find(a); b = find(b);
                if (a != b) {  // 后面向下更新时会连同连接前的值向下更新，所以要先减去防止重复
                    res[a] -= res[b];  // 亲子节点储存父节点的额外值（连接前的值）
                    p[a] = b;
                }
            } else {  // 发送
                res[find(a)] += b;
            }
        }

        for(int i = 1; i <= N; i++) {  // find最后统一再更新一次值
            if (i == find(i)) bw.write(res[i] + " ");  // 考虑当作父节点的点
            else bw.write(res[i] + res[find(i)] + " ");
        }
        bw.flush(); bw.close();
    }

    private static int find(int n) {
        if (p[n] == n || p[n] == p[p[n]]) return p[n];
        int t = find(p[n]);
        res[n] += res[p[n]];  // 父值往下传
        p[n] = t;  // 压缩路径
        return t;
    }
}
```



？？？，状态记录

 ```java
import java.util.Scanner;

public class Main {
    static int N, M, maxN = 100010;
    static int[] cout = new int[maxN], res = new int[maxN];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt(); M = sc.nextInt(); sc.nextLine();

        UnionFind uf = new UnionFind(N);
        while (M-- > 0) {
            String[] str = sc.nextLine().split(" ");
            int op = Integer.parseInt(str[0]);
            int a = Integer.parseInt(str[1]), b = Integer.parseInt(str[2]);
            if (op == 1) uf.union(a, b);  // 连接
            else uf.add(a, b);  // 发送
        }

        for (int i = 1; i <= N; i++) {
            System.out.print(res[i]+ cout[uf.find(i)] + " ");
        }
    }

    private static class UnionFind {
        int[] p;

        public UnionFind(int N) {
            p = new int[N+1];
            for (int i = 1; i <= N; i++) p[i] = i;
        }

        private int find(int a) {
            while (a != p[a]) {  // 路径压缩
                p[a] = p[p[a]];
                a = p[a];
            }
            return a;
        }

        private void union(int a, int b) {
            if (a == b || find(a) == find(b)) return;
            for (int i = 1; i <= N; i++) {  // 遍历更新所有点
                if (find(i) == p[a] || p[i] == p[b]) {
                    res[i] += cout[p[i]];
                }
            }

            cout[p[a]] = 0; cout[p[b]] = 0;
            p[p[a]] = p[b];
        }

        private void add(int a, int b) {
            cout[find(a)] += b;
        }
    }
}
 ```

​	