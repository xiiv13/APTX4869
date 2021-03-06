# 1077. 皇宫看守

太平王世子事件后，陆小凤成了皇上特聘的御前一品侍卫。

皇宫各个宫殿的分布，呈一棵树的形状，宫殿可视为树中结点，两个宫殿之间如果存在道路直接相连，则该道路视为树中的一条边。

已知，在一个宫殿镇守的守卫不仅能够观察到本宫殿的状况，还能观察到与该宫殿直接存在道路相连的其他宫殿的状况。

大内保卫森严，三步一岗，五步一哨，每个宫殿都要有人全天候看守，在不同的宫殿安排看守所需的费用不同。

可是陆小凤手上的经费不足，无论如何也没法在每个宫殿都安置留守侍卫。

帮助陆小凤布置侍卫，在看守全部宫殿的前提下，使得花费的经费最少。

#### 输入格式

输入中数据描述一棵树，描述如下：

第一行 n，表示树中结点的数目。

第二行至第 n+1 行，每行描述每个宫殿结点信息，依次为：该宫殿结点标号 i，在该宫殿安置侍卫所需的经费 k，该结点的子结点数 m，接下来 m 个数，分别是这个结点的 m 个子结点的标号 r1,r2,…,rm。

对于一个 n 个结点的树，结点标号在 1 到 n 之间，且标号不重复。

#### 输出格式

输出一个整数，表示最少的经费。

#### 数据范围

1≤n≤1500

#### 输入样例：

```
6
1 30 3 2 3 4
2 16 2 5 6
3 5 0
4 4 0
5 11 0
6 5 0
```

#### 输出样例：

```
25
```

#### 样例解释：

在2、3、4结点安排护卫，可以观察到全部宫殿，所需经费最少，为 16 + 5 + 4 = 25。

## DP

```java
import java.io.*;
import java.util.*;

class Main {
    static int N, maxN = 1510, idx;
    static int[] from = new int[maxN], to = new int[maxN], info = new int[maxN], val = new int[maxN];
    static boolean[] has_fa = new boolean[maxN];
    static int[][] dp = new int[maxN][3];

//    f[i][0]表示第i个节点由 父节点放置的守卫 看护下的最小代价
//    f[i][1]表示第i个节点由 在该节放置的守卫 看护下的最小代价
//    f[i][2]表示第i个节点由 子节点放置的守卫 看护下的最小代价
//    那么可以写出转移关系：
//    f[i][0] += min(f[j][1], f[j][2]);
//    f[i][1] += min(min(f[j][0], f[j][1]), f[j][2]);
//    f[i][2] = min(f[i][2], sum - min(f[j][1], f[j][2]) + f[j][1]);


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine().trim());

        Arrays.fill(info, -1);
        for (int i = 0; i < N; i++) {
            String[] str = br.readLine().split(" ");
            int a = Integer.parseInt(str[0]);
            val[a] = Integer.parseInt(str[1]);
            int cnt = Integer.parseInt(str[2]);
            for (int j = 0; j < cnt; j++) {
                int b = Integer.parseInt(str[3+j]);
                add(a, b);
                has_fa[b] = true;
            }
        }

        int root = 1;
        while(has_fa[root]) root++;
        dfs(root);  // 第一个没有父节点的点

        System.out.println(Math.min(dp[root][1], dp[root][2]));
    }

    private static void dfs(int u) {
        dp[u][1] = val[u];
        int sum = 0;
        for(int i = info[u]; i != -1; i = from[i]) {
            int t = to[i];

            dfs(t);

            dp[u][0] += Math.min(dp[t][1], dp[t][2]);  // dp[t]没爹看
            dp[u][1] += Math.min(dp[t][0], Math.min(dp[t][1], dp[t][2]));  // dp[t]有爹看
            sum += Math.min(dp[t][1], dp[t][2]);  // ##
        }

        // 如果第i个节点由子节点守卫，那么至少有一个子节点处放置了守卫，
        // 并且每个子节点都得到了守卫，且所有子节点不是由父节点守卫的。
        dp[u][2] = 0x3f3f3f3f;
        for(int i = info[u]; i != -1; i = from[i]) {
            int t = to[i];
            // dp[u][2] = 当前子节点由自己守卫 + (其余子节点得到守卫的总代价 - 去掉当前节点的重复值)
            dp[u][2] = Math.min(dp[u][2], dp[t][1] + (sum-Math.min(dp[t][1], dp[t][2])));  // ##
        }  // dp[u][1] += val[u];
    }

    private static void add(int a, int b) {
        from[idx] = info[a];
        to[idx] = b;
        info[a] = idx++;
    }
}
```

