package codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import msgs.*;
/**
 * Created by zyl on 2017/7/7.
 */
public class MsgEncoder extends MessageToByteEncoder
{
    private static final Logger logger = LoggerFactory.getLogger(MsgEncoder.class);
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception
    {
        if(msg == null){
            logger.warn("encode null message");
            return ;
        }

        if(msg instanceof MessagePack)
        {
            out.writeInt(((MessagePack) msg).length);
            out.writeInt(((MessagePack) msg).type);

            MySerializable ve = (MySerializable)(((MessagePack) msg).content);
            out.writeBytes(ve.toByteBuf());
        }
        else if(msg instanceof ByteBuf){
            out.writeBytes((ByteBuf)msg);
        }
    }
}
