package cmd;

import entity.PlayerEntity;
import msgs.MoveData;

/**
 * Created by zyl on 2017/7/7.
 */
public class UserMoveCommand extends UserCommand
{
    public MoveData moveCmd;

    public UserMoveCommand(PlayerEntity entity, MoveData moveCmd)
    {
        super(entity);
        this.moveCmd = moveCmd;
    }
}
