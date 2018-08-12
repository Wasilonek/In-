package model;

public class Data {

    private static int maxCanvasWidth = 1000;
    private static int maxCanvasHeight = 1000;

    private static int hexHeight = 4; // min 4

    private static int hexRows = 100;
    private static int hexColumns = 100;


    public static int getHexHeight() {
        return hexHeight;
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

    public static void setHexHeight(int hexHeight) {
        Data.hexHeight = hexHeight;
    }

    public static void setHexRows(int hexRows) {
        Data.hexRows = hexRows;
    }

    public static void setHexColumns(int hexColumns) {
        Data.hexColumns = hexColumns;
    }
}
