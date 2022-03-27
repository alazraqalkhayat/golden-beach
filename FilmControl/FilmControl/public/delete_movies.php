<?php session_start(); ?>
<?php 
include_once('../includes/layout/header.php');
include_once('../includes/session.php');
include_once('../includes/functions.php');
include_once('../includes/cnnectdb.php');

login_check ();
?>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>Delete Movies</title>
 
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.css" />
 
  <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
  <!-- Bootstrap Css -->
  <!--link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet"-->
</head>
<body> 
<div class="container">
  <div class="row">
     <h2>Search Here</h2>
     <form action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>" method="POST" style="display: contents;">
     <input type="text" name="search" id="search" placeholder="search here...." class="form-control"> 
     <center style="width: 100%;">
      <button name="submit" type="submit" style="width:50% ; background-color: black; color: white; border-radius: 10px; height: 50px; font-size: x-large; margin-top: 50px;"><span>DELETE</span></button>
  </center>
    </form> 
  </div>

</div>
<script type="text/javascript">
  $(function() {
     $( "#search" ).autocomplete({
       source: 'ajax-db-search.php',
     });
  });
</script>
</body>
</html>
<?php
if ($_SERVER["REQUEST_METHOD"]=="POST") {
  # code...

$film=$_POST["search"]; 
// Create connection
// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}
$film_season= explode(",", $film);
  $fName=$film_season[0];
  $fSeason=(int)$film_season[1];
  $sql = "DELETE FROM imagestaple WHERE name='$fName' AND  season='$fSeason' ";
  $sql2 = "SELECT imagepath FROM imagestaple WHERE name='$fName' AND  season='$fSeason' ";
$result = mysqli_query($conn, $sql2);
if (mysqli_num_rows($result) > 0) {
    // output data of each row
    while($row = mysqli_fetch_assoc($result)) {
        $pathImage=$row["imagepath"];
    }
  }
  if (mysqli_query($conn, $sql) && mysqli_affected_rows($conn)>0) {
    unlink($pathImage);
       $_SESSION['msg']=success_delete_msg_menu();
       redirect ("manage_content.php");


      ?>

      <br>
      <?php
  } else {
    $_SESSION['msg']=fail_delete_msg_menu();
        redirect ("manage_content.php");
  }

  

mysqli_close($conn);
}
?>