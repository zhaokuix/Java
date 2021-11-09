package com.example.api;

import com.example.dao.UserDao;
import com.example.entity.QUser;
import com.example.entity.User;
import org.redisson.api.RedissonClient;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * 用户接口
 */
@RestController
@RequestMapping
public class UserApi {

    @Resource
    private UserDao userDao;

    @Resource
    private RedissonClient redissonClient;

    @GetMapping("/{id}")
    public String get(@PathVariable Long id) {
        User one = userDao.getOne(id);
        return one.getName();
    }

    @GetMapping("/all")
    public Object get() {
        return userDao.findAll();
    }

    @GetMapping("/all1")
    public Object get1() {
        QUser qUser = QUser.user;
        return userDao.findAll(qUser.id.in(1,2,3,4,5),PageRequest.of(0,999));
    }

    @GetMapping("/clear")
    public void clearCache() {
        this.redissonClient.getKeys().deleteByPattern("fbp*");
    }

    @GetMapping("/save")
    public User save() {
        User user = new User();
        user.setName(LocalDateTime.now().toString());
        return userDao.save(user);
    }
}
