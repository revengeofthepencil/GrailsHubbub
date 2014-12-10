package com.grailsinaction

import com.grailsinaction.pages.TimelinePage;

import geb.spock.GebReportingSpec
import spock.lang.Stepwise

class TimelineFunctionalSpec extends GebReportingSpec {
	def "Does timeline load for user 'phil'"() {
		when:
		to TimelinePage, "phil"

		then:
		whatHeading.text() ==  "What is Phil Potts hacking on right now?"
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
