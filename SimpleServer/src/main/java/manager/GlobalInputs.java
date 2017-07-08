package manager;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import cmd.UserCommand;

/**
 * Created by zyl on 2017/7/7.
 */
public class GlobalInputs
{
    public static Queue<UserCommand> cmds = new LinkedBlockingQueue<>();

}
