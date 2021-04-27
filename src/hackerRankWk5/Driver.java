package hackerRankWk5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Driver {
	public static void main(String[] args) {
		List<List<Integer>> obstacles = new ArrayList<>();
		obstacles.add(Arrays.asList(1,1));
		obstacles.add(Arrays.asList(2,2));
		obstacles.add(Arrays.asList(3,3));
		obstacles.add(Arrays.asList(4,4));
		obstacles.add(Arrays.asList(6,6));
		obstacles.add(Arrays.asList(7,7));
		obstacles.add(Arrays.asList(1,9));
		obstacles.add(Arrays.asList(3,7));
		System.out.println("Queens Attack 2 with recursion: " + queensAttackRecursive(10, 3, 5, 5, obstacles));
		System.out.println("Queens Attack 2 without recursion: " + queensAttackNonRecursive(10, 3, 5, 5, obstacles));
	}

	public static int queensAttackRecursive(int n, int k, int r_q, int c_q, List<List<Integer>> obstacles) {
		Set<List<Integer>> obstaclesSet = new HashSet<>(obstacles);
		return queensAttackWithMove(n, r_q + 1, c_q, obstaclesSet, 1, 0)
				+ queensAttackWithMove(n, r_q - 1, c_q, obstaclesSet, -1, 0)
				+ queensAttackWithMove(n, r_q, c_q + 1, obstaclesSet, 0, 1)
				+ queensAttackWithMove(n, r_q, c_q - 1, obstaclesSet, 0, -1)
				+ queensAttackWithMove(n, r_q + 1, c_q + 1, obstaclesSet, 1, 1)
				+ queensAttackWithMove(n, r_q + 1, c_q - 1, obstaclesSet, 1, -1)
				+ queensAttackWithMove(n, r_q - 1, c_q + 1, obstaclesSet, -1, 1)
				+ queensAttackWithMove(n, r_q - 1, c_q - 1, obstaclesSet, -1, -1);
	}

	private static int queensAttackWithMove(int n, int r_q, int c_q, Set<List<Integer>> obstaclesSet, int vertMove,
			int horiMove) {
		if (r_q > n || c_q > n || r_q < 1 || c_q < 1 || obstaclesSet.contains(Arrays.asList(r_q, c_q))) {
			return 0;
		}
		return 1 + queensAttackWithMove(n, r_q + vertMove, c_q + horiMove, obstaclesSet, vertMove, horiMove);
	}
	
	public static int queensAttackNonRecursive(int n, int k, int r_q, int c_q, List<List<Integer>> obstacles) {
	    // Write your code here
	        int up = n - r_q;
	        int down = r_q - 1;
	        int left = c_q - 1;
	        int right = n - c_q;
	        int upRight = Math.min(n - r_q, n - c_q);
	        int upLeft = Math.min(n - r_q, c_q - 1);
	        int downRight = Math.min(r_q - 1, n - c_q);
	        int downLeft = Math.min(r_q - 1, c_q - 1);
	        Set<List<Integer>> obstaclesSet = new HashSet<>(obstacles);
	        for (List<Integer> obsCoords : obstaclesSet) {
	            if (obsCoords.get(0) == r_q) {
	                if (obsCoords.get(1) < c_q) {
	                    left = Math.min(left, c_q - obsCoords.get(1) - 1);
	                } else {
	                    right = Math.min(right, obsCoords.get(1) - c_q - 1);
	                }
	            } else if (obsCoords.get(1) == c_q) {
	                if (obsCoords.get(0) < r_q) {
	                    down = Math.min(down, r_q - obsCoords.get(0) - 1);
	                } else {
	                    up = Math.min(up, obsCoords.get(0) - r_q - 1);
	                }
	            } else if ((obsCoords.get(0) - r_q) / (obsCoords.get(1) - c_q) == 1) {
	                if (obsCoords.get(0) < r_q) {
	                    downLeft = Math.min(downLeft, r_q - obsCoords.get(0) - 1);
	                } else {
	                    upRight = Math.min(upRight, obsCoords.get(0) - r_q - 1);
	                }
	            } else if ((obsCoords.get(0) - r_q) / (obsCoords.get(1) - c_q) == -1) {
	                if (obsCoords.get(0) < r_q) {
	                    downRight = Math.min(downRight, r_q - obsCoords.get(0) - 1);
	                } else {
	                    upLeft = Math.min(upLeft, obsCoords.get(0) - r_q - 1);
	                }
	            }
	        }
	        return up + down + left + right + upRight + upLeft + downRight + downLeft;
	    }
}
