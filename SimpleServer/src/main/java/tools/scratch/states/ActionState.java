package scratch.states;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by zyl on 2017/6/29.
 */
public class ActionState extends SimulationState
{

    private long startTime;
    private long durationTime;


    @Override
    public void CanTransition(){
        throw new NotImplementedException();
    }

    @Override
    public void Transition(){
        throw new NotImplementedException();
    }


    public long getStartTime()
    {
        return startTime;
    }

    public void setStartTime(long startTime)
    {
        this.startTime = startTime;
    }

    public long getDurationTime()
    {
        return durationTime;
    }

    public void setDurationTime(long durationTime)
    {
        this.durationTime = durationTime;
    }


}
