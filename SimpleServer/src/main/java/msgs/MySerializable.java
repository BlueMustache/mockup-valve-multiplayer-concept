package msgs;

import io.netty.buffer.ByteBuf;

/**
 * Created by zyl on 2017/7/7.
 */

public interface MySerializable<T>{
    ByteBuf toByteBuf();
    T toObject(ByteBuf bytes);
}
