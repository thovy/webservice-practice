package com.prac.webservice.springboot.web;

import com.prac.webservice.springboot.config.auth.LoginUser;
import com.prac.webservice.springboot.config.auth.dto.SessionUser;
import com.prac.webservice.springboot.service.posts.PostsService;
import com.prac.webservice.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
//    private final HttpSession httpSession;
//
//    @GetMapping("/")
//    public String index(Model model) {
//        model.addAttribute("posts", postsService.findAllDesc());
//        SessionUser user = (SessionUser) httpSession.getAttribute("user");
//        if (user != null){
//            model.addAttribute("userName", user.getName());
//        }
//        return "index";
//    }
    // 위는 HttpSession 개선 전
    // 아래는 개선 후
    // private 으로 선언된 httpSession 이 사라지고, index 메서드 안에 Session user 부분이 사라짐.
    // index 메서드 파라미터로 @LoginUser 가 들어옴

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        model.addAttribute("posts", postsService.findAllDesc());
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
