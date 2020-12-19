<?php
    $con = mysqli_connect("localhost", "root", "20113602hje", "whatfoo");
    if (!$con) {
	echo "MySQL 접속 에러";
    } else {
	echo "MySQL 접속 완료";
    }
    mysqli_query($con,'SET NAMES utf8');

    $userId = $_POST["userId"];
    $userPw = $_POST["userPw"];


    $statement = mysqli_prepare($con, "SELECT * FROM member userId = ? AND userPw = ?");
    mysqli_stmt_bind_param($statement, "ss", $userId, $userPw);
    mysqli_stmt_execute($statement);


    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userId, $userPw, $userName, $userGender);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        $response["userId"] = $userId;
        $response["userPw"] = $userPw;
        $response["userName"] = $userName;
        $response["userGender"] = $userGender;
    }

    echo json_encode($response);


?>