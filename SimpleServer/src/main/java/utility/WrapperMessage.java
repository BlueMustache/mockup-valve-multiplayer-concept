package utility;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

/**
 * Created by zyl on 2017/7/7.
 */
public class WrapperMessage { //// FIXME: 2017/7/8  有build部分bug，字段错误，不能用
    ByteBuf buf;
    int length;
    int startIndex = 0;

    public WrapperMessage(){
        buf = PooledByteBufAllocator.DEFAULT.buffer(32);
        startIndex = buf.writerIndex();
    }
    public WrapperMessage writeInt(int value){
        buf.writeInt(value);
        length += 4;
        return this;
    }

    public WrapperMessage writeFloat(float value){
        buf.writeFloat(value);
        length+=4;
        return this;
    }

    public ByteBuf build(){
        length += 4;
        buf.markWriterIndex();
        buf.writerIndex(startIndex);
        buf.writeInt(length);
        buf.resetWriterIndex();
        return buf.slice(startIndex, length);
    }

}
