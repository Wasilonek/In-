package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.*;

/**
 * Created by Kamil on 2018-07-17.
 */
public class GrainGrowth {

    private Grain[][] grainGrid;

    private int indexRight, indexLeft, indexUp, indexDown;

    private Random random;

    private Map<Integer, Integer> grainMap;

    private int hasGrainHasNeigbours;


    int id;
    int idToAssign;


    // Do innej klasy

    private int s;
    private int t;
    private int r;
    private int h;

    //********************************


    public GrainGrowth() {
        random = new Random();
        createGrid();


        grainMap = new HashMap<>();

        id = 0;
        idToAssign = 0;

        //setNeigboursForEachGrain();
        // Do innej klasy

        h = Data.getHexHeight();
        r = h / 2;
        s = (int) (h / 1.73205);
        t = (int) (r / 1.73205);

        //*******************************************************
    }

    public void createGrid() {
        grainGrid = new Grain[Data.getHexRows()][Data.getHexColumns()];
        for (int i = 0; i < Data.getHexRows(); i++) {
            for (int j = 0; j < Data.getHexColumns(); j++) {
                grainGrid[i][j] = new Grain();
            }
        }
    }

    public void changeGridSize(int canvasWidth, int canvasHeight) {
//        r = h / 2;
//        s = (int) (h / 1.73205);
//        t = (int) (r / 1.73205);

        Data.setHexColumns((canvasHeight / Data.getHexHeight()));

        Data.setHexRows((int) ((canvasWidth / Data.getHexHeight()) * 1.6));

        createGrid();
        System.out.println(Data.getHexRows() + " " + Data.getHexColumns());
    }

    public void randomGrains(int numberOfGrains) {
        int x, y;
        clearArray();

        int k = 0;
        for (int i = 0; i < numberOfGrains; ) {

            x = random.nextInt(Data.getHexRows());
            y = random.nextInt(Data.getHexColumns());
            if (grainGrid[x][y].getState() == 1) {
                if (k == 10000)
                    break;
                k++;
                continue;
            }
            grainGrid[x][y].setState(1);
            grainGrid[x][y].setId(i);
            grainGrid[x][y].setColor(Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble()));
            //System.out.println(grainGrid[x][y].getId() + "/n ");
            //grainGrid[x][y].setColor(Color.ORANGE);
            i++;
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

    public void drawHex(GraphicsContext graphicsContext, int h) {
        double y, x;

        //Ponownie obliczam wielkosci w oparciu o nowe h
        this.h = h;
        r = h / 2;
        s = (int) (h / 1.73205);
        t = (int) (r / 1.73205);

        System.out.println("s:" + s + " h:" + h + " r:" + r + " t:" + t);

        for (int i = 0; i < Data.getHexRows(); i++) {
            for (int j = 0; j < Data.getHexColumns(); j++) {
                int x1 = i * (s + t);
                int y1 = j * h + (i % 2) * h / 2;

                y = y1;
                x = x1;

                if (grainGrid[i][j].getState() == 1) {
                    graphicsContext.setFill(grainGrid[i][j].getColor());
                    graphicsContext.fillPolygon(new double[]{x + t, x + s + t, x + s + t + t, x + s + t, x + t, x},
                            new double[]{y, y, y + r, y + r + r, y + r + r, y + r}, 6);
                }
            }
        }
    }


    public Grain[][] getGrainGrid() {
        return grainGrid;
    }

    public Grain getGrain(int i, int j) {
        return grainGrid[i][j];
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

    public boolean checkNeighbour(int indUp, int indRight) {
        if (!(indUp == -1) && !(indRight == -1)) {
            if (indUp < Data.getHexRows() - 1 && indRight < Data.getHexColumns() - 1) {
                if (grainGrid[indUp][indRight].getState() == 1) {
                    id = grainGrid[indUp][indRight].getId();
                    fillMap(id, grainMap);
                    //colorMap.put(id, grainsArray[indUp][indRight].getColor());
                    return true;
                }
            }
        }
        return false;
    }


    public void setNeigboursForEachGrain() {



        for (int i = 0; i < Data.getHexRows(); i++) {
            for (int j = 0; j < Data.getHexColumns(); j++) {
                grainMap.clear();
                id = 0;
                idToAssign = 0;
                hasGrainHasNeigbours = 0;

                if (grainGrid[i][j].getState() == 0) {

                    if (i % 2 == 0) {

                        if (checkNeighbour(i - 1, j + 1)) {
                            hasGrainHasNeigbours++;
                        }

                        if (checkNeighbour(i, j - 1)) {
                            hasGrainHasNeigbours++;
                        }

                        if (checkNeighbour(i + 1, j - 1)) {
                            hasGrainHasNeigbours++;
                        }

                        if (checkNeighbour(i + 1, j)) {
                            hasGrainHasNeigbours++;
                        }

                        if (checkNeighbour(i, j + 1)) {
                            hasGrainHasNeigbours++;
                        }

                        if (checkNeighbour(i - 1, j)) {
                            hasGrainHasNeigbours++;
                        }

                        if (hasGrainHasNeigbours > 0) {
                            idToAssign = getIDMaxNeighbour(grainMap);
                            grainGrid[i][j].setNextState(1);
                            grainGrid[i][j].setColor(Color.GREEN);
                            grainGrid[i][j].setId(idToAssign);
                        }
                    }
                } else if (i % 2 != 0) {


                    if (checkNeighbour(i - 1, j)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkNeighbour(i, j - 1)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkNeighbour(i + 1, j)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkNeighbour(i + 1, j + 1)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkNeighbour(i, j + 1)) {
                        hasGrainHasNeigbours++;
                    }

                    if (checkNeighbour(i - 1, j + 1)) {
                        hasGrainHasNeigbours++;
                    }

                    if (hasGrainHasNeigbours > 0) {
                        idToAssign = getIDMaxNeighbour(grainMap);
                        grainGrid[i][j].setNextState(1);
                        grainGrid[i][j].setColor(Color.BLUE);
                        grainGrid[i][j].setId(idToAssign);
                    }
                }
            }
        }
        copyArray();
    }
    public void copyArray() {
        for (int i = 0; i < Data.getHexRows(); i++) {
            for (int j = 0; j < Data.getHexColumns(); j++) {
                if (grainGrid[i][j].getState() == 0) {
                    grainGrid[i][j].setState(grainGrid[i][j].getNextState());
                }
            }
        }
    }
}

