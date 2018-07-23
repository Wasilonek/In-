package model;

/**
 * Created by Kamil on 2018-07-17.
 */
public class Data {



    private static int maxCanvasWidth = 1000;
    private static int maxCanvasHeight = 1000;

    private static int hexSize = 12; // min 4

    private static int hexRows = 30;
    private static int hexColumns = 30;


    public static int getHexSize() {
        return hexSize;
    }

    public static int getMaxCanvasWidth() {
        return maxCanvasWidth;
    }

    public static int getMaxCanvasHeight() {
        return maxCanvasHeight;
    }

    public static int getHexRows() {
        return hexRows;
    }

    public static int getHexColumns() {
        return hexColumns;
    }
}
