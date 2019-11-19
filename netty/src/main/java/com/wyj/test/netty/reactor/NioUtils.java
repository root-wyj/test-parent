package com.wyj.test.netty.reactor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created
 * Author: wyj
 * Date: 2019/11/19
 */
public class NioUtils {

    public static void write(SocketChannel socketChannel, byte[] data) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(data.length);
        buffer.clear();
        buffer.put(data);
        buffer.flip();

        while (buffer.hasRemaining()) {
            socketChannel.write(buffer);
        }
    }

    public static byte[] read(SocketChannel socketChannel) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int length = 0;
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.clear();
        while ((length = socketChannel.read(buffer)) > 0) {
            buffer.flip();
            byte[] tmp = new byte[length];
            buffer.get(tmp);
            baos.write(tmp);
            buffer.clear();
            // 这里并不知道为什么，有时候 读完了，就啥都没有了。。。 并不会进入到下一轮的循环中
            if (length != buffer.capacity()) {
                break;
            }
        }

        if (length == -1) {
            return null;
        }

        byte[] ret = baos.toByteArray();
        baos.close();
        return ret;
    }

    public static byte[] sendAndRecv(SocketChannel socketChannel, byte[] data) throws IOException {
        write(socketChannel, data);
        return read(socketChannel);
    }
}
