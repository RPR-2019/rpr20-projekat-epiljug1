package ba.unsa.etf.rpr.enums;

import java.util.Locale;

public enum StageEnums {
    ALL_PROJECTS{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "All projects";
            else
                return "Svi projekti";
        }
    },
    ALL_DEVELOPERS{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "All developers";
            else
                return "Svi developeri";
        }
    },
    OTHER_PROJECTS{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "Other projects";
            else
                return "Ostali projekti";
        }
    },
    LOGIN{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "Login";
            else
                return "Prijava";
        }
    },
    ADD_PROJECT{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "Add project";
            else
                return "Dodaj projekat";
        }
    },
    QUESTION_LOGOUT{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "Do you want to log out?";
            else
                return "Da li se želite odjaviti";
        }
    },
    HOME_PAGE{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "Home page";
            else
                return "Početna stranica";
        }
    },
    ADD_BUG{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "Add bug";
            else
                return "Dodaj bug";
        }
    },
    EDIT_BUG{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "Edit bug";
            else
                return "Uredi bug";
        }
    },
    ABOUT_APP{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "About app info";
            else
                return "Informacije o aplikaciji";
        }
    },
    EDIT_PROFILE{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "Edit profile";
            else
                return "Uredi profil";
        }
    }
    ;

}
