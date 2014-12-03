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
