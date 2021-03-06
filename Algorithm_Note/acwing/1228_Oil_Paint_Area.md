# 1228. 油漆面积

X星球的一批考古机器人正在一片废墟上考古。

该区域的地面坚硬如石、平整如镜。

管理人员为方便，建立了标准的直角坐标系。

每个机器人都各有特长、身怀绝技。

它们感兴趣的内容也不相同。

经过各种测量，每个机器人都会报告一个或多个矩形区域，作为优先考古的区域。

矩形的表示格式为 (x1,y1,x2,y2)，代表矩形的两个对角点坐标。

为了醒目，总部要求对所有机器人选中的矩形区域涂黄色油漆。

小明并不需要当油漆工，只是他需要计算一下，一共要耗费多少油漆。

其实这也不难，只要算出所有矩形覆盖的区域一共有多大面积就可以了。

注意，各个矩形间可能重叠。

#### 输入格式

第一行，一个整数 n，表示有多少个矩形。

接下来的 n 行，每行有 4 个整数 x1,y1,x2,y2，空格分开，表示矩形的两个对角顶点坐标。

#### 输出格式

一行一个整数，表示矩形覆盖的总面积。

#### 数据范围

1≤n≤10000,
0≤x1,x2,y2,y2≤10000
数据保证 x1<x2 且 y1<y2。

#### 输入样例：

```
3
1 5 10 10
3 1 20 20
2 7 15 17
```

#### 输出样例：

```
340
```



## 线段树

大概理解下面这三种情况：

 ![](pic\1228.jpg)


```java
import java.io.*;
import java.util.*;

class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static int maxN = 10010, N;
    static Node[] data = new Node[maxN * 2];
    static Seg[] segs = new Seg[maxN * 4];

    public static void main(String[] args) throws IOException {
        new Main().init();
    }

    void init() throws IOException {
        N = Integer.parseInt(br.readLine());
        int idx = 0;
        for (int i = 0; i < N; i++) {
            String[] s = br.readLine().split(" ");
            int x1 = Integer.parseInt(s[0]); int y1 = Integer.parseInt(s[1]);
            int x2 = Integer.parseInt(s[2]); int y2 = Integer.parseInt(s[3]);

            data[idx++] = new Node(x1, y1, y2, 1);
            data[idx++] = new Node(x2, y1, y2, -1);
        }

        build(1, 0, 10000);

        Arrays.sort(data, 0, idx);
        int res = 0;
        for (int i = 0; i < idx; i++) {  // (有左必有右，且左右相等)
            if (i > 0) res += segs[1].len * (data[i].x - data[i-1].x);  // 高 * 宽
            update(1, data[i].y1, data[i].y2-1, data[i].k);  // 更新后面 哪些高 需要乘
        }

        bw.write(res+"\n");
        bw.flush(); bw.close();
    }

    void build(int root, int start, int end) {
        segs[root] = new Seg(start, end);
        if (start == end) return;
        int mid = start + end >> 1;
        build(root<<1, start, mid);
        build(root<<1|1, mid + 1, end);
    }

    void update(int root, int start, int end, int k) {
        if (start <= segs[root].le && segs[root].ri <= end) segs[root].cnt += k;
        else {
            int mid = segs[root].le + segs[root].ri >> 1;
            if (start <= mid) update(root<<1, start, end, k);
            if (end > mid) update(root<<1|1, start, end, k);
        }

        pushup(root);
    }

    void pushup(int x) {
        if (segs[x].cnt > 0) segs[x].len = segs[x].ri - segs[x].le + 1;
        else {
            if (segs[x].le == segs[x].ri) segs[x].len = 0;
            else segs[x].len = segs[x<<1].len + segs[x<<1|1].len;
        }
    }

    private class Seg {
        int le, ri;
        int cnt;    // 当前 le到ri区间内 全被覆盖 的次数
        int len;    // 当前 le到ri区间内 被覆盖的所有长度 (y轴)

        public Seg(int l, int r) {
            le = l; ri = r;
        }
    }

    private class Node implements Comparable<Node> {
        int x, y1, y2, k;

        public Node(int x, int y1, int y2, int k) {
            this.x = x; this.y1 = y1; this.y2 = y2; this.k = k;
        }

        @Override
        public int compareTo(Node Node) {  // 按横坐标升序排列
            return this.x - Node.x;
        }
    }
}
```

