<?php

$target_dir = "all_images/";
$fname=$_FILES["fileToUpload"]["name"];
$ftemp=$_FILES["fileToUpload"]["tmp_name"];
if(isset($_POST["submit"])) {
	?>
	<form action="save.php" method="post">
	<?php
	for ($i=0; $i <sizeof($fname) ; $i++) { 

      $datetime =date("Ymdhisa");
		# code...
		$target_file = $target_dir.$fname[$i].".jpg";
		$imageFileType = pathinfo($target_file,PATHINFO_EXTENSION);
   		move_uploaded_file($ftemp[$i], $target_file);
   ?>
   <div style="border: solid 2px; width: 80vw;margin: 1vh 10vw">
   	<div>
   <img src="<?php echo $target_file ?>" name="photo">
   <input type="hidden" name="array_of_image[<?php echo $i ?>]" value="<?php echo $target_file ?>">
   <input type="hidden" name="images_names[<?php echo $i ?>]" value="<?php echo $fname[$i] ?>">
   </div>
   <div id="detialsDiv">
   <input type="text" name="name_image[]" placeholder="The name" value="<?php echo $fname[$i] ?>" class="detials">
   <input type="text" name="story[]" placeholder="Story" class="detials">
   <input type="text" name="trailer[]" placeholder="Trailer" class="detials">
   <input type="number" name="season[]" placeholder="Season" class="detials">
   <input type="number" name="year[]" placeholder="Year" class="detials">
   
   </div>
   <div id="detialsDiv2">
   	<label>catygory</label>
   	<select name="catygory[]">
   		<option>OTHERS</option>
   	</select>
   	<label>brand of section</label>
   	<select name="brand_of_section[]">
		<option>OTHERS</option>
   	</select>
   	<label>name of section</label>
   	<select name="name_of_section[]">
   		<option>OTHERS</option>
   		
   	</select>
   </div>
   </div>
   <?php
}

}
?>
    <input type="submit" value="Save" name="Save">
</form>
<style type="text/css">
	body
	{
		font-size: 18px;
	}
	img
	{
		width: 60%;
    	height: 20%;
		margin: 10px;
	}
	.detials
	{
		display: block;
		width: 100%;
		height: 33px;
		margin-top: 5px;
		margin-bottom: 5px;
		font-size: 18px;
	}

	div
	{
		float: left;
		width: 30%;

	}
	#detialsDiv
	{
        width: 40%;
	}
	#detialsDiv2
	{
		width: 29%;
    	margin: 5px 5px 5px 5px;

	}
	select,label
	{
		display: block;
		width: 100%;
		height: 4%;
	}
</style>
<?php
	
 
     
?>