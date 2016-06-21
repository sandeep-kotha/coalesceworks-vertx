package com.coalesceworks.transport.files


import io.vertx.core.AbstractVerticle
import com.coalesceworks.async.transports.util.VerticleAddresses
import io.vertx.groovy.core.Vertx;
import io.vertx.groovy.core.buffer.Buffer;
import io.vertx.groovy.core.eventbus.EventBus

/**
 * @author Sandeep Kotha
 *
 */
class ReadDirVerticle extends AbstractVerticle {

	/*private final Vertx vertx;
	public ReadDirVerticle(Vertx vertx)
	{
		this.vertx = vertx;
	}*/
	
	
	public void start() throws Exception {
		//EventBus eb = vertx.eventBus();
		
	  
		vertx.eventBus().consumer(VerticleAddresses.READ_DIR_VERTICLE, {message ->
			println "recieved message "+message.body()
			readDir(message.body())			
		})
		
	}
	
	
	
	private void readDir(final String filedir) {
		//String filedir ="C:\\Users\\Sandeep Kotha\\Documents\\em\\"
		vertx.fileSystem().readDir(filedir, { result ->
			if (result.succeeded())
			{
				result.result().each { it ->
					vertx.eventBus().send(VerticleAddresses.FILE_TO_HTTP_OUTBOUND_VERTICLE, it, null)
				}
				println(result.result().getClass());
			} else {
				System.err.println("Oh oh ..." + result.cause());
			}
			
		})
		println "done"
	}
	
	
	
}
