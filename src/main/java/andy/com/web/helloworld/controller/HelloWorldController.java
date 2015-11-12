package andy.com.web.helloworld.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import andy.com.web.helloworld.service.HelloWorldService;
import andy.com.web.helloworld.serviceImpl.HelloWorldServiceImpl;

@Controller
public class HelloWorldController {
	
	//@Autowired
	private HelloWorldService helloWorldService = new HelloWorldServiceImpl();
	
	@RequestMapping("helloworld")
	public String getName(@RequestParam("userName") String userName,  HttpServletRequest request)
	{
		  String newUserName = helloWorldService.getNewName(userName);
          request.setAttribute("newUserName", newUserName+"");
          return "helloworld";
	}
}
