package ch.uzh.ifi.seal.monolith2microservices.utils;

/**
 * Created by gmazlami on 12/21/16.
 */
public class HexColorGenerator {

    private final String[] colorCodes = {"#B6B6B4", "#5CB3FF", "#95B9C7", "#7FFFD4", "#99C68E", "#59E817", "#8AFB17",
            "#FFF380", "#E2A76F", "#FF7F50", "#F75D59", "#EDC9AF", "#FCDFFF", "#FFF5EE",
            "#EBDDE2", "#ADA96E", "#F0FFFF"};

    private int counter = 0;


    public String getNextColor(){
        String hexCode = colorCodes[counter];
        counter = (counter + 1) % colorCodes.length;
        return hexCode;
    }
}
