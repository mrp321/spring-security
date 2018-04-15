package cn.sitedev.demo.controller;

import cn.sitedev.demo.dto.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户控制层
 *
 * @author QChen
 * @date 2017-4-1
 */
@RestController
@RequestMapping("user")
public class UserController {

    /**
     * 增加用户
     *
     * @param user 用户实体
     * @return
     */
    @PostMapping
    public User createUser(@RequestBody(required = false) User user) {
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getId());
        user.setId(1);
        return user;
    }

/**
 * @description
 * @author QChen
 * @date 2018/4/13 0013
 * @param
 * @return
 */
    @DeleteMapping("/{id:\\d+}")
    public void deleteUser(@PathVariable String id) {
        System.out.println("删除id=" + id);
    }

    /**
     * 修改用户
     *
     * @param user 用户实体
     * @return
     */
    @PutMapping("/{id:\\d+}")
    public User modifyUser(@RequestBody User user) {

        System.out.println("userName=" + user.getUsername());
        System.out.println("password=" + user.getPassword());
        System.out.println("id=" + user.getId());
        user.setId(1);
        return user;
    }

    /**
     * 查询用户
     *
     * @param id 用户id
     * @return
     */
    @GetMapping("/{id}")
    // 如果@PathVariable注解不指定name或者value值，则方法参数名和请求url中{}内的名称保持一致
    public User getUser(@PathVariable(name = "id") String id) {
        User user = new User();
        user.setUsername("Tom");
        return user;
    }

    /**
     * 查询所有用户列表
     *
     * @return
     */
    @GetMapping
    public List<User> getUserList() {
        List<User> userList = new ArrayList<>();
        User user1 = new User(1, "1", "1");
        User user2 = new User(2, "2", "2");
        User user3 = new User(3, "3", "3");
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        return userList;
    }


}
