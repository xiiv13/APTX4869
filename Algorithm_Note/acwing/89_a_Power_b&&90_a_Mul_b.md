# 89. a^b

求 a 的 b 次方对 p 取模的值。

**输入格式**

三个整数 a,b,p ,在同一行用空格隔开。

**输出格式**

输出一个整数，表示`a^b mod p`的值。

**数据范围**

0≤a,b,p≤109，数据保证 p≠0

**输入样例：**

```
3 2 7
```

**输出样例：**

```
2
```



## 位运算（非递归）

7^1011 = 7^1000 * 7^10 * 7^1

 同時：

7^1000 = 7^100 * 7^100

7^100 = 7^10 * 7^10

```java
import java.util.*;

public class Main{
    public static void main(String[] args) {
        new Main().init();
    }

    private void init() {
        Scanner sc = new Scanner(System.in);
        long a = sc.nextLong(), b = sc.nextLong(), p = sc.nextLong();
        long res = 1%p;
        while (b > 0) {
            if ((b&1) == 1) res = res*a % p;  // 次方，这里是*
            a = a*a % p;  // 次方，这里是*
            b >>= 1;
        }

        System.out.println(res);
    }
}
```



## 递归

2^10 = (2^2 * 2^2 * 2^2 * 2^2 * 2^2) = (2^2)^5

```java
import java.util.*;

public class Main{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long a = sc.nextLong(), b = sc.nextLong(), p = sc.nextLong();
        a %= p;
        System.out.println(divide(a, b, p)%p);
    }

    private static long divide(long a, long b, long p) {
        if (b == 0) return 1;  // 次方，这里是 1
        if ((b&1) == 1) {
            return a%p * divide((a%p)*(a%p), (b-1)/2, p)%p;  // 次方，这里是*
        } else {
            return divide((a%p)*(a%p), b/2, p)%p;
        }
    }
}
```



# 90. a*b

求 a 乘 b 对 p 取模的值。

**输入格式**

第一行输入整数a，第二行输入整数b，第三行输入整数p。

**输出格式**

输出一个整数，表示`a*b mod p`的值。

**数据范围**

1≤a,b,p≤1018

**输入样例：**

```
3
4
5
```

**输出样例：**

```
2
```

## 位运算（非递归）

例1：

计算 3*7
7的二进制 111
3\*(2^0)=3
3\*(2^1)=6
3\*(2^2)=12

例2：

7\*1011 = 7\*1000 + 7\*10 + 7\*1

同時 7\*1000 = 7\*100 + 7\*100

```java
import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long a = sc.nextLong(), b = sc.nextLong(), c = sc.nextLong();
        long res = 0;
        while (b > 0) {
            if ((b&1) == 1) res = (res+a) % c;  // 乘，这里是+
            a = (a+a) % c;  // 乘，这里是+
            b >>= 1;
        }
        System.out.println(res);
    }
}
```



## 递归

```java
import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long a = sc.nextLong(), b = sc.nextLong(), c = sc.nextLong();
        a %= c;
        System.out.println(divide(a, b, c));
    }

    private static long divide(long a, long b, long c) {
        if (b == 0) return 0;  // 乘，这里是 0 
        if ((b&1) == 1) {
            return a%c + divide((a%c)+(a%c), (b-1)/2, c)%c;  // 乘，这里是+
        } else {
            return divide((a%c)+(a%c), b/2, c)%c;
        }
    }
}
```

