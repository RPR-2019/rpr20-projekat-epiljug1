package ba.unsa.etf.rpr.enums;

import java.util.Locale;

public enum Tooltips {
    YOUR_PROJECTS{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "All projects that you have made";
            else
                return "Svi projekti koje ste kreirali";
        }
    },
    OTHER_PROJECTS{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "All projects that you have been added to";
            else
                return "Svi projekti na koje ste dodani";
        }
    },
    ALL_DEVELOPERS{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "All available developers";
            else
                return "Svi dostupni developeri";
        }
    },
    EDIT_PROFILE{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "Edit your profile data";
            else
                return "Uredite svoje lične podatke";
        }
    },
    ADD_PROJECT{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "Add a new project to your collection";
            else
                return "Dodajte novi projekat u svoju kolekciju";
        }
    }
}
