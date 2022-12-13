package id.skaynix.ecommerce.response;

import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    private static String SIGNATURE;

    @Value("${signature}")
    public void setSignature(String signature) {
        ResponseHandler.SIGNATURE = signature;
    }

    private static Gson gson = new Gson();

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("code", status.value());
        map.put("data", responseObj);

        String textHash = SIGNATURE + gson.toJson(responseObj);
        String encodedHash = Hashing.sha256().hashString(textHash, StandardCharsets.UTF_8).toString();

        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.set("text", textHash);
        responseHeader.set("signature", encodedHash);

        return new ResponseEntity<Object>(responseObj, responseHeader, status);
    }
}
