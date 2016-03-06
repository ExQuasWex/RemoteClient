package View.AdminGUI.Listeners;


import AdminModel.Enum.AccountApproveStatus;
import AdminModel.Enum.AccountStatus;

/**
 * Created by Didoy on 1/2/2016.
 */
public interface TableAccountListener {

    public boolean updateAccountStatus(int id, AccountStatus status);
    public boolean approveAccount(int id, AccountApproveStatus status);

}
