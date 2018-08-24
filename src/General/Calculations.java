package General;

/**
 * Created by johnn on 4/9/2017.
 */
final public class Calculations {

    private static double distanceBetweenCitiesInMiles(double lat1, double lon1, double lat2, double lon2){
        double theta = lon1 - lon2;
        double dist = Math.sin(degreeToRadian(lat1)) * Math.sin(degreeToRadian(lat2)) +
                Math.cos(degreeToRadian(lat1)) * Math.cos(degreeToRadian(lat2)) * Math.cos(degreeToRadian(theta));
        dist = Math.acos(dist);
        dist = radianToDegree(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private static double degreeToRadian(double degree) {
        return (degree * Math.PI / 180.0);
    }

    private static double radianToDegree(double radians) {
        return (radians * 180.0 / Math.PI);
    }

    public static double getDistanceBetweenCitiesInMiles(double lat1, double lon1, double lat2, double lon2){
        return distanceBetweenCitiesInMiles(lat1, lon1, lat2, lon2);
    }

    public static double getDistanceBetweenCitiesInKilometers(double lat1, double lon1, double lat2, double lon2){
        return 1.60934*distanceBetweenCitiesInMiles(lat1, lon1, lat2, lon2);
    }

    public static boolean hasEnoughFuel(double fuel, double burn, double distance){
        return distance < (fuel / (burn/1000));
    }

    public static boolean hasEnoughFuel(double fuel, double burn, double lat1, double lon1, double lat2, double lon2 ){
        return hasEnoughFuel(fuel, burn, getDistanceBetweenCitiesInKilometers(lat1, lon1, lat2, lon2));
    }

    public static int pricePerSeat(double base, double distance){
        return (int)(base+distance/100);
    }

    public static String calcBasePrice(String fuelCap, String modelCap){
        int jetFuel = 565; // per tonne;
        return Integer.toString((Integer.parseInt(fuelCap)*jetFuel/Integer.parseInt(modelCap)));
    }

    public static int getDistancePrice(double distance){
        return (int)(distance / 100.0); // where 100 is $1/100km
    }
}
