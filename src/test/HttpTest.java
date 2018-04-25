import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

public class HttpTest {
    @Test
    public void testHttp(){
        HttpGet httpGet=new HttpGet("http://210.42.121.241/");
        CloseableHttpClient httpClient=HttpClients.createDefault();
        try {
            CloseableHttpResponse response=httpClient.execute(httpGet);
            System.out.println(response.getFirstHeader("Set-Cookie").getValue());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testGetImage(){
        byte []imgByte=null;
        HttpGet httpGet=new HttpGet("http://210.42.121.241/servlet/GenImg");
        CloseableHttpClient httpClient=HttpClients.createDefault();
        try {
            CloseableHttpResponse response=httpClient.execute(httpGet);
            imgByte=EntityUtils.toByteArray(response.getEntity());
            System.out.println(response.getFirstHeader("Set-Cookie").getValue());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i <imgByte.length ; i++) {
            System.out.print(" "+imgByte[i]);
            if(i%10==0) System.out.println();
        }
    }
    @Test
    public void testReplace(){
        String str="E:\\Tomcat\\Tomcat\\img\\check.jpg";
        System.out.println(str.replace("\\","/"));
    }
}
