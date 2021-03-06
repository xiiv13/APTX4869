# 1031. 两个非重叠子数组的最大和

给出非负整数数组 `A` ，返回两个非重叠（连续）子数组中元素的最大和，子数组的长度分别为 `L` 和 `M`。（这里需要澄清的是，长为 L 的子数组可以出现在长为 M 的子数组之前或之后。）

从形式上看，返回最大的 `V`，而 `V = (A[i] + A[i+1] + ... + A[i+L-1]) + (A[j] + A[j+1] + ... + A[j+M-1])` 并满足下列条件之一：

 

- `0 <= i < i + L - 1 < j < j + M - 1 < A.length`, **或**
- `0 <= j < j + M - 1 < i < i + L - 1 < A.length`.

 

**示例：**
```
输入：A = [3,8,1,3,2,1,8,9,0], L = 3, M = 2
输出：29
解释：子数组的一种选择中，[3,8,1] 长度为 3，[8,9] 长度为 2。
```



## 前缀和 + 动态规划

```java
class Solution {
    public int maxSumTwoNoOverlap(int[] A, int L, int M) {
        for (int i = 1; i < A.length; i++) A[i] += A[i-1];
        int res = A[L+M-1];
        int preLmax = A[L-1], preMmax = A[M-1];
        for (int i = L+M; i < A.length; i++) {
            preLmax = Math.max(preLmax, A[i-M]-A[i-M-L]);
            preMmax = Math.max(preMmax, A[i-L]-A[i-L-M]);
            // 这里是重点, 遍历每个L和M数组和前面最大的M和L数组组合
            res = Math.max(res, Math.max(preLmax + (A[i]-A[i-M]), preMmax + (A[i]-A[i-L])));
        }
        return res;
    }
}
```



## 前缀和 + 遍历

```java
class Solution {
    public  int maxSumTwoNoOverlap(int[] A, int L, int M) {
        int N = A.length, dpL[] = new int[N-L+1], dpM[] = new int[N-M+1], res = 0;

        for (int i = 0; i < L; i++) dpL[0] += A[i];  // dpL[i]: 当前点往后L个的和
        for (int i = 0; i < M; i++) dpM[0] += A[i];
        for (int i = 1; i < N-L+1; i++) dpL[i] = dpL[i-1] + A[i+L-1] - A[i-1];
        for (int i = 1; i < N-M+1; i++) dpM[i] = dpM[i-1] + A[i+M-1] - A[i-1];
        
        for (int i = 0; i < N - L - M + 1; i++) {
            int offest = N-L-M-i;
            while (offest >= 0) {  // i+L + offest = i+L + N-L-M-i = N-M
                int tmp = Math.max(dpL[i]+dpM[i+L + offest], dpM[i]+dpL[i+M + offest]);
                res = Math.max(res, tmp);
                offest--;
            }
        }
        return res;
    }
}
```

