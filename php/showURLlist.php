<?php
	$host='auddms';
	$uname='auddms';
	$pwd='wise1871!';
	$db="auddms";

	$con = mysqli_connect("localhost",$uname,$pwd, $db) or die("connection failed");

	$res = "SELECT * FROM URLS";
	$query = mysqli_query($con,$res);
	$result = array();

	while($row = mysqli_fetch_array($query))
	{
array_push($result,array('ORIGIN_URL'=>$row[1],'SHORT_URL'=>$row[2]));
	}


	echo json_encode(array("URLLIST"=>$result));
	

?>