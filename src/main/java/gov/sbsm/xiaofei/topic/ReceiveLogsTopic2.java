/**
 * TODO
 * 
 */
package gov.sbsm.xiaofei.topic;

/**
 * @author hushuang
 *
 */
import com.rabbitmq.client.*;
import java.io.IOException;

public class ReceiveLogsTopic2 {

	private static final String EXCHANGE_NAME = "CeHuiShiZhuCe";
	private static final String QUEUE_NAME = "CeHuiShi.Registration_Education_QUEUE";
	 
	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("10.10.19.120");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
//		声明一个匹配模式的交换器
		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		//String queueName = channel.queueDeclare().getQueue();
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		// 路由关键字
		String[] routingKeys = new String[]{"CeHuiShi.Registration"};
//		绑定路由关键字
		for (String bindingKey : routingKeys) {
			channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, bindingKey);
			System.out.println("ReceiveLogsTopic2 exchange:"+EXCHANGE_NAME+", queue:"+QUEUE_NAME+", BindRoutingKey:" + bindingKey);
		}

		System.out.println("ReceiveLogsTopic2 [*] Waiting for messages. To exit press CTRL+C");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println("ReceiveLogsTopic2 [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
			}
		};
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}
}