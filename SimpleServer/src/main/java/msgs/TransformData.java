package msgs;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import org.jbox2d.common.Vec3;

/**
 * Created by zyl on 2017/7/7.
 */
public class TransformData extends Vec3 implements MySerializable<TransformData>
{

    public TransformData(float x, float y, float z)
    {
        super(x, y, z);
    }

    @Override
    public ByteBuf toByteBuf()
    {
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.buffer(2);
        buf.writeFloat(this.x);
        buf.writeFloat(this.y);
        buf.writeFloat(this.z);
        return buf;
    }

    @Override
    public TransformData toObject(ByteBuf byteBuf)
    {
        this.x = byteBuf.readFloat();
        this.y = byteBuf.readFloat();
        this.z = byteBuf.readFloat();

        return this;
    }
}