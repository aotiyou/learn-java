package com.zxy.learnjava;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Message> received = List.of(
                new Message(1, "Hello!"),
                new Message(2, "发工资了吗？"),
                new Message(2, "发工资了吗？"),
                new Message(3, "去哪吃饭？"),
                new Message(3, "去哪吃饭？"),
                new Message(4, "Bye")
        );
        List<Message> displayMessages = process(received);
        for (Message message : displayMessages) {
            System.out.println(message.text);
        }
    }

    static List<Message> process(List<Message> received) {
        // TODO: 按sequence去除重复消息
        Set<Message> set = new TreeSet<>(new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                if(o1.sequence < o2.sequence) return -1;
                if(o1.sequence > o2.sequence) return 1;
                return 0;
            }
        });
        set.addAll(received);
        received = new ArrayList<>();
        received.addAll(set);
        return received;
    }
}

class Message {
    public final int sequence;
    public final String text;
    public Message(int sequence, String text) {
        this.sequence = sequence;
        this.text = text;
    }
}

