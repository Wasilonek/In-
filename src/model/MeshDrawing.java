package model;

import javafx.scene.canvas.GraphicsContext;

public class MeshDrawing {

    GrainGrowth grainGrowth;

    private int s;    // length of one side
    private int t;    // short side of 30o triangle outside of each hex
    private int r;    // radius of inscribed circle (centre to middle of each side). r= h/2
    private int h;    // height. Distance between centres of two adjacent hexes. Distance between two opposite sides in a hex.

    private int BORDERS;    //default number of pixels for the border.

    public MeshDrawing() {

        grainGrowth = new GrainGrowth();

        BORDERS = 0; //default number of pixels for the border.

        h = Data.getHexSize();            // h = basic dimension: height (distance between two adj centresr aka size)
        r = h / 2;            // r = radius of inscribed circle
        s = (int) (h / 1.73205);    // s = (h/2)/cos(30)= (h/2) / (sqrt(3)/2) = h / sqrt(3)
        t = (int) (r / 1.73205);    // t = (h/2) tan30 = (h/2) 1/sqrt(3) = h / (2 sqrt(3)) = r / sqrt(3)
    }

    public void drawHex(GraphicsContext graphicsContext) {
        double y, x;

        for (int i = 0; i < Data.getHexRows(); i++) {
            for (int j = 0; j < Data.getHexColumns(); j++) {
                int x1 = i * (s + t);
                int y1 = j * h + (i % 2) * h / 2;

                y = y1 + BORDERS;
                x = x1 + BORDERS;

                if (grainGrowth.getGrain(i, j).getState() == 1) {
                    graphicsContext.setFill(grainGrowth.getGrain(i,j).getColor());
                    graphicsContext.fillPolygon(new double[]{x + t, x + s + t, x + s + t + t, x + s + t, x + t, x},
                            new double[]{y, y, y + r, y + r + r, y + r + r, y + r}, 6);
                }
            }
        }
    }
}
