package utility;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;

/**
 * Created by zyl on 2017/7/7.
 */
public class PackageWrapperUtility
{

    public static WrapperMessage create(){
        return new WrapperMessage();
    }

}
