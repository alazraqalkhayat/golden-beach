<?php session_start(); ?>
<?php 
include_once('../includes/layout/header.php');
include_once('../includes/session.php');
include_once('../includes/functions.php');
include_once('../includes/cnnectdb.php');

login_check ();



$images=$_POST["array_of_image"]; 
// Create connection
// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}
for ($i=0; $i < sizeof($_POST["array_of_image"]); $i++) { 
	
	$sql = 'INSERT INTO imagestaple (name, story, trailer, season, year, imagepath, catygory, brand_of_section, name_of_section) VALUES ("'.$_POST["name_image"][$i].'","'.$_POST["story"][$i].'","'.$_POST["trailer"][$i].'","'.$_POST["season"][$i].'","'.$_POST["year"][$i].'","'.$_POST["array_of_image"][$i].'","'.$_POST["catygory"][$i].'","'.$_POST["brand_of_section"][$i].'","'.$_POST["name_of_section"][$i].'")';

	if (mysqli_query($conn, $sql) && mysqli_affected_rows($conn)>0) {
    	 $_SESSION['msg']=success_msg_menu();
       


    	?>

    	<br>
    	<?php
	} else {
    		echo "Error: " . $sql . "<br>" . mysqli_error($conn);
	}

	
}
redirect ("manage_content.php");
mysqli_close($conn);
?>