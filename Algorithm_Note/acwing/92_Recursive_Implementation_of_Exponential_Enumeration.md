# 92. 递归实现指数型枚举


从 1~n 这 n 个整数中随机选取任意多个，输出所有可能的选择方案。

#### 输入格式

输入一个整数n。

#### 输出格式

每行输出一种方案。

同一行内的数必须升序排列，相邻两个数用恰好1个空格隔开。

对于没有选任何数的方案，输出空行。

本题有自定义校验器（SPJ），各行（不同方案）之间的顺序任意。

#### 数据范围

1≤n≤15

#### 输入样例：

```
3
```

#### 输出样例：

```
3
2
2 3
1
1 3
1 2
1 2 3
```

## 递归
```java
import java.io.*;

class Main {
    int N, maxN = 20;
    int[] arr = new int[maxN];

    public static void main(String[] args) throws IOException {
        new Main().init();
    }

    void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        dfs(0, 0);
        // for (int i = 1; i <= N; i++) dfs(1, 1, i);
    }

    void dfs(int u, int state) {
        if (u == N) {
            for (int i = 0; i < N; i++) {
                if ((state>>i&1) == 1) System.out.printf("%d ", i+1);
            }
            System.out.println();
            return;
        }
        dfs(u+1, state);  // 不选
        dfs(u+1, state+(1<<u));  // 选
    }

    void dfs(int start, int cnt, int tar) {
        if (cnt > tar) {
            for (int i = 1; i <= tar; i++) System.out.print(arr[i]+" ");
            System.out.println();
            return;
        }

        for (int i = start; i <= N; i++) {
            arr[cnt] = i;
            dfs(i+1, cnt+1, tar);
        }
    }
}
```

.