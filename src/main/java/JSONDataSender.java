import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import utils.Constants;

import java.io.*;

class JSONDataSender {

    private Game game;

    JSONDataSender(Game game) {
        this.game = game;
    }

    void sendData() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("GAMER", game.getName().replace('\u2014', '-'));
        String jsonData = jsonObject.toString();
        this.executeReq(jsonData, this.createConnectivity(Constants.DATA_VIEWER_PAGE));
    }

    private HttpPost createConnectivity(String restUrl) {
        HttpPost post = new HttpPost(restUrl);
        post.setHeader("Content-Type", "application/json; charset=utf-8");
        post.setHeader("Accept", "application/json");
        post.setHeader("X-Stream", "true");
        return post;
    }

    private void executeReq(String jsonData, HttpPost httpPost) {
        try {
            executeHttpRequest(jsonData, httpPost);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error while encoding api url : " + e);
        } catch (IOException e) {
            System.out.println("IoException occured while sending http request : " + e);
        } catch (Exception e) {
            System.out.println("Exception occured while sending http request : " + e);
        } finally {
            httpPost.releaseConnection();
        }
    }

    private void executeHttpRequest(String jsonData, HttpPost httpPost) throws IOException {
        HttpResponse response;
        String line;
        StringBuilder result = new StringBuilder();
        httpPost.setEntity(new StringEntity(jsonData));
        HttpClient client = HttpClientBuilder.create().build();
        response = client.execute(httpPost);
        System.out.println("Post parameters : " + jsonData);
        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        System.out.println(result.toString());
    }
}
