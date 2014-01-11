/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datsnake;

import static datsnake.Direction.UP;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author David S
 */
public class LOL {
    private ArrayList<Point> body;
    private Direction direction = Direction.RIGHT;
    
    {
    body = new ArrayList<Point>();
    }

    public void kimchi(){
        //create a new location for the head, using the direction.
        int x=0;
        int y=0;
        switch (getDirection()){
            case UP:
                x=0;
                y=-1;
                for (int i = 1; i < 10; i++) {
                    
                }
                break;
            case DOWN:
                x=0;
                y=1;
                break;
            case LEFT:
                x=-1;
                y=0;
                break;
            case RIGHT:
                x=1;
                y=0;
                
        }
        body.add(0, new Point(getHead().x+x, getHead().y+y));
        
        body.remove(body.size() - 1);
    }
    
    public Point getHead(){
        return body.get(0);
    }
    /**
     * @return the body
     */
    public ArrayList<Point> getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(ArrayList<Point> body) {
        this.body = body;
    }

    /**
     * @return the direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
