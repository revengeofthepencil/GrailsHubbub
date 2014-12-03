package com.grailsinaction

class UserController {
    static scaffold = true
	
	def search() {}
	
	def register() {
		if (request.method == "POST") {
			def user = new User(params)
			if (user.validate()) {
				user.save()
				flash.message = "Successfully Created User"
				redirect(uri: '/')
			} else {
				flash.message = "Error Registering User"
				return [user:user]
			}
		} 
	}
	
	
	
	def register2(UserRegistrationCommand urc) {
		if (urc.hasErrors()) {
			render view: "register", model: [ user : urc ]
		} else {
			def user = new User(urc.properties)
			user.profile = new Profile(urc.properties)
			if (user.validate() && user.save()) {
				flash.message = "Welcome aboard, ${urc.fullName ?: urc.loginId}"
				redirect(uri: '/')
			} else {
				// maybe not unique loginId?
				return [ user : urc ]
			}
		}
	}

	
	def results(String loginId) {
		def users = User.where{
			loginId =~ "%${loginId}%"
		}.list()
		return [
			users: users,
			term: params.loginId,
			totalUsers: User.count()
		]
	}
}


class UserRegistrationCommand {
	String loginId
	String password
	String passwordRepeat
	byte[] photo
	String fullName
	String bio
	String homepage
	String email
	String timezone
	String country
	String jabberAddress

	static constraints = {
		importFrom Profile
		importFrom User
		password(size: 6..8, blank: false,
				 validator: { passwd, urc ->
					return passwd != urc.loginId
				})
		passwordRepeat(nullable: false,
				validator: { passwd2, urc ->
					return passwd2 == urc.password
				})
	}
}