package com.hyc.community.controller;

import com.hyc.community.dto.AccessTokenDTO;
import com.hyc.community.dto.GiteeUser;
import com.hyc.community.provider.GiteeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GiteeProvider provider;
    @Value("${gitee.client.id}")
    private String clientId;
    @Value("${gitee.client.secret}")
    private String clientSecret;
    @Value("${gitee.redirect.url}")
    private String redirectUrl;


    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(redirectUrl);
        String accessToken = provider.getAccessToken(accessTokenDTO);
        System.out.println(accessToken);
        GiteeUser user = provider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
