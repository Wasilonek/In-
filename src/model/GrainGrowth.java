package model;

import controller.MainController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Kamil on 2018-07-17.
 */
public class GrainGrowth {
    private Grain[][] grainGrid;
    private int indexRight, indexLeft, indexUp, indexDown;
    private MainController mainController;
    private Random random;

    public GrainGrowth() {
        random = new Random();
        createGrid();
        randomGrains(5);
        setNeigboursForEachGrain();



        BORDERS = 0; //default number of pixels for the border.

        h = Data.getHexSize();            // h = basic dimension: height (distance between two adj centresr aka size)
        r = h / 2;            // r = radius of inscribed circle
        s = (int) (h / 1.73205);    // s = (h/2)/cos(30)= (h/2) / (sqrt(3)/2) = h / sqrt(3)
        t = (int) (r / 1.73205);    // t = (h/2) tan30 = (h/2) 1/sqrt(3) = h / (2 sqrt(3)) = r / sqrt(3)
    }

    public void createGrid(){
        grainGrid = new Grain[Data.getHexRows()][Data.getHexColumns()];
        for (int i = 0; i < Data.getHexRows(); i++) {
            for (int j = 0; j < Data.getHexColumns(); j++) {
                grainGrid[i][j] = new Grain();
            }
        }
    }

    public void randomGrains(int numberOfGrains) {
        int x,y;
        clearArray();

        for (int i = 0; i < 10; i++) {

            x = random.nextInt(Data.getHexRows());
            y = random.nextInt(Data.getHexColumns());
//            if (grainGrid[x][y].getState() == 1) {
//                continue;
//            }
            grainGrid[x][y].setState(1);
            grainGrid[x][y].setId(i);
            grainGrid[x][y].setColor(Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble()));
            //grainGrid[x][y].setColor(Color.RED);
            //i++;
        }
    }

    public void clearArray() {
        for (int i = 0; i < Data.getHexRows(); i++) {
            for (int j = 0; j < Data.getHexColumns(); j++) {
                grainGrid[i][j].setState(0);
                grainGrid[i][j].setNextState(0);
                grainGrid[i][j].setColor(Color.WHITE);
                grainGrid[i][j].setNewColor(Color.WHITE);
                grainGrid[i][j].setNewId(-1);
                grainGrid[i][j].setId(-1);
            }
        }
    }

    private void fillMap(int id, Map<Integer, Integer> grainMap) {
        if (grainMap.containsKey(id)) grainMap.put(id, grainMap.get(id) + 1);
        else grainMap.put(id, 1);
    }

    private int getIDMaxNeighbour(Map<Integer, Integer> grainMap) {
        Map.Entry<Integer, Integer> maxEntry = null;

        for (Map.Entry<Integer, Integer> entry : grainMap.entrySet())
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
                maxEntry = entry;

        int id = 0;
        if (maxEntry != null) {
            int max = maxEntry.getValue();

            List<Integer> listMax = new ArrayList<>();
            for (Map.Entry<Integer, Integer> entry : grainMap.entrySet())
                if (entry.getValue() == max)
                    listMax.add(entry.getKey());

            Random rand = new Random();
            int randWinner = rand.nextInt(listMax.size());
            id = listMax.get(randWinner);
        }

        return id;
    }

    public void setNeigboursForEachGrain() {
        for (int i = 1; i < Data.getHexRows()-1; i++) {
            for (int j = 1; j < Data.getHexColumns()-1; j++) {
                if (i % 2 == 0) {
                    setEdge(i, j);

                    grainGrid[i][j].setSingleNeighbour(0, grainGrid[indexUp][indexLeft]);

                    grainGrid[i][j].setSingleNeighbour(1, grainGrid[i][indexLeft]);

                    grainGrid[i][j].setSingleNeighbour(2, grainGrid[indexDown][indexLeft]);

                    grainGrid[i][j].setSingleNeighbour(3, grainGrid[indexDown][j]);

                    grainGrid[i][j].setSingleNeighbour(4, grainGrid[i][indexRight]);

                    grainGrid[i][j].setSingleNeighbour(5, grainGrid[indexUp][j]);

                } else {
                    setEdge(i, j);

                    grainGrid[i][j].setSingleNeighbour(0, grainGrid[indexUp][j]);

                    grainGrid[i][j].setSingleNeighbour(1, grainGrid[i][indexLeft]);

                    grainGrid[i][j].setSingleNeighbour(2, grainGrid[indexDown][j]);

                    grainGrid[i][j].setSingleNeighbour(3, grainGrid[indexDown][indexRight]);

                    grainGrid[i][j].setSingleNeighbour(4, grainGrid[i][indexRight]);

                    grainGrid[i][j].setSingleNeighbour(5, grainGrid[indexUp][indexRight]);
                }
            }
        }


    }

    public void setEdge(int i, int j) {

        indexUp = i - 1;

        indexDown = i + 1;

        indexRight = j + 1;

        indexLeft = j - 1;

        if (i == 0)
            indexUp = Data.getHexRows() - 1;
        if (i == (Data.getHexRows() - 1))
            indexDown = 0;
        if (j == 0)
            indexLeft = Data.getHexColumns() - 1;
        if (j == (Data.getHexColumns() - 1))
            indexRight = 0;
    }

    public Grain[][] getGrainGrid() {
        return grainGrid;
    }
    public Grain getGrain(int i , int j){
        return grainGrid[i][j];
    }



    GrainGrowth grainGrowth;

    private int s;    // length of one side
    private int t;    // short side of 30o triangle outside of each hex
    private int r;    // radius of inscribed circle (centre to middle of each side). r= h/2
    private int h;    // height. Distance between centres of two adjacent hexes. Distance between two opposite sides in a hex.

    private int BORDERS;    //default number of pixels for the border.


    public void drawHex(GraphicsContext graphicsContext) {
        double y, x;

        for (int i = 0; i < Data.getHexRows(); i++) {
            for (int j = 0; j < Data.getHexColumns(); j++) {
                int x1 = i * (s + t);
                int y1 = j * h + (i % 2) * h / 2;

                y = y1 + BORDERS;
                x = x1 + BORDERS;

                if (grainGrid[i][j].getState() == 1) {
                    graphicsContext.setFill(grainGrid[i][j].getColor());
                    graphicsContext.fillPolygon(new double[]{x + t, x + s + t, x + s + t + t, x + s + t, x + t, x},
                            new double[]{y, y, y + r, y + r + r, y + r + r, y + r}, 6);
                }
            }
        }
    }
}
