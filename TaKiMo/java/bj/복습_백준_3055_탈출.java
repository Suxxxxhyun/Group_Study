import java.util.*;
import java.io.*;

public class Main3055 {
	static ArrayList<Character>[] map;
	static boolean[][][] visited;
	static int R, C, dX, dY;
	static Queue<Ground> q;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int startX = 0;
		int startY = 0;
		
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		
		map = new ArrayList[R];
		visited = new boolean[R][C][2];
		q = new LinkedList<>();
		
		for(int i = 0; i < R; i++) {
			map[i] = new ArrayList<>();
		}
		
		for(int i = 0; i < R; i++) {
			st = new StringTokenizer(br.readLine());
			String tmp = st.nextToken();
			for(int j = 0; j < C; j++) {
				Character ch = tmp.charAt(j);
				
				map[i].add(ch);
				
				// 도착지에는 물만 못 감
				if('D' == ch) {
					dX = i;
					dY = j;
					visited[i][j][1] = true;
				}
				
				// 돌(장애물)에는 물, 고슴도치 둘 다 못 감
				else if('X' == ch) {
					visited[i][j][0] = true;
					visited[i][j][1] = true;
				}
				
				// 물에는 물, 고슴도치 둘 다 못 감
				else if('*' == ch) {
					q.offer(new Ground(i, j, '*', 0));
					visited[i][j][0] = true;
					visited[i][j][1] = true;
				}
				
				// 큐 시작위치
				else if('S' == ch) {
					startX = i;
					startY = j;
					visited[i][j][0] = true;
				}
			}
		}
		
		// 큐에 고슴도치 위치 마지막에 넣기(물 먼저 진행해야 하므로)
		q.offer(new Ground(startX, startY, 'S', 1));
		bfs();
	}
	
	private static void bfs() {
		int[] dx = { -1, 1, 0, 0 };
		int[] dy = { 0, 0, -1, 1 };
		
		while(!q.isEmpty()) {
			Ground now = q.poll();
			
			for(int i = 0; i < 4; i++) {
				int x = now.x + dx[i];
				int y = now.y + dy[i];
				
				if(0 > x || x >= R || 0 > y || y >= C)
					continue;
				
				// 물 진행
				if('*' == now.ch) {
					// '.'를 만나면 해당좌표를 '*'로 바꾸면서 해당 좌표 방문 표시
					if('.' == map[x].get(y) && !visited[x][y][1]) {
						q.offer(new Ground(x, y, '*', 0));
						visited[x][y][0] = true;
						visited[x][y][1] = true;
						map[x].set(y, '*');
					}
				}
				
				
				// 고슴도치 진행
				if('S' == now.ch) {
					// '.'를 만나면 해당좌표를 'S'로 바꾸기 +  now의 좌표를 '.'으로 변경 + 해당좌표 방문 표시
					if('.' == map[x].get(y) && !visited[x][y][0]) {
						q.offer(new Ground(x, y, 'S', now.cnt + 1));
						visited[x][y][0] = true;
						map[x].set(y, '.');
					}
					// 'D'를 만나면 now.cnt 리턴하며 함수 종료
					if('D' == map[x].get(y) && !visited[x][y][0]) {
						System.out.println(now.cnt);
						return;
					}
				}
			}
		}
		
		System.out.println("KAKTUS");
	}
	
	static class Ground {
		int x;
		int y;
		char ch;
		int cnt;
		
		public Ground (int x, int y, char ch, int cnt) {
			this.x = x;
			this.y = y;
			this.ch = ch;
			this.cnt = cnt;
		}
	}
}
