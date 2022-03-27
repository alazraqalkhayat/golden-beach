<?php

$con =new mysqli("194.5.156.94","u788111513_goldenbeach","goldenBeach@777","u788111513_goldenbeach");


if(!$con){
	echo "noooooooooo";
	
	
}else{

		
						
		$select_query=mysqli_query($con,"SELECT users.user_id,users.user_name,users.user_name_of_image,users_comments.comment
                                                 FROM users,users_comments WHERE users.user_id=users_comments.id_of_user and 
                                                 users_comments.id_of_all='".$_GET[id_of_all]."'ORDER By comment_id DESC limit $_GET[limit],5");

		if(mysqli_num_rows($select_query) == 0){

			
		$post["user_name"]="null";
$post["user_name_of_image"]="null";
		$post["comment"]="THER IS NO COMMENT YET ";


echo "[]";
//$out[]=$post;
		//echo json_encode($out);

	
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