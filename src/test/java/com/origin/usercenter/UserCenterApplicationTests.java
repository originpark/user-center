package com.origin.usercenter;

import com.origin.usercenter.model.domain.User;
import com.origin.usercenter.service.UserService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;


@SpringBootTest
class UserCenterApplicationTests {

	@Resource
	private UserService userService;

	@Test
	void contextLoads()  {
		String account = "or@igin_park#";
		String password = "1223003136";
		String cPassword = "1223003136";
		long l = userService.userRegister(account, password, cPassword);
		System.out.println(l);
	}
}
