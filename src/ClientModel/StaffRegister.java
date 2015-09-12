package ClientModel;

/**
 * Created by Didoy on 9/8/2015.
 */
public class StaffRegister {


    private  String name,username, contact, address,gender, password, comfirmpass;

    public  StaffRegister(String name,String Username, String contact, String address,
                          String gender, String password, String cpass){

        this.name = name;
        this.username = Username;
        this.contact = contact;
        this.address = address;
        this.gender = gender;
        this.password = password;
        this.comfirmpass = cpass;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getComfirmpass() {
        return comfirmpass;
    }

    public void setComfirmpass(String comfirmpass) {
        this.comfirmpass = comfirmpass;
    }
}
