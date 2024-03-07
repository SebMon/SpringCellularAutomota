package dk.sdu.se4.cellular;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import dk.sdu.se4.cellular.core.Game;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class Main {
    public static void main(String[] args) {
        int gameHeight = 100;
        int gameWidth = 100;
        int windowHeight = 400;
        int windowWidth = 400;
        String simName = "game_of_life";
        if (args.length > 0) {
            simName = args[0];
        }
        if (args.length >= 5) {
            try {
                windowWidth = Integer.parseInt(args[1]);
                windowHeight = Integer.parseInt(args[2]);
                gameWidth = Integer.parseInt(args[3]);
                gameHeight = Integer.parseInt(args[4]);
                if (windowWidth < gameWidth || windowHeight < gameHeight) {
                    throw new Exception("Game size larger than window size.");
                }
                if (!(windowWidth / gameWidth == windowHeight / gameHeight)) {
                    throw new Exception("Sizes not proportional.");
                }
            } catch (Exception e) {
                System.out.println("Invalid sizing. Game size cannot be larger than window size, and the sizes have to be proportional, e.g. window: 100x200, game: 50x100.");
                return;
            }
        }



        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(Main.class);

        Lwjgl3ApplicationConfiguration cfg =
                new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("Cellular Automota");
        cfg.setWindowedMode(windowWidth, windowHeight);
        cfg.setResizable(false);
        cfg.setBackBufferConfig(8, 8, 8, 8, 16, 0, 8);
        cfg.useVsync(false);

        Game game = applicationContext.getBean(Game.class);
        game.setWindowHeight(windowHeight);
        game.setWindowWidth(windowWidth);
        game.setGameHeight(gameHeight);
        game.setGameWidth(gameWidth);

        try {
            new Lwjgl3Application(game, cfg);
        } catch (RuntimeException e) {
            System.out.println("Not a valid simulation name. Use 'game_of_life' or 'reaction_diffusion'");
        }

    }
}
