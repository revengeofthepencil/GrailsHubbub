<%@ taglib prefix="g" uri="/web-app/WEB-INF/tld/grails.tld" %>
<html>
<head>
	<title>Timeline for ${user.profile ? user.profile.fullName : user.loginId }</title>
	<meta name="layout" content="main">
</head>
<body>
	<h1>Timeline for ${user.profile ? user.profile.fullName : user.loginId }</h1>
	
	<g:if test="${flash.message}">
	    <div class="flash">
	        ${flash.message}
	    </div>
	</g:if>
	
	<div id="newPost">
		<h3>What is  ${user.profile ? user.profile.fullName : user.loginId} working on now?</h3>
		<P>
			<g:form method="post" action="addPost" id="${params.id}">
				<g:textArea id="postContent" name="content" rows="3" cols="50"/>
				<br />
				<g:submitButton name="post" value="Post" />
			</g:form>
		</P>
	</div>
	
	<div id="allPosts">
		<g:render template="postEntry" collection="${user.posts}" var="post" />
		<%--
		<g:each in="${user.posts}" var="post">
			<div class="postEntry">
				<div class="postText">
					${post.content}
				</div>
				<div class="postDate">
					${post.dateCreated}
				</div>
			</div>
		</g:each>
		 --%>
	</div>
</body>

</html>