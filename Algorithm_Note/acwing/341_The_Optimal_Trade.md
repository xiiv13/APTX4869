# 341. 最优贸易

C 国有 n 个大城市和 m 条道路，每条道路连接这 n 个城市中的某两个城市。

任意两个城市之间最多只有一条道路直接相连。

这 m 条道路中有一部分为单向通行的道路，一部分为双向通行的道路，双向通行的道路在统计条数时也计为 1 条。

C 国幅员辽阔，各地的资源分布情况各不相同，这就导致了同一种商品在不同城市的价格不一定相同。

但是，同一种商品在同一个城市的买入价和卖出价始终是相同的。

商人阿龙来到 C 国旅游。

当他得知“同一种商品在不同城市的价格可能会不同”这一信息之后，便决定在旅游的同时，利用商品在不同城市中的差价赚一点旅费。

设 C 国 n 个城市的标号从 1∼n，阿龙决定从 1 号城市出发，并最终在 n 号城市结束自己的旅行。

在旅游的过程中，任何城市可以被重复经过多次，但不要求经过所有 n 个城市。

阿龙通过这样的贸易方式赚取旅费：他会选择一个经过的城市买入他最喜欢的商品——水晶球，并在之后经过的另一个城市卖出这个水晶球，用赚取的差价当做旅费。

因为阿龙主要是来 C 国旅游，**他决定这个贸易只进行最多一次，当然，在赚不到差价的情况下他就无需进行贸易。**

现在给出 n 个城市的水晶球价格，m 条道路的信息（每条道路所连接的两个城市的编号以及该条道路的通行情况）。

请你告诉阿龙，他最多能赚取多少旅费。

#### 输入格式

第一行包含 2 个正整数 n 和 m，中间用一个空格隔开，分别表示城市的数目和道路的数目。

第二行 n 个正整数，每两个整数之间用一个空格隔开，按标号顺序分别表示这 n 个城市的商品价格。

接下来 m 行，每行有 3 个正整数，x，y，z，每两个整数之间用一个空格隔开。

如果 z=1，表示这条道路是城市 x 到城市 y 之间的单向道路；如果 z=2，表示这条道路为城市 x 和城市 y 之间的双向道路。

#### 输出格式

一个整数，表示答案。

#### 数据范围

1≤n≤100000, 1≤m≤500000, 1≤各城市水晶球价格≤100

#### 输入样例：

```
5 5
4 3 5 6 1
1 2 1
1 4 1
2 3 2
3 5 1
4 5 2
```

#### 输出样例：

```
5
```



## SPFA

最一般的最短路维护的是路径上的sum性质，本题维护的是max和min性质，sum性质具有累加性（就是要从前面的值基础上累加，后续出现只会越来越大，所以第一次出现的就是最短），而max 和min对于新出现的数，单独比较即可，所以不能用dijkstra（dijkstra就是利用的sum的累加性）
总的来说就是max和min，后面出现的数不一定比前面的数都差（而dijkstra的sum性质能保证后面出现的数都比前面的数都差）

```java
import java.util.*;
import java.io.*;

public class Main {
    int N, M, idx, maxN = 100010, maxM = 2000010, INF = 0x3f3f3f3f;
    boolean[] vis = new boolean[maxN];
    int[] info = new int[maxN], rnfo = new int[maxN], arr = new int[maxN];
    int[] from = new int[maxM], to = new int[maxM];
    int[] dmin = new int[maxN], dmax = new int[maxN];

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] str = br.readLine().split(" ");
        N = Integer.parseInt(str[0]); M = Integer.parseInt(str[1]);

        str = br.readLine().split(" ");
        for (int i = 1; i <= N; i++) arr[i] = Integer.parseInt(str[i-1]);

        Arrays.fill(info, -1); Arrays.fill(rnfo, -1);
        while (M-- > 0) {
            str = br.readLine().split(" ");
            int a = Integer.parseInt(str[0]), b = Integer.parseInt(str[1]);
            int z = Integer.parseInt(str[2]);
            add(a, b, true); add(b, a, false);  // 单向边
            if (z == 2) { add(b, a, true); add(a, b, false); }  // 双向边
        }

        spfa(dmin, 1, info, true);  // 从 1 开始走，买入水晶球的最低价格 dmin[i]
        spfa(dmax, N, rnfo, false); // 从 N 开始走，卖出水晶球的最高价格 dmax[i]

        int res = 0;  // [i]时最大价格和最低价格
        for (int i = 1; i <= N; i++) res = Math.max(res, dmax[i] - dmin[i]);
        System.out.println(res);
    }

    void spfa(int[] dist, int start, int[] info, boolean flag) {
        Arrays.fill(vis, false);
        if (flag) Arrays.fill(dist, INF);
        else Arrays.fill(dist, -INF);
        dist[start] = arr[start];

        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            vis[cur] = false;

            for (int i = info[cur]; i != -1; i = from[i]) {
                int t = to[i];

                if ((flag && dist[t] > Math.min(dist[cur], arr[t]))
                        || (!flag && dist[t] < Math.max(dist[cur], arr[t]))) {
                    if (flag) dist[t] = Math.min(dist[cur], arr[t]);
                    else dist[t] = Math.max(dist[cur], arr[t]);

                    if (!vis[t]) {
                        vis[t] = true;
                        queue.add(t);
                    }
                }
            }
        }
    }

    void add(int a, int b, boolean d) {
        to[idx] = b;
        if (d) {
            from[idx] = info[a];
            info[a] = idx++;
        } else {
            from[idx] = rnfo[a];
            rnfo[a] = idx++;
        }
    }
}
```

