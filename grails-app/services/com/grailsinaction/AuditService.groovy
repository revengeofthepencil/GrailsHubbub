package com.grailsinaction

import javax.sql.rowset.spi.TransactionalWriter;

class AuditService {

	static transactional = false
	
	@grails.events.Listener
	def onNewPost(Post newPost) {
		String logMessage = "oh yeah! New post is from ${newPost.user.loginId} " + 
			" with content ${newPost.shortContent}"
		log.error(logMessage)
		def auditEntry = new AuditEntry(
			message: logMessage,
			userId: newPost.user.loginId
			)
		
		auditEntry.addToTags(new AuditTag(name: "post"))
		auditEntry.addToTags(new AuditTag(name: "create"))
		auditEntry.addToTags(new AuditTag(name: "user-driven"))
		auditEntry.details = newPost.properties['userId',
			'shortContent', 'dateCreated']
		auditEntry.machineName = InetAddress.localHost.hostName
		
		def dynamicProps = [
			"os-name"       : System.getProperty("os.name"),
			"os-version"    : System.getProperty("os.version"),
			"os-java"       : System.getProperty("java.version")
		]
		dynamicProps.each { key, value ->
			auditEntry[key] = value
		}
		
		auditEntry.save(failOnError: true)
		
	}
}
