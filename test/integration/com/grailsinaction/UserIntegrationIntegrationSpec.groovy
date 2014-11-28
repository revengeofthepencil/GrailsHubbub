package com.grailsinaction

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
}
