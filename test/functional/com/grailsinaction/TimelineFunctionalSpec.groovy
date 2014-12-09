package com.grailsinaction

import geb.spock.GebReportingSpec
import spock.lang.Stepwise

class TimelineFunctionalSpec extends GebReportingSpec {
	def "Does timeline load for user 'phil'"() {
		when:
		go "users/phil"

		then:
		$("#newPost h3").text() == "What is Phil Potts hacking on right now?"
	}

	def "Submitting a new Post"() {
		given: "A user logs in and starts at timeline page"
		login "frankie", "testing"
		go "users/phil"
		
		when: "A new post is entered"
		$("#postContent").value("this is the new post")
		$("#newPost").find("input", type: "button").click()
		
		then: "the new post is shown on the timeline"
		waitFor{
			$("div.postText", text: "this is the new post")
		}
		
	}
	
	
	private login(String username, String password) {
		go "login/form"
		$("input[name='loginId']").value(username)
		$("input[name='password']").value(password)
		$("input[type='submit']").click()
	}
}
