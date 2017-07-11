package world;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by zyl on 2017/6/30.
 */
public class GameMainLoop
{

    static long startTimeBase = 0;
    static long tick = 0; //diff between currenTime and timePrev
    static long timePrev = 0;
    static long currentTime = 0;

    public GameMainLoop()
    {
    }


    public  void Start()
    {
        startTimeBase = 0;
        tick = 0;
        timePrev = 0;
        currentTime = 0;
    }

    public  long getMSTime()
    {
        currentTime = System.currentTimeMillis() - startTimeBase;
        return currentTime;
    }

    public  long tick(long prevTime)
    {
        timePrev = prevTime;
        return getMSTime() - timePrev;
    }

//    public static void main(String[] args) throws InterruptedException
//    {
//        System.out.println(t.getNano());
//        Thread.sleep(100);
//        System.out.println(t.get);
//    }

    public  void runLoop() {

        long thisFrameBeginTick = 0;
        long lastFrameBeginTick = 0;
        long thisFrameEndTick = 0;
        long WORLD_SLEEP_CONST = 16777778;
        long sleepTime = 0;
        long lastTime = 0;
        World world = new World();

        while (true)
        {
            thisFrameBeginTick = System.nanoTime();

            //System.out.println("thisFrameBeginTick" +  thisFrameBeginTick*1.0/1000);
            //System.out.println("lastFrameBeginTick" +  lastFrameBeginTick*1.0/1000);

            long deltaTime = thisFrameBeginTick - lastFrameBeginTick; //从上一帧到当前帧用的时间

           // System.out.println("deltaTime" +  deltaTime*1.0/1000000000 + "milliseconds");

            //get server cmd

            world.Update((float)(deltaTime*1.0/1000000)); //上一帧到这一帧的时间

            thisFrameEndTick = System.nanoTime();

            lastFrameBeginTick = thisFrameBeginTick;

            if (thisFrameEndTick - thisFrameBeginTick < WORLD_SLEEP_CONST)
            {
                try
                {
                    sleepTime = WORLD_SLEEP_CONST - (thisFrameEndTick - thisFrameBeginTick);
                    //System.out.println("Sleep" + sleepTime + "microsecods");
                    TimeUnit.NANOSECONDS.sleep(sleepTime);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            } else
            {
                sleepTime = 0;
            }
        }

    }

//    void Update(long deltaTime) throws InterruptedException
//    {
//        Random r = new Random();
//        int value = r.nextInt(90);
//        System.out.println("Run"  + value + "ms" );
//        Thread.sleep(value);
//    }
}
