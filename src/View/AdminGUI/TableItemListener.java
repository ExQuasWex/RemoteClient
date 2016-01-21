package View.AdminGUI;


import AdminModel.RequestAccounts;

/**
 * Created by Didoy on 1/2/2016.
 */
public interface TableItemListener {

    public boolean Approve(RequestAccounts ra);
    public boolean ApproveAdmin(RequestAccounts ra);
    public boolean Reject(RequestAccounts ra);


}
