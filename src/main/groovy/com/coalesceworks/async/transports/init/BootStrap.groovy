/**
 * 
 */
package com.coalesceworks.async.transports.init

import com.coalesceworks.transport.files.ReadDirVerticle
import com.coalesceworks.async.transports.util.VerticleAddresses

import io.vertx.core.DeploymentOptions
import io.vertx.core.Verticle
import io.vertx.core.json.JsonObject
import io.vertx.groovy.core.Vertx
import io.vertx.groovy.core.eventbus.EventBus

/**
 * @author Sandeep Kotha
 *
 */
class BootStrap {

	static main(args) {
		Vertx vertx = Vertx.vertx();
		BootStrap bootStrap = new BootStrap()
		//Verticle readDirVerticle = new ReadDirVerticle(vertx);
		
		//bootStrap.startHttpServer(vertx)
		
		bootStrap.deployHttpVerticle(vertx, bootStrap);
		bootStrap.deployHttpsVerticle(vertx, bootStrap);
		vertx.deployVerticle(VerticleAddresses.WRITE_TO_FILE_VERTICLE,['instances':1],{res ->
			if (res.succeeded()) {
				println("Deployment id for $VerticleAddresses.WRITE_TO_FILE_VERTICLE is: " + res.result());
				vertx.executeBlocking({ future ->
					bootStrap.postReq(vertx, bootStrap);
				}, null)
			}
			else {
				println("Deployment for $VerticleAddresses.WRITE_TO_FILE_VERTICLE failed!");
			  }
		});
		
		
		
		
		println "deployment complete"
	}
	
	private void postReq(final Vertx vertx, BootStrap bootStrap)
	{
		
		/*String dt = String.valueOf(new Date().format("yyyy-MM-dd'T'HH:mm:ss.SSS"))
		vertx.eventBus().send(VerticleAddresses.WRITE_TO_FILE_VERTICLE,
			new JsonObject(['file':'C:\\sandeep\\vagrant\\uat2-req.log','payload' : "REQ,${dt},post Start".toString()]))*/
		for(int i=0; i<3000; i++)
		{
			String messageId = '303c79b0-f0c4-4ef0-88fb-0b0c263bdde8'
			String resourceId = new Date().time
			String em = bootStrap.getEM(resourceId)
			vertx.eventBus().send(VerticleAddresses.POST_TO_HTTP_EPR,
				new JsonObject(['messageId':messageId, 'resourceId':resourceId, 'em': em]))
		}
		/*String dateTime = String.valueOf(new Date().format("yyyy-MM-dd'T'HH:mm:ss.SSS"))
		vertx.eventBus().send(VerticleAddresses.WRITE_TO_FILE_VERTICLE,
			new JsonObject(['file':'C:\\sandeep\\vagrant\\uat2-req.log','payload' : "REQ,${dateTime},post Completed".toString()]))*/
	}
	
	private void deployHttpVerticle(final Vertx vertx, BootStrap bootStrap)
	{
		vertx.deployVerticle(VerticleAddresses.POST_TO_HTTP_EPR,['instances':16],{res ->
			if (res.succeeded()) {
			  
			  println("Deployment id for $VerticleAddresses.POST_TO_HTTP_EPR is: " + res.result());
			} else {
			  println("Deployment for $VerticleAddresses.POST_TO_HTTPS_EPR failed!");
			}
		  });
	}
	
	
	private void deployHttpsVerticle(final Vertx vertx, BootStrap bootStrap)
	{
		vertx.deployVerticle(VerticleAddresses.POST_TO_HTTPS_EPR,['instances':1],{res ->
			if (res.succeeded()) {
			  println("Deployment id for $VerticleAddresses.POST_TO_HTTPS_EPR is: " + res.result());
			  /*for(int i=0; i<3000; i++)
			  {
				  String messageId = '303c79b0-f0c4-4ef0-88fb-0b0c263bdde8'
				  String resourceId = new Date().time
				  String em = bootStrap.getEM(resourceId)
				  vertx.eventBus().send(VerticleAddresses.POST_TO_HTTPS_EPR,
					  new JsonObject(['messageId':messageId, 'resourceId':resourceId, 'em': em]))
			  }
			  String dateTime = String.valueOf(new Date().format("yyyy-MM-dd'T'HH:mm:ss.SSS"))
			  vertx.eventBus().send(VerticleAddresses.WRITE_TO_FILE_VERTICLE,
				  new JsonObject(['file':'C:\\sandeep\\vagrant\\uat2-req.log','payload' : "REQ,${dateTime},post Completed".toString()]))*/
			 
			} else {
			  println("Deployment for $VerticleAddresses.POST_TO_HTTPS_EPR failed!");
			}
		  });
	}
	
