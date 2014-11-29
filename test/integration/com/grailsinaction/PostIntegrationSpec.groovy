package com.grailsinaction

import grails.test.spock.IntegrationSpec

class PostIntegrationSpec extends IntegrationSpec {
	
	void "adding posts to user links posts correctly"() {
		given: "A brand spanking new user"
		def user = new User(loginId: 'tomwaits', password: 'secret')
		user.save(failOnError: true)
		
		when: "Posts are added to user"
		user.addToPosts(new Post(comment: 'hey you!'))
		user.addToPosts(new Post(comment: 'hey us!'))
		user.addToPosts(new Post(comment: 'hey them!'))
		
		then: "The user has posts attached"
		3 == User.get(user.id).posts.size()
		
	}
	
    void "posts linked to a user can be retrieved"() {
		given: "A user with multiple posts"
		def user = new User(loginId: 'tomwaits', password: 'secret')
		user.save(failOnError: true)
		user.addToPosts(new Post(content: "A Post"))
		user.addToPosts(new Post(content: "B Post"))
		user.addToPosts(new Post(content: "C Post"))

		when: "A user is retrieved by ID"
		def checkUser = User.get(user.id)
		def sortedPostsContent = checkUser.posts.collect {
			it.content
		}.sort()
		
		then: "The posts appear in sorted order"
		sortedPostsContent == ['A Post', 'B Post', 'C Post']
		
    }
	
	
	void "posts can be tagged"() {
		given: "A user with a set of tags"
		def user = new User(loginId: 'tomwaits', password: 'secret')
		
		def tagWaits = new Tag(name: 'Tom Waits')
		def tagCash = new Tag(name: 'Johnny Cash')
		user.addToTags(tagWaits)
		user.addToTags(tagCash)
		user.save(failOnError: true)
		

		when: "A user tags two new posts"
		def waitsPost = new Post(content: "Tom Waits")
		user.addToPosts(waitsPost)
		waitsPost.addToTags(tagWaits)
		
		def bothPost = new Post(content: "I like Tom Waits and Johnny Cash")
		user.addToPosts(bothPost)
		
		bothPost.addToTags(tagCash)
		bothPost.addToTags(tagWaits)
		
		then: "The tags are all applied properly"
		user.tags*.name.sort() == ["Johnny Cash", "Tom Waits"]
		1 == waitsPost.tags.size()
		2 == bothPost.tags.size()
		
	}
}
