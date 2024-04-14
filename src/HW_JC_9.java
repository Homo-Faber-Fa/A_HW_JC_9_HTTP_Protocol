
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.List;


public class HW_JC_9 {
    public static void main(String[] args) throws IOException {
        // создание клиента для работы по http Вадим

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();


        // создание объекта запроса с произвольными заголовками
        HttpGet request = new HttpGet("https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats");
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());


        // отправка запроса
        CloseableHttpResponse response = httpClient.execute(request);


        // преобразование в объекты
        response = httpClient.execute(request);
        
        ObjectMapper objectMapper = new ObjectMapper();
        List<Post> posts = objectMapper.readValue(

                response.getEntity().getContent(), new TypeReference<>() {
                });

        posts.stream().filter(x -> x.getUpvotes() != 0)
                .forEach(System.out::println);
    }

    public record Post(String id, String text, String type, String user, int upvotes) {
        public int getUpvotes() {
            return upvotes;
        }
    }
}
