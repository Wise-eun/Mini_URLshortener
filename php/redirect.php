<?php
	$uname='auddms';
	$pwd='wise1871!';
	$db="auddms";

	$request = $_SERVER['REQUEST_URI'];
	$shortenArr = explode("/",$request);
	$shorten = $shortenArr[2];

	if(strpos($shorten , 'php')!=false)
	   {
		header("Location: {$shorten}");
	   }

	else
	   {
		$con = mysqli_connect("localhost",$uname,$pwd, $db) or die("connection failed");
		$ID = Decode($shorten);
		$query = "SELECT ORIGIN_URL FROM urls WHERE ID = '{$ID}'";
		$result= mysqli_query($con,$query);


		$response = array();
		$response["success"] = false;

		while($row = mysqli_fetch_array($result))
		   {
			$response["success"] = true;
			$response["ORIGIN_URL"] = $row["ORIGIN_URL"];
			header("Location: {$response["ORIGIN_URL"]}");
			exit();
	   	   }
	   }


	function Decode($str)
	   {
		$base = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
		$length = strlen($str);
		$power = 1;
		$res =0;

		for($i=0;$i<$length;$i++)
		   {
	   		$digit = strpos($base,$str[$i]);
	   		$res = $digit * $power + $res;
	   		$power  = $power * 62;
		   }
		return $res;
	   }

	exit();
?>