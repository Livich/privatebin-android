package com.livich.privatebin.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;


interface PrivateBinService {
    @FormUrlEncoded
    @Headers("X-Requested-With: JSONHttpRequest")
    @POST("/")
    Call<PrivateBinPaste> publish(@Field("data") String data,
                                  @Field("expire") String expire,
                                  @Field("burnafterreading") Integer burn,
                                  @Field("opendiscussion") Integer discussion,
                                  @Field("syntaxcoloring") Integer syntaxcoloring,
                                  @Field("formatter") String formatter
    );
}
