package com.wyj.test.kafka.simple;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * @author wuyingjie
 * Created on 2020-02-02
 */
public class SimpleProducer {

    public static void main(String[] args) {

        Properties props = new Properties();

        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        // 每个partition 发送一个批次的大小，byte
        props.put("batch.size", 10240);

        // 会将两个请求发送时间间隔内到达的记录合并到一个单独的批处理请求中
        // 10ms 内达到的请求 如果没达到 batch.size 就合并一起发送
        props.put("linger.ms", 10);

        // 缓冲等待被发送到服务器的记录的总字节数。如果记录发送的速度比发送到服务器的速度快， Producer 就会阻塞，
        // 如果阻塞的时间超过 max.block.ms 配置的时长，则会抛出一个异常。
        props.put("buffer.memory", 33554432);

        //send 方法最长的阻塞时间 30s
        props.put("max.block.ms", 30000);

        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        for (int i=0; i<1; i++) {
            // send 方法是异步的
            // 调用该方法会将消息 放入到 pending record的buffer中，并立刻返回
            // 如果客户端发送的速度超过了 提交给 kafka 服务器的速度，buffer 将会满，
            //  如果满了，send 方法就会被阻塞，超过了一定的(配置)时间，就会TimeOutException
            producer.send(new ProducerRecord<String, String>("is_onboarding_new_event_notice_dev", Integer.toString(i), Integer.toString(i)));
        }

        producer.close();

    }

    /**
     * 配置幂等性</br>
     * 数学中指 f(x)=f(f(x)) </br>
     * 编程中指 对同一个系统，使用同样的条件，一次请求和重复的多次请求对系统资源的影响是一致的</br>
     * 幂等性通常有两个维度，一是对象，二是时间</br>
     * https://www.jianshu.com/p/475589f5cd7b
     * @param props
     */
    private void configIdempotence(Properties props) {
        // If the request fails, the producer can automatically retry
        // 注意这里配置的是 request 失败，而不是说这次的 send，一次request 可能是多次send
        // 另外，retries 参数 比较复杂，涉及的其他参数较多，可以去这里看 http://kafka.apache.org/documentation.html#semantics
        // 如果发送失败重试3次
        // 幂等性配置后，该参数默认为 Integer.MAX_VALUE
        props.put("retries", "3");

        // 打开幂等性配置，保证每个消息在 Stream 中只写入一个副本
        props.put("enable.idempotence", true);

        // 幂等性的配置要求，acks 必须为all
        props.put("acks", "all");


        // 上面的幂等性 保证了消息一定只有一条，但是 因为retires 可能导致 顺序不一
        // 所以加上此参数，保证 消息的顺序就是发送的顺序。
        //
        // 在发生阻塞之前，客户端的一个连接上允许出现未确认请求的最大数量。
        // 注意，如果这个设置大于1，并且有失败的发送，则消息可能会由于重试而导致重新排序(如果重试是启用的话)。
        // 为了保持幂等性，所以 如果有失败的就让 新来的等等，先让失败的发送
        props.put("max.in.flight.requests.per.connection", 0);
    }

}
