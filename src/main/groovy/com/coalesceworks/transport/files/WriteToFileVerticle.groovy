/**
 * 
 */
package com.coalesceworks.transport.files

import com.coalesceworks.async.transports.util.VerticleAddresses;

import io.vertx.core.AbstractVerticle
import io.vertx.core.json.JsonObject;
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

/**
 * @author Sandeep Kotha
 *
 */
class WriteToFileVerticle extends AbstractVerticle {

	public void start() throws Exception {
		//EventBus eb = vertx.eventBus();
		vertx.eventBus().consumer(VerticleAddresses.WRITE_TO_FILE_VERTICLE, {message ->
			//println('******************************* message'+message.body())
			//println('******************************* message :: '+ (message.body().getClass()))
			writeToFile(message.body())
		})
		
	}

	private void writeToFile(final JsonObject message)
	{
		Files.write(Paths.get(message.getString('file')), (message.getString('payload')+"\n").getBytes(),
			StandardOpenOption.APPEND);
	}
}
