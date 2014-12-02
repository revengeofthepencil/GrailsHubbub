package com.grailsinaction

class PostController {
    static scaffold = true
	
	static defaultAction = 'home'
	
	def home() {
		if (!params.id) {
			// default to chuck norris
			params.id = "chuck_norris"
		}
		redirect(action: 'timeline', params:params)
	}
	
	
	def timeline() {
		def user = User.findByLoginId(params.id)
		if (!user) {
			response.sendError(404)
		} else {
			[user : user]
		}
	}
	
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
	
}
