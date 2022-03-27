<?php
/* Code for sign up*/
$con =new mysqli("194.5.156.94","u788111513_goldenbeach","goldenBeach@777","u788111513_goldenbeach");

//check for required fields

if (isset($_POST['user_full_name']) && isset($_POST['user_email']) && isset($_POST['user_name']) && isset($_POST['user_password']) && isset($_POST['user_name_of_image'])) {
	//get data from post
	$full_name=$_POST['user_full_name'];
	$email=$_POST['user_email'];
	$name=$_POST['user_name'];
	$password=$_POST['user_password'];
	$name_of_image=$_POST['user_name_of_image'];

$path="users_images/$name.jpg";

	$Vname= mysqli_query($con,"SELECT * FROM  `users` WHERE user_name='$name'");
	$Vemail = mysqli_query($con,"SELECT * FROM  `users` WHERE  user_email='$email'");
	//check if user exist
	if(mysqli_num_rows($Vname)==0) {
		//check if email exist
	
			if (mysqli_num_rows($Vemail) == 0) {
$insert= mysqli_query($con, "INSERT INTO users (user_full_name,user_email,user_name,user_password,user_name_of_image) values('$full_name','$email','$name','$password','$path')");
if($insert){

file_put_contents($path,base64_decode($name_of_image));

$response["response_message"] = "Registration successful";
                        echo json_encode($response);
}else{

$response["response_message"] = "error";
                        echo json_encode($response);
}


		} else {
			//Email exist
			$response["response_message"] = "Email Already Exists";
                        echo json_encode($response);
		}

	}else{
		
		$response["response_message"] = "Name Already Exists";
                echo json_encode($response);

	}





} else {
	// required field is missing
	$response["response_message"] = "Required fields is missing";
	// print JSON response
	echo json_encode($response);
}	
	