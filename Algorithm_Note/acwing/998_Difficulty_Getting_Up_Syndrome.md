# 998. 起床困难综合症

drd防御战线由n扇防御门组成。

每扇防御门包括一个运算op和一个参数t，其中运算一定是OR,XOR,AND中的一种，参数则一定为非负整数。

如果还未通过防御门时攻击力为x，则其通过这扇防御门后攻击力将变为x op t。

最终drd受到的伤害为对方初始攻击力x依次经过所有n扇防御门后转变得到的攻击力。

由于atm水平有限，他的初始攻击力只能为0到m之间的一个整数（即他的初始攻击力只能在 0, 1, … , m中任选，但在通过防御门之后的攻击力不受m的限制）。

为了节省体力，他希望通过选择合适的初始攻击力使得他的攻击能让drd受到最大的伤害，请你帮他计算一下，他的一次攻击最多能使drd受到多少伤害。

#### 输入格式

第 1 行包含 2 个整数，依次为n, m，表示 drd 有n扇防御门，atm 的初始攻击力为0到m之间的整数。

接下来n行，依次表示每一扇防御门。每行包括一个字符串op和一个非负整数t，两者由一个空格隔开，且op在前，t在后，op表示该防御门所对应的操作，t表示对应的参数。

#### 输出格式

输出一个整数，表示atm的一次攻击最多使drd受到多少伤害。

#### 输入样例：

```
3 10
AND 5
OR 6
XOR 7
```

#### 输出样例：

```
1
```

#### 样例解释

atm可以选择的初始攻击力为 0,1,…,10。

假设初始攻击力为 4，最终攻击力经过了如下计算

```
4 AND 5 = 4

4 OR 6 = 6

6 XOR 7 = 1
```

类似的，我们可以计算出初始攻击力为 1,3,5,7,9 时最终攻击力为 0，初始攻击力为 0,2,4,6,8,10 时最终攻击力为 1，因此 atm 的一次攻击最多使 drd 受到的伤害值为 1。



## 位运算

```java
import java.io.*;

class Main {
    int N, M, maxN = 100010;
    int[] arr = new int[maxN], ops = new int[maxN];

    public static void main(String[] args) throws IOException {
        new Main().init();
    }

    void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] str =  br.readLine().split(" ");
        N = Integer.parseInt(str[0]); M = Integer.parseInt(str[1]);

        for (int i = 0; i < N; i++) {
            str = br.readLine().split(" ");
            char c = str[0].charAt(0);
            if (c == 'O') ops[i] = 0;
            else if (c == 'X') ops[i] = 1;
            else ops[i] = 2;

            arr[i] = Integer.parseInt(str[1]);
        }

        int res = 0;
        for (int i = 0; i < 30; i++) {  // atm的初始攻击力为0到M之间的整数 (M < 2^30)
            if (M>>i > 0) {
                int a = calc(0, i), b = calc(1, i);
                res |= Math.max(a, b) << i;
            } else {  // 超过最大攻击力范围, 位数只能为0
                res |= calc(0, i)<<i;
            }
        }
        System.out.println(res);
    }

    int calc(int n, int j) {
        for (int i = 0; i < N; i++) {
            if (ops[i] == 0) n |= arr[i]>>j & 1;
            else if (ops[i] == 1) n ^= arr[i]>>j & 1;
            else n &= arr[i]>>j & 1;
        }
        return n;
    }
}
```

