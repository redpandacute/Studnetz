<?php

	//Connection
	$db_serverhost = "sql530.your-server.de";
	$db_username = "efxinf_7";
	$db_password = "LxkLYb23hC7nKp4X";
	$db_name = "efxinf_db7";
	
	$connection = mysqli_connect($db_serverhost, $db_username, $db_password, $db_name);
	if(!$connection) { echo "mysql-error: " . mysqli_connect_error() . "<br>\n";  }
	
	$ids = json_decode($_POST['id_array']);
	
	$pictures = array();
	
	for($n = 0; $n < count($ids); $n++) {
		$statement = mysqli_prepare($connection, "SELECT user_profilepictures.blob_profilepicture_small FROM user_profilepictures WHERE user_id = ?");
		mysqli_stmt_bind_param($statement, "i", $ids[$n]);
		mysqli_stmt_execute($statement);
		mysqli_stmt_store_result($statement);
		mysqli_stmt_bind_result($statement, $blob);
		
		while($field = mysqli_stmt_fetch($statement)) {
			$innerarray = array();
			$innerarray['id'] = $ids[$n];
			$innerarray['blob_profilepicture_small'] = $blob;
			$pictures[$n] = $innerarray;
		}
		
		mysqli_stmt_close($statement);
	}
	
	$response['success'] = true;
	$response['data'] = $pictures;
	
	print_r(json_encode($response));
?>
