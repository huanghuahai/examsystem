package cn.ds.controller;

import cn.ds.pojo.*;
import cn.ds.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 用户的控制层
 *
 * @author ds
 */
@Controller
@RequestMapping("/user")
public class UserController {
    //注入service
    @Autowired
    private UserService userService;

    // 用户登录
    @RequestMapping(value = "/login")
    public String login(@RequestParam String username,@RequestParam String password, Model model) {
        User user = userService.login(username);
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        if (user != null) {
            if (user.getPassword().equals(password)) {
                //登录成功
                //return "page/page";
                return "page/admin/adhome";
            } else {
                model.addAttribute("message", "密码错误");
                return "page/loginInfo";
            }
        } else {
            model.addAttribute("message", "不存在此用户");
            return "page/loginInfo";
        }
    }

    //显示所有老师
    @RequestMapping("/findallteacher")
    public String findTeacherAll(Model model){
        List<Teacher>teachers  = userService.findTeacherAll();
        for(int i = 0 ; i < teachers.size() ; i++) {
            System.out.println(teachers.get(i));
        }
        model.addAttribute("teachers",teachers);
        return  "page/admin/adteacher";
    }

    //保存老师
    @RequestMapping(value = "/save")
    public String create(Teacher teacher, Model model) {
        try {
            userService.create(teacher);
            model.addAttribute("message", "保存客户信息系成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:findallteacher.do";
    }
    //老师删除的方法
    @RequestMapping(value = "/delete")
    public String delete(@RequestParam Long id, Model model) {
        try {
            userService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:findallteacher.do";
    }
    //根据id找老师
    @ResponseBody
    @RequestMapping("/findById")
    public Teacher findById(@RequestBody Teacher teacher) {
        Teacher teacher_info = userService.findById(teacher.getId());
        if (teacher_info != null) {
            return teacher_info;
        } else {
            return null;
        }
    }
    @RequestMapping(value = "/update")
    public String update(Teacher teacher, Model model) {
        try {
            userService.update(teacher);
            model.addAttribute("message", "更新老师信息成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:findallteacher.do";
    }
    //查找知识点
    @RequestMapping("/findpoint1")
    public String findPoint1All(Model model){
        List<Point1>point1  = userService.findPoint1All();
        for (Point1 c : point1) {
            System.out.println(c);
            List<Point2> ps = c.getPoint2();
            for (Point2 p : ps) {
                System.out.println("\t"+p);
            }
        }
        model.addAttribute("point1",point1);
        return "page/admin/adpoint";
    }

    //保存point1
    @RequestMapping("/addpoint1")
    public String createpoint1(Point1 point1, Model model) {
        try {
            userService.createpoint1(point1);
            model.addAttribute("message", "保存章节成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:findpoint1.do";
    }

    //保存point2
    @RequestMapping("/addpoint2")
    public String createpoint2(Point2 point2, Model model) {
        try {
            userService.createpoint2(point2);
            model.addAttribute("message", "保存章节成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:findpoint1.do";
    }
    @RequestMapping("/addchoice")//添加选择题
    public String createchoice(Choice choice, Model model){
        try{
            userService.createchoice(choice);
            model.addAttribute("message", "保存章节成功");
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:findallchoice.do";
    }
    //显示point1
    @RequestMapping("/allpoint1")
    @ResponseBody
    public List<Point1> point1All(Model model){
        List<Point1>pt1  = userService.Point1All();
        System.out.println("啦啦啦"+pt1);
        //  model.addAttribute("pt1",pt1);
        return pt1;
    }

    //查找所有知识点
    @RequestMapping("/pointall")
    @ResponseBody
    public List<Point1> pointAll(Model model){
        List<Point1>point  = userService.findPoint1All();
        model.addAttribute("point",point);
        return point;
    }

    //显示所有选择题
    @RequestMapping("/findallchoice")
    public String findChoiceAll(Model model){
        List<Choice>choice = userService.findChoiceAll();
        System.out.println("啦啦啦"+choice);
        model.addAttribute("choice",choice);
        return  "page/admin/adchoice";
    }
    //根据id查询选择
    @ResponseBody
    @RequestMapping("/findBychId")
    public Choice findBychId(@RequestBody Choice choice) {
        Choice choice_info = userService.findBychId(choice.getId());
        System.out.println("这是找到的数据"+choice_info.getDifficulty());
        if (choice_info != null) {
            return choice_info;
        } else {
            return null;
        }
    }
    @RequestMapping(value = "/updatech")
    public String updatech(Choice choice, Model model) {
        try {
            userService.updatech(choice);
            model.addAttribute("message", "更新老师信息成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:findallchoice.do";
    }
    //老师删除的方法
    @RequestMapping(value = "/deletech")
    public String deletech(@RequestParam Long id, Model model) {
        try {
            userService.deletech(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:findallchoice.do";
    }
    //查找所有读程序写结果
    @RequestMapping("/readall")
    @ResponseBody
    public List<ReadProgram> pointReadProgramPAll(Model model){
        List<ReadProgram>readPrograms  = userService.findreadprogramAll();
        model.addAttribute("readPrograms",readPrograms);
        return  readPrograms;
    }



    //跳转到老师列表页面
    @RequestMapping(value = "/ListTeacher")
    public String toListPage(Model model) {
        return "redirect:findallteacher.do";
    }
    // 回到登录页
    @RequestMapping(value="/index")
    public String index(){
        return    "index";
    }
    //跳转到老师
    @RequestMapping("/toteacher")
    public String toSavePage() {
        return "page/admin/adteacher";
    }
    //跳转到题库
    @RequestMapping("/ListTk")
    public String Tk() {
        return "page/admin/adtk";
    }
    @RequestMapping("/toallchoice")
    public String toAllChoice() {
        return "redirect:findallchoice.do";
    }
    @RequestMapping("/toallread")
    public  String toAllRead(){
        return "page/admin/adreadProgram";
    }
}
