<?php

$con =new mysqli("194.5.156.94","u788111513_goldenbeach","goldenBeach@777","u788111513_goldenbeach");


if(!$con){
	echo "noooooooooo";
	
	
}else{

						
		//$select_query=mysqli_query($con,"SELECT name_of_image_of_add,description FROM adds ORDER By add_id DESC");

		$select_query=mysqli_query($con,"SELECT * FROM adds ORDER By add_id DESC");

		if(mysqli_num_rows($select_query) == 0){

		
		
		$post["name_of_image_of_add"]="null";
		$post["description"]="null";

	$out[]=$post;
		echo json_encode($out);
		
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