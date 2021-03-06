# 332. 股票交易

最近 lxhgww 又迷上了投资股票，通过一段时间的观察和学习，他总结出了股票行情的一些规律。

通过一段时间的观察，lxhgww 预测到了未来 T 天内某只股票的走势，第 i 天的股票买入价为每股 APi，第 i 天的股票卖出价为每股 BPi（数据保证对于每个 i，都有 APi≥BPi），但是每天不能无限制地交易，于是股票交易所规定第 i 天的一次买入至多只能购买 ASi 股，一次卖出至多只能卖出 BSi 股。

另外，股票交易所还制定了两个规定。

为了避免大家疯狂交易，股票交易所规定在两次交易（某一天的买入或者卖出均算是一次交易）之间，至少要间隔 W 天，也就是说如果在第 i 天发生了交易，那么从第 i+1 天到第 i+W 天，均不能发生交易。

同时，为了避免垄断，股票交易所还规定在任何时间，一个人的手里的股票数不能超过 MaxP。

在第 1 天之前，lxhgww 手里有一大笔钱（可以认为钱的数目无限），但是没有任何股票，当然，T 天以后，lxhgww 想要赚到最多的钱，聪明的程序员们，你们能帮助他吗？

#### 输入格式

第 1 行包括 3 个整数，分别是 T，MaxP，W。

第 2..T+1 行，第 i+1 行代表第 i 天的股票走势，每行 4 个整数，分别表示 APi，BPi，ASi，BSi。

#### 输出格式

输出包含一个整数，表示能赚到的做多的钱数。

#### 数据范围

0≤W<T≤2000, 1≤MaxP≤2000, 1≤BPi≤APi≤1000, 1≤ASi≤BSi≤MaxP

#### 输入样例：

```
5 2 0
2 1 1 1
2 1 1 1
3 2 1 1
4 3 1 1
5 4 1 1
```

#### 输出样例：

```
3
```



## DP

```java
import java.io.*;
import java.util.*;

class Main{
    int N, C, W, INF = 0x3f3f3f3f, maxN = 2011;
    int[] q = new int[maxN];
    int[][] f = new int[maxN][maxN];

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] str = br.readLine().split(" ");
        N = Integer.parseInt(str[0]); C = Integer.parseInt(str[1]);
        W = Integer.parseInt(str[2]);
        Arrays.fill(f[0], -INF);
        for (int i = 1; i <= N; i++) {  // 遍历每天
            str = br.readLine().split(" ");
            int BP = Integer.parseInt(str[0]), SP = Integer.parseInt(str[1]);
            int BC = Integer.parseInt(str[2]), SC = Integer.parseInt(str[3]);

            for (int j = 0; j <= C; j++) {
                if (j <= BC) f[i][j] = -(BP * j);  // 第 i 天持股数为 j 的收益
                else f[i][j] = -INF;  // 不可能事件，初始值-INF
                f[i][j] = Math.max(f[i][j], f[i-1][j]);
            }
            if (i <= W) continue;

            int hh = 0, tt = -1, pre = i-W-1;
            for (int j = 0; j <= C; j++) {  // buying,不同买进情况的最大现金流方案(j:持有总量)
                while (hh<=tt && j-q[hh] > BC) hh++;  // j-q[hh]是以当前价格买进的数量
                if (hh<=tt) f[i][j] = Math.max(f[i][j], f[pre][q[hh]] - (j-q[hh])*BP);
                while (hh<=tt && f[pre][q[tt]] + q[tt]*BP <= f[pre][j] + j*BP) tt--;  // 最大栈
                q[++tt] = j;
            }

            hh = 0; tt = -1;
            for (int j = C; j >= 0; j--) {  // selling(j是持有总量, q[hh]-j 是以现在价格卖出的数量)
                while (hh<=tt && q[hh]-j > SC) hh++;  // q[hh]恒大于j
                if (hh<=tt) f[i][j] = Math.max(f[i][j], f[pre][q[hh]] + q[hh]*SP - j*SP);
                while (hh<=tt && f[pre][q[tt]] + q[tt]*SP <= f[pre][j] + j*SP) tt--;  // 最大栈
                q[++tt] = j;
            }
        }
        System.out.println(f[N][0]);
    }
}
```

