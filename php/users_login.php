<?php

$con =new mysqli("194.5.156.94","u788111513_goldenbeach","goldenBeach@777","u788111513_goldenbeach");



$r=mysqli_query($con,"SELECT user_name_of_image,user_id,user_full_name,user_email,user_name,user_password FROM `users` where user_name='".$_GET['user_name']."'");

if(!$con){
	echo "noooooooooo";
	
	
}else{


if(mysqli_num_rows($r) == 0){

$post["user_name_of_image"]="null";
$post["user_id"]=0;
$post["user_full_name"]="null";
$post["user_email"]="null";
$post["user_name"]="null";
$post["user_password"]="null";
echo json_encode($post);
//print(json_encode($post));


}else{


while($row=mysqli_fetch_assoc($r)){

	$output=$row;
	echo json_encode($output);

//print(json_encode($output));	
}





	
}



}