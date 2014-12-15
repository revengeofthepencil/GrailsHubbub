package com.grailsinaction

class Post {
    static searchable = true

		String content
	Date dateCreated
	
    static constraints = {
		content blank: false
    }
	
	static belongsTo = [user : User]
	static hasMany = [tags : Tag]
	
	static mapping = {
		sort dateCreated:"desc"
	}	
	
	String getDisplayString() { return content }
}
