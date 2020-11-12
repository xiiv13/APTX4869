import java.util.*;

class Main {
    static int N, M, maxN = 30, INF = 0x3f3f3f3f, res = INF;
    static int[] minv = new int[maxN], mins = new int[maxN];
    static int[] H = new int[maxN], R = new int[maxN];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt(); M = sc.nextInt();
        for (int i = 1; i <= M; i++) {  // 剪枝：预处理最小的体积和表面积
            minv[i] = minv[i-1] + i*i * i;
            mins[i] = mins[i-1] + 2*i * i;
        }

        H[M+1] = R[M+1] = INF;
        dfs(M, 0, 0);   // 剪枝：搜索顺序的优化
        if (res == INF) System.out.println(0);
        else System.out.println(res);
    }

    private static void dfs(int dep, int v, int s) {
        if (v + minv[dep] > N) return;  // 剪枝：估计的最小面积比给定的大是不合法的
        if (s + mins[dep] >= res) return;   // 剪枝：估计的最小面积比当前答案大是不优秀的
        if (s + 2*(N-v)/R[dep+1] >= res) return;    // 剪枝：鬼畜的数学剪枝

        if (dep == 0) {
            if (v == N) res = Math.min(res, s);
            return;
        }

        for (int r = Math.min((int)Math.sqrt(N-v), R[dep+1]-1); r >= dep; r--) {    // 剪枝：缩小上下边界
            for (int h = Math.min((N-v)/r/r, H[dep+1]-1); h >= dep; h--) {
                R[dep] = r; H[dep] = h;
                int t = dep==M ? r*r : 0;   // 底面积
                dfs(dep-1, v + r*r*h, s + 2*r*h + t);
            }
        }
    }
}