package codec;

import msgs.MoveData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zyl on 2017/7/7.
 */
public class MsgDecoder extends ByteToMessageDecoder
{

    private static Logger logger = LoggerFactory.getLogger(MsgDecoder.class);
    private void clear(){
    }
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception
    {
        if(in.readableBytes() < 4){
            return;
        }

        int totalLength = in.getInt(in.readerIndex());

        if(in.readableBytes() < totalLength){
            return;
        }
        else{
            in.readInt();
        }
        int type = in.readInt();
        switch (type){
            case 1:
                MoveData v = new MoveData().toObject(in);
                System.out.println("x:" + v.x + "y:" + v.y);
                out.add(v);
                break;
            default:
                logger.warn("invalid message type {}", type);
                in.readBytes(totalLength - 8); //read and throw the invalid message;
                break;
        }

    }
}
