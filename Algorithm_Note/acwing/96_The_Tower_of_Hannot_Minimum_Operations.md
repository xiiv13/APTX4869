# 96. 奇怪的汉诺塔

汉诺塔问题，条件如下：

1、这里有A、B、C和D四座塔。

2、这里有n个圆盘，n的数量是恒定的。

3、每个圆盘的尺寸都不相同。

4、所有的圆盘在开始时都堆叠在塔A上，且圆盘尺寸从塔顶到塔底逐渐增大。

5、我们需要将所有的圆盘都从塔A转移到塔D上。

6、每次可以移动一个圆盘，当塔为空塔或者塔顶圆盘尺寸大于被移动圆盘时，可将圆盘移至这座塔上。

请你求出将所有圆盘从塔A移动到塔D，所需的最小移动次数是多少。

#### 输出格式

对于每一个整数n(1≤n≤12),输出一个满足条件的最小移动次数，每个结果占一行。

## DP
```java
import java.util.*;

public class Main {
    int[] t3 = new int[15], f4 = new int[15], f5 = new int[15];

    public static void main(String[] args) {
        new Main().init();
    }

    void init() {
        t3[1] = 1;   // t[n]: n個盤移到某點(如：終點)需要的步數
        // i-1 個盤A到B + 最後 1 個從A移到C + i-1 個盤B到A
        for (int i = 2; i <= 12; i++) t3[i] = 2*t3[i-1] + 1;

        Arrays.fill(f4, Integer.MAX_VALUE);
        f4[1] = 1;   // f[n]: n個盤移到某點(如：終點)需要的步數
        for (int i = 2; i <= 12; i++) {
            for (int j = 1; j < i; j++) {
                // j 個盤從A到 B或C + 最後 i-j 個盤A到C（三柱） + j個盤從 B或C 到A
                f4[i] = Math.min(f4[i], 2*f4[j] + t3[i-j]);
            }
        }

        for (int i = 1; i <= 12; i++) System.out.println(f4[i]);
/*
        Arrays.fill(f5, Integer.MAX_VALUE);
        f5[1] = 1;
        for (int i = 2; i <= 12; i++) {
            for (int j = 0; j < i; j++) {
                f5[i] = Math.min(f5[i], 2*f5[j] + f4[i-j]);
            }
        }
 */
    }
}
```



## 標準汉诺塔代碼

```java
import java.util.Scanner;

public class TowersOfHanoi {
    static int step = 0;    // 标记移动次数
    public static void move(int disks,char N,char M) {  // 实现移动的函数
        System.out.println("第" + (++step) +" 次移动 : " +" 把 "+ disks+" 号圆盘从 " + N +" ->移到->  " + M);
    }
    // 递归实现汉诺塔的函数
    public static void hanoi(int n,char A,char B,char C) {
        if (n == 1) {   // 圆盘只有一个时，只需将其从A塔移到C塔
            TowersOfHanoi.move(1, A, C);    // 将编b号为1的圆盘从A移到C
        } else {
            hanoi(n - 1, A, C, B);  // 递归，把A塔上编号1~n-1的圆盘移到B上
            TowersOfHanoi.move(n, A, C);    // 把A塔上编号为n的圆盘移到C上
            hanoi(n - 1, B, A, C);  // 递归，把B塔上编号1~n-1的圆盘移到C上
        }
    }
    public static void main(String[] args) {
        char A = 'A', B = 'B', C = 'C';
        System.out.print("请输入圆盘的个数：");
        Scanner sc = new Scanner(System.in);
        int disks = sc.nextInt();
        TowersOfHanoi.hanoi(disks, A, B, C);
        System.out.println(">>移动了" + step + "次，把A上的圆盘都移动到了C上");
        sc.close();
    }

}
```

.