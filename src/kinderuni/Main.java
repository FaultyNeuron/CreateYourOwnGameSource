package kinderuni;

import kinderuni.ui.ContainerComponent;
import kinderuni.ui.Screen;
import kinderuni.desktop.DesktopSystem;
import kinderuni.ui.Util;
import kinderuni.ui.GraphicsComponent;
import kinderuni.ui.components.ButtonComponent;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Georg Plaz.
 */
public class Main {
    static Random random = new Random();
    public static void main(String... args) throws InterruptedException, FileNotFoundException {
        final DesktopSystem system = new DesktopSystem();

        final Screen screen = system.getScreen();
        ButtonComponent playButton = screen.createButton("spielen!");
        ButtonComponent overviewButton = screen.createButton("level übersicht");
        ContainerComponent container = screen.createSystemContainer();
        playButton.addListener(new ButtonComponent.OnClickListener() {
            @Override
            public void onClick() {
              Util.pushLevelsToScreen(system, screen);
            }
        });
        overviewButton.addListener(new ButtonComponent.OnClickListener() {
            @Override
            public void onClick() {
                screen.push(screen.createLevelOverview(system.getSettings().getLevelSettings().values()));
            }
        });
        container.add(playButton);
        container.add(overviewButton);
        screen.push(container);
    }

}
