package codec;

/**
 * Created by zyl on 2017/7/7.
 */
public class MessagePack<T>
{

    public MessagePack(int length, byte type, T content)
    {
        this.length = length;
        this.type = type;
        this.content = content;
    }

    public int length;

    public byte type; // 1:move operation

    public T content;
}
