package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Value("${gemini.api.key}")
    private String apiKey;

    @PostMapping
    public Map<String, String> chat(@RequestBody Map<String, String> body) {
        String userMessage = body.get("message");

        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.6-flash:generateContent?key=" + apiKey;

        String systemPrompt = "Bạn là trợ lý tư vấn món ăn cho quán TUY FOODS. " +
                "Các món có: Chân gà sốt Thái 45k, Nem chua rán 40k, " +
                "Trà sữa cốm 55k, Gà rán 55k, Sinh tố bơ 40k, Trà chanh 15k... " +
                "Hãy gợi ý món phù hợp với yêu cầu của khách. Trả lời ngắn gọn bằng tiếng Việt.";

        Map<String, Object> requestBody = new HashMap<>();
        List<Map<String, Object>> contents = new ArrayList<>();

        Map<String, Object> content = new HashMap<>();
        List<Map<String, String>> parts = new ArrayList<>();
        parts.add(Map.of("text", systemPrompt + "\nKhách hỏi: " + userMessage));
        content.put("parts", parts);
        contents.add(content);
        requestBody.put("contents", contents);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
            Map responseBody = response.getBody();
            List candidates = (List) responseBody.get("candidates");
            Map candidate = (Map) candidates.get(0);
            Map contentMap = (Map) candidate.get("content");
            List partsList = (List) contentMap.get("parts");
            Map firstPart = (Map) partsList.get(0);
            String reply = (String) firstPart.get("text");
            return Map.of("reply", reply);
        } catch (Exception e) {
            return Map.of("reply", "Lỗi: " + e.getMessage());
        }
    }
}