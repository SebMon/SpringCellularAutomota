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
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(Main.class);

        Lwjgl3ApplicationConfiguration cfg =
                new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("Cellular Automoton");
        cfg.setWindowedMode(400, 400);
        cfg.setResizable(false);
        cfg.setBackBufferConfig(8, 8, 8, 8, 16, 0, 8);
        cfg.useVsync(false);

        new Lwjgl3Application(applicationContext.getBean(Game.class), cfg);

    }
}
