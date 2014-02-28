/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datsnake;

import environment.Environment;
import environment.GraphicsPalette;
import environment.Grid;
import image.ResourceTools;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author David S
 */
class SnakeEnvironment extends Environment {

    private GameState gameState = GameState.PAUSED;
    private Grid grid;
    private int score = 0;
    private LOL snake;
    private ArrayList<Point> apples;
    private Image tofu;
    private ArrayList<Point> tofus;
    private Image loser;
    private ArrayList<Point> losers;
    private boolean drawPicture = false;
    private int moveDelay = 7;
    private int moveCounter = moveDelay;
    private ArrayList<Point> bombs;

    public SnakeEnvironment() {
    }

    @Override
    public void initializeEnvironment() {
        this.grid = new Grid();
        this.grid.setColor(Color.RED);
        this.grid.setColumns(40);
        this.grid.setRows(20);
        this.grid.setCellHeight(20);
        this.grid.setCellWidth(20);
        this.grid.setPosition(new Point(50, 100));

        this.loser = ResourceTools.loadImageFromResource("resources/HAH.jpeg");
        this.losers = new ArrayList<Point>();
        this.losers.add(getRandomGridLocation());

        this.tofu = ResourceTools.loadImageFromResource("resources/2008-06-23-tofu.png");
        this.tofus = new ArrayList<Point>();
        for (int i = 0; i < 1; i++) {
            this.tofus.add(getRandomGridLocation());
        }

        this.apples = new ArrayList<Point>();
        for (int i = 0; i < 3; i++) {
            this.apples.add(getRandomGridLocation());
        }

        this.bombs = new ArrayList<Point>();
        for (int i = 0; i < 3; i++) {
            this.bombs.add(getRandomGridLocation());
        }

        this.snake = new LOL();
        restart();
    }

    private Point getRandomGridLocation() {
        return new Point((int) (Math.random() * this.grid.getColumns()), (int) (Math.random() * this.grid.getRows()));
    }

    @Override
    public void timerTaskHandler() {
        if (this.gameState == GameState.RUNNING) {
            if (snake != null) {
                if (moveCounter <= 0) {
                    snake.move();
                    moveCounter = moveDelay;
                    checkBoredIntersection();

                    if (snake.selfHitTest()) {
                        this.gameState = GameState.ENDED;
                    }
                } else {
                    moveCounter--;
                }
            }
        }

        if (snake.getDirection() == Direction.RIGHT) {
            if (snake.getHead().x >= this.grid.getColumns()) {
                snake.getHead().x = 0;
            }
        } else if (snake.getDirection() == Direction.LEFT) {
            if (snake.getHead().x <= -1) {
                snake.getHead().x = this.grid.getColumns() - 1;
            }
        } else if (snake.getDirection() == Direction.UP) {
            if (snake.getHead().y <= -1) {
                snake.getHead().y = this.grid.getRows() - 1;
            }
        } else if (snake.getDirection() == Direction.DOWN) {
            if (snake.getHead().y >= this.grid.getRows()) {
                snake.getHead().y = 0;
            }
        }
    }

