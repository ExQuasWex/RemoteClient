package View.AdminGUI.Report.Charts;

import java.util.*;

/**
 * Created by Didoy on 2/24/2016.
 */
public class ColorRandom {

    private static   ArrayList takencolors = new ArrayList();

    public static String RandomColor(){
        String color = "";



        final ArrayList<HexColors> COLORS = new ArrayList<>(Arrays.asList(HexColors.values()));
        int SIZE  = COLORS.size();

         if (takencolors.size() == SIZE){
            takencolors.clear();
         }

        int num = randomize(SIZE);

        color = String.valueOf(COLORS.get(num));

        return color;

    }

    private static int randomize(int size){
        Random random = new Random();
        int num = random.nextInt(size);

        if (!takencolors.contains(num)){
            takencolors.add(num);
            return num;
        }else {
            num = randomize(5);
        }

        return num;
    }

}
