<?php

$con =new mysqli("194.5.156.94","u788111513_goldenbeach","goldenBeach@777","u788111513_goldenbeach");


if(!$con){
	echo "noooooooooo";
	
	
}else{

		//$select_query="";
//$limit=$_GET[limit];
						
		$select_query=mysqli_query($con,"SELECT users.user_id,users.user_name,users.user_name_of_image,
                                          users_complainants.complainant FROM users,users_complainants WHERE 
                                          users.user_id=users_complainants.id_of_user ORDER By users_complainants.complainant_id DESC limit $_GET[limit],5");

		if(mysqli_num_rows($select_query) == 0){

		$post["user_id"]=0;	
		$post["user_name"]="null";
                $post["user_name_of_image"]="null";
		$post["complainants"]="null";

	
		echo "[]";
		//print(json_encode($post));


}else{


while($row=mysqli_fetch_assoc($select_query)){

	$output[]=$row;


	//print(json_encode($output));	
}
echo json_encode($output);




	
}



}			