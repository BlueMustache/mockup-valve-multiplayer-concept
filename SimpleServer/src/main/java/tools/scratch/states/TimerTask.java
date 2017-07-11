package scratch.states;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * Created by zyl on 2017/6/30.
 */
public class TimerTask
{
    private HashedWheelTimer wheelTimer = new HashedWheelTimer();

    public void executeTask(){
        wheelTimer.start();
    }

    public static void main(String[] args)
    {
        HashedWheelTimer wheelTimer = new HashedWheelTimer();
        wheelTimer.start();
        wheelTimer.newTimeout(new io.netty.util.TimerTask()
        {
            @Override
            public void run(Timeout timeout) throws Exception
            {
                System.out.println("Execute");
            }
        }, 5, TimeUnit.SECONDS);
    }
}
