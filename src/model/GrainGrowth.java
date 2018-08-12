package model;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.*;


public class GrainGrowth {

    private Grain[][] grainGrid;

    private Random random;

    private Map<Integer, Integer> grainMap;

    private Map<Integer, Color> colorForEveryId, colorMap;

    private int hasGrainHasNeigbours;

    private boolean isGridFull;


    private int s;
    private int t;
    private int r;
    private int h;


    public GrainGrowth() {
        random = new Random();
        createGrid();

        grainMap = new HashMap<>();
        colorMap = new HashMap<>();

        colorForEveryId = new HashMap<>();

        calculateHexSize();
    }

    public void calculateHexSize() {
        h = Data.getHexHeight();
        r = h / 2;
        s = (int) (h / 1.73205);
        t = (int) (r / 1.73205);
    }

    public void randColorForEveryId(int howManyGrains) {
        for (int i = 0; i < howManyGrains; i++) {
            colorForEveryId.put(i, (Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble())));
        }
    }

    public void createGrid() {
        grainGrid = new Grain[Data.getHexRows()][Data.getHexColumns()];
        for (int i = 0; i < Data.getHexRows(); i++) {
            for (int j = 0; j < Data.getHexColumns(); j++) {
                grainGrid[i][j] = new Grain();
            }
        }
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
            grainGrid[x][y].setColor(colorForEveryId.get(grainGrid[x][y].getId()));
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

    public void drawHex(GraphicsContext graphicsContext) {
        Platform.runLater(() -> {
            double y, x;

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
        });
    }

    private void fillMap(int id, Map<Integer, Integer> grainMap) {
        if (grainMap.containsKey(id)) grainMap.put(id, grainMap.get(id) + 1);
        else grainMap.put(id, 1);
    }

    private int getMaxNeighbourId(Map<Integer, Integer> grainMap) {
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

    public boolean checkNeighbour(int i, int j) {
        int id = 0;
        if (!(i == -1) && !(j == -1)) {
            if (i < Data.getHexRows() && j < Data.getHexColumns()) {
                if (grainGrid[i][j].getState() == 1) {
                    id = grainGrid[i][j].getId();
                    fillMap(id, grainMap);
                    colorMap.put(id, grainGrid[i][j].getColor());
                    return true;
                }
            }
        }
        return false;
    }

    public boolean setNeigboursForEachGrain() {
        Platform.runLater(() -> {
            isGridFull = true;
            for (int i = 0; i < Data.getHexRows(); i++) {
                for (int j = 0; j < Data.getHexColumns(); j++) {
                    grainMap.clear();
                    int idToAssign = 0;
                    hasGrainHasNeigbours = 0;

                    if (grainGrid[i][j].getState() == 0) {
                        isGridFull = false;

                        if (i % 2 == 0) {

                            if (checkNeighbour(i - 1, j - 1)) {
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
                                idToAssign = getMaxNeighbourId(grainMap);
                                grainGrid[i][j].setNextState(1);
                                grainGrid[i][j].setColor(colorMap.get(idToAssign));
                                grainGrid[i][j].setId(idToAssign);
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
                                idToAssign = getMaxNeighbourId(grainMap);
                                grainGrid[i][j].setNextState(1);
                                grainGrid[i][j].setColor(colorMap.get(idToAssign));
                                grainGrid[i][j].setId(idToAssign);
                            }
                        }
                    }
                }
            }
            copyArray();

        });
        return isGridFull;
    }

    public void showGrid() {
        System.out.println();
        System.out.println();
        for (int i = 0; i < Data.getHexRows(); i++) {
            for (int j = 0; j < Data.getHexColumns(); j++) {
                if (grainGrid[i][j].getState() == 1) {
                    System.out.print("# ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
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


    //////////////////////////////////////////////////////////////////////////////////////////////////////////
}

