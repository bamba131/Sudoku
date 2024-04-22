import java.util.Random;

public class GenererGrille {

    private static final int TailleGrille = 9;
    private static final int VIDE = 0;

    public static int[][] generateSudoku() {
        int[][] grid = new int[TailleGrille][TailleGrille];
        fillDiagonal(grid);
        //solve(grid);
        return grid;
    }

    private static void fillDiagonal(int[][] grid) {
        Random random = new Random();
        for (int i = 0; i < TailleGrille; i+=3) {
            int num = random.nextInt(9) + 1;
            fillBox(grid, i, i, num);
        }
    }

    private static boolean isSafe(int[][] grid, int row, int col, int num) {
        return !isInRow(grid, row, num) && !isInCol(grid, col, num) && !isInBox(grid, row  , col , num);
    }

    private static boolean isInRow(int[][] grid, int row, int num) {
        for (int i = 0; i < TailleGrille; i++) {
            if (grid[row][i] == num) {
                return true;
            }
        }
        return false;
    }

    private static boolean isInCol(int[][] grid, int col, int num) {
        for (int i = 0; i < TailleGrille; i++) {
            if (grid[i][col] == num) {
                return true;
            }
        }
        return false;
    }

    private static boolean isInBox(int[][] grid, int col, int row, int num) {
       int boxStartRow = row - row % 3;
       int boxStartCol = col - col % 3;
       
        for (int i =  boxStartRow ; i < boxStartRow + 3; i++) {
            for (int j = boxStartCol; j < boxStartCol + 3; j++) {
                if (grid[i][j] == num) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean solve(int[][] grid) {
        for (int row = 0; row < TailleGrille; row++) {
            for (int col = 0; col < TailleGrille; col++) {
                if (grid[row][col] == VIDE) {
                    for (int num = 1; num <= TailleGrille; num++) {
                        if (isSafe(grid, row, col, num)) {
                            grid[row][col] = num;
                            if (solve(grid)) {
                                return true;
                            }
                            else{
                            grid[row][col] = VIDE;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private static void fillBox(int[][] grid, int row, int col, int num) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                while (grid[row][col] != VIDE) {
                    num = (num + 1) % 9;
                    if (num == 0) {
                        num = 1;
                    }
                }
                grid[row + i][col + j] = num;
            }
        }
    }

    public static void main(String[] args) {
        int[][] sudoku = generateSudoku();
        printSudoku(sudoku);
    }

    private static void printSudoku(int[][] sudoku) {
        for (int i = 0; i < TailleGrille; i++) {
            for (int j = 0; j < TailleGrille; j++) {
                System.out.print(sudoku[i][j] + " ");
            }
            System.out.println();
        }
    }
}
