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
    };
}
