<?php
	
	require 'libs/PasswordLib.phar';
	
	//Connection
	$db_serverhost = "sql530.your-server.de";
	$db_username = "efxinf_7";
	$db_password = "LxkLYb23hC7nKp4X";
	$db_name = "efxinf_db7";
	
	$conn = mysqli_connect($db_serverhost, $db_username, $db_password, $db_name);
	if(!$conn) { echo "mysql-error: " . mysqli_connect_error() . "<br>\n";  } 
	
	$user_username = $_POST["user_username"];
	$user_password = $_POST["user_password"];
	
	
	$statement = mysqli_prepare($conn, "SELECT user_archive.user_id, user_hashes.hash_password
		FROM user_archive 
		INNER JOIN user_hashes ON user_archive.user_id = user_hashes.user_id
		WHERE user_archive.user_username = ?"
	); 
	
	//Checking for Username
	mysqli_stmt_bind_param($statement, "s", $user_username);
	mysqli_stmt_execute($statement);
	mysqli_stmt_store_result($statement);
	
	if(mysqli_stmt_num_rows($statement) > 0) {
		
		mysqli_stmt_bind_result($statement, $id, $passwordhash);
		
		$check = array();
		
		while($field = mysqli_stmt_fetch($statement)) {
			$check['hash_password'] = $passwordhash;
			$check['user_id'] = $id;
		}
		
		$lib = new PasswordLib\PasswordLib;
		
		
		//if(password_verify($user_password, $check['hash_password'])) {
		if($lib->verifyPasswordHash($user_password, $check['hash_password'])) {
		//if(strcmp(crypt($user_password, $check['hash_password'])) == 0) {
			mysqli_stmt_close($statement);
			
			$statement = mysqli_prepare($conn, "SELECT user_archive.*, user_subjects.*, user_hashes.*, user_profilepictures.*
				FROM user_archive 
				INNER JOIN user_subjects ON user_archive.user_id = user_subjects.user_id 
				INNER JOIN user_hashes ON user_archive.user_id = user_hashes.user_id
				INNER JOIN user_profilepictures ON user_archive.user_id = user_profilepictures.user_id
				WHERE user_archive.user_id = ?"
			);
		
			mysqli_stmt_bind_param($statement, "i", $check['user_id']);
			mysqli_stmt_execute($statement);
			mysqli_stmt_store_result($statement);
		
			mysqli_stmt_bind_result($statement, 
				$id, $username, $name, $firstname, $school, $grade, $email, $description, 
				$id, $german, $spanish, $english, $french, $biology, $chemistry, $music, $maths, $physics, 
				$id, $passwordhash, 
				$id, $profilepicture_big, $profilepicture_small
			);
		
			$response = array();
			$response['success'] = true;
		
			while ($field = mysqli_stmt_fetch($statement)) {
			
				$response['user_username'] = $username;
				$response['user_name'] = $name;
				$response['user_firstname'] = $firstname;
				$response['user_school'] = $school;
				$response['user_grade'] = $grade;
				$response['user_email'] = $email;
				$response['user_description'] = $description;
		
				$response['subj_german'] = $german;
				$response['subj_spanish'] = $spanish;
				$response['subj_english'] = $english;
				$response['subj_french'] = $french;
				$response['subj_biology'] = $biology;
				$response['subj_chemistry'] = $chemistry;
				$response['subj_music'] = $music;
				$response['subj_maths'] = $maths;
				$response['subj_physics'] = $physics;
			
				$response['hash_password'] = $passwordhash;
				$response['hash_salt'] = $salt;
				$response['blob_profilepicture_big'] = $profilepicture_big;
			
				$response['user_id'] = $id;
			}
			
			mysqli_stmt_close($statement);
			print_r(json_encode($response));
		
		} else {
			//Username or password is incorrect
			mysqli_stmt_close($statement);
			$response['success'] = false;
			$response['hash'] = $check['hash_password'];
			print_r(json_encode($response));
		}
	} else {
		mysqli_stmt_close($statement);
		$response['success'] = false;
		print_r(json_encode($response));
	}
?>