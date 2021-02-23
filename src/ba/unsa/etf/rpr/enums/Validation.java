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
    };
}
