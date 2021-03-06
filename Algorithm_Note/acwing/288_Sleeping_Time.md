# 288. 休息时间

在某个星球上，一天由 N 个小时构成，我们称 0 点到 1 点为第 1 个小时、1 点到 2 点为第 2 个小时，以此类推。

在第 i 个小时睡觉能够恢复 Ui 点体力。

在这个星球上住着一头牛，它每天要休息 B 个小时。

它休息的这 B 个小时不一定连续，可以分成若干段，但是在每段的第一个小时，它需要从清醒逐渐入睡，不能恢复体力，从下一个小时开始才能睡着。

为了身体健康，这头牛希望遵循生物钟，每天采用相同的睡觉计划。

另外，因为时间是连续的，即每一天的第 N 个小时和下一天的第 1 个小时是相连的（N 点等于 0 点），这头牛只需要在每 N 个小时内休息够 B 个小时就可以了。

请你帮忙给这头牛安排一个睡觉计划，使它每天恢复的体力最多。

#### 输入格式

第 1 行输入两个空格隔开的整数 N 和 B。

第 2..N+1 行，第 i+1 行包含一个整数 Ui。

#### 输出格式

输出一个整数，表示恢复的体力值。

#### 数据范围

3≤N≤3830, 2≤B<N, 0≤Ui≤200000

#### 输入样例：

```
5 3
2
0
3
1
4
```

#### 输出样例：

```
6
```

#### 样例解释

这头牛每天 3 点入睡，睡到次日 1 点，即 \[1,4,2\] 时间段休息，每天恢复体力值最大，为 0+4+2=6。



## DP

```java
import java.util.*;

public class Main {
    int N, M, maxN = 4000, INF = 0x3f3f3f3f;
    int[] arr = new int[maxN]; int[][][] f = new int[2][maxN][2];
    
    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt(); M = sc.nextInt();
        for (int i = 1; i <= N; i++) arr[i] = sc.nextInt();
        
        // 第N项不睡
        for (int[][] ff: f) for (int[] a: ff) Arrays.fill(a, -INF);
        f[1][0][0] = 0;  
        f[1][1][1] = 0;  
		helper();
        int res = f[N&1][M][0];
        
        // 第N项在睡
        for (int[][] ff: f) for (int[] a: ff) Arrays.fill(a, -INF);
        f[1][0][0] = 0; 
        f[1][1][1] = arr[1];
		helper();
        res = Math.max(res, f[N&1][M][1]);
        
        System.out.println(res);
    }
    
    void helper() {
        for (int i = 2; i <= N; i++) {
            for (int j = 0; j <= M; j++) {
                // 前i项，睡了j小时，第i项 不睡觉 的最大收益
                f[i&1][j][0] = Math.max(f[i-1&1][j][0], f[i-1&1][j][1]);
                // 前i项，睡了j小时，第i项 在睡觉 的最大收益
                if (j >= 1) f[i&1][j][1] = Math.max(f[i-1&1][j-1][0], f[i-1&1][j-1][1]+arr[i]);
            }
        }        
    }
}
```

