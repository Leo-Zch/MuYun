package net.ximatai.muyun.platform.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import net.ximatai.muyun.core.exception.MyException;
import net.ximatai.muyun.model.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;

@Path("/sso")
public class SsoController {

    private final Logger logger = LoggerFactory.getLogger(SsoController.class);

    @Inject
    UserController userController;

    @Inject
    UserInfoController userInfoController;

    @POST
    @Path("/login")
    public Map login(Map body) {
        String username = (String) body.get("username");
        String password = (String) body.get("password");

        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        PageResult pageResult = userController.query(Map.of("v_username", username));

        if (pageResult.getSize() == 0) {
            logger.error("不存在的用户信息进行登录：{}", username);
            throw new MyException("用户名或密码错误");
        }

        Map userInDB = (Map) pageResult.getList().getFirst();

        if (password.equals(userInDB.get("v_password").toString())) {
            if ((boolean) userInDB.get("b_enabled")) {
                Map<String, ?> user = userInfoController.view((String) userInDB.get("id"));
                return user;
            } else {
                logger.error("用户已停用，用户名：{}", username);
                throw new MyException("用户名或密码错误");
            }
        } else {
            logger.error("用户密码验证失败，用户名：{}", username);
            throw new MyException("用户名或密码错误");
        }

    }

}