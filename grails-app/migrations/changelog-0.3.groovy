databaseChangeLog = {

	changeSet(author: "alexwalker (generated)", id: "1418599873791-1") {
		dropColumn(columnName: "twitter_id", tableName: "profile")
	}
	
	/*
	changeSet(author: "alexwalker (hand-coded)", id: "1418599873791-2") {
		grailsChange {

			change {

				println "Resetting all passwords..."

				def allUsers = sql.rows("select * from user")
				println "Resetting passwords for ${allUsers.size} users"

				Random random = new Random(System.currentTimeMillis())
				def passwordChars = [ 'A'..'Z', 'a'..'z', '0'..'9' ].flatten()

				allUsers.each { user ->
					StringBuilder randomPassword = new StringBuilder()
					1.upto(8) { randomPassword.append(
						passwordChars.get(random.nextInt(passwordChars.size())))
					}
					println "Random password is ${randomPassword} for user ${user.login_id}"
					sql.execute "update user set password = ? where id = ?",
							[ randomPassword.toString(), user.id]

				}
				println "Done resetting passwords..."

			}
		}
	}
	*/
}
