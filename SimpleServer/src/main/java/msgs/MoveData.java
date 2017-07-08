package msgs;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import org.jbox2d.common.Vec2;

/**
 * Created by zyl on 2017/7/7.
 */

public class MoveData extends Vec2 implements MySerializable<MoveData>
{
    @Override
    public ByteBuf toByteBuf()
    {
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.buffer(2);
        buf.writeFloat(this.x);
        buf.writeFloat(this.y);
        return buf;
    }


    @Override
    public MoveData toObject(ByteBuf byteBuf)
    {
        this.x = byteBuf.readFloat();
        this.y = byteBuf.readFloat();
        return this;
    }
}
