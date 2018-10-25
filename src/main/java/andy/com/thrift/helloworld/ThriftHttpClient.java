package andy.com.thrift.helloworld;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.THttpClient;

import java.util.Date;
import org.apache.thrift.transport.*;

public class ThriftHttpClient {

    public static void main(String [] args) throws Exception
    {
        String ip = "127.0.0.1";
        long time = new Date().getTime();
        int port = 8081;
        String uri = "/rpc/user_study";
        String method = "update_done_data";
        String accessToken = "ExYF0SvWmDtxALNXuE3wHaY7n8UyD1Qcn/mK8MzYyMA";
        String url = "http://"+ip+":"+port+uri+"/"+method+"/"+time;

        THttpClient c = new THttpClient(url);
        c.setCustomHeader("Cookie","access_token="+accessToken);
        TFramedTransport transport = new TFramedTransport(c);
        TCompactProtocol protocol = new TCompactProtocol(transport);

        MultiplicationService.Client client = new MultiplicationService.Client(protocol);
    }
}
