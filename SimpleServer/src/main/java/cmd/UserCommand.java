package cmd;

import entity.PlayerEntity;

/**
 * Created by zyl on 2017/7/7.
 */
public class UserCommand
{
    public PlayerEntity playerEntity;

    public UserCommand(PlayerEntity playerEntity)
    {
        this.playerEntity = playerEntity;
    }
}
