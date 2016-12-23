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

        String possiveis = "abcdefghijklmnopqrstuwxyzABCDEFGHIJKLMNOPQRSTUVXWYZÁÉÍÓÚáéíóúâêîôûÂÊÎÔÛÃÕãõ ";

        for (int i = 0; i !=  255; i++) {
            String teste = Character.toString((char) i);
            if(possiveis.contains(teste)) {
                assertTrue(ValidatorHelper.validateName(teste));
            }else{
                assertFalse(ValidatorHelper.validateName(teste));
            }
        }
    }

    @Test
    public void validatePassword(){

    }
}
