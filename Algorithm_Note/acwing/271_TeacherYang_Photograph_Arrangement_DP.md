# 271. 杨老师的照相排列

有 N 个学生合影，站成左端对齐的 k 排，每排分别有 N1,N2,…,Nk 个人。 (N1≥N2≥…≥Nk)

第 1 排站在最后边，第 k 排站在最前边。

学生的身高互不相同，把他们从高到底依次标记为 1,2,…,N。

在合影时要求每一排从左到右身高递减，每一列从后到前身高也递减。

问一共有多少种安排合影位置的方案？

下面的一排三角矩阵给出了当 N=6,k=3,N1=3,N2=2,N3=1 时的全部 16 种合影方案。注意身高最高的是 1，最低的是 6。

```
123 123 124 124 125 125 126 126 134 134 135 135 136 136 145 146
45  46  35  36  34  36  34  35  25  26  24  26  24  25  26  25
6   5   6   5   6   4   5   4   6   5   6   4   5   4   3   3
```

#### 输入格式

输入包含多组测试数据。

每组数据两行，第一行包含一个整数 k 表示总排数。

第二行包含 k 个整数，表示从后向前每排的具体人数。

当输入 k=0 的数据时，表示输入终止，且该数据无需处理。

#### 输出格式

每组测试数据输出一个答案，表示不同安排的数量。

每个答案占一行。

#### 数据范围

1≤k≤5,学生总人数不超过 30 人。

#### 输入样例：

```
1
30
5
1 1 1 1 1
3
3 2 1
4
5 3 3 1
5
6 5 4 3 2
2
15 15
0
```

#### 输出样例：

```
1
1
16
4158
141892608
9694845
```



## DP

```java
import java.util.*;

class Main {
    public static void main(String[] args) {
        new Main().init();
    }

    void init() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            int N = sc.nextInt(); if (N == 0) return;
            int[] arr = new int[5];
            for (int i = 0; i < N; i++) {
                arr[i] = sc.nextInt();
            }

            // dp[a][b][c][d][e]代表从后往前每排人数分别为a, b, c, d, e的所有方案的集合
            long[][][][][] dp = new long[arr[0]+1][arr[1]+1][arr[2]+1][arr[3]+1][arr[4]+1];
            dp[0][0][0][0][0] = 1;  
            for (int a = 0; a <= arr[0]; a++) {
                for (int b = 0; b <= Math.min(a, arr[1]); b++) {
                    for (int c = 0; c <= Math.min(b, arr[2]); c++) {
                        for (int d = 0; d <= Math.min(c, arr[3]); d++) {
                            for (int e = 0; e <= Math.min(d, arr[4]); e++) {
                                long tmp = 0;
                                if (a>0 && a-1>=b) tmp += dp[a-1][b][c][d][e];
                                if (b>0 && b-1>=c) tmp += dp[a][b-1][c][d][e];
                                if (c>0 && c-1>=d) tmp += dp[a][b][c-1][d][e];
                                if (d>0 && d-1>=e) tmp += dp[a][b][c][d-1][e];
                                if (e>0)           tmp += dp[a][b][c][d][e-1];
                                dp[a][b][c][d][e] += tmp;
                            }
                        }
                    }
                }
            }
            System.out.println(dp[arr[0]][arr[1]][arr[2]][arr[3]][arr[4]]);
        }
    }
}
```
