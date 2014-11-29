package com.grailsinaction

import org.junit.internal.runners.statements.FailOnTimeout;

import spock.lang.*

class UserIntegrationIntegrationSpec extends Specification {

    def "saving a user to the DB"() {
		given: "A brand spanking new user"
		def joe = new User(loginId: 'joe', password: 'secret',
			homepage: 'http://www.joe.com')
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
		def existingUser = new User(loginId: 'joe', password: 'secret',
			homepage: 'http://www.joe.com')
		existingUser.save(failOnError: true)
		when: "a property is changed"
		def foundUser = User.get(existingUser.id)
		foundUser.password = testPassword
		foundUser.save(failOnError: true)
		
		then: "The change persists in the DB"
		User.get(existingUser.id).password == testPassword
	}
	
	
	def "extra validation applied to make sure password does not match id"() {
		given: "An invalid user record"
		def invalidUser = new User(loginId: 'longenough',
			password: 'longenough',
			homepage: 'http://www.google.com')

		when: "the user is validated"
		invalidUser.validate()
		
		then:
		invalidUser.hasErrors()
		// TODO: what is the appropriate code?
		"longenough" == invalidUser.errors.getFieldError("password").rejectedValue
	}
	
	
	def "deleteing a saved user"() {
		given: "An existing user"
		def existingUser = new User(loginId: 'joe', password: 'secret',
			homepage: 'http://www.joe.com')
		existingUser.save(failOnError: true)

		when: "the user is deleted"
		def foundUser = User.get(existingUser.id)
		foundUser.delete(flush: true)
		
		then: "The user is no longer in the DB"
		!User.exists(foundUser.id)
	}
}
