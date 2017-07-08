package entity;

import org.jbox2d.common.Transform;

/**
 * Created by zyl on 2017/7/7.
 */
public class PlayerEntity
{
    PlayerSnapShot[] snapShots = new PlayerSnapShot[20];
    int index = 0;

    private ConnectionComponent connectionHandle;

    public static int speed = 5; // FIXME: 2017/7/7  hack way of set speed

    private Transform transform;

    public PlayerEntity(ConnectionComponent connectionHandle, Transform transform){
        this.connectionHandle = connectionHandle;
        this.transform = transform;
    }

    public ConnectionComponent getConnectionHandle()
    {
        return connectionHandle;
    }

    public Transform getTransform()
    {
        return transform;
    }

    public void setTransform(Transform transform)
    {
        this.transform = transform;
    }

    public void insertSnapShots(PlayerSnapShot snapShot){
        if(index < 20){
            snapShots[index] = snapShot;
            index++;
            return;
        }
        for(int i = 0; i < index -1; i++){
            snapShots[i] = snapShots[i + 1];
        }
        snapShots[index - 1] = snapShot;
    }
}
