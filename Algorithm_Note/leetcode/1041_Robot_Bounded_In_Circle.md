# 1041. 困于环中的机器人

在无限的平面上，机器人最初位于 `(0, 0)` 处，面朝北方。机器人可以接受下列三条指令之一：

- `"G"`：直走 1 个单位
- `"L"`：左转 90 度
- `"R"`：右转 90 度

机器人按顺序执行指令 `instructions`，并一直重复它们。

只有在平面中存在环使得机器人永远无法离开时，返回 `true`。否则，返回 `false`。

**示例 1：**

```
输入："GGLLGG"
输出：true
解释：
机器人从 (0,0) 移动到 (0,2)，转 180 度，然后回到 (0,0)。
重复这些指令，机器人将保持在以原点为中心，2 为半径的环中进行移动。
```

**示例 2：**

```
输入："GG"
输出：false
解释：
机器人无限向北移动。
```

**示例 3：**

```
输入："GL"
输出：true
解释：
机器人按 (0, 0) -> (0, 1) -> (-1, 1) -> (-1, 0) -> (0, 0) -> ... 进行移动。
```



## 抽象（数学）

执行一次后回到原点，返回true(不管朝向)。 

否则执行完一次后只有以下三种情况（忽略中间路程，只关注起点和终点，把起点和终点连一条线）：

- 面向北：一条直线。false
- 面向南：线段。true
- 面向东：每次执行完后的线段逐渐变成一个圆。
- 面向西：以面向东的情况下相反方向逐渐变成一个圆。

在这种抽象下不同坐标的终点形成的图像（线或圆）大同小异，不影响最后结果（是否会回到原点）

```java
public class Solution {
    public boolean isRobotBounded(String instructions) {
        int dx[] = {0, 1, 0, -1}, dy[] = {1, 0, -1, 0};  // 东右，北上，西左，南下
        int dir = 0, x = 0, y = 0;
        for (char c : instructions.toCharArray()) {
            if (c == 'G') {
                dir %= 4;
                x += dx[dir];
                y += dy[dir];
            } else if (c == 'L') {
                dir += 1;
            } else {
                dir += 3;
            }
        }
        
        return (x==0&&y==0) || dir%4 != 0;  // 回到原点 || 不是朝向北
    }
}
```

