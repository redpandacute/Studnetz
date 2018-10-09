<?php 	
	$db_serverhost = "sql530.your-server.de";
	$db_username = "efxinf_7";
	$db_password = "LxkLYb23hC7nKp4X";
	$db_name = "efxinf_db7";
 
	//Connecting to db
	$conn = mysqli_connect($db_serverhost, $db_username, $db_password, $db_name);
	if(!$conn) { echo "mysql-error: " . mysqli_connect_error() . "<br>\n";  } 
	//POST
	$user_id = $_POST["user_id"];
	
	$statement = mysqli_prepare($conn, "SELECT user_profilepictures.blob_profilepicture_big FROM user_profilepictures WHERE user_id = ?");
	mysqli_stmt_bind_param($statement, "i", $user_id);
	
	if(mysqli_stmt_execute($statement)) {
		mysqli_stmt_store_result($statement);
		mysqli_stmt_bind_result($statement, $blob);
		
		$response = array();
		
		while($field = mysqli_stmt_fetch($statement)) {
			$response['blob_profilepicture_big'] = $blob;
		}
		
		$response['success'] = true;
		print_r(json_encode($response));
	
	} else {
		$response = array();
		$response['success'] = false;
		print_r(json_encode($response));
	}

?>