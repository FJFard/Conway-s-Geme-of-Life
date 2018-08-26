/* Conway's Game of Life
 * The universe of the Game of Life is an infinite, two-dimensional orthogonal grid of square cells, 
 * each of which is in one of two possible states, alive or dead, (or populated and unpopulated, respectively). 
 * Every cell interacts with its eight neighbours, which are the cells that are horizontally, vertically, 
 * or diagonally adjacent. At each step in time, the following transitions occur:

1. Any live cell with fewer than two live neighbors dies, as if by under population.
2. Any live cell with two or three live neighbors lives on to the next generation.
3. Any live cell with more than three live neighbors dies, as if by overpopulation.
4. Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.

The initial pattern constitutes the seed of the system. 
The first generation is created by applying the above rules simultaneously to every cell in the seed; 
births and deaths occur simultaneously, and the discrete moment at which this happens is sometimes called a tick. 
Each generation is a pure function of the preceding one. 
The rules continue to be applied repeatedly to create further generations.

- Wikipedia.org
 */

/*
 * Author: Faranak J
 * comments are cleared from the following code to make sure code is clear by itself. Please contact me,
 * if it's not.
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class ConwayFar extends JPanel{
    private int cellW = 20;
    private int rows = 50;
    private int cols = rows;

    private int[][] cells;
    private int[][] nextCells;
    private Color deadCell = Color.WHITE;
    private Color aliveCell = Color.RED;
    private Color backgroundColor = Color.BLACK;
    private int sleepTime = 500;

    public ConwayFar()  {
        creatWindow();
        setRandomCells();
        while(true) {
            for(int i=0; i<rows; i++) {
                for(int j=0; j<cols; j++) {
                    int liveN = countLiveNeighbors(i, j);
                    if(cells[i][j] == 0) {
                        if(liveN == 3) {
                            nextCells[i][j] = 1;
                        }
                    }
                    else {
                        if( (liveN < 2) || (liveN >3) ) {
                            nextCells[i][j] = 0;
                        }
                    }                    
                }
            }
            applyChanges();
        }

    }

    public void creatWindow(){

        cells = new int[rows][cols];
        nextCells = new int[rows][cols];

        setPreferredSize(new Dimension(cellW*cols, cellW*rows));
        setBackground(backgroundColor);

        JFrame frame = new JFrame("Far Grid");        

        frame.setContentPane(this);
        frame.setResizable(false);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        freeze();
    }

    public void freeze() {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setRandomCells() {
        Random r = new Random();
        for(int i=0; i<rows; i++) {
            for(int j=0; j<cols; j++) {
                nextCells[i][j] = r.nextInt(2);
            }
        }
        this.applyChanges();
    }

    public void applyChanges() {
        for(int i=0; i<rows; i++) {
            for(int j=0; j<cols; j++) {
                this.cells[i][j] = this.nextCells[i][j];
            }
        }
        this.repaint();
        freeze();
    }

    public int countLiveNeighbors(int row, int column) {
        int iStart = 0;
        int jStart = 0;
        int iEnd = this.rows-1;
        int jEnd = this.cols-1;
        int sum = 0;
        if(row-1 >= 0) {
            iStart = row-1;
        }
        if(column-1 >= 0) {
            jStart = column-1;
        }
        if(row+1 <= iEnd ) {
            iEnd = row+1;
        }
        if(column+1 <= jEnd) {
            jEnd = column+1;
        }

        for(int i = iStart; i <= iEnd; i++) {
            for(int j = jStart; j <= jEnd; j++) {
                sum += cells[i][j];
            }
        }
        sum -= cells[row][column];
        return(sum);

    }

    @Override
    protected void paintComponent(Graphics g) { 
        g.setColor(getBackground());
        g.fillRect(0,0,getWidth(),getHeight());
        int i, j;
        for (i = 0; i < rows; i++) {
            for (j = 0; j < cols; j++) {
                if(cells[i][j] == 0)                    
                    g.setColor(deadCell);
                else
                    g.setColor(aliveCell);
                int x1 = (int)(j*cellW);
                int y1 = (int)(i*cellW);
                int x2 = (int)((j+1)*cellW);
                int y2 = (int)((i+1)*cellW);
                g.fillOval( x1, y1, (x2-x1), (y2-y1) );
            }
        }
    }

}

