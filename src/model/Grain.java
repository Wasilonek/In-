package model;

import javafx.scene.paint.Color;

import java.util.Random;

public class Grain {
    private int state, nextState, id, newId;
    private Color color, newColor;
    private Grain[] neighbours;

    Random random;



    public Grain() {
        neighbours = new Grain[6];
        state = nextState = 0;
        id = newId = -1;


        random = new Random();
       // color = Color.color(random.nextDouble(),random.nextDouble(),random.nextDouble());
//        int choice = random.nextInt(1);
//        if(choice == 0){
            color = Color.WHITE;
//        }
//        if(choice == 1){
//            color = Color.BLUE;
//        }




        newColor = Color.WHITE;
    }



    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getNextState() {
        return nextState;
    }

    public void setNextState(int nextState) {
        this.nextState = nextState;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNewId() {
        return newId;
    }

    public void setNewId(int newId) {
        this.newId = newId;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getNewColor() {
        return newColor;
    }

    public void setNewColor(Color newColor) {
        this.newColor = newColor;
    }

    public void setSingleNeighbour(int index, Grain singleNeighbour) {
        this.neighbours[index] = singleNeighbour;
    }
}
