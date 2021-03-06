# 318. 划分大理石

有价值分别为 1..6 的大理石各 a[1..6] 块，现要将它们分成两部分，使得两部分价值之和相等，问是否可以实现。

其中大理石的总数不超过 20000。

#### 输入格式

输入包含多组数据！

每组数据占一行，包含 6 个整数，表示 a[1]∼a[6]。

当输入为 `0 0 0 0 0 0` 时表示输入结束，且该行无需考虑。

#### 输出格式

每组数据输出一个结果，每个结果占一行。

如果可以实现则输出 `Can`，否则输出 `Can't`。

#### 输入样例：

```
4 7 4 5 9 1
9 8 1 7 2 4
6 6 8 5 9 2
1 6 6 1 0 7
5 9 3 8 8 4
0 0 0 0 0 0
```

#### 输出样例：

```
Can't
Can
Can't
Can't
Can
```



## DP+二进制优化

```java
import java.util.*;

public class Main {
    int maxN = 20010;
    int[] cout = new int[maxN];
    boolean[] f = new boolean[maxN];

    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            int sum = 0;
            for (int i = 1; i <= 6; i ++ ) {
                cout[i] = sc.nextInt();
                sum += cout[i] * i;
            }
            if (sum == 0) break;
            if ((sum&1) == 1) System.out.println("Can't");
            else {
                int tar = sum/2;
                Arrays.fill(f, false);
                f[0] = true;
                for (int i = 1; i <= 6; i++) {  // 遍历石头
                    int cnt = cout[i], k = 1;
                    while (cnt >= k) {  // 遍历二进制状态（01）
                        for (int s = tar; s >= i * k; s--) f[s] |= f[s - i*k];
                        cnt -= k;  // 二进制优化，后面的 会与 前面的 累计
                        k <<= 1;  // 也可以k++
                    }
                    if (cnt > 0)  for (int j = tar; j >= i * cnt; j--) f[j] |= f[j - i*cnt];
                }

                if (f[tar]) System.out.println("Can");
                else System.out.println("Can't");
            }
        }
    }
}
```

