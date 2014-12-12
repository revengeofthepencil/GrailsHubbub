package com.grailsinaction

import grails.plugin.cache.CachePut;
import grails.plugin.cache.Cacheable;

class PostController {
    static scaffold = true

    static defaultAction = "home"

    def postService

    def home() {
        if (!params.id) {
            params.id = "chuck_norris"
        }
        redirect(action: 'timeline', params: params)
    }
    
    def timeline(String id) {
        def user = User.findByLoginId(id)
        if (!user) {
            response.sendError(404)
        } else {
            [ user : user ]
        }
    }
    
	@Cacheable('globaltimeline')
	def global() {
		[ posts : Post.list(params), postCount : Post.count() ]
	}
	
	@CachePut(value='userTimeline')
    def personal() {
        if (!session.user) {
            redirect controller: "login", action: "form"
            return
        } else {
            // Need to reattach the user domain object to the session using
            // the refresh() method.
            render view: "timeline", model: [ user : session.user.refresh() ]
        }
    }

    def addPost(String id, String content)  {
        try {
            def newPost = postService.createPost(id, content)
            flash.message = "Added new post: ${newPost.content}"
        } catch (PostException pe) {
            flash.message = pe.message
        }
        redirect(action: 'timeline', id: id)
    }
	
	def addPostAjax(String content) {
		try {
			def newPost = postService.createPost(
				session.user.loginId, content)
			def recentPosts = Post.findAllByUser(
				session.user,
				[sort: 'dateCreated', order: 'desc', max: 20]
				)
			render template: 'postEntry',
				collection: recentPosts,
				var: 'post'
		} catch (PostException pe) {
			render {
				div(class: "errors", pe.message)
			}
		}
	}
	
	
	def tinyUrl(String fullUrl) {
		def origUrl = fullUrl?.encodeAsURL()
		def tinyUrl =
			new URL("http://tinyurl.com/api-create.php?url=${origUrl}").text
		render(contentType:"application/json") {
			urls(small: tinyUrl, full:fullUrl)
		}
	}
	
}
