<?php

	//VERY INTERESTING: https://websitebeaver.com/prepared-statements-in-php-mysqli-to-prevent-sql-injection
//conn Details
	$db_serverhost = "sql530.your-server.de";
	$db_username = "efxinf_7";
	$db_password = "LxkLYb23hC7nKp4X";
	$db_name = "efxinf_db7";
 
 //Connecting to db
	$conn = mysqli_connect($db_serverhost, $db_username, $db_password, $db_name);
	if(!$conn) { echo "mysql-error: " . mysqli_connect_error() . "<br>\n";  } 
 //POST
	$user_id = $_POST["user_id"];
	$user_name = "%{$_POST['user_name']}%"; //Important: ? can only be a full String, not a part of one
	$user_school = $_POST["user_school"];
	$user_grade = $_POST["user_grade"];
 
	$subj_german = $_POST["subj_german"];
	$subj_spanish = $_POST["subj_spanish"];
	$subj_english = $_POST["subj_english"];
	$subj_chemistry = $_POST["subj_chemistry"];
	$subj_french = $_POST["subj_french"];
	$subj_biology = $_POST["subj_biology"];
	$subj_music = $_POST["subj_music"];
	$subj_maths = $_POST["subj_maths"];
	$subj_physics = $_POST["subj_physics"];
  
	$statement_string = "SELECT user_archive.*, user_subjects.* FROM user_archive INNER JOIN user_subjects ON user_archive.user_id = user_subjects.user_id WHERE (user_archive.user_name LIKE ? OR user_archive.user_username LIKE ? OR user_archive.user_firstname LIKE ?) AND user_archive.user_id != ?";
  
  
  
	if($user_school !== '') {
		$statement_string .= (" AND user_archive.user_school = ? AND user_archive.user_grade >= ?");
	}
  
	if($subj_maths == 1) {$statement_string .= " AND user_subjects.subj_maths = 1"; }
	if($subj_music == 1) {$statement_string .= " AND user_subjects.subj_music = 1"; }
	if($subj_spanish == 1) {$statement_string .= " AND user_subjects.subj_spanish = 1"; }
	if($subj_german == 1) {$statement_string .= " AND user_subjects.subj_german = 1"; }
	if($subj_english == 1) {$statement_string .= " AND user_subjects.subj_english = 1"; }
	if($subj_chemistry == 1) {$statement_string .= " AND user_subjects.subj_chemistry = 1"; }
	if($subj_french == 1) {$statement_string .= " AND user_subjects.subj_french = 1"; }
	if($subj_physics == 1) {$statement_string .= " AND user_subjects.subj_physics = 1"; }
	if($subj_biology == 1) {$statement_string .= " AND user_subjects.subj_biology = 1"; }
  
  
  
  
	$statement = mysqli_prepare($conn, $statement_string);
	if($user_school == '') {
		mysqli_stmt_bind_param($statement, "sssi", $user_name, $user_name, $user_name, $user_id);
	} else {
		mysqli_stmt_bind_param($statement, "sssisi", $user_name, $user_name, $user_name, $user_id, $user_school, $user_grade);
	}
	mysqli_stmt_execute($statement);
	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, 
		$id, $username, $name, $firstname, $school, $grade, $email, $description, 
		$id, $german, $spanish, $english, $french, $biology, $chemistry, $music, $maths, $physics
	);
	
	if (mysqli_stmt_num_rows($statement) > 0) {
	
		$results = array();
		$array = array();
		$results['success'] = true;
		$count_1 = 0;
		
		while($field = mysqli_stmt_fetch($statement)) {
			$innerarray = array();
			
			$innerarray['user_username'] = $username;
			$innerarray['user_name'] = $name;
			$innerarray['user_firstname'] = $firstname;
			$innerarray['user_school'] = $school;
			$innerarray['user_grade'] = $grade;
			$innerarray['user_email'] = $email;
			$innerarray['user_description'] = $description;
			$innerarray['user_id'] = $id;
			
			$innerarray['subj_german'] = $german;
			$innerarray['subj_spanish'] = $spanish;
			$innerarray['subj_english'] = $english;
			$innerarray['subj_french'] = $french;
			$innerarray['subj_biology'] = $biology;
			$innerarray['subj_chemistry'] = $chemistry;
			$innerarray['subj_music'] = $music;
			$innerarray['subj_maths'] = $maths;
			$innerarray['subj_physics'] = $physics;
			$innerarray['hash_password'] = null;
			
			//$innerarray['blob_profilepicture_small'] = $profilepicture_small;
			
			$array[$count_1] = $innerarray;
			$count_1 = $count_1 + 1;
		}
		
		$results['results'] = $array;
		
		print_r(json_encode($results));
		
	} else {
		$results['success'] = false;
		$results['statement'] = $statement_string;
		print_r(json_encode($results));
	}
?>
