package scratch.states;

import scratch.collections.ActionRequestQueue;

/**
 * Created by zyl on 2017/6/29.
 */
public class UserControlState extends ControlState
{
    ActionRequestQueue actionRequestQueue;

    public void ClearActinRequestQueue(){}

    public void ClearLastActionRequest(){}

    public void QueueActionRequest(){}

    @Override
    public void RequestAction(){}

    @Override
    public void PerformAction(){}



}
