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
    },
    CONFIRMATION{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "Confirmation";
            else
                return "Potvrda";
        }
    },
    SUCCESFULLY_ADDED{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "Successfuly added";
            else
                return "Uspješno dodan";
        }
    }
    ,
    SUCCESFULLY_ADDED_DEV{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return " has been succesfuly added to this project!";
            else
                return " je uspješno dodan na ovaj projekat!";
        }
    },
    SUCCESFULLY_REMOVED{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "Successfuly removed";
            else
                return "Uspješno uklonjen";
        }
    }
    ,
    SUCCESFULLY_REMOVED_DEV{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return " has been succesfuly removed to this project!";
            else
                return " je uspješno uklonjen sa ovog projekta!";
        }
    } ,
    ALREADY_WORKING{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return " already working on this project!";
            else
                return " već radi na ovom projektu!";
        }
    },
    ALREADY_EXIST{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "Already exist developer with username: ";
            else
                return "Već postoji developer sa korisničkim imenom: ";
        }
    },
    ALREADY_EXIST_MAIL{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "Already exist developer with this e-mail";
            else
                return "Već postoji developer sa istim e-mailom";
        }
    }
    ;

}
