package com.android.udacity_foodquest.data.remote;

import androidx.annotation.NonNull;

import com.android.udacity_foodquest.util.Constants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Request newRequest;

        newRequest = request.newBuilder()
                .addHeader(Constants.USER_KEY_HEADER,Constants.API_KEY)
                .build();
        return chain.proceed(newRequest);
    }
}
