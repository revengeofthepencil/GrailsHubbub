package com.grailsinaction

import grails.test.mixin.TestFor
import grails.test.mixin.Mock
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(PostController)
@Mock([User, Post])
class PostControllerSpec extends Specification {
	
	def "get a user's timeline based on their ID"() {
		given: "A user with existing posts in the DB"
		User chuck = new User(
			loginId: "chuck_norris",
			password: "password"
			)
		
		chuck.addToPosts(new Post(content: "A first post"))
		chuck.addToPosts(new Post(content: "A second post"))
		chuck.save(failOnError: true)
				
		and: "A loginID param"
		params.id = chuck.loginId
		
		when: "the timeline is invoked"
		def model = controller.timeline()
		
		then: "the user is in the returned model"
		model.user.loginId == "chuck_norris"
		model.user.posts.size() == 2
		
	}
	
	
	def "confirm that non-existant users return a 404"() {
		given: "the ID of a non-existant user"
		params.id = "nope_not_here"
		
		when: "the timeline is invoked"
		def model = controller.timeline()
		
		then: "a 404 is sent to the browser"
		response.status == 404
		
	}
	
	def "Adding a valid new post to the timeline"() {
		given: "A user with posts in the DB"
		User chuck = new User(
			loginId: "chuck_norris",
			password: "password"
			).save(failOnError: true)
		and: "A loginId param"
		params.id = chuck.loginId
		
		and: "Some content in the post"
		params.content = "Guy farted seemed near and hour"
		
		when: "addPost is invoked"
		def model = controller.addPost()
		
		then: "the flash message should show us success"
		flash.message == "Successfully created post"
		response.redirectedUrl == "/post/timeline/${chuck.loginId}"
		Post.countByUser(chuck) == 1
			
	}
	
	

}
