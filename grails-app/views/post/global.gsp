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
		
		<g:render template="postEntry" collection="${posts}" var="post" />
		
        <g:paginate action="global" total="${postCount}" max="3"/>
	</div>
</body>

</html>