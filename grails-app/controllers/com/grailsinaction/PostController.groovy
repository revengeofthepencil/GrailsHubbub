package com.grailsinaction

class PostController {
    static scaffold = true
	static defaultAction = 'home'
	
	def postService
	
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
	
	def addPost(String id, String content) {
		try {
			def newPost = postService.createPost(id, content)
			flash.message = "Successfully created post: ${newPost.content}"
		} catch(PostException ex) {
			flash.message = pe.message
		}

		redirect(action: 'timeline', id: id)
		
	}
	
}
