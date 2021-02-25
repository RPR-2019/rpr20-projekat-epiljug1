package ba.unsa.etf.rpr.enums;

import javafx.fxml.FXML;

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
    },
    ALL_DEVELOPERS{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "To all developers";
            else
                return "Svim developerima dodijeljen";
        }
    },
    SELECT{
        @Override
        public String toString(){
            if(Locale.getDefault().getCountry().equals("US"))
                return "You did not select any bug!";
            else
                return "Niste odabrali ni jedan bug!";
        }
    },
    INFO{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "Bug info";
            else
                return "Informacije o bug-u";
        }
    },
    SEND_REQUEST{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "Send request for solving selected bug";
            else
                return "Pošaljte zahtjev za rješavanje odabranog bug-a";
        }
    },
    SEND_REQUEST_BUG{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "Send request";
            else
                return "Pošaljte zahtjev";
        }
    },
    CONFIRM_SEND_REQUEST{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "Do you want to send request for selected bug?";
            else
                return "Da li želite da pošaljete zahtjev za odabrani bug?";
        }
    }

    ;
}
