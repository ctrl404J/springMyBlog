package com.blog.myBlog.controller;

import com.blog.myBlog.dao.UserDao;
import com.blog.myBlog.dto.BlogDto;
import com.blog.myBlog.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class UserController {

    @Autowired
    UserDao userDao;

    @GetMapping("login")
    public String loginForm(){

        return "layouts/loginForm";
    }

    @PostMapping("login")
    @ResponseBody
    public String loginAction(UserDto userDto, HttpSession session){

        UserDto userInfo = userDao.idCheck(userDto.getUser_id());

        log.info("유저정보:{}", userInfo);
        if( userInfo.getUser_pass().equals(userDto.getUser_pass()) ) {
            session.setAttribute("userName", userInfo.getUser_name());
            return "<script>alert('로그인 되었습니다.'); location.href='/';</script>";
        }else{
            return "<script>alert('아이디나 비밀번호를 확인해 주세요.'); location.href='javascript:history.go(-1)';</script>";
        }
    }

    @GetMapping("join")
    public String joinForm(){

        return "layouts/joinForm";
    }

    @PostMapping("join")
    @ResponseBody
    public String joinAction(HttpServletRequest request, UserDto userDto){

        String userPass1 = request.getParameter("user_pass1");
        String userPass2 = request.getParameter("user_pass2");
        log.info("패스1:{}", userPass1);
        log.info("패스2:{}", userPass2);

        if(userPass1 != null && !"".equals(userPass1) && userPass1.equals(userPass2)) {
            userDto.setUser_pass(userPass1);
            int result = userDao.joinAction(userDto);
            return "<script>alert('회원가입이 완료되었습니다.'); location.href='login';</script>";
        }

        return "<script>alert('비밀번호를 다시 확인해 주세요!'); location.href='javascript:history.go(-1)'; </script>;";

    }


    // 비밀번호 찾기
    @GetMapping("findPass")
    public String findPassForm(){
        return "layouts/findPass";
    }

    @PostMapping("findPass")
    @ResponseBody
    public String findPass(UserDto userDto){
        log.info("비번찾기 정보:{}", userDto);
        String userPass = userDao.findPass(userDto);
        return "비밀번호는:"+userPass;
    }
}
