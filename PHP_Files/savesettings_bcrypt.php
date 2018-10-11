<?php

	require 'libs/PasswordLib.phar';

	$db_serverhost = "sql530.your-server.de";
	$db_username = "efxinf_7";
	$db_password = "LxkLYb23hC7nKp4X";
	$db_name = "efxinf_db7";
	
	$conn = mysqli_connect($db_serverhost, $db_username, $db_password, $db_name);
	
	if(!$conn) { echo "mysql-error: " . mysqli_connect_error() . "<br>\n";  }
	
	$user_username = $_POST["user_username"];
	$user_id = $_POST["user_id"];
	$user_password = $_POST["user_password"];
	$encoded_base64 = $_POST["encoded_base64"];

	$user_id = $_POST["user_id"];
	$user_firstname = $_POST["user_firstname"];
	$user_name = $_POST["user_name"];
	$user_email = $_POST["user_email"];
	$user_school = $_POST["user_school"];
	$user_description = $_POST["user_description"];
	
	$user_grade = $_POST["user_grade"];
  
	$subj_german = $_POST["subj_german"];
	$subj_spanish = $_POST["subj_spanish"];
	$subj_english = $_POST["subj_english"];
	$subj_french = $_POST["subj_french"];
	$subj_biology = $_POST["subj_biology"];
	$subj_chemistry = $_POST["subj_chemistry"];
	$subj_music = $_POST["subj_music"];
	$subj_maths = $_POST["subj_maths"];
	$subj_physics = $_POST["subj_physics"];
	
	$password_changed = $_POST["bool_password_changed"];
	$hash_old_password = $_POST["hash_old_password"];
	
	$bool_image_changed = $_POST["bool_image_changed"];
	
	//VALIDATION---------------
	$patternspaced = '/^[a-zA-Z0-9 äöüÄÖÜ\s]+$/';
	$pattern = '/^[a-zA-Z0-9äöüÄÖÜ\s]+$/';
	$patternPW = '/^[a-zA-Z0-9 \s]+$/';
	$patternemail = '/^[a-zA-Z0-9_.+-äöüÄÖÜ]+@[a-zA-Z0-9.-äöüÄÖÜ]+.[a-zA-Z]+$/';
	
	$valid = true;
	$errorstring = "";

	
	if(empty($user_firstname) || !preg_match($patternspaced, $user_firstname) || strlen($user_firstname) >= 18) {
		$valid = false;
		$errorstring .= '406:1' . 'ID: ' . $user_id;
	}
	
	if(empty($user_name) || !preg_match($patternspaced, $user_name) || strlen($user_name) >= 18) {
		$valid = false;
		$errorstring .= '406:2';
	}
	
	if(empty($user_email) || !preg_match($patternemail, $user_email) || strlen($user_email) > 255) {
		$valid = false;
		$errorstring .= '406:3';
	}
	
	
	$statement = mysqli_prepare($conn, "SELECT hash_password FROM user_hashes WHERE user_id = ?");
	mysqli_stmt_bind_param($statement, "i", $user_id);
	mysqli_stmt_execute($statement);
	mysqli_stmt_store_result($statement);
	
	mysqli_stmt_bind_result($statement, $hash);
	
	$check = array();
	while($field = mysqli_stmt_fetch($statement)) {
		$check['hash'] = $hash;
	}
	
	if(strcmp($hash_old_password, $check['hash']) != 0) {
		$valid = false;
		$errorstring .= '401:1';
	}	
	
	if(count($user_description) > 256) {
		$valid = false;
		$errorstring .= '406:5';
	}
	
	if($valid) {
		
			mysqli_stmt_close($statement);
			
			$statement = mysqli_prepare($conn, "UPDATE user_archive 
				SET 
				user_name = ?, user_firstname = ?, user_school = ?, user_grade = ?, user_email = ?, user_description = ?
				WHERE 
				user_id = ?"
			);
			
			mysqli_stmt_bind_param($statement, "sssissi", $user_name, $user_firstname, $user_school, $user_grade, $user_email, $user_description, $user_id);
			mysqli_stmt_execute($statement);
			mysqli_stmt_close($statement);
		
			$statement = mysqli_prepare($conn, "UPDATE user_subjects 
				SET
				subj_german = ?, subj_spanish = ?, subj_english = ?, subj_french = ?, subj_biology = ?, subj_chemistry = ?, subj_music = ?, subj_maths = ?, subj_physics = ?
				WHERE 
				user_id = ?"
			);
			
			mysqli_stmt_bind_param($statement, "iiiiiiiiii", $subj_german, $subj_spanish, $subj_english, $subj_french, $subj_biology, $subj_chemistry, $subj_music, $subj_maths, $subj_physics , $user_id);
			mysqli_stmt_execute($statement);
			mysqli_stmt_close($statement);
				
			if($bool_image_changed == 1) {
				
				$blob_profilepicture_big = $_POST["blob_profilepicture_big"];
				$blob_profilepicture_small = $_POST["blob_profilepicture_small"];
				
				$statement = mysqli_prepare($conn, "UPDATE user_profilepictures
					SET
					blob_profilepicture_big = ?, blob_profilepicture_small = ?
					WHERE
					user_id = ?"
				);
			
				mysqli_stmt_bind_param($statement, "ssi", $blob_profilepicture_big, $blob_profilepicture_small, $user_id);
				mysqli_stmt_execute($statement);
			}
			$response = array();
			
			if($password_changed == 1) {
			
				$old_password = $_POST["old_password"];
				$new_password = $_POST["new_password"];
				
				$lib = new PasswordLib\PasswordLib();
				
				if($lib->verifyPasswordHash($old_password, $check['hash']) && !empty($new_password) && preg_match($patternPW, $new_password) && strlen($new_password) >= 64) {
			
					$statement = mysqli_prepare($conn, "UPDATE user_hashes
						SET
						hash_password = ?
						WHERE
						user_id = ?"
					);
			
					$lib = new PasswordLib\PasswordLib();
					$hash_password = $lib->createPasswordHash($new_password, '$2a$', array('cost' => 11));
			
			
					mysqli_stmt_bind_param($statement, "si", $hash_password, $user_id);
					mysqli_stmt_execute($statement);
					mysqli_stmt_close($statement);
				} else {
					$errorstring .= '401:2';
				}
			}
		
			$statement = mysqli_prepare($conn, "SELECT user_archive.*, user_subjects.*, user_hashes.*, user_profilepictures.*
				FROM user_archive 
				INNER JOIN user_subjects ON user_archive.user_id = user_subjects.user_id 
				INNER JOIN user_hashes ON user_archive.user_id = user_hashes.user_id
				INNER JOIN user_profilepictures ON user_archive.user_id = user_profilepictures.user_id
				WHERE user_archive.user_id = ?"
			);
				
			mysqli_stmt_bind_param($statement, "i", $user_id);
			mysqli_stmt_execute($statement);
			mysqli_stmt_store_result($statement);
			mysqli_stmt_bind_result($statement, 
				$id, $username, $name, $firstname, $school, $grade, $email, $description, 
				$id, $german, $spanish, $english, $french, $biology, $chemistry, $music, $maths, $physics, 
				$id, $passwordhash,
				$id, $profilepicture_big, $profilepicture_small
			);
		
			
		
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
		
			$response['success'] = true;
			print_r(json_encode($response));
		
	} else {
		mysqli_stmt_close($statement);
		$response['success'] = false;
		$response['error_log'] = str_split($errorstring, 5);
		print_r(json_encode($response));
	}
?>
