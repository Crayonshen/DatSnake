/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datsnake;

import static datsnake.Direction.UP;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author David S
 */
public class LOL {

    {
        body = new ArrayList<Point>();
    }

    public void drawHead(Graphics graphics, Point position, Point size) {
        int startAngle = 30;

        if (getDirection() == Direction.RIGHT) {
            startAngle = 30;
        } else if (getDirection() == Direction.LEFT) {
            startAngle = 210;
        } else if (getDirection() == Direction.UP) {
            startAngle = 120;
        } else if (getDirection() == Direction.DOWN) {
            startAngle = 300;
        }

        graphics.setColor(getHeadColor());
        graphics.fillArc(position.x, position.y, size.x, size.y, startAngle, 300);

        //draw eye
        graphics.setColor(Color.RED);
        if ((direction == Direction.LEFT) || (direction == Direction.RIGHT)) {
            graphics.fillOval(position.x + (size.x / 2), position.y + (size.y / 4), size.x / 6, size.y / 6);
        } else if (direction == Direction.UP) {
            graphics.fillOval(position.x + (size.x / 4), position.y + (size.y / 2), size.x / 6, size.y / 6);
        } else if (direction == Direction.DOWN) {
            graphics.fillOval(position.x + (size.x * 2 / 3), position.y + (size.y / 2), size.x / 6, size.y / 6);
        }
    }

    public void drawBodySegment(Graphics graphics, Point position, Point size) {
        graphics.setColor(getBodyColor());
        graphics.fillOval(position.x, position.y, size.x, size.y);

    }

    public void move() {
        //create a new location for the head, using the direction.
        int x = 0;
        int y = 0;
        switch (getDirection()) {
            case UP:
                x = 0;
                y = -1;
                break;
            case DOWN:
                x = 0;
                y = 1;
                break;
            case LEFT:
                x = -1;
                y = 0;
                break;
            case RIGHT:
                x = 1;
                y = 0;

        }
        body.add(0, new Point(getHead().x + x, getHead().y + y));

        if (getGrowthCounter() > 0) {
            setGrowthCounter(getGrowthCounter() - 1);

        } else {
            body.remove(body.size() - 1);
        }
    }

    public Point getHead() {
        return body.get(0);
    }

    public Point getTail() {
        return body.get(body.size() - 1);
    }

    public boolean selfHitTest() {
        for (int i = 1; i < body.size(); i++) {
            if (body.get(i).equals(getHead())) {
                return true;
            }
        }
        return false;
    }
    private ArrayList<Point> body;
    private Direction direction = Direction.RIGHT;
    private int growthCounter;
    private Color headColor = Color.BLACK;
    private Color bodyColor = Color.PINK;

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

    /**
     * @return the growthCounter
     */
    public int getGrowthCounter() {
        return growthCounter;
    }

    /**
     * @param growthCounter the growthCounter to set
     */
    public void setGrowthCounter(int growthCounter) {
        this.growthCounter = growthCounter;
    }

    /**
     * @param growthCounter the growthCounter to set
     */
    public void grow(int growth) {
        this.setGrowthCounter(this.getGrowthCounter() + growth);
    }

    /**
     * @return the headColor
     */
    public Color getHeadColor() {
        return headColor;
    }

    /**
     * @param headColor the headColor to set
     */
    public void setHeadColor(Color headColor) {
        this.headColor = headColor;
    }

    /**
     * @return the bodyColor
     */
    public Color getBodyColor() {
        return bodyColor;
    }

    /**
     * @param bodyColor the bodyColor to set
     */
    public void setBodyColor(Color bodyColor) {
        this.bodyColor = bodyColor;
    }
}
