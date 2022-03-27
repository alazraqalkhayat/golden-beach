<?php
include_once('../includes/cnnectdb.php');
if (isset($_GET['term'])) {
     
   $query = "SELECT * FROM imagestaple WHERE name LIKE '{$_GET['term']}%' LIMIT 25";
    $result = mysqli_query($conn, $query);
 
    if (mysqli_num_rows($result) > 0) {
     while ($flim = mysqli_fetch_array($result)) {
      $res[] = $flim['name'].','.$flim['season'];
     }
    } else {
      $res = array();
    }
    //return json res
    echo json_encode($res);
}
?>