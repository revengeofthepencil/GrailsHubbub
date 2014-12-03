package com.grailsinaction

class LoginController {

	static defaultAction = 'form'
	
    def form(String id) {
        [loginId: id]
    }
	
	
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
}
