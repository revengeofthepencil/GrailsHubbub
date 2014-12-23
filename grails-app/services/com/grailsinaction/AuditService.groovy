package com.grailsinaction

import javax.sql.rowset.spi.TransactionalWriter;

class AuditService {

	static transactional = false
	
	@grails.events.Listener
	def onNewPost(Post newPost) {
		log.error("oh yeah! New post is from ${newPost.user.loginId} " + 
			" with content ${newPost.shortContent}")
	}
}
