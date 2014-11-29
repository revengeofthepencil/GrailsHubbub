package com.grailsinaction

import org.junit.internal.runners.statements.FailOnTimeout;

import spock.lang.*

class UserIntegrationSpec extends Specification {

	
    def "saving a user to the DB"() {
		given: "A brand spanking new user"
		def joe = new User(loginId: 'joe', password: 'secret')
		when: "the user is saved"
		joe.save()
		then: "it saved successfully and can be found in the DB"
		joe.errors.errorCount == 0
		joe.id != null
		User.get(joe.id).loginId == joe.loginId
	}
	
	
	def "updating a saved user"() {
		given: "An existing user"
		def testPassword = 'ohyeah!'
		def existingUser = new User(loginId: 'joe', password: 'secret')
		existingUser.save(failOnError: true)
		when: "a property is changed"
		def foundUser = User.get(existingUser.id)
		foundUser.password = testPassword
		foundUser.save(failOnError: true)
		
		then: "The change persists in the DB"
		User.get(existingUser.id).password == testPassword
	}
	
	
	def "validation applied when saving a user"() {
		given: "An invalid user record"
		def invalidUser = new User(loginId: 'joe', password: 'tiny', 
			profile: new Profile(homepage: 'http://not-a-valid-url'))
		
		when: "the user is validated"

		invalidUser.validate()
		// TODO: it seems wonky to explicitly call this 
		invalidUser.profile.validate()
		
		then:
		invalidUser.hasErrors()
		"size.toosmall" == invalidUser.errors.getFieldError("password").code
		"tiny" == invalidUser.errors.getFieldError("password").rejectedValue

		"url.invalid" == invalidUser.profile.errors.getFieldError("homepage").code
		"http://not-a-valid-url" == invalidUser.profile.errors.getFieldError("homepage").rejectedValue
		
		!invalidUser.errors.getFieldError("loginId")

	}
	
	
	def "extra validation applied to make sure password does not match id"() {
		given: "An invalid user record"
		def invalidUser = new User(loginId: 'longenough',
			password: 'longenough')

		when: "the user is validated"
		invalidUser.validate()
		
		then:
		invalidUser.hasErrors()
		// TODO: what is the appropriate code?
		"longenough" == invalidUser.errors.getFieldError("password").rejectedValue
	}
	
	
	def "deleteing a saved user"() {
		given: "An existing user"
		def existingUser = new User(loginId: 'joe', password: 'secret')
		existingUser.save(failOnError: true)

		when: "the user is deleted"
		def foundUser = User.get(existingUser.id)
		foundUser.delete(flush: true)
		
		then: "The user is no longer in the DB"
		!User.exists(foundUser.id)
	}
	

	def "following a user"() {
		given: "An set of existing users"
		def john = new User(loginId: 'john', password:'password').save()
		def tom = new User(loginId:'tom', password:'secret').save()
		def nina = new User(loginId:'nina', password:'secret').save()

		when: "Tom follows John & Nina, Nina follows John"
		tom.addToFollowing(john)
		tom.addToFollowing(nina)
		nina.addToFollowing(john)
		
		then: "follower counts should be correct"
		2 == tom.following.size()
		1 == nina.following.size()
	}

	
}
