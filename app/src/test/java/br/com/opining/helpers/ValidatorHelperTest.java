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
        //assertFalse(ValidatorHelper.validateEmail("j!uan@gmail.com"));
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
        assertFalse(ValidatorHelper.validateName(" Juan"));
        assertFalse(ValidatorHelper.validateName("Juan "));
        assertFalse(ValidatorHelper.validateName("1Abc"));
        assertFalse(ValidatorHelper.validateName("abc12"));
        assertFalse(ValidatorHelper.validateName("!ssda"));
        //61 caracteres
        assertFalse(ValidatorHelper.validateName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));

        assertTrue(ValidatorHelper.validateName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
        assertTrue(ValidatorHelper.validateName("ç"));
        assertTrue(ValidatorHelper.validateName("Çabrina"));
        assertTrue(ValidatorHelper.validateName("ébrina"));
        assertTrue(ValidatorHelper.validateName("Çabr ií"));
        assertTrue(ValidatorHelper.validateName("Juan Barros asoijasoia asoia a"));
    }

    @Test
    public void validatePassword(){
        assertFalse(ValidatorHelper.validateName(null));
        assertFalse(ValidatorHelper.validatePassword(""));
        assertFalse(ValidatorHelper.validatePassword("12345"));
        assertTrue(ValidatorHelper.validatePassword("1@13FAs4WEVF54V#%@2 asd"));
        assertFalse(ValidatorHelper.validatePassword("1234567890123456789012345678901"));

        assertTrue(ValidatorHelper.validatePassword("123456"));
    }
}
