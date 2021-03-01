package ba.unsa.etf.rpr.enums;

import java.util.Locale;

public enum ReportsEnum {
    ASSIGNED{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return  "Projects to which the developer has been added.";
            else
                return  "Projekti na koje je developer dodan.";
        }
    },
    NAME{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "Name";
            else
                return "Naziv";
        }
    },
    NAME_DEV{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "Name";
            else
                return "Ime";
        }
    },
    SURNAME_DEV{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "Surname";
            else
                return "Prezime";
        }
    },
    USERNAME{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "Username";
            else
                return "Korisničko ime";
        }
    },
    EMAIL{
        @Override
        public String toString() {
                return "E-mail";
        }
    },
    CREATOR{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "Creator";
            else
                return "Kreator";
        }
    },
    DATE{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "Date created";
            else
                return "Datum kreiranja";
        }
    },
    CLIENT_NAME{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "Client name";
            else
                return "Ime klijenta";
        }
    }
    ,
    CLIENT_EMAIL{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "Client e-mail";
            else
                return "E-mail klijenta";
        }
    },
    LIST_DEVELOPERS{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "List all developers";
            else
                return "Lista svih developera";
        }
    };
}
