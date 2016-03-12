package com.codingbuffalo.data.repository;

import com.codingbuffalo.data.model.Gif;
import com.codingbuffalo.data.model.Page;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class GiphyRepository {
    private final Service mService;
    
    public GiphyRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.giphy.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        
        mService = retrofit.create(Service.class);
    }
    
    public Page<Gif> search(String query, int skip) throws IOException {
        return mService.search(query, skip).execute().body();
    }
    
    public interface Service {
        @GET("/v1/gifs/search?api_key=dc6zaTOxFJmzC")
        Call<Page<Gif>> search(@Query("q") String query, @Query("offset") int offset);
    }
}
