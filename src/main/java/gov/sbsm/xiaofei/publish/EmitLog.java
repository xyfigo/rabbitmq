/**
 * TODO
 * 
 */
package gov.sbsm.xiaofei.publish;

/**
 * @author hushuang
 *
 */

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLog {

    private static final String EXCHANGE_NAME = "cehuishi.zhuce";
    private static final String TRS_QUEUE_NAME = "cehuishi_TRS_queue";
    private static final String JiXuJiaoYu_QUEUE_NAME = "cehuishi_JiXuJiaoYu_queue";

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("10.10.19.120");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        //channel.queueDeclare(TRS_QUEUE_NAME, true, false, false, null);
        //channel.queueDeclare(JiXuJiaoYu_QUEUE_NAME, true, false, false, null);

//		分发消息
		for(int i = 0 ; i < 5; i++){
			String message = "Hello World! " + i;
			 channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
		     System.out.println(" [x] Sent '" + message + "'");
		}
        channel.close();
        connection.close();
    }
}
