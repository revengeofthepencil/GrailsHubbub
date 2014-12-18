package com.grailsinaction

import org.junit.internal.runners.statements.FailOnTimeout;

import spock.lang.*

import grails.plugin.springsecurity.SpringSecurityService

class UserIntegrationSpec extends Specification {
	
	def mockSecurityService

	def setup() {
		mockSecurityService = Mock(SpringSecurityService)
		mockSecurityService.encodePassword(_ as String) >> "kjsdfhkshfalhlkdshflas"
	}
	
    def "saving a user to the DB"() {
		given: "A brand spanking new user"
		
		User joe = new User(loginId: "joe")
		joe.passwordHash = "secret"
		
		when: "the user is saved"
		joe.save()
		then: "it saved successfully and can be found in the DB"
		joe.errors.errorCount == 0
		joe.id != null
		User.get(joe.id).loginId == joe.loginId
	}
	
	
	def "updating a saved user"() {
		given: "An existing user"
		def testLoginId = 'ohyeah!'
		//def existingUser = new User(loginId: 'joe', password: 'secret')
		
		
		User existingUser = new User(loginId: "joe")
		existingUser.passwordHash = "secret"
		existingUser.enabled = true
		existingUser.save(failOnError: true)
		
		when: "a property is changed"
		
		def foundUser = User.get(existingUser.id)
		foundUser.enabled = false
		foundUser.save(failOnError: true)
		
		then: "The change persists in the DB"
		User.get(existingUser.id).enabled == false
	}
	
	
	def "validation applied when saving a user"() {
		given: "An invalid user record"
		def invalidUser = new User(loginId: 'joe', 
			profile: new Profile(homepage: 'http://not-a-valid-url'))
		invalidUser.passwordHash = "secret"
		
		
		when: "the user is validated"

		invalidUser.validate()
		// TODO: it seems wonky to explicitly call this 
		invalidUser.profile.validate()
		
		then:
		invalidUser.hasErrors()
		"url.invalid" == invalidUser.profile.errors.getFieldError("homepage").code
		"http://not-a-valid-url" == invalidUser.profile.errors.getFieldError("homepage").rejectedValue
		!invalidUser.errors.getFieldError("loginId")

	}
	
	

	
	def "deleteing a saved user"() {
		given: "An existing user"
		def existingUser = new User(loginId: 'joe', passwordHash: 'secret')
		existingUser.save(failOnError: true)

		when: "the user is deleted"
		def foundUser = User.get(existingUser.id)
		foundUser.delete(flush: true)
		
		then: "The user is no longer in the DB"
		!User.exists(foundUser.id)
	}
	

	def "following a user"() {
		given: "An set of existing users"
		def john = new User(loginId: 'john', passwordHash:'password').save()
		def tom = new User(loginId:'tom', passwordHash:'secret').save()
		def nina = new User(loginId:'nina', passwordHash:'secret').save()

		when: "Tom follows John & Nina, Nina follows John"
		tom.addToFollowing(john)
		tom.addToFollowing(nina)
		nina.addToFollowing(john)
		
		then: "follower counts should be correct"
		2 == tom.following.size()
		1 == nina.following.size()
	}

	
}
