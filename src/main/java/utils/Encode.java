package utils;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.io.UnsupportedEncodingException;


public class Encode {

    public static String getBasicAuthTemplate(String login, String pass) {
        final String BASIC_AUTH_TEMPLATE = "Basic %1$s";

        try {
            return String.format(BASIC_AUTH_TEMPLATE, Base64.encode((login + ":" + pass).getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return String.format(BASIC_AUTH_TEMPLATE, Base64.encode((login + ":" + pass).getBytes()));
        }
    }
}
