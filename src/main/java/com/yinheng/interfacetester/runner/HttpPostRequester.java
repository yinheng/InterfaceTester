package com.yinheng.interfacetester.runner;

import com.yinheng.interfacetester.data.model.TestCase;
import okhttp3.*;

import java.io.IOException;

public class HttpPostRequester implements TestCaseRequester {

    private static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");

    public String getResponse(TestCase testCase) throws IOException, ResponseFailException {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(JSON_TYPE, testCase.getParam());
        Request request = new Request.Builder().url(testCase.getTarget()).post(requestBody).build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body() == null ? null : response.body().string();
        } else {
            throw new ResponseFailException("Response fail");
        }
    }
}
