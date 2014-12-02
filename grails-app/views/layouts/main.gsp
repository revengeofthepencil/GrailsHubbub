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
      <div id="footerText">Hubbub - Social Networking on Grails</div>
    </div>
  </div>
</body>
</html>
