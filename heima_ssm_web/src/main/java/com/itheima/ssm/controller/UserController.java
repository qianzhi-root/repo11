package com.itheima.ssm.controller;

import com.itheima.ssm.domain.Role;
import com.itheima.ssm.domain.UserInfo;
import com.itheima.ssm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    //给用户添加角色
    @RequestMapping("/addRoleToUser.do")
    public  String addRoleToUser(@RequestParam(name = "userId",required = true) String userId,@RequestParam(name = "ids",required = true) String[] roleIds)throws Exception{
        userService.addRoleToUser(userId,roleIds);
        return "redirect:findAll.do" ;
    }


    //查询用户以及用户可以添加的角色
    @RequestMapping("/findUserByIdAndAllRole.do")
    public  ModelAndView  findUserByIdAndAllRole(@RequestParam(name = "id" ,required =true)String userid)throws Exception{
        ModelAndView mv = new ModelAndView() ;
        //根据用户id查询用户
        UserInfo userInfo = userService.findById(userid);
        //根据用户id查询可以天机的角色
         List<Role> otherRoles =   userService.findOtherRoles(userid) ;
         mv.addObject("user",userInfo);
         mv.addObject("roleList",otherRoles) ;
         mv.setViewName("user-role-add");
          return mv ;
    }

    //查询指定id的用户
    @RequestMapping("/findById.do")
    public  ModelAndView findById(String id)throws Exception{
        ModelAndView mv = new ModelAndView() ;
        UserInfo userInfo = userService.findById(id);
        mv.addObject("user" ,userInfo);
        mv.setViewName("user-show");
        return  mv ;
    }

    //添加用户
    @RequestMapping("/save.do")
    public  String save(UserInfo userInfo)throws Exception{
        userService.save(userInfo);
        return  "redirect:findAll.do";
    }

    //查询所有用户信息
    @RequestMapping("/findAll.do")
    public ModelAndView findAll() throws Exception{
       ModelAndView mv = new ModelAndView();
        List<UserInfo> userList =userService.findAll() ;
        mv.addObject("userList",userList);
        mv.setViewName("user-list");
     return  mv ;

    }

}
