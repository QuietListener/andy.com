/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package andy.com.activemq.helloword;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;
//
//import org.apache.activemq.ActiveMQConnection;
//import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer1 {

    public static void main(String []args) throws Exception {
//    	 // ConnectionFactory ：连接工厂，JMS 用它创建连接  
//        ConnectionFactory connectionFactory;  
//        // Connection ：JMS 客户端到JMS Provider 的连接  
//        Connection connection = null;  
//        // Session： 一个发送或接收消息的线程  
//        Session session;  
//        // Destination ：消息的目的地;消息发送给谁.  
//        Destination destination;  
//        // 消费者，消息接收者  
//        MessageConsumer consumer;  
//        connectionFactory = new ActiveMQConnectionFactory(  
//                ActiveMQConnection.DEFAULT_USER,  
//                ActiveMQConnection.DEFAULT_PASSWORD, "tcp://www.coderlong.com:61616");  
//        try {  
//            // 构造从工厂得到连接对象  
//            connection = connectionFactory.createConnection();  
//            // 启动  
//            connection.start();  
//            // 获取操作连接  
//            session = connection.createSession(Boolean.FALSE,  
//                    Session.AUTO_ACKNOWLEDGE);  
//            // 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置  
//            destination = session.createQueue("test");  
//            consumer = session.createConsumer(destination);  
//            while (true) {  
//                // 设置接收者接收消息的时间，为了便于测试，这里谁定为100s  
//            	 BytesMessage message = ( org.apache.activemq.command.ActiveMQBytesMessage)consumer.receive(100000); 
//                 byte [] bytes = new byte[(int) message.getBodyLength()];
//                 message.reset();
//                 message.readBytes(bytes);
//                 System.out.println(new String(bytes));
//            }  
//        } catch (Exception e) {  
//            e.printStackTrace();  
//        } finally {  
//            try {  
//                if (null != connection)  
//                    connection.close();  
//            } catch (Throwable ignore) {  
//            }  
//        }  
    }  
}