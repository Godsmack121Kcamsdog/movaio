package io.mova.kucherenko.gettyimages.api;

import io.mova.kucherenko.gettyimages.models.response.Images;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ImageService {

    //    @POST("search/images?ﬁelds=id,title,thumb&sort_order=best")
    @GET
    Flowable<Images> getImages(@Url String url);

}
