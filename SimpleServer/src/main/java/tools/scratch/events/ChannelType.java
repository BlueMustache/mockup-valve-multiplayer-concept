package scratch.events;

/**
 * Created by zyl on 2017/6/29.
 */
public enum ChannelType{
    Internal(-1);
    private  int value;

    ChannelType(int value){
        this.value = value;
    }

    public int GetType(){
        return value;
    }
}
