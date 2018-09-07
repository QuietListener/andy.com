package andy.com.web.springmvc.helloworld.serviceImpl;

import andy.com.web.springmvc.helloworld.service.HelloWorldService;

public class HelloWorldServiceImpl implements HelloWorldService {

	@Override
	public String getNewName(String userName) {
		return "hello world "+userName;
	}


}
