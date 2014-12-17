package com.grailsinaction

import java.util.Date;

class User {

	String loginId
	Date dateCreated
	String passwordHash
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	static hasOne = [profile : Profile]
	static hasMany = [ posts : Post, tags : Tag, following : User ]

	transient springSecurityService

	static searchable = { except = ["passwordHash"] }
	static transients = ['springSecurityService']

	static constraints = {
        loginId size: 3..20, unique: true, blank: false
		profile nullable: true
	}

	static mapping = {
		posts sort:'dateCreated'
		password column: '`password`'
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}


	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}

	String toString() {
		return "User $loginId (id: $id)"
	}
	String getDisplayString() {
		return loginId
	}
}
