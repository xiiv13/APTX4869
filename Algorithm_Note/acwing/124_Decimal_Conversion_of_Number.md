# 124. 数的进制转换

编写一个程序，可以实现将一个数字由一个进制转换为另一个进制。

这里有 62 个不同数位{0−9,A−Z,a−z}。

#### 输入格式

第一行输入一个整数，代表接下来的行数。

接下来每一行都包含三个数字，首先是输入进制（十进制表示），然后是输出进制（十进制表示），最后是用输入进制表示的输入数字，数字之间用空格隔开。

输入进制和输出进制都在 2 到 62 的范围之内。

（在十进制下）A=10，B=11，…，Z=35，a=36，b=37，…，z=61 (0−9 仍然表示 0−9)。

#### 输出格式

对于每一组进制转换，程序的输出都由三行构成。

第一行包含两个数字，首先是输入进制（十进制表示），然后是用输入进制表示的输入数字。

第二行包含两个数字，首先是输出进制（十进制表示），然后是用输出进制表示的输入数字。

第三行为空白行。

同一行内数字用空格隔开。

#### 输入样例：

```
8
62 2 abcdefghiz
10 16 1234567890123456789012345678901234567890
16 35 3A0C92075C0DBF3B8ACBC5F96CE3F0AD2
35 23 333YMHOUE8JPLT7OX6K9FYCQ8A
23 49 946B9AA02MI37E3D3MMJ4G7BL2F05
49 61 1VbDkSIMJL3JjRgAdlUfcaWj
61 5 dl9MDSWqwHjDnToKcsWE1S
5 10 42104444441001414401221302402201233340311104212022133030
```

#### 输出样例：

```
62 abcdefghiz
2 11011100000100010111110010010110011111001001100011010010001

10 1234567890123456789012345678901234567890
16 3A0C92075C0DBF3B8ACBC5F96CE3F0AD2

16 3A0C92075C0DBF3B8ACBC5F96CE3F0AD2
35 333YMHOUE8JPLT7OX6K9FYCQ8A

35 333YMHOUE8JPLT7OX6K9FYCQ8A
23 946B9AA02MI37E3D3MMJ4G7BL2F05

23 946B9AA02MI37E3D3MMJ4G7BL2F05
49 1VbDkSIMJL3JjRgAdlUfcaWj

49 1VbDkSIMJL3JjRgAdlUfcaWj
61 dl9MDSWqwHjDnToKcsWE1S

61 dl9MDSWqwHjDnToKcsWE1S
5 42104444441001414401221302402201233340311104212022133030

5 42104444441001414401221302402201233340311104212022133030
10 1234567890123456789012345678901234567890
```



## 高精度

```java
// 高精度模板
while (len > 0) {
	for (int i = len-1; i > 0; i--) {
		num[i-1] += num[i]%b*a;
		num[i] /= b;
	}
	res[idx++] = num[0] % b;
	num[0] /= b;
	while (len > 0 && num[len-1]==0) len--;
}
```



```java
import java.io.*;
import java.util.*;
class Main {
    int maxN = 1010;
    int[] num = new int[maxN], res = new int[maxN];
    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        while (T-- > 0) {
            Arrays.fill(num, 0); int idx = 0;
            String[] str = br.readLine().split(" ");
            int a = Integer.parseInt(str[0]), b = Integer.parseInt(str[1]);
            char[] chs = str[2].toCharArray(); int len = chs.length;
            for (int i = 0; i < len; i++) {  // 转成10进制
                if (chs[i] >= '0' && chs[i] <= '9') num[len-1-i] = chs[i]-'0';
                if (chs[i] >= 'A' && chs[i] <= 'Z') num[len-1-i] = chs[i]-'A'+10;
                if (chs[i] >= 'a' && chs[i] <= 'z') num[len-1-i] = chs[i]-'a'+36;
            }
			// 高精度
            while (len > 0) {
                for (int i = len-1; i > 0; i--) {
                    num[i-1] += num[i]%b*a;
                    num[i] /= b;
                }
                res[idx++] = num[0] % b;
                num[0] /= b;
                while (len > 0 && num[len-1]==0) len--;
            }
            
            System.out.println(a+" "+new String(chs));
            System.out.print(b+" ");
            for (int i = idx-1; i >= 0; i--) {
                if (res[i] >= 0 && res[i] <= 9) System.out.print(res[i]);
                if (res[i] >= 10 && res[i] <= 35) System.out.print((char)(res[i]-10+'A'));
                if (res[i] >= 36) System.out.print((char)(res[i]-36+'a'));
            }
            System.out.println("\n");
        }
    }
}
```

