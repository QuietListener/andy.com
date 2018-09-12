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

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.fusesource.stomp.jms.StompJmsConnectionFactory;
import org.fusesource.stomp.jms.StompJmsDestination;

class Consumer {

    public static void main(String []args) throws JMSException {
    	
        StompJmsConnectionFactory factory = new StompJmsConnectionFactory();
        factory.setBrokerURI("tcp://" + "www.coderlong.com" + ":" + 61613);
        Connection connection = factory.createConnection("admin", "admin");
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination dest = new StompJmsDestination("/queue/test1");

        MessageConsumer consumer = session.createConsumer(dest);
        long start = System.currentTimeMillis();
        long count = 1;
        System.out.println("Waiting for messages...");
        while(true) {
            Message msg = consumer.receive();
            
            if( msg instanceof  TextMessage ) {
                String body = ((TextMessage) msg).getText();
                System.out.println(body);
                count++;
                if( "SHUTDOWN".equals(body)) {
                    long diff = System.currentTimeMillis() - start;
                    System.out.println(String.format("Received %d in %.2f seconds", count, (1.0*diff/1000.0)));
                    break;
                }               
            } else {
                System.out.println("Unexpected message type: "+msg.getClass());
            }
        }
        connection.close();
    }
}