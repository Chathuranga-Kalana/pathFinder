/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author K.L. Chathuranga Kalana
 * w1582988
 */

import java.awt.Color;
import java.util.*;

/**
 * ***********************************************************************
 * Author: Dr E Kapetanios Last update: 22-02-2017
 *
 ************************************************************************
 */
public class PathFindingOnSquaredGrid {

    // given an N-by-N matrix of open cells, return an N-by-N matrix
    // of cells reachable from the top
    public static boolean[][] flow(boolean[][] open) {
        int N = open.length;

        boolean[][] full = new boolean[N][N];
        for (int j = 0; j < N; j++) {
            flow(open, full, 0, j);
        }

        return full;
    }

    // determine set of open/blocked cells using depth first search
    public static void flow(boolean[][] open, boolean[][] full, int i, int j) {
        int N = open.length;

        // base cases
        if (i < 0 || i >= N) {
            return;    // invalid row
        }
        if (j < 0 || j >= N) {
            return;    // invalid column
        }
        if (!open[i][j]) {
            return;        // not an open cell
        }
        if (full[i][j]) {
            return;         // already marked as open
        }
        full[i][j] = true;

        flow(open, full, i + 1, j);   // down
        flow(open, full, i, j + 1);   // right
        flow(open, full, i, j - 1);   // left
        flow(open, full, i - 1, j);   // up
    }

    // does the system percolate?
    public static boolean percolates(boolean[][] open) {
        int N = open.length;

        boolean[][] full = flow(open);
        for (int j = 0; j < N; j++) {
            if (full[N - 1][j]) {
                return true;
            }
        }

        return false;
    }

    // does the system percolate vertically in a direct way?
    public static boolean percolatesDirect(boolean[][] open) {
        int N = open.length;

        boolean[][] full = flow(open);
        int directPerc = 0;
        for (int j = 0; j < N; j++) {
            if (full[N - 1][j]) {
                // StdOut.println("Hello");
                directPerc = 1;
                int rowabove = N - 2;
                for (int i = rowabove; i >= 0; i--) {
                    if (full[i][j]) {
                        // StdOut.println("i: " + i + " j: " + j + " " + full[i][j]);
                        directPerc++;
                    } else {
                        break;
                    }
                }
            }
        }

        // StdOut.println("Direct Percolation is: " + directPerc);
        if (directPerc == N) {
            return true;
        } else {
            return false;
        }
    }

   
    // draw the N-by-N boolean matrix to standard draw
    public static void show(boolean[][] a, boolean which) {
        int N = a.length;
        StdDraw.setXscale(-1, N);
        StdDraw.setYscale(-1, N);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (a[i][j] == which) {
                    StdDraw.square(j, N - i - 1, .5);
                } else {
                    StdDraw.filledSquare(j, N - i - 1, .5);
                }
            }
        }
    }

    // draw the N-by-N boolean matrix to standard draw, including the points A (x1, y1) and B (x2,y2) to be marked by a circle
    public static void show(boolean[][] a, boolean which, int x1, int y1, int x2, int y2) {
        int N = a.length;
        StdDraw.setXscale(-1, N);;
        StdDraw.setYscale(-1, N);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (a[i][j] == which) {
                    if ((i == x1 && j == y1) || (i == x2 && j == y2)) {
                        StdDraw.circle(j, N - i - 1, .5);
                    } else {
                        StdDraw.square(j, N - i - 1, .5);
                    }
                } else {
                    StdDraw.filledSquare(j, N - i - 1, .5);
                }
            }
        }
    }

    // return a random N-by-N boolean matrix, where each entry is
    // true with probability p
    public static boolean[][] random(int N, double p) {
        boolean[][] a = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                a[i][j] = StdRandom.bernoulli(p);
            }
        }
        return a;
    }

    // test client
    public static void main(String[] args) {
        // boolean[][] open = StdArrayIO.readBoolean2D();

        // The following will generate a 10x10 squared grid with relatively few obstacles in it
        // The lower the second parameter, the more obstacles (black cells) are generated
        boolean[][] randomlyGenMatrix = random(10, 0.7);

        StdArrayIO.print(randomlyGenMatrix);
        show(randomlyGenMatrix, true);

        System.out.println();
        System.out.println("The system percolates: " + percolates(randomlyGenMatrix));

        System.out.println();
        System.out.println("The system percolates directly: " + percolatesDirect(randomlyGenMatrix));
        System.out.println();

       

        Scanner in = new Scanner(System.in);
        System.out.println("Enter i for A > ");
        int Ai = in.nextInt();

        System.out.println("Enter j for A > ");
        int Aj = in.nextInt();

        System.out.println("Enter i for B > ");
        int Bi = in.nextInt();

        System.out.println("Enter j for B > ");
        int Bj = in.nextInt();

        System.out.println("Enter calculating method > ");
        System.out.println("For the Manhatten method, Enter M ");
        System.out.println("For the Euclidean method, Enter E ");
        System.out.println("For the Chebyshev method, Enter C ");
        String method = in.next();

        

        // System.out.println("Coordinates for A: [" + Ai + "," + Aj + "]");
        // System.out.println("Coordinates for B: [" + Bi + "," + Bj + "]");
        show(randomlyGenMatrix, true, Ai, Aj, Bi, Bj);
        
        Search searching = new Search();
                   // finding distance
                searching.createCell(randomlyGenMatrix, Ai, Aj, Bi, Bj,method);
                System.out.println("Euc");
                searching.createCell(randomlyGenMatrix, Ai, Aj, Bi, Bj,"E");
                System.out.println("cheb");
                searching.createCell(randomlyGenMatrix, Ai, Aj, Bi, Bj,"C");

        
    }



}
