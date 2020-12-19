<?php
    $con = mysqli_connect("localhost", "root", "20113602hje", "whatfoo");
    mysqli_query($con,'SET NAMES utf8');

    $userId = $_POST["userId"];
    $userPw = $_POST["userPw"];
    $userName = $_POST["userName"];
    $userGender = $_POST["userGender"];

    $statement = mysqli_prepare($con, "INSERT INTO member VALUES (?,?,?,?)");
    mysqli_stmt_bind_param($statement, "sssi", $userId, $userPw, $userName, $userGender);
    mysqli_stmt_execute($statement);


    $response = array();
    $response["success"] = true;


    echo json_encode($response);

?>