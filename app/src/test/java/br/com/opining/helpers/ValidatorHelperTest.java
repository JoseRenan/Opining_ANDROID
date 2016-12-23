package br.com.opining.helpers;

import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.Assert.*;

/*
 * Created by Juan on 22/12/2016.
 */

public class ValidatorHelperTest {

    @Test
    public void validateEmail(){
        assertTrue(ValidatorHelper.validateEmail("juanlyrabarros@gmail.com"));
        assertTrue(ValidatorHelper.validateEmail("juan_lyra_barros@gl.co.br.ba.br.na"));
        assertTrue(ValidatorHelper.validateEmail("juan.barros@gmail.com"));
        assertTrue(ValidatorHelper.validateEmail("juan123.as@gmail.com"));
        assertFalse(ValidatorHelper.validateEmail("juuuuuuuuuuuuuuuuuuuuuuuuuuuuu@gmail.com"));

        assertFalse(ValidatorHelper.validateEmail("juan123.@gmail.com"));
        assertFalse(ValidatorHelper.validateEmail("juuuu as@gmail.com"));
        assertFalse(ValidatorHelper.validateEmail("j!uan@gmail.com"));
        assertFalse(ValidatorHelper.validateEmail(""));
        assertFalse(ValidatorHelper.validateEmail("@gmail.com"));
        assertFalse(ValidatorHelper.validateEmail("juan@"));
        assertFalse(ValidatorHelper.validateEmail("juan@gmail@com"));
        assertFalse(ValidatorHelper.validateEmail("juan@gmail.com."));
        assertFalse(ValidatorHelper.validateEmail(".juanlyra@gmail.com"));
        assertFalse(ValidatorHelper.validateEmail("juan@.com"));
        assertFalse(ValidatorHelper.validateEmail(".juan@gmail.com."));
        assertFalse(ValidatorHelper.validateEmail(null));
        assertFalse(ValidatorHelper.validateEmail("@gmail"));
        assertFalse(ValidatorHelper.validateEmail("juuuuuuuuuuuuuuuuuuuuuuuuuuuuuu@gmail.com"));
    }

    @Test
    public void validateName(){
        assertFalse(ValidatorHelper.validateName(null));
        assertFalse(ValidatorHelper.validateName(""));

        //61 caracteres
        assertFalse(ValidatorHelper.validateName("1111111111111111111111111111111111111111111111111111111111111"));


        String possiveis = "abcdefghijklmnopqrstuwxyzABCDEFGHIJKLMNOPQRSTUVXWYZÁÉÍÓÚáéíóúâêîôûÂÊÎÔÛÃÕãõ ";

        for (int i = 0; i !=  255; i++) {
            String teste = Character.toString((char) i);
            assertTrue(possiveis.contains(teste) == ValidatorHelper.validateName(teste));
        }
    }

    @Test
    public void validatePassword(){
        assertFalse(ValidatorHelper.validateName(null));
        assertFalse(ValidatorHelper.validatePassword(""));
        assertFalse(ValidatorHelper.validatePassword("12345"));
        assertFalse(ValidatorHelper.validatePassword("1234567890123456789012345678901"));

        assertTrue(ValidatorHelper.validatePassword("123456"));
    }
}
