package scratch;

import scratch.states.WorldDatabase;

/**
 * Created by zyl on 2017/6/30.
 */
public class WorldRunnable
{

    public enum ServiceStatus
    {
        Stop,
        Start,
        Pause,
    }

    public static ServiceStatus serviceStatus;
    public final static int WORLD_SLEEP_CONST = 60; //50ms

    public static void run()
    {
        WorldDatabase.getInstance().start();
        long realCurrentTime = 0;
        long realPrevTime = 0;
        World sWorld = new World();
        int prevSleepTime = 0;

        while (!World.IsStopped())
        {
            World.loopCounter++;
            realCurrentTime = System.currentTimeMillis();
            long diff = realCurrentTime - realPrevTime;
            sWorld.Update(diff);
            realPrevTime = System.currentTimeMillis();

            if (realPrevTime -  realCurrentTime < WORLD_SLEEP_CONST )
            {
                try
                {
                    Thread.sleep(WORLD_SLEEP_CONST - (realPrevTime - realCurrentTime));
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            } else
            {
                prevSleepTime = 0;
            }
            if (serviceStatus == ServiceStatus.Stop)
            {
                World.StopNow();
            }
            while (serviceStatus == ServiceStatus.Pause)
            {
                try
                {
                    Thread.sleep(100);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            sWorld .KillAll();                // save and kick all players
            sWorld .UpdateSessions( );      // real players unload required UpdateSessi
//            sBattleGroundMgr.DeleteAllBattleGrounds();
//            47:
//            48:     sWorldSocketMgr->StopNetwork();
//            49:
//            50:     sMapMgr.UnloadAll();

            WorldDatabase.getInstance().shutdownGracefully();       // free mySQL thread resources
        }
    }

}
