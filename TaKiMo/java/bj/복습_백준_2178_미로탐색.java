import java.util.*;
import java.io.*;

public class Main2178 {
	static int N, M;
	static ArrayList<Integer>[] map;
	static boolean[][] visited;

	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		map = new ArrayList[N + 1];
		visited = new boolean[N + 1][M + 1];

		for (int i = 0; i <= N; i++) {
			map[i] = new ArrayList<>();
		}

		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			String tmp = st.nextToken();
			for (int j = 0; j < M; j++) {
				map[i].add((int) tmp.charAt(j) - 48);
			}
		}

		bfs(1, 0);
	}

	private static void bfs(int startX, int startY) {
		Queue<Node> q = new LinkedList<>();
		int[] dx = { -1, 1, 0, 0 };
		int[] dy = { 0, 0, -1, 1 };

		q.offer(new Node(startX, startY, 1));
		visited[startX][startY] = true;

		while (!q.isEmpty()) {
			Node now = q.poll();

			if (N == now.x && M - 1 == now.y) {
				System.out.print(now.cnt);
				return;
			}

			for (int i = 0; i < 4; i++) {
				int x = now.x + dx[i];
				int y = now.y + dy[i];

				if (0 >= x || x > N || 0 > y || y >= M)
					continue;

				if (1 == map[x].get(y) && !visited[x][y]) {
					q.offer(new Node(x, y, now.cnt + 1));
					map[x].set(y, now.cnt + 1);
					visited[x][y] = true;
				}
			}
		}
	}

	static class Node {
		int x;
		int y;
		int cnt;

		public Node(int x, int y, int cnt) {
			this.x = x;
			this.y = y;
			this.cnt = cnt;
		}
	}
}
