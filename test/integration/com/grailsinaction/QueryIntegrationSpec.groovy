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
	
	
	void "Multiple criteria comparison"() {
		when: "A user is selected by loginId or password"
		def users = User.where{
			loginId == "frankie" || password == "crikey"
		}.list(sort: "loginId")
		
		then: "The correct loginIds are returned"
		users*.loginId == ["dillon", "frankie", "sara"]
	}
	
	void "Query on association"() {
		when: "The 'following' collection is queried"
		def users = User.where{
			following.loginId == "sara"
		}.list(sort: "loginId")
		
		then: "A list of the followers of sara is returned"
		users*.loginId == ["phil"]
	}
	
	void "Query on a range of values"() {
		
		given: "The current date and time"
		def now = new Date()
		
		when: "the 'dateCreated' property is queried"
		
		def users = User.where{
			dateCreated in (now - 1)..now
		}.list(sort: "loginId", order: "desc")
		
		then: "The users created within the given date range are returned"
		users*.loginId == ["phil", "peter", "glen","frankie","chuck_norris", "admin"]
	}
	
	void "Retrieve a single instance"() {
		
		when: "A specific user is queried with get()"
		def user = User.where{
			loginId == "phil"
		}.get()
		
		then: "A single user is found"
		user.password == "thomas"
	}
}
