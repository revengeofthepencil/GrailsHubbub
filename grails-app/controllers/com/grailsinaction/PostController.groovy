package com.grailsinaction

class PostController {
    static scaffold = true
	
	def addPost() {
		def user = User.findByLoginId(params.id)
		if (user) {
			def post = new Post(params)
			user.addToPosts(post)
			if (user.save()) {
				flash.message = "Successfully created post"
			} else {
				flash.message = "Oh snap! Post could not be saved"
			} 
		} else {
			flash.message = "Oh snap! The user ID is invalid"
		}		
		
		redirect(action: 'timeline', id: params.id)
	}
	
	def timeline() {
		def user = User.findByLoginId(params.id)
		if (!user) {
			response.sendError(404)
		} else {
			[user : user]
		}
	}
}
