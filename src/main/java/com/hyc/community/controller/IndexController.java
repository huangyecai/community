package com.hyc.community.controller;

import com.hyc.community.mapper.UserMapper;
import com.hyc.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String home(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null){
            for (Cookie cookie:  cookies) {
                if ("token".equals( cookie.getName())){
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    request.getSession().setAttribute("user",user);
                }

            }
        }

        return "index";
    }
    @GetMapping("/index")
    public String index(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "index";
    }

}
