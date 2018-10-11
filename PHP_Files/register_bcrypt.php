<?php
	//Details
	
	require 'libs/PasswordLib.phar';
 
	$db_serverhost = "sql530.your-server.de";
	$db_username = "efxinf_7";
	$db_password = "LxkLYb23hC7nKp4X";
	$db_name = "efxinf_db7";

 
 //Connection step 1
 
	$conn = mysqli_connect($db_serverhost, $db_username, $db_password, $db_name);
	
//POST
	
	$user_username = $_POST["user_username"];
	$user_name = $_POST["user_name"];
	$user_firstname = $_POST["user_firstname"];
	$user_school = $_POST["user_school"];
	$user_grade = $_POST["user_grade"];
	$user_email = $_POST["user_email"];
	
	$user_password = $_POST["user_password"];
	
	//VALIDATION---------------
	$patternspaced = '/^[a-zA-Z0-9 äöüÄÖÜ\s]+$/';
	$pattern = '/^[a-zA-Z0-9äöüÄÖÜ\s]+$/';
	$patternPW = '/^[a-zA-Z0-9 \s]+$/';
	$patternemail = '/^[a-zA-Z0-9_.+-äöüÄÖÜ]+@[a-zA-Z0-9.-äöüÄÖÜ]+.[a-zA-Z]+$/';
	
	$valid = true;
	$errorstring = "";
	
	if(empty($user_username) || !preg_match($pattern, $user_username) || strlen($user_username) >= 18) {
		$valid = false;
		$errorstring .= '406:1';
	}
	
	if(empty($user_firstname) || !preg_match($patternspaced, $user_firstname) || strlen($user_firstname) >= 18) {
		$valid = false;
		$errorstring .= '406:2';
	}
	
	if(empty($user_name) || !preg_match($patternspaced, $user_name) || strlen($user_name) >= 18) {
		$valid = false;
		$errorstring .= '406:3';
	}
	
	if(empty($user_email) || !preg_match($patternemail, $user_email) || strlen($user_email) > 255) {
		$valid = false;
		$errorstring .= '406:4';
	}
	
	if(empty($user_password) || !preg_match($patternPW, $user_password) || strlen($user_password) >= 64) {
		$valid = false;
		$errorstring .= '406:5';
	}
	//-------------------------
	
	
	
	//Checking for availability of username
	$statement = mysqli_prepare($conn, "SELECT user_archive.* FROM user_archive WHERE user_archive.user_username = ? OR user_archive.user_email = ?");
	mysqli_stmt_bind_param($statement, "ss", $user_username, $user_email);
	mysqli_stmt_execute($statement);
	mysqli_stmt_store_result($statement);
	
	if(mysqli_stmt_num_rows($statement) > 0) {
		$valid = false;
		$errorstring .= '406:6';
	}
	
	if (!$valid) {
		mysqli_stmt_close($statement);
		//TODO username or email exists already
		$response = array();
		$response['success'] = false;
		$response['error_log'] = str_split($errorstring, 5);
		
		print_r(json_encode($response));
		
	} else {
	
		mysqli_stmt_close($statement);
		//INSERT NEW USER INTO MAIN TABLE
		$statement = mysqli_prepare($conn, "INSERT INTO user_archive(user_username, user_name, user_firstname,
								   user_school, user_grade, user_email) 
								   VALUES (?, ?, ?, ?, ?, ?)");
		
		//Binding POST to Statement
		mysqli_stmt_bind_param($statement, "ssssis", $user_username, $user_name, $user_firstname, $user_school,
							   $user_grade, $user_email);
		
		mysqli_stmt_execute($statement);
		mysqli_stmt_close($statement);
		
		
		//RETREIVE USER_ID THAT HAS BEEN ASIGNED TO THE NEW USER
		$id_retrieve_statement = mysqli_prepare($conn, "SELECT user_id FROM user_archive WHERE user_username = ?"); //Checking for Username
		mysqli_stmt_bind_param($id_retrieve_statement, "s", $user_username);
		mysqli_stmt_execute($id_retrieve_statement);
		
		mysqli_stmt_store_result($id_retrieve_statement);
		mysqli_stmt_bind_result($id_retrieve_statement, $arch_id);
		
		while ($field = mysqli_stmt_fetch($id_retrieve_statement)) {
			$user_id = $arch_id;
		}
		
		mysqli_stmt_close($id_retrieve_statement);
		
		//INSERT NEW USER INTO SUBJECTS TABLE WITH DUMMY VALUES
		$subj_statement = mysqli_prepare($conn,"INSERT INTO user_subjects(user_id, subj_german, subj_spanish,
										 subj_english, subj_french, subj_biology, subj_chemistry, subj_music, 
										 subj_maths, subj_physics) VALUES (?,?,?,?,?,?,?,?,?,?)");
										 
		$placeholder = 0;
										 
		mysqli_stmt_bind_param($subj_statement, "iiiiiiiiii", $user_id, $placeholder, $placeholder, $placeholder, $placeholder, $placeholder, $placeholder, $placeholder, $placeholder, $placeholder);
		mysqli_stmt_execute($subj_statement);
		
		mysqli_stmt_close($subj_statement);
		
		$statement = mysqli_prepare($conn, "INSERT INTO user_hashes(user_id, hash_password) VALUES (?,?)");
		
		$lib = new PasswordLib\PasswordLib();
		$hash_password = $lib->createPasswordHash($user_password, '$2a$', array('cost' => 11));
		
		mysqli_stmt_bind_param($statement, "is", $user_id, $hash_password);
		mysqli_stmt_execute($statement);
		
		mysqli_stmt_close($statement);
		
		//INSERT NEW USER INTO PB TABLE WITH DUMMY VALUE
		$statement = mysqli_prepare($conn,"INSERT INTO user_profilepictures(user_id, blob_profilepicture_big, blob_profilepicture_small) VALUES (?,?,?)");
		
		$placeholder = 0;
		
		mysqli_stmt_bind_param($statement, "iss", $user_id, $placeholder, $placeholder);
		mysqli_stmt_execute($statement);
		
		mysqli_stmt_close($statement);
		
		$response = array();
		$response['success'] = true;
		
		print_r(json_encode($response));
	}
?>