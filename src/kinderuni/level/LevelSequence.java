package kinderuni.level;

import kinderuni.ui.Info;
import kinderuni.gameLogic.objects.Player;
import kinderuni.level.builder.LevelBuilder;
import kinderuni.settings.levelSettings.LevelSettings;
import kinderuni.ui.graphics.Paintable;
import kinderuni.ui.graphics.Painter;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Georg Plaz.
 */
public class LevelSequence implements Paintable{
    private Thread thread;
    private List<LevelSettings> levels;
    private Player player;
    private LevelBuilder builder;
    private List<FinishedListener> listeners = new LinkedList<>();
    private Level currentLevel;
    private int timer = 0;
    private long timeDelta;

    public LevelSequence(Collection<LevelSettings> levels, Player player, LevelBuilder builder) {
        this.levels = new LinkedList<>(levels);
        this.player = player;
        this.builder = builder;
    }

    public LevelSequence(Player player) {
        levels = new LinkedList<>();
        this.player = player;
    }

    public void add(LevelSettings level){
        levels.add(level);
    }

    public void start(){
        thread = new Thread(){
            @Override
            public void run() {
                try {
                    OUTER:
                    for (LevelSettings levelSettings : levels) {
                        Level level = builder.buildLevel(levelSettings);
                        System.out.println("starting level "+level.getName());
                        currentLevel = level;
                        player.stop();
                        player.unStick();
                        level.put(player);
//                        level.start();
                        while (true) {
                            level.update(timer++);
                            Thread.sleep(10);
                            if (level.getGameState() == Level.State.WON) {
                                System.out.println("won!");
                                break;
                            } else if (level.getGameState() == Level.State.LOST) {
                                System.out.println("lost!");
//                                level.stop();
                                break OUTER;
                            }
                        }
//                        levelComponent.remove(level);
//                        level.stop();
                        timeDelta+=level.getTime();
//                        currentLevel = null;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LevelSequence.this.stop();
            }
        };
        thread.start();
    }

    public void add(FinishedListener listener){
        listeners.add(listener);
    }

    public void stop(){
        thread.interrupt();
        for(FinishedListener listener : listeners){
            listener.finished();
        }
    }

    @Override
    public void paint(Painter painter) {
        currentLevel.paint(painter);
    }

    @Override
    public List<Info> getInfos() {
        return currentLevel.getInfos();
    }

    @Override
    public long getTime() {
        if(currentLevel==null){
            return timeDelta;
        }else {
            return currentLevel.getTime() + timeDelta;
        }
    }

    @Override
    public boolean tracksTime() {
        return true;
    }

    public interface FinishedListener{
        public void finished();
    }
}
