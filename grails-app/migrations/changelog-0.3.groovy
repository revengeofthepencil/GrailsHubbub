databaseChangeLog = {

	changeSet(author: "alexwalker (generated)", id: "1418599873791-1") {
		dropColumn(columnName: "twitter_id", tableName: "profile")
	}
}
