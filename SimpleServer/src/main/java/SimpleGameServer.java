import codec.MsgDecoder;
import codec.MsgEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import world.GameMainLoop;

/**
 * Created by zyl on 2017/7/7.
 */
public class SimpleGameServer
{

    static Logger logger = LoggerFactory.getLogger(SimpleGameServer.class);

    public static void main(String[] args) throws Exception
    {
        ServerBootstrap serverBootStrap;
        NioEventLoopGroup bossGroup = null;
        NioEventLoopGroup workerGroup = null;
        try{
            bossGroup = new NioEventLoopGroup();
            workerGroup = new NioEventLoopGroup();
            serverBootStrap = new ServerBootstrap();
            serverBootStrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>()
                {
                    @Override
                    protected void initChannel(SocketChannel socketChannel){
                        socketChannel.pipeline().addLast("decoder", new MsgDecoder());
                        socketChannel.pipeline().addLast("encoder", new MsgEncoder());
                        socketChannel.pipeline().addLast("game-logic", new GameLogic());
                    }

                }).option(ChannelOption.SO_BACKLOG, 65535)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = serverBootStrap.bind(8077).sync();
            future.awaitUninterruptibly();

            GameMainLoop gameLoop = new GameMainLoop();

            new Thread(
                ()->{
                    System.out.println("Game Loop start");
                    gameLoop.runLoop();
                }
            ).start();

            System.in.read();

            // 监听关闭事件
            future.channel().closeFuture().addListener(new ChannelFutureListener()
            {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception
                {
                    // 抛出监听停止事件，注意该事件的执行线程
                    logger.info("线程" + Thread.currentThread().getName() + "执行TCP监听关闭事件: " + 7788);
                    //onStopListen();
                }
            });
        }catch (Exception e){

        }finally
        {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

    }

}
