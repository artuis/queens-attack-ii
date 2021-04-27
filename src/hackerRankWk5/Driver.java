package hackerRankWk5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Driver {
	public static void main(String[] args) {
		List<List<Integer>> obstacles = new ArrayList<>();
		// testingi nput
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
	
	//recursive solution works until n is too large and method stack causes stack overflow error
	public static int queensAttackRecursive(int n, int k, int r_q, int c_q, List<List<Integer>> obstacles) {
		//specification says duplicate objects exist, and HashSet has faster read times than ArrayList
		Set<List<Integer>> obstaclesSet = new HashSet<>(obstacles);					
		return queensAttackWithMove(n, r_q + 1, c_q, obstaclesSet, 1, 0)			//upward movement
				+ queensAttackWithMove(n, r_q - 1, c_q, obstaclesSet, -1, 0)		//downward movement
				+ queensAttackWithMove(n, r_q, c_q + 1, obstaclesSet, 0, 1)			//rightward movement
				+ queensAttackWithMove(n, r_q, c_q - 1, obstaclesSet, 0, -1)		//leftward movement
				+ queensAttackWithMove(n, r_q + 1, c_q + 1, obstaclesSet, 1, 1)		//upward and rightward movement
				+ queensAttackWithMove(n, r_q + 1, c_q - 1, obstaclesSet, 1, -1)	//upward and leftward movement
				+ queensAttackWithMove(n, r_q - 1, c_q + 1, obstaclesSet, -1, 1)	//downward and rightward movement
				+ queensAttackWithMove(n, r_q - 1, c_q - 1, obstaclesSet, -1, -1);	//downward and leftward movement
	}
	
	//recursive helper method
	private static int queensAttackWithMove(int n, int r_q, int c_q, Set<List<Integer>> obstaclesSet, int vertMove,
			int horiMove) {
		if (r_q > n || c_q > n || r_q < 1 || c_q < 1 || obstaclesSet.contains(Arrays.asList(r_q, c_q))) {
			// if end of board or obstacle is reach return zero
			return 0;
		} // else it is a valid move, so return sum of (this valid move) + (sum of other valid moves in this direction)
		return 1 + queensAttackWithMove(n, r_q + vertMove, c_q + horiMove, obstaclesSet, vertMove, horiMove);
	}
	
	public static int queensAttackNonRecursive(int n, int k, int r_q, int c_q, List<List<Integer>> obstacles) {
		//int declarations are number of moves assuming no obstacles
        int up = n - r_q;
        int down = r_q - 1;
        int left = c_q - 1;
        int right = n - c_q;
        //diagonal moves can be visualized as a combination of two moves, and as the diagonal distance is equal to
        //a single combination of these moves, one can assume that the maximum distance of a diagonal move depends
        //on the lowest possible individual value of these two moves
        int upRight = Math.min(n - r_q, n - c_q);
        int upLeft = Math.min(n - r_q, c_q - 1);
        int downRight = Math.min(r_q - 1, n - c_q);
        int downLeft = Math.min(r_q - 1, c_q - 1);
        //specification says duplicate objects exist, and HashSet has faster read times than ArrayList
        Set<List<Integer>> obstaclesSet = new HashSet<>(obstacles);
        //for loop checks each obstacle to if the obstacle lies in any direction that the queen can move in
        for (List<Integer> obsCoords : obstaclesSet) {
        	//specifically checks if obstacle is on same row as queen
            if (obsCoords.get(0) == r_q) {	
                if (obsCoords.get(1) < c_q) {
                    left = Math.min(left, c_q - obsCoords.get(1) - 1);
                } else {
                    right = Math.min(right, obsCoords.get(1) - c_q - 1);
                }
            //specifically checks if obstacle is on same column as queen
            } else if (obsCoords.get(1) == c_q) {
                if (obsCoords.get(0) < r_q) {
                    down = Math.min(down, r_q - obsCoords.get(0) - 1);
                } else {
                    up = Math.min(up, obsCoords.get(0) - r_q - 1);
                }
            //if the board is visualized as a grid, the slope between the obstacle and the queen must be 1 or -1 to interfere
            //with the queen's moves, therefore we can assume that the difference between the horizontal and vertical components
            //of the queen's and the obstacle's position must be zero
            } else if (((obsCoords.get(0) - r_q) == (obsCoords.get(1) - c_q))) {
                if (obsCoords.get(0) < r_q) {
                    downLeft = Math.min(downLeft, r_q - obsCoords.get(0) - 1);
                } else {
                    upRight = Math.min(upRight, obsCoords.get(0) - r_q - 1);
                }
            //case if slope is -1
            } else if (Math.abs((obsCoords.get(0) - r_q)) == Math.abs((obsCoords.get(1) - c_q))) {
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
