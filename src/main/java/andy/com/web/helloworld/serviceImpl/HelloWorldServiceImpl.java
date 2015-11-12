package andy.com.web.helloworld.serviceImpl;

import andy.com.web.helloworld.service.HelloWorldService;

public class HelloWorldServiceImpl implements HelloWorldService {

	@Override
	public String getNewName(String userName) {
		return "hello world "+userName;
	}


}
