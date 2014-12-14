databaseChangeLog = {

	changeSet(author: "alexwalker (generated)", id: "changelog") {
		// TODO add changes and preconditions here
	}

	include file: 'changelog-0.1.groovy'

	include file: 'changelog-0.1.gorm.groovy'

	include file: 'changelog-0.2.groovy'
}
