package ba.unsa.etf.rpr;

import java.util.ArrayList;

public enum Status {
    NEW, FIXED, UNDER_REVIEW;
    public static ArrayList<String> allStatus(){
        ArrayList<String> status = new ArrayList<>();
        status.add("new");
        status.add("fixed");
        status.add("under review");
        return status;
    }
}
