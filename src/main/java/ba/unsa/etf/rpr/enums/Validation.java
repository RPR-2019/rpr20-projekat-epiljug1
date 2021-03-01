package ba.unsa.etf.rpr.enums;

import java.util.Locale;

public enum Validation {
    EMAIL{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "E-mail is not valid!";
            else
                return "E-mail nije validan!";
        }
    },
    SENDING_EMAIL{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "Something went wrong while sending! Please, check your info";
            else
                return "Došlo je do greške prilikom slanja mail-a! Molimo, provjerite svoje podatke";
        }
    },
    REQUESTS{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "Number of requests: ";
            else
                return "Broj zahtjeva: ";
        }
    },
    USERNAME_ERROR{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "This username is already used!";
            else
                return "Ovo korisničko ime je korišteno!";
        }
    },
    EMAIL_ERROR{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "This e-mail is already used!";
            else
                return "Ova e-mail aderesa je u upotrebi!";
        }
    },
    SELECT{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "You did not select any developer!";
            else
                return "Niste odabrali niti jedanog developera!";
        }
    },
    SELECT_PROJECT{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "You did not select any project!";
            else
                return "Niste odabrali niti jedan projekat!";
        }
    },
    PASSWORD{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "Wrong password!";
            else
                return "Pogrešna šifra!";
        }
    },
    PASSWORD_REGEX{
        @Override
        public String toString() {
            if(Locale.getDefault().getCountry().equals("US"))
                return "Password should contain at least 8 chars, one digit, one lower and upper char and without space, tab";
            else
                return "Password treba da sadrži najmanje 8 karaktera, jedno malo i veliko slovo, bez razmaka";
        }
    };

}
