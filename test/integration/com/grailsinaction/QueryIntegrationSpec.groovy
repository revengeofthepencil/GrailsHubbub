package com.grailsinaction

import grails.test.spock.IntegrationSpec

class QueryIntegrationSpec extends IntegrationSpec {

	//TODO: fix this to run with password hash
    /*
    void "Simple property comparison"() {
        when: "Users are selected by a simple password match"
        def users = User.where {
            passwordHash == "testing".encodeAsSHA256()
        }.list(sort: "loginId")
        
        then: "The users with that password are returned"
        users*.loginId == ["frankie"] 
    }

    void "Multiple criteria"() {
        when: "A user is selected by loginId or password"
        def users = User.where {
            loginId == "frankie" || passwordHash == "crikey".encodeAsSHA256()
        }.list(sort: "loginId")

        then: "The matching loginIds are returned"
        users*.loginId == ["dillon", "frankie", "sara"] 
    }
    */
	
	
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
	
	
	
	void "Criteria query for loginID and date created"() {
		
		given: "The current date and time and a partial login ID"
		def now = new Date()
		def loginIdPart = 'franki'
		
		
		when: "the 'dateCreated' property is queried in a criteria query with the login id"
		
		def users = User.createCriteria().list {
			and {
				ilike "loginId", "%${loginIdPart}%"
				if (now) {
					ge "dateCreated", now - 1
				}
			}
		}
		
		then: "The users with that login ID and password are returned"
		users.size() > 0 
		users*.loginId == ["frankie"]
	}
	
	void "Retrieve a single instance"() {
		
		when: "A specific user is queried with get()"
		def user = User.where{
			loginId == "phil"
		}.get()
		
		then: "A single user is found"
        user.loginId == "phil"
	}
}