    @Override
    public void keyPressedHandler(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            System.out.println("Game State " + gameState.toString());

            if (gameState == GameState.RUNNING) {
                gameState = GameState.PAUSED;
            } else if (gameState == GameState.PAUSED) {
                gameState = GameState.RUNNING;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (snake.getDirection() != Direction.DOWN) {
                snake.setDirection(Direction.UP);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (snake.getDirection() != Direction.UP) {
                snake.setDirection(Direction.DOWN);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (snake.getDirection() != Direction.RIGHT) {
                snake.setDirection(Direction.LEFT);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (snake.getDirection() != Direction.LEFT) {
                snake.setDirection(Direction.RIGHT);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            gameState = GameState.ENDED;
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            restart();
        }
//        if ((e.getKeyCode()== KeyEvent.VK_LEFT)&&(e.getKeyCode()==KeyEvent.VK_RIGHT)) {
//            
//        }

    }

    @Override
    public void keyReleasedHandler(KeyEvent e) {
    }

    @Override
    public void environmentMouseClicked(MouseEvent e) {
    }

    @Override
    public void paintEnvironment(Graphics graphics) {
        if (this.grid != null) {
//            this.grid.paintComponent(graphics);

            for (int i = 0; i < this.apples.size(); i++) {
                this.apples.get(i);
                GraphicsPalette.drawApple(graphics, this.grid.getCellPosition(this.apples.get(i)), this.grid.getCellSize());
            }
            for (int i = 0; i < this.bombs.size(); i++) {
                this.bombs.get(i);
                GraphicsPalette.drawBomb(graphics, this.grid.getCellPosition(this.bombs.get(i)), this.grid.getCellSize(), Color.BLACK);
            }

            Point cellLocation;
            if (snake != null) {
                
                for (int i = 0; i < snake.getBody().size(); i++) {
                    cellLocation = grid.getCellPosition(snake.getBody().get(i));
                    if (i == 0) {
                        snake.drawHead(graphics, cellLocation, this.grid.getCellSize());
                    } else {
                        snake.drawBodySegment(graphics, cellLocation, this.grid.getCellSize());
                    }
                }
            }

            if (tofus != null) {
                for (int i = 0; i < tofus.size(); i++) {
                    cellLocation = grid.getCellPosition(tofus.get(i));
                    graphics.drawImage(tofu, cellLocation.x, cellLocation.y, this.grid.getCellHeight(), this.grid.getCellWidth(), this);
//                    graphics.fillOval( grid.getCellWidth(), grid.getCellHeight());
                }
            }

            graphics.setFont(new Font("Calibri", Font.BOLD, 100));
            graphics.drawString("score = " + this.getScore(), 100, 100);
            if (drawPicture) {
                graphics.drawImage(loser, 0, 0, this);
            }
            if (gameState == GameState.ENDED) {
                this.drawPicture = true;
                graphics.setColor(Color.red);
                graphics.setFont(new Font("Calibri", Font.ITALIC, 100));
                graphics.drawString("GAME OVER!!!", 50, 450);
                graphics.drawString("score = " + this.getScore(), 100, 100);
            }

            if (this.score == 0) {
                graphics.setColor(Color.red);
                graphics.setFont(new Font("Calibri", Font.ITALIC, 100));
                graphics.drawString("Level 1", 600, 100);
            }
            if (this.score == 50) {
                graphics.setColor(Color.red);
                graphics.setFont(new Font("Calibri", Font.ITALIC, 100));
                graphics.drawString("Level 2", 600, 100);
            }
            if (this.score == 100) {
                graphics.setColor(Color.red);
                graphics.setFont(new Font("Calibri", Font.ITALIC, 100));
                graphics.drawString("Level 3", 600, 100);
            }
            if (this.score == 150) {
                graphics.setColor(Color.red);
                graphics.setFont(new Font("Calibri", Font.ITALIC, 100));
                graphics.drawString("Level 4", 600, 100);
            }
            if (this.score == 200) {
                graphics.setColor(Color.red);
                graphics.setFont(new Font("Calibri", Font.ITALIC, 100));
                graphics.drawString("Level 5", 600, 100);
            }
            if (this.score == 300) {
                graphics.setColor(Color.red);
                graphics.setFont(new Font("Calibri", Font.ITALIC, 100));
                graphics.drawString("Level infinate", 100, 600);
            }
        }
    }

    private void restart() {
        //set the score to 0
        score = 0;

        //move the snake back
        snake.getBody().clear();
        snake.getBody().add(new Point(5, 8));
        snake.getBody().add(new Point(5, 7));
        snake.getBody().add(new Point(5, 6));
        snake.getBody().add(new Point(5, 5));
        snake.getBody().add(new Point(5, 4));

        //reset the speed
        this.moveDelay = 8;
        //reset the gamestate
        gameState = GameState.PAUSED;
        this.drawPicture = false;

    }

    private void checkBoredIntersection() {
//if the snake location is the same as an apple location
        //ithen grow the smake and remove the apple
        //later,move apple and make a sound and incresase the score
        for (int i = 0; i < this.apples.size(); i++) {
            if (snake.getHead().equals(this.apples.get(i))) {
//                System.out.println("Apple chomp!!!!!");
                this.setScore(this.getScore() + 10);
                this.apples.get(i).setLocation(getRandomGridLocation());
                //grow the snake
                this.snake.grow(1);
            }
        }
        for (int i = 0; i < this.bombs.size(); i++) {
            if (snake.getHead().equals(this.bombs.get(i))) {
                System.out.println("GameOver");
                gameState = GameState.ENDED;
                break;
            }
        }
        for (int i = 0; i < this.tofus.size(); i++) {
            if (snake.getHead().equals(this.tofus.get(i))) {
                this.setScore(this.getScore() + 10);
                this.snake.grow(1);
                this.tofus.get(i).setLocation(getRandomGridLocation());
                this.moveDelay++;
                break;
            }
        }
    }

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param newScore the score to set
     */
    public void setScore(int newScore) {

        if ((this.score < 50) && (newScore >= 50)) {
            this.moveDelay--;
            this.moveDelay--;
        } else if ((this.score < 100) && (newScore >= 100)) {
            this.moveDelay--;
        } else if ((this.score < 150) && (newScore >= 150)) {
            this.moveDelay--;
            this.moveDelay--;
        } else if ((this.score < 200) && (newScore >= 200)) {
            this.moveDelay--;
            this.moveDelay--;
        } else if ((this.score < 300) && (newScore >= 300)) {
            this.moveDelay--;
            this.moveDelay--;
            this.moveDelay--;
            this.moveDelay--;
            this.moveDelay--;
        }
        this.score = newScore;
    }
}
