package DecisionSupport;

import PriorityModels.PriorityLevel;
import Remote.Method.FamilyModel.FamilyPoverty;

/**
 * Created by reiner on 2/28/2016.
 */
public class Prioritizer {


   public static FamilyPoverty addPriorityLevel(FamilyPoverty familyPoverty){

       String otherincome = familyPoverty.getHasotherIncome();
       String threshold = familyPoverty.getIsbelow8k();
       String childrenInSchool = familyPoverty.getChildreninSchool();


       return null;

   }

    private PriorityLevel addHomePriorityLevel(FamilyPoverty familyPoverty){
        return null;

    }

    private PriorityLevel addJobPriorityLevel(FamilyPoverty familyPoverty){
        PriorityLevel level= null;
        String occupancy = familyPoverty.getOccupancy();
        String underemployed = familyPoverty.getIsunderEmployed();
        String otherincome = familyPoverty.getHasotherIncome();
        String threshold = familyPoverty.getIsbelow8k();
        String childrenInSchool = familyPoverty.getChildreninSchool();


            return null;

    }
}
