package ClientPreference;

import java.util.prefs.Preferences;

/**
 * Created by reiner on 3/8/2016.
 */
public class ClientPreference {

    private static Preferences pref = createPreference();
    private static String IPKEY = "serverip";
    private  static  String ip;

    private static String dbKeyPath = "dbpath";
    private static String path ;




    // get ip address from preference
    public static String getIpPreference(){

        try {
            ip = pref.get(IPKEY, ip);
        }catch (NullPointerException ex){
            ip = "";
            ex.printStackTrace();
        }
        return ip;
    }

    public static Preferences createPreference(){
        pref =  Preferences.userRoot().node(String.valueOf(ClientPreference.class));
        return pref;
    }

    // save ip address from preference
    public static void saveIpPreference(String ipAdd){
        ip = ipAdd;
        pref.put(IPKEY, ip);
    }

    public static void saveDBPath(String selectionPath){
        path = selectionPath;
        pref.put(dbKeyPath, path);
    }

    public static String getDbPath(){
        return pref.get(dbKeyPath, path);
    }




}
