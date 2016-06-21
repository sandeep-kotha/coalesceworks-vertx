/**
 * 
 */
package com.coalesceworks.async.transports.jms

import io.vertx.core.AbstractVerticle
import org.apache.activemq.ActiveMQConnectionFactory
import javax.jms.*

/**
 * @author Sandeep Kotha
 *
 */
class JMSConsumer extends AbstractVerticle {

	public void start() throws Exception {
		//EventBus eb = vertx.eventBus();
		vertx.eventBus().consumer("com.coalesceworks.async.transports.jms.JMSConsumer", {message ->
			message.body().split(",").each {registerJMSConsumer(it)}
		})
		
	}
	
	private void registerJMSConsumer(final String queueName)
	{
		new ActiveMQConnectionFactory(brokerURL: brokerUrl).createConnection().with
		{
			start()
			createSession(false, Session.AUTO_ACKNOWLEDGE).with {
			 createConsumer(queueName).setMessageListener({ msg ->
				 println "Received ${counter.incrementAndGet()} messages"
			 } as MessageListener)
			 
			/* def message = createTextMessage(reader.text)			 
			  createProducer().send(createQueue(queueName), message)*/
			}
			close()
		  }
		
		queueName
	}
	
}
