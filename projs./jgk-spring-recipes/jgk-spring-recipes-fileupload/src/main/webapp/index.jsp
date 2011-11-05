<html>
<head>
<title>File Upload</title>
<script type="text/javascript">
function loadertime() {
document.getElementById("remoteHostName").value=window.location.hostname;
}

</script>
</head>
<body onload="loadertime()">
	<h2>jgk-spring-recipes - File Upload</h2>
	and more here

	<a
		href="http://static.springsource.org/spring/docs/2.0.x/reference/mvc.html#mvc-multipart">http://static.springsource.org/spring/docs/2.0.x/reference/mvc.html#mvc-multipart</a>
	<hr />
	<h1>Please upload a file</h1>
	The idea is to upload an image (verify it is an image), create a thumbnail version, rename and save to disk.
	<div>
		<form method="post" action="fileupload/upload.form"
			enctype="multipart/form-data">
			<input type="file" name="file" /> 
			<br/>
			<input type="text" id="remoteHostName" name="remoteHostName"/>
			<br/>
			Description: <textarea id="file_description" name="description" rows="5" cols="30"></textarea>
			<br/>
			<input type="submit" />
			
		</form>
	</div>
</body>
</html>
