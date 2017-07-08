package manager;

import entity.PlayerEntity;
import io.netty.channel.Channel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zyl on 2017/7/7.
 */
public class GlobalPlayers
{
    public static Map<Channel, PlayerEntity> players = new ConcurrentHashMap<>();
}
