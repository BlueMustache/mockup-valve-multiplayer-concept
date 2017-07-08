import entity.ConnectionComponent;
import entity.PlayerEntity;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import manager.GlobalInputs;
import manager.GlobalPlayers;
import msgs.MoveData;
import cmd.UserMoveCommand;
import org.jbox2d.common.Transform;

/**
 * Created by zyl on 2017/7/7.
 */
public class GameLogic extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        PlayerEntity playerEntity = new PlayerEntity(new ConnectionComponent(ctx.channel()), new Transform());
        GlobalPlayers.players.put(ctx.channel(), playerEntity);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof MoveData){
            GlobalInputs.cmds.offer(new UserMoveCommand(GlobalPlayers.players.get(ctx.channel()), (MoveData)msg));
        }
    }
}
