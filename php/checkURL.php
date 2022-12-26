<?php
	$uname='auddms';
	$pwd='wise1871!';
	$db="auddms";

	$con = mysqli_connect("localhost",$uname,$pwd, $db) or die("connection failed");
	$ORIGIN_URL = $_POST["ORIGIN_URL"];
	$SHORT_URL = [];

	$query = "SELECT SHORT_URL FROM URLS WHERE ORIGIN_URL LIKE '$ORIGIN_URL'";
	$result = mysqli_query($con,$query);


	$response = array();
	$response["success"] = false;

	while($row = mysqli_fetch_array($result))
	   {
		$response["success"] = true;
		$response["SHORT_URL"] =  $row["SHORT_URL"];

		$query2 = "UPDATE URLS SET COUNT= COUNT+1 WHERE ORIGIN_URL LIKE '$ORIGIN_URL'";
		$result2 = mysqli_query($con,$query2);
	   }


	echo json_encode($response);
?>
