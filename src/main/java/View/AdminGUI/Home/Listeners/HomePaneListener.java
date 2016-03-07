package View.AdminGUI.Home.Listeners;

/**
 * Created by reiner on 3/2/2016.
 */
public interface HomePaneListener {

   public  void  viewPeople(String barangayName, String year, String type);
   public  void  viewAllPeople(String barangayName, String year);
   public  void  refresh();
   public void  viewDataByStatus(String barangayName, String date, String status);
   public void showFamilyHistories(String barangayName, String date);

}
