<!-- Tara Moses -->
<!-- Web Apps Assignment 15: Simple File Upload -->
<!-- April 28, 2014 -->

<!DOCTYPE html>
<body bgcolor="#24dbc3" style="text-align: center">
<font face="century gothic">

	<h1>Simple File Upload!</h1>
	
	<form action="actuallyUploadThings.php" method="post" enctype="multipart/form-data">
		<label for="file">Filename:</label>
		<input type="file" name="file" id="file"><br>
		<input type="submit" name="submit" value="Submit">
	</form>
	
	<h1>Uploaded Pictures</h1>
	
	<?php
		session_start();
		if (!isset($_SESSION["arrow"])) {
			$_SESSION["arrow"]=0;
		}
		$i=$_SESSION["arrow"];
		
		if (isset($_GET["arrow"])) {
			if ($_GET["arrow"]==">") $i++;
			else if ($_GET["arrow"]=="<") $i--;
		}
		
		$pictures=glob("upload/*.*");
		
		if ($i>=count($pictures)) $i=0;
		else if ($i<0) $i=count($pictures)-1;
		$_SESSION["arrow"]=$i;
		
		$filename=$pictures[$i];
	?>
	
	<form action="" method="GET">
		<input type="submit" name="arrow" value="<">
		<?php
			echo "<img src='$filename' alt='$filename'>";
		?>
		<input type="submit" name="arrow" value=">">
	</form>
	
	<br>
	<a href="../index.html">Back to Index</a>

</font>
</body>
</html>
