package View.AdminGUI.Report.Enums;

/**
 * Created by Didoy on 1/13/2016.
 */
public enum ReportCategoryMethod {

    OVERVIEW {
        public String toString(){
            return "Overview";
        }
    },
    COMPARE_OVERVIEW {
        public String toString(){
            return "Compare Overview";
        }
    },
    COMPARE_SPECIFIC{
        public String toString(){
            return "Compare Specific";
        }
    },
    SPECIFIC_OVERVIEW{
        public String toString(){
            return "Specific Overview";
        }
    },
    SPECIFIC{
        public String toString(){
            return "Specific";
        }
    }


}
