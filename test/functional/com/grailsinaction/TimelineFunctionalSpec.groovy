package com.grailsinaction

import com.grailsinaction.pages.TimelinePage;
import com.grailsinaction.pages.LoginPage

import geb.spock.GebReportingSpec
import spock.lang.Stepwise

class TimelineFunctionalSpec extends GebReportingSpec {
	def "Does timeline load for user 'phil'"() {
        when:
        login "frankie", "testing"
        go "users/phil"

		then:
        $("#newPost h3").text() == "What is Phil Potts working on now?"
	}

	def "Submitting a new Post"() {
		given: "A user logs in and starts at timeline page"
		login "frankie", "testing"
		to TimelinePage, "phil"
		
		when: "A new post is entered"
		newPostContent.value("this is the new post") 
		submitPostButton.click()

		then: "the new post is shown on the timeline"
		waitFor{
			!posts("this is the new post").empty
		}
		
	}
	
	
	private login(String username, String password) {
        to LoginPage
        loginIdField = username
        passwordField = password
        signInButton.click()
	}
}
