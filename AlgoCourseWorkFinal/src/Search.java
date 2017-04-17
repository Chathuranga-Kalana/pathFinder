
import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

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
public class Search {
    
    public  static class Cell {
    int i ;
    int j;
    boolean blocked;
    int gcost;
    int heuristicCost ;
    Cell parent;
    
    public int getFCost(){
        return heuristicCost+gcost;
    }
    
}
    
   // static boolean[][]firstgrid ;
    
       static void createCell(boolean[][] randomlyGenMatrix, int Ai, int Aj, int Bi, int Bj, String method) {
           
         // start time
        Stopwatch timerFlow = new Stopwatch();
           
         //  firstgrid=randomlyGenMatrix;
        int N = randomlyGenMatrix.length;
        Cell[][] grid = new Cell[N][N];
      for (int i = 0; i < N; i++) {
           for (int j = 0; j < N; j++) {
                Cell cell = new Cell();
                cell.i = i;
                cell.j = j;
                if(method.equalsIgnoreCase("M")){
                cell.heuristicCost = Math.abs(Ai - Bi) + Math.abs(Aj - Bj);
                }
                else if(method.equalsIgnoreCase("E")){
                cell.heuristicCost= (int)Math.pow((int) Math.pow(Ai-Bi,2)+(int)Math.pow(Aj-Bj,2),1/2);
                }
                else if(method.equalsIgnoreCase("C")){
                cell.heuristicCost=Math.max(Math.abs(Ai - Bi),Math.abs(Aj - Bj));
                }else{
                System.out.println("Enter correct letter for a method ");
                System.out.println(" Chebyshev Distance, Enter 'C' ");
                System.out.println(" Euclidean Distance, Enter 'E' ");
                System.out.println(" Manhatten Distance, Enter 'M' ");
               
                
            }
                
                
                cell.blocked = randomlyGenMatrix[i][j];
                grid[i][j] = cell;
                
                
            }
        }
   
         aStar(grid, Ai, Aj, Bi, Bj);
       
      //stop time
        StdOut.println("Elapsed time = " + timerFlow.elapsedTime());
     
    }

    static void aStar(Cell[][] grid, int Ai, int Aj, int Bi, int Bj) {
        int N = grid.length;
        Queue<Cell> openList = new PriorityQueue<>(new Comparator<Cell>() {

            @Override
            public int compare(Cell o1, Cell o2) {
                
                return o1.getFCost() < o2.getFCost() ? -1 : o1.getFCost() > o2.getFCost() ? 1 : 0;
                
            }
        });
        List<Cell> closedList = new LinkedList<Cell>();
        openList.add(grid[Ai][Aj]);
        
        
       
               while (!openList.isEmpty()) {
            Cell index = openList.poll();
            if (index.i == Bi && index.j == Bj) {
                break;
            }
            List<Cell> neighbours = neighbours(index, grid);
            for (Cell neighbour : neighbours) {
                if (!closedList.contains(neighbour)) {
                    if (!openList.contains(neighbour)) {  
                        neighbour.gcost = index.gcost + 1;
                        neighbour.parent = index;
                         
                        openList.add(neighbour);
                    } else {
                        int gCost = index.gcost + 1;
                        if (neighbour.gcost > gCost) {
                            
                            neighbour.gcost = gCost;
                            neighbour.parent = index;
                        }

                    }

                }
            }
            closedList.add(index);
            
        }
        
// draw the path

        if (grid[Bi][Bj].parent != null) {
            Cell current = grid[Bi][Bj].parent;
            StdDraw.setPenColor(Color.RED);
            while (current.parent != null) {
                StdDraw.filledSquare(current.j, N - current.i - 1, .5);
                
                current = current.parent;
            }
        }
        else System.out.println("No path found");
        // FCost value grid
         for(int i=0;i<N;++i){
               for(int j=0;j<N;++j){
                System.out.printf("%-3d ", grid[i][j].getFCost());
                 
               }
               System.out.println();
           }
           System.out.println();
           
            

    } 
    
    
    

    private static List<Cell> neighbours(Cell index, Cell[][] grid) {
        List<Cell> neighbours = new ArrayList();
        int N = grid.length;

        if ((index.i - 1) >= 0 && grid[index.i - 1][index.j].blocked == true) {
            neighbours.add(grid[index.i - 1][index.j]);
        }
        if ((index.i + 1) < N && grid[index.i + 1][index.j].blocked == true) {
            neighbours.add(grid[index.i + 1][index.j]);
        }
        if ((index.j - 1) >= 0 && grid[index.i][index.j - 1].blocked == true) {
            neighbours.add(grid[index.i][index.j - 1]);
        }
        if ((index.j + 1) < N && grid[index.i][index.j + 1].blocked == true) {
            neighbours.add(grid[index.i][index.j + 1]);
        }

        return neighbours;
    } 
}