	private String getEM(String resourceId) {
		//String filedir ="C:\\Users\\Sandeep Kotha\\Documents\\em\\"
		
			
			def template = '<ns0:EnterpriseMessage xmlns:ns0="http://schemas.umusic.com/enterprise/services/2014/05"><ns0:EnterpriseHeader><ns0:MessageId>303c79b0-f0c4-4ef0-88fb-0b0c263bdde8</ns0:MessageId><ns0:ThreadId>85ab7224-dd31-4e98-b060-741f41cfb947</ns0:ThreadId><ns0:CreatedUtc>2016-06-08T16:24:21.521Z</ns0:CreatedUtc><ns0:Source>UMG.DSCHED</ns0:Source><ns0:Action>Publish</ns0:Action><ns0:Communication>Asynchronous</ns0:Communication><ns0:Priority>Regular</ns0:Priority><ns0:Resource><ns0:ResourceName>ReleaseSchedule</ns0:ResourceName><ns0:ResourceId>${resourceId}</ns0:ResourceId><ns0:ResourceCreatedUtc>2016-06-08T21:24:21.000Z</ns0:ResourceCreatedUtc></ns0:Resource><ns0:Custom><OHIObjectMetadata xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns="http://schemas.umusic.com/enterprise/services/migration/ohi/2015/02"><ObjectId>${resourceId}</ObjectId><ObjectHistoryId>53799223910</ObjectHistoryId><Deleted>0</Deleted><ExpirationDate>0001-01-01T00:00:00</ExpirationDate><UserName>system</UserName><UpdateDate>2016-06-08T16:24:21</UpdateDate><Namespace>UMG.DSCHED</Namespace><Originator>UMG.DSCHED</Originator><Name>ReleaseSchedule</Name><Version>1</Version></OHIObjectMetadata></ns0:Custom></ns0:EnterpriseHeader><ns0:MessageBody><ReleaseSchedule xmlns="http://schemas.digiplug.com"><UPC>00791558984132</UPC><CatalogNumber></CatalogNumber><InternalIdentifier>${resourceId}</InternalIdentifier><SequenceNumber>1</SequenceNumber><Status>DRAFT</Status><LabelCopyMetadata><LabelCopyReleaseId>31852081472</LabelCopyReleaseId><ArtistName>Alan Menken, Stephen Schwartz</ArtistName><Title>The Hunchback Of Notre Dame</Title><VersionTitle>Studio Cast Recording</VersionTitle><HasExplicitLyrics>false</HasExplicitLyrics><DSchedOwningCountry>US</DSchedOwningCountry><AccountId>0</AccountId><ProductConfiguration>223</ProductConfiguration></LabelCopyMetadata><CoreReleaseDate>2016-06-29</CoreReleaseDate><EarliestReleaseDate>2016-06-29</EarliestReleaseDate><HasDefaultMarketingRestriction>false</HasDefaultMarketingRestriction><HasDefaultPreOrder>false</HasDefaultPreOrder><IsPriority>false</IsPriority><CategoryOfDelivery>POP_CORE</CategoryOfDelivery><KeyContact>Mercer, Jackson (Concord Music)</KeyContact><Source>DiGS</Source><Management><ReleaseManagement>LOCALLY_MANAGED</ReleaseManagement></Management><LastUpdateDate>2016-06-08T16:27:28</LastUpdateDate><Countries><Country><CountryId>US</CountryId><LocalReleaseDate>2016-06-29</LocalReleaseDate><LocalExpiryDate xsi:nil="true" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" /><HasMarketingRestrictionException>true</HasMarketingRestrictionException><HasPreOrderException>false</HasPreOrderException><IsPriority>false</IsPriority><ReleasingFamilyId>22</ReleasingFamilyId><LocalLabelCopyMetadata><AccountId>0</AccountId><OrgStructure><CompanyId>27065</CompanyId><DivisionId>8274</DivisionId><LabelId>7160</LabelId></OrgStructure></LocalLabelCopyMetadata><Imprint>Ghostlight Records</Imprint><ProjectCode>0073-46413</ProjectCode><Source>DiGS</Source><LocalStatus>DRAFT</LocalStatus><IsOriginating>true</IsOriginating></Country><Country><CountryId>CA</CountryId><LocalReleaseDate>2016-06-29</LocalReleaseDate><LocalExpiryDate xsi:nil="true" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" /><HasMarketingRestrictionException>true</HasMarketingRestrictionException><HasPreOrderException>false</HasPreOrderException><IsPriority>false</IsPriority><ReleasingFamilyId>9</ReleasingFamilyId><LocalLabelCopyMetadata><AccountId>0</AccountId><OrgStructure><CompanyId>26889</CompanyId><DivisionId>8170</DivisionId><LabelId>1852</LabelId></OrgStructure></LocalLabelCopyMetadata><Imprint>Ghostlight Records</Imprint><ProjectCode>0073-41429</ProjectCode><Source>DiGS</Source><LocalStatus>DRAFT</LocalStatus><IsOriginating>false</IsOriginating></Country></Countries><Workflow><Step><StepName>LabelCopy</StepName><Completion>PARTIAL</Completion><SequenceNumber>1</SequenceNumber></Step><Step><StepName>LegalRights</StepName><Completion>COMPLETE</Completion><CompletionDate>2016-06-07</CompletionDate><SequenceNumber>2</SequenceNumber></Step><Step><StepName>BinaryAvailability</StepName><Completion>PARTIAL</Completion><SequenceNumber>3</SequenceNumber></Step><Step><StepName>MarketingRestrictions</StepName><Completion>NOT_INITIALIZED</Completion><SequenceNumber>4</SequenceNumber></Step></Workflow></ReleaseSchedule></ns0:MessageBody></ns0:EnterpriseMessage>'
			new groovy.text.SimpleTemplateEngine().createTemplate(template).make(['resourceId':resourceId]).toString()
		
	}
	
	private void deployReadDirVerticle(final Vertx vertx)
	{
		vertx.deployVerticle(VerticleAddresses.READ_DIR_VERTICLE, {res ->
			if (res.succeeded()) {
			  println("Deployment id $VerticleAddresses.READ_DIR_VERTICLE is: " + res.result());
			  vertx.eventBus().send(VerticleAddresses.READ_DIR_VERTICLE, "C:\\Users\\Sandeep Kotha\\Documents\\em1\\")
			} else {
			  println("Deployment failed!");
			}
		  });
	}
	
	/*private void startHttpServer(final Vertx vertx)
	{
		vertx.createHttpServer()
		.requestHandler({req -> req.response().end("Hello World!")})
		.listen(8080, {handler -> 
		  if (handler.succeeded()) {
			System.out.println("http://localhost:8080/");
		  } else {
			System.err.println("Failed to listen on port 8080");
		  }
		});
	
		vertx.createHttpServer().requestHandler({ request ->
			println request.getClass()
			request.response().end("Hello world")
		  }).listen(8080, 'localhost').close()
	}*/

}
