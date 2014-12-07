<!doctype html>
<html>
<head>
  <title>My Own Hubbub &raquo; <g:layoutTitle default="Welcome" /></title>
  <g:external dir="css" file="hubbub.css"/>
  <g:external dir="css" file="main.css"/>
  <g:layoutHead />
</head>
<body>
  <div>
    <div id="hd">
      <g:link uri="/">
        <asset:image src="headerlogo.png" alt="hubbub logo"/>
      </g:link>
    </div>
    <div id="bd"><!-- start body -->
      <g:layoutBody/>
    </div>  <!-- end body -->
    <div id="ft">
      <div id="footerText">Hubbub - Social Networking on Grails
      <br />Version <g:meta name="app.version"/> 
      on Grails <g:meta name="app.grails.version"/>
      </div>
    </div>
  </div>
</body>
</html>
