package andy.com.springCloud.feign;
import feign.*;
import feign.gson.GsonDecoder;


interface ApiService {
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @RequestLine("GET /service/getIpInfo.php?ip={ip}")
    FeignService.IpInfo getIpInfo(@Param("ip") String ip);
}
public class FeignService{

    public static void main(String [] args)
    {
        ApiService apiService = Feign.builder()
                .decoder(new GsonDecoder())
                .options(new Request.Options(1000, 2000))
                .retryer(new Retryer.Default(1000, 1000, 1))
                .target(ApiService.class, "http://ip.taobao.com");

        apiService.getIpInfo("127.0.0.1");
    }



    static class IpInfo {

        private String ip;
        private String host;
        private String country;
        private String country_id;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCountry_id() {
            return country_id;
        }

        public void setCountry_id(String country_id) {
            this.country_id = country_id;
        }

        @Override
        public String toString() {
            return "IpInfo{" +
                    "ip='" + ip + '\'' +
                    ", host='" + host + '\'' +
                    ", country='" + country + '\'' +
                    ", country_id='" + country_id + '\'' +
                    '}';
        }
    }
}


