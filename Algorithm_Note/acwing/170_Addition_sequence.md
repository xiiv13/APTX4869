# 170. 加成序列

满足如下条件的序列X（序列中元素被标号为1、2、3…m）被称为“加成序列”：

1、X[1]=1

2、X[m]=n

3、X[1]<X[2]<…<X[m-1]<X[m]

4、对于每个 k（2≤k≤m）都存在两个整数 i 和 j （1≤i,j≤k−1，i 和 j 可相等），使得X[k]=X[i]+X[j]。

你的任务是：给定一个整数n，找出符合上述条件的长度m**最小的**“加成序列”。

如果有多个满足要求的答案，只需要找出任意一个可行解。

#### 输入格式

输入包含多组测试用例。

每组测试用例占据一行，包含一个整数n。

当输入为单行的0时，表示输入结束。

#### 输出格式

对于每个测试用例，输出一个满足需求的整数序列，数字之间用空格隔开。

每个输出占一行。

#### 数据范围

1≤n≤100

#### 输入样例：

```
5
7
12
15
77
0
```

#### 输出样例：

```
1 2 4 5
1 2 4 6 7
1 2 4 8 12
1 2 4 5 10 15
1 2 4 8 9 17 34 68 77
```



## 迭代加深

```java
import java.util.*;

public class Main {
    static int N, maxN = 110;
    static int[] arr = new int[maxN];

    public static void main(String[] args)  {
        Scanner sc = new Scanner(System.in);
        arr[0] = 1;
        while (true) {
            N = sc.nextInt(); if (N == 0) break;

            int depth = 1;
            while (!dfs(1, depth)) depth++;  // 迭代加深

            for (int i = 0; i < depth; i++) System.out.print(arr[i] + " ");
            System.out.println();
        }
    }

    static boolean dfs(int u, int limit) {
        if (u > limit) return false;  // 超出限制深度，深搜失败
        if (arr[u - 1] == N) return true;  // 找到答案
        if((arr[u-1]<<(limit-u+1)) < N) return false;  // 剪枝
        Set<Integer> set = new HashSet<>();

        for (int i = u - 1; i >= 0; i--) {
            for (int j = i; j >= 0; j--) {  // 暴力枚举，贪心最优优化
                int sum = arr[i] + arr[j];
                if (sum <= arr[u-1] || set.contains(sum) || sum > N) continue;

                set.add(sum);
                arr[u] = sum;

                if (dfs(u + 1, limit)) return true;
            }
        }
        return false;
    }
}
```
