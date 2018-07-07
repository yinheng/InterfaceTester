package com.yinheng.interfacetester.runner;

import com.yinheng.interfacetester.data.model.TestCase;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

public class HttpGetRequester implements TestCaseRequester {

    // TODO Param not handle.
    public String getResponse(TestCase testCase) throws IOException, ResponseFailException {

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(testCase.getTarget()).get().build();
        Response response = okHttpClient.newCall(request).execute();

        boolean success = response.isSuccessful();

        if (success) {
            ResponseBody responseBody = response.body();
            return responseBody == null ? null : responseBody.string();
        } else {
            throw new ResponseFailException("HttpGetRequester fail to get response: " + response);
        }
    }
}
