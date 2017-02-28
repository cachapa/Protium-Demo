package net.cachapa.protium;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class RestGateway {
    private static Gson jsonParser;

    private OkHttpClient client;
    private String userAgent;

    public RestGateway(OkHttpClient client, String userAgent) {
        this.client = client;
        this.userAgent = userAgent;

        if (jsonParser == null) {
            jsonParser = new Gson();
        }
    }

    protected <T> T fetch(Class<T> tClass, String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("user-agent", userAgent)
                .get()
                .build();

        String json = client.newCall(request)
                .execute()
                .body()
                .string();

        return jsonParser.fromJson(json, tClass);
    }
}
