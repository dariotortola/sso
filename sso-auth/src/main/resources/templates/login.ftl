<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript" src="webjars/jquery/jquery.min.js"></script>

<link rel="stylesheet" href="webjars/bootstrap/css/bootstrap.min.css">
<script type="text/javascript"
	src="webjars/bootstrap/js/bootstrap.min.js"></script>

<script type="text/javascript" src="webjars/angularjs/angular.min.js"></script>
</head>
<body>
<#if RequestParameters['error']??>
	<div class="alert alert-danger">
		There was a problem logging in. Please try again.
	</div>
</#if>
	<div class="container">
		<form role="form" action="login" method="post">
		  <div class="form-group">
		    <label for="username">Username:</label>
		    <input type="text" class="form-control" id="username" name="username"/>
		  </div>
		  <div class="form-group">
		    <label for="password">Password:</label>
		    <input type="password" class="form-control" id="password" name="password"/>
		  </div>
		  <input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		  <button type="submit" class="btn btn-primary">Submit</button>
		</form>
	</div>
</body>
</html>