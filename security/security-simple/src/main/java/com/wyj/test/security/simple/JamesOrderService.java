package com.wyj.test.security.simple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

/**
 * @author wuyingjie
 * Created on 2020-03-31
 */
public class JamesOrderService {

    private LinkedBlockingQueue<OrderRequest> queue = new LinkedBlockingQueue<>();

    public String queryOrderInfo(String orderCode) throws ExecutionException, InterruptedException {
        String serialNo = UUID.randomUUID().toString();
        OrderRequest request = new OrderRequest();
        request.serialNo = serialNo;
        request.orderCode = orderCode;
        request.future = new CompletableFuture<>();

        queue.add(request);

        return request.future.get();
//        return doQueryOrderInfo(orderCode);
    }

    private String doQueryOrderInfo(String orderCode) {
        return "";
    }

    private Map<String, String> doQueryOrderInfoBatch(List<Map<String, String>> batchOrderCode) {
        return new HashMap<>();
    }

    @PostConstruct
    public void init() {
        ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(1);
        scheduled.scheduleAtFixedRate(() -> {
            int size = queue.size();
            if (size == 0) {
                return;
            }

            List<OrderRequest> requestList = new ArrayList<>();
            List<Map<String, String>> batchParams = new ArrayList<>();
            for (int i=0; i<size; i++) {
                OrderRequest requestItem = queue.poll();
                requestList.add(requestItem);
                Map<String, String> mapItem = new HashMap<>();
                mapItem.put("orderCode", requestItem.orderCode);
                mapItem.put("serialNo", requestItem.serialNo);
                batchParams.add(mapItem);
            }

            System.out.println("size:"+size);

            Map<String, String> responses = doQueryOrderInfoBatch(batchParams);

            for (OrderRequest request : requestList) {
                String responseItem = responses.get(request.serialNo);
                if (responseItem != null) {
                    request.future.complete(responseItem);
                }
            }

        }, 0, 10, TimeUnit.MINUTES);
    }

    private static class OrderRequest {
        String orderCode;
        String serialNo;
        CompletableFuture<String> future;
    }
}
