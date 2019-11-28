package com.njupt.hpc.edu.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author : molamola
 * @Project: edu
 * @Description:
 * @date : 2019-11-28 15:24
 **/
public class test {
    public static void main(String[] args) {
        /**
         * 123 : $2a$10$6yY0q5YxPgLFsnRBJdXOsukcmOUboDYSEb1wwpnBZbVXMso0ZHwl2 molamola
         * 321 : $2a$10$3nM8v7RrHU4bXBx7xuXha.IQkxuVOCFQYKlFWWANEvdnNGqgFq/i2 kkk
         * 3213 : $2a$10$wpJe1sr8rdUYgbsqvJ2bVOAz8qtDdBOmD/NQR9k74epUrCa3DWnum wbb
         */
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("3213"));
    }
}
