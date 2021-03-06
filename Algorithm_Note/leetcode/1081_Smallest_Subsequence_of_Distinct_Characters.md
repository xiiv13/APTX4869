# 1081. 不同字符的最小子序列

返回字符串 `text` 中按字典序排列最小的子序列，该子序列包含 `text` 中所有不同字符一次。

**示例 1：**

```
输入："cdadabcc"
输出："adbc"
```

**示例 2：**

```
输入："bcabc"
输出："abc"
```

**示例 3：**

```
输入："ecbacba"
输出："eacb"
```

**示例 4：**

```
输入："leetcode"
输出："letcod"
```



## 单调栈 + 贪心

```c
char * smallestSubsequence(char * s) {
    int N = strlen(s);
    char * stack = (char *)malloc(sizeof(int) * (N+1));
    memset(stack, 0, sizeof(char) * (N+1));
    int top = -1, cout[26];
    memset(cout, 0 , sizeof(int) * 26);
    for (int i = 0; i < N; i++) cout[s[i]-'a']++;
    
    for (int i = 0; i < N; i++) {
        int flag = 0;
        for (int j = 0; j <= top; j++) {
            if (stack[j] == s[i]) {
                cout[s[i]-'a']--;
                flag = 1;
                break;
            }
        }
        if (flag == 0) {
            while (top>-1 && stack[top]>s[i] && cout[stack[top]-'a']>1) {
                cout[stack[top--]-'a']--;
            }
            stack[++top] = s[i];
        }
    }
    stack[top+1] = '\0';
    return stack;
}
```

```java
class Solution {
    public String smallestSubsequence(String s) {
        int N = s.length();
        char[] stack = new char[N]; // 用来模拟栈(每个字母只能唯一出现在栈中)
        int idx = 0;
        int[] mLastIndex = new int[26]; // 用来标记每一个字母出现的最后下标
        boolean[] mExist = new boolean[26]; // 用来标记其是否出现在stack中
        char[] arr = s.toCharArray();

        for (int i = 0; i < N; i++) mLastIndex[arr[i]-'a'] = i;

        for (int i = 0; i < N; i++) {
            int cur = arr[i]-'a';
            char c = arr[i];
            if (!mExist[cur]) { // 如果该字母没有出现在栈中
                // 栈不为空 && 栈顶元素大于当前元素(字典序) && 栈顶字母之后还会出现
                while (idx > 0 && stack[idx-1] > c && mLastIndex[stack[idx-1]-'a'] > i) {
                    mExist[stack[idx-1]-'a'] = false;
                    idx--;
                }

                stack[idx++] = c; // 加入栈中
                mExist[cur] = true;
            }
        }

        StringBuffer res = new StringBuffer();
        for (int i = 0; i < idx; i++) res.append(stack[i]);
        return res.toString();
    }
}
```

## 递归

```java
class Solution {
    public String smallestSubsequence(String s) {
        if(s == null || s.length() == 0) return s;
        int[] count = new int[26];
        for(int i = 0; i < s.length(); i++)
            count[s.charAt(i) - 'a']++;    
        int pos = 0;
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) < s.charAt(pos)) pos = i;
            if(--count[s.charAt(i) - 'a'] == 0) break;
        }
        String res = String.valueOf(s.charAt(pos));
        return res + smallestSubsequence(s.substring(pos+1).replace(res, ""));
    }
}
```

.