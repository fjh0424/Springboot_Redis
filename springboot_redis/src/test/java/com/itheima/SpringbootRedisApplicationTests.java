package com.itheima;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.repository.UserRepository;
import com.itheima.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = SpringbootRedisApplication.class)
@RunWith(SpringRunner.class)
class SpringbootRedisApplicationTests {

	@Autowired
	private UserRepository repository;

	@Autowired
	private RedisTemplate<String,String> redisTemplate;

	@Autowired
	private ObjectMapper objectMapper;
	@Test
	public void testFlush(){
		Boolean users = redisTemplate.delete("users");
		System.out.println("删除是否成功？"+users);
	}
	@Test
	public void textGetOne() throws Exception{

		/**
		 * 使用redis来获取一个对象
		 */
		//弹出一个template
		String users = redisTemplate.boundValueOps("users").get();
		if(users==null){
			List<User> all = repository.findAll();
			String s = objectMapper.writeValueAsString(all);
			redisTemplate.boundValueOps("users").set(s);
			System.out.println("-----------从数据库获取数据---------");
			System.out.println(all);
		}else{
			System.out.println("-----------从redis获取数据----------");
			System.out.println(users);
		}







	}

}
