package com.grailsinaction

import grails.test.spock.IntegrationSpec

class QueryIntegrationSpec extends IntegrationSpec {

    void "Simple Property Comparison"() {
		when: "Users are selected based on password match"
		def users = User.where{
			password == "testing"
		}.list(sort: "loginId")
		
		then: "The users with that password are returned"
		users*.loginId == ["frankie"]
    }
}
