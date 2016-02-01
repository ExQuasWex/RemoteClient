package View.Login.Listeners;

/**
 * Created by Didoy on 1/28/2016.
 */
public interface LoginPaneListener {

    public void showAdminWindow();
    public void showClientWindow();
    public void closeLoginStage();
    public void exitApplication();
    public void  setLoginDisconnected();
    public void  setLoginConnected();


}
