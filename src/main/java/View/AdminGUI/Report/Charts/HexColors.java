package View.AdminGUI.Report.Charts;

/**
 * Created by Didoy on 2/24/2016.
 */
public enum HexColors {

    red {
        public String toString() {
            return "-fx-background-color: #9e3030";
        }
    },
    blue {
        public String toString() {
            return "-fx-background-color: #156fa9";
        }
    },
    green {
        public String toString() {
            return "-fx-background-color: #00703c";
        }
    },
    yellow {
        public String toString() {
            return "-fx-background-color: #ffd000";
        }
    },
    orange {
        public String toString() {
            return "-fx-background-color: #f6a11e";
        }
    }

}
