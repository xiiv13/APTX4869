# 179.八数码

在一个3×3的网格中，1~8这8个数字和一个“X”恰好不重不漏地分布在这3×3的网格中。

例如：

```
1 2 3
X 4 6
7 5 8
```

在游戏过程中，可以把“X”与其上、下、左、右四个方向之一的数字交换（如果存在）。

我们的目的是通过交换，使得网格变为如下排列（称为正确排列）：

```
1 2 3
4 5 6
7 8 X
```

例如，示例中图形就可以通过让“X”先后与右、下、右三个方向的数字交换成功得到正确排列。

交换过程如下：

```
1 2 3   1 2 3   1 2 3   1 2 3
X 4 6   4 X 6   4 5 6   4 5 6
7 5 8   7 5 8   7 X 8   7 8 X
```

把“X”与上下左右方向数字交换的行动记录为“u”、“d”、“l”、“r”。

现在，给你一个初始网格，请你通过最少的移动次数，得到正确排列。

#### 输入格式

输入占一行，将3×3的初始网格描绘出来。

例如，如果初始网格如下所示：
1 2 3

x 4 6

7 5 8

则输入为：1 2 3 x 4 6 7 5 8

#### 输出格式

输出占一行，包含一个字符串，表示得到正确排列的完整行动记录。如果答案不唯一，输出任意一种合法方案即可。

如果不存在解决方案，则输出”unsolvable”。

#### 输入样例：

```
2  3  4  1  5  x  7  6  8 
```

#### 输出样例

```
ullddrurdllurdruldr
```



## aStar

```cpp
#include <iostream>
#include <cstring>
#include <queue>
#include <unordered_map>
#include <algorithm>

using namespace std;
typedef pair<int , string> PIS;

unordered_map<string , int> dist;
unordered_map<string , pair<string , char>> pre;
priority_queue<PIS , vector<PIS> , greater<PIS>> heap;
string ed = "12345678x";
int dx[4] = {-1, 0, 1, 0};
int dy[4] = { 0, 1, 0,-1};
char op[] = "urdl";

int f(string state) {   // 求估值函数,这里是曼哈顿距离
    int res = 0;
    for (int i = 0; i < 9; i++) {
        if (state[i] != 'x') {
            int t = state[i] - '1';
            res += abs(t/3-i/3) + abs(t%3-i%3);
        }
    }
    return res;
}

string bfs(string start) {
    heap.push({f(start) , start});
    dist[start] = 0;

    while (heap.size()) {
        auto t = heap.top(); heap.pop();

        string state = t.second;
        if (state == ed) break;  // 如果到达终点就break
        int step = dist[state]; // 记录到达state的实际距离

        int k = state.find('x');// 确定当前 'x' 的位置
        int x = k / 3 , y = k % 3;

        string backup = state;  // 因为在下面state会变，所以留一个备份
        for (int i = 0; i < 4; i++) {
            int a = x + dx[i], b = y + dy[i];
            if (a >= 0 && a < 3 && b >= 0 && b < 3) {
                swap(state[x * 3 + y], state[a * 3 + b]);
                if (!dist.count(state) || dist[state] > step + 1) {
                    dist[state] = step + 1;
                    pre[state] = {backup, op[i]};
                    heap.push({dist[state] + f(state), state});
                }
                swap(state[x * 3 + y], state[a * 3 + b]);   // 因为要多次交换，所以要恢复现场
            }
        }

    }

    string res;
    while (ed != start) {
        res += pre[ed].second;
        ed = pre[ed].first;
    }
    reverse(res.begin() , res.end());
    return res;
}

int main() {
    string start , seq;
    for (int i = 0; i < 9; i++) {
        char c;
        cin >> c;
        start += c;
        if(c != 'x') seq += c;
    }

    int cnt = 0;
    for (int i = 0 ; i < 8 ; i ++) {
        for (int j = i + 1 ; j < 8 ; j++) {
            if (seq[i] > seq[j]) cnt++;             
        }
    }

    if (cnt % 2) puts("unsolvable");
    else cout << bfs(start) << endl;

    return 0;
}
```

