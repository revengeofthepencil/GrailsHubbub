package com.grailsinaction

import javax.sql.rowset.spi.TransactionalWriter;

class AuditService {

	static transactional = false
	
	@grails.events.Listener
	def onNewPost(Post newPost) {
		log.error('got it! yeah! woo hoo!')
	}
}
