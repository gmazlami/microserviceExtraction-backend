package ch.uzh.ifi.seal.monolith2microservices.utils;

/**
 * Created by gmazlami on 12/21/16.
 */
public class HexColorGenerator {

    private final String[] colorCodes = {"#FF0000", "#00FFFF", "#0000FF", "#800080", "#FFFF00", "#00FF00", "#C0C0C0",
            "#A52A2A", "#808080", "#008000", "#ADD8E6", "#F87217", "#800517", "#E38AAE",
            "#FFF5EE", "#FFF8C6", "#4CC417"};

    private int counter = 0;


    public String getNextColor(){
        String hexCode = colorCodes[counter];
        counter = (counter + 1) % colorCodes.length;
        return hexCode;
    }
}
