/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datsnake;

import environment.Environment;
import environment.Grid;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author David S
 */
class SnakeEnvironment extends Environment{
    private Grid tofu;
    private int score = 0;
    private LOL bored;
    
    
    public SnakeEnvironment() {
    }

    @Override
    public void initializeEnvironment() {  
        this.tofu = new Grid();
        this.tofu.setColor(Color.RED);
        this.tofu.setColumns(40);
        this.tofu.setRows(20);
        this.tofu.setCellHeight(20);
        this.tofu.setCellWidth(20);
        this.tofu.setPosition(new Point(50,100));
        
        
        this.bored = new LOL();
        this.bored.getBody().add(new Point(5,5));
        this.bored.getBody().add(new Point(5,4));
        this.bored.getBody().add(new Point(5,3));
        this.bored.getBody().add(new Point(4,3));
    }

    @Override
    public void timerTaskHandler() { 
        if (bored !=null){
//            bored.move();
        }
        
        if (bored.getDirection() == Diection.RIGHT) {
                if (bored.getHead().x >= this.tofu.getColumns()) {
                    bored.getHead().x = 0 ;
                }
            } else if (bored.getDirection() == Direction.LEFT) {
                if (bored.getHead().x <=-1) {
                    bored.getHead().x = this.tofu.getColumns()-1;
                }
            } else if (bored.getDirection() == Direction.UP) {
                if (bored.getHead().y <= -1) {
                    bored.getHead().y = this.tofu.getRows()-1;
                }
            } else if (bored.getDirection() == Direction.DOWN) {
                if (bored.getHead().y >= this.tofu.getRows()) {
                    bored.getHead().y = 0;
                }
            }
        
    }

    @Override
    public void keyPressedHandler(KeyEvent e) {
    if(e.getKeyCode() == KeyEvent.VK_SPACE){
        this.score += 500000000;
        }else if (e.getKeyCode() ==KeyEvent.VK_G){
            bored.kimchi();
        }else if (e.getKeyCode() ==KeyEvent.VK_UP){
            bored.setDirection(Direction.UP);
        }else if (e.getKeyCode() ==KeyEvent.VK_DOWN){
            bored.setDirection(Direction.DOWN);
        }else if (e.getKeyCode() ==KeyEvent.VK_LEFT){
            bored.setDirection(Direction.LEFT);
        }else if (e.getKeyCode() ==KeyEvent.VK_RIGHT){
            bored.setDirection(Direction.RIGHT);
        }
    }

    @Override
    public void keyReleasedHandler(KeyEvent e) {
    }

    @Override
    public void environmentMouseClicked(MouseEvent e) {
    }

    @Override
    public void paintEnvironment(Graphics graphics) {
   if (this.tofu != null){
       this.tofu.paintComponent(graphics);
       
       Point cellLocation;
       graphics.setColor(Color.pink);
       if(bored!= null){
           for (int i = 0; i < bored.getBody().size(); i++) {
               cellLocation = tofu.getCellPosition(bored.getBody().get(i));
               graphics.fillOval(cellLocation.x, cellLocation.y, tofu.getCellWidth(), tofu.getCellHeight());
               
           }
       }
    }
  
   graphics.setFont(new Font("Times New Roman", Font.BOLD,60));
   graphics.drawString("Score: "+ this.score,50,50);
    }   
}
