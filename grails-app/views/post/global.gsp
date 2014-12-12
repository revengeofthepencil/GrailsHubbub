<html>
<head>
	<title>Timeline for all posts</title>
	<meta name="layout" content="main">
</head>
<body>
	<h1>Timeline for all posts</h1>
	
	<g:if test="${flash.message}">
	    <div class="flash">
	        ${flash.message}
	    </div>
	</g:if>
	
	<div id="allPosts">
		
		<g:each in="${posts}" var="post">
			<div class="postEntry">
				<div class="postText">
					${post.content}
				</div>
				<div class="postDate">
					${post.dateCreated}
				</div>
			</div>
		</g:each>

        <g:paginate action="global" total="${postCount}" max="3"/>
	</div>
</body>

</html>