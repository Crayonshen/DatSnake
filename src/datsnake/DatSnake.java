/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datsnake;

import environment.ApplicationStarter;
import image.ResourceTools;

/**
 *
 * @author David S
 */
public class DatSnake {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        start();
        inputDialogu();
    }

    private static void start() {
        SnakeEnvironment myGame = new SnakeEnvironment();        
        myGame.setBackground(ResourceTools.loadImageFromResource("resources/green-tech-lines-powerpoint-green-tech-lines-backgrounds.jpg"));
        ApplicationStarter.run("Dat Snake", new SnakeEnvironment());
        ApplicationStarter.run("my Rockin' Project", myGame);
    }

    private static void inputDialogu() {
    
    }
    
}
