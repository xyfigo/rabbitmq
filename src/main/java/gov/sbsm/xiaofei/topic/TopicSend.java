/**
 * TODO
 * 
 */
package gov.sbsm.xiaofei.topic;

/**
 * @author hushuang
 *
 */
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

public class TopicSend {

	private static final String EXCHANGE_NAME = "CeHuiShiZhuCe";

	public static void main(String[] argv) {
		Connection connection = null;
		Channel channel = null;
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("10.10.19.120");

			connection = factory.newConnection();
			channel = connection.createChannel();
//			声明一个匹配模式的交换器
			channel.exchangeDeclare(EXCHANGE_NAME, "topic");

			// 待发送的消息
			String[] routingKeys = new String[]{"CeHuiShi.Registration",
												"CehuiShi.Education"};
//			发送消息
	        for(String severity :routingKeys){
	        	String message = "From "+severity+" routingKey' s message!";
	        	channel.basicPublish(EXCHANGE_NAME, severity, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
	        	System.out.println("TopicSend [x] Sent '" + severity + "':'" + message + "'");
	        }
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception ignore) {
				}
			}
		}
	}
}