package com.livk.mybatis.example.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.livk.commons.domain.LivkPage;
import com.livk.commons.util.ReflectionUtils;
import com.livk.mybatis.example.entity.User;
import com.livk.mybatis.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * UserController
 * </p>
 *
 * @author livk
 * @date 2023/1/5
 */
@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("{id}")
    public HttpEntity<User> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PutMapping("{id}")
    public HttpEntity<Boolean> updateById(@PathVariable Integer id,
                                          @RequestBody User user) {
        user.setId(id);
        return ResponseEntity.ok(userService.updateById(user));
    }

    @PostMapping
    public HttpEntity<Boolean> save(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @DeleteMapping("{id}")
    public HttpEntity<Boolean> deleteById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.deleteById(id));
    }

    @GetMapping
    public HttpEntity<LivkPage<User>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                           @RequestParam(defaultValue = "10") Integer pageSize) {
        try (Page<User> page = PageHelper.<User>startPage(pageNum, pageSize)
                .countColumn(ReflectionUtils.getFieldName(User::getId))
                .doSelectPage(userService::list)) {
            LivkPage<User> result = new LivkPage<>(page);
            return ResponseEntity.ok(result);
        }
    }
}
