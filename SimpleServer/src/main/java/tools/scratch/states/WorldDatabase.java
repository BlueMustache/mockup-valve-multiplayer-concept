package scratch.states;

/**
 * Created by zyl on 2017/6/30.
 */
public class WorldDatabase extends Thread
{
    public static WorldDatabase getInstance(){
        return Singleton.INSTANCE.getInstance();
    }

    public enum Singleton{
        INSTANCE;
        private WorldDatabase instance;
        private Singleton(){
            instance = new WorldDatabase();
        }
        public WorldDatabase getInstance(){
            return instance;
        }
    }

    public void shutdownGracefully(){

    }
}
