package net.cachapa.data.gateway;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class ClientManager {
    private static OkHttpClient client;

    public static OkHttpClient getClient() {
        if (client == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new SimpleLogger());
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build();
        }

        return client;
    }

    private static class SimpleLogger implements HttpLoggingInterceptor.Logger {
        @Override
        public void log(String message) {
            System.out.println(message.replace("\\n", "\n"));
        }
    }

}
