package cn.sitedev.demo.controller;

//import cn.sitedev.app.social.impl.AppSignUpUtils;

import cn.sitedev.app.social.impl.AppSignUpUtils;
import cn.sitedev.core.properties.SecurityProperties;
import cn.sitedev.demo.dto.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
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
    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @Autowired
    private AppSignUpUtils appSignUpUtils;

    @Autowired
    private SecurityProperties securityProperties;


    @PostMapping("regist")
    public void regist(HttpServletRequest request, User user) {
        // 不管时注册用户,还是绑定用户,都会拿到一个唯一标识
        String userId = user.getUsername();
        // 执行注册操作,向数据库UserConnection中插入数据(浏览器端注册)
//        providerSignInUtils.doPostSignUp(userId, new ServletWebRequest(request));
        // App端注册
        appSignUpUtils.doPostSignUp(new ServletWebRequest(request), userId);

    }

    /**
     * 从SecurityContext中获取用户信息
     *
     * @return
     */
    @GetMapping("/me")
    public Object getCurUser() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


    /**
     * 从SecurityContext中获取用户信息(Spring Social会自动从SecurityContext中获取Authentication)
     *
     * @param authenction
     * @return
     */
    // 或者:
    @GetMapping("/me2")
    public Object getCurUser(Authentication authenction) {
        return authenction;
    }

    /**
     * 从Authentication中获取Principal中的对象(这里是UserDetails)
     *
     * @param userDetails
     * @return
     */
    @GetMapping("/me3")
    public Object getCurUser(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails;
    }

    @GetMapping("/me4")
    public Object getCurUser(Authentication authenction, HttpServletRequest request) throws UnsupportedEncodingException {
        // 从请求头中获取jwt
        String header = request.getHeader("Authorization");
        String token = StringUtils.substringAfter(header, "bearer ");
        // 将jwt解析为一个Claims对象,用于从jwt 中获取扩展信息
        Claims claims = Jwts.parser().setSigningKey(securityProperties.getOauth2().getJwtSigningKey().getBytes("UTF-8")).parseClaimsJws(token).getBody();
        // 从jwt中获取扩展信息
        String company = (String) claims.get("company");
        System.out.println("company=" + company);
        return authenction;
    }

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
     * @param
     * @return
     * @description
     * @author QChen
     * @date 2018/4/13 0013
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
