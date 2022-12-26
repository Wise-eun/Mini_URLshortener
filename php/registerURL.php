<?php
	$uname='auddms';
	$pwd='wise1871!';
	$db="auddms";

	$con = mysqli_connect("localhost",$uname,$pwd, $db) or die("connection failed");

	$ORIGIN_URL = $_POST["ORIGIN_URL"];
	$COUNT = 1;

	$query = "SELECT max(ID) FROM `urls`";
	$result= mysqli_query($con,$query);
	$row = mysqli_fetch_array($result);

	$ID = $row['max(ID)'] +1;
	$SHORT_URL = Encode($ID);
	$query2 = "INSERT INTO `urls` VALUES('$ID', '$ORIGIN_URL','$SHORT_URL','$COUNT')";
	$result2= mysqli_query($con,$query2);

	if($result2)
		echo $ID;
	else
		echo "fail";

	function Encode($num)
	   {
		$base = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
		$res = "";
		   do {
			   $res =  $res .$base[$num % 62];
			   $num  = intval($num/62);
		      	}while ($num);
	    	return $res;
	   }

?>