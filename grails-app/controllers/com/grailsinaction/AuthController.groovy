package com.grailsinaction

class AuthController {

	static defaultAction = 'form'
	
    def form(String id) {
        [loginId: id]
    }
	
	/**
	 * hand this overt to Spring security
	
	def signIn(String loginId, String password) {
		User user = User.findByLoginId(loginId)
		if (user && user.password.equals(password)) {
			session.user = user
			redirect uri: "/"
		} else {
			flash.error = "Unknown username or password"
			redirect action: "form"
		}
		
	}
	*/
}

