package model;

import javafx.scene.paint.Color;

public class Grain {
    private int state, nextState, id, newId;
    private Color oldColor, newColor;


    public Grain() {
        state = nextState = 0;
        id = newId = -1;
        oldColor = Color.GREEN;
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

    public Color getOldColor() {
        return oldColor;
    }

    public void setOldColor(Color oldColor) {
        this.oldColor = oldColor;
    }

    public Color getNewColor() {
        return newColor;
    }

    public void setNewColor(Color newColor) {
        this.newColor = newColor;
    }
}
