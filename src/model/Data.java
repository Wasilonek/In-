package model;

public class Data {

    private static int maxCanvasWidth = 500;
    private static int maxCanvasHeight = 500;

    private static int hexHeight = 4; // min 4

    private static int hexRows = 160;
    private static int hexColumns = 125;

    public static int getHexRows() {
        return hexRows;
    }

    public static int getHexColumns() {
        return hexColumns;
    }

    public static int getHexHeight() {
        return hexHeight;
    }
}
