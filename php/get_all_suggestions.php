<?php

$con =new mysqli("194.5.156.94","u788111513_goldenbeach","goldenBeach@777","u788111513_goldenbeach");


if(!$con){
	echo "noooooooooo";
	
	
}else{

		//$select_query="";
						
		$select_query=mysqli_query($con,"SELECT users.user_id,users.user_name,users.user_name_of_image,users_suggestions.suggestion 
                                          FROM users,users_suggestions WHERE users.user_id=users_suggestions.id_of_user
                                          ORDER By users_suggestions.suggestion_id DESC limit $_GET[limit],5");

		if(mysqli_num_rows($select_query) == 0){

		$post["user_id"]=0;	
		$post["user_name"]="null";
$post["user_name_of_image"]="null";
		$post["suggestion"]="null";

	echo "[]";
		//echo json_encode($post);
		//print(json_encode($post));


}else{


while($row=mysqli_fetch_assoc($select_query)){

	$output[]=$row;


	//print(json_encode($output));	
}
echo json_encode($output);




	
}



}	