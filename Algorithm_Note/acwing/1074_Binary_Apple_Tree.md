# 1074. 二叉苹果树

有一棵二叉苹果树，如果树枝有分叉，一定是分两叉，即没有只有一个儿子的节点。

这棵树共 N 个节点，编号为 1 至 N，树根编号一定为 1。

我们用一根树枝两端连接的节点编号描述一根树枝的位置。

一棵苹果树的树枝太多了，需要剪枝。但是一些树枝上长有苹果，给定需要保留的树枝数量，求最多能留住多少苹果。

这里的保留是指最终与1号点连通。

#### 输入格式

第一行包含两个整数 N 和 Q，分别表示树的节点数以及要保留的树枝数量。

接下来 N−1 行描述树枝信息，每行三个整数，前两个是它连接的节点的编号，第三个数是这根树枝上苹果数量。

#### 输出格式

输出仅一行，表示最多能留住的苹果的数量。

#### 数据范围

1≤Q<N≤100, N≠1, 每根树枝上苹果不超过 30000 个。

#### 输入样例：

```
5 2
1 3 1
1 4 10
2 3 20
3 5 20
```

#### 输出样例：

```
21
```

## DFS + DP

```java
import java.util.*;

class Main {
    static int maxN = 110, maxM = maxN * 2, N, M, idx;
    static int[][] dp = new int[maxN][maxN];    // dp[i][j]: i号节点为根 的子树中选择j条边的最大价值
    static int[] info = new int[maxN];
    static int[] from = new int[maxM], to = new int[maxM], val = new int[maxM];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt(); M = sc.nextInt();  // 要保留的树枝数量

        Arrays.fill(info, -1);
        for (int i = 1; i <= N - 1; i ++) {
            int a = sc.nextInt(), b = sc.nextInt(), c = sc.nextInt();
            add(a, b, c); add(b, a, c);
        }

        dfs(1, -1);
        System.out.println(dp[1][M]);
    }

    private static void dfs(int u, int f) {
        for (int i = info[u]; i != -1; i = from[i]) {
            int t = to[i];
            if (t == f) continue;

            dfs(t, u);
            // 列态循环(动态规划)
            for (int m = M; m > 0; m--) {  // 循环决策
                for (int c = m-1; c >= 0; c--) {  // 循环该决策不同情况(循环顺序不重要)
                    dp[u][m] = Math.max(dp[u][m], dp[u][c] + dp[t][m-c-1] + val[i]);
                }
            }
        }
    }

    private static void add(int a, int b, int c) {
        from[idx] = info[a];
        to[idx] = b;
        val[idx] = c;
        info[a] = idx++;
    }
}
```

