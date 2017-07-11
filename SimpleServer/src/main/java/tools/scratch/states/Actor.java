package scratch.states;

/**
 * Created by zyl on 2017/6/29.
 */
public class Actor
{

    private StateManager currentStateManager;

    public void SetActionState()
    {
        currentStateManager.SetActionState();
    }

    public void SetMovementState()
    {
        currentStateManager.SetMovemenState();
    }

    public void SetPostureState()
    {
        currentStateManager.SetPostureState();
    }

}
