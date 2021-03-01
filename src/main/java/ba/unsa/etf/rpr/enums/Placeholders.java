package ba.unsa.etf.rpr.enums;

import java.util.Locale;

public enum Placeholders {
    DEVELOPERS{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "There is no developers";
            else
                return "Nema developera";
        }
    },
    PROJECTS{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "There is no projects";
            else
                return "Nema projekata";
        }
    },
    BUGS{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "There is no bugs";
            else
                return "Nema bug-ova";
        }
    },
    REQUSETS{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "There is no requests";
            else
                return "Nema zahtjeva";
        }
    };
}
