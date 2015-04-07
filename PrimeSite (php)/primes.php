<?PHP
	/****************************************
	* Author: Peter J De Sousa
	*	petedes@live.com
	*
	*	mini project.
	*		prime finder backend
	*
	*******************************************/
		function generateTable($trClass, $tdClass, $tableID = "", $numTR=5, $numTC=5)
	{
		$table = "<table>";
		for($rows = 0; $rows < $numTR; $rows++)
		{
			$table .= "<tr>";
			for($cols = 0; $cols < $numTC; $cols++)
				$table .= "<td></td>";
			$table .= "</tr>";
		}
		return $table;
	}
	
	function getAndDisplayPrimes()
	{
	$sql = new mysqli("localhost", "primes", "primes123", "primes");
	$sqlresult  = $sql->query("select t.ms, p.prime_id, p.prime from prime p
	 join time_lapsed t on t.prime_id = p.prime_id order by prime_id desc limit 100;");
	//print("hello");
	if($sqlresult == false)
	{
		die("Error retrieving primes");
	}
	$page = "
	<!doctype html>
	<html>
	<head>
	<script>
		function fetchTable()
		{
			var tables = document.getElementsByTagName('table');
			var tableRequest = new XMLHttpRequest();
			tableRequest.open('GET', 'primes.php?innerTable');
			tableRequest.onreadystatechange = function() 
			{
				if(tableRequest.readyState == 4)
				{
					if(tableRequest.status == 200)
						tables[0].innerHTML = tableRequest.responseText;
					if(tableRequest.status == 404)
						tables[0].innerHMTL = 'Error retrieving values from the database';
				}
			};
			tableRequest.send();
		}
	
	
	</script>
	<link href='css/primesTheme.css' rel='stylesheet' />
	<title> Prime Site! </title>
	</head>
	<body>
	<h1> Primes found so far with the prime application </h1>
	<div id='wrapper'>
	<button type='button' onclick='fetchTable()'>Refresh table</button>
	<table>
	<tr>
		<td class='timeLapsed'> Time Lapsed (ms) </td>
		<td class='prime'> Prime </td>
		<td class='primeID'> Prime ID </td>
	</tr>
	</div>
	";
	//need to close html and body.
	
	for($i = 0; $i < $sqlresult->num_rows; $i++)
	{
		$valArr = $sqlresult->fetch_array(MYSQLI_NUM);
		$page .= "<tr>
			<td class='timeLapsed'> $valArr[0] </td>
			<td class='prime'> $valArr[2] </td>
			<td class='primeID'> $valArr[1] </td>
			</tr>";
	}
	$page .= "
		</table>
		</body>
		</html>";
	$sqlresult->free();
	$sql->close();
	echo($page);
	}
	
	if(isset($_GET['admin']))
	{
		
	}
	if(isset($_GET['innerTable']))
	{
		$sql = new mysqli("localhost", "primes", "primes123", "primes");
		$sqlresult  = $sql->query("select t.ms, p.prime_id, p.prime from prime p
	 join time_lapsed t on t.prime_id = p.prime_id order by prime_id desc limit 100;");
		//print("hello");
		if($sqlresult == false)
		{
			die("Error retrieve primes");
		}
		$page = "<tr>
		<td class='time'> Time Lapsed (ms) </td>
		<td class='prime'>Prime</td>
		<td class='primeID'>Prime ID</td>
		</tr>";
		for($i = 0; $i < $sqlresult->num_rows; $i++)
		{
			$valArr = $sqlresult->fetch_array(MYSQLI_NUM);
			$page .= "
				<tr>
				<td class='time'> $valArr[0] </td>
				<td class='prime'> $valArr[2] </td>
				<td class='primeID'> $valArr[1] </td>
				</tr>
			";
		}
		echo($page);
		return;
	}
	getAndDisplayPrimes();
?>