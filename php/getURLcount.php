<?php
	$host='auddms';
	$uname='auddms';
	$pwd='wise1871!';
	$db="auddms";

	$con = mysqli_connect("localhost",$uname,$pwd, $db) or die("connection failed");

	$res = "SELECT ORIGIN_URL,COUNT FROM URLS ORDER BY COUNT DESC";
	$query = mysqli_query($con,$res);
	$result = array();

	while($row = mysqli_fetch_array($query))
	{
	   array_push($result,array('ORIGIN_URL'=>$row[0], 'COUNT'=>$row[1]));
	  // echo $row['ORIGIN_URL'];
	  // echo ",";
	  // echo $row['COUNT'];
	  // echo ",";
	}


	echo json_encode(array("URLCOUNT"=>$result));
	

?>