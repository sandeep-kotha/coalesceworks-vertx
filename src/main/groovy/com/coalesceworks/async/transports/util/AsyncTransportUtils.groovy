/**
 * 
 */
package com.coalesceworks.async.transports.util

import io.vertx.core.Handler

/**
 * @author Sandeep Kotha
 *
 */
enum AsyncTransportUtils {

	INSTANCE;
	
	def getResult(Handler handler)
	{
		handler.succeeded() ? handler.result() : null
	}
	
}
