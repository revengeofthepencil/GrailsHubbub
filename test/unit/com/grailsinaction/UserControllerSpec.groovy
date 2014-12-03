package com.grailsinaction

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */

@TestFor(UserController)
@Mock([User, Profile])
class UserControllerSpec extends Specification {

	def "Registering a user with known good params"() {
		given: "A set of user params"
		params.with {
			loginId = "jrcash"
			password = "jackson"
			homepage = "http://www.heyporter.com"
		}
		
		and: "a set of profile params"
		params['profile.fullName'] = "Johnny Cash"
		params['profile.email'] = "maninblack@cash.com"
		params['profile.homepage'] = "http://www.johnnycash.com"
		
		when: "the user is registered"
		request.method = "POST"
		controller.register()
		
		then: "the user is created and the browser is re-directed"
		response.redirectedUrl == '/'
		User.count() == 1
		Profile.count() == 1
	}	

}
