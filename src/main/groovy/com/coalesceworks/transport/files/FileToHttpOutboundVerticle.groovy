/**
 * 
 */
package com.coalesceworks.transport.files

import com.coalesceworks.async.transports.util.AsyncTransportUtils;

import io.vertx.core.AbstractVerticle
import io.vertx.groovy.core.buffer.Buffer;
import io.vertx.groovy.core.http.HttpClient
import io.vertx.core.http.HttpClientOptions;


/**
 * @author Sandeep Kotha
 *
 */
class FileToHttpOutboundVerticle extends AbstractVerticle {

	public void start() throws Exception {
		//EventBus eb = vertx.eventBus();
		vertx.eventBus().consumer("com.coalesceworks.transport.files.FileToHttpOutboundVerticle", {message ->
			sendFileOverHttp(message.body())
		})
		
	}
	
	private void sendFileOverHttp(final String fileName) {
		println fileName
		vertx.fileSystem().props(fileName, { props ->			
		if(AsyncTransportUtils.INSTANCE.getResult(props)?.isRegularFile())
			vertx.fileSystem().readFile(fileName, { content ->				
				def buffer = AsyncTransportUtils.INSTANCE.getResult(content);				
				
				def client = vertx.createHttpClient(new HttpClientOptions().setSsl(true).setTrustAll(true).setVerifyHost(false));
				client.post(45003, 'localhost',"/rest-facade/message", { response ->
					
					println("Received response with status code ${response.statusCode()}")
					//println(response.grep())				
				  }).putHeader("content-type", "text/xml").putHeader("Content-Length", String.valueOf(buffer.length())).
			  		putHeader("Authorization", "Basic dXNlcjphbnl0aGluZw==").write(buffer)
				println('http post complete')
			})			
		})
	}

}
