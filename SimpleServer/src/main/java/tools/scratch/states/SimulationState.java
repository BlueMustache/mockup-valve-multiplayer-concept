package scratch.states;

import java.util.Map;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by zyl on 2017/6/29.
 */
public class SimulationState
{
    private int Id;

    private Map<Integer ,State> transitions;

    public int GetId(){ throw new NotImplementedException();}

    public int AddTransition(){ throw new NotImplementedException();}

    public int RemoveTransition(){ throw new NotImplementedException();}

    public void CanTransition(){}

    public void Transition(){}


}
