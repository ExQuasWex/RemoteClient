package View.AdminGUI.Work;

/**
 * Created by reiner on 3/4/2016.
 */
public class RevokeHistory {

    boolean isRevoke;
    String revokeDesck;
    String solution;

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public RevokeHistory(boolean isRevoke, String solution, String revokeDesck) {
        this.isRevoke = isRevoke;
        this.revokeDesck = revokeDesck;
        this.solution = solution;

    }

    public boolean isRevoke() {
        return isRevoke;
    }

    public void setRevoke(boolean isRevoke) {
        this.isRevoke = isRevoke;
    }

    public String getRevokeDesck() {
        return revokeDesck;
    }

    public void setRevokeDesck(String revokeDesck) {
        this.revokeDesck = revokeDesck;
    }
}
