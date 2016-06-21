/**
 * 
 */
package com.coalesceworks.async.transports.util

/**
 * @author Sandeep Kotha
 *
 */
interface VerticleAddresses {

	String FILE_TO_HTTP_OUTBOUND_VERTICLE='com.coalesceworks.transport.files.FileToHttpOutboundVerticle'
	String READ_DIR_VERTICLE='com.coalesceworks.transport.files.ReadDirVerticle'
	String HTTP_INBOUND_VERTICLE='com.coalesceworks.transport.http.HttpInboundVerticle'
	String POST_TO_HTTPS_EPR='com.coalesceworks.transport.https.PostToHttpsEPR'
	String WRITE_TO_FILE_VERTICLE = 'com.coalesceworks.transport.files.WriteToFileVerticle'
	String POST_TO_HTTP_EPR = 'com.coalesceworks.transport.http.PostToHttpEPR'
}
