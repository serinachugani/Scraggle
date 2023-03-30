/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import dictionary.Alphabet;
import dictionary.Dictionary;
import model.GridPoint;
import model.GridUnit;

/**
 *
 * @author serin
 */
public class Game {

    
    
    private final GridUnit[][] grid;
    private final Dictionary dictionary;
    
    //constructor
    public Game(Dictionary dictionary)
    {
        this.grid = new GridUnit[4][4];
        this.dictionary = dictionary;
        this.populateGrid();
    }
    
    //getter
    public GridUnit[][] getGrid()
    {
        return grid;
    }
    
    //adds grid points
    public GridUnit getGridUnit(GridPoint point)
    {
        return grid[point.x][point.y];
    }
    
    //fills grid with letters
    public void populateGrid()
    {
        for(int i = 0; i < 4; ++i)
        {
            for(int j = 0; j < 4; ++j)
            {
                grid[i][j] = new GridUnit(Alphabet.newRandom(), new GridPoint(i, j));
            }
        }
    }
    
    //prints out grid in a 4x4 display
    public void displayGrid()
    {
        System.out.println("--------------------------");
        for(int i = 0; i < 4; ++i)
        {
            System.out.print("|   ");
            
            for (int j = 0; j < 4; ++j)
            {
                System.out.print(grid[i][j].getLetter());
                System.out.print("  |  ");
            }
            
            System.out.println("\n--------------------------");
        }
    }

    /**
     * @return the dictionary
     */
    public Dictionary getDictionary() {
        return dictionary;
    }
}
