package br.com.felipe.login.support;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class GeradorPassword {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123789si"));
    }
}

