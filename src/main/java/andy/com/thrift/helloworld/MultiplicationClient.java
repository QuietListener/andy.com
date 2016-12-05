package andy.com.thrift.helloworld;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class MultiplicationClient {

	final static String Host = "localhost";
	final static int Port = 19090;

	public static void main(String[] args) {

		try {
			  TTransport transport = new TSocket(Host,Port);
		      transport = new TFramedTransport(transport);
		      transport.open();
				
		      TProtocol protocol = new TCompactProtocol(transport);		      
			  MultiplicationService.Client client = new MultiplicationService.Client(	protocol);
			  perform(client);

			transport.close();
		} catch (TException x) {
			x.printStackTrace();
		}
		 catch (Exception x) {
				x.printStackTrace();
			}
	}

	private static void perform(MultiplicationService.Client client)throws TException {

		int product = client.multiply(3, 5);
		System.out.println("3*5=" + product);
	}
}