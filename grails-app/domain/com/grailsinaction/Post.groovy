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
	
	
	String toString() { return "Post '${shortContent}' (id: $id) for user '${user?.loginId}'" }
	String getDisplayString() { return shortContent }

	String getShortContent() {
		def maxSize = 20
		if (content?.size() > maxSize) return content.substring(0, maxSize - 3) + '...'
		else return content
	}

}
