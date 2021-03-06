<html>
<head>
<meta charset="UTF-8">
<link rel="shortcut icon" href="images/favicon.png">
<script type="text/javascript" src="../webjars/jquery/jquery.js"></script>

<link rel="stylesheet" href="../webjars/bootstrap/css/bootstrap.css">
<script type="text/javascript"
	src="../webjars/bootstrap/js/bootstrap.js"></script>

<script type="text/javascript" src="../webjars/angularjs/angular.js"></script>
</head>
<body>
	<div class="container">
		<h2>Please Confirm</h2>

		<p>
			Do you authorize "${authorizationRequest.clientId}" at "${authorizationRequest.redirectUri}" to access your protected resources
			with scope ${authorizationRequest.scope?join(", ")}.
		</p>
		<form id="confirmationForm" name="confirmationForm"
			action="../oauth/authorize" method="post">
			<input name="user_oauth_approval" value="true" type="hidden" />
			
			<button class="btn btn-primary" type="submit">Approve</button>
		</form>
		<form id="denyForm" name="confirmationForm"
			action="../oauth/authorize" method="post">
			<input name="user_oauth_approval" value="false" type="hidden" />
			
			<button class="btn btn-primary" type="submit">Deny</button>
		</form>
	</div>
	
</body>
</html>