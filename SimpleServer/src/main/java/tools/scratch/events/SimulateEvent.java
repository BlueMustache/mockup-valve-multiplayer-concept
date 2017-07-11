package scratch.events;

public class SimulateEvent
{
    private int sourceId;
    private int targetId;
    private ChannelType channelType;
    private Object[] argsList;

    public SimulateEvent(int sourceId, int targetId, ChannelType channelType, Object[] argsList)
    {
        this.sourceId = sourceId;
        this.targetId = targetId;
        this.channelType = channelType;
        this.argsList = argsList;
    }

    public Object[] getArgsList()
    {
        return argsList;
    }

    public ChannelType getChannelType()
    {
        return channelType;
    }

    public int getTargetId()
    {
        return targetId;
    }

    public int getSourceId()
    {
        return sourceId;
    }

}
