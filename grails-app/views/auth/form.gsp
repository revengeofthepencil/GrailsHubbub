<html>
<head>
    <title>Sign into Hubbub</title>
    <meta name="layout" content="main">
</head>
<body>
    <h1>Sign in</h1>
    <g:form uri="/j_spring_security_check" method="POST">
        <fieldset class="form">
            <div class="fieldcontain required">
                <label for="j_username">Login ID</label>
                <g:textField name="j_username" value="${loginId}"/>
            </div>
            <div class="fieldcontain required">
                <label for="j_password">Password</label>
                <g:passwordField name="j_password" />
            </div>
        </fieldset>
        <fieldset class="buttons">
            <g:submitButton name="signIn" value="Sign in"/>
        </fieldset>
    </g:form>

</body>
</html>
