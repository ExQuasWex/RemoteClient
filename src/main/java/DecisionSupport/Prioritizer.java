package DecisionSupport;

import ListModels.ChildrenSchoolCategory;
import PriorityModels.PriorityLevel;
import PriorityModels.PriorityType;
import Remote.Method.FamilyModel.FamilyPoverty;

import java.util.ArrayList;

/**
 * Created by reiner on 2/28/2016.
 */
public class Prioritizer {

    // optional data underemployed, childreninSchool

   public static FamilyPoverty addPriorityLevel(FamilyPoverty familyPoverty, int children){
       PriorityLevel priorityLevel = PriorityLevel.Low;

       PriorityLevel jobPriorityLevel = PriorityLevelCalculator.getJobPriorityLevel(familyPoverty, children);
       PriorityLevel homePriorityLevel = PriorityLevelCalculator.getHomePriorityLevel(familyPoverty, children);



       if (jobPriorityLevel == PriorityLevel.Low && homePriorityLevel ==  PriorityLevel.Low ){
            priorityLevel = PriorityLevel.Low;
        }
        else if (jobPriorityLevel == PriorityLevel.Medium && homePriorityLevel ==  PriorityLevel.Low ){
            priorityLevel = PriorityLevel.Medium;
        }
        else if (jobPriorityLevel == PriorityLevel.High && homePriorityLevel ==  PriorityLevel.Low ){
            priorityLevel = PriorityLevel.Medium;
        }
        else if (jobPriorityLevel == PriorityLevel.Critical && homePriorityLevel ==  PriorityLevel.Low ){
            priorityLevel = PriorityLevel.High;
        }

        //  Home level Medium

        else if (jobPriorityLevel == PriorityLevel.Medium && homePriorityLevel ==  PriorityLevel.Medium ){
            priorityLevel = PriorityLevel.High;
        }
        else if (jobPriorityLevel == PriorityLevel.High && homePriorityLevel ==  PriorityLevel.Medium ){
            priorityLevel = PriorityLevel.High;
        }
        else if (jobPriorityLevel == PriorityLevel.Critical && homePriorityLevel ==  PriorityLevel.Medium ){
            priorityLevel = PriorityLevel.High;
        }


       // Home level High

        else if (jobPriorityLevel == PriorityLevel.High && homePriorityLevel ==  PriorityLevel.High ){
            priorityLevel = PriorityLevel.Critical;
        }
        else if (jobPriorityLevel == PriorityLevel.Critical && homePriorityLevel ==  PriorityLevel.High ){
            priorityLevel = PriorityLevel.Critical;
        }

       // Home level critical

        else if (jobPriorityLevel == PriorityLevel.Critical && homePriorityLevel ==  PriorityLevel.Critical ){
            priorityLevel = PriorityLevel.Critical;
        }

       // Job level Low
        else if (homePriorityLevel == PriorityLevel.Low && jobPriorityLevel == PriorityLevel.Low  ){
            priorityLevel = PriorityLevel.Low;
        }
       else if (homePriorityLevel == PriorityLevel.Medium && jobPriorityLevel == PriorityLevel.Low  ){
           priorityLevel = PriorityLevel.Medium;
       }
       else if (homePriorityLevel == PriorityLevel.High && jobPriorityLevel == PriorityLevel.Low  ){
           priorityLevel = PriorityLevel.Medium;
       }
       else if (homePriorityLevel == PriorityLevel.Critical && jobPriorityLevel == PriorityLevel.Low  ){
           priorityLevel = PriorityLevel.High;
       }

       // job level medium

        else if (homePriorityLevel == PriorityLevel.Medium && jobPriorityLevel == PriorityLevel.Medium  ){
            priorityLevel = PriorityLevel.Medium;
        }
        else if (homePriorityLevel == PriorityLevel.High && jobPriorityLevel == PriorityLevel.Medium  ){
            priorityLevel = PriorityLevel.Medium;
        }
        else if (homePriorityLevel == PriorityLevel.Critical && jobPriorityLevel == PriorityLevel.Medium  ){
            priorityLevel = PriorityLevel.High;
        }

        // job level High
        else if (homePriorityLevel == PriorityLevel.High && jobPriorityLevel == PriorityLevel.High  ){
            priorityLevel = PriorityLevel.Critical;
        }
        else if (homePriorityLevel == PriorityLevel.Critical && jobPriorityLevel == PriorityLevel.High  ){
            priorityLevel = PriorityLevel.Critical;
        }

        // job level critical
        else if (homePriorityLevel == PriorityLevel.Critical && jobPriorityLevel == PriorityLevel.Critical  ){
            priorityLevel = PriorityLevel.Critical;
        }

       PriorityType priorityType = selectPriority(homePriorityLevel, jobPriorityLevel, familyPoverty, children);

       // set Priority levels
       familyPoverty.setJobPriorityLevel(jobPriorityLevel);
       familyPoverty.setHomePriorityLevel(homePriorityLevel);
       familyPoverty.setPriorityLevel(priorityLevel);
       familyPoverty.setPriorityType(priorityType);

       return familyPoverty;

   }

