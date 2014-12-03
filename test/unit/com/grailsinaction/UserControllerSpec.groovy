package com.grailsinaction

import grails.test.mixin.*
import spock.lang.Specification
import spock.lang.Unroll

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

	@Unroll
	def "Registration command object for #loginId validate correctly"() {

		given: "a mocked command object"
		def urc = mockCommandObject(UserRegistrationCommand)

		and: "a set of initial values from the spock test"
		urc.loginId = loginId
		urc.password = password
		urc.passwordRepeat = passwordRepeat
		urc.fullName = "Your Name Here"
		urc.email = "someone@nowhere.net"

		when: "the validator is invoked"
		def isValidRegistration = urc.validate()

		then: "the appropriate fields are flagged as errors"
		isValidRegistration == anticipatedValid
		urc.errors.getFieldError(fieldInError)?.code == errorCode

		where:
		loginId  | password   | passwordRepeat| anticipatedValid   | fieldInError       | errorCode
		"glen"  | "password" | "no-match"   | false               | "passwordRepeat"   | "validator.invalid"
		"peter" | "password" | "password"   | true                | null               | null
		"a"     | "password" | "password"   | false               | "loginId"           | "size.toosmall"

	}

}
