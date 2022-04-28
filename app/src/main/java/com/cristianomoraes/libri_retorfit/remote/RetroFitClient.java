package com.cristianomoraes.libri_retorfit.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetroFitClient {

    private static Retrofit retrofit = null;

    /** MÉTODO DE ACESSO AO CLIENT **/
    public static Retrofit getClient(String url){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if(retrofit == null){

            retrofit = new Retrofit.Builder()
                            .baseUrl(url)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();

        }

        return retrofit;

    }

}