    private static PriorityType selectPriority(PriorityLevel homeLevel, PriorityLevel jobLevel,
                                               FamilyPoverty familyPoverty, int children){
        PriorityType priorityType = PriorityType.HOME;

        if (jobLevel == PriorityLevel.Low && homeLevel ==  PriorityLevel.Low ){

            priorityType = getPriorityTypeBaseOnPoverty(familyPoverty, children );
        }
        else if (jobLevel == PriorityLevel.Medium && homeLevel ==  PriorityLevel.Low ){
            priorityType = PriorityType.JOB;
        }
        else if (jobLevel == PriorityLevel.High && homeLevel ==  PriorityLevel.Low ){
            priorityType = PriorityType.JOB;
        }
        else if (jobLevel == PriorityLevel.Critical && homeLevel ==  PriorityLevel.Low ){
            priorityType = PriorityType.JOB;
        }

        //  Home level Medium

        else if (jobLevel == PriorityLevel.Medium && homeLevel ==  PriorityLevel.Medium ){
            priorityType = getPriorityTypeBaseOnPoverty(familyPoverty, children );
        }
        else if (jobLevel == PriorityLevel.High && homeLevel ==  PriorityLevel.Medium ){
            priorityType = PriorityType.JOB;
        }
        else if (jobLevel == PriorityLevel.Critical && homeLevel ==  PriorityLevel.Medium ){
            priorityType = PriorityType.JOB;
        }

        // Home level High

        else if (jobLevel == PriorityLevel.High && homeLevel ==  PriorityLevel.High ){
            priorityType = getPriorityTypeBaseOnPoverty(familyPoverty, children );
        }
        else if (jobLevel == PriorityLevel.Critical && homeLevel ==  PriorityLevel.High ){
            priorityType = PriorityType.JOB;
        }

        // Home level critical

        else if (jobLevel == PriorityLevel.Critical && homeLevel ==  PriorityLevel.Critical ){
            priorityType = getPriorityTypeBaseOnPoverty(familyPoverty, children );
        }

        // Job level Low
        else if (homeLevel == PriorityLevel.Low && jobLevel == PriorityLevel.Low  ){
            priorityType = getPriorityTypeBaseOnPoverty(familyPoverty, children );

        }
        else if (homeLevel == PriorityLevel.Medium && jobLevel == PriorityLevel.Low  ){
            priorityType = PriorityType.HOME;
        }
        else if (homeLevel == PriorityLevel.High && jobLevel == PriorityLevel.Low  ){
            priorityType = PriorityType.HOME;
        }
        else if (homeLevel == PriorityLevel.Critical && jobLevel == PriorityLevel.Low  ){
            priorityType = PriorityType.HOME;
        }

        // job level medium

        else if (homeLevel == PriorityLevel.Medium && jobLevel == PriorityLevel.Medium  ){
            priorityType = getPriorityTypeBaseOnPoverty(familyPoverty, children );
        }
        else if (homeLevel == PriorityLevel.High && jobLevel == PriorityLevel.Medium  ){
            priorityType = PriorityType.HOME;
        }
        else if (homeLevel == PriorityLevel.Critical && jobLevel == PriorityLevel.Medium  ){
            priorityType = PriorityType.HOME;
        }

        // job level High
        else if (homeLevel == PriorityLevel.High && jobLevel == PriorityLevel.High  ){
            priorityType = PriorityType.HOME;
        }
        else if (homeLevel == PriorityLevel.Critical && jobLevel == PriorityLevel.High  ){
            priorityType = PriorityType.HOME;
        }

        // job level critical
        else if (homeLevel == PriorityLevel.Critical && jobLevel == PriorityLevel.Critical  ){
            priorityType = getPriorityTypeBaseOnPoverty(familyPoverty, children );
        }
        return priorityType;
    }

    private static PriorityType getPriorityTypeBaseOnPoverty(FamilyPoverty familyPoverty, int children){
        PriorityType priorityType;

        int jobFactors = PriorityLevelCalculator.getFactorJobPriorityLevel(familyPoverty, children);
        int homeFactors = PriorityLevelCalculator.getFactorHomePriorityLevel(familyPoverty, children);

        if (jobFactors > homeFactors){
            priorityType = PriorityType.JOB;
        }else if (homeFactors > jobFactors){
            priorityType = PriorityType.HOME;
        }else {
            priorityType = PriorityType.FREE;
        }
        return priorityType;
    }

}
