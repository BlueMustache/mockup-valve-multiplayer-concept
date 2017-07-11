package scratch;

import java.util.Random;
import scratch.WorldRunnable.ServiceStatus;
import scratch.states.WorldDatabase;

/**
 * Created by zyl on 2017/6/30.
 */
public class WorldTimer
{

    static long startTimeBase = 0;
    static long tick = 0; //diff between currenTime and timePrev
    static long timePrev = 0;
    static long currentTime = 0;

    public WorldTimer()
    {
    }

    public static void Start()
    {
        startTimeBase = 0;
        tick = 0;
        timePrev = 0;
        currentTime = 0;
    }

    public static long getMSTime()
    {
        currentTime = System.currentTimeMillis() - startTimeBase;
        return currentTime;
    }

    public static long tick(long prevTime)
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

    public static void main(String[] args){
        long realRunTimeStart = 0;
        long realPrevRunTimeBegin = 0;
        long realPrevRunTimeEnd = 0;
        long WORLD_SLEEP_CONST = 60;
        long prevSleepTime = 0;
        while (true)
        {
            realRunTimeStart = System.currentTimeMillis();
            long deltaTime = realRunTimeStart - realPrevRunTimeBegin; //从上一帧到当前帧用的时间
            System.out.println("deltaTime" + deltaTime+ "seconds");
            try
            {
                Update(deltaTime);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            realPrevRunTimeEnd = System.currentTimeMillis();
            realPrevRunTimeBegin = realRunTimeStart;
            if (realPrevRunTimeEnd - realRunTimeStart < WORLD_SLEEP_CONST)
            {
                try
                {
                    prevSleepTime = WORLD_SLEEP_CONST - (realPrevRunTimeEnd - realRunTimeStart);
                    System.out.println("Sleep" + prevSleepTime + "seconds");
                    Thread.sleep(prevSleepTime);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            } else
            {
                prevSleepTime = 0;
            }
        }

    }
    static void Update(long time) throws InterruptedException
    {
        Random r = new Random();
        int value = r.nextInt(90);
        System.out.println("Run"  + value + "ms" );
        Thread.sleep(value);
    }
}
