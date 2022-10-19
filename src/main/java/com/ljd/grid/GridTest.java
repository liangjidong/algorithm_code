package com.ljd.grid;

// 图：1题
public class GridTest {

    public static void main(String[] args) {
        int[][] grid = {
                {0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 0, 1, 1, 0},
                {0, 0, 0, 1, 0, 1, 0},
                {0, 0, 1, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0}
        };
        System.out.println(numOfIsland(grid));
    }

    // 给定一个由 '1'（陆地）和 '0'（水）组成的的二维网格，计算岛屿的数量。一个岛被水包围，并且它是通过水平方向或垂直方向上相邻的陆地连接而成的。你可以假设网格的四个边均被水包围。
    public static int numOfIsland(int[][] grid) {
        // 遇到1，dfs之，dfs过程中，将1->2，表示已经遍历过
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    dfs(grid, i, j);
                    count++;
                }
            }
        }
        return count;
    }

    private static void dfs(int[][] grid, int i, int j) {
        if (grid[i][j] != 1) {
            return;
        }
        grid[i][j] = 2;
        dfs(grid, i + 1, j);
        dfs(grid, i - 1, j);
        dfs(grid, i, j + 1);
        dfs(grid, i, j - 1);
    }
}
