<div class="postEntry">
	<div class="postText">${post.content}</div>
	<div class="postDate">
		<hub:dateFromNow date="${post.dateCreated}" />	
		<br /><em>${post.user.profile ? post.user.profile.fullName : post.user.loginId} 
			: ${post.user.loginId}</em>	
	</div>

</div>