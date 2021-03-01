package ba.unsa.etf.rpr.enums;

import java.util.Locale;

public enum EmptyFld {
    USERNAME{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "Username field is empty!";
            else
                return "Polje za korisni?ko ime je prazno!";
        }
    },
    PASSWORD{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "Password field is empty!";
            else
                return "Polje za šifru je prazno!";
        }
    },
    EMAIL{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "E-mail field is empty!";
            else
                return "Polje za e-mail je prazno!";
        }
    },
    SURNAME{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "Surname field is empty!";
            else
                return "Polje za prezime je prazno!";
        }
    },
    NAME{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "Name field is empty!";
            else
                return "Polje za ime je prazno!";
        }
    },
    TYPE {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Bug type field is empty!";
            else
                return "Polje za tip bug-a je prazno!";
        }
    },
    SELECT_DEV {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "You did not select any developer!";
            else
                return "Niste izabrali ni jednog developera!";
        }
    },
    SELECT_BUG {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "You did not select any bug!";
            else
                return "Niste izabrali ni jedan bug!";
        }
    },
    SELECT_PROJECT {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "You did not select any project!";
            else
                return "Niste izabrali ni jedan projekat!";
        }
    },
    SOURCE_CODE{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "You did not added a link for source code!";
            else
                return "Niste dodali link za izvorni kod!";
        }
    }

    ;
}
