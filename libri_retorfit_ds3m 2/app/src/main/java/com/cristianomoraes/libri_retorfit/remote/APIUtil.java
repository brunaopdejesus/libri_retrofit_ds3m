package com.cristianomoraes.libri_retorfit.remote;

public class APIUtil {

    private static final String API_URL = "http://10.107.144.14:3000/";

    public static RouterInterface getUsuarioInterface(){

        return RetroFitClient.getClient(API_URL)
                .create(RouterInterface.class);

    }

}
