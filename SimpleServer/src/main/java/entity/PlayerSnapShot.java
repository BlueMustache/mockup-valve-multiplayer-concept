package entity;

import org.jbox2d.common.Transform;

/**
 * Created by zyl on 2017/7/7.
 */
public class PlayerSnapShot
{
    public final long timestamp;

    public final Transform transform;

    public PlayerSnapShot(long timestamp, Transform transform)
    {
        this.timestamp = timestamp;
        this.transform = new Transform(transform.p, transform.q);
    }
}
