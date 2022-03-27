<?php session_start(); ?>
<?php 
include_once('../includes/layout/header.php');
include_once('../includes/session.php');
include_once('../includes/functions.php');
include_once('../includes/cnnectdb.php');

login_check ();

 $target_dir = "uploads/";
 $fname=$_FILES["fileToUpload"]["name"];
 $ftemp=$_FILES["fileToUpload"]["tmp_name"];

$oldimagepath=$_POST["oldimagepath"]; 

if(isset($_POST["submit"]))
{

    for ($i=0; $i <sizeof($fname) ; $i++) { 
      $datetime =date("Ymdhisa");
    # code...
    $target_file = $target_dir.$datetime.$fname[$i].".jpg";
    $imageFileType = pathinfo($target_file,PATHINFO_EXTENSION);
    $ftemp1=$ftemp[$i];
      //move_uploaded_file($ftemp[$i],$target_file);

    }
    var_dump($_POST["name_image"]);
	$sql = ' UPDATE `imagestaple` SET `name`="'.$_POST["name_image"].'",`story`= "'.$_POST["story"].'",`trailer`="'.$_POST["trailer"].'",`season`="'.$_POST["season"].'",`year`="'.$_POST["year"].'",`imagepath`="'.$target_file.'",`catygory`="'.$_POST["catygory"].'",`brand_of_section`="'.$_POST["brand_of_section"].'",`name_of_section`="'.$_POST["name_of_section"].'" WHERE id = "'.$_POST["filmid"].'" ' ; 


	

	if (mysqli_query($conn, $sql) && mysqli_affected_rows($conn)>0) {
    	 $_SESSION['msg']=success_msg_menu();
         unlink($oldimagepath);
         move_uploaded_file($ftemp1,$target_file);
       redirect ("manage_content.php");


    	?>

    	<br>
    	<?php
	} else {
    		echo "Error: " . $sql . "<br>" . mysqli_error($conn);
	}

}	

mysqli_close($conn);
?>