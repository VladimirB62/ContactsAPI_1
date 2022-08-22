package com.telran.contacts.tests;

import com.google.gson.Gson;
import com.telran.contacts.dto.AuthRequestDto;
import com.telran.contacts.dto.ErrorDto;
import com.telran.contacts.dto.LoginRegResponseDto;
import com.telran.contacts.dto.RegRequestDto;
import okhttp3.*;
import org.testng.annotations.Test;

import java.io.IOException;

public class ContactOkHttpTests {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @Test
    public void loginNegativeTestWithInvalidEmail() throws IOException {

        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();

        AuthRequestDto requestDto = AuthRequestDto.builder()
                .email("gushiddinkgmail.com")
                .password("12345678Aa$").build();


        RequestBody requestBody = RequestBody.create(gson.toJson(requestDto),JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        getMessageAndCodeFromResponse(client, request, gson);
    }

    @Test
    public void registrationNegativTestWithWrongEmailFormat() throws IOException {

        Gson gson = new Gson();
        OkHttpClient client =new OkHttpClient();

        RegRequestDto requestDto = RegRequestDto.builder()
                .email("gushiddink@gmail.c")
                .password("12345678Aa$").build();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestDto),JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/registration")
                .post(requestBody)
                .build();

        getMessageAndCodeFromResponse(client, request, gson);

    }

    @Test
    public void registrationNegativTestWithAlreadyExistedUser() throws IOException {

        Gson gson = new Gson();
        OkHttpClient client =new OkHttpClient();

        RegRequestDto requestDto = RegRequestDto.builder()
                .email("gushiddink@gmail.com")
                .password("12345678Aa$").build();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestDto),JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/registration")
                .post(requestBody)
                .build();

        getMessageAndCodeFromResponse(client, request, gson);


    }

    private void getMessageAndCodeFromResponse(OkHttpClient client, Request request, Gson gson) throws IOException {
        Response response = client.newCall(request).execute();

        String responseJson = response.body().string();

        if (response.isSuccessful()){
            LoginRegResponseDto responseDto = gson.fromJson(responseJson, LoginRegResponseDto.class);
            System.out.println(responseDto.getToken());
        }else{
            ErrorDto errorDto = gson.fromJson(responseJson,ErrorDto.class);
            System.out.println(errorDto.getCode());
            System.out.println(errorDto.getMessage());
        }
    }
}
//eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Imd1c2hpZGRpbmtAZ21haWwuY29tIn0.RvRPouJtVT3ZbpYwtU4a57NzbUcQecbrEN5XqeusiJk
