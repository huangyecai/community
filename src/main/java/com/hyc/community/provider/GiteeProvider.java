package com.hyc.community.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyc.community.dto.AccessTokenDTO;
import com.hyc.community.dto.GiteeUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GiteeProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://gitee.com/oauth/token?grant_type=authorization_code")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String result = response.body().string();
            JSONObject jsonObject = JSON.parseObject(result);
            return jsonObject.getString("access_token");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GiteeUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://gitee.com/api/v5/user?access_token="+accessToken)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String result = response.body().string();
            return JSON.parseObject(result,GiteeUser.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
