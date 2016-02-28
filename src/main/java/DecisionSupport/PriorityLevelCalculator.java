package DecisionSupport;

import ListModels.ChildrenSchoolCategory;
import PriorityModels.PriorityLevel;
import Remote.Method.FamilyModel.FamilyPoverty;

/**
 * Created by reiner on 2/28/2016.
 */
public class PriorityLevelCalculator {

    public static PriorityLevel getHomePriorityLevel(FamilyPoverty familyPoverty, int children){
        int factors = 0;
        PriorityLevel priorityLevel = PriorityLevel.Low;

        if (children > 1 && children <=2) {
            factors += 1;
        }else if (children > 3){
            factors += 2;
        }

        String ownerShip = familyPoverty.getOwnership();
        ChildrenSchoolCategory childrenInSchool = familyPoverty.getChildreninSchool();

        if (ownerShip.equals("Rental")){
            factors += 1;
        }
        else if (ownerShip.equals("Sharer")){
            factors += 1;
        }
        else if (ownerShip.equals("Informal Settler")){
            factors += 2;
        }

        if (childrenInSchool != null){

            if (childrenInSchool == ChildrenSchoolCategory.VOCATIONAL){
                factors += 1;
            }
            else if (childrenInSchool == ChildrenSchoolCategory.SOME){
                factors += 1;
            }
            else if (childrenInSchool == ChildrenSchoolCategory.NONE){
                factors += 2;
            }

            priorityLevel = calculatePriority(factors);

        }

        return priorityLevel;

    }

    public static PriorityLevel getJobPriorityLevel(FamilyPoverty familyPoverty, int children){
        int factors = 0;
        PriorityLevel priorityLevel = PriorityLevel.Low;

        String occupancy = familyPoverty.getOccupancy();
        String underemployed = familyPoverty.getIsunderEmployed();
        String otherincome = familyPoverty.getHasotherIncome();
        String threshold = familyPoverty.getIsbelow8k();

        if (children > 1 && children <=2) {
            factors += 1;
        }else if (children > 3){
            factors += 2;
        }


        if (occupancy.equals("Unemployed") ) {
            factors += 2;
        }
        if (underemployed.equals("Yes")){
            factors += 1;
        }
        if (otherincome.equals("No")){
            factors += 2;
        }
        if (threshold.equals("No")){
            factors += 1;
        }

        priorityLevel = calculatePriority(factors);

        return priorityLevel;
    }


    public static int getFactorHomePriorityLevel(FamilyPoverty familyPoverty, int children){
        int factors = 0;

        if (children > 1 && children <=2) {
            factors += 1;
        }else if (children > 3){
            factors += 2;
        }

        String ownerShip = familyPoverty.getOwnership();
        ChildrenSchoolCategory childrenInSchool = familyPoverty.getChildreninSchool();

        if (ownerShip.equals("Rental")){
            factors += 1;
        }
        else if (ownerShip.equals("Sharer")){
            factors += 1;
        }
        else if (ownerShip.equals("Informal Settler")){
            factors += 2;
        }

        if (childrenInSchool != null){

            if (childrenInSchool == ChildrenSchoolCategory.VOCATIONAL){
                factors += 1;
            }
            else if (childrenInSchool == ChildrenSchoolCategory.SOME){
                factors += 1;
            }
            else if (childrenInSchool == ChildrenSchoolCategory.NONE){
                factors += 2;
            }


        }
        return factors;
    }

    public static int getFactorJobPriorityLevel(FamilyPoverty familyPoverty, int children){
        int factors = 0;

        String occupancy = familyPoverty.getOccupancy();
        String underemployed = familyPoverty.getIsunderEmployed();
        String otherincome = familyPoverty.getHasotherIncome();
        String threshold = familyPoverty.getIsbelow8k();

        if (children > 1 && children <=2) {
            factors += 1;
        }else if (children > 3){
            factors += 2;
        }


        if (occupancy.equals("Unemployed") ) {
            factors += 2;
        }
        if (underemployed.equals("Yes")){
            factors += 1;
        }
        if (otherincome.equals("No")){
            factors += 2;
        }
        if (threshold.equals("No")){
            factors += 1;
        }

        return factors;
    }


    private  static PriorityLevel calculatePriority(int factors){
        PriorityLevel priorityLevel = PriorityLevel.Low;


        if (factors <= 2 ){
            priorityLevel = PriorityLevel.Low;
        }
        else if (factors > 2 && factors <= 3){
            priorityLevel = PriorityLevel.Medium;
        }
        else if (factors > 3 && factors <= 4){
            priorityLevel = PriorityLevel.High;
        }
        else if (factors >= 4 && factors <= 5){
            priorityLevel = PriorityLevel.High;
        }else if (factors > 5){
            priorityLevel = PriorityLevel.Critical;
        }
        return priorityLevel;
    }
}
