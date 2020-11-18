import java.io.*;
import java.util.*;

public class Main {
    static int N, M, maxN = 30010;
    static int idx, qidx;
    static int[] info = new int[maxN];
    static int[] from = new int[maxN];
    static int[] to = new int[maxN];
    static int[] qv = new int[maxN];
    static int[] cout = new int[maxN];
    static BitSet[] dist = new BitSet[maxN];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] str = br.readLine().split(" ");
        N = Integer.parseInt(str[0]);
        M = Integer.parseInt(str[1]);

        Arrays.fill(info, -1);
        while (M-- > 0) {
            str = br.readLine().split(" ");
            int a = Integer.parseInt(str[0]);
            int b = Integer.parseInt(str[1]);
            add(a, b);
            cout[a]++;
        }

        topsort();  // 按子节点数量升序排序

        for (int i = 1; i <= N; i++) {
            dist[i] = new BitSet();
            dist[i].set(i);
        }
        for (int i = 0; i < N; i++) {
            int cur = qv[i];
            for (int j = info[cur]; j != -1; j = from[j]) {
                int t = to[j];
                dist[t].or(dist[cur]);
            }
        }

        for (int i = 1; i <= N; i++) {
            System.out.println(dist[i].cardinality());
        }
    }

    private static void topsort() {
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= N; i++) {
            if (cout[i] == 0) {
                queue.add(i);
                qv[qidx++] = i;
            }
        }

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int i = info[cur]; i != -1; i = from[i]) {
                int t = to[i];
                if (--cout[t] == 0) {
                    queue.add(t);
                    qv[qidx++] = t;
                }
            }
        }
    }

    private static void add(int a, int b) {
        from[idx] = info[b];
        to[idx] = a;
        info[b] = idx++;
    }
}