package com.ssafy.puzzlepop.engine;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

//TODO
//세션 완전히 닫혔을 때 리소스 안잡아먹도록 해야함.
@Component
public class SubscriptionManager {

    // URL과 해당 URL을 구독하는 클라이언트들의 연결 수를 저장하는 맵
    private final Map<String, Integer> subscriptionCountMap = new HashMap<>();

    // URL을 구독하는 클라이언트가 추가될 때 호출되는 메서드
    public synchronized void addSubscription(String url) {
        System.out.println("SubscriptionManager.addSubscription");
        subscriptionCountMap.put(url, subscriptionCountMap.getOrDefault(url, 0) + 1);
    }

    // URL을 구독하는 클라이언트가 해지될 때 호출되는 메서드
    public synchronized void removeSubscription(String url) {
        if (subscriptionCountMap.containsKey(url)) {
            int count = subscriptionCountMap.get(url) - 1;
            if (count <= 0) {
                subscriptionCountMap.remove(url);
            } else {
                subscriptionCountMap.put(url, count);
            }
        }
    }

    // URL에 대한 구독자 수를 가져오는 메서드
    public synchronized int getSubscriptionCount(String url) {
        return subscriptionCountMap.getOrDefault(url, 0);
    }
}

