package entity;

import io.netty.channel.Channel;

/**
 * Created by zyl on 2017/7/7.
 */
public class ConnectionComponent
{
    private final Channel channe;

    public ConnectionComponent(Channel channel){
        this.channe = channel;
    }

    public void sendToClient(Object message){
        this.channe.writeAndFlush(message);
    }
}
