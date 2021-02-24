package ba.unsa.etf.rpr.enums;

import java.util.Locale;

public enum BugInfo {
    FIXED{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "fixed";
            else
                return "riješen";
        }
    },
    ASSIGNED{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "assigned";
            else
                return "dodijeljen";
        }
    },
    NEW{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "new";
            else
                return "novi";

        }
    };
}
