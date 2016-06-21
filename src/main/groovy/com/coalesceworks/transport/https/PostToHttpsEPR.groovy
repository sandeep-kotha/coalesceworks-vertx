/**
 * 
 */
package com.coalesceworks.transport.https
import com.coalesceworks.async.transports.util.AsyncTransportUtils;
import com.coalesceworks.async.transports.util.VerticleAddresses;

import io.vertx.groovy.core.buffer.Buffer;
import io.vertx.groovy.core.http.HttpClient
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.AbstractVerticle;


import io.vertx.core.json.JsonObject
/**
 * @author Sandeep Kotha
 *
 */
class PostToHttpsEPR extends AbstractVerticle {

	public void start() throws Exception {
		vertx.eventBus().consumer(VerticleAddresses.POST_TO_HTTPS_EPR, {message ->
			sendOverHttps(message.body())
		})
		
	}
	
	private void sendOverHttps(final JsonObject message)
	{
		String em = message.getString('em')
		String msgId = message.getString('messageId')
		String resourceId = message.getString('resourceId')
		String dateTime = String.valueOf(new Date().format("yyyy-MM-dd'T'HH:mm:ss.SSS"))
		vertx.eventBus().send(VerticleAddresses.WRITE_TO_FILE_VERTICLE,
			new JsonObject(['file':'C:\\sandeep\\vagrant\\uat2-req.log','payload' : "REQ,${dateTime},${msgId},${resourceId}".toString()]))
		def client = vertx.createHttpClient(new HttpClientOptions().setSsl(true).setTrustAll(true).setVerifyHost(false).setConnectTimeout(30000));
		client.post(443, 'internal-AWS19-ES-connector-uat-int-elb-635669240.us-east-1.elb.amazonaws.com',"/rest-facade/publish-v2/message", { response ->
				String dt = String.valueOf(new Date().format("yyyy-MM-dd'T'HH:mm:ss.SSS"))
				vertx.eventBus().send(VerticleAddresses.WRITE_TO_FILE_VERTICLE,
				new JsonObject(['file':'C:\\sandeep\\vagrant\\uat2.csv','payload':"REQ-${dateTime},RES-${dt},${msgId},${resourceId},${response.statusCode()}".toString()]))
			
		  }).putHeader("content-type", "text/xml").putHeader("Content-Length", String.valueOf(em.length())).
			  putHeader("Authorization", "Basic dXNlcjphbnl0aGluZw==").write(em)			
	}
	
}
