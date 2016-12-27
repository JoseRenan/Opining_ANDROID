package br.com.opining.helpers;

public class ValidatorHelper {

    private static final int MIN_DOMINIO_POSSIVEL = 4;
    private static final int MAX_DOMINIO_POSSIVEL = 30;
    private static final int MAX_NAME_POSSIVEL = 60;
    private static final int MIN_PASSWORD_POSSIVEL = 6;
    private static final int MAX_PASSWORD_POSSIVEL = 30;

    public static boolean validateEmail(String email) {
        if (email == null){
            return false;
        }
        int posicao_arroba = email.indexOf("@");
        if (posicao_arroba <= MIN_DOMINIO_POSSIVEL || posicao_arroba >= MAX_DOMINIO_POSSIVEL){
            return false;
        }
        return email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)" +
                "*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\" +
                "x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9]" +
                "(?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25" +
                "[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\" +
                "x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    }

    public static boolean validateName(String name) {
        if (name == null){
            return false;
        }
        return (name.matches("[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ]{1,}([ ][A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ]{1,}){0,}")
            && name.length() <= MAX_NAME_POSSIVEL);
    }

    public static boolean validatePassword(String password) {
        if (password == null){
            return false;
        }

        return password.matches("[0-9a-zA-ZçÇ.,<>;:/?'\"()*!@#$+={}\\[\\] ]{" + MIN_PASSWORD_POSSIVEL + "," + MAX_PASSWORD_POSSIVEL + "}");
    }
}
