package world;

import com.sun.org.apache.xpath.internal.SourceTree;
import entity.PlayerEntity;
import entity.PlayerSnapShot;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import manager.GlobalInputs;
import cmd.UserCommand;
import cmd.UserMoveCommand;
import msgs.TransformData;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import utility.PackageWrapperUtility;

/**
 * Created by zyl on 2017/6/30.
 */
public class World
{
    public static void Stop(){}
    public static int loopCounter ;
    public static boolean IsStopped(){
        return true;
    }

    public void Update(float deltaTime)
    {
        UserCommand cmd = GlobalInputs.cmds.poll();

        if(cmd != null){
           if(cmd instanceof UserMoveCommand){
               UserMoveCommand _pmoveCmd = ((UserMoveCommand)cmd);
               Vec2 v = _pmoveCmd.playerEntity.getTransform().p;
               Vec2 vecDirection = new Vec2(_pmoveCmd.moveCmd.x, _pmoveCmd.moveCmd.y);
               Vec2 vec = vecDirection.mul(PlayerEntity.speed * (deltaTime / 1000));
               Vec2 vecNew = v.add(vec);
               System.out.println("new Position:" + vecNew.x + ":" + vecNew.y);
               _pmoveCmd.playerEntity.setTransform(new Transform(vecNew, _pmoveCmd.playerEntity.getTransform().q)); // new position;

               //buffer player 20 snapshots;
               _pmoveCmd.playerEntity.insertSnapShots(new PlayerSnapShot(System.currentTimeMillis(), _pmoveCmd.playerEntity.getTransform()));

               //send snapshots back to client;
               Transform transform = _pmoveCmd.playerEntity.getTransform();
               ByteBuf buf =Unpooled.buffer(20);
               buf.writeInt(20);
               buf.writeInt(2);
               buf.writeFloat(transform.p.x);
               buf.writeFloat(transform.p.y);
               buf.writeFloat(0);

               _pmoveCmd.playerEntity.getConnectionHandle()
                   .sendToClient(buf
//                       PackageWrapperUtility.create()
//                       .writeInt(2)
//                       .writeFloat(transform.p.x)
//                       .writeFloat(transform.p.y)
//                       .writeFloat(0).build()
//
                   );
           }
        }
    }

    public Transform transformEnity = new Transform();
    public void initialize(){

    }

    public static void StopNow()
    {
    }

    //save and kill all players
    public void KillAll(){

    }

    public void UpdateSessions(){

    }
}
