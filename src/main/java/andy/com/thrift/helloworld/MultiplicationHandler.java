package andy.com.thrift.helloworld;

import org.apache.thrift.TException;

public class MultiplicationHandler implements MultiplicationService.Iface{

	public int multiply(int n1, int n2) throws TException {
		
		return n1 * n2;
	}

}
