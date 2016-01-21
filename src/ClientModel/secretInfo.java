package ClientModel;


import clientModel.SecretQuestion;

import java.io.Serializable;

/**
 * Created by Didoy on 9/23/2015.
 */
public class secretInfo implements Serializable {


    private String secretQuestion1;
    private String secretQuestion2;
    private String secretQuestion3;


    public  secretInfo(){

        this.secretQuestion1 = SecretQuestion.PET.toString();
        this.secretQuestion2 = SecretQuestion.TVSHOW.toString();
        this.secretQuestion3 = SecretQuestion.BOOK.toString();

        }

    public String getSecretQuestion1() {
        return secretQuestion1;
    }

    public String getSecretQuestion2() {
        return secretQuestion2;
    }

    public String getSecretQuestion3() {
        return secretQuestion3;
    }
}
