databaseChangeLog = {

	changeSet(author: "alexwalker (generated)", id: "1418599041883-1") {
		addColumn(tableName: "profile") {
			column(name: "twitter_id", type: "varchar(255)")
		}
	}

	changeSet(author: "alexwalker (generated)", id: "1418599041883-2") {
		modifyDataType(columnName: "photo", newDataType: "mediumblob", tableName: "profile")
	}

	changeSet(author: "alexwalker (generated)", id: "1418599041883-3") {
		createIndex(indexName: "FK7762B85824AB4F86", tableName: "post_tags") {
			column(name: "post_id")
		}
	}

	changeSet(author: "alexwalker (generated)", id: "1418599041883-4") {
		createIndex(indexName: "user_id_uniq_1418599041776", tableName: "profile", unique: "true") {
			column(name: "user_id")
		}
	}
}
