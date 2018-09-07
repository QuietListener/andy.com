package andy.com.thrift.helloworld;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;

public class MultiplicationServer {

	public static final int PORT = 19090;

	public static void main(String[] args) {
		try {

			MultiplicationHandler handler = new MultiplicationHandler();
			final TProcessor processor = new MultiplicationService.Processor<MultiplicationService.Iface>(handler);

			Runnable simple = new Runnable() {

				public void run() {
					runServer(processor);
				}

			};

			new Thread(simple).start();
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

	public static void runServer(TProcessor processor) {

		try {
			TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(PORT);
			TThreadedSelectorServer.Args tArgs = new TThreadedSelectorServer.Args(serverTransport);
			tArgs.maxReadBufferBytes = Integer.MAX_VALUE;
			tArgs.selectorThreads(10);
			tArgs.workerThreads(10);
			tArgs.processor(processor);
			tArgs.protocolFactory(new TCompactProtocol.Factory());
			TServer server = new TThreadedSelectorServer(tArgs);

			System.out.println("Starting the TThreadedSelectorServer server...");
			server.serve();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
