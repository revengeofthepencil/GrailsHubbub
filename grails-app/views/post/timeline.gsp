<%@ taglib prefix="g" uri="/web-app/WEB-INF/tld/grails.tld" %>
<html>
<head>
	<title>Timeline for ${user.profile ? user.profile.fullName : user.loginId }</title>
	<meta name="layout" content="main">
    <g:javascript library="jquery" plugin="jquery"/>
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
                <g:form>
                    <g:textArea id="postContent" name="content" rows="3" cols="50"/><br/>
                    <g:submitToRemote value="Post"
                         url="[controller: 'post', action: 'addPostAjax']"
                         update="allPosts"
                         onSuccess="clearPost(data)"
                         onLoading="showSpinner(true)"
                         onComplete="showSpinner(false)"/>

                    <a href="#" id="showHideUrl" onclick="toggleTinyUrl(); return false;">
                        Show TinyURL
                    </a>
                         
                     <g:img id="spinner" style="display: none" uri="../assets/spinner.gif"/>
                </g:form>
		</P>
	</div>
	
	<div id="allPosts">
		<g:render template="postEntry" collection="${user.posts}" var="post" />
		
	</div>



<g:javascript>
    function clearPost(e) {
        $('#postContent').val('');
    }
    function showSpinner(visible) {
        if (visible) $('#spinner').show();
                else $('#spinner').hide();
    }
</g:javascript>

</body>
</html>
